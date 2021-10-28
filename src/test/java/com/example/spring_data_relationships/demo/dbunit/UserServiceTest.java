package com.example.spring_data_relationships.demo.dbunit;

import com.example.spring_data_relationships.configuration.SpringConfig;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.service.UserService;
import lombok.SneakyThrows;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {SpringConfig.class}, loader = AnnotationConfigContextLoader.class)
@TestPropertySource(locations = "classpath:application.properties")
public class UserServiceTest extends DataSourceBasedDBTestCase {

    @Autowired
    private UserService userService;

    private Connection connection;

    @Override
    public DataSource getDataSource() {
        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:dbunit/schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Override
    protected IDataSet getDataSet() throws Exception {
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("dbunit/data.xml")) {
            return new FlatXmlDataSetBuilder().build(resourceAsStream);
        }
    }

    @Override
    public DatabaseOperation getSetUpOperation() {
        return DatabaseOperation.REFRESH;
    }

    @Override
    public DatabaseOperation getTearDownOperation() {
        return DatabaseOperation.DELETE_ALL;
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        connection = getConnection().getConnection();
    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    //    @Transactional
    //    @Rollback(value = true)
    @SneakyThrows
    @org.junit.Test
    public void shouldReturnUserDtoWhenCreate() {
        String expectedName = "name8";
        String expectedEmail = "email8";
        Long expectedId = 8L;
        UserDto testUser = UserDto.builder().name(expectedName).email(expectedEmail).build();

        UserDto resultUser = userService.create(testUser);

        Assert.assertEquals(expectedName, resultUser.getName());
        Assert.assertEquals(expectedEmail, resultUser.getEmail());
        Assert.assertEquals(expectedId, resultUser.getId());
    }

    @SneakyThrows
    @org.junit.Test
    public void shouldReturnUserDtoWhenGet() {
        Long expectedId = 7L;

        UserDto userDto = userService.get(expectedId).get();

        Assert.assertEquals(UserDto.class, userDto.getClass());
        Assert.assertEquals(expectedId, userDto.getId());
    }

    @SneakyThrows
    @org.junit.Test
    public void shouldReturnUpdatedUserDtoWhenUpdate() {
        String expectedName = "updatedName";
        String expectedEmail = "updatedEmail";
        Long expectedId = 1L;
        UserDto testUser = UserDto.builder()
                .id(expectedId)
                .name(expectedName)
                .email(expectedEmail)
                .build();

        UserDto resultUser = userService.update(testUser, expectedId).get();

        Assert.assertEquals(expectedName, resultUser.getName());
        Assert.assertEquals(expectedEmail, resultUser.getEmail());
        Assert.assertEquals(expectedId, resultUser.getId());
    }

    @SneakyThrows
    @org.junit.Test
    public void shouldVerifyRemoveMethodCallWhenDelete() {
        Long expectedId = 1L;

        UserService spyUserService = Mockito.spy(userService);
        spyUserService.delete(expectedId);
        Boolean exist = spyUserService.existsById(expectedId);

        verify(spyUserService, times(1)).delete(expectedId);
        Assert.assertEquals(false, exist);
    }

}

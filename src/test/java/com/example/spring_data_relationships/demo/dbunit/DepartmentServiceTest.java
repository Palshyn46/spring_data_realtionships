package com.example.spring_data_relationships.demo.dbunit;

import com.example.spring_data_relationships.configuration.SpringConfig;
import com.example.spring_data_relationships.dto.DepartmentDto;
import com.example.spring_data_relationships.service.DepartmentService;
import com.example.spring_data_relationships.service.DepartmentService;
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
public class DepartmentServiceTest extends DataSourceBasedDBTestCase {

    @Autowired
    private DepartmentService departmentService;

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

    @SneakyThrows
    @org.junit.Test
    public void shouldReturnDepartmentDtoWhenCreate() {
        String expectedName = "name8";
        Long expectedId = 8L;
        DepartmentDto testDepartment = DepartmentDto.builder().name(expectedName).build();

        DepartmentDto resultDepartment = departmentService.create(testDepartment);

        Assert.assertEquals(expectedName, resultDepartment.getName());
        Assert.assertEquals(expectedId, resultDepartment.getId());
    }

    @SneakyThrows
    @org.junit.Test
    public void shouldReturnDepartmentDtoWhenGet() {
        Long expectedId = 7L;

        DepartmentDto departmentDto = departmentService.get(expectedId).get();

        Assert.assertEquals(DepartmentDto.class, departmentDto.getClass());
        Assert.assertEquals(expectedId, departmentDto.getId());
    }

    @SneakyThrows
    @org.junit.Test
    public void shouldReturnUpdatedDepartmentDtoWhenUpdate() {
        String expectedName = "updatedName";
        String expectedEmail = "updatedEmail";
        Long expectedId = 1L;
        DepartmentDto testDepartment = DepartmentDto.builder()
                .id(expectedId)
                .name(expectedName)
                .build();

        DepartmentDto resultDepartment = departmentService.update(testDepartment, expectedId).get();

        Assert.assertEquals(expectedName, resultDepartment.getName());
        Assert.assertEquals(expectedId, resultDepartment.getId());
    }

    @SneakyThrows
    @org.junit.Test
    public void shouldVerifyRemoveMethodCallWhenDelete() {
        Long expectedId = 1L;

        DepartmentService spyDepartmentService = Mockito.spy(departmentService);
        spyDepartmentService.delete(expectedId);
        Boolean exist = spyDepartmentService.existsById(expectedId);

        verify(spyDepartmentService, times(1)).delete(expectedId);
        Assert.assertEquals(false, exist);
    }

}

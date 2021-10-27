package com.example.spring_data_relationships.demo.dbunit;

import com.example.spring_data_relationships.configuration.SpringConfig;
import com.example.spring_data_relationships.controller.UserController;
import com.example.spring_data_relationships.dao.UserDao;
import com.example.spring_data_relationships.dto.UserDto;
import com.example.spring_data_relationships.entity.DepartmentEntity;
import com.example.spring_data_relationships.entity.UserEntity;
import com.example.spring_data_relationships.service.UserService;
import com.example.spring_data_relationships.service.UserServiceImpl;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dbunit.Assertion;
import org.dbunit.DataSourceBasedDBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.Assertion.assertEqualsIgnoreCols;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(JUnit4.class)
//@Slf4j
//@SpringBootTest
//@AutoConfigureMockMvc
//@WebMvcTest(UserController.class)
//@SpringJUnitConfig
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class }, loader = AnnotationConfigContextLoader.class)
public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

//    @Autowired
//    private ApplicationContext applicationContext;

    //@MockBean
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;

    @Autowired
    UserServiceImpl userServiceImpl;

//    @Autowired
//    private MockMvc mockMvc;

//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }

//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void init() {
//        objectMapper.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
//    }

    private Connection connection;

    @Override
    public DataSource getDataSource() {

        JdbcDataSource dataSource = new JdbcDataSource();
        dataSource.setURL("jdbc:h2:mem:default;DB_CLOSE_DELAY=-1;init=runscript from 'classpath:dbunit/schema.sql'");
        dataSource.setUser("sa");
        dataSource.setPassword("");

//        Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();
//        source.setUrl("jdbc:postgresql://localhost:5432/spring_data_relationships_3");
//        source.setDataSourceName("A Data Source");
//        source.setServerName("localhost");
//        source.setDatabaseName("test");
//        source.setUser("postgres");
//        source.setPassword("123");
//        source.setMaxConnections(10); //?

//        spring.datasource.url=jdbc:postgresql://localhost:5432/spring_data_relationships_3
//        spring.datasource.username=postgres
//        spring.datasource.password=123

        return dataSource;
    }

    @Override
    public IDataSet getDataSet() throws Exception {
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

    @org.junit.jupiter.api.Test
    //@SneakyThrows
    public void shouldReturn200IfUserExist5() throws Exception{
        //userService = (UserService) applicationContext.getBean("userService");
        Long expectedId = 1L;
        String expectedName = "firstName";
        String expectedEmail = "email@email.com";
        UserDto testUser = UserDto.builder().id(expectedId).name(expectedName).email(expectedEmail).build();

        when(userService.get(eq(expectedId))).thenReturn(Optional.of(testUser));

//        MvcResult result = this.mockMvc.perform(get("/user/{id}", expectedId)).andExpect(status().isOk())
//                .andReturn();
//        String content = result.getResponse().getContentAsString();
//        UserDto expectedUser = objectMapper.readValue(content, UserDto.class);
//        assertEquals(expectedId, expectedUser.getId());
//        assertEquals(expectedName, expectedUser.getName());
//        assertEquals(expectedEmail, expectedUser.getEmail());
    }









    @Test
    public void userTableGivenDataSet_whenSelect_thenFirstNameIsName7() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("select * from user_table where id = 7");

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("name")).isEqualTo("name7");
        assertThat(rs.getString("email")).isEqualTo("email7");
    }

    @Test
    public void userTableGivenDataSet_whenInsert_thenTableHasNewUser() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("dbunit/expected-user.xml")) {
            // given
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("user_table");
            Connection conn = getDataSource().getConnection();

            // when
            conn.createStatement()
                    .executeUpdate("INSERT INTO user_table (name, email) VALUES ('name12312', 'email3213123')");
            ITable actualData = getConnection()
                    .createQueryTable("result_name", "SELECT * FROM user_table WHERE name='name7'");

            // then
            assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
        }
    }

    @Test
    public void userTableGivenDataSet_whenDelete_thenUserIsDeleted() throws Exception {
        try (InputStream is = DataSourceDBUnitTest.class.getClassLoader()
                .getResourceAsStream("dbunit/users_exp_delete.xml")) {
            // given
            ITable expectedTable = (new FlatXmlDataSetBuilder().build(is)).getTable("user_table");

            // when
            connection.createStatement().executeUpdate("delete from user_table where id = 7");

            // then
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("user_table");
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    @Test
    public void departmentTableGivenDataSet_whenSelect_thenFirstNameIsName7() throws SQLException {
        ResultSet rs = connection.createStatement().executeQuery("select * from department_table where id = 7");

        assertThat(rs.next()).isTrue();
        assertThat(rs.getString("name")).isEqualTo("name7");
    }

    @Test
    public void departmentTableGivenDataSet_whenInsert_thenTableHasNewDepartment() throws Exception {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("dbunit/expected-departments.xml")) {
            // given
            IDataSet expectedDataSet = new FlatXmlDataSetBuilder().build(is);
            ITable expectedTable = expectedDataSet.getTable("department_table");
            Connection conn = getDataSource().getConnection();

            // when
            conn.createStatement()
                    .executeUpdate("INSERT INTO department_table (name) VALUES ('name12312')");
            ITable actualData = getConnection()
                    .createQueryTable("result_name", "SELECT * FROM department_table WHERE name='name7'");

            // then
            assertEqualsIgnoreCols(expectedTable, actualData, new String[]{"id"});
        }
    }

    @Test
    public void departmentTableGivenDataSet_whenDelete_thenDepartmentIsDeleted() throws Exception {
        try (InputStream is = DataSourceDBUnitTest.class.getClassLoader()
                .getResourceAsStream("dbunit/departments_exp_delete.xml")) {
            // given
            ITable expectedTable = (new FlatXmlDataSetBuilder().build(is)).getTable("department_table");

            // when
            connection.createStatement().executeUpdate("delete from department_table where id = 7");

            // then
            IDataSet databaseDataSet = getConnection().createDataSet();
            ITable actualTable = databaseDataSet.getTable("department_table");
            Assertion.assertEquals(expectedTable, actualTable);
        }
    }

    //@Test
//    @org.junit.jupiter.api.Test
//    @SneakyThrows
//    public void testGetById() throws Exception {
//        Optional<UserDto> userDto = userService.get(1L);
//    }



//    @Test
//    @Transactional
//    @Rollback(value = true)
//    public void testAddDepartment() {
//
//    }

//
//    @ContextConfiguration(locations = "classpath:application-context-test.xml")
//    @RunWith(SpringJUnit4ClassRunner.class)
//    public class TestEmployeeDAO
//    {
//
//        @Autowired
//        private EmployeeDAO employeeDAO;
//
//        @Autowired
//        private DepartmentDAO departmentDAO;
//
//        @Test
//        @Transactional
//        @Rollback(true)
//        public void testAddDepartment()
//        {
//            DepartmentEntity department = new DepartmentEntity("Information Technology");
//            departmentDAO.addDepartment(department);
//
//            List<DepartmentEntity> departments = departmentDAO.getAllDepartments();
//            Assert.assertEquals(department.getName(), departments.get(0).getName());
//        }
//
//        @Test
//        @Transactional
//        @Rollback(true)
//        public void testAddEmployee()
//        {
//            DepartmentEntity department = new DepartmentEntity("Human Resource");
//            departmentDAO.addDepartment(department);
//
//            EmployeeEntity employee = new EmployeeEntity();
//            employee.setFirstName("Lokesh");
//            employee.setLastName("Gupta");
//            employee.setEmail("howtodoinjava@gmail.com");
//            employee.setDepartment(department);
//
//            employeeDAO.addEmployee(employee);
//
//            List<DepartmentEntity> departments = departmentDAO.getAllDepartments();
//            List<EmployeeEntity> employees = employeeDAO.getAllEmployees();
//
//            Assert.assertEquals(1, departments.size());
//            Assert.assertEquals(1, employees.size());
//
//            Assert.assertEquals(department.getName(), departments.get(0).getName());
//            Assert.assertEquals(employee.getEmail(), employees.get(0).getEmail());
//        }
//    }
}
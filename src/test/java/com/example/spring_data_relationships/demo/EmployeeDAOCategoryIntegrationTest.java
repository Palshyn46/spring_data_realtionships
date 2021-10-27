package com.example.spring_data_relationships.demo;

//        import com.baeldung.junit.tags.example.Employee;
//        import com.baeldung.junit.tags.example.EmployeeDAO;
//        import com.baeldung.junit.tags.example.SpringJdbcConfig;
        import com.example.spring_data_relationships.configuration.SpringConfig;
        import com.example.spring_data_relationships.dao.UserDao;
        import com.example.spring_data_relationships.junit5.SpringJdbcConfig;
        import com.example.spring_data_relationships.service.UserService;
        import org.hamcrest.CoreMatchers;
        import org.junit.Assert;
        import org.junit.Before;
        import org.junit.Test;
        import org.junit.experimental.categories.Category;
        import org.junit.runner.RunWith;
        import org.mockito.Mock;
        import org.mockito.Mockito;
        import org.mockito.MockitoAnnotations;
        import org.springframework.beans.factory.annotation.Autowired;
        import org.springframework.jdbc.core.JdbcTemplate;
        import org.springframework.test.context.ContextConfiguration;
        import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
        import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SpringConfig.class }, loader = AnnotationConfigContextLoader.class)
public class EmployeeDAOCategoryIntegrationTest {

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;

//    @Mock
//    private JdbcTemplate jdbcTemplate;
//    private EmployeeDAO employeeDAO;

//    @Before
//    public void setup() {
//        MockitoAnnotations.initMocks(this);
//        employeeDAO = new EmployeeDAO();
//        employeeDAO.setJdbcTemplate(jdbcTemplate);
//    }

    @org.junit.Test
    @Category(IntegrationTest.class)
    public void testAddEmployeeUsingSimpelJdbcInsert() {

        UserDao userDao1 = userDao;
        UserService userService1 = userService;
//        final Employee emp = new Employee();
//        emp.setId(12);
//        emp.setFirstName("testFirstName");
//        emp.setLastName("testLastName");
//        emp.setAddress("testAddress");
//
//        Assert.assertEquals(employeeDao.addEmplyeeUsingSimpelJdbcInsert(emp), 1);
    }

//    @Test
//    @Category(UnitTest.class)
//    public void givenNumberOfEmployeeWhenCountEmployeeThenCountMatch() {
//
//        // given
//        Mockito.when(jdbcTemplate.queryForObject(Mockito.any(String.class), Mockito.eq(Integer.class)))
//                .thenReturn(1);
//
//        // when
//        int countOfEmployees = employeeDAO.getCountOfEmployees();
//
//        // then
//        Assert.assertThat(countOfEmployees, CoreMatchers.is(1));
//    }

}

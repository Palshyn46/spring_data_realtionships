package com.example.spring_data_relationships.demo.dbunit;

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
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.Assertion.assertEqualsIgnoreCols;

@RunWith(JUnit4.class)
@Slf4j
public class DataSourceDBUnitTest extends DataSourceBasedDBTestCase {

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
}

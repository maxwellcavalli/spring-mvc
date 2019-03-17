package br.com.fh.exampleorder.dao.impl;

import br.com.fh.exampleorder.dao.CustomerDao;
import br.com.fh.exampleorder.model.customer.Customer;
import br.com.fh.exampleorder.model.user.Login;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DefaultCustomerDao implements CustomerDao {

    @Autowired
    private DataSource datasource;

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Customer> findAll() {
        String sql = "select id, nome from cliente order by nome ";
        List<Customer> customers = jdbcTemplate.query(sql, new CustomerMapper());
        return customers;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public DataSource getDatasource() {
        return datasource;
    }

    public void setDatasource(DataSource datasource) {
        this.datasource = datasource;
    }
}

class CustomerMapper implements RowMapper<Customer> {
    public Customer mapRow(ResultSet rs, int arg1) throws SQLException {

        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("nome"));

        return customer;
    }
}

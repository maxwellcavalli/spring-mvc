package br.com.fh.exampleorder.dao.impl;

import br.com.fh.exampleorder.dao.ProductDao;
import br.com.fh.exampleorder.model.product.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DefaultProductDao implements ProductDao {

    @Autowired
    private DataSource datasource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Product> findAll() {
        String sql = "select id, nome from produto order by nome ";
        List<Product> customers = jdbcTemplate.query(sql, new ProductMapper());
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

class ProductMapper implements RowMapper<Product> {
    public Product mapRow(ResultSet rs, int arg1) throws SQLException {

        Product product = new Product();
        product.setId(rs.getInt("id"));
        product.setName(rs.getString("nome"));

        return product;
    }
}

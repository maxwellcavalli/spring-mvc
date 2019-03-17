package br.com.fh.exampleorder.dao.impl;

import br.com.fh.exampleorder.dao.OrderDao;
import br.com.fh.exampleorder.model.customer.Customer;
import br.com.fh.exampleorder.model.order.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class DefaultOrderDao implements OrderDao {

    @Autowired
    private DataSource datasource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public Order register(final Order order) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert
                .withTableName("pedido")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("data", order.getDate())
                .addValue("id_cliente", order.getCustomer().getId())
                .addValue("situacao", order.getSituation());
        Number number = simpleJdbcInsert.executeAndReturnKey(params);

        order.setId(number.intValue());

        return order;
    }

    public void update(Order order) {
        String sql = "update pedido " +
                "set situacao = ? " +
                " where id = ?";
        jdbcTemplate.update(sql, new Object[]{order.getSituation(), order.getId()});
    }

    public List<Order> findAll() {
        String sql = "select ped.id as id_pedido, ped.data as data_pedido, ped.situacao as situacao_pedido," +
                " cli.id as id_cliente, cli.nome as nome_cliente " +
                " from pedido ped " +
                " inner join cliente cli on cli.id = ped.id_cliente " +
                " order by ped.id desc ";
        List<Order> orders = jdbcTemplate.query(sql, new OrderMapper());
        return orders;
    }

    @Override
    public Order load(Integer id) {
        String sql = "select ped.id as id_pedido, ped.data as data_pedido, ped.situacao as situacao_pedido," +
                " cli.id as id_cliente, cli.nome as nome_cliente " +
                " from pedido ped " +
                " inner join cliente cli on cli.id = ped.id_cliente " +
                " where ped.id = :id ";
        List<Order> orders = namedJdbcTemplate.query(sql,
                new MapSqlParameterSource()
                        .addValue("id", id),
                new OrderMapper());

        if (!orders.isEmpty()) {
            return orders.get(0);
        } else {
            return null;
        }
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

class OrderMapper implements RowMapper<Order> {
    public Order mapRow(ResultSet rs, int arg1) throws SQLException {
        Order order = new Order();
        order.setId(rs.getInt("id_pedido"));
        order.setDate(rs.getDate("data_pedido"));
        order.setSituation(rs.getInt("situacao_pedido"));

        Customer customer = new Customer();
        customer.setId(rs.getInt("id_cliente"));
        customer.setName(rs.getString("nome_cliente"));
        order.setCustomer(customer);

        return order;
    }
}

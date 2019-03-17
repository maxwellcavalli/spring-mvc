package br.com.fh.exampleorder.dao.impl;

import br.com.fh.exampleorder.dao.OrderIteDao;
import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.model.order.OrderIte;
import br.com.fh.exampleorder.model.product.Product;
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

public class DefaultOrderIteDao implements OrderIteDao {

    @Autowired
    private DataSource datasource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedJdbcTemplate;

    public void register(final OrderIte orderIte) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        simpleJdbcInsert
                .withTableName("pedido_item")
                .usingGeneratedKeyColumns("id");
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("id_pedido", orderIte.getOrder().getId())
                .addValue("id_produto", orderIte.getProduct().getId())
                .addValue("quantidade", orderIte.getQuantity());
        Number number = simpleJdbcInsert.executeAndReturnKey(params);

        orderIte.setId(number.intValue());
    }


    @Override
    public void delete(Order order) {
        String sql = "delete from pedido_item where id_pedido = ?";
        jdbcTemplate.update(sql, new Object[]{order.getId()});
    }

    /*public void update(OrderIte orderIte) {
        String sql = "update pedido_item " +
                " set quantidade = ?, " +
                " where id = ?";
        jdbcTemplate.update(sql, new Object[]{orderIte.getQuantity(), orderIte.getId()});
    }*/

    public List<OrderIte> findAll(final Order order) {
        String sql = "select ped_item.id as ped_item_id, ped_item.quantidade as ped_item_quantidade, " +
                " ped.id as ped_id, " +
                " prod.id as prod_id, prod.nome as prod_nome " +
                " from pedido_item ped_item " +
                " inner join pedido ped on ped.id = ped_item.id_pedido " +
                " inner join produto prod on prod.id = ped_item.id_produto " +
                " where ped.id = :ped_id " +
                " order by ped_item.id ";
        List<OrderIte> orders = namedJdbcTemplate.query(sql,
                new MapSqlParameterSource()
                        .addValue("ped_id", order.getId()),
                new OrderIteMapper());
        return orders;
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

class OrderIteMapper implements RowMapper<OrderIte> {
    public OrderIte mapRow(ResultSet rs, int arg1) throws SQLException {
        OrderIte orderIte = new OrderIte();
        orderIte.setId(rs.getInt("ped_item_id"));
        orderIte.setQuantity(rs.getInt("ped_item_quantidade"));

        Order order = new Order();
        order.setId(rs.getInt("ped_id"));
        orderIte.setOrder(order);

        Product product = new Product();
        product.setId(rs.getInt("prod_id"));
        product.setName(rs.getString("prod_nome"));
        orderIte.setProduct(product);

        return orderIte;
    }
}

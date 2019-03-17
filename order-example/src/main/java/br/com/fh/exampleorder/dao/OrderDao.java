package br.com.fh.exampleorder.dao;

import br.com.fh.exampleorder.model.order.Order;

import java.util.List;

public interface OrderDao {

    Order register(Order order);

    void update(Order order);

    List<Order> findAll();

    Order load(Integer id);
}

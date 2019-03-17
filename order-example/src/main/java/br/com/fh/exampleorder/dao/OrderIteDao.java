package br.com.fh.exampleorder.dao;

import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.model.order.OrderIte;

import java.util.List;

public interface OrderIteDao {
    void register(OrderIte orderIte);

    void delete(Order order);

    List<OrderIte> findAll(Order order);
}

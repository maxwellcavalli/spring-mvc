package br.com.fh.exampleorder.service;

import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.model.order.OrderIte;

import java.util.List;

public interface OrderService {

    void register(Order order, List<OrderIte> itens);

    List<Order> findAll();

    void delete(Integer id);

    Order load(Integer id);
}

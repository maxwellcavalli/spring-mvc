package br.com.fh.exampleorder.service;

import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.model.order.OrderIte;

import java.util.List;

public interface OrderIteService {

    void register(Order order, List<OrderIte> itens);

    List<OrderIte> findAll(Order order);
}

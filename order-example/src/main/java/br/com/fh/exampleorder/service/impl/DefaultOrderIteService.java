package br.com.fh.exampleorder.service.impl;

import br.com.fh.exampleorder.dao.OrderIteDao;
import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.model.order.OrderIte;
import br.com.fh.exampleorder.service.OrderIteService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultOrderIteService implements OrderIteService {

    @Autowired
    private OrderIteDao orderIteDao;

    public void register(final Order order, final List<OrderIte> itens) {

        orderIteDao.delete(order);

        for (OrderIte item : itens) {
            item.setOrder(order);
            orderIteDao.register(item);
        }
    }

    public void setOrderIteDao(OrderIteDao orderIteDao) {
        this.orderIteDao = orderIteDao;
    }

    public List<OrderIte> findAll(Order order) {
        return orderIteDao.findAll(order);
    }
}

package br.com.fh.exampleorder.service.impl;

import br.com.fh.exampleorder.dao.OrderDao;
import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.model.order.OrderIte;
import br.com.fh.exampleorder.service.OrderIteService;
import br.com.fh.exampleorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultOrderService implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderIteService orderIteService;

    public void register(final Order order, final List<OrderIte> itens) {
        if (order.getId() != 0) {
            orderDao.update(order);
        } else {
            Order oRet = orderDao.register(order);
            order.setId(oRet.getId());
        }

        orderIteService.register(order, itens);
    }

    public List<Order> findAll() {
        return orderDao.findAll();
    }

    @Override
    public void delete(Integer id){
        Order order = load(id);
        order.setSituation(1);

        orderDao.update(order);
    }

    @Override
    public Order load(Integer id) {
        Order order = orderDao.load(id);
        if (order != null){
            List<OrderIte> itens = orderIteService.findAll(order);
            order.setItens(itens);
        }

        return order;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public void setOrderIteService(OrderIteService orderIteService) {
        this.orderIteService = orderIteService;
    }
}

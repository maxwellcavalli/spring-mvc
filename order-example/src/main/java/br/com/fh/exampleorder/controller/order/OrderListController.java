package br.com.fh.exampleorder.controller.order;

import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("order-list-controller")
public class OrderListController {

    @Autowired
    public OrderService orderService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        List<Order> orders = orderService.findAll();

        ModelAndView mav = new ModelAndView("order-list");
        mav.addObject("orders", orders);
        return mav;
    }

}

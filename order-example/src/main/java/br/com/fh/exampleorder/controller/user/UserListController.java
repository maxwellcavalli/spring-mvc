package br.com.fh.exampleorder.controller.user;

import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.model.user.User;
import br.com.fh.exampleorder.service.OrderService;
import br.com.fh.exampleorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


@Controller
@RequestMapping("user-list-controller")
public class UserListController {

    @Autowired
    public UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        List<User> users = userService.findAll();

        ModelAndView mav = new ModelAndView("user-list");
        mav.addObject("users", users);
        return mav;
    }

}

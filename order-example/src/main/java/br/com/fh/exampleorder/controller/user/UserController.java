package br.com.fh.exampleorder.controller.user;

import br.com.fh.exampleorder.model.user.User;
import br.com.fh.exampleorder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@RequestMapping("user-controller")
public class UserController {

    @Autowired
    public UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        String id = request.getParameter("id");
        User user = null;

        if (id != null && !id.isEmpty()){
            user = userService.load(Integer.valueOf(id));

            if (user == null){
                ModelAndView mav = new ModelAndView("not-found");
                return mav;
            }
        } else {
            user = new User();
        }

        ModelAndView mav = new ModelAndView("register-user");
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RedirectView save(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("user") User user) {

        user = userService.register(user);

        RedirectView rv = new RedirectView();
        rv.setUrl("/user-controller?id="+user.getId());
        rv.setContextRelative(true);
        return rv;
    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RedirectView deleteOrder(HttpServletRequest request, HttpServletResponse response,
                                    @PathVariable("id") Integer id) {

        userService.delete(id);

        RedirectView rv = new RedirectView();
        rv.setUrl("/user-list-controller");
        rv.setContextRelative(true);
        return rv;
    }
}

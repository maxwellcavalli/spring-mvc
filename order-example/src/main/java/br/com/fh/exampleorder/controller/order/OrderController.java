package br.com.fh.exampleorder.controller.order;

import br.com.fh.exampleorder.data.OrderItemData;
import br.com.fh.exampleorder.model.customer.Customer;
import br.com.fh.exampleorder.model.order.Order;
import br.com.fh.exampleorder.model.order.OrderIte;
import br.com.fh.exampleorder.model.product.Product;
import br.com.fh.exampleorder.service.CustomerService;
import br.com.fh.exampleorder.service.OrderService;
import br.com.fh.exampleorder.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


@Controller
@RequestMapping("order-controller")
public class OrderController {

    @Autowired
    public CustomerService customerService;

    @Autowired
    public ProductService productService;

    @Autowired
    public OrderService orderService;

    @InitBinder
    public void bindingPreparation(WebDataBinder binder) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        CustomDateEditor orderDateEditor = new CustomDateEditor(dateFormat, true);
        binder.registerCustomEditor(Date.class, orderDateEditor);
    }


    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        String id = request.getParameter("id");

        Order order = null;
        if (id != null && !id.isEmpty()){
            order = orderService.load(Integer.valueOf(id));

            if (order == null){
                ModelAndView mav = new ModelAndView("not-found");
                return mav;
            }

            List<OrderIte> items = order.getItens();

            List<OrderItemData> itensData = new ArrayList<>();

            items.stream().forEach(ite -> {
                OrderItemData orderItemData = new OrderItemData();
                orderItemData.setProductName(ite.getProduct().getName());
                orderItemData.setProductId(ite.getProduct().getId());
                orderItemData.setQuantidade(ite.getQuantity());
                orderItemData.setInternalId(ite.getId());

                itensData.add(orderItemData);
            });


            session.setAttribute("order-itens", itensData);
        } else {
            order = new Order();
            session.removeAttribute("order-itens");
        }

        ModelAndView mav = new ModelAndView("register-order");
        mav.addObject("order", order);
        prepareModel(mav);
        return mav;
    }

    private void prepareModel(ModelAndView mav) {
        List<Customer> customers = customerService.findAll();
        List<Product> products = productService.findAll();

        mav.addObject("customers", customers);
        mav.addObject("products", products);

    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    public RedirectView deleteOrder(HttpServletRequest request, HttpServletResponse response,
                             @PathVariable("id") Integer id) {

        orderService.delete(id);

        RedirectView rv = new RedirectView();
        rv.setUrl("/order-controller?id="+id);
        rv.setContextRelative(true);
        return rv;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public RedirectView save(HttpServletRequest request, HttpServletResponse response,
                             @ModelAttribute("order") Order order) {
        HttpSession session = request.getSession();
        List<OrderItemData> itens = (List<OrderItemData>) session.getAttribute("order-itens");
        if (itens == null) {
            itens = new ArrayList<>();
        }

        final List<OrderIte> orderItes = new ArrayList<>();
        itens.stream()
                .forEach(ite->{
                    OrderIte orderIte = new OrderIte();
                    orderIte.setOrder(order);
                    orderIte.setQuantity(ite.getQuantidade());

                    Product product = new Product();
                    product.setId(ite.getProductId());

                    orderIte.setProduct(product);
                    orderItes.add(orderIte);
                });


        orderService.register(order, orderItes);

        session.removeAttribute("order-itens");

        RedirectView rv = new RedirectView();
        rv.setUrl("/order-controller?id="+order.getId());
        rv.setContextRelative(true);
        return rv;
    }

    @RequestMapping(value = "/load-products", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderItemData>> loadProducts(HttpServletRequest request,
                                                          @RequestBody OrderItemData item) {
        HttpSession session = request.getSession();
        List<OrderItemData> itens = (List<OrderItemData>) session.getAttribute("order-itens");
        if (itens == null) {
            itens = new ArrayList<>();
        }

        return new ResponseEntity<>(itens, HttpStatus.OK);
    }

    @RequestMapping(value = "/remove-product", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderItemData>> removeProduct(HttpServletRequest request,
                                                            @RequestBody OrderItemData item) {
        HttpSession session = request.getSession();
        List<OrderItemData> itens = (List<OrderItemData>) session.getAttribute("order-itens");
        if (itens == null) {
            itens = new ArrayList<>();
        }

        itens = itens.stream()
                .filter(ite -> ite.getInternalId() != item.getInternalId())
                .collect(Collectors.toList());

        session.setAttribute("order-itens", itens);
        return new ResponseEntity<>(itens, HttpStatus.OK);
    }

    @RequestMapping(value = "/add-product", method = RequestMethod.POST,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderItemData>> addProduct(HttpServletRequest request,
                                                    @RequestBody OrderItemData item) {

        if (item.getQuantidade() <= 0){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        HttpSession session = request.getSession();
        List<OrderItemData> itens = (List<OrderItemData>) session.getAttribute("order-itens");
        if (itens == null) {
            itens = new ArrayList<>();
        }

        OrderItemData orderItemData = itens.stream()
                .filter(ite -> ite.getInternalId() == item.getInternalId())
                .findFirst().orElse(null);


        if (orderItemData == null) {
            orderItemData = new OrderItemData();

            int internalId = itens.stream().mapToInt(ite -> ite.getInternalId()).max().orElse(0);
            orderItemData.setInternalId(++internalId);
        }

        int index = itens.indexOf(orderItemData);

        orderItemData.setProductId(item.getProductId());
        orderItemData.setQuantidade(item.getQuantidade());
        orderItemData.setProductName(item.getProductName());

        if (index == -1) {
            itens.add(orderItemData);
        } else {
            itens.set(index, orderItemData);
        }

        final Map<Integer, String> mapProducts = new HashMap<>();
        itens.stream().forEach(ite -> {
            mapProducts.put(ite.getProductId(), ite.getProductName());
        });

        final List<OrderItemData> merged = new ArrayList<>();
        final AtomicInteger countId = new AtomicInteger();
        //merge itens
        itens.stream()
                .collect(Collectors.groupingBy(ite -> ite.getProductId(), Collectors.summingInt(ite -> ite.getQuantidade())))
                .forEach((prod, count) -> {
                    OrderItemData newItem = new OrderItemData();
                    newItem.setInternalId(countId.getAndIncrement());
                    newItem.setProductId(prod);
                    newItem.setQuantidade(count);
                    newItem.setProductName(mapProducts.get(prod));

                    merged.add(newItem);
                });

        session.setAttribute("order-itens", merged);
        return new ResponseEntity<>(merged, HttpStatus.OK);
    }
}

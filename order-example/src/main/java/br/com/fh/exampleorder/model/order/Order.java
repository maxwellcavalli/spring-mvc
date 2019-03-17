package br.com.fh.exampleorder.model.order;

import br.com.fh.exampleorder.model.customer.Customer;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class Order {

    private int id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private Customer customer;
    private int situation;

    private List<OrderIte> itens;

    public Order() {
        this.customer = new Customer();
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getSituation() {
        return situation;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    public List<OrderIte> getItens() {
        return itens;
    }

    public void setItens(List<OrderIte> itens) {
        this.itens = itens;
    }
}

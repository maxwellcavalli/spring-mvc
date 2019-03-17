package br.com.fh.exampleorder.service.impl;

import br.com.fh.exampleorder.dao.CustomerDao;
import br.com.fh.exampleorder.model.customer.Customer;
import br.com.fh.exampleorder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultCustomerService implements CustomerService {


    @Autowired
    private CustomerDao dao;

    public List<Customer> findAll() {
        return dao.findAll();
    }

    public void setDao(CustomerDao dao) {
        this.dao = dao;
    }
}

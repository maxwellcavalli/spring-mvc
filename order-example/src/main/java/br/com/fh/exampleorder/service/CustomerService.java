package br.com.fh.exampleorder.service;

import br.com.fh.exampleorder.model.customer.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAll();

}

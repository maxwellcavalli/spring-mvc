package br.com.fh.exampleorder.dao;

import br.com.fh.exampleorder.model.customer.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll();
}

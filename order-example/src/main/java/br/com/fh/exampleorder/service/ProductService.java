package br.com.fh.exampleorder.service;

import br.com.fh.exampleorder.model.product.Product;

import java.util.List;

public interface ProductService {

    List<Product> findAll();

}

package br.com.fh.exampleorder.dao;

import br.com.fh.exampleorder.model.product.Product;

import java.util.List;

public interface ProductDao {
    List<Product> findAll();
}

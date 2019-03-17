package br.com.fh.exampleorder.service.impl;

import br.com.fh.exampleorder.dao.ProductDao;
import br.com.fh.exampleorder.model.product.Product;
import br.com.fh.exampleorder.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class DefaultProductService implements ProductService {

    @Autowired
    private ProductDao dao;

    public List<Product> findAll() {
        return dao.findAll();
    }

    public void setDao(ProductDao dao) {
        this.dao = dao;
    }
}

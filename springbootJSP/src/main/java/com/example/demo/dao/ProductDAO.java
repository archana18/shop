package com.example.demo.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.demo.model.ProductBo;


@Repository
public interface ProductDAO {

	public List<ProductBo> getProduct();

	public List<ProductBo> getProductByCategory(String category);
}

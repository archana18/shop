package com.example.demo.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.model.ProductBo;

@Repository("productDAO")
public class ProductDAOImpl {

	
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_RESET = "\u001B[0m";
	@Autowired
	private JdbcTemplate jt;
	private static final Logger logger = LoggerFactory.getLogger(ProductDAOImpl.class);
	private static final String GET_PRODUCT_BY_CATEGORY = "SELECT * FROM products WHERE category=?";
	private static final String GET_ALL_PRODUCT_DETAILS = "SELECT * from products";
	private static final String GET_DETAILS = "SELECT * from products where productId=?";

	public List<ProductBo> getProductByCategory(String category) {
		return jt.query(GET_PRODUCT_BY_CATEGORY, new Object[] { category }, new RowMapper<ProductBo>() {
			public ProductBo mapRow(ResultSet rs, int row) throws SQLException {
				logger.info("Dao implementation entered:getProductByCategory");
				ProductBo e = new ProductBo();
				e.setProductId(rs.getInt(1));
				e.setProductDesc(rs.getString(2));
				e.setCategory(rs.getString(3));
				e.setPrice(rs.getString(4));
				e.setQuantity(rs.getInt(5));
				e.setImage(rs.getString(6));
				System.out.println(e);
				return e;
			}
		});
	}

	public List<ProductBo> getProduct() {
		
		logger.info("Dao implementation entered:getProduct");
		return jt.query(GET_ALL_PRODUCT_DETAILS, new RowMapper<ProductBo>() {
			public ProductBo mapRow(ResultSet rs, int row) throws SQLException {
				ProductBo e = new ProductBo();
				
				e.setProductId(rs.getInt(1)); 
				e.setProductDesc(rs.getString(2));
				e.setCategory(rs.getString(3));
				e.setPrice(rs.getString(4));
				e.setQuantity(rs.getInt(5));
				e.setImage(rs.getString(6));
				System.out.println(ANSI_GREEN + e + ANSI_RESET);
				return e;
			}
		});
	}

	public ProductBo getProductInCart(String string) {
		String sql = GET_DETAILS;
		logger.info("Dao implementation entered:getProductInCart");
		return jt.queryForObject(sql, new Object[] { string }, new BeanPropertyRowMapper<ProductBo>(ProductBo.class));
	}

	public ProductBo getProductIntoCart(String string) {
		String sql = GET_DETAILS;
		logger.info("Dao implementation entered:getProductIntoCart");
		return jt.queryForObject(sql, new Object[] { string }, new BeanPropertyRowMapper<ProductBo>(ProductBo.class));
	}
}
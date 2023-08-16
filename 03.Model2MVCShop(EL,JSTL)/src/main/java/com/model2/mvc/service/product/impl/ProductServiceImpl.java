package com.model2.mvc.service.product.impl;

import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;

public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;
	private Product productVO;
	
	public ProductServiceImpl() {
		productDAO =  new ProductDAO();
	} 
	
	public Map<String,Object> getProductList(Search searchVO) throws Exception{
		return productDAO.getProductList(searchVO);
		
	}
	
	public Product updateProduct(Product productVO) throws Exception{
	    return productDAO.updateProduct(productVO);
	}

	
	public Product addProduct(Product productVO)throws Exception{
		return productDAO.insertProduct(productVO);
	}
	
	public Product getProduct(int prodNo) throws Exception{
		System.out.println("impgetpro");
		return productDAO.findProduct(prodNo);
	}
}
/*
 * 	impl ��  interface�� ���� ���¸� �����ؾ��� ==> return type, �Ķ���͸� ��ġ���Ѿ� ��. 
 * 	int�� price�� no �� �� �޴� ���� �ƴ� no�� �޴´�
*/

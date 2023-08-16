package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class GetProductAction extends Action{
	
	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		
		
		
		String getNo=request.getParameter("prodNo");
		
		String menu = request.getParameter("menu");
		System.out.println("getProdAct access : "+menu);
		
		ProductService pService=new ProductServiceImpl();
		Product product=pService.getProduct(Integer.parseInt(getNo));
		
		request.setAttribute("product", product);
		
		System.out.println("getProdAct : "+product);
		
	
		if(menu != null && menu.equals("manage")){
			return "forward:/updateProductView.do?prodNo="+getNo+"&menumanage";
		}else {
			return "forward:/product/getProduct.jsp";
		}
	}
}

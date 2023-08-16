package com.model2.mvc.view.product;

import java.util.Map;

import javax.management.relation.Role;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.User;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;

public class ListProductAction extends Action {
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
Search search=new Search();
		
		int currentPage=1;

		if(request.getParameter("currentPage") != null){
			currentPage=Integer.parseInt(request.getParameter("currentPage"));
		}
		
		search.setCurrentPage(currentPage);
		search.setSearchCondition(request.getParameter("searchCondition"));
		search.setSearchKeyword(request.getParameter("searchKeyword"));
		
		System.out.println("actionSearchCon : "+search);
		//System.out.println(search.setSearchKeyword(request.getParameter("searchKeyword")));
		
		// web.xml  meta-data 로 부터 상수 추출 
		int pageSize = Integer.parseInt( getServletContext().getInitParameter("pageSize"));
		int pageUnit  =  Integer.parseInt(getServletContext().getInitParameter("pageUnit"));
		search.setPageSize(pageSize);
		
		System.out.println("listProdServlet : "+pageSize );
		
		// Business logic 수행
		ProductService productService=new ProductServiceImpl();
		Map<String , Object> map=productService.getProductList(search);
		
		System.out.println(map);
		
		Page resultPage	= 
					new Page( currentPage, ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("ListProdAction ::"+resultPage);
		
		// Model 과 View 연결
		request.setAttribute("list", map.get("list"));
		request.setAttribute("resultPage", resultPage);
		request.setAttribute("search", search);
		
	//====> jsp manage, search 분리 
		
		HttpSession session =request.getSession();
		User vo= (User)session.getAttribute("user");  
		System.out.println(":::::::::::::::::::::::::로그인 ID : "+vo.getUserId()+":::::::::::::::::::::::::ROLE : "+vo.getRole()+"::::::::::::::::::::::::::::::::::::::");
		

		return "forward:/product/listProduct.jsp";
	}
}

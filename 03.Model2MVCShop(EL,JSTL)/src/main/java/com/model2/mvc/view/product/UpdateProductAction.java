package com.model2.mvc.view.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;

public class UpdateProductAction extends Action {

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int prodNo = Integer.parseInt(request.getParameter("prodNo")); // prodNo를 int로 변환

        Product product = new Product();
        product.setProdNo(prodNo); // 변환한 prodNo 설정
        product.setProdName(request.getParameter("prodName"));
        product.setProdDetail(request.getParameter("prodDetail"));
        product.setManuDate(request.getParameter("manuDate").replace("-", ""));
        product.setPrice(Integer.parseInt(request.getParameter("price")));

        ProductService pService = new ProductServiceImpl();
        pService.updateProduct(product);

        System.out.println("updateProdAct : " + product.toString());

        // 세션 처리
        HttpSession session = request.getSession();
        Product sessionProduct = (Product) session.getAttribute("product");
        if (sessionProduct != null && sessionProduct.getProdNo() == prodNo) {
            session.setAttribute("product", product);
        }

        return "redirect:/getProduct.do?prodNo="+prodNo+"&menu=ok";
    }
}

package com.model2.mvc.service.product.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.Search;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.domain.Product;


public class ProductDAO {

	public ProductDAO() { // multiple
		// TODO Auto-generated constructor stub
	}

	public Product insertProduct(Product productVO) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "INSERT INTO product(prod_no, prod_name, prod_detail, manufacture_day, price, reg_date) VALUES (seq_product_prod_no.NEXTVAL,?,?,?,?,SYSDATE)";

		PreparedStatement stmt = con.prepareStatement(sql);
		// stmt.setInt(1, productVO.getProdNo());
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		// stmt.setString(6, productVO.getRegDate()); //Date�� varchar�� �޴µ� String�� ��������
		// ����? ==> sysdate�� �ٷ� ����
		stmt.executeUpdate();

		con.close();

		return productVO;
	}

	public Product findProduct(int prodNo) throws Exception {

		Connection con = DBUtil.getConnection();

		String sql = "SELECT * FROM product WHERE prod_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);

		ResultSet rs = stmt.executeQuery();
//		System.out.println("dao");
		Product productVO = null;
		while (rs.next()) {
			productVO = new Product();
			productVO.setProdNo(rs.getInt("prod_no"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("manufacture_day"));
			productVO.setPrice(rs.getInt("price"));
			productVO.setRegDate(rs.getDate("reg_date")); // regdat ���� �� �������� ���� ===>String �̳� int�� �ƴ� getDate �� �޴´�.
		}

		con.close();

		return productVO;
	}

//======>�Ʒ� �����ϰ� ���� 
	public Map<String, Object> getProductList(Search search) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		Connection con = DBUtil.getConnection();

		System.out.println(search);
		
		// Original Query ����
		String sql = "SELECT prod_no ,  prod_name , price, reg_date  FROM  product ";

		if (search.getSearchCondition() != null) {
			if (search.getSearchCondition().equals("0") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_no = '" + search.getSearchKeyword() + "'";
			} else if (search.getSearchCondition().equals("1") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE prod_name ='" + search.getSearchKeyword() + "'";
			} else if (search.getSearchCondition().equals("2") && !search.getSearchKeyword().equals("")) {
				sql += " WHERE price ='" + search.getSearchKeyword() + "'";
			} 
		}
		sql += " ORDER BY prod_no";

		System.out.println("ProductDAO::Original SQL :: " + sql);

		// ==> TotalCount GET
		int totalCount = this.getTotalCount(sql);
		System.out.println("ProductDAO :: totalCount  :: " + totalCount);

		// ==> CurrentPage �Խù��� �޵��� Query �ٽñ���
		sql = makeCurrentPageSql(sql, search);
		PreparedStatement pStmt = con.prepareStatement(sql);
		ResultSet rs = pStmt.executeQuery();

	

		List<Product> list = new ArrayList<Product>();

		while (rs.next()) {
			
			Product product = new Product();
			
			product.setProdNo(rs.getInt("prod_no"));
			product.setProdName(rs.getString("prod_name"));
			//product.setProdDetail(rs.getString("prod_detail"));
			//product.setManuDate(rs.getString("manufacture_day"));
			product.setPrice(rs.getInt("price"));
			product.setRegDate(rs.getDate("reg_date"));

			list.add(product);
		}

		// ==> totalCount ���� ����
		map.put("totalCount", new Integer(totalCount));
		// ==> currentPage �� �Խù� ���� ���� List ����
		map.put("list", list);

		rs.close();
		pStmt.close();
		con.close();

		return map;
	}

	public Product updateProduct(Product productVO) throws Exception {

		Connection con = DBUtil.getConnection();
		System.out.println("productVO : "+productVO.toString());
		String sql = "UPDATE product SET prod_name=?, prod_detail=?, manufacture_day=?, price=? WHERE prod_no=?";

		PreparedStatement stmt = con.prepareStatement(sql);

		
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setInt(5,productVO.getProdNo());
		stmt.executeUpdate();
		con.close();
		
		
		System.out.println("dao.Update : "+productVO.getPrice());
		return productVO;
	}
	
	// �Խ��� Page ó���� ���� ��ü Row(totalCount)  return
		private int getTotalCount(String sql) throws Exception {
			
			sql = "SELECT COUNT(*) "+
			          "FROM ( " +sql+ ") countTable";
			
			Connection con = DBUtil.getConnection();
			PreparedStatement pStmt = con.prepareStatement(sql);
			ResultSet rs = pStmt.executeQuery();
			
			int totalCount = 0;
			if( rs.next() ){
				totalCount = rs.getInt(1);
			}
			
			pStmt.close();
			con.close();
			rs.close();
			
			return totalCount;
		}
		
		// �Խ��� currentPage Row ��  return 
		private String makeCurrentPageSql(String sql , Search search){
			sql = 	"SELECT * "+ 
						"FROM (		SELECT inner_table. * ,  ROWNUM AS row_seq " +
										" 	FROM (	"+sql+" ) inner_table "+
										"	WHERE ROWNUM <="+search.getCurrentPage()*search.getPageSize()+" ) " +
						"WHERE row_seq BETWEEN "+((search.getCurrentPage()-1)*search.getPageSize()+1) +" AND "+search.getCurrentPage()*search.getPageSize();
			
			System.out.println("ProdDAO :: make SQL :: "+ sql);	
			
			return sql;
		}
}

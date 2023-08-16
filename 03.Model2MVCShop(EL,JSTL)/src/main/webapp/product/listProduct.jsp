<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ page language="java" contentType="text/html; charset=euc-kr"
	pageEncoding="euc-kr"%>


<%
String menu = request.getParameter("menu");
System.out.println("jsp access : " + menu);
%>


<c:set var="menu" value="${param.menu}" />

<html>
<head>
<title></title>

<link rel="stylesheet" href="/css/admin.css" type="text/css">

<script type="text/javascript">
<!--
	//�˻� / page �ΰ��� ��� ��� Form ������ ���� JavaScrpt �̿�  
	function fncGetProductList(currentPage) {
		document.getElementById("currentPage").value = currentPage;
		document.detailForm.submit();
	}
	-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000">

	<div style="width: 98%; margin-left: 10px;">

		<form name="detailForm" action="/listProduct.do?menu=search" method="post">

			<table width="100%" height="37" border="0" cellpadding="0" cellspacing="0">
				<tr>
					<td width="15" height="37">
					<img src="/images/ct_ttl_img01.gif" width="15" height="37" /></td>
					<td background="/images/ct_ttl_img02.gif" width="100%"	style="padding-left: 10px;">
						<table width="100%" border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="93%" class="ct_ttl01">
								
								
								
								<c:choose>
										<c:when test="${menu eq 'manage'}">
									        ��ǰ ����
									    </c:when>
										<c:otherwise>
									        ��ǰ �����ȸ
									    </c:otherwise>
									</c:choose></td>
							</tr>
						</table>
					</td>
					<td width="12" height="37"><img src="/images/ct_ttl_img03.gif"
						width="12" height="37" /></td>
				</tr>
			</table>


			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>

					<td align="right">
					<select name="searchCondition"	class="ct_input_g" style="width: 80px">
							<option value="0"  ${ ! empty search.searchCondition && search.searchCondition==0 ? "selected" : "" }>��ǰ��ȣ</option>
							<option value="1"  ${ ! empty search.searchCondition && search.searchCondition==1 ? "selected" : "" }>��ǰ��</option>
							<option value="2"  ${ ! empty search.searchCondition && search.searchCondition==2 ? "selected" : "" }>��ǰ����</option>
					</select> 
					<input type="text" name="searchKeyword" value="${! empty search.searchKeyword ? search.searchKeyword : "" }"
						class="ct_input_g" style="width: 200px; height: 19px" /></td>

					<td align="right" width="70">
						<table border="0" cellspacing="0" cellpadding="0">
							<tr>
								<td width="17" height="23"><img
									src="/images/ct_btnbg01.gif" width="17" height="23" /></td>
								<td background="/images/ct_btnbg02.gif" class="ct_btn01"
									style="padding-top: 3px;"><a
									href="javascript:fncGetProductList('1');">�˻�</a></td>
								<td width="14" height="23"><img
									src="/images/ct_btnbg03.gif" width="14" height="23" /></td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				style="margin-top: 10px;">
				<tr>
					<td colspan="11">��ü ${resultPage.totalCount} �Ǽ�, ���� ${resultPage.currentPage} ������
					</td>
				</tr>
				<tr>
					<td class="ct_list_b" width="100">No</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">��ǰ��</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b" width="150">����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">�����</td>
					<td class="ct_line02"></td>
					<td class="ct_list_b">�������</td>
				</tr>
				<tr>
					<td colspan="11" bgcolor="808285" height="1"></td>
				</tr>
				
				
				<c:set var="i" value = "0"/>
				<c:forEach var = "product" items = "${list}">
				
					<c:set var = "i" value = "${i+1}" />
				<tr class="ct_list_pop">
					<td align="center">${i}</td>
					<td></td>


					<td align="left">
					<c:choose>
						<c:when test="${menu eq 'manage'}"> 
							<a href="/getProduct.do?prodNo=${product.prodNo}&menu=manage">${product.prodName }</a>
						</c:when>
						<c:otherwise>
							<a href="/getProduct.do?prodNo=${product.prodNo}&menu=search">${product.prodName }</a>
						</c:otherwise>
					</c:choose>
					<td></td>
					<td align="left">${product.price }</td>
					<td></td>
					<td align="left">${product.regDate }</td>
					<td></td>
					<td align="left">${product.proTranCode } <%-- code�� �޾� if�� �з��ؾ� ��  --%>

					</td>
				</tr>
				
				<tr>
					<td colspan="11" bgcolor="D6D7D6" height="1"></td>
				</tr>
				</c:forEach>
			</table>

			<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top: 10px;">
				<tr>
					<td align="center">
					<input type="hidden" id="currentPage" name="currentPage" value="" /> 
						<c:set var="type" value="Product" />
						<jsp:include page = "../common/pageNavigator.jsp">
							<jsp:param value="${type}" name="type" />
						</jsp:include>
						</td>

				</tr>
			</table>
			<!--  ������ Navigator �� -->

		</form>

	</div>
</body>
</html>
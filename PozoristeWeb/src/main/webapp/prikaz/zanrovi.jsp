<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="s" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
		<form action="/Library/controller/getPredstavePoZanru" method="get">
			<select name="zanrID">
				<c:forEach items="${zanrovi }" var="z">
					<option value="${z.idZanr }">${z.naziv}</option>
				</c:forEach>
			</select>
			
			<button type="submit"> Zapocni pretragu po zanru</button>
		</form>
		
		<c:if test="${ !empty listaZanrPredstava}">
			<table border="3">
			<tr> NAZIVI PREDSTAVA SA IZABRANIM ZANROM
			<c:forEach items="${listaZanrPredstava}" var="zp">
			<tr>
			<td>${zp.predstava.naziv}</td>
			</tr>
			</c:forEach>
			</table>
		</c:if>
</body>
</html>
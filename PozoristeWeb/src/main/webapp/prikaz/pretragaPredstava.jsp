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
	<form action="/Library/controller/predstavePoZanru">
		Unesite naziv zanra ovde :<input  name="zanr" type="text"> 
		<button type="submit"></button>
	</form>
	
	<c:if test="${!empty predstave }">
	<select>
		<c:forEach items="${predstave }" var="p">
			<option value="${p.idPredstava}"> ${p.naziv}</option>
			<p>${p.opis }</p>
		</c:forEach>
	</select>
	</c:if>
</body>
</html>
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
	<form action="/Library/controller/getZanrZaPredstavu" method="get">
	<c:if test="${!empty predstave }">
		<select name="idPredstave">
		<c:forEach items="${predstave}" var="p">
			<option value="${p.idPredstava}"> ${p.naziv} ---- ${p.trajanje }</option>  
		</c:forEach>
		</select>
	</c:if>
	<button type="submit">Pretraga</button>
	</form>
	
	
	<c:if test="${!empty zanr }">
		Zanr predstave ${p.naziv} je -------- ${zanr.zanr.naziv}
	</c:if>
</body>
</html>
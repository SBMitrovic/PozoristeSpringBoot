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
	<form action="/Library/controller/getIzvodjenjaZaPosjetioca"
		method="get">
		<select name="idPosjetioca">
			<c:forEach items="${posjetioci}" var="pos">
				<option value="${pos.idPosetilac}">${pos.ime}
					${pos.prezime}</option>
			</c:forEach>
		</select> <input type="submit" value="Prikaz">
	</form>


	<c:if test="${! empty listaOcjena }">
		<table>
		

				<tr>
					<th>Ocjena</th>
					<th>Datum izvodjenja</th>
					<th>Naziv Predstave</th>
				</tr>
				<c:forEach items="${listaOcjena }" var="o">
				<tr>
					<td>${o.ocena}</td>
					<td>${o.getIzvodjenje().getDatum()}</td>
					<td>${o.getIzvodjenje().getPredstava().getNaziv()}</td>

				</tr>
	
				


			</c:forEach>
			<tr>
				<td> <a href="">ULOGE</a></td>
			</tr>
		</table>




	</c:if>


</body>
</html>
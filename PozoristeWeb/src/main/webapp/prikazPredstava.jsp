<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Prikaz predstava</title>
</head>
<body>
	<form action="/Pozoriste/predstave/getPredstave" method="get">
		Režiser: <select name="idReziser">
			<c:forEach items="${reziseri}" var="r">
				<option value="${r.idReziser}">${r.ime} ${r.prezime}</option>
			</c:forEach>
		</select> 
		<input type="submit" value="Prikaži" />
	</form><br>
	<c:if test="${!empty predstave}">
Odabrani režiser: ${reziser.ime} ${reziser.prezime} 

		<table border="1">
			<tr>
				<th>Naziv</th>
				<th>Trajanje</th>
				<th>Izveštaj</th>
			</tr>
			<c:forEach items="${predstave }" var="p">
				<tr>
					<td>${p.naziv }</td>
					<td>${p.trajanje }</td>
					<td><a href="/Pozoriste/predstave/getIzvestaj?idP=${p.idPredstava}">Generiši izveštaj</a></td>
				</tr>
			</c:forEach>

		</table>
	</c:if>
</body>
</html>
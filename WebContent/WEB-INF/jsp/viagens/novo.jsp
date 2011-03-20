<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<form action="<c:url value="/viagem" />" method="post">
			<select name="viagem.pessoa.id">
				<c:forEach items="${pessoas}" var="p">
					<option value="${p.id}">${p.nome}</option>
				</c:forEach>
			</select>
			viajou para <select name="viagem.lugar.id">
				<c:forEach items="${lugares}" var="l">
					<option value="${l.id}">${l.cidade} - ${l.pais}</option>
				</c:forEach>
			</select>
			em: <input type="text" name="viagem.data"> (mm/dd/yyyy)
			<input type="submit" value="Gravar viagem">
		</form>
	</body>
</html>
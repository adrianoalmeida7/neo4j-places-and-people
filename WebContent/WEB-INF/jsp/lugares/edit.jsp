<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="<c:url value="/lugar" />">
	<input name="lugar.id" value="${lugar.id}">
	<input name="lugar.cidade" value="${lugar.cidade}">
	<input name="lugar.pais" value="${lugar.pais}">
	<button type="submit" value="put" name="_method">Alterar</button> 
</form>
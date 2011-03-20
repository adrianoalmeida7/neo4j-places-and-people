<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="<c:url value="/pessoa" />">
	<input name="pessoa.id" value="${pessoa.id}">
	<input name="pessoa.nome" value="${pessoa.nome}">
	<button type="submit" value="put" name="_method">Alterar</button> 
</form>
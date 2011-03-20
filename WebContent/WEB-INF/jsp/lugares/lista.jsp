<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<a href="<c:url value="/lugar/new" />">Cadastrar</a>
<hr />

<c:forEach items="${lugarList}" var="l">
	${l.id} - ${l.cidade} - ${l.pais} - 
	<form action="<c:url value="/lugar/${l.id}" />" method="post">
		<button type="submit" name="_method" value="DELETE">Remover</button>
	</form> - 
	<a href="<c:url value="/lugar/edit/${l.id}" />">Alterar</a> -
	<a href="<c:url value="/lugar/${l.id}" />">Ver</a> -
	
	<br />
</c:forEach>
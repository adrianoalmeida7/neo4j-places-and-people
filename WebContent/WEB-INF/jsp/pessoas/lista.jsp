<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<a href="<c:url value="/pessoa/new" />">Cadastrar</a>
<hr />

<c:forEach items="${pessoaList}" var="p">
	${p.id} - ${p.nome} - 
	<form action="<c:url value="/pessoa/${p.id}" />" method="post">
		<button type="submit" name="_method" value="DELETE">Remover</button>
	</form> - 
	<a href="<c:url value="/pessoa/edit/${p.id}" />">Alterar</a> -
	<a href="<c:url value="/pessoa/${p.id}" />">Ver</a>
	<br />
</c:forEach>
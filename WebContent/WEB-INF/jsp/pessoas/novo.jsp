<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="<c:url value="/pessoa" />">
	<input name="pessoa.nome">
	<input type="submit"> 
</form>
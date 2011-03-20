<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="<c:url value="/lugar" />">
	Cidade:<input name="lugar.cidade"><br />
	Pais: <input name="lugar.pais"><br />
	<input type="submit"> 
</form>
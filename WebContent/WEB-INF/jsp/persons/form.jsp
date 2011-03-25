<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="<c:url value="/person" />">
	<input name="person.name">
	<input type="submit"> 
</form>
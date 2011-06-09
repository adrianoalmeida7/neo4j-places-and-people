<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="<c:url value="/person" />">
	<input name="person.id" value="${person.id}" type="hidden">
	<input name="person.name" value="${person.name}">
	<button type="submit" value="put" name="_method">Update informations</button> 
</form>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="<c:url value="/place" />">
	<input name="place.id" value="${place.id}" type="hidden">
	<input name="city" value="${place.city}">
	<input name="place.country" value="${place.country}">
	<button type="submit" value="put" name="_method">Update information</button> 
</form>
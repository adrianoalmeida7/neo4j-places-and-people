<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<form method="POST" action="<c:url value="/place" />">
	City:<input name="place.city"><br />
	Country: <input name="place.country"><br />
	<input type="submit"> 
</form>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<a href="<c:url value="/place/new" />">Add new place</a>
<hr />

<c:forEach items="${placeList}" var="p">
	${p.id} - ${p.city} - ${p.country} - 
	<form action="<c:url value="/place/${p.id}" />" method="post">
		<button type="submit" name="_method" value="DELETE">Remove</button>
	</form> - 
	<a href="<c:url value="/place/edit/${p.id}" />">Edit</a> -
	<a href="<c:url value="/place/${p.id}" />">See</a> -
	
	<br />
</c:forEach>
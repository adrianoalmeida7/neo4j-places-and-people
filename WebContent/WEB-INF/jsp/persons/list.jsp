<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<a href="<c:url value="/person/new" />">Add new person</a> -
<a href="<c:url value="/person/filterByNameForm" />">Filter for someone</a> - 
<a href="<c:url value="/persons" />">List all persons</a> 
<hr />

<c:forEach items="${personList}" var="p">
	${p.id} - ${p.name} - 
	<form action="<c:url value="/person/${p.id}" />" method="post">
		<button type="submit" name="_method" value="DELETE">Remove</button>
	</form> - 
	<a href="<c:url value="/person/edit/${p.id}" />">Edit</a> -
	<a href="<c:url value="/person/${p.id}" />">See</a>
	<br />
</c:forEach>
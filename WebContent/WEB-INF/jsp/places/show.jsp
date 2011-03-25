<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

Data about ${place.city} - ${place.country} <br />

Travels done:
<ul>
<c:forEach items="${travels}" var="t">
	<li>${t.person.name} at <fmt:formatDate value="${t.date.time}" pattern="MM/dd/yyyy"/> </li>
</c:forEach>
</ul>

<hr />
Who went to ${place.city} also traveled to:
<ul>
<c:forEach items="${placesAlsoVisited}" var="p">
	<li>${p.city} - ${p.country}</li>
</c:forEach>
</ul>
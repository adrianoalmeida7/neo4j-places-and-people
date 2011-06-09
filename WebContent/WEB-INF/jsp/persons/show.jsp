<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<a href="<c:url value="/" />">Home</a><hr />

Data about ${person.name} <br />

Travels done:
<ul>
<c:forEach items="${travels}" var="t">
	<li>${t.place.city} - ${t.place.country} at <fmt:formatDate value="${t.date.time}" pattern="MM/dd/yyyy"/> </li>
</c:forEach>
</ul>

<hr />

These people traveled to the same places as ${person.name}:
<ul>
<c:forEach items="${whoTraveledToSamePlaces}" var="p">
	<li><a href="<c:url value="/person/${p.id}" />">${p.name}</a></li>
</c:forEach>
</ul>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<a href="<c:url value="/" />">Home</a><hr />

Data about ${place.city} - ${place.country} <br />

Travels done:
<ul>
<c:forEach items="${travels}" var="t">
	<li>${t.person.name} at <fmt:formatDate value="${t.date.time}" pattern="MM/dd/yyyy"/> </li>
</c:forEach>
</ul>
<hr />

Who lived at ${place.city}:
<ul>
<c:forEach items="${livedHistory}" var="l">
	<li>${l.person.name} from <fmt:formatDate value="${l.startingAt.time}" pattern="MM/dd/yyyy"/> 
	until <fmt:formatDate value="${l.until.time}" pattern="MM/dd/yyyy"/> </li>
</c:forEach>
</ul>

<hr />
Who traveled to ${place.city} also traveled to:
<ul>
<c:forEach items="${placesAlsoTraveled}" var="p">
	<li>${p.city} - ${p.country}</li>
</c:forEach>
</ul>

<hr />

Who went to ${place.city} also passed through these places:
<ul>
<c:forEach items="${alsoPassedThroughAs}" var="p">
	<li>${p.city} - ${p.country}</li>
</c:forEach>
</ul>
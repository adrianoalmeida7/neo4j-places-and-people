<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
	<body>
		<ul>
			<li><a href="<c:url value="/places" />">Places</a></li>
			<li><a href="<c:url value="/persons" />">Persons</a></li>
			<li><a href="<c:url value="/travel/new" />">New travel</a></li>
			<li><a href="<c:url value="/livedAt/new" />">New Lived at</a></li>
		</ul>
	</body>
</html>
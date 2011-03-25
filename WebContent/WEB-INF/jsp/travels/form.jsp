<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<form action="<c:url value="/travel" />" method="post">
			<select name="travel.person.id">
				<c:forEach items="${persons}" var="p">
					<option value="${p.id}">${p.name}</option>
				</c:forEach>
			</select>
			traveled to <select name="travel.place.id">
				<c:forEach items="${places}" var="p">
					<option value="${p.id}">${p.city} - ${p.country}</option>
				</c:forEach>
			</select>
			at: <input type="text" name="travel.date"> (mm/dd/yyyy)
			<input type="submit" value="Add travel">
		</form>
	</body>
</html>
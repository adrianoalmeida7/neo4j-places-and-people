<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
	<body>
		<form action="<c:url value="/livedAt" />" method="post">
			<select name="lived.person.id">
				<c:forEach items="${persons}" var="p">
					<option value="${p.id}">${p.name}</option>
				</c:forEach>
			</select>
			lived at <select name="lived.place.id">
				<c:forEach items="${places}" var="p">
					<option value="${p.id}">${p.city} - ${p.country}</option>
				</c:forEach>
			</select>
			from: <input type="text" name="lived.startingAt"> (mm/dd/yyyy)
			until: <input type="text" name="lived.until"> (mm/dd/yyyy)
			<input type="submit" value="Add living history">
		</form>
	</body>
</html>
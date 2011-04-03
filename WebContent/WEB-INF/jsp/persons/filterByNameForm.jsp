<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

To use this feature, you should know <a href="http://lucene.apache.org/java/3_0_1/queryparsersyntax.html">Lucene's query syntax</a><hr />

<form method="GET" action="<c:url value="/person/byName" />">
	<input name="name">
	<input type="submit"> 
</form>
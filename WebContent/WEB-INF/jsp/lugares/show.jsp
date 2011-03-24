<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

Dados de ${lugar.cidade} - ${lugar.pais} <br />

Viagens feitas:
<ul>
<c:forEach items="${viagens}" var="v">
	<li>${v.pessoa.nome} em <fmt:formatDate value="${v.data.time}" pattern="MM/dd/yyyy"/> </li>
</c:forEach>
</ul>

<hr />
Quem foi à ${lugar.cidade} também viajou para:
<ul>
<c:forEach items="${lugaresTambemVisitados}" var="l">
	<li>${l.cidade} - ${l.pais}</li>
</c:forEach>
</ul>
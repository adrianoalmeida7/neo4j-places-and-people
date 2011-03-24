<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

Dados de ${pessoa.nome} <br />

Viagens feitas:
<ul>
<c:forEach items="${viagens}" var="v">
	<li>${v.lugar.cidade} - ${v.lugar.pais} em <fmt:formatDate value="${v.data.time}" pattern="MM/dd/yyyy"/> </li>
</c:forEach>
</ul>
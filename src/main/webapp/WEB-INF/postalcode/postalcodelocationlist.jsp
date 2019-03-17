<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Postal Code Location List</title>
</head>
<body>
<form method="POST">
<input type="submit">
</form>

<h3>Awards</h3>
<hr size="4" color="gray"/>
<table>
    <tr>
    <th>Postal Code</th>
    <th>Longitude</th>
    <th>Latitude</th>
    </tr>
    <c:forEach items="${postalcodelocations}" var="postalcodelocation" varStatus="status">
    <tr>
        <td>${postalcodelocation["postalcode"]}</td>
        <td>${postalcodelocation["longitude"]}</td>
        <td>${postalcodelocation["latitude"]}</td>
    </tr>
    </c:forEach>
</table>
<a href="?offset=${backoffset}&pagesize=${pagesize}">Back</a> | <a href="?offset=${offset}&pagesize=${pagesize}">Next</a>
</body>
</html>
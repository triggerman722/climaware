<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Weather Station List</title>
</head>
<body>
<form method="POST">
<input type="submit">
</form>

<h3>Awards</h3>
<hr size="4" color="gray"/>
<table>
    <tr>
    <th>Year</th>
    <th>Month</th>
    <th>Day</th>
    <th>Time</th>
    <th>Wind Speed</th>
    </tr>
    <c:forEach items="${windrecords}" var="windrecord" varStatus="status">
    <tr>
        <td>${windrecord["year"]}</td>
        <td>${windrecord["month"]}</td>
        <td>${windrecord["day"]}</td>
        <td>${windrecord["time"]}</td>
        <td>${windrecord["windspeed"]}</td>
    </tr>
    </c:forEach>
</table>
</body>
</html>
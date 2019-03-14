<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Weather Station List</title>
</head>
<body>
<form method="POST">
<input type="submit">
</form>

<form method="POST">
<p>Postalcode: <input name=postalcode value="${param['postalcode']}">
<input type="submit">
</form>

<h3>Awards</h3>
<hr size="4" color="gray"/>
<table>
    <tr>
    <th>Name</th>
    <th>Longitude</th>
    <th>Latitude</th>
    <th>Elevation</th>
    <th>First Year</th>
    <th>Last Year</th>
    <th>PC</th>
    </tr>
    <c:forEach items="${weatherstations}" var="weatherstation" varStatus="status">
    <tr>
        <td>${weatherstation["name"]}</td>
        <td>${weatherstation["longitude"]}</td>
        <td>${weatherstation["latitude"]}</td>
        <td>${weatherstation["elevation"]}</td>
        <td>${weatherstation["firstyear"]}</td>
        <td>${weatherstation["lastyear"]}</td>
        <td>${weatherstation["tcid"]}</td>
        <!-- End of inner loop -->
    </tr>
    </c:forEach>
</table>
</body>
</html>
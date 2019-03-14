<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Weather Station List</title>
</head>
<body>
<a href="wind/score">Score</a>
<hr>
<h1>Add a single record</h1>
<form method="POST">
<p>Year: <input name=year>
<p>Month: <input name=month>
<p>Day: <input name=day>
<p>Station Id: <input name=stationid>
<p>Time: <input name=time>
<p>Windspeed (km/h): <input name=windspeed>
<p>Latitude: <input name=latitude>
<p>Longitude: <input name=longitude>

<input type="submit">
</form>
<hr>
<h1>All all records</h1>
<form method="POST">
<input type="submit">
</form>

<h3>Records</h3>
<hr size="4" color="gray"/>
<table>
    <tr>
    <th>Year</th>
    <th>Month</th>
    <th>Day</th>
    <th>Time</th>
    <th>Wind Speed</th>
    <th>Station Id</th>
    <th>Latitude</th>
    <th>Longitude</th>
    </tr>
    <c:forEach items="${windrecords}" var="windrecord" varStatus="status">
    <tr>
        <td>${windrecord["year"]}</td>
        <td>${windrecord["month"]}</td>
        <td>${windrecord["day"]}</td>
        <td>${windrecord["time"]}</td>
        <td>${windrecord["windspeed"]}</td>
        <td>${windrecord["stationid"]}</td>
        <td>${windrecord["latitude"]}</td>
        <td>${windrecord["longitude"]}</td>
    </tr>
    </c:forEach>
</table>
<a href="?offset=${backoffset}&pagesize=${pagesize}">Back</a> | <a href="?offset=${offset}&pagesize=${pagesize}">Next</a>
</body>
</html>
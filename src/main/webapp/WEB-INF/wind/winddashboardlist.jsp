<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Wind Dashboard</title>
</head>
<body>

<h1>Total Records: ${dashboard.count}</h1>
<h1>Maximum Windspeed: ${dashboard.maximum}</h1>
<h1>Minimum Windspeed: ${dashboard.minimum}</h1>
<h1>Average Windspeed: ${dashboard.average}</h1>

<hr>

<h3>Records by latitude and longitude</h3>
<hr size="4" color="gray"/>
<table border=1>
    <tr>
    <th>Index</th>
    <th>Latitude</th>
    <th>Longitude</th>
    <th>Count</th>
    <th>Min Year</th>
    <th>Max Year</th>
    <th>Min Windspeed</th>
    <th>Max Windspeed</th>
    <th>avg Windspeed</th>
    <th>Station ID</th>
    </tr>
    <c:forEach items="${dashboard.windDashboardItems}" var="dashboarditem" varStatus="status">
    <tr>
        <td>${status.index}</td>
        <td>${dashboarditem.latitude}</td>
        <td>${dashboarditem.longitude}</td>
        <td>${dashboarditem.count}</td>
        <td>${dashboarditem.minyear}</td>
        <td>${dashboarditem.maxyear}</td>
        <td>${dashboarditem.minwindspeed}</td>
        <td>${dashboarditem.maxwindspeed}</td>
        <td>${dashboarditem.avgwindspeed}</td>
        <td>${dashboarditem.stationid}</td>
    </tr>
    </c:forEach>
</table>

</body>
</html>
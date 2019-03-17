<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Weather Station View</title>
<style type="text/css">
      html, body, #basicMap {
          width: 100%;
          height: 100%;
          margin: 0;
      }
    </style>
    <script src="resources/js/OpenLayers.js"></script>
        <script>
          function init() {
            map = new OpenLayers.Map("basicMap");
            var mapnik         = new OpenLayers.Layer.OSM();
            var fromProjection = new OpenLayers.Projection("EPSG:4326");   // Transform from WGS 1984
            var toProjection   = new OpenLayers.Projection("EPSG:900913"); // to Spherical Mercator Projection
            var position       = new OpenLayers.LonLat(${weatherstation["longitude"]},${weatherstation["latitude"]}).transform( fromProjection, toProjection);
            var zoom           = 15;

            map.addLayer(mapnik);
            map.setCenter(position, zoom );
          }
        </script>
</head>
<body onload="init();">

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
    <th>Station ID</th>
    </tr>
    <tr>
        <td>${weatherstation["name"]}</td>
        <td>${weatherstation["longitude"]}</td>
        <td>${weatherstation["latitude"]}</td>
        <td>${weatherstation["elevation"]}</td>
        <td>${weatherstation["firstyear"]}</td>
        <td>${weatherstation["lastyear"]}</td>
        <td>${weatherstation["tcid"]}</td>
        <td>${weatherstation["stationid"]}</td>
    </tr>
</table>
<form method="POST">
<input type=hidden name=_method value=DELETE >
<input type=hidden name=stationid value="${weatherstation["stationid"]}">
<input type=submit value="delete">
</form>

<div id="basicMap"></div>

</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Wind Score Response</title>
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
<h1> The Wind Score For ${postalcode} Is: <u>${score['score']}</u></h1>
<p>The exact value is ${score['value']}.
<p>Details:
<p>Maximum: ${score.windFact.maximum}
<p>Minimum: ${score.windFact.minimum}
<p>Average: ${score.windFact.average}
<p>Count: ${score.windFact.count}
<p>Weather station: <a href="/climaware/weatherstation?stationid=${stationid}">${stationid}</a>
<div id="basicMap"></div>
</body>
</html>
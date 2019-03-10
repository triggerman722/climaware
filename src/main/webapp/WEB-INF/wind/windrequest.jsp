<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Add a new Wind Record</title>
</head>
<body>
<form method="POST">
<p>Year: <input name=year>
<p>Month: <input name=month>
<p>Day: <input name=day>
<p>Postal Code: <input name=postalcode>
<p>Time: <input name=time>
<p>Windspeed (km/h): <input name=windspeed>
<p>Latitude: <input name=latitude>
<p>Longitude: <input name=longitude>

<input type="submit">
</form>
</body>
</html>
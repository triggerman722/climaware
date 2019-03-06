<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Wind Request</title>
</head>
<body>
<form method="POST">
<p>Year: <input name=year>
<p>Month: <input name=month>
<p>Day: <input name=day>
<p>Latitude: <input name=latitude>
<p>Longitude: <input name=longitude>
<input type=hidden name=action value=adj>
<input type="submit">
</form>
</body>
</html>
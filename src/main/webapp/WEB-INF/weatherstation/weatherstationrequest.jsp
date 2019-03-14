<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Weather Station Request</title>
</head>
<body>
<form method="POST">
<p>Postalcode: <input name=distance value="${param['postalcode']}">
<input type="submit">
</form>
</body>
</html>
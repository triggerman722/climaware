<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>DB Maintenance</title>
</head>
<body>
<form method="POST">
<input type="submit" value="backup">
</form>
<form method="POST">
<input type="submit" name="reload" value="reload">
</form>
<form method="POST">
<input type="submit" name="reloadweatherstations" value="reload weatherstations">
</form>
<form method="POST">
<input type="submit" name="reloadpostalcodes" value="reload postalcodes">
</form>
<h1>The number of rows affected was: ${rowsaffected}</h1>
</body>
</html>

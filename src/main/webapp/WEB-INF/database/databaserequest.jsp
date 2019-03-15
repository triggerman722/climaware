<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>DB Maintenance</title>
</head>
<body>
<form method="POST">
<input type="submit" value="backup">
</form>
<h1>The number of rows affected was: ${rowsaffected}</h1>
</body>
</html>
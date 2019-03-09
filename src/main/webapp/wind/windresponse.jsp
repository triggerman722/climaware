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
<p>Postal Code: <input name=postalcode>
<input type=hidden name=action value=adj>
<input type="submit">
</form>
Yer score is ${score} and that means ${scorestring}
</body>
</html>
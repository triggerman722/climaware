<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Wind Score Response</title>
</head>
<body>
<h1> The Wind Score For This Location Is: <u>${score['score']}</u></h1>
<p>The exact value is ${score['value']}.
</body>
</html>
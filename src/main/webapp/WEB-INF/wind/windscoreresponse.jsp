<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<title>Wind Score Response</title>
</head>
<body>
<h1> The Wind Score For ${postalcode} Is: <u>${score['score']}</u></h1>
<p>The exact value is ${score['value']}.
<p>Details:
<p>Maximum: ${score.windFact.maximum}
<p>Minimum: ${score.windFact.minimum}
<p>Average: ${score.windFact.average}
<p>Count: ${score.windFact.count}
</body>
</html>
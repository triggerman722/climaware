<%@ page import ="java.util.List"%>
<%@ page import ="java.util.Map"%>
<%@ page import ="java.io.ObjectInputStream"%>
<%@ page import ="com.climaware.model.Award"%>
<html>
<head>
<title>Award List</title>
</head>
<body>
<form method="POST">
<input type="submit">
</form>

<h3>Awards</h3>
<hr size="4" color="gray"/>
<table>
<%
// retrieve your list from the request, with casting
List<Award> list = (List<Award>) request.getAttribute("awards");

// print the information about every category of the list
for(Award award : list) {
%><tr><td><%
    out.println(award.getId());
%></td><td><%
    out.println(award.getCode());
%></td></tr><%
}
%>
</table>
</body>
</html>
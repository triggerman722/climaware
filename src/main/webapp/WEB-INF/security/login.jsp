<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>${pageTitle}</title>

    <link href="https://fonts.googleapis.com/css?family=Alegreya+Sans:800" rel="stylesheet">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" type='text/css'>
    <link href="resources/css/common.css" rel="stylesheet">
    <link href="resources/css/registration.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="signup-form">

    <form method="POST" action="/climaware/login" modelAttribute="userForm" class="form-signin">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		<div class="form-header">
			<h2>Login</h2>
			<p>Please enter your credentials.</p>
		</div>

            <div class="form-group ${status.error ? 'has-error' : ''}">
		<label>Username</label>
                <input type="text" name="username" class="form-control" autofocus="true">

		<span>${message}</span>
            </div>

            <div class="form-group ${status.error ? 'has-error' : ''}">
		<label>Password</label>
                <input type="password" name="password" class="form-control">

		<span>${error}</span>
            </div>

	<div class="form-group">
		<button type="submit" class="btn btn-primary btn-block btn-lg">Login</button>
	</div>	
    </form>
	<div class="text-center small">Need an account? <a href="registration">Sign up here</a></div>
</div>
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>


<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Registration</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  <script src="js/validation.js"></script>
</head>

<body class= "text-center">

    <div class="container-fluid">

        <form method="post" class="form-signin" onsubmit="return validateRegistrationForm()" id="registrationForm">
          <img class="mb-4" src="{{ site.baseurl }}/docs/{{ site.docs_version }}/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
          <h1 class="h3 mb-3 font-weight-normal">Please register</h1>

          <jsp:include page="errormessage.jsp"/>

          <label for="inputEmail" class="sr-only">Email address</label>
          <input type="email" id="inputEmail" class="form-control" name="login" placeholder="Email address" required autofocus>

          <label for="inputPassword" class="sr-only">Password</label>
          <input type="password" id="inputPassword" class="form-control" name="password" placeholder="Password" required>

          <label for="inputPassword" class="sr-only">Confirm password</label>
          <input type="password" id="confirmPassword" class="form-control" name="confirmPassword" placeholder="Confirm password" required>

          <button class="btn btn-lg btn-primary btn-block" type="submit">Register</button>
          <p class="mt-5 mb-3 text-muted">&copy; 2017-{{ site.time | date: "%Y" }}</p>
        </form>
    </div>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Personal data</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>

<body>

    <jsp:include page="headmenu.jsp"/>

    <jsp:include page="profileheader.jsp"/>

    <div class="container">

      <div class="row">

        <div class="col-md-8 order-md-1">
          <h4 class="mb-3">Personal data</h4>
          <hr class="mb-4">
            <div class="row">
                  <div class="col-md-6 mb-3">
                    <h5><label for="firstName">First name</label></h5>
                    <input type="text" readonly class="form-control-plaintext" id="firstName" value="${customer.name}">
                  </div>
              <div class="col-md-6 mb-3">
                <h5><label for="lastName">Last name</label></h5>
                <input type="text" readonly class="form-control-plaintext" id="lastName" value="Lastnameexamle">
              </div>
            </div>
          <hr class="mb-4">
            <div class="mb-3">
              <h5><label for="email">Email/Login</label></h5>
              <input type="email" readonly class="form-control-plaintext" id="email" value="${customer.login}">
            </div>
          <hr class="mb-4">
            <div class="mb-3">
              <h5><label for="phone">Phone number</label></h5>
              <input type="text" readonly class="form-control-plaintext" id="phone" value="+${customer.phoneNumber}">
            </div>
          <hr class="mb-4">
            <div class="mb-3">
              <h5><label for="address">Shipping address</label></h5>
              <input type="text" readonly class="form-control-plaintext" id="address" value="Lahojski trakt 28/1">
            </div>
          <hr class="mb-4">
            <div class="row">
              <div class="col-md-5 mb-3">
                <h5><label for="country">Country</label></h5>
                  <input type="text" readonly class="form-control-plaintext" id="country" value="Belarus">
              </div>
              <div class="col-md-4 mb-3">
                <h5><label for="state">State</label></h5>
                <input type="text" readonly class="form-control-plaintext" id="state" value="Minsk">
              </div>
              <div class="col-md-3 mb-3">
                <h5><label for="zip">Zip</label></h5>
                <input type="text" readonly class="form-control-plaintext" id="zip" value="220017">
              </div>
            </div>
          <hr class="mb-4">
        </div>
      </div>

    </div>

    <jsp:include page="footer.jsp"/>

</body>
</html>
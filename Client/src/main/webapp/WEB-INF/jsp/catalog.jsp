<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Catalog</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container-fluid">

    <jsp:include page="headmenu.jsp"/>
    <h1>Catalog</h1>

    <ul class="breadcrumb">
      <li class="breadcrumb-item"><a href="starterpage.html">Home</a></li>
      <li class="breadcrumb-item active">Catalog</li>
    </ul>

    <div id="demo" class="carousel slide" data-ride="carousel">

      <pre>
        <c:forEach var="category" items="${categories}" varStatus="loop">
            <c:out value="${category}"/>
        </c:forEach>
      </pre>

      <!-- Indicators -->
      <ul class="carousel-indicators">
        <li data-target="#demo" data-slide-to="0" class="active"></li>
        <li data-target="#demo" data-slide-to="1"></li>
        <li data-target="#demo" data-slide-to="2"></li>
      </ul>

      <!-- The slideshow -->
      <div class="carousel-inner">
        <div class="carousel-item active">
          <img src="pictures/imgFour.jpg" alt="Bottles">
        </div>
        <div class="carousel-item">
          <img src="pictures/imgFive.jpg" alt="Apple">
        </div>
        <div class="carousel-item">
          <img src="pictures/imgSix.jpg" alt="Grocery">
        </div>
      </div>



      <!-- Left and right controls -->
      <a class="carousel-control-prev" href="#demo" data-slide="prev">
        <span class="carousel-control-prev-icon"></span>
      </a>
      <a class="carousel-control-next" href="#demo" data-slide="next">
        <span class="carousel-control-next-icon"></span>
      </a>

    </div>


</div>

    <jsp:include page="footer.jsp"/>

</body>
</html>
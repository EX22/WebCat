<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Product</title>
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
    <h1>Product</h1>

    <ul class="breadcrumb">
      <li class="breadcrumb-item"><a href="starterpage.html">Home</a></li>
      <li class="breadcrumb-item"><a href="catalog.html">Catalog</a></li>
      <li class="breadcrumb-item"><a href="category.html">Category</a></li>
      <li class="breadcrumb-item active">Product</li>
    </ul>

    <pre>
      <c:forEach var="product" items="${product}" varStatus="loop">
          <c:out value="${product}"/>
      </c:forEach>
    </pre>

    <div class="card mb-4 shadow-sm">
      <div class="card-header">
        <h4 class="my-0 font-weight-normal">"${product.productName}"</h4>
      </div>
      <div class="card-body">
        <h1 class="card-title pricing-card-title">"${product.productPrice}" <small class="text-muted">/ $</small></h1>
        <ul class="list-unstyled mt-3 mb-4">
          <li><c:out value="${product.shortDescription}"/></li>
          <li>10 GB of storage</li>
          <li>Priority email support</li>
          <li>Help center access</li>
        </ul>
        <button type="button" class="btn btn-lg btn-block btn-primary">Buy it now</button>
        <button type="button" class="btn btn-lg btn-block btn-primary">Add to cart</button>
      </div>
    </div>


</div>

    <jsp:include page="footer.jsp"/>

</body>
</html>
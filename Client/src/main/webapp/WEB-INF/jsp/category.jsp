<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Category</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
      function cart(productId)
          {

          $.ajax({
              type:'get',
              url:'cart.html',
              data:{
                id:productId
              },
              success:function(response) {
                document.getElementById("total_items").textContent=response;

              }
            });

          }
  </script>
</head>
<body>

<div class="container-fluid">

    <jsp:include page="headmenu.jsp"/>
    <h1>Category</h1>

    <ul class="breadcrumb">
      <li class="breadcrumb-item"><a href="starterpage.html">Home</a></li>
      <li class="breadcrumb-item"><a href="catalog.html">Catalog</a></li>
      <li class="breadcrumb-item active">Category</li>
    </ul>

    <div class="pricing-header px-3 py-3 pt-md-5 pb-md-4 mx-auto text-center">
      <h1 class="display-4">"${currentCategory.categoryName}"</h1>
      <p class="lead"> Probably some category description. </p>
    </div>

    <div class="container">
      <div class="card-deck mb-3 text-center">

          <ul class="nav">
              <c:forEach var="product" items="${products}" varStatus="loop">
                <li class="nav-item">
                    <div class="card mb-4 shadow-sm">
                      <div class="card-header">
                        <h4 class="my-0 font-weight-normal">"${product.productName}"</h4>
                      </div>
                      <div class="card-body">
                        <img src="/images/${product.photoPath}" class="img-fluid"/>
                        <h1 class="card-title pricing-card-title">"${product.productPrice}"<small class="text-muted">/ "${product.productDiscount}"</small></h1>
                        <ul class="list-unstyled mt-3 mb-4">
                          <li>"${product.shortDescription}"</li>
                        </ul>
                        <a class="btn btn-primary" href="checkout.html?id=${product.productId}" role="button">Buy it now</a>
                        <button type="button" class="btn btn-lg btn-block btn-secondary" onclick="cart(${product.productId})">Add to cart</button>
                      </div>
                    </div>
                </li>
              </c:forEach>
          </ul>
      </div>
    </div>

</div>

    <jsp:include page="footer.jsp"/>

</body>
</html>
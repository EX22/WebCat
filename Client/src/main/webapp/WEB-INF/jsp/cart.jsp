<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Cart</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

    <jsp:include page="headmenu.jsp"/>

    <div class="container">
      <div class="py-5 text-center">
        <h2>Cart</h2>
      </div>

      <div class="row">
        <div class="col-md-4 order-md-2 mb-4">
          <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-muted">Your cart</span>
            <span class="badge badge-secondary badge-pill">Products amount <c:out value="${cartContent.productsCount}"/></span>
          </h4>
          <form class="card p-2">
              <ul class="list-group mb-3">
                  <c:forEach var="product" items="${cartContent.productInfo}" varStatus="loop">
                      <div class="card mb-3" style="max-width: 540px;">
                        <div class="row no-gutters">
                          <div class="col-md-4">
                            <img src="/images/${product.value.photoPath}" class="card-img" alt="...">
                          </div>
                          <div class="col-md-8">
                            <div class="card-body">
                              <h5 class="card-title"><c:out value="${product.value.productName}"/></h5>
                              <p class="card-text"><c:out value="${product.value.productPrice}"/>$</p>
                              <button type="submit" class="btn btn-secondary btn-sm btn-block"
                                        name="productIdToRemove" value="${product.value.productId}">Remove this product</button>
                              <button type="submit" class="btn btn-primary btn-sm btn-block"
                                        name="productIdToAdd" value="${product.value.productId}">Add one more</button>
                              <div class="form-row align-items-center">
                                  <div class="col-auto my-1">
                                        <h5>Quantity <span class="badge badge-secondary">
                                                <c:out value="${cartContent.products[product.value.productId]}"/></span></h5>
                                  </div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                  </c:forEach>
              </ul>

            <div class="input-group">
              <input type="text" class="form-control" placeholder="Promo code"/>
              <div class="input-group-append">
                <button type="submit" class="btn btn-secondary">Redeem</button>
              </div>
            </div>
            <hr class="mb-4">
            <a class="btn btn-primary btn-lg btn-block" href="checkout.html?id=${product.productId}" role="button">Continue to checkout</a>
          </form>
        </div>
      </div>

    </div>

    <jsp:include page="footer.jsp"/>

</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
<head>
  <title>Checkout</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
</head>
<body>

    <div class="container">
      <div class="py-5 text-center">
        <h2>Checkout</h2>
      </div>

      <div class="row">
        <div class="col-md-4 order-md-2 mb-4">
          <h4 class="d-flex justify-content-between align-items-center mb-3">
            <span class="text-muted">Your cart</span>
            <span class="badge badge-secondary badge-pill">Cart size <c:out value=""/></span>
          </h4>
          <ul class="list-group mb-3">

                  <c:forEach var="product" items="${customerCart.products}" varStatus="loop">
                      <li class="list-group-item d-flex justify-content-between lh-condensed">
                        <div>
                          <h6 class="my-0"><c:out value="${product.productName}"/></h6>
                        </div>
                        <span class="text-muted"><c:out value="${product.productPrice}"/>$</span>
                      </li>
                  </c:forEach>

            <li class="list-group-item d-flex justify-content-between bg-light">
              <div class="text-success">
                <h6 class="my-0">Promo code</h6>
                <small>EXAMPLECODE</small>
              </div>
              <span class="text-success">-$5</span>
            </li>
            <li class="list-group-item d-flex justify-content-between">
              <span>Total (USD)</span>
              <strong>$20</strong>
            </li>
          </ul>

          <form class="card p-2">
            <div class="input-group">
              <input type="text" class="form-control" placeholder="Promo code">
              <div class="input-group-append">
                <button type="submit" class="btn btn-secondary">Redeem</button>
              </div>
            </div>
          </form>
        </div>

        <div class="col-md-8 order-md-1">
          <h4 class="mb-3">Shipping address</h4>
          <form method="post" class="needs-validation" novalidate
            onsubmit="return validateCheckOutForm()" id="checkOutForm">
            <div class="row">
              <div class="col-md-6 mb-3">
                <label for="firstName">First name</label>
                <input type="text" class="form-control" id="firstName" name="customerFirstName" placeholder="${customer.name}" value="" required>
                <div class="invalid-feedback">
                  Valid first name is required.
                </div>
              </div>
              <div class="col-md-6 mb-3">
                <label for="lastName">Last name</label>
                <input type="text" class="form-control" id="lastName" name="customerLastName" placeholder="" value="" required>
                <div class="invalid-feedback">
                  Valid last name is required.
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="email">Email/Login</label>
              <input type="email" class="form-control" id="email" name="customerEmail" placeholder="${customer.login}">
              <div class="invalid-feedback">
                Please enter a valid email address for shipping updates.
              </div>
            </div>

            <div class="mb-3">
              <label for="phone">Phone number</label>
              <input type="text" class="form-control" id="phone" name="customerPhone" placeholder="+${customer.phoneNumber}" required>
              <div class="invalid-feedback">
                Please enter your phone number.
              </div>
            </div>

            <div class="mb-3">
              <label for="address">Shipping address</label>
              <input type="text" class="form-control" id="address" name="customerShippingAddress" placeholder="" required>
              <div class="invalid-feedback">
                Please enter your shipping address.
              </div>
            </div>

            <div class="row">
              <div class="col-md-5 mb-3">
                <label for="country">Country</label>
                <select class="custom-select d-block w-100" id="country" name="customerCountry" required>
                  <option value="">Choose...</option>
                  <option>Belarus</option>
                  <option>Russia</option>
                  <option>Ukraine</option>
                </select>
                <div class="invalid-feedback">
                  Please select a valid country.
                </div>
              </div>
              <div class="col-md-4 mb-3">
                <label for="state">State</label>
                <select class="custom-select d-block w-100" id="state" name="customerState" required>
                  <option value="">Choose...</option>
                  <option>Minsk</option>
                  <option>Moscow</option>
                  <option>Kiev</option>
                </select>
                <div class="invalid-feedback">
                  Please provide a valid state.
                </div>
              </div>
              <div class="col-md-3 mb-3">
                <label for="zip">Zip</label>
                <input type="text" class="form-control" id="zip" name="customerZipCode" placeholder="" required>
                <div class="invalid-feedback">
                  Zip code required.
                </div>
              </div>
            </div>
            <hr class="mb-4">
            <div class="custom-control custom-checkbox">
              <input type="checkbox" class="custom-control-input" id="save-info">
              <label class="custom-control-label" for="save-info">Save this information for next time</label>
            </div>

            <hr class="mb-4">
            <button class="btn btn-primary btn-lg btn-block" type="submit">To place an order</button>
          </form>
        </div>
      </div>
    </div>

    <jsp:include page="footer.jsp"/>

</body>
</html>
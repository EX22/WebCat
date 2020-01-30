<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
    <head>
      <title>Settings</title>
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
                <h4 class="mb-3">Settings</h4>
                <hr class="mb-4">
                <form class="needs-validation" novalidate>
                  <div class="row">
                    <div class="col-md-6 mb-3">
                      <label for="firstName">First name</label>
                      <input type="text" class="form-control" id="firstName" placeholder="${customer.name}" value="" required>
                      <div class="invalid-feedback">
                        Valid first name is required.
                      </div>
                    </div>
                    <div class="col-md-6 mb-3">
                      <label for="lastName">Last name</label>
                      <input type="text" class="form-control" id="lastName" placeholder="" value="" required>
                      <div class="invalid-feedback">
                        Valid last name is required.
                      </div>
                    </div>
                  </div>

                  <div class="mb-3">
                    <label for="email">Email/Login</label>
                    <input type="email" class="form-control" id="email" placeholder="${customer.login}">
                    <div class="invalid-feedback">
                      Please enter a valid email address for shipping updates.
                    </div>
                  </div>

                  <div class="mb-3">
                    <label for="phone">Phone number</label>
                    <input type="text" class="form-control" id="phone" placeholder="+${customer.phoneNumber}" required>
                    <div class="invalid-feedback">
                      Please enter your phone number.
                    </div>
                  </div>

                  <div class="mb-3">
                    <label for="address">Shipping address</label>
                    <input type="text" class="form-control" id="address" placeholder="Lahojski trakt 28/1" required>
                    <div class="invalid-feedback">
                      Please enter your shipping address.
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-md-5 mb-3">
                      <label for="country">Country</label>
                      <select class="custom-select d-block w-100" id="country" required>
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
                      <select class="custom-select d-block w-100" id="state" required>
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
                      <input type="text" class="form-control" id="zip" placeholder="" required>
                      <div class="invalid-feedback">
                        Zip code required.
                      </div>
                    </div>
                  </div>
                  <hr class="mb-4">
                  <button class="btn btn-primary btn-lg btn-block" type="submit">Confirm changes</button>
                </form>
              </div>
            </div>
        </div>

        <jsp:include page="footer.jsp"/>

    </body>
</html>

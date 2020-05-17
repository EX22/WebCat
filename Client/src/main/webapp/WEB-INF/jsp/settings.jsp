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
                <form method="post" class="needs-validation" novalidate
                    onsubmit="return validateProfileSettingsForm()" id="profileSettingsForm">
                  <div class="row">
                    <div class="col-md-6 mb-3">
                      <h5><label for="firstName">First name</label>
                      <input type="text" class="form-control" id="firstName" name="customerFirstName"
                                    placeholder="${customer.name}" value="${customer.name}" required>
                      <div class="invalid-feedback">
                        Valid first name is required.
                      </div>
                    </div>
                    <div class="col-md-6 mb-3">
                      <h5><label for="lastName">Last name</label>
                      <input type="text" class="form-control" id="lastName" name="customerLastName"
                                    placeholder="${customer.lastName}" value="${customer.lastName}" required>
                      <div class="invalid-feedback">
                        Valid last name is required.
                      </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <h5><label for="phone">Phone number</label>
                        <input type="text" class="form-control" id="phone" name="customerPhone"
                                    placeholder="${customer.phoneNumber}" value="${customer.phoneNumber}" required>
                        <div class="invalid-feedback">
                          Please enter your phone number.
                        </div>
                    </div>
                  </div>

                  <div class="mb-3">
                    <h5><label for="address">Shipping address</label></h5>
                    <hr class="mb-4">
                    <c:forEach var="contacts" items="${customer.contactsList}" varStatus="loop">
                          <label for="${contacts.id}"><c:out value="${contacts.shippingAddress}"/></label><br>
                          <label for="${contacts.id}"><c:out value="${contacts.country}"/></label><br>
                          <label for="${contacts.id}"><c:out value="${contacts.state}"/></label><br>
                          <label for="${contacts.id}"><c:out value="${contacts.zipCode}"/></label><br>
                          <button type="submit" class="btn btn-primary btn-sm" name="contactsIdToDelete"
                                        value="${contacts.id}">Delete this address</button>
                          <hr class="mb-4">
                    </c:forEach>
                    <h5><label for="address">Add shipping address</label>
                    <input type="text" class="form-control" id="address" name="customerAddress" placeholder="" required>
                    <div class="invalid-feedback">
                      Please enter your shipping address.
                    </div>
                  </div>

                  <div class="row">
                    <div class="col-md-5 mb-3">
                      <h5><label for="country">Country</label>
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
                      <h5><label for="state">State</label>
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
                      <h5><label for="zip">Zip</label>
                      <input type="text" class="form-control" id="zip" name="customerZipCode" placeholder="" required>
                      <div class="invalid-feedback">
                        Zip code required.
                      </div>
                    </div>
                  </div>

                  <div class="mb-3">
                      <h5><label for="email">Email/Login</label>
                      <input type="email" class="form-control" id="email" name="customerEmail" placeholder="${customer.login}">
                      <div class="invalid-feedback">
                        Please enter a valid email address for shipping updates.
                      </div>
                  </div>
                  <div class="mb-3">
                       <h5><label for="currentPassword">Password</label>
                       <input type="password" class="form-control" id="currentPassword" name="customerCurrentPassword"
                                    placeholder="If you want to change your password, type current one here">
                       <div class="invalid-feedback">
                         Please enter a valid password.
                       </div>
                  </div>
                  <div class="mb-3">
                         <h5><label for="newPassword">New Password</label>
                         <input type="password" class="form-control" id="newPassword" name="customerNewPassword"
                                    placeholder="If you want to change your password, type new one here">
                         <div class="invalid-feedback">
                           Please enter new password.
                         </div>
                  </div>
                  <div class="mb-3">
                         <h5><label for="confirmNewPassword">Confirm New Password</label>
                         <input type="password" class="form-control" id="confirmNewPassword" name="customerConfirmNewPassword"
                                    placeholder="Confirm new password">
                         <div class="invalid-feedback">
                           Please confirm new password.
                         </div>
                  </div>
                  <hr class="mb-4">
                  <button class="btn btn-primary btn-lg btn-block" type="submit">Confirm changes</button>
                  <button class="btn btn-secondary btn-lg btn-block" type="reset">Reset changes</button>
                </form>
              </div>
            </div>
        </div>

        <jsp:include page="footer.jsp"/>

    </body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
    <head>
      <title>Order status</title>
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

          <ul class="list-group mb-3">
                <c:forEach var="ordersList" items="${customerOrdersList}" varStatus="loop">
                    <div class="card text-center">
                        <div class="card-header">
                          <c:out value="${ordersList.orderId}"/> Order`s id
                        </div>
                        <div class="card-body">
                          <p class="card-text">Ordered goods list.</p>
                          <p class="card-text"><c:out value="${ordersList.orderPrice}"/> Order`s price.</p>
                          <h5 class="card-title"><c:out value="${ordersList.orderStatus}"/> Order`s status</h5>
                          <span class="badge badge-secondary">Accomplished</span>
                          <span class="badge badge-success">In progress</span>
                        </div>
                        <div class="card-footer text-muted">
                          <c:out value="${ordersList.orderDate}"/> Order`s date
                        </div>
                    </div>
                </c:forEach>
          </ul>

        <jsp:include page="footer.jsp"/>

    </body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

<!DOCTYPE html>
<html lang="en">
    <head>
      <title>Profile</title>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
      <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    </head>

    <body>

        <div class="card" style="width: 18rem;">
          <img src="..." class="card-img-top" alt="...">
          <div class="card-body">
            <p class="card-text">"${customer.name}"</p>
          </div>
        </div>

        <div class="card text-center">
          <div class="card-header">
            <ul class="nav nav-tabs card-header-tabs">
              <li class="nav-item">
                <a class="nav-link" href="#">Personal data</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Order status</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Settings</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="#">Cart</a>
              </li>
            </ul>

                <script type="text/javascript">
                     context = "${context}"
                     active_id = window.location.pathname.substring(context.length + 1)
                     document.getElementById(active_id).className = "active"
                </script>

          </div>
          <div class="card-body">
            <h5 class="card-title">Special title treatment</h5>
            <p class="card-text">With supporting text below as a natural lead-in to additional content.</p>
            <a href="#" class="btn btn-primary">Go somewhere</a>
          </div>
        </div>


    </body>
</html>

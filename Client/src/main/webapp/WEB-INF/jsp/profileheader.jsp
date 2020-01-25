<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

        <div class="card" style="width: 18rem;">
          <img src="..." class="card-img-top" alt="...">
          <div class="card-body">
            <p class="card-text">"${customer.login}"</p>
          </div>
        </div>

        <div class="card text-center">
          <div class="card-header">
            <ul class="nav nav-tabs card-header-tabs">
              <li class="nav-item">
                <a class="nav-link" href="personaldata.html">Personal data</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="orderstatus.html">Order status</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="settings.html">Settings</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="cart.html">Cart</a>
              </li>
            </ul>
          </div>

                <script type="text/javascript">
                     context = "${context}"
                     active_id = window.location.pathname.substring(context.length + 1)
                     document.getElementById(active_id).className = "active"
                </script>
        </div>
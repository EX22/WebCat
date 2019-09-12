<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

    <nav class="site-header sticky-top py-1">
      <div class="container d-flex flex-column flex-md-row justify-content-between">
        <a class="py-2" href="#">
          <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="none" stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" class="d-block mx-auto" role="img" viewBox="0 0 24 24" focusable="false"><title>Product</title><circle cx="12" cy="12" r="10"/><path d="M14.31 8l5.74 9.94M9.69 8h11.48M7.38 12l5.74-9.94M9.69 16L3.95 6.06M14.31 16H2.83m13.79-4l-5.74 9.94"/></svg>
        </a>
        <a class="py-2 d-none d-md-inline-block" href="catalog.html">Catalog</a>
        <a class="py-2 d-none d-md-inline-block" href="contacts.html">Contacts</a>
        <a class="py-2 d-none d-md-inline-block" href="registration.html">Register</a>
        <a class="py-2 d-none d-md-inline-block" href="signin.html">SingIn</a>
        <a class="py-2 d-none d-md-inline-block" href="#">SingOut</a>
        <a class="py-2 d-none d-md-inline-block" href="cart.html">Cart</a>
        <!-- Dropdown -->
              <a class="nav-link dropdown-toggle" href="#" id="navbardrop" data-toggle="dropdown">
                Language
              </a>
              <div class="dropdown-menu">
                <a class="dropdown-item" href="#">Belarusian</a>
                <a class="dropdown-item" href="#">English</a>
                <a class="dropdown-item" href="#">Russian</a>
              </div>

      </div>

      <form class="form-inline" action="/action_page.php">
          <input class="form-control mr-sm-2" type="text" placeholder="Search">
          <button class="btn btn-success" type="submit">Search</button>
      </form>

    </nav>
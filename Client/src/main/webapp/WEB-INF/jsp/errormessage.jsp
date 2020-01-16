<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page import = "java.io.*,java.util.Locale" %>
<%@ page import = "javax.servlet.*,javax.servlet.http.* "%>

    <c:if test="${not empty errorMessage}">
        <div class="alert alert-danger" role="alert">
              <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
              <span class="sr-only">Error:</span>
                <c:out value="${errorMessage}"/>
        </div>
    </c:if>

    <div class="alert alert-danger invisible" id="errorMessage" >
      <span id="errorMessageText">Error message text</span>
    </div>
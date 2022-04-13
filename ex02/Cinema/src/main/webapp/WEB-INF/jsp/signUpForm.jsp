<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%
  Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
  Map<String, String> fields = (Map<String, String>) request.getAttribute("fields");
%>
<html>
<head>
    <title>SignUp</title>
</head>
<style>
  form {width: 400px; margin: auto}

  input[type=text], input[type=password] {
    width: 100%;
    padding: 15px;
    margin: 5px 0 0 0;
    display: inline-block;
    border: none;
    background: #f1f1f1;
  }

  .field {
    margin-bottom: 20px;
    height: 80px;
  }

  input[type=text]:focus, input[type=password]:focus {
    background-color: #ddd;
    outline: none;
  }

  hr {
    border: 1px solid #f1f1f1;
    margin-bottom: 25px;
  }

  .registerbtn {
    background-color: #5237d5;
    color: white;
    padding: 16px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 400px;
    opacity: 0.9;
  }

  .registerbtn:hover {
    opacity:1;
    cursor: pointer;
  }

  .error {
    margin: 2px 0 0;
    font-size: 10pt;
    color: #c41515;
  }

</style>
<body>
<form action="/cinema/signUp" method="post">
  <div class="container">
    <h1 style="text-align: center">Registration</h1>
    <hr>

    <div class="field">
      <label for="firstName"><b>First name</b></label>
      <input autocomplete="false" type="text" placeholder="Enter first name" name="firstName" id="firstName" required
             value="<%= fields.getOrDefault("firstName", "") %>">
      <p class="error"><%= errors.getOrDefault("firstName", "")%></p>
    </div>

    <div class="field">
      <label for="lastName"><b>Last name</b></label>
      <input autocomplete="false" type="text" placeholder="Enter last name" name="lastName" id="lastName" required
             value="<%= fields.getOrDefault("lastName", "") %>">
      <p class="error"><%= errors.getOrDefault("lastName", "")%></p>
    </div>

    <div class="field">
      <label for="email"><b>Email</b></label>
      <input autocomplete="false" type="text" placeholder="Enter Email" name="email" id="email" required
             value="<%= fields.getOrDefault("email", "") %>">
      <p class="error"><%= errors.getOrDefault("email", "")%></p>
    </div>

    <div class="field">
      <label for="password"><b>Password</b></label>
      <input autocomplete="false" type="password" placeholder="Enter Password" name="password" id="password" required
             value="<%= fields.getOrDefault("password", "") %>">
      <p class="error"><%= errors.getOrDefault("password", "")%></p>
    </div>

    <button type="submit" class="registerbtn" value="/cinema/sighUp">Register</button>
  </div>
</form>
</body>
</html>

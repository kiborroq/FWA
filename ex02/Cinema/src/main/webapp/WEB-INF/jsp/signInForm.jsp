<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Map" %>
<%
  Map<String, String> errors = (Map<String, String>) request.getAttribute("errors");
  Map<String, String> fields = (Map<String, String>) request.getAttribute("fields");
%>
<html>
<head>
    <title>SignIn</title>
</head>
<style>
  form {width: 400px; margin: auto}

  input[type=text], input[type=password] {
    width: 100%;
    padding: 15px;
    margin: 5px 0 22px 0;
    display: inline-block;
    border: none;
    background: #f1f1f1;
  }

  input[type=text]:focus, input[type=password]:focus {
    background-color: #ddd;
    outline: none;
  }

  hr {
    border: 1px solid #f1f1f1;
    margin-bottom: 25px;
  }

  .loginbtn {
    background-color: #5237d5;
    color: white;
    padding: 16px 20px;
    margin: 8px 0;
    border: none;
    cursor: pointer;
    width: 400px;
    opacity: 0.9;
  }

  .loginbtn:hover {
    opacity:1;
    cursor: pointer;
  }

  .error {
    margin: 0;
    font-size: 14pt;
    color: #c41515;
    text-align: center;
  }

</style>
<body>
<form action="/cinema/signIn" method="post">
  <div class="container">
    <h1 style="text-align: center">Authentication</h1>
    <p class="error"><%= errors.getOrDefault("commonError", "") %></p>
    <hr>

    <label for="email"><b>Email</b></label>
    <input autocomplete="false" type="text" placeholder="Enter Email" name="email" id="email" required
           value="<%= fields.getOrDefault("email", "") %>"/>

    <label for="password"><b>Password</b></label>
    <input autocomplete="false" type="password" placeholder="Enter Password" name="password" id="password" required>

    <button type="submit" class="loginbtn" value="/cinema/sighIn">Login</button>
  </div>
</form>
</body>
</html>

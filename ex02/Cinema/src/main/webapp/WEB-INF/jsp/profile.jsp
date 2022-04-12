<%@ page import="edu.school21.cinema.listeners.ActiveSessionListener" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page import="java.time.LocalDate" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ page import="edu.school21.cinema.models.User" %>
<%@ page import="java.time.Instant" %>
<%@ page import="java.time.ZoneId" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% User user = (User) request.getSession().getAttribute("user"); %>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
</head>
<style>
    body {
        height: 100vh;
        font-family: Verdana, sans-serif;
    }

    .container {
        margin: auto;
        padding: 15px;
        width: 700px;
        height: 100%;
        display: flex;
        flex-direction: column;
    }

    .avatar-user-info {
        width: 100%;
        display: flex;
        justify-content: left;
    }

    .avatar-info {
        display: flex;
        flex-direction: column;
    }

    .user-info {
        font-size: 13pt;
        margin-top: 50px;
        margin-left: 30px;
        display: flex;
        flex-direction: row;
        overflow-x: auto;
        scrollbar-width: thin;
    }

    .uploadbtn {
        background-color: #5237d5;
        color: white;
        padding: 7px 20px;
        margin: 8px 0;
        border: none;
        cursor: pointer;
        height: 30px;
        width: 200px;
        opacity: 0.9;
    }

    .uploadbtn:hover {
        opacity:1;
        cursor: pointer;
    }

    .avatar {
        width:200px;
        height: 175px;
    }

    .info-key {
        margin-right: 10px;
        min-width: 100px;
    }

    .label {
        color: #a4a4a4;
        margin: 2px;
    }

    .value {
        margin: 2px;
    }

    .sessions-list {
        margin-top: 40px;
        display: flex;
        justify-content: space-between;
    }

    .images-list {
        margin-top: 30px;
        display: flex;
        justify-content: space-between;
    }

    table {
        font-size: 10pt;
        border-collapse: collapse;
        width: 87%;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: center;
        padding: 4px;
    }

    th {
        background-color: #dddddd;
    }
</style>
<body>
<div class="container">
    <div class="avatar-user-info">
        <div class="avatar-info">
            <img class="avatar" src="https://www.iguides.ru/upload/medialibrary/9f8/9f8fdff471b7d281f81f694c100b5adc.png">
            <button type="submit" class="uploadbtn" value="/cinema/sighUp">Upload</button>
        </div>
        <div class="user-info">
            <div class="info-key">
                <p class="label">Email</p>
                <p class="label">First name</p>
                <p class="label">Last name</p>
            </div>
            <div class="info-value">
                <p class="value"><%= user.getEmail() %></p>
                <p class="value"><%= user.getFirstName() %></p>
                <p class="value"><%= user.getLastName() %></p>
            </div>
        </div>
    </div>
    <div class="sessions-list">
        <p class="label">Sessions</p>
        <table>
            <tr>
                <th>Date</th>
                <th>Time</th>
                <th>IP</th>
            </tr>
            <c:forEach var="s" items="<%= ActiveSessionListener.getActiveSessions() %>">
                <tr>
                    <td>${s.date}</td>
                    <td>${s.time}</td>
                    <td>${s.ip}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <div class="images-list">
        <p class="label">Images</p>
        <table>
            <tr>
                <th>File name</th>
                <th>Size</th>
                <th>MIME</th>
            </tr>
            <tr>
                <td><a href="https://www.iguides.ru/upload/medialibrary/9f8/9f8fdff471b7d281f81f694c100b5adc.png">avatar.jpeg</a></td>
                <td>500KB</td>
                <td>image/jpeg</td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>
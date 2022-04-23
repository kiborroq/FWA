<%@ page import="edu.school21.cinema.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.school21.cinema.models.ImageFile" %>
<%@ page import="edu.school21.cinema.models.UserSession" %>
<%@ page import="edu.school21.cinema.services.SessionService" %>
<%@ page import="java.time.LocalDateTime" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% User user = (User) request.getSession().getAttribute("user"); %>
<% List<ImageFile> imageFiles = (List<ImageFile>) request.getSession().getAttribute("files"); %>
<% List<SessionService.SessionDto> userSessions = (List<SessionService.SessionDto>) request.getSession().getAttribute("sessions"); %>
<!DOCTYPE html>
<html>
<script>
    function triggerInput() {
        document.getElementById('inputFile').click();
    }
</script>
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

    .upload-form {
        width: 198px;
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
        height:175px;
        width: 198px;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 1px dashed #5237d5;
        border-radius: 5px;
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
            <img id="fileSelect" onclick="triggerInput()" class="avatar" src="
            <%=
                user.getAvatar() != null
                    ? "/cinema/images/" + user.getAvatar().getUuid()
                    : "https://www.pngitem.com/pimgs/m/421-4213053_default-avatar-icon-hd-png-download.png"
            %>">
            <form class="upload-form" action="/cinema/images" method="POST" enctype="multipart/form-data">
                <input id="inputFile" style="display: none" type="file" name="fileToUpload">
                <input type="submit" class="uploadbtn" value="Upload">
            </form>
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
            <c:forEach var="s" items="<%= userSessions %>">
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
            <c:forEach var="f" items="<%= imageFiles %>">
                <tr>
                    <td><a href="/cinema/images/${f.uuid.toString()}">${f.name}</a></td>
                    <td>${f.size}</td>
                    <td>${f.mime}</td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
</body>
</html>
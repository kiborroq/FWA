<%@ page import="edu.school21.cinema.models.User" %>
<%@ page import="java.util.List" %>
<%@ page import="edu.school21.cinema.models.ImageFile" %>
<%@ page import="edu.school21.cinema.services.SessionService" %>
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

    function inputImage(event) {
        if (event.target.files[0]) {
            document.getElementById('image').src=URL.createObjectURL(event.target.files[0]);
            document.getElementById("add-icon").style.display = "none";
            document.getElementById("uploadbtn").disabled = false;
        }
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
        padding: 15px;
        width: 700px;
        height: 100%;
        display: flex;
        flex-direction: column;
        margin: auto auto 50px;
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

    .image-preview {
        height:175px;
        width: 194px;
        cursor: pointer;
        display: flex;
        align-items: center;
        justify-content: center;
        border: 2px dashed #5237d5;
        border-radius: 10px;
    }

    .image {
        max-width: 194px;
        max-height: 175px;
        border-radius: inherit;
    }

    .add-icon {
        font-size: 40pt;
        color: #5237d5;
    }

    .add-icon:hover {
        font-size: 60pt;
    }

    .info-key {
        margin-right: 10px;
        min-width: 100px;
    }

    .label {
        color: #a4a4a4;
        margin: 2px;
        font-size: 13pt;
    }

    .table-label {
        color: #5237d5;
        margin: 2px;
        font-size: 13pt;
        text-align: left;
    }

    .value {
        margin: 2px;
    }

    .sessions-list {
        margin-top: 40px;
        display: flex;
        flex-direction: column;
    }

    .images-list {
        margin-top: 30px;
        display: flex;
        flex-direction: column;
    }

    table {
        font-size: 10pt;
        border-collapse: collapse;
    }

    hr {
        height: 2px;
        background-color: #5237d5;
        border: none;
        width: 100%;
        padding: 0;
        margin: 0 0 15px;
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
            <div id="fileSelect" onclick="triggerInput()" class="image-preview">
                <i id="add-icon" class="add-icon">+</i>
                <img id="image" class="image" alt="" src="">
            </div>
            <form class="upload-form" action="/cinema/images" method="POST" enctype="multipart/form-data">
                <input id="inputFile" style="display: none" type="file" name="fileToUpload" onchange="inputImage(event)" accept="image/*">
                <input id="uploadbtn" type="submit" class="uploadbtn" value="Upload" disabled>
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
        <p class="table-label">Sessions</p>
        <hr>
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
        <p class="table-label">Images</p>
        <hr>
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
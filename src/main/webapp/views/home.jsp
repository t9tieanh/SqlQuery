<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>SQL Gateway - Dark Mode</title>
  <!-- Bootstrap CSS -->
<%--  <link href="<c:url value='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css'/>" rel="stylesheet">--%>
  <link rel="stylesheet" href="<c:url value='/template/css/style.css' />" />
  <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/3.10.2/mdb.min.css" rel="stylesheet" />
  <link rel="stylesheet" href="<c:url value='/template/css/header.css' />" />
  <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
  <style>
    body {
      background-size: 100% 100%;
      background-image: url("<c:url value='/template/media/richard-stachmann-xSI9HVPmYeE-unsplash.jpg' />");
    }
    .container {
      background-color: #ffffff;
      border-radius: 8px;
      padding: 30px;
      margin-top: 100px;
      /*box-shadow: 0px 4px 12px rgb(255, 255, 255);*/
    }
    .alert-success {
      background-color: #0c4f8f;
      color: #ffffff;
    }
  </style>
  <script>
    // Hàm để tạo câu lệnh SQL dựa trên các giá trị đầu vào
    function updateSQLPreview() {
      var command = document.getElementById("command").value;
      var userId = document.getElementById("userId").value;
      var email = document.getElementById("email").value;
      var firstName = document.getElementById("firstName").value;
      var lastName = document.getElementById("lastName").value;

      var sqlPreview = "";

      // Xây dựng câu lệnh SQL cho từng trường hợp
      if (command === "insert") {
        sqlPreview = "INSERT INTO user (email, firstName, lastName) VALUES ('" + email + "', '" + firstName + "', '" + lastName + "');";
      } else if (command === "update") {
        sqlPreview = "UPDATE user SET email = '" + email + "', firstName = '" + firstName + "', lastName = '" + lastName + "' WHERE id = '" + userId + "';";
      } else if (command === "select") {
        sqlPreview = "SELECT * FROM user;";
      }

      // Cập nhật câu lệnh SQL hiển thị
      document.getElementById("sqlPreview").innerText = sqlPreview;
    }

    // Hàm để thay đổi các trường hiển thị dựa trên lệnh SQL
    function toggleFields() {
      var command = document.getElementById("command").value;
      var userIdField = document.getElementById("userIdField");
      var inputFields = document.querySelectorAll(".input-field");

      // Chỉ hiển thị trường UserID khi chọn lệnh "update"
      if (command === "update") {
        userIdField.style.display = "block";
      } else {
        userIdField.style.display = "none";
      }

      // Chỉ ẩn các trường input khi chọn lệnh "select"
      if (command === "select") {
        inputFields.forEach(function(field) {
          field.style.display = "none";
        });
      } else {
        inputFields.forEach(function(field) {
          field.style.display = "block";
        });
      }

      // Cập nhật trước câu lệnh SQL
      updateSQLPreview();
    }

    document.getElementById("command").addEventListener('change', toggleFields);
  </script>

</head>
<body>

<%@ include file="/common/header.jsp" %>


<div class="container">
  <h1 class="text-center mb-4">The SQL Gateway</h1>

  <div class="row">
      <div class="card">
        <div class="card-body">
          <form action="${pageContext.request.contextPath}/home" method="post">
            <!-- Thanh chọn lệnh SQL -->
            <div class="mb-3">
              <label for="command" class="form-label">SQL command:</label>
              <select class="form-select" name="command" id="command" onchange="toggleFields()">
                <option value="insert">Insert</option>
                <option value="update">update from user</option>
                <option value="select">Select * from user</option>
              </select>
            </div>

            <!-- UserID cho lệnh UPDATE -->
            <div class="mb-3" id="userIdField" style="display: none;">
              <label for="userId" class="form-label">UserID:</label>
              <input type="text" class="form-control" name="userId" id="userId" placeholder="Enter UserID for update" oninput="updateSQLPreview()">
            </div>

            <!-- Các trường thuộc tính khác -->
            <div class="mb-3 input-field">
              <label for="email" class="form-label">Email:</label>
              <input type="email" class="form-control" name="email" id="email" placeholder="Enter email" oninput="updateSQLPreview()">
            </div>
            <div class="mb-3 input-field">
              <label for="firstName" class="form-label">FirstName:</label>
              <input type="text" class="form-control" name="firstName" id="firstName" placeholder="Enter first name" oninput="updateSQLPreview()">
            </div>
            <div class="mb-3 input-field">
              <label for="lastName" class="form-label">LastName:</label>
              <input type="text" class="form-control" name="lastName" id="lastName" placeholder="Enter last name" oninput="updateSQLPreview()">
            </div>

            <div class="mt-4" style="margin-bottom: 10px">
              <h5>Preview SQL command:</h5>
              <div id="sqlPreview" style="white-space: pre-wrap; background-color: #f8f9fa; padding: 10px; border-radius: 5px;">
                -- SQL command preview will appear here --
              </div>
            </div>

            <button type="submit" class="btn btn-primary">Execute</button>
          </form>
        </div>
      </div>

      <div class="mt-4">
        <h5>SQL result:</h5>
        <div class="alert alert-success" role="alert">
          <c:if test="${not empty resultMessage}">${resultMessage}</c:if> <!-- Hiển thị thông báo -->
        </div>

        <!-- Table to display data -->
        <table class="table table-bordered mt-3">
          <thead>
          <tr>
            <th>UserID</th>
            <th>Email</th>
            <th>FirstName</th>
            <th>LastName</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach var="user" items="${users}">
            <tr>
              <td>${user.id}</td>
              <td>${user.email}</td>
              <td>${user.firstName}</td>
              <td>${user.lastName}</td>
            </tr>
          </c:forEach>
          </tbody>
        </table>
      </div>
    </div>
</div>



<!-- Bootstrap JS (Optional) -->
<script src="<c:url value='https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/js/bootstrap.bundle.min.js'/>"></script>
</body>
</html>

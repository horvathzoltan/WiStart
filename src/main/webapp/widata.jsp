<%--
  Created by IntelliJ IDEA.
  User: pdomokos
  Date: 2/16/17
  Time: 2:25 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <title>Getting Started: Handling Form Submission</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel='stylesheet' href='/webjars/jquery-ui/1.12.1/jquery-ui.min.css'>
    <link rel='stylesheet' href='/webjars/bootstrap/3.3.7/css/bootstrap.min.css'>
    <script src="/webjars/jquery/3.1.1/jquery.min.js"></script>
    <script src="/webjars/jquery-ui/1.12.1/jquery-ui.min.js"></script>
    <script src="/webjars/bootstrap/3.3.7/js/bootstrap.min.js"></script>

</head>
<body>
<h1>Form</h1>

<form:form method="POST" action="/widata">
    <table>
        <tr>
            <td><form:label path="interval">Interval</form:label></td>
            <td><form:select path="interval" items="${selectList}"/></td>
        </tr>
        <tr>
            <td><form:label path="userIds">Users</form:label></td>
            <td><form:select path="userIds" items="${userList}" multiple="true"/></td>
        </tr>
        <tr>

            <td><form:label path="dateInterest">Date</form:label></td>
            <td><div id="embeddingDatePicker"></div></td>
            <td><form:input path="dateInterest" type="hidden" id="selectedDate" name="selectedDate"/></td>

        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Submit"/>
            </td>
        </tr>
    </table>
</form:form>


<script>
    $(document).ready(function() {
        $("#selectedDate").val(new Date());
        $('#embeddingDatePicker')
            .datepicker({
                format: 'yyyy-mm-dd'
            }).change(function() {
                console.log("change");
                $("#selectedDate").val($('#embeddingDatePicker').datepicker().val());
            });

    });
</script>
</body>
</html>

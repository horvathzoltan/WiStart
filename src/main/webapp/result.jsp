<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Getting Started: Handling Form Submission</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
<h1>Result</h1>

<table>
    <tr>
        <td>Interval</td>
        <td>${interval}</td>
    </tr>
    <tr>
        <td>userIds</td>
        <c:forEach items="${userIds}" var="myItem" varStatus="myItemStat">
            <td>${myItem}</td>
        </c:forEach>
    </tr>
    <tr>
        <td>Date</td>
        <td>${dateInterest}</td>
    </tr>
    <tr>
        <td>data</td>
        <td>${oneDayWiData}</td>
    </tr>

</table>
<a href="/widata">Submit another query</a>
<div id="curve_chart" style="width: 900px; height: 500px"></div>

<%--<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>--%>
<%--<script type="text/javascript">--%>
    <%--// Load the Visualization API and the piechart package.--%>
    <%--google.charts.load('current', {'packages':['corechart']});--%>

    <%--// Set a callback to run when the Google Visualization API is loaded.--%>
    <%--google.charts.setOnLoadCallback(drawChart);--%>

    <%--// Callback that creates and populates a data table,--%>
    <%--// instantiates the pie chart, passes in the data and--%>
    <%--// draws it.--%>
    <%--function drawChart() {--%>

        <%--var data = google.visualization.arrayToDataTable(...);--%>

        <%--var options = {--%>
            <%--title: 'Step chart',--%>
            <%--curveType: 'function',--%>
            <%--legend: { position: 'bottom' }--%>
        <%--};--%>

        <%--var chart = new google.visualization.LineChart(document.getElementById('curve_chart'));--%>

        <%--chart.draw(data, options);--%>
    <%--}--%>



<%--</script>--%>
</body>
</html>

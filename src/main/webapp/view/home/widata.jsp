<div>
    <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
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
                <td>
                    <div id="embeddingDatePicker"></div>
                </td>
                <td><form:input path="dateInterest" type="hidden" id="selectedDate" name="selectedDate"/></td>

            </tr>
            <tr>
                <td colspan="2">
                    <input type="submit" value="Submit"/>
                </td>
            </tr>
        </table>
    </form:form>
</div>

<script>
    $(document).ready(function () {
        $("#selectedDate").val(new Date());
        $('#embeddingDatePicker')
            .datepicker({
                format: 'yyyy-mm-dd'
            }).change(function () {
            console.log("change");
            $("#selectedDate").val($('#embeddingDatePicker').datepicker().val());
        });

    });
</script>


<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <table border="2px">
        <tr>
            <td>作者</td>
            <td>古诗名</td>
            <td>内容</td>
        </tr>
        <c:forEach items="${requestScope.poetries}" var="poety">
            <tr>
                <td>${poety.poet.name}</td>
                <td>${poety.title}</td>
                <td>${poety.content}</td>
            </tr>
        </c:forEach>
        <tr>
            <td>
                <c:if test="${requestScope.nowPage>1}">
                <a href="${pageContext.request.contextPath}/poetries/showAll?nowPage=${requestScope.nowPage-1}&text=${requestScope.text}">上一页</a>
                </c:if>
            </td>
            <td>总页数:${requestScope.endPage}</td>
            <td>
                <c:if test="${requestScope.endPage!=requestScope.nowPage}">
                <a href="${pageContext.request.contextPath}/poetries/showAll?nowPage=${requestScope.nowPage+1}&text=${requestScope.text}">下一页</a>
                </c:if>
            </td>

        </tr>
    </table>
</body>
</html>
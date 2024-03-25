<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ジャンル選択</title>
</head>
<body>
    <h1>ジャンル選択</h1>
    <form action="QuizServlet" method="get">
        <select name="genreId">
            <c:forEach items="${genres}" var="genre">
                <option value="${genre.id}">${genre.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="開始">
    </form>
</body>
</html>


<%--
参考：JSTL タグライブラリ
https://workteria.forward-soft.co.jp/blog/detail/10069#anchor_4
https://qiita.com/sculptcat/items/53d1a3a2d3b973354085
 --%>
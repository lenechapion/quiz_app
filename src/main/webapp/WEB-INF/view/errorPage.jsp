<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>エラーページ</title>
</head>
<body>
	<h1>エラーが発生しました</h1>
	<p>処理中にエラーが発生しました！</p>
	<%
	String errorMessage = (String) request.getAttribute("errorMessage");
	if (errorMessage != null && !errorMessage.isEmpty()) {
	%>
	<p>
		エラーメッセージ
		<%=errorMessage%></p>
	<%
	}
	%>
	<a href="<%=request.getContextPath()%>/QuizServlet?reset=true">最初に戻る</a>
	<br>
</body>
</html>
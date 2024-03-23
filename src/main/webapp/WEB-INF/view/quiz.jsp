<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Question"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>クイズ</title>
</head>
<body>
	<h1>クイズ</h1>
	<%
	Question question = (Question) request.getAttribute("question");
	if (question != null) {
	%>
	<form action="QuizServlet" method="post">
		<p><%= question.getQuestion()%></p>
		<input type="radio" name="answer" value="<%=question.getOption1()%>"><%=question.getOption1()%><br>
		<input type="radio" name="answer" value="<%=question.getOption2()%>"><%=question.getOption2()%><br>
		<input type="radio" name="answer" value="<%=question.getOption3()%>"><%=question.getOption3()%><br>
		<input type="hidden" name="correctAnswer"value="<%= question.getAnswer() %>">
		<input type="submit" value="回答">
	</form>
	<% } else {%>
	<p>問題の取得に失敗しました</p>
	<%}%>
</body>
</html>

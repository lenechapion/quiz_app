<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Question"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>クイズ</title>
<script>
	function validateForm() {
		var radios = document.getElementsByName("answer");
		var formValid = false;

		var i = 0;
		while (!formValid && i < radios.length) {
			if (radios[i].checked)
				formValid = true;
			i++;
		}

		if (!formValid)
			alert("選択肢を一つ選んでください。");
		return formValid;
	}
</script>
</head>
<body>
	<h1>
		Q.<%=session.getAttribute("index") != null ? (Integer) session.getAttribute("index") + 1 : 1%></h1>
	<%
	Question question = (Question) request.getAttribute("question");
	if (question != null) {
	%>
	<form action="QuizServlet" method="post"
		onsubmit="return validateForm();">
		<p>問題：<%=question.getQuestion()%></p><br>
		<input type="radio" name="answer" value="<%=question.getOption1()%>"><%=question.getOption1()%><br>
		<input type="radio" name="answer" value="<%=question.getOption2()%>"><%=question.getOption2()%><br>
		<input type="radio" name="answer" value="<%=question.getOption3()%>"><%=question.getOption3()%><br>
		<input type="submit" value="回答"><br>
		<p><a href="#" onclick="alert('<%=question.getHint() %>'); return false;">ヒントを見る</a><br>
		<p></p>
		<a href="QuizServlet">ジャンル選択に戻る</a>
	</form>
	<%
	} else {
	%>
	<p>問題の取得に失敗しました。</p>
	<%
	}
	%>
</body>
</html>

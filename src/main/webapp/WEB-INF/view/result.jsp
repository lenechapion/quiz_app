<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>クイズ結果</title>
</head>
<body>
	<h1>クイズ結果</h1>
	<%-- ここでクイズの結果を表示 --%>
	<p>お疲れさまでした！クイズの結果は・・・</p>
	<%-- 例えば、セッションからスコアや正解数を取得して表示 --%>
	<% HttpSession session = request.getSession();
        Integer score = (Integer) session.getAttribute("score");
        if (score == null) { score = 0; }
        out.println("<p>正解数: " + score + "</p>");
    %>
	<a href="QuizServlet">クイズを再開始する</a>
</body>
</html>


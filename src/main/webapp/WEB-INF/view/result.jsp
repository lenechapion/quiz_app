<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.List" %>
	<%@ page import="model.Question" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>クイズ結果</title>
</head>
<body>
	<h1>結果</h1>
	<%
	// スコアの取得と初期化
	Integer score = (Integer) request.getSession().getAttribute("score");
	if (score == null) {
		score = 0;
	}
	// 質問リストの取得
	List<Question> questions = (List<Question>) request.getSession().getAttribute("questions");
	int totalQuestions = questions != null ? questions.size() : 0; // nullチェック
	%>
	<%--<Question>型にキャストでsize()呼び出しがエラー（ー） --%>
	
	<p>お疲れさまでした！</p>
	<p>あなたのスコアは<%= score %> / <%= totalQuestions %>です！</p>
	
	<a href="QuizServlet?reset=true">クイズを再開始する</a>
</body>
</html>
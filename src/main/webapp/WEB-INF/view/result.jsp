<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ page import="java.util.List" %>
	<%@ page import="model.Question" %>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>クイズ結果</title>
<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color:#EEFFFF;
        text-align: center;
        padding: 20px;
    }
    h1 {
        color: #333;
    }
    p {
        font-size: 18px;
        color: #666;
    }
    a {
        display: inline-block;
        background-color: #4CAF50;
        color: white;
        padding: 10px 20px;
        text-decoration: none;
        border-radius: 5px;
        font-weight: bold;
    }
    a:hover {
        background-color: #45a049;
    }
</style>
</head>
<body>
	<h1>★結果★</h1>
	<%
	// スコアの取得と初期化
	Integer score = (Integer) request.getSession().getAttribute("score");
	if (score == null) {
		score = 0;
	}
	// 問題の取得
	List<Question> questions = (List<Question>) request.getSession().getAttribute("questions");
	int totalQuestions = questions != null ? questions.size() : 0; // nullチェック
	%>
	<%--<Question>型にキャストでsize()呼び出しがエラー（ー） --%>
	
	<p>お疲れさまでした！</p></br>
	<p>結果は <%= totalQuestions %>問中、<%= score %>問正解でした！(ﾟ∀ﾟﾉﾉ"☆ﾊﾟﾁﾊﾟﾁﾊﾟﾁ</p></br>
	<p>他ジャンルも遊んでみてね！</p>
	<a href="QuizServlet">ジャンル選択に戻る</a></br>
		

	
	
	
</body>
</html>
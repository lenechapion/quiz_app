<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="model.Question"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>回答結果</title>
<style>
    body {
        font-family: 'Arial', sans-serif;
        background-color: #EEFFFF;
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
    
    <%
    Boolean isCorrect = (Boolean) session.getAttribute("isCorrect");
    String selectedAnswer = (String) session.getAttribute("selectedAnswer");
    Question currentQuestion = (Question) session.getAttribute("currentQuestion");
    List<Question> questions = (List<Question>) session.getAttribute("questions");
    Integer index = (Integer) session.getAttribute("index");
    %>

    <h1><% if (Boolean.TRUE.equals(isCorrect)) { %>
        <p>正解です！</p>
    <% } else { %>
        <p>残念…不正解です！</p>
    <% } %>
	</h1>
    <p>あなたの選択: <%= selectedAnswer %></p>
    <p>正解: <%= currentQuestion.getAnswer() %></p>
	<p>解説: <%= currentQuestion.getCommentary() %></p>
    <p>問題: <%= currentQuestion.getQuestion() %></p>
    

    <form action="QuizServlet" method="post">
        <% if (index < questions.size() - 1) { %>
            <input type="hidden" name="nextAction" value="nextQuestion">
        <% } else { %>
            <input type="hidden" name="nextAction" value="showResult">
        <% } %>
        <input type="submit" value="次へ">
    </form>
</body>
</html>

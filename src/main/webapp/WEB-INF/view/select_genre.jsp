<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ジャンル選択</title>
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
    <h1>クイズ❔っぽいやつ</h1>
    
    <p>クイズの出題ジャンルを選択してください</p>
    <p>"開始"ボタンでジャンルに沿った問題(固定)が全１０問出題されます！</p>
    <p>３つの選択肢から"答えを１つ"選択して下さい。</p><br>
    <p>"ヒントを見る"というモノがありますが、基本ヒントの役割を果たしていません( ◜◡◝ )</p>
    <p>※高難度問題は全問ヒントあり※</p>
    <h2>ジャンル選択</h2>
    <form action="QuizServlet" method="get">
        <select name="genreId">
            <c:forEach items="${genres}" var="genre">
                <option value="${genre.id}">${genre.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="開始"><br>

        
<p>　　　　　　　,-､ 　　　　　　　　　,.-､</p>
<p>　　　　　　./:::::＼　 　　　　　 ／::::::ヽ</p>
<p>　　　　/::::::::::::;ゝ--───--､._/::::::::::::::| </p>
<p>　　　　 /,.-‐''"´ 　　　　　　　　 ＼:::::::::::|</p> 
<p>　　　　　／　 　　　　　　　　　　　　ヽ､::::| </p>
<p>　　　　/　　　　●　　　 　 　 　 　 　 　 　 ヽ| </p>
<p>　　 　 l　　　, , ,　　 　 　 　 　 　 ●　　　 　 l </p>
<p>　　　　　　　 .|　　　 　　　　(_人__丿　　　　　､､､　　|　　　　</p>
<p>　 　 　l　　　　　　　　　　　　　　　　　　　 　 l </p>
<p>　　　　` ､　　　　　　　　 　 　 　 　 　 　 　 / </p>
<p>　　　　　　`ｰ ､__　　　　　　　　　　　　／ </p>
<p>　　　　　　　　　/`'''ｰ‐‐──‐‐‐┬'''""´</p>
        
        
        
</form>
</body>
</html>
<%-- 
c:forEach[ループ]
var[] 
--%>
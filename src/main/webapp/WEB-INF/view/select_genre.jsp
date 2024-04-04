<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>ジャンル選択</title>
</head>
<body>
    <h1>クイズ❔</h1>
    
    <p>クイズのジャンルを選択してください</p>
    <p>"開始"ボタンでジャンルに沿った問題が全１０問出題されます！</p>
    <p>３つの選択肢から"答えを１つ"選択して下さい。</p><br>
    <p>"ヒントを見る"というモノがありますが、基本ヒントらしさはありません( ◜◡◝ )</p>
    <p>（高難度ジャンルは全問ヒントあり。）</p>
    <h2>ジャンル選択</h2>
    <form action="QuizServlet" method="get">
        <select name="genreId">
            <c:forEach items="${genres}" var="genre">
                <option value="${genre.id}">${genre.name}</option>
            </c:forEach>
        </select>
        <input type="submit" value="開始"><br>
        
        
    </form>
</body>
</html>


<%-- 
c:forEach[ループ]
var[] 
--%>

<%--
参考：JSTL タグライブラリ
https://workteria.forward-soft.co.jp/blog/detail/10069#anchor_4
https://qiita.com/sculptcat/items/53d1a3a2d3b973354085
 --%>
package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.QuestionDAO;
import model.Question;

/**
 * Servlet implementation class QuizServlet
 */
@WebServlet("/QuizServlet")
public class QuizServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuizServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		if (session.getAttribute("questions") == null) {
			List<Question> questions = QuestionDAO.getAllQuestion();
			session.setAttribute("questions", questions);
			session.setAttribute("index", 0);
		}

		Integer index = (Integer) session.getAttribute("index");
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		if (index < questions.size()) {
			request.setAttribute("question", questions.get(index));
			RequestDispatcher rd = request.getRequestDispatcher("quiz.jsp");
			rd.forward(request, response);

//		} else {
//			//全ての問題が終わった後の結果表示などの処理
//			response.sendRedirect("result.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Integer index = (Integer) session.getAttribute("questions");
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		//回答のチェック,index取得
		String selectedAnswer = request.getParameter("answer");
		Question currentQuestion = questions.get(index); //current(現在)
		//正解数取得・初期化
		Integer score = (Integer) session.getAttribute("score");
		if (score == null) {
			score = 0; //スコアがなければ０で初期化
		}
		//選択した回答が正解かチェック
		if (selectedAnswer != null && selectedAnswer.equals(currentQuestion.getAnswer())) {
			score++;//正解数をインクリメント
			session.setAttribute("score", score);//更新されたスコアをセッションに保存
		}
		//次の問題の表示、またはクイズ終了処理
		index++;
		if (index < questions.size()) {
			//まだ問題が残ってる場合は、indexを更新
			session.setAttribute("index", index);
			doGet(request, response); //次の問題を表示する
		} else {
			//全問回答後、結果に移る
			response.sendRedirect("result.jsp");
		}

	}

}
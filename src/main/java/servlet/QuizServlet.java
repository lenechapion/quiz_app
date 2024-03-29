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

import dao.GenreDAO;
import dao.QuestionDAO;
import model.Genre;
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
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String reset = request.getParameter("reset");
		String genreIdString = request.getParameter("genreId");//ジャンルID
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		Integer index = (Integer) session.getAttribute("index");

		//次へを押した処理（仮修正版）
		if ("true".equals(reset) && questions != null && index != null) {
			if (index < questions.size() - 1) {
				//次の問題がある場合、インデックスを更新して問題を表示
				session.setAttribute("index", index + 1);
				Question question = questions.get(index + 1);
				request.setAttribute("question", question);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
				rd.forward(request, response);
				return;
			} else {//全ての問題に回答した場合、結果画面へ
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/result.jsp");
				rd.forward(request, response);
				return;
			}

		} else {
			//ジャンル選択画面に遷移させる条件
			if (genreIdString == null || "true".equals(reset)) {
				//ジャンルリストをDBから取得してリクエストスコープにセット
				List<Genre> genres = GenreDAO.getAllGenre();
				request.setAttribute("genres", genres);

				//ジャンル選択画面にフォワード
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/select_genre.jsp");
				rd.forward(request, response);
				return;//メソッド処理終了
			}

			//ジャンル選択に基づく問題をフィルタリング
			try {
				int genreId = Integer.parseInt(genreIdString);
				List<Question> questionsByGenre = QuestionDAO.getQuestionsByGenre(genreId);

				session.setAttribute("questions", questionsByGenre);
				session.setAttribute("index", 0);//インデックスリセット
				session.setAttribute("score", 0);//スコアリセット
			} catch (NumberFormatException e) {
				e.printStackTrace();
				return;
			}

		}

		//最終問題の回答後、結果ページへ遷移する処理
		Boolean finalAnswerGiven = (Boolean) session.getAttribute("finalAnswerGiven");
		if (Boolean.TRUE.equals(finalAnswerGiven)) {
			session.removeAttribute("finalAnswerGiven");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/result.jsp");
			rd.forward(request, response);

		}

		//リセットパラメータがtrue、または最初のアクセス時(ジャンルIDが指定されていない場合)
		if ("true".equals(reset)) {
			session.invalidate();//セッションを無効化
			session = request.getSession(true);//新しいセッション
		}

		//ジャンル選択に基づく問題のフィルタリング
		try {
			int genreId = Integer.parseInt(genreIdString);
			List<Question> questionsByGenre = QuestionDAO.getQuestionsByGenre(genreId);
			if (!questionsByGenre.isEmpty()) {
				session.setAttribute("questions", questionsByGenre);
				session.setAttribute("index", 0);
				session.setAttribute("score", 0);
				request.setAttribute("question", questionsByGenre.get(0));
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
				rd.forward(request, response);
				return;
			} else {
				request.setAttribute("errorMessage", "選択ジャンルにクイズが存在しません");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/select_genre.jsp");
				rd.forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorPage", "エラー");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/errorPage.jsp");
			rd.forward(request, response);
			return;

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		Integer index = (Integer) session.getAttribute("index");
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		//回答のチェック,index取得
		String nextAction = request.getParameter("nextAction");
		Question currentQuestion = questions.get(index); //current(現在)
		String selectedAnswer = request.getParameter("answer");

		if ("nextQuestion".equals(nextAction) || "showResult".equals(nextAction)) {
			if (index < questions.size() - 1) {
				session.setAttribute("index", ++index);
				Question nextQuestion = questions.get(index);
				request.setAttribute("question", nextQuestion);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
				rd.forward(request, response);
			} else {
				session.setAttribute("finalAnswerGiven", true);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/result.jsp");
				rd.forward(request, response);
			}
			return;
		}

		boolean isCorrect = selectedAnswer != null && selectedAnswer.equals(currentQuestion.getAnswer());
		session.setAttribute("isCorrect", isCorrect);
		session.setAttribute("selectedAnswer", selectedAnswer);
		session.setAttribute("currentQuestion", currentQuestion);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/answer.jsp");
		rd.forward(request, response);

	}

}
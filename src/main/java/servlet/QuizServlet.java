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
		String genreIdString = request.getParameter("genreId"); // リクエストからジャンルIDを取得
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		Integer index = (Integer) session.getAttribute("index");

		// 「次へ」ボタンを押したときの
		if ("true".equals(reset) && questions != null && index != null) {
			if (index < questions.size() - 1) {
				session.setAttribute("index", index + 1);
				Question question = questions.get(index + 1);
				request.setAttribute("question", question);
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
				rd.forward(request, response);
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/result.jsp");
				rd.forward(request, response);
			}
			return;
		}

		// ジャンル選択画面への遷移、またはクイズのリセットを行う(条件分岐)
		if (genreIdString == null || "true".equals(reset)) {
			List<Genre> genres = GenreDAO.getAllGenre();
			request.setAttribute("genres", genres);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/select_genre.jsp");
			rd.forward(request, response);
		} else {
			// 選択ジャンルに基づいてクイズを開始する処理
			initializeQuizForSelectedGenre(session, request, response, genreIdString);
		}

		// クイズ終了後に結果画面へ遷移する処理
		if (Boolean.TRUE.equals(session.getAttribute("finalAnswerGiven"))) {
			session.removeAttribute("finalAnswerGiven");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/result.jsp");
			rd.forward(request, response);
		}

		// セッションのリセット
		if ("true".equals(reset)) {
			session.invalidate();
			session = request.getSession(true);
		}
	}

	// 選択されたジャンルに基づいてクイズを開始する処理
	private void initializeQuizForSelectedGenre(HttpSession session, HttpServletRequest request,
			HttpServletResponse response, String genreIdString) throws ServletException, IOException {
		try {
			int genreId = Integer.parseInt(genreIdString);
			List<Question> questionsByGenre = QuestionDAO.getQuestionsByGenre(genreId);
			if (questionsByGenre.isEmpty()) {
				request.setAttribute("errorMessage", "選択したジャンルにクイズが存在しません。");
				RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/select_genre.jsp");
				rd.forward(request, response);
				return;
			}
			session.setAttribute("questions", questionsByGenre);
			session.setAttribute("index", 0); // 問題のインデックスをリセット
			session.setAttribute("score", 0); // スコアをリセット
			request.setAttribute("question", questionsByGenre.get(0)); // 最初の問題をセット
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
			rd.forward(request, response);
		} catch (NumberFormatException e) {
			e.printStackTrace();
			request.setAttribute("errorPage", "エラー");
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/errorPage.jsp");
			rd.forward(request, response);
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

		//次の問題に移るか、クイズを終了するか
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
		//回答が正しいかどうか
		boolean isCorrect = selectedAnswer != null && selectedAnswer.equals(currentQuestion.getAnswer());
		session.setAttribute("isCorrect", isCorrect);

		//正解の場合のみスコアをインクリメント
		if (isCorrect) {
			Integer score = (Integer) session.getAttribute("score");
			if (score == null) {
				score = 0;//スコアが未設定の場合は０で初期化
			}
			score++;
			session.setAttribute("score", score);//更新されたスコアをセッションに保存
		}

		//回答と現在の問題をセッションに保存
		session.setAttribute("selectedAnswer", selectedAnswer);
		session.setAttribute("currentQuestion", currentQuestion);

		//回答結果のページにフォワード
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/answer.jsp");
		rd.forward(request, response);

	}

}
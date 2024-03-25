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

		//リセットパラメータがtrue、または最初のアクセス時(ジャンルIDが指定されていない場合)
		if ("true".equals(reset) || genreIdString == null) {
			if ("true".equals(reset)) {
				session.invalidate();//セッションを無効化
				session = request.getSession(true);//新しいセッション
			}
			//ジャンルリストをDBから取得してリクエストスコープにセット
			List<Genre>genres = GenreDAO.getAllGenre();
			request.setAttribute("genres",genres);
			
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
			
		Integer index = (Integer) session.getAttribute("index");
		List<Question> questions = (List<Question>) session.getAttribute("questions");
		//次の問題へ
		if (index < questions.size()) {
			Question question = questions.get(index);
			request.setAttribute("question", question);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
			rd.forward(request, response);
		} else {//全問終了後、結果へ
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/result.jsp");
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
		String selectedAnswer = request.getParameter("answer");
		Question currentQuestion = questions.get(index); //current(現在)
		//正解数取得or初期化
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
			//まだ問題が残ってる場合は、indexを更新、次の問題を表示
			session.setAttribute("index", index);
			doGet(request, response); //次の問題を表示する
		} else {
			//全問回答後、結果ページに
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/view/result.jsp");
			rd.forward(request, response);
		}

	}

}
package model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dao.QuestionDAO;

public class QuestionService {
	    public static List<Question> getWeightedRandomQuestions(int genreId, int userId) {
	        List<Question> questions = QuestionDAO.getQuestionsByGenre(genreId);
	        Map<Question, Integer> weightedQuestions = new HashMap<>();
	        
	        // 正解履歴を基に重み付けを行う
	        for (Question question : questions) {
	            int weight = getWeightBasedOnCorrectHistory(userId, question.getId());
	            weightedQuestions.put(question, weight);
	        }

	        // 重みに基づいて問題をランダムに選出するロジックを実装
	        List<Question> selectedQuestions = selectQuestionsBasedOnWeight(weightedQuestions);

	        return selectedQuestions;
	    }
	    
	    private static int getWeightBasedOnCorrectHistory(int userId, int questionId) {
	        // 正解履歴を参照して問題を計算するロジックを実装
	        return 1; // 仮実装
	    }
	    
	    private static List<Question> selectQuestionsBasedOnWeight(Map<Question, Integer> weightedQuestions) {
	        // 重みに基づいて問題を選出するロジックを実装
	        return new ArrayList<>(weightedQuestions.keySet()); // 仮実装
	    }
	}	
//    public static List<Question> getRandomQuestions(int genreId, List<Integer> correctQuestionIds) {
//        List<Question> questions = QuestionDAO.getQuestionsByGenre(genreId);
//        
//        // 過去に正解した問題をリストから除外（オプション）
//        questions.removeIf(question -> correctQuestionIds.contains(question.getId()));
//        
//        Collections.shuffle(questions); // リストをシャッフル
//        return questions.subList(0, Math.min(10, questions.size())); // 最初の10問を返す
//    }


package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import model.Question;

public class QuestionDAO {
	public static List<Question> getAllQuestion() {
		List<Question> questions = new ArrayList<>();
		try (Connection con = DBUtil.getConnection()) {
			if (con != null) {
				Statement stmt = con.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM questions");

				while (rs.next()) {
					Question question = new Question();
					question.setId(rs.getInt("id")); // 
					question.setQuestion(rs.getString("question"));
					question.setOption1(rs.getString("option1"));
					question.setOption2(rs.getString("option2"));
					question.setOption3(rs.getString("option3"));
					question.setAnswer(rs.getString("answer"));
					questions.add(question);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return questions;
	}
}

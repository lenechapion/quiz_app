package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import db.DBUtil;
import model.Genre;

public class GenreDAO {

	//DBから全ジャンルを取得
	public static List<Genre> getAllGenre() {
		List<Genre> genres = new ArrayList<>();
		//ジャンル情報を取得
		String sql = "SELECT * FROM genres";

		try (Connection con = DBUtil.getConnection();
				PreparedStatement stmt = con.prepareStatement(sql)) {
			ResultSet rs = stmt.executeQuery();

			// ジャンルオブジェクト生成、リストに追加
			while (rs.next()) {
				Genre genre = new Genre();
				genre.setId(rs.getInt("id"));
				genre.setName(rs.getString("name"));
				genres.add(genre);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return genres;
	}

}

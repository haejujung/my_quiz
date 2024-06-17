package com.tenco.quiz.ver3;

import java.sql.SQLException;
import java.util.List;

public class MainTest1 {

	public static void main(String[] args) {

		QuizRepositoryImpl quizRepositoryImpl = new QuizRepositoryImpl();
		try {
//			List<QuizDTO> quizDtos = quizRepositoryImpl.viewQuizQuestion();
//			for (QuizDTO quizDTO : quizDtos) {
//				System.out.println(quizDTO.toString());
//			}
//			
//			for (int i = 0; i < quizDtos.size(); i++) {
//				System.out.println(quizDtos.get(i).toString());
//			}
			
			QuizDTO dto = quizRepositoryImpl.playQuizGame();
			System.out.println(dto);
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

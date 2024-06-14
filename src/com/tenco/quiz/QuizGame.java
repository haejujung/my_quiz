package com.tenco.quiz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Result;

public class QuizGame {

	private static final String URL = "jdbc:mysql://localhost:3306/quizdb?serverTimezone=Asia/Seoul";
	private static final String USER = "root";
	private static final String PASSWORD = "asd123";

	public static void main(String[] args) {

		// JDBC 드라이버 로드 <-- 인터페이스 <-- 구현 클래스 필요

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}

		try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
				Scanner scanner = new Scanner(System.in)) {

			while (true) {
				System.out.println();
				System.out.println("------------------");
				System.out.println("1. 퀴즈 문제 추가");
				System.out.println("2. 퀴즈 문제 조회");
				System.out.println("3. 퀴즈 게임 시작");
				System.out.println("4. 종료");
				System.out.println("옵션을 선택 하세요 : ");

				int choice = scanner.nextInt(); // 블로킹

				if (choice == 1) {
					// 퀴즈 문제 추가 --> 함수로 만들기
					addQuizQuestion(conn, scanner);
				} else if (choice == 2) {
					viewQuizQuestion(conn, scanner);
				} else if (choice == 3) {
					playQuizGame(conn, scanner);

				} else if (choice == 4) {
					System.out.println("프로그램을 종료 합니다");
					break;
				} else {
					System.out.println("잘못된 선택입니다. 다시 시도하세요.");
				}

			}

		} catch (Exception e) {
		}

	} // end of main

	private static void playQuizGame(Connection conn, Scanner scanner) {
		String sql = " select * from quiz order by rand() limit 1 ";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				
				String question = rs.getString("question");
				String answer = rs.getString("answer");

				System.out.println("퀴즈 문제 " + question);
				scanner.nextLine();
				System.out.print("당신에 답: ");
				String userAnswer = scanner.nextLine();

				// 대소문자 무시하면서 문자열비교
				if (userAnswer.equalsIgnoreCase(answer)) {
					System.out.println("정답입니다 ! 점수를 얻었습니다");
				} else {
					System.out.println("오답입니다 !");
					System.out.println("퀴즈 정답은 --> " + answer);

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void viewQuizQuestion(Connection conn, Scanner scanner) {
		String sql = " select * from quiz ";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				System.out.println("문제 ID : " + resultSet.getInt("id"));
				System.out.println("문제 : " + resultSet.getString("question"));
				System.out.println("정답 : " + resultSet.getString("answer"));
				if (!resultSet.isLast()) {
					System.out.println("@@@@@@@@@@@@@@@@@@@@@");
				}
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private static void addQuizQuestion(Connection conn, Scanner scanner) {
		System.out.println("퀴즈 문제를 입력하세요 : ");
		scanner.nextLine();
		String question = scanner.nextLine();
		System.out.println("퀴즈 정답을 입력하세요 : ");
		String answer = scanner.nextLine();

		String sql = " insert into quiz(question, answer) values (?,?) ";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, question);
			pstmt.setString(2, answer);

			int rowsInsertedCount = pstmt.executeUpdate();
			System.out.println("추가된 행의 수 " + rowsInsertedCount);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	// 퀴즈를 추가하는 함수 만들기
	// 사용자 퀴즈와 답을 입력 받아야함
	// connection 을 활용해서 query 날려야 한다.

}

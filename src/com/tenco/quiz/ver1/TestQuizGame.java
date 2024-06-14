package com.tenco.quiz.ver1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.tenco.quiz.DBConnectionManager;

public class TestQuizGame {

	public static void main(String[] args) {

		try (Connection conn = DBConnectionManager.getConnection(); Scanner scanner = new Scanner(System.in)) {

			while (true) {
				System.out.println();
				System.out.println("-------------");
				System.out.println("1. 퀴즈 문제 추가하실분");
				System.out.println("2. 퀴즈 문제 조회 해보세요");
				System.out.println("3. 퀴즈 게임 시작 하세요");
				System.out.println("4. 종료 입니다");
				System.out.println("옵션을 선택 하세요 : ");

				int choice = scanner.nextInt();

				if (choice == 1) {
					addQuizQuestion(conn, scanner);
				} else if (choice == 2) {
					viewQuizQuestion(conn);
				} else if (choice == 3) {
					playQuizGame(conn, scanner);
				} else if (choice == 4) {
					System.out.println("프로그램을 종료 합니다");
					break;
				} else {
					System.out.println("잘못된 선택입니다. 다시 시도하세요.");
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void playQuizGame(Connection conn, Scanner scanner) {

		String query = "  select * from quiz order by rand() limit 1 ";
		try {
			PreparedStatement pstmt = conn.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				String question = rs.getString("question");
				String answer = rs.getString("answer");

				System.out.println("퀴즈 문제 " + question);
				scanner.nextLine();
				System.out.println("당신에 답 : ");
				String userAnswer = scanner.nextLine();

				if (userAnswer.equalsIgnoreCase(answer)) {
					System.out.println("정다빕니다!");
				} else {
					System.out.println("틀려뜹니다");
					System.out.println("정답은 : " + answer + "입니다.");
				}

			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void viewQuizQuestion(Connection conn) {
		try {
			PreparedStatement pstmt = conn.prepareStatement(" select * from quiz ");
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				System.out.println("문제 ID : " + resultSet.getInt("id"));
				System.out.println("문제 : " + resultSet.getString("question"));
				System.out.println("정답 : " + resultSet.getString("answer"));
				if (!resultSet.isLast()) {
					System.out.println("@@@@@@@@@@@@@@@@@@@@@");
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	private static void addQuizQuestion(Connection conn, Scanner scanner) {
		System.out.println(" 퀴즈 문제를 입력바람 : ");
		scanner.nextLine();
		String question = scanner.nextLine();
		System.out.println("퀴즈 정답을 맞춰주세요 : ");
		String answer = scanner.nextLine();

		try {
			PreparedStatement pstmt = conn.prepareStatement(" insert into quiz(question, answer) values (?,?) ");
			pstmt.setString(1, question);
			pstmt.setString(2, answer);

			int rowsInsertedCount = pstmt.executeUpdate();
			System.out.println("추가된 행의 수 " + rowsInsertedCount);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}

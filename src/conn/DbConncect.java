package conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import board.conn.DbConnect;

public class DbConncect {
	
private static DbConnect db = new DbConnect();
	
	private String driver = "oracle.jdbc.driver.OracleDriver";//오라클 용 드라이버 명
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";//로그인에 필요한 서버 주소 및 sid


	private void DbConnect() {
	}

	public static DbConnect getInstance() {
		return db;
	}

	public Connection conn() {
		Connection conn = null;
		try {
			Class.forName(driver);//드라이버 로드
			conn = DriverManager.getConnection(url, "hr", "xhfl120");//db서버에 접속
			System.out.println("오라클 연결 성공");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("jdbc driver 로딩 실패");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("오라클 연결 실패");
			e.printStackTrace();
		}
		return conn;
	}
	

}

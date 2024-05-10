package test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PhoneBookDAOImpl implements PhoneBookDAO {
	private Connection getConnection() throws SQLException {

		Connection conn = null;

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String dburl = "jdbc:oracle:thin:@localhost:1521:xe";

			conn = DriverManager.getConnection(dburl, "himedia", "himedia");

		} catch (ClassNotFoundException e) {
			System.err.println("JDBC 드라이버를 로드하지 못했습니다.");
		}
		return conn;
	}

	
	@Override
	public boolean createSeq() {
		Connection conn = null;
		Statement stmt = null;
		int createCount = 0;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = """
					CREATE SEQUENCE seq_phone_book START WITH 1 INCREMENT BY 1
					""";
			createCount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("시퀀스가 이미 있음");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return createCount == 0;
	}


	@Override
	public boolean seqDrop() {
		Connection conn = null;
		Statement stmt = null;
		int dropCount = 0;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = """
					DROP SEQUENCE seq_phone_book 
					""";
			dropCount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return dropCount == 0;
	}


	@Override
	public boolean tableDrop() {
		Connection conn = null;
		Statement stmt = null;
		int dropCount = 1;

		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = """
					DROP Table PHONE_BOOK 
					""";
			dropCount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return dropCount == 0;
	}


	@Override
	public boolean createTable() {
		Connection conn = null;
		Statement stmt = null;
		int createCount = 0;
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = """
					CREATE Table PHONE_BOOK (
					id NUMBER(10) DEFAULT seq_phone_book.NEXTVAL PRIMARY KEY,
					name VARCHAR2(10) NOT NULL,
					hp VARCHAR2(20),
					tel VARCHAR2(20)
					)
					""";
			createCount = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("테이블이 이미 있음");
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return createCount == 0;
	}

	@Override
	public List<PhoneBookVO> getList() {
		List<PhoneBookVO> list = new ArrayList<>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			String sql = "SELECT * FROM phone_book";
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String hp = rs.getString(3);
				String tel = rs.getString(4);
				PhoneBookVO vo = new PhoneBookVO(id, name, hp, tel);
				list.add(vo);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null)
					stmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return list;
	}

	@Override
	public List<PhoneBookVO> search(String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<PhoneBookVO> list = new ArrayList<>();
		try {
			conn = getConnection();
			String sql = "SELECT * FROM phone_book WHERE name LIKE ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + name + "%");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long id = rs.getLong(1);
				String rName = rs.getString(2);
				String hp = rs.getString(3);
				String tel = rs.getString(4);
				PhoneBookVO vo = new PhoneBookVO(id, rName, hp, tel);
				list.add(vo);
			}
		}catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
				if (rs != null)
					rs.close();
			} catch (Exception e) {
				// TODO: handle exception
			}
		} 
		return list;
	}


	@Override
	public boolean insert(PhoneBookVO phoneBookVo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertCount = 0;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO phone_book (name, hp, tel) VALUES(?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, phoneBookVo.getName());
			pstmt.setString(2, phoneBookVo.getHp());
			pstmt.setString(3, phoneBookVo.getTel());
			insertCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return insertCount == 1;
	}


	@Override
	public boolean delete(Long id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int deletCount = 0;
		try {
			conn = getConnection();
			String sql = """
					DELETE FROM phone_book
					WHERE id = ? 
					""";
			pstmt= conn.prepareStatement(sql);
			pstmt.setLong(1, id);
			deletCount = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
				if (conn != null)
					conn.close();
			
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
		return deletCount == 1 ;
	}

}

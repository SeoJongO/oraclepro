package oraclepro;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhoneDao {

		private Connection conn = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;

		private String driver = "oracle.jdbc.driver.OracleDriver";
		private String url = "jdbc:oracle:thin:@localhost:1521:xe";
		private String id = "phonedb";
		private String pw = "phonedb";

		List<PersonVo> personList = new ArrayList<PersonVo>();
		
		// DB연결
		private void getConnection() {
			try {
				// 1. JDBC 드라이버 (Oracle) 로딩
				Class.forName(driver);

				// 2. Connection 얻어오기
				conn = DriverManager.getConnection(url, id, pw);
				System.out.println("접속성공");

			} catch (ClassNotFoundException e) {
				System.out.println("error: 드라이버 로딩 실패 - " + e);
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}

		// 자원정리
		private void close() {
			// 5. 자원정리
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
		}
		
		// Search
			public List<PersonVo> personSearch(String word) {
				
				getConnection();
				
				try {
					// 3. SQL문 준비 / 바인딩 / 실행
					String query = "";
					query += " select person_id, ";
					query += "        name, ";
					query += "        hp, ";
					query += "        company ";
					query += " from person ";
					query += " where name like '%"+word+"%' ";
					query += " or hp like '%"+word+"%' ";
					query += " or company like '%"+word+"%' ";
					query += " order by person_id asc";
					
					pstmt = conn.prepareStatement(query);
					
					rs = pstmt.executeQuery();
					
					// 4.결과처리
					while (rs.next()) {
						int person_id = rs.getInt("person_id");
						String name = rs.getString("name");
						String hp = rs.getString("hp");
						String company = rs.getString("company");

						PersonVo personVo = new PersonVo(person_id, name, hp, company);

						personList.add(personVo);
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}

				close();

				return personList;

			}
		
		// Delete
		public int personDelete(int person_id) {
			int count = -1;

			this.getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " delete from person ";
				query += " where person_id = ? ";

				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, person_id);

				count = pstmt.executeUpdate();

				// 4.결과처리
				System.out.println(count + "건 삭제");

			} catch (SQLException e) {
				e.printStackTrace();
			}

			this.close();

			return count;
		}

		// Update
		public int personUpdate(String name, String hp, String company, int person_id) {

			int count = -1;

			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " update person ";
				query += " set name = ?, ";
				query += "     hp = ?, ";
				query += "     company = ? ";
				query += " where person_id = ? ";

				pstmt = conn.prepareStatement(query);

				pstmt.setString(1, name);
				pstmt.setString(2, hp);
				pstmt.setString(3, company);
				pstmt.setInt(4, person_id);

				count = pstmt.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			// 4.결과처리
			System.out.println(count + "건 수정");

			close();

			return count;
		}

		// Insert
		public int personInsert(PersonVo personVo) {

			int count = -1;

			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " insert into person ";
				query += " values(seq_person_id.nextval, ?, ?, ?) ";

				pstmt = conn.prepareStatement(query);

				pstmt.setString(1, personVo.getName());
				pstmt.setString(2, personVo.getHp());
				pstmt.setString(3, personVo.getCompany());

				count = pstmt.executeUpdate();
				
				

				// 4.결과처리
				System.out.println(count + "건 등록");

			} catch (SQLException e) {
				e.printStackTrace();
			}

			close();

			return count; // 성공갯수 리턴
		}
		
		// List
		public List<PersonVo> PersonList() {

			getConnection();
			
			try {
				String query = "";
				query += " select person_id, ";
				query += "        name, ";
				query += "        hp, ";
				query += "        company ";
				query += " from person ";
				query += " order by person_id asc";
				
				pstmt = conn.prepareStatement(query);
				
				rs = pstmt.executeQuery();
				
				while (rs.next()) {
					int person_id = rs.getInt("person_id");
					String name = rs.getString("name");
					String hp = rs.getString("hp");
					String company = rs.getString("company");

					PersonVo personVo = new PersonVo(person_id, name, hp, company);

					personList.add(personVo);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}

			close();

			return personList;

		}
}

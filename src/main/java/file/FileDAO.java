package file;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class FileDAO {

    private Connection conn; // 데이터베이스 연결 객체
    
	public int hit(String fileRealName) {
		String SQL = "update file set downloadCount = downloadCount + 1 " + "where fileRealName = ?";
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, fileRealName);
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public ArrayList<FileDTO> getList() {
		String SQL = "select fileName, fileRealName, downloadCount from file";
		ArrayList<FileDTO> list = new ArrayList<>();
		try {
			PreparedStatement pstmt = conn.prepareStatement(SQL);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				FileDTO file = new FileDTO(rs.getString(1), rs.getString(2), rs.getInt(3));
				list.add(file);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
    
    public FileDAO() { // 생성자
        try {
            String dbURL = "jdbc:mysql://localhost:3306/file"; // 데이터베이스 URL
            String dbID = "kncho83"; // 데이터베이스 사용자 ID
            String dbPassword = "1234"; // 데이터베이스 사용자 비밀번호
            Class.forName("com.mysql.cj.jdbc.Driver"); // JDBC 드라이버 로드
            conn = DriverManager.getConnection(dbURL, dbID, dbPassword); // 데이터베이스 연결
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }
    
    public int upload(String fileName, String fileRealName) { // 파일 업로드 메서드
        String SQL = "insert into file values(?, ?)"; // 파일 정보를 삽입하는 SQL 쿼리
        try {
            PreparedStatement pstmt = conn.prepareStatement(SQL); // SQL 쿼리를 준비
            pstmt.setString(1, fileName); // 1번째 파라미터에 파일 이름 설정
            pstmt.setString(2, fileRealName); // 2번째 파라미터에 실제 파일 이름 설정
            return pstmt.executeUpdate(); // 쿼리 실행 후 영향 받은 행의 수 반환
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
        return -1; // 예외 발생 시 -1 반환
    }
}

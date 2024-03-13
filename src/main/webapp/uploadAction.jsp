<%@ page import="file.FileDAO" %> <!-- FileDAO 클래스를 임포트하여 사용 -->
<%@ page import="java.io.File" %> <!-- 파일 관련 작업을 위한 자바 기본 클래스 임포트 -->
<%@ page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy" %> <!-- 중복된 파일 이름 처리를 위한 정책 클래스 임포트 -->
<%@ page import="com.oreilly.servlet.MultipartRequest" %> <!-- 멀티파트 요청을 처리하기 위한 클래스 임포트 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <!-- 페이지 언어, 컨텐트 타입, 인코딩 설정 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 파일 업로드</title>
</head>
<body>
    <% 
        String directory = application.getRealPath("/upload/"); // 업로드할 디렉토리 경로
        int maxSize = 1024 * 1024 * 100; // 업로드할 파일의 최대 크기를 100MB로 제한
        String encoding = "UTF-8"; // 파일 인코딩 방식 지정
        
        MultipartRequest multipartRequest 
        = new MultipartRequest(request, directory, maxSize, encoding, 
            new DefaultFileRenamePolicy()); // MultipartRequest 객체 생성하여 파일 업로드 처리
        
        String fileName = multipartRequest.getOriginalFileName("file"); // 업로드된 파일의 원본 이름 가져오기
        String fileRealName = multipartRequest.getFilesystemName("file"); // 서버에 저장된 실제 파일 이름 가져오기
        
        new FileDAO().upload(fileName, fileRealName); // FileDAO의 upload 메서드를 사용하여 파일 정보 데이터베이스에 저장
        
        out.write("파일명: " + fileName + "<br>"); // 업로드된 파일의 원본 이름을 화면에 출력
        out.write("실제파일명: " + fileRealName + "<br>"); // 서버에 저장된 실제 파일 이름을 화면에 출력
    %>
</body>
</html>

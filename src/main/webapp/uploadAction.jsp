<%@page import="com.oreilly.servlet.multipart.DefaultFileRenamePolicy"%>
<%@page import="com.oreilly.servlet.MultipartRequest"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.File"%>
<%@ page import="file.FileDAO"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 파일 업로드</title>
</head>
<body>
    <% 
        // 업로드할 파일이 저장될 서버 상의 디렉토리 경로를 설정합니다.
        String directory = "C:/kjw_data/upload";
        // 업로드 파일 크기의 최대값을 100MB로 설정합니다.
        int maxSize = 1024 * 1024 * 100;
        // 파일의 인코딩 설정을 UTF-8로 지정합니다.
        String encoding = "UTF-8";
        
        // MultipartRequest 객체를 생성하여 파일 업로드 요청을 처리합니다.
        // 이때, DefaultFileRenamePolicy를 사용하여 파일명 중복을 처리합니다.
        MultipartRequest multipartRequest = new MultipartRequest(request, directory, maxSize, encoding, new DefaultFileRenamePolicy());
        
        // 업로드된 파일의 원본 이름과 실제 서버에 저장된 파일 이름을 가져옵니다.
        String fileName = multipartRequest.getOriginalFileName("file");
        String fileRealName = multipartRequest.getFilesystemName("file");
        
        // 특정 확장자(.gif, .png, .jpg, .txt)만 업로드를 허용합니다.
        if(!fileName.endsWith(".gif") && !fileName.endsWith(".png") && !fileName.endsWith(".jpg") && !fileName.endsWith(".txt")){
            // 허용되지 않는 확장자의 파일은 삭제합니다.
            File file = new File(directory + "/" + fileRealName);
            file.delete();
            // 사용자에게 확장자가 허용되지 않음을 알립니다.
            out.write("업로드 할 수 없는 확장자 입니다.");
        } else {
            // 허용된 확장자의 파일인 경우, 파일 정보를 데이터베이스에 저장합니다.
            new FileDAO().upload(fileName, fileRealName);
            // 업로드된 파일의 정보를 사용자에게 표시합니다.
            out.write("파일명: " + fileName + "<br>");
            out.write("실제파일명: " + fileRealName + "<br>");
        }
    %>
</body>
</html>

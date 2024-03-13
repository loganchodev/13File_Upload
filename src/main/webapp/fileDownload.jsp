<%@ page import="java.io.File" %> <!-- java.io.File 클래스를 임포트하여 파일 관련 작업 수행 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%> <!-- 페이지의 언어, 컨텐트 타입, 문자 인코딩 설정 -->
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JSP 파일 다운로드</title>
</head>
<body>
<%
	String directory = application.getRealPath("/upload/"); // 서버의 '/upload/' 디렉토리의 실제 경로를 가져옴
	String files[] = new File(directory).list(); // 해당 디렉토리 내의 모든 파일 이름을 String 배열로 가져옴
	
	for(String file : files){ // 배열에 있는 모든 파일 이름에 대해 반복
		out.write("<a href=\"" + request.getContextPath() + "/downloadAction?file=" +
			java.net.URLEncoder.encode(file, "UTF-8") + "\">" + file + "</a><br>"); 
			// 각 파일 이름에 대한 링크 생성 및 출력. 링크는 'downloadAction' 서블릿 또는 페이지를 통해 파일 다운로드를 처리하도록 설정됨.
			// 파일 이름은 URL 인코딩을 사용하여 웹 안전한 문자열로 변환됨.
	}
	
%>
</body>
</html>

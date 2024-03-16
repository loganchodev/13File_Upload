<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html></html>
<head>
<title>FileUpload</title>
</head>
<script>
	function validateForm(form) { // 폼 입력을 검증하는 함수
		if (form.title.value == "") {
			alert("제목을 입력하세요."); // 제목이 비어있을 때 경고
			form.title.focus(); // 제목 입력 필드에 포커스
			return false;
		}

		if (form.attachedFile.value == "") {
			alert("첨부 파일을 선택 안내문입니다."); // 파일 첨부가 되지 않았을 때 경고
			return false;
		}
	}
</script>
<body>
	<h3>파일 업로드</h3>
	<span style="color: red;">${errorMessage }</span>
	<!-- 에러 메시지가 있을 경우 표시 -->

	<form name="fileForm" method="post" enctype="multipart/form-data"
		action="UploadProcess.do" onsubmit="return validateForm(this);">
		<!-- 파일 업로드 폼, 제출 시 검증 함수 호출 -->
		제목: <input type="text" name="title" /><br /> 카테고리(선택사항):
		<!-- 카테고리 선택 (선택 사항) -->
		<input type="checkbox" name="cate" value="산" checked />산
		<!-- '산' 카테고리 선택 체크박스 -->
		<input type="checkbox" name="cate" value="강" />강
		<!-- '강' 카테고리 선택 체크박스 -->
		<input type="checkbox" name="cate" value="바다" />바다
		<!-- '바다' 카테고리 선택 체크박스 -->
		<input type="checkbox" name="cate" value="계곡" />계곡 <br />
		<!-- '계곡' 카테고리 선택 체크박스 -->
		첨부 파일: <input type="file" name="attachedFile" /> <br />
		<!-- 파일 첨부 필드 -->
		<input type="submit" value="전송하기" />
	</form>
</body>
</html>

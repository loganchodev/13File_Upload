package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/downloadAction")
public class downloadAction extends HttpServlet{
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("file"); // 요청으로부터 다운로드할 파일 이름을 가져옴
        
        String directory = this.getServletContext().getRealPath("/upload/"); // 서블릿 컨텍스트를 통해 실제 파일이 위치한 디렉토리 경로를 얻음
        File file = new File(directory + "/" + fileName); // 파일 객체 생성
        
        String mimeType = getServletContext().getMimeType(file.toString()); // 파일의 MIME 타입을 가져옴
        if(mimeType == null) {
            response.setContentType("application/octet-stream"); // MIME 타입이 없으면 기본적으로 바이너리 데이터로 처리
        }
        
        String downloadName = null;
        if(request.getHeader("user-agent").indexOf("MSIE") == -1) { // 인터넷 익스플로러가 아닌 브라우저일 경우
            downloadName = new String(fileName.getBytes("UTF-8"), "8859_1"); // UTF-8로 인코딩된 파일 이름을 ISO-8859-1로 변환
        } else {
            downloadName = new String(fileName.getBytes("EUC-KR"), "8859_1"); // EUC-KR로 인코딩된 파일 이름을 ISO-8859-1로 변환
        }
        
        response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadName + "\";"); // 다운로드될 파일 이름 설정
        
        FileInputStream fileInputStream = new FileInputStream(file); // 파일로부터 데이터를 읽어오기 위한 FileInputStream 생성
        ServletOutputStream servletOutputStream = response.getOutputStream(); // 클라이언트로 데이터를 전송하기 위한 ServletOutputStream 생성
        
        byte b[] = new byte[1024];
        int data = 0;
        
        while((data = (fileInputStream.read(b, 0, b.length))) != -1) {
            servletOutputStream.write(b, 0, data); // 읽은 데이터를 클라이언트에 전송
        }

        servletOutputStream.flush(); // 출력 스트림 버퍼를 비움
        servletOutputStream.close(); // ServletOutputStream을 닫음
        fileInputStream.close(); // FileInputStream을 닫음
    }
}

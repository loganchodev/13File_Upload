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

// 서블릿 매핑을 위한 어노테이션. '/downloadAction' URL 요청을 이 서블릿이 처리하도록 지정.
@WebServlet("/downloadAction")
public class downloadAction extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // GET 요청을 처리하기 위한 메소드. 파일 다운로드 로직을 포함.
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 요청 파라미터에서 'file' 이름으로 전달된 파일 이름을 가져옴.
        String fileName = request.getParameter("file");
        
        // 파일이 저장된 서버 상의 디렉토리 경로 설정.
        String directory = "C:/kjw_data/upload";
        // 파일 객체 생성. 전달받은 파일 이름을 사용하여 파일의 경로를 지정.
        File file = new File(directory + "/" + fileName);
        
        // 파일의 MIME 타입을 결정. 파일의 종류에 따라 브라우저가 어떻게 처리할지 결정될 수 있음.
        String mimeType = getServletContext().getMimeType(file.toString());
        if (mimeType == null) {
            // MIME 타입이 결정되지 않은 경우, 바이너리 데이터로 처리하기 위해 기본값 설정.
            response.setContentType("application/octet-stream");
        }
        
        // 브라우저별 다운로드 파일명 처리를 위한 구분.
        String downloadName = null;
        if (request.getHeader("user-agent").indexOf("MSIE") == -1) {
            // MSIE(인터넷 익스플로러)가 아닌 경우, UTF-8로 인코딩된 파일 이름을 ISO-8859-1로 변환.
            downloadName = new String(fileName.getBytes("UTF-8"), "8859_1");
        } else {
            // MSIE인 경우, EUC-KR로 인코딩된 파일 이름을 ISO-8859-1로 변환.
            downloadName = new String(fileName.getBytes("EUC-KR"), "8859_1");
        }
        
        // 다운로드 될 파일 이름을 설정하기 위한 헤더 설정.
        response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadName + "\";");
        
        // 파일로부터 데이터를 읽기 위한 FileInputStream 객체 생성.
        FileInputStream fileInputStream = new FileInputStream(file);
        // 클라이언트로 데이터를 전송하기 위한 ServletOutputStream 객체 생성.
        ServletOutputStream servletOutputStream = response.getOutputStream();
        
        byte b[] = new byte[1024];
        int data = 0;
        
        // 파일 데이터를 읽고 클라이언트로 전송.
        while ((data = (fileInputStream.read(b, 0, b.length))) != -1) {
            servletOutputStream.write(b, 0, data);
        }

        // 출력 스트림의 버퍼를 비우고 스트림을 닫음.
        servletOutputStream.flush();
        servletOutputStream.close();
        // 입력 스트림을 닫음.
        fileInputStream.close();
    }
}

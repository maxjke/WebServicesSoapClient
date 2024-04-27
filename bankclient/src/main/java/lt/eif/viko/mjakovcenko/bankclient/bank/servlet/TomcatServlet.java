package lt.eif.viko.mjakovcenko.bankclient.bank.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import lt.eif.viko.mjakovcenko.bankclient.bank.transformation.ToPdf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Servlet for serving a PDF file stored within the application resources.
 * This servlet is mapped to handle requests for PDF files under the "/pdf/*" URL pattern.
 */
@WebServlet(name = "TomcatServlet", urlPatterns = {"/pdf/*"})
public class TomcatServlet extends HttpServlet {

    /**
     * Serves a PDF file to the client.
     * The method checks if the requested PDF exists, and if it does, streams it back to the client.
     * If the PDF file does not exist, a 404 error is sent.
     *
     * @param response HttpServletResponse object to write the PDF file to.
     * @throws IOException If an I/O error occurs during the operation.
     */
    private void servePdfFile(HttpServletResponse response) throws IOException {
        Resource resource = new ClassPathResource("/Client_1.pdf");
        if (!resource.exists()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=Client_1.pdf");
        response.setContentLength((int) resource.contentLength());

        try (InputStream is = resource.getInputStream(); OutputStream os = response.getOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * Handles the GET request by determining if the request is for the PDF file.
     * If the request URI matches the PDF file path, it calls {@link #servePdfFile(HttpServletResponse)}.
     * Otherwise, it renders an HTML page that provides a link to the PDF file.
     *
     * @param request  HttpServletRequest object that contains the request the client has made to the servlet.
     * @param response HttpServletResponse object that contains the response the servlet sends to the client.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs when handling the request.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getRequestURI().endsWith("Client_1.pdf")) {
            servePdfFile(response);
        } else if (request.getRequestURI().endsWith("generatePDF")) {
            try {
                ToPdf.convertToPdf();
                servePdfFile(response);
            } catch (Exception e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Open PDF</title>");
            out.println("<style>");
            out.println("html, body { height: 100%; margin: 0; padding: 0; display: flex; align-items: center; justify-content: center; font-family: Arial, sans-serif; }");
            out.println("body { background-color: #f4f4f9; }");
            out.println(".container { text-align: center; }");
            out.println("a { background-color: #007bff; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; }");
            out.println("a:hover { background-color: #0056b3; }");
            out.println("</style>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div class='container'>");
            out.println("<h1>Open PDF Page</h1>");
            out.println("<p><a href='/pdf/Client_1.pdf'>Open Existing PDF</a></p>");
            out.println("<p><a href='/pdf/generatePDF'>Generate PDF</a></p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}

package servletcontroller.clientservice;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateClientPage extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String clientId = "clientId";
        PrintWriter out = response.getWriter();
        String value = request.getParameter(clientId);
        String alertMessage = "<form action='update?clientId=%s' method='post'>";
        String result = String.format(alertMessage,value);

        out.print("<!DOCTYPE html>" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/style.css\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"body\">\n" +
                " <div class='background'> </div>"+
                        " <div class=\"header\">\n" +
                        "            <div class = \"logo\"><p>CLIENT MANAGEMENT SYSTEM</p></div>\n" +
                        "            <div class=\"navigation\">\n" +
                        "                <div class=\"nav3\">\n" +
                        "                    <a href=\"../../index.html\">Home</a>\n" +
                        "                </div>\n" +
                        "                <div class=\"nav1\">\n" +
                        "                    <a href=\"createclient.html\">Create Client</a>\n" +
                        "                </div>\n" +
                        "                <div class=\"nav2\">\n" +
                        "                    <a href=\"showclient.html\">Show Client</a>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </div>"+
                "    <div class=\"middle\">\n" +
                "        <div class=\"update\" id =\"update\" style=\"display:block\">\n" +
                "           <p style=\"color:white;text-align:center;font-size:20px;\">UPDATE CLIENT INFORMATION</p>");

                out.println(result);
                out.print("<input type=\"text\" name=\"name\" placeholder=\"Enter client name\"><br>\n" +
                "                <input type=\"text\" name=\"address\" placeholder=\"Enter client address\"><br>\n" +
                "                <input type=\"submit\" style=\"text-align:center\">\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");

    }
}

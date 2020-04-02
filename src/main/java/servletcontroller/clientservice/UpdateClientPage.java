package servletcontroller.clientservice;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import pojo.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UpdateClientPage extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<!DOCTYPE html>" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <link rel=\"stylesheet\" type=\"text/css\" href=\"../../css/style.css\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"body\">\n" +
                "    <div class=\"header\">\n" +
                "        <div class = \"logo\"><p>CLIENT MANAGEMENT SYSTEM</p></div>\n" +
                "        <div class=\"navigation\">\n" +
                "            <div class=\"nav1\">\n" +
                "                <a href='createclient.html' >Create Client</a>\n" +
                "            </div>\n" +
                "            <div class=\"nav2\">\n" +
                "                <a href='createclient.html'>Show Client</a>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "    <div class=\"middle\">\n" +
                "        <div class=\"update\" id =\"update\" style=\"display:block\">\n" +
                "            <form action='update?clientId="+request.getParameter("clientId")+"' method=\"post\">\n" +
                "                <input type=\"text\" name=\"name\" placeholder=\"Enter name\"><br>\n" +
                "                <input type=\"text\" name=\"address\" placeholder=\"Enter address\"><br>\n" +
                "                <input type=\"submit\" style=\"text-align:center\">\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");

    }
}

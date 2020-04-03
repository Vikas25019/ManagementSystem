package servletcontroller.mapping;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import pojo.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class Mapping extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

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
                " <div class='background'> </div>"+
                " <div class=\"header\">\n" +
                "            <div class = \"logo\"><p>EMPLOYEE MANAGEMENT SYSTEM</p></div>\n" +
                "            <div class=\"navigation\">\n" +
                "                <div class=\"nav3\">\n" +
                "                    <a href=\"../../index.html\">Home</a>\n" +
                "                </div>\n" +
                "                <div class=\"nav1\">\n" +
                "                    <a href=\"createemployee.html\">Create Employee</a>\n" +
                "                </div>\n" +
                "                <div class=\"nav2\">\n" +
                "                    <a href=\"showemployee.html\">Show Employee</a>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>"+
                "    <div class=\"middle\">\n" +
                "        <div class=\"update\" id =\"update\" style=\"display:block\">\n" +
                "           <p style=\"color:white;text-align:center;font-size:20px;\">UPDATE EMPLOYEE INFORMATION</p>"+
                "            <form action='update-employee?employeeId="+request.getParameter("employeeId")+"&clientId="+request.getParameter("clientId")+"' method=\"post\">\n" +
                "           <input type='text' name=\"name\" placeholder=\"Enter name\"><br>"+
                "           <input type=\"text\" name=\"department\" placeholder=\"Enter department\"><br>"+
                "           <input type=\"text\" name=\"email\" placeholder=\"Enter email\"><br>"+
                "           <input type=\"text\" name=\"dateOfBirth\" placeholder=\"Enter dateOfBirth\"><br>"+
                "           <input type=\"submit\" style=\"text-align:center\">\n" +
                "            </form>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>");

    }
}

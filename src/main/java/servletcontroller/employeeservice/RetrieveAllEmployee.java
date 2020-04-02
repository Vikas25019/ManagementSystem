package servletcontroller.employeeservice;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import pojo.Employee;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class RetrieveAllEmployee extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        Employee employee = new Employee();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            List<List<String>> data = daoInterface.retrieveAll(employee,mysqlDatabaseOperation);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel='stylesheet' type='text/css' href='../../css/style.css'>");
            out.println("<title></title>");

            out.println("</head>");
            out.println("<body class='body'>");
            out.print(" <div class=\"background\">\n" +
                    "        </div>");
            out.println(" <div class=\"header\">\n" +
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
                    "        </div>");


            out.println("<div class='tableContent'>");

            out.print("<table class='content-table'");
            out.print("<thead>");
            out.print("<tr>");

            out.print("<th>id</th><th>name</th><th>address</th><th>Edit</th><th>Delete</th></tr>");
            out.print("</thead>");
            out.print("<tbody>");
            out.print("<tr>");
            for(List<String> list : data){
                for(String value : list){
                    out.print("<td>"+value+"</td>");
                }
                out.print("<td><a href='update-employee-page?employeeId="+list.get(1)+"'>edit</a></td><td><a href='deleteEmployee?employeeId="+list.get(1)+"'>delete</a></td></tr>");
            }
            out.print("</tbody>");
            out.print("<table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");


        } catch (Exception e) {
            out.print("<script>alert('"+e+"')</script>");
        }

    }
}

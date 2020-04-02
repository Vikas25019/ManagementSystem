package servletcontroller.employeeservice;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import inputvalidation.InputValidation;
import inputvalidation.InvalidException;
import pojo.Client;
import pojo.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class RetrieveEmployee extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        RetrieveEmployee retrieveEmployee = new RetrieveEmployee();
        final String ID = "employeeId";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Employee employee = new Employee();

        String id = request.getParameter(ID);
        employee.setId(id);

        boolean valid = retrieveEmployee.inputValidation(employee, request, response);

        if (valid) {
            Map<String, String> checkData = new HashMap<>();
            checkData.put(ID, id);

            try {
                boolean checkId = daoInterface.isIdPresent(employee, mysqlDatabaseOperation, checkData);
                if (checkId) {
                    Map<String, String> viewData = daoInterface.retrieve(employee, mysqlDatabaseOperation, checkData);


                    Set<String> columns = viewData.keySet();
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

                    out.print("<table class='content-table'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    for (String columnName : columns) {
                        out.print("<th>" + columnName + "</th>");
                    }
                    out.print("<th>Edit</th><th>Delete</th></tr>");
                    out.print("<tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (String columnName : columns) {
                        out.print("<td>" + viewData.get(columnName) + "</td>");
                    }
                    out.print("<td><a href='update-page?employeeId=" + viewData.get("employeeId") + "'>edit</a></td><td><a href='delete?employeeId=" + viewData.get("employeeId") + "'>delete</a></td></tr>");
                    out.print("</tbody>");
                    out.println("</table>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");

                } else {
                    out.print("<script>alert('Id is not present.');location ='showemployee.html';</script>");
                }
            } catch (Exception e) {
                out.print("<script>alert('" + e + "');location ='showemployee.html';</script>");
            }
        }
    }

    private boolean inputValidation(Employee employee, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        InputValidation inputValidation = new InputValidation();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            inputValidation.userIdValidator(employee.getId());
            return true;
        } catch (InvalidException e) {
            out.print("<script>alert('" + e + "');location ='showemployee.html';</script>");
            return false;
        }
    }
}
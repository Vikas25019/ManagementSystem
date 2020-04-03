package servletcontroller.employeeservice;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import inputvalidation.InputValidation;
import inputvalidation.InvalidException;

import pojo.Employee;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class RetrieveEmployee extends HttpServlet {
    final String LOCATION ="showemployee.html";
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        RetrieveEmployee retrieveEmployee = new RetrieveEmployee();
        final String EMPLOYEEID = "employeeId";
        final String CLIENTID = "clientID";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Employee employee = new Employee();

        String id = request.getParameter(EMPLOYEEID);
        employee.setId(id);

        boolean valid = retrieveEmployee.inputValidation(employee, request, response);

        if (valid) {
            LinkedHashMap<String, String> checkData = new LinkedHashMap<>();
            checkData.put(EMPLOYEEID, id);

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

                    out.print("<table class='content-table-employee'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    for (String columnName : columns) {
                        String heading = "<th>%s</th>";
                        String output = String.format(heading,columnName);
                        out.print(output);
                    }
                    out.print("<th>Edit</th><th>Delete</th></tr>");
                    out.print("<tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (String columnName : columns) {
                        String value = "<td>%s</td>";
                        String output = String.format(value,viewData.get(columnName));
                        out.print(output);
                    }

                    String st = "<td><a href='update-employee-page?employeeId=%s&clientId=%s'>edit</a></td><td><a href='delete?employeeId=%s'>delete</a></td></tr>";
                    String employeeIdValue = viewData.get(EMPLOYEEID);
                    String clientIdValue = viewData.get(CLIENTID);
                    String output = String.format(st,employeeIdValue,clientIdValue,employeeIdValue);
                    out.print(output);

                    out.print("</tbody>");
                    out.println("</table>");
                    out.println("</div>");

                    out.println("</body>");
                    out.println("</html>");

                } else {
                    String message ="Id is not present.";
                    String alertMessage = "<script>alert('%s');location ='%s';</script>";
                    String result = String.format(alertMessage,message,LOCATION);
                    out.println(result);

                }
            } catch (Exception e) {
                String alertMessage = "<script>alert('%s');location ='%s';</script>";
                String result = String.format(alertMessage,e,LOCATION);
                out.println(result);

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
            String alertMessage = "<script>alert('%s');location ='%s';</script>";
            String result = String.format(alertMessage,e,LOCATION);
            out.println(result);
            return false;
        }
    }
}
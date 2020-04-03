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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class RetrieveAllEmployee extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        Employee employee = new Employee();
        final String EMPLOYEE_ID = "employeeId";
        final String CLIENT_ID = "clientId";
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            List<LinkedHashMap<String,String>> data = daoInterface.retrieveAll(employee,mysqlDatabaseOperation);

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

            out.print("<table class='content-table-employee'");
            out.print("<thead>");
            out.print("<tr>");

            LinkedHashMap<String,String> hashData = data.get(0);
            Set<String> columnSet = hashData.keySet();
            for(String columnName : columnSet){
                String heading = "<th>%s</th>";
                String output = String.format(heading,columnName);
                out.print(output);
            }

            out.print("<th>Edit</th><th>Delete</th></tr>");
            out.print("</thead>");
            out.print("<tbody>");
            out.print("<tr>");
            for(LinkedHashMap<String,String> result : data){
                Set<String> columns= result.keySet();
                for(String columnName : columns){
                    String value = "<td>%s</td>";
                    String output = String.format(value,result.get(columnName));
                    out.print(output);
                }
                String stringLine = "<td><a href='update-employee-page?employeeId=%s&clientId=%s'>edit</a></td><td><a href='delete-employee?employeeId=%s'>delete</a></td><td><a href='viewMappingInfo?clientId=%s'>view client information</a></td></tr>";
                String employeeId = result.get(EMPLOYEE_ID);
                String clientId = result.get(CLIENT_ID);
                String output = String.format(stringLine,employeeId,clientId,employeeId,clientId);
                out.print(output);
            }
            out.print("</tbody>");
            out.print("<table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");

        } catch (Exception e) {
            String alertMessage = "<script>alert('%s'); location ='%s';</script>";
            String location = "retrieve-all-employee";
            String result = String.format(alertMessage,e,location);
            out.println(result);
        }

    }
}

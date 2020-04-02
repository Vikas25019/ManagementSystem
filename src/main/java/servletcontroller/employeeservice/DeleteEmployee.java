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
import java.util.HashMap;
import java.util.Map;

public class DeleteEmployee extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        DeleteEmployee deleteEmployee = new DeleteEmployee();
        final String ID = "employeeId";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Employee employee = new Employee();

        String id = request.getParameter(ID);
        employee.setId(id);

        boolean valid = deleteEmployee.inputValidation(employee, request, response);
        if (valid) {

            Map<String, String> checkData = new HashMap<>();
            checkData.put(ID, id);

            try {
                boolean checkId = daoInterface.isIdPresent(employee, mysqlDatabaseOperation, checkData);

                if (checkId) {

                    daoInterface.delete(employee, mysqlDatabaseOperation, checkData);
                    out.print("<script>alert('Record deleted successfully!'); location ='retrieveAll';</script>");

                } else {
                    out.print("<script>alert('Id is not present.'); location ='retrieveAll';</script>");
                }
            } catch (Exception e) {
                out.print("<script>alert('" + e + "');location ='retrieveAll';</script>");
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
            out.print("<script>alert('" + e + "');location ='retrieveAll';</script>");
            return false;
        }
    }
}

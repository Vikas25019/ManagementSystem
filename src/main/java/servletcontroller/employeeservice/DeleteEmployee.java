package servletcontroller.employeeservice;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import inputvalidation.InputValidation;
import inputvalidation.InvalidException;
import pojo.Employee;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.LinkedHashMap;


public class DeleteEmployee extends HttpServlet {
    final String LOCATION = "retrieve-all-employee";
    final String alertMessage = "<script>alert('%s'); location ='%s';</script>";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        DeleteEmployee deleteEmployee = new DeleteEmployee();
        final String ID = "employeeId";

        PrintWriter out = response.getWriter();

        Employee employee = new Employee();

        String id = request.getParameter(ID);
        employee.setId(id);

        boolean valid = deleteEmployee.inputValidation(employee,response);
        if (valid) {

            LinkedHashMap<String, String> checkData = new LinkedHashMap<>();
            checkData.put(ID, id);

            try {
                boolean checkId = daoInterface.isIdPresent(employee, mysqlDatabaseOperation, checkData);

                if (checkId) {

                    daoInterface.delete(employee, mysqlDatabaseOperation, checkData);
                    String message = "Record deleted successfully!";
                    String result = String.format(alertMessage,message,LOCATION);
                    out.println(result);
                } else {
                    String message = "Id is not present.";
                    String result = String.format(alertMessage,message,LOCATION);
                    out.println(result);
                }
            } catch (Exception e) {
                String result = String.format(alertMessage,e,LOCATION);
                out.println(result);
            }
        }
    }

    private boolean inputValidation(Employee employee,HttpServletResponse response) throws IOException{
        InputValidation inputValidation = new InputValidation();
        PrintWriter out = response.getWriter();
        try {
            inputValidation.userIdValidator(employee.getId());
            return true;
        } catch (InvalidException e) {

            String result = String.format(alertMessage,e,LOCATION);
            out.println(result);
            return false;
        }
    }
}

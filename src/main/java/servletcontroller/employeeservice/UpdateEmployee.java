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
import java.util.LinkedHashMap;
import java.util.Map;

public class UpdateEmployee extends HttpServlet {
    final String LOCATION = "retrieve-all-employee";
    final String alertMessage = "<script>alert('%s'); location ='%s';</script>";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        UpdateEmployee updateEmployee = new UpdateEmployee();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        final String ID = "employeeId";
        final String CLIENT_ID = "clientId";
        final String NAME = "name";
        final String DEPARTMENT = "department";
        final String EMAIL = "email";
        final String DOB = "dateOfBirth";

        Employee employee = new Employee();

        String id = request.getParameter(ID);
        String clientId = request.getParameter(CLIENT_ID);

        String name = request.getParameter(NAME);
        String department = request.getParameter(DEPARTMENT);
        String email = request.getParameter(EMAIL);
        String dob = request.getParameter(DOB);

        employee.setId(id);
        employee.setClientId(clientId);
        employee.setName(name);
        employee.setDepartment(department);
        employee.setEmail(email);
        employee.setDateOfBirth(dob);



        boolean valid = updateEmployee.inputValidation(employee,request,response);
        if(valid) {

            LinkedHashMap<String, String> data = employee.employeeData();
            Map<String, String> checkData = new HashMap<>();
            checkData.put(ID, id);
            try {

                boolean checkId = daoInterface.isIdPresent(employee, mysqlDatabaseOperation, checkData);
                if (checkId) {
                    int status = daoInterface.update(employee, mysqlDatabaseOperation, data, ID);
                    if (status > 0) {
                        String message = "Record update successfully!";
                        String result = String.format(alertMessage,message,LOCATION);
                        out.println(result);

                    } else {
                        String message = "Sorry! unable to update record";
                        String result = String.format(alertMessage,message,LOCATION);
                        out.println(result);
                    }
                } else {
                    String message = "Id is not present";
                    String result = String.format(alertMessage,message,LOCATION);
                    out.println(result);
                }
            } catch (Exception e) {
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
            inputValidation.userIdValidator(employee.getClientId());
            inputValidation.userNameValidator(employee.getName());
            inputValidation.userDepartmentValidator(employee.getDepartment());
            inputValidation.userEmailValidator(employee.getEmail());
            inputValidation.userDateOfBirthValidator(employee.getDateOfBirth());
            return true;
        } catch (InvalidException e) {
            String id = employee.getId();
            String message = "<script>alert('%s');location ='update-employee-page?employeeId=%s';</script>";
            String result = String.format(message,e,id);
            out.println(result);
            return false;
        }
    }

}

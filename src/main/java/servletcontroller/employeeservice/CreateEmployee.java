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
import java.util.Map;

public class CreateEmployee extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        CreateEmployee createEmployee = new CreateEmployee();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        final String ID = "employeeId";
        final String CLIENT_ID = "clientId";
        final String NAME = "name";
        final String DEPARTMENT = "department";
        final String EMAIL = "email";
        final String DOB = "dateOfBirth";

        Employee employee = new Employee();

        //insertion
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

        boolean valid = createEmployee.inputValidation(employee,request,response);

        if (valid) {
            Map<String, String> data = employee.employeeData();
            Map<String, String> checkData = new HashMap<>();
            checkData.put(ID, id);
            try {
                boolean checkId = daoInterface.isIdPresent(employee, mysqlDatabaseOperation, checkData);
                if (!checkId) {
                    int status = daoInterface.create(employee, mysqlDatabaseOperation, data);
                    if (status > 0) {
                        out.print("<script>alert('Record saved successfully!');</script>");
                        request.getRequestDispatcher("createemployee.html").include(request, response);
                    } else {
                        out.print("<script>alert('Sorry! unable to save record');</script>");
                    }
                }
                else{
                    out.println("<script>alert('Id already present'); location ='createemployee.html';</script>");
                }
            } catch (Exception e) {
                out.println("error" + e);
            }
        }
    }

    private boolean inputValidation(Employee employee,HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        InputValidation inputValidation = new InputValidation();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            inputValidation.userIdValidator(employee.getId());
            inputValidation.userIdValidator(employee.getClientId());
            inputValidation.userNameValidator(employee.getName());
            inputValidation.userDepartmentValidator(employee.getDepartment());
            inputValidation.userEmailValidator(employee.getEmail());
            inputValidation.userDateOfBirthValidator(employee.getDateOfBirth());
            return true;
        } catch (InvalidException e) {
            out.print("<script>alert('"+e+"');location ='createemployee.html';</script>");
            return false;
        }
    }
}



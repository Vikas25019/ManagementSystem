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

public class CreateEmployee extends HttpServlet {
    final String LOCATION = "createemployee.html";
    final String alertMessage = "<script>alert('%s'); location ='%s';</script>";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Employee, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Employee> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        CreateEmployee createEmployee = new CreateEmployee();

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

        boolean valid = createEmployee.inputValidation(employee,response);

        if (valid) {
            LinkedHashMap<String, String> data = employee.employeeData();
            Map<String, String> checkData = new HashMap<>();
            checkData.put(ID, id);
            try {
                boolean checkId = daoInterface.isIdPresent(employee, mysqlDatabaseOperation, checkData);
                if (!checkId) {
                    int status = daoInterface.create(employee, mysqlDatabaseOperation, data);

                    if (status > 0) {
                        //request.getRequestDispatcher("mapping-database?employeeId="+employee.getId()+"&clientId="+employee.getClientId()+"").include(request, response);
                        //ServletContext context = getServletContext().getContext("mapping");
                       // out.print("<script>alert("+context+");</script>");
                       // RequestDispatcher rd = context.getRequestDispatcher("mapping-database?employeeId="+id+"&clientId="+clientId+"");
                       // rd.forward(request,response);
                        String message = "Record saved successfully!";
                        String result = String.format(alertMessage,message,LOCATION);
                        out.println(result);

                    } else {
                        String message = "Sorry! unable to save record";
                        String result = String.format(alertMessage,message,LOCATION);
                        out.println(result);
                    }
                }
                else{
                    String message = "Id already present";
                    String result = String.format(alertMessage,message,LOCATION);
                    out.println(result);
                }
            } catch (Exception e) {
                String result = String.format(alertMessage,e,LOCATION);
                out.println(result);
            }
        }
    }

    private boolean inputValidation(Employee employee,HttpServletResponse response) throws IOException, ServletException {
        InputValidation inputValidation = new InputValidation();
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
            String result = String.format(alertMessage,e,LOCATION);
            out.println(result);
            return false;
        }
    }
}



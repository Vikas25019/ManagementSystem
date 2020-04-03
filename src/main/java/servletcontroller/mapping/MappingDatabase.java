package servletcontroller.mapping;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

public class MappingDatabase extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException{
        IDaoInterface<MappingDatabase, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<MappingDatabase> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        MappingDatabase mappingDatabase = new MappingDatabase();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        final String CLIENT_ID = "clientId";
        final String EMPLOYEE_ID = "employeeId";

        String clientId = request.getParameter(CLIENT_ID);
        String employeeId = request.getParameter(EMPLOYEE_ID);
        LinkedHashMap<String, String> data = new LinkedHashMap<>();
        data.put(CLIENT_ID, clientId);
        data.put(EMPLOYEE_ID, employeeId);

        try {
            int status = daoInterface.create(mappingDatabase, mysqlDatabaseOperation, data);
            if (status > 0) {
                out.print("<script>alert('Record saved successfully!');</script>");
                request.getRequestDispatcher("createemployee.html").include(request, response);
            } else {
                out.print("<script>alert('Sorry! unable to save record');</script>");
            }
        } catch (Exception e) {
            out.println("error" + e);
        }
    }


}


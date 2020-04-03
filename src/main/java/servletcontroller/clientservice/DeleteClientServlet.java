package servletcontroller.clientservice;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import inputvalidation.InputValidation;
import inputvalidation.InvalidException;
import pojo.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

public class DeleteClientServlet extends HttpServlet {
    final String LOCATION = "retrieveAll";
    final String alertMessage = "<script>alert('%s'); location ='%s';</script>";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        DeleteClientServlet deleteClientServlet = new DeleteClientServlet();

        final String ID = "clientId";
        PrintWriter out = response.getWriter();
        Client client = new Client();

        String id = request.getParameter(ID);
        client.setId(id);

        boolean valid = deleteClientServlet.inputValidation(client, response);
        if (valid) {
            LinkedHashMap<String, String> checkData = new LinkedHashMap<>();
            checkData.put(ID, id);

            try {
                boolean checkId = daoInterface.isIdPresent(client, mysqlDatabaseOperation, checkData);

                if (checkId) {
                    daoInterface.delete(client, mysqlDatabaseOperation, checkData);
                    String message = "Record deleted successfully!";
                    String result = String.format(alertMessage, message, LOCATION);
                    out.println(result);

                } else {
                    String message = "Id is not present.";
                    String result = String.format(alertMessage, message, LOCATION);
                    out.println(result);
                }
            } catch (Exception e) {

                String result = String.format(alertMessage, e, LOCATION);
                out.println(result);
            }
        }
    }

    private boolean inputValidation(Client client, HttpServletResponse response) throws IOException, ServletException {
        InputValidation inputValidation = new InputValidation();
        PrintWriter out = response.getWriter();
        try {
            inputValidation.userIdValidator(client.getId());
            return true;
        } catch (InvalidException e) {
            String result = String.format(alertMessage, e, LOCATION);
            out.println(result);
            return false;
        }
    }
}

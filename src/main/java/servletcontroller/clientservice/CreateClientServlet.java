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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CreateClientServlet extends HttpServlet {
    final String LOCATION = "createclient.html";
    final String alertMessage = "<script>alert('%s'); location ='%s';</script>";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        CreateClientServlet createClientServlet = new CreateClientServlet();

        PrintWriter out = response.getWriter();

        final String ID = "clientId";
        final String NAME = "name";
        final String ADDRESS = "address";

        Client client = new Client();

        String id = request.getParameter(ID);
        String name = request.getParameter(NAME);
        String address = request.getParameter(ADDRESS);

        client.setId(id);
        client.setName(name);
        client.setAddress(address);

        boolean valid = createClientServlet.inputValidation(client,response);

        if (valid) {
            LinkedHashMap<String, String> data = client.clientData();
            Map<String, String> checkData = new HashMap<>();
            checkData.put(ID, id);
            try {
                boolean checkId = daoInterface.isIdPresent(client, mysqlDatabaseOperation, checkData);
                if (!checkId) {
                    int status = daoInterface.create(client, mysqlDatabaseOperation, data);
                    if (status > 0) {
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

    private boolean inputValidation(Client client,HttpServletResponse response) throws IOException, ServletException {
        InputValidation inputValidation = new InputValidation();
        PrintWriter out = response.getWriter();
        try {
            inputValidation.userIdValidator(client.getId());
            inputValidation.userNameValidator(client.getName());
            inputValidation.userAddressValidator(client.getAddress());
            return true;
        } catch (InvalidException e) {
            String result = String.format(alertMessage,e,LOCATION);
            out.println(result);
            return false;
        }
    }

}

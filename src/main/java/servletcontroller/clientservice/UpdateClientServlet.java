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

public class UpdateClientServlet extends HttpServlet {
    final String LOCATION = "retrieveAll";
    final String alertMessage = "<script>alert('%s'); location ='%s';</script>";

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        UpdateClientServlet updateClientServlet = new UpdateClientServlet();

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


        boolean valid = updateClientServlet.inputValidation(client,response);
        if(valid) {

            LinkedHashMap<String, String> data = client.clientData();
            Map<String, String> checkData = new HashMap<>();
            checkData.put(ID, id);
            try {

                boolean checkId = daoInterface.isIdPresent(client, mysqlDatabaseOperation, checkData);
                if (checkId) {
                    int status = daoInterface.update(client, mysqlDatabaseOperation, data, ID);
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

    private boolean inputValidation(Client client,  HttpServletResponse response) throws IOException, ServletException {
        InputValidation inputValidation = new InputValidation();

        PrintWriter out = response.getWriter();
        try {
            inputValidation.userNameValidator(client.getName());
            inputValidation.userAddressValidator(client.getAddress());
            return true;
        } catch (InvalidException e) {
            String id = client.getId();
            String message = "<script>alert('%s');location ='update-page?clientId=%s';</script>";
            String result = String.format(message,e,id);
            out.println(result);
            return false;
        }
    }

}
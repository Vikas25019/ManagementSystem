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
import java.util.Map;

public class CreateClientServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        CreateClientServlet createClientServlet = new CreateClientServlet();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        final String ID = "clientId";
        final String NAME = "name";
        final String ADDRESS = "address";

        Client client = new Client();

        //insertion
        String id = request.getParameter(ID);
        String name = request.getParameter(NAME);
        String address = request.getParameter(ADDRESS);

        client.setId(id);
        client.setName(name);
        client.setAddress(address);

        boolean valid = createClientServlet.inputValidation(client,response);

        if (valid) {
            Map<String, String> data = client.clientData();
            Map<String, String> checkData = new HashMap<>();
            checkData.put(ID, id);
            try {
                boolean checkId = daoInterface.isIdPresent(client, mysqlDatabaseOperation, checkData);
                if (!checkId) {
                    int status = daoInterface.create(client, mysqlDatabaseOperation, data);
                    if (status > 0) {
                        out.print("<p>Record saved successfully!</p>");
                        request.getRequestDispatcher("../../index.html").include(request, response);
                    } else {
                        out.println("Sorry! unable to save record");
                    }
                }
                else{
                    out.println("<script>alert('Id already present'); location ='client-management.html';</script>");
                }
            } catch (Exception e) {
                out.println("error" + e);
            }
        }
    }

    private boolean inputValidation(Client client,HttpServletResponse response) throws IOException {
        InputValidation inputValidation = new InputValidation();
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            inputValidation.userIdValidator(client.getId());
            inputValidation.userNameValidator(client.getName());
            inputValidation.userAddressValidator(client.getAddress());
            return true;
        } catch (InvalidException e) {
            out.println(e);
            return false;
        }
    }
}


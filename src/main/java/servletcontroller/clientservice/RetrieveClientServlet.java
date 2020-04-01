package servletcontroller.clientservice;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import pojo.Client;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrieveClientServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();

        final String ID = "clientId";

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        Client client = new Client();

        String id = request.getParameter(ID);
        client.setId(id);

        Map<String, String> checkData = new HashMap<>();
        checkData.put(ID, id);

        boolean checkId = false;
        try {
            checkId = daoInterface.isIdPresent(client, mysqlDatabaseOperation, checkData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (checkId) {
            try {
                Map<String, String> viewData = daoInterface.retrieve(client, mysqlDatabaseOperation, checkData);
                //RETRIVE ALL CLIENT
                out.println(viewData);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Id is not present.");
        }
    }
}
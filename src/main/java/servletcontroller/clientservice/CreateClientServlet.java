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
import java.util.Map;

public class CreateClientServlet extends HttpServlet {
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException , ServletException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();

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

        Map<String, String> data = client.clientData();
        try {
            out.println(data);
            int status = daoInterface.create(client, mysqlDatabaseOperation, data);
            if(status>0){
                out.print("<p>Record saved successfully!</p>");
                request.getRequestDispatcher("../../index.html").include(request, response);
            }else{
                out.println("Sorry! unable to save record");
            }
        } catch (Exception e) {
            out.println("error"+e);
        }
    }

}


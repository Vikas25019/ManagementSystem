package servletcontroller.clientservice;

import dao.DaoImplementation;
import dao.IDaoInterface;
import dao.MysqlDatabaseOperation;
import pojo.Client;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

public class RetrieveAllClient extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        Client client = new Client();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        try {
            List<List<String>> data = daoInterface.retrieveAll(client,mysqlDatabaseOperation);

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title></title>");

            out.println("</head>");
            out.println("<body>");
            out.println("<div class='header'>");
            out.println("</div>");
            out.println("<div class='content'>");

            out.print("<table border='1' width='100%'");
            out.print("<tr>");

            out.print("<th>id</th><th>name</th><th>address</th><th>Edit</th><th>Delete</th></tr>");
            out.print("<tr>");
            for(List<String> list : data){
                for(String value : list){
                    out.print("<td>"+value+"</td>");
                }
                out.print("<td><a href='#'>edit</a></td><td><a href='#'>delete</a></td></tr>");
            }

            out.println("</div>");
            out.println("</body>");
            out.println("</html>");


        } catch (Exception e) {
            System.out.println(e);
        }

    }
}

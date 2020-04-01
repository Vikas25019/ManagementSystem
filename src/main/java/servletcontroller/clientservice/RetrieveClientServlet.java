package servletcontroller.clientservice;

import com.sun.javadoc.SeeTag;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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
                //RETRIVE  CLIENT

                Set<String> columns = viewData.keySet();
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<link rel='stylesheet' type='text/css' href='../../css/style.css'>");
                out.println("<title></title>");

                out.println("</head>");
                out.println("<body>");

                out.println("<div class=\"header\">\n" +
                        "            <div class = \"logo\"><p>CLIENT MANAGEMENT SYSTEM</p></div>\n" +
                        "            <div class=\"navigation\">\n" +
                        "                <div class=\"nav1\">\n" +
                        "                    <a href=\"#\" onclick=\"showInsertBlock()\">Create Client</a>\n" +
                        "                </div>\n" +
                        "                <div class=\"nav2\">\n" +
                        "                    <a href=\"#\" onclick=\"showRetrieveBlock()\">Show Client</a>\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </div>");

                out.println("<div class='tableContent'>");

                out.print("<table class='content-table'>");
                out.print("<thead>");
                out.print("<tr>");
                for(String columnName : columns){
                    out.print("<th>"+columnName+"</th>");
                }
                out.print("<th>Edit</th><th>Delete</th></tr>");
                out.print("<tr>");
                out.print("</thead>");
                out.print("<tbody>");
                for(String columnName : columns){
                    out.print("<td>"+viewData.get(columnName)+"</td>");
                }
                out.print("<td><a href='#'>edit</a></td><td><a href='#'>delete</a></td></tr>");
                out.print("</tbody>");
                out.println("</table>");
                out.println("</div>");
                out.println("</body>");
                out.println("</html>");

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Id is not present.");
        }
    }
}
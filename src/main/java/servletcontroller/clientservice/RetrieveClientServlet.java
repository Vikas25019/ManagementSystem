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
import java.util.*;

public class RetrieveClientServlet extends HttpServlet {
    final String LOCATION ="showclient.html";

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        RetrieveClientServlet retrieveClientServlet = new RetrieveClientServlet();
        final String ID = "clientId";

        PrintWriter out = response.getWriter();
        Client client = new Client();

        String id = request.getParameter(ID);
        client.setId(id);

        boolean valid = retrieveClientServlet.inputValidation(client, response);

        if (valid) {
            LinkedHashMap<String, String> checkData = new LinkedHashMap<>();
            checkData.put(ID, id);

            try {
                boolean checkId = daoInterface.isIdPresent(client, mysqlDatabaseOperation, checkData);
                if (checkId) {
                    LinkedHashMap<String, String> viewData = daoInterface.retrieve(client, mysqlDatabaseOperation, checkData);


                    Set<String> columns = viewData.keySet();
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<link rel='stylesheet' type='text/css' href='../../css/style.css'>");
                    out.println("<title></title>");

                    out.println("</head>");
                    out.println("<body class='body'>");
                    out.print(" <div class=\"background\">\n" +
                            "        </div>");
                    out.println(" <div class=\"header\">\n" +
                            "            <div class = \"logo\"><p>CLIENT MANAGEMENT SYSTEM</p></div>\n" +
                            "            <div class=\"navigation\">\n" +
                            "                <div class=\"nav3\">\n" +
                            "                    <a href=\"../../index.html\">Home</a>\n" +
                            "                </div>\n" +
                            "                <div class=\"nav1\">\n" +
                            "                    <a href=\"createclient.html\">Create Client</a>\n" +
                            "                </div>\n" +
                            "                <div class=\"nav2\">\n" +
                            "                    <a href=\"showclient.html\">Show Client</a>\n" +
                            "                </div>\n" +
                            "            </div>\n" +
                            "        </div>");

                    out.println("<div class='tableContent'>");

                    out.print("<table class='content-table-client'>");
                    out.print("<thead>");
                    out.print("<tr>");
                    for (String columnName : columns) {
                        String heading = "<th>%s</th>";
                        String output = String.format(heading,columnName);
                        out.print(output);
                    }
                    out.print("<th>Edit</th><th>Delete</th></tr>");
                    out.print("<tr>");
                    out.print("</thead>");
                    out.print("<tbody>");
                    for (String columnName : columns) {
                        String value = "<td>%s</td>";
                        String output = String.format(value,viewData.get(columnName));
                        out.print(output);
                    }

                    String st = "<td><a href='update-page?clientId=%s'>edit</a></td><td><a href='delete?clientId=%s'>delete</a></td></tr>";
                    String idValue = viewData.get(ID);
                    String output = String.format(st,idValue,idValue);
                    out.print(output);

                    out.print("</tbody>");
                    out.println("</table>");
                    out.println("</div>");
                    out.println("</body>");
                    out.println("</html>");

                } else {
                    String message ="Id is not present.";
                    String alertMessage = "<script>alert('%s');location ='%s';</script>";
                    String result = String.format(alertMessage,message,LOCATION);
                    out.println(result);
                }
            } catch (Exception e) {
                String alertMessage = "<script>alert('%s');location ='%s';</script>";
                String result = String.format(alertMessage,e,LOCATION);
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
            String alertMessage = "<script>alert('%s');location ='%s';</script>";
            String result = String.format(alertMessage,e,LOCATION);
            out.println(result);
            return false;
        }
    }
}
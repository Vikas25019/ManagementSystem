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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

public class RetrieveAllClient extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        IDaoInterface<Client, MysqlDatabaseOperation> daoInterface = new DaoImplementation<>();
        MysqlDatabaseOperation<Client> mysqlDatabaseOperation = MysqlDatabaseOperation.getInstance();
        Client client = new Client();

        final String CLIENT_ID = "clientId";
        PrintWriter out = response.getWriter();

        try {
            List<LinkedHashMap<String,String>> data = daoInterface.retrieveAll(client,mysqlDatabaseOperation);

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

            out.print("<table class='content-table-client'");
            out.print("<thead>");
            out.print("<tr>");

            LinkedHashMap<String,String> hashData = data.get(0);
            Set<String> columnSet = hashData.keySet();
            for(String columnName : columnSet){
                String heading = "<th>%s</th>";
                String output = String.format(heading,columnName);
                out.print(output);
            }

            out.print("<th>Edit</th><th>Delete</th></tr>");
            out.print("</thead>");
            out.print("<tbody>");
            out.print("<tr>");
            for(LinkedHashMap<String,String> result : data){
                Set<String> columns= result.keySet();
                for(String columnName : columns){
                    String value = "<td>%s</td>";
                    String output = String.format(value,result.get(columnName));
                    out.print(output);
                }
                String stringLine = "<td><a href='update-page?clientId=%s'>edit</a></td><td><a href='delete?clientId=%s'>delete</a></td></tr>";
                String id = result.get(CLIENT_ID);
                String output = String.format(stringLine,id,id);
                out.print(output);
            }
            out.print("</tbody>");
            out.print("<table>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");


        } catch (Exception e) {
            String alertMessage = "<script>alert('%s'); location ='%s';</script>";
            String location = "retrieveAll";
            String result = String.format(alertMessage,e,location);
            out.println(result);
        }

    }
}

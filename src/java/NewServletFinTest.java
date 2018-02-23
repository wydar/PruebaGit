import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/NewServletFinTest"})
public class NewServletFinTest extends HttpServlet {


    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //Recuperamos la nota para mostrarla por pantalla
        int nota = LoginManager.getNumAciertos(request);
        if(nota==-1){
            nota=0;
        }
        
        //Reseteamos los valores
        LoginManager.setNumAciertos(request, -1);
        LoginManager.setNumPreg(request, -1);
        LoginManager.setNumTest(request, -1);
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"indice.css\" />");
            out.println("<title>Servlet ServletFinTest</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Alumno: "+LoginManager.getLoginName(request)+"</p>");            
            out.println("<h1>Fin del Test</h1>");
            out.println("<form class=\"\" action=\"NewServlet\" method=\"get\">");
            out.println("<p>Tu porcentaje de acierto es: "+(nota*10)+"%</p>");
            out.println("<p>Tu numero de fallos es: "+(10-nota)+"</p>");
            //Dependiendo de la nota se generará un mensaje u otro
            if(nota<=3)
            out.println("<p>Tu nota es demasiado baja, no te recomiendo presentarte al examen</p>");
            else if(nota>3 && nota<6)
            out.println("<p>Vas bien encaminado pero debes seguir estudiando</p>");
            else
            out.println("<p>Tu nota es muy buena estas preparado para el examen, enhorabuena!</p>");
            
            out.println("<h2>Ahora puedes finalizar el test y elegir uno nuevo</h2>");
            out.println("<input type=\"submit\" name=\"\" value=\"Finalizar\">");
            out.println("<h2> O si no puedes desconectarte pinchando <a href=\"NewServletLogout\">aquí</a></h2>");
            out.println("</form>");
            out.println("<div class=\"footer\">\n" +
"        <p id=top class=\"ftp\"> Pablo Garcia Acosta (Universidad de Málaga)</p>\n" +
"        <p class=\"ftp\"> Copyright &#169; Todos los derechos reservados <p>\n" +
"        </div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}

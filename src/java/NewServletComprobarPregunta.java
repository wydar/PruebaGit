import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author PC
 */
@WebServlet(urlPatterns = {"/NewServletComprobarPregunta"})
public class NewServletComprobarPregunta extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //Recuperamos datos de la sesion que vamos a necesitar 
        int numTest=LoginManager.getNumTest(request);
        int numPreg=LoginManager.getNumPregunta(request);

        int respuesta = new Integer(request.getParameter("preg"+numPreg));
        
        //Comprobamos si la respuesta seleccionada es correcta y si lo es la anotamos
        boolean correcta=validarPregunta(request,"../ficheros/Respuestas"+numTest+".txt",respuesta);
        if(correcta){ //si es correcta sumamos 1 el numero de aciertos
            int aciertos=LoginManager.getNumAciertos(request);
            if(aciertos==-1)//es if es para el caso en que sea la primera respuesta correcta ya que el valor por defecto es -1
               LoginManager.setNumAciertos(request, 1);
            else
                LoginManager.setNumAciertos(request, aciertos+1);
        }
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"indice.css\" />");
            out.println("<title>Servlet ServletComprobarPregunta</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Alumno: "+LoginManager.getLoginName(request)+"</p>");            
            //Dependiendo si la respuesta es correcta o no imprimiremos una cosa u otra
            if(correcta){
            out.println("<h1>¡Respuesta correcta!</h1>");
            out.println("<img src='imagenes/Correcto.png' alt=\"correcto\">");
            }else{
            out.println("<h1>Incorrecto</h1>");
            out.println("<p><h2>La Respuesta correcta era:</h2>"+LoginManager.getRespCorrecta(request)+"</p>");
            out.println("");
            out.println("<img src='imagenes/incorrecto.png' alt=\"incorrecto\" height=\"150\" width=\"150\" >");
            }
            //Si hemos llegado a la ultima pregunta generamos la pagina final llamando a ServletFinTest
            if(numPreg+1<11){
            LoginManager.setNumPreg(request, (LoginManager.getNumPregunta(request)+1));
            out.println("<form class=\"\" action=\"NewServletTest\" method=\"get\">");
            }else{
               out.println("<form class=\"\" action=\"NewServletFinTest\" method=\"get\">"); 
            }
            out.println("<input type=\"submit\" name=\"\" value=\"CONTINUAR\">");
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


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    /*Este método se usa para comprobar si una pregunta es o no correcta, 
    como argumentos le pasamos la sesión del usuario con para obtener el número de la pregunta
    que estamos comprobando, un String con la ruta del fichero y un entero con el número 
    de la respuesta seleccionada. La función devuelve un booleano con el que se indica,
    si la respuesta es correcta o no. */
    private static boolean validarPregunta(HttpServletRequest request,String pathFichero, int respuesta){
        boolean validacion=false;
        try (Scanner sc =new Scanner (new File(pathFichero))){    
            int pregunta =LoginManager.getNumPregunta(request);
            boolean igual=false;
            while(!igual && sc.hasNext()){
                int preg = new Integer(sc.next());
                if(preg==pregunta){
                    if(respuesta ==new Integer(sc.next()) )
                        validacion = true;
                }else{
                    sc.next();
                }
            }
            
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewServletComprobarPregunta.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return validacion;
    }
}

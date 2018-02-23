import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(urlPatterns = {"/NewServlet"})
public class NewServlet extends HttpServlet {

//Si el proceso de Login es correcto este metodo genera la pagina principal
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String user;
        if(LoginManager.getLoginName(request)!=null){//si hay una sesion iniciada cogemos el nombre del LoginManager
            user=LoginManager.getLoginName(request);
        }else{ // si no hay sesion iniciada lo cogemos del formulario
            user=request.getParameter("user");    
        }
        
        //generamos la pagina principal
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Formulario 1</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"indice.css\" />");
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Hola "+user+" <a href=\"NewServletLogout\">Desconectarse</a></p>");
            out.println("<p id=\"fecha\"> </p>");
            out.println("<script> document.getElementById('fecha').innerHTML='fecha de ultima modificacion: '+document.lastModified;</script>");
            out.println("<h1>Bienvenido al sistema tutor</h1>");
            out.println("<p>Este sistema evalua tus conocimientos de diferentes materias para que puedas comprobar lo bien o mal");
            out.println("preparado que estas. !Buena suerte con tus test!</p>");
            out.println("<h2>Test disponibles:  </h2>");
            out.println("<form class=\"\" action=\"NewServletTest\" method=\"get\">");
            out.println("<fieldset>");
            out.println("<p>Test 1: <input type=\"radio\" name=\"numero_test\" value=\"1\"> </p>");
            out.println("<p>Test 2: <input type=\"radio\" name=\"numero_test\" value=\"2\"> </p>");
            out.println("<p>Test 3: <input type=\"radio\" name=\"numero_test\" value=\"3\"> </p>");
            out.println("<input onClick=\"window.print()\"  type=\"submit\" name=\"Enviar\" value=\"Comenzar\">");
            out.println("</fieldset>");
            out.println("</form>");
            out.println("<div class=\"footer\">\n" +
"        <p id=top class=\"ftp\"> Pablo Garcia Acosta (Universidad de Málaga)</p>\n" +
"        <p class=\"ftp\"> Copyright &#169; Todos los derechos reservados <p>\n" +
"        </div>");
            out.println("</body>");
            out.println("</html>");

        }

    }
    //metodo que genera la pagina con el correspondiente error
    protected void genPagError(HttpServletRequest request, HttpServletResponse response) throws IOException{
                response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"indice.css\" />");
            out.println("        <script>\n" +
"            function validateForm() {\n" +
"                mensaje='';\n" +
"                flag_ok=true;\n" +
"                var x = document.formulario.user.value;\n" +
"                if (x == \"\") {\n" +
"                    mensaje = mensaje + \"Nombre de usuario no puede estar vacio \\n\";\n" +
"                    flag_ok = false;\n" +
"                }\n" +
"                var x = document.formulario.pass.value;\n" +
"                if (x == \"\") {\n" +
"                    mensaje = mensaje + \"La contrseña no puede estar vacia \\n\";\n" +
"                    flag_ok = false;\n" +
"                }\n" +
"                if (flag_ok == false) {\n" +
"                    alert(mensaje);\n" +
"                    return false;\n" +
"                  } else\n" +
"                    return true;\n" +
"            }\n" +
"</script>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div>");
            out.println(" <h1> Bienvenido a la aplicación tutor </h1>");
            out.println("<form name=\"formulario\" onsubmit=\"return validateForm()\"method=\"get\" action=\"NewServlet\"> ");
            out.println("<table>");
            out.println("<tr><td>Nombre ususario:</td> <td><input type=\"text\" name=\"user\" value=\"\" placeholder=\"Usuario\"></td> </tr>");
            out.println("<tr> <td>Contraseña:</td> <td><input type=\"password\" name=\"pass\" value=\"\"  placeholder=\"Contraseña\"></td></tr>");
            out.println("</table"); 
            out.println("<p class=\"error\">ERROR: nombtre de usuario y/o contraseña no valido</p>");
            out.println("<br>");
            out.println(" <input class=\"botonSubmit\" type=\"submit\" value=\"Entrar\">");
            out.println("</form><br>");
            out.println("<p>Si no estas registrado, registrate pinchando <a href=\"PagRegistro.html\">aquí </a></p>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String directorio_de_ejecucion_de_la_aplicacion = new java.io.File( "." ).getCanonicalPath();
        System.out.println("***************************************");
        System.out.println(directorio_de_ejecucion_de_la_aplicacion);
        System.out.println("*****************************************");
        //si ya hay alguna sesion iniciada va directamente al ProcessRequest que genera la pagina principal
        //esta situacion se puede sar si por ejemplo el usuario a terminado un test y quiere volver a la pagina principal
        if(LoginManager.getLoginName(request)!=null){
            processRequest(request,response);
        }else{ //si no la hay se recogen los datos del formualrio y se comprueban que son correctos
            String user = request.getParameter("user");
            String password  = request.getParameter("pass");
            boolean validate= validar(user,password);
            if(validate){//si los datos son correctos se genera una sesion y se genera la pagina de inicio
             LoginManager.login(request, user);
             processRequest(request,response);
            }else{//si no lo son se genera la pagina del Login pero con el mensaje de error
                genPagError(request, response);;
            }
        }
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    
    //Con este metodo validamos si lo datos introducidos son correctos comparandolos con los que hay en el fichero
    //con los usarios con sus contraseñas
       private boolean validar(String nom,String password) throws FileNotFoundException, IOException{
        File fich = new File("../ficheros/usuarios.txt");
        
        FileReader fr = new FileReader(fich);
		Scanner sc = new Scanner(fr);
		boolean find = false;
		String linea=nom+" "+password;
		String probar;
		while (!find && sc.hasNextLine()) {
			probar = sc.nextLine();
			if (linea.equals(probar)) {
				find = true;
			}
		}
		fr.close();
		sc.close();
		return find;
        
    }

}

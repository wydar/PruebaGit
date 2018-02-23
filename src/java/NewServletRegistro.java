

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Scanner;


@WebServlet(urlPatterns = {"/NewServletRegistro"})
public class NewServletRegistro extends HttpServlet {
    private int error;


    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
      //  recuperamos los datos obtenidos del formulario
        String user = request.getParameter("user");
        String pass1 = request.getParameter("pass1");
    
        registro(user,pass1);
        response.sendRedirect("index.html");
        
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
    
    //Este metodo generará la pagina si se ha producido algun error en el registro mostrando el mensaje correspondiente
    private void generarPagError(HttpServletResponse response) throws IOException{
        response.setContentType("text/html; charset=ISO-8859-1");
        PrintWriter out = response.getWriter();
        out.println();
         out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet NewServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Registro</h1>");;            
            out.println("<form method=\"get\" action=\"NewServletRegistro\">");
            out.println("<table>");
            out.println("<tr><td>Nombre ususario:</td> <td><input type=\"text\" name=\"user\" value=\"\" placeholder=\"Usuario\"></td></tr>");
            out.println("<tr><td>Contraseña:</td> <td><input type=\"password\" name=\"pass1\" value=\"\"  placeholder=\"Contraseña\"></td></tr>");
            out.println("<tr><td>Repite Contraseña: </td> <td><input type=\"password\" name=\"pass2\" value=\"\"  placeholder=\"Contraseña\"></td></tr>");
            out.println("</table>");
            out.println("<input type=\"submit\" value=\"Registro\">");
            out.println("</form>");
            
            //Dependiendo del error que se haya producido se muestra un mensaje u otro
            if(error!=-1){
                switch(error){
                    case 0:
                        out.println("<h2> ERROR: Ususario y contraseña obligatorios</h2>");
                        break;
                    case 1:
                        out.println("<h2>ERROR: Usuario obligatorio");
                        break;
                    case 2:
                        out.println("<h2>ERROR: Contraseña obligatoria<h2>");
                        break;
                    case 3:
                        out.println("<h2>ERROR: El usuario ya existe");
                        break;
                    default:
                        out.println("<h2>ERROR: La contraseña no coincide");
                        
                }
                error=-1;
            }

            out.println("</body>");
            out.println("</html>");
    }
    
    //con este metodo comprobamos si el usuario no está ya registrado
    public static boolean comprobar(String nom) throws IOException {
		File fich = new File("../ficheros/usuarios.txt");
                FileReader fr = new FileReader(fich);
		Scanner sc = new Scanner(fr);
		boolean find = false;
		
		String probar;
		while (!find && sc.hasNext()) {
			probar = sc.next();
			if (nom.equals(probar)) {
				find = true;
			}
                        probar=sc.next();
		}
		fr.close();
		sc.close();
		return find;
	}
    //metodo para registrar a alguien en el fichero
    public static void registro(String nom, String pass) throws IOException {
		File fich = new File("../ficheros/usuarios.txt");
		FileWriter fw = new FileWriter(fich,true);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.newLine();
		bw.write(nom+" "+pass);
		bw.close();
		fw.close();

	}
}

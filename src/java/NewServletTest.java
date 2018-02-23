import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/NewServletTest"})
public class NewServletTest extends HttpServlet {

    //En este metodo iremos generando la pagina con las preguntas
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        //aqui lo que hacemos es comprobar que test esta haciendo
        int numTest =-1;
        if(request.getParameter("numero_test")==null){
            numTest=LoginManager.getNumTest(request);
        }else{
            numTest = new Integer(request.getParameter("numero_test"));
            LoginManager.setNumTest(request, numTest);
        }
        

        String fichPreg="";
        String fichResp="";
        
        //Dependiendo del test seleccionado se abrira uno u otro
        //En este caso como solo tengo un test preparado aunque aparezcan 3 casos diferentes
        //se abrira siempre el mismo test
        switch(numTest){
            case 1:
                fichPreg="../ficheros/Test1.txt";
                fichResp="../ficheros/Respuestas1.txt";
                break;
            case 2:
                fichPreg="../ficheros/Test1.txt";
                fichResp="../ficheros/Respuestas1.txt";
                break;
            case 3:
                fichPreg="../ficheros/Test1.txt";
                fichResp="../ficheros/Respuestas1.txt";
                break;      
        }
        File preguntas = new File(fichPreg);
        File respuestas = new File(fichResp);
        
        //Vemos porque pregunta vamos, si es la primera la ponemos nosotros
        int numPreg=LoginManager.getNumPregunta(request);
        if(numPreg==-1){
            numPreg=1;
            LoginManager.setNumPreg(request, numPreg);
        }
        
        
        //Bucamos la pregunta que queremos mostrar
        String [] pregunta=buscadorPregunta(preguntas,numPreg);
        
        /*Guardamos en la sesion la respuesta correcta a la pregunta que usaremos  mas adelante en 
         NewServletComprobarPregunta para mostrar la respuesta correcta en el caso de que se haya contestado de forma incorrecta  */            
        LoginManager.setRespCorrecta(request, getRespuestaCorrecta(fichResp,pregunta,numPreg));
        
        
        //Con los datos ya procesados generamos la pagina con las preguntas
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"indice.css\" />");
            out.println("<title>Test"+numTest+"</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<p>Alumno: "+LoginManager.getLoginName(request)+"</p>");
            out.println("<h1>Pregunta "+numPreg+"</h1>");
            out.println("<form class=\"\" action=\"NewServletComprobarPregunta\" method=\"get\">");
            out.println("<p id=\"pregunta\">"+pregunta[0]+"</p>");//Aqui ponemos la pregunta en si
            out.println("<p><input type=\"radio\" name=\"preg"+numPreg+"\" value=\"1\">"+pregunta[1]+" </p>");//aqui la respuesta 1
            out.println("<p><input type=\"radio\" name=\"preg"+numPreg+"\" value=\"2\">"+pregunta[2]+" </p>");//aqui la repsuesta 2
            out.println("<p><input type=\"radio\" name=\"preg"+numPreg+"\" value=\"3\">"+pregunta[3]+" </p>");//aqui la respuesta 3
            out.println("<input type=\"submit\" name=\"\" value=\"Siguente\">");
            out.println("</form>");
            out.println("<form class=\"\" action=\"NewServletFinTest\" method=\"get\">");
            out.println("<input id=\"cancelar\" type=\"submit\" name=\"\" value=\"Cancelar Test\">");
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

       if(request.getParameter("numero_test")!=null){//con esto lo que hacemos es inicializar el numero de aciertos si el test acaba de empezar
           LoginManager.setNumTest(request, new Integer(request.getParameter("numero_test")));
           LoginManager.setNumAciertos(request, -1);
       }
        
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
 
    /*Con este metodo lo que hacemos  es buscar que pregunta tenemos que mostrar en la pagina web
      y cuando la encuentra lo que hace es devolver y array de String con 4 elementos: 
        -Posicion 0: La pregunta
        -Posicion 1: La primera respuesta
        -Posicion 2: La segunda respuesta
        -Posicion 3: La tercera respuesta 
    como argumentos recibe el fichero donde estan todas las preguntas y el numero de la pregunta que se quiere
    */
private static String [] buscadorPregunta (File fich,int num) throws IOException {
		String [] sol = new String [4];
		try(Scanner sc = new Scanner(fich);){
			int i=0;
			while(i<num-1 && sc.hasNextLine()) {
				sc.nextLine();
				sc.nextLine();
				sc.nextLine();
				sc.nextLine();
				i++;
				
			}
			
			sol[0]=sc.nextLine();
			sol[1]=sc.nextLine();
			sol[2]=sc.nextLine();
			sol[3]=sc.nextLine();
			
			System.out.println(sol[0]);
			System.out.println(sol[1]);
			System.out.println(sol[2]);
			System.out.println(sol[3]);

		}catch(NoSuchElementException e) {
			System.out.println("**FIN**");
		}
		return sol;
	}

/*con este metodo lo que hacemos es encontrar cual es la respuesta correcta de una pregunta
como argumentos se recibe el paht del fichero donde se encuentras las respuestas
y el array de String que se consigue con el metodo buscadorPregunta.
*/
private static String getRespuestaCorrecta(String pathFichero,String [] respuestas,int numPreg){
    //numPreg los valores validos son del 1 al 10
    int correcta=0;
    try(Scanner sc=new Scanner(new File(pathFichero))){
        boolean find=false;
        int preg=0;       
        while(sc.hasNext() && !find){
            preg=sc.nextInt();
            if(preg==numPreg){
                correcta=sc.nextInt();
                find=true;
            }else{
                sc.next();
            }
        }
    }catch(IOException e){
        
    }
    return respuestas[correcta];
}
}

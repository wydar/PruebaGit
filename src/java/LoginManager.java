
/*
* To change this template, choose Tools | Templates
* and open the template in the editor.
*/

/**
*
* @author Lidia Fuentes
*/
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

//Esta clase no esta explicada puesto que es igual que la que se expuso como ejemplo en clase, con la
//diferencia de que tiene mas argumentos que guardar. Los argumentos que guardan estan explicados en la memoria
public final class LoginManager {
  private final static String LOGIN_NAME_ATTRIBUTE ="user";
  private final static String NUM_TEST_ATTRIBUTE="test";
  private final static String NUM_PREGUNTA_ATTRIBUTE="pregunta";
  private final static String RESP_CORRECTA_ATTRIBUTE="correcta";
  private final static String NUM_ACIERTOS_ATTRIBUTE="aciertos";
  
  
  private LoginManager() {}
    public final static void login(HttpServletRequest request,String loginName) {
      HttpSession session = request.getSession(true);
      // deprecated, sustituida por setAttribute
      session.setAttribute(LOGIN_NAME_ATTRIBUTE, loginName);
      session.setAttribute(NUM_PREGUNTA_ATTRIBUTE,-1);
    }
    public final static void logout(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session != null) {
        session.invalidate();
      }
    }
    public final static String getLoginName(HttpServletRequest request) {
      HttpSession session = request.getSession(false);
      if (session == null) {
        return null;
      } else {
        // deprecated, sustituida por getAttribute
        return (String) session.getAttribute(LOGIN_NAME_ATTRIBUTE);
      }
    }
    public final static void setNumTest(HttpServletRequest request,int num){
      HttpSession session = request.getSession(false);
      if(session!=null)
      session.setAttribute(NUM_TEST_ATTRIBUTE, num);
    }
    
    public final static int getNumTest(HttpServletRequest request){
      HttpSession session = request.getSession(false);
      int num=-1;
      if(session!=null)
      num = (int) session.getAttribute(NUM_TEST_ATTRIBUTE);
      return num;
    }
    
    public final static void setNumPreg(HttpServletRequest request, int num){
      HttpSession session = request.getSession(false);
      if(session!=null)
      session.setAttribute(NUM_PREGUNTA_ATTRIBUTE, num);
    }
    
    public final static int getNumPregunta(HttpServletRequest request){
      HttpSession session = request.getSession(false);
      int num=-1;
      if(session!=null)
      num = (int) session.getAttribute(NUM_PREGUNTA_ATTRIBUTE);
      return num;
    }
    
    public final static void setNumAciertos(HttpServletRequest request, int aciertos){
      HttpSession session = request.getSession(false);
      if(session!=null)
      session.setAttribute(NUM_ACIERTOS_ATTRIBUTE, aciertos);
    }
    
    public final static int getNumAciertos(HttpServletRequest request){
      HttpSession session = request.getSession(false);
      int aciertos=-1;
      if(session!=null)   
      aciertos = (int) session.getAttribute(NUM_ACIERTOS_ATTRIBUTE);
      return aciertos;
    }
    
    public final static void setRespCorrecta(HttpServletRequest request, String correcta){
      HttpSession session = request.getSession(false);
      if(session!=null)
      session.setAttribute(RESP_CORRECTA_ATTRIBUTE, correcta);
    }
    
    public final static String getRespCorrecta(HttpServletRequest request){
      HttpSession session = request.getSession(false);
      String respuesta="";
      if(session!=null)
      respuesta = (String) session.getAttribute(RESP_CORRECTA_ATTRIBUTE);
      return respuesta;
    }
  }


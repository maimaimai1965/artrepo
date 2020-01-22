package ua.mai.art;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class ArtSessionListener  implements HttpSessionListener {

  public void sessionCreated(HttpSessionEvent sessionEvent) {
    System.out.println("Session Created:: ID=" + sessionEvent.getSession().getId());
  }

  public void sessionDestroyed(HttpSessionEvent sessionEvent) {
    System.out.println("Session Destroyed:: ID=" + sessionEvent.getSession().getId());
  }

}

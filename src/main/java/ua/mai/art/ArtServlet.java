package ua.mai.art;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static java.lang.System.out;

@WebServlet(
        urlPatterns = {"/*"},
        name = "Art servlet Example",
        description = "Art Servlet Using Annotations"
//        loadOnStartup = 1
)
public class ArtServlet extends
                                 HttpServlet {
//                                 DispatcherServlet {
  private static final long serialVersionUID = 1L;

  @Override
  public void init() throws ServletException {
    super.init();
  }
//  @Override
//  protected void doService(HttpServletRequest request, HttpServletResponse response) throws Exception {
//   super.doService(request, response);
//  }

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    ServletContext ctx = request.getServletContext();
//    ctx.setAttribute("User", "Pankaj");
//    String user = (String) ctx.getAttribute("User");
//    ctx.removeAttribute("User");

//    HttpSession session = request.getSession();
//    session.invalidate();

//    PrintWriter out = response.getWriter();
    response.getWriter().append("\nHi!!!\n");
  }

}

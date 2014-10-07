package at.technikum.wien.clad.gae.todo.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.technikum.wien.clad.gae.todo.dao.Dao;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class CreateUserServiceImpl extends HttpServlet {
	  /**
	 * 
	 */
	private static final long serialVersionUID = -126809189049592815L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
	      throws IOException {
	    System.out.println("Creating new user ");
	 
	    String name = checkNull(req.getParameter("name"));
	    String email = checkNull(req.getParameter("email"));
	    String password = checkNull(req.getParameter("password"));

	    Dao.INSTANCE.addUser(name, email, password);

	    resp.sendRedirect("/TodoApplication.jsp");
	  }

	  private String checkNull(String s) {
	    if (s == null) {
	      return "";
	    }
	    return s;
	  }
	} 
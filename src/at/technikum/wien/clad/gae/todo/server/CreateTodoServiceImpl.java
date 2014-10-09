package at.technikum.wien.clad.gae.todo.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.technikum.wien.clad.gae.todo.dao.Dao;

@SuppressWarnings("serial")
public class CreateTodoServiceImpl extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("Creating new todo ");
		String user = (String) req.getParameter("selecteduser");
		String summary = checkNull(req.getParameter("summary"));
		String longDescription = checkNull(req.getParameter("description"));
		String url = checkNull(req.getParameter("url"));
		Integer importance = Integer.parseInt(req.getParameter("importance"));

		Dao.INSTANCE.add(user, summary, longDescription, url, importance);

		resp.sendRedirect("/TodoApplication.jsp");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
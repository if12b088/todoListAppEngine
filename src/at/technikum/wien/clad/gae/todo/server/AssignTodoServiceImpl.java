package at.technikum.wien.clad.gae.todo.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.technikum.wien.clad.gae.todo.dao.Dao;

@SuppressWarnings("serial")
public class AssignTodoServiceImpl extends HttpServlet {

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		System.out.println("Assign todo ");
		Long id = Long.parseLong(req.getParameter("id"));
		String user = checkNull((String) req.getParameter("reassign"));

		Dao.INSTANCE.updateAuthor(id, user);

		resp.sendRedirect("/TodoApplication.jsp");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
}
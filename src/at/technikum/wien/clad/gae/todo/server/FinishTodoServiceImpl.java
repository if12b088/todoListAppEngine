package at.technikum.wien.clad.gae.todo.server;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import at.technikum.wien.clad.gae.todo.dao.Dao;

public class FinishTodoServiceImpl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		String id = req.getParameter("id");
		Dao.INSTANCE.finish(Long.parseLong(id));
		resp.sendRedirect("/TodoApplication.jsp");
	}
}
<%@page import="java.util.Collection"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.Set"%>
<%@ page import="com.google.appengine.api.users.User"%>
<%@ page import="com.google.appengine.api.users.UserService"%>
<%@ page import="com.google.appengine.api.users.UserServiceFactory"%>
<%@ page import="at.technikum.wien.clad.gae.todo.model.Todo"%>
<%@ page import="at.technikum.wien.clad.gae.todo.dao.Dao"%>

<!DOCTYPE html>


<%@page import="java.util.ArrayList"%>

<html>
<head>
<title>Todos</title>
<link rel="stylesheet" type="text/css" href="css/main.css" />
<meta charset="utf-8">
</head>
<body>
	<%
		Dao dao = Dao.INSTANCE;

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(request.getRequestURI());
		String urlLinktext = "Login";
		List<Todo> myTodos = new ArrayList<Todo>();
		List<Todo> otherTodos = new ArrayList<Todo>();
		List<Todo> myFinishedTodos = new ArrayList<Todo>();
		Set<String> users = dao.getAllUsers();

		if (user != null) {
			url = userService.createLogoutURL(request.getRequestURI());
			urlLinktext = "Logout";
			myTodos = dao.getTodosByEmail(user.getEmail());
			otherTodos = dao.getOtherTodos(user.getEmail());
			myFinishedTodos = dao.getFinishedTodosByEmail(user.getEmail());
			users.add(user.getEmail());
		}

		List<Integer> importance = new ArrayList<Integer>();
		importance.add(1);
		importance.add(2);
		importance.add(3);
		importance.add(4);
		importance.add(5);
		importance.add(6);
		importance.add(7);
		importance.add(8);
		importance.add(9);
		importance.add(10);
	%>
	<div style="width: 100%;">
		<div class="line"></div>
		<div class="topLine">
			<div style="float: left;">
				<img src="images/todo.png" />
			</div>
			<div style="float: left;" class="headline">Todos</div>
			<div style="float: right;">
				<a href="<%=url%>"><%=urlLinktext%></a>
				<%=(user == null ? "" : user.getNickname())%></div>
		</div>
	</div>

	<div style="clear: both;" />
	You have a total number of
	<%=myTodos.size()%>
	Todos.
	<br />

	<table>
		<tr>
			<th>Short description</th>
			<th>Long Description</th>
			<th>URL</th>
			<th>Done</th>
			<th>Reassign</th>
		</tr>

		<%
			for (Todo todo : myTodos) {
		%>
		<tr>
			<td><%=todo.getShortDescription()%></td>
			<td><%=todo.getLongDescription()%></td>
			<td><%=todo.getUrl()%></td>
			<td>
				<form action="/done" method="get" accept-charset="utf-8">
					<input type="hidden" value="<%=todo.getId()%>" name="id">
					<input type="submit" value="Done" />
				</form>
			</td>
			<td>
				<form action="/assign" method="post" accept-charset="utf-8">
					<select name="reassign">
						<%
							for (String u : users) {
						%>
						<option><%=u%></option>
						<%
							}
						%>
					</select>
					<input name="id" type="hidden" value="<%=todo.getId()%>" >
					<input type="submit" value="Reassign" />
				</form>
			</td>
		</tr>
		<%
			}
		%>
	</table>

	Other have a total number of
	<%=otherTodos.size()%>
	Todos.

	<table>
		<tr>
			<th>Owner</th>
			<th>Short description</th>
			<th>Long Description</th>
			<th>URL</th>
			<th>Reassign</th>
		</tr>

		<%
			for (Todo todo : otherTodos) {
		%>
		<tr>
			<td><%=todo.getAuthor()%></td>
			<td><%=todo.getShortDescription()%></td>
			<td><%=todo.getLongDescription()%></td>
			<td><%=todo.getUrl()%></td>
			<td>
				<form action="/assign" method="post" accept-charset="utf-8">
					<select name="reassign">
						<%
							for (String u : users) {
						%>
						<option><%=u%></option>
						<%
							}
						%>
					</select>
					<input name="id" type="hidden" value="<%=todo.getId()%>" >
					<input type="submit" value="Reassign" />
				</form>
			</td>
		</tr>
		<%
			}
		%>
	</table>

	Your finished have a total number of
	<%=myFinishedTodos.size()%>
	Todos.

	<table>
		<tr>
			<th>Short description</th>
			<th>Long Description</th>
			<th>URL</th>
			<th>Importance</th>
			<th>Creation Date</th>
			<th>Finish Date</th>
		</tr>

		<%
			for (Todo todo : myFinishedTodos) {
		%>
		<tr>
			<td><%=todo.getShortDescription()%></td>
			<td><%=todo.getLongDescription()%></td>
			<td><%=todo.getUrl()%></td>
			<td><%=todo.getImportance()%></td>
			<td><%=todo.getCreateDate()%></td>
			<td><%=todo.getFinishDate()%></td>
		</tr>
		<%
			}
		%>
	</table>

	<hr />

	<div class="main">

		<div class="headline">New todo</div>

		<%
			if (user != null) {
		%>

		<form action="/new" method="post" accept-charset="utf-8">
			<table>
				<tr>
					<td><label for="summary">Summary</label></td>
					<td><input type="text" name="summary" id="summary" size="65" /></td>
				</tr>
				<tr>
					<td><label for="description">Description</label></td>
					<td><textarea rows="4" cols="50" name="description"
							id="description"></textarea></td>
				</tr>
				<tr>
					<td valign="top"><label for="url">URL</label></td>
					<td><input type="url" name="url" id="url" size="65" /></td>
				</tr>
				<tr>
					<td><label for="user">Assign user</label></td>
					<td><select name="selecteduser">
							<%
								for (String u : users) {
							%>
							<option><%=u%></option>
							<%
								}
							%>
					</select></td>
				</tr>
				<tr>
					<td><label for="importance">Importance</label></td>
					<td><select name="importance">
							<%
								for (int i : importance) {
							%>
							<option><%=i%></option>
							<%
								}
							%>
					</select></td>
				</tr>
				<tr>
					<td colspan="2" align="right"><input type="submit"
						value="Create" /></td>
				</tr>
			</table>
		</form>

		<%
			} else {
		%>

		Please login with your Google account

		<%
			}
		%>
	</div>
</body>
</html>

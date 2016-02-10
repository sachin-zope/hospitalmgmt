package hospitalmgmt.servlets;

import hospitalmgmt.dao.LoginDAO;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    LoginDAO loginDAO = new LoginDAO();
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		System.out.println("login request for " + username + "/" + password);
		try {
			if(loginDAO.validate(username, password)) {
				response.sendRedirect("indoor_register.jsp");
			} else {
				response.sendRedirect("index.html");
			}
		} catch(Exception e) {
			request.setAttribute("LOGIN_ERROR", "Username or Password is invalid. Please try again!");
		}
	}

}

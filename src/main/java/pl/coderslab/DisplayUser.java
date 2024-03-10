package pl.coderslab;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import pl.coderslab.entity.*;

@WebServlet(name = "DisplayUser", value = "/user/display")
public class DisplayUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("id"));
        UserDAO userDAO = new UserDAO();
        User user = userDAO.read(userId);
        request.setAttribute("user", user);
        request.getRequestDispatcher("/userDisplayView.jsp").forward(request, response);
    }
}
package pl.coderslab;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import java.io.IOException;
import pl.coderslab.entity.*;

@WebServlet(name = "DeleteUser", value = "/user/delete")
public class DeleteUser extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDAO userDAO = new UserDAO();
        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.delete(userId);
        response.sendRedirect("/user/list");
    }
}
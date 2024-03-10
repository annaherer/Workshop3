package pl.coderslab;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.coderslab.entity.*;

import java.io.IOException;

@WebServlet(name = "AddUser", value = "/user/add")
public class AddUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/userAddView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName = request.getParameter("name").trim();
        String userEmail = request.getParameter("email").trim();
        String userPassword = request.getParameter("password");

        String error = "";

        if (userName.isEmpty()) {
            error = "User name cannot be blank.";
        } else if (userEmail.isEmpty()) {
            error = "Email address cannot be blank.";
        } else if (userPassword.isEmpty()) {
            error = "Password cannot be blank.";
        }

        if (error.isEmpty()) {
            User user = new User(userName, userEmail, userPassword);
            UserDAO userDAO = new UserDAO();
            userDAO.create(user);
            response.sendRedirect("/user/list");
        }
        else {
            request.setAttribute("error", error);
            request.getRequestDispatcher("/userAddView.jsp").forward(request, response);
        }
    }
}
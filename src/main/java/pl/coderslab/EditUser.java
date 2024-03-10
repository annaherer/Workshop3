package pl.coderslab;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import pl.coderslab.entity.*;

import java.io.IOException;

@WebServlet(name = "EditUser", value = "/user/edit")
public class EditUser extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtaining user details from database and passing to Edit view.
        int userId = Integer.parseInt(request.getParameter("id"));
        UserDAO userDAO = new UserDAO();
        User user = userDAO.read(userId);
        request.setAttribute("user", user);
        System.out.println(user.getUserName());
        request.getRequestDispatcher("/userEditView.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtaining information from the form, retrieving user ID from request parameters (this was earlier set in doGet method)
        int userId = Integer.parseInt(request.getParameter("id"));
        String userName = request.getParameter("name").trim();
        String userEmail = request.getParameter("email").trim();
        String oldPassword = request.getParameter("oldPassword");
        String userPassword = request.getParameter("password");

        String error = "";

        // Checking only username and email. Password can be blank and this means that there is no intention to update password
        if (userName.isEmpty()) {
            error = "User name cannot be blank.";
        } else if (userEmail.isEmpty()) {
            error = "Email address cannot be blank.";
        }

        if (error.isEmpty()) {
            // Amending username and email based on form entry and using old password in hashed form
            User user = new User (userId, userName, userEmail, oldPassword);

            // Updating user in database - at this point only name and email, password will be dealt with separately
            UserDAO userDAO = new UserDAO();
            userDAO.update(user);

            // Updating password only if entry was made be the user. updatePassword method will perform hashing.
            if (!userPassword.isEmpty()) {
                userDAO.updatePassword(userId, userPassword);
            }

            // Redirect to user list
            response.sendRedirect("/user/list");
        }
        else {
            // Return to edit view with error message
            request.setAttribute("error", error);
            request.getRequestDispatcher("/userEditView.jsp").forward(request, response);
        }
    }
}
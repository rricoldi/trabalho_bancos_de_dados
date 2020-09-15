package controller;

import dao.DAOFactory;
import dao.UsuarioDAO;
import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;


@WebServlet(name = "UserLoginController", 
    urlPatterns = {
        "",
        "/login",
        "/logout"
})
public class UserLoginController extends HttpServlet {
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session;
        RequestDispatcher dispatcher;

        switch (request.getServletPath()) {
            case "": {
                session = request.getSession(false);

                if (session != null && session.getAttribute("usuario") != null) {
                    dispatcher = request.getRequestDispatcher("/welcome.jsp");
                } else {
                    dispatcher = request.getRequestDispatcher("/index.jsp");
                }

                dispatcher.forward(request, response);

                break;
            }
            
            case "/logout": {
                session = request.getSession(false);

                if (session != null) {
                    session.invalidate();
                }

                response.sendRedirect(request.getContextPath() + "/");
            }
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsuarioDAO dao;
        Usuario usuario = new Usuario();
        HttpSession session = request.getSession();

        switch (request.getServletPath()) {
            case "/login":
                usuario.setEmail(request.getParameter("email"));
                usuario.setSenha(request.getParameter("senha"));

                try (DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUsuarioDAO();

                    dao.authenticate(usuario);

                    session.setAttribute("usuario", usuario);
                } catch (ClassNotFoundException | IOException | SQLException | SecurityException ex) {
                    session.setAttribute("error", ex.getMessage());
                }

                response.sendRedirect(request.getContextPath() + "/");
        }  
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}

package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Comentario;

@WebServlet(name = "ComentarioController", 
    urlPatterns = {
        "/comentario/create",
    }
)

public class ComentarioController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ComentarioController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ComentarioController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

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
        DAO<Comentario> dao;
        Comentario comentario;

        RequestDispatcher dispatcher;
        
        switch(request.getServletPath()) {  
            case "/comentario/create":
                dispatcher = request.getRequestDispatcher("/view/comentario/create.jsp");
                dispatcher.forward(request, response);
                break;            
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
        DAO<Comentario> dao;
        Comentario comentario = new Comentario();
        
        HttpSession session = request.getSession();
        
        String servletPath = request.getServletPath();
        
        switch(request.getServletPath()) {                
            case "/comentario/create":
                comentario.setRss_feed(request.getParameter("rss_feed"));
                comentario.setEmail(request.getParameter("email"));
                comentario.setComentario(request.getParameter("comentario"));
                
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getComentarioDAO();
                        dao.create(comentario);                        
                    response.sendRedirect(request.getContextPath() + "/podcast");
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    System.err.print(ex);
                    response.sendRedirect(request.getContextPath() + servletPath);
                }
                break;        
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

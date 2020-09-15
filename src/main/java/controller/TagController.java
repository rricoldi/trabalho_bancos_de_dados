package controller;

import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Tag;

@WebServlet(name = "TagController", 
    urlPatterns = {
        "/tag/create",
    }
)

public class TagController extends HttpServlet {

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
            out.println("<title>Servlet TagController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet TagController at " + request.getContextPath() + "</h1>");
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
        DAO<Tag> dao;
        Tag tag;

        RequestDispatcher dispatcher;
        
        switch(request.getServletPath()) {  
            case "/tag/create":
                dispatcher = request.getRequestDispatcher("/view/tag/create.jsp");
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
        DAO<Tag> dao;
        Tag tag = new Tag();
        
        HttpSession session = request.getSession();
        
        String servletPath = request.getServletPath();
        
        switch(request.getServletPath()) {                
            case "/tag/create":
                tag.setRss_feed(request.getParameter("rss_feed"));
                tag.setTag(request.getParameter("tag"));
                
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getTagDAO();
                        dao.create(tag);                        
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

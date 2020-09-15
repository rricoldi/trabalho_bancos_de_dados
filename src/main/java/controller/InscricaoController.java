package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DAO;
import dao.DAOFactory;
import dao.InscricaoDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Inscricao;

@WebServlet(name = "InscricaoController", urlPatterns = {
    "/inscricao/create",
    "/inscricao/isSubscribed",
    "/inscricao/update",
    "/inscricao/cancelSubscription",
})
public class InscricaoController extends HttpServlet {

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
            out.println("<title>Servlet InscricaoController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet InscricaoController at " + request.getContextPath() + "</h1>");
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
        DAO<Inscricao> dao;
        Inscricao inscricao;

        RequestDispatcher dispatcher;
        
        switch(request.getServletPath()) {
            case "/inscricao/isSubscribed":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    InscricaoDAO inscricaoDao = daoFactory.getInscricaoDAO();
                    
                    boolean estaInscrito = inscricaoDao.isSubscribed(request.getParameter("rss_feed"), request.getParameter("email"));
                    
                    response.getOutputStream().print(estaInscrito);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/podcast");
                }
                break;
                
            case "/inscricao/cancelSubscription":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    InscricaoDAO inscricaoDao = daoFactory.getInscricaoDAO();
                    
                    inscricaoDao.cancelSubscription(request.getParameter("rss_feed"), request.getParameter("email"));
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                response.sendRedirect(request.getContextPath() + "/podcast");
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
        DAO<Inscricao> dao;
        Inscricao inscricao;

        RequestDispatcher dispatcher;
        switch(request.getServletPath()) {        
            case "/inscricao/create":
            case "/inscricao/update":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getInscricaoDAO();
                    inscricao = new Inscricao();
                    inscricao.setRss_feed(request.getParameter("rss_feed"));
                    inscricao.setEmail(request.getParameter("email"));
                    inscricao.setClassificacao(0);
                    if(request.getServletPath().equals("/inscricao/create")) {
                        dao.create(inscricao);
                    } else {
                        dao.update(inscricao);
                    }
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                response.sendRedirect(request.getContextPath() + "/podcast");
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

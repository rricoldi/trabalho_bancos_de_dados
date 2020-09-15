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
import model.Podcast;

@WebServlet(name = "PodcastController", 
    urlPatterns = {
        "/podcast",
        "/podcast/create",
        "/podcast/read",
        "/podcast/update",
        "/podcast/delete",
    }
)

public class PodcastController extends HttpServlet {

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
            out.println("<title>Servlet PodcastController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PodcastController at " + request.getContextPath() + "</h1>");
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
        DAO<Podcast> dao;
        Podcast podcast;

        RequestDispatcher dispatcher;
        
        switch(request.getServletPath()) {
            case "/podcast":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getPodcastDAO();

                    List<Podcast> podcastList = dao.all();
                    request.setAttribute("podcastList", podcastList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                dispatcher = request.getRequestDispatcher("/view/podcast/index.jsp");
                dispatcher.forward(request, response);
                break;
                
            case "/podcast/create":
                dispatcher = request.getRequestDispatcher("/view/podcast/create.jsp");
                dispatcher.forward(request, response);
                break;
            
            case "/podcast/read":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getPodcastDAO();

                    podcast = dao.read(request.getParameter("rss_feed"));
                    
                    Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(podcast);
                    
                    response.getOutputStream().print(json);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/podcast");
                }
                break;
             
            case "/podcast/update":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getPodcastDAO();

                    podcast = dao.read(request.getParameter("rss_feed"));
                    request.setAttribute("podcast", podcast);
                    
                    dispatcher = request.getRequestDispatcher("/view/podcast/update.jsp");
                    dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/podcast");
                }
                break;
            case "/podcast/delete":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getPodcastDAO();
                    dao.delete(request.getParameter("rss_feed"));
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
        DAO<Podcast> dao;
        Podcast podcast = new Podcast();
        
        HttpSession session = request.getSession();
        
        String servletPath = request.getServletPath();
        
        switch(request.getServletPath()) {                
            case "/podcast/create":
            case "/podcast/update":
                podcast.setRss_feed(request.getParameter("rss_feed"));
                podcast.setNome(request.getParameter("nome"));
                podcast.setSite(request.getParameter("site"));
                
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getPodcastDAO();
                    if(servletPath.equals("/podcast/create")) {
                        dao.create(podcast);                        
                    } else {
                        dao.update(podcast);
                    }
                    response.sendRedirect(request.getContextPath() + "/podcast");
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + servletPath);
                }
                break;
            case "/podcast/delete":
                String podcasts[] = request.getParameterValues("delete");
                
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getPodcastDAO();
                    try {
                        daoFactory.beginTransaction();
                        
                        for(String podcastRss_feed : podcasts) {
                            dao.delete(podcastRss_feed);
                        }
                        
                        daoFactory.commitTransaction();
                        daoFactory.endTransaction();
                    } catch(SQLException ex) {
                        request.getSession().setAttribute("error", ex.getMessage());
                        daoFactory.rollbackTransaction();
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

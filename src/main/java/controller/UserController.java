/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dao.DAO;
import dao.DAOFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Usuario;

/**
 *
 * @author renan
 */
@WebServlet(name = "UserController", 
        urlPatterns = {
            "/user", 
            "/user/create",
            "/user/read",
            "/user/update",
            "/user/delete"
        })
public class UserController extends HttpServlet {

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
            out.println("<title>Servlet UserController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UserController at " + request.getContextPath() + "</h1>");
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
        DAO<Usuario> dao;
        Usuario usuario;
        
        RequestDispatcher dispatcher;
        
        switch(request.getServletPath()) {
            case "/user":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUsuarioDAO();

                    List<Usuario> usuarioList = dao.all();
                    request.setAttribute("usuarioList", usuarioList);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                dispatcher = request.getRequestDispatcher("/view/user/index.jsp");
                dispatcher.forward(request, response);
                break;
                
            case "/user/create":
                dispatcher = request.getRequestDispatcher("/view/user/create.jsp");
                dispatcher.forward(request, response);
                break;
            
            case "/user/read":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUsuarioDAO();

                    usuario = dao.read(request.getParameter("email"));
                    
                    Gson gson = new GsonBuilder().create();
                    String json = gson.toJson(usuario);
                    
                    response.getOutputStream().print(json);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user");
                }
                break;
             
            case "/user/update":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUsuarioDAO();

                    usuario = dao.read(request.getParameter("email"));
                    request.setAttribute("usuario", usuario);
                    
                    dispatcher = request.getRequestDispatcher("/view/user/update.jsp");
                    dispatcher.forward(request, response);
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + "/user");
                }
                break;
            case "/user/delete":
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUsuarioDAO();
                    dao.delete(request.getParameter("email"));
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                }
                response.sendRedirect(request.getContextPath() + "/user");
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
                
        DAO<Usuario> dao;
        Usuario usuario = new Usuario();
        
        HttpSession session = request.getSession();
        
        String servletPath = request.getServletPath();
        
        switch(request.getServletPath()) {                
            case "/user/create":
            case "/user/update":
                usuario.setEmail(request.getParameter("email"));
                usuario.setSenha(request.getParameter("senha"));
                usuario.setNome(request.getParameter("nome"));
                usuario.setIdade(Integer.parseInt(request.getParameter("idade")));
                usuario.setSexo(request.getParameter("sexo"));
                usuario.setPais(request.getParameter("pais"));       
                
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUsuarioDAO();
                    if(servletPath.equals("/user/create")) {
                        dao.create(usuario);                        
                    } else {
                        dao.update(usuario);
                    }
                    response.sendRedirect(request.getContextPath() + "/user");
                } catch (ClassNotFoundException | IOException | SQLException ex) {
                    request.getSession().setAttribute("error", ex.getMessage());
                    response.sendRedirect(request.getContextPath() + servletPath);
                }
                break;
            case "/user/delete":
                String usuarios[] = request.getParameterValues("delete");
                
                try ( DAOFactory daoFactory = DAOFactory.getInstance()) {
                    dao = daoFactory.getUsuarioDAO();
                    try {
                        daoFactory.beginTransaction();
                        
                        for(String usuarioEmail : usuarios) {
                            dao.delete(usuarioEmail);
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
                response.sendRedirect(request.getContextPath() + "/user");
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

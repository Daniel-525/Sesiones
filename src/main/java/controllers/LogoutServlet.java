/*
 * Autor: Cristian Arias
 * Fecha y version: 13/11/2025  |  Version: 1.0
 * Descripcion: Servlet que gestiona el cierre de sesión del usuario.
 * Si existe una sesión activa, la invalida y redirige al usuario
 * a la página de login.
 */

package controllers;

// Importa clases de Jakarta Servlet para manejar solicitudes HTTP
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Importa servicios de login para validar la sesión
import services.LoginService;
import services.LoginServiceSessionImplement;

// Importa utilidades de Java
import java.io.IOException;
import java.util.Optional;

// Define la URL a la que responderá este servlet
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    // Método que maneja solicitudes GET para cerrar sesión
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Crea una instancia del servicio de login
        LoginService auth = new LoginServiceSessionImplement();

        // Obtiene el username de la sesión, si existe
        Optional<String> username = auth.getUsername(req);

        // Si hay un usuario logueado
        if (username.isPresent()) {
            // Obtiene la sesión del usuario
            HttpSession session = req.getSession();
            // Invalida la sesión, eliminando todos sus atributos
            session.invalidate();
        }

        // Redirige al usuario a la página de login
        resp.sendRedirect(req.getContextPath() + "/login.html");
    }
}

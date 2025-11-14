/*
 * Autor: Cristian Arias
 * Fecha y version: 13/11/2025  |  Version: 1.0
 * Descripcion: Servlet que gestiona el login de usuarios.
 * Permite iniciar sesión validando usuario y contraseña, mantener
 * la sesión activa mediante HttpSession y mostrar mensajes HTML
 * dependiendo del estado de autenticación.
 */

package controllers;

// Importa clases de Jakarta Servlet para manejar solicitudes HTTP
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// Importa servicios de login para validar usuarios
import services.LoginService;
import services.LoginServiceSessionImplement;

// Importa clases de utilidades de Java
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

// Define las URLs a las que responderá este servlet
@WebServlet({"/login", "/login.html"})
public class LoginServlet extends HttpServlet {

    // Constantes que almacenan el usuario y contraseña válidos
    final static String USERNAME = "admin";
    final static String PASSWORD = "12345";

    // Método que maneja solicitudes GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Crea una instancia del servicio de login
        LoginService auth = new LoginServiceSessionImplement();
        // Obtiene el username de la sesión, si existe
        Optional<String> usernameOptional = auth.getUsername(req);

        // Si el usuario ya está logueado
        if (usernameOptional.isPresent()) {

            // Configura el tipo de contenido como HTML con codificación UTF-8
            resp.setContentType("text/html;charset=UTF-8");

            // Usa try-with-resources para abrir PrintWriter y escribir HTML
            try (PrintWriter out = resp.getWriter()) {

                // Estructura básica del HTML
                out.println("<!DOCTYPE html>");
                out.println("<html lang='es'>");
                out.println("<head>");
                out.println("<meta charset='UTF-8'>");
                out.println("<title>✅ Sesión Activa</title>");
                // Incluye hoja de estilos CSS
                out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
                out.println("</head>");
                out.println("<body>");

                // Contenedor principal
                out.println("<div class='contenedor'>");

                // Encabezado con saludo al usuario
                out.println("<div class='encabezado'>");
                out.println("<h1>¡Hola " + usernameOptional.get() + "!</h1>");
                out.println("<span class='etiqueta'>Sesión activa</span>");
                out.println("</div>");

                // Mensaje de bienvenida
                out.println("<p class='mensaje'>Has iniciado sesión con éxito. ¡Bienvenido de vuelta!</p>");

                // Acciones disponibles: volver al inicio o cerrar sesión
                out.println("<div class='acciones'>");
                out.println("<a href='" + req.getContextPath() + "/index.html' class='boton'>Volver al Inicio</a>");
                out.println("<a href='" + req.getContextPath() + "/logout' class='boton secundario'>Cerrar Sesión 🔓</a>");
                out.println("</div>");

                out.println("</div>"); // Cierra contenedor principal
                out.println("</body>");
                out.println("</html>");
            }

        } else {
            // Si no hay sesión activa, muestra el JSP de login
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    // Método que maneja solicitudes POST (cuando se envía el formulario de login)
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Obtiene los parámetros enviados desde el formulario
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Valida que las credenciales coincidan con las constantes
        if (USERNAME.equals(username) && PASSWORD.equals(password)) {

            // Crea una sesión si no existe y guarda el username
            HttpSession session = req.getSession();
            session.setAttribute("username", username);

            // Redirige al usuario a la página de sesión activa
            resp.sendRedirect(req.getContextPath() + "/login.html");

        } else {
            // Si las credenciales son incorrectas, envía un error 401 Unauthorized
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Lo sentimos, no está autorizado para ingresar a esta página.");
        }
    }
}

/*
 * Autor: Cristian Arias
 * Fecha y version: 13/11/2025  |  Version: 1.0
 * Descripcion: Servlet que muestra el listado de productos disponibles.
 * Verifica si hay sesión activa y muestra precios y acciones de compra
 * solo para usuarios logueados.
 */

package controllers;
// Define el paquete donde se encuentra este servlet

// Importa clases de Jakarta Servlet para manejar solicitudes HTTP
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Importa clases del modelo y servicios
import models.Producto;
import services.LoginService;
import services.LoginServiceSessionImplement;
import services.ProductoService;
import services.ProductoServiceImplement;

// Importa utilidades de Java
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;

// Define las URLs a las que responderá este servlet
@WebServlet({"/productos.html", "/productos"})
public class ProductoServlet extends HttpServlet {

    // Método que maneja solicitudes GET
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Crea instancia del servicio de productos para obtener la lista
        ProductoService service = new ProductoServiceImplement();

        // Obtiene todos los productos mediante el método listar()
        List<Producto> productos = service.listar();

        // Crea instancia del servicio de login para verificar sesión
        LoginService auth = new LoginServiceSessionImplement();

        // Obtiene el username de la sesión actual, si existe
        Optional<String> usernameOptional = auth.getUsername(req);

        // Configura el tipo de contenido de la respuesta como HTML UTF-8
        resp.setContentType("text/html;charset=UTF-8");

        // Crea un PrintWriter para escribir la respuesta HTML
        try (PrintWriter out = resp.getWriter()) {

            // Inicia la estructura básica del HTML
            out.println("<!DOCTYPE html>");
            out.println("<html lang='es'>");

            // Sección <head> con metadatos y estilos
            out.println("<head>");
            out.println("<meta charset='UTF-8'>"); // Codificación de caracteres
            out.println("<title>📦 Listado de Productos</title>"); // Título de la página
            out.println("<link rel='stylesheet' href='" + req.getContextPath() + "/css/styles.css'>");
            // Vincula hoja de estilos CSS
            out.println("</head>");

            // Sección <body> del HTML
            out.println("<body>");
            out.println("<div class='contenedor'>"); // Contenedor principal

            // Encabezado de la página con título y etiqueta
            out.println("<div class='encabezado'>");
            out.println("<h1>Listado de Productos</h1>");
            out.println("<span class='etiqueta'>Inventario Disponible</span>");
            out.println("</div>");

            // Mensaje de bienvenida según si hay usuario logueado
            if (usernameOptional.isPresent()) {
                out.println("<p class='mensaje'>Hola <strong>"
                        + usernameOptional.get()
                        + "</strong>, ¡listo para comprar! 🛒</p>");
            } else {
                out.println("<p class='mensaje'>⚠️ Debes iniciar sesión para ver precios y comprar.</p>");
            }

            // ======= TABLA DE PRODUCTOS =======
            out.println("<table class='tabla-carro'>"); // Clase CSS para estilo uniforme
            out.println("<thead>");
            out.println("<tr>");
            out.println("<th>ID</th>"); // Columna ID
            out.println("<th>Nombre</th>"); // Columna Nombre
            out.println("<th>Tipo</th>"); // Columna Tipo

            // Si hay usuario logueado, agrega columnas de Precio y Acciones
            if (usernameOptional.isPresent()) {
                out.println("<th>Precio</th>"); // Columna Precio
                out.println("<th>Acciones</th>"); // Columna Acciones
            }
            out.println("</tr>");
            out.println("</thead>");
            out.println("<tbody>");

            // Recorre todos los productos y genera filas dinámicamente
            productos.forEach(p -> {
                out.println("<tr>");
                out.println("<td>" + p.getId() + "</td>"); // Muestra ID del producto
                out.println("<td>" + p.getNombre() + "</td>"); // Muestra nombre
                out.println("<td>" + p.getTipo() + "</td>"); // Muestra tipo

                // Si hay usuario logueado, muestra precio y botón de agregar
                if (usernameOptional.isPresent()) {
                    out.println("<td>$<strong>" + p.getPrecio() + "</strong></td>"); // Precio
                    out.println("<td><a class='boton secundario' href='"
                            + req.getContextPath()
                            + "/agregar-carro?id=" + p.getId()
                            + "'>➕ Agregar</a></td>"); // Botón de agregar al carrito
                }

                out.println("</tr>"); // Cierra fila
            });

            out.println("</tbody>");
            out.println("</table>"); // Cierra tabla

            // ====== BOTONES DE NAVEGACIÓN ======
            out.println("<div class='acciones' style='justify-content: space-between;'>");
            // Botón para volver al inicio
            out.println("<a href='" + req.getContextPath() + "/index.html' class='boton'>Volver al Inicio</a>");

            // Botón para ver carrito solo si hay sesión activa
            if (usernameOptional.isPresent()) {
                out.println("<a href='" + req.getContextPath() + "/carro' class='boton'>Ver Carro 🛒</a>");
            }
            out.println("</div>"); // Cierra div acciones

            out.println("</div>"); // Cierra contenedor principal
            out.println("</body>"); // Cierra body
            out.println("</html>"); // Cierra html
        }
    }
}

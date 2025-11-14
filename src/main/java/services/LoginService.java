/*
 * Autor: Cristian Arias
 * Fecha y version: 13/11/2025  |  Version: 1.0
 * Descripcion: Interfaz que define los métodos de servicio para login.
 * Permite obtener el nombre de usuario almacenado en la sesión HTTP.
 */

package services;
// Define el paquete donde se encuentra la interfaz

// Importa la clase HttpServletRequest para manejar solicitudes HTTP
import jakarta.servlet.http.HttpServletRequest;

// Importa Optional de Java para manejar valores que pueden estar ausentes
import java.util.Optional;

// Define la interfaz pública LoginService
public interface LoginService {

    // Metodo que devuelve un Optional con el nombre de usuario si existe en la sesión
    // Recibe como parámetro el HttpServletRequest para acceder a la sesión
    Optional<String> getUsername(HttpServletRequest request);
}

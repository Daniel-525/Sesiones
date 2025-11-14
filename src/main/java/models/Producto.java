/*
 * Autor: Cristian Arias
 * Fecha y version: 13/11/2025  |  Version: 1.0
 * Descripcion: Clase modelo que representa un producto.
 * Contiene atributos como id, nombre, tipo y precio,
 * junto con sus métodos getters y setters para acceder y modificar los datos.
 */

package models;
// Define el paquete donde se encuentra la clase

// Clase pública Producto
public class Producto {

    // Atributo que almacena el ID único del producto
    private Long id;

    // Atributo que almacena el nombre del producto
    private String nombre;

    // Atributo que almacena el tipo o categoría del producto
    private String tipo;

    // Atributo que almacena el precio del producto
    private double precio;

    // Constructor vacío, permite crear un objeto sin inicializar atributos
    public Producto() {
    }

    // Constructor con parámetros para inicializar todos los atributos
    public Producto(Long id, String nombre, String tipo, double precio) {
        this.id = id;           // Asigna el ID del producto
        this.nombre = nombre;   // Asigna el nombre
        this.tipo = tipo;       // Asigna el tipo
        this.precio = precio;   // Asigna el precio
    }

    // Metodo getter para obtener el ID del producto
    public Long getId() {
        return id;
    }

    // Metodo setter para asignar un nuevo ID al producto
    public void setId(Long id) {
        this.id = id;
    }

    // Metodo getter para obtener el nombre del producto
    public String getNombre() {
        return nombre;
    }

    // Metodo setter para asignar un nuevo nombre al producto
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    // Metodo getter para obtener el tipo del producto
    public String getTipo() {
        return tipo;
    }

    // Metodo setter para asignar un nuevo tipo al producto
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    // Metodo getter para obtener el precio del producto
    public double getPrecio() {
        return precio;
    }

    // Metodo setter para asignar un nuevo precio al producto
    // Nota: recibe un int, pero el atributo es double; podría ser mejor usar double
    public void setPrecio(int precio) {
        this.precio = precio;
    }
}

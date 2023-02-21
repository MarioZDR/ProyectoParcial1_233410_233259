/**
 * Clase Cliente.java creada el 15/02/2023.
 */
package dominio;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Objects;

/**
 * Esta clase representa a un usuario registrado.
 * @author 00000233259 y 00000233410.
 */
public class Cliente{
    
    /**
     * Este atributo representa el número identificador del cliente.
     */
    private Integer id;
    /**
     * Este atributo representa la dirección del cliente.
     */
    private Direccion direccion;
    /**
     * Este atributo representa la edad actual del cliente.
     */
    private Integer edad;
    /**
     * Este atributo representa el nombre del cliente.
     */
    private String nombre;
    /**
     * Este atributo representa el apellido paterno del cliente.
     */
    private String apellidoPaterno;
    /**
     * Este atributo representa el apellido materno del cliente.
     */
    private String apellidoMaterno;
    /**
     * Este atributo representa la password del cliente.
     */
    private String password;
    /**
     * Este atributo representa la fecha de nacimiento del cliente.
     */
    private Date fechaNacimiento;

    /**
     * Constructor vacío.
     */
    public Cliente() {
    }

    /**
     * Este constructor crea un cliente con los atributos id direccion, edad, nombre, 
 apellido paterno, apellido materno, password y fecha de nacimiento.
     * @param idDireccion Representa el número identificador de la dirección
     * del cliente.
     * @param nombre Representa el nombre del cliente.
     * @param apellidoPaterno Representa el apellido paterno del cliente.
     * @param apellidoMaterno Representa el apellido materno del cliente .
     * @param password Representa la password del cliente.
     * @param fechaNacimiento Representa la fecha de nacimiento del cliente.
     */
    public Cliente(Direccion idDireccion, String nombre, String apellidoPaterno, String apellidoMaterno, String password, Date fechaNacimiento) {
        this.direccion = idDireccion;
        this.edad = 0;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Este constructor crea un cliente con los atributos id, id direccion, edad, nombre, 
 apellido paterno, apellido materno, password y fecha de nacimiento.
     * @param id Representa el número identificador del cliente.
     * @param idDireccion Representa el número identificador de la dirección
     * del cliente.
     * @param edad Representa la edad actual del cliente.
     * @param nombre Representa el nombre del cliente.
     * @param apellidoPaterno Representa el apellido paterno del cliente.
     * @param apellidoMaterno Representa el apellido materno del cliente .
     * @param password Representa la password del cliente.
     * @param fechaNacimiento Representa la fecha de nacimiento del cliente.
     */
    public Cliente(Integer id, Direccion idDireccion, Integer edad, String nombre, String apellidoPaterno, String apellidoMaterno, String password, Date fechaNacimiento) {
        this.id = id;
        this.direccion = idDireccion;
        this.edad = edad;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.password = password;
        this.fechaNacimiento = fechaNacimiento;
    }
    /**
     * Constuctor que incicializa la ID y password al valor de sus parámetros.
     * @param id ID del cliente.
     * @param password Password del cliente.
     */
    public Cliente(Integer id, String password) {
        this.id = id;
        this.password = password;
    }

    /**
     * Este método hashCode utiliza el id del cliente.
     * @return Regresa el código hash del cliente.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Este método equals utiliza el id del cliente para realizar la comparación.
     * @param obj Representa el objeto a comparar con el actual.
     * @return Regresa si ambos objetos son iguales o no.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cliente other = (Cliente) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Este método regresa el número identificador del cliente.
     * @return Regresa el número identificador del cliente.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo id.
     * @param id Representa el número identificador del cliente.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Este método regresa el id de la dirección del cliente.
     * @return Regresa el id de la dirección del cliente.
     */
    public Direccion getDireccion() {
        return direccion;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo idDireccion.
     * @param direccion Representa el id de la dirección del cliente.
     */
    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    /**
     * Este método regresa la edad actual del cliente.
     * @return Regresa la edad actual del cliente.
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo edad.
     * @param edad Representa la edad actual del cliente.
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    /**
     * Este método regresa el nombre del cliente.
     * @return Regresa el nombre del cliente.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo nombre.
     * @param nombre Representa el nombre del cliente.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Este método regresa el apellido paterno del cliente.
     * @return Regresa el apellido paterno del cliente.
     */
    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo apellidoPaterno.
     * @param apellidoPaterno Representa el apellido paterno del cliente.
     */
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    /**
     * Este método regresa el apellido materno del cliente.
     * @return Regresa el apellido materno del cliente.
     */
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo apellidoMaterno.
     * @param apellidoMaterno Representa el apellido materno del cliente.
     */
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    /**
     * Este método regresa la password del cliente.
     * @return Regresa la password del cliente.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo password.
     * @param contraseña Representa la password del cliente.
     */
    public void setPassword(String contraseña) {
        this.password = contraseña;
    }

    /**
     * Este método regresa la fecha de nacimiento del cliente.
     * @return Regresa la fecha de nacimiento del cliente.
     */
    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo fechaNacimiento.
     * @param fechaNacimiento Representa el valor de la fecha de nacimiento del cliente.
     */
    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    /**
     * Este método regresa una cadena con los atributos del cliente.
     * @return Regresa una cadena con los atributos del cliente.
     */
    @Override
    public String toString() {
        return "Clientes{" + "id=" + id + ", nombre=" + nombre + ", apellidoPaterno=" + apellidoPaterno + ", apellidoMaterno=" + apellidoMaterno + ", contrase\u00f1a=" + password + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
}
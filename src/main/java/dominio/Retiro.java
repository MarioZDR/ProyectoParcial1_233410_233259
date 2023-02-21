/**
 * Clase Retiro creada el 15/02/2023.
 */
package dominio;

import java.sql.Date;
import java.util.GregorianCalendar;

/**
 * Este clase representa un retiro.
 * @author 00000233259 y 00000233410.
 */
public class Retiro {

    /**
     * Atributo que representa la ID del retiro.
     */
    private Integer id;
    /**
     * Atributo que representa el monto del retiro.
     */
    private Float monto;
    /**
     * Atributo que representa la fecha del retiro.
     */
    private Date fecha;
    /**
     * Atributo que representa la ID de la cuenta donde se retiró.
     */
    private Integer idCuenta;
    /**
     * Atributo que representa el folio del retiro.
     */
    private String folio;
    /**
     * Atributo que representa la password del retiro.
     */
    private String password;
   
    private String estado;
    /**
     * Constructor por defecto.
     */
    public Retiro() {
    }

    /**
     * Constructor que inicializa los atributos al valor de los parámetros
     * enviados.
     *
     * @param id ID del retiro.
     * @param monto monto del retiro.
     * @param fecha fecha de realización del retiro.
     * @param idCuenta ID de la cuenta del retiro.
     * @param folio folio del retiro.
     * @param password password del retiro.
     * @param estado estado del retiro.
     */
    public Retiro(Integer id, Float monto, Date fecha, Integer idCuenta, String folio, String password,String estado) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.idCuenta = idCuenta;
        this.folio = folio;
        this.password = password;
        this.estado=estado;
    }
    /**
     * Constructor que inicializa los atributos al valor de los parámetros
     * enviados.
     * @param folio Folio del retiro.
     * @param password Password del retiro.
     */
    public Retiro(String folio, String password) {
        this.folio = folio;
        this.password = password;
    }
    /**
     * Constructor que inicializa los atributos al valor de los parámetros
     * enviados.
     * @param idCuenta ID de la cuenta.
     * @param folio Folio del retiro.
     * @param password Password del retiro.
     * @param monto Monto del retiro.
     */
    public Retiro(Integer idCuenta, String folio, String password,Float monto) {
        this.monto = monto;
        this.idCuenta = idCuenta;
        this.folio = folio;
        this.password = password;
    }
    
    
    /**
     * Método que devuelve la ID del retiro.
     *
     * @return ID del retiro.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Método que asigna la ID del retiro.
     *
     * @param id ID del retiro.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Método que devuelve el monto del retiro.
     *
     * @return monto del retiro.
     */
    public Float getMonto() {
        return monto;
    }

    /**
     * Método que asigna el monto del retiro.
     *
     * @param monto monto del retiro.
     */
    public void setMonto(Float monto) {
        this.monto = monto;
    }

    /**
     * Método que devuelve la fecha del retiro.
     *
     * @return fecha del retiro.
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * Método que asigna la fecha del retiro.
     *
     * @param fecha fecha del retiro.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * Método que devuelve la ID de la cuenta del retiro.
     *
     * @return ID de la cuenta del retiro.
     */
    public Integer getIdCuenta() {
        return idCuenta;
    }

    /**
     * Método que asigna la ID de la cuenta del retiro.
     *
     * @param idCuenta ID de la cuenta del retiro.
     */
    public void setIdCuenta(Integer idCuenta) {
        this.idCuenta = idCuenta;
    }

    /**
     * Método que devuelve el folio del retiro.
     *
     * @return folio del retiro.
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Método que asigna el folio del retiro.
     *
     * @param folio folio del retiro.
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * Método que devuelve la password del retiro.
     *
     * @return password del retiro.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Método que asigna la password del retiro.
     *
     * @param password password del retiro.
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Método para asignar el estado del retiro.
     * @return estado del retiro.
     */
    public String getEstado() {
        return estado;
    }
    /**
     * Método para asignar el estado del retiro.
     * @param estado estado del retiro.
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
        
    
    /**
     * Método hashCode de la clase Retiro.
     *
     * @return hashCode.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + this.id;
        return hash;
    }

    /**
     * Método equals de la clase Retiro.
     *
     * @param obj objeto Retiro a comparar.
     * @return valor booleano.
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
        final Retiro other = (Retiro) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * Método toString de la clase Retiro.
     *
     * @return objeto representado en String.
     */
    @Override
    public String toString() {
        return "Retiro{" + "id=" + id + ", monto=" + monto + ", fecha=" + fecha + ", idCuenta=" + idCuenta + ", folio=" + folio + ", password=" + password + '}';
    }

}

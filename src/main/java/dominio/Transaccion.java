/*
Transaccion.java creada el 15/02/2023
*/
package dominio;

import java.sql.Date;

/**
 * Este clase representa una transacción.
 * @author 00000233259 y 00000233410.
 */
public class Transaccion {
    /**
     * Atributo que representa la ID de la transacción
     */
    private Integer id;
    /**
     * Atributo que representa el monto de la transacción.
     */
    private Float monto;
    /**
     * Atributo que representa la fecha de la realización de la transacción.
     */
    private Date fecha;
    /**
     * Atributo que representa la Id de la cuenta de destino.
     */
    private Integer idCuentaDestino;
    /**
     * Atributo que representa la ID de la cuenta de origen.
     */
    private Integer idCuentaOrigen;
    /**
     * Constructor por defecto.
     */
    public Transaccion() {
    }
    /**
     * Constructor que inicializa los atributos al valor de los parámetros enviados.
     * @param id id de la transacción.
     * @param monto valor del monto.
     * @param fecha fecha de la transacción.
     * @param idCuentaDestino ID de la cuenta de destino.
     * @param idCuentaOrigen ID de la cuenta de origen.
     */
    public Transaccion(Integer id, Float monto, Date fecha, Integer idCuentaDestino, Integer idCuentaOrigen) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.idCuentaDestino = idCuentaDestino;
        this.idCuentaOrigen = idCuentaOrigen;
    }

    public Transaccion(Float monto, Integer idCuentaDestino, Integer idCuentaOrigen) {
        this.monto = monto;
        this.idCuentaDestino = idCuentaDestino;
        this.idCuentaOrigen = idCuentaOrigen;
    }
    
    /**
     * Método que devuelve la ID de la transacción.
     * @return ID de la transacción.
     */
    public Integer getId() {
        return id;
    }
    /**
     * Método que asigna la ID de la transacción.
     * @param id ID de la transacción.
     */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
     * Método que devuelve el monto de la transacción.
     * @return monto de la transacción.
     */
    public Float getMonto() {
        return monto;
    }
    /**
     * Método que asigna el monto de la transacción.
     * @param monto monto de la transacción.
     */
    public void setMonto(Float monto) {
        this.monto = monto;
    }
    /**
     * Método que devuelve la fecha de la transacción.
     * @return fecha de la transacción.
     */
    public Date getFecha() {
        return fecha;
    }
    /**
     * Método que asigna la fecha de la transacción.
     * @param fecha fecha de la transacción.
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    /**
     * Método que devuelve la ID de la cuenta destino.
     * @return ID de la cuenta destino.
     */
    public Integer getIdCuentaDestino() {
        return idCuentaDestino;
    }
    /**
     * Método asigna la ID de la cuenta destino.
     * @param idCuentaDestino ID de la cuenta destino.
     */
    public void setIdCuentaDestino(Integer idCuentaDestino) {
        this.idCuentaDestino = idCuentaDestino;
    }
    /**
     * Método que devuelve la ID de la cuenta origen.
     * @return ID de la cuenta origen.
     */
    public Integer getIdCuentaOrigen() {
        return idCuentaOrigen;
    }
    /**
     * Método asigna la ID de la cuenta origen.
     * @param idCuentaOrigen Id de la cuenta origen. 
     */
    public void setIdCuentaOrigen(Integer idCuentaOrigen) {
        this.idCuentaOrigen = idCuentaOrigen;
    }
    /**
     * Método hashCode de la clase Transaccion.
     * @return hashCode.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
        return hash;
    }
    /**
     * Método equals de la clase Transaccion.
     * @param obj objeto Transaccion a comparar.
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
        final Transaccion other = (Transaccion) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    /**
     * Método toString de la clase Transaccion.
     * @return objeto representado en String.
     */
    @Override
    public String toString() {
        return "Transaccion{" + "id=" + id + ", monto=" + monto + ", fecha=" + fecha + ", idCuentaDestino=" + idCuentaDestino + ", idCuentaOrigen=" + idCuentaOrigen + '}';
    }
    
    
}

/**
 * Clase Direccion.java creada el 15/02/2023.
 */
package dominio;

import java.util.Objects;

/**
 * Esta clase representa la dirección de una persona.
 * @author 00000233259 y 00000233410.
 */
public class Direccion {
    
    /**
     * Este atributo representa el numero de casa de una dirección.
     */
    private String numeroCasa;
    /**
     * Este atributo representa la calle de una dirección.
     */
    private String calle;
    /**
     * Este atributo representa la colonia de una dirección.
     */
    private String colonia;
    /**
     * Este atributo representa el id de la dirección.
     */
    private Integer id;
    
    /**
     * Constructor vacío.
     */
    public Direccion(){}

    /**
     * Este constructor crea una dirección con solo el id
     * @param id Es el identificador de la dirección
     */
    public Direccion(Integer id) {
        this.id = id;
    }

    /**
     * Este constructor crea un objeto con los atributos numeroCasa,
     * calle y colonia.
     * @param numeroCasa Representa el numero de casa de una dirección.
     * @param calle Representa la calle de una dirección.
     * @param colonia Representa la colonia de una dirección. 
     */
    public Direccion(String numeroCasa, String calle, String colonia) {
        this.numeroCasa = numeroCasa;
        this.calle = calle;
        this.colonia = colonia;
    }

    /**
     * Este constructor crea un objeto con los atributos id, numeroCasa,
     * calle y colonia.
     * @param numeroCasa Representa el numero de casa de una dirección.
     * @param calle Representa la calle de una dirección.
     * @param colonia Representa la colonia de una dirección. 
     * @param id Representa el id de una dirección.
     */
    public Direccion(String numeroCasa, String calle, String colonia, Integer id) {
        this.numeroCasa = numeroCasa;
        this.calle = calle;
        this.colonia = colonia;
        this.id = id;
    }

    /**
     * Este método regresa el numero de casa de la dirección.
     * @return Regresa el numero de casa de la dirección.
     */
    public String getNumeroCasa() {
        return numeroCasa;
    }

    /**
     * Este método asigna el valor recibido en el párametro al atributo numeroCasa.
     * @param numeroCasa Representa el numero de casa de la direccion.
     */
    public void setNumeroCasa(String numeroCasa) {
        this.numeroCasa = numeroCasa;
    }

    /**
     * Este método regresa la calle de la dirección.
     * @return Regresa la calle de la dirección.
     */
    public String getCalle() {
        return calle;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo calle.
     * @param calle Representa la calle de la dirección.
     */
    public void setCalle(String calle) {
        this.calle = calle;
    }

    /**
     * Este método regresa la colonia de la dirección.
     * @return Regresa la colonia de la dirección.
     */
    public String getColonia() {
        return colonia;
    }
    
    /**
     * Este método asigna el valor recibido en el parámetro al atributo colonia.
     * @param colonia Representa la colonia de la dirección.
     */
    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    /**
     * Este método regresa el id de la dirección.
     * @return Regresa el id de la dirección.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Este método asigna el valor recibido en el parámetro al atributo id.
     * @param id Representa el id de la dirección.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Este método hash utiliza el id de la dirección.
     * @return Regresa el código hash de la dirección.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 11 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Este método equals utiliza el id de la dirección para comparar.
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
        final Direccion other = (Direccion) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Este método regresa una cadena con los atributos de la dirección.
     * @return Regresa una cadena con los atributos de la dirección.
     */
    @Override
    public String toString() {
        return "Direccion{" + "numeroCasa=" + numeroCasa + ", calle=" + calle + ", colonia=" + colonia + ", id=" + id + '}';
    }
    
}
/**
 * Clase IDireccionesDAO.java creada el 15/02/2023.
 */
package interfaces;

import dominio.Direccion;
import excepciones.PersistenciaException;

/**
 * Este clase representa la interfaz de DireccionesDAO.
 * @author 00000233259 y 00000233410.
 */
public interface IDireccionesDAO {
    /**
     * Método insertar el cual inserta una Dirección en la base de datos.
     * @param direccion Dirección a insertar.
     * @return Dirección si se ha insertado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Direccion insertar(Direccion direccion) throws PersistenciaException;
    /**
     * Método consultar el cual consulta una Dirección de un cliente.
     * @param id ID del cliente asociado a la Dirección.
     * @return Dirección si se ha consultado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Direccion consultar(Integer id) throws PersistenciaException;
}

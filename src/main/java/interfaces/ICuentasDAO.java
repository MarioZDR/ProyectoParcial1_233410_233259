/**
 * Clase ICuentasDAO.java creada el 15/02/2023.
 */
package interfaces;

import dominio.Cuenta;
import excepciones.PersistenciaException;
import java.util.List;

/**
 * Este clase representa la interfaz de CuentasDAO.
 * @author 00000233259 y 00000233410.
 */
public interface ICuentasDAO {
    /**
     * Método consultar que consulta una Cuenta desde la base de datos.
     * @param numCuenta Número de cuenta a consultar.
     * @return Cuenta si se ha encontrado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Cuenta consultar(int numCuenta) throws PersistenciaException;
    /**
     * Método consultarLista que consulta una lista de cuentas desde la base de datos.
     * @param idCliente ID del cliente para consultar las cuentas asociadas a él.
     * @return Lista de cuentas del cliente.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    List<Cuenta> consultarLista(int idCliente) throws PersistenciaException;
    /**
     * Método crear el cual crea una Cuenta para el cliente.
     * @param nombreCuenta Nombre de la cuenta a crear.
     * @param idCliente ID del cliente creador de la cuenta.
     * @return Cuenta si se ha creado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
     Cuenta crear(String nombreCuenta,int idCliente) throws PersistenciaException;
     /**
      * Método cancelar el cual cancela una Cuenta de un cliente.
      * @param idCuenta Número de la cuenta a cancelar.
      * @param idCliente ID del cliente dueño de la cuenta a cancelar.
      * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
      */
     void cancelar(int idCuenta,int idCliente) throws PersistenciaException;
}

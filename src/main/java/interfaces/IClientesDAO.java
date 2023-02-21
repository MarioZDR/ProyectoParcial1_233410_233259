/**
 * Clase IClientesDAO.java creada el 15/02/2023.
 */
package interfaces;

import dominio.Cliente;
import excepciones.PersistenciaException;

/**
 * Este clase representa la interfaz de ClientesDAO.
 * @author 00000233259 y 00000233410.
 */
public interface IClientesDAO {
    /**
     * Método consultarCredenciales el cual consulta la contraseña y la ID del cliente para verificar si coincide con los datos recuperados de la UI.
     * @param idCliente ID del cliente a consultar.
     * @return El cliente si ha sido encontrado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Cliente consultarCredenciales(int idCliente)throws PersistenciaException;
    /**
     * Método consultar el cual consulta a un cliente desde la base de datos.
     * @param idCliente ID del cliente a consultar.
     * @return Cliente si ha sido encontrado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Cliente consultar(int idCliente)throws PersistenciaException;
    /**
     * Método insertar el cual inserta un cliente a la base de datos, recuperando los datos de la UI.
     * @param cliente Cliente a insertar.
     * @return Cliente si ha sido insertado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Cliente insertar(Cliente cliente) throws PersistenciaException;
    /**
     * Método acutalizar el cual actualiza a un cliente desde la base de datos, recuperando los datos de la UI.
     * @param cliente Cliente a actualizar.
     * @return Cliente si ha sido actualizado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Cliente actualizar(Cliente cliente) throws PersistenciaException;
}

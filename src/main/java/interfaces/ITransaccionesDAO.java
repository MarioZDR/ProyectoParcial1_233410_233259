/**
 * Clase ITransaccionesDAO.java creada el 15/02/2023.
 */
package interfaces;

import auxiliares.ConfiguracionPaginado;
import dominio.Retiro;
import dominio.Transaccion;
import excepciones.PersistenciaException;
import java.sql.Date;
import java.util.List;

/**
 * Este clase representa la interfaz de TransaccionesDAO.
 * @author 00000233259 y 00000233410.
 */
public interface ITransaccionesDAO {
    /**
     * Método transferir el cual transfiere un monto desde una cuenta origen a una cuenta destino.
     * @param numCuentaOrigen Número de cuenta origen la cual envía el monto.
     * @param numCuentaDestino Número de cuenta destino la cual recibe el monto.
     * @param monto Cantidad de dinero a enviar.
     * @return Valor booleano, regresa true si la transacción se ha cumplido, false en caso contrario.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    boolean transferir(int numCuentaOrigen,int numCuentaDestino,Float monto) throws PersistenciaException;
    /**
     * Método consultarTransaccion el cual consulta una transacción de un cliente.
     * @param idTransaccion ID de la transacción a consultar.
     * @return Transacción en caso de haberla consultado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Transaccion consultarTransaccion(int idTransaccion) throws PersistenciaException;
    /**
     * Método consultarListaTransaccionesRealizadas el cual consulta una lista de transacciones realizadas entre un rango de fechas.
     * @param idCuenta Número de la cuenta asociada a la transacción.
     * @param fechaInicio Fecha Inicio del rango de fechas.
     * @param fechaFin Fecha Fin del rango de fechas.
     * @param config Objeto de configuración de paginado.
     * @return Lista de transacciones realizadas en el rango de fechas asociadas a esa cuenta.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    List<Transaccion> consultarListaTransaccionesRealizadas(int idCuenta, Date fechaInicio, Date fechaFin, ConfiguracionPaginado config) throws PersistenciaException;
    /**
     * Método consultarListaTransaccionesRealizadas el cual consulta una lista de transacciones recibidas entre un rango de fechas.
     * @param idCuenta Número de la cuenta asociada a la transacción.
     * @param fechaInicio Fecha Inicio del rango de fechas.
     * @param fechaFin Fecha Fin del rango de fechas.
     * @param config Objeto de configuración de paginado.
     * @return Lista de transacciones recibidas en el rango de fechas asociadas a esa cuenta.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    List<Transaccion> consultarListaTransaccionesRecibidas(int idCuenta, Date fechaInicio, Date fechaFin, ConfiguracionPaginado config) throws PersistenciaException;
    /**
     * Método crearRetiro el cual crea un retiro a partir del parámetro recibido.
     * @param retiro Retiro a crear.
     * @return Retiro si se ha creado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Retiro crearRetiro(Retiro retiro) throws PersistenciaException;
     /**
     * Método consultarRetiro el cual consulta un retiro a partir del folio proporcionado.
     * @param folioConsultar Número de folio del retiro a consultar.
     * @return Retiro si se ha consultado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Retiro consultarRetiro(String folioConsultar) throws PersistenciaException;
    /**
     * Método consultarRetiro el cual crea un retiro a partir de la instrucción recibida en la UI.
     * @param retiro Retiro a crear.
     * @return Retiro si se ha creado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Retiro retirar(Retiro retiro) throws PersistenciaException;
    /**
     * Método actualizarRetiro el cual actualiza el estado de un retiro.
     * @param retiro Retiro a actualizar.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    void actualizarRetiro(Retiro retiro) throws PersistenciaException;
    /**
     * Método consultarUltimoRetiro el cual consulta el último retiro hecho por el cliente antes de pasar a estado "No cobrado"
     * @param idCliente ID del cliente asociado al retiro.
     * @return Retiro si lo ha consultado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    Retiro consultarUltimoRetiro(Integer idCliente) throws PersistenciaException;
    
     /**
     * Método consultarListaRetiro el cual consulta una lista de retiros hechos entre un rango de fechas.
     * @param idCuenta Número de la cuenta asociada al retiro.
     * @param fechaInicio Fecha Inicio del rango de fechas.
     * @param fechaFin Fecha Fin del rango de fechas.
     * @param config Objeto de configuración de paginado.
     * @return Lista de retiros hechos en el rango de fechas asociados a esa cuenta.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    List<Retiro> consultarListaRetiro(int idCuenta, Date fechaInicio, Date fechaFin, ConfiguracionPaginado config) throws PersistenciaException;
}

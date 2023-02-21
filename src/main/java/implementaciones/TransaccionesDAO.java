/**
 * Clase TransaccionesDAO.java creada el 15/02/2023.
 */
package implementaciones;

import auxiliares.ConfiguracionPaginado;
import dominio.Retiro;
import dominio.Transaccion;
import excepciones.PersistenciaException;
import interfaces.IConexionBD;
import interfaces.ITransaccionesDAO;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Este clase representa el objeto que permite acceder a los datos de transacciones.
 * @author 00000233259 y 00000233410.
 */
public class TransaccionesDAO implements ITransaccionesDAO {
    /**
     * Atributo que representa el generador de conexión.
     */
    private final IConexionBD GENERADOR_CONEXION;
    /**
     * Atributo que se utiliza para llevar un registro cronológicamente de los datos.
     */
    private static final Logger LOG = Logger.getLogger(ClientesDAO.class.getName());
    /**
     * Método constructor el cual inicializa el atributo GENERADOR_CONEXION al valor de su parámetro.
     * @param generadorConexion Objeto IConxionBD para crear la conexión.
     */
  
    public TransaccionesDAO(IConexionBD generadorConexion) {
        this.GENERADOR_CONEXION = generadorConexion;
    }
    /**
     * Método transferir el cual transfiere un monto desde una cuenta origen a una cuenta destino.
     * @param numCuentaOrigen Número de cuenta origen la cual envía el monto.
     * @param numCuentaDestino Número de cuenta destino la cual recibe el monto.
     * @param monto Cantidad de dinero a enviar.
     * @return Valor booleano, regresa true si la transacción se ha cumplido, false en caso contrario.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public boolean transferir(int numCuentaOrigen, int numCuentaDestino, Float monto) throws PersistenciaException {
        String codigoSQL = "call transaccion(?,?,?,?);";
        try (
                Connection conexion = GENERADOR_CONEXION.crearConexion(); CallableStatement comando = conexion.prepareCall(codigoSQL);) {
            comando.setInt(1, numCuentaOrigen);
            comando.setInt(2, numCuentaDestino);
            comando.setFloat(3, monto);
            comando.registerOutParameter(4, Types.INTEGER);
            comando.execute();
            int resultado = comando.getInt(4); 
            if (resultado==1) {
                return true;
            } else {
                throw new PersistenciaException("No fue posible realizar la transferencia");
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible realizar la transferencia");
        }
    }
    /**
     * Método consultarTransaccion el cual consulta una transacción de un cliente.
     * @param idTransaccion ID de la transacción a consultar.
     * @return Transacción en caso de haberla consultado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public Transaccion consultarTransaccion(int idTransaccion) throws PersistenciaException 
    {
        String codigoSQL = "SELECT monto, fecha, id_cuenta_origen, id_cuenta_destino FROM transacciones WHERE id = ?;";
        try (
            Connection conexion = GENERADOR_CONEXION.crearConexion(); PreparedStatement comando = conexion.prepareStatement(codigoSQL);) {
            comando.setInt(1, idTransaccion);
            ResultSet resultado = comando.executeQuery();
            Transaccion transaccion = null;
            if (resultado.next()) {
                Float monto = resultado.getFloat("monto");
                Date fecha = resultado.getDate("fecha");
                int id_cuenta_origen = resultado.getInt("id_cuenta_origen");
                int id_cuenta_destino = resultado.getInt("id_cuenta_destino");
                transaccion = new Transaccion(idTransaccion, monto, fecha, id_cuenta_destino, id_cuenta_origen);
                return transaccion;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar la transaccion");
        }
    }
    /**
     * Método consultarListaTransaccionesRealizadas el cual consulta una lista de transacciones realizadas entre un rango de fechas.
     * @param idCuenta Número de la cuenta asociada a la transacción.
     * @param fechaInicio Fecha Inicio del rango de fechas.
     * @param fechaFin Fecha Fin del rango de fechas.
     * @param config Objeto de configuración de paginado.
     * @return Lista de transacciones realizadas en el rango de fechas asociadas a esa cuenta.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
     @Override
    public List<Transaccion> consultarListaTransaccionesRealizadas(int idCuenta, Date fechaInicio, Date fechaFin ,ConfiguracionPaginado config) throws PersistenciaException {
        String codigoSQL = "CALL consultarTransaccionesPorCuentaRealizadas(?,?,?,?,?);";
        try (
             Connection conexion = GENERADOR_CONEXION.crearConexion(); CallableStatement comando = conexion.prepareCall(codigoSQL);) {
            comando.setInt(1, idCuenta);
            comando.setDate(2, fechaInicio);
            comando.setDate(3, fechaFin);
            comando.setInt(4, config.getElementosPorPagina());
            comando.setInt(5, config.getElementosASaltar());
            ResultSet resultado = comando.executeQuery();
            List<Transaccion> listaTransacciones = new LinkedList<>();
            while (resultado.next()) {
                Integer id = resultado.getInt("id");
                Float monto = resultado.getFloat("monto");
                Date fecha = resultado.getDate("fecha");
                Integer id_cuenta_origen = resultado.getInt("id_cuenta_origen");
                Integer id_cuenta_destino = resultado.getInt("id_cuenta_destino");
                Transaccion transaccion = new Transaccion(id, monto, fecha, id_cuenta_destino, id_cuenta_origen);
                listaTransacciones.add(transaccion);
            }
            return listaTransacciones;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar la lista");
        }
    }
    /**
     * Método consultarListaTransaccionesRealizadas el cual consulta una lista de transacciones recibidas entre un rango de fechas.
     * @param idCuenta Número de la cuenta asociada a la transacción.
     * @param fechaInicio Fecha Inicio del rango de fechas.
     * @param fechaFin Fecha Fin del rango de fechas.
     * @param config Objeto de configuración de paginado.
     * @return Lista de transacciones recibidas en el rango de fechas asociadas a esa cuenta.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public List<Transaccion> consultarListaTransaccionesRecibidas(int idCuenta, Date fechaInicio, Date fechaFin , ConfiguracionPaginado config) throws PersistenciaException {
        String codigoSQL = "CALL consultarTransaccionesPorCuentaRecibidas(?,?,?,?,?);";
        try (
             Connection conexion = GENERADOR_CONEXION.crearConexion(); CallableStatement comando = conexion.prepareCall(codigoSQL);) {
            comando.setInt(1, idCuenta);
            comando.setDate(2, fechaInicio);
            comando.setDate(3, fechaFin);
            comando.setInt(4, config.getElementosPorPagina());
            comando.setInt(5, config.getElementosASaltar());
            ResultSet resultado = comando.executeQuery();
            List<Transaccion> listaTransacciones = new LinkedList<>();
            while (resultado.next()) {
                Integer id = resultado.getInt("id");
                Float monto = resultado.getFloat("monto");
                Date fecha = resultado.getDate("fecha");
                Integer id_cuenta_origen = resultado.getInt("id_cuenta_origen");
                Integer id_cuenta_destino = resultado.getInt("id_cuenta_destino");
                Transaccion transaccion = new Transaccion(id, monto, fecha, id_cuenta_destino, id_cuenta_origen);
                listaTransacciones.add(transaccion);
            }
            return listaTransacciones;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar la lista");
        }
    }
     /**
     * Método crearRetiro el cual crea un retiro a partir del parámetro recibido.
     * @param retiro Retiro a crear.
     * @return Retiro si se ha creado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public Retiro crearRetiro(Retiro retiro) throws PersistenciaException{
         String codigoSQL = "INSERT INTO TRANSACCIONES_SIN_CUENTA (id_cuenta_retiro,folio,password,monto) values (?,?,?,?)";
       try (
                Connection conexion = GENERADOR_CONEXION.crearConexion(); CallableStatement comando = conexion.prepareCall(codigoSQL);){
            comando.setInt(1, retiro.getIdCuenta());
            comando.setString(2, retiro.getFolio());
            comando.setString(3, retiro.getPassword());
            comando.setFloat(4, retiro.getMonto());
            comando.executeUpdate();
            ResultSet llaves = comando.getGeneratedKeys();
            if (llaves.next()){
                Integer llavePrimaria = llaves.getInt(1);
                retiro.setId(llavePrimaria);
                return retiro;
            }
            return null;
        }catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible realizar el retiro");
        }
   }
     /**
     * Método consultarRetiro el cual consulta un retiro a partir del folio proporcionado.
     * @param folioConsultar Número de folio del retiro a consultar.
     * @return Retiro si se ha consultado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public Retiro consultarRetiro(String folioConsultar) throws PersistenciaException {
        String codigoSQL = "SELECT RETIRO.* FROM TRANSACCIONES_SIN_CUENTA AS RETIRO WHERE RETIRO.FOLIO = ?;";
        try (
            Connection conexion = GENERADOR_CONEXION.crearConexion(); PreparedStatement comando = conexion.prepareStatement(codigoSQL);) {
            comando.setString(1, folioConsultar);
            ResultSet resultado = comando.executeQuery();
            Retiro retiro = null;
            if (resultado.next()) {
                int id = resultado.getInt("id");
                Float monto = resultado.getFloat("monto");
                Date fecha = resultado.getDate("fecha");
                int id_cuenta = resultado.getInt("id_cuenta_retiro");
                String estado = resultado.getString("estado");
                String folio = resultado.getString("folio");
                String password = resultado.getString("password");
                retiro = new Retiro(id, monto, fecha, id_cuenta, folio, password, estado);
                return retiro;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar el retiro");
        }
    }
     /**
     * Método el cual crea un retiro a partir de la instrucción recibida en la UI.
     * @param retiro Retiro a crear.
     * @return Retiro si se ha creado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
     @Override
    public Retiro retirar(Retiro retiro) throws PersistenciaException{
        String codigoSQL = "call retiro(?,?,?,?,?);";
        try (
                Connection conexion = GENERADOR_CONEXION.crearConexion(); CallableStatement comando = conexion.prepareCall(codigoSQL);) {
            comando.setInt(1, retiro.getIdCuenta());
            comando.setFloat(2, retiro.getMonto());
            comando.setString(3, retiro.getFolio());
            comando.setString(4, retiro.getPassword());
            comando.registerOutParameter(5, Types.INTEGER);
            comando.execute();
            int resultado = comando.getInt(5); 
            if (resultado==1) {
                return retiro;
            } else {
                throw new PersistenciaException("Saldo insuficiente y/o Folio expirado");
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible realizar el retiro");
        }
   }
    /**
     * Método para actualizar el estado de un retiro.
     * @param retiro Retiro a actualizar.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public void actualizarRetiro(Retiro retiro) throws PersistenciaException{
        String codigoSQL = "call actualizarRegistro(?,?);";
        try (
            Connection conexion = GENERADOR_CONEXION.crearConexion(); CallableStatement comando = conexion.prepareCall(codigoSQL);) {
            comando.setString(1, retiro.getFolio());
            comando.setString(2, retiro.getPassword());
            comando.execute();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
   }
   /**
     * Método consultarUltimoRetiro el cual consulta el último retiro hecho por el cliente antes de pasar a estado "No cobrado"
     * @param idCliente ID del cliente asociado al retiro.
     * @return Retiro si lo ha consultado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
   @Override
    public Retiro consultarUltimoRetiro(Integer idCliente) throws PersistenciaException {
        String codigoSQL = "CALL consultarUltimoRetiro(?);";
        try (
             Connection conexion = GENERADOR_CONEXION.crearConexion(); CallableStatement comando = conexion.prepareCall(codigoSQL);) {
            comando.setInt(1, idCliente);
            ResultSet resultado = comando.executeQuery();
            Retiro retiro = null;
            if (resultado.next()) {
                Integer id = resultado.getInt("id");
                Float monto = resultado.getFloat("monto");
                Date fecha = resultado.getDate("fecha");
                String estado = resultado.getString("estado");
                Integer id_cuenta = resultado.getInt("id_cuenta_retiro");
                String password = resultado.getString("password");
                String folio = resultado.getString("folio");
                retiro = new Retiro(id, monto, fecha, id_cuenta, folio, password, estado);
                return retiro;
            }else{
                return null;
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar el último retiro");
        }
    }
    /**
     * Método consultarListaRetiro el cual consulta una lista de retiros hechos entre un rango de fechas.
     * @param idCuenta Número de la cuenta asociada al retiro.
     * @param fechaInicio Fecha Inicio del rango de fechas.
     * @param fechaFin Fecha Fin del rango de fechas.
     * @param config Objeto de configuración de paginado.
     * @return Lista de retiros hechos en el rango de fechas asociados a esa cuenta.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public List<Retiro> consultarListaRetiro(int idCuenta, Date fechaInicio, Date fechaFin , ConfiguracionPaginado config) throws PersistenciaException {
        String codigoSQL = "CALL consultarRetirosPorCuenta(?,?,?,?,?);";
        try (
             Connection conexion = GENERADOR_CONEXION.crearConexion(); CallableStatement comando = conexion.prepareCall(codigoSQL);) {
            comando.setInt(1, idCuenta);
            comando.setDate(2, fechaInicio);
            comando.setDate(3, fechaFin);
            comando.setInt(4, config.getElementosPorPagina());
            comando.setInt(5, config.getElementosASaltar());
            ResultSet resultado = comando.executeQuery();
            List<Retiro> listaRetiros = new LinkedList<>();
            while (resultado.next()) {
                Integer id = resultado.getInt("id");
                Float monto = resultado.getFloat("monto");
                Date fecha = resultado.getDate("fecha");
                String estado = resultado.getString("estado");
                Integer id_cuenta = resultado.getInt("id_cuenta_retiro");
                String folio = resultado.getString("folio");
                String password = resultado.getString("password");
                Retiro retiro = new Retiro(id, monto, fecha, idCuenta, folio, password, estado);
                listaRetiros.add(retiro);
            }
            return listaRetiros;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar la lista");
        }
    } 



}

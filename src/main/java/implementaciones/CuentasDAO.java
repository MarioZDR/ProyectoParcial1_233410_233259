/**
 * Clase CuentasDAO.java creada el 15/02/2023.
 */
package implementaciones;

import dominio.Cuenta;
import excepciones.PersistenciaException;
import interfaces.IConexionBD;
import interfaces.ICuentasDAO;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Este clase representa el objeto que permite acceder a los datos de cuentas.
 * @author 00000233259 y 00000233410.
 */
public class CuentasDAO implements ICuentasDAO {

    /**
     * Atributo que representa el generador de conexión.
     */
    private final IConexionBD GENERADOR_CONEXION;
    /**
     * Atributo que se utiliza para llevar un registro cronológicamente de los
     * datos.
     */
    private static final Logger LOG = Logger.getLogger(ClientesDAO.class.getName());

    /**
     * Método constructor el cual inicializa el atributo GENERADOR_CONEXION al
     * valor de su parámetro.
     *
     * @param generadorConexion Objeto IConxionBD para crear la conexión.
     */
    public CuentasDAO(IConexionBD generadorConexion) {
        this.GENERADOR_CONEXION = generadorConexion;
    }

    /**
     * Método consultar que consulta una Cuenta desde la base de datos.
     *
     * @param numCuenta Número de cuenta a consultar.
     * @return Cuenta si se ha encontrado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de
     * persistencia.
     */
    @Override
    public Cuenta consultar(int numCuenta) throws PersistenciaException {
        String codigoSQL = "SELECT estado,id_cliente, fecha_apertura, saldo,nombre_cuenta FROM CUENTAS where num_cuenta = ?;";
        try (
                Connection conexion = GENERADOR_CONEXION.crearConexion(); PreparedStatement comando = conexion.prepareStatement(codigoSQL);) {
            comando.setInt(1, numCuenta);
            ResultSet resultado = comando.executeQuery();
            Cuenta cuenta = null;
            if (resultado.next()) {
                Integer id_cliente = resultado.getInt("id_cliente");
                Date fechaApertura = resultado.getDate("fecha_apertura");
                Integer saldo = resultado.getInt("saldo");
                String estado = resultado.getString("estado");
                String nombre = resultado.getString("nombre_cuenta");
                cuenta = new Cuenta(numCuenta, fechaApertura, id_cliente, estado, nombre);
                cuenta.setSaldo(saldo);
                return cuenta;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar la cuenta");
        }
    }

    /**
     * Método consultarLista que consulta una lista de cuentas desde la base de
     * datos.
     *
     * @param idCliente ID del cliente para consultar las cuentas asociadas a
     * él.
     * @return Lista de cuentas del cliente.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de
     * persistencia.
     */
    @Override
    public List<Cuenta> consultarLista(int idCliente) throws PersistenciaException {
        String codigoSQL = "SELECT num_cuenta, fecha_apertura, saldo, nombre_cuenta FROM CUENTAS where id_cliente = ? AND estado = 'Activo';";
        try (
                Connection conexion = GENERADOR_CONEXION.crearConexion(); PreparedStatement comando = conexion.prepareStatement(codigoSQL);) {
            comando.setInt(1, idCliente);
            ResultSet resultado = comando.executeQuery();
            List<Cuenta> cuentas = new ArrayList<>();
            while (resultado.next()) {
                Integer numCuenta = resultado.getInt("num_cuenta");
                Date fechaApertura = resultado.getDate("fecha_apertura");
                Integer saldo = resultado.getInt("saldo");
                String estado = "Activo";
                String nombre = resultado.getString("nombre_cuenta");
                Cuenta cuenta = new Cuenta(numCuenta, fechaApertura, idCliente, estado, nombre);
                cuenta.setSaldo(saldo);
                cuentas.add(cuenta);
            }
            return cuentas;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar la lista de cuentas");
        }
    }

    /**
     * Método crear el cual crea una Cuenta para el cliente.
     *
     * @param nombreCuenta Nombre de la cuenta a crear.
     * @param idCliente ID del cliente creador de la cuenta.
     * @return Cuenta si se ha creado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de
     * persistencia.
     */
    @Override
    public Cuenta crear(String nombreCuenta, int idCliente) throws PersistenciaException {
        String codigoSQL = "INSERT INTO CUENTAS (nombre_cuenta,id_cliente) values (?,?);";
        Cuenta cuenta = new Cuenta();
        try (
                Connection conexion = this.GENERADOR_CONEXION.crearConexion(); PreparedStatement comando = conexion.prepareStatement(codigoSQL, Statement.RETURN_GENERATED_KEYS);) {
            comando.setString(1, nombreCuenta);
            comando.setInt(2, idCliente);
            comando.executeUpdate();
            ResultSet llaves = comando.getGeneratedKeys();
            if (llaves.next()) {
                Integer llavePrimaria = llaves.getInt(1);
                cuenta.setNumCuenta(llavePrimaria);
                return cuenta;
            }
            throw new PersistenciaException("Cuenta registrada pero número no generado");
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new PersistenciaException("No fue posible registrar la cuenta");
        }

    }

    /**
     * Método cancelar el cual cancela una Cuenta de un cliente.
     *
     * @param idCuenta Número de la cuenta a cancelar.
     * @param idCliente ID del cliente dueño de la cuenta a cancelar.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de
     * persistencia.
     */
    @Override
    public void cancelar(int idCuenta, int idCliente) throws PersistenciaException {
        String codigoSQL = "UPDATE CUENTAS SET ESTADO = ? WHERE NUM_CUENTA = ? AND ID_CLIENTE = ?;";
        try (
                Connection conexion = this.GENERADOR_CONEXION.crearConexion(); PreparedStatement comando = conexion.prepareStatement(codigoSQL);) {
            comando.setString(1, "Cancelado");
            comando.setInt(2, idCuenta);
            comando.setInt(3, idCliente);
            comando.executeUpdate();
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new PersistenciaException("No fue posible cancelar la cuenta");
        }
    }

}

 /**
 * Clase ClientesDAO.java creada el 15/02/2023.
 */
package implementaciones;

import dominio.Cliente;
import dominio.Direccion;
import excepciones.PersistenciaException;
import interfaces.IClientesDAO;
import interfaces.IConexionBD;
import java.util.*;
import java.sql.*;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import java.sql.Date;

/**
 * Este clase representa el objeto que permite acceder a los datos de clientes.
 * @author 00000233259 y 00000233410.
 */
public class ClientesDAO implements IClientesDAO {
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
    public ClientesDAO(IConexionBD generadorConexion) {
        this.GENERADOR_CONEXION = generadorConexion;
    }

     /**
     * Método consultarCredenciales el cual consulta la contraseña y la ID del cliente para verificar si coincide con los datos recuperados de la UI.
     * @param idCliente ID del cliente a consultar.
     * @return El cliente si ha sido encontrado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public Cliente consultarCredenciales(int idCliente) throws PersistenciaException  {
        String codigoSQL =  "SELECT id,CAST(AES_DECRYPT(password, 563) AS CHAR)AS password FROM clientes where id = ?;";
        try (
            Connection conexion = GENERADOR_CONEXION.crearConexion();
            PreparedStatement comando = conexion.prepareStatement(codigoSQL);){
            comando.setInt(1, idCliente);
            ResultSet resultado = comando.executeQuery();
            Cliente cliente = null;
             if(resultado.next()){
             int id = resultado.getInt("id");
             String password = resultado.getString("password");
             cliente = new Cliente(id,password);
             return cliente;
            }
            return null;
        } catch (SQLException ex) {
           LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar al cliente");
        }
    }
     /**
     * Método consultar el cual consulta a un cliente desde la base de datos.
     * @param idCliente ID del cliente a consultar.
     * @return Cliente si ha sido encontrado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public Cliente consultar(int idCliente) throws PersistenciaException  {
        String codigoSQL =  "SELECT clientes.*,CAST(AES_DECRYPT(password, 563) AS CHAR)AS PASS FROM clientes where id = ?;";
        try (
            Connection conexion = GENERADOR_CONEXION.crearConexion();
            PreparedStatement comando = conexion.prepareStatement(codigoSQL);){
            comando.setInt(1, idCliente);
            ResultSet resultado = comando.executeQuery();
            Cliente cliente = null;
             if(resultado.next()){
             int id = resultado.getInt("id");
             String password = resultado.getString("pass");
             Date fechaNacimiento = resultado.getDate("fecha_nacimiento");
             String nombres = resultado.getString("nombres");
             String apellidoPaterno = resultado.getString("apellido_paterno");
             String apellidoMaterno = resultado.getString("apellido_materno");
             Integer idDireccion = resultado.getInt("id_direccion");
             Integer edad = resultado.getInt("edad");
             cliente = new Cliente(id, new Direccion(idDireccion), edad, nombres, apellidoPaterno, apellidoMaterno, password, fechaNacimiento);
             return cliente;
            }
            return null;
        } catch (SQLException ex) {
           LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar al cliente");
        }
    }
    
    /**
     * Método insertar el cual inserta un cliente a la base de datos, recuperando los datos de la UI.
     * @param cliente Cliente a insertar.
     * @return Cliente si ha sido insertado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public Cliente insertar(Cliente cliente) throws PersistenciaException {
        String codigoSQL = "INSERT INTO CLIENTES (nombres,apellido_paterno,apellido_materno,id_direccion,password,fecha_nacimiento) values (?,?,?,?,AES_ENCRYPT(?,563),?);";

        try (
                Connection conexion = this.GENERADOR_CONEXION.crearConexion();
                PreparedStatement comando = conexion.prepareStatement(codigoSQL, Statement.RETURN_GENERATED_KEYS);) {
            comando.setString(1, cliente.getNombre());
            comando.setString(2, cliente.getApellidoPaterno());
            comando.setString(3, cliente.getApellidoMaterno());
            comando.setInt(4, cliente.getDireccion().getId());
            comando.setString(5, cliente.getPassword());
            comando.setDate(6, cliente.getFechaNacimiento());
            comando.executeUpdate();
            ResultSet llaves = comando.getGeneratedKeys();
            if (llaves.next()) {
                Integer llavePrimaria = llaves.getInt(1);
                cliente.setId(llavePrimaria);
                return cliente;
            }
            throw new PersistenciaException("Cliente registrado, pero ID no generado");

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new PersistenciaException("No fue posible registrar al cliente");
        }
    }

 
 /**
     * Método acutalizar el cual actualiza a un cliente desde la base de datos, recuperando los datos de la UI.
     * @param cliente Cliente a actualizar.
     * @return Cliente si ha sido actualizado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    @Override
    public Cliente actualizar(Cliente cliente) throws PersistenciaException {
        String codigoSQL = "UPDATE CLIENTES SET NOMBRES = ?, APELLIDO_PATERNO = ?,"
                + "APELLIDO_MATERNO = ?, ID_DIRECCION = ?, PASSWORD = AES_ENCRYPT(?,563), FECHA_NACIMIENTO = ? "
                + "WHERE ID = ?;";
        try (
                Connection conexion = this.GENERADOR_CONEXION.crearConexion();
                PreparedStatement comando = conexion.prepareStatement(codigoSQL);) {
            comando.setString(1, cliente.getNombre());
            comando.setString(2, cliente.getApellidoPaterno());
            comando.setString(3, cliente.getApellidoMaterno());
            comando.setInt(4, cliente.getDireccion().getId());
            comando.setString(5, cliente.getPassword());
            comando.setDate(6, cliente.getFechaNacimiento());
            comando.setInt(7, cliente.getId());
            comando.executeUpdate();
            if(this.consultar(cliente.getId()).equals(cliente)){
                 return cliente;
            }else{
                 throw new PersistenciaException("Cliente no actualizado");
            }
        } catch (SQLException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new PersistenciaException("No fue posible actualizar al cliente");
        }
    }
}

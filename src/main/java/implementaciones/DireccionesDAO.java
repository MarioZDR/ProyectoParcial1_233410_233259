/**
 * Clase DireccionesDAO.java creada el 15/02/2023.
 */
package implementaciones;

import dominio.Direccion;
import excepciones.PersistenciaException;
import interfaces.IConexionBD;
import interfaces.IDireccionesDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Este clase representa el objeto que permite acceder a los datos de direcciones.
 * @author 00000233259 y 00000233410.
 */
public class DireccionesDAO implements IDireccionesDAO {
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
    public DireccionesDAO(IConexionBD generadorConexion) {
        this.GENERADOR_CONEXION = generadorConexion;
    }

    /**
     * Método insertar el cual inserta una Dirección en la base de datos.
     *
     * @param direccion Dirección a insertar.
     * @return Dirección si se ha insertado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de
     * persistencia.
     */
    @Override
    public Direccion insertar(Direccion direccion) throws PersistenciaException {
        String codigoSQL = "INSERT INTO DIRECCIONES (numero,calle,colonia) values (?,?,?);";

        try (
                Connection conexion = this.GENERADOR_CONEXION.crearConexion(); PreparedStatement comando = conexion.prepareStatement(codigoSQL, Statement.RETURN_GENERATED_KEYS);) {
            comando.setString(1, direccion.getNumeroCasa());
            comando.setString(2, direccion.getCalle());
            comando.setString(3, direccion.getColonia());

            comando.executeUpdate();
            ResultSet llaves = comando.getGeneratedKeys();
            if (llaves.next()) {
                Integer llavePrimaria = llaves.getInt(1);
                direccion.setId(llavePrimaria);
                return direccion;
            }
            throw new PersistenciaException("Dirección registrada, pero ID no generado");

        } catch (SQLException e) {
            LOG.log(Level.SEVERE, e.getMessage());
            throw new PersistenciaException("No fue posible registrar la dirección");
        }
    }

    /**
     * Método consultar el cual consulta una Dirección de un cliente.
     *
     * @param id ID del cliente asociado a la Dirección.
     * @return Dirección si se ha consultado.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de
     * persistencia.
     */
    @Override
    public Direccion consultar(Integer id) throws PersistenciaException {
        String codigoSQL = "SELECT numero, calle, colonia FROM direcciones where id = ?;";
        try (
                Connection conexion = GENERADOR_CONEXION.crearConexion(); PreparedStatement comando = conexion.prepareStatement(codigoSQL);) {
            comando.setInt(1, id);
            ResultSet resultado = comando.executeQuery();
            Direccion direccion = null;
            if (resultado.next()) {
                String calle = resultado.getString("calle");
                String numero = resultado.getString("numero");
                String colonia = resultado.getString("colonia");
                direccion = new Direccion(numero, calle, colonia);
                return direccion;
            }
            return null;
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
            throw new PersistenciaException("No fue posible consultar la direccion");
        }
    }
}

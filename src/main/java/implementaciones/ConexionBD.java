/**
 * Clase ConexionBD.java creada el 15/02/2023.
 */
package implementaciones;

import interfaces.IConexionBD;
import java.sql.*;

/**
 * Este clase representa la conexión a una base de datos.
 * @author 00000233259 y 00000233410.
 */
public class ConexionBD implements IConexionBD{
    /**
     * Atributo que representa el usuario para la conexión.
     */
    private String usuario;
    /**
     * Atributo que representa la contraseña para la conexión.
     */
    private String password;
    /**
     * Atributo que representa la dirección de la conexión.
     */
    private String cadenaConexion;
    /**
     * Método constructor que inicializa los atributos al valor de sus parámetros.
     * @param cadenaConexion dirección de la conexión.
     * @param usuario usuario para la conexión.
     * @param password contraseña de la conexión.
     */
    public ConexionBD(String cadenaConexion, String usuario, String password){
        this.usuario=usuario;
        this.password=password;
        this.cadenaConexion=cadenaConexion;
    }
    /**
     * Método crearConexión que crea la conexión hacía la base de datos.
     * @return la conexión creada.
     * @throws SQLException Se lanza al ocurrir un error SQL.
     */
    @Override
    public Connection crearConexion() throws SQLException{
        Connection conexion = DriverManager.getConnection(cadenaConexion, usuario, password);
        return conexion;
    }
    
}

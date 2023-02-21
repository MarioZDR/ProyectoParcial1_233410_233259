/**
 * Clase IConexionBD.java creada el 15/02/2023.
 */
package interfaces;

import java.sql.*;

/**
 * Este clase representa la interfaz la conexión a base de datos.
 * @author 00000233259 y 00000233410.
 */
public interface IConexionBD {
    /**
     * Método crearConexión que crea la conexión hacía la base de datos.
     * @return la conexión creada.
     * @throws SQLException Se lanza al ocurrir un error SQL.
     */
    Connection crearConexion() throws SQLException;
}

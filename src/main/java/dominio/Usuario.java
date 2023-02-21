 /**
 * Método para asignar el estado del retiro.
 * @return 
*/
package dominio;

/**
 * Este clase representa a un usuario logeado.
 * @author 00000233259 y 00000233410.
 */
public class Usuario {
    /**
     * Atributo que representa a un cliente.
     */
    private Cliente cliente; 
    /**
     * Constructor que inicializa los atributos al valor de los parámetros
     * enviados.
     * @param cliente Cliente logeado.
     */
    public Usuario(Cliente cliente) {
        this.cliente = cliente;
    }
    /**
     * Método para obtener el cliente.
     * @return Cliente obtenido.
     */
    public Cliente getCliente() {
        return cliente;
    }
    /**
     * Método para asignar un cliente.
     * @param cliente Cliente a asignar.
     */
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
    
    

}

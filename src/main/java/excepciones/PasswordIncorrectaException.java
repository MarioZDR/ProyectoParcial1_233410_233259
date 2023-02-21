/**
 * Clase PasswordIncorrectaException.java creada el 15/02/2023.
 */
package excepciones;

/**
 * Este clase representa las excepciones de error de contraseña.
 * @author 00000233410_00000233259
 */
public class PasswordIncorrectaException extends Exception{
    /**
     * Método constructor por defecto.
     */
    public PasswordIncorrectaException() {
    }
    /**
     * Método constructor que llama al constructor de la clase padre.
     * @param string Cadena a enviar.
     */
    public PasswordIncorrectaException(String string) {
        super(string);
    }
     /**
     * Método constructor que llama al constructor de la clase padre.
     * @param string Cadena a enviar.
     * @param thrwbl Representa la causa de la excepción.
     */
    public PasswordIncorrectaException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }
    /**
     * Método constructor que llama al constructor de la clase padre.
     * @param thrwbl Representa la causa de la excepción.
     */
    public PasswordIncorrectaException(Throwable thrwbl) {
        super(thrwbl);
    }
    /**
     * Método constructor que llama al constructor de la clase padre.
     * @param string Cadena a enviar.
     * @param thrwbl Representa la causa de la excepción.
     * @param bln Indica si se debe incluir la causa de la excepción en la traza de la pila de la excepción
     * @param bln1 Indica si se debe habilitar o no la supresión de excepciones
     */
    public PasswordIncorrectaException(String string, Throwable thrwbl, boolean bln, boolean bln1) {
        super(string, thrwbl, bln, bln1);
    }

  
    
}

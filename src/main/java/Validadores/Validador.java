/**
 * Clase Validadores.java creada el 15/02/2023.
 */
package validadores;

import dominio.Cliente;
import dominio.Direccion;
import excepciones.PersistenciaException;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Este clase representa los validadores del proyecto.
 * @author 00000233410_00000233259
 */
public class Validador {

    /**
     * Constructor por defecto.
     */
    public Validador() {

    }

    /**
     * Método que valida un campo.
     *
     * @param nombre campo a validar 5 a 30 caracteres solo letras y espacios.
     * @return Verdadero o falso si se ha validado el campo.
     * @throws excepciones.PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    public boolean validaCampoSinNumero(String nombre)throws PersistenciaException {
        String patron = "^(?=.{1,35}$)[a-zA-Z]+(?: [a-zA-Z]+)*$";
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(nombre);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Método que valida un campo.
     *
     * @param nombre campo a validar 5 a 30 caracteres letras, espacio, "_" y numeros.
     * @return Verdadero o falso si se ha validado el campo.
     * @throws excepciones.PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    public boolean validaCampoConNumero(String nombre)throws PersistenciaException {
        String patron = "^(?=.{1,35}$)[a-zA-Z0-9]+(?: [a-zA-Z0-9]+)*$";
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(nombre);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
    
    /**
     * Método que valida una contraseña.
     *
     * @param pass contraseña a validar.
     * @return Verdadero o falso si se ha validado el contraseña.
     * @throws excepciones.PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    public boolean validaContrasenia(String pass)throws PersistenciaException {
        String patron = "^(?i)(?=.*[a-z])(?=.*[0-9])[a-z0-9#.!@$*&_]{8,30}$";
        Pattern p = Pattern.compile(patron);
        Matcher matcher = p.matcher(pass);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * Método que valida una fecha.
     *
     * @param fecha fecha a validar.
     * @return Verdadero o falso si se ha validado el fecha.
     * @throws excepciones.PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    public boolean validaFecha(Date fecha) throws PersistenciaException{
        if (fecha != null) {
            return true;
        } else {
            throw new PersistenciaException("Selecciona una fecha.");
        }
    }
    /**
     * Método que valida a un cliente.
     * @param cliente Cliente a validar.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    public void validarCliente(Cliente cliente) throws PersistenciaException {
        if(cliente.getPassword()!= null && !cliente.getPassword().trim().equals("") && this.validaContrasenia(cliente.getPassword())){
        }else{
             throw new PersistenciaException("Formato inválido de contraseña. Ingresa mínimo de 8 carácteres y utiliza números.");
        }
        if (cliente.getNombre()!= null && cliente.getApellidoMaterno()!= null && 
                cliente.getApellidoPaterno()!= null) {
            if (!cliente.getNombre().trim().equals("")
                    && !cliente.getApellidoMaterno().trim().equals("")
                    && !cliente.getApellidoPaterno().trim().equals("")) {
                if(this.validaCampoSinNumero(cliente.getNombre())&&
                        this.validaCampoSinNumero(cliente.getApellidoMaterno())
                        &&this.validaCampoSinNumero(cliente.getApellidoPaterno())){
                    System.out.println("Cliente validado");
                }else{
                    throw new PersistenciaException("Formato invalido del cliente");
                }
            }else{
                throw new PersistenciaException("Formato invalido del cliente");
            }
        }else{
            throw new PersistenciaException("Formato invalido del cliente");
        }
    }
    /**
     * Método que valida un cliente.
     * @param direccion Dirección a validar.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    public void validarDireccion(Direccion direccion) throws PersistenciaException {
        if (direccion.getCalle() != null && direccion.getColonia() != null && direccion.getNumeroCasa() != null) {
            if (!direccion.getCalle().trim().equals("")
                    && !direccion.getColonia().trim().equals("")
                    && !direccion.getNumeroCasa().trim().equals("")) {
                if(this.validaCampoConNumero(direccion.getCalle())&&
                        this.validaCampoConNumero(direccion.getColonia())&&
                        this.validaCampoConNumero(direccion.getNumeroCasa())){
                    System.out.println("Direccion valida");
                }else{
                     throw new PersistenciaException("Formato invalido de la direccion");
                }
            }else{
                throw new PersistenciaException("Formato invalido de la direccion");
            }
        }else{
            throw new PersistenciaException("Formato invalido de la direccion");
        }
    }
    /**
     * Método que valida una ID.
     * @param id ID a validar.
     * @return Valor booleano.
     */
    public boolean validarID(String id){
        return !id.equals("");
    }
    /**
     * Método para validar el nombre de una cuenta.
     * @param nombre Nombre a validar.
     * @return Valor booleano.
     * @throws PersistenciaException Se lanza al ocurrir un error en la capa de persistencia.
     */
    public boolean validarNombreCuenta(String nombre) throws PersistenciaException {
        if (nombre.matches("[a-zA-Z]{1,15}")) {
            return true;
        } else {
            throw new PersistenciaException("Formato de nombre inválido");
        }
    }
}

/**
 * Clase GeneradorClaves.java creada el 15/02/2023.
 */
package auxiliares;

import java.util.Random;

/**
 * Este clase representa el generador de claves del proyecto.
 * @author 00000233410_00000233259
 */
public class GeneradorClaves {
    /**
     * Método constructor por defecto.
     */
    public GeneradorClaves() {
    }
    /**
     * Método generarClave para generar una clave aleatoria, siendo el tamaño el valor del parámetro longitud.
     * @param longitud Tamaño de la clave.
     * @return Clave generada.
     */
    public static String generarClave(int longitud) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < longitud; i++) {
            int numeroAleatorio = random.nextInt(10);
            sb.append(numeroAleatorio);
        }
        return sb.toString();
    }
}

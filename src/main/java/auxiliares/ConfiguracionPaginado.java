/**
 * Clase ConfiguracionPaginado.java creada el 15/02/2023.
 */
package auxiliares;

/**
 * Este clase representa la configuración del paginado de las tablas.
 * @author 00000233410_00000233259
 */
public class ConfiguracionPaginado {
    /**
     * Atributo que representa el número de páginas.
     */
    private int numeroPagina;
    /**
     * Atributo que representa el número de elementos por página.
     */
    private int elementosPorPagina;
    /**
     * Método consutructor que inicializa los atributos de la clase.
     */
    public ConfiguracionPaginado() {
        this.numeroPagina = 0;
        this.elementosPorPagina = 5;
    }
    /**
     * Método constructor que inicializa los atributos de la clase al valor de sus parámetros.
     * @param numeroPagina Número de páginas.
     * @param elementosPorPagina Elementos por página.
     */
    public ConfiguracionPaginado(int numeroPagina, int elementosPorPagina) {
        this.numeroPagina = numeroPagina;
        this.elementosPorPagina = elementosPorPagina;
    }
    /**
     * Método para obtener el número de páginas.
     * @return Número de páginas.
     */
    public int getNumeroPagina() {
        return numeroPagina;
    }
    /**
     * Método para asignar el número de páginas.
     * @param numeroPagina Número de páginas.
     */
    public void setNumeroPagina(int numeroPagina) {
        this.numeroPagina = numeroPagina;
    }
    /**
     * Método para obtener el número de elementos por página.
     * @return Número de elementos por página.
     */
    public int getElementosPorPagina() {
        return elementosPorPagina;
    }
    /**
     * Método para asignar el número de elementos por página.
     * @param elementosPorPagina Elementos por página.
     */
    public void setElementosPorPagina(int elementosPorPagina) {
        this.elementosPorPagina = elementosPorPagina;
    }
    /**
     * Método para obtener los elementos a saltar.
     * @return Número de elementos a saltar.
     */
    public int getElementosASaltar() {
        return this.numeroPagina*this.elementosPorPagina;
    }
    /**
     * Método que incrementa el atributo de numeroPagina.
     */
    public void avanzarPagina() {
        this.numeroPagina++;
    }
    /**
     * Método que decrementa el atributo numeroPagina.
     */
    public void retrocederPagina() {
        if (this.numeroPagina > 0) {
            this.numeroPagina--;
        }
    }
}

/**
 * Clase CuentaComboBoxModel.java creada el 15/02/2023.
 */
package auxiliares;

import dominio.Cuenta;
import java.util.List;
import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

/**
 * Este clase representa el Modelo del comboBox de las cuentas.
 * @author 00000233410_00000233259
 */
public class CuentaComboBoxModel extends AbstractListModel<Cuenta> implements ComboBoxModel<Cuenta>{
    /**
     * Atributo que representa una lista de cuentas.
     */
    private List<Cuenta> cuentas;
    /**
     * Atributo que representa la cuenta seleccionada.
     */
    private Cuenta cuentaSeleccionada;
    /**
     * Método constructor que inicializa el atributo cuentas al valor del parámetro enviado.
     * @param cuentas Lista de cuentas.
     */
    public CuentaComboBoxModel(List<Cuenta> cuentas) {
        this.cuentas=cuentas;
    }
    /**
     * Método para obtener el tamaño de la lista.
     * @return tamaño de la lista.
     */
    @Override
    public int getSize() {
        return cuentas.size();
    }
    /**
     * Método para obtener un elemento de la lista.
     * @param index Index del elemento.
     * @return Elemento perteneciente al index.
     */
    @Override
    public Cuenta getElementAt(int index) {
        return cuentas.get(index);
    }
    /**
     * Método para asignar la cuenta seleccionada.
     * @param item Cuenta seleccionada.
     */
    @Override
    public void setSelectedItem(Object item) {
        cuentaSeleccionada = (Cuenta) item;
    }
    /**
     * Método para obtener la cuenta seleccionada.
     * @return Cuenta seleccionada.
     */
    @Override
    public Object getSelectedItem() {
        return cuentaSeleccionada;
    }
}

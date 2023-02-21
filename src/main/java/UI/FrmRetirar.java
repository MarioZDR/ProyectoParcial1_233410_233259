/*
 * FrmRetirar.java creada el 20/02/2023.
 */
package UI;

import auxiliares.CuentaComboBoxModel;
import auxiliares.GeneradorClaves;
import dominio.Cuenta;
import dominio.Retiro;
import dominio.Transaccion;
import implementaciones.ClientesDAO;
import interfaces.IClientesDAO;
import interfaces.IDireccionesDAO;
import java.util.logging.Logger;
import dominio.Usuario;
import excepciones.PersistenciaException;
import interfaces.ICuentasDAO;
import interfaces.ITransaccionesDAO;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 * Este frame se utiliza para generar retiros.
 *
 * @author 00000233259 y 00000233410.
 */
public class FrmRetirar extends javax.swing.JFrame {

    /**
     * Este atributo se utiliza para llevar un registro cronológicamente de los
     * datos.
     */
    private static final Logger LOG = Logger.getLogger(ClientesDAO.class.getName());
    /**
     * Este atributo representa la DAO de Clientes.
     */
    public final IClientesDAO clientesDAO;
    /**
     * Este atributo representa la DAO de Direcciones.
     */
    public final IDireccionesDAO direccionesDAO;
    /**
     * Este atributo representa la DAO de Transacciones.
     */
    public final ITransaccionesDAO transaccionesDAO;
    /**
     * Este atributo representa la DAO de Cuentas.
     */
    public final ICuentasDAO cuentasDAO;
    /**
     * Este atributo representa el usuario logeado actualmente.
     */
    private Usuario usuarioLogeado;

    /**
     * Método constructor que inicializa los atributos al valor de sus
     * parámetros.
     *
     * @param clientesDAO representa la DAO de Clientes.
     * @param direccionesDAO representa la DAO de Direcciones.
     * @param transaccionesDAO representa la DAO de Transacciones.
     * @param cuentasDAO representa la DAO de Cuentas.
     * @param usuarioLogeado representa el usuario actual.
     */
    public FrmRetirar(IClientesDAO clientesDAO, IDireccionesDAO direccionesDAO, ITransaccionesDAO transaccionesDAO, ICuentasDAO cuentasDAO, Usuario usuarioLogeado) {
        this.clientesDAO = clientesDAO;
        this.direccionesDAO = direccionesDAO;
        this.transaccionesDAO = transaccionesDAO;
        this.cuentasDAO = cuentasDAO;
        this.usuarioLogeado = usuarioLogeado;
        initComponents();
        insertarLista();
        this.btnGenerarFolioPassword.setText("Generar folio y contraseña");
        this.setSize(440, 340);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    /**
     * Este método inserta la lista de cuentas activas pertenecientes al cliente
     * logeado en una ComboBox.
     */
    private void insertarLista() {
        try {
            List<Cuenta> cuentas = cuentasDAO.consultarLista(usuarioLogeado.getCliente().getId());
            CuentaComboBoxModel model = new CuentaComboBoxModel(cuentas);
            this.cbxCuentas.setModel(model);
            this.cbxCuentas.setSelectedIndex(0);
        } catch (PersistenciaException ex) {
            mostrarMensaje("Crea una cuenta para poder transferir");
            regresarMenu();
        }
    }

    /**
     * Este método realiza la extracción de los datos de la interfaz de usuario.
     *
     * @return Regresa un objeto retiro con los datos extraidos.
     */
    private Retiro extraerDatos() {
        String folio = GeneradorClaves.generarClave(10);
        String password = GeneradorClaves.generarClave(8);
        Integer numeroCuentaOrigen = ((Cuenta) cbxCuentas.getSelectedItem()).getNumCuenta();
        Float monto = Float.valueOf(0);
        Retiro retiro = new Retiro(numeroCuentaOrigen, folio, password, monto);
        return retiro;
    }

    /**
     * Este método realiza el registro de la generación del retiro utilizando la
     * DAO.
     */
    public void registrarRetiro() {
        if (verInformacionRetiro()) {
            return;
        }
        Retiro retiroExtraido = extraerDatos();
        try {
            Retiro retiro = transaccionesDAO.crearRetiro(retiroExtraido);
            if (retiro != null) {
                mostrarMensaje("Retiro registrado");
                verInformacionRetiro();
            }
        } catch (PersistenciaException ex) {
            this.mostrarMensaje(ex.getMessage());
            Logger.getLogger(FrmRetirar.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método realiza una validación para mostrar información de algún
     * retiro activo.
     *
     * @return Regresa true en caso un retiro activo existente, false en caso
     * contrario.
     */
    public boolean verInformacionRetiro() {
        try {
            Retiro retiroActualizado = transaccionesDAO.consultarUltimoRetiro(usuarioLogeado.getCliente().getId());
            if (retiroActualizado != null) {
                transaccionesDAO.actualizarRetiro(retiroActualizado);
            }
            Retiro ultimoRetiro = transaccionesDAO.consultarUltimoRetiro(usuarioLogeado.getCliente().getId());
            if (ultimoRetiro != null && !ultimoRetiro.getEstado().equalsIgnoreCase("No cobrado")) {
                mostrarMensaje("Folio : " + ultimoRetiro.getFolio() + "\n" + "Contraseña: " + ultimoRetiro.getPassword());
                btnGenerarFolioPassword.setText("Ver de nuevo");
                return true;
            }
        } catch (PersistenciaException ex) {
            this.mostrarMensaje(ex.getMessage());
            Logger.getLogger(FrmRetirar.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * Este método muestra un mensaje en un JOptionPane.
     *
     * @param msj El mensaje a mostrar.
     */
    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Este método regresa al menú principal.
     */
    private void regresarMenu() {
        FrmMenu frmMenu = new FrmMenu(clientesDAO, direccionesDAO, transaccionesDAO, cuentasDAO, usuarioLogeado);
        this.setVisible(false);
        frmMenu.setVisible(true);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnFondoGeneral = new javax.swing.JPanel();
        lblRetirar = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        jpnFondo = new javax.swing.JPanel();
        lblNumeroCuenta = new javax.swing.JLabel();
        cbxCuentas = new javax.swing.JComboBox<>();
        btnGenerarFolioPassword = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnFondoGeneral.setBackground(new java.awt.Color(12, 28, 68));
        jpnFondoGeneral.setForeground(new java.awt.Color(12, 28, 68));
        jpnFondoGeneral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblRetirar.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 50)); // NOI18N
        lblRetirar.setForeground(new java.awt.Color(255, 255, 255));
        lblRetirar.setText("Retirar");
        jpnFondoGeneral.add(lblRetirar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        btnRegresar.setBackground(new java.awt.Color(183, 210, 242));
        btnRegresar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jpnFondoGeneral.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, -1, -1));

        jpnFondo.setBackground(new java.awt.Color(183, 210, 242));

        lblNumeroCuenta.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblNumeroCuenta.setText("Selecciona una de tus cuentas:");

        btnGenerarFolioPassword.setBackground(new java.awt.Color(65, 103, 158));
        btnGenerarFolioPassword.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnGenerarFolioPassword.setForeground(new java.awt.Color(255, 255, 255));
        btnGenerarFolioPassword.setText("Generar folio y contraseña");
        btnGenerarFolioPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarFolioPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jpnFondoLayout = new javax.swing.GroupLayout(jpnFondo);
        jpnFondo.setLayout(jpnFondoLayout);
        jpnFondoLayout.setHorizontalGroup(
            jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnFondoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnGenerarFolioPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNumeroCuenta))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jpnFondoLayout.setVerticalGroup(
            jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnFondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblNumeroCuenta)
                .addGap(18, 18, 18)
                .addComponent(cbxCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnGenerarFolioPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(73, 73, 73))
        );

        jpnFondoGeneral.add(jpnFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 390, 170));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondoGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 472, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnFondoGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se regresa
     * al menú.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        FrmMenu frmMenu = new FrmMenu(clientesDAO, direccionesDAO, transaccionesDAO, cuentasDAO, usuarioLogeado);
        this.setVisible(false);
        frmMenu.setVisible(true);
    }//GEN-LAST:event_btnRegresarActionPerformed
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se llama
     * al método registrarRetiro.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnGenerarFolioPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarFolioPasswordActionPerformed
        registrarRetiro();
    }//GEN-LAST:event_btnGenerarFolioPasswordActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGenerarFolioPassword;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<Cuenta> cbxCuentas;
    private javax.swing.JPanel jpnFondo;
    private javax.swing.JPanel jpnFondoGeneral;
    private javax.swing.JLabel lblNumeroCuenta;
    private javax.swing.JLabel lblRetirar;
    // End of variables declaration//GEN-END:variables
}

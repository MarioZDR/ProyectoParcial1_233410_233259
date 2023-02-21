/*
 * Clase FrmCancelarCuenta.java creada el 20/02/2023.
 */
package UI;

import auxiliares.CuentaComboBoxModel;
import dominio.Cliente;
import dominio.Cuenta;
import implementaciones.ClientesDAO;
import interfaces.IClientesDAO;
import interfaces.IDireccionesDAO;
import java.util.logging.Logger;
import dominio.Usuario;
import excepciones.PasswordIncorrectaException;
import excepciones.PersistenciaException;
import interfaces.ICuentasDAO;
import interfaces.ITransaccionesDAO;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 * Este frame se utiliza para la cancelación de cuentas.
 *
 * @author 00000233259 y 00000233410.
 */
public class FrmCancelarCuenta extends javax.swing.JFrame {

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
    public FrmCancelarCuenta(IClientesDAO clientesDAO, IDireccionesDAO direccionesDAO, ITransaccionesDAO transaccionesDAO, ICuentasDAO cuentasDAO, Usuario usuarioLogeado) {
        this.clientesDAO = clientesDAO;
        this.direccionesDAO = direccionesDAO;
        this.transaccionesDAO = transaccionesDAO;
        this.cuentasDAO = cuentasDAO;
        this.usuarioLogeado = usuarioLogeado;
        initComponents();
        this.setSize(470, 325);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        insertarLista();
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
     * Este método realiza la lógica de cancelar la cuenta.
     */
    private void cancelarCuenta() {
        int numCuenta = extraerCuenta();
        try {
            validarPassword();
            cuentasDAO.cancelar(numCuenta, usuarioLogeado.getCliente().getId());
            mostrarMensaje("Cuenta " + numCuenta + " Cancelada");
            regresarMenu();
        } catch (PersistenciaException ex) {
            mostrarMensaje(ex.getMessage());
            Logger.getLogger(FrmCancelarCuenta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (PasswordIncorrectaException ex) {
            mostrarMensaje(ex.getMessage());
            Logger.getLogger(FrmCancelarCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método se utiliza para extraer una cuenta seleccionada por el
     * usuario en la ComboBox.
     *
     * @return Regresa el número de cuenta de la cuenta extraida.
     */
    private Integer extraerCuenta() {
        return ((Cuenta) cbxCuentas.getSelectedItem()).getNumCuenta();
    }

    /**
     * Este método realiza la validación de la contraseña ingresada.
     *
     * @return Regresa verdadero si la contraseña.
     * @throws PasswordIncorrectaException Se lanza esta excepción en caso de
     * que la contraseña sea incorrecta.
     * @throws PersistenciaException Se lanza en caso de ocurrir algún error en
     * la capa de persistencia.
     */
    private boolean validarPassword() throws PasswordIncorrectaException, PersistenciaException {
        Cliente cliente = this.usuarioLogeado.getCliente();;
        if (cliente.getPassword().equals(txtPassword.getText())) {
            return true;
        } else {
            throw new PasswordIncorrectaException("Contraseña incorrecta");
        }
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
     * Este método muestra un mensaje en un JOptionPane.
     *
     * @param msj El mensaje a mostrar.
     */
    private void mostrarMensaje(String msj) {
        JOptionPane.showMessageDialog(null, msj, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnFondo = new javax.swing.JPanel();
        lblCancelarCuenta = new javax.swing.JLabel();
        btnAceptar = new javax.swing.JButton();
        lblSeleccionar = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();
        cbxCuentas = new javax.swing.JComboBox<>();
        txtPassword = new javax.swing.JPasswordField();
        lblPassword = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnFondo.setBackground(new java.awt.Color(12, 28, 68));
        jpnFondo.setForeground(new java.awt.Color(12, 28, 68));
        jpnFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblCancelarCuenta.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 50)); // NOI18N
        lblCancelarCuenta.setForeground(new java.awt.Color(255, 255, 255));
        lblCancelarCuenta.setText("Cancelar Cuenta");
        jpnFondo.add(lblCancelarCuenta, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        btnAceptar.setBackground(new java.awt.Color(65, 103, 158));
        btnAceptar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAceptar.setForeground(new java.awt.Color(255, 255, 255));
        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });
        jpnFondo.add(btnAceptar, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 220, 90, 40));

        lblSeleccionar.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblSeleccionar.setForeground(new java.awt.Color(255, 255, 255));
        lblSeleccionar.setText("Selecciona una de tus cuentas:");
        jpnFondo.add(lblSeleccionar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, -1, -1));

        btnRegresar.setBackground(new java.awt.Color(183, 210, 242));
        btnRegresar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jpnFondo.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, -1));
        jpnFondo.add(cbxCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 400, 30));
        jpnFondo.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 400, 40));

        lblPassword.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblPassword.setForeground(new java.awt.Color(255, 255, 255));
        lblPassword.setText("Contraseña");
        jpnFondo.add(lblPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 469, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se llama
     * al método que cancela la cuenta.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        cancelarCuenta();
    }//GEN-LAST:event_btnAceptarActionPerformed
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


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<Cuenta> cbxCuentas;
    private javax.swing.JPanel jpnFondo;
    private javax.swing.JLabel lblCancelarCuenta;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSeleccionar;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}

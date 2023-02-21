/*
 * FrmTransferencia.java creada el 20/02/2023.
 */
package UI;

import auxiliares.CuentaComboBoxModel;
import dominio.Cuenta;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 * Este frame se utiliza realizar transferencias entre cuentas.
 *
 * @author 00000233259 y 00000233410.
 */
public class FrmTransferencia extends javax.swing.JFrame {

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
    public FrmTransferencia(IClientesDAO clientesDAO, IDireccionesDAO direccionesDAO, ITransaccionesDAO transaccionesDAO, ICuentasDAO cuentasDAO, Usuario usuarioLogeado) {
        this.clientesDAO = clientesDAO;
        this.direccionesDAO = direccionesDAO;
        this.transaccionesDAO = transaccionesDAO;
        this.cuentasDAO = cuentasDAO;
        this.usuarioLogeado = usuarioLogeado;
        initComponents();
        this.setSize(440, 340);
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
     * Este método realiza las validaciones correspondientes a los datos
     * ingresados por el usuario.
     *
     * @param transaccion Es el objeto que tiene los datos ingresados por el
     * usuario.
     * @return Regresa true en caso de que sean validados, false en caso
     * contrario.
     * @throws PersistenciaException Se lanza en caso de un error en la capa de
     * persistencia.
     */
    private boolean validaciones(Transaccion transaccion) throws PersistenciaException {
        try {
            validarSaldo(transaccion);
            validarCuentaDestino(transaccion);
            validarCuentas(transaccion);
        } catch (PersistenciaException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
        return false;
    }

    /**
     * Este método realiza la validación del saldo disponible.
     *
     * @param transaccion Representa el objeto Transaccion a validar.
     * @return Regresa true en caso de que sea válido, false en caso contrario.
     * @throws PersistenciaException Se lanza en caso de un error en la capa de
     * persistencia.
     */
    private boolean validarSaldo(Transaccion transaccion) throws PersistenciaException {

        if (!this.txtSaldo.getText().trim().equals("")) {
            if (cuentasDAO.consultar(transaccion.getIdCuentaOrigen()).getSaldo() >= transaccion.getMonto() && (transaccion.getMonto() > 0)) {
                return true;
            } else {
                throw new PersistenciaException("Saldo insuficiente");
            }
        } else {
            throw new PersistenciaException("Ingresa un monto");
        }
    }

    /**
     * Este método realiza la validación de las cuentas ingresadas.
     *
     * @param transaccion Representa el objeto Transaccion a validar.
     * @return Regresa true en caso de que sean válidas, false en caso
     * contrario.
     * @throws PersistenciaException Se lanza en caso de un error en la capa de
     * persistencia.
     */
    private boolean validarCuentas(Transaccion transaccion) throws PersistenciaException {
        if (transaccion.getIdCuentaDestino() != transaccion.getIdCuentaOrigen()) {
            return true;
        } else {
            throw new PersistenciaException("No se puede realizar una transacción a la misma cuenta");
        }

    }

    /**
     * Este método realiza la validación de la cuenta destino.
     *
     * @param transaccion Representa el objeto Transaccion a validar.
     * @return Regresa true en caso de que sea válida, false en caso contrario.
     * @throws PersistenciaException Se lanza en caso de un error en la capa de
     * persistencia.
     */
    private boolean validarCuentaDestino(Transaccion transaccion) throws PersistenciaException {
        if (this.txtNumeroCuenta.getText().trim().equals("")) {
            throw new PersistenciaException("Ingresa un número de cuenta");
        }
        if (cuentasDAO.consultar(transaccion.getIdCuentaDestino()) == null) {
            throw new PersistenciaException("No existe la cuenta destino");
        } else {
            return true;
        }
    }

    /**
     * Este método realiza el proceso de transferencia, utilizando los métodos
     * extraerDatos, validaciones y transferir.
     */
    private void realizarTransferencia() {
        Transaccion transaccion = extraerDatos();
        try {
            validaciones(transaccion);
            if (transaccionesDAO.transferir(transaccion.getIdCuentaOrigen(), transaccion.getIdCuentaDestino(), transaccion.getMonto())) {
                this.mostrarMensaje("Transacción exitosa de la cuenta " + transaccion.getIdCuentaOrigen() + " a la cuenta " + transaccion.getIdCuentaDestino());
                regresarMenu();
            }
        } catch (PersistenciaException ex) {
            this.mostrarMensaje(ex.getMessage());
            Logger.getLogger(FrmTransferencia.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Este método realiza la extracción de los datos de la interfaz de usuario.
     *
     * @return Regresa el objeto Transaccion extraido.
     */
    private Transaccion extraerDatos() {
        Integer numeroCuentaOrigen = ((Cuenta) cbxCuentas.getSelectedItem()).getNumCuenta();
        Integer numeroCuentaDestino = Integer.valueOf(txtNumeroCuenta.getText());
        Float monto = Float.valueOf(txtSaldo.getText());
        Transaccion transferencia = new Transaccion(monto, numeroCuentaDestino, numeroCuentaOrigen);
        return transferencia;
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
        lblTransferencias = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        jpnFondo = new javax.swing.JPanel();
        lblNombreDestinatario = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        lblNumeroCuenta = new javax.swing.JLabel();
        txtNumeroCuenta = new javax.swing.JTextField();
        cbxCuentas = new javax.swing.JComboBox<>();
        lblNumeroCuenta1 = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnFondoGeneral.setBackground(new java.awt.Color(12, 28, 68));
        jpnFondoGeneral.setForeground(new java.awt.Color(12, 28, 68));
        jpnFondoGeneral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTransferencias.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 50)); // NOI18N
        lblTransferencias.setForeground(new java.awt.Color(255, 255, 255));
        lblTransferencias.setText("Transferencias");
        jpnFondoGeneral.add(lblTransferencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        btnConfirmar.setBackground(new java.awt.Color(65, 103, 158));
        btnConfirmar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        jpnFondoGeneral.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 260, 110, 30));

        jpnFondo.setBackground(new java.awt.Color(183, 210, 242));

        lblNombreDestinatario.setText("Saldo");
        lblNombreDestinatario.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N

        txtSaldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSaldoKeyTyped(evt);
            }
        });

        lblNumeroCuenta.setText("Selecciona una de tus cuentas:");
        lblNumeroCuenta.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N

        txtNumeroCuenta.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroCuentaKeyTyped(evt);
            }
        });

        lblNumeroCuenta1.setText("Cuenta destino");
        lblNumeroCuenta1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N

        javax.swing.GroupLayout jpnFondoLayout = new javax.swing.GroupLayout(jpnFondo);
        jpnFondo.setLayout(jpnFondoLayout);
        jpnFondoLayout.setHorizontalGroup(
            jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnFondoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(cbxCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblNumeroCuenta))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jpnFondoLayout.createSequentialGroup()
                        .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jpnFondoLayout.createSequentialGroup()
                                .addComponent(lblNumeroCuenta1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnFondoLayout.createSequentialGroup()
                                .addComponent(lblNombreDestinatario)
                                .addGap(74, 74, 74)))
                        .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNumeroCuenta)
                            .addComponent(txtSaldo))))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jpnFondoLayout.setVerticalGroup(
            jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnFondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblNumeroCuenta)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNumeroCuenta1)
                    .addComponent(txtNumeroCuenta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombreDestinatario)
                    .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        jpnFondoGeneral.add(jpnFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 390, 160));

        btnRegresar.setBackground(new java.awt.Color(183, 210, 242));
        btnRegresar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jpnFondoGeneral.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 270, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondoGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondoGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se llama
     * al método realizarTransferencia.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        realizarTransferencia();
    }//GEN-LAST:event_btnConfirmarActionPerformed
    /**
     * Este método realiza la validación que solo pueda introducir números y con
     * una longitud de 10.
     *
     * @param evt Al presionar una tecla en el campo de texto
     */
    private void txtNumeroCuentaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroCuentaKeyTyped
        int tecla = evt.getKeyChar();
        boolean numeros = tecla >= 48 && tecla <= 57;
        if (!numeros) {
            evt.consume();
        }
        if (txtNumeroCuenta.getText().trim().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroCuentaKeyTyped
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se regresa
     * al menú.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        regresarMenu();
    }//GEN-LAST:event_btnRegresarActionPerformed
    /**
     * Este método realiza la validación que solo pueda introducir números
     * positivos decimales con máximo 3 decimales.
     *
     * @param evt Al presionar una tecla en el campo de texto.
     */
    private void txtSaldoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSaldoKeyTyped
        char c = evt.getKeyChar();
        String input = txtSaldo.getText();
        int dotIndex = input.indexOf('.');
        if (dotIndex != -1 && input.substring(dotIndex).length() > 3) {
            evt.consume();
        } else if (c == '.' && dotIndex == -1) {
        } else if (c < '0' || c > '9') {
            evt.consume();
        }
    }//GEN-LAST:event_txtSaldoKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JComboBox<Cuenta> cbxCuentas;
    private javax.swing.JPanel jpnFondo;
    private javax.swing.JPanel jpnFondoGeneral;
    private javax.swing.JLabel lblNombreDestinatario;
    private javax.swing.JLabel lblNumeroCuenta;
    private javax.swing.JLabel lblNumeroCuenta1;
    private javax.swing.JLabel lblTransferencias;
    private javax.swing.JTextField txtNumeroCuenta;
    private javax.swing.JTextField txtSaldo;
    // End of variables declaration//GEN-END:variables
}

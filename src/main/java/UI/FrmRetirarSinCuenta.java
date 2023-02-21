/*
 * FrmRetirarSinCuenta.java creada el 20/02/2023.
 */
package UI;

import dominio.Retiro;
import dominio.Usuario;
import excepciones.PersistenciaException;
import implementaciones.ClientesDAO;
import interfaces.IClientesDAO;
import interfaces.ICuentasDAO;
import interfaces.IDireccionesDAO;
import interfaces.ITransaccionesDAO;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Este frame se utiliza para realizar el cobro de retiros sin cuenta.
 *
 * @author 00000233259 y 00000233410.
 */
public class FrmRetirarSinCuenta extends javax.swing.JFrame {

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
     * Método constructor que inicializa los atributos al valor de sus
     * parámetros.
     *
     * @param clientesDAO representa la DAO de Clientes.
     * @param direccionesDAO representa la DAO de Direcciones.
     * @param transaccionesDAO representa la DAO de Transacciones.
     * @param cuentasDAO representa la DAO de Cuentas.
     */
    public FrmRetirarSinCuenta(IClientesDAO clientesDAO, IDireccionesDAO direccionesDAO, ITransaccionesDAO transaccionesDAO, ICuentasDAO cuentasDAO) {
        this.clientesDAO = clientesDAO;
        this.direccionesDAO = direccionesDAO;
        this.transaccionesDAO = transaccionesDAO;
        this.cuentasDAO = cuentasDAO;
        initComponents();
        this.setSize(440, 320);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
    }

    /**
     * Este método regresa a la ventana de inicio de sesión.
     */
    private void regresarInicio() {
        FrmLogin frmLogin = new FrmLogin(clientesDAO, direccionesDAO, transaccionesDAO, cuentasDAO);
        this.setVisible(false);
        frmLogin.setVisible(true);
    }

    /**
     * Este método extrae los datos de la interfaz de usuario.
     *
     * @return Regresa un objeto Retiro con los datos extraidos.
     * @throws PersistenciaException Se lanza en caso de un error en la capa de
     * persistencia.
     */
    private Retiro extraerDatos() throws PersistenciaException {
        String folio = this.txtFolio.getText();
        String password = this.txtPassword.getText();
        Float monto = Float.valueOf(this.txtSaldo.getText());
        Retiro retiroActual = transaccionesDAO.consultarRetiro(folio);
        Retiro retiro = new Retiro(retiroActual.getIdCuenta(), folio, password, monto);
        return retiro;
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

        jpnFondoGeneral = new javax.swing.JPanel();
        lblTransferencias = new javax.swing.JLabel();
        btnConfirmar = new javax.swing.JButton();
        jpnFondo = new javax.swing.JPanel();
        lblSaldo = new javax.swing.JLabel();
        txtSaldo = new javax.swing.JTextField();
        txtFolio = new javax.swing.JTextField();
        lblFolio = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblPassword = new javax.swing.JLabel();
        btnRegresar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnFondoGeneral.setBackground(new java.awt.Color(12, 28, 68));
        jpnFondoGeneral.setForeground(new java.awt.Color(12, 28, 68));
        jpnFondoGeneral.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblTransferencias.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 50)); // NOI18N
        lblTransferencias.setForeground(new java.awt.Color(255, 255, 255));
        lblTransferencias.setText("Realizar retiro");
        jpnFondoGeneral.add(lblTransferencias, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 10, -1, -1));

        btnConfirmar.setBackground(new java.awt.Color(65, 103, 158));
        btnConfirmar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnConfirmar.setForeground(new java.awt.Color(255, 255, 255));
        btnConfirmar.setText("Confirmar");
        btnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarActionPerformed(evt);
            }
        });
        jpnFondoGeneral.add(btnConfirmar, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 250, 110, 30));

        jpnFondo.setBackground(new java.awt.Color(183, 210, 242));

        lblSaldo.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblSaldo.setText("Saldo");

        txtSaldo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSaldoKeyTyped(evt);
            }
        });

        txtFolio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFolioKeyTyped(evt);
            }
        });

        lblFolio.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblFolio.setText("Folio");

        txtPassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPasswordKeyTyped(evt);
            }
        });

        lblPassword.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblPassword.setText("Password");

        javax.swing.GroupLayout jpnFondoLayout = new javax.swing.GroupLayout(jpnFondo);
        jpnFondo.setLayout(jpnFondoLayout);
        jpnFondoLayout.setHorizontalGroup(
            jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jpnFondoLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFolio)
                    .addGroup(jpnFondoLayout.createSequentialGroup()
                        .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSaldo)
                            .addComponent(lblPassword))
                        .addGap(74, 74, 74)
                        .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
                            .addComponent(txtFolio)
                            .addComponent(txtSaldo))))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        jpnFondoLayout.setVerticalGroup(
            jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpnFondoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFolio)
                    .addComponent(txtFolio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword))
                .addGap(16, 16, 16)
                .addGroup(jpnFondoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSaldo)
                    .addComponent(txtSaldo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        jpnFondoGeneral.add(jpnFondo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, 390, 140));

        btnRegresar.setBackground(new java.awt.Color(183, 210, 242));
        btnRegresar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRegresar.setText("Regresar");
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jpnFondoGeneral.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 260, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnFondoGeneral, javax.swing.GroupLayout.PREFERRED_SIZE, 461, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondoGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se llama a
     * los métodos de extraer datos, realizar retiro y regresar inicio.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarActionPerformed
        try {
            Retiro retiro = extraerDatos();
            if (transaccionesDAO.retirar(retiro) != null) {
                this.mostrarMensaje("Se ha realizado el retiro correctamente");
                regresarInicio();
            }
        } catch (PersistenciaException ex) {
            this.mostrarMensaje(ex.getMessage());
            regresarInicio();
            Logger.getLogger(FrmRetirarSinCuenta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnConfirmarActionPerformed
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
    /**
     * Este método realiza la validación que solo pueda introducir números y con
     * una longitud de 10.
     *
     * @param evt Al presionar una tecla en el campo de texto.
     */
    private void txtFolioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFolioKeyTyped
        int tecla = evt.getKeyChar();
        boolean numeros = tecla >= 48 && tecla <= 57;
        if (!numeros) {
            evt.consume();
        }
        if (txtFolio.getText().trim().length() == 10) {
            evt.consume();
        }
    }//GEN-LAST:event_txtFolioKeyTyped
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se llama
     * al método regresarInicio.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnRegresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresarActionPerformed
        regresarInicio();
    }//GEN-LAST:event_btnRegresarActionPerformed
    /**
     * Este método realiza la validación que solo pueda introducir números y con
     * una longitud de 8.
     *
     * @param evt Al presionar una tecla en el campo de texto.
     */
    private void txtPasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPasswordKeyTyped
        int tecla = evt.getKeyChar();
        boolean numeros = tecla >= 48 && tecla <= 57;
        if (!numeros) {
            evt.consume();
        }
        if (txtFolio.getText().trim().length() == 8) {
            evt.consume();
        }
    }//GEN-LAST:event_txtPasswordKeyTyped


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JPanel jpnFondo;
    private javax.swing.JPanel jpnFondoGeneral;
    private javax.swing.JLabel lblFolio;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblSaldo;
    private javax.swing.JLabel lblTransferencias;
    private javax.swing.JTextField txtFolio;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtSaldo;
    // End of variables declaration//GEN-END:variables
}

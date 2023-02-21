/*
 * FrmModificarDatos.java creada el 20/02/2023.
 */
package UI;

import dominio.Cliente;
import dominio.Direccion;
import implementaciones.ClientesDAO;
import interfaces.IClientesDAO;
import interfaces.IDireccionesDAO;
import java.util.logging.Logger;
import dominio.Usuario;
import excepciones.PersistenciaException;
import interfaces.ICuentasDAO;
import interfaces.ITransaccionesDAO;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import validadores.Validador;

/**
 * Este frame se utiliza para la modificación de los datos.
 *
 * @author 00000233259 y 00000233410.
 */
public class FrmModificarDatos extends javax.swing.JFrame {

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
     * Este atributo representa el validador de campos.
     */
    private Validador validador;

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
    public FrmModificarDatos(IClientesDAO clientesDAO, IDireccionesDAO direccionesDAO, ITransaccionesDAO transaccionesDAO, ICuentasDAO cuentasDAO, Usuario usuarioLogeado) {
        this.clientesDAO = clientesDAO;
        this.direccionesDAO = direccionesDAO;
        this.transaccionesDAO = transaccionesDAO;
        this.cuentasDAO = cuentasDAO;
        this.usuarioLogeado = usuarioLogeado;
        this.validador = new Validador();
        initComponents();
        this.setSize(700, 490);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.llenarCampos();
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
     * Este método llena los campos de texto con la información del cliente
     * logeado actualmente para poder realizar su modificación.
     */
    private void llenarCampos() {
        Cliente clienteLogeado = usuarioLogeado.getCliente();
        this.txtNombre.setText(clienteLogeado.getNombre());
        this.txtApellidoMaterno.setText(clienteLogeado.getApellidoMaterno());
        this.txtApellidoPaterno.setText(clienteLogeado.getApellidoPaterno());
        this.txtPasswordVieja.setText(clienteLogeado.getPassword());
        try {
            Direccion direccionClienteLogeado = direccionesDAO.consultar(clienteLogeado.getDireccion().getId());
            this.txtCalle.setText(direccionClienteLogeado.getCalle());
            this.txtColonia.setText(direccionClienteLogeado.getColonia());
            this.txtNumero.setText(direccionClienteLogeado.getNumeroCasa());
        } catch (PersistenciaException ex) {
            this.mostrarMensaje(ex.getMessage());
            Logger.getLogger(FrmModificarDatos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Este método extrae los datos del cliente de la interfaz de usuario.
     *
     * @return Regresa el cliente extraido.
     */
    private Cliente extraerDatosFormularioCliente() {
        //sacar datos del form
        Date fechaNacimiento = null;
        String nombre = this.txtNombre.getText();
        String apellidoPaterno = this.txtApellidoPaterno.getText();
        String apellidoMaterno = this.txtApellidoMaterno.getText();
        String password = this.txtPasswordNueva.getText();
        if (password.trim().equals("")) {
            password = usuarioLogeado.getCliente().getPassword();
        }
        LocalDate fecha = cpnFechaNacimiento.getSelectedDate();
        if (fecha != null) {
            fechaNacimiento = new Date(fecha.getYear() - 1900, fecha.getMonthValue() - 1, fecha.getDayOfMonth());
        } else {
            fechaNacimiento = usuarioLogeado.getCliente().getFechaNacimiento();
        }
        Cliente cliente = new Cliente(null, nombre, apellidoPaterno, apellidoMaterno, password, fechaNacimiento);
        return cliente;
    }

    /**
     * Este método extrae los datos de la dirección de la interfaz de usuario.
     *
     * @return Regresa la dirección extraida.
     */
    private Direccion extraerDatosFormularioDireccion() {
        //sacar datos del form
        String calle = this.txtCalle.getText();
        String colonia = this.txtColonia.getText();
        String numero = this.txtNumero.getText();
        Direccion direccion = new Direccion(numero, calle, colonia);
        return direccion;
    }

    /**
     * Este método realiza la modificación de los datos del cliente, utilizando
     * los métodos de extracción, validación y DAO.
     */
    public void actualizar() {
        Direccion direccionPorActualizar;
        Direccion direccionExtraida;
        Cliente clienteExtraido;
        try {
            //Se extraen los datos
            direccionExtraida = extraerDatosFormularioDireccion();
            clienteExtraido = extraerDatosFormularioCliente();
            clienteExtraido.setId(usuarioLogeado.getCliente().getId());

            //Se validan los datos
            validador.validaFecha(clienteExtraido.getFechaNacimiento());
            validador.validarCliente(clienteExtraido);
            validador.validarDireccion(direccionExtraida);
            //Se insertan los datos
            direccionPorActualizar = direccionesDAO.insertar(direccionExtraida);
            clienteExtraido.setDireccion(direccionPorActualizar);
            if (!confirmarModificacion()) {
                return;
            }
            clientesDAO.actualizar(clienteExtraido);
            usuarioLogeado.setCliente(clienteExtraido);
            //Se muestran mensajes

            this.mostrarMensaje("Se han actualizado los datos");
            regresarMenu();
        } catch (PersistenciaException ex) {
            this.mostrarMensaje(ex.getMessage());
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
     * Este método muestra un JOptionPane para poder realizar una confirmación
     * de la modificación.
     *
     * @return Regresa true en caso de que confirme la modificación, false en
     * caso contrario.
     */
    public boolean confirmarModificacion() {
        String password = usuarioLogeado.getCliente().getPassword();
        String confirmacion;
        do {
            confirmacion = JOptionPane.showInputDialog(null, "Por favor confirme su contraseña:");
            if (confirmacion != null && confirmacion.equals(password)) {
                mostrarMensaje("Modificación confirmada");
                return true;
            } else if (confirmacion == null) {
                mostrarMensaje("No se ha realizado ninguna modificación");
            } else {
                mostrarMensaje("Contraseña incorrecta, intente de nuevo");
            }
        } while (confirmacion != null);
        return false;
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
        lblModificarDatos = new javax.swing.JLabel();
        lblCambiarContrasena = new javax.swing.JLabel();
        txtApellidoMaterno = new javax.swing.JTextField();
        txtColonia = new javax.swing.JTextField();
        lblApellidoMaterno_ = new javax.swing.JLabel();
        lblColonia = new javax.swing.JLabel();
        lblApellidoPaterno_ = new javax.swing.JLabel();
        txtApellidoPaterno = new javax.swing.JTextField();
        lblApellidoPaterno = new javax.swing.JLabel();
        lblApellidoMaterno = new javax.swing.JLabel();
        lblInformacionPersonal1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblPasswordNueva = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        lblNumero = new javax.swing.JLabel();
        lblNombre1 = new javax.swing.JLabel();
        cpnFechaNacimiento = new com.github.lgooddatepicker.components.CalendarPanel();
        lblDomicilio1 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        btnRegresar1 = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        txtPasswordNueva = new javax.swing.JPasswordField();
        txtPasswordVieja = new javax.swing.JPasswordField();
        lblCalle1 = new javax.swing.JLabel();
        lblPasswordVieja = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnFondo.setBackground(new java.awt.Color(12, 28, 68));
        jpnFondo.setForeground(new java.awt.Color(12, 28, 68));
        jpnFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblModificarDatos.setText("Modificar datos");
        lblModificarDatos.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 50)); // NOI18N
        lblModificarDatos.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblModificarDatos, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblCambiarContrasena.setText("Cambiar Contraseña");
        lblCambiarContrasena.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        lblCambiarContrasena.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblCambiarContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 240, -1, -1));
        jpnFondo.add(txtApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 270, 40));

        txtColonia.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtColoniaKeyTyped(evt);
            }
        });
        jpnFondo.add(txtColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 360, 270, 30));

        lblApellidoMaterno_.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblApellidoMaterno_.setForeground(new java.awt.Color(255, 255, 255));
        lblApellidoMaterno_.setText("Materno");
        jpnFondo.add(lblApellidoMaterno_, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, 60, -1));

        lblColonia.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblColonia.setForeground(new java.awt.Color(255, 255, 255));
        lblColonia.setText("Colonia");
        jpnFondo.add(lblColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, -1, -1));

        lblApellidoPaterno_.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblApellidoPaterno_.setForeground(new java.awt.Color(255, 255, 255));
        lblApellidoPaterno_.setText("Paterno");
        jpnFondo.add(lblApellidoPaterno_, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, 60, -1));
        jpnFondo.add(txtApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, 270, 40));

        lblApellidoPaterno.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblApellidoPaterno.setForeground(new java.awt.Color(255, 255, 255));
        lblApellidoPaterno.setText("Apellido");
        jpnFondo.add(lblApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 60, -1));

        lblApellidoMaterno.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblApellidoMaterno.setForeground(new java.awt.Color(255, 255, 255));
        lblApellidoMaterno.setText("Apellido");
        jpnFondo.add(lblApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 60, -1));

        lblInformacionPersonal1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        lblInformacionPersonal1.setForeground(new java.awt.Color(255, 255, 255));
        lblInformacionPersonal1.setText("Información personal");
        jpnFondo.add(lblInformacionPersonal1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, -1, -1));

        lblNombre.setText("Fecha de nacimiento");
        lblNombre.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 10, -1, -1));

        txtNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNombreKeyTyped(evt);
            }
        });
        jpnFondo.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 270, 30));

        lblPasswordNueva.setText("Contraseña nueva");
        lblPasswordNueva.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblPasswordNueva.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblPasswordNueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 330, -1, -1));

        txtCalle.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCalleKeyTyped(evt);
            }
        });
        jpnFondo.add(txtCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, 270, 30));

        lblNumero.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblNumero.setForeground(new java.awt.Color(255, 255, 255));
        lblNumero.setText("Número");
        jpnFondo.add(lblNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 330, -1, -1));

        lblNombre1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblNombre1.setForeground(new java.awt.Color(255, 255, 255));
        lblNombre1.setText("Nombre");
        jpnFondo.add(lblNombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, -1, -1));
        jpnFondo.add(cpnFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 40, 240, 190));

        lblDomicilio1.setText("Domicilio");
        lblDomicilio1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        lblDomicilio1.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblDomicilio1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 250, -1, -1));

        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });
        jpnFondo.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 320, 270, 30));

        btnRegresar1.setBackground(new java.awt.Color(183, 210, 242));
        btnRegresar1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRegresar1.setText("Regresar");
        btnRegresar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresar1ActionPerformed(evt);
            }
        });
        jpnFondo.add(btnRegresar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, -1, -1));

        btnActualizar.setText("Guardar cambios");
        btnActualizar.setBackground(new java.awt.Color(65, 103, 158));
        btnActualizar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(255, 255, 255));
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jpnFondo.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 400, 170, 30));
        jpnFondo.add(txtPasswordNueva, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 360, 230, 30));
        jpnFondo.add(txtPasswordVieja, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 290, 230, 30));

        lblCalle1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblCalle1.setForeground(new java.awt.Color(255, 255, 255));
        lblCalle1.setText("Calle");
        jpnFondo.add(lblCalle1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, -1, -1));

        lblPasswordVieja.setText("Contraseña antigua");
        lblPasswordVieja.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblPasswordVieja.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblPasswordVieja, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 270, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondo, javax.swing.GroupLayout.DEFAULT_SIZE, 702, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtColoniaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtColoniaKeyTyped

    }//GEN-LAST:event_txtColoniaKeyTyped

    private void txtNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreKeyTyped

    private void txtCalleKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCalleKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCalleKeyTyped

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNumeroKeyTyped
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se regresa
     * al menú.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnRegresar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegresar1ActionPerformed
        regresarMenu();
    }//GEN-LAST:event_btnRegresar1ActionPerformed
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se llama
     * al método que modifica los datos del cliente.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        actualizar();
    }//GEN-LAST:event_btnActualizarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnRegresar1;
    private com.github.lgooddatepicker.components.CalendarPanel cpnFechaNacimiento;
    private javax.swing.JPanel jpnFondo;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoMaterno_;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblApellidoPaterno_;
    private javax.swing.JLabel lblCalle1;
    private javax.swing.JLabel lblCambiarContrasena;
    private javax.swing.JLabel lblColonia;
    private javax.swing.JLabel lblDomicilio1;
    private javax.swing.JLabel lblInformacionPersonal1;
    private javax.swing.JLabel lblModificarDatos;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNombre1;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblPasswordNueva;
    private javax.swing.JLabel lblPasswordVieja;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JPasswordField txtPasswordNueva;
    private javax.swing.JPasswordField txtPasswordVieja;
    // End of variables declaration//GEN-END:variables
}

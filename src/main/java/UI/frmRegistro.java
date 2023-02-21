/*
 * FrmRegistro.java creada el 20/02/2023.
 */
package UI;

import dominio.Cliente;
import dominio.Direccion;
import dominio.Usuario;
import excepciones.PersistenciaException;
import implementaciones.ClientesDAO;
import interfaces.IClientesDAO;
import interfaces.ICuentasDAO;
import interfaces.IDireccionesDAO;
import interfaces.ITransaccionesDAO;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import validadores.Validador;

/**
 * Este frame se utiliza para registrar un cliente.
 *
 * @author 00000233259 y 00000233410.
 */
public class frmRegistro extends javax.swing.JFrame {

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
     * Este atributo representa el validador de campos.
     */
    private final Validador validador = new Validador();

    /**
     * Método constructor que inicializa los atributos al valor de sus
     * parámetros.
     *
     * @param clientesDAO representa la DAO de Clientes.
     * @param direccionesDAO representa la DAO de Direcciones.
     * @param transaccionesDAO representa la DAO de Transacciones.
     * @param cuentasDAO representa la DAO de Cuentas.
     */
    public frmRegistro(IClientesDAO clientesDAO, IDireccionesDAO direccionesDAO, ITransaccionesDAO transaccionesDAO, ICuentasDAO cuentasDAO) {
        this.clientesDAO = clientesDAO;
        this.direccionesDAO = direccionesDAO;
        this.transaccionesDAO = transaccionesDAO;
        this.cuentasDAO = cuentasDAO;
        initComponents();
        this.setSize(650, 450);
        setTitle("Registrarse");
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        LocalDate fechaLimiteInferior = LocalDate.of(1900, 1, 1);
        LocalDate fechaLimiteSuperior = LocalDate.now();
        cpnFechaNacimiento.getSettings().setDateRangeLimits(fechaLimiteInferior, fechaLimiteSuperior);
    }

    /**
     * Este método realiza el registro del cliente, para ello se extraen los
     * datos, validan datos y se utilizan las DAO.
     *
     * @throws PersistenciaException Se lanza en caso de ocurrir un error en la
     * capa de persistencia.
     */
    public void guardar() throws PersistenciaException {
        Direccion direccionGuardada;
        Direccion direccionExtraida;
        Cliente clienteDireccion;
        Cliente clienteGuardado;
        try {
            //Se extraen los datos
            direccionExtraida = extraerDatosFormularioDireccion();
            clienteDireccion = extraerDatosFormularioCliente();
            //Se validan los datos
            validador.validaFecha(clienteDireccion.getFechaNacimiento());
            validador.validarCliente(clienteDireccion);
            validador.validarDireccion(direccionExtraida);
            //Se insertan los datos
            direccionGuardada = direccionesDAO.insertar(direccionExtraida);
            clienteDireccion.setDireccion(direccionGuardada);
            clienteGuardado = clientesDAO.insertar(clienteDireccion);
            //Se muestran mensajes
            this.mostrarMensajeClienteRegistrado(clienteGuardado);
            regresarInicio();
        } catch (PersistenciaException ex) {
            this.mostrarMensajeClienteError(ex.getMessage());
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
        String password = this.txtPassword.getText();
        LocalDate fecha = cpnFechaNacimiento.getSelectedDate();
        if (fecha != null) {
            fechaNacimiento = new Date(fecha.getYear() - 1900, fecha.getMonthValue() - 1, fecha.getDayOfMonth());
        } else {
            fechaNacimiento = null;
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
     * Este método muestra un mensaje en un JOptionPane utilizando un cliente.
     *
     * @param cliente Representa el cliente a utilizar.
     */
    private void mostrarMensajeClienteRegistrado(Cliente cliente) {
        JOptionPane.showMessageDialog(null, "Se ha registrado correctamente con el id " + cliente.getId(), "Info", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Este método muestra un mensaje en un JOptionPane sobre un error en la
     * operación de un cliente.
     *
     * @param msj El mensaje a mostrar.
     */
    private void mostrarMensajeClienteError(String mensaje) {
        JOptionPane.showMessageDialog(null, mensaje, "Info", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Este método muestra un mensaje en un JOptionPane para indicar que la
     * dirección fue guardada.
     */
    private void mostrarMensajeDireccionGuardada() {
        JOptionPane.showMessageDialog(null, "Se ha guardado la dirección", "Info", JOptionPane.PLAIN_MESSAGE);
    }

    /**
     * Este método muestra un mensaje en un JOptionPane para indicar el error en
     * la operación de una dirección.
     */
    private void mostrarMensajeDireccionError() {
        JOptionPane.showMessageDialog(null, "No se ha guardado la dirección", "Info", JOptionPane.ERROR_MESSAGE);
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
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnFondo = new javax.swing.JPanel();
        lblBienvenido = new javax.swing.JLabel();
        lblCalle = new javax.swing.JLabel();
        txtColonia = new javax.swing.JTextField();
        btnRegistrarse = new javax.swing.JButton();
        lblInformacionPersonal = new javax.swing.JLabel();
        lblApellidoPaterno = new javax.swing.JLabel();
        txtApellidoPaterno = new javax.swing.JTextField();
        lblApellidoMaterno = new javax.swing.JLabel();
        txtApellidoMaterno = new javax.swing.JTextField();
        lblContrasena = new javax.swing.JLabel();
        txtCalle = new javax.swing.JTextField();
        lblCalle2 = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblCalle3 = new javax.swing.JLabel();
        lblCalle4 = new javax.swing.JLabel();
        cpnFechaNacimiento = new com.github.lgooddatepicker.components.CalendarPanel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        btnDesconectar = new javax.swing.JButton();
        txtPassword = new javax.swing.JPasswordField();
        lblContrasenia2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnFondo.setBackground(new java.awt.Color(12, 28, 68));
        jpnFondo.setForeground(new java.awt.Color(12, 28, 68));
        jpnFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblBienvenido.setText("Registrarse");
        lblBienvenido.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 50)); // NOI18N
        lblBienvenido.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblBienvenido, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 0, -1, -1));

        lblCalle.setText("Colonia");
        lblCalle.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblCalle.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, -1, -1));
        jpnFondo.add(txtColonia, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 350, 210, 30));

        btnRegistrarse.setText("Registrarse");
        btnRegistrarse.setBackground(new java.awt.Color(65, 103, 158));
        btnRegistrarse.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        btnRegistrarse.setForeground(new java.awt.Color(255, 255, 255));
        btnRegistrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarseActionPerformed(evt);
            }
        });
        jpnFondo.add(btnRegistrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 350, 130, 40));

        lblInformacionPersonal.setText("Información personal");
        lblInformacionPersonal.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        lblInformacionPersonal.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblInformacionPersonal, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, -1, -1));

        lblApellidoPaterno.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblApellidoPaterno.setForeground(new java.awt.Color(255, 255, 255));
        lblApellidoPaterno.setText("Apellido Paterno");
        jpnFondo.add(lblApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 130, -1, -1));
        jpnFondo.add(txtApellidoPaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, 210, 30));

        lblApellidoMaterno.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblApellidoMaterno.setForeground(new java.awt.Color(255, 255, 255));
        lblApellidoMaterno.setText("Apellido Materno");
        jpnFondo.add(lblApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));
        jpnFondo.add(txtApellidoMaterno, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 210, 30));

        lblContrasena.setText("Contraseña");
        lblContrasena.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblContrasena.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblContrasena, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 270, -1, -1));
        jpnFondo.add(txtCalle, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 210, 30));

        lblCalle2.setText("Número");
        lblCalle2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblCalle2.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblCalle2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 320, -1, -1));
        jpnFondo.add(txtNumero, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 310, 210, 30));

        lblCalle3.setText("Calle");
        lblCalle3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblCalle3.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblCalle3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, -1, -1));

        lblCalle4.setText("Selecciona tu fecha de nacimiento");
        lblCalle4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblCalle4.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblCalle4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 30, -1, -1));
        jpnFondo.add(cpnFechaNacimiento, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 60, -1, 200));

        lblNombre.setText("Nombres");
        lblNombre.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 14)); // NOI18N
        lblNombre.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 80, -1, -1));
        jpnFondo.add(txtNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, 210, 30));

        btnDesconectar.setText("Volver");
        btnDesconectar.setBackground(new java.awt.Color(183, 210, 242));
        btnDesconectar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDesconectar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesconectarActionPerformed(evt);
            }
        });
        jpnFondo.add(btnDesconectar, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, -1, -1));
        jpnFondo.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 300, 220, 30));

        lblContrasenia2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 18)); // NOI18N
        lblContrasenia2.setForeground(new java.awt.Color(255, 255, 255));
        lblContrasenia2.setText("Domicilio");
        jpnFondo.add(lblContrasenia2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 240, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 680, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jpnFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y llama al
     * método regresarInicio.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnDesconectarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesconectarActionPerformed
        regresarInicio();
    }//GEN-LAST:event_btnDesconectarActionPerformed
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y se llama
     * al método que registra al cliente.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnRegistrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarseActionPerformed
        try {
            guardar();
        } catch (PersistenciaException ex) {
            Logger.getLogger(frmRegistro.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnRegistrarseActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDesconectar;
    private javax.swing.JButton btnRegistrarse;
    private com.github.lgooddatepicker.components.CalendarPanel cpnFechaNacimiento;
    private javax.swing.JPanel jpnFondo;
    private javax.swing.JLabel lblApellidoMaterno;
    private javax.swing.JLabel lblApellidoPaterno;
    private javax.swing.JLabel lblBienvenido;
    private javax.swing.JLabel lblCalle;
    private javax.swing.JLabel lblCalle2;
    private javax.swing.JLabel lblCalle3;
    private javax.swing.JLabel lblCalle4;
    private javax.swing.JLabel lblContrasena;
    private javax.swing.JLabel lblContrasenia2;
    private javax.swing.JLabel lblInformacionPersonal;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTextField txtApellidoMaterno;
    private javax.swing.JTextField txtApellidoPaterno;
    private javax.swing.JTextField txtCalle;
    private javax.swing.JTextField txtColonia;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JPasswordField txtPassword;
    // End of variables declaration//GEN-END:variables
}

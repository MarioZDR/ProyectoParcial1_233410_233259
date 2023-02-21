/*
 * FrmVerRetiros.java creada el 20/02/2023.
 */
package UI;

import auxiliares.ConfiguracionPaginado;
import auxiliares.CuentaComboBoxModel;
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
import java.awt.event.ItemEvent;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * Este frame se utiliza ver los retiros realizados por el cliente.
 *
 * @author 00000233259 y 00000233410.
 */
public class FrmVerRetiros extends javax.swing.JFrame {

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
     * Este atributo representa la configuración del paginado.
     */
    private ConfiguracionPaginado paginado;
    /**
     * Este atributo representa el número de página actual.
     */
    private int numeroPagina = 0;
    /**
     * Este atributo representa los elementos por página.
     */
    private int elementosPorPagina = 3;

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
    public FrmVerRetiros(IClientesDAO clientesDAO, IDireccionesDAO direccionesDAO, ITransaccionesDAO transaccionesDAO, ICuentasDAO cuentasDAO, Usuario usuarioLogeado) {
        this.clientesDAO = clientesDAO;
        this.direccionesDAO = direccionesDAO;
        this.transaccionesDAO = transaccionesDAO;
        this.cuentasDAO = cuentasDAO;
        this.usuarioLogeado = usuarioLogeado;
        this.paginado = new ConfiguracionPaginado(this.numeroPagina, this.elementosPorPagina);
        initComponents();
        this.setSize(580, 452);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        insertarCuentas();
        LocalDate fechaLimiteInferior = LocalDate.of(1900, 1, 1);
        LocalDate fechaLimiteSuperior = LocalDate.now();
        dpcFechaFin.getSettings().setDateRangeLimits(fechaLimiteInferior, fechaLimiteSuperior);
        dpcFechaInicio.getSettings().setDateRangeLimits(fechaLimiteInferior, fechaLimiteSuperior);
    }

    /**
     * Este método carga en la tabla los retiros del cliente.
     */
    private void cargarTablaRetiros() {
        Cuenta cuentaSeleccionada = extraerCuenta();
        Date fechaInicio = extraerFechaInicio();
        Date fechaFin = extraerFechaFin();
        try {
            if (validarFechas()) {
                List<Retiro> listaRetiros = this.transaccionesDAO.consultarListaRetiro(cuentaSeleccionada.getNumCuenta(), fechaInicio, fechaFin, paginado);
                DefaultTableModel modeloTablaRetiros = (DefaultTableModel) this.tblOperaciones.getModel();
                modeloTablaRetiros.setRowCount(0);
                for (Retiro t : listaRetiros) {
                    Object[] filaNueva = {t.getMonto(), t.getFecha(), t.getEstado(), t.getIdCuenta(), t.getFolio(), t.getPassword()};
                    modeloTablaRetiros.addRow(filaNueva);
                }
            }
        } catch (PersistenciaException ex) {
            LOG.log(Level.SEVERE, ex.getMessage());
        }
    }

    /**
     * Este método realiza la validación de las fechas.
     *
     * @return Regresa true en caso de que sean validas, false en caso
     * contrario.
     */
    private boolean validarFechas() {
        return !(extraerFechaFin() == null || extraerFechaFin() == null);
    }

    /**
     * Este método extrae la cuenta seleccionada por el usuario en la ComboBox.
     *
     * @return Regresa la cuenta seleccionada por el Cliente.
     */
    private Cuenta extraerCuenta() {
        Cuenta cuenta = (Cuenta) cbxCuentas.getSelectedItem();
        return cuenta;
    }

    /**
     * Este método extrae la fecha de inicio de la interfaz de usuario.
     *
     * @return Regresa la fecha de inicio extraida.
     */
    private Date extraerFechaInicio() {
        Date fechaInicio = null;
        LocalDate fechaInicioExtraida = dpcFechaInicio.getDate();
        if (fechaInicioExtraida != null) {
            fechaInicio = new Date(fechaInicioExtraida.getYear() - 1900, fechaInicioExtraida.getMonthValue() - 1, fechaInicioExtraida.getDayOfMonth());
        }
        return fechaInicio;
    }

    /**
     * Este método extrae la fecha de fin de la interfaz de usuario.
     *
     * @return Regresa la fecha de fin extraida.
     */
    private Date extraerFechaFin() {
        Date fechaFin = null;
        LocalDate fechaFinExtraida = dpcFechaFin.getDate();
        if (fechaFinExtraida != null) {
            fechaFin = new Date(fechaFinExtraida.getYear() - 1900, fechaFinExtraida.getMonthValue() - 1, fechaFinExtraida.getDayOfMonth());
        }
        return fechaFin;
    }

    /**
     * Este método avanza una página en el paginado.
     */
    private void avanzarPagina() {
        this.paginado.avanzarPagina();
    }

    /**
     * Este método retrocede una página en el paginado.
     */
    private void retrocederPagina() {
        this.paginado.retrocederPagina();
    }

    /**
     * Este método inserta la lista de cuentas activas pertenecientes al cliente
     * logeado en una ComboBox.
     */
    private void insertarCuentas() {
        try {
            List<Cuenta> cuentas = cuentasDAO.consultarLista(usuarioLogeado.getCliente().getId());
            CuentaComboBoxModel model = new CuentaComboBoxModel(cuentas);
            this.cbxCuentas.setModel(model);
            this.cbxCuentas.setSelectedIndex(0);
        } catch (PersistenciaException ex) {
            mostrarMensaje("Hubo un error al revisar la lista de cuentas");
            regresarMenu();
        }
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpnFondo = new javax.swing.JPanel();
        lblOperaciones = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOperaciones = new javax.swing.JTable();
        btnRegresar = new javax.swing.JButton();
        btnVolver = new javax.swing.JButton();
        btnConsultar = new javax.swing.JButton();
        cbxPaginas = new javax.swing.JComboBox<>();
        dpcFechaInicio = new com.github.lgooddatepicker.components.DatePicker();
        lblOperaciones1 = new javax.swing.JLabel();
        lblOperaciones2 = new javax.swing.JLabel();
        cbxCuentas = new javax.swing.JComboBox<>();
        lblOperaciones4 = new javax.swing.JLabel();
        lblOperaciones3 = new javax.swing.JLabel();
        dpcFechaFin = new com.github.lgooddatepicker.components.DatePicker();
        btnSiguiente1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jpnFondo.setBackground(new java.awt.Color(12, 28, 68));
        jpnFondo.setForeground(new java.awt.Color(12, 28, 68));
        jpnFondo.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblOperaciones.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        lblOperaciones.setForeground(new java.awt.Color(255, 255, 255));
        lblOperaciones.setText("Fecha Inicio");
        jpnFondo.add(lblOperaciones, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 90, -1, -1));

        tblOperaciones.setBackground(new java.awt.Color(183, 210, 242));
        tblOperaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Monto", "Fecha", "Estado", "Id_Cuenta", "Folio", "Password"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Float.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblOperaciones);

        jpnFondo.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 530, 150));

        btnRegresar.setText("Regresar");
        btnRegresar.setBackground(new java.awt.Color(183, 210, 242));
        btnRegresar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRegresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegresarActionPerformed(evt);
            }
        });
        jpnFondo.add(btnRegresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, -1, -1));

        btnVolver.setText("Volver");
        btnVolver.setBackground(new java.awt.Color(65, 103, 158));
        btnVolver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnVolver.setForeground(new java.awt.Color(255, 255, 255));
        btnVolver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVolverActionPerformed(evt);
            }
        });
        jpnFondo.add(btnVolver, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, 90, -1));

        btnConsultar.setText("Consultar");
        btnConsultar.setBackground(new java.awt.Color(65, 103, 158));
        btnConsultar.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnConsultar.setForeground(new java.awt.Color(255, 255, 255));
        btnConsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConsultarActionPerformed(evt);
            }
        });
        jpnFondo.add(btnConsultar, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 360, 90, -1));

        cbxPaginas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "3", "5", "10" }));
        cbxPaginas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxPaginasItemStateChanged(evt);
            }
        });
        jpnFondo.add(cbxPaginas, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 50, 90, -1));
        jpnFondo.add(dpcFechaInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 210, -1));

        lblOperaciones1.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 50)); // NOI18N
        lblOperaciones1.setForeground(new java.awt.Color(255, 255, 255));
        lblOperaciones1.setText("Retiros");
        jpnFondo.add(lblOperaciones1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        lblOperaciones2.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        lblOperaciones2.setForeground(new java.awt.Color(255, 255, 255));
        lblOperaciones2.setText("Ver registros");
        jpnFondo.add(lblOperaciones2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 20, -1, -1));

        cbxCuentas.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxCuentasItemStateChanged(evt);
            }
        });
        jpnFondo.add(cbxCuentas, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 340, 210, -1));

        lblOperaciones4.setText("Cuenta");
        lblOperaciones4.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        lblOperaciones4.setForeground(new java.awt.Color(255, 255, 255));
        jpnFondo.add(lblOperaciones4, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 320, -1, -1));

        lblOperaciones3.setFont(new java.awt.Font("MS Reference Sans Serif", 0, 12)); // NOI18N
        lblOperaciones3.setForeground(new java.awt.Color(255, 255, 255));
        lblOperaciones3.setText("Fecha Fin");
        jpnFondo.add(lblOperaciones3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 90, -1, -1));
        jpnFondo.add(dpcFechaFin, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 110, 210, -1));

        btnSiguiente1.setText("Siguiente");
        btnSiguiente1.setBackground(new java.awt.Color(65, 103, 158));
        btnSiguiente1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSiguiente1.setForeground(new java.awt.Color(255, 255, 255));
        btnSiguiente1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSiguiente1ActionPerformed(evt);
            }
        });
        jpnFondo.add(btnSiguiente1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, 90, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 574, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jpnFondo, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
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
        regresarMenu();
    }//GEN-LAST:event_btnRegresarActionPerformed
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y llama al
     * método retrocederPagina.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnVolverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVolverActionPerformed
        retrocederPagina();
    }//GEN-LAST:event_btnVolverActionPerformed
    /**
     * Este evento ocurre cuando un usuario modifica la selección en un ComboBox
     * y modifica los elementos por pagina en el paginado.
     *
     * @param evt Evento al modificar la selección en un ComboBox.
     */
    private void cbxPaginasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxPaginasItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            int elementosPorPagina = Integer.parseInt((String) this.cbxPaginas.getSelectedItem());
            this.paginado.setElementosPorPagina(elementosPorPagina);
            this.cargarTablaRetiros();
        }
    }//GEN-LAST:event_cbxPaginasItemStateChanged
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y llama al
     * método avanzarPagina.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnSiguiente1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSiguiente1ActionPerformed
        avanzarPagina();
    }//GEN-LAST:event_btnSiguiente1ActionPerformed
    /**
     * Este evento ocurre cuando un usuario modifica la selección en un ComboBox
     * y modifica la cuenta seleccionada.
     *
     * @param evt Evento al modificar la selección en un ComboBox.
     */
    private void cbxCuentasItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxCuentasItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            this.cargarTablaRetiros();
        }
    }//GEN-LAST:event_cbxCuentasItemStateChanged
    /**
     * Este evento ocurre cuando un usuario da un click en el botón y carga la
     * tabla de retiros.
     *
     * @param evt Evento al dar click en el botón.
     */
    private void btnConsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConsultarActionPerformed
        this.cargarTablaRetiros();
    }//GEN-LAST:event_btnConsultarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConsultar;
    private javax.swing.JButton btnRegresar;
    private javax.swing.JButton btnSiguiente1;
    private javax.swing.JButton btnVolver;
    private javax.swing.JComboBox<Cuenta> cbxCuentas;
    private javax.swing.JComboBox<String> cbxPaginas;
    private com.github.lgooddatepicker.components.DatePicker dpcFechaFin;
    private com.github.lgooddatepicker.components.DatePicker dpcFechaInicio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel jpnFondo;
    private javax.swing.JLabel lblOperaciones;
    private javax.swing.JLabel lblOperaciones1;
    private javax.swing.JLabel lblOperaciones2;
    private javax.swing.JLabel lblOperaciones3;
    private javax.swing.JLabel lblOperaciones4;
    private javax.swing.JTable tblOperaciones;
    // End of variables declaration//GEN-END:variables
}

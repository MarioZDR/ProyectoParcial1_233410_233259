/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package principal;

import UI.FrmLogin;
import implementaciones.ClientesDAO;
import implementaciones.ConexionBD;
import implementaciones.CuentasDAO;
import implementaciones.DireccionesDAO;
import implementaciones.TransaccionesDAO;
import interfaces.IClientesDAO;
import interfaces.IConexionBD;
import interfaces.ICuentasDAO;
import interfaces.IDireccionesDAO;
import interfaces.ITransaccionesDAO;

/**
 *
 * @author Gabriel Mancinas
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       IConexionBD generadorConexiones = new ConexionBD("jdbc:mysql://localhost/bancoproyectouno","root","12345678");
        IClientesDAO clientesDAO = new ClientesDAO(generadorConexiones);
        IDireccionesDAO direccionesDAO = new DireccionesDAO(generadorConexiones);
        ITransaccionesDAO transaccionesDAO = new TransaccionesDAO(generadorConexiones);
        ICuentasDAO cuentasDAO = new CuentasDAO(generadorConexiones);
        new FrmLogin(clientesDAO,direccionesDAO,transaccionesDAO,cuentasDAO).setVisible(true);
    }
    
}

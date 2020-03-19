/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidor;

import redes.RedServidor;
import java.io.IOException;

/**
 * Es el encargado de iniciar los servicios 
 * @author Karen Dayanna Castaño Orjuela
 * @author Carlos Alberto Campos Armero
 */
public class Servidor {

    private RedServidor redServidor;

    public Servidor(RedServidor redServidor) {
        this.redServidor = redServidor;
    }
    /**
     * Se encarga de encender el servidor
     */
    public void ejecutarServidor() {
        try {
            this.redServidor.activar();
        } catch (IOException ex) {
            System.out.println("<< SERVER: NO PUDE INICIAR MIS SERVICIOS");
        }
    }

    public static void main(String[] args) {
        RedServidor red = new RedServidor(9090);

        Servidor servidor = new Servidor(red);
        servidor.ejecutarServidor();
    }
}

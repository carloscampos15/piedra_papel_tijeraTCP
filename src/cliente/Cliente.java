/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.IOException;
import redes.RedCliente;

/**
 *
 * @author carlo
 */
public class Cliente{

    private RedCliente redCliente;
    private String nombre;
    private int puerto;

    public Cliente(String nombre) throws IOException {      
        this.nombre = nombre;
        this.puerto = 9090;
        redCliente = new RedCliente(this.puerto, "127.0.0.1");
    }
    
    public void ejecutarCliente(){
        this.redCliente.procesar();
    }
    
    public static void main(String[] args) {
        try {
            Cliente c = new Cliente("carlos");
            c.ejecutarCliente();
        } catch (IOException ex) {
            System.out.println("El cliente no pudo inciar sus servicios");
        }
    }
}

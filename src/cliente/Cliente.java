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

    public String getNombre() {
        return nombre;
    }
    
    public void ejecutarCliente(){
        this.redCliente.procesar();
    }
    
    public boolean updateName(String name) throws IOException{
        this.redCliente.updateNameUser(name);
        return true;
    }
    
    public boolean setAction(String action) throws IOException{
        this.redCliente.setAction(action);
        return true;
    }
    
    public boolean restartGame(String name) throws IOException{
        this.redCliente.restartGame(name);
        return true;
    }
    
    public boolean confirmRestartGame(int confirm) throws IOException{
        this.redCliente.confirmRestartGame(confirm);
        return true;
    }
    
    public boolean closeGame() throws IOException{
        this.redCliente.closeGame();
        return true;
    }
    
    public void setNotificableRed(Notificable notificable){
        this.redCliente.setNotificable(notificable);
    }
}

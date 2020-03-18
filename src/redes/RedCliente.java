/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes;

import cliente.MensajeEntrada;
import cliente.Notificable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 *
 * @author carlo
 */
public class RedCliente {

    private Socket socket;
    private int puerto;
    private String destino;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private Notificable notificable;

    public RedCliente(int puerto, String destino) throws IOException {
        this.puerto = puerto;
        this.destino = destino;
        this.socket = new Socket(this.destino, this.puerto);
        entrada = new DataInputStream(socket.getInputStream());
        salida = new DataOutputStream(socket.getOutputStream());
    }

    public String recibir() throws IOException {
        DataInputStream entrada = new DataInputStream(socket.getInputStream());
        return entrada.readUTF();
    }

    public void enviar(String mensaje) throws IOException {
        DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
        salida.writeUTF(mensaje);
        salida.flush();
    }

    public void procesar() {
        MensajeEntrada readMessage = new MensajeEntrada(this.notificable, entrada);
        readMessage.start();
    }

    /**
     *
     * @param name
     * @return
     * @throws IOException
     */
    public boolean updateNameUser(String name) throws IOException {
        String message = "{name:"+name+"}";
        salida.writeUTF(message);
        salida.flush();
        return true;
    }
    
    /**
     *
     * @param action
     * @return
     * @throws IOException
     */
    public boolean setAction(String action) throws IOException {
        String message = "{action:"+action+"}";
        salida.writeUTF(message);
        salida.flush();
        return true;
    }
    
    /**
     *
     * @param name
     * @return
     * @throws IOException
     */
    public boolean restartGame(String name) throws IOException {
        String message = "{restartGame:TRUE,name:"+name+"}";
        salida.writeUTF(message);
        salida.flush();
        return true;
    }    
    
    /**
     *
     * @param confirm
     * @return
     * @throws IOException
     */
    public boolean confirmRestartGame(int confirm) throws IOException {
        String message = "{confirmGame:"+confirm+"}";
        salida.writeUTF(message);
        salida.flush();
        return true;
    }    
    
    /**
     *
     * @return
     * @throws IOException
     */
    public boolean closeGame() throws IOException {
        String message = "{closeGame:TRUE}";
        salida.writeUTF(message);
        salida.flush();
        return true;
    }    
    
    public Notificable getNotificable() {
        return notificable;
    }

    public void setNotificable(Notificable notificable) {
        this.notificable = notificable;
    }
}

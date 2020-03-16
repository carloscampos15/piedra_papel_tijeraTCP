/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author carlo
 */
public class RedCliente{

    private Socket socket;
    private int puerto;
    private String destino;

    public RedCliente(int puerto, String destino) throws IOException {
        this.puerto = puerto;
        this.destino = destino;
        this.socket = new Socket(this.destino, this.puerto);
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
        try {
            Scanner scn = new Scanner(System.in);
            DataInputStream entrada = new DataInputStream(socket.getInputStream());
            DataOutputStream salida = new DataOutputStream(socket.getOutputStream());
            
            //HILO DE ENVIO
            Thread sendMessage = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        
                        // read the message to deliver.
                        String msg = scn.nextLine();
                        
                        try {
                            // write on the output stream
                            salida.writeUTF(msg);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            
            //HILO DE LEER
            Thread readMessage = new Thread(new Runnable() {
                
                @Override
                public void run() {
                    
                    while (true) {
                        try {
                            // read the message sent to this client
                            String msg = entrada.readUTF();
                            System.out.println(msg);
                        } catch (IOException e) {
                            
                            e.printStackTrace();
                        }
                    }
                }
            }); sendMessage.start();
            readMessage.start();
        } catch (IOException ex) {
            Logger.getLogger(RedCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

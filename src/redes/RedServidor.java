/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redes;

import models.Juego;
import Controller.ControladorJuego;
import Controller.Jugador;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Se encarga de controlar todo lo que tiene que ver con el servidor
 * @author Karen Dayanna Castaño Orjuela
 * @author Carlos Alberto Campos Armero
 */
public class RedServidor {
    private ArrayList<Juego> juegos;  
    private ControladorJuego controller;
    private ServerSocket listenSocket;
    private int port;
    
    public RedServidor(int port) {
        this.port = port;
        juegos = new ArrayList<>();
        this.controller = new ControladorJuego(juegos);
    }
    
    public void setController(ControladorJuego controller) {
        this.controller = controller;
    }
    /**
     * se encarga de encerder el servidor
     * @throws IOException 
     */
    public void activar() throws IOException {
        System.out.println("<< SERVER: binding port");
        this.listenSocket = new ServerSocket(port);
        this.ejecutarServicios();
    }
    /**
     * Se encarga de realizar la conección con el cliente
     */
    public void ejecutarServicios() {
        try {
            while (true) {

                System.out.println("SERVER: Esperando clientes");

                Socket clientSocket = listenSocket.accept();

                System.out.println("SERVER: Cliente recibido");
                
                DataInputStream entrada = new DataInputStream(clientSocket.getInputStream());
                DataOutputStream salida = new DataOutputStream(clientSocket.getOutputStream());
                
                System.out.println("SERVER: Creando nuevo controlador para este cliente");
                
                Jugador jugador = new Jugador(clientSocket, entrada, salida, controller);
                this.juegos = controller.agregarJugadorSala(jugador);
                Thread t = new Thread(jugador);
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(RedServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

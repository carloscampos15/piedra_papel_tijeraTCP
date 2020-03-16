/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Esta encargado de todos los componenten que se requieren para un cliente
 * @author Karen Dayanna Castaño Orjuela
 * @author Carlos Alberto Campos Armero
 */
public class Jugador implements Runnable {

    private int juego_id;
    private String name;
    private String actionGame;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private Socket clientSocket;
    private ControladorJuego controller;

    public Jugador(Socket clientSocket, DataInputStream entrada, DataOutputStream salida, ControladorJuego controller) {
        this.entrada = entrada;
        this.salida = salida;
        this.clientSocket = clientSocket;
        this.controller = controller;
    }

    public String getActionGame() {
        return actionGame;
    }

    public void setActionGame(String actionGame) {
        this.actionGame = actionGame;
    }

    public int getJuego_id() {
        return juego_id;
    }

    public void setJuego_id(int juego_id) {
        this.juego_id = juego_id;
    }

    @Override
    public void run() {
        String received;
        while (true) {
            try {
                received = entrada.readUTF();
                JSONObject receivedJson = new JSONObject(received);
                
                
                
            } catch (IOException | JSONException ex) {
                System.out.println("<<Error de lectura");
            }
        }
    }
}

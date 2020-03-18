/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Juego;
import org.json.JSONException;
import org.json.JSONObject;
import redes.RedServidor;

/**
 * Esta encargado de todos los componenten que se requieren para un cliente
 *
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
    public boolean isAlive;

    public static final String PIEDRA = "PIEDRA";
    public static final String PAPEL = "PAPEL";
    public static final String TIJERAS = "TIJERAS";

    public Jugador(Socket clientSocket, DataInputStream entrada, DataOutputStream salida, ControladorJuego controller) {
        this.entrada = entrada;
        this.salida = salida;
        this.clientSocket = clientSocket;
        this.controller = controller;
        this.actionGame = "";
        this.isAlive = true;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void iniciarSesion(JSONObject receivedJson) throws JSONException, IOException {
        this.setName(receivedJson.getString("name"));
        boolean estadoJuego = controller.consultarEstadoJuego(this.juego_id);
        if (estadoJuego) {
            ArrayList<Juego> temp = RedServidor.juegos;
            ArrayList<Jugador> jugadores = temp.get(this.juego_id - 1).getPlayers();

            jugadores.get(0).salida.writeUTF("{response: EMPIEZA EL JUEGO, oponente: " + jugadores.get(1).getName() + ", code: 202}");
            jugadores.get(1).salida.writeUTF("{response: EMPIEZA EL JUEGO, oponente: " + jugadores.get(0).getName() + ", code: 202}");
        } else {
            salida.writeUTF("{response: ESPERANDO JUGADORES PARA INICIAR EL JUEGO..., code: 203}");
        }
    }

    public boolean enviarMensaje() {
        return true;
    }

    public ControladorJuego getController() {
        return controller;
    }

    @Override
    public void run() {
        while (isAlive) {
            String received;
            while (true) {
                try {
                    received = entrada.readUTF();
                    JSONObject receivedJson = new JSONObject(received);

                    if (receivedJson.has("name") && !receivedJson.has("restartGame")) {
                        this.iniciarSesion(receivedJson);
                    } else {
                        ArrayList<Juego> temp = RedServidor.juegos;
                        ArrayList<Jugador> jugadores = temp.get(this.juego_id - 1).getPlayers();
                        for (Jugador jugador : temp.get(this.juego_id - 1).getPlayers()) {

                            if (receivedJson.has("restartGame")) {
                                jugador.actionGame = "";
                                if (jugador != this) {
                                    jugador.salida.writeUTF("{response: " + this.getName() + " QUIERE JUGAR DE NUEVO, code: 204}");
                                    this.salida.writeUTF("{response: INVITACION PARA JUGAR DE NUEVO ENVIADA, code: 205}");
                                }
                            } else if (receivedJson.has("confirmGame")) {
                                if (receivedJson.getInt("confirmGame") == 0) {
                                    jugadores.get(0).salida.writeUTF("{response: EMPIEZA EL JUEGO, oponente: " + jugadores.get(1).getName() + ", code: 202}");
                                    jugadores.get(1).salida.writeUTF("{response: EMPIEZA EL JUEGO, oponente: " + jugadores.get(0).getName() + ", code: 202}");
                                } else {
                                    if (this == jugador) {
                                        jugadores.get(0).salida.writeUTF("{response: ESPERANDO JUGADORES PARA INICIAR EL JUEGO..., code: 203}");
                                        jugadores.get(1).salida.writeUTF("{response: ESPERANDO JUGADORES PARA INICIAR EL JUEGO..., code: 203}");

                                        temp.get(this.juego_id - 1).getPlayers().remove(this);
                                        RedServidor.juegos = temp;

                                        this.isAlive = false;
                                    }
                                }
                            } else if (receivedJson.has("closeGame")) {
                                if (this == jugador) {
                                    jugadores.get(0).salida.writeUTF("{response: ESPERANDO JUGADORES PARA INICIAR EL JUEGO..., code: 203}");
                                    jugadores.get(1).salida.writeUTF("{response: ESPERANDO JUGADORES PARA INICIAR EL JUEGO..., code: 203}");

                                    temp.get(this.juego_id - 1).getPlayers().remove(this);
                                    RedServidor.juegos = temp;

                                    this.isAlive = false;
                                }
                            } else if (receivedJson.has("action")) {
                                jugador.salida.writeUTF(controller.realizarAccion(this, receivedJson));
                            }
                        }
                    }
                } catch (IOException | JSONException ex) {
                    System.out.println("<<Error de lectura");
                }
            }
        }
    }
}

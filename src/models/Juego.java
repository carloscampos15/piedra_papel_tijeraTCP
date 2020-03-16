/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import Controller.Jugador;
import java.util.ArrayList;

/**
 * Es el encargado de tener todo los componentes del juego
 *
 * @author Karen Dayanna Castaño Orjuela
 * @author Carlos Alberto Campos Armero
 */
public class Juego {

    private ArrayList<Jugador> players;
    private String state;

    public Juego(ArrayList<Jugador> players) {
        this.players = players;
    }

    public ArrayList<Jugador> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Jugador> players) {
        this.players = players;
    }

    public void addPlayer(Jugador jugador) {
        players.add(jugador);
    }

    public boolean juegoEnLinea() {
        int cont = 0;
        for (Jugador jugador : players) {
            if (!jugador.getActionGame().equals("")) {
                cont++;
            }
        }

        if (players.size() < 2 || cont < 2) {
            return false;
        } else {
            return true;
        }
    }

    public String calcularGanador() {
        Jugador jugador1 = players.get(0);
        Jugador jugador2 = players.get(1);
        if ((jugador1.getActionGame().equals(Jugador.PIEDRA) && jugador2.getActionGame().equals(Jugador.TIJERAS))
                || (jugador1.getActionGame().equals(Jugador.PAPEL) && jugador2.getActionGame().equals(Jugador.PIEDRA))
                || (jugador1.getActionGame().equals(Jugador.TIJERAS) && jugador2.getActionGame().equals(Jugador.PAPEL))) {
            return "EL GANADOR ES: " + jugador1.getName();
        } else if ((jugador2.getActionGame().equals(Jugador.PIEDRA) && jugador1.getActionGame().equals(Jugador.TIJERAS))
                || (jugador2.getActionGame().equals(Jugador.PAPEL) && jugador1.getActionGame().equals(Jugador.PIEDRA))
                || (jugador2.getActionGame().equals(Jugador.TIJERAS) && jugador1.getActionGame().equals(Jugador.PAPEL))) {
            return "EL GANADOR ES: " + jugador2.getName();
        }
        return "EMPATE";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.Jugador;
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
        this.state = "EN ESPERA";
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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    /**
     * Si esta conectado una sola persona el juego queda en espera, y si son dos jugadores entonces inicia el juego
     * @return 
     */
    public boolean juegoEnLinea() {
        int cont = 0;
        for (Jugador jugador : players) {
            if (!jugador.getActionGame().equals("")) {
                cont++;
            }
        }

        if (players.size() < 2 || cont < 2) {
            this.state = "EN ESPERA";
            return false;
        } else {
            this.state = "JUEGO INICIADO";
            return true;
        }
    }
    
    /**
     * Segun la acción que realice cada jugador se mira quien es el posible ganador
     * @return 
     */
    public String calcularGanador() {
        Jugador jugador1 = players.get(0);
        Jugador jugador2 = players.get(1);
        this.state = "JUEGO TERMINADO";
        if ((jugador1.getActionGame().equalsIgnoreCase(Jugador.PIEDRA) && jugador2.getActionGame().equalsIgnoreCase(Jugador.TIJERAS))
                || (jugador1.getActionGame().equalsIgnoreCase(Jugador.PAPEL) && jugador2.getActionGame().equalsIgnoreCase(Jugador.PIEDRA))
                || (jugador1.getActionGame().equalsIgnoreCase(Jugador.TIJERAS) && jugador2.getActionGame().equalsIgnoreCase(Jugador.PAPEL))) {
            return jugador1.getName()+":"+jugador1.getActionGame()+","+jugador2.getName()+":"+jugador2.getActionGame()+", ganador: EL GANADOR ES " + jugador1.getName();
        } else if ((jugador2.getActionGame().equalsIgnoreCase(Jugador.PIEDRA) && jugador1.getActionGame().equalsIgnoreCase(Jugador.TIJERAS))
                || (jugador2.getActionGame().equalsIgnoreCase(Jugador.PAPEL) && jugador1.getActionGame().equalsIgnoreCase(Jugador.PIEDRA))
                || (jugador2.getActionGame().equalsIgnoreCase(Jugador.TIJERAS) && jugador1.getActionGame().equalsIgnoreCase(Jugador.PAPEL))) {
            return jugador1.getName()+":"+jugador1.getActionGame()+","+jugador2.getName()+":"+jugador2.getActionGame()+", ganador: EL GANADOR ES " + jugador2.getName();
        }
        return jugador1.getName()+":"+jugador1.getActionGame()+","+jugador2.getName()+":"+jugador2.getActionGame()+", ganador: EMPATE";
    }
}

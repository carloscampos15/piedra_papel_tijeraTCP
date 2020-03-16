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
    
    public void addPlayer(Jugador jugador){
        players.add(jugador);
    }
    
}

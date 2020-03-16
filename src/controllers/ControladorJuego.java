/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import models.Juego;
import java.util.ArrayList;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Es el encargado de todo lo que tiene que ver con el juego
 *
 * @author Karen Dayanna Castaño Orjuela
 * @author Carlos Alberto Campos Armero
 */
public class ControladorJuego {

    private ArrayList<Juego> juegos;

    public ControladorJuego(ArrayList<Juego> juegos) {
        this.juegos = juegos;
    }

    /**
     * Se encarga de mirar si existen usuarios que se encuentren en espera para
     * comenzar a jugar y no tienen un compañero
     *
     * @return
     */
    public boolean consultarDisponibilidad() {
        for (Juego juego : juegos) {
            if (juego.getPlayers().size() < 2) {
                return true;
            }
        }
        return false;
    }

    /**
     * Si existe un jugador que se encuentra en espera, agrega el nuevo jugador
     * para que puedan comenzar el juego
     *
     * @param jugador es la persona que va a ingresar a jugar (cliente)
     * @return
     */
    public ArrayList<Juego> agregarJugadorSala(Jugador jugador) {
        if (consultarDisponibilidad()) {
            int juego_id = 1;
            for (Juego juego : juegos) {
                if (juego.getPlayers().size() < 2) {
                    jugador.setJuego_id(juego_id);
                    juego.addPlayer(jugador);
                }
                juego_id ++;
            }
        } else {
            int juego_id = juegos.size()+1;
            ArrayList<Jugador> nuevosJugadores = new ArrayList<>();
            jugador.setJuego_id(juego_id);
            nuevosJugadores.add(jugador);
            Juego nuevoJuego = new Juego(nuevosJugadores);
            juegos.add(nuevoJuego);
        }
        return juegos;
    }
    
    public boolean consultarEstadoJuego(int juego_id){
        Juego juego = juegos.get(juego_id-1);
        if(juego.getPlayers().size() == 2){
            return true;
        }
        return false;
    }
    
    public String realizarAccion(Jugador jugador, JSONObject receivedJson) throws JSONException{
        Juego juego = juegos.get(jugador.getJuego_id() - 1);
        for(Jugador jugadore : juego.getPlayers()){
            if(jugadore == jugador){
                jugadore.setActionGame(receivedJson.getString("action"));
            }
        }
        
        if(juego.juegoEnLinea()){
            System.out.println("{" + juego.calcularGanador() + ", code: 200}");
            return "{" + juego.calcularGanador() + ", code: 200}";
        }
        return("{response: ESPERANDO RESPUESTA DEL OPONENTE, code: 201}");
    }
}
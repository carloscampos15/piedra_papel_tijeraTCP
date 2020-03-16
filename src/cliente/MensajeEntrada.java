/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import java.io.DataInputStream;
import java.io.IOException;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Es el hilo encargado de estar siempre pendiente de recibir un mensaje
 *
 * @author Karen Castaño Orjuela Castaño
 * @author Carlos Alberto Campos Armero
 */
public class MensajeEntrada extends Thread {

    private Notificable notificable;
    private DataInputStream entrada;

    public MensajeEntrada(Notificable notificable, DataInputStream entrada) {
        this.notificable = notificable;
        this.entrada = entrada;
    }

    /**
     * Segun el mensaje recibido le informa a la interfaz notificable para que
     * este realice el procedimeinto correspondiente
     *
     * @param receivedJson
     * @return
     * @throws org.json.JSONException
     */
    public String procesarMensaje(JSONObject receivedJson) throws JSONException {
        switch(receivedJson.getInt("code")){
            case 200:
                this.notificable.jugar(receivedJson);
                break;
            case 201:
                System.out.println(receivedJson);
                break;
            case 202:
                this.notificable.habilitarJuego(receivedJson.getString("oponente"));
                break;
            case 203:
                this.notificable.deshabilitarJuego(receivedJson.getString("response"));
                break;
        }
        return "";
    }

    @Override
    public void run() {
        while (true) {
            try {
                String msg = entrada.readUTF();
                JSONObject receivedJson = new JSONObject(msg);
                procesarMensaje(receivedJson);
            } catch (IOException e) {
                System.out.println(">>ERROR AL RECIBIR DATOS");
            } catch (JSONException ex) {
                System.out.println(">>ERROR TRANSFORMANDO DATOS");
            }
        }
    }

}

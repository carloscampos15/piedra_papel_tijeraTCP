/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cliente;

import org.json.JSONObject;

/**
 * Interfaz que conecta la interzas con el cliente
 * @author Karen Castaño Orjuela Castaño
 * @author Carlos Alberto Campos Armero
 */
public interface Notificable { 
    public void login(String mensaje);
    public void deshabilitarJuego(String mensaje);
    public void habilitarJuego(String mensaje);
    public void jugar(JSONObject receivedJson);
}

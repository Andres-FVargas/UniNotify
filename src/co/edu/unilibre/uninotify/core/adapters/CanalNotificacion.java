package co.edu.unilibre.uninotify.core.adapters;

import co.edu.unilibre.uninotify.model.Usuario;

public interface CanalNotificacion {
    boolean enviar(Usuario usuario, String mensaje);
    String getTipoCanal();
}
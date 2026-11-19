package co.edu.unilibre.omnisignal.core.adapters;

import co.edu.unilibre.omnisignal.model.Usuario;

public interface CanalNotificacion {
    boolean enviar(Usuario usuario, String mensaje);
    String getTipoCanal();
}
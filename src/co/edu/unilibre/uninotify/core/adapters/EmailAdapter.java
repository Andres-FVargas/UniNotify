package co.edu.unilibre.uninotify.core.adapters;

import co.edu.unilibre.uninotify.model.Usuario;

public class EmailAdapter implements CanalNotificacion {
    private final ServicioEmailExterno servicioEmail = new ServicioEmailExterno();

    @Override
    public boolean enviar(Usuario usuario, String mensaje) {
        try {
            servicioEmail.sendSMTP(usuario.getEmail(), "Notificación OmniSignal", mensaje);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getTipoCanal() {
        return "EMAIL";
    }
}
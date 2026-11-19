package co.edu.unilibre.uninotify.core.adapters;

import co.edu.unilibre.uninotify.model.Usuario;

public class WhatsAppAdapter implements CanalNotificacion {
    private final WhatsAppSDKExterno whatsappSdk = new WhatsAppSDKExterno();

    @Override
    public boolean enviar(Usuario usuario, String mensaje) {
        return whatsappSdk.pushMessage(usuario.getTelefono(), mensaje, true);
    }

    @Override
    public String getTipoCanal() {
        return "WHATSAPP";
    }
}
package co.edu.unilibre.omnisignal.core.adapters;

import co.edu.unilibre.omnisignal.model.Usuario;

public class SMSAdapter implements CanalNotificacion {
    private final ApiSMSExterna apiSms = new ApiSMSExterna();

    @Override
    public boolean enviar(Usuario usuario, String mensaje) {
        try {
            long numero = Long.parseLong(usuario.getTelefono().replaceAll("[^0-9]", ""));
            String textoRecortado = mensaje.length() > 140 ? mensaje.substring(0, 137) + "..." : mensaje;
            return apiSms.dispatchText(numero, textoRecortado) == 200;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getTipoCanal() {
        return "SMS";
    }
}
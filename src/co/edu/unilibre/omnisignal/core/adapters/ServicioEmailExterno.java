package co.edu.unilibre.omnisignal.core.adapters;

public class ServicioEmailExterno {
    public void sendSMTP(String correo, String asunto, String cuerpo) {
        if (correo == null || !correo.contains("@")) {
            throw new IllegalArgumentException("Dirección SMTP inválida");
        }
        System.out.println("   [SMTP Server] Para: " + correo + " | Asunto: " + asunto + " | Cuerpo: " + cuerpo);
    }
}
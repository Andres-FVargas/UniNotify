package co.edu.unilibre.omnisignal.core.adapters;

public class WhatsAppSDKExterno {
    public boolean pushMessage(String celular, String msg, boolean ack) {
        if (celular == null || celular.isEmpty()) return false;
        System.out.println("   [WhatsApp Business API] Cel: " + celular + " | Ack: " + ack + " | Msg: " + msg);
        return true;
    }
}

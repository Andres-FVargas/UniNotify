package co.edu.unilibre.uninotify.core.adapters;

public class ApiSMSExterna {
    public int dispatchText(long numeroTelefono, String textoCorto) {
        if (numeroTelefono <= 0) return 400;
        System.out.println("   [Twilio-Sim SMS] Tel: +" + numeroTelefono + " | Texto: " + textoCorto);
        return 200;
    }
}
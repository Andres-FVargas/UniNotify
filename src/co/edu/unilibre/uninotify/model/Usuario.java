package co.edu.unilibre.uninotify.model;

import java.util.HashSet;
import java.util.Set;

public class Usuario {
    private String id;
    private String nombre;
    private String email;
    private String telefono;
    private Set<String> canalesActivos;

    public Usuario(String id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.canalesActivos = new HashSet<>();
        this.canalesActivos.add("EMAIL");
        this.canalesActivos.add("SMS");
        this.canalesActivos.add("WHATSAPP");
    }

    public void configurarCanales(boolean email, boolean sms, boolean whatsapp) {
        canalesActivos.clear();
        if (email) canalesActivos.add("EMAIL");
        if (sms) canalesActivos.add("SMS");
        if (whatsapp) canalesActivos.add("WHATSAPP");
    }

    public boolean aceptaCanal(String tipoCanal) {
        return canalesActivos.contains(tipoCanal.toUpperCase());
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }
    public Set<String> getCanalesActivos() { return canalesActivos; }

    @Override
    public String toString() {
        return id + " - " + nombre + " (" + canalesActivos + ")";
    }
}

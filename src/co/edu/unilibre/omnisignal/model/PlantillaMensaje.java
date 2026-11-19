package co.edu.unilibre.omnisignal.model;

public class PlantillaMensaje {
    private String id;
    private String titulo;
    private String formato;

    public PlantillaMensaje(String id, String titulo, String formato) {
        this.id = id;
        this.titulo = titulo;
        this.formato = formato;
    }

    public String aplicar(String nombreUsuario, String contenido) {
        return formato.replace("{nombre}", nombreUsuario)
                      .replace("{mensaje}", contenido);
    }

    public String getId() { return id; }
    public String getTitulo() { return titulo; }

    @Override
    public String toString() {
        return id + " - " + titulo;
    }
}
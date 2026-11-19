package co.edu.unilibre.omnisignal.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistroLog {
    private String fechaHora;
    private String destinatario;
    private String canal;
    private String estado;
    private String detalle;

    public RegistroLog(String destinatario, String canal, String estado, String detalle) {
        this.fechaHora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        this.destinatario = destinatario;
        this.canal = canal;
        this.estado = estado;
        this.detalle = detalle;
    }

    public String getEstado() { return estado; }
    public String getCanal() { return canal; }

    @Override
    public String toString() {
        return String.format("[%s] Destinatario: %s | Canal: %s | Estado: %s | Msg: %s",
                fechaHora, destinatario, canal, estado, detalle);
    }
}
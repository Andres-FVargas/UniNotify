package co.edu.unilibre.uninotify.core.singleton;

import co.edu.unilibre.uninotify.model.RegistroLog;
import java.util.ArrayList;
import java.util.List;

public class RegistroHistorial {
    private static RegistroHistorial instancia;
    private final List<RegistroLog> logs;

    private RegistroHistorial() {
        this.logs = new ArrayList<>();
    }

    public static synchronized RegistroHistorial getInstancia() {
        if (instancia == null) {
            instancia = new RegistroHistorial();
        }
        return instancia;
    }

    public void agregarLogs(List<RegistroLog> nuevosLogs) {
        logs.addAll(nuevosLogs);
    }

    public List<RegistroLog> getLogs() {
        return new ArrayList<>(logs);
    }

    public void limpiar() {
        logs.clear();
    }
}
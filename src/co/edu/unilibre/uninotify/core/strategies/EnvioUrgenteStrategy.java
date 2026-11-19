package co.edu.unilibre.uninotify.core.strategies;

import co.edu.unilibre.uninotify.core.adapters.CanalNotificacion;
import co.edu.unilibre.uninotify.model.RegistroLog;
import co.edu.unilibre.uninotify.model.Usuario;
import java.util.ArrayList;
import java.util.List;

public class EnvioUrgenteStrategy implements EstrategiaEnvio {
    @Override
    public List<RegistroLog> despachar(Usuario usuario, String mensaje, List<CanalNotificacion> canales) {
        List<RegistroLog> resultados = new ArrayList<>();
        for (CanalNotificacion canal : canales) {
            if (usuario.aceptaCanal(canal.getTipoCanal())) {
                boolean exito = canal.enviar(usuario, "[URGENTE] " + mensaje);
                resultados.add(new RegistroLog(
                        usuario.getNombre(),
                        canal.getTipoCanal(),
                        exito ? "ENVIADO" : "FALLIDO",
                        mensaje
                ));
            }
        }
        return resultados;
    }
}
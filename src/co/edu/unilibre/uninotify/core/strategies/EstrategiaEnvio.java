package co.edu.unilibre.uninotify.core.strategies;

import co.edu.unilibre.uninotify.core.adapters.CanalNotificacion;
import co.edu.unilibre.uninotify.model.RegistroLog;
import co.edu.unilibre.uninotify.model.Usuario;
import java.util.List;

public interface EstrategiaEnvio {
    List<RegistroLog> despachar(Usuario usuario, String mensaje, List<CanalNotificacion> canales);
}
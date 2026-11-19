package co.edu.unilibre.omnisignal.core.strategies;

import co.edu.unilibre.omnisignal.core.adapters.CanalNotificacion;
import co.edu.unilibre.omnisignal.model.RegistroLog;
import co.edu.unilibre.omnisignal.model.Usuario;
import java.util.List;

public interface EstrategiaEnvio {
    List<RegistroLog> despachar(Usuario usuario, String mensaje, List<CanalNotificacion> canales);
}
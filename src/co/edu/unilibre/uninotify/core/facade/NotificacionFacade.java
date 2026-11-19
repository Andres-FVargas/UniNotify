package co.edu.unilibre.uninotify.core.facade;

import co.edu.unilibre.uninotify.model.PlantillaMensaje;
import co.edu.unilibre.uninotify.model.RegistroLog;
import co.edu.unilibre.uninotify.model.Usuario;
import co.edu.unilibre.uninotify.core.strategies.EstrategiaEnvio;
import co.edu.unilibre.uninotify.core.strategies.EnvioUrgenteStrategy;
import co.edu.unilibre.uninotify.core.strategies.EnvioInformativoStrategy;
import co.edu.unilibre.uninotify.core.strategies.EnvioConReintentoStrategy;
import co.edu.unilibre.uninotify.core.adapters.WhatsAppAdapter;
import co.edu.unilibre.uninotify.core.adapters.CanalNotificacion;
import co.edu.unilibre.uninotify.core.adapters.SMSAdapter;
import co.edu.unilibre.uninotify.core.adapters.EmailAdapter;
import co.edu.unilibre.uninotify.core.singleton.RegistroHistorial;
import java.util.*;

public class NotificacionFacade {
    private final List<CanalNotificacion> canales;
    private final Map<String, Usuario> usuarios;
    private final Map<String, PlantillaMensaje> plantillas;

    public NotificacionFacade() {
        this.canales = Arrays.asList(
                new EmailAdapter(),
                new SMSAdapter(),
                new WhatsAppAdapter()
        );
        this.usuarios = new LinkedHashMap<>();
        this.plantillas = new LinkedHashMap<>();
        cargarDatosIniciales();
    }

    private void cargarDatosIniciales() {
        usuarios.put("U01", new Usuario("U01", "Andrés Vargas", "andres@unilibre.edu.co", "573001234567"));
        usuarios.put("U02", new Usuario("U02", "Laura Gómez", "laura@unilibre.edu.co", "573109876543"));
        usuarios.put("U03", new Usuario("U03", "Usuario Fallo Prueba", "correo-invalido", ""));

        plantillas.put("P01", new PlantillaMensaje("P01", "Alerta de Seguridad", "ALERTA para {nombre}: {mensaje}"));
        plantillas.put("P02", new PlantillaMensaje("P02", "Recordatorio Académico", "Hola {nombre}, recuerda: {mensaje}"));
        plantillas.put("P03", new PlantillaMensaje("P03", "Mensaje Libre", "{nombre} - {mensaje}"));
    }

    public String enviarNotificacion(String idUsuario, String idPlantilla, String texto, String tipoPrioridad) {
        Usuario usuario = usuarios.get(idUsuario);
        if (usuario == null) return "Error: Usuario no encontrado.";

        PlantillaMensaje plantilla = plantillas.getOrDefault(idPlantilla, plantillas.get("P03"));
        String mensajeFormateado = plantilla.aplicar(usuario.getNombre(), texto);

        EstrategiaEnvio estrategia;
        switch (tipoPrioridad.toUpperCase()) {
            case "URGENTE":
                estrategia = new EnvioUrgenteStrategy();
                break;
            case "REINTENTO":
                estrategia = new EnvioConReintentoStrategy();
                break;
            default:
                estrategia = new EnvioInformativoStrategy();
                break;
        }

        List<RegistroLog> logsGenerados = estrategia.despachar(usuario, mensajeFormateado, canales);
        RegistroHistorial.getInstancia().agregarLogs(logsGenerados);

        long exitosos = logsGenerados.stream().filter(l -> l.getEstado().equals("ENVIADO")).count();
        return "Proceso finalizado para " + usuario.getNombre() + ". Envíos exitosos: " + exitosos + " de " + logsGenerados.size();
    }

    public void actualizarPreferencias(String idUsuario, boolean email, boolean sms, boolean whatsapp) {
        Usuario u = usuarios.get(idUsuario);
        if (u != null) {
            u.configurarCanales(email, sms, whatsapp);
        }
    }

    public Collection<Usuario> getUsuarios() { return usuarios.values(); }
    public Collection<PlantillaMensaje> getPlantillas() { return plantillas.values(); }
    public List<RegistroLog> getHistorial() { return RegistroHistorial.getInstancia().getLogs(); }
}
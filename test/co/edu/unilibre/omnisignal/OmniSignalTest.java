package co.edu.unilibre.omnisignal;

import co.edu.unilibre.omnisignal.core.adapters.*;
import co.edu.unilibre.omnisignal.core.facade.NotificacionFacade;
import co.edu.unilibre.omnisignal.core.singleton.RegistroHistorial;
import co.edu.unilibre.omnisignal.core.strategies.*;
import co.edu.unilibre.omnisignal.model.*;
import org.junit.Before;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class OmniSignalTest {

    @Before
    public void limpiarSingleton() {
        RegistroHistorial.getInstancia().limpiar();
    }

    @Test
    public void testPatronAdapterCanales() {
        Usuario user = new Usuario("U99", "Test", "test@unilibre.edu.co", "573001112233");
        CanalNotificacion email = new EmailAdapter();
        CanalNotificacion sms = new SMSAdapter();
        CanalNotificacion wa = new WhatsAppAdapter();

        assertTrue(email.enviar(user, "Prueba Email"));
        assertTrue(sms.enviar(user, "Prueba SMS"));
        assertTrue(wa.enviar(user, "Prueba WA"));
    }

    @Test
    public void testPatronStrategyUrgenteVsInformativo() {
        Usuario user = new Usuario("U99", "Test", "test@unilibre.edu.co", "573001112233");
        List<CanalNotificacion> canales = Arrays.asList(new EmailAdapter(), new SMSAdapter());

        EstrategiaEnvio urgente = new EnvioUrgenteStrategy();
        List<RegistroLog> logsUrgente = urgente.despachar(user, "Alerta", canales);
        assertEquals(2, logsUrgente.size());

        EstrategiaEnvio info = new EnvioInformativoStrategy();
        List<RegistroLog> logsInfo = info.despachar(user, "Aviso", canales);
        assertEquals(1, logsInfo.size());
    }

    @Test
    public void testPatronFacadeYSingleton() {
        NotificacionFacade facade = new NotificacionFacade();
        String respuesta = facade.enviarNotificacion("U01", "P01", "Fallo en servidor", "URGENTE");

        assertTrue(respuesta.contains("Envíos exitosos: 3 de 3"));
        assertEquals(3, RegistroHistorial.getInstancia().getLogs().size());
    }
}
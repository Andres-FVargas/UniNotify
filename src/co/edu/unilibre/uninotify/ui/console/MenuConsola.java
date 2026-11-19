package co.edu.unilibre.uninotify.ui.console;

import co.edu.unilibre.uninotify.model.PlantillaMensaje;
import co.edu.unilibre.uninotify.model.RegistroLog;
import co.edu.unilibre.uninotify.model.Usuario;
import co.edu.unilibre.uninotify.core.facade.NotificacionFacade;
import java.util.Scanner;

public class MenuConsola {
    private final NotificacionFacade facade;
    private final Scanner scanner;

    public MenuConsola(NotificacionFacade facade) {
        this.facade = facade;
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = 0;
        while (opcion != 4) {
            System.out.println("\n=== OMNISIGNAL (MODO CONSOLA) ===");
            System.out.println("1. Enviar Notificación");
            System.out.println("2. Configurar Preferencias de Usuario");
            System.out.println("3. Ver Historial de Envíos");
            System.out.println("4. Salir");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                opcion = 0;
            }

            switch (opcion) {
                case 1:
                    flujoEnvio();
                    break;
                case 2:
                    flujoPreferencias();
                    break;
                case 3:
                    System.out.println("\n--- HISTORIAL DE LOGS ---");
                    for (RegistroLog log : facade.getHistorial()) {
                        System.out.println(log);
                    }
                    break;
                case 4:
                    System.out.println("Cerrando consola...");
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private void flujoEnvio() {
        System.out.println("\nUsuarios disponibles:");
        for (Usuario u : facade.getUsuarios()) System.out.println("  " + u);
        System.out.print("ID Usuario (ej. U01): ");
        String idUser = scanner.nextLine().toUpperCase();

        System.out.println("Plantillas disponibles:");
        for (PlantillaMensaje p : facade.getPlantillas()) System.out.println("  " + p);
        System.out.print("ID Plantilla (ej. P01): ");
        String idPlantilla = scanner.nextLine().toUpperCase();

        System.out.print("Contenido del mensaje: ");
        String texto = scanner.nextLine();

        System.out.print("Prioridad (URGENTE / INFORMATIVO / REINTENTO): ");
        String prioridad = scanner.nextLine().toUpperCase();

        String resultado = facade.enviarNotificacion(idUser, idPlantilla, texto, prioridad);
        System.out.println(">> " + resultado);
    }

    private void flujoPreferencias() {
        System.out.print("ID Usuario a configurar (ej. U01): ");
        String idUser = scanner.nextLine().toUpperCase();
        System.out.print("¿Activar EMAIL? (true/false): ");
        boolean email = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("¿Activar SMS? (true/false): ");
        boolean sms = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("¿Activar WHATSAPP? (true/false): ");
        boolean wa = Boolean.parseBoolean(scanner.nextLine());

        facade.actualizarPreferencias(idUser, email, sms, wa);
        System.out.println(">> Preferencias actualizadas.");
    }
}
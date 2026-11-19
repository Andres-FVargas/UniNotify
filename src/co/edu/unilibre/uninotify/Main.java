package co.edu.unilibre.uninotify;

import co.edu.unilibre.uninotify.core.facade.NotificacionFacade;
import co.edu.unilibre.uninotify.ui.console.MenuConsola;
import co.edu.unilibre.uninotify.ui.gui.VentanaOmniSignal;
import javax.swing.SwingUtilities;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        NotificacionFacade facadeCompartida = new NotificacionFacade();
        Scanner sc = new Scanner(System.in);

        System.out.println("==========================================");
        System.out.println("   BIENVENIDO A OMNISIGNAL NOTIFIER");
        System.out.println("==========================================");
        System.out.println("1. Ejecutar en Consola");
        System.out.println("2. Ejecutar en Interfaz Gráfica (Swing)");
        System.out.print("Elija modo de inicio: ");

        String opcion = sc.nextLine();
        if ("2".equals(opcion)) {
            SwingUtilities.invokeLater(() -> new VentanaOmniSignal(facadeCompartida).setVisible(true));
        } else {
            new MenuConsola(facadeCompartida).iniciar();
        }
    }
}
package co.edu.unilibre.uninotify.ui.gui;

import co.edu.unilibre.uninotify.core.facade.NotificacionFacade;
import co.edu.unilibre.uninotify.model.RegistroLog;
import javax.swing.*;
import java.awt.*;

public class VentanaOmniSignal extends JFrame {
    private final NotificacionFacade facade;
    private JComboBox<String> cmbUsuario;
    private JComboBox<String> cmbPlantilla;
    private JComboBox<String> cmbPrioridad;
    private JTextField txtMensaje;
    private JCheckBox chkEmail, chkSms, chkWhatsapp;
    private JTextArea areaHistorial;

    public VentanaOmniSignal(NotificacionFacade facade) {
        this.facade = facade;
        configurarVentana();
        inicializarComponentes();
        actualizarHistorial();
    }

    private void configurarVentana() {
        setTitle("OmniSignal - Centro de Notificaciones Multicanal");
        setSize(680, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 8, 8));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Configuración de Envío"));

        cmbUsuario = new JComboBox<>(new String[]{"U01", "U02", "U03"});
        cmbPlantilla = new JComboBox<>(new String[]{"P01", "P02", "P03"});
        cmbPrioridad = new JComboBox<>(new String[]{"URGENTE", "INFORMATIVO", "REINTENTO"});
        txtMensaje = new JTextField("Revisión de arquitectura completada");

        JPanel panelCanales = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkEmail = new JCheckBox("Email", true);
        chkSms = new JCheckBox("SMS", true);
        chkWhatsapp = new JCheckBox("WhatsApp", true);
        panelCanales.add(chkEmail);
        panelCanales.add(chkSms);
        panelCanales.add(chkWhatsapp);

        JButton btnEnviar = new JButton("Despachar Notificación");
        btnEnviar.setBackground(new Color(33, 150, 243));
        btnEnviar.setForeground(Color.WHITE);

        panelFormulario.add(new JLabel("Usuario Destinatario:"));
        panelFormulario.add(cmbUsuario);
        panelFormulario.add(new JLabel("Canales Aceptados por Usuario:"));
        panelFormulario.add(panelCanales);
        panelFormulario.add(new JLabel("Plantilla de Mensaje:"));
        panelFormulario.add(cmbPlantilla);
        panelFormulario.add(new JLabel("Prioridad (Strategy):"));
        panelFormulario.add(cmbPrioridad);
        panelFormulario.add(new JLabel("Contenido del Mensaje:"));
        panelFormulario.add(txtMensaje);
        panelFormulario.add(new JLabel(""));
        panelFormulario.add(btnEnviar);

        areaHistorial = new JTextArea();
        areaHistorial.setEditable(false);
        areaHistorial.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scroll = new JScrollPane(areaHistorial);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro de Auditoría (Singleton)"));

        add(panelFormulario, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        btnEnviar.addActionListener(e -> {
            String user = (String) cmbUsuario.getSelectedItem();
            facade.actualizarPreferencias(user, chkEmail.isSelected(), chkSms.isSelected(), chkWhatsapp.isSelected());
            String respuesta = facade.enviarNotificacion(
                    user,
                    (String) cmbPlantilla.getSelectedItem(),
                    txtMensaje.getText(),
                    (String) cmbPrioridad.getSelectedItem()
            );
            JOptionPane.showMessageDialog(this, respuesta, "Resultado OmniSignal", JOptionPane.INFORMATION_MESSAGE);
            actualizarHistorial();
        });
    }

    private void actualizarHistorial() {
        StringBuilder sb = new StringBuilder();
        for (RegistroLog log : facade.getHistorial()) {
            sb.append(log.toString()).append("\n");
        }
        areaHistorial.setText(sb.toString());
    }
}
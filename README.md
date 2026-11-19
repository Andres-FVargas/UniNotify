# UniNotify — Motor de Notificaciones Multicanal

Proyecto desarrollado en Java para el **Parcial de Patrones de Diseño** (Ingeniería de Software II - Universidad Libre).

## Funcionalidades Implementadas
* **Envío Multicanal Adaptado:** Soporte para notificaciones simuladas vía **Email (SMTP)**, **SMS (Twilio API)** y **WhatsApp Business SDK**.
* **Estrategias de Despacho Dinámicas:**
  * `URGENTE`: Envía la alerta simultáneamente por todos los canales activos del usuario.
  * `INFORMATIVO`: Despacha el mensaje únicamente por el primer canal habilitado.
  * `REINTENTO`: Intenta el envío secuencialmente a través de los canales hasta lograr una entrega exitosa.
* **Plantillas Personalizables:** Sustitución dinámica de etiquetas (`{nombre}`, `{mensaje}`) para alertas de seguridad, recordatorios académicos o mensajes libres.
* **Preferencias por Usuario:** Activación o desactivación granular de canales por destinatario.
* **Registro de Auditoría Centralizado:** Historial en tiempo real de envíos exitosos y fallidos compartido entre interfaces.
* **Doble Interfaz Desacoplada:** Ejecución tanto por **Consola** como por **Interfaz Gráfica (Java Swing)** sobre exactamente el mismo núcleo de negocio.

## Patrones de Diseño Aplicados
1. **Adapter (Estructural):** Estandariza las interfaces incompatibles de los proveedores externos (`ServicioEmailExterno`, `ApiSMSExterna`, `WhatsAppSDKExterno`) bajo la interfaz `CanalNotificacion`.
2. **Strategy (Comportamiento):** Encapsula los algoritmos de envío (`EnvioUrgenteStrategy`, `EnvioInformativoStrategy`, `EnvioConReintentoStrategy`) eliminando bloques condicionales rígidos.
3. **Facade (Estructural):** `NotificacionFacade` oculta la complejidad de coordinación del núcleo y expone una interfaz limpia para las vistas.
4. **Singleton (Creacional):** `RegistroHistorial` garantiza una única instancia global del log de auditoría durante toda la ejecución.

## Requisitos y Ejecución
* **JDK:** Java 17 o superior.
* **IDE:** Apache NetBeans (proyecto Ant).
* **Pruebas:** JUnit 4.13.2.

### Cómo ejecutar
1. Clonar el repositorio: `git clone https://github.com/Andres-FVargas/UniNotify.git`
2. Abrir el proyecto `UniNotify` en Apache NetBeans.
3. Ejecutar `co.edu.unilibre.uninotify.Main` (`Shift + F6`) y elegir `1` para **Consola** o `2` para **Interfaz Gráfica (Swing)**.
4. Para ejecutar las pruebas unitarias, correr el test en `co.edu.unilibre.uninotify` (`Ctrl + F6`).

## Declaración de Integridad Académica y Uso de IA
Para el desarrollo de este proyecto se utilizó apoyo de herramientas de Inteligencia Artificial (Gemini) como asistente técnico para estructurar la arquitectura inicial de paquetes en Java, generar el código base de los diagramas UML en PlantUML y validar las buenas prácticas en las pruebas unitarias con JUnit 4. Todo el código fuente, la lógica de los patrones de diseño, las interfaces y la documentación entregada fueron revisados, adaptados, probados y comprendidos en su totalidad por los integrantes del grupo.
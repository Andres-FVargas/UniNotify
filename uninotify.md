Para nuestro proyecto UniNotify (Sistema de Notificaciones Multicanal), decidimos enfocarnos en construir un motor de mensajería sólido, desacoplado y fácil de escalar, delimitando el sistema de la siguiente manera:  

Lo que SÍ implementamos: 

Envío de notificaciones simuladas a través de tres canales distintos: Correo Electrónico (Email), SMS y WhatsApp.  

 

Gestión de reglas de despacho dinámicas según la prioridad del mensaje: Envío Urgente (dispara la alerta por todos los canales activos del usuario), Envío Informativo (envía el mensaje únicamente por el primer canal habilitado) y Envío con Reintento (intenta despachar secuencialmente a través de los canales del destinatario hasta lograr una entrega exitosa).  

 

Personalización de mensajes mediante plantillas reutilizables (alerta de seguridad, recordatorio académico o mensaje libre) donde se reemplaza dinámicamente el nombre del destinatario y el contenido.  

 

Configuración de preferencias por usuario (qué canales acepta recibir) y un historial centralizado en memoria con el registro de todos los envíos exitosos y fallidos.  

 

Una fachada única y sencilla (NotificacionFacade) para que tanto la interfaz por consola como la interfaz gráfica en Java Swing ejecuten las mismas operaciones sin conocer cómo funciona cada canal por debajo. 

Lo que queda FUERA del alcance (y por qué): 

Conexión real a APIs de terceros (Twilio, servidores SMTP reales o WhatsApp Business API): Se simulan mediante salidas estructuradas y validaciones en consola/log porque el objetivo de este trabajo es evaluar la arquitectura y los patrones de diseño, no gastar presupuesto en pasarelas de mensajería ni depender de servicios de red externos.  

 

Base de datos relacional persistente o aplicación web: Una interfaz web no suma puntos adicionales y configurar motores de bases de datos externos desviaría el enfoque hacia infraestructura en lugar de pulir los patrones y las pruebas unitarias. Por eso, los usuarios de prueba y el historial de auditoría se gestionan en memoria durante la ejecución. 

 

2. Requisitos del sistema.

Requisitos Funcionales (RF) 

RF1. Gestión de Canales: El sistema debe permitir enviar notificaciones mediante tres canales simulados (correo electrónico, SMS y WhatsApp), adaptando los parámetros y el formato del mensaje a lo que espera cada proveedor.  

 

RF2. Gestión de Destinatarios: El sistema debe contar con usuarios precargados identificados por su ID, nombre, correo electrónico y número de teléfono, permitiendo consultarlos y administrar sus preferencias.  

 

RF3. Preferencias de Notificación: El sistema debe permitir activar o desactivar qué canales acepta cada usuario, evitando enviar mensajes por aquellos medios que el destinatario haya deshabilitado.  

 

RF4. Envío según Prioridad: El sistema debe despachar notificaciones urgentes por todos los canales habilitados del usuario, e informativas únicamente por el primer canal disponible.  

 

RF5. Respaldo y Reintento entre Canales: Cuando se seleccione la prioridad de reintento, el sistema debe intentar enviar la notificación recorriendo los canales habilitados del usuario hasta conseguir una entrega exitosa. 

  

RF6. Plantillas de Mensajes: El sistema debe permitir elegir entre distintas plantillas predefinidas y personalizarlas automáticamente con el nombre del destinatario y el texto ingresado.  

 

RF7. Historial de Auditoría: El sistema debe registrar y permitir consultar todos los intentos de envío, mostrando fecha, hora, destinatario, canal utilizado y estado del mensaje (ENVIADO o FALLIDO). 

 

Requisitos No Funcionales (RNF) 

RNF1. Rendimiento: El sistema debe procesar el despacho de notificaciones, cambios de preferencias y consultas del historial en menos de 2 segundos en condiciones normales.  

 

RNF2. Usabilidad: La aplicación debe ofrecer tanto un menú claro por consola como una ventana gráfica intuitiva en Java Swing, permitiendo operar todas las funciones sin dificultad.  

 

RNF3. Fiabilidad: Ante datos de contacto inválidos (como un correo mal formado o un teléfono vacío), el sistema debe capturar el error, registrar el estado como fallido y continuar operando sin cerrarse inesperadamente.  

RNF4. Disponibilidad: El software debe funcionar completamente offline, al tener los proveedores externos simulados localmente.  

 

RNF5. Mantenibilidad y Extensibilidad: El código debe estar organizado en paquetes independientes (model, core.adapters, core.strategies, core.facade, core.singleton, ui.console, ui.gui) para agregar nuevos canales o reglas sin modificar la lógica existente.  

 

RNF6. Compatibilidad: El proyecto debe compilar y ejecutarse correctamente en equipos con Windows que cuenten con JDK 17 o superior y Apache NetBeans. 

 

3. Problemas de Diseño Identificados 

Problema 1. Incompatibilidad entre proveedores de mensajería: El sistema necesita enviar mensajes por correo electrónico, SMS y WhatsApp, pero cada servicio externo trabaja con firmas y tipos de datos distintos (sendSMTP recibe tres cadenas, dispatchText exige un número long y devuelve un código HTTP, y pushMessage pide un booleano de confirmación). Acoplar el núcleo a estas diferencias obligaría a llenar el código de validaciones específicas por canal. 

 

Problema 2. Variación en las reglas de despacho: Las notificaciones no siempre se comportan igual: una alerta urgente debe salir por todos los medios posibles, un aviso informativo solo requiere un canal y un envío con reintento debe buscar respaldo si falla el primer medio. Si manejamos esto con bloques if-else gigantes, cada nueva regla obligaría a modificar la clase principal de envío.  

 

Problema 3. Complejidad en la coordinación del envío: Para despachar una notificación hay que buscar al usuario, aplicar la plantilla, seleccionar la estrategia de prioridad, recorrer los canales adaptados y guardar los resultados en el log. Si tanto la consola como la interfaz gráfica hicieran estos pasos por su cuenta, duplicaríamos lógica de negocio en las vistas.  

 

Problema 4. Dispersión del registro de auditoría: Tanto la consola como la ventana gráfica necesitan consultar y alimentar exactamente el mismo historial de mensajes enviados y fallidos durante toda la sesión. Si cada vista creara su propia lista de registros, perderíamos la trazabilidad global del sistema. 

 

4. Patrones de Diseño Seleccionados 

Para resolver estos cuatro problemas arquitectónicos, aplicamos los siguientes patrones de diseño: 

PATRON 

Tipo 

Problema que soluciona 

 

Clases principales en UniNotify 

Adapter 

Estructural 

Unifica las interfaces incompatibles de Email, SMS y WhatsApp bajo una interfaz común. 

CanalNotificacion, EmailAdapter, SMSAdapter, WhatsAppAdapter 

Strategy 

Comportamiento 

Permite intercambiar dinámicamente el algoritmo de envío según la prioridad del mensaje. 

EstrategiaEnvio, EnvioUrgenteStrategy, EnvioInformativoStrategy, EnvioConReintentoStrategy 

Facade 

Estructural 

Simplifica el acceso al núcleo del sistema para que consola y Swing usen las mismas operaciones. 

NotificacionFacade 

Singleton 

Creacional 

Garantiza una única instancia centralizada del historial de auditoría en toda la aplicación. 

RegistroHistorial 




# SOBREVIVE AL 18 🇨🇱

Videojuego 2D desarrollado en **Java y LibGDX**. Controlas a un huaso que debe recoger comida típica chilena y evitar beber demasiados terremotos.

## Cómo jugar

- **← / →:** mover al personaje.
- **ESC:** pausar o reanudar la partida.
- **Empanada:** +10 puntos.
- **Anticucho:** +15 puntos.
- **Terremoto:** resta una vida.

Comienzas con **10 vidas**. Desde el quinto terremoto se activa el mareo durante **30 segundos**, que altera la cámara y el movimiento. Los objetos aumentan su velocidad según el puntaje.

## Ejecutar

**Requisitos:** Java 17 y conexión a internet para la primera descarga de dependencias de Gradle.

Desde la carpeta raíz del proyecto, donde está `gradlew.bat`, ejecuta en PowerShell:

```powershell
.\gradlew.bat lwjgl3:run
```

Para verificar la compilación:

```powershell
.\gradlew.bat core:compileJava
```

## Estructura y patrones

- `core`: lógica, personajes, objetos y pantallas del juego.
- `lwjgl3`: ejecución de escritorio.
- `assets`: imágenes, música y efectos de sonido.
- **Singleton:** `GestorRecord` administra el récord.
- **Strategy:** `MovimientoNormal` y `MovimientoMareado`.
- **Template Method:** `ObjetoCaida.procesarColision()`.
- **Builder:** `ConfiguracionPartida` define vidas, duración del mareo y velocidad inicial.

**Proyecto académico — Programación Avanzada (GM).**

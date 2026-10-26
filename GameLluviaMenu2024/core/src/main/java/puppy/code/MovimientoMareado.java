package puppy.code;

import com.badlogic.gdx.math.MathUtils;

/**
 * GM-7: Patron de diseno Strategy.
 *
 * Estrategia de movimiento cuando el jugador
 * se encuentra mareado por los terremotos.
 *
 * El jugador camina con menor precision y
 * experimenta desviaciones hacia los lados.
 */

public class MovimientoMareado implements EstrategiaMovimiento
{
    @Override 
    public float calcularDesplazamiento(boolean izquierda, boolean derecha, float velocidad, float delta, float tiempo)
    {
        // Si no se presiona ningun o ambas a la vez se permanecera quieto
        if(izquierda == derecha)
        {
            return 0f; 
        }

        // Determinar la direccion para donde va el movimiento
        float direccion = derecha ? 1f : -1f;

        // Reducir la velocidad controlada del jugador
        float velocidadMareada = velocidad * 0.65f;

        // Generar oscilaciones laterales (crear movimientos involuntarios)
        float desviacion = MathUtils.sin(tiempo * 7f) * velocidad * 0.90f;

        // Combinar movimientos y desviacion considerando el tiempo entre fotogramas
        return (direccion * velocidadMareada + desviacion) * delta;
    }
}

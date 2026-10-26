package puppy.code;

/**
 * GM-7: Patron Strategy.
 *
 * Estrategia de movimiento normal.
 * El jugador se mueve hacia la izquierda
 * o derecha utilizando las flechas.
 * 
 */

public class MovimientoNormal implements EstrategiaMovimiento
{
    @Override 
    public float calcularDesplazamiento(boolean izquierda, boolean derecha, float velocidad, float delta, float tiempo)
    {
        float desplazamiento = 0f;

        if(izquierda)
        {
            desplazamiento -= velocidad * delta;
        }
        
        if(derecha)
        {
            desplazamiento += velocidad * delta; 
        }

        return desplazamiento;
    }
}

package puppy.code;

/**
 * GM-7 Este es el patron de diseño para aplicar Strategy
 * 
 * Define un comportamiento de movimientos 
 * el que puede utilizar el personaje
 * 
 * Permitira intercambiar el movimiento normal y el movimiento mareado
*/

public interface EstrategiaMovimiento 
{
    /**
     * Calcula cuanto se debe desplazarse el jugador
     * 
     * @param izquierda indica si presiona izquierda
     * @param derecha indica si presiona derecha
     * @param velocidad la velocidad base que tiene el usuario
     * @param delta tiempo trascurrido entre fotogramas
     * @param tiempo tiempo acumulado para las oscilaciones.
     * 
     * @return desplazamiento horizontal en pixeles
     */

    float calcularDesplazamiento(boolean izquierda, boolean derecha, float velocidad, float delta, float tiempo);
}

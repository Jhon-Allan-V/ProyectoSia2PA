package puppy.code;

import com.badlogic.gdx.graphics.Texture;

// Clase hija los cuales son los objetos que caeran en el juego
// Este representa la empanada dentro del juego


public class Empanada extends ObjetoCaida implements Recolectable
{
    public Empanada(float x, float y, Texture textura, float velocidad)
    {
        super(x, y, textura, velocidad);
    }

    @Override 
    public int getPuntos()
    {
        return 10; 
    }

    @Override
    public void aplicarEfecto(Tarro tarro)
    {
        // Empanda otorga 10 puntos
        tarro.sumarPuntos(getPuntos());
    }
}
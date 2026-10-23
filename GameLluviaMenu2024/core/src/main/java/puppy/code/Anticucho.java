package puppy.code;

import com.badlogic.gdx.graphics.Texture;

// Clase hija los cuales son los objetos que caeran en el juego 

// Este representa el anticucho dentro del juego

public class Anticucho extends ObjetoCaida implements Recolectable
{
    public Anticucho(float x, float y, Texture textura, float velocidad)
    {
        super(x, y, textura, velocidad);
    }

    @Override 
    public int getPuntos()
    {
        return 15; 
    }

    @Override
    public void aplicarEfecto(Tarro tarro)
    {
        // El anticucho otorga 15 puntos
        tarro.sumarPuntos(getPuntos());
    }
}
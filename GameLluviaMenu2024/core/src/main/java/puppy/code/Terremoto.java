
package puppy.code;

import com.badlogic.gdx.graphics.Texture;

// Bebida alcoholica que provoca daño en el jugador 

public class Terremoto extends ObjetoCaida
{
    public Terremoto(float x, float y, Texture textura, float velocidad)
    {
        super(x, y, textura, velocidad);
    }

    @Override
    public void aplicarEfecto(Tarro tarro)
    {
        // El jugador pierde una vida.
        tarro.dañar();
    }
}
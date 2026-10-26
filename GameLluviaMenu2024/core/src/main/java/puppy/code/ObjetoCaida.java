package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.math.Rectangle;

/* 

Gm-4 Una clase abstracta que va a representar cualquier objeto
en caida durante la partida.

Por ejemplo los objetos Empanada, Anticucho y Terremoto heredaran
esta parte.

*/

public abstract class ObjetoCaida 
{
    private final Rectangle area; 
    private final Texture texture;
    private final float velocidad; 

    public ObjetoCaida(float x, float y, Texture texture, float velocidad)
    {
        this.area = new Rectangle(x, y, 64, 64);
        this.texture = texture;
        this.velocidad = velocidad; 
    }

    public void actualiza()
    {
        area.y -= velocidad * Gdx.graphics.getDeltaTime();
    }

    public void dibujar(SpriteBatch batch)
    {
        batch.draw(texture, area.x, area.y, area.width, area.height);
    }

    public boolean estaFueraPantalla()
    {
        return area.y + area.height < 0;
    }

    public boolean colisiona(Rectangle jugador)
    {
        return area.overlaps(jugador);
    }

    public Rectangle getArea()
    {
        return new Rectangle(area);
    }

    /* 
    Metodo abstracto el cual cada comida puede implementar su efecto
    */

    public abstract void aplicarEfecto(Tarro tarro); 
}

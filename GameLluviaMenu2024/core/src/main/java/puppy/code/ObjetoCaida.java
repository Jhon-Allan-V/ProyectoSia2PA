package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * GM-4: Clase abstracta de objetos en caida.
 *
 * GM-5: Encapsulamiento de atributos
 * y copia defensiva del rectangulo.
 *
 * GM-8: Patron Template Method.
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

    /**
     * GM-8 Template Method
     * 
     * Define el procedimiento comun que
     * siguen todos los objetos al colisionar.
     *
     * Es final para que las clases hijas
     * no puedan cambiar el orden de pasos.
     *
     * Retorna true si se aplico el efecto.
     */

     public final boolean procesarColision(Tarro tarro)
    {
        // PASO 1:
        // Verificar que el jugador exista
        // tenga vidas y no este herido
        if (tarro == null || tarro.getVidas() <= 0 || tarro.estaHerido())
        {
            return false;
        }

        // PASO 2:
        // Verificar que exista una colision
        if (!colisiona(tarro.getArea()))
        {
            return false;
        }

        // PASO 3:
        // Cada clase hija aplica su efecto
        aplicarEfecto(tarro);

        // La colision fue procesada.
        return true;
    }

     /**
     * Metodo abstracto utilizado por
     * el Template Method.
     *
     * Cada objeto define su propio efecto.
     */

    public abstract void aplicarEfecto(Tarro tarro); 
}

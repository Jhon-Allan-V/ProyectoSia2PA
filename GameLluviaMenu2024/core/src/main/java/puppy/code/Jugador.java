package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/*
- Clase que repreesnta al jugador dentro del juego.
-
- El huaso es el profesor cubillos con vestido de huaso
- y debe recolectar la mayor cantidad de comida sin beber mucho
- terremoto. 
-
- Este hereda de Tarro para mantener las mecanicas que ya estan 
- implementadas. 

*/

public class Jugador extends Tarro
{
    // Animacion de movimiento para el jugador
    private Texture paso1; 
    private Texture paso2;
    private Texture paso3; 
    private Texture texturaMareado;

    // Imagen de cuando el jugador esta quieto
    private TextureRegion quieto;

    // Animacion para las 2 direcciones
    private Animation<TextureRegion> caminaDerecha;
    private Animation<TextureRegion> caminaIzquierda;

    private float tiempoAnimacion = 0;
    private boolean caminando = false; 
    private boolean mirandoDerecha = true;

    private Rectangle areaColision = new Rectangle();

    private static float ANCHO = 128;
    private static float ALTO = 160;


    public Jugador(Texture textura, Texture paso1, Texture paso2, Texture paso3, Sound hurtSound)
    {
        super(textura, hurtSound);

        this.paso1 = paso1;
        this.paso2 = paso2;
        this.paso3 = paso3;
        
        texturaMareado = new Texture(Gdx.files.internal("jugador_mareado.png"));
        texturaMareado.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);


        textura.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        paso1.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        paso2.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);
        paso3.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        quieto = new TextureRegion(textura);

        TextureRegion[] derecha = {new TextureRegion(paso1), new TextureRegion(paso2), new TextureRegion(paso3)};
        TextureRegion[] izquierda = {new TextureRegion(paso1), new TextureRegion(paso2), new TextureRegion(paso3)};

        for(TextureRegion frame : izquierda)
        {
            frame.flip(true, false);
        }

        caminaDerecha = new Animation<TextureRegion>(0.15f, derecha);
        caminaIzquierda = new Animation<TextureRegion>(0.15f, izquierda);
    }

    @Override 
    public Rectangle getArea()
    {
        Rectangle posicion = super.getArea();

        areaColision.set(posicion.x + 28, posicion.y + 10, 72, 110);

        return areaColision;
    }

    @Override 
    public void actualizarMovimiento()
    {
        float xAnterior = super.getArea().x;

        super.actualizarMovimiento();

        if (super.getArea().x > 800 - ANCHO)
        {
            super.getArea().x = 800 - ANCHO;
        }

        float diferencia = super.getArea().x - xAnterior;

        caminando = Math.abs(diferencia) > 0.001f;

        if(caminando)
        {
            mirandoDerecha = diferencia > 0;
            tiempoAnimacion += Gdx.graphics.getDeltaTime();
        }
        else
        {
            tiempoAnimacion = 0;
        }
    }

    @Override 
    public void dibujar(SpriteBatch batch)
    {

        Rectangle area = super.getArea();

        if(estaHerido())
        {
            batch.draw(texturaMareado, area.x, area.y, ANCHO, ALTO);

            actualizarEstadoHerido();

            return;
        }

        TextureRegion imagenActual;

        if(caminando)
        {
            if(mirandoDerecha)
            {
                imagenActual = caminaDerecha.getKeyFrame(tiempoAnimacion, true);
            }
            else
            {
                imagenActual = caminaIzquierda.getKeyFrame(tiempoAnimacion, true);
            }
        }
        else
        {
            imagenActual = quieto;
        }

        batch.draw(imagenActual, area.x, area.y, ANCHO, ALTO);
    }

    @Override 
    public void destruir()
    {
         // Liberar la textura original de Tarro.
        super.destruir();

        // Liberar las texturas adicionales.
        paso1.dispose();
        paso2.dispose();
        paso3.dispose();
        texturaMareado.dispose();
    }
}

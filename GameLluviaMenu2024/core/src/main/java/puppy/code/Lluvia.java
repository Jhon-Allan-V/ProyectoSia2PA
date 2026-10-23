package puppy.code;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

public class Lluvia 
{
	// Array que almacena los objetos que caen
	private Array<ObjetoCaida> objetos; 
   
	private long lastDropTime;
    
	// Textura de los nuevos objetos
	private Texture texturaEmpanada;
    private Texture texturaAnticucho;
    private Texture texturaTerremoto;
    
	private Sound dropSound;
    private Music rainMusic;
	   
	public Lluvia(Texture texturaEmpanada, Texture texturaAnticucho, Texture texturaTerremoto, Sound ss, Music mm) 
	{
		this.texturaEmpanada = texturaEmpanada;
		this.texturaAnticucho = texturaAnticucho;
		this.texturaTerremoto = texturaTerremoto;

		rainMusic = mm;
		dropSound = ss;
	}
	
	public void crear() 
	{
		objetos = new Array<ObjetoCaida>();
	     
		crearObjeto();

	    rainMusic.setLooping(true);
	    rainMusic.play();
	}
	
	private void crearObjeto() 
	{
		float x = MathUtils.random(0, 800 - 64);
		float y = 480;
		float velocidad = 300;

		int tipo = MathUtils.random(1, 3);

		ObjetoCaida objeto;

		if (tipo == 1)
        {
            objeto = new Empanada(x, y, texturaEmpanada, velocidad);
        }
        else if (tipo == 2)
        {
            objeto = new Anticucho(x, y, texturaAnticucho, velocidad);
        }
        else
        {
            objeto = new Terremoto(x, y, texturaTerremoto, velocidad);
        }

        objetos.add(objeto);

        lastDropTime = TimeUtils.nanoTime();
	}
	
    public boolean actualizarMovimiento(Tarro tarro) 
    { 
	   // generar gotas de lluvia 
	   if(TimeUtils.nanoTime() - lastDropTime > 100000000)
	   {
			crearObjeto();
	   } 

	   for(int i = objetos.size - 1; i >= 0; i--)
	   {
			ObjetoCaida objeto = objetos.get(i);

			// Movimiento vertical
			objeto.actualiza();

			// Eliminar si este salio de la pantalla

			if (objeto.estaFueraPantalla())
			{
				objetos.removeIndex(i);
				continue; 
			}

			// Colision de jugador validacion

			if(objeto.colisiona(tarro.getArea()))
			{
				/* 
				
				Tiene polimorfismo ya que cada clase
				tiene su propio efecto sobre el jugador
				
				*/
				
				objeto.aplicarEfecto(tarro);

				// Los objetos recolectables
				// reproducen un sonido
				if(objeto instanceof Recolectable)
				{
					dropSound.play();
				}

				objetos.removeIndex(i);

				if(tarro.getVidas() <= 0)
				{
					return false; 
				}
			}
	   }

	   return true; 
	    
    }  
   
	public void actualizarDibujoLluvia(SpriteBatch batch) 
    {
		for (ObjetoCaida objeto : objetos)
        {
            objeto.dibujar(batch);
        }
    }
   
    public void destruir()
    {
        dropSound.dispose();
        rainMusic.dispose();

        texturaEmpanada.dispose();
        texturaAnticucho.dispose();
        texturaTerremoto.dispose();
    }

    public void pausar()
    {
        rainMusic.stop();
    }

    public void continuar()
    {
        rainMusic.play();
    }
}

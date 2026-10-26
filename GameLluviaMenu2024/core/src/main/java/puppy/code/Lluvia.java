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
    
	private Sound comerSound;
	private Sound beberSound;
	private Music musica;

	// GM-9: Configuracion construida mediante Builder.
	private final ConfiguracionPartida configuracion;
	   
	public Lluvia(Texture texturaEmpanada, Texture texturaAnticucho, Texture texturaTerremoto, Sound comerSound, Sound beberSound, Music mm, ConfiguracionPartida configuracion) 
	{
		this.texturaEmpanada = texturaEmpanada;
		this.texturaAnticucho = texturaAnticucho;
		this.texturaTerremoto = texturaTerremoto;

		this.comerSound = comerSound;
    	this.beberSound = beberSound;
    	this.musica = mm;

		 // GM-9: Guardar la configuracion de la partida.
    	this.configuracion = configuracion;
	}
	
	public void crear() 
	{
		objetos = new Array<ObjetoCaida>();
	     
		crearObjeto(0);

	    musica.setLooping(true);
		musica.setVolume(0.35f);
		musica.play();
	}
	
	private void crearObjeto(int puntos) 
	{
		float x = MathUtils.random(0, 800 - 64);
		float y = 480;
		float velocidad = configuracion.getVelocidadInicialCaida();

		if(puntos >= 2000)
		{
			velocidad = 400f;
		}
		else if(puntos >= 1000)
		{
			velocidad = 350f;
		}
		else if(puntos >= 500)
		{
			velocidad = 270f;
		}

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
			crearObjeto(tarro.getPuntos());
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

			/**
			 * GM-8 UTILIZACION DE TEMPLATE METHOD
			 * 
			 * ObjetoCaida verifica las condiciones
			 * detecta la colision y aplica su efecto
			 */

			if(objeto.procesarColision(tarro))
			{
				if (objeto instanceof Recolectable)
				{
					comerSound.play(0.7f);
				}
				else
				{
					beberSound.play(0.7f);
				}

				// Eliminar el objeto recogido.
				objetos.removeIndex(i);

				// Comprobar si termino la partida.
				if (tarro.getVidas() <= 0)
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
        comerSound.dispose();
        beberSound.dispose();
        musica.dispose();

        texturaEmpanada.dispose();
        texturaAnticucho.dispose();
        texturaTerremoto.dispose();
    }

    public void pausar()
    {
        musica.stop();
    }

    public void continuar()
    {
        musica.play();
    }
}

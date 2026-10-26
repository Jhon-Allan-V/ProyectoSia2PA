package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Tarro {
	private Rectangle bucket;
	private Texture bucketImage;
	private Sound sonidoHerido;
	private int vidas = 10;
	private int puntos = 0;
	private int terremotosTomados = 0;
	private int velx = 400;
	
	// GM-7 Estrategia utilizada para mover al player
	private EstrategiaMovimiento estrategiaMovimiento = new MovimientoNormal();

	// Tiempo para calcular las oscilaciones
	private float tiempoMovimiento = 0f; 
	
	private boolean herido = false;
	private int tiempoHeridoMax = 50;
	private int tiempoHerido;
	
	
	public Tarro(Texture tex, Sound ss) 
	{
		bucketImage = tex;
		sonidoHerido = ss;
	}
	
	public int getVidas() 
	{
		return vidas;
	}

	/**
	 * GM-7: Cambiar la estrategia de movimiento
	 * durante la partida.
	 */
	public void setEstrategiaMovimiento(EstrategiaMovimiento nuevaEstrategia)
	{
		if (nuevaEstrategia != null)
		{
			this.estrategiaMovimiento = nuevaEstrategia;
		}
	}

	
	public int getPuntos() 
	{
		return puntos;
	}
	
	public Rectangle getArea() 
	{
		return new Rectangle(bucket);
	}

	protected void limitarPosicionDerecha(float maximo)
    {
        bucket.x = MathUtils.clamp(bucket.x, 0f, maximo);
    }
	
	public void sumarPuntos(int pp) 
	{
		// Solamente se pueden usar valores positivos
		if(pp > 0)
		{
			puntos += pp;
		}
	}

	public int getTerremotosTomados()
	{
		return terremotosTomados;
	}

	public void beberTerremoto()
	{
		terremotosTomados++;

		dañar();

		Gdx.app.log("FONDA", "Terremotos tomados: " + terremotosTomados);
	}
		
	
	public void crear() 
	{
		    bucket = new Rectangle();
		    bucket.x = 800 / 2 - 64 / 2;
		    bucket.y = 20;
		    bucket.width = 64;
		    bucket.height = 64;
	}
	public void dañar() 
	{
		// Evitar descontar vidas si ya se llego a cero
		if(vidas <= 0)
		{
		  	return;
		}

		vidas--;

		herido = true;
		tiempoHerido = tiempoHeridoMax;
		sonidoHerido.play();
	}
	   
	 
	 
	public void dibujar(SpriteBatch batch) 
	{
		
		if (!herido)
		{
			batch.draw(bucketImage, bucket.x, bucket.y);
		}
		else
		{
			batch.draw(bucketImage, bucket.x, bucket.y + MathUtils.random(-5, 5));

			actualizarEstadoHerido();
		}
	}

	protected void actualizarEstadoHerido()
	{
		if (herido)
		{
			tiempoHerido--;

			if (tiempoHerido <= 0)
			{
				herido = false;
			}
		}
	}
	    
	public void actualizarMovimiento() 
	{ 
		// Tiempo transcurrido desde el ultimo fotograma.
		float delta = Gdx.graphics.getDeltaTime();

		tiempoMovimiento += delta;

		// Detectar las teclas.
		boolean izquierda =
			Gdx.input.isKeyPressed(Input.Keys.LEFT);

		boolean derecha =
			Gdx.input.isKeyPressed(Input.Keys.RIGHT);

		// GM-7: La estrategia calcula el desplazamiento.
		float desplazamiento = estrategiaMovimiento.calcularDesplazamiento(izquierda, derecha, velx, delta, tiempoMovimiento);

		// Aplicar el movimiento al jugador.
		bucket.x += desplazamiento;

		// Evitar que salga de los limites.
		bucket.x = MathUtils.clamp(bucket.x, 0f, 800f - 64f);
	}
	    

	public void destruir() {
		bucketImage.dispose();
		sonidoHerido.dispose();
	   }
	
   	public boolean estaHerido() {
	   return herido;
   	}
	   
}

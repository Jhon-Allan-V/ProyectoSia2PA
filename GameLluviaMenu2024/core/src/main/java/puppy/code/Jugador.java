package puppy.code;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;

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
    public Jugador(Texture textura, Sound hurtSound)
    {
        super(textura, hurtSound);
    }
}

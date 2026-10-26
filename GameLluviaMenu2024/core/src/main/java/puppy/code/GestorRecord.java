
package puppy.code;

/**
 * GM-6: Patron de diseno Singleton.
 *
 * Administra el record del jugador.
 *
 * Solo puede existir una instancia de esta clase
 * durante la ejecucion del programa.
 */
public final class GestorRecord
{
    // Instancia unica del Singleton.
    private static final GestorRecord instancia = new GestorRecord();

    // Record actual del juego.
    private int record = 0;

    
    // Constructor privado.
    // Impide crear objetos con new GestorRecord().
     
    private GestorRecord()
    {
    }

    
    // Permite obtener la unica instancia.
     
    public static GestorRecord getInstancia()
    {
        return instancia;
    }

    
    // Consultar el record actual.
     
    public int getRecord()
    {
        return record;
    }

    
    // Actualizar el record solamente
    // cuando el nuevo puntaje sea mayor.
     
    public void actualizarRecord(int nuevoPuntaje)
    {
        if (nuevoPuntaje > record)
        {
            record = nuevoPuntaje;
        }
    }
}

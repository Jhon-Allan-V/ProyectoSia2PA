
package puppy.code;

/**
 * GM-9: Patron de diseno Builder.
 *
 * Permite construir una configuracion
 * personalizada para la partida.
 */

public final class ConfiguracionPartida
{
    private final int vidasIniciales;
    private final float duracionMareo;
    private final float velocidadInicialCaida;

    // Constructor privado: solo Builder puede usarlo.
    private ConfiguracionPartida(Builder builder)
    {
        this.vidasIniciales = builder.vidasIniciales;
        this.duracionMareo = builder.duracionMareo;
        this.velocidadInicialCaida = builder.velocidadInicialCaida;
    }

    public int getVidasIniciales()
    {
        return vidasIniciales;
    }

    public float getDuracionMareo()
    {
        return duracionMareo;
    }

    public float getVelocidadInicialCaida()
    {
        return velocidadInicialCaida;
    }

    // =====================================
    // GM-9: BUILDER
    // =====================================

    public static class Builder
    {
        // Valores predeterminados del juego.
        private int vidasIniciales = 10;
        private float duracionMareo = 30f;
        private float velocidadInicialCaida = 200f;

        public Builder vidasIniciales(int vidas)
        {
            this.vidasIniciales = vidas;
            return this;
        }

        public Builder duracionMareo(float segundos)
        {
            this.duracionMareo = segundos;
            return this;
        }

        public Builder velocidadInicialCaida(float velocidad)
        {
            this.velocidadInicialCaida = velocidad;
            return this;
        }

        public ConfiguracionPartida build()
        {
            if (vidasIniciales <= 0 || duracionMareo <= 0 || velocidadInicialCaida <= 0)
            {
                throw new IllegalArgumentException("La configuracion solo permite valores positivos");
            }

            return new ConfiguracionPartida(this);
        }
    }
}

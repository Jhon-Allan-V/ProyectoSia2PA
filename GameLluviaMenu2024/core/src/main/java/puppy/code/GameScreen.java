package puppy.code;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.math.MathUtils;

public class GameScreen implements Screen
{
    final GameLluviaMenu game;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    private Tarro tarro;
    private Lluvia lluvia;
    private Texture fonda;
    private Texture barraHUD;

    // Variables para controlar el mareo.
    private int ultimoTerremotoRegistrado = 0;
    private float tiempoMareo = 0f;
    private float tiempoOsilacion = 0f;

    public GameScreen(final GameLluviaMenu game)
    {
        this.game = game;

        this.batch = game.getBatch();
        this.font = game.getFont();

        Sound hurtSound = Gdx.audio.newSound(
            Gdx.files.internal("hurt.ogg")
        );

        // Crear al jugador con sus animaciones.
        tarro = new Jugador(
            new Texture(Gdx.files.internal("jugador_quieto.png")),
            new Texture(Gdx.files.internal("jugador_paso1.png")),
            new Texture(Gdx.files.internal("jugador_paso2.png")),
            new Texture(Gdx.files.internal("jugador_paso3.png")),
            hurtSound
        );

        // Cargar los alimentos y terremotos.
        Texture empanada = new Texture(
            Gdx.files.internal("Empanada.png")
        );

        Texture anticucho = new Texture(
            Gdx.files.internal("Anticucho.png")
        );

        Texture terremoto = new Texture(
            Gdx.files.internal("Terremoto.png")
        );

        // Fondo de la fonda.
        fonda = new Texture(
            Gdx.files.internal("Fonda.png")
        );

        // Crear la barra oscura del HUD.
        Pixmap pixmapHUD = new Pixmap(
            1, 1, Pixmap.Format.RGBA8888
        );

        pixmapHUD.setColor(0.03f, 0.05f, 0.12f, 0.85f);
        pixmapHUD.fill();

        barraHUD = new Texture(pixmapHUD);

        pixmapHUD.dispose();

        // Mantener el estilo pixel art.
        empanada.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        anticucho.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        terremoto.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        // Cargar los efectos de sonido.
        Sound comerSound = Gdx.audio.newSound(
            Gdx.files.internal("comer.wav")
        );

        Sound beberSound = Gdx.audio.newSound(
            Gdx.files.internal("beber.wav")
        );

        // Musica dieciochera.
        Music musica = Gdx.audio.newMusic(
            Gdx.files.internal("cueca_fondo.ogg")
        );

        // Crear la lluvia de objetos.
        lluvia = new Lluvia(
            empanada,
            anticucho,
            terremoto,
            comerSound,
            beberSound,
            musica
        );

        // Configurar la camara.
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        // Iniciar la partida.
        tarro.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta)
    {
        // =====================================
        // PAUSAR CON ESC
        // =====================================

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            lluvia.pausar();

            game.setScreen(new PausaScreen(game, this));

            return;
        }

        ScreenUtils.clear(0, 0, 0.2f, 1);

        // =====================================
        // ACTUALIZAR EL JUGADOR
        // =====================================

        if(!tarro.estaHerido())
        {
            tarro.actualizarMovimiento();

            boolean sigueJugando =
                lluvia.actualizarMovimiento(tarro);

            if(!sigueJugando)
            {
                if(game.getHigherScore() < tarro.getPuntos())
                {
                    game.setHigherScore(tarro.getPuntos());
                }

                game.setScreen(new GameOverScreen(game));

                dispose();

                return;
            }
        }

        // =====================================
        // TEMPORIZADOR DE MAREO
        // =====================================

        // Reducir el tiempo restante.
        if(tiempoMareo > 0f)
        {
            tiempoMareo -= delta;

            if(tiempoMareo < 0f)
            {
                tiempoMareo = 0f;
            }
        }

        // Revisar cuantos terremotos ha tomado.
        int terremotosActuales =
            tarro.getTerremotosTomados();

        // Detectar si ha tomado uno nuevo.
        if(terremotosActuales > ultimoTerremotoRegistrado)
        {
            ultimoTerremotoRegistrado = terremotosActuales;

            // Desde el quinto terremoto en adelante.
            if(terremotosActuales >= 5)
            {
                // Reiniciar los 60 segundos.
                tiempoMareo = 60f;

                tiempoOsilacion = 0f;

                Gdx.app.log(
                    "FONDA",
                    "Mareo activado por 60 segundos"
                );
            }
        }

        // =====================================
        // MOVIMIENTO DE CAMARA POR MAREO
        // =====================================

        // Restaurar la posicion normal.
        camera.position.set(400f, 240f, 0f);

        if(tiempoMareo > 0f)
        {
            tiempoOsilacion += delta;

            // Oscilacion horizontal mas intensa.
            camera.position.x +=
                MathUtils.sin(tiempoOsilacion * 11f) * 12f;

            // Oscilacion vertical.
            camera.position.y +=
                MathUtils.sin(tiempoOsilacion * 9f) * 8f;
        }
        else
        {
            tiempoOsilacion = 0f;
        }

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        // =====================================
        // DIBUJAR EL JUEGO
        // =====================================

        batch.begin();

        // Fondo ampliado para cubrir el movimiento.
        batch.draw(fonda, -16, -12, 832, 504);

        // Dibujar al huaso.
        tarro.dibujar(batch);

        // Dibujar empanadas, anticuchos y terremotos.
        lluvia.actualizarDibujoLluvia(batch);

        // =====================================
        // HUD - BARRA SUPERIOR
        // =====================================

        // Barra oscura semitransparente.
        batch.draw(barraHUD, 0, 395, 800, 85);

        // Puntaje del jugador.
        font.draw(
            batch,
            "Puntos: " + tarro.getPuntos(),
            15,
            465
        );

        // Record.
        font.draw(
            batch,
            "HighScore: " + game.getHigherScore(),
            280,
            465
        );

        // Vidas restantes.
        font.draw(
            batch,
            "Vidas: " + tarro.getVidas(),
            630,
            465
        );

        // =====================================
        // CONTADOR DE TERREMOTOS
        // =====================================

        font.draw(
            batch,
            "Terremotos: " + tarro.getTerremotosTomados(),
            15,
            415
        );

        // =====================================
        // TEMPORIZADOR VISIBLE DEL MAREO
        // =====================================

        if(tiempoMareo > 0f)
        {
            font.draw(
                batch,
                "MAREO: " + MathUtils.ceil(tiempoMareo) + " s",
                300,
                415
            );
        }
        else
        {
            font.draw(
                batch,
                "Estado: NORMAL",
                300,
                415
            );
        }

        batch.end();
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void show()
    {
        lluvia.continuar();
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void pause()
    {
        lluvia.pausar();
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void dispose()
    {
        tarro.destruir();
        lluvia.destruir();

        // Liberar recursos graficos.
        fonda.dispose();
        barraHUD.dispose();
    }
}

package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameScreen implements Screen
{
    final GameLluviaMenu game;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;

    private Tarro tarro;
    private Lluvia lluvia;
    private Texture fonda;

    public GameScreen(final GameLluviaMenu game)
    {
        this.game = game;

        this.batch = game.getBatch();
        this.font = game.getFont();

        Sound hurtSound = Gdx.audio.newSound(Gdx.files.internal("hurt.ogg"));

        tarro = new Jugador(new Texture(Gdx.files.internal("jugador_quieto.png")), hurtSound);

        Texture empanada = new Texture(Gdx.files.internal("Empanada.png"));

        Texture anticucho = new Texture(Gdx.files.internal("Anticucho.png"));

        Texture terremoto = new Texture(Gdx.files.internal("Terremoto.png"));

        // NUEVO: cargar el fondo en el atributo de la clase
        fonda = new Texture(Gdx.files.internal("Fonda.png"));

        empanada.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        anticucho.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        terremoto.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        Sound comerSound = Gdx.audio.newSound(Gdx.files.internal("comer.wav"));

		Sound beberSound = Gdx.audio.newSound(Gdx.files.internal("beber.wav"));

        Music musica = Gdx.audio.newMusic(Gdx.files.internal("cueca_fondo.ogg"));

        lluvia = new Lluvia(empanada, anticucho, terremoto, comerSound, beberSound, musica);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        tarro.crear();
        lluvia.crear();
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();

        batch.setProjectionMatrix(camera.combined);

        if(!tarro.estaHerido())
        {
            tarro.actualizarMovimiento();

            boolean sigueJugando = lluvia.actualizarMovimiento(tarro);

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

        batch.begin();

        // NUEVO: dibujar el fondo antes de los demás elementos
        batch.draw(fonda, 0, 0, 800, 480);

        // Puntaje del usuario
        font.draw(batch, "Puntos: " + tarro.getPuntos(), 5, 475);

        // Mostrar vidas restantes.
        font.draw(batch, " Vidas: " + tarro.getVidas(), 670, 475);

        // Mostrar puntuacion maxima.
        font.draw(batch, "HighScore: " + game.getHigherScore(), camera.viewportWidth / 2 - 50, 475);

        tarro.dibujar(batch);

        // Dibujar empanadas, anticuchos y terremotos.
        lluvia.actualizarDibujoLluvia(batch);

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

        game.setScreen(new PausaScreen(game, this));
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

        // NUEVO: liberar la textura del fondo
        fonda.dispose();
    }
}

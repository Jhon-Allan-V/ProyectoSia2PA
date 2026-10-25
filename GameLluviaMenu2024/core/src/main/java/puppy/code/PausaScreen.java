
package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;


public class PausaScreen implements Screen
{
    final GameLluviaMenu game;

    private GameScreen partida;

    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private Texture fondoPausa;

    private Texture pixel;

    private Rectangle botonContinuar;
    private Rectangle botonMenu;

    public PausaScreen(final GameLluviaMenu game,
                       GameScreen partida)
    {
        this.game = game;
        this.partida = partida;

        this.batch = game.getBatch();
        this.font = game.getFont();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        fondoPausa = new Texture(
            Gdx.files.internal("Fondo.png")
        );

        fondoPausa.setFilter(
            Texture.TextureFilter.Nearest,
            Texture.TextureFilter.Nearest
        );

        Pixmap pixmap = new Pixmap(
            1, 1, Pixmap.Format.RGBA8888
        );

        pixmap.setColor(1, 1, 1, 1);
        pixmap.fill();

        pixel = new Texture(pixmap);
        pixmap.dispose();


        botonContinuar = new Rectangle(
            235, 215, 330, 55
        );

        botonMenu = new Rectangle(
            235, 135, 330, 55
        );
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        batch.setColor(1, 1, 1, 1);

        batch.draw(
            fondoPausa,
            0, 0, 800, 480
        );

        // Panel oscuro semitransparente.
        batch.setColor(0.02f, 0.04f, 0.10f, 0.82f);

        batch.draw(
            pixel,
            130, 85, 540, 310
        );

        batch.setColor(1, 1, 1, 1);

        font.getData().setScale(2f);
        font.setColor(1f, 0.82f, 0.35f, 1f);

        font.draw(
            batch,
            "JUEGO EN PAUSA",
            0, 350,
            800, Align.center, false
        );

        font.getData().setScale(1f);
        font.setColor(1, 1, 1, 1);

        font.draw(
            batch,
            "El huaso necesita un descansito!",
            0, 305,
            800, Align.center, false
        );

        batch.setColor(0.12f, 0.40f, 0.28f, 1f);

        batch.draw(
            pixel,
            botonContinuar.x,
            botonContinuar.y,
            botonContinuar.width,
            botonContinuar.height
        );

        batch.setColor(1, 1, 1, 1);

        font.getData().setScale(1.4f);
        font.setColor(1, 1, 1, 1);

        font.draw(
            batch,
            "CONTINUAR PARTIDA",
            botonContinuar.x,
            botonContinuar.y + 35,
            botonContinuar.width,
            Align.center,
            false
        );

        batch.setColor(0.55f, 0.19f, 0.14f, 1f);

        batch.draw(
            pixel,
            botonMenu.x,
            botonMenu.y,
            botonMenu.width,
            botonMenu.height
        );

        batch.setColor(1, 1, 1, 1);

        font.draw(
            batch,
            "VOLVER AL MENU",
            botonMenu.x,
            botonMenu.y + 35,
            botonMenu.width,
            Align.center,
            false
        );

        font.getData().setScale(0.9f);
        font.setColor(1f, 0.82f, 0.35f, 1f);

        font.draw(
            batch,
            "Presiona ESC para continuar",
            0, 108,
            800, Align.center, false
        );

        font.getData().setScale(1f);
        font.setColor(1, 1, 1, 1);
        batch.setColor(1, 1, 1, 1);

        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE))
        {
            continuarPartida();
            return;
        }

        if (Gdx.input.justTouched())
        {
            Vector3 posicionMouse = new Vector3(
                Gdx.input.getX(),
                Gdx.input.getY(),
                0
            );

            camera.unproject(posicionMouse);
            if (botonContinuar.contains(
                posicionMouse.x,
                posicionMouse.y))
            {
                continuarPartida();
                return;
            }

            if (botonMenu.contains(
                posicionMouse.x,
                posicionMouse.y))
            {
                volverAlMenu();
                return;
            }
        }
    }

   
    private void continuarPartida()
    {
        
        game.setScreen(partida);

        
        dispose();
    }

    private void volverAlMenu()
    {
        
        game.setScreen(new MainMenuScreen(game));

        
        partida.dispose();

        
        dispose();
    }

    @Override
    public void show()
    {
    }

    @Override
    public void resize(int width, int height)
    {
    }

    @Override
    public void pause()
    {
    }

    @Override
    public void resume()
    {
    }

    @Override
    public void hide()
    {
    }

    @Override
    public void dispose()
    {
        fondoPausa.dispose();
        pixel.dispose();

    }
}

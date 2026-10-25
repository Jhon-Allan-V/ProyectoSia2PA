
package puppy.code;

import com.badlogic.gdx.Gdx;
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

public class GameOverScreen implements Screen
{
    private final GameLluviaMenu game;

    private SpriteBatch batch;
    private BitmapFont font;
    private OrthographicCamera camera;

    private int puntajeFinal;

    private Texture fondo;
    private Texture pixel;

    private Rectangle botonReiniciar;
    private Rectangle botonMenu;

    public GameOverScreen(final GameLluviaMenu game, int puntajeFinal)
    {
        this.game = game;
        this.puntajeFinal = puntajeFinal;

        this.batch = game.getBatch();
        this.font = game.getFont();

       
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);

        
        fondo = new Texture(Gdx.files.internal("Fondo.png"));

        fondo.setFilter(
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

       
        botonReiniciar = new Rectangle(235, 170, 330, 52);
        botonMenu = new Rectangle(235, 100, 330, 52);
    }

    @Override
    public void render(float delta)
    {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

      

        batch.setColor(1, 1, 1, 1);
        batch.draw(fondo, 0, 0, 800, 480);

        // Panel oscuro semitransparente.
        batch.setColor(0.02f, 0.04f, 0.10f, 0.85f);
        batch.draw(pixel, 120, 65, 560, 350);

        batch.setColor(1, 1, 1, 1);

       

        font.getData().setScale(2f);
        font.setColor(1f, 0.82f, 0.35f, 1f);

        font.draw(
            batch,
            "SE ACABO EL 18!",
            0, 370,
            800, Align.center, false
        );

        font.getData().setScale(1f);
        font.setColor(1, 1, 1, 1);

        font.draw(
            batch,
            "El huaso tomo demasiados terremotos!",
            0, 330,
            800, Align.center, false
        );

       

        font.getData().setScale(1.2f);

        font.draw(
            batch,
            "Puntaje final: " + puntajeFinal,
            0, 290,
            800, Align.center, false
        );

        font.setColor(1f, 0.82f, 0.35f, 1f);

        font.draw(
            batch,
            "Record: " + game.getHigherScore(),
            0, 260,
            800, Align.center, false
        );

    

        batch.setColor(0.12f, 0.40f, 0.28f, 1f);

        batch.draw(
            pixel,
            botonReiniciar.x,
            botonReiniciar.y,
            botonReiniciar.width,
            botonReiniciar.height
        );

        batch.setColor(1, 1, 1, 1);
        font.setColor(1, 1, 1, 1);

        font.draw(
            batch,
            "VOLVER A JUGAR",
            botonReiniciar.x,
            botonReiniciar.y + 33,
            botonReiniciar.width,
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
            botonMenu.y + 33,
            botonMenu.width,
            Align.center,
            false
        );

       
        font.getData().setScale(1f);
        font.setColor(1, 1, 1, 1);
        batch.setColor(1, 1, 1, 1);

        batch.end();

       

        if (Gdx.input.justTouched())
        {
            
            Vector3 mouse = new Vector3(
                Gdx.input.getX(),
                Gdx.input.getY(),
                0
            );

            camera.unproject(mouse);

        
            if (botonReiniciar.contains(mouse.x, mouse.y))
            {
                game.setScreen(new GameScreen(game));
                dispose();
                return;
            }

           
            if (botonMenu.contains(mouse.x, mouse.y))
            {
                game.setScreen(new MainMenuScreen(game));
                dispose();
                return;
            }
        }
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
    
        fondo.dispose();
        pixel.dispose();


    }
}

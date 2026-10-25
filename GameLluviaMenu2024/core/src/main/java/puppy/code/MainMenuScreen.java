package puppy.code;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ScreenUtils;


public class MainMenuScreen implements Screen 
{

	final GameLluviaMenu game;
	
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;

	private Texture fondoMenu;

	private Texture panelMenu;

	public MainMenuScreen(final GameLluviaMenu game) 
	{
		this.game = game;
        
		this.batch = game.getBatch();
        this.font = game.getFont();
		
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

		// Nuevo fondo
		fondoMenu = new Texture(Gdx.files.internal("Fondo.png"));

		fondoMenu.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

		Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);

		pixmap.setColor(0.02f, 0.04f, 0.10f, 0.78f);
		pixmap.fill();

		panelMenu = new Texture(pixmap);

		pixmap.dispose();
		
	}

	@Override
	public void render(float delta) 
	{
		ScreenUtils.clear(0, 0, 0.2f, 1);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

		batch.draw(fondoMenu, 0, 0, 800, 480);
		batch.draw(panelMenu, 80, 74, 640, 330);

		font.getData().setScale(2.2f);
		font.setColor(1f, 0.82f, 0.35f, 1f);

		font.draw(batch, "SOBREVIVE AL 18", 0, 365, 800, Align.center, false);

		font.getData().setScale(1f);
        font.setColor(1f, 1f, 1f, 1f);

		font.draw(batch, "Come harto, pero cuidado con los terremotos!", 0, 325, 800, Align.center, false);

        font.draw(batch, "Flechas IZQUIERDA / DERECHA: Mover al huaso", 0, 288, 800, Align.center, false);

        font.draw(batch, "ESC: Pausar la partida", 0, 263,  800, Align.center, false);

        font.draw(batch, "Empanada: +10 puntos | Anticucho: +15 puntos", 0, 230, 800, Align.center, false);

        font.draw(batch, "Terremoto: -1 vida | Vidas iniciales: 10", 0, 205, 800, Align.center, false);

        font.setColor(1f, 0.82f, 0.35f, 1f);

        font.draw(batch, "Desde el quinto terremoto comienza el mareo!", 0, 176, 800, Align.center, false);


		
		font.getData().setScale(1.4f);
        font.setColor(1f, 1f, 1f, 1f);

        font.draw(batch, "CLIC O ENTER PARA COMENZAR", 0, 120, 800, Align.center, false);

        font.getData().setScale(1f);
        font.setColor(1f, 1f, 1f, 1f);

        batch.end();

        if (Gdx.input.justTouched() ||
            Gdx.input.isKeyJustPressed(Input.Keys.ENTER))
        {
            game.setScreen(new GameScreen(game));
            dispose();
        }
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() 
	{
		fondoMenu.dispose();

		panelMenu.dispose();
		
	}

}

package puppy.code;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

	public class GameLluviaMenu extends Game {

		private SpriteBatch batch;
		private BitmapFont font;
		private int higherScore;

		public void create() {
			batch = new SpriteBatch();
			font = new BitmapFont(); // use libGDX's default Arial font
			this.setScreen(new MainMenuScreen(this));
		}

		public void render() {
			super.render(); // important!
		}

		public void dispose() {
			batch.dispose();
			font.dispose();
		}

		public SpriteBatch getBatch() {
			return batch;
		}

		public BitmapFont getFont() {
			return font;
		}

		public int getHigherScore() {
			return higherScore;
		}

		public void setHigherScore(int nuevoPuntaje) 
		{
			// El record solo se actualiza si el puntaje supera el anterior
			 if (nuevoPuntaje > higherScore)
			{
				higherScore = nuevoPuntaje;
			}
		}
		

	}

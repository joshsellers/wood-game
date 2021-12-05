package com.jahbz.wood.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jahbz.wood.world.World;
import com.jahbz.wood.world.entities.TestMob;

import static com.jahbz.wood.core.Utility.random;

public class Main extends ApplicationAdapter {
	public static final String VERSION = "0.1";

	private static final float VIEWPORT_SCALE = 3;

	private SpriteBatch batch;

	private OrthographicCamera camera;
	private CameraController camControl;

	private BitmapFont font;

	private World world;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / VIEWPORT_SCALE, h / VIEWPORT_SCALE);

		camControl = new CameraController(camera);
		Gdx.input.setInputProcessor(camControl);

		font = new BitmapFont();
		batch = new SpriteBatch();

		world = new World(camera);
		int numTestEntities = 1;
		for (int i = 0; i < numTestEntities; i++) {
			int ww = World.MAP_WIDTH;
			int wh = World.MAP_HEIGHT;
			world.addEntity(new TestMob(random(0, ww) << SpriteSheet.TILE_SHIFT,
					random(0, wh << SpriteSheet.TILE_SHIFT), world));
			System.out.println(((float) i / numTestEntities) * 100 + "%");
		}
	}

	public void update() {
		camControl.update();
		world.update();
		camera.update();
	}

	@Override
	public void render() {
		update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.render();

		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),
				10, Gdx.graphics.getHeight() - 10);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}

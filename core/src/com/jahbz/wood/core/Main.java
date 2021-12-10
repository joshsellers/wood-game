package com.jahbz.wood.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jahbz.wood.resourcing.AssetCreator;
import com.jahbz.wood.resourcing.MobAssetData;
import com.jahbz.wood.resourcing.SpriteSheet;
import com.jahbz.wood.world.World;
import com.jahbz.wood.world.entities.TestMob;

import static com.jahbz.wood.core.Utility.random;

public class Main extends ApplicationAdapter {
	public static final String VERSION = "0.1.4";

	private static final float VIEWPORT_SCALE = 3;

	private static final boolean DEBUG = true;

	private SpriteBatch batch;

	private OrthographicCamera camera;
	private DebugControllerInterface camControl;

	private BitmapFont font;

	private World world;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / VIEWPORT_SCALE, h / VIEWPORT_SCALE);

		camControl = new DebugControllerInterface(camera);
		Gdx.input.setInputProcessor(camControl);

		font = new BitmapFont();
		//font.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
		batch = new SpriteBatch();

		world = new World(camera);

		AssetCreator.createAssets(MobAssetData.TEST_MOB);
		int numTestEntities = 25;
		long startTime = System.currentTimeMillis();
		long totalElapsedTime = 0;
		for (int i = 0; i < numTestEntities; i++) {
			int ww = World.MAP_WIDTH;
			int wh = World.MAP_HEIGHT;
			world.addEntity(new TestMob(random(0, ww) << SpriteSheet.TILE_SHIFT,
					random(0, wh) << SpriteSheet.TILE_SHIFT, world));
			if (DEBUG) {
				long stopTime = System.currentTimeMillis();
				System.out.printf("%.1f%%", (float) (i + 1) / numTestEntities * 100);

				long elapsedTime = stopTime - startTime;
				totalElapsedTime += elapsedTime;
				long avg = totalElapsedTime / (long) (i + 1);
				long remainingMillis = avg * (numTestEntities - (i + 1));
				System.out.println(" (estimated time remaining: " + (remainingMillis / 1000) + " seconds (about " +
						String.format("%.2f", remainingMillis / 60000f) + " minutes))");
				startTime = System.currentTimeMillis();
			}
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
		font.draw(batch, "v" + VERSION, 10, Gdx.graphics.getHeight() - 10);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),
				10, Gdx.graphics.getHeight() - 30);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}

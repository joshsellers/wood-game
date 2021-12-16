package com.jahbz.wood.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.jahbz.wood.resourcing.AssetCreator;
import com.jahbz.wood.resourcing.MobAssetData;
import com.jahbz.wood.resourcing.SpriteSheet;
import com.jahbz.wood.ui.UIController;
import com.jahbz.wood.ui.UIHandler;
import com.jahbz.wood.ui.UIProfile;
import com.jahbz.wood.world.World;
import com.jahbz.wood.world.entities.TestMob;

import static com.jahbz.wood.core.Utility.random;

public class Main extends ApplicationAdapter {
	public static final String VERSION = "0.1.6";

	private static final float VIEWPORT_SCALE = 3;

	private static final boolean DEBUG = false;

	public static int WIDTH;
	public static int HEIGHT;
	public static boolean FULLSCREEN = false;

	private SpriteBatch batch;

	private OrthographicCamera camera;
	private DebugControllerInterface camControl;

	private BitmapFont font;

	private World world;
	
	private UIHandler ui;
	private UIController uiControl;

	@Override
	public void create() {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / VIEWPORT_SCALE, h / VIEWPORT_SCALE);

		InputMultiplexer multiIn = new InputMultiplexer();

		camControl = new DebugControllerInterface(camera);
		multiIn.addProcessor(camControl);

		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HATTEN.TTF"));
		FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
		parameter.size = 24;
		font = generator.generateFont(parameter);
		generator.dispose();

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
		
		ui = new UIHandler();
		uiControl = new UIController(ui);
		multiIn.addProcessor(uiControl);

		UIProfile testProfile = new UIProfile("test2", ui);
		ui.addProfile(testProfile);
		UIProfile pauseMenuProfile = new UIProfile("pausemenu", ui);
		ui.addProfile(pauseMenuProfile);



		Gdx.input.setInputProcessor(multiIn);
	}

	public void update() {
		camControl.update();
		uiControl.update();
		world.update();
		camera.update();
	}

	@Override
	public void render() {
		update();

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		world.render();
		ui.render();

		batch.begin();
		font.draw(batch, "v" + VERSION, 10, 50);
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(),
				10, 25);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}

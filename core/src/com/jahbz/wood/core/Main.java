package com.jahbz.wood.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jahbz.wood.world.World;
import com.jahbz.wood.world.entities.TestEntity;

public class Main extends ApplicationAdapter {
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
		camera.setToOrtho(false, w / 3, h / 3);

		camControl = new CameraController(camera);
		Gdx.input.setInputProcessor(camControl);

		font = new BitmapFont();
		batch = new SpriteBatch();

		world = new World(camera);
		world.addEntity(new TestEntity(50, 50, world));
		world.addEntity(new TestEntity(10, 100, world));
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camControl.update();
		world.update();
		camera.update();

		world.render();

		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}

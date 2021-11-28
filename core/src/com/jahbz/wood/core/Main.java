package com.jahbz.wood.core;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.jahbz.wood.world.TileType;

public class Main extends ApplicationAdapter {
	private SpriteBatch batch;

	public static final int MAP_WIDTH = 40;
	public static final int MAP_HEIGHT = 25;

	private TiledMap map;
	private TiledMapRenderer renderer;

	private OrthographicCamera camera;
	private CameraController camControl;

	private BitmapFont font;

	@Override
	public void create() {
		batch = new SpriteBatch();

		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		camera = new OrthographicCamera();
		camera.setToOrtho(false, w / 3, h / 3);

		camControl = new CameraController(camera);
		Gdx.input.setInputProcessor(camControl);

		font = new BitmapFont();
		batch = new SpriteBatch();

		map = new TiledMap();
		MapLayers layers = map.getLayers();
		for (int l = 0; l < 1; l++) {
			TiledMapTileLayer layer = new TiledMapTileLayer(40, 25, TileType.TILE_SIZE,
					TileType.TILE_SIZE);
			for (int x = 0; x < layer.getWidth(); x++) {
				for (int y = 0; y < layer.getHeight(); y++) {
					int id = (int) (Math.random() * 5) + 1;

					TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
					cell.setTile(TileType.TILE_TYPES[id]);
					layer.setCell(x, y, cell);
				}
			}
			layers.add(layer);
		}

		renderer = new OrthogonalTiledMapRenderer(map);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camControl.update();
		camera.update();
		renderer.setView(camera);
		renderer.render();
		batch.begin();
		font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
		batch.end();
	}
	
	@Override
	public void dispose() {
		batch.dispose();
	}
}

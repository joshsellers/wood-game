package com.jahbz.wood.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jahbz.wood.world.entities.Entity;
import com.jahbz.wood.world.tiles.TileType;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static final int MAP_WIDTH = 40;
    public static final int MAP_HEIGHT = 25;

    private TiledMapRenderer renderer;

    private final List<Entity> entities = new ArrayList<>();

    private final OrthographicCamera camera;
    private final SpriteBatch batch;

    public World(OrthographicCamera camera) {
        createMap();

        this.camera = camera;
        batch = new SpriteBatch();
    }

    public void update() {
        for (Entity e : getEntities()) {
            if (e.isActive())
                e.update();
        }
    }

    public void render() {
        renderer.setView(camera);
        renderer.render();

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (Entity e : getEntities()) {
            if (e.isActive())
                e.render(batch);
        }
        batch.end();
    }

    private void createMap() {
        TiledMap map = new TiledMap();
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

    public synchronized List<Entity> getEntities() {
        return entities;
    }

    public synchronized void addEntity(Entity e) {
        getEntities().add(e);
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}

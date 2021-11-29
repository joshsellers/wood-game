package com.jahbz.wood.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jahbz.wood.core.SpriteSheet;
import com.jahbz.wood.world.entities.Entity;
import com.jahbz.wood.world.tiles.TileType;

import java.util.ArrayList;
import java.util.List;

public class World {
    public static final int MAP_WIDTH = 40;
    public static final int MAP_HEIGHT = 25;

    public static final int TILE_SHIFT = 4;

    private TiledMap map;
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
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        for (int l = 0; l < 1; l++) {
            TiledMapTileLayer layer = new TiledMapTileLayer(40, 25, SpriteSheet.TILE_SIZE,
                    SpriteSheet.TILE_SIZE);
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

        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(TileType.VOID);
        ((TiledMapTileLayer) map.getLayers().get(0)).setCell(30, 3, cell);

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public synchronized List<Entity> getEntities() {
        return entities;
    }

    public synchronized void addEntity(Entity e) {
        getEntities().add(e);
    }

    public TileType getTile(int x, int y) {
        if (x < MAP_WIDTH && x >= 0 && y < MAP_HEIGHT && y >= 0)
            return (TileType) ((TiledMapTileLayer) map.getLayers().get(0)).getCell(x, y).getTile();
        return TileType.VOID;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}

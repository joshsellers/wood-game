package com.jahbz.wood.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.jahbz.wood.resourcing.SpriteSheet;
import com.jahbz.wood.world.entities.Entity;
import com.jahbz.wood.world.tiles.TileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.jahbz.wood.core.Utility.random;

public class World {
    public static final int MAP_WIDTH = 50; //32
    public static final int MAP_HEIGHT = 50; //18

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

        try {
            Collections.sort(getEntities());
        } catch (Exception e) {
            Gdx.app.log("World", e.getMessage());
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
            TiledMapTileLayer layer = new TiledMapTileLayer(MAP_WIDTH, MAP_HEIGHT, SpriteSheet.TILE_SIZE,
                    SpriteSheet.TILE_SIZE);
            for (int x = 0; x < layer.getWidth(); x++) {
                for (int y = 0; y < layer.getHeight(); y++) {
                    int id = random(TileType.GRASS_1.getId(), TileType.GRASS_5.getId());

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

    public TileType getTile(int x, int y) {
        if (x < MAP_WIDTH && x >= 0 && y < MAP_HEIGHT && y >= 0)
            return (TileType) ((TiledMapTileLayer) map.getLayers().get(0)).getCell(x, y).getTile();
        return TileType.VOID;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }
}

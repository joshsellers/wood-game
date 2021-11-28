package com.jahbz.wood.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;

public class TileType extends StaticTiledMapTile {
    public final static int NUM_TYPE_TYPES = 6;
    public final static TileType[] TILE_TYPES = new TileType[NUM_TYPE_TYPES];

    public final static int TILE_SIZE = 16;

    private final static Texture RAW_TILE_SHEET = new Texture("sprite_sheet.png");
    private final static TextureRegion[][] TILE_SHEET = TextureRegion.split(RAW_TILE_SHEET,
            TILE_SIZE, TILE_SIZE);

    // Define tile types
    public final static TileType VOID    = new TileType(0, "Void",  TILE_SHEET[0][0]);
    public final static TileType GRASS_1 = new TileType(1, "Grass", TILE_SHEET[0][1]);
    public final static TileType GRASS_2 = new TileType(2, "Grass", TILE_SHEET[3][1]);
    public final static TileType GRASS_3 = new TileType(3, "Grass", TILE_SHEET[3][2]);
    public final static TileType GRASS_4 = new TileType(4, "Grass", TILE_SHEET[4][1]);
    public final static TileType GRASS_5 = new TileType(5, "Grass", TILE_SHEET[4][2]);

    private String name;

    public TileType(int id, String name, TextureRegion textureRegion) {
        super(textureRegion);
        setId(id);
        if (TILE_TYPES[id] != null)
            throw new RuntimeException("Duplicate tile id at " + id);
        TILE_TYPES[id] = this;
    }

    public String getName() {
        return name;
    }
}

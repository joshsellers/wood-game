package com.jahbz.wood.world.tiles;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.jahbz.wood.core.SpriteSheet;

public class TileType extends StaticTiledMapTile {
    public final static int NUM_TYPE_TYPES = 6;
    public final static TileType[] TILE_TYPES = new TileType[NUM_TYPE_TYPES];

    // Define tile types
    public final static TileType VOID    = new TileType(0, "Void",  true,
            SpriteSheet.SPRITE_SHEET[0][0]);
    public final static TileType GRASS_1 = new TileType(1, "Grass", false,
            SpriteSheet.SPRITE_SHEET[0][1]);
    public final static TileType GRASS_2 = new TileType(2, "Grass", false,
            SpriteSheet.SPRITE_SHEET[3][1]);
    public final static TileType GRASS_3 = new TileType(3, "Grass", false,
            SpriteSheet.SPRITE_SHEET[3][2]);
    public final static TileType GRASS_4 = new TileType(4, "Grass", false,
            SpriteSheet.SPRITE_SHEET[4][1]);
    public final static TileType GRASS_5 = new TileType(5, "Grass", false,
            SpriteSheet.SPRITE_SHEET[4][2]);

    private final String name;
    private final boolean solid;

    public TileType(int id, String name, boolean solid, TextureRegion textureRegion) {
        super(textureRegion);
        setId(id);
        if (TILE_TYPES[id] != null)
            throw new RuntimeException("Duplicate tile id at " + id);
        TILE_TYPES[id] = this;

        this.name = name;
        this.solid = solid;
    }

    public String getName() {
        return name;
    }

    public boolean isSolid() { return solid; }
}

package com.jahbz.wood.core;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheet {
    public final static int TILE_SIZE = 16;

    private final static Texture RAW_SPRITE_SHEET = new Texture("sprite_sheet.png");
    public final static TextureRegion[][] SPRITE_SHEET = TextureRegion.split(RAW_SPRITE_SHEET,
            TILE_SIZE, TILE_SIZE);
}

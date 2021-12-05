package com.jahbz.wood.core;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class SpriteSheet {
    public final static int TILE_SIZE = 16;
    public final static int TILE_SHIFT = 4;

    private final static Texture RAW_SPRITE_SHEET = new Texture("sprite_sheet.png");
    public final static TextureRegion[][] SPRITE_SHEET = TextureRegion.split(RAW_SPRITE_SHEET,
            TILE_SIZE, TILE_SIZE);

    public static Texture createTextureFromTiles(int tileX, int tileY, int width, int height) {
        Pixmap finalPixmap = new Pixmap(width * SpriteSheet.TILE_SIZE, height * SpriteSheet.TILE_SIZE,
                Pixmap.Format.RGBA8888);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                TextureRegion region = SpriteSheet.SPRITE_SHEET[tileY + y][tileX + x];
                Texture tile = region.getTexture();
                tile.getTextureData().prepare();
                Pixmap pixmap = tile.getTextureData().consumePixmap();
                finalPixmap.drawPixmap(pixmap,
                        x * SpriteSheet.TILE_SIZE, y * SpriteSheet.TILE_SIZE,
                        region.getRegionX(), region.getRegionY(),
                        region.getRegionWidth(), region.getRegionHeight());

                pixmap.dispose();
            }
        }

        Texture texture = new Texture(finalPixmap);
        finalPixmap.dispose();
        return texture;
    }
}

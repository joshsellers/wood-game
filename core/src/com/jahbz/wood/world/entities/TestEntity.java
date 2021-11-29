package com.jahbz.wood.world.entities;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jahbz.wood.core.SpriteSheet;
import com.jahbz.wood.world.World;

public class TestEntity extends Entity {
    private final Texture texture;

    public TestEntity(int x, int y, World world) {
        super(0x00, x, y, 16, 32, world);
        Pixmap pm = new Pixmap(width, height, Pixmap.Format.RGBA8888);

        TextureRegion topRegion = SpriteSheet.SPRITE_SHEET[25][9];
        Texture topTile = topRegion.getTexture();
        topTile.getTextureData().prepare();
        Pixmap top = topTile.getTextureData().consumePixmap();

        TextureRegion bottomRegion = SpriteSheet.SPRITE_SHEET[26][9];
        Texture bottomTile = bottomRegion.getTexture();
        bottomTile.getTextureData().prepare();
        Pixmap bottom = bottomTile.getTextureData().consumePixmap();

        pm.drawPixmap(top, 0, 0, topRegion.getRegionX(), topRegion.getRegionY(),
                topRegion.getRegionWidth(), topRegion.getRegionHeight());
        pm.drawPixmap(bottom, 0, height / 2, bottomRegion.getRegionX(), bottomRegion.getRegionY(),
                bottomRegion.getRegionWidth(), bottomRegion.getRegionHeight());
        texture = new Texture(pm);

        pm.dispose();
        top.dispose();
        bottom.dispose();
    }


    @Override
    protected void tick() {
        x += 0.5;
        System.out.println(world.getTile((int) (x + width) >> 4, (int) y >> 4).getName());
    }

    @Override
    protected void draw(SpriteBatch batch) {
        batch.draw(texture, x, y);
    }

    @Override
    public void dispose() {
        texture.dispose();
    }
}

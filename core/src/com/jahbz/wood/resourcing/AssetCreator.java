package com.jahbz.wood.resourcing;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jahbz.wood.world.entities.Mob;

public class AssetCreator {

    public static void createAssets(MobAssetData data) {
        MobAssetCapsule capsule = new MobAssetCapsule();
        for (int i = 0; i <= Mob.RIGHT; i++) {
            capsule.standingTextures[i] = createTextureFromTiles(
                    data.getXTile() + i, data.getYTile(), data.getWidth(), data.getHeight()
            );
        }

        Texture[][] movingFrames = new Texture[Mob.RIGHT + 1][data.getMovingAnimationLength()];
        for (int i = 0; i < movingFrames.length; i++) {
            for (int j = 0; j < data.getMovingAnimationLength(); j++) {
                movingFrames[i][j] = createTextureFromTiles(
                        (data.getMovingAnimationXTile() + j) + data.getMovingAnimationLength() * i,
                        data.getMovingAnimationYTile(),
                        data.getWidth(), data.getHeight()
                );
            }
        }

        capsule.movingAnimation = new Animation[] {
                new Animation<>(data.getFrameInterval(), movingFrames[Mob.UP]),
                new Animation<>(data.getFrameInterval(), movingFrames[Mob.DOWN]),
                new Animation<>(data.getFrameInterval(), movingFrames[Mob.LEFT]),
                new Animation<>(data.getFrameInterval(), movingFrames[Mob.RIGHT])
        };

        data.setAssetCapsule(capsule);
    }

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

    @SuppressWarnings("rawtypes")
    public static class MobAssetCapsule {
        private final Texture[] standingTextures = new Texture[Mob.RIGHT + 1];

        private Animation[] movingAnimation;

        public Texture[] getStandingTextures() {
            return standingTextures;
        }

        public Animation[] getMovingAnimation() {
            return movingAnimation;
        }
    }
}

package com.jahbz.wood.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jahbz.wood.core.SpriteSheet;
import com.jahbz.wood.world.World;

public abstract class AnimatedMob extends Mob {

    protected Texture[] standingTextures = new Texture[RIGHT + 1];
    @SuppressWarnings("rawtypes")
    protected Animation[] movingAnimation;
    private float stateTime;

    public AnimatedMob(int id, float x, float y, int width, int height, float maxHitPoints,
                       int tileWidth, int tileHeight, int standingFramesXTile, int standingFramesYTile,
                       int movingFramesXTile, int movingFramesYTile, int movingFramesLength,
                       float frameInterval, World world) {
        super(id, x, y, width, height, maxHitPoints, world);

        for (int i = 0; i <= RIGHT; i++) {
            standingTextures[i] = SpriteSheet.createTextureFromTiles(
                    standingFramesXTile + i, standingFramesYTile, tileWidth, tileHeight
            );
        }

        Texture[][] movingFrames = new Texture[RIGHT + 1][movingFramesLength];
        for (int i = 0; i < movingFrames.length; i++) {
            for (int j = 0; j < movingFramesLength; j++) {
                movingFrames[i][j] = SpriteSheet.createTextureFromTiles(
                        (movingFramesXTile + j) + movingFramesLength * i, movingFramesYTile,
                        tileWidth, tileHeight
                );
            }
        }

        movingAnimation = new Animation[] {
                new Animation<>(frameInterval, movingFrames[UP]),
                new Animation<>(frameInterval, movingFrames[DOWN]),
                new Animation<>(frameInterval, movingFrames[LEFT]),
                new Animation<>(frameInterval, movingFrames[RIGHT])
        };
    }

    @Override
    public void render(SpriteBatch batch) {
        stateTime += Gdx.graphics.getDeltaTime();

        Texture currentFrame;
        if (isMoving())
            currentFrame = (Texture) movingAnimation[getMovingDir()].getKeyFrame(stateTime, true);
        else
            currentFrame = standingTextures[getMovingDir()];

        if (currentFrame != null) batch.draw(currentFrame, x, y);
        else Gdx.app.log("AnimatedMob(" + getId() + ")", "currentFrame is null");

        draw(batch);
    }

}

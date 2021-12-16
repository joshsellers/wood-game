package com.jahbz.wood.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jahbz.wood.resourcing.AssetCreator;
import com.jahbz.wood.resourcing.MobAssetData;
import com.jahbz.wood.world.World;

public abstract class AnimatedMob extends Mob {

    protected Texture[] standingTextures;
    @SuppressWarnings("rawtypes")
    protected Animation[] movingAnimation;
    private float stateTime;

    public AnimatedMob(int id, float x, float y, int width, int height, float maxHitPoints, World world) {
        super(id, x, y, width, height, maxHitPoints, world);

        AssetCreator.MobAssetCapsule assetCapsule = MobAssetData.getAssetCapsule(getId());
        standingTextures = assetCapsule.getStandingTextures();
        movingAnimation = assetCapsule.getMovingAnimation();
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

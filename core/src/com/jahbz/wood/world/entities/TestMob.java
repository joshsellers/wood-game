package com.jahbz.wood.world.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jahbz.wood.resourcing.MobAssetData;
import com.jahbz.wood.world.World;

import static com.jahbz.wood.core.Utility.random;

public class TestMob extends AnimatedMob {

    public TestMob(float x, float y, World world) {
        super(0x00, x, y, 16, 32, 50,world);
        movementSpeed = 2;
    }

    @Override
    protected void tick() {
        if (random(0, 50) == 0 && !isMoving()) move(random(UP, RIGHT),  random(1, 32));
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void dispose() {

    }

    @Override
    protected void onDamageTaken(float amt, Mob source) {

    }

    @Override
    protected void onDie(Mob source) {

    }

    @Override
    public int compareTo(Entity e) {
        return e.getY() > getY() ? 1 : -1;
    }
}

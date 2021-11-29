package com.jahbz.wood.world.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jahbz.wood.world.World;

public abstract class Mob extends Entity {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    protected float maxHitPoints;
    protected float hitPoints;

    protected float movementSpeed = 1;
    private int movingDir = 1;
    protected boolean moving;

    private float targetX;
    private float targetY;

    public Mob(int id, float x, float y, int width, int height, float maxHitPoints, World world) {
        super(id, x, y, width, height, world);

        targetX = this.x;
        targetY = this.y;

        this.maxHitPoints = maxHitPoints;
        hitPoints = this.maxHitPoints;
    }

    @Override
    public void update() {
        move();
        bounds.setPosition(x, y);
    }

    private void move() {

    }

    protected void move(int dir) {
        int delta = dir == UP || dir == RIGHT ? 1 : -1;

        if (dir < 2)
            targetY = (((int) y >> World.TILE_SHIFT) + delta) << World.TILE_SHIFT;
        else
            targetX = (((int) x >> World.TILE_SHIFT) + delta) << World.TILE_SHIFT;

        if (world.getTile((int) targetX >> World.TILE_SHIFT, (int) targetY >> World.TILE_SHIFT).isSolid()) {
            targetX = x;
            targetY = y;
        }

        movingDir = dir;
    }

    @Override
    public void render(SpriteBatch batch) {
        // Maybe do animation stuff in yet another abstract subclass? (i.e. dont override here)
    }

    public void damage(float amt, Mob source) {
        if (amt > 0)
            hitPoints = Math.max(hitPoints - amt, 0);
        else {
            Gdx.app.log("Mob (" + getId() + ")", "HP damage must be greater than zero. Source ID: " +
                    source.getId() + ", damage value: " + amt);
            return;
        }

        if (hitPoints == 0)
            die(source);
        else
            onDamageTaken(amt, source);
    }

    protected abstract void onDamageTaken(float amt, Mob source);

    public void die(Mob source) {
        deactivate();
        onDie(source);
    }

    protected abstract void onDie(Mob source);

    public float getHitPoints() {
        return hitPoints;
    }

    public float getMaxHitPoints() {
        return maxHitPoints;
    }

    public boolean isMoving() {
        return moving;
    }

    public int getMovingDir() {
        return movingDir;
    }

    protected float getTargetX() {
        return targetX;
    }

    protected float getTargetY() {
        return targetY;
    }
}

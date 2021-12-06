package com.jahbz.wood.world.entities;

import com.badlogic.gdx.Gdx;
import com.jahbz.wood.resourcing.SpriteSheet;
import com.jahbz.wood.world.World;

public abstract class Mob extends Entity {

    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;

    protected float maxHitPoints;
    protected float hitPoints;

    private static final float DEFAULT_SPEED = 0.25f;
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
        tick();
        //noinspection AssignmentUsedAsCondition
        if (moving = x != targetX || y != targetY) move();
        bounds.setPosition(x, y);
    }

    private void move() {
        float newY;
        float newX;
        switch (movingDir) {
            case UP:
                newY = y + DEFAULT_SPEED * movementSpeed;
                y = Math.min(newY, targetY);
                break;
            case DOWN:
                newY = y - DEFAULT_SPEED * movementSpeed;
                y = Math.max(newY, targetY);
                break;
            case RIGHT:
                newX = x + DEFAULT_SPEED * movementSpeed;
                x = Math.min(newX, targetX);
                break;
            case LEFT:
                newX = x - DEFAULT_SPEED * movementSpeed;
                x = Math.max(newX, targetX);
                break;
        }
    }

    protected void move(int dir) {
        move(dir, 1);
    }

    protected void move(int dir, int distance) {
        int delta = dir == UP || dir == RIGHT ? distance : -distance;

        if (dir < 2)
            targetY = (((int) y >> SpriteSheet.TILE_SHIFT) + delta) << SpriteSheet.TILE_SHIFT;
        else
            targetX = (((int) x >> SpriteSheet.TILE_SHIFT) + delta) << SpriteSheet.TILE_SHIFT;

        if (world.getTile((int) targetX >> SpriteSheet.TILE_SHIFT, (int) targetY >> SpriteSheet.TILE_SHIFT).isSolid()) {
            targetX = x;
            targetY = y;
        }

        movingDir = dir;
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

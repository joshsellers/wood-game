package com.jahbz.wood.world.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.jahbz.wood.world.World;

public abstract class Entity {
    protected float x, y;
    protected int width, height;

    protected Rectangle bounds;

    protected boolean active;

    private final int id;

    protected World world;

    public Entity(int id, float x, float y, int width, int height, World world) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.world = world;

        bounds = new Rectangle(x, y, width, height);

        active = true;
    }

    public void update() {
        bounds.setPosition(x, y);
        tick();
    }
    protected abstract void tick();

    public void render(SpriteBatch batch) {

        draw(batch);
    }
    protected abstract void draw(SpriteBatch batch);

    public abstract void dispose();

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
    }

    public void activate() {
        active = true;
    }

    public void deactivate() {
        active = false;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

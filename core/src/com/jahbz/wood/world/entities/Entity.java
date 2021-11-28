package com.jahbz.wood.world.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class Entity {
    protected int x, y;
    protected int width, height;

    protected boolean active;

    private final int id;

    public Entity(int id, int x, int y, int width, int height) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        active = true;
    }

    public void update() {

        tick();
    }
    protected abstract void tick();

    public void render(SpriteBatch batch) {

        draw(batch);
    }
    protected abstract void draw(SpriteBatch batch);

    public abstract void dispose();

    public int getX() {
        return x;
    }

    public int getY() {
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
}

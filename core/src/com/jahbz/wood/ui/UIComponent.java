package com.jahbz.wood.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class UIComponent {
    private boolean visible;

    private final boolean selectable;
    private boolean selected;
    private final int selectionIndex;

    protected float x, y;
    protected float width, height;

    private final String id;

    private final UIProfile profile;

    private final Rectangle bounds;

    protected Texture primaryTexture;
    protected Texture selectedTexture;

    public UIComponent(String id, float x, float y, float width, float height, boolean selectable, int selectionIndex,
                       UIProfile profile) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.profile = profile;
        this.selectable = selectable;
        this.selectionIndex = selectionIndex;

        bounds = new Rectangle(x, y, width, height);

        Texture[] textures = createTextures();
        primaryTexture = textures[0];
        selectedTexture = textures[1];
    }

    public abstract Texture[] createTextures();

    public abstract void draw(SpriteBatch batch);

    public void select() {
        if (isSelectable()) {
            selected = true;
            onSelect();
        }
    }

    public void deselect() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

    public boolean isSelectable() {
        return selectable;
    }

    public int getSelectionIndex() {
        return selectionIndex;
    }

    public void show() {
        visible = true;
    }

    public void hide() {
        visible = false;
    }

    public boolean isVisible() {
        return visible;
    }

    protected abstract void onSelect();

    public abstract void onCursorDown();

    public abstract void onCursorUp();

    public String getId() {
        return id;
    }

    public UIProfile getProfile() {
        return profile;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Rectangle getBounds() {
        return bounds;
    }
}

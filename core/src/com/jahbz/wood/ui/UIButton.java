package com.jahbz.wood.ui;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIButton extends UIComponent {

    private final static int BUTTON_PADDING = 5;

    private final String label;
    private final boolean toggleable;

    private boolean toggled;

    private Texture pressedTexture;
    private Texture onTexture;
    private Texture offTexture;

    private boolean isPressed = false;

    public UIButton(String id, String label, boolean toggleable, boolean defaultToggleState, float x, float y,
                    float width, float height, int selectionIndex, UIProfile profile) {
        super(id, x, y, width, height, true, selectionIndex, profile);
        this.label = label;
        this.toggleable = toggleable;
        toggled = defaultToggleState;
    }

    @Override
    public Texture[] createTextures() {
        Pixmap primaryPixmap = new Pixmap((int) getWidth(), (int) getHeight(),
                Pixmap.Format.RGBA8888);
        primaryPixmap.setColor(0x000099FF);
        primaryPixmap.fillRectangle(0, 0, primaryPixmap.getWidth(), primaryPixmap.getHeight());

        Pixmap selectedPixmap = new Pixmap((int) getWidth(), (int) getHeight(),
                Pixmap.Format.RGBA8888);
        selectedPixmap.setColor(0x0000FFFF);
        selectedPixmap.fillRectangle(0, 0, primaryPixmap.getWidth(), primaryPixmap.getHeight());
        selectedPixmap.setColor(0x000099FF);
        selectedPixmap.fillRectangle(BUTTON_PADDING, BUTTON_PADDING,
                selectedPixmap.getWidth() - BUTTON_PADDING * 2,
                selectedPixmap.getHeight() - BUTTON_PADDING * 2);

        Pixmap pressedPixmap = new Pixmap((int) getWidth(), (int) getHeight(),
                Pixmap.Format.RGBA8888);
        pressedPixmap.setColor(0x0000FFFF);
        pressedPixmap.fillRectangle(0, 0, primaryPixmap.getWidth(), primaryPixmap.getHeight());

        Pixmap offPixmap = new Pixmap((int) getHeight() / 2, (int) getHeight() / 2,
                Pixmap.Format.RGBA8888);
        offPixmap.setColor(0x000099FF);
        offPixmap.fillCircle(offPixmap.getWidth() / 2, offPixmap.getHeight() / 2,
                offPixmap.getWidth() / 2 + 2);
        offPixmap.setColor(0x880000FF);
        offPixmap.fillCircle(offPixmap.getWidth() / 2, offPixmap.getHeight() / 2,
                offPixmap.getWidth() / 2 - 2);
        offPixmap.setColor(0xBB0000FF);
        offPixmap.fillCircle(offPixmap.getWidth() / 2, offPixmap.getHeight() / 2,
                offPixmap.getWidth() / 2 - 5);

        Pixmap onPixmap = new Pixmap((int) getHeight() / 2, (int) getHeight() / 2,
                Pixmap.Format.RGBA8888);
        onPixmap.setColor(0x000099FF);
        onPixmap.fillCircle(onPixmap.getWidth() / 2, onPixmap.getHeight() / 2,
                onPixmap.getWidth() / 2 + 2);
        onPixmap.setColor(0x889900FF);
        onPixmap.fillCircle(onPixmap.getWidth() / 2, onPixmap.getHeight() / 2,
                onPixmap.getWidth() / 2 - 2);
        onPixmap.setColor(0x77FF00FF);
        onPixmap.fillCircle(onPixmap.getWidth() / 2, onPixmap.getHeight() / 2,
                onPixmap.getWidth() / 2 - 5);

        Texture[] textures = new Texture[] {new Texture(primaryPixmap), new Texture(selectedPixmap)};
        pressedTexture = new Texture(pressedPixmap);
        onTexture = new Texture(onPixmap);
        offTexture = new Texture(offPixmap);
        primaryPixmap.dispose();
        selectedPixmap.dispose();
        pressedPixmap.dispose();
        onPixmap.dispose();
        offPixmap.dispose();

        return textures;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(currentTexture, x, y);
        if (isToggleable()) batch.draw(isToggled() ? onTexture : offTexture, x + getWidth() + 5,
                y + (getHeight() / 2) - (float)  (onTexture.getHeight() / 2));
        getProfile().getHandler().getButtonFont().draw(batch, label, x + 10, y + 26);
    }

    @Override
    protected void onSelect() {
        currentTexture = selectedTexture;
    }

    @Override
    protected void onDeselect() {
        currentTexture = primaryTexture;
    }

    @Override
    public void onCursorDown() {
        isPressed = true;
        currentTexture = pressedTexture;
    }

    @Override
    public void onCursorUp() {
        isPressed = false;
        if (isToggleable()) toggled = !toggled;
        getProfile().getHandler().buttonPressed(this);
        currentTexture = selectedTexture;
    }

    public boolean isToggleable() {
        return toggleable;
    }

    public boolean isToggled() {
        return toggled;
    }
}

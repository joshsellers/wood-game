package com.jahbz.wood.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIButton extends UIComponent {

    private final String label;
    private final boolean toggleable;

    private boolean toggled;

    private Texture pressedTexture;

    private boolean isPressed = false;

    public UIButton(String id, String label, boolean toggleable, float x, float y, float width, float height,
                    int selectionIndex, UIProfile profile) {
        super(id, x, y, width, height, true, selectionIndex, profile);
        this.label = label;
        this.toggleable = toggleable;
    }

    @Override
    public Texture[] createTextures() {
        return null;
    }

    @Override
    public void draw(SpriteBatch batch) {

    }

    @Override
    protected void onSelect() {

    }

    @Override
    public void onCursorDown() {
        isPressed = true;
    }

    @Override
    public void onCursorUp() {
        isPressed = false;
        getProfile().getHandler().buttonPressed(getId());
    }

    public boolean isToggleable() {
        return toggleable;
    }

    public boolean isToggled() {
        return toggled;
    }
}

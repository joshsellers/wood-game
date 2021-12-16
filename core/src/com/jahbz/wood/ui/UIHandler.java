package com.jahbz.wood.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.jahbz.wood.core.Main;

import java.util.Hashtable;
import java.util.Set;

import static com.jahbz.wood.ui.UIController.*;

public class UIHandler {

    private final Hashtable<String, UIProfile> profiles = new Hashtable<>();

    private final SpriteBatch batch;

    private final BitmapFont buttonFont;

    private String currentProfileHandle = "NONE";

    private int mx, my;

    protected UIController controller;

    protected GlyphLayout fontLayout = new GlyphLayout();

    public UIHandler() {
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HATTEN.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 14;
        parameter.color = new Color(0xFF8000FF);
        buttonFont = generator.generateFont(parameter);
        generator.dispose();
    }
    
    public void render() {
        batch.begin();
        if (!currentProfileHandle.equalsIgnoreCase("NONE")) {
            for (UIComponent component : getCurrentProfile().getComponents()) {
                if (component.isVisible()) {
                    if (!Gdx.input.isCursorCatched()) {
                        if (component.getBounds().contains(mx, my) && !component.isSelected())
                            getCurrentProfile().setCurrentSelectionIndex(component.getSelectionIndex());
                        else if (!component.getBounds().contains(mx, my) && component.isSelected())
                            component.deselect();
                    }
                    component.draw(batch);
                }
            }
        }
        batch.end();
    }

    public void buttonPressed(UIButton button) {
        String buttonId = button.getId();
        switch (buttonId) {
            case "exit":
                Gdx.app.exit();
                break;
            case "togglefullscreen":
                Main.FULLSCREEN = Gdx.graphics.isFullscreen();
                Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
                if (Main.FULLSCREEN) {
                    Gdx.graphics.setWindowedMode(Main.WIDTH, Main.HEIGHT);
                    MOUSE_SCALE_X = 1;
                    MOUSE_SCALE_Y = 1;
                }
                else {
                    Gdx.graphics.setFullscreenMode(currentMode);
                    float scale = (float) Main.WIDTH / currentMode.width;
                    MOUSE_SCALE_X = scale;
                    MOUSE_SCALE_Y = scale;
                }
                break;
        }
    }

    public void windowResize(int width, int height) {
        //if (!Main.FULLSCREEN) {
            MOUSE_SCALE_X = (float) Main.WIDTH / (width);
            MOUSE_SCALE_Y = (float) Main.HEIGHT / (height);
        //}
    }

    public void moveCursor(int direction) {
        for (String handle : getProfileHandleSet()) {
            UIProfile currentProfile = getProfile(handle);
            if (currentProfile.isOpen()) {
                int gridSideLength = currentProfile.getGridWidth();
                int x = currentProfile.getCurrentSelectionIndex() % gridSideLength;
                int y = currentProfile.getCurrentSelectionIndex() / gridSideLength;

                switch (direction) {
                    case UP:
                        y--;
                        break;
                    case DOWN:
                        y++;
                        break;
                    case LEFT:
                        x--;
                        break;
                    case RIGHT:
                        x++;
                        break;
                }

                if (x + y * currentProfile.getGridWidth() < currentProfile.getNumSelectables())
                    currentProfile.setCurrentSelectionIndex(x + y * gridSideLength);
            }
        }
    }

    public void updateMousePosition(int x, int y) {
        mx = x;
        my = y;
    }

    public void openProfile(String handle) {
        closeAllProfiles();
        if (profileExists(handle)) {
            getProfile(handle).open();
            currentProfileHandle = handle;
        } else
            Gdx.app.log("UIProfile", "No UIProfile with handle \"" + handle + "\"");
    }
    
    public void closeProfile(String handle) {
        if (profileExists(handle)) {
            getProfile(handle).close();
            //currentProfileHandle = "NONE";
        }
        else
            Gdx.app.log("UIProfile", "No UIProfile with handle \"" + handle + "\"");
    }
    
    public void closeAllProfiles() {
        for (String handle : getProfileHandleSet()) {
            closeProfile(handle);
        }
    }

    public synchronized Hashtable<String, UIProfile> getProfiles() {
        return profiles;
    }

    public synchronized void addProfile(UIProfile profile) {
        getProfiles().put(profile.getHandle(), profile);
        controller.profileKeyBindings.put(profile.getKeyBinding(), profile.getHandle());
        controller.profileButtonBindings.put(profile.getButtonBinding(), profile.getHandle());
    }

    public UIProfile getProfile(String handle) {
        if (profileExists(handle))
            return getProfiles().get(handle);
        else 
            throw new RuntimeException("No UIProfile with handle \"" + handle + "\"");
    }
    
    public boolean profileExists(String handle) {
        return getProfiles().containsKey(handle);
    }
    
    public Set<String> getProfileHandleSet() {
        return getProfiles().keySet();
    }

    public String getCurrentProfileHandle() {
        return currentProfileHandle;
    }

    public UIProfile getCurrentProfile() {
        return getProfile(getCurrentProfileHandle());
    }

    public BitmapFont getButtonFont() {
        return buttonFont;
    }
}

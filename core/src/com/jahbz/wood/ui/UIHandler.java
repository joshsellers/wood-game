package com.jahbz.wood.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
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

    public UIHandler() {
        batch = new SpriteBatch();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/HATTEN.TTF"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
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
                    MOUSE_SCALE = 1;
                }
                else {
                    Gdx.graphics.setFullscreenMode(currentMode);
                    MOUSE_SCALE = (float) currentMode.width / Main.WIDTH;
                    System.out.println(currentMode.width + " " + Main.WIDTH);
                }

                break;
        }
    }

    public void moveCursor(int direction) {
        for (String handle : getProfileHandleSet()) {
            UIProfile currentProfile = getProfile(handle);
            if (currentProfile.isOpen()) {
                int gridSideLength = currentProfile.getGridWidth();
                int x = currentProfile.getCurrentSelectionIndex() % gridSideLength;
                int y = currentProfile.getCurrentSelectionIndex() / gridSideLength;

                switch (direction) {
                    case JOYSTICK_UP:
                        y++;
                        break;
                    case JOYSTICK_DOWN:
                        y--;
                        break;
                    case JOYSTICK_LEFT:
                        x--;
                        break;
                    case JOYSTICK_RIGHT:
                        x++;
                        break;
                }

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

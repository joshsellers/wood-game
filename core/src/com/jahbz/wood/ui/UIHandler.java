package com.jahbz.wood.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Hashtable;
import java.util.Set;

import static com.jahbz.wood.ui.UIController.*;

public class UIHandler {

    private final Hashtable<String, UIProfile> profiles = new Hashtable<>();

    private SpriteBatch batch;

    private String currentProfileHandle = "NONE";

    private int mx, my;
    
    public void render() {
        if (!currentProfileHandle.equalsIgnoreCase("NONE")) {
            for (UIComponent component : getCurrentProfile().getComponents()) {
                if (component.isVisible()) {
                    if (component.getBounds().contains(mx, my))
                        getCurrentProfile().setCurrentSelectionIndex(component.getSelectionIndex());
                    component.draw(batch);
                }
            }
        }
    }
    
    public void buttonPressed(String buttonId) {
        
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
                        y--;
                        break;
                    case JOYSTICK_DOWN:
                        y++;
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
        if (profileExists(handle)) 
            getProfile(handle).close();
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
}

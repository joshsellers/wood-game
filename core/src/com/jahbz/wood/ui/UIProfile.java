package com.jahbz.wood.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

public class UIProfile {

    private boolean isOpen;

    private final List<UIComponent> components = new ArrayList<>();

    private final String handle;

    private int currentSelectionIndex;
    private int gridWidth;
    private int gridHeight;
    private /*final*/ int numSelectionIndices;

    private final UIHandler handler;

    public UIProfile(String handle, UIHandler handler) {
        this.handler = handler;
        this.handle = handle;
        FileHandle configFile = Gdx.files.internal("uiconfigs/" + handle + ".uiconfig");
        String rawConfigData = configFile.readString();
        String[] configEntries = rawConfigData.split("\n");

        String[] gridData = configEntries[0].split(",");
        gridWidth = Integer.parseInt(gridData[0]);
        gridHeight = Integer.parseInt(gridData[1]);

        for (int i = 1; i < configEntries.length; i++) {
            String entryHeader = configEntries[i].split(":")[0].toUpperCase();
            switch (entryHeader) {
                case "BUTTON":
                    //add button
                    break;
                case "LABEL":
                    //add label;
                    break;
            }
        }
    }

    public void setCurrentSelectionIndex(int index) {
        currentSelectionIndex = index < numSelectionIndices ? Math.max(index, 0) : numSelectionIndices - 1;
        for (UIComponent component : getComponents()) {
            if (component.isSelectable() && !component.isSelected() && component.getSelectionIndex() == currentSelectionIndex)
                component.select();
            else if (component.isSelected()) component.deselect();
        }
    }

    public int getNumSelectionIndices() {
        return numSelectionIndices;
    }

    public int getCurrentSelectionIndex() {
        return currentSelectionIndex;
    }

    public synchronized List<UIComponent> getComponents() {
        return components;
    }

    public synchronized void addComponent(UIComponent component) {
        getComponents().add(component);
    }

    public String getHandle() {
        return handle;
    }

    public void open() {
        isOpen = true;
        for (UIComponent component : getComponents())
            component.show();
    }

    public void close() {
        isOpen = false;
        for (UIComponent component : getComponents())
            component.hide();
    }

    public boolean isOpen() {
        return isOpen;
    }

    public UIHandler getHandler() {
        return handler;
    }

    public int getGridWidth() {
        return gridWidth;
    }

    public int getGridHeight() {
        return gridHeight;
    }
}

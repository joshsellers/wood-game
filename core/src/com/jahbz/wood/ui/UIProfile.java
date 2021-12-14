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
    private final int gridWidth;
    private final int gridHeight;
    private final int numSelectionIndices;

    private final UIHandler handler;

    public UIProfile(String handle, UIHandler handler) {
        this.handler = handler;
        this.handle = handle;
        FileHandle configFile = Gdx.files.internal("uiconfigs/" + handle + ".uiconfig");
        String rawConfigData = configFile.readString();
        String[] configEntries = rawConfigData.split("\n");
        for (int i = 0; i < configEntries.length; i++)
            configEntries[i] = configEntries[i].replaceAll("\\s", "");;

        String[] gridData = configEntries[0].split(",");
        gridWidth = Integer.parseInt(gridData[0]);
        gridHeight = Integer.parseInt(gridData[1].trim());
        numSelectionIndices = gridWidth * gridHeight;

        for (int i = 1; i < configEntries.length; i++) {
            if (configEntries[i].startsWith("//")) continue;
            String[] entry = configEntries[i].split(":");
            String entryHeader = entry[0].toUpperCase();

            String[] entryData = entry[1].split(",");
            switch (entryHeader) {
                case "BUTTON":
                    String id = entryData[0];
                    String labelText = entryData[1].replace("_", " ");
                    float dispX = Integer.parseInt(entryData[2]);
                    float dispY = Integer.parseInt(entryData[3]);
                    float width;
                    if (entryData[4].equalsIgnoreCase("labelwidth")) {
                        handler.fontLayout.setText(handler.getButtonFont(), labelText);
                        width = handler.fontLayout.width + UIButton.LABEL_PADDING * 2;
                    } else
                        width = Integer.parseInt(entryData[4]);
                    float height;
                    if (entryData[5].equalsIgnoreCase("labelheight")) {
                        handler.fontLayout.setText(handler.getButtonFont(), labelText);
                        height = handler.fontLayout.height * 2 + UIButton.LABEL_PADDING;
                    } else
                        height = Integer.parseInt(entryData[5]);
                    int indexX = Integer.parseInt(entryData[6]);
                    int indexY = Integer.parseInt(entryData[7]);
                    boolean toggleable = Boolean.parseBoolean(entryData[8]);
                    boolean defaultToggleState = Boolean.parseBoolean(entryData[9]);

                    UIButton button = new UIButton(id, labelText, toggleable,
                            defaultToggleState,dispX, dispY, width, height,
                            indexX + indexY * gridWidth, this);
                    addComponent(button);
                    break;
                case "LABEL":
                    //add label;
                    break;
            }
        }
    }

    public UIComponent getSelectedComponent() {
        return getComponent(getCurrentSelectionIndex());
    }

    public UIComponent getComponent(int index) {
        return getComponents().get(index);
    }

    public void setCurrentSelectionIndex(int index) {
        currentSelectionIndex = index < numSelectionIndices ? Math.max(index, 0) : numSelectionIndices - 1;
        for (UIComponent component : getComponents()) {
            if (component.isSelectable() && !component.isSelected() &&
                    component.getSelectionIndex() == currentSelectionIndex) {
                component.select();
            } else if (component.isSelected() && component.getSelectionIndex() != currentSelectionIndex)
                component.deselect();
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

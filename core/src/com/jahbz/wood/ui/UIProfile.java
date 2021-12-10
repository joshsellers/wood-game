package com.jahbz.wood.ui;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

public class UIProfile {

    private boolean isOpen;

    private final List<UIComponent> components = new ArrayList<>();

    private final String id;

    private int selectionIndex;
    private /*final*/ int numSelectionIndices;

    public UIProfile(String configFile) {
                                                        //this might not work watch out
        id = configFile.split("/")[1].split("\\.")[0];

    }

    public void setSelectionIndex(int index) {
        selectionIndex = index < numSelectionIndices ? Math.max(index, 0) : numSelectionIndices - 1;
        for (UIComponent component : getComponents()) {
            if (component.isSelectable() && !component.isSelected() && component.getSelectionIndex() == selectionIndex)
                component.select();
            else if (component.isSelected()) component.deselect();
        }
    }

    public int getSelectionIndex() {
        return selectionIndex;
    }

    public synchronized List<UIComponent> getComponents() {
        return components;
    }

    public synchronized void addComponent(UIComponent component) {
        getComponents().add(component);
    }

    public String getId() {
        return id;
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
}

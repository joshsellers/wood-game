package com.jahbz.wood.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;

public class MovementController implements ControllerListener, InputProcessor {
    public final static float DEAD_ZONE = 0.3f;

    private Controller gamePad = null;
    private boolean controllerConnected = false;

    public MovementController() {
        for (Controller controller : Controllers.getControllers()) {
            controllerConnected = true;
            Gdx.app.log("MovementController", "Connected to " + controller.getName() + ".");
            gamePad = controller;
            Gdx.input.setCursorCatched(true);
            break;
        }

        if (gamePad == null) {
            Gdx.app.log("MovementController", "Could not find a game pad.");
        } else {
            gamePad.addListener(this);
        }
    }

    public void update() {

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Gdx.input.setCursorCatched(false);
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    @Override
    public void connected(Controller controller) {

    }

    @Override
    public void disconnected(Controller controller) {
        Gdx.input.setCursorCatched(false);
        controllerConnected = false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        Gdx.input.setCursorCatched(true);
        controllerConnected = true;
        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        Gdx.input.setCursorCatched(true);
        controllerConnected = true;
        return false;
    }

    public Controller getGamePad() {
        return gamePad;
    }
}

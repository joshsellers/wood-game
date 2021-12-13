package com.jahbz.wood.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.jahbz.wood.core.Main;
import com.jahbz.wood.gamepad.GamePadCode;

public class UIController implements ControllerListener, InputProcessor {
    public final static float DEAD_ZONE = 0.5f;

    public final static int JOYSTICK_CENTERED = -1;
    public final static int JOYSTICK_UP = 0;
    public final static int JOYSTICK_DOWN = 1;
    public final static int JOYSTICK_LEFT = 2;
    public final static int JOYSTICK_RIGHT = 3;

    public static float MOUSE_SCALE = 1;

    private final UIHandler handler;

    private Controller gamePad = null;
    private boolean controllerConnected = false;

    private int joyStickDirection = JOYSTICK_CENTERED;

    public UIController(UIHandler handler) {
        this.handler = handler;
        this.handler.controller = this;

        for (Controller controller : Controllers.getControllers()) {
            controllerConnected = true;
            Gdx.app.log("UIController", controller.getName());
            gamePad = controller;
            Gdx.input.setCursorCatched(true);
            break;
        }

        if (gamePad == null) {
            Gdx.app.log("UIController", "Could not find a game pad.");
        } else {
            gamePad.addListener(this);
        }
    }

    public void update() {
        //this needs to be a little more complicated. or not used at all.
        /*if (isControllerConnected()) {
            float joyX = gamePad.getAxis(GamePadCode.LEFT_STICK_X.getCode());
            float joyY = gamePad.getAxis(GamePadCode.LEFT_STICK_Y.getCode());
            boolean deadZoned = joyX > -DEAD_ZONE && joyX < DEAD_ZONE &&
                    joyY > -DEAD_ZONE && joyY < DEAD_ZONE;
            if (deadZoned)
                joyStickDirection = JOYSTICK_CENTERED;
            else if (joyX > 0)
                joyStickDirection = JOYSTICK_RIGHT;
            else if (joyX < 0)
                joyStickDirection = JOYSTICK_LEFT;
            else if (joyY > 0)
                joyStickDirection = JOYSTICK_DOWN;
            else if (joyY < 0)
                joyStickDirection = JOYSTICK_UP;

            if (joyStickDirection != JOYSTICK_CENTERED)
                handler.moveCursor(joyStickDirection);
        }*/
    }

    public int getJoyStickDirection() {
        return joyStickDirection;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.P && !handler.getProfile("test2").isOpen()) {
            handler.openProfile("test2");
        } else if (keycode == Input.Keys.P)
            handler.closeProfile("test2");

        if (keycode == Input.Keys.ESCAPE && !handler.getProfile("pausemenu").isOpen())
            handler.openProfile("pausemenu");
        else if (keycode == Input.Keys.ESCAPE)
            handler.closeProfile("pausemenu");
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Gdx.input.setCursorCatched(false);

        UIComponent selectedComponent = handler.getCurrentProfile().getSelectedComponent();
        if (selectedComponent.isVisible() && selectedComponent.isSelected())
            selectedComponent.onCursorDown();
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        UIComponent selectedComponent = handler.getCurrentProfile().getSelectedComponent();
        if (selectedComponent.isVisible() && selectedComponent.isSelected())
            selectedComponent.onCursorUp();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        Gdx.input.setCursorCatched(false);
        handler.updateMousePosition((int) (screenX * MOUSE_SCALE),
                (int) ((Gdx.graphics.getHeight() - screenY) * MOUSE_SCALE));
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

    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        Gdx.input.setCursorCatched(true);

        if (buttonCode == GamePadCode.A_BUTTON.getCode()) {
            UIComponent selectedComponent = handler.getCurrentProfile().getSelectedComponent();
            if (selectedComponent.isVisible() && selectedComponent.isSelected())
                selectedComponent.onCursorDown();
        }


        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        switch (GamePadCode.BUTTON_CODES[buttonCode]) {
            case A_BUTTON:
                UIComponent selectedComponent = handler.getCurrentProfile().getSelectedComponent();
                if (selectedComponent.isVisible() && selectedComponent.isSelected())
                    selectedComponent.onCursorUp();
                break;
            case DPAD_UP:
                handler.moveCursor(JOYSTICK_UP);
                break;
            case DPAD_DOWN:
                handler.moveCursor(JOYSTICK_DOWN);
                break;
            case DPAD_LEFT:
                handler.moveCursor(JOYSTICK_LEFT);
                break;
            case DPAD_RIGHT:
                handler.moveCursor(JOYSTICK_RIGHT);
                break;
            case PAUSE_BUTTON:
                if (!handler.getProfile("pausemenu").isOpen())
                    handler.openProfile("pausemenu");
                else handler.closeProfile("pausemenu");
                break;
        }

        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        Gdx.input.setCursorCatched(true);
        return false;
    }

    public boolean isControllerConnected() {
        return controllerConnected;
    }
}

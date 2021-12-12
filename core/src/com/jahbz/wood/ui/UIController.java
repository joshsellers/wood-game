package com.jahbz.wood.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.jahbz.wood.gamepad.GamePadCode;

public class UIController implements ControllerListener, InputProcessor {
    public final static float DEAD_ZONE = 0.3f;

    public final static int JOYSTICK_CENTERED = -1;
    public final static int JOYSTICK_UP = 0;
    public final static int JOYSTICK_DOWN = 1;
    public final static int JOYSTICK_LEFT = 2;
    public final static int JOYSTICK_RIGHT = 3;

    private final UIHandler handler;

    private Controller gamePad = null;
    private boolean controllerConnected = false;

    private int joyStickDirection = JOYSTICK_CENTERED;

    public UIController(UIHandler handler) {
        this.handler = handler;

        for (Controller controller : Controllers.getControllers()) {
            controllerConnected = true;
            Gdx.app.log("UIController", controller.getName());
            gamePad = controller;
            break;
        }

        if (gamePad == null) {
            Gdx.app.log("UIController", "Could not find a game pad.");
        } else {
            gamePad.addListener(this);
        }
    }

    public void update() {
        if (isControllerConnected()) {
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
        }
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
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for (UIComponent component : handler.getCurrentProfile().getComponents()) {
            if (component.isVisible() && component.isSelected())
                component.onCursorDown();
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        for (UIComponent component : handler.getCurrentProfile().getComponents()) {
            if (component.isVisible() && component.isSelected())
                component.onCursorUp();
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        handler.updateMousePosition(screenX, screenY);
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
        if (buttonCode == GamePadCode.A_BUTTON.getCode()) {
            for (UIComponent component : handler.getCurrentProfile().getComponents()) {
                if (component.isVisible() && component.isSelected())
                    component.onCursorDown();
            }
        }

        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        switch (GamePadCode.BUTTON_CODES[buttonCode]) {
            case A_BUTTON:
                for (UIComponent component : handler.getCurrentProfile().getComponents()) {
                    if (component.isVisible() && component.isSelected())
                        component.onCursorUp();
                }
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
        }
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        return false;
    }

    public boolean isControllerConnected() {
        return controllerConnected;
    }
}

package com.jahbz.wood.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerListener;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.jahbz.wood.gamepad.GamePadCode;
import com.jahbz.wood.resourcing.SpriteSheet;
import com.jahbz.wood.world.World;

public class DebugControllerInterface implements ControllerListener, InputProcessor {
    public final static float DEAD_ZONE = 0.3f;

    private OrthographicCamera cam;
    private Controller gamePad = null;

    private boolean controllerConnected = false;

    public DebugControllerInterface(OrthographicCamera cam) {
        this.cam = cam;

        for (Controller controller : Controllers.getControllers()) {
            controllerConnected = true;
            Gdx.app.log("CameraController", controller.getName());
            gamePad = controller;
            break;
        }

        if (gamePad == null) {
            Gdx.app.log("CameraController", "Could not find a game pad.");
        } else {
            gamePad.addListener(this);
        }
    }

    public void update() {
        if (controllerConnected) {
            float currentSpeed = 2.5f;
            float joyX = gamePad.getAxis(GamePadCode.RIGHT_STICK_X.getCode());
            float joyY = gamePad.getAxis(GamePadCode.RIGHT_STICK_Y.getCode());
            boolean deadZoned = joyX > -DEAD_ZONE && joyX < DEAD_ZONE &&
                    joyY > -DEAD_ZONE && joyY < DEAD_ZONE;
            if (deadZoned) {
                joyX = 0;
                joyY = 0;
            }

            float velocityX = (joyX) * currentSpeed;
            float velocityY = -((joyY) * currentSpeed);

            float viewW = getCamera().viewportWidth;
            float viewH = getCamera().viewportHeight;
            float camX = getCamera().position.x - viewW / 2;
            float camY = getCamera().position.y - viewH / 2;
            int w = World.MAP_WIDTH * SpriteSheet.TILE_SIZE;
            int h = World.MAP_HEIGHT * SpriteSheet.TILE_SIZE;
            if (camX + viewW + velocityX < w && camX + velocityX >= 0)
                getCamera().translate(velocityX, 0);
            if (camY + viewH + velocityY < h && camY + velocityY >= 0)
                getCamera().translate(0, velocityY);
        }
    }

    @Override
    public void connected(Controller controller) {
        controllerConnected = true;
    }

    @Override
    public void disconnected(Controller controller) {
        controllerConnected = false;
    }

    @Override
    public boolean buttonDown(Controller controller, int buttonCode) {
        //Gdx.app.log("CameraController", GamePadCode.BUTTON_CODES[buttonCode] + " (" + buttonCode + ")");

        return false;
    }

    @Override
    public boolean buttonUp(Controller controller, int buttonCode) {
        return false;
    }

    @Override
    public boolean axisMoved(Controller controller, int axisCode, float value) {
        //Gdx.app.log("CameraController", GamePadCode.AXIS_CODES[axisCode] + " (" + axisCode + ")");
        return false;
    }

    public void setCamera(OrthographicCamera cam) {
        this.cam = cam;
    }

    public OrthographicCamera getCamera() {
        return cam;
    }

    public Controller getGamePad() {
        return gamePad;
    }

    public void dispose() {

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
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}

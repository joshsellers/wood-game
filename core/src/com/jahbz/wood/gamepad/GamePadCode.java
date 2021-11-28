package com.jahbz.wood.gamepad;

public enum GamePadCode {
    LEFT_STICK_X     (0),
    LEFT_STICK_Y     (1),
    RIGHT_STICK_X    (2),
    RIGHT_STICK_Y    (3),

    LEFT_TRIGGER     (4),
    RIGHT_TRIGGER    (5),

    A_BUTTON         (0),
    B_BUTTON         (1),
    X_BUTTON         (2),
    Y_BUTTON         (3),

    SELECT_BUTTON    (4),
    DAMMIT_BILL      (5),
    PAUSE_BUTTON     (6),

    LEFT_STICK_DOWN  (7),
    RIGHT_STICK_DOWN (8),

    LEFT_BUMPER      (9),
    RIGHT_BUMPER     (10),

    DPAD_UP          (11),
    DPAD_DOWN        (12),
    DPAD_LEFT        (13),
    DPAD_RIGHT       (14);


    private final int code;

    GamePadCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static final GamePadCode[] BUTTON_CODES = {
            A_BUTTON, B_BUTTON, X_BUTTON, Y_BUTTON,
            SELECT_BUTTON,
            DAMMIT_BILL,
            PAUSE_BUTTON,
            LEFT_STICK_DOWN, RIGHT_STICK_DOWN,
            LEFT_BUMPER, RIGHT_BUMPER,
            DPAD_UP, DPAD_DOWN, DPAD_LEFT, DPAD_RIGHT
    };

    public static final GamePadCode[] AXIS_CODES = {
            LEFT_STICK_X, LEFT_STICK_Y,
            RIGHT_STICK_X, RIGHT_STICK_Y,
            LEFT_TRIGGER, RIGHT_TRIGGER
    };
}

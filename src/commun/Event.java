package commun;

import commun.GravMatrix.Direction;

public enum Event {
    LEFT, RIGHT, UP, DOWN,
    CLOSE, RESTART;

    /**
     * Cast event to a corresponding GravMatrix.Direction (or null)
     * @return appropriate direction
     */
    public GravMatrix.Direction toDirection() {
        switch (this) {
        case UP:
            return Direction.UP;
        case DOWN:
            return Direction.DOWN;
        case LEFT:
            return Direction.LEFT;
        case RIGHT:
            return Direction.RIGHT;
        default:
            return null;
        }
    }
}
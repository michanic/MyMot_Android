package ru.michanic.mymot.Enums;

public enum CellAccessoryType {
    RIGHT,
    TOP,
    BOTTOM,
    HIDDEN,
    CHECKED,
    LOADING;

    public double angle() {
        switch (this) {
            case RIGHT:
                return 0;
            case TOP:
                return - Math.PI / 2;
            case BOTTOM:
                return Math.PI / 2;
            case HIDDEN:
                return 0;
            case CHECKED:
                return 0;
            case LOADING:
                return 0;
        }
        return 0;
    }

}

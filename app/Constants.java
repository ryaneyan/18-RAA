package app;

public class Constants {
    private Constants() {}

    public static final double RADIUS = 40;
    public static final double X_DIFF = 3*(RADIUS/(Math.sqrt(3)));
    public static final double Y_DIFF = RADIUS*1.5;
    public static final double X_ORIGIN = 550;
    public static final double Y_ORIGIN = 150;
    public static final double NUM_OF_SIDES = 6;
    public static final double ATOM_SIZE = RADIUS/3;
    public static final double ATOMS_AMOUNT = 6;
    public static final double HEX_RADIUS = RADIUS + 10;
    public static final double HEX_OFFSET_X = RADIUS  - 15; // Horizontal offset between hexagons
    public static final double HEX_OFFSET_Y = RADIUS - 25;
}

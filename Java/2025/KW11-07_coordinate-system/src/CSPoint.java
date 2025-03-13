import java.awt.Point;

/**
 * Repräsentiert einen Punkt im 2D-Koordinatensystem.
 * Erweitert java.awt.Point und fügt eine eindeutige ID hinzu.
 */
public class CSPoint extends Point {
    private static int nextId = 1;
    private final int id;

    public CSPoint(int x, int y) {
        super(x, y);
        this.id = nextId++;
    }

    public CSPoint() {
        this(0, 0);
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Punkt ID: " + id + " [x=" + x + ", y=" + y + "]";
    }
}
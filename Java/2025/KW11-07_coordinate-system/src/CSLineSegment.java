/**
 * Repräsentiert ein Liniensegment im 2D-Koordinatensystem, definiert durch zwei Punkte.
 */
public class CSLineSegment {
    private static int nextId = 1;
    private final int id;
    private final CSPoint startPoint;
    private final CSPoint endPoint;

    public CSLineSegment(CSPoint startPoint, CSPoint endPoint) {
        this.id = nextId++;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public int getId() {
        return id;
    }

    public CSPoint getStartPoint() {
        return startPoint;
    }

    public CSPoint getEndPoint() {
        return endPoint;
    }

    @Override
    public String toString() {
        return "Linie ID: " + id + " [Start=" + startPoint + ", Ende=" + endPoint + "]";
    }
}
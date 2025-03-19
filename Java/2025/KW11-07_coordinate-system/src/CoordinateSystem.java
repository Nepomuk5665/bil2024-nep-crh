import java.util.ArrayList;
import java.util.List;

/**
 * Repräsentiert ein 2D-Koordinatensystem mit Punkten und Liniensegmenten.
 */
public class CoordinateSystem {
    private final int size;
    private final List<CSPoint> points;
    private final List<CSLineSegment> lineSegments;

    /**
     * Konstruktor für ein quadratisches Koordinatensystem.
     * 
     * @param size Die Größe des Koordinatensystems (muss > 0 und durch 20 teilbar sein)
     * @throws IllegalArgumentException wenn die Größe ungültig ist
     */
    public CoordinateSystem(int size) {
        if (size <= 0 || size % 20 != 0) {
            throw new IllegalArgumentException("Größe muss > 0 und durch 20 teilbar sein");
        }
        this.size = size;
        this.points = new ArrayList<>();
        this.lineSegments = new ArrayList<>();
    }

    public void addPoint(CSPoint point) {
        if (point != null) {
            points.add(point);
        }
    }

    public void addLineSegment(CSLineSegment lineSegment) {
        if (lineSegment != null) {
            lineSegments.add(lineSegment);
        }
    }

    public List<CSPoint> getAllPoints() {
        return new ArrayList<>(points);
    }

    public List<CSLineSegment> getAllLineSegments() {
        return new ArrayList<>(lineSegments);
    }

    public int getCoordinateSystemSize() {
        return size;
    }
}
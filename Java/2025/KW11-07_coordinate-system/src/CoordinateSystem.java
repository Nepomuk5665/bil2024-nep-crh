import java.util.ArrayList;
import java.util.List;


public class CoordinateSystem {
    private final int size;
    private final List<CSPoint> points;
    private final List<CSLineSegment> lineSegments;

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
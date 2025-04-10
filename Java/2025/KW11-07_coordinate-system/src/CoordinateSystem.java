import java.util.ArrayList;
import java.util.List;

public class CoordinateSystem {
    private final int size;
    private final List<CSShape> shapes;

    public CoordinateSystem(int size) {
        if (size <= 0 || size % 20 != 0) {
            throw new IllegalArgumentException("Größe muss > 0 und durch 20 teilbar sein");
        }
        this.size = size;
        this.shapes = new ArrayList<>();
    }

    public void addShape(CSShape shape) {
        if (shape != null) {
            shapes.add(shape);
        }
    }

    public List<CSShape> getAllShapes() {
        return new ArrayList<>(shapes);
    }

    public List<CSPoint> getAllPoints() {
        List<CSPoint> points = new ArrayList<>();
        for (CSShape shape : shapes) {
            if (shape instanceof CSPoint) {
                points.add((CSPoint) shape);
            }
        }
        return points;
    }

    public List<CSLineSegment> getAllLineSegments() {
        List<CSLineSegment> lineSegments = new ArrayList<>();
        for (CSShape shape : shapes) {
            if (shape instanceof CSLineSegment) {
                lineSegments.add((CSLineSegment) shape);
            }
        }
        return lineSegments;
    }

    public double calculateDistance(CSPoint p1, CSPoint p2) {
        return p1.distance(p2);
    }

    public double calculateSlope(CSPoint p1, CSPoint p2) {
        double dx = p2.x - p1.x;
        if (dx == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (p2.y - p1.y) / dx;
    }

    public CSShape reflectShapeX(CSShape shape) {
        return shape.reflectX();
    }

    public CSShape reflectShapeY(CSShape shape) {
        return shape.reflectY();
    }

    public int getCoordinateSystemSize() {
        return size;
    }
}
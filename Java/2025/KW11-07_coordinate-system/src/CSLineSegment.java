
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class CSLineSegment implements CSShape {
    private static int nextId = 1;
    private final int id;
    private final CSPoint startPoint;
    private final CSPoint endPoint;
    private Color color = Color.GREEN;

    public CSLineSegment(CSPoint startPoint, CSPoint endPoint) {
        this.id = nextId++;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    @Override
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
    public void render(Graphics2D g2d, int fieldScale, int size) {
        CSPoint translatedStart = translatePoint(startPoint, fieldScale, size);
        CSPoint translatedEnd = translatePoint(endPoint, fieldScale, size);
        g2d.setColor(color);
        g2d.drawLine(translatedStart.x, translatedStart.y, 
                    translatedEnd.x, translatedEnd.y);
    }

    @Override
    public boolean containsPoint(Point point, int tolerance) {
        double lineLength = startPoint.distance(endPoint);
        if (lineLength == 0) return false;
        
        double distance = Math.abs((endPoint.y - startPoint.y) * point.x - 
                                (endPoint.x - startPoint.x) * point.y + 
                                endPoint.x * startPoint.y - 
                                endPoint.y * startPoint.x) / lineLength;
        
        if (distance <= tolerance) {
            double dotProduct = ((point.x - startPoint.x) * (endPoint.x - startPoint.x) + 
                            (point.y - startPoint.y) * (endPoint.y - startPoint.y)) / (lineLength * lineLength);
            
            return dotProduct >= 0 && dotProduct <= 1;
        }
        
        return false;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public double getLength() {
        return startPoint.distance(endPoint);
    }

    @Override
    public double getSlope() {
        double dx = endPoint.x - startPoint.x;
        if (dx == 0) {
            return Double.POSITIVE_INFINITY;
        }
        return (endPoint.y - startPoint.y) / dx;
    }

    @Override
    public CSShape reflectX() {
        return new CSLineSegment(
            (CSPoint) startPoint.reflectX(),
            (CSPoint) endPoint.reflectX()
        );
    }

    @Override
    public CSShape reflectY() {
        return new CSLineSegment(
            (CSPoint) startPoint.reflectY(),
            (CSPoint) endPoint.reflectY()
        );
    }

    @Override
    public String toString() {
        return "Linie ID: " + id + " [Start=" + startPoint + ", Ende=" + endPoint + "]";
    }
    
    private CSPoint translatePoint(Point point, int fieldScale, int size) {
        return new CSPoint(point.x * fieldScale + size / 2, size / 2 - point.y * fieldScale);
    }
}
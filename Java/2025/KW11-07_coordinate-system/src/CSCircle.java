import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class CSCircle implements CSShape {
    private static int nextId = 1;
    private final int id;
    private final CSPoint center;
    private final int radius;
    private Color color = Color.YELLOW;

    public CSCircle(CSPoint center, int radius) {
        this.id = nextId++;
        this.center = center;
        this.radius = radius;
    }

    @Override
    public int getId() {
        return id;
    }

    public CSPoint getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }

    @Override
    public void render(Graphics2D g2d, int fieldScale, int size) {
        CSPoint translatedCenter = translatePoint(center, fieldScale, size);
        int scaledRadius = radius * fieldScale;
        
        g2d.setColor(color);
        g2d.drawOval(translatedCenter.x - scaledRadius, translatedCenter.y - scaledRadius, 
                    scaledRadius * 2, scaledRadius * 2);
    }

    @Override
    public boolean containsPoint(Point point, int tolerance) {
        double distance = center.distance(point);
        return Math.abs(distance - radius) <= tolerance;
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
        return 2 * Math.PI * radius;
    }

    @Override
    public double getSlope() {
        return 0.0/0.0;
    }

    @Override
    public CSShape reflectX() {
        return new CSCircle(
            new CSPoint(center.x, -center.y),
            radius
        );
    }

    @Override
    public CSShape reflectY() {
        return new CSCircle(
            new CSPoint(-center.x, center.y),
            radius
        );
    }

    @Override
    public String toString() {
        return "Kreis ID: " + id + " [Center=" + center + ", Radius=" + radius + "]";
    }
    
    private CSPoint translatePoint(Point point, int fieldScale, int size) {
        return new CSPoint(point.x * fieldScale + size / 2, size / 2 - point.y * fieldScale);
    }
}
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class CSRectangle implements CSShape {
    private static int nextId = 1;
    private final int id;
    private final CSPoint topLeft;
    private final CSPoint bottomRight;
    private Color color = Color.ORANGE;

    public CSRectangle(CSPoint topLeft, CSPoint bottomRight) {
        this.id = nextId++;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    @Override
    public int getId() {
        return id;
    }

    public CSPoint getTopLeft() {
        return topLeft;
    }

    public CSPoint getBottomRight() {
        return bottomRight;
    }

    @Override
    public void render(Graphics2D g2d, int fieldScale, int size) {
        CSPoint translatedTopLeft = translatePoint(topLeft, fieldScale, size);
        CSPoint translatedBottomRight = translatePoint(bottomRight, fieldScale, size);
        
        int width = translatedBottomRight.x - translatedTopLeft.x;
        int height = translatedBottomRight.y - translatedTopLeft.y;
        
        g2d.setColor(color);
        g2d.drawRect(translatedTopLeft.x, translatedTopLeft.y, width, height);
    }

    @Override
    public boolean containsPoint(Point point, int tolerance) {
        int minX = Math.min(topLeft.x, bottomRight.x) - tolerance;
        int maxX = Math.max(topLeft.x, bottomRight.x) + tolerance;
        int minY = Math.min(topLeft.y, bottomRight.y) - tolerance;
        int maxY = Math.max(topLeft.y, bottomRight.y) + tolerance;
        
        if (point.x >= minX && point.x <= maxX && point.y >= minY && point.y <= maxY) {
            return true;
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
        int width = Math.abs(bottomRight.x - topLeft.x);
        int height = Math.abs(bottomRight.y - topLeft.y);
        return 2 * (width + height);
    }

    @Override
    public double getSlope() {
        return 0.0/0.0;
    }

    @Override
    public CSShape reflectX() {
        return new CSRectangle(
            new CSPoint(topLeft.x, -topLeft.y),
            new CSPoint(bottomRight.x, -bottomRight.y)
        );
    }

    @Override
    public CSShape reflectY() {
        return new CSRectangle(
            new CSPoint(-topLeft.x, topLeft.y),
            new CSPoint(-bottomRight.x, bottomRight.y)
        );
    }

    @Override
    public String toString() {
        return "Rechteck ID: " + id + " [TopLeft=" + topLeft + ", BottomRight=" + bottomRight + "]";
    }
    
    private CSPoint translatePoint(Point point, int fieldScale, int size) {
        return new CSPoint(point.x * fieldScale + size / 2, size / 2 - point.y * fieldScale);
    }
}
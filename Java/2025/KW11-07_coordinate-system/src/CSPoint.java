import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class CSPoint extends Point implements CSShape {
    private static int nextId = 1;
    private final int id;
    private Color color = Color.BLUE;

    public CSPoint(int x, int y) {
        super(x, y);
        this.id = nextId++;
    }

    public CSPoint() {
        this(0, 0);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void render(Graphics2D g2d, int fieldScale, int size) {
        CSPoint translated = translatePoint(this, fieldScale, size);
        g2d.setColor(color);
        g2d.drawLine(translated.x, translated.y, translated.x, translated.y);
    }

    @Override
    public boolean containsPoint(Point point, int tolerance) {
        return (point.x >= x - tolerance && point.x <= x + tolerance) &&
               (point.y >= y - tolerance && point.y <= y + tolerance);
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
        return 0;
    }

    @Override
    public double getSlope() {
        return 0.0/0.0;
    }

    @Override
    public CSShape reflectX() {
        return new CSPoint(x, -y);
    }

    @Override
    public CSShape reflectY() {
        return new CSPoint(-x, y);
    }

    @Override
    public String toString() {
        return "Punkt ID: " + id + " [x=" + x + ", y=" + y + "]";
    }
    
    private CSPoint translatePoint(Point point, int fieldScale, int size) {
        return new CSPoint(point.x * fieldScale + size / 2, size / 2 - point.y * fieldScale);
    }
}
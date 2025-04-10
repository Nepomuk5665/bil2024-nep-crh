import java.awt.Color;

public interface CSShape {
    int getId();
    void render(java.awt.Graphics2D g2d, int fieldScale, int size);
    boolean containsPoint(java.awt.Point point, int tolerance);
    Color getColor();
    void setColor(Color color);
    double getLength();
    double getSlope();
    CSShape reflectX();
    CSShape reflectY();
}
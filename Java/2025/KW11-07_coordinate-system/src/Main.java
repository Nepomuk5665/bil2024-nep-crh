import java.awt.Color;

public class Main {
    public static void main(String[] args) {
        CSPoint p1 = new CSPoint(100, 150);
        CSPoint p2 = new CSPoint(-120, 80);
        CSPoint p3 = new CSPoint(0, -200);
        CSPoint p4 = new CSPoint(-50, -60);
        CSPoint p5 = new CSPoint();

        CoordinateSystem cs = new CoordinateSystem(500);
        
        p1.setColor(Color.MAGENTA);
        p3.setColor(Color.CYAN);
        
        cs.addShape(p1);
        cs.addShape(p2);
        cs.addShape(p3);
        cs.addShape(p4);
        cs.addShape(p5);
        
        CSLineSegment line1 = new CSLineSegment(p1, p3);
        CSLineSegment line2 = new CSLineSegment(p2, p4);
        line2.setColor(Color.RED);
        
        cs.addShape(line1);
        cs.addShape(line2);
        
        CSRectangle rect = new CSRectangle(new CSPoint(-80, 120), new CSPoint(80, 40));
        rect.setColor(Color.PINK);
        cs.addShape(rect);
        
        CSShape reflectedRect = cs.reflectShapeY(rect);
        cs.addShape(reflectedRect);
        
        CSCircle circle = new CSCircle(new CSPoint(0, 0), 60);
        cs.addShape(circle);
        
        CSCircle circle2 = new CSCircle(new CSPoint(-100, -100), 40);
        circle2.setColor(Color.ORANGE);
        cs.addShape(circle2);
        
        CSShape reflectedCircle = cs.reflectShapeX(circle2);
        cs.addShape(reflectedCircle);
        
        System.out.println("Line length: " + line1.getLength());
        System.out.println("Line slope: " + line1.getSlope());
        System.out.println("Rectangle perimeter: " + rect.getLength());
        System.out.println("Circle circumference: " + circle.getLength());
        
        CSRenderer renderer = new CSRenderer(cs);
    }
}
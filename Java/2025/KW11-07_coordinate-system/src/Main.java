public class Main {
    public static void main(String[] args) {
        CSPoint p1 = new CSPoint(100, 150);
        CSPoint p2 = new CSPoint(-120, 80);
        CSPoint p3 = new CSPoint(0, -200);
        CSPoint p4 = new CSPoint(-50, -60);
        CSPoint p5 = new CSPoint();

        CoordinateSystem cs = new CoordinateSystem(500);
        
        cs.addPoint(p1);
        cs.addPoint(p2);
        cs.addPoint(p3);
        cs.addPoint(p4);
        cs.addPoint(p5);
        
        CSLineSegment line1 = new CSLineSegment(p1, p3);
        CSLineSegment line2 = new CSLineSegment(p2, p4);
        cs.addLineSegment(line1);
        cs.addLineSegment(line2);
        
        CSRenderer renderer = new CSRenderer(cs);
    }
}
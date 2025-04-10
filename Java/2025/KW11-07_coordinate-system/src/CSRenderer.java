import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CSRenderer extends JPanel {

  private CoordinateSystem cs;
  private JFrame mainFrame;

  private int size;
  private int fieldScale;
  private int pointSize;

  private final int OFFSET_MID;
  private final int OFFSET_END;

  public CSRenderer(CoordinateSystem cs, int fieldScale, int pointSize) {
    this.cs = cs;
    this.size = cs.getCoordinateSystemSize() * fieldScale;
    this.fieldScale = fieldScale;
    this.pointSize = pointSize;

    OFFSET_MID = (size + fieldScale) / 2;
    OFFSET_END = size + (fieldScale / 2);

    this.setPreferredSize(new Dimension(size + fieldScale, size + fieldScale));
    this.setupMouseMotionListener(2);

    mainFrame = new JFrame();
    mainFrame.setResizable(true);
    mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    mainFrame.add(this);
    mainFrame.pack();
    mainFrame.setLocationRelativeTo(null);

    mainFrame.setVisible(true);
  }

  public CSRenderer(CoordinateSystem cs) {
    this(cs, 1, 3);
  }

  @Override
  public void paintComponent(Graphics g) {
    Graphics2D g2d = (Graphics2D) g;
    
    drawCoordinateSystemGrid(g2d);
    
    for (CSShape shape : cs.getAllShapes()) {
      if (shape instanceof CSPoint) {
        g2d.setStroke(new BasicStroke(pointSize));
      } else {
        g2d.setStroke(new BasicStroke(fieldScale));
      }
      shape.render(g2d, fieldScale, size);
    }
  }

  private void drawCoordinateSystemGrid(Graphics2D g2d) {
    g2d.setStroke(new BasicStroke(fieldScale));
    for (int i = (fieldScale / 2); i <= OFFSET_END; i += (10 * fieldScale)) {
      g2d.setColor(Color.LIGHT_GRAY);
      g2d.drawLine(i, 0, i, OFFSET_END);
      g2d.drawLine(0, i, OFFSET_END, i);

      g2d.setColor(Color.BLACK);
      g2d.drawLine(i, -5 * fieldScale + OFFSET_MID, i, 5 * fieldScale + OFFSET_MID);
      g2d.drawLine(-5 * fieldScale + OFFSET_MID, i, 5 * fieldScale + OFFSET_MID, i);
    }

    g2d.drawLine(OFFSET_MID, 0, OFFSET_MID, OFFSET_END);
    g2d.drawLine(0, OFFSET_MID, OFFSET_END, OFFSET_MID);

    g2d.setColor(Color.RED);
    g2d.drawLine(OFFSET_MID, OFFSET_MID, OFFSET_MID, OFFSET_MID);
  }

  private CSPoint translatePoint(Point point) {
    return new CSPoint(point.x * fieldScale + size / 2, size / 2 - point.y * fieldScale);
  }

  private void setupMouseMotionListener(int leeway) {
    int scaledLeeway = leeway + pointSize / 2;
    this.addMouseMotionListener(new MouseAdapter() {
      @Override
      public void mouseMoved(MouseEvent me) {
        for (CSShape shape : cs.getAllShapes()) {
          Point screenPoint = me.getPoint();
          Point worldPoint = new Point(
              (screenPoint.x - size / 2) / fieldScale,
              (size / 2 - screenPoint.y) / fieldScale
          );
          
          if (shape.containsPoint(worldPoint, scaledLeeway)) {
            mainFrame.setTitle(shape.toString());
            return;
          }
        }
        
        mainFrame.setTitle("");
      }
    });
  }
}
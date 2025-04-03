package panels;

import managers.AffineTransformationManager;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.util.List;

public class CartesianCoordinatePanel extends JPanel {
    private int scale = 50;
    private AffineTransformationManager manager;

    public CartesianCoordinatePanel(AffineTransformationManager manager) {
        this.manager = manager;
        addMouseWheelListener(e -> {
            int rotation = e.getWheelRotation();
            if (rotation < 0) {
                scale += 5;
            } else if (rotation > 0 && scale > 25) {
                scale -= 5;
            }
            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;

        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(1));

        g2d.drawLine(0, centerY, width, centerY);
        g2d.drawLine(centerX, 0, centerX, height);

        drawArrow(g2d, width, centerY, true);
        drawArrow(g2d, centerX, 0, false);

        g2d.drawString("X", width - 20, centerY - 5);
        g2d.drawString("Y", centerX + 5, 15);

        drawGrid(g2d, width, height, centerX, centerY);
        drawPoints(g2d, centerX, centerY);
        drawTriangle(g2d, centerX, centerY);
        drawReflectionPoint(g2d, centerX, centerY);
        drawReflectedPoints(g2d, centerX, centerY);
    }

    private void drawArrow(Graphics2D g2d, int x, int y, boolean isXAxis) {
        int arrowSize = 10;
        if (isXAxis) {
            g2d.drawLine(x - arrowSize, y + arrowSize / 2, x, y);
            g2d.drawLine(x - arrowSize, y - arrowSize / 2, x, y);
        } else {
            g2d.drawLine(x - arrowSize / 2, y + arrowSize, x, y);
            g2d.drawLine(x + arrowSize / 2, y + arrowSize, x, y);
        }
    }

    private void drawGrid(Graphics2D g2d, int width, int height, int centerX, int centerY) {
        g2d.setStroke(new BasicStroke(1));

        for (int x = centerX + scale; x < width; x += scale) {
            g2d.drawLine(x, centerY - 5, x, centerY + 5);
            g2d.drawString(String.valueOf((x - centerX) / scale), x - 5, centerY + 20);
        }
        for (int x = centerX - scale; x > 0; x -= scale) {
            g2d.drawLine(x, centerY - 5, x, centerY + 5);
            g2d.drawString(String.valueOf((x - centerX) / scale), x - 10, centerY + 20);
        }

        for (int y = centerY - scale; y > 0; y -= scale) {
            g2d.drawLine(centerX - 5, y, centerX + 5, y);
            g2d.drawString(String.valueOf((centerY - y) / scale), centerX + 10, y + 5);
        }
        for (int y = centerY + scale; y < height; y += scale) {
            g2d.drawLine(centerX - 5, y, centerX + 5, y);
            g2d.drawString(String.valueOf((centerY - y) / scale), centerX + 10, y + 5);
        }
    }

    private void drawPoints(Graphics2D g2d, int centerX, int centerY) {
        List<Point2D.Double> points = manager.getPoints();
        g2d.setColor(Color.BLACK);

        for (Point2D.Double point : points) {
            int x = centerX + (int) (point.x * scale) - 3;
            int y = centerY - (int) (point.y * scale) - 3;
            g2d.fill(new Ellipse2D.Double(x, y, 6, 6));
        }
    }

    private void drawTriangle(Graphics2D g2d, int centerX, int centerY) {
        List<Point2D.Double> points = manager.getPoints();
        if (points.size() == 3) {
            g2d.setColor(Color.LIGHT_GRAY);

            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));

            int[] xPoints = new int[3];
            int[] yPoints = new int[3];

            for (int i = 0; i < 3; i++) {
                xPoints[i] = centerX + (int) (points.get(i).x * scale);
                yPoints[i] = centerY - (int) (points.get(i).y * scale);
            }

            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }

    private void drawReflectionPoint(Graphics2D g2d, int centerX, int centerY) {
        Point2D.Double reflectionPoint = manager.getReflectionPoint();
        if (reflectionPoint != null) {
            g2d.setColor(Color.RED);
            int x = centerX + (int) (reflectionPoint.x * scale) - 4;
            int y = centerY - (int) (reflectionPoint.y * scale) - 4;
            g2d.fill(new Ellipse2D.Double(x, y, 8, 8));
        }
    }

    private void drawReflectedPoints(Graphics2D g2d, int centerX, int centerY) {
        List<Point2D.Double> reflectedPoints = manager.getReflectedPoints();
        g2d.setColor(Color.BLACK);

        for (Point2D.Double point : reflectedPoints) {
            int x = centerX + (int) (point.x * scale) - 3;
            int y = centerY - (int) (point.y * scale) - 3;
            g2d.fill(new Ellipse2D.Double(x, y, 6, 6));
        }

        if (reflectedPoints.size() == 3) {
            g2d.setColor(Color.GRAY);

            float[] dashPattern = {5, 5};
            g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, dashPattern, 0));

            int[] xPoints = new int[3];
            int[] yPoints = new int[3];

            for (int i = 0; i < 3; i++) {
                xPoints[i] = centerX + (int) (reflectedPoints.get(i).x * scale);
                yPoints[i] = centerY - (int) (reflectedPoints.get(i).y * scale);
            }

            g2d.drawPolygon(xPoints, yPoints, 3);
        }
    }

    public void refresh() {
        repaint();
    }
}

package managers;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AffineTransformationManager {
    private List<Point2D.Double> currentPoints = new ArrayList<>();
    private List<Point2D.Double> reflectedPoints = new ArrayList<>();
    private Point2D.Double reflectionPoint;
    private boolean isAnimating = false;
    private double scaleFactor = 1.0;

    private double[][] scaleMatrix = new double[3][3];

    public AffineTransformationManager() {
        initializeScaleMatrix();
    }

    public void addPoint(double x, double y) {
        if (currentPoints.size() >= 3) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "You cannot add more than three points. Please generate affine transformation before.!",
                    "Error", JOptionPane.ERROR_MESSAGE));
            return;
        }
        currentPoints.add(new Point2D.Double(x, y));
    }

    public void clearPoints() {
        currentPoints.clear();
        reflectedPoints.clear();
    }

    public List<Point2D.Double> getPoints() {
        return currentPoints;
    }

    public void setReflectionPoint(double x, double y) {
        this.reflectionPoint = new Point2D.Double(x, y);
    }

    public Point2D.Double getReflectionPoint() {
        return reflectionPoint;
    }

    public void setScaleFactor(double scaleFactor) {
        this.scaleFactor = scaleFactor;
        initializeScaleMatrix();
    }

    public double getScaleFactor() {
        return scaleFactor;
    }

    public void generateReflectedTriangleWithAnimation(final JPanel panel) {
        if (reflectionPoint == null) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Reflection point is not set. Please set a reflection point first.",
                    "Error", JOptionPane.ERROR_MESSAGE));
            return;
        }

        isAnimating = true;
        Timer timer = new Timer(30, e -> {
            if (reflectedPoints.size() < currentPoints.size()) {
                Point2D.Double point = currentPoints.get(reflectedPoints.size());
                double scaledX = point.x * scaleMatrix[0][0];
                double scaledY = point.y * scaleMatrix[1][1];

                double reflectedX = 2 * reflectionPoint.x - scaledX;
                double reflectedY = 2 * reflectionPoint.y - scaledY;
                reflectedPoints.add(new Point2D.Double(reflectedX, reflectedY));
                panel.repaint();
            } else {
                ((Timer) e.getSource()).stop();
                isAnimating = false;
            }
        });
        timer.start();
    }

    public List<Point2D.Double> getReflectedPoints() {
        return reflectedPoints;
    }

    private void initializeScaleMatrix() {
        scaleMatrix[0][0] = scaleFactor;
        scaleMatrix[1][1] = scaleFactor;
        scaleMatrix[2][2] = 1.0;
    }
}
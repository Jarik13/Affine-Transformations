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

    public void generateReflectedTriangleWithAnimation(final JPanel panel) {
        if (reflectionPoint == null) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Reflection point is not set. Please set a reflection point first.",
                    "Error", JOptionPane.ERROR_MESSAGE));
            return;
        }

        clearReflectedPoints();

        for (Point2D.Double point : currentPoints) {
            double reflectedX = 2 * reflectionPoint.x - point.x;
            double reflectedY = 2 * reflectionPoint.y - point.y;

            double dx = reflectedX - reflectionPoint.x;
            double dy = reflectedY - reflectionPoint.y;

            double scaledX = reflectionPoint.x + dx * scaleFactor;
            double scaledY = reflectionPoint.y + dy * scaleFactor;
            reflectedPoints.add(new Point2D.Double(scaledX, scaledY));
        }

        panel.repaint();
    }

    public List<Point2D.Double> getReflectedPoints() {
        return reflectedPoints;
    }

    private void initializeScaleMatrix() {
        scaleMatrix[0][0] = scaleFactor;
        scaleMatrix[1][1] = scaleFactor;
        scaleMatrix[2][2] = 1.0;
    }

    public void clearReflectedPoints() {
        reflectedPoints.clear();
    }
}
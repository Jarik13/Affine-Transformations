package managers;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AffineTransformationManager {
    private List<Point2D.Double> currentPoints = new ArrayList<>();
    private List<Point2D.Double> reflectedPoints = new ArrayList<>();
    private Point2D.Double reflectionPoint;

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

    public List<Point2D.Double> generateReflectedTriangle() {
        reflectedPoints.clear();
        if (reflectionPoint == null) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Reflection point is not set. Please set a reflection point first.",
                    "Error", JOptionPane.ERROR_MESSAGE));
            return reflectedPoints;
        }

        for (Point2D.Double point : currentPoints) {
            double reflectedX = 2 * reflectionPoint.x - point.x;
            double reflectedY = 2 * reflectionPoint.y - point.y;
            reflectedPoints.add(new Point2D.Double(reflectedX, reflectedY));
        }

        return reflectedPoints;
    }

    public void setReflectedPoints(List<Point2D.Double> reflectedPoints) {
        this.reflectedPoints = reflectedPoints;
    }

    public List<Point2D.Double> getReflectedPoints() {
        return reflectedPoints;
    }
}
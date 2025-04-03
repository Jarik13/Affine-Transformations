package managers;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AffineTransformationManager {
    private List<Point2D.Double> currentPoints = new ArrayList<>();
    private List<Point2D.Double> reflectedPoints = new ArrayList<>();
    private Point2D.Double reflectionPoint;
    private double scaleFactor = 1.0;

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
        reflectionPoint = null;
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
    }

    public void generateReflectedTriangleWithAnimation(final JPanel panel) {
        if (reflectionPoint == null) {
            SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(null,
                    "Reflection point is not set. Please set a reflection point first.",
                    "Error", JOptionPane.ERROR_MESSAGE));
            return;
        }

        clearReflectedPoints();

        double[][] translationToOrigin = {
                {1, 0, -reflectionPoint.x},
                {0, 1, -reflectionPoint.y},
                {0, 0, 1}
        };

        double[][] reflectionMatrix = {
                {-1, 0, 0},
                {0, -1, 0},
                {0, 0, 1}
        };

        double[][] scalingMatrix = {
                {scaleFactor, 0, 0},
                {0, scaleFactor, 0},
                {0, 0, 1}
        };

        double[][] translationBack = {
                {1, 0, reflectionPoint.x},
                {0, 1, reflectionPoint.y},
                {0, 0, 1}
        };

        double[][] transformMatrix = multiplyMatrices(translationBack,
                multiplyMatrices(scalingMatrix,
                        multiplyMatrices(reflectionMatrix, translationToOrigin)));

        for (Point2D.Double point : currentPoints) {
            double[] pointVector = {point.x, point.y, 1};
            double[] transformedPoint = multiplyMatrixAndVector(transformMatrix, pointVector);

            reflectedPoints.add(new Point2D.Double(transformedPoint[0], transformedPoint[1]));
        }

        panel.repaint();
    }

    public List<Point2D.Double> getReflectedPoints() {
        return reflectedPoints;
    }

    private double[][] multiplyMatrices(double[][] first, double[][] second) {
        int rows = first.length, cols = second[0].length, common = second.length;
        double[][] result = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                for (int k = 0; k < common; k++) {
                    result[i][j] += first[i][k] * second[k][j];
                }
            }
        }
        return result;
    }

    private double[] multiplyMatrixAndVector(double[][] matrix, double[] vector) {
        double[] result = new double[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < vector.length; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    public void clearReflectedPoints() {
        reflectedPoints.clear();
    }
}
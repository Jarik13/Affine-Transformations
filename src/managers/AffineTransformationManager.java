package managers;

import javax.swing.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AffineTransformationManager {
    private List<Point2D.Double> currentPoints = new ArrayList<>();

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
}

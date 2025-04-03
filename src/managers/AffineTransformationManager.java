package managers;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class AffineTransformationManager {
    private List<Point2D.Double> currentPoints = new ArrayList<>();

    public void addPoint(double x, double y) {
        currentPoints.add(new Point2D.Double(x, y));
    }

    public List<Point2D.Double> getPoints() {
        return currentPoints;
    }
}

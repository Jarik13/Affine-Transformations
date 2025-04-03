import panels.CartesianCoordinatePanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Affine Transformations!");
        frame.setLayout(new BorderLayout());
        initializeUI(frame);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private static void initializeUI(JFrame frame) {
        initializeInputPanel(frame);
        initializeCartesianCoordinatePanel(frame);
    }

    private static void initializeInputPanel(JFrame frame) {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel xLabel = new JLabel("X:");
        JTextField xTextField = new JTextField(5);
        JLabel yLabel = new JLabel("Y:");
        JTextField yTextField = new JTextField(5);
        JButton addPointButton = new JButton("Add Point");
        JButton centerPointButton = new JButton("Set Center Point");

        inputPanel.add(xLabel);
        inputPanel.add(xTextField);
        inputPanel.add(yLabel);
        inputPanel.add(yTextField);
        inputPanel.add(addPointButton);
        inputPanel.add(centerPointButton);

        frame.add(inputPanel, BorderLayout.NORTH);
    }

    private static void initializeCartesianCoordinatePanel(JFrame frame) {
        CartesianCoordinatePanel coordinatePanel = new CartesianCoordinatePanel();
        frame.add(coordinatePanel, BorderLayout.CENTER);
    }
}
import managers.AffineTransformationManager;
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
        AffineTransformationManager manager = new AffineTransformationManager();
        CartesianCoordinatePanel coordinatePanel = new CartesianCoordinatePanel(manager);

        initializeInputPanel(frame, manager, coordinatePanel);

        frame.add(coordinatePanel, BorderLayout.CENTER);
    }

    private static void initializeInputPanel(JFrame frame, AffineTransformationManager manager, CartesianCoordinatePanel coordinatePanel) {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel xLabel = new JLabel("X:");
        JTextField xTextField = new JTextField(5);
        JLabel yLabel = new JLabel("Y:");
        JTextField yTextField = new JTextField(5);
        JButton addPointButton = new JButton("Add Point");
        JButton setReflectionPointButton = new JButton("Set Reflection Point");
        JLabel scaleTriangleLabel = new JLabel("Scale:");
        JTextField scaleTriangleTextField = new JTextField(5);
        JButton generateAffineTransformationButton = new JButton("Generate Affine Transformation");
        JButton playAnimationButton = new JButton("Play Animation");
        JButton stopAnimationButton = new JButton("Stop Animation");
        JButton continueAnimationButton = new JButton("Continue Animation");
        JButton pauseAnimationButton = new JButton("Pause Animation");
        JButton clearPanelButton = new JButton("Clear");

        inputPanel.add(xLabel);
        inputPanel.add(xTextField);
        inputPanel.add(yLabel);
        inputPanel.add(yTextField);
        inputPanel.add(addPointButton);
        inputPanel.add(setReflectionPointButton);
        inputPanel.add(scaleTriangleLabel);
        inputPanel.add(scaleTriangleTextField);
        inputPanel.add(generateAffineTransformationButton);
        inputPanel.add(clearPanelButton);
        inputPanel.add(playAnimationButton);
        inputPanel.add(stopAnimationButton);
        inputPanel.add(continueAnimationButton);
        inputPanel.add(pauseAnimationButton);

        addPointButton.addActionListener(e -> {
            try {
                double x = Double.parseDouble(xTextField.getText());
                double y = Double.parseDouble(yTextField.getText());
                manager.addPoint(x, y);
                coordinatePanel.refresh();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for X and Y.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        setReflectionPointButton.addActionListener(e -> {
            try {
                double x = Double.parseDouble(xTextField.getText());
                double y = Double.parseDouble(yTextField.getText());
                manager.setReflectionPoint(x, y);
                coordinatePanel.refresh();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Please enter valid numbers for X and Y.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        generateAffineTransformationButton.addActionListener(e -> {
            try {
                double scale = Double.parseDouble(scaleTriangleTextField.getText());
                manager.setScaleFactor(scale);
                manager.generateReflectedTriangleWithAnimation(coordinatePanel);
                manager.saveTransformMatrixToFile("transform_matrix.txt");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for scale factor.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        playAnimationButton.addActionListener(e -> {
            try {
                double scale = Double.parseDouble(scaleTriangleTextField.getText());
                manager.setScaleFactor(scale);
                manager.startAnimation(coordinatePanel);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number for scale factor.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        stopAnimationButton.addActionListener(e -> manager.stopAnimation());

        continueAnimationButton.addActionListener(e -> manager.continueAnimation());

        pauseAnimationButton.addActionListener(e -> manager.pauseAnimation());

        clearPanelButton.addActionListener(e -> {
            manager.clearPoints();
            coordinatePanel.refresh();
        });

        frame.add(inputPanel, BorderLayout.NORTH);
    }
}
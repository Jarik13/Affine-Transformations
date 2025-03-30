import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    }

    private static void initializeInputPanel(JFrame frame) {
        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        JLabel xLabel = new JLabel("X:");
        JTextField xTextField = new JTextField(5);
        JLabel yLabel = new JLabel("Y:");
        JTextField yTextField = new JTextField(5);
        JButton addPointButton = new JButton("Add Point");

        inputPanel.add(xLabel);
        inputPanel.add(xTextField);
        inputPanel.add(yLabel);
        inputPanel.add(yTextField);
        inputPanel.add(addPointButton);

        frame.add(inputPanel, BorderLayout.NORTH);
    }
}
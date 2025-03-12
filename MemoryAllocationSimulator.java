import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MemoryAllocationSimulator extends JFrame {
    private JTextArea outputArea;
    private JTextField processSizeField, memoryBlockField;
    private JButton firstFitButton, bestFitButton, worstFitButton, resetButton, addMemoryBlockButton, removeMemoryBlockButton;
    private List<Integer> memory = new ArrayList<>(List.of(100, 300, 40, 50, 150, 240, 200, 400));
    private List<Boolean> free = new ArrayList<>(List.of(false, true, false, true, false, true, false, true));
    private int processNumber = 4;
    private int divs = memory.size();

    public MemoryAllocationSimulator() {
        initialize();
    }

    private void initialize() {
        setTitle("Memory Allocation Simulator");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Heading Panel
        JLabel headingLabel = new JLabel("Memory Allocation Simulator", SwingConstants.CENTER);
        headingLabel.setFont(new Font("Serif", Font.BOLD, 24));
        headingLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(headingLabel, BorderLayout.NORTH);

        // Section 1: Table
        JPanel tablePanel = new JPanel(new BorderLayout());
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane tableScrollPane = new JScrollPane(outputArea);
        tableScrollPane.setBorder(BorderFactory.createTitledBorder("Memory Allocation Table"));
        tablePanel.add(tableScrollPane, BorderLayout.CENTER);

        // Section 2: Graph
        GraphPanel graphPanel = new GraphPanel();
        graphPanel.setBorder(BorderFactory.createTitledBorder("Graphical Representation"));
        add(graphPanel, BorderLayout.CENTER);

        // Section 3: Buttons (Below the Table)
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BoxLayout(controlPanel, BoxLayout.Y_AXIS));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Controls"));

        // Process allocation section
        JPanel processPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        processPanel.add(new JLabel("Process Size (KB): "));
        processSizeField = new JTextField(10);
        processPanel.add(processSizeField);

        // Allocation method buttons
        JPanel allocationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        firstFitButton = createButton("First Fit", Color.GREEN);
        bestFitButton = createButton("Best Fit", Color.BLUE);
        worstFitButton = createButton("Worst Fit", Color.RED);
        resetButton = createButton("Reset", Color.ORANGE);
        allocationPanel.add(firstFitButton);
        allocationPanel.add(bestFitButton);
        allocationPanel.add(worstFitButton);
        allocationPanel.add(resetButton);

        // Memory block management section
        JPanel memoryManagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        memoryManagePanel.add(new JLabel("Block Size (KB): "));
        memoryBlockField = new JTextField(10);
        memoryManagePanel.add(memoryBlockField);
        addMemoryBlockButton = createButton("Add Block", Color.GREEN);
        removeMemoryBlockButton = createButton("Remove Block", Color.RED);
        memoryManagePanel.add(addMemoryBlockButton);
        memoryManagePanel.add(removeMemoryBlockButton);

        // Add all sections to the control panel
        controlPanel.add(processPanel);
        controlPanel.add(allocationPanel);
        controlPanel.add(memoryManagePanel);

        // Add table panel and control panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(controlPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.WEST);

        addListeners(graphPanel);
        printTable(-1);

        // Footer
        JLabel footerLabel = new JLabel("Done by: Pranjal Usulkar", SwingConstants.CENTER);
        footerLabel.setFont(new Font("Serif", Font.ITALIC, 16));
        footerLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(footerLabel, BorderLayout.PAGE_END);

        setVisible(true);
    }

    private JButton createButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        return button;
    }

    private void addListeners(GraphPanel graphPanel) {
        firstFitButton.addActionListener(e -> {
            try {
                int processSize = Integer.parseInt(processSizeField.getText());
                firstFit(processSize);
                graphPanel.repaint();
            } catch (NumberFormatException ex) {
                showError("Please enter a valid process size.");
            }
        });

        bestFitButton.addActionListener(e -> {
            try {
                int processSize = Integer.parseInt(processSizeField.getText());
                bestFit(processSize);
                graphPanel.repaint();
            } catch (NumberFormatException ex) {
                showError("Please enter a valid process size.");
            }
        });

        worstFitButton.addActionListener(e -> {
            try {
                int processSize = Integer.parseInt(processSizeField.getText());
                worstFit(processSize);
                graphPanel.repaint();
            } catch (NumberFormatException ex) {
                showError("Please enter a valid process size.");
            }
        });

        resetButton.addActionListener(e -> {
            resetMemory();
            graphPanel.repaint();
        });

        addMemoryBlockButton.addActionListener(e -> {
            addMemoryBlock();
            graphPanel.repaint();
        });

        removeMemoryBlockButton.addActionListener(e -> {
            removeMemoryBlock();
            graphPanel.repaint();
        });
    }

    private void firstFit(int processSize) {
        int ans = -1;
        for (int i = 0; i < divs; i++) {
            if (free.get(i) && processSize <= memory.get(i)) {
                ans = i;
                free.set(i, false);
                memory.set(i, memory.get(i) - processSize); // Subtract the allocated process size from the block
                break;
            }
        }
        printTable(ans);
    }

    private void bestFit(int processSize) {
        int ans = -1, curr = Integer.MAX_VALUE;
        for (int i = 0; i < divs; i++) {
            if (free.get(i) && processSize <= memory.get(i)) {
                if (memory.get(i) - processSize < curr) {
                    curr = memory.get(i) - processSize;
                    ans = i;
                }
            }
        }
        if (ans != -1) {
            free.set(ans, false);
            memory.set(ans, memory.get(ans) - processSize); // Subtract the allocated process size from the block
        }
        printTable(ans);
    }

    private void worstFit(int processSize) {
        int ans = -1, curr = 0;
        for (int i = 0; i < divs; i++) {
            if (free.get(i) && processSize <= memory.get(i)) {
                if (memory.get(i) - processSize > curr) {
                    curr = memory.get(i) - processSize;
                    ans = i;
                }
            }
        }
        if (ans != -1) {
            free.set(ans, false);
            memory.set(ans, memory.get(ans) - processSize); // Subtract the allocated process size from the block
        }
        printTable(ans);
    }

    private void addMemoryBlock() {
        try {
            int blockSize = Integer.parseInt(memoryBlockField.getText());
            memory.add(blockSize);
            free.add(true);
            divs = memory.size();
            printTable(-1);
            memoryBlockField.setText("");
        } catch (NumberFormatException ex) {
            showError("Please enter a valid memory block size.");
        }
    }

    private void removeMemoryBlock() {
        if (memory.size() > 1) {
            memory.remove(memory.size() - 1);
            free.remove(free.size() - 1);
            divs = memory.size();
            printTable(-1);
        } else {
            showError("Cannot remove the last memory block.");
        }
    }

    private void printTable(int pos) {
        StringBuilder output = new StringBuilder();
        output.append("+----------------------------------------------------------+\n");
        output.append("|\tNo.\tMemory \t\t Status \t Process   |\n");
        output.append("+----------------------------------------------------------+\n");
        int j = 1;
        for (int i = 0; i < divs; i++) {
            // Change status to "Free Space" and "Occupied"
            output.append("|\t").append(i + 1).append(" \t ").append(memory.get(i)).append("  \t\t  ")
                    .append((free.get(i) ? "Free Space \t\t   |" : "Occupied \t Process " + j++ + " |"))
                    .append("\n");
        }
        output.append("+----------------------------------------------------------+\n");
        outputArea.setText(output.toString());
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void resetMemory() {
        memory = new ArrayList<>(List.of(100, 300, 40, 50, 150, 240, 200, 400));
        free = new ArrayList<>(List.of(false, true, false, true, false, true, false, true));
        processNumber = 4;
        divs = memory.size();
        processSizeField.setText("");
        printTable(-1);
    }

    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int width = getWidth();
            int height = getHeight();
            int blockWidth = width / divs;

            for (int i = 0; i < divs; i++) {
                int blockHeight = (int) ((memory.get(i) / 500.0) * height);
                // Use green for free space and red for occupied
                g.setColor(free.get(i) ? Color.GREEN : Color.RED);
                g.fillRect(i * blockWidth, height - blockHeight, blockWidth - 5, blockHeight);
                g.setColor(Color.BLACK);
                g.drawRect(i * blockWidth, height - blockHeight, blockWidth - 5, blockHeight);
                g.drawString(memory.get(i) + " KB", i * blockWidth + 10, height - blockHeight - 5);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryAllocationSimulator::new);
    }
}

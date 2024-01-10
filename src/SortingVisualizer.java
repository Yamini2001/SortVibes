import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class SortingVisualizer extends JFrame {
    private static final int ARRAY_SIZE = 50;
    private static final int ARRAY_MAX_VALUE = 500;

    private int[] array;
    private SortPanel sortPanel;
    private JLabel comparisonsLabel;
    private JLabel swapsLabel;

    private int comparisonsCount = 0;
    private int swapsCount = 0;

    public SortingVisualizer() {
        initializeArray();
        initializeGUI();
    }

    private void initializeArray() {
        array = new int[ARRAY_SIZE];
        Random rand = new Random();

        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = rand.nextInt(ARRAY_MAX_VALUE) + 1;
        }
    }

    private void initializeGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        sortPanel = new SortPanel(array);
        add(sortPanel, BorderLayout.CENTER);

        JButton sortButton = new JButton("Sort");
        sortButton.addActionListener(e -> {
            sortButton.setEnabled(false);  // Disable the sort button during sorting
            comparisonsCount = 0;
            swapsCount = 0;
            updateStatistics();

            sortPanel.startSorting();
            new Thread(() -> {
                bubbleSort(array, sortPanel);
                sortPanel.finishSorting();

                // Enable the sort button after sorting is finished
                SwingUtilities.invokeLater(() -> sortButton.setEnabled(true));
            }).start();
        });

        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> {
            initializeArray();
            sortPanel.resetArray(array);
            sortPanel.repaint();
            comparisonsCount = 0;
            swapsCount = 0;
            updateStatistics();

            // Enable the sort button after resetting the array on the EDT
            SwingUtilities.invokeLater(() -> sortButton.setEnabled(true));
        });

        JButton shuffleButton = new JButton("Shuffle");
        shuffleButton.addActionListener(e -> {
            shuffleArray(array);
            sortPanel.resetArray(array);
            sortPanel.repaint();
            comparisonsCount = 0;
            swapsCount = 0;
            updateStatistics();
        });

        comparisonsLabel = new JLabel("Comparisons: 0");
        swapsLabel = new JLabel("Swaps: 0");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sortButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(shuffleButton);
        buttonPanel.add(comparisonsLabel);
        buttonPanel.add(swapsLabel);

        add(buttonPanel, BorderLayout.SOUTH);

        addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                sortPanel.repaint();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void updateStatistics() {
        comparisonsLabel.setText("Comparisons: " + comparisonsCount);
        swapsLabel.setText("Swaps: " + swapsCount);
    }

    private void bubbleSort(int[] array, SortPanel sortPanel) {
        int n = array.length;
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < n; i++) {
                comparisonsCount++;
                if (array[i - 1] > array[i]) {
                    int temp = array[i - 1];
                    array[i - 1] = array[i];
                    array[i] = temp;

                    swapsCount++;

                    swapped = true;

                    sortPanel.repaint();

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            }
            updateStatistics(); // Update statistics after each iteration
        } while (swapped);

        updateStatistics(); // Update statistics after sorting completes
    }

    private void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            // Swap array[i] and array[index]
            int temp = array[i];
            array[i] = array[index];
            array[index] = temp;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SortingVisualizer());
    }
}

class SortPanel extends JPanel {
    private int[] array;
    private Color[] colors;

    public SortPanel(int[] array) {
        this.array = array;
        this.colors = generateRandomColors(array.length);
    }

    private Color[] generateRandomColors(int count) {
        Color[] randomColors = new Color[count];
        Random rand = new Random();

        for (int i = 0; i < count; i++) {
            randomColors[i] = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
        }

        return randomColors;
    }

    public void startSorting() {
        // Initialization before sorting starts
    }

    public void finishSorting() {
        repaint(); // Ensure the final state is displayed
    }

    public void resetArray(int[] newArray) {
        array = Arrays.copyOf(newArray, newArray.length);
        colors = generateRandomColors(array.length);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int barWidth = getWidth() / array.length;
        int maxArrayValue = Arrays.stream(array).max().orElse(1);

        for (int i = 0; i < array.length; i++) {
            int barHeight = array[i] * getHeight() / maxArrayValue;
            g.setColor(colors[i]);
            g.fillRect(i * barWidth, getHeight() - barHeight, barWidth, barHeight);
        }
    }
}

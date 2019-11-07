import javax.imageio.plugins.jpeg.JPEGImageReadParam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class MainFrame implements Runnable {
    private JFrame frame;

    @Override
    public void run() {
        this.frame = new JFrame("Swing Threading");
        this.frame.setPreferredSize(new Dimension(400, 150));
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.createComponent(this.frame.getContentPane());
        this.frame.setResizable(false);
        this.frame.pack();
        this.frame.setVisible(true);
    }

    private void createComponent(Container c) {
        JProgressBar progressBar = new JProgressBar(0, 100);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);

        JButton startButton = new JButton("Start");
        startButton.addActionListener((ActionEvent e) -> {
            this.start(e, progressBar);
        });

        JPanel panel = new JPanel(new GridLayout(1, 2));
        panel.add(startButton);
        panel.add(progressBar);

        JCheckBox checkBox = new JCheckBox("Check me to proof UI is responsive");

        JPanel mainPanel = new JPanel(new GridLayout(2, 1));
        mainPanel.add(panel);
        mainPanel.add(checkBox);

        c.add(mainPanel, BorderLayout.CENTER);
    }

    private void start(ActionEvent e, JProgressBar progressBar) {
        SwingWorker<Void, Integer> worker = new SwingWorker<Void, Integer>() {
            @Override
            protected Void doInBackground() throws Exception {
                for (int i = 0; i < 100; i++) {
                    try {
                        Thread.sleep(100);
                        publish(i + 1);
                        System.out.println(i + 1);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                int amount = progressBar.getMaximum() - progressBar.getMinimum();
                progressBar.setValue(chunks.get(chunks.size() - 1));
            }

            @Override
            protected void done() {
                System.out.println("Completed task");
            }
        };

        worker.execute();

    }
}

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class Main {
    static boolean running = true;
    static Canvas canvas = new Canvas();
    static Game screen = new Game();
    static Graphics2D g;
    static BufferStrategy bs;
    static JFrame frame;

    public static void createWindow() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(canvas, BorderLayout.CENTER);
        canvas.setPreferredSize(new Dimension(screen.width, screen.height));
        canvas.createBufferStrategy(1);
        bs = canvas.getBufferStrategy();
        frame.pack();

        frame.setSize(screen.width, screen.height);
        //f.setLayout(null);
        frame.setVisible(true);
    }

    static public void start() {
        while (running) {
            g = (Graphics2D) bs.getDrawGraphics();
            screen.update(g);
            g.dispose();
            bs.show();
            try {
                Thread.sleep(10);
            } catch (InterruptedException ignored) {
            }
        }
        System.exit(0);

    }

    public static void main(String[] args) {
        createWindow();
        start();


    }
}
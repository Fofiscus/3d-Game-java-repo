/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg3dgame;

/**
 *
 * @author rreid
 */
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.ImageObserver;
import javax.swing.JFrame;
import pkg3dgame.graphics.Render;
import pkg3dgame.graphics.Screen;

public class Display extends Canvas implements Runnable, ImageObserver {

    private static final long serialVersionUID = 1L;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static final String TITLE = "RogueLike Pre-Alpha v0.01";

    private Thread thread;
    private Screen screen;
    private BufferedImage img;
    private Render render;
    private int[] pixels;
    private boolean running = false;

    public Display() {
        screen = new Screen(WIDTH, HEIGHT);
        img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
    }

    private void start() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();

    }

    private boolean stop() {
        boolean stopped = false;
        stopped = !running;

        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stopped;
    }

    @Override
    public void run() {
        int frames =0;
        
        double unprocessedSeconds = 0;
        long previousTime = System.nanoTime();
        double secondsPerTick = 1 / 60.0;
        int tickCount = 0;
        boolean ticked = false;
        while (running) {
            long currentTime = System.nanoTime();
            long passedTime = currentTime - previousTime;
            previousTime = currentTime;
            unprocessedSeconds += passedTime / 1000000000.0;
            
            while (unprocessedSeconds > secondsPerTick) {
              tick();
              unprocessedSeconds -= secondsPerTick;
              ticked = true;
              tickCount++;
              if (tickCount % 60 == 0) {
                  System.out.println(frames + "fps");
                  previousTime += 1000;
                  frames = 0;
              }
            }
            if(ticked) {
                render();
                frames++;
            }
            render();
            frames++;
        }
    }

    private void tick() {

    }

    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            bs = this.getBufferStrategy();
        }

        screen.render();

        for (int i = 0; i < WIDTH * HEIGHT; i++) {
            pixels[i] = screen.pixels[i];
        }

        Graphics g = bs.getDrawGraphics();
        g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
        g.dispose();
        bs.show();
    }

    public static void main(String[] args) {

        Display game = new Display();
        JFrame Frame = new JFrame();
        Frame.add(game);
        Frame.pack();
        Frame.setTitle(TITLE);
        Frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Frame.setSize(WIDTH, HEIGHT);
        Frame.setResizable(false);
        Frame.setVisible(true);

        game.start();

        System.out.println("Running...");
    }

}

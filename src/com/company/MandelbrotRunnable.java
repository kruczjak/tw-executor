package com.company;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class MandelbrotRunnable extends JFrame {
    public static final int MAX_ITER = 570;
    public static final double ZOOM = 150;
    private BufferedImage I;


    public MandelbrotRunnable() throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        long start = System.nanoTime();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                executor.execute(new WorkerRunnable(x,y,I));
            }
        }
        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.SECONDS);

        long elapsedTime = System.nanoTime() - start;
        System.out.println(elapsedTime);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) {
        try {
            new Mandelbrot().setVisible(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
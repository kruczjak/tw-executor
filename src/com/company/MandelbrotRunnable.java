package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MandelbrotRunnable extends JFrame {
    public static final int MAX_ITER = 10000;
    public static final double ZOOM = 5000;
    private BufferedImage I;


    public MandelbrotRunnable() throws ExecutionException, InterruptedException {
        super("MandelbrotFuture Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService executor = Executors.newFixedThreadPool(1);
        long start = System.nanoTime();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                executor.execute(new WorkerRunnable(x,y,I));
            }
        }
        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.SECONDS);

        long elapsedTime = System.nanoTime() - start;
        System.out.println("Runnable: " + elapsedTime);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
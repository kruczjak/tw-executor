package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MandelbrotRunnable extends JFrame {
    public static final int MAX_ITER = 100000;
    public static final double ZOOM = 50000;
    private final int type;
    private BufferedImage I;


    public MandelbrotRunnable(int type) throws ExecutionException, InterruptedException {
        super("MandelbrotFuture Set");
        this.type = type;
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService executor = null;
        switch (this.type) {
            case 0:
                executor = Executors.newSingleThreadExecutor();
                break;
            case 1:
                executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                break;
            case 2:
                executor = Executors.newCachedThreadPool();
                break;
            case 3:
                executor = Executors.newWorkStealingPool();
                break;
        }
        long start = System.nanoTime();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                assert executor != null;
                executor.execute(new WorkerRunnable(x,y,I));
            }
        }
        executor.shutdown();
        executor.awaitTermination(100, TimeUnit.SECONDS);

        long elapsedTime = System.nanoTime() - start;
        System.out.println("Runnable" + type + ": " + elapsedTime);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
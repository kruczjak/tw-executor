package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MandelbrotFuture extends JFrame {
    public static final int MAX_ITER = 10000;
    public static final double ZOOM = 5000;
    private BufferedImage I;


    public MandelbrotFuture() throws ExecutionException, InterruptedException {
        super("MandelbrotFuture Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(1);
        Set<Future<Worker>> set = new HashSet<Future<Worker>>();
        long start = System.nanoTime();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Future<Worker> futureWorker= pool.submit(new Worker(x, y));
                set.add(futureWorker);
            }
        }

        for (Future<Worker> worker : set) {
            Worker myWorker = worker.get();
            I.setRGB(myWorker.getX(), myWorker.getY(), myWorker.getIter() | (myWorker.getIter() << 8));
        }
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Future: " + elapsedTime);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
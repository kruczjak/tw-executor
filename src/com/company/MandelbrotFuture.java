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
    public static final int MAX_ITER = 100000;
    public static final double ZOOM = 50000;
    private final int type;
    private BufferedImage I;


    public MandelbrotFuture(int type) throws ExecutionException, InterruptedException {
        super("MandelbrotFuture Set");
        this.type = type;
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = null;
        switch (this.type) {
            case 0:
                pool = Executors.newSingleThreadExecutor();
                break;
            case 1:
                pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
                break;
            case 2:
                pool = Executors.newCachedThreadPool();
                break;
            case 3:
                    pool = Executors.newWorkStealingPool();
                break;
        }
        Set<Future<Worker>> set = new HashSet<Future<Worker>>();
        long start = System.nanoTime();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                assert pool != null;
                Future<Worker> futureWorker= pool.submit(new Worker(x, y));
                set.add(futureWorker);
            }
        }

        for (Future<Worker> worker : set) {
            Worker myWorker = worker.get();
            I.setRGB(myWorker.getX(), myWorker.getY(), myWorker.getIter() | (myWorker.getIter() << 8));
        }
        long elapsedTime = System.nanoTime() - start;
        System.out.println("Future" + type + ": " + elapsedTime);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
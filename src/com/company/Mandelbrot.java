package com.company;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import javax.swing.JFrame;

public class Mandelbrot extends JFrame {
    public static final int MAX_ITER = 570;
    public static final double ZOOM = 150;
    private BufferedImage I;


    public Mandelbrot() throws ExecutionException, InterruptedException {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
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
        System.out.println(elapsedTime);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }
}
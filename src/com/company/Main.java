package com.company;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        try {
            new Mandelbrot().setVisible(true);
            new MandelbrotFuture(0);
            new MandelbrotFuture(1);
            new MandelbrotFuture(2);
            new MandelbrotFuture(3);
//            new MandelbrotRunnable(0);
//            new MandelbrotRunnable(1);
//            new MandelbrotRunnable(2);
//            new MandelbrotRunnable(3);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

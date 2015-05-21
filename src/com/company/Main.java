package com.company;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        try {
            new Mandelbrot().setVisible(true);
            new MandelbrotFuture().setVisible(true);
            new MandelbrotRunnable().setVisible(true);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

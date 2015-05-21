package com.company;

import java.util.concurrent.Callable;

/**
 * Created by kruczjak on 5/20/15.
 */
public class Worker implements Callable<Worker> {
    private double zx, zy, cX, cY, tmp;
    private final int x;
    private final int y;
    private int iter = 0;

    public Worker(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Worker call() throws Exception {
        zx = zy = 0;
        cX = (x - 400) / MandelbrotFuture.ZOOM;
        cY = (y - 300) /  MandelbrotFuture.ZOOM;
        this.iter = MandelbrotFuture.MAX_ITER;
        while (zx * zx + zy * zy < 4 && this.iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            this.iter--;
        }
        return this;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIter() {
        return iter;
    }
}

package com.company;

import java.awt.image.BufferedImage;

/**
 * Created by kruczjak on 5/21/15.
 */
public class WorkerRunnable implements Runnable {
    private double zx, zy, cX, cY, tmp;
    private final int x;
    private final int y;
    private int iter = 0;
    private final BufferedImage I;

    public WorkerRunnable(int x, int y, BufferedImage i) {
        this.x = x;
        this.y = y;
        I = i;
    }

    @Override
    public void run() {
        zx = zy = 0;
        cX = (x - 400) / MandelbrotRunnable.ZOOM;
        cY = (y - 300) /  MandelbrotRunnable.ZOOM;
        this.iter = MandelbrotRunnable.MAX_ITER;
        while (zx * zx + zy * zy < 4 && this.iter > 0) {
            tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            this.iter--;
        }
        I.setRGB(x, y, iter | (iter << 8));
    }
}

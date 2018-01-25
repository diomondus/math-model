package com.butilov.mathmodel;

import lombok.Getter;

/**
 * Created by Dmitry Butilov
 * on 10.01.18.
 */
public class Solver {
    @Getter
    private double t[] = new double[1000];
    @Getter
    private double p[] = new double[1000];
    @Getter
    private double n[] = new double[1000];

    public void solveEquations(double a1, double a2, double n0, double nr, double p0, double pr, double T, double N) {
        t[0] = 0;
        p[0] = p0;
        n[0] = n0;

        double h = T;
        if (N < 1) {
            N = 1;
        } else {
            h /= N;
            if (N > 1000) {
                N = 1000;
            }
        }
        final double sqrtA1 = Math.sqrt(a1);
        final double sqrtA2 = Math.sqrt(a2);
        final double a1MULa2 = sqrtA1 * sqrtA2;
        final double a2DIVa1 = sqrtA2 / sqrtA1;
        final double deltaNMULa2DIVa1 = (nr - n0) * a2DIVa1;
        final double deltaP = p0 - pr;

        for (int i = 1; i <= N; i++) {
            t[i] = i * h;

            double sin = Math.sin(a1MULa2 * t[i]);
            double cos = Math.cos(a1MULa2 * t[i]);

            p[i] = (sin * deltaNMULa2DIVa1) + (cos * deltaP) + pr;
            n[i] = (sin * deltaP) + (nr * a2DIVa1) - cos * deltaNMULa2DIVa1;
        }
    }
}

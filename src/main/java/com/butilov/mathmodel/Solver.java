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

        double h = T / N;
        double b = Math.sqrt(a1);
        double c = Math.sqrt(a2);
        double a = b * c;

        for (int i = 1; i < N; i++) {
            t[i] = i * h;
            p[i] = (Math.sin(a * t[i]) * c * (nr - n0)) / b;
            p[i] += Math.cos(a * t[i]) * (-pr + p0) + pr;

            n[i] = Math.sin(a * t[i]) * b * (p0 - pr) + nr * c;
            n[i] -= Math.cos(a * t[i]) * c * (nr - n0);
            n[i] = n[i] / b;
        }
    }
}

package com.eckstein.paige.anagrams;

import java.util.Random;

public class GamePlay {

    private int[] tiles;
    Random rng;

    public GamePlay()
    {
        tiles = new int[]{R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a,
                          R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.a, R.drawable.b,
                          R.drawable.b, R.drawable.c, R.drawable.c, R.drawable.d, R.drawable.d,
                          R.drawable.d, R.drawable.d, R.drawable.e, R.drawable.e, R.drawable.e,
                          R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e,
                          R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.e, R.drawable.f,
                          R.drawable.f, R.drawable.g, R.drawable.g, R.drawable.g, R.drawable.h,
                          R.drawable.h, R.drawable.i, R.drawable.i, R.drawable.i, R.drawable.i,
                          R.drawable.i, R.drawable.i, R.drawable.i, R.drawable.i, R.drawable.i,
                          R.drawable.j, R.drawable.j, R.drawable.k, R.drawable.k, R.drawable.l,
                          R.drawable.l, R.drawable.l, R.drawable.l, R.drawable.m, R.drawable.m,
                          R.drawable.n, R.drawable.n, R.drawable.n, R.drawable.n, R.drawable.n,
                          R.drawable.n, R.drawable.o, R.drawable.o, R.drawable.o, R.drawable.o,
                          R.drawable.o, R.drawable.o, R.drawable.o, R.drawable.o, R.drawable.p,
                          R.drawable.p, R.drawable.q_pink_text, R.drawable.r, R.drawable.r, R.drawable.r,
                          R.drawable.r, R.drawable.r, R.drawable.r, R.drawable.s, R.drawable.s,
                          R.drawable.s, R.drawable.s, R.drawable.t, R.drawable.t, R.drawable.t,
                          R.drawable.t, R.drawable.t, R.drawable.t, R.drawable.u, R.drawable.u,
                          R.drawable.u, R.drawable.u, R.drawable.v, R.drawable.v, R.drawable.w,
                          R.drawable.w, R.drawable.x_pink_text, R.drawable.y, R.drawable.y, R.drawable.z_pink_text};

        rng = new Random();
    }

    public int getRandomTile()
    {
        return tiles[rng.nextInt(100)];
    }
}

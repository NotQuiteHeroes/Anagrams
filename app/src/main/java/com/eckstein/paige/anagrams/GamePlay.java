package com.eckstein.paige.anagrams;

import java.util.Random;

public class GamePlay {

    private int[] tiles;
    private Random rng;

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
        return tiles[rng.nextInt(99)];
    }

    public String getTileLetter(int drawable)
    {
       switch(drawable)
       {
           case R.drawable.a:
           {
               return "a";
           }
           case R.drawable.b:
           {
               return "b";
           }
           case R.drawable.c:
           {
               return "c";
           }
           case R.drawable.d:
           {
               return "d";
           }
           case R.drawable.e:
           {
               return "e";
           }
           case R.drawable.f:
           {
               return "f";
           }
           case R.drawable.g:
           {
               return "g";
           }
           case R.drawable.h:
           {
               return "h";
           }
           case R.drawable.i:
           {
               return "i";
           }
           case R.drawable.j:
           {
               return "j";
           }
           case R.drawable.k:
           {
               return "k";
           }
           case R.drawable.l:
           {
               return "l";
           }
           case R.drawable.m:
           {
               return "m";
           }
           case R.drawable.n:
           {
               return "n";
           }
           case R.drawable.o:
           {
               return "o";
           }
           case R.drawable.p:
           {
               return "p";
           }
           case R.drawable.q:
           {
               return "q";
           }
           case R.drawable.r:
           {
               return "r";
           }
           case R.drawable.s:
           {
               return "s";
           }
           case R.drawable.t:
           {
               return "t";
           }
           case R.drawable.u:
           {
               return "u";
           }
           case R.drawable.v:
           {
               return "v";
           }
           case R.drawable.w:
           {
               return "w";
           }
           case R.drawable.x:
           {
               return "x";
           }
           case R.drawable.y:
           {
               return "y";
           }
           case R.drawable.z:
           {
               return "z";
           }
       }

       return "a";
    }
}

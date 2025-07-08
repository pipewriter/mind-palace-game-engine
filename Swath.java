import javax.imageio.*;
import java.io.*;
import java.awt.image.*;
import java.util.*;

import java.awt.datatransfer.*;

import java.awt.*;


import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.image.*;
import java.io.IOException;
public class Swath{
static boolean isDisabled = false;
private static void shuffleArray(int[] array)
{
    int index;
    Random random = new Random();
    for (int i = array.length - 1; i > 0; i--)
    {
        index = random.nextInt(i + 1);
        if (index != i)
        {
            array[index] ^= array[i];
            array[i] ^= array[index];
            array[index] ^= array[i];
        }
    }
}


public static double[] randArray(int count){
    double[] ra = new double[count];
    for(int i = 0; i < count; i++){
    ra[i] = rn();
    //p(ra[i]);
    }
    return ra;
}
public static int i(double d){return (int)d;}

public static double[][] randArray2(int w,int h){
double[][] ra = new double[w][h];
int count = w*h;
for(int i = 0; i < w;i++){
for(int j = 0; j < h;j++){
ra[i][j]= rn();
}
}
return ra;
}
public static double rn(){
return _secretRandom.nextDouble();
}
public static Random _secretRandom = new Random();
public static int ri(){
return _secretRandom.nextInt();
}
public static int rc(){ //random color
return ri() | 0xff_00_00_00;
}
public static int cint(int r, int g, int b){
return ((255<<24)|(r&255)<<16) |
((g&255)<<8) |
((b&255<<0));}

static double[][] huh = new double[][]{
{ 65,  255,255,255},
{ 50,  122,122,122},
{ 30,  0,122,20},
{ 10,  25,255,0},
{ 0,  255,255,0},
{ -30,  0,0,255},
};

public static int[] lerp(int percentage, int r1, int g1, int b1, int r2, int g2, int b2) {
return new int[]{
(r2*100+(r1-r2)*percentage)/100,
(g2*100+(g1-g2)*percentage)/100,
(b2*100+(b1-b2)*percentage)/100,
};
}

static public void p(Object o){
// System.out.println(o);
}

//it's as beautiful as I imagined it

// z5103232 AAAAAAAAAA

static LinkedList<Integer> z5103232 (int z13865763){
var e = new LinkedList<Integer>();
for(int i = 0; i < 24; i++){
e.push(1<<i);
}
Random r = new Random(z13865763);
var g = new LinkedList<Integer>();
for(int i = 0; i < 24; i++){
g.push(e.remove(r.nextInt(e.size())));
}
return g;
} 

int doit_x = 0;
int doit_y = 0;
void doIt(){
    Random r = new Random();
    int cc1=r.nextInt()|0xff_00_00_00;
    int cc2=r.nextInt()|0xff_00_00_00;
    int c1 = cc1;//0xff_00_00_00;
    int c2 = cc2;//0xff_ff_ff_ff;
    oneBlock(doit_x,doit_y);
    for(int j = 0; j < 25; j++){
        for(int i = 0; i < 25; i++){
            int k = (int)(i/(25/25.0));
            int m = (int)(j/(25/25.0));
            // a.pix(bi,doit_x*512+i,doit_y*512+j, hmm[m][k]);
            // a.pix(bi,doit_x*512+i,doit_y*512+j, hmm[m][k]);
            int[] decomp = hmm2[global_holder];
            int lk = (doit_x*25+i)*250 + (doit_y*25+j);
            if(lk >= 62500){
                System.out.println(decomp);
                System.out.println(decomp.length);

            }
            if(hmm[m] == null){
                System.out.println("very strange hmm[m] case");
                System.exit(0);
            }
            decomp[lk] = hmm[m][k];
        }
    }
}


static int w = (int)(10);
static int h = (int)(10);
// static int w = (int)(10*Math.random()+1);
// static int h = (int)(10*Math.random()+1);
// static BufferedImage bi =a.makeBI(512*w,512*h);

// public static int getColorFromCompletedMap(int x, int y){
//     if(x < 0 || x > bi.getWidth() || y < 0 || y > bi.getHeight())
//         return 0;
//     return bi.getRGB(x, y);
// }

// public static void main(String[] args){
//     for(int i = 0; i < w; i++){
//     for(int j = 0; j < h; j++){
//     try{
//     doit_x=i;
//     doit_y=j;
//     doIt();
//     }catch(Exception e){}
//     }
//     }
//     // a.dumpClipboard(bi);

// }

// public int getColorFromCompletedMap(int x, int y){
//     if(isDisabled) return 0xff_ff_00_00;
//     // x/=2;
//     // y/=2;
//     if(x < 0 || x >= bi.getWidth() || y < 0 || y >= bi.getHeight())
//         return 0;
//     return bi.getRGB(x, y);
// }

public static int map2(int input) {
    int result = input / 4096 + 50;
    if(result < 0) result = 0;
    if(result >= 100) result = 99;
    return result;
}
public static int map(int input) {
    int result = input % 4096;
    return (result < 0) ? result + 4096 : result;
}
/*
    2000 2000 up and downy
*/
public int global_holder = 0;
public  int seed = 0;


int readmex1 = 0;
int readmey1 = 0;
int readmex2 = 0;
int readmey2 = 0;
public synchronized int getColorFromCompletedMap(int x, int y){
    if(isDisabled) return 0xff_ff_00_00;
    int ogx = x;
    int ogy = y;
    x/=4;
    y/=4;
    int w = 4096;
    int h = 4096;
    int xx = map2(x);
    int yy = map2(y);
    x = map(x);
    y = map(y);
    readmex1 = x;
    readmey1 = y;
    readmex2 = xx;
    readmey2 = yy;
    int lkup1 = yy*100+xx;
    if(hmm2[lkup1] == null){
        // System.out.println("yes it is needed...");
        hmm2[lkup1] = new int[250*250];
        //GENERATE
        global_holder = lkup1;
        System.out.println("Global holder set to " + lkup1 + " " + seed + " " + xx + " " + yy + " " + ogx + " " + ogy);
        baseSeed = (new Random(seed+lkup1)).nextInt(100000);
        
        for(int i = 0; i < 10; i++){
        for(int j = 0; j < 10; j++){
            doit_x=i;
            doit_y=j;
            doIt();
        }
        }
        // System.out.println("finished");


    }

    x = (int)(25.0/409.6*x);
    y = (int)(25.0/409.6*y);
    // System.out.println(x + " " + y);
    // System.out.println(hmm[(int)(25/512.0*x)][(int)(25/512.0*y)]&0xFF_ff_ff);
    return hmm2[lkup1][y*250+x];
        // a.pix(bi,doit_x*512+i,doit_y*512+j, hmm[m][k]);

    // return bi.getRGB(x, y);
}
// BufferedImage bi =a.makeBI(512*w,512*h);
public Swath(int seed){
    this.seed = seed;
}


int[][] hmm= new int[25][25];
// int[][] hmm2 = new int[100*100][];
int[][] hmm2 = new int[200*200][];

static int xy(int x,int y){
int g = (x<<10)+y;
p(g);
return g;
}

int baseSeed = 0;
int rseed(int x, int y){
return new Random(baseSeed+xy(x,y)).nextInt();
}
static BufferedImage makeBI(int w, int h){
    return new BufferedImage(
    w,h, BufferedImage.TYPE_4BYTE_ABGR);
}
static void dumpClipboard(BufferedImage bi2){
    try{
    TransferableImage trans = new TransferableImage(bi2);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(trans, null);
    }
    catch(Exception e){e.printStackTrace();}
}
public void specialGetMapImage(int x, int y){
    BufferedImage bi = makeBI(250, 250);
    // if(isDisabled) return 0xff_ff_00_00;
    x/=2;
    y/=2;
    int w = 4096;
    int h = 4096;
    x = map(x);
    y = map(y);
    int xx = map2(x);
    int yy = map2(y);
    int lkup1 = yy*100+xx;
    System.out.println("map  "+ x + " " + y);
    System.out.println("map2 "+ xx + " " + yy);
    // if(hmm2[lkup1] == null){
    //     // System.out.println("yes it is needed...");
    //     hmm2[lkup1] = new int[250*250];
    //     //GENERATE
    //     global_holder = lkup1;
    //     Random rvar = (new Random(seed+lkup1));
    //     baseSeed = rvar.nextInt(100000);
    //     Swath.rvar = rvar;
    //     for(int i = 0; i < 10; i++){
    //     for(int j = 0; j < 10; j++){
    //         doit_x=i;
    //         doit_y=j;
    //         doIt();
    //     }
    //     }
    //     // System.out.println("finished");

    for(int i = 0; i < 250; i ++){
        for(int j = 0; j < 250; j ++){
            // System.out.println(hmm2 + " " + lkup1);
            // System.out.println(hmm2[lkup1]);
            bi.setRGB(i, j,hmm2[lkup1][j*250+i]);
        }
    }
    dumpClipboard(bi);
    // }
    // mm2[lkup1]
    // x = (int)(25.0/409.6*x);
    // y = (int)(25.0/409.6*y);
    // System.out.println(x + " " + y);
    // System.out.println(hmm[(int)(25/512.0*x)][(int)(25/512.0*y)]&0xFF_ff_ff);

    // return hmm2[lkup1][y*250+x];
        // a.pix(bi,doit_x*512+i,doit_y*512+j, hmm[m][k]);

    // return bi.getRGB(x, y);
}
void oneBlock(int x, int y){

    Random r = new Random();
    int cc1=rseed(x,y)    |0xff_00_00_00;
    int cc2=rseed(x+1,y)  |0xff_00_00_00;
    int cc3=rseed(x,y+1)  |0xff_00_00_00;
    int cc4=rseed(x+1,y+1)|0xff_00_00_00;
    //cc1 = 0xff_00_ff_00;
    //cc2 = 0xff_00_00_ff;
    //cc3 = 0xff_ff_ff_ff;
    hmm[0][0]=cc1;  hmm[0][24]=cc2;
    hmm[24][0]=cc3;hmm[24][24]=cc4;

    Random r2 = new Random();
    int seed1 = r2.nextInt(1000);
    int seed2 = r2.nextInt(1000);

    seed1=rseed(x,0);
    seed2=rseed(0,y);
    var z13843755 = z5103232 (seed1);
    for(int i = 0; i < 24; i++){
    {
    int mask = z13843755.pop();
    int inv = mask^0xffff_ffff;
    int nc = (hmm[0][i]&inv)|(cc2&mask);
    hmm[0][i+1]=nc;
    }}
    z13843755 = z5103232 (seed1);
    for(int i = 0; i < 24; i++){
    {
    int mask = z13843755.pop();
    int inv = mask^0xffff_ffff;
    int nc = (hmm[24][i]&inv)|(cc4&mask);
    hmm[24][i+1]=nc;
    }
    }
    {
    for(int i = 0; i<25; i++){
    z13843755 = z5103232 (seed2);
    for(int j = 0; j<23; j++){
    int mask = z13843755.pop();
    int inv = mask^0xffff_ffff;
    int nc = (hmm[j][i]&inv)|(hmm[24][i]&mask);
    hmm[j+1][i]=nc;
    }}
    }
}


}
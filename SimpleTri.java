import java.awt.image.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.nio.*;
import java.util.function.*;
import java.lang.reflect.Array;

class SimpleTri{
static double[] unwrap(LinkedList<double[]> d){
int per = d.get(0).length;
int boxvals = per;
double[] dd = new double[d.size()*boxvals];
int c = 0;
for(var ddd: d){

for(int i = 0; i < boxvals; i++){
dd[c*boxvals+i]=ddd[i];
}
c++;
}
return dd;
}
static int[] unwrap2(LinkedList<Integer> sos){

int[] array = new int[sos.size()];
int e = 0; for (var v : sos){
array[e++] = v; 
}
return array;
}
static public void putDoubleArr(double[] ia, ByteBuffer current){
// current.putInt(ia.length);
current.putInt(ia.length);
// System.out.println("placing...d "+ia.length);
var ib = current.asDoubleBuffer();
ib.put(ia);
current.position(current.position()+ia.length*8);
}
static public void putIntArr(int[] ia, ByteBuffer current){
// current.putInt(ia.length);
current.putInt(ia.length);
// System.out.println("placing...i "+ia.length);

var ib = current.asIntBuffer();
ib.put(ia);
current.position(current.position()+ia.length*4);
}
static double i(Object o){
return (Integer)o;
}
static double d(Object o){
return (Double)o;

}

static double[] tri(Object[] p1, Object[] p2, Object[] p3){
return new double[]{
d(p1[0]),
d(p1[1]),
d(p1[2]),
d(p2[0]),
d(p2[1]),
d(p2[2]),
d(p3[0]),
d(p3[1]),
d(p3[2]),

};
}
// static interface z59376 extends Function<Object[],Object[]>{
// }
private static final long C1 = 0xcc9e2d544565345l;
private static final long C2 = 0x1b87359325796423l;
public static int smear(long hashCode) {
return new Random((int) (C2 *
    Long.rotateLeft((long) (hashCode * C1), 15))).nextInt();
}
public static int smear(long hashCode, int mixer) {
return new Random((int) (C2 *
Long.rotateLeft((long) (hashCode * C1+mixer), 15))).nextInt();
}

LinkedList<double[]> l = new LinkedList<double[]>();
LinkedList<Integer> sos = new LinkedList<Integer>();
static ByteBuffer b = ByteBuffer.allocate(10_000_000);


static double[][] idk = new double[9][9];
static PerlinNoise perlin = new PerlinNoise(
    12345L,   // seed
    50.0,     // scale
    3,        // octaves
    0.5,      // persistence
    2.0       // lacunarity
);
        
static{
    for(int i = 1; i < 8; i++){
        for(int j = 1; j < 8; j++){
            idk[i][j] = 2.5*(Math.random()-Math.random());
        }
    }
}
static double mapx(int i, int j){
    return i*4;
}
static double mapy(int i, int j){
    return j*4;
}
static double mapz(int i, int j){
    // int dx = (i-4);
    // int dy = (j-4);
    // double ee = Math.max(0,4 - Math.sqrt(dx*dx+dy*dy));
    // return idk[i][j];
    return perlin.noise(i*4+parkerval, j*4+parkerval2)*40;
}



static Object[] e3(){
    Random r = new Random();
    LinkedList<double[]> l = new LinkedList<double[]>();
    LinkedList<Integer> sos = new LinkedList<Integer>();
    // sub(l,sos);
    double ang = 0;
    double[] fuckme = new double[64*4];
    for(int i = 0; i < 64*4; i++){
        fuckme[i] = Math.random()*4;
    }
    for(int i = 0; i < 64; i++){
        int idk = 5;
        int kk = 8;
        double slice = ((Math.PI*2)/4)/8;
        {
            int ii = i%8;
            int jj = i/8;
            int so = (8-jj-1)*8+ii;
            double[] t = new double[]{
                mapx(ii+1,jj+1), mapz(ii+1,jj+1), mapy(ii+1,jj+1),
                mapx(ii+0,jj+1), mapz(ii+0,jj+1), mapy(ii+0,jj+1),
                mapx(ii+1,jj+0), mapz(ii+1,jj+0), mapy(ii+1,jj+0),
            };
            double[] t2 = new double[]{
                mapx(ii+1,jj+0), mapz(ii+1,jj+0),mapy(ii+1,jj+0), 
                mapx(ii+0,jj+1), mapz(ii+0,jj+1),mapy(ii+0,jj+1), 
                mapx(ii+0,jj+0), mapz(ii+0,jj+0),mapy(ii+0,jj+0), 
            };
            // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
            int sca = 1;
            int dist = 2;
            // scale(t, sca, sca, sca);
            // translate(t, dist, 0,0 );
            sos.push(so);
            sos.push(so);
            sos.push(so);
            l.push(t);
            l.push(t2);
            // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
            // scale(t2, sca, sca, sca);
            // translate(t2, dist, 0,0 );

            sos.push(so);
            sos.push(so);
            sos.push(so);
        }
    }
    System.out.println("Hey hey hey hey hey");
    // }
    // int val = (int)(20 * Math.random())+1;
    // for(int i = 0; i < val; i++){
    //     ang = i*1.0/val * Math.PI * 2; 
    //     double[] t = new double[]{
    //         0,0,0,
    //         0,100,0,
    //         Math.cos(ang)*5,0,Math.sin(ang)*5
    //     };
    //     int te = 0;
    //     sos.push(te);
    //     sos.push(te);
    //     sos.push(te);
    //     l.push(t);
    // }
    int[] array = new int[sos.size()];
    int e = 0; 
    for (var v : sos){
        array[e++] = v; 
    }

    double[] dd = unwrap(l);

    // double z721484 = Math.random()-Math.random();
    // double z721484 = 0;
    // double z65793 = z13560306();
    // scale(dd, Math.pow(10,z721484 *z65793)); 
    scale(dd,4);
    translate(dd, -64, 0, -64);

    // translate(dd, forcemovex, forcemovey, forcemovez);

    return new Object[]{
        dd,
        array
    };
}

static int s = 64;

public static <T> T[] addAll(T[] a, T[] b) {
    if (a == null) {
        return b == null ? null : b.clone();
    } else if (b == null) {
        return a.clone();
    }
    int aLen = a.length;
    int bLen = b.length;

    @SuppressWarnings("unchecked")
    T[] result = (T[]) Array.newInstance(a.getClass().getComponentType(), aLen + bLen);
    System.arraycopy(a, 0, result, 0, aLen);
    System.arraycopy(b, 0, result, aLen, bLen);
    return result;
}
public static int[] addAll(int[] a, int[] b) {
    if (a == null) {
        return b == null ? null : b.clone();
    } else if (b == null) {
        return a.clone();
    }
    int aLen = a.length;
    int bLen = b.length;
    int[] result = new int[aLen + bLen];
    System.arraycopy(a, 0, result, 0, aLen);
    System.arraycopy(b, 0, result, aLen, bLen);
    return result;
}

public static double[] addAll(double[] a, double[] b) {
    if (a == null) {
        return b == null ? null : b.clone();
    } else if (b == null) {
        return a.clone();
    }
    int aLen = a.length;
    int bLen = b.length;
    double[] result = new double[aLen + bLen];
    System.arraycopy(a, 0, result, 0, aLen);
    System.arraycopy(b, 0, result, aLen, bLen);
    return result;
}
public static int parkerval = 32;
public static int parkerval2 = -64;
public static ByteBuffer main(String[] args){
    b.clear();
    Object[] e2 = null;
    e2 = e3();
    double[] d = addAll((double[])(e2[0]),new double[0]);
    int[] sos =  addAll((int[])(e2[1]),new int[0]);

    b.putInt(d.length/3); // how many POINTS (not tris)
    b.putInt(8);//subdiv count // how subdivided is the texture??

    {
        b.putInt(1); // TEXTURE Flag = No/ YES CLIPBOARD READ
        b.putInt(128);
        b.putInt(128);
        b.putInt(9999); // ???
        Random r = new Random();
        int[] dd = new int[128*128];
        for(int x = 0; x < 128; x++){
            for(int z = 0; z < 128; z++){
            // dd[i] = 0xff_00_00_00 | (r.nextInt());

                int myval =  ((int)(-70+200*(0.5+0.5*perlin.noise(x/4+parkerval,z/4+parkerval2))));
                // if(myval < 80 || myval > 160)System.out.println(myval);
                PerlinNoise.thingy(x/4+parkerval, z/4+parkerval2);
                // dd[(x)+(127-z)*128] =  0xff_00_00_00 | myval;
                
                
                
                int k = 24; // for example, random power between 2^0 and 2^10
                int exponent = ThreadLocalRandom.current().nextInt(k + 1);
                int randomPower = (1 << exponent);
                int andthen = ~randomPower;
                
                dd[(x)+(127-z)*128] =  0xff_00_00_00 | (PerlinNoise.terrainColor(myval).toInt() & andthen);

                /* DEVIATE THE COLOR HERE BY BOMBARDING IT WITH RADIATION CHANCES  */
                
            }
        }
        // int[] dd = new int[]{0xff_00_00_00 | (r.nextInt())};
        // p("ThreeDumb: dd " + dd.length + " ");
        putIntArr(dd, b);
    }
    b.putInt(9999); // ???

    b.putInt(1); // NO SOS, all 0's
    putIntArr(sos,b);
    p("ThreeDumb: sos " + sos.length + " ");

    System.out.println("ThreeDumb: d again " + d.length + " ");
    // System.exit(0);
    putDoubleArr(d, b);
    return b;
}
public static void p(Object o){
// System.out.println(o);
}

static double[] scale(double[] d, double sx, double sy, double sz){
for(int i = 0; i < d.length; i++){
double s = 1.0;
if(i%3==0)s=sx;
if(i%3==1)s=sy;
if(i%3==2)s=sz;
d[i]*=s;
}
return d;
}
static double[] scale(double[] d, double s){
return scale(d, s,s,s);
}
static double[] translate(double[] d, double sx, double sy, double sz){
for(int i = 0; i < d.length; i++){
double s = 0.0;
if(i%3==0)s=sx;
if(i%3==1)s=sy;
if(i%3==2)s=sz;
d[i]+=s;
}
return d;
}
}
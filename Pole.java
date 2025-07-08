import java.awt.image.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.nio.*;
import java.util.function.*;
import java.lang.reflect.Array;
import java.awt.*;
import java.awt.datatransfer.*;

class Pole{
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
static ByteBuffer b = ByteBuffer.allocate(100_000_000);


static double mapx(int i, int j){
    return i*5;
}
static double mapy(int i, int j){
    return j*5;
}
static double mapz(int i, int j){
    int dx = (i-4);
    int dy = (j-4);
    double ee = Math.max(0,4 - Math.sqrt(dx*dx+dy*dy));
    return ee*10;
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
            int superslot = 0;
            int ii = i%8;
            int jj = i/8;
            int so = (8-jj-1)*16+ii;
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
            int dist = 0;
            scale(t, sca, sca, sca);
            translate(t, dist, 0,0 );
            translate(t, 0, 40,0 );
            sos.push(so);
            sos.push(so);
            sos.push(so);
            l.push(t);
            l.push(t2);
            // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
            scale(t2, sca, sca, sca);
            translate(t2, dist, 0,0 );
            translate(t2, 0, 40,0 );

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
    // scale(dd,15);
    translate(dd, 0, -5.9, 0);

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

public static BufferedImage imageToBufferedImage(Image im) {
    BufferedImage bi = new BufferedImage
        (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
    Graphics bg = bi.getGraphics();
    bg.drawImage(im, 0, 0, null);
    bg.dispose();
    return bi;
}
public static ByteBuffer main(String[] args){
    b.clear();
    Object[] e2 = null;
    e2 = Pole.SimpleTriCurved.e3();

    double[] d = (double[])(e2[0]);
    int[] sos = (int[])(e2[1]);
    e2 = Pole.SimpleTriCurved2.e3();
    d = addAll(d,(double[])(e2[0]));
    sos = addAll(sos,(int[])(e2[1]));


    b.putInt(d.length/3); // how many POINTS (not tris)
    b.putInt(8);//subdiv count // how subdivided is the texture??

    {
        // BufferedImage bi = null;
        // try{
        //     Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        //     Transferable t = clipboard.getContents(null);
        //     Image i = (Image)t.getTransferData(DataFlavor.imageFlavor);
        //     bi = imageToBufferedImage(i);
        // }catch(Exception e){
        //     e.printStackTrace();
        //     System.exit(0);
        // }

        b.putInt(1); //data mode yes
        b.putInt(32);
        b.putInt(128);
        b.putInt(9999); // ???
        Random r = new Random();
        // int[] dd = new int[128*128];
        // for(int x = 0; x < 128; x++){
        //     for(int z = 0; z < 128; z++){
        //         dd[(x)+(127-z)*128] =  0xff_00_00_00 | r.nextInt();
        //     }
        // }
        int[] dd = Math.random() > 0.5 ? PolePaint.main(null) : TowerPaint.main(null);
        putIntArr(dd, b);
    }
    b.putInt(9999); // ???

    b.putInt(1); // NO SOS, all 0's
    putIntArr(sos,b);
    p("ThreeDumb: sos " + sos.length + " ");

    p("ThreeDumb: d again " + d.length + " ");
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
static class SimpleTriCurved{
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
    static ByteBuffer b = ByteBuffer.allocate(100_000_000);
    
    
    static double mapx(int i, int j){
        return i;
    }
    static double mapy(int i, int j){
        return 4*j;
    }
    static double mapz(int i, int j){
        return i;
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
                int so = (88 - jj - 1)* 8+ii;
                double[] t = new double[]{
                    mapx(ii+1,jj+1), mapy(ii+1,jj+1), mapz(ii+1,jj+1),
                    mapx(ii+0,jj+1), mapy(ii+0,jj+1), mapz(ii+0,jj+1),
                    mapx(ii+1,jj+0), mapy(ii+1,jj+0), mapz(ii+1,jj+0),
                };
                double[] t2 = new double[]{
                    mapx(ii+1,jj+0), mapy(ii+1,jj+0), mapz(ii+1,jj+0),
                    mapx(ii+0,jj+1), mapy(ii+0,jj+1), mapz(ii+0,jj+1),
                    mapx(ii+0,jj+0), mapy(ii+0,jj+0), mapz(ii+0,jj+0),
                };
                // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
                // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
                // translate(t, 0 ,0,20);
                sos.push(so);
                sos.push(so);
                sos.push(so);
                l.push(t);
                l.push(t2);
                // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
                // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
                // translate(t2, 0 ,0,20);
    
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
        // scale(dd,15);
        translate(dd, 0, -5.9, 0);
    
        // translate(dd, forcemovex, forcemovey, forcemovez);
    
        return new Object[]{
            dd,
            array
        };
    }
    
    static int s = 64;
    
    public static ByteBuffer main(String[] args){
        b.clear();
        Object[] e2 = null;
        e2 = e3();
        double[] d = (double[])(e2[0]);
        int[] sos = (int[])(e2[1]);
    
        b.putInt(d.length/3); // how many POINTS (not tris)
        b.putInt(8);//subdiv count // how subdivided is the texture??
    
        {
            b.putInt(0); // TEXTURE Flag = No/ YES CLIPBOARD READ
            // b.putInt(1);
            // b.putInt(1);
            // b.putInt(9999); // ???
            // Random r = new Random();
            // int[] dd = new int[64*64];
            // for(int i = 0; i < dd.length; i++){
            //     dd[i] = 0xff_00_00_00 | (r.nextInt());
            // }
            // // int[] dd = new int[]{0xff_00_00_00 | (r.nextInt())};
            // // p("ThreeDumb: dd " + dd.length + " ");
            // putIntArr(dd, b);
        }
        b.putInt(9999); // ???
    
        b.putInt(1); // NO SOS, all 0's
        putIntArr(sos,b);
        p("ThreeDumb: sos " + sos.length + " ");
    
        p("ThreeDumb: d again " + d.length + " ");
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

    static class SimpleTriCurved2{
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
        static ByteBuffer b = ByteBuffer.allocate(100_000_000);
        
        
        static double mapx(int i, int j){
            return i;
        }
        static double mapy(int i, int j){
            return 4*j;
        }
        static double mapz(int i, int j){
            return 8-i;
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
                    int so = (88 - jj - 1)* 8+ii;
                    double[] t = new double[]{
                        mapx(ii+1,jj+1), mapy(ii+1,jj+1), mapz(ii+1,jj+1),
                        mapx(ii+0,jj+1), mapy(ii+0,jj+1), mapz(ii+0,jj+1),
                        mapx(ii+1,jj+0), mapy(ii+1,jj+0), mapz(ii+1,jj+0),
                    };
                    double[] t2 = new double[]{
                        mapx(ii+1,jj+0), mapy(ii+1,jj+0), mapz(ii+1,jj+0),
                        mapx(ii+0,jj+1), mapy(ii+0,jj+1), mapz(ii+0,jj+1),
                        mapx(ii+0,jj+0), mapy(ii+0,jj+0), mapz(ii+0,jj+0),
                    };
                    // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
                    // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
                    // translate(t, 0 ,0,20);
                    sos.push(so);
                    sos.push(so);
                    sos.push(so);
                    l.push(t);
                    l.push(t2);
                    // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
                    // translate(t, 0 ,(i/kk)*5+5*Math.sin(slice*(i%kk)),-(i%kk)*5);
                    // translate(t2, 0 ,0,20);
        
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
            // scale(dd,15);
            translate(dd, 0, -5.9, 0);
        
            // translate(dd, forcemovex, forcemovey, forcemovez);
        
            return new Object[]{
                dd,
                array
            };
        }
        
        static int s = 64;
        
        public static ByteBuffer main(String[] args){
            b.clear();
            Object[] e2 = null;
            e2 = e3();
            double[] d = (double[])(e2[0]);
            int[] sos = (int[])(e2[1]);
        
            b.putInt(d.length/3); // how many POINTS (not tris)
            b.putInt(8);//subdiv count // how subdivided is the texture??
        
            {
                b.putInt(0); // TEXTURE Flag = No/ YES CLIPBOARD READ
                // b.putInt(1);
                // b.putInt(1);
                // b.putInt(9999); // ???
                // Random r = new Random();
                // int[] dd = new int[64*64];
                // for(int i = 0; i < dd.length; i++){
                //     dd[i] = 0xff_00_00_00 | (r.nextInt());
                // }
                // // int[] dd = new int[]{0xff_00_00_00 | (r.nextInt())};
                // // p("ThreeDumb: dd " + dd.length + " ");
                // putIntArr(dd, b);
            }
            b.putInt(9999); // ???
        
            b.putInt(1); // NO SOS, all 0's
            putIntArr(sos,b);
            p("ThreeDumb: sos " + sos.length + " ");
        
            p("ThreeDumb: d again " + d.length + " ");
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
    static class PolePaint {

        // --- Utility for clipboard image transfer ---
        static class TransferableImage implements Transferable {
            private Image image;
            public TransferableImage(Image image) {
                this.image = image;
            }
            public Object getTransferData(DataFlavor flavor)
                    throws UnsupportedFlavorException, IOException {
                if (flavor.equals(DataFlavor.imageFlavor)) {
                    return image;
                } else {
                    throw new UnsupportedFlavorException(flavor);
                }
            }
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor.equals(DataFlavor.imageFlavor);
            }
        }

        static void dumpClipboard(BufferedImage bi2) {
            try {
                TransferableImage trans = new TransferableImage(bi2);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(trans, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        static BufferedImage makeBI(int w, int h) {
            return new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        }

        // Helper to pack ARGB values into an int
        static int argb(int a, int r, int g, int b) {
            return ((a & 0xFF) << 24) | ((r & 0xFF) << 16)
                    | ((g & 0xFF) << 8) | (b & 0xFF);
        }

        // --- Define base colors ---
        static final int BLACK           = argb(0,   0,   0,   0);
        static final int BG              = argb(0,  0,  0,  0); // dark background

        // Metal tower colors
        static final int METAL_BASE      = argb(255, 120, 120, 120); // base metal grey
        static final int METAL_HIGHLIGHT = argb(255, 180, 180, 180); // lighter metal for bands & spire
        static final int RUST            = argb(255, 150,  75,   0); // rusty orange-brown

        public static int[] main(String[] args) {
            int width  = 32;
            int height = 128;
            int[] image = new int[width * height];
            Arrays.fill(image, BG);

            // We'll use this mask to mark pixels that belong to the tower
            boolean[] towerMask = new boolean[width * height];

            // Draw a black border around the image
            for (int x = 0; x < width; x++) {
                image[x] = BLACK;
                image[x + (height - 1) * width] = BLACK;
            }
            for (int y = 0; y < height; y++) {
                image[0 + y * width] = BLACK;
                image[(width - 1) + y * width] = BLACK;
            }

            Random rand = new Random();

            // --- Stage 1: Draw the Tower Body ---
            // We'll make a tower that stretches from near the bottom to a top level.
            int bottomY = height - 2;
            int topY = 20;  // tower body spans from y = bottomY down to y = topY
            // Choose a center X with a slight random horizontal offset (ensuring safe margins)
            int centerX = width / 2 + rand.nextInt(5) - 2;
            centerX = clamp(centerX, 5, width - 5);

            // For a tapered tower, let the tower’s half-width vary with height:
            // At the bottom it will be wider (half-width = 3) and at the top narrower (half-width = 2)
            int bottomHalfWidth = 3; // half-width at bottom (total width ≈ 7 pixels)
            int topHalfWidth = 2;    // half-width at top (total width ≈ 5 pixels)

            // Draw each horizontal row of the tower body
            for (int y = bottomY; y >= topY; y--) {
                double fraction = (double)(y - topY) / (double)(bottomY - topY);
                int baseHalfWidth = (int) Math.round(topHalfWidth + (bottomHalfWidth - topHalfWidth) * fraction);
                // Add a little randomness to the tower’s edge for an irregular look
                int randomOffset = rand.nextInt(3) - 1;  // -1, 0, or 1
                int halfWidth = clamp(baseHalfWidth + randomOffset, 1, 4);
                for (int x = centerX - halfWidth; x <= centerX + halfWidth; x++) {
                    if (x > 0 && x < width - 1) {
                        int shadeOffset = rand.nextInt(31) - 15;
                        int towerColor = varyColor(METAL_BASE, shadeOffset, shadeOffset, shadeOffset);
                        image[x + y * width] = towerColor;
                        towerMask[x + y * width] = true;
                    }
                }
                // Every 10 pixels (roughly a "floor") add a horizontal band in a lighter metal
                if ((y - topY) % 10 == 0) {
                    for (int x = centerX - halfWidth; x <= centerX + halfWidth; x++) {
                        if (x > 0 && x < width - 1) {
                            image[x + y * width] = METAL_HIGHLIGHT;
                            towerMask[x + y * width] = true;
                        }
                    }
                }
            }

            // --- Stage 2: Build the Spire ---
            // Extend a narrow spire from the top of the tower body upward.
            int spireTopY = 5;  // spire will run from y = topY - 1 up to spireTopY
            for (int y = topY - 1; y >= spireTopY; y--) {
                // The spire is drawn as a narrow column centered at centerX
                for (int x = centerX - 1; x <= centerX + 1; x++) {
                    if (x > 0 && x < width - 1) {
                        int shadeOffset = rand.nextInt(31) - 15;
                        int spireColor = varyColor(METAL_HIGHLIGHT, shadeOffset, shadeOffset, shadeOffset);
                        image[x + y * width] = spireColor;
                    }
                }
            }
            // A small crown detail at the very top of the spire
            int crownY = spireTopY - 1;
            if (crownY > 0) {
                image[centerX + crownY * width] = METAL_HIGHLIGHT;
                if (centerX - 1 > 0) image[(centerX - 1) + crownY * width] = METAL_HIGHLIGHT;
                if (centerX + 1 < width) image[(centerX + 1) + crownY * width] = METAL_HIGHLIGHT;
            }

            // --- Stage 3: Add Rust Patches and Weathering ---
            // Iterate over the tower area and randomly change some tower pixels to a rust color.
            int rustChance = 10; // roughly a 10% chance for a given tower pixel to get rusted
            for (int y = topY; y <= bottomY; y++) {
                for (int x = centerX - bottomHalfWidth - 2; x <= centerX + bottomHalfWidth + 2; x++) {
                    if (x > 0 && x < width - 1) {
                        int idx = x + y * width;
                        if (towerMask[idx] && rand.nextInt(100) < rustChance) {
                            image[idx] = RUST;
                        }
                    }
                }
            }
            return image;
            // // --- Convert our pixel array to a BufferedImage and copy to clipboard ---
            // BufferedImage bi = makeBI(width, height);
            // for (int j = 0; j < height; j++) {
            //     for (int i = 0; i < width; i++) {
            //         bi.setRGB(i, j, image[i + j * width]);
            //     }
            // }

            // dumpClipboard(bi);
            // System.out.println("Generated wild metal country tower. Copied to clipboard!");
        }

        // --- Helpers for color variation ---
        static int varyColor(int baseColor, int dr, int dg, int db) {
            int a = (baseColor >> 24) & 0xFF;
            int r = (baseColor >> 16) & 0xFF;
            int g = (baseColor >> 8)  & 0xFF;
            int b = (baseColor      ) & 0xFF;

            r = clamp(r + dr, 0, 255);
            g = clamp(g + dg, 0, 255);
            b = clamp(b + db, 0, 255);

            return argb(a, r, g, b);
        }

        static int darkerColor(int baseColor, int amount) {
            int a = (baseColor >> 24) & 0xFF;
            int r = (baseColor >> 16) & 0xFF;
            int g = (baseColor >> 8)  & 0xFF;
            int b = (baseColor      ) & 0xFF;

            r = clamp(r - amount, 0, 255);
            g = clamp(g - amount, 0, 255);
            b = clamp(b - amount, 0, 255);

            return argb(a, r, g, b);
        }

        static int clamp(int val, int min, int max) {
            return Math.min(max, Math.max(min, val));
        }
    }
    static class TowerPaint{
            
        static class TransferableImage implements Transferable {
            private Image image;
            public TransferableImage(Image image) {
                this.image = image;
            }
            public Object getTransferData(DataFlavor flavor)
                    throws UnsupportedFlavorException, IOException {
                if (flavor.equals(DataFlavor.imageFlavor)) {
                    return image;
                } else {
                    throw new UnsupportedFlavorException(flavor);
                }
            }
            public DataFlavor[] getTransferDataFlavors() {
                return new DataFlavor[]{DataFlavor.imageFlavor};
            }
            public boolean isDataFlavorSupported(DataFlavor flavor) {
                return flavor.equals(DataFlavor.imageFlavor);
            }
        }

        static void dumpClipboard(BufferedImage bi2) {
            try {
                TransferableImage trans = new TransferableImage(bi2);
                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(trans, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        static BufferedImage makeBI(int w, int h) {
            return new BufferedImage(w, h, BufferedImage.TYPE_4BYTE_ABGR);
        }

        static int argb(int a, int r, int g, int b) {
            return ((a & 0xFF) << 24) | ((r & 0xFF) <<16) | ((g & 0xFF)<<8) | (b & 0xFF);
        }

        static int clamp(int val, int min, int max) {
            return Math.min(max, Math.max(min, val));
        }

        static int varyColor(int baseColor, int dr, int dg, int db) {
            int a = (baseColor >> 24) & 0xFF;
            int r = (baseColor >> 16) & 0xFF;
            int g = (baseColor >>  8) & 0xFF;
            int b = (baseColor      ) & 0xFF;

            r = clamp(r + dr, 0, 255);
            g = clamp(g + dg, 0, 255);
            b = clamp(b + db, 0, 255);

            return argb(a, r, g, b);
        }

        static int darkerColor(int baseColor, int amount) {
            int a = (baseColor >> 24) & 0xFF;
            int r = (baseColor >> 16) & 0xFF;
            int g = (baseColor >>  8) & 0xFF;
            int b = (baseColor      ) & 0xFF;

            r = clamp(r - amount, 0, 255);
            g = clamp(g - amount, 0, 255);
            b = clamp(b - amount, 0, 255);

            return argb(a, r, g, b);
        }

        // You can change these ranges or create more elaborate color picking.
        static int randomBaseColor(Random rand) {
            // For example, pick a random hue leaning toward green–brown range
            // Or just do any color. Let's do something broad:
            int r = 20 + rand.nextInt(80);  // 20..99
            int g = 80 + rand.nextInt(130); // 80..209
            int b = 20 + rand.nextInt(80);  // 20..99
            return argb(255, r, g, b);
        }

        static int randomBrightColor(Random rand) {
            // Often used for symbols
            int r = 200 + rand.nextInt(56); // 200..255
            int g = 200 + rand.nextInt(56);
            int b = 40  + rand.nextInt(216);
            return argb(255, r, g, b);
        }

        public static int[] main(String[] args) {
            final int width  = 32;
            final int height = 128;

            // Create the image array & fill with dark background
            int[] image = new int[width * height];
            Arrays.fill(image, argb(0, 0, 0, 0)); // dark grayish BG

            // Draw black border
            for (int x = 0; x < width; x++) {
                image[x] = argb(0, 0, 0, 0);
                image[x + (height - 1) * width] = argb(0, 0, 0, 0);
            }
            for (int y = 0; y < height; y++) {
                image[y * width] = argb(0, 0, 0, 0);
                image[(width - 1) + y * width] = argb(0, 0, 0, 0);
            }

            Random rand = new Random();

            // Possibly draw one or two towers.
            drawRandomTower(image, width, height, rand);

            // 30% chance to draw a second tower next to it, smaller
            if (rand.nextInt(10) < 3) {
                drawRandomTower(image, width, height, rand);
            }
            return image;

            // // Convert array to BufferedImage
            // BufferedImage bi = makeBI(width, height);
            // for (int j = 0; j < height; j++) {
            //     for (int i = 0; i < width; i++) {
            //         bi.setRGB(i, j, image[i + j * width]);
            //     }
            // }

            // // Copy to system clipboard
            // dumpClipboard(bi);
            // System.out.println("Generated one or two random towers. Copied to clipboard!");
        }

        // Draws a single tower with random attributes onto the image array
        static void drawRandomTower(int[] image, int width, int height, Random rand) {
            // Pick random base color for the tower
            int towerBase   = randomBaseColor(rand);
            // Then pick or derive complementary or variant colors
            int towerStripe = varyColor(towerBase, rand.nextInt(50), rand.nextInt(50), rand.nextInt(50));
            int towerSymbol = randomBrightColor(rand);
            int towerTop    = varyColor(towerBase, rand.nextInt(40)-20, rand.nextInt(40)-20, rand.nextInt(40)-20);

            // Choose random tower sizes
            int towerBottomWidth = 12 + rand.nextInt(10);  // 12..21
            int towerTopWidth    = 6  + rand.nextInt(8);   // 6..13
            int towerHeight      = (int) (height * 0.4 + rand.nextInt(40)); // about 40% to 40%+40 px
            int towerBottomY     = height - 2;
            int towerTopY        = towerBottomY - towerHeight;
            if (towerTopY < 2) towerTopY = 2;

            // Random horizontal center, ensuring it won't go out of bounds
            // We’ll ensure at least towerBottomWidth/2 padding on each side
            int towerCenterX = towerBottomWidth/2 + rand.nextInt(width - towerBottomWidth - 1);

            // Fill tower from bottom to top
            for (int y = towerBottomY; y >= towerTopY; y--) {
                // Interpolate row width from bottom to top
                double t = (double)(towerBottomY - y) / (towerHeight);
                int rowWidth = (int)(towerBottomWidth + t * (towerTopWidth - towerBottomWidth));

                int leftX  = towerCenterX - rowWidth/2;
                int rightX = leftX + rowWidth;

                for (int x = leftX; x < rightX; x++) {
                    if (x > 0 && x < width - 1 && y > 0 && y < height - 1) {
                        // Slight random shading offset
                        int shade = rand.nextInt(25) - 12;
                        int towerColor = varyColor(towerBase, shade, shade, shade);
                        image[x + y * width] = towerColor;
                    }
                }
            }

            int turretHeight = 4 + rand.nextInt(4); // 4..7
            int ringWidth    = towerTopWidth + (2 + rand.nextInt(4)); // 2..5 extra
            for (int y = towerTopY - turretHeight; y < towerTopY; y++) {
                int leftX  = towerCenterX - ringWidth/2;
                int rightX = leftX + ringWidth;

                for (int x = leftX; x < rightX; x++) {
                    if (x > 0 && x < width - 1 && y > 0 && y < height - 1) {
                        int shade = rand.nextInt(20) - 10;
                        int turretColor = varyColor(towerTop, shade, shade, shade);
                        image[x + y * width] = turretColor;
                    }
                }
            }

            // Random stripes
            int stripeCount = 1 + rand.nextInt(4);
            for (int s = 0; s < stripeCount; s++) {
                // random Y coordinate within tower
                if (towerHeight < 5) break; // if the tower is very short, skip
                int stripeY = towerTopY + rand.nextInt(Math.max(1, towerHeight - 2));
                // random thickness
                int thickness = 1 + rand.nextInt(2);
                // compute row width at that Y
                double t = (double)(towerBottomY - stripeY) / towerHeight;
                int rowWidth = (int)(towerBottomWidth + t * (towerTopWidth - towerBottomWidth));

                int leftX  = towerCenterX - rowWidth/2;
                int rightX = leftX + rowWidth;
                for (int y = stripeY; y < stripeY + thickness; y++) {
                    for (int x = leftX; x < rightX; x++) {
                        if (x > 0 && x < width - 1 && y > 0 && y < height - 1) {
                            image[x + y * width] = towerStripe;
                        }
                    }
                }
            }

            // Random symbols / panels
            int symbolCount = 2 + rand.nextInt(6);
            for (int i = 0; i < symbolCount; i++) {
                // random Y in tower range
                int sy = towerTopY + 1 + rand.nextInt(Math.max(1, towerHeight - 2));
                // compute row width at sy
                double t = (double)(towerBottomY - sy) / towerHeight;
                int rowWidth = (int)(towerBottomWidth + t * (towerTopWidth - towerBottomWidth));
                int leftX  = towerCenterX - rowWidth/2;
                int rightX = leftX + rowWidth;

                // pick random x in that row
                if (rowWidth > 2) {
                    int sx = leftX + 1 + rand.nextInt(Math.max(1, rowWidth - 2));
                    // small 2×2 or 3×2 shape
                    int w = 2 + rand.nextInt(2);
                    int h = 2 + rand.nextInt(2);

                    for (int yy = sy; yy < sy + h; yy++) {
                        for (int xx = sx; xx < sx + w; xx++) {
                            if (xx > 0 && xx < width - 1 && yy > 0 && yy < height - 1) {
                                image[xx + yy * width] = towerSymbol;
                            }
                        }
                    }
                }
            }

            // Cracks/chips
            int crackCount = 10 + rand.nextInt(15);
            for (int c = 0; c < crackCount; c++) {
                int cx = 1 + rand.nextInt(width - 2);
                int cy = 1 + rand.nextInt(height - 2);
                int oldColor = image[cx + cy * width];
                // If not the BG or black border, we can do something to it
                if (oldColor != argb(255, 20, 20, 20) && oldColor != argb(0, 0, 0, 0)) {
                    if (rand.nextBoolean()) {
                        // carve a hole
                        image[cx + cy * width] = argb(255, 20, 20, 20);
                    } else {
                        // darken
                        image[cx + cy * width] = darkerColor(oldColor, 40);
                    }
                }
            }
        }
    }

}
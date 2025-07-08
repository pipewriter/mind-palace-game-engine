import java.awt.image.*;
import java.util.*;
import java.nio.*;
import java.util.function.*;




public class PlantRock{
static double[] unwrap(LinkedList<double[]> d){
    // if(d.size() == 0){
    //     System.out.println("doing the strange code");
    //     return new double[0];
    // } 
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









static void z1357933 (double[] z14836992, double z7434610, int s1, int s2){
for(int i = 0; i < z14836992.length/3; i++){
double x = z14836992[i*3+s1];
double y = z14836992[i*3+s2];
//double z = z14836992[i*3+2];
double a = Math.atan2(x, y);
double ang = a + z7434610;
double di = Math.sqrt(x*x+y*y);
z14836992[i*3+s1] = Math.sin(ang)*di;
z14836992[i*3+s2] = Math.cos(ang)*di;
}
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




public static void main2(String[] args){
Function<Object[],Object[]> f =
x -> new Object[]{(Integer)x[0]+3};

var e =f.apply(new Object[]{2});
System.out.println(e[0]);

z59376 f2 = x-> tup((Integer)x[0]+3);
}
static double i(Object o){
return (Integer)o;
}
static double d(Object o){
return (Double)o;
}
static double z1265021 ( double x){ return x*x;}
static double z12895172 ( double x){ return Math.sqrt(x); }

static double z810077 ( double x){ return Math.sin(x); }
static double z3065800 ( double x){ return Math.cos(x); }
static Object[] z2809911 (Object ...o){  return o; }
static Object[] tup(Object ...o){
return o;
}

static interface z59376 extends Function<Object[],Object[]>{
}

// z527195

//based





//static class z13560306{
//family
//main seed
//deviation seed
//}

static Random r = null;
public static int secret_numer = 0;
public static void z11617219 (int z8448343 ){ 
//p("hola");
r = new Random(secret_numer*666 + z8448343*1488 );
}

//static Random r = new Random(11);
static Random r2 = new Random();
static double z13560306 (){
return r.nextDouble()*0.8+r2.nextDouble()*0.2+0.1;
}
static int z13560306 (int i){
return r.nextInt(i);
}
static int z13560306_(){
int i =  r.nextInt();
int spot = r2.nextInt(24);
int dmg = 1<<spot;
int inv = 0xff_ff_ff ^ dmg;
return (i & inv) | (r2.nextInt(2)<<spot);
}
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

// z5248432
// z6461423  z14903280  z10285094 

static Object[] e2(){
    //My guess is rock. Yes, it's a rock
LinkedList<double[]> l = new LinkedList<double[]>();
LinkedList<Integer> sos = new LinkedList<Integer>();
Random r = new Random();

double z7434610 = Math.PI*2;
int z5890402 = z13560306(10)+4;//(r.nextInt(20)+3);//0.05+r.nextInt(20)/20.0;
int z3056052 = z13560306(10)+4;//(r.nextInt(20)+3);//0.05+r.nextInt(20)/20.0;
double z2350330 = z13560306()/2+Math.random()/2;

double z14800373 = Math.random()*z7434610;

double x1 =z12895172( z13560306());
double y1 =z12895172( z13560306());
double z1 =z12895172( z13560306());

double z11958356 = (x1+y1+z1)/2;

double z721484 = Math.random()-Math.random();
double z65793 = z13560306();

z59376 z7326889 = args ->{
double z14903280 = i(args[0]);
double z10285094 = i(args[1]);
z14903280= z14903280 * 1.0 / z3056052 *z7434610;
z10285094= z10285094 * 1.0 / z5890402 * z7434610 /2;

return z2809911(
z3065800(z14903280)*x1,
z810077(z14903280)*z3065800(z10285094)*y1,
z810077(z14903280)*z810077(z10285094)*z1
);
};
for(int z14903280 = 0; z14903280 < z3056052; z14903280 += 1){
for(int z10285094 = 0; z10285094 < z5890402; z10285094 += 1){

double[] t2 = tri(
z7326889.apply(tup(z14903280 + 1 * 0  , z10285094 + 1 * 0)),
z7326889.apply(tup(z14903280 + 1 * 1  , z10285094 + 1 * 0)),
z7326889.apply(tup(z14903280 + 1 * 0  , z10285094 + 1 * 1)));
double[] t3 = tri(
z7326889.apply(tup(z14903280 + 1 * 1  , z10285094 + 1 * 0)),
z7326889.apply(tup(z14903280 + 1 * 0  , z10285094 + 1 * 1)),
z7326889.apply(tup(z14903280 + 1 * 1  , z10285094 + 1 * 1)));

scale(t2,0.4);
scale(t3,0.4);

// scale(t2, Math.pow(10,z721484 *z65793)); 
// scale(t3, Math.pow(10,z721484 *z65793)); 

z1357933 ( t2, z2350330, 0, 1);
z1357933 ( t3, z2350330, 0, 1);

z1357933 (t2, z14800373, 0,2);
z1357933 (t3, z14800373, 0,2);
scale(t2, 2);
scale(t3, 2);
translate(t2, 0, -0.3+0.6 - z11958356*1, 0);
translate(t3, 0, -0.3+0.6 - z11958356*1, 0);
l.push(t2);
l.push(t3);
int ti = r.nextInt(64);
for(int i = 0; i < 6; i++)
sos.push(ti);
}}
return tup(scale(unwrap(l), 5,5,5), unwrap2(sos));
}




static void sub(
LinkedList<double[]> l, LinkedList<Integer> sos){
double phi = Math.PI * (3 - Math.sqrt(5));
int z2094724 = (int)z1265021 (z13560306()*10) ;

double stalk = 1;

double z1937030 = z13560306();
double z11156765 = z13560306();
double z14500998 = z13560306();

double z6384426 = z13560306()*2;

for(int i = 0; i < 1+z2094724; i++){
double z10170125 = 1 - (i *1.0 / (s - 1))*2;
double radius = Math.sqrt(1 - z10170125*z10170125)*z1937030;
double theta = phi * i;
if(z10170125 < Math.random()*z6384426 && l.size() != 0) continue;
double z1560017 = Math.cos(theta)*radius;
double z98340 = Math.sin(theta)*radius;

double[] t = new double[]{
0,0,0,
0,0.1,0,
z1560017*z11156765,z10170125 *z14500998,z98340*z11156765};
int te = (int)(Math.random()*64);
sos.push(te);
sos.push(te);
sos.push(te);
l.push(t);
}
// p("beeeeep fat");

}


//CLEARLY PLANTS
static Object[] e3(){
Random r = new Random();
LinkedList<double[]> l = new LinkedList<double[]>();
LinkedList<Integer> sos = new LinkedList<Integer>();
sub(l,sos);
int[] array = new int[sos.size()];
int e = 0; for (var v : sos){
array[e++] = v; 
}

double[] dd = unwrap(l);

double z721484 = Math.random()-Math.random();
double z65793 = z13560306();
scale(dd, Math.pow(10,z721484 *z65793)); 
scale(dd,15);
translate(dd, 0, -5.9*PlantRock.sizemod, 0);

return new Object[]{
dd,
array
};
}

static int treeleafquantity = 0;
static double treeleafradius = 0;
static Object[] e1(){
p("Doing Tree e1() - how many leaves?");
LinkedList<double[]> l = new LinkedList<double[]>();
LinkedList<Integer> sos = new LinkedList<Integer>();
double phi = Math.PI * (3 - Math.sqrt(5));
double ss = treeleafquantity;
//ss*=ss;
int s= (int)ss+1;

double xx = Math.random()*6;
double yy = Math.random()*6;
double zz = Math.random()*6;
// double r = Math.random()*4;

for(int i = 0; i < s; i++){
double y = 1- (i *1.0 / (s - 1))*2;
double radius = Math.sqrt(1 - y*y)*treeleafradius;
double theta = phi * i;

double x = Math.cos(theta)*radius;
double z = Math.sin(theta)*radius;

//if(Math.random() > y) continue;
double[] t = new double[]{
0,0,0,
0,0.1,0,
x,y,z};
translate(t,0,-1.2+10,0);
translate(t,xx,yy,zz);
{
    // lol was forgetting the stalk
    int te = (int)(Math.random()*64);
    sos.push(te);
    sos.push(te);
    sos.push(te);
}
l.push(new double[]{
    0,-1.2,1,
    0,-1.2,0,
    xx,-1.2+10+yy,0+zz
});
// l.push(t);
int te = (int)(Math.random()*64);
sos.push(te);
sos.push(te);
sos.push(te);
l.push(t);
}
int[] array = new int[sos.size()];
int e = 0; for (var v : sos){
array[e++] = v; 
}
// return scale(unwrap(l), 5,5,5);
return new Object[]{
    scale(unwrap(l), 5,5,5),
    array
};
}

// z3286056
static int s= 64;
 
public static int override_color_a;
public static int override_color_b;

public static double override_v1;
public static int override_v2;
public static double override_v3;
public static double override_v4;

public static int override_v5;
public static double override_v6;
public static double override_v7;
public static double override_v8;
public static double override_v9;

public static double sizemod;
public static int coloroverride;
static int[] doIt(){
    int[] store = new int[s*s];
    Random r = new Random();
    int cc1=z13560306_()|0xff_00_00_00;
    int cc2=z13560306_()|0xff_00_00_00;
    
    cc1 = coloroverride; // uhm black whatever
    cc2 = override_color_b; // uhm black whatever

    for(int j = 0; j < s; j++){
        for(int i = 0; i < s; i++){
            int c1 = cc1;//0xff_00_00_00;
            int c2 = cc2;//0xff_ff_ff_ff;
            double fixed = z13560306()*0.89+0.1;
            for(int e = 0; e < 24; e++){
                int k = (i)/24%24;
                int mask=masks[e];
                int inv = mask^0xff_ff_ff_ff;
                c1 = (c2&mask)|(c1&inv);
                //pix(bi, i, j, c1);2
                store[i*s+j] = c1;
                shuffleArray(masks);
                if(Math.random() < fixed) break;
            }
        }
    }
    return store;
}

static void wall(BufferedImage bi, int x, int c){
for(int i = 0; i < 512; i++){
pix(bi, x,i,c);
}
}
static void pix(BufferedImage bi, int x, int y, int c){
if(y>=0&&y<512){
bi.setRGB(x, y, c);
}
}


// z10176774   

static ByteBuffer b = ByteBuffer.allocate(1_000_000);
static Random lim = new Random(secret_numer) ;
public static double plantweight = lim.nextDouble()*4;
public static double treeweight = lim.nextDouble()*2;
public static double rockweight = lim.nextDouble()*3;
public static ByteBuffer main(String[] args){
b.clear();

// int z9364967 = Math.random()*4 > lim ? 1 : 0;


double roll = Math.random()*(plantweight+treeweight+rockweight);
int choice = 0;

Object[] e2 = null;
String outstring = "";
if(roll < plantweight){                  
    choice = 0;
    sizemod = PlantRock.override_v1;
    coloroverride = PlantRock.override_color_a;

    z11617219(choice);
    e2 = e3();
    outstring = "plant";} 
else if(roll < treeweight+plantweight ){ 
    choice = 1;
    sizemod = PlantRock.override_v4;
    coloroverride = PlantRock.override_v5;
    
    z11617219(choice);e2 = e1();outstring = "tree";}
else{                                    
    choice = 2;
    sizemod = PlantRock.override_v3;
    coloroverride = PlantRock.override_v2;

    z11617219(choice);e2=e2();outstring = "rock";}
p("ThreeDumb: making a " + outstring);

// e2 = e3();

double[] d = (double[])(e2[0]);
// System.out.println("d.length " + d.length);
int[] sos = (int[])(e2[1]);
p("ThreeDumb: d " + d.length + " (should be 3 div), sos " + sos.length);
b.putInt(d.length/3);
b.putInt(8);//subdiv count

{
    b.putInt(1); // TEXTURE Flag = No/ YES CLIPBOARD READ
    b.putInt(s);
    b.putInt(s);
    b.putInt(9999);

    int[] dd = doIt();
    p("ThreeDumb: dd " + dd.length + " ");
    putIntArr(dd, b);
}
b.putInt(9999);

b.putInt(1); // NO SOS, all 0's
putIntArr(sos,b);
p("ThreeDumb: sos " + sos.length + " ");

p("ThreeDumb: d again " + d.length + " ");
putDoubleArr(d, b);
return b;
// System.out.println(d.length);
// try {
// String fname = "./ThreeDumbPollFolder/3d";
// Path path = FileSystems.getDefault().getPath(fname);
// Files.write(path, b.array());
// } catch (Exception e) {
// e.printStackTrace();
// }
}


public static void p(Object o){
// System.out.println(o);
}


static double[] scale(double[] d, double sx, double sy, double sz){
for(int i = 0; i < d.length; i++){
double s = 1.0;
if(i%3==0)s=sx*PlantRock.sizemod;
if(i%3==1)s=sy*PlantRock.sizemod;
if(i%3==2)s=sz*PlantRock.sizemod;
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




static int[] masks = new int[24];
static{
for(int i = 0; i < masks.length; i++){
masks[i] = 1<<i;
}
shuffleArray(masks);
}
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
}
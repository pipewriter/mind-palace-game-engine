import java.nio.ByteBuffer;

class RGB {
    public int r;
    public int g;
    public int b;
    public RGB(int r, int g, int b){
        this.r = r;
        this.g = g;
        this.b = b;
    }
    public static RGB fromCombined(int rgb){
        int b = (rgb>>>0)&255;
        int g = (rgb>>>8)&255;
        int r = (rgb>>>16)&255;
        // System.out.println("HEHEHEH EHEH "+ r+" "+ g +" "+ b + "   " + rgb);
        return new RGB(r,g,b);
    }
    public int toInt(){
    return ((255<<24)|(r&255)<<16) |
    ((g&255)<<8) |
    ((b&255<<0));
    }
}
public class ColorGroup {
    public static ColorGroup black = new ColorGroup(0,0,0);
    public static ColorGroup red = new ColorGroup(255,0,0);
    public static ColorGroup fightergreen = new ColorGroup(48,204,40);
    public static ColorGroup evenmore = new ColorGroup(48,204,40, 224,127,0);
    public float r1;
    public float g1;
    public float b1;
    public float r2;
    public float g2;
    public float b2;
    public float r3;
    public float g3;
    public float b3;
    public float r4;
    public float g4;
    public float b4;
    public ColorGroup(
        RGB rgb){
        this.r1 = rgb.r;
        this.g1 = rgb.g;
        this.b1 = rgb.b;
        this.r2 = -1;
        this.g2 = -1;
        this.b2 = -1;
        this.r3 = -1;
        this.g3 = -1;
        this.b3 = -1;
        this.r4 = -1;
        this.g4 = -1;
        this.b4 = -1;
    }
    public ColorGroup(
        float r1,
        float g1,
        float b1){
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = -1;
        this.g2 = -1;
        this.b2 = -1;
        this.r3 = -1;
        this.g3 = -1;
        this.b3 = -1;
        this.r4 = -1;
        this.g4 = -1;
        this.b4 = -1;
    }
    public ColorGroup(
        float r1,
        float g1,
        float b1,
        float r2,
        float g2,
        float b2){
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
        this.r3 = -1;
        this.g3 = -1;
        this.b3 = -1;
        this.r4 = -1;
        this.g4 = -1;
        this.b4 = -1;
    }
    public ColorGroup(
        float r1,
        float g1,
        float b1,
        float r2,
        float g2,
        float b2,
        float r3,
        float g3,
        float b3){
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
        this.r3 = r3;
        this.g3 = g3;
        this.b3 = b3;
        this.r4 = -1;
        this.g4 = -1;
        this.b4 = -1;

    }
    public ColorGroup(
        float r1,
        float g1,
        float b1,
        float r2,
        float g2,
        float b2,
        float r3,
        float g3,
        float b3,
        float r4,
        float g4,
        float b4
    ){
        this.r1 = r1;
        this.g1 = g1;
        this.b1 = b1;
        this.r2 = r2;
        this.g2 = g2;
        this.b2 = b2;
        this.r3 = r3;
        this.g3 = g3;
        this.b3 = b3;
        this.r4 = r4;
        this.g4 = g4;
        this.b4 = b4;
    }
    void print(){
        System.out.println("Color: " + r1 + ", " + g1 + ", " + b1);
    }
    public int      c1(){
        int idkwtf = ((int)r1)<<16
              |((int)g1)<<8
              |((int)b1);
        // p(r1, g1, b1);
        // p(idkwtf);
        return idkwtf;
    }
    public int      c2(){
        return (int)r2<<16
              |(int)g2<<8
              |(int)b2;
    }
    public int      c3(){
        return (int)r3<<16
              |(int)g3<<8
              |(int)b3;
    }
    public int      c4(){
        return (int)r4<<16
              |(int)g4<<8
              |(int)b4;
    }
    public void put(ByteBuffer b){
        b.putFloat(r1);
        b.putFloat(g1);
        b.putFloat(b1);
        b.putFloat(r2);
        b.putFloat(g2);
        b.putFloat(b2);
        b.putFloat(r3);
        b.putFloat(g3);
        b.putFloat(b3);
        b.putFloat(r4);
        b.putFloat(g4);
        b.putFloat(b4);
    }
    public static ColorGroup get(ByteBuffer b){
        return new ColorGroup(
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat(),
            b.getFloat());
    }
    
}
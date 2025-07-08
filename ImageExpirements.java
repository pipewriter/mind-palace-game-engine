import java.awt.image.BufferedImage;
import java.io.File;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;
class ImageExpirements{
    static int PARKER_BUFFER_SIZE = 300_000_000;

    public static boolean flipper = false;
    public static long now = System.currentTimeMillis();
    public static long t(){
        if(flipper){
            now = System.currentTimeMillis();
        } else{
            now = System.currentTimeMillis() - now;
        }
        flipper = !flipper;
        return now;
    }
    
    public static void p(Object ... o){
        for(var oo: o){
            System.out.print(oo == null ? "null":oo.toString() +", ");
        }
        System.out.println();
    }
    public static long k(){
        flipper = false;
        long nu = System.currentTimeMillis();
        long ret = nu - now;
        now = nu;
        return ret;
    }
    public static void pk(){
        p(k());
    }
    public static void main2(String[] args) throws Exception{
        pk();
        p("hey");
        pk();
        Thread.sleep(1000);
        pk();

        
    }
    public static void main(String[] args) throws Exception{
        // to memory test
        // to memory race
        String[] ss = new String[]{
            "white3k",
            "covtest",
            "dumbshot",
            "working",
            "road"
        };
        for(var s: ss)simpleTime(s);
    }
    public static void simpleTime(String s) throws Exception{
        {
            p(s);
            k();
            buffToPix(s+".dat");
            pk();
            pngToPix(s+".png");
            pk();
            p("------");
        }
    }
    public static void main1(String[] args) throws Exception{
        // pngToBuff("covtest.png", "covtest.dat");
        // pngToBuff("dumbshot.png", "dumbshot.dat");
        // pngToBuff("white3k.png", "white3k.dat");
        pngToBuff("working.png", "working.dat");
        pngToBuff("road.png", "road.dat");
    }
    
    static BufferedImage makeBI(int w, int h){
        return new BufferedImage(
        w,h, BufferedImage.TYPE_4BYTE_ABGR);
    }
    public static void pixToPng(int[] pix, int width, String png) throws Exception{
        int height = pix.length/width;
        BufferedImage bi = makeBI(width, height);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                bi.setRGB(i, j, pix[i+j*width]);
            }
        }
        ImageIO.write(bi, "png", new File(png));
    }
    public static BufferedImage pixToBI(int[] pix, int width){
        int height = pix.length/width;
        BufferedImage bi = makeBI(width, height);
        for(int i = 0; i < width; i++){
            for(int j = 0; j < height; j++){
                bi.setRGB(i, j, pix[i+j*width]);
            }
        }
        return bi;
        // ImageIO.write(bi, "png", new File(png));
    }
    public static int[] biToPix(BufferedImage bi){
        int[] ia = new int[bi.getWidth()*bi.getHeight()];
        for(int i = 0; i < bi.getWidth(); i++){
            for(int j = 0; j < bi.getHeight(); j++){
                ia[i+j*bi.getWidth()] = bi.getRGB(i, j);
                // ia[i + j*bi.getWidth()] = 0xff_ff_00_ff;
                // System.out.println("");
            }
        }
        return ia;
        // ImageIO.write(bi, "png", new File(png));
    }
    static int width = 0;
    static int height = 0;
    public static int[] pngToPix(String fname) throws Exception{
        BufferedImage bi = ImageIO.read(new File(fname));
        width = bi.getWidth();
        height = bi.getHeight();
        int[] pixels_raw = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
        return pixels_raw;
    }
    public static void pngToBuff(String fname, String outname) throws Exception{
        int[] p = pngToPix(fname);
        ParkerBuffer pbuf = new ParkerBuffer(true, outname);
        pbuf.putInt(width);
        pbuf.putIntArr(p);
        pbuf.finalizeWritingFile();
    }
    public static BufferedImage pngToBI(String fname) throws Exception{
        // int[] p = pngToPix(fname);
        BufferedImage bi = ImageIO.read(new File(fname));
        return bi;
        // BufferedImage bi = pixToBI(p);
        // ParkerBuffer pbuf = new ParkerBuffer(true, outname);
        // pbuf.putInt(width);
        // pbuf.putIntArr(p);
        // pbuf.finalizeWritingFile();
    }
    public static void buffToPng(String fname, String outname) throws Exception{
        ParkerBuffer pbuf = new ParkerBuffer(false, fname);
        int width = pbuf.getInt();
        pixToPng(pbuf.getIntArr(), width, outname);
    }
    public static int[] buffToPix(String fname) throws Exception{
        ParkerBuffer pbuf = new ParkerBuffer(false, fname);
        int width = pbuf.getInt();
        return pbuf.getIntArr();
    }
}

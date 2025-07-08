import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.image.*;
import java.io.IOException;
import java.util.Random;
import java.io.*;
import javax.imageio.*;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.*;
import java.nio.file.Path;
import java.util.*;
import java.awt.datatransfer.*;

public class a2{

    static BiConsumer<BufferedImage, BiConsumer<Integer, Integer>> forEachPixel = (bi, callback) -> {
        int w = bi.getWidth();
        int h = bi.getHeight();
        for(int x = 0; x < w; x++){
            for(int y = 0; y < h; y++){
                callback.accept(x, y);
            }
        }
    };

    public static BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bi = new BufferedImage
            (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

//combine a b??
//collage a b??
//morph a b??
//shrink a b?
//what algo a b??
public static boolean goright = false;
public static BufferedImage _fff0000019471(BufferedImage a, BufferedImage b){
double _fff0000002307 =b.getWidth()*1.0/a.getWidth();
double _fff0000000689 = b.getHeight()*1.0/a.getHeight();
double _fff0000010370 = Math.abs(1-_fff0000002307);
double _fff0000010974 = Math.abs(1-_fff0000000689);
BufferedImage _fff0000004020 =null;

if(goright){
 //using _fff0000010370 
_fff0000004020=new BufferedImage((int)(a.getWidth()+b.getWidth()/_fff0000000689), a.getHeight(),
BufferedImage.TYPE_INT_ARGB);
int _fff0000011562 = (int)(b.getWidth()/_fff0000000689);
for(int x = 0; x < _fff0000011562; x++){
for(int y = 0; y < a.getHeight(); y++){
int _fff0000010481 = Math.min((int)(x*_fff0000000689), b.getWidth()-1);

_fff0000004020.setRGB(x+a.getWidth(),y,b.getRGB(_fff0000010481, Math.min((int)(y*_fff0000000689), b.getHeight()-1))); 
}}

}else{
//width is closer, stack vertically
 //using _fff0000010974
 int _fff0000003095 = (int)(b.getHeight()/_fff0000002307);
 _fff0000004020=new BufferedImage(a.getWidth(), (int)(a.getHeight()+b.getHeight()/_fff0000002307),
 BufferedImage.TYPE_INT_ARGB);
for(int x= 0; x < a.getWidth(); x++){
for(int y = 0; y < _fff0000003095; y++){
//pullfrom

_fff0000004020.setRGB(x, a.getHeight()+y, b.getRGB(Math.min((int)(x*_fff0000002307),b.getWidth()-1),
Math.min((int)(y*_fff0000002307),b.getHeight()-1)))    ;
}}
}
for(int x = 0; x < a.getWidth(); x++){
for(int y = 0; y < a.getHeight(); y++){
_fff0000004020.setRGB(x, y, a.getRGB(x, y));
}
}

//for(int x = 0; x < _fff0000004020.getWidth(); x++){
//for(int y = 0; y < _fff0000004020.getHeight(); y++){
//_fff0000004020.setRGB(x, y, 0xff_ff_00_00);
//}
//}
System.out.println("completed " + _fff0000004020);
System.out.println("a:" + a.getWidth() + " " + a.getHeight());
System.out.println("b:" + b.getWidth() + " " + b.getHeight());
System.out.println("c:" + _fff0000004020.getWidth() + " " + _fff0000004020.getHeight());
return _fff0000004020;
}
    public static void main(String[] args){
   Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
try{
Robot r = new Robot();
clipboard.setContents(new StringSelection("hehe"), new StringSelection("hehe"));

BufferedImage _fff0000016099 = null;
BufferedImage _fff0000012720 = null;
int chainlength = 0;
while(chainlength != 1){              
try{
            Transferable t = clipboard.getContents(null);
            Image i = (Image)t.getTransferData(DataFlavor.imageFlavor);
            BufferedImage bi = imageToBufferedImage(i);
            if(_fff0000016099==null){
            _fff0000016099=bi;
            _fff0000012720=bi;
            }
            if( !_fff0000002493._fff0000010727(_fff0000012720, bi)){
            chainlength++;
            _fff0000016099=bi;
            System.out.println("bi is " + bi);
            _fff0000012720 = _fff0000019471(_fff0000012720,_fff0000016099);
            //put _fff0000012720 into the clipboard now
            System.out.println("Assigning to clipboard, cl: " + chainlength );
TransferableImage trans = new TransferableImage(_fff0000012720);
System.out.println("doing the mutation");
clipboard.setContents(trans, null);
                        }else{}
}catch(IllegalStateException e){
    System.out.println("Neutral Jing");
}catch(Exception e){
    _fff0000016099=null;chainlength=0;
}
// System.out.println("tick");
Thread.sleep(300);
}
   }catch(Exception e){System.out.println("EEEEE");e.printStackTrace();}
}}
// class _fff0000002493{ 
// public static boolean _fff0000010727(BufferedImage a, BufferedImage b){ //eql
// boolean match1 = a.getHeight()==b.getHeight() && a.getWidth() == b.getWidth();
// if(match1){
//     for(int i = 0; i < 100; i++){
//         int x =  (int)Math.random()*a.getWidth();
//         int y = (int)Math.random()*a.getHeight();
//         if(a.getRGB(x,y) == b.getRGB(x, y)){
//             return true;
//         }
//     }
//     return false;
// }
// return false;
// }
// }


// class TransferableImage implements Transferable, ClipboardOwner {

//     Image i;

//     public TransferableImage( Image i ) {
//         this.i = i;
//     }

//     //empty ClipBoardOwner implementation
//     public void lostOwnership(Clipboard clipboard, Transferable contents) {}

//     public Object getTransferData( DataFlavor flavor )
//     throws UnsupportedFlavorException, IOException {
//         if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
//             return i;
//         }
//         else {
//             throw new UnsupportedFlavorException( flavor );
//         }
//     }

//     public DataFlavor[] getTransferDataFlavors() {
//         DataFlavor[] flavors = new DataFlavor[ 1 ];
//         flavors[ 0 ] = DataFlavor.imageFlavor;
//         return flavors;
//     }

//     //unused
//     public boolean isDataFlavorSupported( DataFlavor flavor ) {
//         DataFlavor[] flavors = getTransferDataFlavors();
//         for ( int i = 0; i < flavors.length; i++ ) {
//             if ( flavor.equals( flavors[ i ] ) ) {
//                 return true;
//             }
//         }

//         return false;
//     }
// }



























































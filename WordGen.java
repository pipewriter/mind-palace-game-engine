
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;
// import org.lwjgl.system.CallbackI.I;
// import org.lwjgl.system.CallbackI.P;
// import org.lwjgl.system.CallbackI.Z;
import org.lwjgl.glfw.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import java.util.*;
import java.nio.*;



public class WordGen {

public static long window = -3;
static int buggaX;
static int buggaY;
static Spriter textSheet2;
static Spriter fightSpriter;
    public static void main(String[] args) throws Exception{
        if(args.length == 0) glfwInit();
        int ww = 1500;
        int hh = 850;
        // start = System.nanoTime();
        glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
        // glfwWindowHint(GLFW_CENTER_CURSOR, GL_TRUE);
        buggaX = ww;
        buggaY = hh;
        if(args.length != 0) glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // 'GLFW_VISIBLE' window hint to 'GLFW_FALSE' makes the window hidden
        window = glfwCreateWindow(ww, hh, "", 0, 0);
    
        glfwMakeContextCurrent(window);
        if(args.length == 0){
            glfwShowWindow(window);
            System.out.println("Showing window");
        }else{

        }
        GL.createCapabilities();
        System.out.println("glGetString(GL_VERSION) 222 " + glGetString(GL_VERSION));

        // glfwWindowHint(GLFW_RESIZABLE, 0);
        // glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
        // glfwWindowHint(GLFW_FLOATING, GLFW_TRUE);
        glEnable(GL_BLEND);
        glEnable(GL_DEPTH_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDepthFunc(GL_LEQUAL);

        fightSpriter = new Spriter(16,"./Fighters.bmp",8);
        textSheet2 = new Spriter(5000, "./textsheet.bmp", 16); // called once per program
        glClearColor(0.8f, 0.8f, 0.8f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        textSheet2.drawString("Which of the following is a feature introduced in OpenGL 4.6", 0, 0, 0, 0.06, 255, 255, 0);
        textSheet2.drawString("that allows for more efficient GPU memory usage by enabling", 0.0, -0.08, 0, 0.06, 255, 255, 0);
        textSheet2.drawString("the sharing of memory between different buffer types", 0.0, -0.16, 0, 0.07, 255, 255, 0);

        textSheet2.drawString("A) Buffer Storage Objects", 0.0, -0.24, 0, 0.07, 255, 255, 0);
        textSheet2.drawString("B) Shader Storage Buffer Objects", 0.0, -0.32, 0, 0.07, 255, 255, 0);
        textSheet2.drawString("C) Persistent Mapped Buffers", 0.0, -0.40, 0, 0.07, 255, 255, 0);
        textSheet2.drawString("D) Bindless Textures", 0.0, -0.48, 0, 0.07, 255, 255, 0);
        textSheet2.step(2, 2, 0,0,0, 0, 0);

        glfwSwapBuffers(window);

        if(args.length == 0){
            while(true){
                glfwPollEvents();
                if(false)break;
            }
        }else{
            glfwPollEvents();
        }
        // need int[] to clipboard
        // int ww = 1500;
        // int hh = 850;

        // int[] ia = takeFrame("Butt");
        // a.dumpClipboard(ImageExpirements.pixToBI(ia, 1500));

        // System.out.println("I'M here and done with main()");
    }

    static Random random = new Random();

static int r1, g1, b1, r2, g2, b2; 
public static void spinColor(){
    do {
        r1 = random.nextInt(256);
        g1 = random.nextInt(256);
        b1 = random.nextInt(256);
        r2 = random.nextInt(256);
        g2 = random.nextInt(256);
        b2 = random.nextInt(256);
    } while (contrastIsTooLow(r1, g1, b1, r2, g2, b2));
}
public static void colorRed(){
        r1 = 256;
        g1 = 256;
        b1 = 0;
        r2 = 256;
        g2 = 0;
        b2 = 0;
}
public static void colorGreen(){
        r1 = 256;
        g1 = 256;
        b1 = 0;
        r2 = 0;
        g2 = 256;
        b2 = 0;
}
public static int[] takeFrame(LinkedList<String> value, boolean newColor) {
    // int r1, g1, b1, r2, g2, b2;
    if(newColor){

        do {
            r1 = random.nextInt(256);
            g1 = random.nextInt(256);
            b1 = random.nextInt(256);
            r2 = random.nextInt(256);
            g2 = random.nextInt(256);
            b2 = random.nextInt(256);
        } while (contrastIsTooLow(r1, g1, b1, r2, g2, b2));
    }

    // r1 = 255;
    // g1 = 255;
    // b1 = 255;
    // r2 = 0;
    // g2 = 0;
    // b2 = 0;
    
    return takeFrame(value, r1, g1, b1, r2, g2, b2);
}


public static double rn(){
// return _secretRandom.nextDouble();
return Math.random();
}

public static int rc(){
// return _secretRandom.nextDouble();
return (int)((Math.random())*256);
}
private static boolean contrastIsTooLow(int r1, int g1, int b1, int r2, int g2, int b2) {
    double L1 = 0.2126 * Math.pow(r1 / 255.0, 2.2) + 0.7152 * Math.pow(g1 / 255.0, 2.2) + 0.0722 * Math.pow(b1 / 255.0, 2.2);
    double L2 = 0.2126 * Math.pow(r2 / 255.0, 2.2) + 0.7152 * Math.pow(g2 / 255.0, 2.2) + 0.0722 * Math.pow(b2 / 255.0, 2.2);
    
    double contrast = (Math.max(L1, L2) + 0.05) / (Math.min(L1, L2) + 0.05);
    
    return contrast < 4.5; // You can adjust this threshold as needed
}

    static long otraWindow;
    // public static int[] takeFrame(String value, int r1, int g1, int b1, int r2, int g2, int b2){
    public static int[] takeFrame(LinkedList<String> v, int r1, int g1, int b1, int r2, int g2, int b2){
        // var value = v.pop();
        ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
        otraWindow = glfwGetCurrentContext();
        glfwMakeContextCurrent(window);
        System.out.println("YO " + otraWindow + " " + window);
        
        glClearColor(r2/255.0f, g2/255.0f, b2/255.0f, 1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

        Spriter.clockValue = (int)(System.currentTimeMillis()%1000);

        fightSpriter.drawSprite(29,
                            0,
                            0,
                            0.02, // move on back 
                            2,
                            2, 0, ColorGroup.evenmore, new ColorGroup(r2,g2,b2), 1000);

        fightSpriter.step(4, 2, 0,0,0, 0, 0);
        int inc = 0;
        System.out.println("here in wordgen");
        System.out.println(v);
        for(var value: v){
            textSheet2.drawString(value, 0, -.08*inc++ +0.5, 0, 0.09, r1, g1, b1);
        }
        textSheet2.step(2, 2, 0,0,0, 0, 0);

        glfwSwapBuffers(window);
        glfwPollEvents();
        glReadBuffer(GL_FRONT);
        Spriter.secretFinalClickCheckCall();
        
        bb.clear();
        glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
        {
            ByteBuffer rgba = bb;
            rgba.rewind();

            ByteBuffer argb = ByteBuffer.allocateDirect(buggaX * buggaY * 4);

            for (int y = 0; y < buggaY; y++) {
                for (int x = 0; x < buggaX; x++) {
                    int inPos = ((buggaY - 1 - y) * buggaX + x) * 4; // flipped position for rgba
                    byte r = rgba.get(inPos);
                    byte g = rgba.get(inPos + 1);
                    byte b = rgba.get(inPos + 2);
                    byte a = rgba.get(inPos + 3);
                    
                    argb.put(a);
                    argb.put(r);
                    argb.put(g);
                    argb.put(b);
                }
            }

            argb.rewind();
            bb = argb;
        }
        int[] ia = new int[buggaX*buggaY];
        var bbi = bb.asIntBuffer();
        bbi.get(ia);
        System.out.println("I'M here I think I have the pixels!!!");
        glfwMakeContextCurrent(otraWindow);
        return ia;
    }
}
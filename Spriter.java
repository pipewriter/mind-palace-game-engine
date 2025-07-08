
import java.io.*;
import java.util.*;
import org.lwjgl.opengl.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import java.awt.image.*;
import javax.imageio.*;
import static org.lwjgl.glfw.GLFW.*;

interface Recolorable{
    public int base0();
    public int base1();
    public int base2();
    public int base3();
    public int re0();
    public int re1();
    public int re2();
    public int re3();
}
class Recolor {
    ColorGroup original;
    ColorGroup replacement;
    Recolor(ColorGroup a, ColorGroup b){
        original = a;
        replacement = b;
    }
    public static Recolor convert(Recolorable r){
        return new Recolor(new ColorGroup(
            (r.base0()>>16)&255,
            (r.base0()>>8)&255,
            (r.base0()>>0)&255,
            (r.base1()>>16)&255,
            (r.base1()>>8)&255,
            (r.base1()>>0)&255,
            (r.base2()>>16)&255,
            (r.base2()>>8)&255,
            (r.base2()>>0)&255,
            (r.base3()>>16)&255,
            (r.base3()>>8)&255,
            (r.base3()>>0)&255
        ),new ColorGroup(
            (r.re0()>>16)&255,
            (r.re0()>>8)&255,
            (r.re0()>>0)&255,
            (r.re1()>>16)&255,
            (r.re1()>>8)&255,
            (r.re1()>>0)&255,
            (r.re2()>>16)&255,
            (r.re2()>>8)&255,
            (r.re2()>>0)&255,
            (r.re3()>>16)&255,
            (r.re3()>>8)&255,
            (r.re3()>>0)&255
        ));
    }
    public final static Recolor NON_RECOLOR = new Recolor(
        new ColorGroup(0,0,0, 0,0,0, 0,0,0, 0,0,0),
        new ColorGroup(0,0,0, 0,0,0, 0,0,0, 0,0,0)
    );
}
public class Spriter{


    public static Object[] tup(Object ... o){
        return o;
    }

    private static HashMap<String, Object[]> timingsMap = new HashMap<String, Object[]>();
    public static void time(String label){
        time(label, false);
    }
    public static String getTimings(){
        String s = "";
        
        for(var t: timingsMap.entrySet()){
            s+=(", " + t.getKey() + ": " + ((Long)t.getValue()[2]));
        }
        return s;
    }
    public static void time(String label, boolean beep){
        if(true)return;
        Object[] o = timingsMap.get(label);
        if(o == null){
            o = tup(false, System.nanoTime(), 0l);
        }
        boolean t = (Boolean)o[0];
        long taken = (Long)o[1];
        long nn = (Long)o[2];
        if(t){
            long n = (System.nanoTime()-taken);
            nn = n;
            if(beep || n > 50)
                System.out.println("SPRITER TIMING " + label +": "+ n + " END");
        }else{
            taken = System.nanoTime();
        }
        t = !t;
        timingsMap.put(label, tup(t, taken, nn));
    }
    public static int ALL_CAUSE_STEP;
    public static int ALL_CAUSE_TRIANGLES;
    /*
    * this is designed to be supplimentary to a gl
    * it is assumed that gl will have already been made and this will
    * just encapsulate a single draw call of sprites
    */
    public int drawct = 0;
    public float[] attributesData = null;
    public int[] attributesData2 = null;
    public int[] indexData = null;

    private static int vertShader;
    private static int fragShader;
    public int attributesBuffer;
    public int colorBuffer;
    public int indexBuffer;

    private static int nextAvailableTextureSlot = 0;
    private int textureSlot;
    private double screenWidth;
    private double screenHeight;
    private int screenDimensionsUniform;
    private int offsetUniform;
    private int sideLengthUniform;
    private int texId;
    private int elapsedUniform;
    private int nonceUniform;

    public static int globaltexId;

    private int clickTrackUniform;
    private int blinkLevelUniform;

    private int buttxUniform;
    private int buttyUniform;
    private int buttzUniform;
    private int neckUniform;
    private int chinUniform;
    private int uiShrinkerValueUniform;
    private int cutoutValueUniform;
    private int disable2dUniform;
    private int aspectUniform;
    private int clockUniform;
    private int zoomUniform;
    private int textureLocation;
    private int texture2Location;
    private int distanceUniform;
    private int zfarUniform;
    private int znearUniform;

    
    private int fogFlavorUniform;
    private int skyFlavorUniform;
    private int watFlavorUniform;
    private int pqimodeUniform;

    private int sinFlavorUniform;

    private int textureSizeUniform;

    private int texture1id;
    public static int texture2id;
    public static HashMap<Integer,Integer> globalTextureIds = new HashMap<Integer,Integer>();
    
    public static float aspect = 16.0f/9.0f;

    int attributeStride = (24+96+4+4  + 4)/4;
    int sideLength;
    
    static byte[] ba = new byte[4*100*400];
    // static String frag_string = null;
    // static String vert_string = null;
    // static{
    //     //I need an override
    //     // try{
    //     //     frag_string = new String(Arrays.copyOf(ba, (new FileInputStream(Main.resourcefolder + "./procdrawfrag.glsl")).read(ba)), "UTF-8");
    //     //     vert_string = new String(Arrays.copyOf(ba, (new FileInputStream(Main.resourcefolder + "./procdrawvert.glsl")).read(ba)), "UTF-8");
    //     // }catch(Exception e){e.printStackTrace();}
    // }
    public Spriter(int[] data, int width, int height, int sideLength, int maxspritesonscreen){
        this(data,width,height,sideLength,maxspritesonscreen,null,null);
    }
    public int width;

    private static int xtra1;
    private static int xtra2;
    public static int[] getdat(String sheeturl){
        BufferedImage bi = null;
        try{
            bi = ImageIO.read(new File(sheeturl));
        }catch(Exception e){System.out.println("failfail loading " + sheeturl + " " + Main.resourcefolder);}
        return getdat(bi);
    }
    public static int[] getdat(BufferedImage bi){
        xtra1 = bi.getWidth();
        xtra2 = bi.getHeight();
        int[] pixels_raw = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
        return pixels_raw;
    }

    public Spriter(int maxspritesonscreen, String sheeturl, int sideLength){
        this(getdat(Main.resourcefolder + sheeturl),xtra1,xtra2,sideLength,maxspritesonscreen,null,null);
    }
    public Spriter(BufferedImage bi,int sideLength, int maxspritesonscreen, String vertS, String fragS){
        this(getdat(bi),bi.getWidth(),bi.getHeight(),sideLength,maxspritesonscreen,null,fragS);
    }
    
    public Spriter(BufferedImage bi,int sideLength, int maxspritesonscreen){
        this(bi, sideLength, maxspritesonscreen, null, null);
    }
    
    private int program;
    public static int communalNormal= -1;
    public static int communalTop = -1;
    public static int communalOther = -1;

    // static int clickColors = 0xff_ff_ff_ff;
    public static int clickColors = 0;
    public static int communalprogram(){
        if(glfwGetCurrentContext() == Main.window){
            return communalNormal;
        }else if(glfwGetCurrentContext() == Main.topWindow){
            return communalTop;
        }
        else if(glfwGetCurrentContext() == WordGen.window){
            return communalOther;
        }
        return -1; // NEED SETTING!!
    }
    public static void setcommunalprogram(){
        if(glfwGetCurrentContext() == Main.window){
            communalNormal = glCreateProgram();
            System.out.println("Set communalNormalas " + communalNormal);
            // System.exit(0);
        }else if(glfwGetCurrentContext() == Main.topWindow){
            communalTop = glCreateProgram();
            System.out.println("Set communalTopas " + communalTop);
        }
        else if(glfwGetCurrentContext() == WordGen.window){
            communalOther = glCreateProgram();
            System.out.println("Set communalOtheras " + communalOther);
        }
    }
    

    public static int TEXTURE_OFFSET = 0;

    int ofs = -1;
    int len = -1;
    public Spriter(int[] data, int width, int height, int sideLength, int maxspritesonscreen, String vertS, String fragS){
        time("sectionA");
        ofs = ClickTrack.getOfs(maxspritesonscreen);
        len = maxspritesonscreen;
        time("sectionA-1");
        // sideLength = 1;
        this.width = width;
        this.sideLength = sideLength;
        // int maxspritesonscreen = 12;
        textureSlot = nextAvailableTextureSlot;
        nextAvailableTextureSlot++;
        // attributesData = new float[maxspritesonscreen*attributeStride*4];
        attributesData = new float[maxspritesonscreen*(attributeStride)*6];

        // for(int i = 0; i < attributesData2.length; i++){
        //     attributesData2[i] = 128;
        // }
        indexData = new int[maxspritesonscreen*6];
        String frag = fragS;
        String vert = vertS;
        frag = null;
        vert = null;
        time("sectionA-1");
        boolean isFirstTime = false;
        if(communalprogram() == -1){
            isFirstTime = true;
            System.out.println("ok at least got here");
            // System.exit(0);
            time("sectionA-2");
            setcommunalprogram();
            // if(frag == null && vert == null){
            //     if(vertShader == 0 || fragShader == 0){
            System.out.println("Doing the normal");
            vertShader = glCreateShader(GL_VERTEX_SHADER);
            fragShader = glCreateShader(GL_FRAGMENT_SHADER);
            try{
                byte[] ba = new byte[4*100*400];
                String frag1 = new String(Arrays.copyOf(ba, (new FileInputStream(Main.resourcefolder  +"./procdrawfrag1.2.6.glsl")).read(ba)), "UTF-8");
                String frag2 = new String(Arrays.copyOf(ba, (new FileInputStream(Main.resourcefolder  +"./procdrawfrag_special.glsl")).read(ba)), "UTF-8");
                boolean isSpecial = glfwGetCurrentContext() == WordGen.window;
                frag = isSpecial ? frag2 : frag1;
                if(fragS != null){
                    frag = fragS;
                    System.out.println(frag);
                    System.exit(0);
                }
                vert = new String(Arrays.copyOf(ba, (new FileInputStream(Main.resourcefolder + "./procdrawvert1.0.glsl")).read(ba)), "UTF-8");
            }catch(Exception e){e.printStackTrace();}
            glShaderSource(fragShader, frag);
            glShaderSource(vertShader, vert);
            glCompileShader(vertShader);
            glCompileShader(fragShader);
            {
                int k = glGetShaderi(fragShader, GL_COMPILE_STATUS);
                if(k == GL_FALSE){
                    System.out.println("ERROR IN THE COMPILE STEP");
                    System.out.println(glGetShaderInfoLog(fragShader));
                    fragShader = staticBackupShader;
                }else{
                    staticBackupShader = fragShader;
                }
            }
            // System.out.println(glGetShaderInfoLog(vertShader));
            // System.out.println(glGetShaderInfoLog(fragShader));
            //     }else{
            //         // System.out.println("Compilation avoided");
            //     }
            // }
            time("sectionA-2");

            // if(frag == null)frag = frag_string;
            // if(vert == null)vert = vert_string;
            glAttachShader(communalprogram(), fragShader);
            glAttachShader(communalprogram(), vertShader);
            glLinkProgram(communalprogram());
        }
        time("sectionA-3");
        glUseProgram(communalprogram());
        // System.out.println(glGetProgramInfoLog(program));
        int k = glGetError();
        if(k != GL_NO_ERROR){
            System.out.println("gl kk1 " + k);
            // System.exit(0);
        }
        time("sectionA-3");

        time("sectionA");
        time("sectionB");
        
        attributesBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, attributesBuffer);

        colorBuffer = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, colorBuffer);

        glEnableVertexAttribArray(0);
        glVertexAttribPointer(0, 1, GL_FLOAT, false, attributeStride, 0); // sprite offset
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, attributeStride, 4); // position
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, attributeStride, 16); // textureMap

        glEnableVertexAttribArray(3);
        glVertexAttribPointer(3, 3, GL_FLOAT, false, attributeStride, 24); // c1
        glEnableVertexAttribArray(4);
        glVertexAttribPointer(4, 3, GL_FLOAT, false, attributeStride, 36); // c2
        glEnableVertexAttribArray(5);
        glVertexAttribPointer(5, 3, GL_FLOAT, false, attributeStride, 48); // c3
        glEnableVertexAttribArray(6);
        glVertexAttribPointer(6, 3, GL_FLOAT, false, attributeStride, 60); // c4
        glEnableVertexAttribArray(7);
        glVertexAttribPointer(7, 3, GL_FLOAT, false, attributeStride, 72); // c5
        glEnableVertexAttribArray(8);
        glVertexAttribPointer(8, 3, GL_FLOAT, false, attributeStride, 84); // c6
        glEnableVertexAttribArray(9);
        glVertexAttribPointer(9, 3, GL_FLOAT, false, attributeStride, 96); // c7
        glEnableVertexAttribArray(10);
        glVertexAttribPointer(10, 3, GL_FLOAT, false, attributeStride, 108); // c8
        glEnableVertexAttribArray(11);
        glVertexAttribPointer(11, 1, GL_FLOAT, false, attributeStride, 120); // c1        
        glEnableVertexAttribArray(12);
        glVertexAttribPointer(12, 1, GL_FLOAT, false, attributeStride, 124); // c1       
        // glEnableVertexAttribArray(13);
        // glVertexAttribPointer(13, 1, GL_INT, false, attributeStride, 128); // c1   
        glEnableVertexAttribArray(13);
        glVertexAttribIPointer(13, 1, GL_UNSIGNED_INT, attributeStride, 128); // c1       

		indexBuffer = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        time("sectionB");
        time("sectionC");

        // int[] pixels_raw = bi.getRGB(0, 0, width, height, null, 0, );
        int[] pixels_raw = data;
        tempIntDataForStorage = data;
        // System.out.println("MAKING NEw with " + bi.getWidth() + ", " + bi.getHeight());

        {
            int zz = glGetError();
            if(zz != GL_NO_ERROR){
                System.out.println("gl zz0999 " + zz);
                System.exit(0);
            }
        }
        if(isFirstTime){
            try{
                // pixels_raw = ImageExpirements.pngToPix("./textsheet_flip.bmp");
                // globaltexId = glGenTextures();
                texture2id = 0;
                globalTextureIds.put(communalprogram(), glGenTextures());
                glActiveTexture(GL_TEXTURE0 + 0);  // Activate the first texture unit.
                glBindTexture(GL_TEXTURE_2D, globaltexId);
                // {
                //     glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
                //     glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
                //     glGenerateMipmap(GL_TEXTURE_2D);
                // }
                // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels_raw);
            }catch(Exception e){e.printStackTrace();}
        }
        
        {
            int zz = glGetError();
            if(zz != GL_NO_ERROR){
                System.out.println("gl zz3232234 " + zz);
                System.exit(0);
            }
        }
        texId = glGenTextures();
        glActiveTexture(GL_TEXTURE0 + 1);  // Activate the first texture unit.
        // System.out.println("Just generated " + texId);
        glBindTexture(GL_TEXTURE_2D, texId);
        texture1id = 1;
        {
            glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glGenerateMipmap(GL_TEXTURE_2D);
        }
        // try {
        //     Process process = Runtime.getRuntime().exec("nvidia-smi");
        //     BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        //     String line;
        //     while ((line = reader.readLine()) != null) {
        //         System.out.println(line);
        //     }
        //     process.waitFor();
        // } catch (Exception e) {
        //     e.printStackTrace();
        // }
        if(pixels_raw.length != width * height){
            System.out.println("holy fuck holy fuck" + pixels_raw.length +" "+ width * height );


            pixels_raw = new int[width*height];
            // System.exit(0);
        }
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels_raw);


        {
            // System.out.println("here once " + TEXTURE_OFFSET);
            int zz = glGetError();
            if(zz != GL_NO_ERROR){
                int[] maxTextureUnits = new int[1];
                glGetIntegerv(GL_MAX_TEXTURE_IMAGE_UNITS, maxTextureUnits);
                System.out.println("max units is " + maxTextureUnits[0]);
                System.out.println("gl zz234 " + zz);
                // System.exit(0);
            }
        }
        // System.out.println("done init");
		screenDimensionsUniform = glGetUniformLocation(communalprogram(), "screenDimensions");
		offsetUniform = glGetUniformLocation(communalprogram(), "offset");
        sideLengthUniform = glGetUniformLocation(communalprogram(), "sideLength");
        elapsedUniform = glGetUniformLocation(communalprogram(), "elapsed");
        nonceUniform = glGetUniformLocation(communalprogram(), "nonce");
        buttxUniform = glGetUniformLocation(communalprogram(), "buttx");
        clickTrackUniform = glGetUniformLocation(communalprogram(), "clickTrack");
        blinkLevelUniform = glGetUniformLocation(communalprogram(), "blinklevel");
        buttyUniform = glGetUniformLocation(communalprogram(), "butty");
        buttzUniform = glGetUniformLocation(communalprogram(), "buttz");
        neckUniform = glGetUniformLocation(communalprogram(), "neck");
        chinUniform = glGetUniformLocation(communalprogram(), "chin");
        uiShrinkerValueUniform = glGetUniformLocation(communalprogram(), "uishrinker");
        cutoutValueUniform = glGetUniformLocation(communalprogram(), "cutout");
        disable2dUniform = glGetUniformLocation(communalprogram(), "disable2d");
        aspectUniform = glGetUniformLocation(communalprogram(), "aspect");
        clockUniform = glGetUniformLocation(communalprogram(), "clock");
        zoomUniform = glGetUniformLocation(communalprogram(), "zoom");
        textureLocation = glGetUniformLocation(communalprogram(), "ourTexture");
        texture2Location = glGetUniformLocation(communalprogram(), "colorInversionMask");
        distanceUniform = glGetUniformLocation(communalprogram(), "imprintingDistance");
        fogFlavorUniform = glGetUniformLocation(communalprogram(), "fogFlavor");
        skyFlavorUniform = glGetUniformLocation(communalprogram(), "skyFlavor");
        watFlavorUniform = glGetUniformLocation(communalprogram(), "watFlavor");
        pqimodeUniform = glGetUniformLocation(communalprogram(), "pqimode");
        sinFlavorUniform = glGetUniformLocation(communalprogram(), "sinflavor");
        textureSizeUniform = glGetUniformLocation(communalprogram(), "u_textureSize");

        znearUniform = glGetUniformLocation(communalprogram(), "znear");
        zfarUniform = glGetUniformLocation(communalprogram(), "zfar");

        time("sectionC");
    }
    public boolean dead = false;
    public void cleanUp(){
        dead = true;
        glDeleteBuffers(attributesBuffer);
        glDeleteBuffers(indexBuffer);
        glDeleteTextures(texId);
        glBindTexture(GL_TEXTURE_2D, 0);
        // glDeleteShader(fragShader);
        // glDeleteShader(vertShader);
        // glDeleteProgram(program);
    }
    int[] tempIntDataForStorage;
    static int staticBackupShader;

    int indexStride = 1;

    public void drawString(String s, double x, double y, double z, double charHeight, double r, double g, double b){
        int len = s.length();
        double spacing = charHeight/2;
        for(int i = 0; i < len; i++){
            double xx = x + i*spacing - spacing * (len-1) / 2.0;
            double yy = y;
            drawCharacter(s.charAt(i), xx, yy, z, charHeight, r, g, b);
        }
    }
    public void drawCharacter(char c, double x, double y, double z, double h, double r, double g, double b){
        if(c > 255 || c < 0) return;
        int offset = 62; //apostrophe
        if(c >= TextHelper.charToOffsetLookup.length || TextHelper.charToOffsetLookup[c] != -1){
            offset = TextHelper.charToOffsetLookup[c];
        }
        drawSprite(offset, x, y, z, h, h, 0, 1,
            0, 0, 0,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            (float)r,(float) g, (float)b,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1, 1000);
    }
    public void drawEasy(int spriteoffset, double x, double y, double z, double w,
        double h, double r, int cr, int cg, int cb){
            drawSprite(spriteoffset, x, y, z, w, h, r, 1,
            0,   0,  0,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            cr, cg, cb,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1);
    }
    public void drawSprite(int spriteoffset, double x, double y, double z, double w, double h, double r, double transparency){
        drawSprite(spriteoffset, x, y, z, w, h, r, transparency,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1, 1000);
    }
    public void drawSprite(int spriteoffset, double x, double y, double z, double w, double h, double r,
        Recolor re
    ){
        if(re == null){
            drawSprite(spriteoffset, x, y, z, w, h, r, 1);
        }else{
            drawSprite(spriteoffset, x, y, z, w, h, r, re.original, re.replacement);
        }
    }
    
    public void drawSprite(int spriteoffset, double x, double y, double z, double w, double h, double r,
        Recolor re, int mode
    ){
        if(re == null){
            drawSprite(spriteoffset, x, y, z, w, h, r, 1);
        }else{
            drawSprite(spriteoffset, x, y, z, w, h, r, re.original, re.replacement, mode);
        }
    }
    public void drawSprite(int spriteoffset, double x, double y, double z, double w, double h, double r,
        ColorGroup a, ColorGroup b
    ){
        drawSprite(spriteoffset, x, y, z, w, h, r, 1,
        a.r1, a.g1, a.b1,
        a.r2, a.g2, a.b2,
        a.r3, a.g3, a.b3,
        a.r4, a.g4, a.b4,
        b.r1, b.g1, b.b1,
        b.r2, b.g2, b.b2,
        b.r3, b.g3, b.b3,
        b.r4, b.g4, b.b4);
    }
    
    public void drawSprite(int spriteoffset, double x, double y, double z, double w, double h, double r,
        ColorGroup a, ColorGroup b, int mode
    ){
        drawSprite(spriteoffset, x, y, z, w, h, r, 1,
        a.r1, a.g1, a.b1,
        a.r2, a.g2, a.b2,
        a.r3, a.g3, a.b3,
        a.r4, a.g4, a.b4,
        b.r1, b.g1, b.b1,
        b.r2, b.g2, b.b2,
        b.r3, b.g3, b.b3,
        b.r4, b.g4, b.b4, mode);
    }
    

    //     glBindTexture(GL_TEXTURE_2D, globaltexId);
    // public static void setLateTexture(){
    //     glActiveTexture(GL_TEXTURE0 + 0);
    //     try{
    //         System.out.println("setlatetexture");
    //     // texId = glGenTextures();
    //     int[] pixels_raw = ImageExpirements.pngToPix("./mask_test.png");
    //     // sideLength = 8;
    //     System.out.println("here");
    //     {
    //         glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
    //         glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
    //         glGenerateMipmap(GL_TEXTURE_2D);
    //     }
    //     glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 800,600, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels_raw);

    //     System.out.println("here and done");
    //     // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 2, 2, 0, GL_RGBA, GL_UNSIGNED_BYTE, new int[]{0xff_ff_ff_ff,0xff_ff_ff_00,0xff_ff_00_00,0xff_00_00_00});
    //     // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 2, 2, 0, GL_RGBA, GL_UNSIGNED_BYTE, new int[]{0xff_ff_ff_ff,0xff_ff_ff_00,0xff_ff_00_00,0xff_00_00_00});
    //     }catch(Exception e){e.printStackTrace();}

    // } 
    public static void clearLateTexture(){
        System.out.println("set to " + globalTextureIds.get(communalprogram()));
        glActiveTexture(GL_TEXTURE0 + 0);
        glBindTexture(GL_TEXTURE_2D, globalTextureIds.get(communalprogram()));
        try{
            System.out.println("setlatetexture");
        // texId = glGenTextures();
        // int[] pixels_raw = ImageExpirements.pngToPix("./mask_test.png");
        int[] pixels_raw = new int[]{0};
        int width = 1;
        // sideLength = 8;
        System.out.println("here");
        // glBindTexture(GL_TEXTURE_2D, globalTextureIds.get(communalprogram()));
        {
            glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glGenerateMipmap(GL_TEXTURE_2D);
        }
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width,pixels_raw.length/width, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels_raw);

        System.out.println("here and done");
        // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 2, 2, 0, GL_RGBA, GL_UNSIGNED_BYTE, new int[]{0xff_ff_ff_ff,0xff_ff_ff_00,0xff_ff_00_00,0xff_00_00_00});
        // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 2, 2, 0, GL_RGBA, GL_UNSIGNED_BYTE, new int[]{0xff_ff_ff_ff,0xff_ff_ff_00,0xff_ff_00_00,0xff_00_00_00});
        }catch(Exception e){e.printStackTrace();}
    }
    public static void setLateTexture(Spriter existing){
        setLateTexture(existing, 0);
    }
    public static void setLateTexture(Spriter existing, int pqimode){
        // globalTextureIds.put(communalprogram(), glGenTextures());
        Spriter.pqimode = pqimode;
        int width = existing.width;
        System.out.println("set to " + globalTextureIds.get(communalprogram()));
        glActiveTexture(GL_TEXTURE0 + 0);
        glBindTexture(GL_TEXTURE_2D, globalTextureIds.get(communalprogram()));
        try{
            System.out.println("setlatetexture");
        // texId = glGenTextures();
        // int[] pixels_raw = ImageExpirements.pngToPix("./mask_test.png");
        int[] pixels_raw = existing.tempIntDataForStorage;
        Spriter.sizex = width;
        Spriter.sizey = pixels_raw.length/width;
        // sideLength = 8;
        System.out.println("here");
        // glBindTexture(GL_TEXTURE_2D, globalTextureIds.get(communalprogram()));
        {
            glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexParameteri (GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glGenerateMipmap(GL_TEXTURE_2D);
        }
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width,pixels_raw.length/width, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels_raw);

        System.out.println("here and done");
        // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 2, 2, 0, GL_RGBA, GL_UNSIGNED_BYTE, new int[]{0xff_ff_ff_ff,0xff_ff_ff_00,0xff_ff_00_00,0xff_00_00_00});
        // glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, 2, 2, 0, GL_RGBA, GL_UNSIGNED_BYTE, new int[]{0xff_ff_ff_ff,0xff_ff_ff_00,0xff_ff_00_00,0xff_00_00_00});
        }catch(Exception e){e.printStackTrace();}
        // globalTextureIds.put(communalprogram(), existing.texture1id);
    }

    public void drawSprite(int spriteoffset, double x, double y, double z, double w, double h, double r, double transparency,
        float r1, float g1, float b1,
        float r2, float g2, float b2,
        float r3, float g3, float b3,
        float r4, float g4, float b4,
        float r5, float g5, float b5,
        float r6, float g6, float b6,
        float r7, float g7, float b7,
        float r8, float g8, float b8){
        drawSprite(spriteoffset, x, y, z, w, h, r, transparency,
        r1, g1, b1,
        r2, g2, b2,
        r3, g3, b3,
        r4, g4, b4,
        r5, g5, b5,
        r6, g6, b6,
        r7, g7, b7,
        r8, g8, b8,
        0);
    }
    public int bobo = 0;
    public Object[] drawPointAndCache(Object...o){
        // if(hasRepaunch()) return null;

        // System.out.println(o[7] instanceof Integer);
        drawPoint((Integer)o[0], (double)o[1], (double)o[2], (double) o[3], (double) o[4], (ColorGroup) o[5],
        (ColorGroup) o[6], (Integer) o[7]);
        // drawPoint(0, 0,0,0, 0,ColorGroup.black,ColorGroup.black,0);
        return o;
    }
    public void drawPointCharacter(char c, double x, double y, double z, ColorGroup cg){
        int offset = 62; //apostrophe
        if(c >= TextHelper.charToOffsetLookup.length || TextHelper.charToOffsetLookup[c] != -1){
            if(c < TextHelper.charToOffsetLookup.length && c > 0)
                offset = TextHelper.charToOffsetLookup[c];
        }
        drawPoint(offset, x, y, z, 1, ColorGroup.black, cg, 0);
    }
    public static int convertCharacter(char c){
        int offset = 62; //apostrophe
        if(c >= TextHelper.charToOffsetLookup.length || TextHelper.charToOffsetLookup[c] != -1){
            if(c < TextHelper.charToOffsetLookup.length && c > 0)
                offset = TextHelper.charToOffsetLookup[c];
        }
        return offset;
    }
    public int mumu= 0;

    public static class ClickTrack{
        static int id = 0;
        static int lastofs = 0;
        // static boolean mode = false;
        static boolean mode = true;
        static int max = 0b11111111_11111111_11111111 - 1;
        static Object[] vals = new Object[max];
        static int nextofs = 0;
        public static int getOfs(int size){
            // System.out.println("allocating " +nextofs);
            nextofs += size;
            return nextofs-size;
        }
        // the purpose of this is to allow for more sheets
        public static int register(Object o, int ofs, int len) {
            if(ofs != lastofs){
                id = ofs; //this means you need to draw everything from one sheet all at once
                lastofs = ofs;
            }
            int vid = id;
            vals[vid] = o;
            id = (id+1)%(ofs+len);
            if(id < ofs)id=ofs;
            return vid;
        }
        public static Object lookup(int val){
            if(val > vals.length) return null;
            return vals[val];
        }
    }
    public static int counter = 0;


    public void drawTriPoint(int spriteoffset, double x, double y, double z, double transparency,
            ColorGroup a, ColorGroup b, int mode){
        
        // if(hasRepaunch()) return;
        int offset = offset3*(attributeStride);
        
        int offset2 = drawct*indexStride;
        // int offset3 = `*3;
        int load = 33;
        {
            int bump = mumu;
            int bumpload = load*bump;
            float xx = (float)(x);
            float yy = (float)(y);
            float u = 0f;
            float v = 0f;
            switch(mumu){
                case 1:
                    u = 1f;
                    v = 0f;
                    break;
                case 2:
                    u = 0f;
                    v = 1f;
                    break;
            }
            if(drawct%2==0){
                switch(mumu){
                    case 0:
                        u = 0f;
                        v = 1f;
                        break;
                    case 1:
                        u = 1f;
                        v = 0f;
                        break;
                    case 2:
                        u = 1f;
                        v = 1f;
                        break;
                }
            }
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] =  a.r1;
            attributesData[offset+bumpload+7] =  a.g1;
            attributesData[offset+bumpload+8] =  a.b1;
            attributesData[offset+bumpload+9] =  a.r2;
            attributesData[offset+bumpload+10] = a.g2;
            attributesData[offset+bumpload+11] = a.b2;
            attributesData[offset+bumpload+12] = a.r3;
            attributesData[offset+bumpload+13] = a.g3;
            attributesData[offset+bumpload+14] = a.b3;
            attributesData[offset+bumpload+15] = a.r4;
            attributesData[offset+bumpload+16] = a.g4;
            attributesData[offset+bumpload+17] = a.b4;
            attributesData[offset+bumpload+18] = b.r1;
            attributesData[offset+bumpload+19] = b.g1;
            attributesData[offset+bumpload+20] = b.b1;
            attributesData[offset+bumpload+21] = b.r2;
            attributesData[offset+bumpload+22] = b.g2;
            attributesData[offset+bumpload+23] = b.b2;
            attributesData[offset+bumpload+24] = b.r3;
            attributesData[offset+bumpload+25] = b.g3;
            attributesData[offset+bumpload+26] = b.b3;
            attributesData[offset+bumpload+27] = b.r4;
            attributesData[offset+bumpload+28] = b.g4;
            attributesData[offset+bumpload+29] = b.b4;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(mode);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
        }
        mumu++;
        if(mumu >= 3){
            mumu = 0;
            indexData[offset2+0] = offset3 + 0;
            indexData[offset2+1] = offset3 + 1;
            indexData[offset2+2] = offset3 + 2;
            drawct+=3;
            offset3+=3;

        }
    }
    int offset3 = 0;
    public void drawPoint(int spriteoffset, double x, double y, double z, double transparency,
        ColorGroup a, ColorGroup b, int mode){
        if(hasRepaunch()) return;

        
        int offset = offset3*(attributeStride);
        int offset2 = drawct*indexStride;
        // {
        //     // check to catch buffer overrun
        //     if(offset2 + 6 > indexData.length)
        //         return; // tacky but should work
        //         //silent failure isn't great
        // }
        // if(bobo == 0 && offset2+6 >= indexData.length) return; 
        int load = 33;
        {
            int bump = bobo;
            int bumpload = load*bump;
            float xx = (float)(x);
            float yy = (float)(y);
            float u = 0f;
            float v = 0f;
            switch(bobo){
                case 1:
                    u = 1f;
                    v = 0f;
                    break;
                case 2:
                    u = 1f;
                    v = 1f;
                    break;
                case 3:
                    u = 0f;
                    v = 1f;
                    break;
            }
            if(offset+bumpload+0 == attributesData.length){
                // System.out.println("annoying 128 error");
                return;
            }
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] =  a.r1;
            attributesData[offset+bumpload+7] =  a.g1;
            attributesData[offset+bumpload+8] =  a.b1;
            attributesData[offset+bumpload+9] =  a.r2;
            attributesData[offset+bumpload+10] = a.g2;
            attributesData[offset+bumpload+11] = a.b2;
            attributesData[offset+bumpload+12] = a.r3;
            attributesData[offset+bumpload+13] = a.g3;
            attributesData[offset+bumpload+14] = a.b3;
            attributesData[offset+bumpload+15] = a.r4;
            attributesData[offset+bumpload+16] = a.g4;
            attributesData[offset+bumpload+17] = a.b4;
            attributesData[offset+bumpload+18] = b.r1;
            attributesData[offset+bumpload+19] = b.g1;
            attributesData[offset+bumpload+20] = b.b1;
            attributesData[offset+bumpload+21] = b.r2;
            attributesData[offset+bumpload+22] = b.g2;
            attributesData[offset+bumpload+23] = b.b2;
            attributesData[offset+bumpload+24] = b.r3;
            attributesData[offset+bumpload+25] = b.g3;
            attributesData[offset+bumpload+26] = b.b3;
            attributesData[offset+bumpload+27] = b.r4;
            attributesData[offset+bumpload+28] = b.g4;
            attributesData[offset+bumpload+29] = b.b4;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(mode);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);

        }
        bobo++;
        if(bobo >= 4){
            bobo = 0;
            indexData[offset2+0] = offset3 + 0;
            indexData[offset2+1] = offset3 + 1;
            indexData[offset2+2] = offset3 + 2;
            indexData[offset2+3] = offset3 + 0;
            indexData[offset2+4] = offset3 + 2;
            indexData[offset2+5] = offset3 + 3;
            drawct+=6;
            offset3+=4;
        }
    }
    public void drawBillboard(int spriteoffset, double x, double y, double z, float ox, float oy, float w, float h, double r, double transparency, Object hovered){
        drawBillboardWithOffset(spriteoffset, x, y, z, ox, oy, w, h, r, transparency,
        0f,0f,0f,
        0f,0f,0f,
        0f,0f,0f,
        0f,0f,0f,
        0f,0f,0f,
        0f,0f,0f,
        0f,0f,0f,
        0f,0f,0f, hovered
        );
    }
    
    public void drawBillboardWithOffset(int spriteoffset, double x, double y, double z, float ox, float oy, float w, float h, double r, double transparency,
        float r1, float g1, float b1,
        float r2, float g2, float b2,
        float r3, float g3, float b3,
        float r4, float g4, float b4,
        float r5, float g5, float b5,
        float r6, float g6, float b6,   
        float r7, float g7, float b7,
        float r8, float g8, float b8, Object hovered){
            if(hasRepaunch()) return;
        
        double x1 = 0;
        double y1 = 0;
        double x2 = 0;
        double y2 = 0;
        int c = -1;
        // if(hovered != null){
        //     c = ClickTrack.register(hovered, ofs, len);
        //     lastRegister = c;
        // }

        int offset = offset3*attributeStride;
        int offset2 = drawct*indexStride;
        int load = 33;
        {
            int bump = 0;
            int bumpload = load*bump;
            float xx = (float)(x+x1);
            float yy = (float)(y+y1);
            float u = 0f;
            float v = 0f;
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] = r1;
            attributesData[offset+bumpload+7] = g1;
            attributesData[offset+bumpload+8] = b1;
            attributesData[offset+bumpload+9] = r2;
            attributesData[offset+bumpload+10] = g2;
            attributesData[offset+bumpload+11] = b2;
            attributesData[offset+bumpload+12] = r3;
            attributesData[offset+bumpload+13] = g3;
            attributesData[offset+bumpload+14] = b3;
            attributesData[offset+bumpload+15] = r4;
            attributesData[offset+bumpload+16] = g4;
            attributesData[offset+bumpload+17] = b4;
            attributesData[offset+bumpload+18] = r5;
            attributesData[offset+bumpload+19] = g5;
            attributesData[offset+bumpload+20] = b5;
            attributesData[offset+bumpload+21] = r6;
            attributesData[offset+bumpload+22] = g6;
            attributesData[offset+bumpload+23] = b6;
            attributesData[offset+bumpload+24] = w;
            attributesData[offset+bumpload+25] = h;
            attributesData[offset+bumpload+26] = b7;
            attributesData[offset+bumpload+27] = ox;
            attributesData[offset+bumpload+28] = oy;
            attributesData[offset+bumpload+29] = b8;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(2000);
            // attributesData2[offset3+0] = c;
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
        }
        {
            int bump = 1;
            int bumpload = load*bump;
            float xx = (float)(x+x2);
            float yy = (float)(y+y2);
            float u = 1f;
            float v = 0f;
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] = r1;
            attributesData[offset+bumpload+7] = g1;
            attributesData[offset+bumpload+8] = b1;
            attributesData[offset+bumpload+9] = r2;
            attributesData[offset+bumpload+10] = g2;
            attributesData[offset+bumpload+11] = b2;
            attributesData[offset+bumpload+12] = r3;
            attributesData[offset+bumpload+13] = g3;
            attributesData[offset+bumpload+14] = b3;
            attributesData[offset+bumpload+15] = r4;
            attributesData[offset+bumpload+16] = g4;
            attributesData[offset+bumpload+17] = b4;
            attributesData[offset+bumpload+18] = r5;
            attributesData[offset+bumpload+19] = g5;
            attributesData[offset+bumpload+20] = b5;
            attributesData[offset+bumpload+21] = r6;
            attributesData[offset+bumpload+22] = g6;
            attributesData[offset+bumpload+23] = b6;
            attributesData[offset+bumpload+24] = w;
            attributesData[offset+bumpload+25] = h;
            attributesData[offset+bumpload+26] = b7;
            attributesData[offset+bumpload+27] = ox;
            attributesData[offset+bumpload+28] = oy;
            attributesData[offset+bumpload+29] = b8;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(2001);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
            // attributesData2[offset3+1] = c;
        }
        {
            int bump = 2;
            int bumpload = load*bump;
            float xx = (float)(x-x1);
            float yy = (float)(y-y1);
            float u = 1f;
            float v = 1f;
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] = r1;
            attributesData[offset+bumpload+7] = g1;
            attributesData[offset+bumpload+8] = b1;
            attributesData[offset+bumpload+9] = r2;
            attributesData[offset+bumpload+10] = g2;
            attributesData[offset+bumpload+11] = b2;
            attributesData[offset+bumpload+12] = r3;
            attributesData[offset+bumpload+13] = g3;
            attributesData[offset+bumpload+14] = b3;
            attributesData[offset+bumpload+15] = r4;
            attributesData[offset+bumpload+16] = g4;
            attributesData[offset+bumpload+17] = b4;
            attributesData[offset+bumpload+18] = r5;
            attributesData[offset+bumpload+19] = g5;
            attributesData[offset+bumpload+20] = b5;
            attributesData[offset+bumpload+21] = r6;
            attributesData[offset+bumpload+22] = g6;
            attributesData[offset+bumpload+23] = b6;
            attributesData[offset+bumpload+24] = w;
            attributesData[offset+bumpload+25] = h;
            attributesData[offset+bumpload+26] = b7;
            attributesData[offset+bumpload+27] = ox;
            attributesData[offset+bumpload+28] = oy;
            attributesData[offset+bumpload+29] = b8;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(2002);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
            // attributesData2[offset3+2] = c;
        }
        {
            int bump = 3;
            int bumpload = load*bump;
            float xx = (float)(x-x2);
            float yy = (float)(y-y2);
            float u = 0f;
            float v = 1f;
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] = r1;
            attributesData[offset+bumpload+7] = g1;
            attributesData[offset+bumpload+8] = b1;
            attributesData[offset+bumpload+9] = r2;
            attributesData[offset+bumpload+10] = g2;
            attributesData[offset+bumpload+11] = b2;
            attributesData[offset+bumpload+12] = r3;
            attributesData[offset+bumpload+13] = g3;
            attributesData[offset+bumpload+14] = b3;
            attributesData[offset+bumpload+15] = r4;
            attributesData[offset+bumpload+16] = g4;
            attributesData[offset+bumpload+17] = b4;
            attributesData[offset+bumpload+18] = r5;
            attributesData[offset+bumpload+19] = g5;
            attributesData[offset+bumpload+20] = b5;
            attributesData[offset+bumpload+21] = r6;
            attributesData[offset+bumpload+22] = g6;
            attributesData[offset+bumpload+23] = b6;
            attributesData[offset+bumpload+24] = w;
            attributesData[offset+bumpload+25] = h;
            attributesData[offset+bumpload+26] = b7;
            attributesData[offset+bumpload+27] = ox;
            attributesData[offset+bumpload+28] = oy;
            attributesData[offset+bumpload+29] = b8;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(2003);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
            // attributesData2[offset3+3] = c;
        }
        indexData[offset2+0] = offset3 + 0;
        indexData[offset2+1] = offset3 + 1;
        indexData[offset2+2] = offset3 + 2;
        indexData[offset2+3] = offset3 + 0;
        indexData[offset2+4] = offset3 + 2;
        indexData[offset2+5] = offset3 + 3;
        drawct+=6;
        offset3+=4;
    }
    public void drawSprite(int spriteoffset, double x, double y, double z, double w, double h, double r, double transparency,
        float r1, float g1, float b1,
        float r2, float g2, float b2,
        float r3, float g3, float b3,
        float r4, float g4, float b4,
        float r5, float g5, float b5,
        float r6, float g6, float b6,
        float r7, float g7, float b7,
        float r8, float g8, float b8, int mode){
            if(hasRepaunch()) return;

        double opp = h/2;
        double adj = w/2;
        double len = Math.sqrt(opp*opp + adj * adj);
        double ang1 = Math.atan2(opp, adj);
        double ang2 = Math.PI - ang1;

        double x1 = len * Math.cos(ang1+r);
        double y1 = len * Math.sin(ang1+r);
        double x2 = len * Math.cos(ang2+r);
        double y2 = len * Math.sin(ang2+r);


        int offset = offset3*attributeStride;
        
        // if(offset+attributeStride >= attributesData.length){
        //     return;
        // }
        int offset2 = drawct*indexStride;
        // int offset3 = drawct*4;
        int load = 33;
        {
            int bump = 0;
            int bumpload = load*bump;
            float xx = (float)(x+x1);
            float yy = (float)(y+y1);
            float u = 0f;
            float v = 0f;
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] = r1;
            attributesData[offset+bumpload+7] = g1;
            attributesData[offset+bumpload+8] = b1;
            attributesData[offset+bumpload+9] = r2;
            attributesData[offset+bumpload+10] = g2;
            attributesData[offset+bumpload+11] = b2;
            attributesData[offset+bumpload+12] = r3;
            attributesData[offset+bumpload+13] = g3;
            attributesData[offset+bumpload+14] = b3;
            attributesData[offset+bumpload+15] = r4;
            attributesData[offset+bumpload+16] = g4;
            attributesData[offset+bumpload+17] = b4;
            attributesData[offset+bumpload+18] = r5;
            attributesData[offset+bumpload+19] = g5;
            attributesData[offset+bumpload+20] = b5;
            attributesData[offset+bumpload+21] = r6;
            attributesData[offset+bumpload+22] = g6;
            attributesData[offset+bumpload+23] = b6;
            attributesData[offset+bumpload+24] = r7;
            attributesData[offset+bumpload+25] = g7;
            attributesData[offset+bumpload+26] = b7;
            attributesData[offset+bumpload+27] = r8;
            attributesData[offset+bumpload+28] = g8;
            attributesData[offset+bumpload+29] = b8;
            attributesData[offset+bumpload+30] = (float)transparency;

            attributesData[offset+bumpload+31] = Float.intBitsToFloat(mode);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
            // attributesData2[offset3+1] = c;
        }
        {
            int bump = 1;
            int bumpload = load*bump;
            float xx = (float)(x+x2);
            float yy = (float)(y+y2);
            float u = 1f;
            float v = 0f;
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] = r1;
            attributesData[offset+bumpload+7] = g1;
            attributesData[offset+bumpload+8] = b1;
            attributesData[offset+bumpload+9] = r2;
            attributesData[offset+bumpload+10] = g2;
            attributesData[offset+bumpload+11] = b2;
            attributesData[offset+bumpload+12] = r3;
            attributesData[offset+bumpload+13] = g3;
            attributesData[offset+bumpload+14] = b3;
            attributesData[offset+bumpload+15] = r4;
            attributesData[offset+bumpload+16] = g4;
            attributesData[offset+bumpload+17] = b4;
            attributesData[offset+bumpload+18] = r5;
            attributesData[offset+bumpload+19] = g5;
            attributesData[offset+bumpload+20] = b5;
            attributesData[offset+bumpload+21] = r6;
            attributesData[offset+bumpload+22] = g6;
            attributesData[offset+bumpload+23] = b6;
            attributesData[offset+bumpload+24] = r7;
            attributesData[offset+bumpload+25] = g7;
            attributesData[offset+bumpload+26] = b7;
            attributesData[offset+bumpload+27] = r8;
            attributesData[offset+bumpload+28] = g8;
            attributesData[offset+bumpload+29] = b8;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(mode);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
        }
        {
            int bump = 2;
            int bumpload = load*bump;
            float xx = (float)(x-x1);
            float yy = (float)(y-y1);
            float u = 1f;
            float v = 1f;
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] = r1;
            attributesData[offset+bumpload+7] = g1;
            attributesData[offset+bumpload+8] = b1;
            attributesData[offset+bumpload+9] = r2;
            attributesData[offset+bumpload+10] = g2;
            attributesData[offset+bumpload+11] = b2;
            attributesData[offset+bumpload+12] = r3;
            attributesData[offset+bumpload+13] = g3;
            attributesData[offset+bumpload+14] = b3;
            attributesData[offset+bumpload+15] = r4;
            attributesData[offset+bumpload+16] = g4;
            attributesData[offset+bumpload+17] = b4;
            attributesData[offset+bumpload+18] = r5;
            attributesData[offset+bumpload+19] = g5;
            attributesData[offset+bumpload+20] = b5;
            attributesData[offset+bumpload+21] = r6;
            attributesData[offset+bumpload+22] = g6;
            attributesData[offset+bumpload+23] = b6;
            attributesData[offset+bumpload+24] = r7;
            attributesData[offset+bumpload+25] = g7;
            attributesData[offset+bumpload+26] = b7;
            attributesData[offset+bumpload+27] = r8;
            attributesData[offset+bumpload+28] = g8;
            attributesData[offset+bumpload+29] = b8;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(mode);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
        }
        {
            int bump = 3;
            int bumpload = load*bump;
            float xx = (float)(x-x2);
            float yy = (float)(y-y2);
            float u = 0f;
            float v = 1f;
            attributesData[offset+bumpload+0] = (float)spriteoffset;
            attributesData[offset+bumpload+1] = xx;
            attributesData[offset+bumpload+2] = yy;
            attributesData[offset+bumpload+3] = (float)z;
            attributesData[offset+bumpload+4] = u;
            attributesData[offset+bumpload+5] = v;
            attributesData[offset+bumpload+6] = r1;
            attributesData[offset+bumpload+7] = g1;
            attributesData[offset+bumpload+8] = b1;
            attributesData[offset+bumpload+9] = r2;
            attributesData[offset+bumpload+10] = g2;
            attributesData[offset+bumpload+11] = b2;
            attributesData[offset+bumpload+12] = r3;
            attributesData[offset+bumpload+13] = g3;
            attributesData[offset+bumpload+14] = b3;
            attributesData[offset+bumpload+15] = r4;
            attributesData[offset+bumpload+16] = g4;
            attributesData[offset+bumpload+17] = b4;
            attributesData[offset+bumpload+18] = r5;
            attributesData[offset+bumpload+19] = g5;
            attributesData[offset+bumpload+20] = b5;
            attributesData[offset+bumpload+21] = r6;
            attributesData[offset+bumpload+22] = g6;
            attributesData[offset+bumpload+23] = b6;
            attributesData[offset+bumpload+24] = r7;
            attributesData[offset+bumpload+25] = g7;
            attributesData[offset+bumpload+26] = b7;
            attributesData[offset+bumpload+27] = r8;
            attributesData[offset+bumpload+28] = g8;
            attributesData[offset+bumpload+29] = b8;
            attributesData[offset+bumpload+30] = (float)transparency;
            attributesData[offset+bumpload+31] = Float.intBitsToFloat(mode);
            attributesData[offset+bumpload+32] = Float.intBitsToFloat(clickColors);
        }


        indexData[offset2+0] = offset3 + 0;
        indexData[offset2+1] = offset3 + 1;
        indexData[offset2+2] = offset3 + 2;
        indexData[offset2+3] = offset3 + 0;
        indexData[offset2+4] = offset3 + 2;
        indexData[offset2+5] = offset3 + 3;
        drawct+=6;
        offset3+=4;
        // drawct++;
    }

    static float globaluishrinker = 1.0f;
    Float uishrinker = null;
    float cutout = 0f;
    public static float disable2d = 0f;
    static int elapsedAsInt = 0;
    static int clockValue = 0;
    static int blinkValue = 0;
    static float zoomValue = 0;
    boolean flag = false;
    static float distanceUniformValue = 0;

    static float fogred;
    static float foggreen;
    static float fogblue;
    static float skyred;
    static float skygreen;
    static float skyblue;
    static float watred;
    static float watgreen;
    static float watblue;
    static int pqimode;

    static float sin0;
    static float sin1;
    static float sin2;
    static float sin3;
    static float[] sinflavors = new float[30];

    static float sizex;
    static float sizey;


    public void step(double screenWidth, double screenHeight, double x, double y, double z, double neck, double chin){
        //prep for next scene
        // time("step");
        
        // {
        //     int WW = glGetError();
        //     if(WW != GL_NO_ERROR){
        //         System.out.println("gl ZZZZZ eep " + WW);
        //         // System.exit(0);
        //     }
        // }

        glUseProgram(communalprogram());
        
        glUniform2f(screenDimensionsUniform, (float)screenWidth, (float)screenHeight);
        glUniform2f(offsetUniform, (float)0, (float)0);
        glUniform1i(sideLengthUniform, sideLength);
        glUniform1i(elapsedUniform, elapsedAsInt);
        glUniform1f(uiShrinkerValueUniform,  uishrinker != null ? uishrinker : globaluishrinker);
        glUniform1f(cutoutValueUniform,  cutout);
        glUniform1f(disable2dUniform,  (float)disable2d);
        glUniform1f(buttxUniform, (float)x);
        glUniform1f(buttyUniform, (float)y);
        glUniform1f(buttzUniform, (float)z);
        glUniform1f(neckUniform, (float)neck);
        glUniform1f(chinUniform, (float)chin);
        glUniform1f(aspectUniform, aspect);
        glUniform1f(zoomUniform, zoomValue);
        glUniform1i(clockUniform, clockValue);
        glUniform1i(blinkLevelUniform, blinkValue);
        glUniform1i(textureLocation, texture1id);  // Set ourTexture to use texture unit 0.
        glUniform1i(texture2Location, 0);  // Set ourTexture to use texture unit 0.
        glUniform1f(distanceUniform, distanceUniformValue);  // Set ourTexture to use texture unit 0.
        glUniform3f(fogFlavorUniform, fogred,foggreen,fogblue);  // Set ourTexture to use texture unit 0.
        glUniform3f(skyFlavorUniform, skyred,skygreen,skyblue);  // Set ourTexture to use texture unit 0.
        glUniform3f(watFlavorUniform, watred,watgreen,watblue);  // Set ourTexture to use texture unit 0.
        glUniform1fv(sinFlavorUniform,  sinflavors);  // Set ourTexture to use texture unit 0.
        glUniform2f(textureSizeUniform, sizex, sizey);  // Set ourTexture to use texture unit 0.
        glUniform1i(pqimodeUniform, pqimode);  

        glUniform1f(zfarUniform, zfaruniformvalue);
        glUniform1f(znearUniform, znearuniformvalue);

        stepInternal2();
    }

    static float zfaruniformvalue = 200_000f*1000;
    static float znearuniformvalue = 0.1f;
    public static void resetzplanes(){
        zfaruniformvalue = 200_000f*1000;
        znearuniformvalue = 0.1f;
    }
    private static boolean replayAttack = false;

    public void stepInternal2(){
        glUniform1i(sideLengthUniform, sideLength);

        glActiveTexture(GL_TEXTURE0 + 0);
        glBindTexture(GL_TEXTURE_2D, globalTextureIds.get(communalprogram()));
        glActiveTexture(GL_TEXTURE0 + 1);  // Activate the first texture unit.
        glBindTexture(GL_TEXTURE_2D, texId);
        glBindBuffer(GL_ARRAY_BUFFER, attributesBuffer);
        if(!hasRepaunch() && drawct != 0 && !replayAttack)
            glBufferData(GL_ARRAY_BUFFER, attributesData, GL_DYNAMIC_DRAW);

        glEnableVertexAttribArray(0);
        attributeStride*=4;
        glVertexAttribPointer(0, 1, GL_FLOAT, false, attributeStride, 0); // sprite offset
        glEnableVertexAttribArray(1);
        glVertexAttribPointer(1, 3, GL_FLOAT, false, attributeStride, 4); // position
        glEnableVertexAttribArray(2);
        glVertexAttribPointer(2, 2, GL_FLOAT, false, attributeStride, 16); // textureMap
        glEnableVertexAttribArray(3);
        glVertexAttribPointer(3, 3, GL_FLOAT, false, attributeStride, 24); // c1
        glEnableVertexAttribArray(4);
        glVertexAttribPointer(4, 3, GL_FLOAT, false, attributeStride, 36); // c2
        glEnableVertexAttribArray(5);
        glVertexAttribPointer(5, 3, GL_FLOAT, false, attributeStride, 48); // c3
        glEnableVertexAttribArray(6);
        glVertexAttribPointer(6, 3, GL_FLOAT, false, attributeStride, 60); // c4
        glEnableVertexAttribArray(7);
        glVertexAttribPointer(7, 3, GL_FLOAT, false, attributeStride, 72); // c5
        glEnableVertexAttribArray(8);
        glVertexAttribPointer(8, 3, GL_FLOAT, false, attributeStride, 84); // c6
        glEnableVertexAttribArray(9);
        glVertexAttribPointer(9, 3, GL_FLOAT, false, attributeStride, 96); // c7
        glEnableVertexAttribArray(10);
        glVertexAttribPointer(10, 3, GL_FLOAT, false, attributeStride, 108); // c8
        glEnableVertexAttribArray(11);
        glVertexAttribPointer(11, 1, GL_FLOAT, false, attributeStride, 120); // c1   
        glEnableVertexAttribArray(12);
        glVertexAttribPointer(12, 1, GL_FLOAT, false, attributeStride, 124); // c1     
        //  glEnableVertexAttribArray(13);
        // glVertexAttribPointer(13, 1, GL_INT, false, attributeStride, 128); // c1     
        glEnableVertexAttribArray(13);
        glVertexAttribIPointer(13, 1, GL_UNSIGNED_INT, attributeStride, 128); // c1       
        attributeStride/=4;




		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexBuffer);
        if(!hasRepaunch() && drawct != 0 && !replayAttack)
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indexData, GL_DYNAMIC_DRAW);

        // {
        //     int WW = glGetError();
        //     if(WW != GL_NO_ERROR){
        //         System.out.println("gl WWawqaa eep " + WW);
        //         System.exit(0);
        //     }
        // }
        // if(drawct != 1)
        // System.out.println( (1.0*drawct*100/(indexData.length/6))+"%  total drawn: " + (drawct) + " out of " + indexData.length/6);
        glDrawElements(GL_TRIANGLES, drawct * indexStride, GL_UNSIGNED_INT, 0);


        int WW = glGetError();
		if(WW != GL_NO_ERROR){
			System.out.println("gl WW eep " + WW);
            // System.exit(0);
		}
        ALL_CAUSE_TRIANGLES+=offset3;
        ALL_CAUSE_STEP++;
        if(repaunch && drawct != 0 &&!loaded && !weird_scapeLock){
            loaded = true;
            // System.out.println("WOOOOOT DONE");
            // System.out.println("drawing..." + drawct + " " + offset3);
        }
        // if(!repaunch){
        //     drawct = 0;
        //     offset3 = 0;
        // }
        // time("step");
        if(!replayAttack)
        secretStepCalls.addLast(this);
    }
    static LinkedList<Object> secretStepCalls = new LinkedList<Object>();
    static void registerDepthClear(){
        secretStepCalls.addLast("clear");
    }
    static void secretFinalClickCheckCall(){
        replayAttack = true;
        for(var ss: secretStepCalls){
            if(ss instanceof Spriter){
                Spriter s = (Spriter)ss;
                // System.out.println("beep");
                glUniform1i(s.clickTrackUniform, 1);
                s.stepInternal2();
                // glDrawElements(GL_TRIANGLES, s.drawct * s.indexStride, GL_UNSIGNED_INT, 0);
                glUniform1i(s.clickTrackUniform, 0);
                if(!s.repaunch){ //can't be ruining the thing now can we?
                    s.drawct = 0;
                    s.offset3 = 0;
                    s.mumu = 0;
                }
            }else{
                glClear(GL_DEPTH_BUFFER_BIT);
            }
        }
        replayAttack = false;
        // System.out.println(secretStepCalls.size());
        secretStepCalls.clear();
    }

    public boolean loaded = false;
    public boolean repaunch = false;
    public boolean weird_scapeLock = false;
    public boolean hasRepaunch(){
        boolean b =  repaunch && loaded ;
        return b;
    }
    public void clearRepaunch(){
        loaded = false;
        drawct = 0;
        offset3 = 0;
        bobo= 0;
        mumu = 0;
    }
}

class TextHelper {
    public static int[] charToOffsetLookup = new int[]{
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
        -1, -1,
        255, //SPACE 32
        -1, -1, -1, -1, -1, -1,
        63, //APOSTROPHE 39
        -1, -1, -1, -1, -1,
        62, // MINUS 45
        -1, -1,
        61, 52, 53, 54, 55, 56, 57, 58, 59, 60,
        -1, -1, -1, -1, -1, -1, -1,
        0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21,
        22, 23, 24, 25,
        -1, -1, -1, -1, -1, -1,
        26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46,
        47, 48, 49, 50, 51
    };
    static {
        charToOffsetLookup = Arrays.copyOf(charToOffsetLookup, 256);
        for(int i = 123; i < charToOffsetLookup.length; i++){
            if(charToOffsetLookup[i] == 0){
                charToOffsetLookup[i] = -1;
            }
        }
        charToOffsetLookup['/'] = 64;
        charToOffsetLookup['.'] = 65;
        charToOffsetLookup[','] = 66;
        charToOffsetLookup['='] = 67;
        charToOffsetLookup['!'] = 68;
        charToOffsetLookup['@'] = 69;
        charToOffsetLookup['#'] = 70;
        charToOffsetLookup['$'] = 71;
        charToOffsetLookup['%'] = 72;
        charToOffsetLookup['^'] = 73;
        charToOffsetLookup['&'] = 74;
        charToOffsetLookup['*'] = 75;
        charToOffsetLookup['('] = 76;
        charToOffsetLookup[')'] = 77;
        charToOffsetLookup['"'] = 78;
        charToOffsetLookup['\''] = 79;
        charToOffsetLookup['|'] = 80;
        charToOffsetLookup['>'] = 81;
        charToOffsetLookup['<'] = 82;
        charToOffsetLookup['+'] = 83;
        charToOffsetLookup['~'] = 84;
        charToOffsetLookup['`'] = 85;
        charToOffsetLookup['_'] = 86;
        charToOffsetLookup['['] = 87;
        charToOffsetLookup[']'] = 88;
        charToOffsetLookup['{'] = 89;
        charToOffsetLookup['}'] = 90;

        charToOffsetLookup[':'] = 91;
        charToOffsetLookup[';'] = 92;
        charToOffsetLookup['\\'] = 93;
        charToOffsetLookup['?'] = 94;
    }
    public static int[] irregularLookup = new int[256];
    static {
        for(int i = 0; i < irregularLookup.length; i++){
            irregularLookup[i] = -1;
        }
        //Alphabet uppercaser 
        for(int i = 0; i < irregularLookup.length; i++){
            if
            (
                i >= 65+32 && i <= 90+32
            )
            {
                irregularLookup[i] = i-32;
            }
        }
        irregularLookup['1'] = '!';
        irregularLookup['2'] = '@';
        irregularLookup['3'] = '#';
        irregularLookup['4'] = '$';
        irregularLookup['5'] = '%';
        irregularLookup['6'] = '^';
        irregularLookup['7'] = '&';
        irregularLookup['8'] = '*';
        irregularLookup['9'] = '(';
        irregularLookup['0'] = ')';
        irregularLookup['-'] = '_';
        irregularLookup['\''] = '"';
        irregularLookup[','] = '<';
        irregularLookup['.'] = '>';
        irregularLookup['['] = '{';
        irregularLookup[']'] = '}';
        irregularLookup['\\'] = '|';
        irregularLookup['`'] = '~';
        irregularLookup['='] = '+';
        irregularLookup['/'] = '?';
        irregularLookup[';'] = ':';
    }
}
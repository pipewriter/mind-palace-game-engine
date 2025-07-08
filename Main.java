import java.awt.*;
import java.awt.event.*;
import java.awt.datatransfer.*;
import java.awt.image.*;
// Import the FileWriter class
import java.io.*;
import java.nio.*;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.List;
import java.util.Queue;
import java.lang.reflect.Field;

import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.zip.*;
import javax.imageio.*;
import java.lang.reflect.*;
import java.net.CacheRequest;
import java.util.stream.*;
import java.util.function.*;
import java.nio.file.*;
import java.util.*;
import java.awt.datatransfer.*;
import java.io.*;
import java.util.*;
import java.nio.*;
import javax.imageio.*;
import java.awt.image.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.MemoryUtil;
// import org.lwjgl.system.CallbackI.I;
// import org.lwjgl.system.CallbackI.P;
// import org.lwjgl.system.CallbackI.Z;
import org.w3c.dom.Text;

import java.io.Writer.*;
import javax.sound.sampled.*;

import jdk.jfr.consumer.RecordedMethod;

import javax.swing.*;
import java.awt.*;
import javax.swing.filechooser.*;
import org.lwjgl.glfw.*;
import java.util.concurrent.*;
import java.security.InvalidParameterException;
import java.security.Key;
import java.nio.file.*;
// 
// import static org.lwjgl.glfw.GLFWVidMode;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.*;
import static org.lwjgl.opengl.GL43.*;
import java.text.DecimalFormat;

import java.awt.datatransfer.*;
import java.util.regex.Pattern;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.Duration;

import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALC10;
import org.lwjgl.openal.AL10;
import org.lwjgl.openal.AL11;
import org.lwjgl.openal.ALCCapabilities;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.text.NumberFormat;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse.BodyHandlers;

class Tuple<X, Y> { 
    public X x;
    public Y y;
    public Tuple(X x, Y y) { 
      this.x = x; 
      this.y = y; 
    } 
}
class Tuple3<X, Y, Z> { 
    public X x;
    public Y y;
    public Z z;
    public Tuple3(X x, Y y, Z z) { 
      this.x = x; 
      this.y = y; 
      this.z = z;
    } 
}
class Tuple5<X, Y, Z, A, B> { 
    public final X x;
    public final Y y;
    public final Z z;
    public final A a;
    public final B b;
    public Tuple5(X x, Y y, Z z, A a, B b) { 
      this.x = x; 
      this.y = y; 
      this.z = z;
      this.a = a;
      this.b = b;
    } 
}
class MonoTuple5<X> { 
    public final X x;
    public final X y;
    public final X z;
    public final X a;
    public final X b;
    public MonoTuple5(X x, X y, X z, X a, X b) { 
      this.x = x; 
      this.y = y; 
      this.z = z;
      this.a = a;
      this.b = b;
    } 
}


class ClickBox{
    public Object o;
    public Object p;
    public Object q;
    public double screenX;
    public double screenY;
    public double screenWidth;
    public double screenHeight;
    public double screenZ;
    public int pass;
    public ClickBox(int pass, Object o,Object p,Object q, double screenX, double screenY, double screenWidth, double screenHeight){
        this.pass = pass;
        this.o = o;
        this.p = p;
        this.q = q;
        this.screenX = screenX;
        this.screenY = screenY;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }
}
class ClickBoxRegistry{
    static double storedOsx;
    static double storedOsy;

    static LinkedList<ClickBox> cbs= new LinkedList<ClickBox>(); //clickboxes; refreshed every frame
    public static void clear(){
        cbs.clear();
    }
    public static void clear(Object o){

    }
    public static void register(int pass, Object o, Object p, Object q, double screenX, double screenY, double screenWidth, double screenHeight){
        cbs.addLast(new ClickBox(pass, o,p,q, screenX, screenY, screenWidth, screenHeight));
    }
    public static ClickBox get(int pass, double mouseX_m1_to_1, double mouseY_mp5_to_p5){
        Iterator<ClickBox> cbi = cbs.iterator();
        LinkedList<ClickBox> foundStack = new LinkedList<ClickBox>();
        while(cbi.hasNext()){
            ClickBox p = cbi.next();
            if(p.screenX-p.screenWidth/2 <= mouseX_m1_to_1 && p.screenX+ p.screenWidth/2 > mouseX_m1_to_1
                && p.screenY-p.screenHeight/2 <= mouseY_mp5_to_p5 && p.screenY+p.screenHeight/2 > mouseY_mp5_to_p5 && pass == p.pass){
                foundStack.push(p);
            }
        }
        if(foundStack.size() == 0)
            return null;
        Iterator<ClickBox> fsi = foundStack.iterator();
        ClickBox cb = null;
        double heighestSeen = -1;
        while(fsi.hasNext()){
            ClickBox p = fsi.next();
            if(p.screenZ> heighestSeen){
                heighestSeen = p.screenZ;
                cb = p;
            }
        }
        return cb;
    }
}
 
class Input implements GLFWKeyCallbackI {
    public static long lastTouch = System.nanoTime();

    public static void simulateKey(int key){
        lastTouch = System.nanoTime();
        pressStack.push(new Tuple<Integer, Boolean >(key, true));
        pressStack.push(new Tuple<Integer, Boolean >(key, false));
    }
    public static boolean rightClickEngaged = false;
    public class InputMouse implements GLFWMouseButtonCallbackI {
        @Override
        public void invoke(long window, int button, int action, int mods) {
            // System.out.println("clicked");
            // System.out.println(window);
            // System.out.println(Main.window);
            // System.out.println(Main.topWindow);
            if(button == GLFW_MOUSE_BUTTON_2){
                rightClickEngaged =  action != GLFW.GLFW_RELEASE;
            }
            if(window == Main.topWindow){
                Input.clickStackTopWindow.push(new Tuple<Integer, Boolean >(button, action != GLFW.GLFW_RELEASE));
                return;
            }
            Input.clickStack.push(new Tuple<Integer, Boolean >(button, action != GLFW.GLFW_RELEASE));
        }
    }
    public static Tuple<Integer, Boolean> chewOneAdvancedClickTopWindow(){
        if(Input.clickStackTopWindow.isEmpty())
            return null;
        Tuple<Integer, Boolean> key = Input.clickStackTopWindow.removeLast(); // first pushed
        return key;
    }
    public static Tuple<Integer, Boolean> chewOneAdvancedClick(){
        if(Input.clickStack.isEmpty())
            return null;
        Tuple<Integer, Boolean> key = Input.clickStack.removeLast(); // first pushed
        return key;
    }
    protected static LinkedList<Tuple<Integer, Boolean>>   clickStack = new LinkedList<Tuple<Integer, Boolean>>();
    protected static LinkedList<Tuple<Integer, Boolean>>   clickStackTopWindow = new LinkedList<Tuple<Integer, Boolean>>();

    public static boolean[] keys = new boolean[65536];

    public static LinkedList<Tuple<Integer, Boolean>>   pressStack = new LinkedList<Tuple<Integer, Boolean>>();
    public static boolean                               simulatedShiftEnabled = false;
    public static boolean                               simulatedControlEnabled = false;
    public static boolean                               simulatedAltEnabled = false;

    public void invoke(long window, int key, int scancode, int action, int mods) {
        // if(window == Main.topWindow)
        //     return; // Abort, no control
        lastTouch = System.nanoTime();
        pressStack.push(new Tuple<Integer, Boolean >(key, action != GLFW.GLFW_RELEASE));
        if (key < 65536 && key >= 0){
            boolean toSet = action != GLFW.GLFW_RELEASE;
            keys[key] = toSet;
        }
    }
    public static boolean isKeyUp(int keycode) {
        return !keys[keycode];
    }
    public static boolean isKeyDown(int keycode) {
        return keys[keycode];
    }
}
class TimeLockingKey{

    static TextNodule globalTypingSwitch = null;
    int keycode;
    double keyLock;
    static final double BIG_LOCK_TIME = 0.3;
    static final double LITTLE_LOCK_TIME = 0.05;
    boolean transition = false;
    

    static int[] importantkeys = new int[]{
        GLFW_KEY_DELETE, GLFW_KEY_TAB,  GLFW_KEY_C, GLFW_KEY_X, GLFW_KEY_F, GLFW_KEY_V, GLFW_KEY_LEFT_BRACKET, GLFW_KEY_RIGHT_BRACKET, GLFW_KEY_PAGE_UP, GLFW_KEY_SPACE, GLFW_KEY_PAGE_DOWN,
        GLFW_KEY_ESCAPE, GLFW_KEY_ENTER, GLFW_KEY_Z, GLFW_KEY_P,  GLFW_KEY_Q,  GLFW_KEY_E, GLFW_KEY_F1, GLFW_KEY_R, GLFW_KEY_O, GLFW_KEY_I,GLFW_KEY_U,GLFW_KEY_H,  GLFW_KEY_BACKSPACE,
        GLFW_KEY_LEFT,GLFW_KEY_RIGHT,GLFW_KEY_UP,GLFW_KEY_DOWN,GLFW_KEY_Y, GLFW_KEY_T,GLFW_KEY_PAGE_DOWN,GLFW_KEY_PAGE_UP,GLFW_KEY_HOME,GLFW_KEY_END, GLFW_KEY_B,
         GLFW_KEY_D, GLFW_KEY_J, GLFW_KEY_F2, GLFW_KEY_F3, GLFW_KEY_F4, GLFW_KEY_F5, GLFW_KEY_F6,GLFW_KEY_F7, GLFW_KEY_G,
        GLFW_KEY_F8,GLFW_KEY_F9,GLFW_KEY_F10,GLFW_KEY_F11,GLFW_KEY_F12, GLFW_KEY_L, GLFW_KEY_GRAVE_ACCENT, GLFW_KEY_APOSTROPHE, GLFW_KEY_M, GLFW_KEY_N,
        GLFW_KEY_1, GLFW_KEY_2, GLFW_KEY_3, GLFW_KEY_4, GLFW_KEY_5, GLFW_KEY_6, GLFW_KEY_7, GLFW_KEY_8, GLFW_KEY_9, GLFW_KEY_0,
        GLFW_KEY_KP_1,GLFW_KEY_KP_2,GLFW_KEY_KP_3,GLFW_KEY_KP_4,GLFW_KEY_KP_5,GLFW_KEY_KP_6,GLFW_KEY_KP_7,GLFW_KEY_KP_8,GLFW_KEY_KP_9,GLFW_KEY_KP_0,
        GLFW_KEY_KP_ADD, GLFW_KEY_SLASH, GLFW_KEY_PERIOD, GLFW_KEY_COMMA, GLFW_KEY_S, GLFW_KEY_BACKSLASH,
        GLFW_KEY_K, GLFW_KEY_W, GLFW_KEY_A, GLFW_KEY_S, GLFW_KEY_D,
    }; 
    static HashMap<Integer, TimeLockingKey> keys = new HashMap<Integer, TimeLockingKey>();
    public static TimeLockingKey[] tlkArray;

    static{
        for(var a: importantkeys){
            keys.put(a, new TimeLockingKey(a));
        }
        int l = TextHelper.charToOffsetLookup.length;
        tlkArray = new TimeLockingKey[l];
        for(int i = 0; i < l; i++){
            tlkArray[i] = new TimeLockingKey(i);
        }
    }
    static TargetDataLine line;
    static{
        try{
            Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
            Mixer mixer = AudioSystem.getMixer(mixerInfo[2]);
            AudioFormat format = new AudioFormat(44100f, 2*8, 1, true, false);
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format); // format is an AudioFormat object
            DataLine.Info info2 = new DataLine.Info(SourceDataLine.class, format);
            if (!AudioSystem.isLineSupported(info2)) {
                System.out.println("broke");
            }
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(format); 
            line.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    boolean timeLockType;
    
    public TimeLockingKey(int keycode){
        this(keycode, true);
    }

    public TimeLockingKey(int keycode, boolean timeLockType){
        this.timeLockType = timeLockType;
        this.keycode = keycode;
    }
    public static void updateAll(double delta){
        keys.values().forEach(x->x.update(delta));
    }
    public void update(double delta){
        if(Input.isKeyUp(keycode)){
            keyLock = 0;
            transition = false;
        }
        if(keyLock > 0 && timeLockType){
            keyLock -= delta;
        }
    }
    private boolean expired(){
        return keyLock <= 0;
    }
    public boolean isNewPress(){
        boolean down = Input.isKeyDown(keycode);
        if(expired() && down){
            if(!transition){
                keyLock = BIG_LOCK_TIME;
                transition = true;
            }else{
                keyLock = LITTLE_LOCK_TIME;
            }
            return true;
        }
        return false;
    }
}


class IdeaBeacon extends GameObject{
    int c1,c2,spriteid;
    public IdeaBeacon(){
        spriteid = Math.random()>0.5?9:10;
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(spriteid);
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        c1 = b.getInt();
        c2 = b.getInt();
        spriteid = b.getInt();
        return this;
    }
    public int classid() {return 5;}
    public void clean() {}
    public void load() {}
    public GameObject clone() {
        var ib =  new IdeaBeacon();
        ib.c1=c1;
        ib.c2=c2;
        ib.spriteid=spriteid;
        return ib;
    }
    void sectorMarshal(ParkerBuffer b) {}
    void sectorUnMarshal(ParkerBuffer b) {}
}
class ProtoVideo extends Pasty{
    long fulltime = 0;
    public ProtoVideo(){}
    int sl(){
        return sidelength;
    }
    public int classid() {
        return 18;
    }
    public double calcPerc(){
        if(fulltime > 0){
            long clock = System.currentTimeMillis();
            long remainder = (clock%fulltime);
            double whereami = (remainder*1.0/fulltime);
            return whereami;
        }
        return 0;
    }
    public Pasty clone(){
        ProtoVideo p = new ProtoVideo();
        p.data = data.clone();
        p.height = height;
        p.width = width;
        p.whrat = whrat;
        p.size = size;
        p.fulltime = fulltime;
        p.sidelength = sidelength;
        {
            {
                // System.out.println("yo" + glfwGetCurrentContext());
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                // p.sprite.tempIntDataForStorage = null;
            }
        }
        return p;
    }
    Pasty _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        fulltime = b.getLong();
        sidelength = b.getInt();
        super._unmarshal(b, shouldreaddata);
        return this;
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putLong(fulltime);
        b.putInt(sidelength);
        super._marshal(b, shouldwritedata);
    }
    int sidelength = 1;
    static public ProtoVideo promote(Pasty other, long fulltime, int sidelength){
        System.out.println("here in promote... sidelength is... " + sidelength);
        ProtoVideo p = new ProtoVideo();
        p.sidelength = sidelength;
        p.data =   other.data.clone();
        p.height = other.height;
        p.width =  other.width;
        p.whrat =  other.whrat;
        p.size =   other.size;
        p.fulltime = fulltime;
        {
            {
                // System.out.println("yo" + glfwGetCurrentContext());
                p.sprite = new Spriter(p.data, p.width, p.height, sidelength, p.mso());
                // p.sprite.tempIntDataForStorage = null;
            }
        }
        return p;
    }
}
class ThreeDumb extends Pasty{
    double[] values;
    int[] sos;
    int subdivs = 1;
    int pointsCt = 1;
    int displayMode = 0;

    public ThreeDumb(){
        super();
    }
    int sl(){
        return subdivs;
    }
    int mso(){
        return pointsCt;
    }
    public boolean isWritable(){
        return values != null;
    }

    
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        if(shouldwritedata){
            b.putInt(pointsCt);
            b.putInt(subdivs);
            b.putIntArr(sos);
            b.putInt(displayMode);
            for(int i = 0; i < pointsCt*3; i++){
                b.putDouble(values[i]);
            }
        }
        super._marshal(b, shouldwritedata);
    }
    Pasty _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        if(shouldreaddata){
            pointsCt = b.getInt();
            subdivs = b.getInt();
            sos = b.getIntArr();
            displayMode = b.getInt();
            values = new double[pointsCt*3];
            for(int i = 0; i < pointsCt*3; i++){
                values[i] = b.getDouble();
            }
        }
        super._unmarshal(b, shouldreaddata);
        return this;
    }
    public int classid() {return 6;}
    public ThreeDumb clone() {
        var p =  new ThreeDumb();
        if(data == null) return null;
        p.data = data.clone();
        p.height = height;
        p.width = width;
        p.whrat = whrat;
        p.pointsCt = pointsCt;
        p.subdivs = subdivs;
        p.displayMode = displayMode;
        {
            {
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                // p.sprite.tempIntDataForStorage = null;
                p.sprite.clearRepaunch();
                p.sprite.repaunch = false;
            }
        }
        p.sos = sos.clone();
        p.values = values.clone();
        return p;
    }
    public BurningBush promote(){
        return promote(null);
    }
    // public BurningBush promote(File wavfile){
    //     try{
            
    //     }catch(Exception e){
    //         e.printStackTrace();
    //     }
    
    // }
    public BurningBush promote(byte[] customBytes){
        try{
        var p =  new BurningBush();
        p.data = data.clone();
        
        // go = p;
        // go.x =  ii+Math.random()*20-10;
        // go.z =  jj+Math.random()*20-10;
        // go.x = x;
        // go.z = y;
        // //necessary, should be solved by hfunc
        // // go.ydisp = heightAt2((int)go.x, (int)go.z,0)+8;
        // go.ydisp = ydisp;
        // go.rot = neck+Math.PI/2;
        p.x = x;
        p.z = z;
        p.ydisp = ydisp;
        p.rot = rot;

        p.height = height;
        p.width = width;
        p.whrat = whrat;
        p.pointsCt = pointsCt;
        p.subdivs = subdivs;
        p.displayMode = displayMode;
        {
            {
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                // p.sprite.tempIntDataForStorage = null;
                p.sprite.clearRepaunch();
                p.sprite.repaunch = false;
            }
        }
        p.sos = sos.clone();
        p.values = values.clone();
        // p.audioBytes = audioBytes;
        {
            File file = new File(Main.resourcefolder + "./output.wav");

            // File file = new File("./sound/output.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            // Get Audio Format information
            AudioFormat audioFormat = audioInputStream.getFormat();
            // Check if already PCM
            // Read the audio input stream into a byte array
            byte[] audioBytes = new byte[audioInputStream.available()];
            int readBytes = audioInputStream.read(audioBytes, 0, audioInputStream.available());
            if(customBytes != null)
                audioBytes = customBytes;
            // System.out.println("Read bytes " + readBytes);
            audioInputStream.close();
            p.audioBytes = audioBytes;
            p.load();

        }
        
        return p;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    void sectorMarshal(ParkerBuffer b) {
        super.sectorMarshal(b);
        b.putInt(pointsCt);
        b.putInt(subdivs);
        b.putIntArr(sos);
        b.putInt(displayMode);
        for(int i = 0; i < pointsCt*3; i++){
            b.putDouble(values[i]);
        }
    }
    void sectorUnMarshal(ParkerBuffer b) {
        super.sectorUnMarshal(b);
        // System.out.println("evenin govna");
        pointsCt = b.getInt();
        subdivs = b.getInt();
        sos = b.getIntArr();
        displayMode = b.getInt();
        // b.getInt(); // why was I setting displaymode here?
        values = new double[pointsCt*3];
        for(int i = 0; i < pointsCt*3; i++){
            values[i] = b.getDouble();
        }
    }
    public void load(){
        super.load();
        sprite.repaunch = true;
    }
    public void clean(){
        super.clean();
        values = null;
        sos = null;
        
    }
}
class BurningBush extends ThreeDumb {
    void hush(){
        if (loadedSource != 0) {
            AL10.alDeleteSources(loadedSource);
            checkALError("Deleting source");
        }
        if (loadedBuffer != 0) {
            AL10.alDeleteBuffers(loadedBuffer);
            checkALError("Deleting buffer");
        }
        
        loadedBuffer = AL10.alGenBuffers();
        loadedSource =  AL10.alGenSources();
        nextMillisToPlay = 0;
        playing = false;
    }
    void noise(){
        if(!Main.cycling_remote_mode){
            var bb = this;
            bb.playing = true;
            {
                AL10.alDeleteBuffers(bb.loadedBuffer);
                AL10.alDeleteSources(bb.loadedSource);
                bb.loadedBuffer = AL10.alGenBuffers();
                bb.loadedSource =  AL10.alGenSources();
            }
            bb.fuckmebuffer.position(0);
            bb.reloadaaa();
            bb.sprite.repaunch = false; // sometimes I am just SOO stupid

            AL10.alSourcePlay(bb.loadedSource);
        }
    }
    double temporary_reapply_start_holder = 0;
    double temporary_reapply_end_holder = 0;
    double rolloffValue = 3f;
    byte[] audioBytes = new byte[0];
    int mono = 1;
    int sampleRate = 44100;
    double currentwhereami = 0;
    double whereamioveride = 0;
    double whereamioveride_end = 1;
    int loadedSource = 0;
    int loadedBuffer = 0;
    long nextMillisToPlay = 0;

    boolean playing;
    boolean playing2;
    boolean pickupAgain;

    float gain;
    double offsetAsProportion;
    double cycleAsProportion;
    float a, b, c, d;

    ByteBuffer fuckmebuffer = null;

    public BurningBush() {
        super();
    }

    public void setGain(float gainValue) {
        AL10.alSourcef(loadedSource, AL10.AL_GAIN, gainValue);
        checkALError("Setting gain");
    }

    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(mono);
        b.putInt(sampleRate);
        b.putFloat(gain);
        b.putDouble(offsetAsProportion);
        b.putDouble(rolloffValue);
        if (shouldwritedata) {
            b.putByteArr(audioBytes);
        }
        super._marshal(b, shouldwritedata);
    }

    Pasty _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        mono = b.getInt();
        sampleRate = b.getInt();
        gain = b.getFloat();
        offsetAsProportion = b.getDouble();
        rolloffValue = b.getDouble();
        if (shouldreaddata) {
            audioBytes = b.getByteArr();
        }
        super._unmarshal(b, shouldreaddata);

        return this;
    }

    public int classid() {
        return 14;
    }

    public BurningBush clone() {
        var p = new BurningBush();
        p.data = data.clone();
        p.height = height;
        p.width = width;
        p.whrat = whrat;
        p.pointsCt = pointsCt;
        p.subdivs = subdivs;
        p.rolloffValue = rolloffValue;
        p.displayMode = displayMode;
        p.cycleAsProportion = cycleAsProportion;
        p.offsetAsProportion = offsetAsProportion;
        p.size = size;
        {
            p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
            p.sprite.clearRepaunch();
            p.sprite.repaunch = false;
        }
        p.sos = sos.clone();
        p.values = values.clone();
        p.audioBytes = audioBytes.clone();
        return p;
    }

    void sectorMarshal(ParkerBuffer b) {
        b.putByteArr(audioBytes);
        super.sectorMarshal(b);
    }

    void sectorUnMarshal(ParkerBuffer b) {
        audioBytes = b.getByteArr();
        super.sectorUnMarshal(b);
    }

    public void load() {
        super.load();
        fuckmebuffer = ByteBuffer.allocateDirect(audioBytes.length);
        fuckmebuffer.clear();
        fuckmebuffer.put(audioBytes);
        fuckmebuffer.flip();

        int buffer = AL10.alGenBuffers();
        checkALError("Generating buffer");

        int source = AL10.alGenSources();
        checkALError("Generating source");

        loadedSource = source;
        loadedBuffer = buffer;

        if (loadedSource == 0) {
            System.out.println("Failed to generate OpenAL source.");
            System.exit(0);
        }

        System.out.println("Setting loaded source to " + loadedSource);
    }

    public void reloadaaa() {
        AL10.alBufferData(loadedBuffer, AL10.AL_FORMAT_MONO16, fuckmebuffer, sampleRate);
        checkALError("Buffer data");

        AL10.alSourcei(loadedSource, AL10.AL_BUFFER, loadedBuffer);
        checkALError("Binding buffer to source");
    }

    public void clean() {
        if (loadedSource != 0) {
            AL10.alDeleteSources(loadedSource);
            checkALError("Deleting source");
        }
        if (loadedBuffer != 0) {
            AL10.alDeleteBuffers(loadedBuffer);
            checkALError("Deleting buffer");
        }
        loadedSource = 0;
        loadedBuffer = 0;
        nextMillisToPlay = 0;
        playing = false;

        System.out.println("CLEANING IT");
        super.clean();
    }

    private void checkALError(String operation) {
        int error = AL10.alGetError();
        if (error != AL10.AL_NO_ERROR) {
            System.err.println("OpenAL Error during " + operation + ": " + error);
        }
    }
}
class ShinyBauble extends GameObject{
    public ShinyBauble(){}
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {}
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {return this;
    }
    public int classid() {return 1;}
    public void clean() {}
    public void load() {}
    public GameObject clone() {
        return new ShinyBauble();
    }
    void sectorMarshal(ParkerBuffer b) {}
    void sectorUnMarshal(ParkerBuffer b) {}
}
class TenPortals extends GameObject{
    static TenPortals newEmpty(){
        var tp = new TenPortals();
        tp.portalNodules = new PortalNodule[10];
        return tp;
    }
    int c1,c2,c3,c4;
    PortalNodule[] portalNodules;
    public TenPortals(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void loadme(){
        if(portalNodules == null){
            System.out.println("portal nodules is null");
            return;
        } 
        for(int i = 0; i < 10; i++){
            Main.keyed_positions[i] = portalNodules[i];
        }
    }
    void distill(){
        portalNodules = new PortalNodule[10];
        for(int i = 0; i < 10; i++){
            var p = Main.keyed_positions[i];
            // keyed_positions[i] = portalNodules[i];
            portalNodules[i] = Main.keyed_positions[i]; 
            Main.keyed_positions[i] = null; // preference but i like it
        }
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        // System.out.println("MARSHALLING OUT TENPORTALS " + shouldwritedata);
        if(shouldwritedata){
            sectorMarshal(b);
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
            sectorUnMarshal(b);
        }
        return this;
    
    }
    public int classid() {
        return 16;
    }
    public void clean() {
        portalNodules = null;
    }
    public void load() {}
    public GameObject clone() {
        TenPortals tp = new TenPortals();
        tp.c1 = c1;
        tp.c2 = c2;
        tp.c3 = c3;
        tp.c4 = c4;
        if(portalNodules != null){
            tp.portalNodules = new PortalNodule[10];
            for(int i = 0; i < 10; i++){
                if(portalNodules[i] != null)
                    tp.portalNodules[i] = (PortalNodule) portalNodules[i].clone();
            }
        }
        return tp;
    }
    void sectorMarshal(ParkerBuffer b) {
        // System.out.println("MARSHALLING OUT TENPORTALS =");
        if(portalNodules != null){
            b.putInt(1);
            // System.out.println("MARSHALLING OUT TENPORTALS == ");
            for(int i = 0; i < 10; i++){
                if(portalNodules[i] != null){
                    // System.out.println("MARSHALLING OUT TENPORTALS === i");

                    b.putInt(1);
                    portalNodules[i]._marshal(b, true);
                }else{
                    b.putInt(0);
                }
            }
        }else{
            b.putInt(0);
        }
    }
    void sectorUnMarshal(ParkerBuffer b) {
        if(b.getInt() == 1){
            portalNodules = new PortalNodule[10];
            for(int i = 0; i < 10; i++){
                if(b.getInt() == 1){
                    portalNodules[i] = (PortalNodule)(new PortalNodule())._unmarshal(b, true);
                }
            }
        }
    }
}
class RecordNodule extends GameObject{
    double recordTime;
    byte[] bytes = null;
    int c1,c2,c3,c4;
    double radius;
    int mode;
    public RecordNodule(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putDouble(recordTime);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        b.putDouble(radius);
        b.putInt(mode);
        if(shouldwritedata){
            b.putByteArr(bytes);
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        recordTime = b.getDouble();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        radius = b.getDouble();
        mode = b.getInt();
        if(shouldreaddata){
            bytes = b.getByteArr();
        }
        return this;
    }
    public int classid() {
        return 4;
    }
    public void clean() {
        bytes = null;
    }
    public void load() {}
    public GameObject clone() {
        var rn = new RecordNodule();
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        rn.bytes = bytes;
        rn.recordTime = recordTime;
        return rn;
    }
    void sectorMarshal(ParkerBuffer b) {
        b.putByteArr(bytes);
    }
    void sectorUnMarshal(ParkerBuffer b) {
        bytes = b.getByteArr();
    }

}
class ObeliskNodule extends GameObject{
    
    long activationtime = -1;
    int c1,c2,c3,c4;
    public ObeliskNodule(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putLong(activationtime);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        if(shouldwritedata){
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        activationtime = b.getLong();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
        }
        return this;
    }
    public int classid() {
        return 17;
    }
    public void clean() {
    }
    public void load() {}
    public boolean isFresherThan(ObeliskNodule other){
        return activationtime > other.activationtime;
    }
    void activate(){
        activationtime = System.currentTimeMillis();
        System.out.println("setting activation time to " + activationtime);
        System.out.println("bumped into ObeliskNodule");
    }
    void sectorMarshal(ParkerBuffer b) {
    }
    void sectorUnMarshal(ParkerBuffer b) {
    }
    
    public GameObject clone() {
        var rn = new ObeliskNodule();
        // rn.style = style;
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        return rn;
    }
}
interface IHasAPasty{
    public Pasty getPasty();
}
class PressurePlate extends GameObject {
    int pjid;
    int c1,c2,c3,c4;
    public PressurePlate(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(pjid);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        if(shouldwritedata){
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        pjid = b.getInt();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
        }
        return this;
    }
    public int classid() {
        return 32;
    }
    public void clean() {
    }
    public void load() {}

    void sectorMarshal(ParkerBuffer b) {
    }
    void sectorUnMarshal(ParkerBuffer b) {
    }
    public GameObject clone() {
        var rn = new PressurePlate();
        // rn.style = style;
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        rn.pjid = pjid;
        return rn;
    }
}

class PumpJack extends GameObject implements IHasAPasty{
    public Pasty getPasty(){
        return hasAPasty;
    }
    public boolean isMatch(Pasty p ){
        if(hasAPasty != null){
            return (hasAPasty.isMatch(p));
        }
        return false;
    }

    public void breath(){
        // if(size*100)
    }
    boolean enabledisable;
    long activationtime = -1;
    int pjid = 0;
    double activationchance = -1;
    int c1,c2,c3,c4;
    public Pasty hasAPasty; // not worried about serialization
     // yet
     // do display first
    public PumpJack(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(hasAPasty == null ? 0: 1);
        if(hasAPasty != null){
            // hasAPasty._marshal(b, shouldstoredata);
            // hasAPasty._marshal(b, shouldstoredata);
            GameObject.marshal(b, hasAPasty, shouldwritedata);
        }
        b.putInt(0); // for future pasty list
        b.putLong(activationtime);
        b.putInt(pjid);
        b.putDouble(activationchance);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        if(shouldwritedata){
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        boolean has = b.getInt() == 1;
        if(has){
            // hasAPasty = new Pasty();
             hasAPasty =(Pasty) GameObject.unmarshal(b, shouldreaddata);

            // hasAPasty._unmarshal(b, shouldreaddata);
        }
        b.getInt(); // for future pasty list
        activationtime = b.getLong();
        pjid = b.getInt();
        activationchance = b.getDouble();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
        }
        return this;
    }
    public int classid() {
        return 26;
    }
    public void clean() {
        if(hasAPasty != null) hasAPasty.clean();
    }
    public void load() {
        if(hasAPasty != null) hasAPasty.load();
    }
    void sectorMarshal(ParkerBuffer b) {
        if(hasAPasty != null) hasAPasty.sectorMarshal(b);
    }
    void sectorUnMarshal(ParkerBuffer b) {
        if(hasAPasty != null) hasAPasty.sectorUnMarshal(b);
    }
    public GameObject clone() {
        var rn = new PumpJack();
        // rn.style = style;
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        rn.pjid = pjid;
        if(hasAPasty != null){
            rn.hasAPasty = hasAPasty.clone();
        }
        return rn;
    }
}
class CompressorNodule extends GameObject{
    
    long activationtime = -1;
    int c1,c2,c3,c4;
    public CompressorNodule(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putLong(activationtime);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        if(shouldwritedata){
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        activationtime = b.getLong();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
        }
        return this;
    }
    public int classid() {
        return 19;
    }
    public void clean() {
    }
    public void load() {}
    void sectorMarshal(ParkerBuffer b) {
    }
    void sectorUnMarshal(ParkerBuffer b) {
    }
    public GameObject clone() {
        var rn = new CompressorNodule();
        // rn.style = style;
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        return rn;
    }
}
class FollowPath extends GameObject{
    LinkedList<Tuple<Long,PortalNodule>> movements
        = new LinkedList<Tuple<Long,PortalNodule>>();

    long activationtime = -1;
    int c1,c2,c3,c4;
    public FollowPath(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putLong(activationtime);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        if(shouldwritedata){
            sectorMarshal(b);
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        activationtime = b.getLong();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
            sectorUnMarshal(b);
        }
        return this;
    }
    public int classid() {
        return 27;
    }
    public void clean() {
        movements = null;
    }
    public void load() {

    }
    public void bump(){

    }
    void sectorMarshal(ParkerBuffer b) {
        if(movements == null)
            movements = new LinkedList<Tuple<Long,PortalNodule>>();
        var i = movements.iterator();
        while(i.hasNext()){
            b.putInt(1);
            var ii = i.next();
            b.putLong(ii.x);
            GameObject.marshal(b, (PortalNodule)ii.y, false);
        }
        b.putInt(0);
    }
    void sectorUnMarshal(ParkerBuffer b) {
        if(movements == null)
            movements = new LinkedList<Tuple<Long,PortalNodule>>();
        while(b.getInt() == 1){
            var idk = b.getLong();
            var pn = (PortalNodule)GameObject.unmarshal(b, false);
            movements.addLast(new Tuple<Long,PortalNodule>(idk, pn));
        }
    }
    public GameObject clone() {
        var rn = new FollowPath();
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        rn.movements = new LinkedList<Tuple<Long,PortalNodule>>(movements);
        return rn;
    }
}
class ControllerNodule extends GameObject{
    int currentIndex = 0;
    LinkedList<PortalNodule> portalNodules = new LinkedList<PortalNodule>();
    long activationtime = -1;
    int c1,c2,c3,c4;
    public static ControllerNodule fromPortals(LinkedList<PortalNodule> pn){
        var cn = new ControllerNodule();
        cn.portalNodules = pn; // assume already cloned
        return cn;
    }

    public ControllerNodule(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putLong(activationtime);
        b.putInt(currentIndex);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        if(shouldwritedata){
            sectorMarshal(b);
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        activationtime = b.getLong();
        currentIndex = b.getInt();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
            sectorUnMarshal(b);
        }
        return this;
    }
    public int classid() {
        return 20;
    }
    public void clean() {
    }
    public void load() {}
    void goBack(){
        if(!portalNodules.isEmpty()){
            currentIndex--;
            if(currentIndex <= 0){
                currentIndex = 0;
                // resume position
            }
            portalNodules.get(currentIndex).activate();
        }
    }
    void goForward(){
        if(!portalNodules.isEmpty()){
            currentIndex++;
            if(currentIndex >= portalNodules.size()){
                currentIndex = portalNodules.size()-1;
                // resume position
            }
            portalNodules.get(currentIndex).activate();
        }
    }
    void sectorMarshal(ParkerBuffer b) {
        if(portalNodules == null)
            portalNodules = new LinkedList<PortalNodule>();
        var i = portalNodules.iterator();
        while(i.hasNext()){
            b.putInt(1);
            var ii = i.next();
            GameObject.marshal(b, (PortalNodule)ii, false);
        }
        b.putInt(0);
    }
    void sectorUnMarshal(ParkerBuffer b) {
        portalNodules = new LinkedList<PortalNodule>();
        while(b.getInt() == 1){
            var pn = (PortalNodule)GameObject.unmarshal(b, false);
            portalNodules.addLast(pn);
        }
    }
    
    public GameObject clone() {
        var rn = new ControllerNodule();
        // rn.style = style;
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        rn.currentIndex = currentIndex;
        rn.portalNodules = new LinkedList<PortalNodule>(portalNodules);
        return rn;
    }
}
class FolderNodule extends TextNodule implements IHasAPasty{
    public String transientKey;
    public Pasty hasAPasty;

    public LinkedList<GameObject> bakedObjects = new LinkedList<GameObject>(); 
    
    public Pasty getPasty(){
        return hasAPasty;
    }
    
    public String getTitle(){
        // if(bytes == null) return null;
        if(state == null) return null;
        String title =  state.stringify().split("\\R", 2)[0];
        System.out.println("here in folder nodule title is " + title);
        return title;
    }

    public FolderNodule(){ }
    void _marshal(ParkerBuffer b,  boolean shouldstoredata){
        b.putInt(0); // color;
        b.putInt(hasAPasty == null ? 0: 1);
        // b.putInt(attemptedPostProcessing ? 0: 1);
        if(hasAPasty != null){
            hasAPasty._marshal(b, shouldstoredata);
        }
        super._marshal(b, shouldstoredata);
    }
    void sectorMarshal(ParkerBuffer b){
        if(hasAPasty != null) hasAPasty.sectorMarshal(b);
        // b.putByteArr(bytes);
        for(var e: bakedObjects){
            b.putInt(1);
            GameObject.marshal(b, e, false);
            e.sectorMarshal(b);
        }
        b.putInt(0);
        super.sectorMarshal(b);
    }
    void sectorUnMarshal(ParkerBuffer b){
        if(hasAPasty != null) hasAPasty.sectorUnMarshal(b);
        // bytes = b.getByteArr();
        bakedObjects = new LinkedList<GameObject>();
        while(b.getInt() == 1){
            var e = GameObject.unmarshal(b, false);
            e.sectorUnMarshal(b);
            bakedObjects.addLast(e);
        }
        super.sectorUnMarshal(b);
    }
    FolderNodule _unmarshal(ParkerBuffer b, boolean shouldreaddata){
        b.getInt(); // color;
        boolean has = b.getInt() == 1;
        // attemptedPostProcessing = b.getInt() == 1;
        if(has){
            hasAPasty = new Pasty();
            hasAPasty._unmarshal(b, shouldreaddata);
        }

        super._unmarshal(b, shouldreaddata);
        return this;
    }
    public GameObject clone() {
        var tn = new FolderNodule();
        tn.state = state.clone();
        tn.sizeMulti = sizeMulti;
        if(hasAPasty != null){
            tn.hasAPasty = hasAPasty.clone();
        }
        tn.bakedObjects = new LinkedList<GameObject>();
        for(var e: bakedObjects){
            tn.bakedObjects.addLast(e.clone());
        }
        return tn;
    }
    public int classid() {
        return 24;
    }
    public void clean() {
        if(hasAPasty != null) hasAPasty.clean();
        bakedObjects = new LinkedList<GameObject>();
        super.clean();
    }
    public void load() {
        if(hasAPasty != null) hasAPasty.load();
    }
}
class Searcher extends TextNodule{
    
    public void teleportObject(GameObject go){
        // PortalNodule.lastchin =      Main.chin;
        // PortalNodule.lastnangle =    Main.neck;
        // PortalNodule.lastx =         Main.x;
        // PortalNodule.lastz =         Main.z;
        // PortalNodule.lastydisp = Main.dispy;
        // PortalNodule.newReturn();
        // go.chin =     0;
        go.rot =     rot + Math.PI/2;
        go.x =        x;
        go.z =        z;
        go.x+= Math.sin(rot+Math.PI/2)*PortalNodule.range*4;
        go.z+= Math.cos(rot+Math.PI/2)*PortalNodule.range*4;

        // Main.dispy =    ydisp;
    }
    public void teleportPlayer(){
        // PortalNodule.lastchin =      Main.chin;
        // PortalNodule.lastnangle =    Main.neck;
        // PortalNodule.lastx =         Main.x;
        // PortalNodule.lastz =         Main.z;
        // PortalNodule.lastydisp = Main.dispy;
        PortalNodule.newReturn();
        Main.chin =     0;
        Main.neck =     rot + Math.PI/2;
        Main.x =        x;
        Main.z =        z;
        Main.x+= Math.sin(rot+Math.PI/2)*PortalNodule.range*4;
        Main.z+= Math.cos(rot+Math.PI/2)*PortalNodule.range*4;

        // Main.dispy =    ydisp;
    }
    int c1,c2,c3,c4;
    public String getWord(){
        // if(bytes == null) return null;
        if(state == null) return null;
        String title =  state.stringify().split("\\R", 2)[0];
        System.out.println("here in searcher, word is " + title);
        return title;
    }
    public Searcher(){ 
        // state = null;
        sizeMulti = 6;
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b,  boolean shouldstoredata){
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        super._marshal(b, shouldstoredata);
    }
    void sectorMarshal(ParkerBuffer b){
        super.sectorMarshal(b);
    }
    void sectorUnMarshal(ParkerBuffer b){
        super.sectorUnMarshal(b);
    }
    Searcher _unmarshal(ParkerBuffer b, boolean shouldreaddata){
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        super._unmarshal(b, shouldreaddata);
        return this;
    }
    public GameObject clone() {
        var tn = new Searcher();
        tn.c1 = c1;
        tn.c2 = c2;
        tn.c3 = c3;
        tn.c4 = c4;
        tn.state = state.clone();
        tn.sizeMulti = sizeMulti;
        return tn;
    }
    public int classid() {
        return 28;
    }
    public void clean() { 
    }
    public void load() {
        var arrayList = state.rows;
        arrayList.subList(2, arrayList.size()).clear();
        if(Main.temporarilyNeededGameObjects.contains(this)){
            Main.temporarilyNeededGameObjects.remove(this);
            if(Main.temporarilyNeededGameObjects.isEmpty()){
                Main.addStatement("loading worldlinks complete ACK!");

            }
        }
        arrayList.subList(2, arrayList.size()).clear();
    }
}
class PhantomNodule extends GameObject{
    public boolean isObtainable(){
        return false;
    }
    public PhantomNodule(){}
    public int classid() {
        return 22;
    }    
     void        _marshal(ParkerBuffer b, boolean shouldwritedata){

     }
     GameObject  _unmarshal(ParkerBuffer b, boolean shouldreaddata){
        return this;
     }
    public  void clean(){}
    public  void load(){}
    public GameObject clone() {
        var rn = new PhantomNodule();
        return rn;
    }
    void sectorUnMarshal(ParkerBuffer b) {}
    void sectorMarshal(ParkerBuffer b) {}
    public PortalNodule convert(){
        var pn = new PortalNodule();
        // it should be facing the spot
        // pn.`ydisp = ydisp ;
        pn.destx = x+12*Math.sin(rot+Math.PI/2);
        pn.destz = z+12*Math.cos(rot+Math.PI/2);
        pn.nangle = rot-Math.PI/2;
        return pn;
    }
}
class PromiseNodule extends GameObject{
    public Thread watchThread;
    public boolean isObtainable(){
        return false;
    }
    public PromiseNodule(){}
    public int classid() {
        return 23;
    }    
    void        _marshal(ParkerBuffer b, boolean shouldwritedata){

    }
    GameObject  _unmarshal(ParkerBuffer b, boolean shouldreaddata){
    return this;
    }
    public  void clean(){}
    public  void load(){}
    public GameObject clone() {
        return this;
    }
    void sectorUnMarshal(ParkerBuffer b) {}
    void sectorMarshal(ParkerBuffer b) {}
}
class FunnelNodule extends GameObject{
    LinkedList<PhantomNodule> phantoms = new LinkedList<PhantomNodule>();
    LinkedList<PhantomNodule> completed;
    int c1,c2,c3,c4;

    public FunnelNodule(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        if(shouldwritedata){
            sectorMarshal(b);
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
            sectorUnMarshal(b);
        }
        return this;
    }
    public int classid() {
        return 21;
    }
    public void clean() {}
    public void load() {}
    void sectorMarshal(ParkerBuffer b) {
        {
            if(phantoms == null)
                phantoms = new LinkedList<PhantomNodule>();
            var i = phantoms.iterator();
            while(i.hasNext()){
                b.putInt(1);
                var ii = i.next();
                GameObject.marshal(b, (PhantomNodule)ii, false);
            }
            b.putInt(0);
        }
    }
    void sectorUnMarshal(ParkerBuffer b) {
        {
            phantoms = new LinkedList<PhantomNodule>();
            while(b.getInt() == 1){
                var pn = (PhantomNodule)GameObject.unmarshal(b, false);
                phantoms.addLast(pn);
            }
        }
    }
    
    public GameObject clone() {
        var rn = new FunnelNodule();
        // rn.style = style;
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        rn.phantoms = new LinkedList<PhantomNodule>(phantoms);
        return rn;
    }
}
class FunnelNodule2 extends FunnelNodule{

    public FunnelNodule2(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
        completed = new LinkedList<PhantomNodule>();
    }
    public int classid() {
        return 25;
    }
    void sectorMarshal(ParkerBuffer b) {
        {
            if(phantoms == null)
                phantoms = new LinkedList<PhantomNodule>();
            var i = phantoms.iterator();
            while(i.hasNext()){
                b.putInt(1);
                var ii = i.next();
                GameObject.marshal(b, (PhantomNodule)ii, false);
            }
            b.putInt(0);
        }
        {
            var phantoms = completed;
            if(phantoms == null)
                phantoms = new LinkedList<PhantomNodule>();
            var i = phantoms.iterator();
            while(i.hasNext()){
                b.putInt(1);
                var ii = i.next();
                GameObject.marshal(b, (PhantomNodule)ii, false);
            }
            b.putInt(0);
        }
    }
    void sectorUnMarshal(ParkerBuffer b) {
        {
            phantoms = new LinkedList<PhantomNodule>();
            while(b.getInt() == 1){
                var pn = (PhantomNodule)GameObject.unmarshal(b, false);
                phantoms.addLast(pn);
            }
        }
        {
            completed = new LinkedList<PhantomNodule>();
            while(b.getInt() == 1){
                var pn = (PhantomNodule)GameObject.unmarshal(b, false);
                completed.addLast(pn);
            }
        }
    }
    public GameObject clone() {
        var rn = new FunnelNodule2();
        // rn.style = style;
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        rn.phantoms = new LinkedList<PhantomNodule>(phantoms);
        rn.completed = new LinkedList<PhantomNodule>(completed);
        return rn;
    }
}
class PortalNodule extends GameObject{
    public static void defaultOne(){
        PortalNodule.lastchin =      Main.chin;
        PortalNodule.lastnangle =    Main.neck;
        PortalNodule.lastx =         Main.x;
        PortalNodule.lastz =         Main.z;
        PortalNodule.newReturn();
        Main.chin = 0;
        Main.neck = 0;
        Main.x = 1_000_000+2000;
        Main.z = 1_000_000+2000;
        PortalNodule.noise();
    }

    long activationtime = -1;
    int style = 0;
    int c1,c2,c3,c4;
    double destx, destz, nangle, chin, chary;
    static double range = 3;
    public PortalNodule(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(style);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        b.putDouble(destx);
        b.putDouble(destz);
        b.putDouble(nangle);
        b.putDouble(chin);
        b.putDouble(chary);
        if(shouldwritedata){
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        style = b.getInt();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        destx = b.getDouble();
        destz = b.getDouble();
        nangle = b.getDouble();
        chin = b.getDouble();
        chary = b.getDouble();
        if(shouldreaddata){
        }
        return this;
    }
    public int classid() {
        return 9;
    }
    public void clean() {
    }
    public void load() {}
    public PortalNodule clone() {
        var rn = new PortalNodule();
        // rn.style = style;
        rn.style = 0;
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        rn.destx = destx;
        rn.destz = destz;
        rn.nangle = nangle;
        rn.chin = chin;
        rn.style = style;
        rn.chary = chary;
        return rn;
    }
    void sectorMarshal(ParkerBuffer b) {
    }
    void sectorUnMarshal(ParkerBuffer b) {
    }
    static double lastchin = Integer.MAX_VALUE;
    static double lastnangle = 0;
    static double lastx = 0;
    static double lastz = 0;
    static double lastydisp = 0;

    static LinkedList<PortalNodule> returnals = new LinkedList<PortalNodule>();
    static LinkedList<PortalNodule> unreturnals = new LinkedList<PortalNodule>();

    static void returnal(){
        if(lastchin == Integer.MAX_VALUE) return;
        Main.chin = lastchin;
        Main.neck = lastnangle;
        Main.x = lastx;
        Main.z = lastz;
        Main.dispy = lastydisp;

        // ALSO PUSH BACK SINCE OTHERWISE YOU GET STUCK IN THE PORTAL DUH
        
        Main.x+= Math.sin(Main.neck+Math.PI)*PortalNodule.range*4;
        Main.z+= Math.cos(Main.neck+Math.PI)*PortalNodule.range*4;
        noise();
    }
    static void newReturn(){
        unreturnals.clear();
        PortalNodule pn = new PortalNodule();
        pn.chin =      Main.chin;
        pn.nangle =    Main.neck;
        pn.destx =         Main.x;
        pn.destz =         Main.z;
        pn.chary = Main.dispy;
        returnals.addFirst(pn);
    }
    static void doReReturnal(){
        if(unreturnals.isEmpty()) return;
        var pn = unreturnals.removeFirst();
        returnals.addLast(pn);
        Main.chin = pn.chin;
        Main.neck = pn.nangle;
        Main.x = pn.destx;
        Main.z = pn.destz;
        Main.dispy = pn.chary;
        Main.x+= Math.sin(Main.neck+Math.PI)*PortalNodule.range*4;
        Main.z+= Math.cos(Main.neck+Math.PI)*PortalNodule.range*4;
    }
    static void doReturn(){
        if(returnals.isEmpty()) return;
        var pn = returnals.removeFirst();
        System.out.println("Did it, there are no this many left " + returnals.size());
        unreturnals.addLast(pn);
        Main.chin = pn.chin;
        Main.neck = pn.nangle;
        Main.x = pn.destx;
        Main.z = pn.destz;
        Main.dispy = pn.chary;

        // ALSO PUSH BACK SINCE OTHERWISE YOU GET STUCK IN THE PORTAL DUH
        
        Main.x+= Math.sin(Main.neck+Math.PI)*PortalNodule.range*4;
        Main.z+= Math.cos(Main.neck+Math.PI)*PortalNodule.range*4;

    }
    void activate(){
        System.out.println("portal jump from "+ Main.x + "," + Main.z + " to " + destx + "," + destz);
        // PortalNodule.lastchin =      Main.chin;
        // PortalNodule.lastnangle =    Main.neck;
        // PortalNodule.lastx =         Main.x;
        // PortalNodule.lastz =         Main.z;
        // PortalNodule.lastydisp = Main.dispy;
        PortalNodule.newReturn();
        Main.chin = chin;
        Main.neck = nangle;
        Main.x = destx;
        Main.z = destz;
        Main.dispy = chary;
        noise();
    }
    void noiselessActivate(){
        // System.out.println("portal jump from "+ Main.x + "," + Main.z + " to " + destx + "," + destz);
        // lastchin =      Main.chin;
        // lastnangle =    Main.neck;
        // lastx =         Main.x;
        // lastz =         Main.z;
        // lastydisp = Main.dispy;
        Main.chin = chin;
        Main.neck = nangle;
        Main.x = destx;
        Main.z = destz;
        Main.dispy = chary;
    }
    void noiselessActivateNO_HEAD(){
        // System.out.println("portal jump from "+ Main.x + "," + Main.z + " to " + destx + "," + destz);
        // lastchin =      Main.chin;
        // lastnangle =    Main.neck;
        // lastx =         Main.x;
        // lastz =         Main.z;
        // lastydisp = Main.dispy;
        // Main.chin = chin;
        // Main.neck = nangle;
        Main.x = destx;
        Main.z = destz;
        Main.dispy = chary;
    }
    static void noise(){
        if(!Main.cycling_remote_mode){
            var bb = Main.poc_sfx_warp;
            bb.playing = true;
            {
                AL10.alDeleteBuffers(bb.loadedBuffer);
                AL10.alDeleteSources(bb.loadedSource);
                bb.loadedBuffer = AL10.alGenBuffers();
                bb.loadedSource =  AL10.alGenSources();
            }
            bb.fuckmebuffer.position(0);
            bb.reloadaaa();
            bb.sprite.repaunch = false; // sometimes I am just SOO stupid

            AL10.alSourcePlay(bb.loadedSource);
        }
    }
}
class TextNodule extends GameObject{
    public State state;
    double sizeMulti = 2;
    long timed_id;
    static{
    }
    public boolean isWritable(){
        return state != null;
    }
    public State getImportantState(){
        return state;
    }

    public void openIntoVirtualFolder(){
        Main.deleteFolderContents(new File("VirtualFolder"));
        // if virtual folder does not exist, create
        var f = (new File("VirtualFolder"));
        if(!f.exists()){
            f.mkdirs();
        }
        try{
            if(state != null){
                {
                    try{
                        // java.awt.Desktop.getDesktop().browse(f.toURI());
                    }catch(Exception e){
                        System.out.println("ERROR OPENING FILE");
                        e.printStackTrace();
                    }
                }
                //I believe this should work
                String filename = "file"+this.hashCode() + ".txt";
                String out = "./VirtualFolder/"+filename;
                File theFile = new File(out);
                FileOutputStream fileOutputStream = new FileOutputStream(out);
                byte[] bytes = state.stringify().getBytes("UTF-8");
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                Main.linkTextNodule = this;
                ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "code", "--new-window", out);
                Process p = pb.start();
                p.waitFor();
                System.out.println("ParkerParker GOT HERE GOT HERE");
            }else{
                System.out.println("FileNodule state is null");
            }
        }catch(Exception e){
            System.out.println("FileNodule error openinto");
            e.printStackTrace();
        }
    }
    public static int hashForTwoFloats(double x, double y) {
        int result = 17;  // A prime number
        result = 31 * result + Double.hashCode(x);  // Another prime number
        result = 31 * result + Double.hashCode(y);
        return Math.abs(result);
    }
    public void openIntoVirtualFolderSpecial(){
        try{
            if(state != null){
                int hash = hashForTwoFloats(x,z);
                String name = "dynamic_txt_" + hash + ".txt";
                //I believe this should work
                String out = "./VirtualFolder/"+name;
                File theFile = new File(out);
                FileOutputStream fileOutputStream = new FileOutputStream(out);
                byte[] bytes = state.stringify().getBytes("UTF-8");
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                // Main.linkTextNodule = this;
                // ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/C", "code", "--new-window", out);
                // Process p = pb.start();
                // p.waitFor();
                // System.out.println("ParkerParker GOT HERE GOT HERE");
            }else{
                System.out.println("FileNodule state is null");
            }
        }catch(Exception e){
            System.out.println("FileNodule error openinto");
            e.printStackTrace();
        }
    }
    public TextNodule(){
        state = new State();
        timed_id = System.currentTimeMillis(); // will be overwritten on _unmarshal, but not creation
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putDouble(sizeMulti);
        // b.putLong(timed_id);
        if(shouldwritedata){
            sectorMarshal(b);
        }
        landed = false;
    }
    public boolean landed = true;
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        sizeMulti = b.getDouble();
        // timed_id = b.getLong();
        if(shouldreaddata){
            // System.out.println("WOOHOO HERE WE GO");
            sectorUnMarshal(b);
            landed = false;
        }else{
            // System.out.println("SHOULD NOT BE HERE");
            landed = true;
        }
        return this;
    }
    //THIS IS A MASSIVE LIABILITY
    public int classid() {return 2;}
    public void clean() {
        if(!this.equals(TimeLockingKey.globalTypingSwitch)){  // special case for distance-edits
            if(state != null)
                state.specialClean();
            state = null;
        }else{
            System.out.println("Rare");
        }
    }
    public void load() {
        if(!this.equals(TimeLockingKey.globalTypingSwitch)){  // special case for distance-edits
            state.specialLoad();
            state.rows.remove(state.rows.size()-1);
            state.curb();
        }
    }
    public GameObject clone() {
        var tn = new TextNodule();
        tn.state = state.clone();
        tn.sizeMulti = sizeMulti;
        tn.size = size;
        return tn;
    }
    void sectorMarshal(ParkerBuffer b) {
        // System.out.println("Marshalling out state " + state.stringify());
        getImportantState().marshal(b);
    }
    void sectorUnMarshal(ParkerBuffer b) {
        // if(state == null || (state.rows.size() == 1 && state.rows.get(0).size() == 0)){ // special case for distance-edits
            // System.out.println("as expected...UNPACK");
            state = new State();
            state.unmarshal(b);
            // System.out.println("Just read in the state, it is " + state.stringify());
        // }else{
        //     var dropstate = new State();
        //     dropstate.unmarshal(b); // DROP IT HARMLESSLY
        //     System.out.println("Rare UNPACK");
        // }
    }
}
class TextNodule2 extends TextNodule{
    int c1, c2,c3,mode;
    public TextNodule2(){
        super();
        c1 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putDouble(sizeMulti);
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(mode);
        if(shouldwritedata){
            sectorMarshal(b);
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        sizeMulti = b.getDouble();
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        mode = b.getInt();
        if(shouldreaddata){
            sectorUnMarshal(b);
        }
        return this;
    }
    //THIS IS A MASSIVE LIABILITY
    public int classid() {return 3;}
    public GameObject clone() {
        var tn = new TextNodule2();
        tn.state = state.clone();
        tn.c1 = c1;
        tn.mode = mode;
        tn.sizeMulti = sizeMulti;
        return tn;
    }
    // void sectorMarshal(ParkerBuffer b) {
    //     state.marshal(b);
    // }
    // void sectorUnMarshal(ParkerBuffer b) {
    //     if(state == null){ // special case for distance-edits
    //         state = new State();
    //         state.unmarshal(b);
    //     }
    // }
}

class FileNodule extends TextNodule implements IHasAPasty{

    public State transientAscii = null;
    public State oldStateForSaving = null;

    public State getImportantState(){
        if(oldStateForSaving != null)
            return oldStateForSaving;
        return state;
    }
    public String transientKey;
    public LinkedList<String> transientPath;
    public Pasty getPasty(){
        return hasAPasty;
    }

    public Pasty hasAPasty;

    public String getTitle(){
        if(bytes == null) return null;
        if(oldStateForSaving != null)
            return oldStateForSaving.stringify().split("\\R", 2)[0];

        return state.stringify().split("\\R", 2)[0];
    }

    public FileNodule(){ }
    byte[] bytes = null;
    boolean cachedIsFileInterestingCheck = true;
    boolean attemptedPostProcessing;

    public static GameObject readIn(String fileString, boolean shoulddelete){
        FileNodule fn = new FileNodule();
        Main.p(" FileNodule reading file");
        System.out.println("FileNodule reading file");
        {

            class FFMpegThumbnailGenerator {
                public void generateThumbnail(String inputPath, String outputPath) throws IOException {
                    ProcessBuilder processBuilder = new ProcessBuilder(
                        "../../ffmpeg/ffmpeg.exe","-y", "-i", inputPath,  "-vframes", "1", outputPath
                    );


                    processBuilder.redirectErrorStream(true);
                    Process process = processBuilder.start();

                    // Handle the process's input/output streams if necessary

                    
                    try {
                        System.out.println("before");
                        // Wait for a maximum of 1 second
                        if (!process.waitFor(1, TimeUnit.SECONDS)) {
                            System.out.println("Process timeout. Terminating the process.");
                            process.destroy(); // Terminate the process
                            // Handle the timeout situation, e.g., throw an exception or return an error
                        } else {
                            System.out.println("after");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        // Handle the interruption
                    }
                }

                public void thumbnail(String inputPath, String outputPath) {
                    try {
                        generateThumbnail(inputPath, "thumbnail.bmp");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            (new FFMpegThumbnailGenerator()).thumbnail(fileString, "thumbnail.bmp");
            System.out.println("DID IT WORK " + (new File("thumbnail.jpg").exists()));

        }
        try{
            File file = new File(fileString);
            File thumbnailfile = new File("thumbnail.bmp");
            // System.out.println("is it........ uh..... " + ((new File(fileString)).exists()));

            if(thumbnailfile.exists()){
                try{
                    boolean parkercheck = fileString.toLowerCase().endsWith( ".png".toLowerCase()) ||
                        fileString.toLowerCase().endsWith(".jpeg".toLowerCase()) ||
                        fileString.toLowerCase().endsWith( ".jpg".toLowerCase()) ||
                        fileString.toLowerCase().endsWith( ".bmp".toLowerCase());
                    boolean idek = !DragonDrop.checkBoxValue  && (fileString.toLowerCase().endsWith( ".png".toLowerCase()) ||
                        fileString.toLowerCase().endsWith(".jpeg".toLowerCase()) ||
                        fileString.toLowerCase().endsWith( ".jpg".toLowerCase()) ||
                        fileString.toLowerCase().endsWith( ".bmp".toLowerCase()) ||
                        fileString.toLowerCase().endsWith(".webp".toLowerCase()));
                    var p = new Pasty();
                    System.out.println("Ok I think this is the crux " + idek + " " + parkercheck);
                    var bi = ImageExpirements.pngToBI("thumbnail.bmp");
                    if(idek && parkercheck){
                        bi = ImageExpirements.pngToBI(fileString);
                    }

                    p.width = bi.getWidth();
                    p.height = bi.getHeight();
                    int[] ia = ImageExpirements.biToPix(bi);
                    p.whrat = bi.getWidth()*1.0/bi.getHeight();
                    p.data = ia;
                    // for(int i = 0; i < 1000; i++){
                    //     System.out.println("What the fuck " + ia[i]);
                    // }
                    System.out.println(p.data.length + " " + p.whrat);
                    // Main.pleaseloadme.add(p);
                    // p.sprite = new Spriter(bi, p.sl(), p.mso());
                    p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());

                    p.load();
                    // pasties.add(p);
                    fn.hasAPasty = p;
                    System.out.println("finished assigning hasapasty");
                    thumbnailfile.delete();
                    
                    // IS A KNOWN TYPE OF IMAGE ????
                    if(idek){    

                        if(shoulddelete)file.delete();

                        return p;
                    }
                    // System.exit(0);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            // System.out.println("");
            {
                //FILE GET NAME (NOT DIRECTORY)
                String s = file.getName();
                fn.state = new State();
                fn.state.insert(s);
            }
                    // System.out.println("checking..... hasa " + fn.hasAPasty);
            fn.bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(fn.bytes);
            fileInputStream.close();


            // try this to solve the delete new files rpoblem
            if(shoulddelete)file.delete();
            return fn;
        }catch(Exception e){
            System.out.println("FileNodule error readin " +fileString);
            e.printStackTrace();
            return null;
        }
    }
    public static void readInSerialDump(String fileString){
        // Main.p(" FileNodule reading file");
        try{
            File file = new File(fileString);

            ParkerBuffer pb =
                new ParkerBuffer(false, fileString);

            int val = pb.getInt();
            while(val != 0){
                // try{
                    Main.intoInventory(GameObject.unmarshal(pb, true));

                    // Main.intoInventory(GameObject.unmarshal(pb, k, true));
                    val = pb.getInt();
                // }catch(Exception e){
                //     System.out.println("error");
                // }
            }
            pb.close();
            Main.addStatement("readInSerialDump complete");
            // try this to solve the delete new files rpoblem
            file.delete();
        }catch(Exception e){
            System.out.println("FileNodule error readin " +fileString);
            Main.addStatement("FileNodule error readin " +fileString);
            e.printStackTrace();
        }
    }
    public static LinkedList<GameObject> readInSerialDumpGetList(String fileString){
        // Main.p(" FileNodule reading file");
        LinkedList<GameObject> gos = new LinkedList<GameObject>();
        try{
            File file = new File(fileString);
            // ByteBuffer.wrap(serialdump)
            // 300_000_000 was the default
            ParkerBuffer pb = new ParkerBuffer(false, fileString, ByteBuffer.allocate(50_000_000));
            // ParkerBuffer pb = new ParkerBuffer(false, fileString, ByteBuffer.allocate(300_000_000));
            // ParkerBuffer pb = new ParkerBuffer(false, fileString);
            // byte[] serialdump = Files.readAllBytes(file.toPath());
            // ParkerBuffer pb = new ParkerBuffer(false, fileString, ByteBuffer.wrap(serialdump));
            
            int val = pb.getInt();
            while(val != 0){

                gos.add(GameObject.unmarshal(pb, true));

                // Main.intoInventory(GameObject.unmarshal(pb, k, true));
                val = pb.getInt();
            }
            pb.close();
            // try this to solve the delete new files rpoblem
            return gos;
        }catch(Exception e){
            System.out.println("FileNodule error readin " +fileString);
            e.printStackTrace();
        }
        return null;
    }
    public void readInDontDelete(String fileString){
        Main.p(" FileNodule reading file");
        try{
            File file = new File(fileString);
            {
                //FILE GET NAME (NOT DIRECTORY)
                String s = file.getName();
                state = new State();
                state.insert(s);
            }
            bytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(bytes);
            fileInputStream.close();


            // try this to solve the delete new files rpoblem
            // file.delete();
        }catch(Exception e){
            System.out.println("FileNodule error readin " +fileString);
            e.printStackTrace();
        }
    }
    void _marshal(ParkerBuffer b,  boolean shouldstoredata){
        b.putInt(hasAPasty == null ? 0: 1);
        b.putInt(attemptedPostProcessing ? 0: 1);
        if(hasAPasty != null){
            hasAPasty._marshal(b, shouldstoredata);
        }
        super._marshal(b, shouldstoredata);
    }
    void sectorMarshal(ParkerBuffer b){
        if(hasAPasty != null) hasAPasty.sectorMarshal(b);
        b.putByteArr(bytes);
        super.sectorMarshal(b);
    }
    void sectorUnMarshal(ParkerBuffer b){
        if(hasAPasty != null) hasAPasty.sectorUnMarshal(b);
        bytes = b.getByteArr();
        super.sectorUnMarshal(b);
    }
    FileNodule _unmarshal(ParkerBuffer b, boolean shouldreaddata){
        boolean has = b.getInt() == 1;
        attemptedPostProcessing = b.getInt() == 1;
        if(has){
            hasAPasty = new Pasty();
            hasAPasty._unmarshal(b, shouldreaddata);
        }

        super._unmarshal(b, shouldreaddata);
        return this;
    }

    public GameObject clone() {
        var tn = new FileNodule();
        if(oldStateForSaving != null){
            tn.state = oldStateForSaving.clone();
        }else{
            tn.state = state.clone();
        }
        tn.sizeMulti = sizeMulti;
        tn.bytes = bytes.clone();
        if(hasAPasty != null){
            tn.hasAPasty = hasAPasty.clone();
        }
        return tn;
    }
    public void load(){
        if(hasAPasty != null) hasAPasty.load();
    }
    public void clean(){
        if(hasAPasty != null) hasAPasty.clean();
        // hasAPasty = null;
        super.clean();
        bytes = null;
    }
    public int classid() {
        return 13;
    }
}
class LinkNodule extends TextNodule{
    public LinkNodule(){ }
    public GameObject clone() {
        var tn = new LinkNodule();
        tn.state = state.clone();
        tn.sizeMulti = sizeMulti;
        return tn;
    }
    public int classid() {
        return 10;
    }
    public void launch(){
        //i literally forgot what
        //it was
        try{
            String toOpen = "";
            if(state != null){
                toOpen = state.stringify("");
            }
            if(toOpen != null && toOpen.length() < 5) return;

            java.awt.Desktop.getDesktop().browse(new java.net.URI(
                toOpen
            ));
        }catch(Exception e){
            System.out.println("ERROR OPENING FILE");
            e.printStackTrace();
        }
    }
}
class LudeonNodule extends TextNodule2{
    State other_state = null;
    int a_index = -1;
    int b_index = -1;
    public LudeonNodule(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        state = new State();
        sizeMulti=20;
    }
    public int classid() {return 7;}
    public GameObject clone() {
        var tn = new LudeonNodule();
        tn.state = state.clone();
        tn.c1 = c1;
        tn.mode = mode;
        tn.sizeMulti = sizeMulti;
        return tn;
    }
    void setState(){
        int rows = state.rows.size() - 1;
        if(rows >= 2){
            a_index = (new Random()).nextInt(rows);
            b_index = a_index-1;
            if(b_index < 0){
                b_index = a_index+1;
            }
            if(Math.random() > 0.5){
                int temp = a_index;
                a_index = b_index;
                b_index = temp;
            }
            // while(b_index == a_index){b_index= (new Random()).nextInt(rows-1);}//keep trying
            var a = state.rows.get(a_index).get(0);
            var b = state.rows.get(b_index).get(0);
            other_state = new State();
            var toAdd = other_state.rows.get(0);
            toAdd.add(a);
            toAdd.add(new RichChar(' '));
            toAdd.add(new RichChar('v'));
            toAdd.add(new RichChar('s'));
            toAdd.add(new RichChar(' '));
            toAdd.add(b);
        }
    }
    void left(){
        var left = state.rows.get(a_index);
        var right = state.rows.get(b_index);
        if(b_index < a_index){
            state.rows.set(a_index, right);
            state.rows.set(b_index, left);
        }
        {
            final Runnable runnable =
                (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
            if (runnable != null) runnable.run();
        }
        setState();
    }
    void right(){
        var left = state.rows.get(a_index);
        var right = state.rows.get(b_index);
        if(b_index > a_index){
            state.rows.set(a_index, right);
            state.rows.set(b_index, left);
        }
        {
            final Runnable runnable =
                (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
            if (runnable != null) runnable.run();
        }
        setState();
    }
    void goback(){
        other_state = null;
    }
}

class ChestNodule extends GameObject{
    int c1,c2,c3,c4;
    static double range = 3;
    byte[] serialdump = null;
    public ChestNodule(){
        c1 = (int)(Math.random()*0xff_ff_ff);
        c2 = (int)(Math.random()*0xff_ff_ff);
        c3 = (int)(Math.random()*0xff_ff_ff);
        c4 = (int)(Math.random()*0xff_ff_ff);
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(c1);
        b.putInt(c2);
        b.putInt(c3);
        b.putInt(c4);
        if(shouldwritedata){
            b.putByteArr(serialdump);
        }
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        c1 = b.getInt();
        c2 = b.getInt();
        c3 = b.getInt();
        c4 = b.getInt();
        if(shouldreaddata){
            serialdump = b.getByteArr();
        }
        return this;
    }
    public int classid() {
        return 11;
    }
    public void clean() {
        serialdump = null;
    }
    public void load() {}
    
    public GameObject clone() {
        var rn = new ChestNodule();
        rn.c1 = c1;
        rn.c2 = c2;
        rn.c3 = c3;
        rn.c4 = c4;
        if(serialdump != null)
            rn.serialdump = serialdump.clone();
        return rn;
    }
    void sectorMarshal(ParkerBuffer b) {
        b.putByteArr(serialdump);
    }
    void sectorUnMarshal(ParkerBuffer b) {
        serialdump = b.getByteArr();
    }
    void activate(){
        if(serialdump == null) return;
        if(serialdump.length == 0) return;
        Main.p("reading..." + serialdump.length);
        Main.p(Arrays.toString(Arrays.copyOf(serialdump, 100)));
        ParkerBuffer b = new ParkerBuffer(ByteBuffer.wrap(serialdump));
        b.getInt(); //throw away one just to make up for parkerbuffer weirdness
        while(b.getInt() == 1){
            Main.p("and adding...");
            Main.intoInventory(GameObject.unmarshal(b, true));
        }
    }
}
class Shader extends Pasty {
    void sectorUnMarshal(ParkerBuffer b){
        byte[] ba = b.getByteArr();
        try{
            fragShaderArtifact = new String(ba, "UTF-8");
        }catch(Exception e){e.printStackTrace();}
        super.sectorUnMarshal(b);
    }
    Pasty _unmarshal(ParkerBuffer b, boolean shouldreaddata){
        if(shouldreaddata){
            byte[] ba = b.getByteArr();
            try{
                fragShaderArtifact = new String(ba, "UTF-8");
            }catch(Exception e){e.printStackTrace();}
        }
        super._unmarshal(b, shouldreaddata);
        return this;
    }
    void _marshal(ParkerBuffer b,  boolean shouldstoredata){
            if(shouldstoredata){
            b.putByteArr(fragShaderArtifact.getBytes());
        }
        super._marshal(b, shouldstoredata);
    }
    void sectorMarshal(ParkerBuffer b){
        b.putByteArr(fragShaderArtifact.getBytes());
        super.sectorMarshal(b);
    }
    static Shader promote(Pasty pasty, String fragShaderArtifact){
        Shader p = new Shader();
        p.fragShaderArtifact = fragShaderArtifact;
        p.data = pasty.data.clone();
        p.height = pasty.height;
        p.width = pasty.width;
        p.whrat = pasty.whrat;
        p.rot = pasty.rot;
        p.x = pasty.x;
        p.ydisp = pasty.ydisp;
        p.z = pasty.z;
        p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
        // p.sprite.tempIntDataForStorage = null;
        return p;
    }
    public Shader clone(){
        Shader p = new Shader();
        p.fragShaderArtifact = fragShaderArtifact;
        p.data = data.clone();
        p.height = height;
        p.width = width;
        p.whrat = whrat;
        {
            {
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                // p.sprite.tempIntDataForStorage = null;
            }
        }
        return p;
    }
    public void load(){
        super.load();
    }
    public void clean(){
        fragShaderArtifact = null;
        super.clean();
    }
    public int classid(){
        return 8;
    }
    public Shader(){}
}

class ParkerQuizItem extends Pasty {
    public double random_temporary_phi = (Math.random())*Math.PI*2;
    // public double random_temporary_theta = (Math.random()-0.5)*Math.PI/4+Math.PI/16;
    // public double random_temporary_theta = (Math.random()-0.5)*Math.PI/2;
    public double random_temporary_theta = Math.acos(Math.random()-0.5)*2;

    public static double[] rotatePoint(double px, double py, double pz, double theta, double phi) {
        // Convert degrees to radians
        // double theta = Math.toRadians(thetaDeg);
        // double phi = Math.toRadians(phiDeg);

        // Rotation matrices components
        double cosTheta = Math.cos(theta);
        double sinTheta = Math.sin(theta);
        double cosPhi = Math.cos(phi);
        double sinPhi = Math.sin(phi);

        // Apply rotation around Z-axis
        double xRotatedZ = cosTheta * px - sinTheta * py;
        double yRotatedZ = sinTheta * px + cosTheta * py;
        double zRotatedZ = pz; // Z remains unchanged for rotation around Z-axis

        // Apply rotation around Y-axis to the result of Z-axis rotation
        double xRotatedFinal = cosPhi * xRotatedZ + sinPhi * zRotatedZ;
        double yRotatedFinal = yRotatedZ; // Y remains unchanged for rotation around Y-axis
        double zRotatedFinal = -sinPhi * xRotatedZ + cosPhi * zRotatedZ;

        // Return the rotated coordinates
        return new double[] {xRotatedFinal, yRotatedFinal, zRotatedFinal};
    }
    public double alignment(double incoming_phi, double incoming_theta){
        //phi   pi *2
        //theta pi

        double t1 = random_temporary_theta;
        double t2 = incoming_theta;
        double p1 = random_temporary_phi;
        double p2 = incoming_phi;
        // Calculate the angular distance in radians
        double angle = Math.acos(Math.sin(t1) * Math.sin(t2) +
                                 Math.cos(t1) * Math.cos(t2) * Math.cos(p2 - p1));

        // System.out.println(angle/Math.PI);
        
        double idk = 1 - angle/Math.PI;
        // idk = Math.pow(idk/,2);
        idk-=0.8;
        if(idk < 0)idk = 0;
        idk*=5;
        // idk = 
        return idk;
    }

    public int mode;
    public static ParkerQuizItem promotePasty(Pasty pa, String str){
        ParkerQuizItem p = new ParkerQuizItem();
        p.data =        pa.data.clone();
        p.height =      pa.height;
        p.width =       pa.width;
        p.whrat =       pa.whrat;
        p.size =        pa.size;
        p.cohortID =    Main.assigningCohortID;
        p.answer =      str;

        p.rot   = pa.rot;
        p.x     = pa.x;
        p.ydisp = pa.ydisp;
        p.z     = pa.z;
        p.size  = pa.size;

        {
            {
                // System.out.println("yo" + glfwGetCurrentContext());
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                // p.sprite.tempIntDataForStorage = null;
            }
        }
        System.out.println("promoting it with value " + str);
        return p;
    }

    public ParkerQuizItem(){}
    
    public int classid() {
        return 15;
    }

    String answer = "";
    String prompt_word = "";
    int cohortID;
    
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(mode);
        b.putInt(cohortID);
        b.putFloat((float)random_temporary_phi);
        b.putFloat((float)random_temporary_theta);
        b.putByteArr(answer.getBytes());
        b.putByteArr(prompt_word.getBytes());
        super._marshal(b, shouldwritedata);
    }

    Pasty _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        mode = b.getInt();
        cohortID = b.getInt();
        random_temporary_phi   = b.getFloat();
        random_temporary_theta = b.getFloat();
        answer = new String(b.getByteArr());
        prompt_word = new String(b.getByteArr());
        super._unmarshal(b, shouldreaddata);
        return this;
    }

    void sectorMarshal(ParkerBuffer b) {
        super.sectorMarshal(b);
    }
    void sectorUnMarshal(ParkerBuffer b) {
        super.sectorUnMarshal(b);
    }
    
    public Pasty clone(){
        ParkerQuizItem p = new ParkerQuizItem();
        p.mode = mode;
        p.data = data.clone();
        p.height = height;
        p.width = width;
        p.whrat = whrat;
        p.size = size;
        p.cohortID = cohortID;
        p.answer = new String(answer);
        p.random_temporary_phi= random_temporary_phi;
        p.random_temporary_theta= random_temporary_theta;
        p.prompt_word = new String(prompt_word);
        {
            {
                // System.out.println("yo" + glfwGetCurrentContext());
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                // p.sprite.tempIntDataForStorage = null;
            }
        }
        return p;
    }
}

class MultipleChoice extends Pasty{
    public MultipleChoice(){}
    public Pasty clone(){
        MultipleChoice p = new MultipleChoice();
        p.data = data.clone();
        p.height = height;
        p.width = width;
        p.whrat = whrat;
        p.size = size;
        {
            {
                // System.out.println("yo" + glfwGetCurrentContext());
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                // p.sprite.tempIntDataForStorage = null;
            }
        }
        return p;
    }

    public int classid(){
        // return 0;
        return 29;
    }
}
class Conveyor extends GameObject{
    public static double rad = 30;
    public Conveyor(){ }
    public Conveyor clone(){
        return new Conveyor();
    }
    
    public int classid(){
        // return 0;
        return 30;
    }
    void sectorMarshal(ParkerBuffer b) {}
    void sectorUnMarshal(ParkerBuffer b) {}
    public void clean() {}
    public void load() {}
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {}
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {return this;
    }
}
class Crux extends GameObject{
    Crux visitor;
    int upx,downx,upz,downz;
    public Crux(){ }
    public Crux clone(){
        return new Crux();
    }
    public int classid(){
        // return 0;
        return 33;
    }
    void sectorMarshal(ParkerBuffer b) {}
    void sectorUnMarshal(ParkerBuffer b) {}
    public void clean() {}
    public void load() {}
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
        b.putInt(upx);
        b.putInt(downx);
        b.putInt(upz);
        b.putInt(downz);
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        upx = b.getInt();
        downx = b.getInt();
        upz = b.getInt();
        downz = b.getInt();
        return this;
    }
}
class TtsMachine extends GameObject{
    public static double rad = 30;
    public TtsMachine(){ }
    public TtsMachine clone(){
        return new TtsMachine();
    }
    
    public int classid(){
        // return 0;
        return 31;
    }
    void sectorMarshal(ParkerBuffer b) {}
    void sectorUnMarshal(ParkerBuffer b) {}
    public void clean() {}
    public void load() {}
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {}
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {return this;
    }
}

class Pasty extends GameObject{ // named after leanbeefpaddy
    public int cachedSignature = -1;
    static Random r = null;
    public long getSignature(){
        if(cachedSignature == -1 && data != null){ // need siggy
            r = new Random(12); // seed is always the same
            for(int i = 0; i < 100; i++){
                cachedSignature = cachedSignature^data[r.nextInt(data.length)];
                System.out.println("cached is " + cachedSignature);
            }
            if(cachedSignature == -1) cachedSignature = 0; // meh
            System.out.println("just did the hashing, neat " + cachedSignature);
            return cachedSignature;
        }else{
            System.out.println("quickly " + cachedSignature);
            return cachedSignature;
        }
    }
    public boolean isMatch(Pasty p){
        if(p != null){
            if(p.width == width && p.height == height){
                if(getSignature() == -1) return false;
                return p.getSignature() == getSignature();
            }
        }
        return false;
    }
    public boolean hidden;
    public boolean touched = false;
    public long lastLoaded = -1;
    public void openIntoVirtualFolder(){
        Main.p(" FileNodule opening file");
        Main.deleteFolderContents(new File("VirtualFolder"));
        // if virtual folder does not exist, create
        var f = (new File("VirtualFolder"));
        if(!f.exists()){
            f.mkdirs();
        }
        try{
            {
                {
                    try{
                        java.awt.Desktop.getDesktop().browse(f.toURI());
                    }catch(Exception e){
                        System.out.println("ERROR OPENING FILE");
                        e.printStackTrace();
                    }
                }
                var p = this;
                BufferedImage bi = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                for(int i = 0; i < p.width; i++){
                    for(int j = 0; j < p.height; j++){
                        bi.setRGB(i, j, p.data[i+j*p.width]);
                    }
                }
                try{
                    ImageIO.write(bi,"png", new File("./VirtualFolder/anonymous.png"));
    
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            System.out.println("FileNodule error openinto");
            e.printStackTrace();
        }
    }
    public static int hashForTwoFloats(double x, double y) {
        int result = 17;  // A prime number
        result = 31 * result + Double.hashCode(x);  // Another prime number
        result = 31 * result + Double.hashCode(y);
        return  Math.abs(result);
    }
    public void openIntoVirtualFolderSpecial(){
        int hash = hashForTwoFloats(x,z);
        String name = "dynamic_img_" + hash + ".png";
        
        var f = (new File("./VirtualFolder/"+name));
        if(f.exists()){
            System.out.println("already found for x, skipping " + name);
            return;
        }
        try{
            {
                var p = this;
                BufferedImage bi = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                for(int i = 0; i < p.width; i++){
                    for(int j = 0; j < p.height; j++){
                        bi.setRGB(i, j, p.data[i+j*p.width]);
                    }
                }
                try{
                    ImageIO.write(bi,"png", new File("./VirtualFolder/" + name));
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }catch(Exception e){
            System.out.println("f error openinto");
            e.printStackTrace();
        }
    }

    // maybe i should browse /fit/ more
    // for better productivity
    public Pasty(){}
    
    int height;
    int width;
    int[] data;
    Spriter sprite;
    double whrat;
    String fragShaderArtifact;
    int sl(){
        return 1;
    }
    int mso(){
        return 1;
    }
    public int classid(){
        return 0;
    }
    public Pasty clone(){
        Pasty p = new Pasty();
        p.data = data.clone();
        p.height = height;
        p.width = width;
        p.whrat = whrat;
        p.size = size;
        {
            {
                // System.out.println("yo" + glfwGetCurrentContext());
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                // p.sprite.tempIntDataForStorage = null;
            }
        }
        return p;
    }
    Pasty _unmarshal(ParkerBuffer b, boolean shouldreaddata){
        {
            width =b.getInt();
            height = b.getInt();
            // System.out.println("supposed width " + width + " height " + height);
            whrat= 1.0*width/height;
            if(shouldreaddata){
                {
                    {
                        data = imgRead(b);
                        sprite = new Spriter(data, width, height, sl(), mso(), null, fragShaderArtifact);
                    }
                }
            }
        }
        return this;
    }
    void _marshal(ParkerBuffer b,  boolean shouldstoredata){
        {
            b.putInt(width);
            b.putInt(height);
            if(shouldstoredata){
                {
                    {
                        imgWrite(b);
                    }
                }
            }
        }
    }
    private int[] imgRead(ParkerBuffer b){
        long nt = System.nanoTime();
        nt = System.nanoTime();
        data = b.getIntArr();
        {
            // System.out.println("step1 " + (System.nanoTime()-nt)/1_000_000);
            nt = System.nanoTime();
        }
        return data;
    }
    private void imgWrite(ParkerBuffer b){
        long nt = System.nanoTime();
        nt = System.nanoTime();
        // System.out.println("writing");
        b.putIntArr(data);
        {
            // System.out.println("step1 " + (System.nanoTime()-nt)/1_000_000);
            nt = System.nanoTime();
        }
    }
    void sectorUnMarshal(ParkerBuffer b){
        data = imgRead(b);
    }
    void sectorMarshal(ParkerBuffer b){
        imgWrite(b);
    }
    public void load(){
        lastLoaded = System.currentTimeMillis();
        if(data.length == 0){
            System.out.println("holy fuck data is 0 " + this.x + " " + this.z);
            
            System.out.println("holy fuck data is 0 " + this.getClass());
        }
        sprite = new Spriter(data, width, height, sl(), mso(),null, fragShaderArtifact);
        if(this.getClass() == Pasty.class) // avoid for the others
            sprite.repaunch = true;
    }
    public void clean(){
        // System.out.println("Cleaning pasty for class " + this.getClass());
        data = null;
        if(sprite != null){
            sprite.tempIntDataForStorage = null;
            //added with the remote loading feature thingy
            if(!Main.ghostSpriters.contains(sprite)){
                sprite.cleanUp();
            }else{
                // sprite.repaunch = true;
                // System.out.println("GOOD! avoided!");
                // System.exit(0);
            }
        } 
        sprite = null;
    }
}

public class Main extends ClassLoader{
    public static double doomguymode = 0;
    public static int _osx = 0;
    public static int _osy = 0;
    public static PortalNodule stage1ukeyportal = null;
    public static Pasty stage1pasty = null;

    public static void copyRecursive(File source, File target) throws IOException {
        if (source.isDirectory()) {
            // If the directory does not exist in the target location, create it
            if (!target.exists()) {
                target.mkdir();
            }

            // List all the directory contents
            String[] files = source.list();

            for (String file : files) {
                File srcFile = new File(source, file);
                File tgtFile = new File(target, file);

                // Recursive function call
                copyRecursive(srcFile, tgtFile);
            }
        } else {
            // Copy the file
            Files.copy(source.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
    public static void rasterInto(int width, int height, int[] data,
    int rasteredWidth, int rasteredHeight, int[] rasteredData, int xOffset, int yOffset) {
    for (int y = 0; y < height; y++) {
    for (int x = 0; x < width; x++) {
    if (x + xOffset >= 0 && x + xOffset < rasteredWidth && y + yOffset >= 0 && y + yOffset < rasteredHeight) {
    int pixel = data[y * width + x];
    rasteredData[(y + yOffset) * rasteredWidth + (x + xOffset)] = pixel;
    }
    }
    }
    }
public static class ParkerImage{
    int[] data;
    int width;
    int height;
    public ParkerImage(int[] data, int width, int height){
        this.data = data;
        this.width = width;
        this.height = height;
    }
}
public static class Rectangle {
    public int x;
    public int y;
    public int width;
    public int height;
    public double secretwidth;
    public double secretheight;
    public double secretaspect;

    public double secretx;
    public double secrety;

    public boolean blockedleft;
    public boolean blockedright;
    public boolean blockedtop;
    public boolean blockedbottom;

    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.secretx =x;
        this.secrety =y;
        this.width = width;
        this.height = height;
    }
    public Rectangle(int width, int height) {
        this.width = width;
        this.height = height;
    }
    public boolean intersects(Rectangle other) {
        int r1left = x;
        int r1right = x+width;
        int r2left = other.x;
        int r2right = other.x+other.width;
        int r1top = y;
        int r1bottom = y+height;
        int r2top = other.y;
        int r2bottom = other.y+other.height;
        boolean avoida = r2left > r1right; // the other rect is to the right
        boolean avoidb = r2right < r1left; // the other rect is to the left
        boolean avoidc = r2top > r1bottom; // the other rect is to the south
        boolean avoidd = r2bottom < r1top; // the other rect is to the north
        //it's the silver linings playbook
        // if literally all are false...
        boolean touching = !(avoida || avoidb || avoidc || avoidd);
        if(touching){
            //they are inside of each other,
            //but how are they inside of each other
            p("touching!!!");
            int strength1 = r2left-r1left;
            int strength2 = r1left-r2left;
            int strength3 = r2bottom-r1bottom;
            int strength4 = r1bottom-r2bottom;
            int strength = Math.max(Math.max(strength1, strength2),Math.max(strength3, strength4));
            if(r1left < r2left && strength == strength1){ // the other rect is to the right
                other.blockedleft = true;
                this.blockedright = true;
                // p("COLLISION A!");
            }
            if(r2left < r1left && strength == strength2){
                other.blockedright = true;
                this.blockedleft = true;
                // p("COLLISION B!");
            }
            if(r1bottom < r2bottom && strength == strength3){ // the other rect is to the south
                other.blockedtop = true;
                this.blockedbottom = true;
                // p("COLLISION C!");
            }
            if(r2bottom < r1bottom && strength == strength4){
                other.blockedbottom = true;
                this.blockedtop = true;
                // p("COLLISION D!");
            }
        }
        return touching;
    }
    public boolean canGrow(){
        // p("blockedleft " + blockedleft);
        // p("blockedright " + blockedright);
        // p("blockedbottom " + blockedbottom);
        // p("blockedtop " + blockedtop);
        boolean can =  !((blockedleft && blockedright) || (blockedtop && blockedbottom));
        if(!can){
            p("it can't grow anymore because: ");
            p("blockedleft " + blockedleft);
            p("blockedright " + blockedright);
            p("blockedbottom " + blockedbottom);
            p("blockedtop " + blockedtop);
        }
        return can;
    }
    public static boolean insideof(Rectangle outer, Rectangle inner) {
        boolean i = outer.x <= inner.x && outer.x + outer.width >= inner.x + inner.width
        && outer.y <= inner.y && outer.y + outer.height >= inner.y + inner.height;
        if(!i){
            // which side has broken out
            // p("touching!!!");
            if(inner.x < outer.x){ // the other rect is to the right
                inner.blockedleft = true;
            }
            if(outer.x + outer.width < inner.x + inner.width){
                inner.blockedright = true;
            }
            if(inner.y < outer.y){ // the other rect is to the south
                inner.blockedtop = true;
            }
            if(outer.y + outer.height < inner.y + inner.height){
                inner.blockedbottom = true;
            }
        }
        return i;
    }
}
// public static List<Rectangle> arrangeAspectRatios(List<Double> aspectRatios, int width, int height) {
//     //find some equadistant points in a circle,
//     //grow by 1 ratio every frame, stop growing when you overlap one of the other ones or go out of bounds
//     // pick X random points in the region
//     // grow by 1 ratio every frame, if 
// }

public static List<Rectangle> arrangeAspectRatios(List<Double> aspectRatios, int width, int height) {
    // list to store rectangles
    List<Rectangle> rectangles = new ArrayList<>();
    List<Rectangle> living = new ArrayList<>();
    Rectangle outer = new Rectangle(0,0,width,height);

    // iterate over aspect ratios
    for (double aspectRatio : aspectRatios) {
        int x = (int)(Math.random()*width);
        int y = (int)(Math.random()*height);
        var r = new Rectangle(x,y,0,0);
        r.secretaspect = aspectRatio;
        rectangles.add(r);
        living.add(r);
    }
    while(living.size() > 0){
        // p("weehoo");
        redo:
        for(var rect: living){
            // p("here");
            //grow
            if(!rect.canGrow()){
                p("I cannot grow anymore, i cannot");
                living.remove(rect);
                break redo;
            }
            if(rect.blockedbottom){
                rect.secrety -= 1;
            }else 
            if(!rect.blockedbottom && !rect.blockedtop){
                rect.secrety -= 0.5;
            }
            if(rect.blockedright){
                rect.secretx -=rect.secretaspect;
            }else
            if(!rect.blockedright && !rect.blockedleft){
                rect.secretx -=rect.secretaspect/2;
            }
            rect.secretwidth+=rect.secretaspect;
            rect.secretheight+=1;
            //check boundaries
            //given 
            rect.width = (int)(rect.secretwidth);
            rect.height = (int)(rect.secretheight);
            rect.x = (int)(rect.secretx);
            rect.y = (int)(rect.secrety);
            // do i intersect?
            for(var other: rectangles){
                if(other != rect){
                    if(rect.intersects(other)){
                        // living.remove(rect);
                        // living.remove(other);
                        // p("REMOVING rect");
                        // p("REMOVING other");
                        // break redo;
                    }
                }
            }
            Rectangle.insideof(outer,rect);
            // if(!){
            //     living.remove(rect);
            //     p("it's outside of now!");
            //     break redo;
            // }
        }
    }
    // return list of rectangles
    return rectangles;
}
    
      
static void dumpClipboard(BufferedImage bi2){
    try{
    TransferableImage trans = new TransferableImage(bi2);
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    clipboard.setContents(trans, null);
    }
    catch(Exception e){e.printStackTrace();}
}
private static void printStream(InputStream inputStream, String streamType) throws IOException {
    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
    String line;
    while ((line = reader.readLine()) != null) {
        // System.out.println(streamType + "> " + line);
        Main.statements.addFirst(streamType + "> " + line);
    }
}
public static int[] refit(int[] data, int width, int height, int newHeight){
    int newWidth = width*newHeight/height;
    int[] newdat = new int[newHeight*newWidth];
    double rat = (newHeight*1.0/height);
    for(int i = 0; i < newWidth; i++){
        for(int j = 0; j < newHeight; j++){
            newdat[newWidth*j + i] = data[(int)(i/rat) + (int)(j/rat) * width];
        }
    }
    return newdat;
}
private static List<File> getFilesInFolder(String folderPath) {
    List<File> fileList = new ArrayList<>();
    Path directory = Paths.get(folderPath);

    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
        for (Path path : directoryStream) {
            if (Files.isRegularFile(path)) {
                fileList.add(path.toFile());
            }
        }
    } catch (IOException e) {
        System.err.println("Error reading folder: " + e.getMessage());
    }
    return fileList;
}
private static List<File> getFilesInFolder2(String folderPath) {
    List<File> fileList = new ArrayList<>();
    Path directory = Paths.get(folderPath);

    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
        for (Path path : directoryStream) {
            // if (Files.isRegularFile(path)) {
                fileList.add(path.toFile());
            // }
        }
    } catch (IOException e) {
        System.err.println("Error reading folder: " + e.getMessage());
    }
    return fileList;
}

public static void link_into_map(String linktext){
    
    var go = new LinkNodule();
    go.state.insert(linktext);
    go.state.enter(false);
    go.ydisp = y+Math.random();
    go.x = x+12*Math.sin(neck)+Math.random();
    go.z = z+12*Math.cos(neck)+Math.random();
    go.rot = neck+Math.PI/2+Math.random()/4;
    PURGATORY.add(go);
    
}
public static void link_into_inventory(String linktext){

    var go = new LinkNodule();
    go.state.insert(linktext);
    go.state.enter(false);
    INVENTORY_PURGATORY.add(go);

}
public static void bi_into_inventory(BufferedImage bi){
    System.out.println(bi.getWidth());
    System.out.println(bi.getHeight());
    // p.sprite = new Spriter(bi, p.sl(), p.mso());
    CurvedSurface.width = bi.getWidth();
    CurvedSurface.height = bi.getHeight();
    CurvedSurface.data = ImageExpirements.biToPix(bi);
    ByteBuffer bb  = CurvedSurface.main(null);
    System.out.println("finished with curved");
    bb.flip();
    ThreeDumb go = Main.convert_bb_to_ThreeDumb(bb,Main.x, Main.dispy+7.5, Main.y);
    // go.load(); // maybe if I don't load that's good???
    go.displayMode = 2;
    // go.sprite.clearRepaunch();
    // go.sprite.repaunch = false;
    INVENTORY_PURGATORY.add(go);
}

public static MultipleChoice presumed_assumed_edited_multiple_choice;
public static Queue<Long> globalQueue = new ConcurrentLinkedQueue<Long>();
public static Queue<LinkedList<String>> MULTIPLECHOICE_PURGATORY = new ConcurrentLinkedQueue<LinkedList<String>>();
public static Queue<LinkedList<String>> MULTIPLECHOICE_RESULT_PURGATORY = new ConcurrentLinkedQueue<LinkedList<String>>();
public static Queue<GameObject> INVENTORY_PURGATORY = new ConcurrentLinkedQueue<GameObject>();
public static Queue<GameObject> PURGATORY = new ConcurrentLinkedQueue<GameObject>();
public static Queue<GameObject> DELETION_PURGATORY = new ConcurrentLinkedQueue<GameObject>();
public static Queue<GameObject> PUMP_PURGATORY = new ConcurrentLinkedQueue<GameObject>();
public static Queue<Tuple<GameObject,GameObject>> BOBBIES_REPLACE = new ConcurrentLinkedQueue<Tuple<GameObject,GameObject>>();
public static LinkedList<AggregatorLayer> restlessAggregators = new LinkedList<AggregatorLayer>();

// public static Set<Long> NeededLoading = ConcurrentHashMap.newKeySet();
// public static Set<Long> NeededInPlay = ConcurrentHashMap.newKeySet();
// public static Set<Long> UnneededUnloading = ConcurrentHashMap.newKeySet();

public static ConcurrentHashMap<Long,Future>         NeededLoading     = new ConcurrentHashMap<>();
public static Set<Long>                              InPlay      = new HashSet<>();
public static ConcurrentHashMap<Long,Future>         UnneededUnloading = new ConcurrentHashMap<>();
public static ConcurrentHashMap<Future<?>, Runnable> taskMap  = new ConcurrentHashMap<>();

public static HashSet<Long> neededNow = new HashSet<Long>();

// public static LinkedList<GameObject> neededNowGameObjects = new LinkedList<>();
public static Map<String, FileNodule> neededFileNodules = new HashMap<String, FileNodule>();
public static HashSet<GameObject> neededGameObjects = new HashSet<GameObject>();
public static ConcurrentHashMap<GameObject, GameObject> temporarilyNeededTextNodules = new ConcurrentHashMap<GameObject, GameObject>();

public static HashSet<GameObject> temporarilyNeededGameObjects = new HashSet<GameObject>();


public static String buildFolderAsKey(LinkedList<String> folderStack, String name){
    String idk = "./";
    for(var e: folderStack){
        idk+=e + "/";
    }
    idk+=name;
    return idk;
}

// public static HashSet<GameObject> neededGameObjects = new HashSet<GameObject>();
public static HashSet<GameObject> missingGameObjects = null;
public static GameObject originalBumpedFile = null;
public static ObeliskNodule designatedObelisk = null;
public static boolean obelisk_dont_bounce = false;
public static boolean hands_flag_flipped = false;
public static boolean portals_flag_flipped = false;

public static ConcurrentLinkedQueue<Tuple<ByteBuffer,GameObject[]>> parkerBufferBuffers = new ConcurrentLinkedQueue<Tuple<ByteBuffer,GameObject[]>>();

// public static ConcurrentLinkedQueue<ByteBuffer> parkerBufferBuffers = new ConcurrentLinkedQueue<ByteBuffer>();





static {
}

// var toReturn = Collections.synchronizedList(new LinkedList<GameObject>());
// var cleanupAfter = Collections.synchronizedList(new LinkedList<GameObject>());

public static double WATER = -30;
public static int MAX_RES = 9;
public static int SPAWN_TEXTURE = 32;
static Random r = new Random(3242345);
static double[] OCTAVE_LOOKUP3 = new double[]{
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),};
static double[] OCTAVE_LOOKUP4 = new double[]{
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),
    r.nextDouble(),};
    
    public static Swath[] swaths = new Swath[52];

public static Function<int[],Double> heightAt2 = (int[] ints) ->{
    int x = ints[0];
    int y = ints[1];
    int resolution = ints[2];
    double cum = 0;
    double[] special_octave = new double[10];
    for(int i = 0; i < 10; i++){
        special_octave[i] = arbitrary(x, y, resolution, 1000+i, OCTAVE_LOOKUP3)/512;
    }
    
    for(int i = 0; i < 10; i++){
        int k  = (swaths[24+i].getColorFromCompletedMap((int)x,(int)y));
        special_octave[i] *= (k&255)/255.0*2;
    }

    for(int i = resolution; i < MAX_RES; i++){
        int breadth = 1<<i;
        // p("breadth " +breadth);''
        int anch1x = x/breadth;
        int anch1y = y/breadth;
        double percx = (x%breadth)*1.0/breadth;
        double percy = (y%breadth)*1.0/breadth;
        // (anch1x+1)*breadth;
        double a = (heightAt((anch1x+0)*breadth, (anch1y+0)*breadth)*(1-percx)+percx*heightAt((anch1x+1)*breadth, (anch1y+0)*breadth));
        double b = (heightAt((anch1x+0)*breadth, (anch1y+1)*breadth)*(1-percx)+percx*heightAt((anch1x+1)*breadth, (anch1y+1)*breadth));
        double TRVE_HEIGHT = (b*percy+(1-percy)*a);
        cum += TRVE_HEIGHT*special_octave[i]*breadth;
    }
    cum-=30;
    if(cum < WATER) return WATER;
    return cum;
};
public static float[] rgbToHsv(int r, int g, int b) {
    float[] hsv = new float[3];
    int max = Math.max(r, Math.max(g, b));
    int min = Math.min(r, Math.min(g, b));
    float delta = max - min;

    if (max == r) {
        hsv[0] = (g - b) / delta;
    } else if (max == g) {
        hsv[0] = 2 + (b - r) / delta;
    } else {
        hsv[0] = 4 + (r - g) / delta;
    }

    hsv[0] *= 60;
    if (hsv[0] < 0) {
        hsv[0] += 360;
    }

    hsv[1] = max == 0 ? 0 : (delta / max);
    hsv[2] = max;

    return hsv;
}

// Convert HSV to RGB
public static int[] hsvToRgb(float h, float s, float v) {
    h = (h % 360 + 360) % 360; // ensuring 0 <= h < 360
    int hi = (int) (h / 60.0) % 6;
    float f = h / 60.0f - hi;
    float p = v * (1 - s);
    float q = v * (1 - s * f);
    float t = v * (1 - s * (1 - f));

    int[] rgb = new int[3];
    switch(hi) {
        case 0: rgb[0] = (int)v; rgb[1] = (int)t; rgb[2] = (int)p; break;
        case 1: rgb[0] = (int)q; rgb[1] = (int)v; rgb[2] = (int)p; break;
        case 2: rgb[0] = (int)p; rgb[1] = (int)v; rgb[2] = (int)t; break;
        case 3: rgb[0] = (int)p; rgb[1] = (int)q; rgb[2] = (int)v; break;
        case 4: rgb[0] = (int)t; rgb[1] = (int)p; rgb[2] = (int)v; break;
        case 5: rgb[0] = (int)v; rgb[1] = (int)p; rgb[2] = (int)q; break;
    }

    return rgb;
}
public static Function<int[],Double> heightAt3 = (int[] ints) ->{
    int x = ints[0];
    int y = ints[1];
    int resolution = ints[2];
    double cum = 0;
    double[] special_octave = new double[10];
    for(int i = 0; i < 10; i++){
        special_octave[i] = arbitrary(x+10_000, y, resolution, 1000+i, OCTAVE_LOOKUP4)/512;
    }

    for(int i = resolution; i < MAX_RES; i++){
        int breadth = 1<<i;
        // p("breadth " +breadth);''
        int anch1x = x/breadth;
        int anch1y = y/breadth;
        double percx = (x%breadth)*1.0/breadth;
        double percy = (y%breadth)*1.0/breadth;
        // (anch1x+1)*breadth;
        double a = (heightAt((anch1x+0)*breadth+10_000, (anch1y+0)*breadth)*(1-percx)+percx*heightAt((anch1x+1)*breadth, (anch1y+0)*breadth));
        double b = (heightAt((anch1x+0)*breadth+10_000, (anch1y+1)*breadth)*(1-percx)+percx*heightAt((anch1x+1)*breadth, (anch1y+1)*breadth));
        double TRVE_HEIGHT = (b*percy+(1-percy)*a);
        cum+=TRVE_HEIGHT*special_octave[i]*breadth;
    }
    cum-=-20;
    if(cum < WATER) return WATER;
    return cum;
};

    
    public static class Sector extends Tuple5<
    BiFunction<int[],double[], Double>, // heightfunc
    BiFunction<int[],double[], int[]>, // colorfunc
    BiFunction<int[],double[], Integer>, // texturefunc
    double[], // state
    int[] // ordering  1,2,3  2,3,1
    >{
    public Sector(
    BiFunction<int[],double[], Double> a, // heightfunc
    BiFunction<int[],double[], int[]> b, // colorfunc
    BiFunction<int[],double[], Integer> c, // texturefunc
    double[] d, // state
    int[] e// ordering  1,2,3  2,3,1
    ){
    super(a,b,c,d,e);
    }
    }
    public static int map(int input) {
        int result = input % 4096;
        return (result < 0) ? result + 4096 : result;
    }
    
    // public static BiFunction<int[],double[],Double> bowlHeightFunction = (int[] a, double[] b) -> {
    //     int xx = a.length > 3 ? a[3] : a[0];
    //     int yy = a.length > 3 ? a[4] : a[1];
    //     var x = 1.0*(xx)+b[3]*128-64*b[5];
    //     var y = 1.0*(yy)+b[4]*128-64*b[5];
    //     double fixation = b[5];
    //     var d = Math.sqrt(x*x+y*y);
    //     var height = -28.0;
    //     height =
    //         b[5]*64/2
    //          -((int)(d/2/30))*30
    //          - Math.min(0,((d/2)%30-15  ))
    //          -29.0;
        
    //     if((x  < 10 && x > -10) || (y < 10 && y > -10)){
    //         double val = Math.max(Math.abs(x), Math.abs(y));
    //         height = (64*b[5]/2 - val/2) - 15 ;
    //         b[6] = 0;
    //     }else if((d/2)%30-15 < 0){
    //         b[6] = 200;
    //     }else{
    //         b[6] = 100;
    //     }
    //     height -= 20;
    //     if(height < -29) height = -29;
    //     // b[6] = 50;
    //     return (height+29)*b[8]-29;
    // };
        
    public static BiFunction<int[],double[],Double> defaultHeightFunction = 
        (a, b) -> {
            int x = (a[0]-1_000_000);
            int y = (a[1]-1_000_000);
            double val = heightAt2.apply(a);
            double dx = x - 2000;
            double dz = y - 2000;
            double dist = Math.sqrt(dx*dx + dz*dz);
            boolean special = false;
            if(dist< 200 || Math.abs(dx) < 40 || Math.abs(dz) < 40 ){
                val = -28;
                special = true;
            }
            int ogx = x;
            int ogy = y;
            x = map(x)-2048;
            y = map(y)-2048;

            // cake part goes here
            // add it to val
            int spread = 400;
            int out = 5000;

            if(ogx > out && ogx < out+spread && ogy > out && ogy < out + spread){
                val = 200*2*2;
            }
            spread = 300;
            out = 5500;
            if(ogx > out && ogx < out+spread && ogy > out && ogy < out + spread){
                val = 200*2*1.5;
            }
            spread = 3000;
            out = 25000;
            if(ogx > out && ogx < out+spread && ogy > out && ogy < out + spread){
                val = 2000*2*1.5;
            }
            if(val == WATER){
                val = Math.min(-29,heightAt3.apply(a));
            }
            b[0] = val;
            
            // {
            //     double fixation = 2048;
            //     var d = Math.sqrt(x*x+y*y);
            //     var height = -28.0;
            //     int v1 = 300;
            //     d/=2; // slope it out a bit
            //     height = fixation  - Math.max(d%v1-v1*3/4, 0) - (int)((d)/v1)*v1/4 - 29.0;
            //     val+=height;
            //     val-=1850;
            // }
            if(val <= -30) return -30.0;
            return val;
            // return -30.0;
        };
        
    public static BiFunction<int[],double[],Double> parkerHeightFunction = 
        (a, b) -> {
            int islandx = (a[0]-1_000_000);
            int islandy = (a[1]-1_000_000);
            int localx = a[3];
            int localy = a[4];
            int ddx = localx +64;
            int ddz = localy +64;
            double localdist = Math.sqrt(ddx*ddx + ddz*ddz);
            
            double val = heightAt2.apply(a);
            double dx = islandx - 2000;
            double dz = islandy - 2000;
            double dist = Math.sqrt(dx*dx + dz*dz);
            boolean special = false;
            if(dist< 200){
                val = -28;
                special = true;
            }
            if(localdist < 66){
                val = -28;
                special = true;
            }
            islandx = map(islandx)-2048;
            islandy = map(islandy)-2048;

            // cake part goes here
            // add it to val
            
            if(val == WATER){
                val = Math.min(-29,heightAt3.apply(a));
            }
            b[0] = val;
            
            {
                double fixation = 2048;
                var d = Math.sqrt(islandx*islandx+islandy*islandy);
                var height = -28.0;
                int v1 = 300;
                d/=2; // slope it out a bit
                height = fixation  - Math.max(d%v1-v1*3/4, 0) - (int)((d)/v1)*v1/4 - 29.0;
                val+=height;
                val-=1850;
            }
            if(val <= -30) return -30.0;
            return val ;
            // return -30.0;
        };
        
    public static BiFunction<int[],double[],Double> freeRangeHeight1 = 
        (a, b) -> {
            int x = (a[0]-1_000_000);
            int y = (a[1]-1_000_000);
            double val = heightAt2.apply(a);
            double dx = x - 2000;
            double dz = y - 2000;
            double dist = Math.sqrt(dx*dx + dz*dz);
            boolean special = false;
            if(dist< 200){
                val = -28;
                special = true;
            }
            x = map(x)-2048;
            y = map(y)-2048;

            // cake part goes here
            // add it to val
            
            if(val == WATER){
                val = Math.min(-29,heightAt3.apply(a));
            }
            b[0] = val;
            
            if(val <= -30) return -30.0;
            return val;
            // return -30.0;
        };
        
    public static int[] greenBrownMapping(int r, int g, int b){
        //minimum red
        // minimum green
        // minimum blue
        //maximum blue
        //maximum green
        //maximum red
        // uh,
        //assume 0-256
        r = Math.max(20,r);
        return new int[]{
            r,
            Math.min(r/2, g),
            Math.min(r/8,g)
        };
    }
    public static int[] yellowGraySandMapping(int r, int g, int b){
        r = Math.max(r, g+20);
        g = Math.max(r+20, g);
        b = Math.min(Math.min(b,r),g);
        return new int[]{
            r/2,
            g/2,
            b/2
        };
    }
    static double[][] huh = new double[][]{
        { 65,  235,0,245},
        { 50,  122,122,122},
        { 30,  0,122,20},
        { 10,  0,255,0},
        { 0,  255,255,0},
        { -20,  10,20,240},
    };
    public static RGB terrainColor(double elevation){
        RGB rgb = null;
        for(int i = 0; i < huh.length; i++){
            double[] row = huh[i];
            if(elevation > row[0] || i == huh.length-1){
                rgb = new RGB((int)row[1],(int)row[2],(int)row[3]);
                break;
            }
        }
        return rgb;
    }
    
    public static RGB terrainColor2(double elevation){
        RGB rgb = null;
        for(int i = 0; i < huh.length; i++){
            double[] row = huh[i];
            if(elevation > row[0] || i == huh.length-1){
                if(i == huh.length-1)
                    rgb = new RGB((int)row[1],(int)row[2],(int)row[3]);
                else{
                    double a = huh[i][0];
                    double b = huh[i+1][0];
                    int perc = (int)(100*(elevation-b)/(a-b));
                    int[] c = lerp(perc,  (int)huh[i+0][1],(int)huh[i+0][2],(int)huh[i+0][3],  (int)huh[i+1][1],(int)huh[i+1][2],(int)huh[i+1][3]); //yellow to red`
                    
                    return new RGB(c[0], c[1], c[2]);
                }
                break;
            }
        }
        return rgb;
    }


    public static int[] soOptions = new int[]{ 9, 10, 13, 28, 33, 34}; 

    public static BiFunction<int[],double[],int[]> defaultColorFunction = 
    (int[] a, double[] b) -> {
        boolean special = false;
        {
            int x = (a[0]-1_000_000);
            int y = (a[1]-1_000_000);
            double dx = x - 2000;
            double dz = y - 2000;
            double dist = Math.sqrt(dx*dx + dz*dz);
            if(dist< 200 || Math.abs(dx) < 40 || Math.abs(dz) < 40 ){
                special = true;
            }
        }
        double ithinkheight = b[0];
        int ii = a[0];
        int jj = a[1];
        int c = swaths[9].getColorFromCompletedMap  ((int)ii,(int)jj);
        int c2 = swaths[10].getColorFromCompletedMap((int)ii,(int)jj);
        int c3 = swaths[11].getColorFromCompletedMap((int)ii,(int)jj);
        
        int pick = (c3&255);
        double idk = Math.ceil(256.0/soOptions.length);


        // int so = ((c3&255) > 127) ? 9 : 10;
        int so = soOptions[(int)(pick/idk)];
        int c4 = swaths[22].getColorFromCompletedMap((int)ii,(int)jj);
        if(special ){
            // System.out.println("YUP");
            // System.exit(0);
            so = SPAWN_TEXTURE;
        }
        if(ithinkheight <= -30)
            return new int[]{darken(r(c)), darken(g(c)), darken(b(c)), 12,darken(r(c2)), darken(g(c2)), darken(b(c2)),darken(r(c3)), darken(g(c3)), darken(b(c3))};
        if(ithinkheight <= -29){
            return new int[]{darken(r(c)), darken(g(c)), darken(b(c)), so,darken(r(c2)), darken(g(c2)), darken(b(c2)),darken(r(c3)), darken(g(c3)), darken(b(c3))};
        }

        return new int[]{r(c), g(c), b(c), so,r(c2),g(c2),b(c2),r(c3),g(c3),b(c3)};

    };;
    
    public static BiFunction<int[],double[],int[]> freeRangeColor1 = 
    (int[] a, double[] b) -> {
        boolean special = false;
        {
            int x = (a[0]-1_000_000);
            int y = (a[1]-1_000_000);
            double dx = x - 2000;
            double dz = y - 2000;
            double dist = Math.sqrt(dx*dx + dz*dz);
            if(dist < 200){
                special = true;
            }
        }
        double ithinkheight = b[0];
        int ii = a[0];
        int jj = a[1];
        int c = swaths[9].getColorFromCompletedMap  ((int)ii,(int)jj);
        int c2 = swaths[10].getColorFromCompletedMap((int)ii,(int)jj);
        int c3 = swaths[11].getColorFromCompletedMap((int)ii,(int)jj);
        
        int pick = (c3&255);
        double idk = Math.ceil(256.0/soOptions.length);


        // int so = ((c3&255) > 127) ? 9 : 10;
        int so = soOptions[(int)(pick/idk)];
        int c4 = swaths[22].getColorFromCompletedMap((int)ii,(int)jj);
        if(special ){
            // System.out.println("YUP");
            // System.exit(0);
            so = SPAWN_TEXTURE;
        }
        var tc = terrainColor(ithinkheight+38);
        // System.out.println(ithinkheight + " " + tc.r + " " + tc.g + " " + tc.b);
        if(ithinkheight <= -29)
            return new int[]{darken(tc.r), darken(tc.g), darken(tc.b), 29,darken(r(c2)), darken(g(c2)), darken(b(c2)),darken(r(c3)), darken(g(c3)), darken(b(c3))};
       

        return new int[]{tc.r, tc.g, tc.b, 29,r(c2),g(c2),b(c2),r(c3),g(c3),b(c3)};

    };;
    public static BiFunction<int[],double[],Integer> defaultTextureFunction = (a,b)->{
        return 1;
    };
    
    public static BiFunction<int[],double[],Double> bowlHeightFunction = (int[] a, double[] b) -> {
        double idek = 0;
        {
            double val = -28;
            
            int x = (a[0]-1_000_000);
            int y = (a[1]-1_000_000);
            x = map(x)-2048;
            y = map(y)-2048;
            {
                double fixation = 2048;
                var d = Math.sqrt(x*x+y*y);
                var height = -28.0;
                int v1 = 300;
                d/=2; // slope it out a bit
                height = fixation  - Math.max(d%v1-v1*3/4, 0) - (int)((d)/v1)*v1/4 - 29.0;
                val+=height;
                val-=1850;
                if(val <= -30) val = -30;
                idek = val;
            }
        }
        int xx = a.length > 3 ? a[3] : a[0];
        int yy = a.length > 3 ? a[4] : a[1];
        var x = 1.0*(xx)+b[3]*128-64*b[5];
        var y = 1.0*(yy)+b[4]*128-64*b[5];
        double fixation = b[5];
        var d = Math.sqrt(x*x+y*y);
        var height = -28.0;
        height =
            b[5]*64/2
             -((int)(d/2/30))*30
             - Math.min(0,((d/2)%30-15  ))
             -29.0 + idek;
        
        if((x  < 10 && x > -10) || (y < 10 && y > -10)){
            double val = Math.max(Math.abs(x), Math.abs(y));
            height = (64*b[5]/2 - val/2) - 15 ;
            b[6] = 0;
        }else if((d/2)%30-15 < 0){
            b[6] = 200;
        }else{
            b[6] = 100;
        }
        height -= 20;
        if(height < -29) height = -29;
        // b[6] = 50;
        return (height+29)*b[8]-29;
    };
    public static BiFunction<int[],double[], int[]> bowlColorFunction = (a, b) -> {

            if(b[6] == 0)
                return new int[]{ (int)(b[6] ),(int)(255*b[1]),(int)(255*b[2]), 28, 0,0,0};
            if(b[6] == 200)
                return new int[]{(int)(256*b[9]),(int)(256*b[10]),(int)(256*b[11]), 10, (int)(256*b[0]),(int)(256*b[1]),(int)(256*b[2])};
            return new int[]{(int)(256*b[0]),(int)(256*b[1]),(int)(256*b[2]),29,0,0,0};
    }; 
    public static BiFunction<int[],double[],Double> hfunc;
    public static BiFunction<int[],double[],Double> moundHeightFunction = (int[] a, double[] b) -> {
        double span = b[5]*64;
        int xx = a.length > 3 ? a[3] : a[0];
        int yy = a.length > 3 ? a[4] : a[1];
        var x = 1.0*(xx)+b[3]*128-64*b[5];
        var y = 1.0*(yy)+b[4]*128-64*b[5];
        double height = Math.min(Math.sin((x/span+0.5)*Math.PI),Math.sin((y/span+0.5)*Math.PI));
        // height = (x + y)/100;
        // double height = Math.sin((x/span+0.5)*Math.PI)+Math.sin((y/span+0.5)*Math.PI);
        b[6] = height;
        double cap = Math.pow(span, 0.8)/5;
        return height*cap+cap-29;
        // return -29;
    };
    
    public static BiFunction<int[],double[], int[]> moundColorFunction = (a, b) -> {
        var h = Math.sqrt((b[6]+1)/2);
        int r= (int)(255*b[0]*h);
        int g= (int)(255*b[1]*h);
        int bl= (int)(255*b[2]*h);
        return new int[]{r,g,bl,9,r-30,g-30,bl-30};
    };
    
    public static BiFunction<int[],double[],Double> hfn1 = (int[] a, double[] bb) -> {
        double FIXATION = bb[5];
        int xx = a.length > 3 ? a[3] : a[0];
        int yy = a.length > 3 ? a[4] : a[1];
        var x = 1.0*(xx)+bb[3]*128-64*bb[5];
        var y = 1.0*(yy)+bb[4]*128-64*bb[5];
        var d = Math.sqrt(x*x+y*y);
        var height = -28.0;
        height = -Math.sqrt(d*(100))+Math.sqrt(FIXATION*128*10);
        if(height < -29) height = -29;
        // bb[5] = height;
        return height;
    };
    public static BiFunction<int[],double[], int[]> cfn1 = (a, bbb) -> {

        if(bbb[6] == 0)
            // return new int[]{ (int)(bbb[6] ),(int)(255*bbb[1]),(int)(255*bbb[2]), 28, 0,0,0};
            return new int[]{255,255,255,32,0,0,0,2};
        int rr = (int)(bbb[11] );
        int gg = (int)(255*bbb[1]);
        int bb = (int)(255*bbb[2]);
        
        int dr = (int)(bbb[9]);
        int dg = (int)(bbb[10]);
        int db = (int)(bbb[11]);
        if(bbb[6] == 200)
            return new int[]{rr,gg,bb, 10, rr+dr,gg+dg,bb+db};
        return new int[]{rr,gg,bb, bbb[8] < 0 ? 33: 34, rr-50, gg-20,bb+10};
    };
    // to check validity of a placed
    // tile, simply keep recurrently
    //checking this hashmap,
    //sectorFunctions
    //gonna have to save
    //this boy in unmarshal
    //as well as setup
    //a default one, too
    //saving a hashmap...
    //order not important,
    //just do a while loop,
    //while writing out the
    //keys(longs) and reverse
    //on unmarshal
    //parkerspace_data_t.save, of course
    public static HashMap<Long, Sector> sectorFunctions = new HashMap<>(); // array of functions on a by sector basis. // ok that should be good.

    public static BiFunction[] id2fnlkp = new BiFunction[]{
        defaultHeightFunction,
        defaultColorFunction,
        parkerHeightFunction,
        freeRangeHeight1,
        freeRangeColor1,
        bowlHeightFunction,
        bowlColorFunction,
        
        moundHeightFunction,
        moundColorFunction,

        hfn1,
        cfn1,

    };
    public static BiFunction[] PICKME = new BiFunction[]{
        bowlHeightFunction,
        bowlColorFunction,
        
        moundHeightFunction,
        moundColorFunction,

        hfn1,
        cfn1,
    };
    public static HashMap<BiFunction, Integer> fn2idlkp = new HashMap<>(); 
    static void startup2(){
        //populate
        int i = 0;
        for(var fn: id2fnlkp){
            fn2idlkp.put(fn, i);
            i++;
        }
    }

    static void startup3(){
        var t = new Tuple<Function<int[],Double>,Function<int[],int[]>>(
            (int[] ints) -> {
                int x = ints[0];
                int y = ints[1];
                return (Math.sin(x/40.0  ) + Math.sin(y/40.0))*30    - 30;
            },
            (arr) -> {
                var red =    100;
                var green =  50;
                var blue =   20;
                return new int[]{red, green, blue};
            }
        );
        var g = new Tuple<Function<int[],Double>,Function<int[],int[]>>(
            (int[] ints) -> {
                return -50.0;
            },
            (arr) -> {
                var red =    50;
                var green =  100;
                var blue =   20;
                return new int[]{red, green, blue};
            }
        );
        var h = new Tuple<Function<int[],Double>,Function<int[],int[]>>(
            (int[] ints) -> {
                int x = ints[0];
                int y = ints[1];
                double a =  (Math.sin(x/20.0  ) + Math.sin(y/20.0))*70    - 30;
                double b = - 50.0;
                double c = Math.max(a,b);
                return c;
            },
            (arr) -> {
                var red =    200;
                var green =  200;
                var blue =   255;
                return new int[]{red, green, blue};
            }
        );
    }
    public static double justHeight( int ii, int jj, int resolution){
        int temp = 128;
        int osx = (int)(Math.floor(ii/temp)*temp);
        int osy = (int)(Math.floor(jj/temp)*temp);
        var putx = ii%128==0&&ii!=osx ? ii : osx;
        var puty = jj%128==0&&jj!=osy ? jj : osy;
        // var putx = ii-(ii%128);
        // var puty = jj-(jj%128);
        var get = sectorFunctions.get(xy(putx,puty));
        var hfunc = get == null ? defaultHeightFunction : get.x;
        var params = get == null ? new double[10] : get.a;
        // p("here is where I will do fixation_fix");
        boolean isuknow = get == defaultHeightFunction; // Is MAIN_TERRAIN
        int[] inj = new int[]{
            ii,
            jj,
            resolution,ii%128-128, jj%128-128
        };
        Main.hfunc = hfunc;
        double hres = hfunc.apply(inj, params);
        double height = hres;
        return height;
    }



    public static class Bubba {
        public static void hello() {
            System.out.println("Hel Smeag 34dddd5d5!");
            x += Math.random()*5;
            z += Math.random()*5;
            scapeSpriter.clearRepaunch();
            demandreset = true;
        }
    }
    static double fps_accum = 0;
    static boolean F12_OVERRIDE = false;
    static boolean FINE_MODE = true;
    static boolean F8_MODE = false;

    static BufferedImage makeBI(int w, int h){
    return new BufferedImage(
    w,h, BufferedImage.TYPE_4BYTE_ABGR);
    }
    
    public static ThreeDumb convert_bb_to_ThreeDumb(ByteBuffer bb,double x,double y, double z) {
        ThreeDumb go = null;
        {
            // File theFile = new File("ThreeDumbPollFolder\\3d");
            // if(theFile.exists()){
                try{
                    // byte[] bytes = Files.readAllBytes(Paths.get("ThreeDumbPollFolder\\3d"));
                    // ByteBuffer b = ByteBuffer.wrap(bytes);

                    //why am i using a pbuf here?
                    ParkerBuffer pb = new ParkerBuffer(bb);
                    ThreeDumb p = new ThreeDumb();
                    p.pointsCt = pb.getInt();
                    // System.out.println("ptct rec " + p.pointsCt);
                    p.subdivs = pb.getInt();
                    // System.out.println("subdivs " + p.subdivs);

                    int datamode = pb.getInt();
                    BufferedImage bi = null;
                    if(datamode == 1){
                        // System.out.println("inside datamode");
                        int width = pb.getInt();
                        int height = pb.getInt();
                        // p("W"+width+","+height);
                        // System.out.println("WTF AAA" + pb.getInt());
                        pb.getInt();

                        int[] texdat = pb.getIntArr();
                        p.data = texdat;
                        p.width = width;
                        p.height = height;
                        p.whrat = width*1.0/height;
                        // p.sprite = new Spriter(p.texdat, p.width, p.height, p.sl(), p.mso());
                        // p.sprite.repaunch = true;
                    }
                    // System.out.println("WTF BBB" + pb.getInt());
                    pb.getInt();
                    {
                        //Optional SOs
                        int sosOption = pb.getInt();
                        if(sosOption == 1){
                            p.sos = pb.getIntArr();
                            // System.out.println("getting optional sos " + p.sos.length);
                        }else{
                            p.sos = new int[p.pointsCt];
                            // System.out.println("generating sos... " + p.sos.length);

                        }
                    }
                    double values[] = pb.getDoubleArr();
                    // System.out.println("rec vals " + values.length);
                    {
                        // try{
                        p.values = values;
                        
                        go = p;
                        // go.x =  ii+Math.random()*20-10;
                        // go.z =  jj+Math.random()*20-10;
                        go.x = x;
                        go.z = z;
                        //necessary, should be solved by hfunc
                        // go.ydisp = heightAt2((int)go.x, (int)go.z,0)+8;
                        go.ydisp = y;
                        // go.rot = Math.random()*Math.PI*2;
                        // go.rot = Math.PI/2;
                        //good, correct
                        // System.out.println("DEFINITELY am here, for sure");
                        // if(!lock())
                        //     pasties.add(go);
                    }
                    return go;
    
                }catch(Exception e){
                    e.printStackTrace();
                }
                return null;
            //     theFile.delete();
            // }
        }
    }
    public static void deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }
    public static void deleteFolderContents(File folder) {
        File[] files = folder.listFiles();
        if(files!=null) { //some JVMs return null for empty dirs
            for(File f: files) {
                if(f.isDirectory()) {
                    deleteFolder(f);
                } else {
                    f.delete();
                    p("gutting file... in  " + folder);
                }
            }
        }
        // folder.delete();
    }
    public static int[] lerp(int percentage, int r1, int g1, int b1, int r2, int g2, int b2) {
        return new int[]{
            (r2*100+(r1-r2)*percentage)/100,
            (g2*100+(g1-g2)*percentage)/100,
            (b2*100+(b1-b2)*percentage)/100,
        };
    }
    public static RGB gre2red(double perc){
        int[] c = null;
        if(perc > 0.5){
            c = lerp((int)(100*(perc-0.5)*2), 0,255,0,  255,255,0); //green to yellow
        }else{
            c = lerp((int)(100*(perc)*2), 255,255,0,  255,0,0); //yellow to red`
        }
        return new RGB(c[0], c[1], c[2]);
    }
    
    // static double[][] huh = new double[][]{
    //     { 65,  255,255,255},
    //     { 50,  122,122,122},
    //     { 30,  0,122,20},
    //     { 10,  0,255,0},
    //     { 0,  255,255,0},
    //     { -20,  0,0,255},
    // };
    // public static RGB terrainColor(double elevation){
    //     RGB rgb = null;
    //     for(int i = 0; i < huh.length; i++){
    //         double[] row = huh[i];
    //         if(elevation > row[0] || i == huh.length-1){
    //             rgb = new RGB((int)row[1],(int)row[2],(int)row[3]);
    //             break;
    //         }
    //     }
    //     return rgb;
    // }
    
    // public static RGB terrainColor2(double elevation){
    //     RGB rgb = null;
    //     for(int i = 0; i < huh.length; i++){
    //         double[] row = huh[i];
    //         if(elevation > row[0] || i == huh.length-1){
    //             if(i == huh.length-1)
    //                 rgb = new RGB((int)row[1],(int)row[2],(int)row[3]);
    //             else{
    //                 double a = huh[i][0];
    //                 double b = huh[i+1][0];
    //                 int perc = (int)(100*(elevation-b)/(a-b));
    //                 int[] c = lerp(perc,  (int)huh[i+0][1],(int)huh[i+0][2],(int)huh[i+0][3],  (int)huh[i+1][1],(int)huh[i+1][2],(int)huh[i+1][3]); //yellow to red`
                    
    //                 return new RGB(c[0], c[1], c[2]);
    //             }
    //             break;
    //         }
    //     }
    //     return rgb;
    // }
    public static long xy(int x, int y) {
        return (((long)x) << 32) | y;
    }
    public static int r(int rgb){
        return (rgb>>>16)&0xff;
    }
    public static int g(int rgb){
        return (rgb>>>8)&0xff;
    }
    public static int b(int rgb){
        return (rgb>>>0)&0xff;
    }
    public static Object[] o(Object ... oo){
        return oo;
    }
    public static int darken(int value){
        return value>>1;
    }

    public static String print_key(long l) {
        return x(l) + ", " +y(l);
    }
    public static int x(long key){
        return (int)(key>>>32);
    }
    public static int y(long key){
        return (int)(key);
    }
    private static long C1 = 0xcc9e2d5446534345l;
    private static long C2 = 0x1b87359325787423l;
    public static int smear(long hashCode) {
        return new Random((int) (C2 * Long.rotateLeft((long) (hashCode * C1), 15))).nextInt();
    }
    public static int smear(long hashCode, int mixer) {
        return new Random((int) (C2 * Long.rotateLeft((long) (hashCode * C1+mixer), 15))).nextInt();
    }
static double[] OCTAVE_LOOKUP = new double[]{1,1,1,1,1,1,1,1,1,1,1,1};
static double[] OCTAVE_LOOKUP2 = new double[]{Math.random()*512,Math.random()*256,Math.random()*128,Math.random()*64,Math.random()*32,Math.random()*16,Math.random()*8,Math.random()*4,Math.random()*2,Math.random()*1,};
        // if(k.wasPluggedIn()){
        //     while (true) {
        //         Thread.sleep(100);
        //         for (int i = 0; i < 8; i++) {
        //             System.out.print(String.format("%03d-", k.get(i)));
        //         }
        //         System.out.println();
        //         for (int i = 0; i < 8; i++) {
        //             System.out.print(String.format("%03d-", k.get(i+8)));
        //         }
        //         System.out.println();
        //         System.out.println();
        //     }
        // }else{
        //     System.out.println("not plugged in");
        // }
public static double heightAt(int x, int y){
    double height = ((Integer)smear(xy(x, y))).hashCode()*1.0/Integer.MAX_VALUE;
    // System.out.println(height);
    return height;
}
public static double avgheight(double x, double y){
    double avgheight = 0;

    for(int i = 0; i < 4; i++){
        int x1 = ((int)x);
        int y1 = ((int) y);
        double rx = (x)%1;
        double ry = (y)%1;
        if(i == 1 || i == 2){
            x1+=1;
            rx = 1-rx;
        }
        if(i == 2 || i == 3){
            y1+=1;
            ry = 1-ry;
        }
        rx = 1-rx;
        ry = 1-ry;
        double h = justHeight(x1, y1, 3);
        // double h = heightAt2.apply(new int[]{x1, y1, 3});
        // var o = overrides.get(xy(x1,y1));
        // if(o != null) h = o.height;

        avgheight += h*rx*ry;
    }
    return avgheight;
}

public static double idkAt(int x, int y, int mixer){
    double height = ((Integer)smear(xy(x, y), mixer)).hashCode()*1.0/Integer.MAX_VALUE;
    // System.out.println(height);
    return height;
}
public static double arbitrary(int x, int y, int resolution, int mixer){
    return arbitrary(x, y, resolution, mixer, OCTAVE_LOOKUP3);
}
public static double arbitrary(int x, int y, int resolution, int mixer, double[] octave_lookup){
    double cum = 0;
    for(int i = resolution; i < MAX_RES; i++){
        int breadth = 1<<i;
        // p("breadth " +breadth);''
        int anch1x = x/breadth;
        int anch1y = y/breadth;
        double percx = (x%breadth)*1.0/breadth;
        double percy = (y%breadth)*1.0/breadth;
        // (anch1x+1)*breadth;
        double a = (idkAt((anch1x+0)*breadth, (anch1y+0)*breadth,mixer)*(1-percx)+percx*idkAt((anch1x+1)*breadth, (anch1y+0)*breadth, mixer));
        double b = (idkAt((anch1x+0)*breadth, (anch1y+1)*breadth,mixer)*(1-percx)+percx*idkAt((anch1x+1)*breadth, (anch1y+1)*breadth, mixer));
        double TRVE_HEIGHT = (b*percy+(1-percy)*a);
        cum+=TRVE_HEIGHT*octave_lookup[i]*breadth;
    }
    return cum*3+128;
}

public static Object[] tup(Object ... o){
    return o;
}
// public static void p(Object o){
//     System.out.println(o);
// }
public static void p(Object ... o){
    // for(var oo: o){
    //     System.out.print(oo == null ? "null":oo.toString() +", ");
    // }
    // System.out.println();
}
// public static int r(int c){
//     return (c>>16)&255;
// }
// public static int g(int c){
//     return (c>>8)&255;
// }
// public static int b(int c){
//     return (c>>0)&255;
// }


public static double d(double a, double b, double c, double dd){
    return Math.sqrt((a-b)*(a-b)+(c-dd)*(c-dd));
}
public static int _7812(int i){
    return i - 7812;
}
public static String stringify_7812(long i){
    return _7812(x(i)) + "," +  _7812(y(i));
}
    private static HashMap<String, Object[]> timingsMap = new HashMap<String, Object[]>();
    private static HashMap<String, LinkedList<Long>> subtimingsMap = new HashMap<String, LinkedList<Long>>();
    public static boolean time(String label){
        return time(label, false);
    }
    public static String getTimings(){
        String s = "";
        
        for(var t: timingsMap.entrySet()){
            s+=(", " + t.getKey() + ": " + ((Long)t.getValue()[2]));
        }
        return s;
    }
    public static void subtiming_clear(String label){
        subtimingsMap.remove(label);
    }

    private static LinkedList<Object[]> triggered = new LinkedList<Object[]>();

    public static void subtiming(String label){
        if(timingsMap.get(label) == null) return;
        // System.out.println("Ok I'm doing SUBTIMING for label: " + label);
        var ll = subtimingsMap.get(label);
        if(ll == null){
            ll = new LinkedList<Long>();
            subtimingsMap.put(label, ll);
        }
        ll.addLast(System.currentTimeMillis());
    }
    public static void printTime(Object[] idk){
        String label = (String)idk[0];
        Long n = (Long)idk[1];
        Long taken = (Long)idk[2];
        var ll = subtimingsMap.get(label);
        if(ll != null){
            int i = 0;
            for(var timing: ll){
                System.out.println("subtime " + label + " " + i++ + " " + (timing - taken));
            }
        }
        System.out.println("ELAPSED _ \t\t\t\t " + label +":\t\t "+ n);
    }
    public static void printAllTimes(){
        if(!timingPrints) return;
        for(var idk: triggered){
            printTime(idk);
        }
        timingsMap.clear();
        subtimingsMap.clear();
        triggered.clear();
    }

    public static boolean time(String label, boolean beep){
        Object[] o = timingsMap.get(label);
        boolean deleteAfter = false;
        if(o != null){
            deleteAfter = true;
        }
        if(o == null){
            o = tup(false, System.currentTimeMillis(), 0l);
        }
        boolean t = (Boolean)o[0];
        long taken = (Long)o[1];
        long nn = (Long)o[2];
        boolean ret = false;
        if(t){
            long n = (System.currentTimeMillis()-taken);
            nn = n;
            if(beep || n > 30){
                triggered.addLast( tup(label, n, taken));
            } 
            ret = beep || n > 30;
        }else{
            taken = System.currentTimeMillis();
        }
        t = !t;
        timingsMap.put(label, tup(t, taken, nn));
        if(deleteAfter)
            timingsMap.remove(label);
    
        return ret;
    }

public static byte[] load(String fname){
    try{
        Path path = FileSystems.getDefault().getPath(fname);
        byte[] bytes = Files.readAllBytes(path);
        return bytes;
    }catch(Exception e){
        return null;
    }
}
public static void save(String fname, byte[] b){
    try {
        Path path = FileSystems.getDefault().getPath(fname);
        Files.write(path, b);
    } catch (Exception e) {
        e.printStackTrace();
    }
}
static DecimalFormat df = new DecimalFormat("0.#");
static DecimalFormat df2 = new DecimalFormat("000.0");
static DecimalFormat df3 = new DecimalFormat("000000");

static class Override{
    int texture;
    double height;
}

public static boolean timingPrints = false;
public static HashMap<Long, Override> overrides = new HashMap<Long, Override>();
// public static ArrayList<GameObject> pasties = new ArrayList<GameObject>();
public static QuadTree pasties = new QuadTree();
public static ArrayList<GameObject> pasties_throw_away = new ArrayList<GameObject>();

static LinkedList<GameObject>[] invStore = (LinkedList<GameObject>[]) new LinkedList[]{
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
    new LinkedList<GameObject>(),
};

static int tryGetInvStoreIndex(){
    int check = (int)(Math.random()*invStore.length);
    for(int i = 0; i < 20; i++){
        check = (int)(Math.random()*invStore.length);
        if(invStore[check].size() == 0)
            break; 
    }
    return check;
}

static Spriter[] invStorePics = new Spriter[invStore.length];

static HashMap<Object, Pasty> picStore = new HashMap<Object, Pasty>();


static LinkedList<GameObject> bobbies = invStore[0];
static LinkedList<GameObject> offhand = invStore[1];
static LinkedList<GameObject> skysigns = new LinkedList<GameObject>();
static LinkedList<GameObject> dellies = new LinkedList<GameObject>();

public static boolean DEV_NO_SAVE_MODE = false;
public static boolean APOSTROPHE_MODE = true;
public static long last_electric_frogs_ts;
public static void marshal(){
    var f = (new File("./"+folder+"/"));
    if(!f.exists()){
        f.mkdirs();
    }
    
    ParkerBuffer b = new ParkerBuffer(true, fname);
    b.putInt(TEMPORARY_CONTEXT_CONTEXT); // for future use
    b.putInt(1); // for future use
    {   
        if(globalHandSpriterOverride != null){
            // YES USE HANDS
            b.putInt(2); // for future use
            GameObject.marshal(b, globalHandSpriterOverride, true);
            // handSpriterOverride._marshal(b, true);
        }else{
            b.putInt(0); // for future use
        }
    }
    {
        DragonDrop.setSettings(settings);
        b.putInt(2); // for future use
        for(var  i: settings){
            var idk = (Integer)i;
            b.putInt(idk);
        }
    }
    // b.putInt(1); // for future use
    if(central_dogma != null){
        b.putInt(1);
        GameObject.marshal(b, central_dogma, false);
    }else{
        b.putInt(0);
    }
    
    if(globalTenPortals != null)
        keyed_positions = globalTenPortals.portalNodules;
    for(int i = 0; i < 10; i++){
        var p = keyed_positions[i];
        if(p != null){
            b.putInt(1);
            GameObject.marshal(b, p, false);
        }else{
            b.putInt(0);
        }
    }


    if(depthStack.size() == 0){
        b.putInt(0);
    }else{
        b.putInt(1);
        var it = depthStack.iterator();
        while(it.hasNext()){
            var i = it.next();
            if(i.x == null){
                it.remove();
                continue;
            }
            b.putInt(1);
            {
                b.putInt(i.x.width);
                b.putIntArr(i.x.tempIntDataForStorage);
                b.putInt(i.z);
            }
            GameObject.marshal(b, i.y, false);
        }
        b.putInt(0); // no more "null terminated"
    }

    if(!DEV_NO_SAVE_MODE){
        for(var pair: sectorFunctions.entrySet()){
            var l = pair.getKey();
            var sector = pair.getValue();
            b.putInt(1);
            // write ... each sector
            b.putLong(l); // key
            b.putInt(fn2idlkp.get(sector.x));
            b.putInt(fn2idlkp.get(sector.y));
            // b.putInt(fn2idlkp.get(sector.z));
            b.putDoubleArr(sector.a);
        }
        b.putInt(0);
    }

    {
        b.putDouble(neck);
        b.putDouble(chin);
        b.putDouble(x);
        b.putDouble(z);
    }
    
    b.putInt(overrides.size());
    for(var e: overrides.entrySet()){
        b.putLong(e.getKey());
        var v = e.getValue();
        b.putInt(v.texture);
        b.putDouble(v.height);
    }
    pasties.queryAll();
    b.putInt(pasties.size());
    // p("writing out" + pasties.size());
    // for(var p: pasties){
    for(int i = 0; i < pasties.size(); i++){
        GameObject p = pasties.getAt(i);
        // p("writing out");
        GameObject.marshal(b, p, false);
    }
    for(var bobbies: invStore){
        b.putInt(bobbies.size());
        for(var p: bobbies){
            GameObject.marshal(b, p, true);
        }
    }
    b.putInt(skysigns.size());
    for(var p: skysigns){
        GameObject.marshal(b, p, true);
        // p("marshallign out skysigns");
    }
    // byte[] out = b.array();
    // save(handle,out);

    var it = stacks.iterator();
    while(it.hasNext()){
        var i = it.next();
        b.putInt(1);
        i.marshal(b);

    }
    b.putInt(0); // no more "null terminated"
    

    b.finalizeWritingFile();
}
public static String folder = "pspace";
public static String resourcefolder = "./";
public static long pastcum = 0;
public static Boolean shouldmakebackup = true;
public static String fname = "./"+folder+"/parkerspace_data_t.save";
public static Object[] settings = tup(0,0,0,0,0,0,0,0,0,0);

public static void unmarshal(){
    //byte[]
    //bytebuffer
    // var ba = load(handle);
    // if(ba == null) return;
    // String fname = "./parkerspace_data.save";
    if(!(new File(fname).exists())) return;
    ParkerBuffer b = new ParkerBuffer(false, fname);
    TEMPORARY_CONTEXT_CONTEXT = b.getInt(); // for future use;
    b.getInt(); // for future use;
    // b.getInt(); // for future use;
    // b.getInt(); // for future use;
    if(b.getInt() == 2){
        System.out.println("unmarshalling custom hand");
        handSpriterOverride = (Pasty)GameObject.unmarshal(b, true);
        globalHandSpriterOverride = handSpriterOverride;
    }
    if(b.getInt() == 2){ // is it really just this easy?
        settings = tup(
            b.getInt(),
            b.getInt(),
            b.getInt(),
            b.getInt(),
            b.getInt(),
            b.getInt(),
            b.getInt(),
            b.getInt(),
            b.getInt(),
            b.getInt());
        System.out.println("new settings feature!!");
        {
            var  v1 = ((Integer)settings[0] == 1);
            var  v2 = ((Integer)settings[0] == 1);
            var  v3 = ((Integer)settings[0] == 1);
            var  v4 = ((Integer)settings[0] == 1);
            var  v5 = ((Integer)settings[0] == 1);
            var  v6 = ((Integer)settings[0] == 1);
            var  v7 = ((Integer)settings[0] == 1);
            var  v8 = ((Integer)settings[0] == 1);
        }
        // {
        //     if(v1){
        //         ClipboardMonitor.insertionMode = checkBoxValue;
                
        //     }
        // }
    }

    if(b.getInt()== 1){
        central_dogma = (PortalNodule)GameObject.unmarshal(b, false);
    }
    for(int i = 0; i < 10; i++){
        if(b.getInt()== 1){
            keyed_positions[i] = (PortalNodule)GameObject.unmarshal(b, false);
        }
    }
    globalTenPortals = new TenPortals();
    globalTenPortals.portalNodules = keyed_positions;
    activeTenPortals = globalTenPortals;

    if(b.getInt()== 1){
        while(b.getInt() == 1){
            int width = b.getInt();
            int[] data = b.getIntArr();
            int ctx = b.getInt();
            // int ctx = 0;
            int height = data.length/width;
            PortalNodule p = (PortalNodule)GameObject.unmarshal(b, false);
            var s = new Spriter(data, width, height, 1, 4);
            depthStack.addLast(new Tuple3(s,p,ctx));
        }
        Spriter.globaluishrinker = (float)Math.pow(Main.cutoutSize, depthStack.size());
    }

    if(!DEV_NO_SAVE_MODE){
        while(b.getInt() == 1){
            long key = b.getLong();
            BiFunction<int[],double[],Double> x = id2fnlkp[b.getInt()];
            BiFunction<int[],double[],int[]> y = id2fnlkp[b.getInt()];
            // BiFunction<int[],double[],Integer> z = id2fnlkp[b.getInt()];
            double[] a = b.getDoubleArr();
            var s = new Sector(x,y,null,a,new int[0]);
            sectorFunctions.put(key, s);
        }
    }


    {
        neck = b.getDouble();
        chin = b.getDouble();
        x = Math.round(b.getDouble());
        z = Math.round(b.getDouble());
    }
    int count = b.getInt();
    System.out.println("deleteme");
    for(int i = 0; i < count; i++){
        var o =new Override();
        long xy = b.getLong();
        o.texture = b.getInt();
        o.height = b.getDouble();
        overrides.put(xy, o);
    }
    System.out.println("deleteme");

    {
        int quant = b.getInt();
        // System.out.println("1 reading in "+ quant);
        for(int i = 0; i < quant; i++){
            GameObject go = GameObject.unmarshal(b, false);
            pasties.insert(go); // bypass the trigger
            if(go instanceof ProtoVideo){
                // System.out.println("ALERT ALERT FOUND ONE FOUND ONE");
            }
            if(go instanceof ParkerQuizItem){
                // System.out.println("ALERT ALERT FOUND ONE FOUND ONE (pqi)");
                // Main.DELETION_PURGATORY.add(go);
            }
            if(go instanceof PumpJack){
                // System.out.println("ALERT ALERT FOUND ONE FOUND ONE (pumpjack)");
                // Main.DELETION_PURGATORY.add(go);
            }
            pasties_throw_away.add(go);
        }
    }
    if(Main.SAVE_UPGRADE){
        //OLD STYLE; TO DELETE
        {
            int quant = b.getInt();
            // System.out.println("2 reading in "+ quant);
            for(int i = 0; i < quant; i++){
                System.out.println("What is wrong with this???");
                intoInventory(GameObject.unmarshal(b, true));
            }
        }
    }else{
        for(var bobbies: invStore){
            int quant = b.getInt();
            // System.out.println("3 reading in "+ quant);
            for(int i = 0; i < quant; i++){
                System.out.println("WTF WTF WTF " + quant);
                intoInventory(GameObject.unmarshal(b, true));
            }
        }
        System.out.println("uuuhhhh so this is? " + bobbies.size());
    }
    System.out.println("deleteme");

    {
        int quant = b.getInt();
        // System.out.println("4 reading in "+ quant);
        for(int i = 0; i < quant; i++){
            // p("marshalling in skysigns");
            skysigns.addLast(GameObject.unmarshal(b, true));
        }
    }
    System.out.println("deleteme");

    while(b.getInt() == 1){
        // System.out.println("COUNTEM ##");
        GigaStack gs = new GigaStack();
        gs.unmarshal(b);
        stacks.addLast(gs);
    }
        System.out.println("uuuhhhh so this is? " + bobbies.size());
    
}

static int buggaX;
static int buggaY;
static class ResizeFunction implements GLFWWindowSizeCallbackI {
public void invoke(long window, int newWidth, int newHeight) {
System.out.println("RESIZE INVOKE CALLED " + newWidth + ":" + newHeight);
glfwSetWindowSize(window,newWidth,newHeight);
glViewport(0, 0, newWidth, newHeight);
buggaX = newWidth;
buggaY = newHeight;
{ 
    int[] temp1 = new int[1];
    int[] temp2 = new int[1];
    GLFW.glfwGetWindowPos(window, temp1, temp2);

    // set cursor to middle, IDGAF
    glfwSetCursorPos(window,buggaX/2+temp1[0], buggaY/2+temp2[0]);
}
{
    double[] temp1 = new double[1];
    double[] temp2 = new double[1];
    glfwGetCursorPos(window,temp1,temp2);
    FocusFunction.repo_x = (int)temp1[0];
    FocusFunction.repo_y = (int)temp2[0];
}

}
}
static class FocusFunction implements GLFWWindowFocusCallbackI {
    static int repo_x = 400;
    static int repo_y = 400;
    public void invoke(long window, boolean focused) {
        boolean prior = Main.focused;
        Main.focused = focused;
        if(Main.focused && prior != focused){
            {
                int sw = 1920;
                int sh = 1200;
                // long monitor = GLFW.glfwGetPrimaryMonitor();

                // GLFWVidMode vidMode = GLFW.glfwGetVideoMode(monitor);
                // int width = vidMode.width();
                // int height = vidMode.height();
                
                int[] temp1 = new int[1];
                int[] temp2 = new int[1];
                GLFW.glfwGetWindowPos(window, temp1, temp2);

                // set cursor to middle, IDGAF
                glfwSetCursorPos(window,buggaX/2+temp1[0], buggaY/2+temp2[0]);

            }


            // glfwHideWindow(topWindow);
            // glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        }else{
            // glfwShowWindow(topWindow);
        }
        // System.out.println("DOING FOCUS CODE " + focused);
        // repo_x = 
        double[] temp1 = new double[1];
        double[] temp2 = new double[1];
        glfwGetCursorPos(window,temp1,temp2);
        repo_x = (int)temp1[0];
        repo_y = (int)temp2[0];
        // if(focused){
        //     glfwSetCursorPos(window,400,400);
        // }
    }
}
static boolean focused = false;
static RecordNodule globalRecording = null;

static TargetDataLine line = null;

public static BufferedImage imageToBufferedImage(Image im) {
    BufferedImage bi = new BufferedImage
        (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
    Graphics bg = bi.getGraphics();
    bg.drawImage(im, 0, 0, null);
    bg.dispose();
    return bi;
}


public interface Resolvable{
    public int getResolution();
}

static double manhattan(double x, double z, double x2, double z2){
    return Math.abs(x-x2)+Math.abs(z-z2)  ; //manhattan distance
}
static ExecutorService exe = Executors.newFixedThreadPool(1);
static ExecutorService exe0 = Executors.newFixedThreadPool(1);
static ExecutorService exe2 = Executors.newFixedThreadPool(1);
static ExecutorService exe3 = Executors.newFixedThreadPool(8);

static LinkedList<Future<LinkedList<GameObject>>> myTaskCry = new LinkedList<Future<LinkedList<GameObject>>>();
// static Object myTaskCry = null;
static Object tt = null;

static boolean coreLock = false;
static boolean outerLock = false;
static boolean lock(){
    return false;
    // return coreLock;
}

static Object forGettingLx7Lz7 = null;
public static double x = 1_000_000+2000;
public static double y = 0;
public static double z = 1_000_000+2000;
static double accel_x;
static double accel_z;
static double dispy = 0;

static double originx;
static double originz;

static int lx7 = -1;
static int lz7 = -1;
static Object terTaskCry = null;
static Object terTaskCry2 = null;

static int ter_lx7 = -1;
static int ter_lz7 = -1;
static HashSet<Long> lastSet = new HashSet<Long>();
static double followdist  = 10;  //derive osx,osz
static double followangle = 0; //derive osx,osz
static double followneck  = 10;  //derive osy
static boolean followmode = true;
static boolean muted = false;
public static double neck = 0;
public static double chin = 0;

public static int PARKER_BUFFER_SIZE = 2_000_000_000;
private static long timeStamp;
private static boolean isFileUpdated( File file ) {
    var ts = file.lastModified();
    if( timeStamp != ts ) {
      timeStamp = ts;
      return true;
    }
    return false;
  }
public static int minzed(int v1) {
    if(v1 < 0)
        return 0;
    return v1;
}

public static class FreeMouse{
    static boolean mode = false;
    static double x = 0;
    static double y = 0;
}
public static class TileClick{
    static int id = 0;
    static boolean mode = false;
    static long[] vals = new long[0xff_ff_ff];
    static int targetx = -1;
    static int targety = -1;
    public static int register(int x, int y) {
        id = (id+1)%(0xff_ff_ff);
        vals[id] = xy(x, y);
        return id;
    }
    public static long lookup(int val){
        if(val > vals.length) return 0;
        return vals[val];
    }
}

public static TextNodule wordgentypingswitch = new TextNodule();
public static TextNodule textgentypingswitch = new TextNodule();
public static TextNodule textstamp = new TextNodule();

public static TextNodule quiztypingswitch = new TextNodule();

public static TextNodule fuzzytypingswitch = new TextNodule();

public static TextNodule multiplechoicetypingswitch = new TextNodule();

public static ParkerQuizItem quizCheck = null;
public static ParkerQuizItem priorQuizCheck = null;
public static double quizTransitionEnergy = 0;
public static double quizTransitionEnergy_DEFAULT = 1.0;

public static boolean dumpside = false;

public static String guess = null;
public static String answer = null;

public static LinkedList<Boolean> score = new LinkedList<Boolean>();

public static long window = -1;
public static long topWindow = -2;
public static State topWindowState = new State();
public static float dumbcolor;
public static int xx = 100;
public static int yy = 100;
public static long start;
public static long startM;
public static boolean demandreset = false;
public static long lastkeyused = 0;
public static Runnable arbfunc = () ->{

    
};
    static boolean FULL_SCREEN_MODE_IS_ON = false;
public static Runnable arbfunc2 = () ->{

    
};
public static Spriter topFighters;
public static Spriter topText;
public static double jump = 0;
static void startup(){
    glfwInit();
    try{
        WordGen.main(new String[]{"cool"});
    }catch(Exception e){e.printStackTrace();}
    

    
    var monitor = glfwGetPrimaryMonitor();
    // IntBuffer xSize = IntBuffer.allocate(4);
    // IntBuffer ySize = IntBuffer.allocate(4);
    // glfwGetMonitorPhysicalSize(monitor, xSize, ySize);
    // GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
    GLFWVidMode vidmode = glfwGetVideoMode(monitor);

    int ww = vidmode.width();
    int hh = vidmode.height();
    start = System.nanoTime();
    startM = System.currentTimeMillis();
    buggaX = ww;
    buggaY = hh;
    if(!FULL_SCREEN_MODE_IS_ON) monitor = 0;
    
    
    glfwWindowHint(GLFW_DEPTH_BITS, 32);
    // GLFW.glfwWindowHint(GLFW.GLFW_FLOATING, GLFW.GLFW_TRUE);


    window = glfwCreateWindow(ww, hh, "ParkerSpace - I'm Alright in the Mind Palace", monitor, 0);
    
    glfwMakeContextCurrent(window);
    // glfwWindowHint(GLFW_RESIZABLE, GL_TRUE);
    glfwWindowHint(GLFW_CENTER_CURSOR, GL_TRUE);
    glfwWindowHint(GLFW_DECORATED, 0);
    // glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
    // glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
    // glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
    // glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    glfwShowWindow(window);
    GL.createCapabilities();
    // Set the desired OpenGL version and profile
    
    // glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
    // glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 1);
    // glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
    // // You can also set other window hints, for example:
    // glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE); // For macOS compatibility
    System.out.println(" -- glGetString(GL_VERSION) " + glGetString(GL_VERSION));
    
    glfwWindowHint(GLFW_RESIZABLE, 0);
    // glfwWindowHint(GLFW_DECORATED, GLFW_FALSE);
    glfwWindowHint(GLFW_FLOATING, GLFW_TRUE);
    // glfwWindowHint(GLFW_FOCUS_ON_SHOW, GLFW_TRUE);
    topWindow = glfwCreateWindow(900, 500, "", 0, 0);
    glEnable(GL_BLEND);
    glEnable(GL_DEPTH_TEST);
    glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
    glDepthFunc(GL_LEQUAL);
    // glfwShowWindow(topWindow);
    glfwSetWindowOpacity(topWindow, 1.0f);
    
    glfwMakeContextCurrent(topWindow);
    GL.createCapabilities();
    System.out.println("checkpoint idek");

    topFighters = new Spriter(600*3,"./Fighters.bmp",8);
    System.out.println("checkpoint idek");
    topText = new Spriter(1000, "textsheet.bmp", 16);
    System.out.println("checkpoint idek");
    scapeSpriter = new Spriter(100_000,"./Fighters.bmp",8);
    topFighters.uishrinker = 1.0f;
    topText.uishrinker = 1.0f;
    glfwMakeContextCurrent(window);
    System.out.println("checkpoint idek");
}

public static Spriter scapeSpriter;

public static boolean SAVE_UPGRADE = false;
public static PortalNodule central_dogma = null;
public static PortalNodule[] keyed_positions = new PortalNodule[10];
public static int sleep_time_override = -1;
/**
 * @param args
 */

 public static int[] convertToARGB(int[] rgbaColors) {
    int[] argbColors = new int[rgbaColors.length];
    for (int i = 0; i < rgbaColors.length; i++) {
        int r = (rgbaColors[i] >> 24) & 0xFF;
        int g = (rgbaColors[i] >> 16) & 0xFF;
        int b = (rgbaColors[i] >> 8) & 0xFF;
        int a = rgbaColors[i] & 0xFF;
        argbColors[i] = (a << 24) | (r << 16) | (g << 8) | b;
    }
    return argbColors;
}
public static int[] convertToARGBAndFlip(int[] rgbaColors, int width) {
    return convertToARGBAndFlip(rgbaColors, width, 1 );
}
public static int[] convertToARGBAndFlip(int[] rgbaColors, int width, double borderperc) {
    int height = rgbaColors.length/width;
    int widthsidecut = (int)(width*borderperc);
    int heightsidecut = (int)(height*borderperc); 
    int[] argbColors = new int[rgbaColors.length];
    for (int i = 0; i < rgbaColors.length; i++) {
        int height_i = i/width;
        int width_i = i%width;
        int r = (rgbaColors[i] >> 24) & 0xFF;
        int g = (rgbaColors[i] >> 16) & 0xFF;
        int b = (rgbaColors[i] >> 8) & 0xFF;
        int a = rgbaColors[i] & 0xFF;
        boolean drop = (width_i < widthsidecut) || ((width-1-widthsidecut) < width_i) ||
            (height_i < heightsidecut) || ((height-1-heightsidecut) < height_i);
        argbColors[(height-1-height_i)*width+(width_i)] = !drop?0:(a << 24) | (r << 16) | (g << 8) | b;
    }
    return argbColors;
}
public static void codeTooLarge1(){

    if(TimeLockingKey.globalTypingSwitch == textgentypingswitch){
        
        if(TimeLockingKey.keys.get(GLFW_KEY_ENTER).isNewPress()){
            String str = TimeLockingKey.globalTypingSwitch.state.getLineAlone().trim();
            TimeLockingKey.globalTypingSwitch.state = new State();
            if(str != null)ASSIGNING_PROMPT_WORD = str;
            System.out.println("ASSIGNING PROMPT WORD..."  + str);

        }
        // if(TimeLockingKey.keys.get(GLFW_KEY_SPACE).isNewPress()){
            
        //     // SPECIAL DO IT
        //     {
        //         String str = TimeLockingKey.globalTypingSwitch.state.getLineAlone().trim();
        //         int[] ia = WordGen.takeFrame(str);  
        //         TimeLockingKey.globalTypingSwitch.state = new State();
        //         int ww = 1500;
        //         int hh = 850;
        //         ParkerQuizItem p = new ParkerQuizItem();
        //         p.mode = 1;
        //         p.answer = str;
        //         p.prompt_word = ASSIGNING_PROMPT_WORD;
        //         p.cohortID = assigningCohortID;
        //         // var bbi = bb.asIntBuffer();
        //         // int[] ia = new int[ww*hh];
        //         p.sprite = new Spriter(ia, ww, hh, 1, 4);
        //         p.width = ww;
        //         p.height = hh;
        //         p.data = ia;
        //         p.whrat = ww*(1.0)/hh;
        //         intoInventory(p);
        //     }
        // }
        if(TimeLockingKey.keys.get(GLFW_KEY_ENTER).isNewPress()){
            {
                System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAA HERE HER EHRE ");
                // CHANGE THE FACE
                faceSelection = (int)(Math.random()*20000);
            }
        }
    }
    
    if(TimeLockingKey.globalTypingSwitch == multiplechoicetypingswitch){
        if(TimeLockingKey.keys.get(GLFW_KEY_ENTER).isNewPress()){
            String str = TimeLockingKey.globalTypingSwitch.state.getLineAlone().trim();
            TimeLockingKey.globalTypingSwitch.state = new State();
            // may or may not use PN;
            PromiseNodule pn = new PromiseNodule();
            Runnable arbfunc = () ->{
                
                LinkedList<LinkedList<String>> ch = Chat2.call2(str);
                System.out.println(ch);
                // System.exit(0);
                for(var e: ch){
                    // String str = TimeLockingKey.globalTypingSwitch.state.getLineAlone().trim();
                    // var e = new LinkedList<String>();
                    // e.add(str);
                    // e.add(str);
                    // e.add(str);
                    // intoInventory(p);
                    // INVENTORY_PURGATORY.add(p);
                    MULTIPLECHOICE_PURGATORY.add(e);
                }
            };
            Thread t = new Thread(arbfunc);
            pn.watchThread = t;
            t.start();

            // kickoff...?
        }
    }
}
public static int[][] convertToARGBAndFlip2(int[] rgbaColors, int width, double borderperc) {
    int height = rgbaColors.length/width;
    int widthsidecut = (int)(width*borderperc);
    int heightsidecut = (int)(height*borderperc); 
    int[] argbColors = new int[rgbaColors.length];
    int[] argbColors2 = new int[rgbaColors.length];
    for (int i = 0; i < rgbaColors.length; i++) {
        int height_i = i/width;
        int width_i = i%width;
        int r = (rgbaColors[i] >> 24) & 0xFF;
        int g = (rgbaColors[i] >> 16) & 0xFF;
        int b = (rgbaColors[i] >> 8) & 0xFF;
        int a = rgbaColors[i] & 0xFF;
        boolean drop = (width_i < widthsidecut) || ((width-1-widthsidecut) < width_i) ||
            (height_i < heightsidecut) || ((height-1-heightsidecut) < height_i);
            argbColors[(height-1-height_i)*width+(width_i)] = !drop?0:(a << 24) | (r << 16) | (g << 8) | b;
            argbColors2[(height-1-height_i)*width+(width_i)] = drop?0:(a << 24) | (r << 16) | (g << 8) | b;
    }
    return new int[][]{argbColors, argbColors2};
}
public static Spriter cutOutSpriter = null;

public static LinkedList<Tuple3<Spriter /*outer*/,  PortalNodule, Integer>> depthStack =
    new LinkedList<Tuple3<Spriter /*outer*/,  PortalNodule, Integer>>();

private static String previousTextContent = null;

public static TextNodule linkTextNodule = null;
public static long FILE_LAST_UPDATED_VALUE = 0;
public static Tuple3<Spriter, PortalNodule, Integer> FLICKER_GRAB_CATCH_VALUE = null;
public static PortalNodule FLICKER_AFTER_PORTAL = null;
public static int TEMPORARY_CONTEXT_CONTEXT = tryGetInvStoreIndex() ;
public static double cutoutSize = 1.05;

public static boolean shouldJump = false;

public static GameObject nearest = null;
public static GameObject lastnearest = null;
public static GameObject nearestself = null;

public static LinkedList<GigaStack> stacks = new LinkedList<GigaStack>();
public static int bobbyselection = 0;

public static class GigaStack{
    public LinkedList<Tuple3<Spriter /*outer*/,  PortalNodule, Integer>> depthStack =
        new LinkedList<Tuple3<Spriter /*outer*/,  PortalNodule, Integer>>();
    int TEMPORARY_CONTEXT_CONTEXT = 0;
    int bobbyselection = 0;
    LinkedList<GameObject>[] invStore = (LinkedList<GameObject>[]) new LinkedList[]{
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
        new LinkedList<GameObject>(),
    };
    double chin;
    double neck;
    double x = 1_000_000;
    double z = 1_000_000;
    public void marshal(ParkerBuffer b){
        {
            var it = depthStack.iterator();
            while(it.hasNext()){
                var i = it.next();
                b.putInt(1);
                {
                    b.putInt(i.x.width);
                    b.putIntArr(i.x.tempIntDataForStorage);
                    b.putInt(i.z);
                }
                GameObject.marshal(b, i.y, false);
            }
            b.putInt(0); // no more "null terminated"
        }
        {
            b.putDouble(chin);
            b.putDouble(neck);
            b.putDouble(x);
            b.putDouble(z);
            b.putInt(TEMPORARY_CONTEXT_CONTEXT);
            b.putInt(bobbyselection);
        }
        {
            for(var bobbies: invStore){
                b.putInt(bobbies.size());
                for(var p: bobbies){
                    GameObject.marshal(b, p, true);
                }
            }
        }
    }
    public void unmarshal(ParkerBuffer b){
        {
            while(b.getInt() == 1){
                int width = b.getInt();
                int[] data = b.getIntArr();
                int ctx = b.getInt();
                // int ctx = 0;
                int height = data.length/width;
                PortalNodule p = (PortalNodule)GameObject.unmarshal(b, false);
                var s = new Spriter(data, width, height, 1, 4);
                depthStack.addLast(new Tuple3(s,p,ctx));
            }
        }
        {
            chin = b.getDouble();
            neck = b.getDouble();
            x = b.getDouble();
            z = b.getDouble();
            TEMPORARY_CONTEXT_CONTEXT = b.getInt();
            bobbyselection = b.getInt();

        }
        {
            for(var bobbies: invStore){
                int quant = b.getInt();
                // System.out.println("3 reading in "+ quant);
                for(int i = 0; i < quant; i++){
                    System.out.println("I think here is the problem");
                    bobbies.addLast(GameObject.unmarshal(b, true));
                }
            }
        }
    }
}

static void loadValuesFromFirstGigaStack(){
    if(stacks.isEmpty()) return;
    GigaStack gs = stacks.getFirst();
    depthStack                = gs.depthStack                ;
    TEMPORARY_CONTEXT_CONTEXT = gs.TEMPORARY_CONTEXT_CONTEXT ;
    bobbyselection            = gs.bobbyselection            ;
    chin                      = gs.chin                      ;
    neck                      = gs.neck                      ;
    x                         = gs.x                         ;
    z                         = gs.z                         ;
    invStore                  = gs.invStore                  ;
    bobbies = invStore[bobbyselection];
    Spriter.globaluishrinker = (float)Math.pow(Main.cutoutSize, depthStack.size());
}

static void dumpToFirstGigaStack(){
    if(stacks.isEmpty()) stacks.addFirst(new GigaStack());
    GigaStack gs = stacks.getFirst();
    gs.depthStack                = depthStack                ;
    gs.TEMPORARY_CONTEXT_CONTEXT = TEMPORARY_CONTEXT_CONTEXT;
    gs.TEMPORARY_CONTEXT_CONTEXT = TEMPORARY_CONTEXT_CONTEXT ;
    gs.bobbyselection            = bobbyselection            ;
    gs.chin                      = chin                      ;
    gs.neck                      = neck                      ;
    gs.x                         = x                         ;
    gs.z                         = z                         ;
    gs.invStore                  = invStore                  ;
}

public static double lastplacementx = 0;
public static double lastplacementz = 0;

public static double AUTO_CONTEXT_TIMER = Integer.MIN_VALUE;

public static int seed = 0;
public static int presentation_cycle_counter = 0;
public static int presentation_last_imprint = 0;
public static boolean CYCLING_MODE = false;

public static double velocity = 0;
public static double flightheight = 0;

public static int insertionPoint = 0;
public static Pasty backplate = null;

public static HashSet<Long> touchedSet = new HashSet<Long>();

public static ByteBuffer audioData = ByteBuffer.allocateDirect(100_000_000);

public static int faceSelection = (int)(Math.random()*20000);

public static int assigningCohortID = (int)(Math.random()*Integer.MAX_VALUE);
public static int filteringCohortID = assigningCohortID;
public static HashSet<ParkerQuizItem> passed = new HashSet<ParkerQuizItem>();
public static int lastAllQuizSize = 0;

public static Object objectOfFixation = null;

public static double neckdeviance = 0;
public static double chindeviance = 0;
public static double followdeviance = 0;
public static boolean wigglemode = false;
public static boolean newwigglemode = false;
public static ParkerQuizItem wiggleItem = null;
public static long hiddenCallBackTime = 0;
public static double fileneeded_x_catch = 0;
public static double fileneeded_z_catch = 0;
public static ParkerQuizItem firstquizitem = null;
public static Pasty toPromoteToQuizItem = null;



public static int prestigelevel = 1;
public static boolean FORCE_HIDE_DURING_ROTATION_EXPIRMENT = false;
public static boolean forward = true;
public static LinkedList<ParkerQuizItem>[] BACKTRACK_ITEM_STACK_S = (LinkedList<ParkerQuizItem>[])new LinkedList[]{
    new LinkedList<ParkerQuizItem>(),
    new LinkedList<ParkerQuizItem>(),
    new LinkedList<ParkerQuizItem>(),
    new LinkedList<ParkerQuizItem>(),   
    new LinkedList<ParkerQuizItem>(),   
    // new LinkedList<ParkerQuizItem>(),
    // new LinkedList<ParkerQuizItem>(),
    // new LinkedList<ParkerQuizItem>(),
};

public static LinkedList<ParkerQuizItem> fwd_bwd = new LinkedList<ParkerQuizItem>();
public static String ASSIGNING_PROMPT_WORD = "NO LINK";
public static String VISUAL_PROMPT_WORD = "NO WORD SET";
                            
public static int bt_amt = 5;
public static LinkedList<ParkerQuizItem> BACKTRACK_ITEM_STACK = null;
public static boolean BACKTRACK_ENGAGED = false;
public static ParkerQuizItem SAVED_RETURNAL_ELEMENT = null;
public static ParkerQuizItem JUST_IN_CASE = null;
public static ParkerQuizItem LOST_WASTED = null;
public static LinkedList<ParkerQuizItem> unwindme = new LinkedList<ParkerQuizItem>();


public static boolean hidePQItoggle = true;
public static boolean hidePQIOverlay = false;


public static byte[] readFileToByteArray(Path filePath) throws IOException {
    return Files.readAllBytes(filePath);
}

// static CopyOnWriteArrayList<Pasty> pleaseloadme = new CopyOnWriteArrayList<>();
static CopyOnWriteArrayList<Object[]> pleaseloadme = new CopyOnWriteArrayList<>();

static BufferedImage incomingImageForPasting = null;

static boolean IS_FLAGGED_AS_BACKUP = false;
//#quik
static Pasty fortounload;
static Pasty poppedPasty;
static boolean fortounload_mousedown;
static int fortounload_mousebutton;
static Double fortounload_last_x;
static Double fortounload_last_y;

static double parkervalue;
static Pasty handSpriterOverride;
static Pasty globalHandSpriterOverride;
static TenPortals globalTenPortals;
static TenPortals activeTenPortals;
static TenPortals neededTenPortals;
static Random rand = new Random();
static long last_frames_key = 0;
static float mouseWheelVelocity = 0;
static float mouseWheelRotation = 0;
static double poppedPastySize = 10;
static double textstamp_size = 1;

static double TEMPORARY_VIDEO_PERCENT_DONE = 0;

public static Color scalarToColor(double scalar, double min, double max) {
    // Normalize scalar value to 0-1 range
    double normalized = (scalar - min) / (max - min);
    // Map normalized value to hue (0-360 degrees)
    float hue = (float) (normalized * 360.0);
    // Set saturation and brightness to maximum (1.0 for full color)
    float saturation = 1.0f;
    float brightness = 1.0f;
    
    // Convert HSV to RGB
    return Color.getHSBColor(hue, saturation, brightness);
}

public static BufferedImage waithere = null;
public static long waitherefulltime = 0;
public static int waitheresidelength = 0;
public static boolean cycling_remote_mode = false;
public static long last_cycling = 0;

public static void intoInventory(GameObject go){
    // System.out.println("HERE + " + go);
    // intoInventory(go);
    if(!(go instanceof FunnelNodule) && nearest instanceof FunnelNodule && !((FunnelNodule)nearest).phantoms.isEmpty()){
        if(go != null){
            var fn = (FunnelNodule)nearest;
            var ph = fn.phantoms.pop();
            go.ydisp = ph.ydisp;
            go.x = ph.x+Math.random()/100000; // to fix the positioning
            go.z = ph.z;
            go.rot = ph.rot;
            if(fn.completed != null)
                fn.completed.addLast(ph);
            // go.load();
            // pasties.add(go);
            PURGATORY.add(go);
            {
                // long fulltime = (long)(bb.audioBytes.length /(44.1)/2);
                // if(fulltime != 0){
                    var bb = plop_sfx;
                    // var bb = !funnelsound ? poc_sfx: plop_sfx;
                    
                    bb.playing = true;

                    // System.out.println("fulltime is "  + fulltime);
                    // long clock = System.currentTimeMillis();
                    // long remainder = (clock%fulltime);
                    // System.out.println("remainder is  "  + remainder);
                    // double whereami = (remainder*1.0/fulltime);
                    // System.out.println("WHY ISN'T IT WORKING ???? " + bb.displayMode);
                    // System.out.println("whereami is  "  + whereami);
                    // System.out.println("going to try..." + (int)(whereami*bb.audioBytes.length));
                    {
                        // I want to delete it but NO!s
                        AL10.alDeleteBuffers(bb.loadedBuffer);
                        AL10.alDeleteSources(bb.loadedSource);
                        bb.loadedBuffer = AL10.alGenBuffers();
                        bb.loadedSource =  AL10.alGenSources();
                        // bb.fuckmebuffer.clear();
                        // bb.fuckmebuffer.put(bb.audioBytes);
                        // bb.fuckmebuffer.flip();
                    }
                    // bb.fuckmebuffer.position((int)(whereami*bb.audioBytes.length)/2*2);
                    bb.fuckmebuffer.position(0);
                    // bb.fuckmebuffer.position((int)(bb.fuckmebuffer.limit()*Math.random()));
                    // bb.fuckmebuffer.position(477520+2);
                    bb.reloadaaa();
                    bb.sprite.clearRepaunch();
                    // bb.sprite.repaunch = false; // sometimes I am just SOO stupid

                    AL10.alSourcePlay(bb.loadedSource);
                    // bb.nextMillisToPlay = nownow + (long)(fulltime*(1-whereami));
                    // System.out.println("delay is ... " + (long)(fulltime*(1-whereami)));
                // }
            }
        }
    }else{
        // System.out.println("HERE 2 + " + go);

        bobbies.addFirst(go);
    }
}
public static BurningBush bbExNihilo(String s){
    try{
        Path path = Paths.get(s);
        byte[] a = Files.readAllBytes(path);
        // do it
        File f = new File(s);
        File file = new File(s);
        // file.delete();
        byte[] aa = a;
        {
            ByteBuffer bb = SimpleTri.main(null);
            bb.flip();
            GameObject go = null;
            {
                try{
                    ParkerBuffer pb = new ParkerBuffer(bb);
                    ThreeDumb p = new ThreeDumb();
                    p.pointsCt = pb.getInt();
                    p.subdivs = pb.getInt();
                    int datamode = pb.getInt();
                    System.out.println("`ED DATA MODE IS " + datamode);
                    BufferedImage bi = null;
                    if(datamode == 1){
                        int width = pb.getInt();
                        int height = pb.getInt();
                        pb.getInt();
                        int[] texdat = pb.getIntArr();
                        p.sprite = new Spriter(texdat, width, height, p.sl(), p.mso());
                        p.sprite.repaunch = true;
                        p.data = texdat;
                        p.width = width;
                        p.height = height;
                        p.whrat = width*1.0/height;
                    }
                    pb.getInt();
                    {
                        int sosOption = pb.getInt();
                        if(sosOption == 1){
                            p.sos = pb.getIntArr();
                        }else{
                            p.sos = new int[p.pointsCt];
                        }
                    }
                    double values[] = pb.getDoubleArr();
                    {
                        p.values = values;
                        System.out.println("WTF?? "  +aa.length);
                        p = p.promote(aa);
                        p.displayMode = 2; // way to control only new imports
                        ((BurningBush)p).cycleAsProportion = 2;
                        ((BurningBush)p).offsetAsProportion = 2;
                        go = p;
                        go.x = x;
                        go.z = z;
                        go.ydisp = dispy+y;
                        go.rot = neck+Math.PI/2;
                        // if(!lock())
                        //     pasties.add(go);
                        ((Pasty)go).sprite.repaunch = false;
                        return (BurningBush)go;
                        // intoInventory(go);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
}

public static BurningBush bbExNihilo(byte[] bytes){
    try{
        // Path path = Paths.get(s);
        // byte[] a = Files.readAllBytes(path);
        // // do it
        // File f = new File(s);
        // File file = new File(s);
        // file.delete();
        byte[] aa = bytes;
        {
            ByteBuffer bb = SimpleTri.main(null);
            bb.flip();
            GameObject go = null;
            {
                try{
                    ParkerBuffer pb = new ParkerBuffer(bb);
                    ThreeDumb p = new ThreeDumb();
                    p.pointsCt = pb.getInt();
                    p.subdivs = pb.getInt();
                    int datamode = pb.getInt();
                    System.out.println("`ED DATA MODE IS " + datamode);
                    BufferedImage bi = null;
                    if(datamode == 1){
                        int width = pb.getInt();
                        int height = pb.getInt();
                        pb.getInt();
                        int[] texdat = pb.getIntArr();
                        p.sprite = new Spriter(texdat, width, height, p.sl(), p.mso());
                        p.sprite.repaunch = true;
                        p.data = texdat;
                        p.width = width;
                        p.height = height;
                        p.whrat = width*1.0/height;
                    }
                    pb.getInt();
                    {
                        int sosOption = pb.getInt();
                        if(sosOption == 1){
                            p.sos = pb.getIntArr();
                        }else{
                            p.sos = new int[p.pointsCt];
                        }
                    }
                    double values[] = pb.getDoubleArr();
                    {
                        p.values = values;
                        System.out.println("WTF?? "  +aa.length);
                        p = p.promote(aa);
                        p.displayMode = 2; // way to control only new imports
                        ((BurningBush)p).cycleAsProportion = 2;
                        ((BurningBush)p).offsetAsProportion = 2;
                        go = p;
                        go.x = x;
                        go.z = z;
                        go.ydisp = dispy+y;
                        go.rot = neck+Math.PI/2;
                        // if(!lock())
                        //     pasties.add(go);
                        ((Pasty)go).sprite.repaunch = false;
                        return (BurningBush)go;
                        // intoInventory(go);
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }catch(Exception e){
        e.printStackTrace();
    }
    return null;
}
public static BurningBush poc_sfx = null;
public static BurningBush plop_sfx = null;
public static BurningBush poc_sfx_warp = null;
public static BurningBush sfx_failure = null;
public static BurningBush sfx_success = null;
public static RateLimiter rateLimiter = new RateLimiter();
public static boolean pumpjackmode = false;

public static HashSet<Spriter> ghostSpriters = new HashSet<Spriter>();
public static double ghost_captured_x;
public static double ghost_captured_y;
public static double ghost_captured_z;
public static double ghost_captured_chin;
public static double ghost_captured_neck;
public static boolean ghostAssignment = false;
public static LinkedList<HashSet<Spriter>> ghost_subs = new LinkedList<HashSet<Spriter>>();
public static LinkedList<MonoTuple5<Double>> ghost_pos = new LinkedList<MonoTuple5<Double>>();
static Clipboard clipboard;

public static LinkedList<GameObject> recursiveFolderRead1(String ss){
    System.out.println("./DumpFolder2");
    // var folderPath = "./DumpFolder2";
    var folderPath = ss;
    List<File> fileList = new ArrayList<>();
    Path directory = Paths.get(folderPath);
    try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
        LinkedList<GameObject> gos = new LinkedList<GameObject>();
        for (Path path : directoryStream) {
            var file = path.toFile();
            if (Files.isRegularFile(path)) {
                var s = file.getAbsolutePath();
                var fn = FileNodule.readIn(s, false);
                System.out.println("regular:");
                System.out.println(path.toFile());
                // path.toFile();
                // fileList.add();
                // Main.intoInventory(fn);
                gos.addLast(fn);
                
            }else{
                var fn = new FolderNodule();
                System.out.println("folder:");
                System.out.println(path.toFile());
                {
                    //FILE GET NAME (NOT DIRECTORY)
                    String s = file.getName();
                    fn.state = new State();
                    fn.state.insert(s);
                }
                {
                    // But first, recursively call this function
                    // again
                }
                var gos2 = recursiveFolderRead1(file.getAbsolutePath());
                fn.bakedObjects = gos2;
                gos.addLast(fn);

                // Main.intoInventory(fn);
            }
        }
        return gos;
    } catch (IOException e) {
        System.err.println("Error reading folder: " + e.getMessage());
    }
    return new LinkedList<GameObject>();
}

static class Tile{
    public int resolution = -1;
    Object[][] data = null;
}

static ByteBuffer recordBuffer = ByteBuffer.allocate(20_000_000);

static class DedicatedRecordingRunnable implements Runnable{
    public int counter = 0;
    public long now = System.nanoTime();
    public long accum = 0;
    public long last = System.nanoTime();
    
    public void run(){
        while(true){
            counter++;
            now = System.nanoTime();
            if(globalRecording != null){
                double delta = (now - last) * 1.0 / 1_000_000_000;
                try{
                    globalRecording.recordTime += delta;
                    byte[] data = new byte[line.getBufferSize() / 5];
                    // int amt = line.available();
                    line.read(data, 0, data.length);
                    Main.recordBuffer.put(data);
                }catch(Exception e){
                    e.printStackTrace();
                    globalRecording = null;
                }
            }
            accum += now-last;
            last = now;
            if(accum > 1_000_000_000l){
                // System.out.println("Fps thread: " + counter);
                counter = 0;
                accum = 0;
                int putx = ((int)x)-(((int)x)%128);
                int puty = ((int)z)-(((int)z)%128);
            }else{
                try{
                    Thread.sleep(10);
                }catch(Exception e){}
            }
        }
    }
}

public static void pumpjack_heartbeat(){
    HashSet<Integer> jak_id_set = new HashSet<Integer>();

    {
        pasties.query2D(x-50, x+50, z-50, z+50);
        for(int gg = 0; gg < pasties.size(); gg++){
            GameObject go = pasties.getAt(gg);
            if(go instanceof PressurePlate && go.dist(x,z) < 30){
                jak_id_set.add(((PressurePlate)go).pjid);
            }
        }

    }
    // if(pumpjackmode){
    // pumpjack breath
    // maybe average 100 seconds
    
    // boolean holdingPasty = !offhand.isEmpty() && offhand.get(offhand.size()-1) instanceof Pasty; 
    // Pasty targetPasty = holdingPasty ? (Pasty)offhand.get(offhand.size()-1): null;
    // LinkedList<Pasty> offhandPasties = new LinkedList<Pasty>();
    // for(var item: offhand){
        //     if(item instanceof Pasty){
            //         offhandPasties.add((Pasty)item);
            //     }
            // }
    pasties.queryAll();
    LinkedList<PumpJack> jaks = new LinkedList<PumpJack>();
    for(int gg = 0; gg < pasties.size(); gg++){
        GameObject go = pasties.getAt(gg);
        if(go instanceof PumpJack && jak_id_set.contains(((PumpJack)go).pjid)){
            var pj = (PumpJack)go;
            jaks.addLast(pj);
        }
    }
    long noww = System.currentTimeMillis();
    for(var jak: jaks){
        if(noww < jak.activationtime)
            continue;
        addStatement("Executing pumpjack " + jak.pjid);
        int jr = 30;
        double rand_6 = (Math.random()+Math.random()+Math.random())*2;
        jak.activationtime = noww + (long)(rand_6*60_000);
        pasties.query2D(jak.x-30, jak.x+30, jak.z-30, jak.z+30);
        for(int zzz = 0; zzz < pasties.size(); zzz++){
            GameObject jj = pasties.getAt(zzz);
            if(jj instanceof TextNodule && jj.dist(jak) < 30){
                var tn = (TextNodule)jj;
                Runnable arbfunc = () ->{
                    Main.temporarilyNeededTextNodules.put(tn, tn);
                    boolean isReady = tn.state != null;
                    while (!isReady) { // Replace isReady() with your actual condition check
                        try {
                            Thread.sleep(100); // pause for 100ms before checking again
                            isReady = tn.state != null;
                            addStatement("waiting...");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // restore interrupt status and exit if needed
                            return; // or handle the interruption as appropriate
                        }
                    }
                    final var s = tn.state.stringify(" ").replace("\"", "\\\"");
                    Main.temporarilyNeededTextNodules.remove(tn);
                    String data = Chat.call(s);
                    if(s.length() > 40)
                        addStatement("prompting with text " + s.substring(0, 40));
                    else
                        addStatement("prompting with text " + s);

                    TextNodule p = new TextNodule();
                    p.sizeMulti/=1;
                    p.state = new State();
                    p.x = jak.x + Math.random();
                    p.z = jak.z + Math.random();
                    p.x+= Math.sin(jak.rot+Math.PI/2)*60;
                    p.z+= Math.cos(jak.rot+Math.PI/2)*60;
                    p.ydisp= jak.ydisp;
                    {
                        System.out.println("I have the data which is... " + data);
                        Scanner ss = new Scanner(data);
                        int i = 0;
                        while(ss.hasNextLine()){
                            if(i != 0){
                                p.state.enter(false);
                            }
                            i++;
                            String line = ss.nextLine();
                            // Split the line into words. (Note: This collapses multiple spaces.)
                            String[] words = line.split(" ");
                            StringBuilder lineBuffer = new StringBuilder();
                            
                            for(String word : words){
                                // If the buffer is empty, simply add the word.
                                if(lineBuffer.length() == 0){
                                    lineBuffer.append(word);
                                } 
                                // Otherwise, check if adding this word (plus a space) would exceed 40 characters.
                                else if(lineBuffer.length() + 1 + word.length() <= 40){
                                    lineBuffer.append(" ").append(word);
                                } 
                                // If it would exceed the limit, output the current buffer and start a new line.
                                else {
                                    p.state.insert(lineBuffer.toString());
                                    p.state.enter(false);
                                    lineBuffer = new StringBuilder(word);
                                }
                            }
                            // If there's any text left in the buffer, output it.
                            if(lineBuffer.length() > 0){
                                p.state.insert(lineBuffer.toString());
                            }
                        }
                        ss.close();
                    }
                    // need to project it out by 60;


                    PURGATORY.add(p);
                };
                Thread t = new Thread(arbfunc);
                t.start();
                
            }
        }
    }
}
public static void conveyor_heartbeat(){
    pasties.queryAll();
    int psize = pasties.gathermeindex;
    GameObject[] gatherme  = pasties.gatherme;
    LinkedList<Conveyor> conveyors = new LinkedList<Conveyor>();
    for(int i = 0; i < pasties.size(); i++){
        GameObject e = pasties.getAt(i);
        if(e instanceof Conveyor){
            conveyors.addLast((Conveyor)e);
        }
    }
    Set<GameObject> movedItems = new HashSet<GameObject>();
    for(var conveyor: conveyors){
        var e = conveyor;
        {
            double dx = e.x - x;
            double dy = e.z - z;
            double dist = Math.sqrt(dx * dx + dy * dy);

            if(dist < e.rad){
                // PUSH CHARACTER THE DIRECTION THAT IT'S FACING
                dispy += Math.random() * 0.2 - 0.1;
                x += 0.4 * Math.sin(e.rot);
                z += 0.4 * Math.cos(e.rot);
                
                // PULL TOWARDS THE CENTER OF THE BELT
                double centerPullStrength = 0.05;  // tweak this value as needed
                if(dist > 0.001){ // avoid division by zero
                    x += centerPullStrength * (e.x - x) / dist;
                    z += centerPullStrength * (e.z - z) / dist;
                }
            }
        }
        {
            double rad = Conveyor.rad;
            pasties.query2D(e.x - rad, e.x + rad, e.z - rad, e.z + rad);
            for(int i = 0; i < pasties.size(); i++){
                GameObject pp = pasties.getAt(i);
                if(!(pp instanceof Conveyor) && !(pp instanceof TtsMachine) && !pp.designatedCenterGO()){
                    
                    double dx = e.x - pp.x;
                    double dy = e.z - pp.z;
                    double dist = Math.sqrt(dx * dx + dy * dy);

                    if(dist < e.rad){
                        // PUSH THE OBJECT THE DIRECTION THAT THE BELT IS FACING
                        pp.ydisp += Math.random() * 0.2 - 0.1;
                        double prevX = pp.x;
                        double prevY = pp.z;
                        double desiredx = pp.x + 0.4 * Math.sin(e.rot);
                        double desiredy = pp.z + 0.4 * Math.cos(e.rot);
                        
                        // PULL TOWARDS THE CENTER OF THE BELT
                        double centerPullStrength = 0.05;  // tweak as needed
                        if(dist > 0.001){
                            desiredx += centerPullStrength * (e.x - pp.x) / dist;
                            desiredy += centerPullStrength * (e.z - pp.z) / dist;
                        }
                        
                        boolean successful = pasties.update(pp, prevX, prevY, desiredx, desiredy);
                        if(successful && (pp.getClass() == TextNodule.class)){
                            // IS THERE A TTS MACHINE NEARBY 
                            movedItems.add(pp);
                        }
                        if(pp instanceof Pasty){
                            var ppp = (Pasty) pp;
                            if(ppp.sprite != null){
                                ppp.sprite.repaunch = false;
                            }
                        }
                    }
                }
            }
        }
    }
    for(var mi: movedItems){
        // I'm assuming it's loaded but of course worth testing
        double rad = 20;
        var e = mi;
        pasties.query2D(e.x - rad, e.x + rad, e.z - rad, e.z + rad);
        for(int i = 0; i < pasties.size(); i++){
            GameObject pp = pasties.getAt(i);
            
            // if(pp instanceof TtsMachine){
            //     System.exit(0);
            // }

            if(pp instanceof TtsMachine){
                TextNodule tn = (TextNodule) mi;
                pasties.remove(tn);

                Runnable arbfunc = () ->{
                    final var s = tn.state.stringify(" ").replace("\"", "\\\"");
                    hellinacell(s, tn);
                };
                Thread t = new Thread(arbfunc);
                // pn.watchThread = t; // no promise nodule for this... at least I don't think so.
                t.start();
            };
            
        }
    }

}
// public static void hellinacell_2_text(){
//     String data = Chat.call(s);
//     TextNodule p = new TextNodule();
//     p.sizeMulti/=(1.5*1.5*1.5*1.5);
//     p.state = new State();
//     {
//         System.out.println("I have the data which is... " + data);
//         Scanner ss = new Scanner(data);
//         int i = 0;
//         while(ss.hasNextLine()){
//             if(i!= 0){
//                 p.state.enter(false);
//             }
//             i++;
//             var line = ss.nextLine();
//             int ii = 0;
//             while(ii < line.length()){
//                 System.out.println("about to extract..." + ii + " , " + (Math.min(ii+40, line.length())));
//                 String sub = line.substring(ii,Math.min(ii+40, line.length()));
//                 p.state.insert(sub);
//                 ii+=40;
//                 p.state.enter(false);
//             }
//         }
//         ss.close();
//     }
//     {
//         var x = pn.x;
//         var z = pn.z;
//         pasties.query2D(x-50, x+50, z-50, z+50);
//         boolean pastiesContains = false;
//         for(int i = 0; i < pasties.size(); i++){
//             GameObject pp = pasties.getAt(i);
//             if(pp == pn){
//                 pastiesContains = true;
//                 break;
//             }
//         }
//         if(pastiesContains){
//             System.out.println("()Decided to replace the pn");
//             System.out.println("pnx " + pn.x);
//             System.out.println("pnz " + pn.z);
//             System.out.println("pny " + pn.ydisp);
//             p.assumeApproximatePositionOf(pn);
//             System.out.println("px " + p.x);
//             System.out.println("pz " + p.z);
//             System.out.println("py " + p.ydisp);
//             PURGATORY.add(p);
//             System.out.println("added to purgatgory");
//         }else{
//             System.out.println("not in pasties, attempting bobbies!");
//             // System.exit(0);
//             BOBBIES_REPLACE.add(new Tuple<GameObject, GameObject>(pn,p));

//         }
//     }
//     DELETION_PURGATORY.add(pn);
// }
public static void hellinacell(String s, GameObject destinationModel){
    // Allowed voices list.
    List<String> allowedVoices = Arrays.asList("alloy", "ash", "coral", "echo", "fable", "onyx", "nova", "sage", "shimmer");
    
    // Randomly choose one voice for all chunks.
    Random random = new Random();
    String chosenVoice = allowedVoices.get(random.nextInt(allowedVoices.size()));
    
    // Generate a random speed between 0.9 and 1.1.
    double speed = 0.9 + random.nextDouble() * (1.1 - 0.9);
    speed = 1;
    // Split string s into chunks of at most 4096 characters.
    final int CHUNK_SIZE = 4096;
    List<String> chunks = new ArrayList<>();
    for (int start = 0; start < s.length(); start += CHUNK_SIZE) {
        int end = Math.min(s.length(), start + CHUNK_SIZE);
        chunks.add(s.substring(start, end));
    }
    
    // Process each chunk separately using TTS.
    // We'll save each chunk's output as a separate MP3 file.
    List<String> chunkFiles = new ArrayList<>();
    String baseTimestamp = String.valueOf(System.currentTimeMillis());
    
    for (int i = 0; i < chunks.size(); i++) {
        // Create a unique filename for this chunk; adding the chunk index helps uniqueness.
        String chunkFile = "_" + baseTimestamp + "_" + i + ".mp3";
        // Call your TTS engine on the chunk using the chosen voice and speed.
        TextToSpeech.call(chunks.get(i), chosenVoice, speed, chunkFile);
        chunkFiles.add("./temp/" + chunkFile);
    }
    
    // Optionally: Merge the individual MP3 files into one.
    // To do this, create a temporary file list for ffmpeg.
    try {
        File fileList = new File("./temp/chunks.txt");
        try (PrintWriter writer = new PrintWriter(fileList)) {
            for (String filename : chunkFiles) {
                // ffmpeg expects lines of the form: file 'path/to/file'
                writer.println("file '" + new File(filename).getAbsolutePath() + "'");
            }
        }
        
        // Merge the files using ffmpeg concat demuxer.
        String mergedFile = "merged_" + baseTimestamp + ".mp3";
        ProcessBuilder mergePb = new ProcessBuilder(
            "../../ffmpeg/ffmpeg.exe",
            "-f", "concat",
            "-safe", "0",
            "-i", fileList.getAbsolutePath(),
            "-c", "copy",
            "./temp/" + mergedFile
        );
        mergePb.redirectErrorStream(true);
        mergePb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process mergeProcess = mergePb.start();
        if (!mergeProcess.waitFor(180, TimeUnit.SECONDS)) {
            System.out.println("Merging process timeout. Terminating the process.");
            mergeProcess.destroy();
        }
        
        // Convert the merged MP3 file to WAV (if that is your final output).
        String myname2 = "processme" + System.currentTimeMillis() + ".wav";
        ProcessBuilder processBuilder = new ProcessBuilder(
            "../../ffmpeg/ffmpeg.exe",
            "-i", "./temp/" + mergedFile,
            "-vn",
            "-acodec", "pcm_s16le",
            "-ar", "44100",
            "-ac", "1",
            "./temp/" + myname2
        );
        processBuilder.redirectErrorStream(true);
        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        Process process = processBuilder.start();
        
        if (!process.waitFor(180, TimeUnit.SECONDS)) {
            System.out.println("Audio conversion process timeout. Terminating the process.");
            process.destroy();
        }
        
        // Move the final WAV file to your destination folder.
        // Path sourcePath = Paths.get("./temp/" + myname2);
        // Path destinationPath = Paths.get("./DumpFolder/processme.wav");
        // Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);

        FileNodule fn = new FileNodule();
        // byte[] fileContent = readFileToByteArray(toWatch.resolve(changedPath));
        fn.readInDontDelete("./temp/" + mergedFile);
        fn.x = destinationModel.x;
        fn.z = destinationModel.z;
        fn.ydisp = destinationModel.ydisp;
        PURGATORY.add(fn);

        
    } catch (Exception e) {
        e.printStackTrace();
    }
    
}

public static void threedfold(){
    File theFile = new File("ThreeDumbPollFolder\\3d");
    if(theFile.exists()){
        try{

            byte[] bytes = Files.readAllBytes(Paths.get("ThreeDumbPollFolder\\3d"));
            ByteBuffer b = ByteBuffer.wrap(bytes);
            ParkerBuffer pb = new ParkerBuffer(b);
            // while(b.hasRemaining()){
                ThreeDumb p = new ThreeDumb();
                p.displayMode = 2; // why was it 1?
                p.pointsCt = pb.getInt();
                p.subdivs = pb.getInt();
                int datamode = pb.getInt();
                BufferedImage bi = null;
                if(datamode == 1){
                    int width = pb.getInt();
                    int height = pb.getInt();
                    System.out.println("W"+width+","+height);
                    pb.getInt(); // burn this idk why


                    int[] texdat = pb.getIntArr();

                    p.sprite = new Spriter(texdat, width, height, p.sl(), p.mso());
                    p.sprite.repaunch = true;
                    p.data = texdat;
                    p.width = width;
                    p.height = height;
                    p.whrat = width*1.0/height;
                }
                pb.getInt(); // burn this

                {
                    //Optional SOs
                    int sosOption = pb.getInt();
                    if(sosOption == 1){
                        p.sos = pb.getIntArr();
                        System.out.println("Good!" + p.sos.length + " " + p.pointsCt);
                        // for(var e: p.sos){
                        //     // p("good! "+ e);
                        // }
                    }else{
                        p.sos = new int[p.pointsCt];
                    }
                }
                double values[] = pb.getDoubleArr();
                System.out.println("values ct + " + values.length);
                {
                    // try{
                    GameObject go = null;
                    p.values = values;
                    {
                        if(datamode == 0){
                            Transferable t = Main.clipboard.getContents(null);
                            Image i = (Image)t.getTransferData(DataFlavor.imageFlavor);
                            bi = imageToBufferedImage(i);
                            p.sprite = new Spriter(bi, p.sl(), p.mso());
                            p.sprite.repaunch = true;
                            p.width = bi.getWidth();
                            p.height = bi.getHeight();
                            p.data = p.sprite.tempIntDataForStorage;
                            // p.sprite.tempIntDataForStorage = null;
                            p.whrat = bi.getWidth()*1.0/bi.getHeight();
                        }
                    }
                    go = p;
                    go.ydisp = y;
                    go.x = x+3*Math.sin(neck)+Math.random()/10000;
                    go.z = z+3*Math.cos(neck);
                    go.rot = neck+Math.PI/2;
                    // pasties.add(go);
                    PURGATORY.add(go);
                    addStatement("ThreeDumb Successfully added");
                    // }catch(Exception e){
                    //     e.printStackTrace();
                    // }
                }

            // }
        }catch(Exception e){
            e.printStackTrace();
            addStatement("ThreeDumb import error");
        }

        theFile.delete();
    }
}

public static    ByteBuffer bbi = ByteBuffer.allocateDirect(3840*2160*4);
public static int g_cx;
public static int g_cy;
public static int g_lkp;
public static int hover_cx;
public static int hover_cy;
public static int hover_lkp;

public static int temporary_color_expirement = 0;
public static int mouse_inset_x;
public static int mouse_inset_y;
public static LinkedList<String> statements = new LinkedList<>();

public static LinkedList<Tuple<Long, PortalNodule>> walkthrough = new LinkedList<Tuple<Long, PortalNodule>>();
public static boolean walkthrough_begin = false;
public static long walkthrough_begin_stamp = 0;
public static boolean walkthrough_replay_on = false;
public static long walkthrough_replay_stamp = 0;
public static LinkedList<Tuple<Long, PortalNodule>> walkthrough_replay = new LinkedList<Tuple<Long, PortalNodule>>();


    static HashMap<String, Searcher> exacts = new HashMap<String, Searcher>();
static LinkedList<String> lastUsed = new LinkedList<String>();

static String firstOption = null;
static int fuzzycursorposition = 0;

public static void splitLongStrings(LinkedList<String> list, int x) {
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
            String current = iterator.next();
            if (current.length() > x) {
                String firstHalf = current.substring(0, x);
                String secondHalf = current.substring(x);
                iterator.set(firstHalf);
                iterator.add(secondHalf);
                // iterator = list.listIterator();
                iterator.previous();
            }
        }
    }

public static double protoZoomValue = 0;
public static double zoomScrollAdjustment = 0;
public static HashSet<Long> modifiedSectors = new HashSet<Long>();


public static int sside = 13; // sector side Note: should always be odd number

public static long statement_bump_stamp = 0;
public static void addStatement(String statement){
    statements.addFirst(statement);
    statement_bump_stamp = System.currentTimeMillis();
    if(statements.size() > 20){
        statements.removeLast();
    }
}
public static void addLog(String statement){
    statements.addFirst(statement);
    statement_bump_stamp = System.currentTimeMillis();
    if(statements.size() > 20){
        statements.removeLast();
    }
}
public static Pasty nannypasty = null;
public static long nannypasty_timing = 0;

public static LinkedList<GameObject> permamountains = new LinkedList<GameObject>();

public static ThreeDumb floor = null;


public static void addMountains(){
    {
        LinkedList<GameObject> gos = FileNodule.readInSerialDumpGetList(Main.resourcefolder+"mountains_serialdump_3");
        for(var go: gos){
            go.x = 1_000_000;
            go.z = 1_000_000;
            go.ydisp = -100; // it can be below...
            // PURGATORY.add(go);
        }
        permamountains = gos;
    }
    // {
    //     LinkedList<GameObject> gos = FileNodule.readInSerialDumpGetList(Main.resourcefolder+"mountains_small_2");
    //     for(var go: gos){
    //         go.x = 1_000_000;
    //         go.z = 1_000_000;
    //         go.ydisp = -99; // it can be below...
    //         // PURGATORY.add(go);
    //     }
    //     permamountains.addAll(gos);
    // }
    {
        LinkedList<GameObject> gos = FileNodule.readInSerialDumpGetList(Main.resourcefolder+"floorserialdump");
        for(var go: gos){
            floor = (ThreeDumb) go;
        }
    }
}

public static void demandFocusBackFromBrowser(){
    if(window != -1)
        glfwFocusWindow(window);
    // glfwSetWindowAttrib(window, GLFW_FOCUSED, GLFW_TRUE);
}

public static String SEARCHER_TOOLTIP = null;

public static boolean one_key_state_for_blinking = false;

public static boolean random_blinking = false;
public static boolean data_sizes_mode = false;
public static boolean grid_lock_mode = false;


public static double panvertical = 0;

public static boolean isPointInTriangle(double[] triangle, double px, double pz) {
    // Extract the triangle vertices
    double x1 = triangle[0], y1 = triangle[1], z1 = triangle[2];
    double x2 = triangle[3], y2 = triangle[4], z2 = triangle[5];
    double x3 = triangle[6], y3 = triangle[7], z3 = triangle[8];

    // We'll ignore y-values for the point-in-triangle check
    // and just use (x,z).

    // Vector v0 = v3 - v1  => (x3 - x1, z3 - z1)
    double v0x = x3 - x1;
    double v0z = z3 - z1;
    // Vector v1 = v2 - v1  => (x2 - x1, z2 - z1)
    double v1x = x2 - x1;
    double v1z = z2 - z1;
    // Vector v2 = p  - v1  => (px  - x1, pz  - z1)
    double v2x = px  - x1;
    double v2z = pz  - z1;

    // Dot products
    double dot00 = v0x*v0x + v0z*v0z;
    double dot01 = v0x*v1x + v0z*v1z;
    double dot02 = v0x*v2x + v0z*v2z;
    double dot11 = v1x*v1x + v1z*v1z;
    double dot12 = v1x*v2x + v1z*v2z;

    // Compute barycentric coordinates
    double invDenom = 1.0 / (dot00 * dot11 - dot01 * dot01);
    double u = (dot11 * dot02 - dot01 * dot12) * invDenom;
    double v = (dot00 * dot12 - dot01 * dot02) * invDenom;

    // Check if point is in triangle
    // (u >= 0, v >= 0, and u+v <= 1)
    return (u >= 0.0) && (v >= 0.0) && (u + v <= 1.0);
}
public static double computeSlope(double[] triangle) {
    // Extract the triangle vertices
    double x1 = triangle[0], y1 = triangle[1], z1 = triangle[2];
    double x2 = triangle[3], y2 = triangle[4], z2 = triangle[5];
    double x3 = triangle[6], y3 = triangle[7], z3 = triangle[8];

    // Edge e1 = v2 - v1
    double e1x = x2 - x1;
    double e1y = y2 - y1;
    double e1z = z2 - z1;

    // Edge e2 = v3 - v1
    double e2x = x3 - x1;
    double e2y = y3 - y1;
    double e2z = z3 - z1;

    // Normal = e1 x e2 = (A, B, C)
    double A = e1y * e2z - e1z * e2y;
    double B = e1z * e2x - e1x * e2z;
    double C = e1x * e2y - e1y * e2x;

    // If the triangle is nearly vertical, B ~ 0 => slope goes infinite
    // (you can handle that edge case however you prefer)
    if (Math.abs(B) < 1e-12) {
        // Could return something large, or handle differently
        return Double.POSITIVE_INFINITY;
    }

    // Slope (steepest) = sqrt( (∂y/∂x)^2 + (∂y/∂z)^2 )
    //                  = sqrt( A^2 + C^2 ) / |B|
    double slope = Math.sqrt(A * A + C * C) / Math.abs(B);
    return slope;
}
public static double getYOnTrianglePlane(double[] triangle, double px, double pz) {
    double x1 = triangle[0], y1 = triangle[1], z1 = triangle[2];
    double x2 = triangle[3], y2 = triangle[4], z2 = triangle[5];
    double x3 = triangle[6], y3 = triangle[7], z3 = triangle[8];

    // Edges
    double e1x = x2 - x1;
    double e1y = y2 - y1;
    double e1z = z2 - z1;

    double e2x = x3 - x1;
    double e2y = y3 - y1;
    double e2z = z3 - z1;

    // Normal = (A,B,C)
    double A = e1y * e2z - e1z * e2y;
    double B = e1z * e2x - e1x * e2z;
    double C = e1x * e2y - e1y * e2x;

    // Plane equation: A*x + B*y + C*z + D = 0
    // D = -(A*x1 + B*y1 + C*z1)
    double D = -(A * x1 + B * y1 + C * z1);

    // Solve for y at (px, pz):
    // A*px + B*y + C*pz + D = 0  =>  B*y = - (A*px + C*pz + D)
    // => y = -(A*px + C*pz + D) / B
    if (Math.abs(B) < 1e-12) {
        // Edge case: plane is almost vertical => can't solve for y in normal way
        return Double.NaN; // or handle differently
    }
    return -(A * px + C * pz + D) / B;
}
static double[] debugTriangle = new double[9];
static double[] debugTriangle_EMPTY = new double[9];

static Set<BurningBush> bbsetlast = new HashSet<BurningBush>();


static int last_frame_nlx7 = (int)(x/128);
static int last_frame_nlz7 = (int)(z/128);

public static Crux lookForCruxInSquare(double x, double z, double side){
    pasties.query2D(x-side, x+side, z-side, z+side);
    Crux find = null;
    for(int i = 0; i < pasties.size(); i++){
        GameObject go = pasties.getAt(i);
        if(go instanceof Crux){
            return (Crux)go;
        }
    }
    return null;
}
public static boolean isSearcherInSquare(String str, double x, double z, double side){
    pasties.query2D(x-side, x+side, z-side, z+side);
    Crux find = null;
    for(int i = 0; i < pasties.size(); i++){
        GameObject go = pasties.getAt(i);
        if(go instanceof Searcher){
            var searcher = ((Searcher)go);
            addStatement("SEARCHER FOUND with word "+ searcher.getWord());
            if(str.equals(searcher.getWord()))
                return true;
        }
    }
    return false;
}

public static void do_loading_logic(){

    while(!pleaseloadme.isEmpty()){
        Object[] o = pleaseloadme.remove(0);
        String s = (String)o[0];
        Boolean shoulddelete = (Boolean)o[1];
        // System.out.println("discharging pleaseloadme");
        // p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
        // String s = file.getAbsolutePath();
            if(s.endsWith("serialdump")){
                addStatement("here in one serialdump path");
                FileNodule.readInSerialDump(s);
            }else if(s.endsWith("processme.wav")){
                try{
                    Path path = Paths.get(s);
                    byte[] a = Files.readAllBytes(path);
                    // do it
                    File f = new File(s);
                    File file = new File(s);
                    file.delete();
                    byte[] aa = a;
                    {
                        ByteBuffer bb = SimpleTri.main(null);
                        bb.flip();
                        GameObject go = null;
                        {
                            try{
                                ParkerBuffer pb = new ParkerBuffer(bb);
                                ThreeDumb p = new ThreeDumb();
                                p.pointsCt = pb.getInt();
                                p.subdivs = pb.getInt();
                                int datamode = pb.getInt();
                                System.out.println("`ED DATA MODE IS " + datamode);
                                BufferedImage bi = null;
                                if(datamode == 1){
                                    int width = pb.getInt();
                                    int height = pb.getInt();
                                    pb.getInt();
                                    int[] texdat = pb.getIntArr();
                                    p.sprite = new Spriter(texdat, width, height, p.sl(), p.mso());
                                    p.sprite.repaunch = true;
                                    p.data = texdat;
                                    p.width = width;
                                    p.height = height;
                                    p.whrat = width*1.0/height;
                                }
                                pb.getInt();
                                {
                                    int sosOption = pb.getInt();
                                    if(sosOption == 1){
                                        p.sos = pb.getIntArr();
                                    }else{
                                        p.sos = new int[p.pointsCt];
                                    }
                                }
                                double values[] = pb.getDoubleArr();
                                {
                                    p.values = values;
                                    System.out.println("WTF?? "  +aa.length);
                                    p = p.promote(aa);
                                    p.displayMode = 2; // way to control only new imports
                                    ((BurningBush)p).cycleAsProportion = 2;
                                    ((BurningBush)p).offsetAsProportion = 2;
                                    go = p;
                                    go.x = x;
                                    go.z = z;
                                    go.ydisp = dispy+y;
                                    go.rot = neck+Math.PI/2;
                                    // if(!lock())
                                    //     pasties.add(go);
                                    ((Pasty)go).sprite.repaunch = false;
                                    intoInventory(go);
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                var fn = FileNodule.readIn(s, shoulddelete);
                if(fn != null){
                    Main.intoInventory(fn); // please god work
                }
            }
    
        // p.load();
    }
}

public static void ttsdraw(GameObject go, int mode, ColorGroup b, Spriter fightSpriter, ColorGroup micColors){

    if(go instanceof TtsMachine){
        TtsMachine p = (TtsMachine)go;
        for(int i = 0; i < 4; i++){
            double px = p.x-1_000_000;
            double pz = p.z-1_000_000;
            double py = p.ydisp+5/1;
            if(i == 1 || i == 2){
                px -= 2*Math.sin(p.rot);
                pz -= 2*Math.cos(p.rot);
            }else{
                px += 2*Math.sin(p.rot);
                pz += 2*Math.cos(p.rot);
            }
            px += 0.5*Math.sin(p.rot-Math.PI/2);
            pz += 0.5*Math.cos(p.rot-Math.PI/2);

            if(i == 2 || i==3){
                py+=-6;
            }
            // RGB r1 = RGB.fromCombined(p.c1);
            // RGB r2 = RGB.fromCombined(p.c2);
            // int so = p.style == 1 ? 39 : 22;
            // so = p.style == 2 ? 45 : so;
            // ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
            fightSpriter.drawPoint(60, px, py, pz,  1, b, b, mode);
        }
    }
    
        if(go instanceof PortalNodule){
            PortalNodule p = (PortalNodule)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double py = p.ydisp+5/1;
                if(i == 1 || i == 2){
                    px -= 2*Math.sin(p.rot);
                    pz -= 2*Math.cos(p.rot);
                }else{
                    px += 2*Math.sin(p.rot);
                    pz += 2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-6;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                int so = p.style == 1 ? 39 : 22;
                so = p.style == 2 ? 45 : so;
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                
                if(mode != 8 && neededGameObjects.contains(go)){
                    mode = 99;
                }
                fightSpriter.drawPoint(so, px, py, pz,  1, micColors, replacementC, mode);
            }
        }
        if(go instanceof Conveyor || go instanceof PressurePlate){
            // Conveyor p = (Conveyor) go;
            var p = go;
            // Define the center point (adjusted as before)
            double centerX = p.x - 1_000_000;
            double centerZ = p.z - 1_000_000;
            double py = p.ydisp;
            double radius = Conveyor.rad; // distance from center
        
            // Loop 4 times, one for each point.
            for (int i = 0; i < 4; i++){
                // Choose the direction:
                // For counterclockwise, use a plus:
                double angle = p.rot + i * (Math.PI / 2) + (Math.PI / 4);
                // For clockwise, use a minus:
                // double angle = p.rot - i * (Math.PI / 2);
                
                // Compute positions based on the angle.
                double px = centerX + radius * Math.sin(angle);
                double pz = centerZ + radius * Math.cos(angle);
                
                int so = 45 + 16 - 2;
                if(go instanceof PressurePlate){
                    so = 23;
                    var pp = (PressurePlate) go;
                    RGB r1 = RGB.fromCombined(pp.c1);
                    RGB r2 = RGB.fromCombined(pp.c2);
                    fightSpriter.drawPoint(so, px, py, pz, 1, ColorGroup.red, new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b), mode);
                }else{
                    fightSpriter.drawPoint(so, px, py, pz, 1, b, b, mode);
                }
            }
        }
}

public static void gridOperation(String searcherString){
    
    addStatement("grid lock search begun for word " + searcherString);
    Crux crux = null;
    {
        int rendx = (int)(x/128)*128+64;
        int rendz = (int)(z/128)*128+64;
        crux  = lookForCruxInSquare(rendx, rendz, 0.1);
    }
    LinkedList<Crux> nextNeighbors = new LinkedList<Crux>();
    HashSet<Crux> visited = new HashSet<Crux>();
    HashSet<Crux> visitedLeafs = new HashSet<Crux>();
    if(crux != null){
        addStatement("crux found in player square");
        visited.add(crux);
        crux.visitor = null;
    }
    while(crux != null){
        // check this square for the 
        if(searcherString != null){
            if(isSearcherInSquare(searcherString, crux.x, crux.z, 64)){
                addStatement("search word is found; navigating to " + searcherString );
                break; // GO HERE
            }
        }
        addStatement("inloop " + crux.upx + " "+ crux.downx + " "+ crux.upz + " "+ crux.downz );

        if(crux.upx + crux.downx + crux.upz + crux.downz == 1){
            visitedLeafs.add(crux);
        }

        if(crux.upx != 0){
            {
                int rendx = (int)(crux.x/128)*128+64 + 128;
                int rendz = (int)(crux.z/128)*128+64 + 0  ;
                var c = lookForCruxInSquare(rendx, rendz, 0.1);
                if(c != null && !visited.contains(c)){
                    addStatement("eastward found");
                    nextNeighbors.addLast(c);
                    visited.add(c);
                    c.visitor = crux;
                    
                }
            }
        }
        if(crux.downx != 0){
            {

                int rendx = (int)(crux.x/128)*128+64 - 128;
                int rendz = (int)(crux.z/128)*128+64 + 0  ;
                var c = lookForCruxInSquare(rendx, rendz, 0.1);
                if(c != null && !visited.contains(c)){
                    addStatement("westward found");
                    nextNeighbors.addLast(c);
                    visited.add(c);
                    c.visitor = crux;

                }
            }
        }
        if(crux.upz != 0){
            {

                int rendx = (int)(crux.x/128)*128+64 + 0 ;
                int rendz = (int)(crux.z/128)*128+64 + 128  ;
                var c = lookForCruxInSquare(rendx, rendz, 0.1);
                if(c != null && !visited.contains(c)){
                    addStatement("northward found");
                    nextNeighbors.addLast(c);
                    visited.add(c);
                    c.visitor = crux;

                }
            }
        }
        if(crux.downz != 0){
            {

                int rendx = (int)(crux.x/128)*128+64 + 0;
                int rendz = (int)(crux.z/128)*128+64 + -128  ;
                var c = lookForCruxInSquare(rendx, rendz, 0.1);
                if(c != null && !visited.contains(c)){
                    addStatement("southward found");
                    nextNeighbors.addLast(c);
                    visited.add(c);
                    c.visitor = crux;

                }
            }
        }
        if(!nextNeighbors.isEmpty())
            crux = nextNeighbors.removeFirst();
        else{
            if(searcherString != null){
                addStatement("route not found; is it on grid? " + searcherString);
                return;
            }
            // crux = null;
            break;
        }
    }

    // test going to the last used crux

    addStatement("bfs network completed; size determined to be " + visited.size());
    if(crux != null){
        if(Input.simulatedShiftEnabled || automatic_random_mode){
            // is random
            List<Crux> list = new ArrayList<>(visitedLeafs);
            Crux randomElement = list.get(new Random().nextInt(list.size()));
            crux = randomElement;
        }
        // test the backtracking
        LinkedList<Crux> nav = new LinkedList<Crux>();
        nav.add(crux);
        while(crux.visitor != null){
            nav.add(crux.visitor);
            crux = crux.visitor;
        }
        addStatement("nav built with size " + nav.size());
        globalNav = nav;
    }
                
}
//truemain

public static LinkedList<Crux> globalNav = new LinkedList<Crux>();
public static Crux throwaway_last = null;
public static long global_nav_millis_cooldown = -1;


public static long global_nav_timer_milis = 100;

public static boolean automatic_random_mode = false;
// Helper function to interpolate between two angles along the shortest path
private static double interpolateAngle(double start, double end, double fraction) {
    double diff = end - start;

    // Find the shortest path difference [-π, π]
    // Using <= for robustness, although unlikely diff will be exactly -PI
    while (diff <= -Math.PI) {
        diff += 2 * Math.PI;
    }
    while (diff > Math.PI) {
        diff -= 2 * Math.PI;
    }

    // Calculate the interpolated angle along the shortest path
    double interpolated = start + diff * fraction;

    // Normalize the result to the range [0, 2π)
    // The double modulo operation handles potential negative results from Java's % operator
    double normalizedOutput = (interpolated % (2 * Math.PI) + 2 * Math.PI) % (2 * Math.PI);

    return normalizedOutput;
}
public static void main(String[] args) {
// ParkerSetter.main(null);


try{
CopyOnWriteArrayList<Long> pleasenukeme = new CopyOnWriteArrayList<>();

try{
    
    if((new File(".\\Latest.txt")).exists()){
        var s = new Scanner(new File(".\\Latest.txt"));
        var c = Long.parseLong(s.nextLine()); // last use
        var d = Long.parseLong(s.nextLine()); // playtime
        var e = Boolean.parseBoolean(s.nextLine()); // backup
        s.close();
        if(e){
            IS_FLAGGED_AS_BACKUP = true;
        }
    }
}catch(Exception e){
    System.out.println("Unable to access latest file, no big deal");
    e.printStackTrace();
}


// defaultColorFunction = (int[] a, double[] b) -> {
    // double ithinkheight = b[0];
    // int ii = a[0];
    // int jj = a[1];
    // int c = swaths[9].getColorFromCompletedMap((int)ii-1_000_000,(int)jj-1_000_000);
    // if(ithinkheight <= -29){
    //     return new int[]{darken(r(c)), darken(g(c)), darken(b(c)), 29,0,0,0};

    // }
    // return new int[]{r(c), g(c), b(c), 29,0,0,0};
    // return new int[]{};
// };
// System.out.println("AT LEAST AM HERE NIQQA " + args[4] + " ");
resourcefolder = args[4]; // expediting it

startup2();
startup3();
startup();

System.out.println("YO YO YO ");

long device = ALC10.alcOpenDevice((ByteBuffer) null);
ALCCapabilities deviceCaps = ALC.createCapabilities(device);

long context = ALC10.alcCreateContext(device, (IntBuffer) null);
ALC10.alcMakeContextCurrent(context);
AL.createCapabilities(deviceCaps);
AL10.alDistanceModel(AL11.AL_EXPONENT_DISTANCE);
float volume = 10.0f;
AL10.alListenerf(AL10.AL_GAIN, volume);
// AL10.alListenerf(AL10.AL_GAIN, 0f);



{
    File theDir = new File("./"+Main.folder);
    if (!theDir.exists()){
        theDir.mkdirs();
    }
}


if(APOSTROPHE_MODE)
    p("APOSTROPHE_MODE ON");
if(args.length > 0){
    PARKER_BUFFER_SIZE = Integer.parseInt(args[0]);
    folder = args[1];
    fname = "./"+folder+"/parkerspace_data_t.save";
    if(args.length >= 3){
        sleep_time_override = Integer.parseInt(args[2]);
    }if(args.length >= 4){
        r = new Random(Integer.parseInt(args[3]));
        Vor.r = new Random(3245+Integer.parseInt(args[3]));
        Vor.init();
        seed = Integer.parseInt(args[3]);

        C1 = r.nextLong();
        C2 = r.nextLong();

    }
    // if(args.length >= 5){
    //     int should_flip = Integer.parseInt(args[4]);
    //     if(should_flip == 1){
    //         DEV_NO_SAVE_MODE = true;
    //     }
    // }
    if(args.length >= 5){
        resourcefolder = args[4];
    }
    if(args.length >= 6){
        pastcum = Long.parseLong(args[5]);
    }
    if(args.length >= 7){
        shouldmakebackup = Boolean.parseBoolean(args[6]);
    }
} 

// Swath.override_color_a = 0xff_00_00_00;
// Swath.override_color_b = 0xff_00_ff_00;
// please dear god
for(int i = 0; i < swaths.length; i++){
    // swaths[i] = new Swath(i);
    swaths[i] = new Swath(i+451*seed);
}

for(int i = 0; i < 10; i++){
    parkerBufferBuffers.add(
        new Tuple<ByteBuffer,GameObject[]>(
            ByteBuffer.allocate(PARKER_BUFFER_SIZE)
            ,new GameObject[1_000_000]));
}
try{

}catch(Exception e){}
boolean running = true;

BufferedImage previouslySeen = null;
boolean insertionMode = false;
Clip clip = null;
try{
   clip = AudioSystem.getClip();
}catch(Exception e){
    e.printStackTrace();;
}



AudioFormat format = new AudioFormat(44100f, 2*8, 1, true, false);
Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);


// glfwWindowHint(GLFW_MAXIMIZED, GL_TRUE);
// glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
glfwSetWindowPos(window, xx, yy);
var in = new Input();

glfwSetKeyCallback(window, in);
glfwSetKeyCallback(topWindow, in);
var mouseInput = in.new InputMouse();


GLFW.glfwSetScrollCallback(window, new GLFWScrollCallback() {
    public void invoke (long win, double dx, double dy) {
        // System.out.println(dy);
        mouseWheelVelocity = (float) dy;
        if(Main.poppedPasty != null){
            poppedPastySize = (float)(((poppedPastySize+dy)+20)%20);

        }else{
            mouseWheelRotation = (float)(((mouseWheelRotation+dy)+1000)%1000);
        }
    }
});
glfwSetMouseButtonCallback(window, mouseInput);
glfwSetMouseButtonCallback(topWindow, mouseInput);
glDebugMessageCallback(new GLDebugMessageCallback() {
    // @Override
    public void invoke(int source, int type, int id, int severity, int length, long message, long userParam) {
        System.err.println("OpenGL Error Message: " + getMessage(length, message));
    }
}, 0);
if(!FULL_SCREEN_MODE_IS_ON)
    glfwSetWindowSizeCallback(window, new ResizeFunction()); // temporarily commented
glfwSetWindowFocusCallback(window, new FocusFunction());



try{
    BufferedImage bi = ImageIO.read(new File(Main.resourcefolder + "mountain.bmp"));
    ByteBuffer bb = ByteBuffer.allocateDirect(bi.getWidth()*bi.getHeight()*4);
    int[] pixels = bi.getRGB(0, 0, bi.getWidth(), bi.getHeight(), null, 0, bi.getWidth());
    for (int y = 0; y < bi.getHeight(); y++) {
        for (int x = 0; x < bi.getWidth(); x++) {
            int pixel = pixels[y * bi.getWidth() + x];
            bb.put((byte) minzed((pixel >> 16) & 0xFF)); // Red component
            bb.put((byte) minzed((pixel >> 8) & 0xFF)); // Green component
            bb.put((byte) minzed((pixel & 0xFF))); // Blue component
            bb.put((byte)((pixel >> 24) & 0xFF)); // A
        }
    }
    bb.flip();
    GLFWImage image = GLFWImage.malloc();
    GLFWImage.Buffer buffer = GLFWImage.malloc(1);
    image.set(bi.getWidth(), bi.getHeight(), bb);
    buffer.put(0, image);
    glfwSetWindowIcon(window, buffer);
    image.free();
    buffer.free();
}catch(Exception e){e.printStackTrace();}

glEnable(GL_BLEND);
glEnable(GL_DEPTH_TEST);
glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
glDepthFunc(GL_LEQUAL);

{

}

// p(glfwSetCursor)

long last = System.nanoTime()  ; // kick it off immediately

int ticks = 0;
int lastticks = 0;
double gridx = 4;
double gridy = 4;
double tweenstate = 0;
double offsetx = 0;
double offsety = 0;
double tweenduration = 0.4;


Spriter textSheet = new Spriter(100_000, "textsheet.bmp", 16); // called once per program
textSheet.repaunch = true;
Spriter textSheet3 = new Spriter(5000, "textsheet.bmp", 16); // called once per program
textSheet3.repaunch = true;
Spriter textSheet2 = new Spriter(5000, "textsheet.bmp", 16); // called once per program
Spriter doomguysheet = new Spriter(1, "Untitled.png", 1); // called once per program
Spriter doomguysheet2 = new Spriter(1, "Untitled2.png", 1); // called once per program
// doomguysheet.repaunch = true;

Spriter fightSpriter = new Spriter(10000,"./Fighters.bmp",8);
scapeSpriter.repaunch = true;
Spriter slimeSpriter = new Spriter(6*100,"./gohere.png",1);
Spriter slimeText = new Spriter(20000, "textsheet.bmp", 16); // called once per program

Spriter swapSpriter = new Spriter(100_000,"./Fighters.bmp",8);
swapSpriter.repaunch = true;
Spriter cubemapSpriter = new Spriter(6,"cubemap10.bmp",4);
// cubemapSpriter.repaunch = true;

// Spriter inversionMaskTest = new Spriter(8,"mask_test.png",1);

// Spriter.setLateTexture(inversionMaskTest);
Spriter.clearLateTexture();
LinkedList<Future> crappies = new LinkedList<Future>();
Spriter[] cubemapSpriters = new Spriter[]{
    new Spriter(6,"cubemap.bmp",4),
    new Spriter(6,"cubemap2.bmp",4),
    new Spriter(6,"cubemap3.bmp",4),
    new Spriter(6,"cubemap4.bmp",4),
    new Spriter(6,"cubemap5.bmp",4),
    new Spriter(6,"cubemap6.bmp",4),
    new Spriter(6,"cubemap7.bmp",4),
    new Spriter(6,"cubemap8.bmp",4),
    new Spriter(6,"cubemap9.bmp",4),
    new Spriter(6,"cubemap10.bmp",4),
};
for(int i = 0; i < cubemapSpriters.length; i++){
    cubemapSpriters[i].repaunch = false;
    cubemapSpriters[i].clearRepaunch();
}
// cubemapSpriters[0].repaunch = true;
// cubemapSpriters[1].repaunch = true;
// cubemapSpriters[2].repaunch = true;
// cubemapSpriters[3].repaunch = true;
// cubemapSpriters[4].repaunch = true;
// cubemapSpriters[5].repaunch = true;
// cubemapSpriters[6].repaunch = true;
// cubemapSpriters[7].repaunch = true;
// cubemapSpriters[8].repaunch = true;
// cubemapSpriters[9].repaunch = true;

Spriter mountainTemp = new Spriter(1, "mountain.bmp", 1); // called once per program
mountainTemp.cutout = 0.9f;


// class Tersec{
//     Spriter s;
//     int x;
//     int z;
// }
// Tersec[] tersecs = new Tersec[400];
// for(int i = 0; i < 400; i++){
//     tersecs[i] = new Tersec();
//     tersecs[i].s = new Spriter(5000,"./VirtualFolder/Fighters.bmp",8);
// }
int resolution1 = 0;

int intelapsed = 0;

HashMap<Long, Spriter> hashmap = new HashMap<Long, Spriter>();

clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();



Thread dedicatedRecordingThread = new Thread(new DedicatedRecordingRunnable());
dedicatedRecordingThread.start();

// Thread dedicatedMobileUploader = new Thread(new MobileUploader());
// dedicatedMobileUploader.start();

HashMap<Long, Tile> tmap = new HashMap<Long, Tile>();
Tile[][] tmap2 = new Tile[4000][4000]; //relative coords 200,200 is self

ParkerQuizItem lastNearestPQI = null;
glfwSetCursorPos(window,400,400);

int tabselection = -1;
double size = 0.07;

if(Main.DEV_NO_SAVE_MODE){
    // sectorFunctions.clear();
    // overrides.clear();
    for(File file: new File("./"+Main.folder).listFiles()) 
        if (!file.isDirectory()) 
            file.delete();
    // var kn = new KeyNodule();
    // kn.ydisp = -25;
    // kn.x = x+12*Math.sin(neck);
    // kn.z = z+12*Math.cos(neck);
    // p("YO YO YO");
}
Vor.r = new Random(3245+Integer.parseInt(args[3]));
// try{
// System.out.println("central unmarshalling...");
unmarshal();
Main.deleteFolderContents(new File("temp"));
// System.out.println("uuuhhhh so this is? " + bobbies.size());
// System.out.println("bsel " + bobbyselection);

loadValuesFromFirstGigaStack();
// System.out.println("bsel " + bobbyselection);
// System.out.println("uuuhhhh so this is? " + bobbies.size());

{

    System.out.println("setting temporary context context to... " + TEMPORARY_CONTEXT_CONTEXT);
    tabselection = -1;
    bobbyselection = TEMPORARY_CONTEXT_CONTEXT;
    bobbyselection = 0; // parker late 1/14/2024 override
    bobbies = invStore[bobbyselection];
    // if(bobbies.size() >= 1){
    //     tabselection = 0;
    // } 
}
{
    for(var idk: invStore){
        System.out.println("this is what i care about " + bobbies.size());
    }
}
// if(false && pasties.stream().filter(x->x instanceof KeyNodule).count() == 0){
//     var kn = new KeyNodule();
//     kn.ydisp = -25;
//     kn.x = x+12*Math.sin(neck);
//     kn.z = z+12*Math.cos(neck);
//     pasties.add(kn);
// }
{
    poc_sfx = (bbExNihilo(resourcefolder + "/output.wav"));
    plop_sfx = (bbExNihilo(resourcefolder + "/plop.wav"));
    poc_sfx_warp = (bbExNihilo(resourcefolder + "/quicker_warp_sound_effect.wav"));
    sfx_success = (bbExNihilo(resourcefolder + "/interesting_success.wav"));
    sfx_failure = (bbExNihilo(resourcefolder + "/interesting_failure.wav"));
}
{
    // UUUHHHH, I'm lazy,
    // if pasties.size() == 0
    // it's tacky but it works
    addMountains();
    if(false && pasties.isEmpty()){
        pasties.queryAll();

        // {
        //     LinkedList<GameObject> gos = FileNodule.readInSerialDumpGetList(Main.resourcefolder+"_abcs");
        //     var ii = gos.iterator();
        //     int i = 0;
        //     while(ii.hasNext()){
        //         var go = ii.next();
        //         intoInventory(go);
        //     }
        // }
        // {
        //     // bobbies.addFirst(bbExNihilo(resourcefolder + "/output.wav"));
        //     poc_sfx = (bbExNihilo(resourcefolder + "/output.wav"));
        // }
        {
            LinkedList<GameObject> gos = FileNodule.readInSerialDumpGetList(Main.resourcefolder+"_beginner");
            // LinkedList<GameObject> gos = new LinkedList<GameObject>();//FileNodule.readInSerialDumpGetList(Main.resourcefolder+"_beginner");
            // Collections.reverse(gos);
            var funnel = new FunnelNodule();
            {
                
                int i = 0;
                while(Math.PI/180*50/8*i < Math.PI*2){
                    var go = new PhantomNodule();
                    System.out.println(go.getClass());
                    double g = Math.PI/180*50/8*i;
                    go.x = x+180*Math.sin(neck+g);
                    go.z = z+180*Math.cos(neck+g);
                    go.rot = neck+Math.PI/2+g;   
                    go.ydisp = 155;
                    funnel.phantoms.add(go);
                    // pasties.add(go);
                    i++;
                }
            }
            gos.addFirst(funnel);
            

            var ii = gos.iterator();
            int i = 0;
            while(ii.hasNext()){
                
                var go = ii.next();
                System.out.println(go.getClass());
                if(go instanceof BurningBush){
                    var bb = (BurningBush)go;
                    bb.load();
                }
                double g = Math.PI/8*i;
                go.x = x+50*Math.sin(neck+g);
                go.z = z+50*Math.cos(neck+g);
                go.rot = neck+Math.PI/2+g;   
                go.ydisp = 155;
                pasties.add(go);
                i++;
            }
        }
        {
            LinkedList<GameObject> gos = FileNodule.readInSerialDumpGetList(Main.resourcefolder+"_medium");
            var ii = gos.iterator();
            Collections.reverse(gos);
            int i = 0;
            while(ii.hasNext()){
                
                var go = ii.next();
                System.out.println(go.getClass());
                double g = Math.PI/16*(i + 12);
                go.x = x+100*Math.sin(neck+g);
                go.z = z+100*Math.cos(neck+g);
                go.rot = neck+Math.PI/2+g;   
                go.ydisp = 155;
                pasties.add(go);
                i++;
            }
        }
        {
            LinkedList<GameObject> gos = FileNodule.readInSerialDumpGetList(Main.resourcefolder+"_advanced");
            var ii = gos.iterator();
            Collections.reverse(gos);
            int i = 0;
            while(ii.hasNext()){
                
                var go = ii.next();
                System.out.println(go.getClass());
                double g = Math.PI/24*(i+12);
                go.x = x+150*Math.sin(neck+g);
                go.z = z+150*Math.cos(neck+g);
                go.rot = neck+Math.PI/2+g;   
                go.ydisp = 155;
                pasties.add(go);
                i++;
            }
        }
        {
            LinkedList<GameObject> gos = FileNodule.readInSerialDumpGetList(Main.resourcefolder+"spawnSerialDump");
            var ii = gos.iterator();
            Collections.reverse(gos);
            int i = 0;
            while(ii.hasNext()){
                
                var go = ii.next();
                System.out.println(go.getClass());
                double g = Math.PI/180*50/8*(i-10);
                go.x = x+180*Math.sin(neck+g);
                go.z = z+180*Math.cos(neck+g);
                go.rot = neck+Math.PI/2+g;   
                go.ydisp = 155;
                pasties.add(go);
                i++;
            }
        }
        // {
        //     var p = new Pasty();
        //     try{
        //         var bi = ImageExpirements.pngToBI(Main.resourcefolder + "dynamic_img_1915149393.png");
        //         p.sprite = new Spriter(bi, p.sl(), p.mso());
        //         p.width = bi.getWidth();
        //         p.height = bi.getHeight();
        //         p.data = p.sprite.tempIntDataForStorage;
        //         p.x = x+12*Math.sin(neck);
        //         p.z = z+12*Math.cos(neck);
        //         p.rot = neck+Math.PI/2;
        //         p.whrat = bi.getWidth()*1.0/bi.getHeight();

        //         p.ydisp = 155;
        //         p.load();
        //         pasties.add(p);
        //         // System.exit(0);
        //     }catch(Exception e){
        //         e.printStackTrace();
        //     }
        // }
        // {
        //     var p = new Pasty();
        //     try{
        //         var bi = ImageExpirements.pngToBI(Main.resourcefolder + "dynamic_img_1112664213.png");
        //         p.sprite = new Spriter(bi, p.sl(), p.mso());
        //         p.width = bi.getWidth();
        //         p.height = bi.getHeight();
        //         p.data = p.sprite.tempIntDataForStorage;
        //         p.x = x+12*Math.sin(neck+Math.PI/4);
        //         p.z = z+12*Math.cos(neck+Math.PI/4);
        //         p.rot = neck+Math.PI/2+Math.PI/4;
        //         p.whrat = bi.getWidth()*1.0/bi.getHeight();

        //         p.ydisp = 155;
        //         p.load();
        //         pasties.add(p);
        //         // System.exit(0);
        //     }catch(Exception e){
        //         e.printStackTrace();
        //     }
        // }
    }

}
originx = x;
originz = z;

boolean cursorFlicker = true;
boolean chordOn = false;

long lastClock = System.nanoTime();
LinkedList<Double> fpsAgr = new LinkedList<Double>();

long ARBITRARY_TRACKER_A= 0;
long ARBITRARY_TRACKER_B= 0;


String lastCmd = null;
LinkedList<Spriter> rememberToStep = new LinkedList<Spriter>(); // a dangerous game mein friend

long idk2 = System.currentTimeMillis();
long highwatermark = 0;
boolean triggerPrint = false;
boolean expired_death = false;
int frames_since_last = 0;

DragonDrop.main(null);
{
    DragonDrop.processSettings(settings);
}
{
    File theDir = new File("./"+folder);
    if (!theDir.exists()){
        theDir.mkdirs();
    }
}


HashMap<WatchService, Tuple<LinkedList<String>, Path>> services =
     new HashMap<WatchService, Tuple<LinkedList<String>, Path>>();
{
    try {
        Path toWatch = Paths.get("./VirtualFolder");
        WatchService watchService = null;
        watchService = FileSystems.getDefault().newWatchService();
        // Register both modification and creation events
        toWatch.register(watchService,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.OVERFLOW,
            StandardWatchEventKinds.ENTRY_DELETE,
            StandardWatchEventKinds.ENTRY_CREATE);
        var tempy = new LinkedList<String>();
        tempy.add("VirtualFolder");
        services.put(watchService, new Tuple<LinkedList<String>, Path>(tempy, toWatch));
    } catch (IOException e) {
        e.printStackTrace();
    }
}
System.out.println("begin running");
long last_full_save = System.currentTimeMillis();
boolean f6pressed = false;

HashMap<Long, Object[]> FUCKMELOOKUP = new HashMap<Long, Object[]>(); 

ClipboardMonitor.startMonitoring();


// this shit will work?
//  glfwMaximizeWindow(window);

long playtime_threshold_tracker = pastcum  + ((System.currentTimeMillis() - startM));

MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
MemoryUsage before = memoryMXBean.getHeapMemoryUsage();
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
int[] fps_timings = new int[100];
long[][] nano_timings = new long[100][100];
long previousTiming = System.currentTimeMillis();

int timingsIndex = 0;

        int[] gathermeindex_pumpjack = new int[1];
        GameObject[] gatherme_pumpjack = new GameObject[1000];
while(running || outerLock){ //* WHILE LINE WHILE LINE WHILE LINE WHILE LINE WHILE LINE WHILE LINE WHILE LINE WHILE LINE */

neededNow.clear(); // duh

conveyor_heartbeat();
// demandFocusBackFromBrowser();
{
    if(walkthrough_begin){
        PortalNodule pn = new PortalNodule();
        pn.chin = chin;
        pn.nangle = neck;
        pn.destx = x;
        pn.destz = z;
        pn.chary = dispy;
        var idk = System.currentTimeMillis() - walkthrough_begin_stamp;
        var tup = new Tuple<Long, PortalNodule>(idk, pn);
        walkthrough.addLast(tup);
        System.out.println("adding");
    }else{
        if(walkthrough_replay_on){
            var now = System.currentTimeMillis();
            
            Tuple<Long, PortalNodule> slot = null;
            while(!walkthrough_replay.isEmpty()){
                var k = walkthrough_replay.removeFirst();
                // System.out.println("removed " +  now+ " " + (k.x) + " " + (-now + k.x + walkthrough_replay_stamp));
                if(now < k.x + walkthrough_replay_stamp){
                    if(slot != null){
                        // engage/activate
                        // System.out.println("Activating!");
                        slot.y.noiselessActivateNO_HEAD();
                    }
                    // System.out.println("add");

                    walkthrough_replay.addFirst(k);
                    break;
                }
                slot = k;
            }
        }
    }
}
// System.out.println("yeah here");
{
    long now = System.currentTimeMillis();
    // System.out.println("timing " + (now - previousTiming));
    fps_timings[timingsIndex] = (int) (now - previousTiming);
    try{
        while(fps_timings[timingsIndex] < 16){
            Thread.sleep(1);
            now = System.currentTimeMillis();
            fps_timings[timingsIndex] = (int) (now - previousTiming);

        }
    }catch(Exception e){e.printStackTrace();}
    timingsIndex++; // need to be consistent for the whole cycle
    if(timingsIndex == 100) timingsIndex = 0;
    previousTiming = now;
}
nano_timings[timingsIndex][1] = System.currentTimeMillis();
nano_timings[timingsIndex][4] = System.currentTimeMillis();

{

    var i = BOBBIES_REPLACE.iterator();
    while(i.hasNext()){
        System.out.println("BOB REP 2");
        System.out.println( 
                    "Current Thread ID (): "
                    + Thread.currentThread().getId());
        var tuple = i.next();
        var a = tuple.x;
        var b = tuple.y;
        
        if(bobbies.contains(a)){
            int j = bobbies.indexOf(a);
            bobbies.set(j, b);
            b.load();
        }
        i.remove();
    }
}

if(cycling_remote_mode){
    boolean holdingRemote = (bobbies.size() >= 1) && bobbies.get(0) instanceof ControllerNodule; 
    if(holdingRemote && last_cycling + 3000 < System.currentTimeMillis()){
        last_cycling = System.currentTimeMillis();
        var cn = (ControllerNodule)bobbies.get(0);
        if(cn.currentIndex == cn.portalNodules.size()-1){
            cn.currentIndex = -1;
        }
        cn.goForward();
    }
}
if(waithere != null){
    System.out.println("HERE COMES THE BIG ONE");
    System.out.println("HERE COMES THE BIG ONE");
    System.out.println("HERE COMES THE BIG ONE");
    Pasty p = new Pasty();
     BufferedImage bi = waithere;
    p.sprite = new Spriter(bi, waitheresidelength, p.mso());
    p.width = bi.getWidth();
    p.height = bi.getHeight();
    p.data = p.sprite.tempIntDataForStorage;
    p.whrat = bi.getWidth()*1.0/bi.getHeight();
    intoInventory(ProtoVideo.promote(p, waitherefulltime, waitheresidelength));
    waithere = null;
}
{
    // System.out.println(chin);
    // chin = 0;
    // chin = Math.max(chin, -Math.PI/4);
    // chin = Math.min(chin, Math.PI/4);
}


{
    long nowkey = GameObject.getRegionKey(x, z);
    if(nowkey != last_frames_key){
        hands_flag_flipped = false;
        portals_flag_flipped = false;
        last_frames_key = nowkey;
        System.out.println("PERCEIVED TO BE IN A NEW REGION");
        long reg = GameObject.getRegionKey(x,z);
        int xx = x(reg);
        int zz = y(reg);
        {
            neededFileNodules.clear();
            neededGameObjects.clear();
            missingGameObjects = null;
            designatedObelisk = null; // might be u
        }
        pasties.query2D(xx-2048, xx+2048, zz-2048, zz+2048);
        System.out.println("Doing special query");
        ObeliskNodule selection = null;
        for(int gg = 0; gg < pasties.size(); gg++){
            GameObject go = pasties.getAt(gg);
            if(go instanceof ObeliskNodule){
                var on = (ObeliskNodule)go;
                if(selection == null || on.activationtime > selection.activationtime){
                    selection = on;
                }
                var obel = (ObeliskNodule)go;
            }
        }
        if(selection != null){
            System.out.println("found and triggering");
            designatedObelisk = selection;
        }else{
            System.out.println("reverting");
            // link
            // set to oldhands
            System.out.println("In theory loading the global tenportals" + globalTenPortals);
            // globalTenPortals.distill(); // this is the new globalTenPortals?
            // if(globalTenPortals == null)globalTenPortals = new TenPortals();
            activeTenPortals = globalTenPortals;
            // globalTenPortals.loadme();
            handSpriterOverride = globalHandSpriterOverride;
        }
    }
}

do_loading_logic();
if(!outerLock && nannypasty != null && nannypasty_timing + 60*3*1000 < System.currentTimeMillis()){
    nannypasty_timing = System.currentTimeMillis();
    addStatement("beginning a nannycheck");
    {
        try{
            byte[] pngBytes1 = null;
            byte[] pngBytes2 = null;
            {
                glReadBuffer(GL_FRONT);
                ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
                glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
                var bbi = bb.asIntBuffer();
                int[] ia = new int[buggaX*buggaY];
                bbi.get(ia);
                ia = convertToARGBAndFlip(ia,buggaX);
                Pasty holder = null;
                pngBytes1 = RawToPng.rawToPng(ia,buggaX, buggaY);
            }
            {
                // glReadBuffer(GL_FRONT);
                // ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
                // glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
                // var bbi = bb.asIntBuffer();
                // int[] ia = new int[buggaX*buggaY];
                // bbi.get(ia);
                // ia = convertToARGBAndFlip(ia,buggaX);
                // Pasty holder = null;
                pngBytes2 = RawToPng.rawToPng(nannypasty.data,nannypasty.width,nannypasty.height);
            }
            LinkedList<String> ch = ChatImageInput.call6(pngBytes1, pngBytes2);
            splitLongStrings(ch, 39);
            Collections.reverse(ch);
            for(var c: ch){
                addStatement(c);
            }
            addStatement("");
        }catch(Exception e){
            addStatement("error during nanny query " + e.getClass());
            e.printStackTrace();
        }
        sfx_failure.noise();
    }
}
if(!outerLock && last_full_save + 18*30000 < System.currentTimeMillis() && !IS_FLAGGED_AS_BACKUP){
    try {
        // Create a FileWriter object to write to the file "Latest.txt"
        FileWriter writer = new FileWriter("Latest.txt", false); // 'true' to append to file, 'false' or omitted to overwrite

        long playtime = pastcum  + ((System.currentTimeMillis() - startM));
        // Write a value to the file
        writer.write(System.currentTimeMillis() + "\n"); // Replace "Your value here" with the actual value you want to write
        writer.write(playtime + "\n"); // Replace "Your value here" with the actual value you want to write
        writer.write("false" + "\n"); // Replace "Your value here" with the actual value you want to write

        // Close the FileWriter
        writer.close();
    } catch (Exception e) {
        // Print the stack trace if an exception occurs
        e.printStackTrace();
    }
    last_full_save = System.currentTimeMillis();
    {
        addStatement("Saving. Please wait...");
        System.out.println("saving NEW AGE");

        // foreach long in hashset,
        // swap the filenames appropriately

        var ii = touchedSet.iterator();
        while(ii.hasNext()){
            Long key = ii.next();
            int lx7 = x(key);
            int lz7 = y(key);
            String strOld = "./"+folder+"/" + "sector-x" + lx7 + "z" +lz7 + "-TOUCHED";
            String strNew = "./"+folder+"/" + "sector-x" + lx7 + "z" +lz7 + "";
            // RENAME RENAME
            try {
                Path sourcePath = Paths.get(strOld);
                Path destinationPath = Paths.get(strNew);
                if(Files.exists(sourcePath)){
                    Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                }
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        touchedSet.clear();

        {
            {
                int nlx7 = (int)(x/128);
                int nlz7 = (int)(z/128);
                {
                    
                    HashSet<Long> incoming = new HashSet<Long>();
                    for(int i = 0; i < sside*sside; i++){
                        incoming.add(xy(nlx7+i/sside-sside/2, nlz7+i%sside-sside/2));
                    }
                    // for(int i = 0; i < 25; i++){
                    //     if((i/5 == 0 && (i%5==0 ||i%5==4)) || (i/5 == 4 && (i%5==0 ||i%5==4)))continue;
                    //     incoming.add(xy(nlx7+i/5-2, nlz7+i%5-2));
                    // }
                    HashSet<Long> toSave = (HashSet<Long>)incoming.clone();
                    // toSave.removeAll(incoming);
                    for(var sector: toSave){
                        int lx7 = x(sector);
                        int lz7 = y(sector);
                        String str = "sector-x" + lx7 + "z" +lz7;
                        ParkerBuffer pb = new ParkerBuffer(true, "./"+folder+"/"+str);
                        
                        pasties.queryAll();
                        
                        {
                            int psize = pasties.gathermeindex;
                            GameObject[] gatherme  = pasties.gatherme;
                            int fromIndex = 0; // start from the second element (index 1)
                            int toIndex = psize;
                            Arrays.sort(gatherme, fromIndex, toIndex, new Comparator<GameObject>() {
                                // @Override
                                public int compare(GameObject p1, GameObject p2) {
                                    if (p1.x != p2.x) {
                                        return Double.compare(p1.x, p2.x);
                                    } else {
                                        return Double.compare(p1.z, p2.z);
                                    }
                                }
                            });
                        }
                        // for(var e: pasties){
                        for(int i = 0; i < pasties.size(); i++){
                            GameObject e = pasties.getAt(i);

                            // old saves
                            int flx7 = (int)(e.x/128);
                            int flz7 = (int)(e.z/128);
                            //get all pasties in sector
                            //save sector
                            if(flx7 == lx7 && flz7 == lz7){
                                try{
                                    e.sectorMarshal(pb);
                                }catch(Exception ee){
                                    p("sector_error caught error for sector " + _7812(flx7) + ":"+_7812(flz7));
                                }
                            }
                        }
                        pb.finalizeWritingFile();
                    }
                }
            }

            
            //save
            dumpToFirstGigaStack();
            marshal();
        }
    }
    if(Main.focused){
        // should be completely    
        glReadBuffer(GL_FRONT);
        ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
        glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
        var bbi = bb.asIntBuffer();
        int[] ia = new int[buggaX*buggaY];
        bbi.get(ia);
        ia = convertToARGBAndFlip(ia,buggaX);
        // dump out

        int width = buggaX;
        int height = buggaY;

        double fivepoint3 = 1.6/0.3;
        // somehow pick the middle
        double ihave = width*1.0/height;
        int desireheight = (int)(width /fivepoint3);
        if(desireheight > height){
            desireheight = height;
        }

        // imagine
        // width = 1600
        // height = 1600
        // desireheight = 300


        BufferedImage image = new BufferedImage(width, desireheight, BufferedImage.TYPE_INT_ARGB);
        // Set the pixels of the image using the int array
        int inset = (height-desireheight)/2;
        int pixelIndex = width * inset; // the inset
        for (int y = 0; y < desireheight; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, ia[pixelIndex]);
                pixelIndex++;
            }
        }

        // it's ok for it to fail if for whatever reason x/y is already bigger than 5.3?
        // which would be crazy
        // Whatever aspect ratio makes... 1.6 x 0.3
        // 5.3...
        // 

        // sliver 
        // cover2sliver

        // Save the BufferedImage as a PNG file
        try {
            File output = new File("cover2sliver.png");
            ImageIO.write(image, "png", output);
            System.out.println("Image saved as 'cover2sliver.png'");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    int bkpwait = 60*1000*90; 
    
    long playtime2 = pastcum  + ((System.currentTimeMillis() - startM));
    long diff = playtime_threshold_tracker/bkpwait - playtime2/bkpwait;
    playtime_threshold_tracker = playtime2;
    if(false && diff != 0 || f6pressed)
    {
        if(shouldmakebackup){
            System.out.println("FULL BACKUP");
            String sourceDir = "./"; // Replace with your source directory path
            String targetDir = "../" ; // Replace with your target directory path

            {
                String currentDir = System.getProperty("user.dir");
                String folderName = currentDir.substring(currentDir.lastIndexOf(File.separator) + 1);
                targetDir = "../" + folderName + "_bkp";

            }
            File srcDir = new File(sourceDir);
            File theDir = new File(targetDir);
            int i = 0;
            while(theDir.exists()){
                targetDir += ""+i;
                theDir = new File(targetDir);
            }
            theDir.mkdirs();
            try {
                Main.copyRecursive(new File(sourceDir), new File(targetDir));
                System.out.println("Copy completed.");
            } catch (IOException e) {
                e.printStackTrace();
            }
            // if (srcDir.isDirectory()) {
            //     File[] files = srcDir.listFiles();

            //     if (files != null) {
            //         for (File file : files) {
            //             Path sourcePath = file.toPath();
            //             Path targetPath = new File(targetDir + File.separator + file.getName()).toPath();
                        
            //             // try {
            //             //     Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
            //             //     System.out.println("Copied: " + file.getName());
            //             // } catch (IOException e) {
            //             //     e.printStackTrace();
            //             // }
            //         }
            //     }
            // } else {
            //     System.out.println("Source directory does not exist or is not a directory.");
            // }
            
            try {
                // Create a FileWriter object to write to the file "Latest.txt"
                FileWriter writer = new FileWriter(targetDir +"/"+ "Latest.txt", false); // 'true' to append to file, 'false' or omitted to overwrite

                long playtime = pastcum  + ((System.currentTimeMillis() - startM));
                // Write a value to the file
                writer.write(System.currentTimeMillis() + "\n"); // Replace "Your value here" with the actual value you want to write
                writer.write(playtime + "\n"); // Replace "Your value here" with the actual value you want to write
                writer.write("true" + "\n"); // Replace "Your value here" with the actual value you want to write

                // Close the FileWriter
                writer.close();
            } catch (Exception e) {
                // Print the stack trace if an exception occurs
                e.printStackTrace();
            }
        }
    }
}

{
    int k  = (swaths[20].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000));
    // k = swaths[0].getColorFromCompletedMap(
    //                     (int)Main.x-1_000_000,(int)Main.z-1_000_000);
    // System.out.println(k);
    // k = 0xff_00_ff_ff;
    Spriter.watred = r(k)/255.0f;
    Spriter.watgreen = g(k)/255.0f;
    Spriter.watblue = b(k)/255.0f;
    // Spriter.fogred   = 1.0f;
    // Spriter.foggreen = 0.0f;
    // Spriter.fogblue  = 1.0f;

    
}
{
    int k  = (swaths[21].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000));
    Spriter.skyred = r(k)/255.0f;
    Spriter.skygreen = g(k)/255.0f;
    Spriter.skyblue = b(k)/255.0f;
}
// {
//     int k  = (swaths[23].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000));
//     Spriter.fogred = r(k)/255.0f;
//     Spriter.foggreen = g(k)/255.0f;
//     Spriter.fogblue = b(k)/255.0f;
// }



time("all-time");
long allTimeTest = System.currentTimeMillis();
if(sleep_time_override != -1){
    try{
        Thread.sleep(sleep_time_override);
    }catch(Exception e){e.printStackTrace();}
}

if(Main.focused && !muted){
    AL10.alListenerf(AL10.AL_GAIN, volume);
}else{
    // AL10.alListenerf(AL10.AL_GAIN, 0.0f);
}

if(TimeLockingKey.globalTypingSwitch == quiztypingswitch && hiddenCallBackTime < System.currentTimeMillis())
{
    if(quizCheck != null){
        quizCheck.hidden = true;
    }
    // if(prestigelevel == 3 || prestigelevel == 1){
    //     for(var p: pasties){
    //         if(p instanceof Pasty){
    //             ((Pasty)p).hidden = true;
    //         }
    //     }
    // }
}

{
    // if out of range
    double dx = x - fileneeded_x_catch;
    double dz = z - fileneeded_z_catch;
    double dist = Math.sqrt(dx*dx + dz*dz);
    if(!neededGameObjects.isEmpty() && dist > 50){
        neededFileNodules.clear();
        neededGameObjects.clear();
        System.out.println("left file editing range!");
        Main.deleteFolderContents(new File("VirtualFolder"));
        missingGameObjects = null;
        designatedObelisk = null; // might be unnecesary?
    }

    {
        // System.out.println("beep");
        // file updated watcher
        if(!neededFileNodules.isEmpty()){ // 
        // System.out.println("beep");
            for(var watchService: services.keySet()){
                var bundle = services.get(watchService);
                LinkedList<String> folders = bundle.x;
                Path toWatch = bundle.y; 
                WatchKey key = watchService.poll();
                if(key != null){
                    // System.out.println("keys: " + services.keySet());
                    System.out.println("");
                    System.out.println("toWatch: " + toWatch);
                    System.out.println("key: " + key);
                    for (WatchEvent<?> event : key.pollEvents()) {
                        Path changedPath = (Path) event.context();
                        System.out.println(event.kind());

                        if (!neededFileNodules.isEmpty() && event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                            // System.out.println("File " + changedPath + " is modified.");
                            // UPDATE THE BYTES VIA NEEDEDFILENODULES
                            // var i = neededFileNodules.keySet().iterator();
                            // while(i.hasNext()){
                            //     var ii = i.next();
                            //     System.out.println("Key item: " + ii);
                            // }
                            System.out.println("2:"+changedPath.toString());
                            var here = new LinkedList<String>();
                            var here2 = (LinkedList<String>)folders.clone();
                            here2.removeFirst();
                            var obnoxiousKey = buildFolderAsKey(here2,changedPath.toString());
                            System.out.println("1:"+obnoxiousKey);
                            FileNodule associated = neededFileNodules.get(obnoxiousKey);
                            if(associated != null && associated.state != null){
                                try{
                                    byte[] fileContent = readFileToByteArray(toWatch.resolve(changedPath));
                                    associated.bytes = fileContent;
                                    // var s = new String(fileContent);
                                    // if(associated.getTitle().equals("450bug.txt")){
                                    //     // System.out.println(s);
                                    //     // System.out.println("Writing out bytes for... " + associated + " associated " + associated.getTitle());
                                    // }

                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }
                        } 
                        if (event.kind() == StandardWatchEventKinds.ENTRY_CREATE) { // can occur even if neededFileNodules is not empty
                            // if(!changedPath.toString().startsWith("dynamic_img_") && !changedPath.toString().startsWith("dynamic_txt_")){ // it's mine
                            // }
                            System.out.println("ENTRY_CREATE for " + changedPath + " ");
                            // // ADD TO INVENTORY AND MAKE SURE TO REGISTER IT TOO!
                            // // add to inventory will go something like...
                            // // well, it will take the rename,

                            if(Files.isDirectory(Paths.get("./VirtualFolder/" + changedPath))){
                                System.out.println("AVOIDIDNG 2 AVOIDING " + ("./VirtualFolder/" + changedPath));
                                continue;
                            };

                            if(changedPath.startsWith("anonymous.png")) continue;
                            var here = new LinkedList<String>();
                            var here2 = (LinkedList<String>)folders.clone();
                            here2.removeFirst();
                            var obnoxiousKey = buildFolderAsKey(here2,changedPath.toString());
                            System.out.println("obno key " + obnoxiousKey);
                            System.out.println("checking set for " + changedPath.toString());
                            System.out.println("current set is " + neededFileNodules);
                            FileNodule associated = neededFileNodules.get(obnoxiousKey);
                            if(associated == null){
                                System.out.println("NOT KNOWN FILE: " + changedPath.toString());
                                var i = neededFileNodules.keySet().iterator();

                                while(i.hasNext()){
                                    var ii = i.next();
                                    System.out.println("AAAAA key item: " + ii);
                                }
                                System.out.println("Making the file for " + changedPath);
                                try{
    
                                    String handle = changedPath.toString();
                                    String fullPath = toWatch.resolve(changedPath).toString();
                                    FileNodule fn = new FileNodule();
                                    // byte[] fileContent = readFileToByteArray(toWatch.resolve(changedPath));
                                    fn.readInDontDelete(fullPath);
                                    Main.intoInventory(fn); // please god work
                                    neededFileNodules.put(handle, fn);
                                }catch(Exception e){
                                    e.printStackTrace();
                                }
                            }else{
                                System.out.println("found "+ changedPath.toString());
                            }
                        }
                    }
                    key.reset();
                }
            }
        }
    }
}
    
Spriter.aspect = buggaX*1.0f/buggaY;
long diff = System.currentTimeMillis() -idk2;
idk2 = System.currentTimeMillis();
highwatermark = Math.max(diff,highwatermark);
if(triggerPrint){
    p("high", highwatermark, "last", diff);
    triggerPrint = false;
}
if(Math.abs(originx-x) > 3000 && Math.abs(originz-z)> 3000){
    originx = x;
    originz = z;
    tmap2 = new Tile[4000][4000];
}
while( !globalQueue.isEmpty()){
    if(true)break;
    Long value = globalQueue.remove();
    // System.out.println("processing");
    // int ix = _7  812((int)(lx7));
    // int iy = _7812((int)(lz7));
    int ix = _7812(x(value));
    int iy = _7812(y(value));
    // System.out.println(ix + "  eeewk " + iy);
    var devilx = ix;
    var devily = iy;

    AggregatorLayer agg = new AggregatorLayer();
    // System.out.println("Creating new aggregator layer " + agg);
    restlessAggregators.add(agg);
    agg.storedx = 1000064 + devilx*128 -64;
    agg.storedz = 1000064 + devily*128 -64;
    agg.storedy = avgheight(agg.storedx, agg.storedz); 


    for(int ii = 0;  ii < 128; ii++){
        for(int jj = 0; jj < 128; jj++){
            if(ii%8 != 0 || jj%8!= 0) continue;
            if(Math.random() < 0.8) continue;
            // I don't want to pass all this useless
            //context into some object I want it
            //done all right here, is that os hard?
            // where is my javascript
            double x = 1000064+-128+128*devilx+ii;
            double y = 1000064+-128+128*devily+jj;
            int x_flat = 1000064+-128+128*devilx;
            int y_flat = 1000064+-128+128*devily;
            class Crappy implements Runnable {
                GameObject go = null;
                Integer mode = 0;

                public void run() {
                    try{

                    int[] inj = new int[]{
                        (int)x,
                        (int)y,
                        0,(int)(x%128-128), (int)(y%128-128)
                    };
                    {
                        var get = sectorFunctions.get(xy(x_flat, y_flat));
                        var hfunc = get == null ? defaultHeightFunction : get.x;
                        var params = get == null ? new double[10] : get.a;
                        var ydisp = hfunc.apply(inj, params);
                        var cfunc = get == null ? defaultColorFunction : get.y;
                        var cres = cfunc.apply(inj, params);
                        double resulttype = cres[3];
                        boolean isDetectedAsWater = false;
                        isDetectedAsWater = resulttype == 12 || resulttype == SPAWN_TEXTURE;
                        
                        if(!(ydisp != -30 && !isDetectedAsWater)) return;
                        int HATRED = 3422 + (ix/4) + (iy/4)<<12;
                        HATRED = Vor.getColor((int)x-1_000_000,(int)y-1_000_000);
                        PlantRock.secret_numer = HATRED; //PUT SEED HERE
                        PlantRock.secret_numer = HATRED; //PUT SEED HERE
                        if(Math.random() > 0.995){
                            mode = 1;
                        }
                        if(Math.random() > 0.996){
                            mode = 2;
                        }
                        // plant color
                        PlantRock.secret_numer = HATRED; //PUT SEED HERE

                        
                        // plant color
                        int my_color = 
                            swaths[0].getColorFromCompletedMap(
                                (int)x-1_000_000,(int)y-1_000_000);
                        // plant size
                        PlantRock.override_v1 = (swaths[1].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        // rock color
                        // PlantRock.override_v2 = (swaths[2].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        PlantRock.override_v2 = (swaths[2].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000));
                        // rock size
                        PlantRock.override_v3 = (swaths[3].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        // tree size
                        PlantRock.override_v4 = (swaths[4].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        // tree color
                        // PlantRock.override_v5 = (swaths[5].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        PlantRock.override_v5 = (swaths[5].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000));
                        // tree pick
                        PlantRock.override_v6 = (swaths[6].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        // rock pick
                        PlantRock.override_v7 = (swaths[7].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        // plant pick
                        PlantRock.override_v8 = (swaths[8].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        // GROUND COLOR (NOT NEEDED HERE)
                        PlantRock.override_v9 = (swaths[9].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff;
                        double k4  = ((swaths[41].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000))&255)/255.0;
                        double k5  = ((swaths[40].getColorFromCompletedMap((int)x-1_000_000,(int)y-1_000_000))&255)/255.0;
                        PlantRock.treeleafquantity = 1+(int)(k4*50);
                        PlantRock.treeleafradius = 4*k5;
                        PlantRock.treeweight = PlantRock.override_v6;
                        PlantRock.plantweight = PlantRock.override_v8;
                        PlantRock.rockweight = PlantRock.override_v7;
                        PlantRock.secret_numer = 0;
                        PlantRock.override_color_a = my_color;
                        double idk = (int)Math.abs(PlantRock.secret_numer*1.0/Integer.MAX_VALUE);
                        double chance = new Random(PlantRock.secret_numer).nextDouble();
                        ByteBuffer bb  = PlantRock.main(null);
                        bb.flip();
                        if(mode == 0){
                            agg.process(bb, x, ydisp, y );
                        }else{
                            go = Main.convert_bb_to_ThreeDumb(bb,x, ydisp+7.5, y);
                            // PURGATORY.add(go);
                        }
                    }
                    
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }  
            // System.out.println("Submitting...");
            var c = new Crappy();
            var f = exe.submit(c);
            crappies.push(f);
            agg.crappies.push(f);
            taskMap.put(f, c);
        }
    }
}

var aggi = restlessAggregators.iterator();
while(aggi.hasNext()){
    var agg = aggi.next();
    
    boolean alldone = false;
    {
        var i = agg.crappies.iterator();
        while(i.hasNext()){
            Future f = i.next();
            if(f.isDone()){
                i.remove();
            }
        }
        alldone = agg.crappies.size() == 0;
    }
    if(alldone || agg.isfull()){
        class Crappy implements Runnable {
            GameObject go = null;
            Integer mode = 0;
            public void run() {
                // System.out.println("Ye");
                // still has it
                // dump the remainder
                ByteBuffer fuckme = ByteBuffer.allocate(50_000_000);

                boolean hasany = agg.complete(fuckme, agg.storedx, agg.storedy, agg.storedz);
                if(!hasany)return;
                // System.out.println("I am here at least");
                fuckme.flip();
                go = Main.convert_bb_to_ThreeDumb(fuckme,agg.storedx, agg.storedy+7.5, agg.storedz);
                System.out.println(go.x);
                System.out.println(go.z);
                System.out.println(go.ydisp);
                if(agg.isfull()){
                    System.out.println("might be ok");
                    // System.exit(0);
                }
            }
        }
        var c = new Crappy();
        var f = exe0.submit(c);
        crappies.push(f);
        taskMap.put(f, c);
    }
    if(alldone && agg.slot == 0)
        aggi.remove();
}
var iterator2 = crappies.iterator();
while(iterator2.hasNext()){
    if(lock()) break;
    var f = iterator2.next();
    if(f.isDone()){
        iterator2.remove();
        var crappy = taskMap.remove(f);
        // System.out.println("I GOT IT "  + f);
        // System.out.println("I GOT IT "  + idk);
        
        try {
            
            Field goField = crappy.getClass().getDeclaredField("go");
            ThreeDumb fieldValue = (ThreeDumb) goField.get(crappy);
            Field modeField = crappy.getClass().getDeclaredField("mode");
            Integer mode = (Integer) modeField.get(crappy);
            if(fieldValue != null) {
                var p = fieldValue;
                
                // System.out.println(" " + p.x + " " + p.ydisp + " " + p.z);
                // System.exit(0);
                p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                p.sprite.repaunch = true;
                if(mode == 1){

                    {
                        double k  = 0;
                        double k2 = 0;
                        double k3 = 0;
                        double k5  = 0;
                        double k6 = 0;
                        double k7 = 0;
                        double k4 = 0;
                        if(true){

                            // ByteBuffer needthis =(new SimpleToneGenerator()).main(color);
                            k  =  ((swaths[30].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                            k2  = ((swaths[31].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                            k3  = ((swaths[32].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                            k4  = ((swaths[33].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;

                            k5  =  ((swaths[34].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                            k6  = ((swaths[35].getColorFromCompletedMap((int)x-1_000_000, (int)z-1_000_000))&255)/255.0;
                            k7  = ((swaths[36].getColorFromCompletedMap((int)x-1_000_000, (int)z-1_000_000))&255)/255.0;
                        }else if(Math.random() < 0.5){
                            k4  = ((swaths[37].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                        }else{
                            k  =  ((swaths[38].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                            k2  = ((swaths[39].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                            k3  = ((swaths[40].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                            k4  = ((swaths[41].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000))&255)/255.0;
                        }
                        var vall = (new ToneGenerator()).get();
                        // p = p.promote(vall);
                        // System.out.println(p);
                        // ((BurningBush)p).cycleAsProportion = k4*50+50;
                        // ((BurningBush)p).cycleAsProportion *=3;
                        // ((BurningBush)p).offsetAsProportion = Math.random();
                        // ((BurningBush)p).setGain(0.3f);
                    }
                    
                } 

                
                // DO NOT PLACE IF THAT REGION 
                // IS NOT LOADED
                boolean shouldload = false;
                {
                    int _nlx7 = (int)(p.x/128);
                    int _nlz7 = (int)(p.z/128);
                    var sector = xy(_nlx7, _nlz7);
                    shouldload = InPlay.contains(sector);
                }
                if(shouldload || true){
                    if(mode == 2 && false){
                        PortalNodule pn = new PortalNodule();
                        pn.chin =   0;
                        pn.style = 1;
                        pn.nangle = Math.random()*Math.PI*2;
                        pn.x =      p.x;
                        pn.z =      p.z;
                        pn.rot =    p.rot;
                        pn.chary =  10; // lol idk
                        pn.ydisp = p.ydisp;
                        pn.destx = 1_000_000 + 2048+4096*((int)(Math.random()*25));
                        pn.destz = 1_000_000 + 2048+4096*((int)(Math.random()*25));
                        // pasties.add(pn);
                        PURGATORY.add(pn);
                    }else{
                        // pasties.add(p);
                        PURGATORY.add(p);
                    }
                }else{
                    System.out.println("Surprise surprise");
                    System.exit(0);
                }
            }

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

ticks++;

if(glfwWindowShouldClose(window)){
    running = false;
}
if(glfwWindowShouldClose(topWindow)){
    // running = false;
    glfwIconifyWindow(topWindow);
}
long now = System.nanoTime();
double delta = (now - last) * 1.0 / 1_000_000_000;
Spriter.blinkValue += 10*(now - last)/1_000_000;
if(Spriter.blinkValue >= 1000 && Input.keys[GLFW_KEY_1] &&  one_key_state_for_blinking){
    Spriter.blinkValue = 1000;
}
if(Input.keys[GLFW_KEY_1] && !one_key_state_for_blinking){
    Spriter.blinkValue = 0;
}


if(random_blinking){
    if(Math.random() *150 < 1){
        Spriter.blinkValue = 0;
    }
}
one_key_state_for_blinking = Input.keys[GLFW_KEY_1]; 


{
    if(Main.mouseWheelVelocity != 0){
        zoomScrollAdjustment += 16*Main.mouseWheelVelocity*delta;
        zoomScrollAdjustment = Math.min(5, zoomScrollAdjustment);
        zoomScrollAdjustment = Math.max(0, zoomScrollAdjustment);
    }
    Main.mouseWheelVelocity = 0;
    if(Input.rightClickEngaged){
        protoZoomValue += 0.4*delta;
    }else{
        protoZoomValue -= 0.7*delta;
    }
    protoZoomValue = Math.min(0.5 -
         Math.pow(2, zoomScrollAdjustment)/100, protoZoomValue);
    protoZoomValue = Math.max(0f, protoZoomValue);

    Spriter.zoomValue = (float)protoZoomValue;
}
if(CYCLING_MODE){
    neck += delta;
}

if(delta > 0.1){
    delta = 0.1;
    // if(timingPrints)
    //     System.out.println(" --- FULL LOOP --- TRUETAG TIMING ERROR: DELTA WAS " + ((int)(delta*1000)));
}

// System.out.println("AUTO_CONTEXT_TIMER " + AUTO_CONTEXT_TIMER);
// if(TimeLockingKey.globalTypingSwitch == null) // don't context if typing
AUTO_CONTEXT_TIMER += delta;

if(false && AUTO_CONTEXT_TIMER > 30*30*30){
    AUTO_CONTEXT_TIMER = Integer.MIN_VALUE;
    PortalNodule pn = new PortalNodule();
    pn.chin = chin;
    pn.nangle = neck;
    pn.destx = x;
    pn.destz = z;
    pn.chary = dispy;
    Input.simulateKey(GLFW_KEY_R);
    System.out.println("Doing an automatic R call to spice things up");
    Tuple3<Spriter,PortalNodule, Integer> item = new Tuple3<Spriter,PortalNodule,Integer>(
        null, // spriter is created later, after the catch
        pn, TEMPORARY_CONTEXT_CONTEXT);
    TEMPORARY_CONTEXT_CONTEXT = tryGetInvStoreIndex(); // for the new one
    FLICKER_GRAB_CATCH_VALUE = item; // this will be caught on the following cycle to
    Spriter.disable2d = 2.0F;
    depthStack.push(item);
    Spriter.globaluishrinker = (float)Math.pow(cutoutSize, depthStack.size());

    {   // One of the most priceless couple of lines of code ever.
        // Main.chin   += Math.random()*2*Math.PI;
        // Main.neck   += Math.random()*2*Math.PI;
        // Main.x      += Math.random()*2000-1000;
        // Main.z      += Math.random()*2000-1000;
        shouldJump = true;
    }
    // {
    //     System.out.println("setting temporary context context to... " + TEMPORARY_CONTEXT_CONTEXT);
    //     tabselection = -1;
    //     bobbyselection = TEMPORARY_CONTEXT_CONTEXT;
    //     bobbies = invStore[bobbyselection];
    //     if(bobbies.size() >= 1){
    //         tabselection = 0;
    //     } 
    // }
}


jump-=delta;
fps_accum+=delta;
frames_since_last++;
{   
    if(fps_accum > 1){
        p("fps tick " + frames_since_last);
        frames_since_last = 0;
        fps_accum -= 1;
        // var up = isFileUpdated(new File("./cl/Main$Bubba.class"));
        // if(up){
        //     p("IT'S UP!");
        //     p("new bubba detected");
        //     try{
        //         var cl = Main.class.getClassLoader();
        //         InputStream is = cl.getResourceAsStream("cl/Main$Bubba.class");
        //         byte[] buf = new byte[20000];
        //         int len = is.read(buf);
        //         is.close();
        //         var cluh1 = new Main().defineClass("Main$Bubba", buf, 0, len);
        //         var cluh = cluh1.getClassLoader().loadClass("Main$Bubba");
                
        //         cluh.getMethod("hello").invoke(null);
        //     }catch(Exception e){e.printStackTrace();}
        // }
        // p(Other.render);
    }
}

TimeLockingKey.updateAll(delta);

last = now;
double elapsed = (System.nanoTime() - start) / 1_000_000_000.0;
Spriter.clockValue = (int)(System.currentTimeMillis()%1000);


if(false && wigglemode){
    neck-=neckdeviance;
    chin-=chindeviance;
    followdist-=followdeviance;
    neckdeviance = 0.3*Math.sin(elapsed*5);
    chindeviance = 0.3*Math.cos(elapsed*5);
    followdeviance = 100*Math.sin(elapsed*3);
    neck+=neckdeviance;
    chin+=chindeviance;
    followdist+=followdeviance;
}

// if(newwigglemode){
//     ParkerQuizItem randomElement = wiggleItem;
//     // System.out.println("Random element: " + randomElement.answer);

//     final int[] timings = new int[]{1, 3, 5, 7, 11, 7, 5, 3};
//     final int all = 1+3+5+7+11+7+5+3;
//     double left = elapsed%all;
//     int i = 0;
//     while(timings[i] < left){
//         left-=timings[i];
//         i++;
//     }
//     double adjusted = left/timings[i];
//     // if(prestigelevel == 1 || prestigelevel == 2)
//     //     FORCE_HIDE_DURING_ROTATION_EXPIRMENT = i%2 == 0;
//     // else
//     //     FORCE_HIDE_DURING_ROTATION_EXPIRMENT = false;

//     // if((adjusted*10)%2 < 1)
//     //     FORCE_HIDE_DURING_ROTATION_EXPIRMENT = true;

//     // double cycle = (elapsed*4)%(Math.PI*2);
//     // double twocycle = (elapsed*4)%(Math.PI*2*2);
//     double cycle = ((adjusted*2)%1)*Math.PI*2;
//     double twocycle = adjusted*Math.PI*2*2;
//     double fourcycle = adjusted*Math.PI*2*2*2;
//     if(twocycle < Math.PI*2){
//         // var angle = Math.abs((cycle)-Math.PI);
//         // x = randomElement.x + Math.sin(randomElement.rot+angle)*PortalNodule.range*4;
//         // z = randomElement.z + Math.cos(randomElement.rot+angle)*PortalNodule.range*4;
//         // neck = randomElement.rot+angle+-Math.PI;
//         // chin = 0;
//         var angle = (Math.PI - Math.abs((cycle)-Math.PI))*1.2;
//         double str = (Math.PI - Math.abs(cycle-Math.PI));
//         // angle = Math.PI/2;
//         // str = 1;

//         x = randomElement.x + Math.sin(randomElement.rot+angle)*PortalNodule.range*str*6;
//         z = randomElement.z + Math.cos(randomElement.rot+angle)*PortalNodule.range*str*6;
//         neck = randomElement.rot+angle+-Math.PI;
//         chin = Math.PI/6;
//         Main.dispy = str*10;
//     }else{
//         var angle = Math.abs((cycle)-Math.PI)*1.2;
//         double str = (Math.PI - Math.abs(cycle-Math.PI));
//         // angle = Math.PI/2;
//         // str = 1;
        
//         x = randomElement.x + Math.sin(randomElement.rot+angle)*PortalNodule.range*str*6;
//         z = randomElement.z + Math.cos(randomElement.rot+angle)*PortalNodule.range*str*6;
//         neck = randomElement.rot+angle+-Math.PI;
//         chin = Math.PI/6;
//         Main.dispy = str*10;
//     }
//     if(priorQuizCheck != null && quizTransitionEnergy > 0.01){
//         var angle = Math.abs((cycle)-Math.PI)*1.2;
//         double str = (Math.PI - Math.abs(cycle-Math.PI));
//         angle = Math.PI/2;
//         str = 1;
        
//         double x1       = quizCheck.x + Math.sin(quizCheck.rot+angle)*PortalNodule.range*str*6;
//         double z1       = quizCheck.z + Math.cos(quizCheck.rot+angle)*PortalNodule.range*str*6;
//         double neck1    = quizCheck.rot+angle+-Math.PI;
//         double chin1    = Math.PI/6;
//         double dispy1   = str*10;

//         double diff_x1           = -x1;       
//         double diff_z1           = -z1;       
//         double diff_neck1        = -neck1;    
//         double diff_chin1        = -chin1;    
//         double diff_dispy1       = -dispy1;   
//         {
//             angle = Math.PI/2;
//             str = 1;
//             diff_x1     += priorQuizCheck.x + Math.sin(priorQuizCheck.rot+angle)*PortalNodule.range*str*6;
//             diff_z1     += priorQuizCheck.z + Math.cos(priorQuizCheck.rot+angle)*PortalNodule.range*str*6;
//             diff_neck1  += priorQuizCheck.rot+angle+-Math.PI;
//             diff_chin1  += Math.PI/6;
//             diff_dispy1 += str*10;
//         }
//         if(diff_neck1 > Math.PI ){
//             diff_neck1-=Math.PI*2;
//             System.out.println("TRIGGERING A");
//         } 
//         if(diff_neck1 < -Math.PI ){
//             diff_neck1+=Math.PI*2;
//             System.out.println("TRIGGERING B");

//         } 

//         x       = x1    + diff_x1*quizTransitionEnergy;
//         z       = z1    + diff_z1*quizTransitionEnergy;
//         neck    = neck1 + diff_neck1*quizTransitionEnergy;
//         chin    = chin1 + diff_chin1*quizTransitionEnergy;
//         dispy   = dispy + diff_dispy1*quizTransitionEnergy;
//         quizTransitionEnergy = Math.max(0, quizTransitionEnergy-delta);
//     }
//     if(quizTransitionEnergy < 0.01 && !unwindme.isEmpty() && !BACKTRACK_ENGAGED){
//         priorQuizCheck = quizCheck;
//         quizCheck = unwindme.pop();
//         System.out.println("POPPING " + quizCheck+" "+ System.currentTimeMillis());
//         quizTransitionEnergy = quizTransitionEnergy_DEFAULT;
//         wiggleItem = quizCheck;
//         Spriter.setLateTexture(quizCheck.sprite);if(prestigelevel==4)Spriter.clearLateTexture();
//         VISUAL_PROMPT_WORD = quizCheck.prompt_word;
        

//     }
//     // if(quizTransitionEnergy < 0.01 && unwindme.isEmpty() && !BACKTRACK_ENGAGED){
//     //     int ii = 0;
//     //     boolean triggered = false;
//     //     if(!BACKTRACK_ENGAGED && prestigelevel != 1){
//     //         for(var stack: BACKTRACK_ITEM_STACK_S){
//     //             int depthMulti = (int)Math.pow(2,ii++);
//     //             if(stack.size() >= bt_amt * depthMulti){
//     //                 BACKTRACK_ITEM_STACK = stack; // set it to be this one
//     //                 BACKTRACK_ENGAGED = true;
//     //                 for(int kk = 0; kk < bt_amt*(depthMulti/2); kk++){
//     //                     //burn first half always
//     //                     stack.pop();
//     //                 }
//     //                 SAVED_RETURNAL_ELEMENT = JUST_IN_CASE;
//     //                 // SAVED_RETURNAL_ELEMENT = quizCheck;
//     //                 // LOST_WASTED = BACKTRACK_ITEM_STACK.pop(); // fuck it
//     //                 // unwindme.push(LOST_WASTED);
//     //                 quizCheck = BACKTRACK_ITEM_STACK.pop();
//     //                 wiggleItem = quizCheck;
//     //                 Spriter.setLateTexture(quizCheck.sprite);if(prestigelevel==4)Spriter.clearLateTexture();
//     //                 Spriter.clearLateTexture();
//     //                 unwindme.push(quizCheck);
//     //                 System.out.println("BREAKING ON TIER " + ii);
//     //                 VISUAL_PROMPT_WORD = quizCheck.prompt_word;

//     //                 break;
//     //             }
//     //         }
//     //     }
//     // }
//     // else{
//     //     var angle = Math.abs((cycle)-Math.PI);
//     //     double str = (Math.PI - Math.abs(cycle-Math.PI));
//     //     double invstr = (Math.abs(cycle-Math.PI));
//     //     x = randomElement.x + Math.sin(randomElement.rot+angle)*PortalNodule.range*invstr*6;
//     //     z = randomElement.z + Math.cos(randomElement.rot+angle)*PortalNodule.range*invstr*6;
//     //     neck = randomElement.rot+angle+-Math.PI;
//     //     chin = Math.PI/5;
//     //     Main.dispy = str*10;

//     // }


//     // neck-=neckdeviance;
//     // chin-=chindeviance;
//     // followdist-=followdeviance;
//     // neckdeviance = 0.3*Math.sin(elapsed*5);
//     // chindeviance = 0.3*Math.cos(elapsed*5);
//     // followdeviance = 100*Math.sin(elapsed*3);
//     // neck+=neckdeviance;
//     // chin+=chindeviance;
//     // followdist+=followdeviance;


// }


Spriter.elapsedAsInt = (int)(elapsed*1000);
{
    // Get rid of this for now
    // if(elapsed > 230*60){
    //     if(!lock()&&!outerLock){ 
    //         p("killing...EXPIRED ");
    //         running = false;
    //         expired_death = true;
    //     }
    //     p("GOING TO KILL IT GOING TO KILL IT");
    // }
}
if(intelapsed != (int)(elapsed*2)){
    intelapsed = (int)(elapsed*2);
    cursorFlicker = !cursorFlicker;
    lastticks = ticks*2;
    ticks = 0;
    
    pumpjack_heartbeat();
    // }
    if(!lock()){
        // {
        //     File theDir = new File("ThreeDumbPollFolder");
        //     if (!theDir.exists()){
        //         theDir.mkdirs();
        //     }
        // }
        // {
        //     File theDir = new File("f3folder");
        //     if (!theDir.exists()){
        //         theDir.mkdirs();
        //     }
        // }
        // {
        //     File theFile = new File("f3folder\\serialdump");
        //     try{
        //         if(theFile.exists()){
        //             p("f3 serialdump found");
        //             ParkerBuffer b = new ParkerBuffer(false, "f3folder\\serialdump");
        //             while(b.getInt() == 1){
        //                 intoInventory(GameObject.unmarshal(b, true));
        //             }
        //             b.close();
        //             p("deleting file ... " + theFile.delete());
        //         }
        //     }catch(Exception e){
        //         e.printStackTrace();
        //     }
        // }

        // uuuuhhhhhhhh.
        // import it

        // OR  DO  A  YOU-KNOW-WHAT  UNMARSHALLING
        // the counter strat i am going with for this is,
        // code
        //is there a file in it that I can read in as bytes[]?

        //look for file called 3d

        // do the following thingydo

        //read in the sprite info

        // read in the ThreeDumb 
        threedfold();
    }
    if(true){
        {
            File theDir = new File("VirtualFolder");
            if (!theDir.exists()){
                theDir.mkdirs();
            }
        }
        {
            if(linkTextNodule != null && linkTextNodule.state != null){
                String filename = "file" + linkTextNodule.hashCode() + ".txt";
                File theFile = new File("VirtualFolder\\" + filename);
                try{
                    // System.out.println("ParkerParker here");
                    if(theFile.exists()){
                        long lastModified = theFile.lastModified() ;
                        // System.out.println("ParkerParker here2 " + lastModified);

                        if(lastModified > Main.FILE_LAST_UPDATED_VALUE + 2000){
                            // System.out.println("ParkerParker here3");
                            Main.FILE_LAST_UPDATED_VALUE = lastModified;
                            linkTextNodule.state = new State();
                            // iterate through
                            Scanner scanner = new Scanner(theFile);
                            while (scanner.hasNextLine()) {
                                String line = scanner.nextLine();
                                linkTextNodule.state.insert(line);
                                linkTextNodule.state.enter(false);
                            }
                            scanner.close();
                        }
                        // return
                        textSheet.clearRepaunch();
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    if(ClipboardMonitor.finishedANewImage != null){
        
        if(false){

            Pasty p = new Pasty();
            var bi = ClipboardMonitor.finishedANewImage;
            p.sprite = new Spriter(bi, p.sl(), p.mso());
            p.width = bi.getWidth();
            p.height = bi.getHeight();
            p.data = p.sprite.tempIntDataForStorage;
            // p.sprite.tempIntDataForStorage = null;
            p.whrat = bi.getWidth() * 1.0 / bi.getHeight();
            // p = ParkerQuizItem.promotePasty(p, "no importa");

            intoInventory(p);
        }

        // System.exit(0);
        {
            Pasty p = new Pasty(); // NOT GETTING USED
            var bi = ClipboardMonitor.finishedANewImage;
            System.out.println(bi.getWidth());
            System.out.println(bi.getHeight());
            // p.sprite = new Spriter(bi, p.sl(), p.mso());
            CurvedSurface.width = bi.getWidth();
            CurvedSurface.height = bi.getHeight();
            CurvedSurface.data = ImageExpirements.biToPix(bi);
            ByteBuffer bb  = CurvedSurface.main(null);
            System.out.println("finished with curved");
            bb.flip();
            ThreeDumb go = Main.convert_bb_to_ThreeDumb(bb,Main.x, Main.dispy+7.5, Main.y);
            go.load();
            go.displayMode = 2;
            go.sprite.clearRepaunch();
            go.sprite.repaunch = false;
            intoInventory(go);
        }
        ClipboardMonitor.finishedANewImage = null;
    }
    if(ClipboardMonitor.finishedanewlink != null){

            var go = new LinkNodule();
            go.state = new State();
            go.state.insert(ClipboardMonitor.finishedanewlink );
            intoInventory(go);



        ClipboardMonitor.finishedanewlink = null;
    }

    if (false && !lock() && insertionMode) { // clipboard watcher
        // code goes here
        try {
            Transferable t = clipboard.getContents(null);
            Image i = (Image) t.getTransferData(DataFlavor.imageFlavor);
            BufferedImage incomingImage = imageToBufferedImage(i);
            boolean guaranteedTrigger = false;
            if (previouslySeen == null) {
                guaranteedTrigger = true;
                previouslySeen = incomingImage; // always null
            }
            if (!_fff0000002493._fff0000010727(previouslySeen, incomingImage) || guaranteedTrigger) {
                p("new copy discovered while in Clipboard Aggregate mode! Adding...");
                {
                    if(false){

                        Pasty p = new Pasty();
                        var bi = incomingImage;
                        p.sprite = new Spriter(bi, p.sl(), p.mso());
                        p.width = bi.getWidth();
                        p.height = bi.getHeight();
                        p.data = p.sprite.tempIntDataForStorage;
                        // p.sprite.tempIntDataForStorage = null;
                        p.whrat = bi.getWidth() * 1.0 / bi.getHeight();
                        // p = ParkerQuizItem.promotePasty(p, "no importa");
                    }
                    System.exit(0);
                    ByteBuffer bb  = CurvedSurface.main(null);
                    bb.flip();
                    GameObject go = Main.convert_bb_to_ThreeDumb(bb,Main.x, Main.dispy+7.5, Main.y);

                    intoInventory(go);
                    glfwMakeContextCurrent(topWindow);
                    // invStorePics[bobbyselection] = new Spriter(bi, p.sl(), p.mso());
                    // invStorePics[bobbyselection].uishrinker = 1.0f;
                    {
                        // var spr = new Spriter(bi, p.sl(), p.mso());
                        // spr.uishrinker = 1.0f;
                        // picStore.put(p, spr);
                        // var pc = p.clone();
                        // picStore.put(p,pc);
                    }

                    glfwMakeContextCurrent(window);
                    if(AUTO_CONTEXT_TIMER > 0)AUTO_CONTEXT_TIMER = 0;
                }
                previouslySeen = incomingImage;// idk idk idk idk

                // // Uncommenting the  following after 12 months
                // {
                //     System.out.println("clearning clipboard to null");
                //     TransferableImage trans = new TransferableImage(null);
                //     try{
                //         clipboard.setContents(trans, null);
                //     }catch(Exception e){
                //         p("Clipboard exception error");
                //         e.printStackTrace();
                //     }
                // }
    
                {
                    final Runnable runnable =
                        (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                    if (runnable != null) runnable.run();
                }
            } else { }
        } catch (IllegalStateException e) {
            System.out.println("Neutral Jing");
        } catch (Exception e) {
            // previouslySeen = null;
            // try {
            //     int rough_line_length = 200;
            //     String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
            //     boolean newTextContent = false; // Add a flag to indicate new text content
            //     if (previousTextContent == null || !previousTextContent.equals(data)) {
            //         newTextContent = true;
            //         previousTextContent = data;
            //     }
            //     if (newTextContent) {
            //         Scanner s = new Scanner(data);
            //         GameObject tn = new TextNodule();
            //         int i  = 0;
            //         while(s.hasNextLine()){
            //             var line = s.nextLine().trim();
            //             if(i==0){
            //                 if(line.startsWith("https://") || line.startsWith("http://")){
            //                     tn = new LinkNodule();
            //                     ((TextNodule)tn).state.insert(line);
            //                     ((TextNodule)tn).state.enter(false);
            //                     break;
            //                 }
            //                 //file:///C:/Users/Parker/Dropbox/clearning/c.bat
            //                 if(line.startsWith("C:\\") || line.startsWith("C:\\Users\\Parker\\")){
            //                     System.out.println("uuuhmm1");
            //                     tn = new LinkNodule();
            //                     line = line.replace('\\', '/');
            //                     line = line.replace(" ", "%20");
            //                     line = "file:///" + line;
            //                     System.out.println("uuuhmm");
            //                     ((TextNodule)tn).state.insert(line);
            //                     ((TextNodule)tn).state.enter(false);
            //                     break;
            //                 }
            //             }
            //             i++;
            //             while(line.length() > 0){
            //                 var a = line.substring(0,Math.min(rough_line_length, line.length()));
            //                 ((TextNodule)tn).state.insert(a);
            //                 ((TextNodule)tn).state.enter(false);
            //                 line = line.substring(Math.min(rough_line_length, line.length()),line.length()) ;
            //                 if(line.length() > 0){
            //                     line = "   " + line;
            //                 }
            //             }
            //         }
            //         intoInventory(tn);
            //         s.close();
            //         // Play sound notification for new text content
            //         final Runnable runnable = (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
            //         if (runnable != null) runnable.run();
            //         if(AUTO_CONTEXT_TIMER > 0)AUTO_CONTEXT_TIMER = 0;
            //     }
            // } catch (Exception e2) {
            //     // System.out.println("ah it's nothing");
            // }
        }
    }    

    neck = (neck+Math.PI*2)%(Math.PI*2);
}
{
    
double speed = 20*delta*1.22 * (FINE_MODE?5:1);


if(followmode){
    if(Input.keys[GLFW_KEY_UP]){ followdist *=1+speed*0.1;}
    if(Input.keys[GLFW_KEY_DOWN]){ followdist /=1+speed*0.1;if(followdist <1)followdist=1;}
}
if(Input.keys[GLFW_KEY_LEFT]){ panvertical -=speed*0.01;}
if(Input.keys[GLFW_KEY_RIGHT]){ panvertical +=speed*0.01;}

{
    
    File theDir = new File("DumpFolder");
    if (!theDir.exists()){
        theDir.mkdirs();
    }
    try {
        java.util.List<File> droppedFiles = getFilesInFolder("./DumpFolder");
        for (File file : droppedFiles) {
            Instant now2 = Instant.now();
            BasicFileAttributes attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
            Instant fileModifiedTime = attrs.lastModifiedTime().toInstant();
            Duration duration = Duration.between(fileModifiedTime, now2);

            if (duration.getSeconds() > 2) {
                String s = file.getAbsolutePath();
                pleaseloadme.add(tup(s, true));
                // if(s.endsWith("serialdump")){
                //     FileNodule.readInSerialDump(s);
                // }else{
                //     // // FileNodule fn = new FileNodule();
                //     // var fn = FileNodule.readIn(s);
                //     // if(fn != null){
                //     //     Main.intoInventory(fn); // please god work
                //     //     Main.p("FileNod adding the new filenode to bobbies");
                //     // }

                // }
                
            }

        }
    } catch (Exception ex) {
        ex.printStackTrace();
    }
}

// if(!focused){
//     double[] temp1 = new double[1];
//     double[] temp2 = new double[1];
//     glfwGetCursorPos(window,temp1,temp2);

//     // System.out.println(temp1[0] + " " + temp2[0]);
//     // System.out.println(FreeMouse.x + " " + FreeMouse.y);
//     int mx = 400;
//     int my = 400;
//     mx = FocusFunction.repo_x;
//     my = FocusFunction.repo_y;
//     double dist1 = temp1[0]-mx;
//     double dist2 = temp2[0]-my;
//     if(dist1 != 0 || dist2 != 0){
//         Input.lastTouch = System.nanoTime();
//     }
//     { //NaN bandaid hopefully
//         if(dist1 == 0){dist1 = 1;}
//         if(dist2 == 0){dist2 = 1;}
//         if(mx==0)mx=1;
//         if(my==0)my=1;
//     }
//     double maxspeed = delta < 0.05 ? 170*delta*1.22 : 10*0.05*1.22;
//     // double maxspeed = delta < 0.05 ? 170*delta*1.22 : 10*0.05*1.22;
//     maxspeed = 170*delta*1.22;
//     double sign1 = dist1/Math.abs(dist1);
//     double sign2 = dist2/Math.abs(dist2);
//     int toCut = 5;
//     double chinAd = maxspeed*(dist2-toCut*sign2)/my;
//     chinAd/=3.0;
//     double neckAd = maxspeed*(dist1-toCut*sign1)/mx;
//     neckAd/=2.0;
//     // chinAd = Math.max(chinAd, -1);
//     // chinAd = Math.min(chinAd, 1);
//     // if(Math.abs(dist1) > toCut) neck+=neckAd;
//     // if(Math.abs(dist2) > toCut) chin+=chinAd;

//     // glfwSetCursorPos(window,mx,my);
//     FreeMouse.x = temp1[0];
//     FreeMouse.y = temp2[0];
//     // System.out.println(FreeMouse.x + " " + FreeMouse.y);
// }
// if(focused && fortounload == null){
if(focused && fortounload == null && !grid_lock_mode){
    double[] temp1 = new double[1];
    double[] temp2 = new double[1];
    glfwGetCursorPos(window,temp1,temp2);
    // if(!FreeMouse.mode){
    if(TimeLockingKey.globalTypingSwitch == null){

        // p("mouse", temp1[0], temp2[0]);
        int mx = 400;
        int my = 400;
        mx = FocusFunction.repo_x;
        my = FocusFunction.repo_y;
        double dist1 = temp1[0]-mx;
        double dist2 = temp2[0]-my;
        if(dist1 != 0 || dist2 != 0){
            Input.lastTouch = System.nanoTime();
        }
        { //NaN bandaid hopefully
            if(dist1 == 0){dist1 = 1;}
            if(dist2 == 0){dist2 = 1;}
            if(mx==0)mx=1;
            if(my==0)my=1;
        }
        double maxspeed = delta < 0.05 ? 170*delta*1.22 : 10*0.05*1.22;
        // double maxspeed = delta < 0.05 ? 170*delta*1.22 : 10*0.05*1.22;
        maxspeed = 170*delta*1.22;
        double sign1 = dist1/Math.abs(dist1);
        double sign2 = dist2/Math.abs(dist2);
        int toCut = 5;
        double chinAd = maxspeed*(dist2-toCut*sign2)/my;
        chinAd/=3.0;
        double neckAd = maxspeed*(dist1-toCut*sign1)/mx;
        neckAd/=2.0;
        // chinAd = Math.max(chinAd, -1);
        // chinAd = Math.min(chinAd, 1);
        if(Math.abs(dist1) > toCut) neck+=neckAd;
        if(Math.abs(dist2) > toCut) chin+=chinAd;
        // chin = 0;
        // chin+=Math.PI/4;
        // if(chin < )
        // chin = ((Math.PI*2+chin)%(Math.PI*2));
        // if(chin > Math.PI*2 + Math.PI/4){
        //     System.out.println("EEEE");
        // }
        // if(chin > Math.PI*2 - Math.PI/4){
        //     System.out.println("OOOO");
        // }
        // chin = Math.min(chin, Math.PI/4 + Math.PI/16);
        // chin = Math.max(chin, - Math.PI/4 + Math.PI/16);
        // chin-=Math.PI/4;
        // chin = (chin+Math.PI*2)%(Math.PI*2);
        // if(chin > Math.PI){
        //     chin = Math.min(chin, Math.PI*2 - Math.PI/4);
        //     chin = Math.max(chin,  Math.PI*2);
        // }else{
        //     // chin = Math.max(chin, 0);
        //     chin = Math.min(chin, Math.PI/4);
        // }
        // // chin = 0;
        // if(chin > 8){
        //     System.out.println("HOW HOW HOW " + chinAd);
        //     System.exit(0);
        // }

        glfwSetCursorPos(window,mx,my);
        FreeMouse.x = temp1[0];
        FreeMouse.y = temp2[0];
    }else if(FreeMouse.mode){
        FreeMouse.x = temp1[0];
        FreeMouse.y = temp2[0];
    }
}
time("deleteme", false);

long current = System.currentTimeMillis();
// if(!globalNav.isEmpty()){
//     addStatement("global nav is not empty");
//     addStatement(""+global_nav_millis_cooldown);
//     addStatement(""+current);
// }
if (!globalNav.isEmpty()) {
    // time for the next jump
    Crux toJump = globalNav.removeLast();

    double previousNeck = neck;
    // Compute raw angle (atan2 returns [-π, π])
    double rawAngle = -Math.atan2(toJump.z - z, toJump.x - x) + Math.PI / 2;

    // Normalize to [0, 2π)
    double normalizedAngle = (rawAngle + 2 * Math.PI) % (2 * Math.PI);

    // Snap to nearest 90-degree angle (π/2 increments)
    double targetAngle = Math.round(normalizedAngle / (Math.PI / 2)) * (Math.PI / 2);
    if (targetAngle >= Math.PI * 2 - 0.01 || targetAngle < 0.01){
        previousNeck = targetAngle;
    }
    addStatement(""+(int)(targetAngle*100));
    
    // Calculate tween fraction based on time remaining for the turn
    double turnTweenFraction = 1.0 - (global_nav_millis_cooldown - current) * 1.0 / global_nav_timer_milis;
    turnTweenFraction = Math.min(Math.max(turnTweenFraction, 0), 1);

    // Smoothly update the neck angle by interpolating between previous and target angles
    neck = interpolateAngle(previousNeck, targetAngle, turnTweenFraction);
    // addStatement("Turning: " + previousNeck + " -> " + targetAngle + " (current neck: " + neck + ")");

    // Also tween the position (if applicable)
    double tweenFraction = 1.0 - (global_nav_millis_cooldown - current) * 1.0 / global_nav_timer_milis;
    tweenFraction = Math.min(Math.max(tweenFraction, 0), 1);
    if (throwaway_last != null) {
        x = (toJump.x - throwaway_last.x) * tweenFraction + throwaway_last.x;
        z = (toJump.z - throwaway_last.z) * tweenFraction + throwaway_last.z;
    }
    
    // When the turn (or jump) completes, reset the timer and update the last position
    if (global_nav_millis_cooldown < current) {
        global_nav_millis_cooldown = current + global_nav_timer_milis;
        throwaway_last = toJump;
        addStatement("Performing jump with moves: " + globalNav.size());
    } else {
        // If not finished, push back the jump for further tweening in subsequent updates
        globalNav.addLast(toJump);
    }
}
if(globalNav.isEmpty() && automatic_random_mode){
    gridOperation(null);
}
if((TimeLockingKey.globalTypingSwitch == null || Input.simulatedControlEnabled ) && focused && !grid_lock_mode)
{
    {
        for(int gg = 0; gg < sside; gg++){
            for(int hh = 0; hh < sside; hh++){
                double ggg = (gg - sside/2)*128+x;
                double hhh = (hh - sside/2)*128+z;
                int rendx = (int)(ggg/128)*128+64;
                int rendz = (int)(hhh/128)*128+64;
                
                
                int nlx7 = (int)(rendx/128);
                int nlz7 = (int)(rendz/128);
                var sector = xy(nlx7, nlz7);
                var inplay = new HashSet<>(InPlay);
                if(!inplay.contains(sector)){
                    continue;
                }

                pasties.query2D(rendx-0.1, rendx+0.1, rendz-0.1, rendz+0.1);
                ThreeDumb find = null;
                for(int i = 0; i < pasties.size(); i++){
                    GameObject go = pasties.getAt(i);
                    if(go instanceof ThreeDumb){
                        find = (ThreeDumb)go;
                        break;
                    }
                }
                if(find == null){
                    // pasties.remove(find);

                    // ByteBuffer bb  = CurvedSurface.main(null);
                    {
                        SimpleTri.parkerval = rendz/4;
                        SimpleTri.parkerval2 = rendx/4;
                        ByteBuffer bb  = SimpleTri.main(null);
                        bb.flip();
                        ThreeDumb go = Main.convert_bb_to_ThreeDumb(bb,x, 0, z);
                        find = go;
    
                        var f = go;
                        // var f = floor.clone();
                        f.x = rendx;
                        f.z = rendz;
                        PURGATORY.add(f);
                    }
                    {
                        ByteBuffer bb  = Pole.main(null);
                        bb.flip();
                        double x = rendx+(Math.random()*128-64);
                        double z = rendz+(Math.random()*128-64);
                        double y = 0;
                        {
                            double[] temp = new double[9];
                            for(int i = 0; i < find.values.length; i+=9){
                                for(int j = 0; j < 9; j++){
                                    temp[j] = find.values[i+j];
                                }
                                double px = (x%128)-64;
                                double pz = (z%128)-64;
                                boolean provar = isPointInTriangle(temp, pz,px);
                                if(provar){
                                    y = 8 + getYOnTrianglePlane(temp, pz, px);
                                    break;
                                }
                            }
                        }
                        // don't use perlin bcuz it might not be perlin anymore
                        // double y = SimpleTri.perlin.noise(x/4,z/4)*40*4;

                        ThreeDumb go = Main.convert_bb_to_ThreeDumb(bb,0, 0, 0);
                        // x-=64;
                        // z-=64;
                        var f = go;
                        f.x = x;
                        f.z = z;
                        f.ydisp = y-3;
                        PURGATORY.add(f);
                    }
                }
            }
        }
    }
    // p("moving... focused: " + focused);
    double adjustment = 0;
    boolean yes = false;
    if(!lock()){
        if(Input.keys[GLFW_KEY_SPACE] ){

                
            int now_nlx7 = (int)(x/128);
            int now_nlz7 = (int)(z/128);
            // System.out.println(now_nlx7 + now_nlz7);
            if(last_frame_nlx7 != -1){
                if(last_frame_nlx7 != now_nlx7){
                    Main.addLog("Making an x-connection");
                    {
                        int rendx = (int)(now_nlx7)*128+64;
                        int rendz = (int)(now_nlz7)*128+64;
                        Crux crux = lookForCruxInSquare(rendx, rendz, 0.1);
                        if(crux == null){
                            Main.addLog("new crux made");

                            crux = new Crux();
                            crux.ydisp = 0;
                            crux.x = rendx+0.1*Math.random();
                            crux.z = rendz;
                            PURGATORY.add(crux);
                        }else{
                            Main.addLog("reusing old crux, adding east/west");
                        }
                        if(now_nlx7 < last_frame_nlx7)
                            crux.upx = 1;
                        else
                            crux.downx = 1;
                    }
                    {
                        int rendx = (int)(last_frame_nlx7)*128+64;
                        int rendz = (int)(last_frame_nlz7)*128+64;
                        Crux crux = lookForCruxInSquare(rendx, rendz, 0.1);
                        if(crux == null){
                            Main.addLog("new crux made");

                            crux = new Crux();
                            crux.ydisp = 0;
                            crux.x = rendx+0.1*Math.random();
                            crux.z = rendz;
                            PURGATORY.add(crux);
                        }else{
                            Main.addLog("reusing old crux, adding east/west");
                        }
                        if(now_nlx7 < last_frame_nlx7)
                            crux.downx = 1;
                        else
                            crux.upx = 1;
                    }
                    // System.exit(0);
                }else if(last_frame_nlz7 != now_nlz7){
                    // find rendesvous for both 
                    // trying to make them point to each other
                    // add if they don't exist
                    Main.addLog("Making a z-connection");
                    
                    {
                        int rendx = (int)(now_nlx7)*128+64;
                        int rendz = (int)(now_nlz7)*128+64;
                        Crux crux = lookForCruxInSquare(rendx, rendz, 0.1);
                        if(crux == null){
                            Main.addLog("new crux made");

                            crux = new Crux();
                            crux.ydisp = 0;
                            crux.x = rendx+0.1*Math.random();
                            crux.z = rendz;
                            PURGATORY.add(crux);
                        }else{
                            Main.addLog("reusing old crux, adding north/south");
                        }
                        if(now_nlz7 < last_frame_nlz7)
                            crux.upz = 1;
                        else
                            crux.downz = 1;
                    }
                    {
                        int rendx = (int)(last_frame_nlx7)*128+64;
                        int rendz = (int)(last_frame_nlz7)*128+64;
                        Crux crux = lookForCruxInSquare(rendx, rendz, 0.1);
                        if(crux == null){
                            Main.addLog("new crux made");

                            crux = new Crux();
                            crux.ydisp = 0;
                            crux.x = rendx+0.1*Math.random();
                            crux.z = rendz;
                            PURGATORY.add(crux);
                        }else{
                            Main.addLog("reusing old crux, adding north/south");
                        }
                        if(now_nlz7 < last_frame_nlz7)
                            crux.downz = 1;
                        else
                            crux.upz = 1;
                    }
                }
            }
            last_frame_nlx7 = now_nlx7;
            last_frame_nlz7 = now_nlz7;

            // this is where it places the small texture, in this case,
            // a tiny hopechud

            // although for starters painting it red would do fine
            int rendx = (int)(x/128)*128+64; // expirement, if the common 3dumb is placed in the middle too, then 63 63 will be empty
            int rendz = (int)(z/128)*128+64; // expirement, if the common 3dumb is placed in the middle too, then 63 63 will be empty
            pasties.query2D(rendx-0.1, rendx+0.1, rendz-0.1, rendz+0.1);
            ThreeDumb find = null;
            for(int i = 0; i < pasties.size(); i++){
                GameObject go = pasties.getAt(i);
                if(go instanceof ThreeDumb){
                    find = (ThreeDumb)go;
                    break;
                }
            }
            if(find != null && !Input.simulatedShiftEnabled){
                Main.modifiedSectors.add(find.getSectorKey());
                
                double my2 = 1-(x%128)/128.0;
                double mx2 = (z%128)/128.0;
                int xxxx = (int)x%128-64;
                int zzz = (int)z%128-64;
                if(find.values != null){
                    for(int i = 0; i < find.values.length; i+=3){
                        if(Math.abs(find.values[i+2] - xxxx)<16 && Math.abs(find.values[i+0] - zzz)<16){
                            find.values[i+1] = 5;
                        }
                    }
                }

                int width = find.width;
                int height = find.height;
                int x = (int)(width*mx2);
                int y = (int)(((int)(height*my2)));
                int expi1 = 10 + width/300;
                int expi2 = 10 + height/300;
                for(int xx = -expi1; xx < expi1; xx++){
                    for(int yy = -expi2; yy < expi2; yy++){
                        int xxx = x+xx;
                        int yyy = y+yy;
                        if(xxx < 0 || xxx >= width || yyy < 0 || yyy >= height) continue;
                        int calc = xxx + yyy*width;
                        int color = 0xff_ff_00_00;
                        color = scalarToColor(mouseWheelRotation, 0, 1000).getRGB();
                        find.data[calc] = color; // red
                    }
                }
                find.sprite.cleanUp();
                find.sprite.clearRepaunch();
                find.load();
            }
        }else{
            last_frame_nlx7 = -1;
        }
    }
    if(Input.keys[GLFW_KEY_Q]){
        // dispy = 150;
        dispy+=speed*0.3;
    }
    if(Input.keys[GLFW_KEY_E]){
        // dispy = 10;
        dispy-=speed*0.3;
    }
    if(Input.keys[GLFW_KEY_2]){
        dispy = 150;
        // dispy+=speed*0.3;
    }
    if(Input.keys[GLFW_KEY_3]){
        dispy = 10;
        // dispy-=speed*0.3;
    }

    boolean wk = Input.keys[GLFW_KEY_W] ;//|| Input.keys[GLFW_KEY_UP];  
    boolean sk = Input.keys[GLFW_KEY_S] ;//|| Input.keys[GLFW_KEY_DOWN];
    boolean ak = Input.keys[GLFW_KEY_A] ;//|| Input.keys[GLFW_KEY_LEFT];
    boolean dk = Input.keys[GLFW_KEY_D] ;//|| Input.keys[GLFW_KEY_RIGHT];
    if(wk){yes=true;
    }
    if(sk){yes=true; adjustment=Math.PI;}
    if(!followmode || true){
        if(ak){yes=true; adjustment=-Math.PI/2;}
        if(dk){yes=true; adjustment=Math.PI/2;}
        if(wk&&ak){
            yes=true; adjustment=-Math.PI/4;
        }
        if(wk&dk){
            // p("go forward-right");
            yes=true; adjustment=Math.PI/4;
        }
        if(sk&&ak){
            yes=true; adjustment=-3*Math.PI/4;
        }
        if(sk&dk){
            yes=true; adjustment=3*Math.PI/4;
        }
    }else{
        if(ak){
            followangle +=speed/3;
            neck-=speed/9;
        }
        if(dk){
            followangle -=speed/3;
            neck+=speed/9;
        }
    }
    double ang = adjustment + neck;
    if(TileClick.targetx != -1){
        double difx = TileClick.targetx-x;
        double dify = TileClick.targety-z;
        yes = true;
        ang = Math.PI/2-Math.atan2(dify,difx);
        // p("closing in..." + Math.sqrt(difx*difx+dify*dify));
    }
    if(yes){
        Input.lastTouch = System.nanoTime();
        {

            int rendx = (int)(x/128)*128+64; // expirement, if the common 3dumb is placed in the middle too, then 63 63 will be empty
            int rendz = (int)(z/128)*128+64; // expirement, if the common 3dumb is placed in the middle too, then 63 63 will be empty
            pasties.query2D(rendx-0.1, rendx+0.1, rendz-0.1, rendz+0.1);
            ThreeDumb find = null;
            for(int i = 0; i < pasties.size(); i++){
                GameObject go = pasties.getAt(i);
                if(go instanceof ThreeDumb){
                    find = (ThreeDumb)go;
                    break;
                }
            }
            if(find != null){
                // don't know what to do next
            }

        }
        double texturespeed = 0;
        {
            int ii = (int)(Math.floor(x/2)*2);
            int jj = (int)(Math.floor(z/2)*2);

            double height = justHeight(ii, jj, 1);

            var ov = overrides.get(xy(ii,jj));
            int texture =  arbitrary(ii, jj, 1, 7) > 128 ? 9 : 10;
            if(height == WATER) texture = 12;
            if(ov != null){
                texture = ov.texture;
            }
            if(texture == 9){
                texturespeed = 0.8;
            }else
            if(texture == 10){
                texturespeed = 0.5;
            }else
            if(texture == 12){
                texturespeed = 0.2;
            }else
            if(texture == 11){
                texturespeed = 1.2;
            }
        }
        double h1 = avgheight(x, z);
        double nx = x+Math.cos(-ang+Math.PI*1/2)*speed;
        double nz = z+Math.sin(-ang+Math.PI*1/2)*speed;
        double h2 = avgheight(nx, nz);
        double rise = h2-h1;
        if(rise < 0) rise = 0; // fine downhill
        double run = speed;
        double grad = rise/run;
        grad = 1; // remove later
        texturespeed = 1;
        accel_x += Math.cos(-ang+Math.PI*1/2)*speed*texturespeed;
        accel_z += Math.sin(-ang+Math.PI*1/2)*speed*texturespeed;
    }
}
{
    double ang = Math.atan2(accel_x, accel_z);
    double h1 = avgheight(x, z);
    double nx = x+Math.cos(-ang+Math.PI*1/2)*speed;
    double nz = z+Math.sin(-ang+Math.PI*1/2)*speed;
    double h2 = avgheight(nx, nz);
    double rise = h2-h1;
    if(rise < 0) rise = 0; // fine downhill
    double run = speed;
    double grad = rise/run;
    grad = 1;
    double texturespeed = 1;
    {
        int ii = (int)(Math.floor(x/2)*2);
        int jj = (int)(Math.floor(z/2)*2);
        // double height = heightAt2.apply(new int[]{ii, jj, 1});
        double height = justHeight(ii, jj, 1);
        var ov = overrides.get(xy(ii,jj));
        int texture =  arbitrary(ii, jj, 1, 7) > 128 ? 9 : 10;
        if(height == WATER) texture = 12;
        if(ov != null){
            texture = ov.texture;
        }
        if(texture == 9){
            texturespeed = 0.8;
        }else
        if(texture == 10){
            texturespeed = 0.5;
        }else
        if(texture == 12){
            texturespeed = 0.2;
        }else
        if(texture == 11){
            texturespeed = 1.2;
        }
    }
    double maxspeed = delta < 0.05 ? 10*delta*1.22 : 10*0.05*1.22;
    maxspeed*=1.4;
    var increasex = 0.3*accel_x*maxspeed*(5.0/(1+grad)-1);
    var increasez = 0.3*accel_z*maxspeed*(5.0/(1+grad)-1);
    x += increasex;
    z += increasez;
    if(false && nearestself != null){
        double xx = x - nearestself.x;
        double zz = z - nearestself.z;
        if(Math.sqrt(zz*zz+xx*xx) < 25){
            // repell off of it
            double angle = Math.atan2(zz, xx);
            x = nearestself.x + Math.cos(angle) * 25.1/1.6;
            z = nearestself.z + Math.sin(angle) * 25.1/1.6;
            System.out.println("IN DA CIRCLE");
            accel_x = 0;
            accel_z = 0;

        }
    }
    doomguymode = (doomguymode +
        (Math.abs(increasex) + Math.abs(increasez))/10)%2;

    // accel_x*=0.87*delta*60;
    // accel_z*=0.87*delta*60;
    double maxdecline = delta < 0.05 ? delta : 0.05;
    accel_x*=1-Math.min(maxdecline*10, 1);
    accel_z*=1-Math.min(maxdecline*10, 1);
}
// if(Input.keys[GLFW_KEY_S]){ z-=speed;}`  
// if(Input.keys[GLFW_KEY_D]){ x-=speed;}
}

if(Main.focused){
    glReadBuffer(GL_BACK);
    bbi.clear();
    glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bbi);
    // System.out.println("snapping " + Mouse.mx + " " + Mouse.my);
    int width2 = buggaX;
    int height2 = buggaY;
    {
        // int x =Mouse.mxi; 
        // int y =Mouse.myi; 
        int x = width2/2; 
        int y = height2/2;

        {
            x = (int)FreeMouse.x;
            y = (int)FreeMouse.y;
        }
        // System.out.println(" " + x + " " + y);

        if(x >= 0 && y >= 0 && x < width2 && y < height2){
            // p(x + ", " + y);
            y = (height2-y-1);
            int i = (x + (width2 * y)) * 4; //offset??
            int r = bbi.get(i) & 0xFF;
            int g = bbi.get(i + 1) & 0xFF;
            int b = bbi.get(i + 2) & 0xFF;
            int lkp = ((r<<0)|(g<<8)|(b<<16));
            if(lkp == 65280){
                // System.out.println(lkp);
                // System.out.println("lookup was not zero, prob slime");
                // System.exit(0);
            }
            if(lkp != 0){
                for(int j = 0; j < pasties.size(); j++){
                    GameObject pp = pasties.getAt(j);
                    if(pp instanceof Searcher){
                        var p = (Searcher) pp;
                        if((p.hashCode()&0xffff) == hover_lkp){
                            // p.teleportPlayer();
                            // tooltip based on this string
                            SEARCHER_TOOLTIP = p.getWord();
                            break;
                        }
                    }
                }
            }else{
                SEARCHER_TOOLTIP = null;
            }

            // System.out.println("ALERT ALERT ALERT");
            // System.out.println("LOOKUP WAS lkp " + lkp);
            int cx = lkp&65535;
            int cy = (lkp>>16);
            hover_lkp = lkp;
            hover_cx = cx;
            hover_cy = cy;
            // g_lkp = lkp;

            // System.exit(0);
            // Object o = Spriter.ClickTrack.lookup(lkp);
            // if(o != null){
            //     slime = o;
            //     // p("color is "+ r + " " + g + " " + b);
            //     // p("num is "+ lkp);
            //     // p("xy is " + x(l) + " " + y(l));
            //     // Spriter.ClickTrack.targetx = (int) x(l);
            //     // Spriter.ClickTrack.targety = (int) y(l);
            // }else{
            //     slime = null;
            // }
        }
    }
}
{
    Tuple<Integer, Boolean> c = null;
    
    // while((c = Input.chewOneAdvancedClickTopWindow()) != null){
    //     int buttdown = c.x;
    //     boolean pressed = c.y;
    //     if(buttdown == GLFW_MOUSE_BUTTON_1 && pressed){
    //         double[] mouseposx = new double[1];
    //         double[] mouseposy = new double[1];
    //         int[] winposx = new int[1];
    //         int[] winposy = new int[1];
    //         int[] winsizewidth = new int[1];
    //         int[] winsizeheight = new int[1];
    //         glfwGetCursorPos(topWindow, mouseposx, mouseposy);
    //         glfwGetWindowSize(topWindow, winsizewidth, winsizeheight);
    //         glfwGetWindowPos(topWindow, winposx, winposy);
    //         var xp = mouseposx[0]/winsizewidth[0];
    //         var yp = mouseposy[0]/winsizeheight[0];

    //         // ClickBoxRegistry.register(0,"top left", null,null,                0.25,0.25,0.5,0.5);
    //         // ClickBoxRegistry.register(0,"bottom right ", null,null, 0.75,0.75,0.5,0.5);
    

    //         p(xp, yp);
    //         xp=2*xp-1;
    //         yp=-(2*yp-1);
    //         System.out.println(xp + " , "  + yp);

    //             ClickBox o = ClickBoxRegistry.get(0,xp,yp);
    //             if(o != null){
    //                 System.out.println("WWEEEE GOT ONE AAAAA " + o.o);
    //                 var msg = (String) o.o;
    //                 if(msg.equals("changecenter")){
    //                     insertionPoint = (Integer) o.p;
    //                 }
    //                 if(msg.equals("delete")){
    //                     if(!lock()){
    //                         bobbies.remove(o.p);
    //                     }
    //                 }
    //                 if(msg.equals("promote")){
    //                     backplate = ((Pasty)o.p).clone();
    //                 }
    //                 if(msg.equals("backplate")){
    //                     // intoInventory(insertionPoint, backplate.clone());
    //                 }
    //             }
    //             else{
    //                 System.out.println("MISS...");
    //             }
    //         // if(yp < 1/10.0){
    //         //     insertionMode = !insertionMode;
    //         //     dumbcolor = 0;
    //         // }else if(yp < 6.0/10.0){
    //         //     // int depth = (int)((yp-1/10.0)/(5/10.0)*5);
    //         //     // int choice = (int)(xp*5) + depth*5;
    //         //     // dumbcolor = choice/5f;
                
    //         //     // p("picked " + choice, depth, (int)(xp*5), xp, yp);
    //         //     // bobbyselection = choice;
    //         //     // bobbyselection = Math.min(24,bobbyselection);
    //         //     // bobbyselection = Math.max(0,bobbyselection);
    //         //     // bobbies = invStore[bobbyselection];
                


    //         // }else{
    //         //     // NOOP
    //         // }
    //     }
    // }
    if(Main.focused){
        ClickBoxRegistry.clear();
        glReadBuffer(GL_BACK);
        bbi.clear();
        glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bbi);
        // System.out.println("snapping " + Mouse.mx + " " + Mouse.my);
        int width2 = buggaX;
        int height2 = buggaY;
        {
            // int x =Mouse.mxi; 
            // int y =Mouse.myi; 
            int x = width2/2; 
            int y = height2/2;

            {
                x = (int)FreeMouse.x;
                y = (int)FreeMouse.y;
            }
            // System.out.println(" " + x + " " + y);

            if(x >= 0 && y >= 0 && x < width2 && y < height2){
                // p(x + ", " + y);
                y = (height2-y-1);
                int i = (x + (width2 * y)) * 4; //offset??
                int r = bbi.get(i) & 0xFF;
                int g = bbi.get(i + 1) & 0xFF;
                int b = bbi.get(i + 2) & 0xFF;
                int lkp = ((r<<0)|(g<<8)|(b<<16));
                // System.out.println("ALERT ALERT ALERT");
                // System.out.println("LOOKUP WAS lkp " + lkp);
                int cx = lkp&65535;
                int cy = (lkp>>16);
                if(cx != 65535){
                    g_cx = cx;
                    g_cy = cy;
                    g_lkp = lkp;
                }

                // System.exit(0);
                // Object o = Spriter.ClickTrack.lookup(lkp);
                // if(o != null){
                //     slime = o;
                //     // p("color is "+ r + " " + g + " " + b);
                //     // p("num is "+ lkp);
                //     // p("xy is " + x(l) + " " + y(l));
                //     // Spriter.ClickTrack.targetx = (int) x(l);
                //     // Spriter.ClickTrack.targety = (int) y(l);
                // }else{
                //     slime = null;
                // }
            }
        }
    }


    while((c = Input.chewOneAdvancedClick()) != null){
        {
            
            if(g_lkp != 0 && TimeLockingKey.globalTypingSwitch!= null){
                TimeLockingKey.globalTypingSwitch.state.cx = g_cx;
                TimeLockingKey.globalTypingSwitch.state.desiredCX = g_cx;
                TimeLockingKey.globalTypingSwitch.state.cy = g_cy;
                var s = TimeLockingKey.globalTypingSwitch.state;
                System.out.println("assigned " + g_cx + " " + g_cy);
                s.highlightedr2 = g_cy;
                s.highlightedc2 = g_cx;
                if(!Input.simulatedShiftEnabled){
                    s.highlightedr1 = g_cy;
                    s.highlightedc1 = g_cx;
                }
            }
        }
        Main.focused = true;


        {
            // if(hover_lkp == 65280){
            //     System.out.println("CLICK lookup was not zero, prob slime");
            //     // PortalNodule.returnal();
            //     PortalNodule.defaultOne();
                
            pasties.queryAll();
            for(int i = 0; i < pasties.size(); i++){
                GameObject pp = pasties.getAt(i);
                if(pp instanceof Searcher){
                    var p = (Searcher) pp;
                    if((p.hashCode()&0xffff) == hover_lkp){
                        p.teleportPlayer();
                    }
                }
                // System.exit(0);
            }
        }
        // glfwHideWindow(topWindow);
        // glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
        int buttdown = c.x;
        boolean pressed = c.y;
        if(!FreeMouse.mode){

            boolean specialCheck = false;
            if(TimeLockingKey.globalTypingSwitch == textstamp){
                var idk = textstamp.state.getLines();
                if(idk.size() != 0 && (idk.size() >= 2 || idk.pop().trim().length() >= 1)){
                    specialCheck = true;
                    System.out.println("special check is true!");
                }
            }
            if(TimeLockingKey.globalTypingSwitch != null && 
                (TimeLockingKey.globalTypingSwitch != textstamp || specialCheck)){
                System.out.println("here");
                if(specialCheck){
                    if(pressed){
                        if(buttdown == GLFW_MOUSE_BUTTON_1){
                            // read buffers, assign
                            //WYSIWYG
                            glReadBuffer(GL_FRONT);
                            ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
                            glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
                            var bbi = bb.asIntBuffer();
                            int[] ia = new int[buggaX*buggaY];
                            // stick it back inside of fortounload
                            
                            bbi.get(ia);
                            ia = convertToARGBAndFlip(ia,buggaX);
                            int width = fortounload.width;
                            int height = fortounload.height;

                            double magic2 = (1-1/Spriter.globaluishrinker)/2;
                            // double magic2 = (Spriter.globaluishrinker - 1);
                            double magic = 1 - magic2*2;
                            System.out.println(magic);
                            System.out.println(magic2);

                            double wrat = buggaX*1.0/width ;
                            double hrat = buggaY*1.0/height;
                            // double wrat = width*1.0/buggaX;
                            // double hrat = height*1.0/buggaY;
                            // System.out.println(height);
                            // System.out.println(buggaX);
                            // System.out.println(buggaY);
                            // System.out.println(wrat);
                            // System.out.println(hrat);
                            for(int i = 0; i < width; i++){
                                for(int j = 0; j < height; j++){
                                    int mappedx = (int)(buggaX*magic2 +  wrat*i*magic);
                                    int mappedy = (int)(buggaY*magic2 +  hrat*j*magic);
                                    if(mappedx > 0 && mappedx < buggaX && mappedy > 0 &&mappedy < buggaY){
                                        Color color = scalarToColor(mouseWheelRotation, 0, 1000);
                                        if(color.getRGB() == ia[mappedx+mappedy*buggaX]){
                                            fortounload.data[i+j*width] = 
                                                ia[mappedx+mappedy*buggaX];
                                        }
                                    }
                                }
                            }
                            textstamp.state = new State();
                            fortounload.sprite.cleanUp();
                            fortounload.load();
                            // somehow get the top into the bot
                            
                        }else{
                        }
                    }
                }else{
                    // if(pressed){
                    //     if(buttdown == GLFW_MOUSE_BUTTON_1){
                    //         TimeLockingKey.globalTypingSwitch.sizeMulti*=1.5;
                    //     }else{
                    //         TimeLockingKey.globalTypingSwitch.sizeMulti/=1.5;
                    //     }
                    // }
                }
            }else{
                if(pressed){
                    if(nearest != null){
                        if(fortounload != null){
                            if(poppedPasty != null){
                                glReadBuffer(GL_FRONT);
                                ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
                                glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
                                var bbi = bb.asIntBuffer();
                                int[] ia = new int[buggaX*buggaY];
                                bbi.get(ia);
                                ia = convertToARGBAndFlip(ia,buggaX);
                                int width = fortounload.width;
                                int height = fortounload.height;
                                double magic2 = (1-1/Spriter.globaluishrinker)/2;
                                double magic = 1 - magic2*2;
                                double wrat = buggaX*1.0/width ;
                                double hrat = buggaY*1.0/height;
                                // double whrat = poppedPasty.whrat;
                                double whrat_adj = poppedPasty.whrat/fortounload.whrat;
                                double mx = 0;
                                double my = 0;
                                double idk = Math.pow(1.2, poppedPastySize-10); 
                                // coords are in 0..1 same as the iasdjfposadjpjsdofigj
                                double incoming_width = 0.5 * idk* whrat_adj / 2; // ofc divided by 2
                                double incoming_height = 0.5 * idk / 2;
                                {
                                    double[] temp1 = new double[1];
                                    double[] temp2 = new double[1];
                                    glfwGetCursorPos(window,temp1,temp2);
                                    double _mx = temp1[0]/buggaX;
                                    double _my = temp2[0]/buggaY;
                                    mx = _mx;
                                    my = _my;
                                    double magic3 = Spriter.globaluishrinker;
                                    {
                                        mx = (mx*2-1)*(magic3);
                                        mx = mx/2+0.5;
                                    }
                                    {
                                        my = (my*2-1)*(magic3);
                                        my = my/2+0.5;
                                    }
                                }
                                // System.out.println(mx);
                                // System.out.println(my);
                                // System.out.println(incoming_width);
                                // System.out.println(incoming_height);
                                int startx = (int)((mx-incoming_width/2) *buggaX*magic + buggaX*magic2);
                                int endx = (int)((mx+incoming_width/2)   *buggaX*magic + buggaX*magic2);
                                int starty = (int)((my-incoming_height/2)*buggaY*magic + buggaY*magic2);
                                int endy = (int)((my+incoming_height/2)  *buggaY*magic + buggaY*magic2);
                                System.out.println(startx);
                                System.out.println(endx);
                                System.out.println(starty);
                                System.out.println(endy);

                                for(int i = 0; i < width; i++){
                                    for(int j = 0; j < height; j++){
                                        int mappedx = (int)(buggaX*magic2 +  wrat*i*magic);
                                        int mappedy = (int)(buggaY*magic2 +  hrat*j*magic);
                                        // i have an idea; set all pixels to red. 
                                        // that will prove i'm right
                                        
                                        if(mappedx > 0 && mappedx < buggaX && mappedy > 0 && mappedy < buggaY){

                                            // set to red just cuz
                                            if(mappedx >= startx && mappedx < endx && mappedy >= starty && mappedy < endy ){
                                                // fortounload.data[i+j*width] = 0xff_ff_00_00; 
                                                fortounload.data[i+j*width] = 
                                                    ia[mappedx+mappedy*buggaX];
                                            }
                                        }
                                    }
                                }
                                textstamp.state = new State();
                                fortounload.sprite.cleanUp();
                                fortounload.load();
                                // somehow get the top into the bot

                                if(buttdown == GLFW_MOUSE_BUTTON_1){
                                    // PROCESS IT
                                    boolean holdingPasty = !bobbies.isEmpty() && bobbies.get(0) instanceof Pasty; 
                                    if(holdingPasty){
                                        poppedPasty = (Pasty)bobbies.pop().clone();
                                    }else{
                                        fortounload.hidden = false;
                                        poppedPasty = null;
                                        fortounload = null; // yes escape it
                                        
                                        if(fortounload instanceof BurningBush){
                                            var bbb = (BurningBush)fortounload;
                                            bbb.sprite.repaunch = false;
                                        }
                                    }
                                }else{
                                    // DO NOTHING
                                    // IT's a sticker!!1
                                }

                                // poppedPasty = null;
                                // Parker Idea
                                // if another, load it
                                // if not, break

                            }else{
                                fortounload_mousebutton = buttdown;
                                fortounload_mousedown = true;
                                fortounload_last_x = null;
                                fortounload_last_y = null;
                            }
                            // for now just leave it to escape
                        }else{
                            if(nearest instanceof FunnelNodule){
                                // get reemotes
                                var fn = (FunnelNodule)nearest;
                                // var ph = (LinkedList<PortalNodule>)fn.phantoms.clone();
                                // var co = (LinkedList<PortalNodule>)fn.completed.clone(); 
                                var ph = new LinkedList<PortalNodule>();
                                var co = new LinkedList<PortalNodule>();
                                // System.out.println("dude " + fn.phantoms.size());
                                // System.out.println("dude " + fn.completed.size());
                                for(var item: fn.phantoms){
                                    ph.addLast(item.convert());
                                }
                                bobbies.addFirst(ControllerNodule.fromPortals(ph));
                                if(fn.completed != null){
                                    for(var item: fn.completed){
                                        co.addLast(item.convert());
                                    }
                                    bobbies.addFirst(ControllerNodule.fromPortals(co));
                                }
                            }
                            else if(buttdown == GLFW_MOUSE_BUTTON_1){
                                if(Input.simulatedControlEnabled){
                                    if(nearest instanceof Pasty){
                                        var become = (Pasty)nearest;
                                        become.hidden = true;
                                        fortounload = become;
                                        textstamp.state= new State();
                                        TimeLockingKey.globalTypingSwitch = textstamp;
                                    }
                                }else{

                                    // poppedPasty = (Pasty)bobbies.pop();
                                    boolean holdingPasty = !bobbies.isEmpty() && bobbies.get(0) instanceof Pasty; 
                                    if(Input.simulatedShiftEnabled && holdingPasty && nearest instanceof Pasty){
                                        var become = (Pasty)nearest;
                                        become.hidden = true;
                                        fortounload = become;
                                        poppedPasty = (Pasty)bobbies.pop().clone();
                                    }
                                    else if(nearest instanceof TenPortals){
                                        neededTenPortals = (TenPortals)nearest;
                                    }else if(nearest instanceof PortalNodule){
                                        var pn = (PortalNodule)nearest;
                                        if(pn.style == 0){
                                            pn.style = 2;
                                        }else if(pn.style == 2){
                                            pn.style = 0;
                                        }
                                    }else if(nearest instanceof PumpJack){
                                        var pj = (PumpJack)nearest;
                                        boolean holdingPasty2 = !bobbies.isEmpty() && bobbies.get(0) instanceof Pasty; 
                                        if(holdingPasty2){
                                            var p = (Pasty)bobbies.get(0);
                                            if(p != null)
                                            pj.hasAPasty = p.clone();
                                        }
                                    }else if(nearest instanceof FolderNodule){
                                        var pj = (FolderNodule)nearest;
                                        boolean holdingPasty2 = !bobbies.isEmpty() && bobbies.get(0) instanceof Pasty; 
                                        boolean holdingFile = !bobbies.isEmpty() && bobbies.get(0) instanceof FileNodule; 
                                        boolean holdingFolder = !bobbies.isEmpty() && bobbies.get(0) instanceof FolderNodule; 
                                        if(holdingPasty2){
                                            var p = (Pasty)bobbies.get(0);
                                            if(p != null)
                                            pj.hasAPasty = p.clone();
                                            bobbies.remove(p);
                                        }
                                        if(holdingFile){
                                            var p = (FileNodule)bobbies.get(0);
                                            System.out.println("D O I N G I T ! AAAAAAAAAAAAAAAAAAAAAAAA");
                                            if(p != null)
                                            pj.bakedObjects.addLast(p.clone());
                                            bobbies.remove(p);
                                        }
                                        if(holdingFolder){
                                            var p = (FolderNodule)bobbies.get(0);
                                            System.out.println("D O I N G I T ! folder nodule");
                                            if(p != null)
                                            pj.bakedObjects.addLast(p.clone());
                                            bobbies.remove(p);

                                        }

                                    }else{
                                                    
                                            nearest.size*=1.5;
                                            textSheet.clearRepaunch();

                                    }
                                }
                            }else if(buttdown == GLFW_MOUSE_BUTTON_3){
                                if(nearest instanceof BurningBush){
                                    var bb = ((BurningBush)nearest);
                                    if(bb.rolloffValue == 3){
                                        bb.rolloffValue = 1.3;
                                    }else{
                                        bb.rolloffValue = 3;
                                    }
                                }
                            }else{
                                if(nearest instanceof FolderNodule){
                                    var pj = (FolderNodule)nearest;
                                    for(var e: pj.bakedObjects){
                                        intoInventory(e);
                                    }
                                    pj.bakedObjects.clear();
                                }else{
                                    nearest.size/=1.5;
                                    textSheet.clearRepaunch();
                                }
                            }
                        }
                    }else{
                        if(Input.simulatedControlEnabled){
                            double speed = 10*delta*1.22;
                            if(buttdown == GLFW_MOUSE_BUTTON_1){
                                dispy+=speed*0.3*5;
                            }
                            else{
                                dispy-=speed*0.3*5;
                            }
                        }else{
                            if(buttdown == GLFW_MOUSE_BUTTON_1){
                                // size*= 1.5;
                                size = Math.min(size*1.5, 3);

                            }else{
                                if(!Input.simulatedControlEnabled){
                                    // size/= 1.5;
                                    size = Math.max(size/1.5, 0.03);

                                }else{
                                    // if(lastCmd != null){
                                    //     try{
                                    //         Runtime.
                                    //             getRuntime().
                                    //             exec(lastCmd);
                                    //     }catch(Exception e){
                                    //         e.printStackTrace();
                                    //     }
                                    // }
                                }
                                
                            }
                        }
                    }
                }else{
                    if(fortounload != null){
                        fortounload_mousedown = false;
                    }
                }
            }
        }
        // else if(FreeMouse.mode){
        //     // DO THE FREEMOUSE STUFF
        //     glReadBuffer(GL_FRONT);
        //     ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
        //     glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
        //     System.out.println("snapping " + FreeMouse.x + " " + FreeMouse.y);
        //     int width = buggaX;
        //     int height = buggaY;

        //     {
        //         int x =(int)FreeMouse.x; 
        //         int y =(int)FreeMouse.y; 
        //         y = (height-y-1);
        //         int i = (x + (width * y)) * 4; //offset??
        //         int r = bb.get(i) & 0xFF;
        //         int g = bb.get(i + 1) & 0xFF;
        //         int b = bb.get(i + 2) & 0xFF;
        //         int lkp = ((r<<16)|(g<<8)|(b<<0));
        //         long l = TileClick.lookup(lkp);
        //         p("color is "+ r + " " + g + " " + b);
        //         p("num is "+ lkp);
        //         p("xy is " + x(l) + " " + y(l));
        //         TileClick.targetx = (int) x(l);
        //         TileClick.targety = (int) y(l);
        //     }
        //     // BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        //     // for(int x = 0; x < width; x++){
        //     //     for(int y = 0; y < height; y++){
        //     //         int i = (x + (width * y)) * 4; //offset??
        //     //         int r = bb.get(i) & 0xFF;
        //     //         int g = bb.get(i + 1) & 0xFF;
        //     //         int b = bb.get(i + 2) & 0xFF;
        //     //         bi.setRGB(x, height - (y + 1), (0xFF << 24) | (r << 16) | (g << 8) | b);
        //     //     }
        //     // }
        //     // TransferableImage trans = new TransferableImage(bi);
        //     // clipboard.setContents(trans, null);
        // }
    }
}
{
    // if(Input.keys[GLFW_KEY_F6]){
    //     z+= 100;
    // }
    // if(Input.keys[GLFW_KEY_F8]){
    //     x+= 100;
    // }   
    // if(Input.keys[GLFW_KEY_F7]){
    //     x-= 100;
    // }
}
class SaveRegion implements Runnable {
    Long sector = null;
    java.util.List<GameObject> cleanUpAfter = new LinkedList<GameObject>();
    public void run(){
        int lx7 = x(sector);
        int lz7 = y(sector);
        String str = "sector-x" + lx7 + "z" +lz7 + "-TOUCHED";
        Tuple<ByteBuffer,GameObject[]> toUse = null;
        try{
            while((toUse = parkerBufferBuffers.poll()) == null){
                Thread.sleep(5);
                System.out.println("waiting on poll SaveRegion");
            }
        }catch(Exception e){ e.printStackTrace();}
        boolean needsFileSaving = Main.modifiedSectors.contains(sector);
        Main.modifiedSectors.remove(sector);

        ParkerBuffer pb = null;
        if(needsFileSaving) pb = new ParkerBuffer(true, "./"+folder+"/"+str, toUse.x);
        
        int[] gathermeindex = new int[1];
        // GameObject[] gatherme = new GameObject[10000];
        GameObject[] gatherme = toUse.y;

        pasties.queryAll(gatherme, gathermeindex, lx7*128-128,lx7*128+128,lz7*128-128,lz7*128+128);
        int psize = gathermeindex[0];
        {
            int fromIndex = 0; // start from the second element (index 1)
            int toIndex = psize;
            Arrays.sort(gatherme, fromIndex, toIndex, new Comparator<GameObject>() {
                // @Override
                public int compare(GameObject p1, GameObject p2) {
                    if (p1.x != p2.x) {
                        return Double.compare(p1.x, p2.x);
                    } else {
                        return Double.compare(p1.z, p2.z);
                    }
                }
            });
        }

        for(int i = 0; i < psize; i++){
            var e = gatherme[i];
            // var e = pasties.get(i);
            // old saves
            int flx7 = (int)(e.x/128);
            int flz7 = (int)(e.z/128);
            //get all pasties in sector
            //save sector
            if(flx7 == lx7 && flz7 == lz7){
                try{
                    if(needsFileSaving) e.sectorMarshal(pb);
                    cleanUpAfter.add(e);
                }catch(Exception ee){
                    // System.out.println("ERROR: Save Task: " + flx7 + ", "+ flz7 + ee.getClass().getCanonicalName());

                    // ee.printStackTrace();
                }
            }
        }
        p("Multithread:SAVETASK--- completed   " + _7812(lx7) +","+ _7812(lz7));
        if(needsFileSaving) pb.finalizeWritingFile();
        if(needsFileSaving) touchedSet.add(xy(lx7,lz7));
        try{
            if(needsFileSaving) pb.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        parkerBufferBuffers.add(toUse); // DONE WITH IT

    }

}

class LoadRegion implements Runnable {
    Long sector = null;
    java.util.List<GameObject> toReturn = new LinkedList<GameObject>();
    public void run() {
        int lx7 = x(sector);
        int lz7 = y(sector);
        String str = "sector-x" + lx7 + "z" +lz7 + (touchedSet.contains(xy(lx7,lz7))?"-TOUCHED":"");

        Tuple<ByteBuffer,GameObject[]> toUse = null;
        try{
            while((toUse = parkerBufferBuffers.poll()) == null){
                Thread.sleep(5);
                System.out.println("waiting on poll LoadRegion");
            }
        }catch(Exception e){ e.printStackTrace();}

        if((new File("./"+folder+"/"+str).exists())){
            ParkerBuffer pb = new ParkerBuffer(false, "./"+folder+"/"+str, toUse.x);
            // int psize = pasties.size();
            
            int[] gathermeindex = new int[1];
            GameObject[] gatherme = toUse.y;
            pasties.queryAll(gatherme, gathermeindex, lx7*128-128,lx7*128+128,lz7*128-128,lz7*128+128);
            // pasties.queryAll(gatherme, gathermeindex);
            int psize = gathermeindex[0];
            // System.out.println("PSize determined to be... " + psize);
            {
                int fromIndex = 0; // start from the second element (index 1)
                int toIndex = psize;
                Arrays.sort(gatherme, fromIndex, toIndex, new Comparator<GameObject>() {
                    // @Override
                    public int compare(GameObject p1, GameObject p2) {
                        if (p1.x != p2.x) {
                            return Double.compare(p1.x, p2.x);
                        } else {
                            return Double.compare(p1.z, p2.z);
                        }
                    }
                });
            }
            for(int i = 0; i < psize; i++){
                var e = gatherme[i];
                int flx7 = (int)(e.x/128);
                int flz7 = (int)(e.z/128);
                if(flx7 == lx7 && flz7 == lz7){
                    try{
                        // System.out.println("a");
                        e.sectorUnMarshal(pb);
                        // System.out.println("b");
                        toReturn.add(e);
                        // System.out.println("c");

                    }catch(Exception ee){
                        // System.out.println("d");
                        System.out.println("ERROR:Multithread: Load Task: " + _7812(flx7) + ", "+ _7812(flz7) + " " + ee.getClass().getCanonicalName());
                        
                        // to nuke
                        pleasenukeme.add(xy((int)(e.x), (int)(e.z)));
                        toReturn = new LinkedList<GameObject>();
                        break;
                        // ee.printStackTrace();
                    }
                }
            }
            try{
                pb.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }else{
            // System.out.println("set_plants will go here " +  _7812(lx7) +","+ _7812(lz7));
            //arbfunc goes here
            globalQueue.add(xy(lx7,lz7));
        }
        parkerBufferBuffers.add(toUse); // DONE WITH IT
    }
}
// if(velocity >= 0 || flightheight > avgheight(x,z)){
//     flightheight += velocity;
//     velocity -= 3; //acelleration constant
//     if(flightheight <= avgheight(x,z)){
//         velocity = 0;
//     }
// }

// if(Input.simulatedControlEnabled){
//     focused = false;
// }

while(!Input.pressStack.isEmpty()){
Tuple<Integer, Boolean> key = Input.pressStack.removeLast(); // first pushed
// if(key.y){
//     p("key pressed " + key.x);
// }
if(key.x == GLFW_KEY_LEFT_CONTROL || key.x == GLFW_KEY_RIGHT_CONTROL){
    Input.simulatedControlEnabled = key.y;
    if(key.y)
    chordOn = !chordOn;
}else
if(key.x == GLFW_KEY_LEFT_SHIFT || key.x == GLFW_KEY_RIGHT_SHIFT){
    Input.simulatedShiftEnabled = key.y;
}else
if(key.x == GLFW_KEY_LEFT_ALT || key.x == GLFW_KEY_RIGHT_ALT){
    Input.simulatedAltEnabled = key.y;
}else
{
/******** KEYOARD HANDLE HANDLE HANDLE KEYBOARD ********/
// if(key.x == GLFW_KEY_SPACE){ 
//     reassign();
//     tmap2 = new Tile[4000][4000];
// }
// p(Input.simulatedControlEnabled);

if(TimeLockingKey.globalTypingSwitch == null){
    var esckey = TimeLockingKey.keys.get(GLFW_KEY_ESCAPE).isNewPress();
    if(esckey){
        if(newwigglemode){
            newwigglemode = false;
            FORCE_HIDE_DURING_ROTATION_EXPIRMENT = false;
            prestigelevel = 0;
        }else{
            if(neededTenPortals != null){
                neededTenPortals = null;
            }
            if(fortounload != null){
                fortounload.hidden = false;
                fortounload = null;
                if(fortounload instanceof BurningBush){
                    var bb = (BurningBush)fortounload;
                    bb.sprite.repaunch = false;
                }
                if(fortounload instanceof MultipleChoice){
                    System.out.println("YIPPEE GOODY!!2");
                    System.exit(0);
                    
                    // PromiseNodule pn = new PromiseNodule();
                    // Runnable arbfunc = () -> {
                    //     try{
                    //         var pngBytes = RawToPng.rawToPng(
                    //             fortounload.data,
                    //             fortounload.width,
                    //             fortounload.height);
                    //         LinkedList<LinkedList<String>> ch =
                    //             ChatImageInput.call2(pngBytes);
                    //         System.out.println(ch);
                    //         System.out.println("hey...");
                    //         System.exit(0);
                    //     }catch(Exception e){
                    //         e.printStackTrace();
                    //     }
                    //     // Chat2.call2(str);
                    //     // LinkedList<LinkedList<String>> ch = ChatImageInput.call2("test test test");
                    // };
                    // Thread t = new Thread(arbfunc);
                    // pn.watchThread = t;
                    // t.start();
                }
                fortounload_last_x = null;
                if(poppedPasty != null){
                    intoInventory(poppedPasty.clone());
                    poppedPasty = null;
                }
            }
            else if(stage1ukeyportal != null){
                stage1ukeyportal = null;
            }else if(!Input.simulatedShiftEnabled){
                System.out.println("So odd 2");
                focused = false;
                glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            }
        }
    }
    if(!lock()){
        if(TimeLockingKey.keys.get(GLFW_KEY_DELETE).isNewPress() ){ 
            if(Input.simulatedAltEnabled && Input.simulatedControlEnabled && Input.simulatedShiftEnabled && !outerLock){
                // int flx7 = (int)(x/128);
                // int flz7 = (int)(z/128);
                // var iterator = pasties.iterator();

                // while(iterator.hasNext()){
                //     GameObject p = iterator.next();
                //     if(p.getSectorKey() == xy(flx7, flz7)){
                //         iterator.remove();
                //     }
                // }
                // I DONT CARE!!!!
                
            }else{
                dellies.clear();
                if(nearest != null){
                    if(!outerLock){
                        if(nearest instanceof FileNodule && ((FileNodule)nearest).hasAPasty != null){
                            dellies.push(((FileNodule)nearest).hasAPasty);
                            ((FileNodule)nearest).hasAPasty = null;
                        }else{
                            pasties.remove(nearest);
                            dellies.push(nearest.clone());
                            nearest.clean();
                        }
                    }
                }else{
                    if(!bobbies.isEmpty()){
                        dellies.push(bobbies.removeFirst());
                    }
                }
            }
        }
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_P).isNewPress()){
        chin = 0;
        neck = 0;
        followmode = !followmode;
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_M).isNewPress()){
        if(!Input.simulatedControlEnabled){
            muted = !muted;
            if(muted) AL10.alListenerf(AL10.AL_GAIN, 0.0f);
            else AL10.alListenerf(AL10.AL_GAIN, 10.0f);
        }else{
            // DUMP ALL
            // print and exit
            System.out.print("isle:" +"\t");
            for(int i = 0; i < 15; i++){
                System.out.print(i + "\t");
            }
            System.out.println();

            for(int j = 0; j < 100; j++){
                if(fps_timings[j]< 18) continue; // not a problem
                System.out.print(fps_timings[j] + ":\t");
                for(int i = 0; i < 15; i++){
                    System.out.print(nano_timings[j][i] + "\t");
                }
                System.out.println();
            }
            // System.exit(0);

        }

    }
    if(TimeLockingKey.keys.get(GLFW_KEY_N).isNewPress()){
        if(Input.simulatedControlEnabled){
            System.out.println("dumping1");
            if(globalTenPortals == null) globalTenPortals = TenPortals.newEmpty();
            System.out.println("dumping2");
            intoInventory(globalTenPortals);
            activeTenPortals = null;
            globalTenPortals = null;
        }else{
            Collections.reverse(bobbies);
        }
    }

    // if(TimeLockingKey.keys.get(GLFW_KEY_N).isNewPress()){
    //     if(Input.simulatedControlEnabled){
    //         for(var p: pasties){
    //             if(p instanceof Pasty){
    //                 ((Pasty)p).hidden = false;
    //             }
    //         }
    //     }else{
    //         for(var p: pasties){
    //             if(p instanceof Pasty){
    //                 ((Pasty)p).hidden = true;
    //             }
    //         }
    //     }
    //     // muted = !muted;
    //     // if(muted) AL10.alListenerf(AL10.AL_GAIN, 0.0f);
    //     // else AL10.alListenerf(AL10.AL_GAIN, 10.0f);
    // }
    if(!lock()){
        if((TimeLockingKey.keys.get(GLFW_KEY_I).isNewPress())){
            var go = new TextNodule();
            go.ydisp = y + dispy;
            // go.ydisp = y;
            go.x = x+12*Math.sin(neck);
            go.z = z+12*Math.cos(neck);
            go.rot = neck+Math.PI/2;
            pasties.add(go);
            TimeLockingKey.globalTypingSwitch = go;
            textSheet3.repaunch = false;
            textSheet3.clearRepaunch();
            textSheet.clearRepaunch();
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_U).isNewPress())){
            // var go = new TextNodule2();
            // go.ydisp = y + dispy;

            // // go.ydisp = y;
            // go.x = x+12*Math.sin(neck);
            // go.z = z+12*Math.cos(neck);
            // go.rot = neck+Math.PI/2;
            // pasties.add(go);
            // TimeLockingKey.globalTypingSwitch = go;
            // textSheet.repaunch = false;
            // textSheet.clearRepaunch();
            if(stage1ukeyportal == null){
                // grab starting image
                // grab starting portal
                if(!(nearest instanceof Pasty)){
                    glReadBuffer(GL_FRONT);
                    ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
                    glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
                    var bbi = bb.asIntBuffer();
                    int[] ia = new int[buggaX*buggaY];
                    bbi.get(ia);
                    ia = convertToARGBAndFlip(ia,buggaX);
                    Pasty p = new Pasty();
                    p.data = ia;
                    p.height = buggaY;
                    p.width = buggaX;
                    p.whrat = buggaX*1.0/buggaY;
                    p.size = 2;
                    p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                    stage1pasty = p;
                    // bb.clean(); // clean up allocatedirect
                }else{
                    stage1pasty = (Pasty)nearest.clone();
                }
                {
                    PortalNodule pn = new PortalNodule();
                    pn.chin = chin;
                    pn.nangle = neck;
                    pn.destx = x;
                    pn.destz = z;
                    pn.chary = dispy;
                    stage1ukeyportal = pn;
                }
                System.out.println("First step of dual portal");
            }else
            {
                Pasty stage2pasty = null;
                PortalNodule stage2portal = null;
                if(!(nearest instanceof Pasty)){
                    glReadBuffer(GL_FRONT);
                    ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
                    glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
                    var bbi = bb.asIntBuffer();
                    int[] ia = new int[buggaX*buggaY];
                    bbi.get(ia);
                    ia = convertToARGBAndFlip(ia,buggaX);
                    Pasty p = new Pasty();
                    p.data = ia;
                    p.height = buggaY;
                    p.width = buggaX;
                    p.whrat = buggaX*1.0/buggaY;
                    p.size = 2;
                    p.sprite = new Spriter(p.data, p.width, p.height, p.sl(), p.mso());
                    stage2pasty = p;
                    // bb.clean(); // clean up allocatedirect
                }else{
                    stage2pasty = (Pasty)nearest.clone();
                }
                {
                    PortalNodule pn = new PortalNodule();
                    pn.chin = chin;
                    pn.nangle = neck;
                    pn.destx = x;
                    pn.destz = z;
                    pn.chary = dispy;
                    stage2portal = pn;
                }
                {
                    stage2portal.x = stage1ukeyportal.destx+12*Math.sin(stage1ukeyportal.nangle+ Math.PI);
                    stage2portal.z = stage1ukeyportal.destz+12*Math.cos(stage1ukeyportal.nangle+ Math.PI);
                    stage2portal.rot = stage1ukeyportal.nangle+Math.PI/2;
                    stage2portal.ydisp = avgheight(stage2portal.x, stage2portal.z) + 10;
                    pasties.add(stage2portal);
                }
                {
                    stage1ukeyportal.x = stage2portal.destx+12*Math.sin(stage2portal.nangle+ Math.PI);
                    stage1ukeyportal.z = stage2portal.destz+12*Math.cos(stage2portal.nangle+ Math.PI);
                    stage1ukeyportal.ydisp = avgheight(stage1ukeyportal.x, stage1ukeyportal.z) + 10;
                    stage1ukeyportal.rot = stage2portal.nangle+Math.PI/2;
                    pasties.add(stage1ukeyportal);
                }
                {
                    stage2pasty.x = stage1ukeyportal.destx+24*Math.sin(stage1ukeyportal.nangle + Math.PI);
                    stage2pasty.z = stage1ukeyportal.destz+24*Math.cos(stage1ukeyportal.nangle + Math.PI);
                    stage2pasty.ydisp = avgheight(stage2pasty.x, stage2pasty.z) + 10;
                    stage2pasty.rot = stage1ukeyportal.nangle+Math.PI/2;
                    pasties.add(stage2pasty);
                }
                {
                    stage1pasty.x = stage2portal.destx+24*Math.sin(stage2portal.nangle+ Math.PI);
                    stage1pasty.z = stage2portal.destz+24*Math.cos(stage2portal.nangle+ Math.PI);
                    stage1pasty.ydisp = avgheight(stage1pasty.x, stage1pasty.z) + 10;
                    stage1pasty.rot = stage2portal.nangle+Math.PI/2;
                    pasties.add(stage1pasty);
                }

                // if already have starting
                // grab ending portal
                // use starting and ending portal
                // to place 2 pasties, 2 destinations, and 2 portals
                // (the destinations will be the OG portal saves,)
                // ( the actual portals will be places behind that)
                // ( the actual pasties will be placed even behind that)
                System.out.println("Second step of dual portal");
                

                // where am I ?
                // might as well just try it
                // anyway





                stage1ukeyportal = null; // reset for next use
            }
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_J).isNewPress())){
            // var go = new LudeonNodule();
            // go.ydisp = y;
            // go.x = x+12*Math.sin(neck);
            // go.z = z+12*Math.cos(neck);
            // go.rot = neck+Math.PI/2;
            // pasties.add(go);
            if(jump < 0){
                jump = 1;
                x = x+12*Math.sin(neck);
                z = z+12*Math.cos(neck);
            }

        }
        // if((TimeLockingKey.keys.get(GLFW_KEY_1).isNewPress())){
        //     if( Input.simulatedControlEnabled){
        //         if(lastCmd != null){
        //             try{
        //                 Runtime.
        //                 getRuntime().
        //                 exec(lastCmd);
        //             }catch(Exception e){
        //                 e.printStackTrace();
        //             }
        //         }
        //     }else{
        //         if(!followmode){
        //             // glfwShowWindow(topWindow);
        //         }
        //     }
        // }
        boolean holdingController = !bobbies.isEmpty() && bobbies.get(0) instanceof ControllerNodule; 

        if(holdingController){
            var cn = (ControllerNodule)bobbies.get(0);
            if(TimeLockingKey.keys.get(GLFW_KEY_1).isNewPress()){
                cn.goBack();
            }
            if(TimeLockingKey.keys.get(GLFW_KEY_2).isNewPress()){
                cn.goForward();
            }
        }
        boolean holdingPortal = !bobbies.isEmpty() && bobbies.get(0) instanceof PortalNodule; 
        if(holdingPortal){
            var cn = (PortalNodule)bobbies.get(0);
            if(TimeLockingKey.keys.get(GLFW_KEY_1).isNewPress()){
                cn.activate();
            }
        }
        {
            if(grid_lock_mode){
                if(TimeLockingKey.keys.get(GLFW_KEY_A).isNewPress()){
                    neck-=Math.PI/2;
                }
                if(TimeLockingKey.keys.get(GLFW_KEY_D).isNewPress()){
                    neck+=Math.PI/2;
                }
                if(TimeLockingKey.keys.get(GLFW_KEY_Q).isNewPress()){
                    x += Math.sin(neck-Math.PI/2)*128;
                    z += Math.cos(neck-Math.PI/2)*128;
                }
                if(TimeLockingKey.keys.get(GLFW_KEY_E).isNewPress()){
                    x += Math.sin(neck+Math.PI/2)*128;
                    z += Math.cos(neck+Math.PI/2)*128;
                }
                if(TimeLockingKey.keys.get(GLFW_KEY_W).isNewPress()){
                    x += Math.sin(neck)*128;
                    z += Math.cos(neck)*128;
                }
                if(TimeLockingKey.keys.get(GLFW_KEY_S).isNewPress()){
                    x -= Math.sin(neck)*128;
                    z -= Math.cos(neck)*128;
                }
            }
            if(TimeLockingKey.keys.get(GLFW_KEY_5).isNewPress()){
                automatic_random_mode = !automatic_random_mode;
                globalNav.clear();
                addStatement("automated random mode: " + automatic_random_mode);
            }
            if(TimeLockingKey.keys.get(GLFW_KEY_6).isNewPress()){
                global_nav_timer_milis = (global_nav_timer_milis*2)%1550;
                addStatement("grid speed: " + global_nav_timer_milis);
            }
            if(TimeLockingKey.keys.get(GLFW_KEY_7).isNewPress()){
                addStatement(".");
            }
            if(TimeLockingKey.keys.get(GLFW_KEY_8).isNewPress()){
                grid_lock_mode = !grid_lock_mode;
                if(grid_lock_mode){
                    // lock to position

                    neck = 0;
                    chin = 0;
                    x = (int)(x/128)*128+64;
                    z = (int)(z/128)*128+64;
                }
            }
            if(TimeLockingKey.keys.get(GLFW_KEY_9).isNewPress()){
                data_sizes_mode = !data_sizes_mode;
            }
            if(TimeLockingKey.keys.get(GLFW_KEY_0).isNewPress()){
                random_blinking = !random_blinking;
            }

            // if(TimeLockingKey.keys.get(GLFW_KEY_1).isNewPress()){
            //     Spriter.blinkValue = 0;
            // }
            // boolean[] pressed = new boolean[]{
            //     TimeLockingKey.keys.get(GLFW_KEY_1).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_1).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_2).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_2).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_3).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_3).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_4).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_4).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_5).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_5).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_6).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_6).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_7).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_7).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_8).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_8).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_9).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_9).isNewPress(),
            //     TimeLockingKey.keys.get(GLFW_KEY_0).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_0).isNewPress(),
            // };
            
        }
        // boolean[] pressed = new boolean[]{
        //     TimeLockingKey.keys.get(GLFW_KEY_1).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_1).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_2).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_2).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_3).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_3).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_4).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_4).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_5).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_5).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_6).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_6).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_7).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_7).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_8).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_8).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_9).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_9).isNewPress(),
        //     TimeLockingKey.keys.get(GLFW_KEY_0).isNewPress() || TimeLockingKey.keys.get(GLFW_KEY_KP_0).isNewPress(),
        // };
        // for(int i = 0; i < pressed.length; i++){
        //     if(pressed[i]){
        //         if(Input.simulatedControlEnabled){
        //             if(globalTenPortals == null){
        //                 globalTenPortals = TenPortals.newEmpty();
        //             }
        //             activeTenPortals = globalTenPortals;
        //             PortalNodule pn = new PortalNodule();
        //             pn.chin = chin;
        //             pn.nangle = neck;
        //             pn.destx = x;
        //             pn.destz = z;
        //             pn.chary = dispy;
        //             if(neededTenPortals != null){
        //                 neededTenPortals.portalNodules[i] = pn;

        //             }else{
        //                 globalTenPortals.portalNodules[i] = pn;
        //             }
        //         }else{
        //             if(activeTenPortals == null){
        //                 activeTenPortals = TenPortals.newEmpty();
        //             }
        //             if(activeTenPortals.portalNodules != null &&
        //                 activeTenPortals.portalNodules[i]!= null){
        //                 activeTenPortals.portalNodules[i].activate();
        //             }
        //         }
        //     }
        // }
        var ykey = (TimeLockingKey.keys.get(GLFW_KEY_Y).isNewPress());
        if(ykey && !Input.simulatedControlEnabled){
            // PortalNodule.lastchin =      Main.chin;
            // PortalNodule.lastnangle =    Main.neck;
            // PortalNodule.lastx =         Main.x;
            // PortalNodule.lastz =         Main.z;
            PortalNodule.newReturn();

            // Teleport to world origin
            int x = (int)Main.x-1_000_000;
            int y = (int)Main.z-1_000_000;
            x=x/4096*4096+2048+1_000_000;
            y=y/4096*4096+2048+1_000_000;
            Main.x = x;
            Main.z = y;
            neck = 0;
            chin = 0;
            
        }
        if(ykey && Input.simulatedControlEnabled){
            if(globalRecording == null){
                try{
                    if(line == null){
                        // format = new AudioFormat(11000f*4, 1*8, 1, true, true);
                        mixerInfo = AudioSystem.getMixerInfo();
                        info = new DataLine.Info(TargetDataLine.class, format);
                        
                        for(int i = 0; i < mixerInfo.length; i++){
                        Mixer mixer = AudioSystem.getMixer(mixerInfo[i]);
                            try{
                            line = (TargetDataLine) mixer.getLine(info);
                            }catch(Exception e){
                            }
                        }
                    }
                    line.open(format);
                    line.start();
                    recordBuffer.clear();
                    var go = new RecordNodule();
                    go.ydisp = y-4;
                    go.x = x+10*Math.sin(neck);
                    go.z = z+10*Math.cos(neck);
                    go.rot = neck+Math.PI/2;
                    pasties.add(go);
                    globalRecording = go;
                    go.bytes = new byte[0];
                    textSheet3.repaunch = false;
                    textSheet3.clearRepaunch();
                    textSheet.clearRepaunch();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else if(globalRecording != null){
                if(globalRecording.bytes != null){
                    byte[] a = new byte[recordBuffer.position()];
                    recordBuffer.flip();
                    recordBuffer.get(a);
                    globalRecording.bytes = a;
                    p("DONE recording; wrote out " +globalRecording.bytes.length);
                    p("DONE recording; wrote out " +globalRecording.recordTime);
                    pasties.remove(globalRecording);
                    globalRecording = null;
                    //WOOT
                    textSheet.repaunch = true;
                    textSheet.clearRepaunch();

                    byte[] aa = a.clone();
                    {
                        ByteBuffer bb = SimpleTri.main(null);
                        bb.flip();
                        GameObject go = null;
                        {
                            try{
                                ParkerBuffer pb = new ParkerBuffer(bb);
                                ThreeDumb p = new ThreeDumb();
                                p.pointsCt = pb.getInt();
                                p.subdivs = pb.getInt();
                                int datamode = pb.getInt();
                                System.out.println("`ED DATA MODE IS " + datamode);
                                BufferedImage bi = null;
                                if(datamode == 1){
                                    int width = pb.getInt();
                                    int height = pb.getInt();
                                    pb.getInt();
                                    int[] texdat = pb.getIntArr();
                                    p.sprite = new Spriter(texdat, width, height, p.sl(), p.mso());
                                    p.sprite.repaunch = true;
                                    p.data = texdat;
                                    p.width = width;
                                    p.height = height;
                                    p.whrat = width*1.0/height;
                                }
                                pb.getInt();
                                {
                                    int sosOption = pb.getInt();
                                    if(sosOption == 1){
                                        p.sos = pb.getIntArr();
                                    }else{
                                        p.sos = new int[p.pointsCt];
                                    }
                                }
                                double values[] = pb.getDoubleArr();
                                {
                                    p.values = values;
                                    p = p.promote(aa);
                                    ((BurningBush)p).cycleAsProportion = 2;
                                    ((BurningBush)p).offsetAsProportion = 2;
                                    ((BurningBush)p).displayMode = 2;
                                    go = p;
                                    go.x = x;
                                    go.z = z;
                                    go.ydisp = dispy+y;
                                    go.rot = neck+Math.PI/2;
                                    if(!lock())
                                        pasties.add(go);
                                }
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                }else{
                    //drop; do not save
                }
            }
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_GRAVE_ACCENT).isNewPress()) || (TimeLockingKey.keys.get(GLFW_KEY_KP_ADD).isNewPress())){
            if(central_dogma == null || Input.simulatedControlEnabled){
                PortalNodule.lastchin =      Main.chin;
                PortalNodule.lastnangle =    Main.neck;
                PortalNodule.lastx =         Main.x;
                PortalNodule.lastz =         Main.z;
                PortalNodule.newReturn();
                chin = 0;
                neck = 0;
                x = 1_000_000+2000;
                z = 1_000_000+2000;
                PortalNodule.noise();
            }else{
                central_dogma.activate();
            }
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_PERIOD).isNewPress())){
            if(!Input.simulatedControlEnabled){
                intoInventory(new FolderNodule());
            }else{
                x = 1_200_000 ;
                z = 1_000_000 ;
            }
        }

        if((TimeLockingKey.keys.get(GLFW_KEY_COMMA).isNewPress())){
            if(Input.simulatedControlEnabled){
                var go = new Searcher();
                go.ydisp = y + dispy;
                go.x = x+12*Math.sin(neck);
                go.z = z+12*Math.cos(neck);
                go.rot = neck+Math.PI/2;
                pasties.add(go);
                TimeLockingKey.globalTypingSwitch = go;
                textSheet3.repaunch = false;
                textSheet3.clearRepaunch();
                textSheet.clearRepaunch();
            }else{
                Spriter.ClickTrack.mode = !Spriter.ClickTrack.mode; 
            }
            // if(!Input.simulatedControlEnabled){
            //     intoInventory(new FolderNodule());
            // }else{
            //     x = 1_200_000 ;
            //     z = 1_000_000 ;
            // }

        }

        if((TimeLockingKey.keys.get(GLFW_KEY_BACKSLASH).isNewPress())){
            if(!Input.simulatedControlEnabled){
                walkthrough_begin = !walkthrough_begin;
                if(walkthrough_begin){
                    walkthrough = new LinkedList<Tuple<Long, PortalNodule>>();
                    walkthrough_begin_stamp = System.currentTimeMillis();
                }else{
                    // PINCH THE LOAF
                    var fp = new FollowPath();
                    fp.movements = walkthrough;
                    intoInventory(fp);
                }
            }else{
                walkthrough_replay_on = !walkthrough_replay_on;
                if(walkthrough_replay_on){
                    walkthrough_replay = (LinkedList<Tuple<Long, PortalNodule>>)walkthrough.clone();
                    walkthrough_replay_stamp = System.currentTimeMillis();
                }
            }
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_SLASH).isNewPress())){
            pasties.query2D(x-1000, x+1000, z-1000, z+1000);

            if(!Input.simulatedShiftEnabled){
                //nevermind, it's going to be harder,
                //will need multiples of ghostx/y/z, not just 1, unless I collapse it??
                //on the existing???
                for(var idk: ghostSpriters){
                    idk.cleanUp();
                }
                ghostSpriters.clear();
                ghost_subs.clear();
                ghost_pos.clear();
                // System.out.println("SHOULD NOT BE HEAR");
                // System.exit(0);
            }
            if(!Input.simulatedControlEnabled){
                HashSet<Spriter> ghost_sub = new HashSet<Spriter>(); 
                for(int i = 0; i < pasties.size(); i++){
                    GameObject go = pasties.getAt(i);
                    if(go.getClass() == Pasty.class){
                        ghostAssignment = true;
                        if(((Pasty)go).sprite != null){
                            ghostSpriters.add(((Pasty)go).sprite);
                            ghost_sub.add(((Pasty)go).sprite);
                        }
                    }
                }
                if(ghostAssignment){
                    ghost_subs.addLast(ghost_sub);
                }

            }
        }
        if(Main.APOSTROPHE_MODE && (TimeLockingKey.keys.get(GLFW_KEY_APOSTROPHE).isNewPress())){
            p("APOSTROPHE_MODE running arbfunc");
            arbfunc.run();
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_H).isNewPress())){
            if(nearest instanceof Pasty){
                pasties.remove(nearest);
                var p = (Pasty)nearest.clone();
                var o = (Pasty)nearest;
                
                p.sprite = new Spriter(o.data, o.height, o.width, p.sl(), p.mso());
                p.width = o.height;
                p.height = o.width;
                p.whrat = 1/o.whrat;
                for(int i = 0; i < o.width; i++){
                    for(int j = 0; j < o.height; j++){
                        p.data[j + i*o.height] = o.data[o.width-1-i+j*o.width];
                    }
                }
                p.x = o.x;
                p.z = o.z;
                p.rot = o.rot;
                p.ydisp = o.ydisp;
                pasties.add(p);
                p.load();
                nearest.clean();
            }
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_G).isNewPress())){
            if(Input.simulatedControlEnabled){
                // PortalNodule.returnal();
                if(Input.simulatedShiftEnabled){
                    PortalNodule.doReReturnal();
                }else{
                    PortalNodule.doReturn();
                }
            }else{
                ClipboardMonitor.insertionMode = !ClipboardMonitor.insertionMode;
                if(DragonDrop.specialCheckBox != null)
                    DragonDrop.specialCheckBox.setSelected(ClipboardMonitor.insertionMode); 
            }
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_O).isNewPress())){
            // var go = new ShinyBauble();
            // go.ydisp = y;
            // go.x = x+12*Math.sin(neck);
            // go.z = z+12*Math.cos(neck);
            // go.rot = neck+Math.PI/2;
            // pasties.add(go);

            PortalNodule pn = new PortalNodule();
            pn.chin = chin;
            pn.nangle = neck;
            pn.destx = x;
            pn.destz = z;
            pn.chary = dispy;
            if(Input.simulatedControlEnabled){
                central_dogma = pn;
            }else{
                intoInventory(pn);
            }
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_K).isNewPress())){
            if(Input.simulatedControlEnabled){
                multiplechoicetypingswitch = new TextNodule();
                TimeLockingKey.globalTypingSwitch = multiplechoicetypingswitch;
            }
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_L).isNewPress())){
            if(grid_lock_mode && !Input.simulatedControlEnabled){

                gridOperation(null);
            }else{
                addStatement("loading all worldlinks");
                if(Input.simulatedControlEnabled){
                    fuzzycursorposition = 0;
                    fuzzytypingswitch = new TextNodule();
                    TimeLockingKey.globalTypingSwitch = fuzzytypingswitch;
                    exacts.clear();
                    // queryall
                    pasties.queryAll();

                    for(int i = 0; i < pasties.size(); i++){
                        GameObject p = pasties.getAt(i);
                        if(p instanceof Searcher){
                            var idk = ((Searcher) p).getWord();
                            
                            if(idk != null && !idk.equals("")){
                                exacts.put(idk, (Searcher) p);
                            }else{
                                System.out.println("yeah, here");
                                temporarilyNeededGameObjects.add(p);
                                // System.exit(0);
                            }
                        }
                        // GameObject p = iterator.next();
                        // if(p.getSectorKey() == xy(flx7, flz7)){
                        //     pasties.remove(p);
                        // }
                    }
                    {
                        // make if doesn't exist
                        System.out.println("HERE1");
                        var f = (new File("DumpFolderNiches"));
                        if(!f.exists()){
                            f.mkdirs();
                            System.out.println("HERE2");
                        }
                        {
                            // now i need the list of all of the whatevers
                            // where can i get that?

                            List<String> words = new ArrayList<>(Main.exacts.keySet());

                            for(var word: words){
                                System.out.println("HERE3 " + word);

                                try{
                                    f = (new File("DumpFolderNiches/"+word));
                                    if(!f.exists()){
                                        f.mkdirs();
                                    }
                                }catch(Exception e){e.printStackTrace();}
                            }
                        }
                    }

                }else{
                    var go = new LinkNodule();
                    go.ydisp = y;
                    go.x = x+12*Math.sin(neck);
                    go.z = z+12*Math.cos(neck);
                    go.rot = neck+Math.PI/2;
                    pasties.add(go);
                    TimeLockingKey.globalTypingSwitch = go;
                    textSheet3.repaunch = false;
                    textSheet3.clearRepaunch();
                    textSheet.clearRepaunch();
                    // TimeLockingKey.whatever 
                }
            }

            
        }
        if((TimeLockingKey.keys.get(GLFW_KEY_B).isNewPress())){
            // var go = new IdeaBeacon();
            // go.ydisp = y;
            // go.x = x+12*Math.sin(neck);
            // go.z = z+12*Math.cos(neck);
            // go.rot = neck+Math.PI/2;
            // pasties.add(go);
            // bobbies
            // Collections.reverse(bobbies);
            if(!bobbies.isEmpty()){
                int theotherone = (bobbyselection+1)%(2);
                var otherbobbies = invStore[theotherone];
                otherbobbies.addFirst(bobbies.remove(0));
            }
            // if(bobbies.size() >= 1){
            //     tabselection = 0;
            // } 
        }
        // if((TimeLockingKey.keys.get(GLFW_KEY_B).isNewPress())){
        //     var go = new IdeaBeacon();
        //     if(!outerLock){
        //         boolean found = pasties.remove(nearest);
        //         if(found){
        //             p("removed!");
        //             skysigns.addLast(nearest);
        //             go.ydisp = y+10;
        //         }else{
        //             found = skysigns.remove(nearest);
        //             if(found){
        //                 go.ydisp = y-10;
        //                 pasties.add(nearest);
        //             }
        //         }
        //     }
        //     // go.x = x+12*Math.sin(neck);
        //     // go.z = z+12*Math.cos(neck);
        //     // go.rot = neck+Math.PI/2;
        // }
        // if((TimeLockingKey.keys.get(GLFW_KEY_R).isNewPress() && Input.simulatedControlEnabled &&  Input.simulatedShiftEnabled)){
        //     // if(dellies.size() != 0)
        //     // bobbies.shuffle()
        //     p("beep");
        //     // Collections.shuffle(bobbies);
        //         // intoInventory(dellies.pop());
        // }
        var zkey = TimeLockingKey.keys.get(GLFW_KEY_Z).isNewPress();
        if(zkey){
            if(Input.simulatedControlEnabled){
                if(dellies.size() != 0){
                    var idk = dellies.pop();
                    if(idk != null)
                        intoInventory(idk);
                }
            }else{
                // Main.FINE_MODE = !Main.FINE_MODE;
                // change hands
                bobbyselection = (bobbyselection+1)%(2);
                int offhandsel = (bobbyselection+1)%(2);
                // bobbyselection = (bobbyselection+1)%(invStore.length);
                bobbies = invStore[bobbyselection];
                offhand = invStore[offhandsel];
                // if(bobbies.size() >= 1){
                //     tabselection = 0;
                // } 
            }
        }
        var ckey = TimeLockingKey.keys.get(GLFW_KEY_C).isNewPress();
        // if((ckey && Input.simulatedControlEnabled) || TimeLockingKey.keys.get(GLFW_KEY_PAGE_UP).isNewPress()){ 
        if(ckey){ 
            if(nearest != null && !(nearest instanceof PromiseNodule)){
                if(Input.simulatedControlEnabled){
                    // insertionMode = false;
                    if(nearest instanceof Pasty){
                        var p = (Pasty)nearest;
                        BufferedImage bi = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                        for(int i = 0; i < p.width; i++){
                            for(int j = 0; j < p.height; j++){
                                bi.setRGB(i, j, p.data[i+j*p.width]);
                            }
                        }
                        ClipboardMonitor.previouslySeen = bi; // ideal solution!!!
                        try{
                            TransferableImage trans = new TransferableImage(bi);
                            clipboard.setContents(trans, null);
                        }
                        catch(Exception e){e.printStackTrace();}

                        if(p instanceof Shader){
                            var uhm = (Shader)p;
                            System.out.println();
                            p(uhm.fragShaderArtifact);
                        }
                    }
                    if(nearest instanceof FileNodule){
                    }
                }else if(Input.simulatedShiftEnabled){
                    if(nearest instanceof Pasty){
                        // open as 4chan pastable
                        neededFileNodules.clear(); // will stop it opening
                        ((Pasty)nearest).openIntoVirtualFolder();
                    }
                    // if(nearest instanceof FileNodule){
                    //     var f = (new File("AdditiveFolder"));
                    //     if(!f.exists()){
                    //         f.mkdirs();
                    //     }
                    //     try{
                    //         var fn = (FileNodule) nearest;
                    //         if(fn.state != null){
                    //             var val = fn.state.stringify().split("\\R", 2)[0];
                    //             if(!val.equals("")){
                    //                 String out = "./AdditiveFolder/"+val;
                    //                 // System.out.println("out "  + out);
                    //                 FileOutputStream fileOutputStream = new FileOutputStream(out);
                    //                 fileOutputStream.write(fn.bytes);
                    //                 fileOutputStream.close();
                    //                 java.awt.Desktop.getDesktop().browse(f.toURI());
                    //             }else{
                    //                 System.out.println("fn " + fn + " is seen as empty string\"\"");
                    //             }
                    //         }else{
                    //             System.out.println("FileNodule state is null");
                    //         }
                    //     }catch(Exception e){
                    //         e.printStackTrace();
                    //     }
                    // }
                }else{
                    intoInventory(nearest.clone());
                }
            }
        }
        // if((TimeLockingKey.keys.get(GLFW_KEY_X).isNewPress() && Input.simulatedControlEnabled) || TimeLockingKey.keys.get(GLFW_KEY_SPACE).isNewPress()){
        if(TimeLockingKey.keys.get(GLFW_KEY_SPACE).isNewPress()){
        
            // if(Input.simulatedControlEnabled){
            //     int flx7 = (int)(x/128);
            //     int flz7 = (int)(z/128);
            //     {
            //         // NUKE
            //         pasties.queryAll();
            //         for(int ii = 0; ii < pasties.size(); ii++){
            //             GameObject p = pasties.getAt(ii);
            //             // GameObject p = iterator.next();
            //             if(p.getSectorKey() == xy(flx7, flz7)){
            //                 pasties.remove(p);
            //             }
            //         }
            //     }
            // }
            // System.exit(0);
            // Find rendesvouz
            //rendezvous
            if(Input.simulatedControlEnabled){
                // for(int gg = 0; gg < 7; gg++){
                //     for(int hh = 0; hh < 7; hh++){
                //         double ggg = (gg - 3)*128+x;
                //         double hhh = (hh - 3)*128+z;
                //         int rendx = (int)(ggg/128)*128+64;
                //         int rendz = (int)(hhh/128)*128+64;
                //         pasties.query2D(rendx-0.1, rendx+0.1, rendz-0.1, rendz+0.1);
                //         ThreeDumb find = null;
                //         for(int i = 0; i < pasties.size(); i++){
                //             GameObject go = pasties.getAt(i);
                //             if(go instanceof ThreeDumb){
                //                 find = (ThreeDumb)go;
                //                 break;
                //             }
                //         }
                //         if(find == null){
                //             // pasties.remove(find);
                //             var f = floor.clone();
                //             f.x = rendx;
                //             f.z = rendz;
                //             PURGATORY.add(f);
                //         }
                //     }
                // }
            }
        }
        if(TimeLockingKey.keys.get(GLFW_KEY_X).isNewPress()){ 
            if(nearest != null){
                pasties.remove(nearest);
                // tabselection = 0;
                var i = nearest.clone();
                nearest.clean();
                intoInventory(i);
            }
            // if(tabselection == -1){
            //     if(!outerLock){
            //     }
            // }else{
            //     if(!bobbies.isEmpty()){
            //         var b = bobbies.get(tabselection);
            //         if(b instanceof PortalNodule){
            //             ((PortalNodule)b).activate();
            //         }
            //         if(b instanceof ChestNodule){
            //             ((ChestNodule)b).activate();
            //         }
            //         if(b instanceof KeyNodule){
            //             ((KeyNodule)b).activate();
            //         }
            //     }
            // }
        }
        if((esckey && Input.simulatedShiftEnabled)){ 
            // Clear entire inventory
            bobbies.clear();
            tabselection = -1;
        }
        if(TimeLockingKey.keys.get(GLFW_KEY_LEFT_BRACKET).isNewPress()){
            // var f = (new File("DumpFolder"));
            // try{
            //     java.awt.Desktop.getDesktop().browse(f.toURI());
            // }catch(Exception e){
            //     System.out.println("ERROR OPENING FILE");
            //     e.printStackTrace();
            // }
            // addStatement("HI! " + System.currentTimeMillis());
            // try{
            //     Thread.sleep(4000);
            //     demandFocusBackFromBrowser();
            // }catch(Exception e){

            // }
            // ParkerSetter.GridBrowserPanel.stupidasstest();
            addStatement("HI! " + ((int)(x-1_000_000))/128 + " " + ((int)(z-1_000_000))/128);
            // pasties.query2D(x-50, x+50, z-50, z+50);
            // ByteBuffer bb = ByteBuffer.allocate(1_000_000);
            // // ParkerBuffer pb = new ParkerBuffer(bb);
            // {
            //     ParkerBuffer pb =
            //         new ParkerBuffer(true, "fuckmylife.dat", bb);
            //     pb.putInt(pasties.size());
            //     System.out.println("writing "  +pasties.size());
            //     for(int i = 0; i < pasties.size(); i++){
            //         GameObject pp = pasties.getAt(i);
            //         GameObject.marshal(pb, pp, true);
            //     }
            //     pb.finalizeWritingFile();
            // }
            // bb.clear();
            // {
            //     ParkerBuffer pb =
            //         new ParkerBuffer(false, "fuckmylife.dat", bb);
            //     int count = pb.getInt();
            //     System.out.println("reading "  +count);
            //     for(int i = 0; i < count; i++){
            //         var go = GameObject.unmarshal(pb, true);
            //         System.out.println("one " + go.getClass());
            //         go.x += 2;
            //         go.z += 2;
            //         pasties.add(go);
            //         go.load();
            //     }
            // }
        }
        if(TimeLockingKey.keys.get(GLFW_KEY_RIGHT_BRACKET).isNewPress()){
            // CHEST TIME
            {
                try{
                
                    System.out.println("DUUUDE");
                    ParkerBuffer pb = new ParkerBuffer(true, "VirtualFolder/serialdump");
                    var ii = bobbies.iterator();
                    while(ii.hasNext()){
                        pb.putInt(1);
                        var k = ii.next();
                        GameObject.marshal(pb, k, true);
                    }
                    pb.putInt(0);
                    pb.finalizeWritingFile();

                    var f = (new File("VirtualFolder"));
                    java.awt.Desktop.getDesktop().browse(f.toURI());
                }catch(Exception e){
                    System.out.println("ERROR OPENING FILE");
                    e.printStackTrace();
                }
            }
                // int pos = pb.current.position();
                // p("done writing " + pos);
                // var na = Arrays.copyOf(pb.current.array(), pos);
                // pb.putByteArr(b);
                // var cn = new ChestNodule();
                // cn.serialdump = na;
                // intoInventory(cn);
            

        }
        if(TimeLockingKey.keys.get(GLFW_KEY_F).isNewPress()){
            boolean holdingFunnel = (bobbies.size() >= 1) && bobbies.get(0) instanceof FunnelNodule; 
            if(holdingFunnel){
                var fn = (FunnelNodule)bobbies.get(0);
                if(nearest instanceof PhantomNodule){
                    fn.phantoms.remove(nearest);
                }else{
                    PhantomNodule go = new PhantomNodule();
                    go.ydisp = y + dispy;
                    go.x = x+12*Math.sin(neck)+Math.random()/100000; // to fix the positioning
                    go.z = z+12*Math.cos(neck);
                    go.rot = neck+Math.PI/2;
                    fn.phantoms.addLast(go);
                }
            }else{
                try{
                    Pasty p = new Pasty();
                    Transferable t = clipboard.getContents(null);
                    Image i = (Image)t.getTransferData(DataFlavor.imageFlavor);
                    BufferedImage bi = imageToBufferedImage(i);
                    p.sprite = new Spriter(bi, p.sl(), p.mso());
                    p.width = bi.getWidth();
                    p.height = bi.getHeight();
                    p.data = p.sprite.tempIntDataForStorage;
                    // p.sprite.tempIntDataForStorage = null;
                    p.whrat = bi.getWidth()*1.0/bi.getHeight();
                    {
                        
                    }
                    intoInventory(p);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    if(!lock()&&!outerLock){ //just added this
        if(TimeLockingKey.keys.get(GLFW_KEY_Q).isNewPress() && Input.simulatedControlEnabled){ 
            p("q press, killing...");
            running = false;
        }
    }
    var tab = TimeLockingKey.keys.get(GLFW_KEY_TAB).isNewPress();
    if(tab){
        if(Input.simulatedControlEnabled){
            //   if(!bobbies.isEmpty())
            //         intoInventory(bobbies.remove(0));
             if(!bobbies.isEmpty())
                bobbies.addFirst(bobbies.removeLast());
        }else{
            if(!bobbies.isEmpty())
                bobbies.addLast(bobbies.remove(0));
        }
        //     if(!Input.simulatedShiftEnabled){
                // tabseflection = -1;
                // bobbyselection = (bobbyselection+1)%(2);
                // // bobbyselection = (bobbyselection+1)%(invStore.length);
                // bobbies = invStore[bobbyselection];
                // if(bobbies.size() >= 1){
                //     tabselection = 0;
                // } 
                // if(!bobbies.isEmpty())
                //     intoInventory(bobbies.remove(0));
            // }else{
                // tabselection = -1;
                // bobbyselection = (bobbyselection - 1 + 2)%(2);
                // // bobbyselection = (bobbyselection - 1 + invStore.length)%(invStore.length);
                // bobbies = invStore[bobbyselection];
                // if(bobbies.size() >= 1){
                //     tabselection = 0;
                // } 
        //     }
        // }else{
        //     if(!Input.simulatedShiftEnabled){
        //         tabselection = (tabselection+2)%(bobbies.size()+1) + -1;
        //     }else{
        //         tabselection = tabselection-1;
        //         if(tabselection == -2) tabselection = bobbies.size()-1;
        //     }
        // }
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_ENTER).isNewPress()){
        if(nearest instanceof TextNodule){
            TimeLockingKey.globalTypingSwitch = (TextNodule)nearest;
            textSheet3.repaunch = false;
            textSheet3.clearRepaunch();
            textSheet.clearRepaunch();
        }
    }
    boolean tkey = TimeLockingKey.keys.get(GLFW_KEY_T).isNewPress();
    if(!lock() && tkey){
            var tn = new FileNodule();
            tn.state = new State();
            tn.bytes = new byte[0];
            
            tn.ydisp = y + dispy;
            tn.x = x+12*Math.sin(neck);
            tn.z = z+12*Math.cos(neck);
            tn.rot = neck+Math.PI/2;
            pasties.add(tn);
            TimeLockingKey.globalTypingSwitch = tn;
            textSheet3.repaunch = false;
            textSheet3.clearRepaunch();
            textSheet.clearRepaunch();
        if(Input.simulatedControlEnabled){
        }else{
        }

    }
    // if(!lock() && tkey && Input.simulatedControlEnabled){
    // if(false){
    //     // if(nearest instanceof TextNodule2){
    //     //     var t =((TextNodule2)nearest);
    //     //     if(t.mode != 0){
    //     //         t.mode = 0;
    //     //     }else{
    //     //         t.mode = 1;
    //     //     }
    //     // }
    //     // what is my current row?
    //     //invstore
    //     bobbies = invStore[bobbyselection];
    //     // gather pasties
    //     var pasties = new LinkedList<Pasty>();
    //     try{
    //         for(var b: bobbies){
    //             if(b instanceof Pasty){
    //                 pasties.add((Pasty)b);
    //             }
    //         }
    //         int[] output = new int[1650*1275];
    //         for(int i = 0; i < 1650; i++){
    //             for(int j = 0; j < 1275; j++){
    //                 output[j*1650+i] = 0xffff_ffff;
    //             }
    //         }
    //         if(pasties.size() != 0){
    //             // prepare to shrink and do it.
    //             int eachheight = 1000/pasties.size();
    //             // resize each pasty to height while rastering
    //             {
    //                 var bi = makeBI(3300,2550);
    //                 // Create a list of images
    //                 List<ParkerImage> images = new ArrayList<>();
    //                 // images.add(new ParkerImage(new int[] {1, 2, 3, 4, 5, 6, 7, 0xff_00_ffff}, 4, 2));
    //                 // images.add(new ParkerImage(new int[] {1, 2, 3, 4, 5, 6, 7, 8, 0xff_ff_ffff, 10, 11, 12}, 4, 3));
    //                 // images.add(new ParkerImage(new int[] {1, 2, 3, 4, 5, 0xff_00_00ff, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16}, 4, 4));
    //                 for(var p: pasties){
    //                     images.add(new ParkerImage(p.data, p.width, p.height));
    //                 }

    //                 // Compute the aspect ratios for the images
    //                 List<Double> aspectRatios = new ArrayList<>();
    //                 for (ParkerImage image : images) {
    //                     aspectRatios.add((double) image.width / image.height);
    //                     p("first image aspect is " + ((double) image.width / image.height));
    //                 }
                    
    //                 List<Rectangle> packedRectangles = arrangeAspectRatios(aspectRatios, 3300, 2550);

    //                 // Create an array to hold the rastered image data
    //                 int[] rasteredData = new int[3300 * 2550];

    //                 // Raster each packed rectangle into the rastered image
    //                 for (int i = 0; i < packedRectangles.size(); i++) {
    //                     Rectangle rect = packedRectangles.get(i);
    //                     ParkerImage image = images.get(i);
    //                     p("new rect size is" + rect.width + " "  + rect.height + " and offset: " + rect.x + " " + rect.y);
    //                     int[] refitData = refit(image.data, image.width, image.height, rect.height);
    //                     rasterInto(rect.width, rect.height, refitData, 3300, 2550, rasteredData, rect.x, rect.y);
    //                 }
                    
    //                 for(int i = 0; i < 3300; i++){
    //                     for(int j = 0; j < 2550; j++){
    //                         bi.setRGB(i, j, 0xffff_ffff);
    //                     }
    //                 }
    //                 for(int i = 0; i < 3300; i++){
    //                     for(int j = 0; j < 2550; j++){
    //                         bi.setRGB(i, j, rasteredData[j*3300+i]);
    //                     }
    //                 }
    //                 p("OK WELL WHATEVER - IT'S OVER");
    //                 insertionMode = false;
    //                 dumpClipboard(bi);
    //             }
    //             for(var p: pasties){
    //                 // p.width;
    //                 // {
    //                 // }
    //                 //place one and break
    //                 int newHeight = 500;
    //                 int[] newdat = refit(p.data, p.width, p.height, newHeight);
    //                 int newWidth = p.width*500/p.height;
                    
    //                 rasterInto(newWidth, newHeight, newdat,
    //                     1650, 1275, output,
    //                     300, 300);
    //                 // for(int i = 0; i < 1650; i++){
    //                 //     for(int j = 0; j < 1275; j++){
    //                 //         bi.setRGB(i, j, output[j*1650+i]);
    //                 //     }
    //                 // }
    //                 insertionMode = false;
    //                 // dumpClipboard(bi);
    //                 break;
    //             }
    //         }
    //     }catch(Exception e){
    //         p("Error doing the printout thingy");
    //         e.printStackTrace();
    //         p("Error doing the printout thingy");
    //     }
    // }
    // if(TimeLockingKey.keys.get(GLFW_KEY_LEFT_BRACKET).isNewPress()){
    //     // size/= 1.5;
    //     // Main.chin   += Math.random()*2*Math.PI;
    //     // Main.neck   += Math.random()*2*Math.PI;
    //     // double kk = Main.neck + (Math.random()-0.5)*Math.PI/2;
    //     // double rad = Math.random()*300+100;
    //     // Main.x      += Math.cos(kk)*(rad);
    //     // Main.z      += Math.sin(kk)*rad;
    // }
    // if(TimeLockingKey.keys.get(GLFW_KEY_RIGHT_BRACKET).isNewPress()){
    //     // size*= 1.5;
    //     // Main.chin   += Math.random()*2*Math.PI;
    //     // Main.neck   += Math.random()*2*Math.PI;
    //     // double kk = Math.cos(Math.random()*Math.PI);
    //     double kk = Main.neck;// + (Math.random()-0.5)*2*Math.PI/2;
    //     // double rad = 20;
    //     double rad = Math.random()*300+100;
    //     Main.x      += Math.sin(kk)*(rad);
    //     Main.z      += Math.cos(kk)*rad;
    //     // var i =pasties.iterator();
    //     // while(i.hasNext()){
    //     //     var n = i.next();
    //     //     if(Math.random() > 0.8){
    //     //         n.sprite.cleanUp();
    //     //         i.remove();
    //     //     }
    //     // }
    // }
    // System.out.println("FUCK my life");

    if(TimeLockingKey.keys.get(GLFW_KEY_F1).isNewPress()){
        if(Input.simulatedShiftEnabled){
            var r = new Random();
            var pj = new PumpJack();
            pj.pjid = r.nextInt();
            intoInventory(pj);
            var pp = new PressurePlate();
            pp.pjid = pj.pjid;
            pp.c1 = pj.c1;
            intoInventory(pp);
        }else 
        if(!Input.simulatedControlEnabled){
            intoInventory(new Conveyor());
        }else{
            intoInventory(new TtsMachine());
        }
    }
    if(!lock()){
        // if((TimeLockingKey.keys.get(GLFW_KEY_V).isNewPress() && Input.simulatedControlEnabled) || TimeLockingKey.keys.get(GLFW_KEY_PAGE_DOWN).isNewPress()){
        if(TimeLockingKey.keys.get(GLFW_KEY_V).isNewPress()){
            boolean funnelsound = false;
            textSheet.clearRepaunch();
            {
                GameObject b = null;
                if(!bobbies.isEmpty()){
                    b = bobbies.removeFirst();
                    // tabselection = -1;
                    if(b instanceof ThreeDumb){
                        ((ThreeDumb)b).sprite.repaunch = true;
                    }
                }
                if(nearest instanceof FunnelNodule && !((FunnelNodule)nearest).phantoms.isEmpty()){
                    
                    GameObject go = null;
                    if(b != null){
                        go = b;
                        var fn = (FunnelNodule)nearest;
                        var ph = fn.phantoms.pop();
                        go.ydisp = ph.ydisp;
                        go.x = ph.x+Math.random()/100000; // to fix the positioning
                        go.z = ph.z;
                        go.rot = ph.rot;
                        // go.load();
                        // pasties.add(go);
                        PURGATORY.add(go);
                        funnelsound = true;
                    }
                }else{
                    try{
                        GameObject go = null;
                        if(b != null){
                            go = b;
                            go.ydisp = y + dispy;
                            go.x = x+12*Math.sin(neck)+Math.random()/100000; // to fix the positioning
                            go.z = z+12*Math.cos(neck);
                            if(go instanceof RecordNodule){
                                go.ydisp = y-4;
                                go.x = x+6*Math.sin(neck);
                                go.z = z+6*Math.cos(neck);
                            }
                            go.rot = neck+Math.PI/2;

                            if(nearest instanceof FileNodule && go instanceof Pasty && !(go instanceof ParkerQuizItem) && !(go instanceof ThreeDumb)  && ((FileNodule)nearest).hasAPasty == null){
                                var fn = (FileNodule)nearest;
                                fn.hasAPasty = (Pasty)go;
                            }else{
                                pasties.add(go);
                            }
                            if(go instanceof BurningBush)go.load();
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }

            {
                // long fulltime = (long)(bb.audioBytes.length /(44.1)/2);
                // if(fulltime != 0){
                    // var bb = poc_sfx;
                    var bb = !funnelsound ? poc_sfx: plop_sfx;
                    
                    bb.playing = true;

                    // System.out.println("fulltime is "  + fulltime);
                    // long clock = System.currentTimeMillis();
                    // long remainder = (clock%fulltime);
                    // System.out.println("remainder is  "  + remainder);
                    // double whereami = (remainder*1.0/fulltime);
                    // System.out.println("WHY ISN'T IT WORKING ???? " + bb.displayMode);
                    // System.out.println("whereami is  "  + whereami);
                    // System.out.println("going to try..." + (int)(whereami*bb.audioBytes.length));
                    {
                        // I want to delete it but NO!s
                        AL10.alDeleteBuffers(bb.loadedBuffer);
                        AL10.alDeleteSources(bb.loadedSource);
                        bb.loadedBuffer = AL10.alGenBuffers();
                        bb.loadedSource =  AL10.alGenSources();
                        // bb.fuckmebuffer.clear();
                        // bb.fuckmebuffer.put(bb.audioBytes);
                        // bb.fuckmebuffer.flip();
                    }
                    // bb.fuckmebuffer.position((int)(whereami*bb.audioBytes.length)/2*2);
                    bb.fuckmebuffer.position(0);
                    // bb.fuckmebuffer.position((int)(bb.fuckmebuffer.limit()*Math.random()));
                    // bb.fuckmebuffer.position(477520+2);
                    bb.reloadaaa();
                    bb.sprite.repaunch = false; // sometimes I am just SOO stupid

                    AL10.alSourcePlay(bb.loadedSource);
                    // bb.nextMillisToPlay = nownow + (long)(fulltime*(1-whereami));
                    // System.out.println("delay is ... " + (long)(fulltime*(1-whereami)));
                // }
            }
        }
    }
    // if(TimeLockingKey.keys.get(GLFW_KEY_R).isNewPress() && Input.simulatedControlEnabled){
    //     Collections.shuffle(bobbies);
    // }
    boolean f3 = TimeLockingKey.keys.get(GLFW_KEY_F3).isNewPress();
    boolean f4 = TimeLockingKey.keys.get(GLFW_KEY_F4).isNewPress();
    boolean f5 = TimeLockingKey.keys.get(GLFW_KEY_F5).isNewPress();
    boolean f6 = TimeLockingKey.keys.get(GLFW_KEY_F6).isNewPress();
    boolean f7 = TimeLockingKey.keys.get(GLFW_KEY_F7).isNewPress();
    boolean f8 = TimeLockingKey.keys.get(GLFW_KEY_F8).isNewPress();
    boolean f9 = TimeLockingKey.keys.get(GLFW_KEY_F9).isNewPress();
    boolean f10 = TimeLockingKey.keys.get(GLFW_KEY_F10).isNewPress();
    boolean f11 = TimeLockingKey.keys.get(GLFW_KEY_F11).isNewPress();
    boolean f12 = TimeLockingKey.keys.get(GLFW_KEY_F12).isNewPress();
    if(f3){
        DragonDrop.frame.setVisible(true);
        if(Input.simulatedControlEnabled){
            // process it ignoring the nightmare that is whatever
            var ee = recursiveFolderRead1("./DumpFolder2");
            for(var e: ee){
                Main.intoInventory(e);
            }
        }
    }
    if(f4){
        if(nearest instanceof FileNodule){
            var fn = (FileNodule)nearest;
            System.out.println("f4 on file nod");
            boolean holdingBush = !bobbies.isEmpty() && bobbies.get(0) instanceof BurningBush; 
            var bb = holdingBush ? (BurningBush)bobbies.get(0) : null;
            
            // if(holdingBush){
            //     System.out.println(bb.temporary_reapply_start_holder);
            //     System.out.println(bb.temporary_reapply_end_holder);
            //     System.exit(0);
            // } 
            boolean should = !holdingBush ? false:bb.temporary_reapply_start_holder != 0 || bb.temporary_reapply_end_holder != 0;
            double overridestart = !holdingBush? 0:bb.temporary_reapply_start_holder;
            double overrideend = !holdingBush? 0:bb.temporary_reapply_end_holder;
            Runnable arbfunc = () ->{
                var val = fn.state.stringify().split("\\R", 2)[0];
                String myname = "_"+System.currentTimeMillis()+val;
                String out = "./temp/"+myname;
                // System.out.println("out "  + out);
                String inputPath = out;
                long fulltime = 0;
                try{
                    FileOutputStream fileOutputStream = new FileOutputStream(out);
                    fileOutputStream.write(fn.bytes);
                    fileOutputStream.close();
                    if(should){
                        double seconds = 0;
                        {
                            ProcessBuilder processBuilder = new ProcessBuilder(
                                    "../../ffmpeg/ffprobe.exe", 
                                    "-v", "error",
                                    "-show_entries", "format=duration",
                                    "-of", "default=noprint_wrappers=1:nokey=1",
                                    inputPath
                            );
                            processBuilder.redirectErrorStream(true);
                            Process process = processBuilder.start();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                            String line;
                            double duration = 0.0;
                            while ((line = reader.readLine()) != null) {
                                try {
                                    duration = Double.parseDouble(line);
                                    seconds = duration;
                                    System.out.println("Duration: " + duration + " seconds");
                                } catch (NumberFormatException e) {
                                    System.err.println("Failed to parse the duration.");
                                    e.printStackTrace();
                                }
                            }
                            if (!process.waitFor(1, TimeUnit.MINUTES)) {
                                System.out.println("Process timeout. Terminating the process.");
                                process.destroy(); // Terminate the process
                            }
                            
                        }
                        System.out.println("Calculated");
                        System.out.println("seconds is " + seconds);
                        System.out.println("overridestart " + overridestart);
                        System.out.println("overrideend " + overrideend);
                        String formattedTime1 = null; //String.format("%02d:%02d:%06.3f", hours, minutes, seconds);
                        String formattedTime2 = null; //String.format("%02d:%02d:%06.3f", hours, minutes, seconds);
                        {
                            double targetTimeSeconds = bb.temporary_reapply_start_holder * seconds;
                            int hours = (int) targetTimeSeconds / 3600;
                            int minutes = (int) (targetTimeSeconds % 3600) / 60;
                            double seconds2 = targetTimeSeconds % 60;
                            formattedTime1 = String.format("%02d:%02d:%06.3f", hours, minutes, seconds2);
                        }
                        {
                            double targetTimeSeconds = (bb.temporary_reapply_end_holder-bb.temporary_reapply_start_holder) * seconds;
                            int hours = (int) targetTimeSeconds / 3600;
                            int minutes = (int) (targetTimeSeconds % 3600) / 60;
                            double seconds2 = targetTimeSeconds % 60;
                            formattedTime2 = String.format("%02d:%02d:%06.3f", hours, minutes, seconds2);
                        }
                        {
                            String myname2 = "_"+System.currentTimeMillis()+val+ ".mp4";
                            ProcessBuilder processBuilder = new ProcessBuilder(
                                "../../ffmpeg/ffmpeg.exe",
                                "-ss", formattedTime1,
                                "-t", formattedTime2,
                                "-i",
                                "./temp/"+myname,
                                "-c",
                                "copy",
                                "-f", "mp4",
                                "./temp/"+myname2 
                            );
                            processBuilder.redirectErrorStream(true);
                            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                            Process process = processBuilder.start();
                            try {
                                System.out.println("before");
                                if (!process.waitFor(60*3, TimeUnit.SECONDS)) {
                                    System.out.println("Process timeout. Terminating the process.");
                                    process.destroy(); // Terminate the process
                                } else {
                                    System.out.println("after");
                                }
                            } catch (InterruptedException e) {
                                Thread.currentThread().interrupt();
                            }
                            try {
                                Path sourcePath = Paths.get("./temp/"+myname2 );
                                long size2 = (new File("./temp/"+myname2 )).length();
                                fulltime = (long)(size2 /(44.1)/2);
                                
                                Path destinationPath = Paths.get("./DumpFolder/edit.mp4");
                                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        // System.exit(0);
                    }
                    String myname2 = "processme"+System.currentTimeMillis()+".wav";

                    ProcessBuilder processBuilder = new ProcessBuilder(
                        "../../ffmpeg/ffmpeg.exe",
                        "-i",
                        "./temp/"+myname,
                        "-vn",
                        "-acodec",
                        "pcm_s16le",
                        "-ar",
                        "44100",
                        "-ac", "1","./temp/"+myname2 
                    );


                    processBuilder.redirectErrorStream(true);
                    processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                    Process process = processBuilder.start();

                    // Handle the process's input/output streams if necessary

                    // yt-dlp -f "bestvideo+bestaudio[ext=m4a]/best" %Var1% -o temp.mp4
                    //ffmpeg -i output.mp4 -vn -acodec pcm_s16le -ar 44100 -ac 1 processme.wav
                    //ffmpeg -i temp.mp4 -vn -acodec pcm_s16le -ar 44100 -ac 1 processme.wav
                    
                    try {
                        System.out.println("before");
                        // Wait for a maximum of 1 second
                        if (!process.waitFor(60*3, TimeUnit.SECONDS)) {
                            System.out.println("Process timeout. Terminating the process.");
                            process.destroy(); // Terminate the process
                            // Handle the timeout situation, e.g., throw an exception or return an error
                        } else {
                            System.out.println("after");
                            // System.out.println("YT STAGE 2 COMPLETED  "  + str);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        // Handle the interruption
                    }
                    try {
                        Path sourcePath = Paths.get("./temp/"+myname2 );
                        long size2 = (new File("./temp/"+myname2 )).length();
                        fulltime = (long)(size2 /(44.1)/2);
                        
                        Path destinationPath = Paths.get("./DumpFolder/processme.wav");
                        Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }catch(Exception e){e.printStackTrace();}
                {
                    try{
                        System.out.println("A");
                        StemCell.main(inputPath, fulltime, inputPath + ".png");
                        System.out.println("B");
                        BufferedImage bi = ImageExpirements.pngToBI(inputPath + ".png");
                        System.out.println("C");
                        if(fulltime == 0)
                            fulltime =  StemCell.stemcellfulltime;
                        System.out.println("D");
                        waitherefulltime = fulltime;
                        waitheresidelength = StemCell.stemcellsidelength;
                        System.out.println("intermediary set, sl is " + waitheresidelength);
                        waithere = bi;
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }
            };
            new Thread(arbfunc).start();
        }else if(nearest instanceof BurningBush){
            var f = (new File("VirtualFolder"));
            if(!f.exists()){
                f.mkdirs();
            }
            System.out.println("here here ");
            var bb = (BurningBush)nearest;
            // need what I have, 
            byte[] bytes = bb.audioBytes;
            System.out.println("here here 2");

            if(bytes != null){
                System.out.println("here here 3");

                // mp3
                
                // String myname = "temp"+System.currentTimeMillis()+".mp3";
                String myname = "temp"+System.currentTimeMillis()+".wav";
                // Path path = Paths.get(myname);
                // Files.write(path, bytes);
                Main.deleteFolderContents(new File("VirtualFolder"));
                
                AudioFormat audioFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    44100.0F, // Sample Rate
                    16,       // Sample Size in Bits
                    1,        // Channels
                    1,        // Frame Size
                    44100.0F, // Frame Rate
                    false);   // Little Endian

                byte[] audioBytes = bb.audioBytes;
                // byte[] audioBytes = ... // Your audio data here

                ByteArrayInputStream bais = new ByteArrayInputStream(audioBytes);
                AudioInputStream audioInputStream = new AudioInputStream(bais, audioFormat, audioBytes.length / audioFormat.getFrameSize());

                File outFile = new File("./VirtualFolder/"+myname);
                try {
                    if (AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, outFile) == -1) {
                        throw new IOException("Could not write audio file");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                {
                    try{
                        java.awt.Desktop.getDesktop().browse(f.toURI());
                    }catch(Exception e){
                        System.out.println("ERROR OPENING FILE");
                        e.printStackTrace();
                    }
                }
                // ProcessBuilder processBuilder = new ProcessBuilder(
                //     "../../yt-dlp/yt-dlp.exe",
                //     "-f", "bestvideo+bestaudio[ext=m4a]/best",
                //     str,  "-o", "./temp/"+myname
                // );


                // processBuilder.redirectErrorStream(true);
                // processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                // Process process = processBuilder.start();
            }
        }else{
            if(TimeLockingKey.globalTypingSwitch != wordgentypingswitch){
                TimeLockingKey.globalTypingSwitch = wordgentypingswitch;
            }else{
                TimeLockingKey.globalTypingSwitch = null;
            }
        }
    }
    // if(f4){
    //     FFmpegUI.main(null);
        
    // }
    if(f5){
        a2.goright = true;
         boolean holdingPasty = (bobbies.size() >= 2) && bobbies.get(0) instanceof Pasty && bobbies.get(1) instanceof Pasty; 
        if(holdingPasty){
            var poppedPasty1 = (Pasty)bobbies.pop();
            var poppedPasty2 = (Pasty)bobbies.pop();
            BufferedImage bi1 = null;
            BufferedImage bi2 = null;
            {
                var p = poppedPasty1;
                 bi1 = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                for(int i = 0; i < p.width; i++){
                    for(int j = 0; j < p.height; j++){
                        bi1.setRGB(i, j, p.data[i+j*p.width]);
                    }
                }
            }
            {
                var p = poppedPasty2;
                 bi2 = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                for(int i = 0; i < p.width; i++){
                    for(int j = 0; j < p.height; j++){
                        bi2.setRGB(i, j, p.data[i+j*p.width]);
                    }
                }
            }
            var _fff0000012720 = a2._fff0000019471(bi1,bi2);
            Pasty p = new Pasty();
            var bi = _fff0000012720;
            p.sprite = new Spriter(bi, p.sl(), p.mso());
            p.width = bi.getWidth();
            p.height = bi.getHeight();
            p.data = p.sprite.tempIntDataForStorage;
            // p.sprite.tempIntDataForStorage = null;
            p.whrat = bi.getWidth() * 1.0 / bi.getHeight();
            // p = ParkerQuizItem.promotePasty(p, "no importa");

            intoInventory(p);
        }
    }
    if(f6){
        a2.goright = false;
         boolean holdingRemotes = (bobbies.size() >= 2) && bobbies.get(0) instanceof ControllerNodule && bobbies.get(1) instanceof ControllerNodule; 
         boolean holdingFunnels = (bobbies.size() >= 2) && bobbies.get(0) instanceof FunnelNodule && bobbies.get(1) instanceof FunnelNodule; 

         boolean holdingPasty = (bobbies.size() >= 2) && bobbies.get(0) instanceof Pasty && bobbies.get(1) instanceof Pasty; 
        if(holdingPasty){
            var poppedPasty1 = (Pasty)bobbies.pop();
            var poppedPasty2 = (Pasty)bobbies.pop();
            BufferedImage bi1 = null;
            BufferedImage bi2 = null;
            {
                var p = poppedPasty1;
                 bi1 = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                for(int i = 0; i < p.width; i++){
                    for(int j = 0; j < p.height; j++){
                        bi1.setRGB(i, j, p.data[i+j*p.width]);
                    }
                }
            }
            {
                var p = poppedPasty2;
                 bi2 = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                for(int i = 0; i < p.width; i++){
                    for(int j = 0; j < p.height; j++){
                        bi2.setRGB(i, j, p.data[i+j*p.width]);
                    }
                }
            }
            var _fff0000012720 = a2._fff0000019471(bi1,bi2);
            Pasty p = new Pasty();
            var bi = _fff0000012720;
            p.sprite = new Spriter(bi, p.sl(), p.mso());
            p.width = bi.getWidth();
            p.height = bi.getHeight();
            p.data = p.sprite.tempIntDataForStorage;
            // p.sprite.tempIntDataForStorage = null;
            p.whrat = bi.getWidth() * 1.0 / bi.getHeight();
            // p = ParkerQuizItem.promotePasty(p, "no importa");

            intoInventory(p);
        }else if(holdingRemotes){
            var cn = new ControllerNodule();
            LinkedList<ControllerNodule> controllers = new LinkedList<ControllerNodule>();
            int i = 0;
            while(bobbies.size() > i && bobbies.get(i) instanceof ControllerNodule){
                // System.out.println("got one" + bobbies.get(i));
                controllers.addLast((ControllerNodule)bobbies.get(i).clone());
                i++;
            }
            var pns = new LinkedList<PortalNodule>();
            boolean keepgoing = true;
            while(keepgoing){
                keepgoing = false;
                for(var c: controllers){
                    if(!c.portalNodules.isEmpty()){
                        keepgoing = true;
                        pns.addLast(c.portalNodules.pop());
                    }
                }
            }
            cn.portalNodules = pns;
            intoInventory(cn);
        }else if(holdingFunnels){
            var cn = new FunnelNodule();
            LinkedList<FunnelNodule> controllers = new LinkedList<FunnelNodule>();
            int i = 0;
            while(bobbies.size() > i && bobbies.get(i) instanceof FunnelNodule){
                // System.out.println("got one" + bobbies.get(i));
                controllers.addLast((FunnelNodule)bobbies.get(i).clone());
                i++;
            }
            var pns = new LinkedList<PhantomNodule>();
            boolean keepgoing = true;
            while(keepgoing){
                keepgoing = false;
                for(var c: controllers){
                    while(!c.phantoms.isEmpty()){
                        keepgoing = true;
                        pns.addLast(c.phantoms.pop());
                    }
                }
            }
            cn.phantoms = pns;
            intoInventory(cn);
        }
    }
    // if(f5){
    //     // if(){

    //     // }
    //     // so that you can type anyway
    //     // glfwShowWindow(topWindow);
    //     GameObject find = null;
    //     double lowest = 15;
    //     for(GameObject p: pasties){
    //         double dx = p.x-x;
    //         double dz = p.z-z;
    //         double dist = Math.sqrt(dx*dx+dz*dz);
    //         if(dist < lowest && !(p instanceof ThreeDumb)){
    //             lowest = dist;
    //             find = p;
    //         }
    //     }
    //     System.out.println(find);
    //     if(find instanceof Pasty ){
    //         var p = (Pasty)find;
    //         pasties.remove(p);
    //         var pqi = ParkerQuizItem.promotePasty(p, "no importa");
    //         pasties.add(pqi);
    //         // var pqi = ((ParkerQuizItem)find);
    //         // System.out.println("assigning " + assigningCohortID);
    //         // System.out.println("filtering " + filteringCohortID);
    //         // System.out.println("pqi filtering " + pqi.cohortID);
    //         // pqi.cohortID = assigningCohortID;
    //         // System.out.println("pqi filtering (new) " + pqi.cohortID);
    //     }else{
        
    //     // System.out.println("wtf");
    //     // if(find instanceof Pasty){
    //     //     // 
    //     //     toPromoteToQuizItem = (Pasty) find;
    //     //     TimeLockingKey.globalTypingSwitch = quiztypingswitch;

    //     // }
    //     }

    //     // for(GameObject p: pasties){
    //     //     double dx = p.x-x;
    //     //     double dz = p.z-z;
    //     //     double dist = Math.sqrt(dx*dx+dz*dz);
    //     //     if(dist < lowest){
    //     //         lowest = dist;
    //     //         find = p;
    //     //     }
    //     // }
    //     // if(find instanceof ThreeDumb){
    //     //     System.out.println("PROMOTING");
    //     //     var idk = (ThreeDumb)find;
    //     //     if(!lock()){
    //     //         intoInventory(idk.promote());
    //     //     }
    //     // }
    //     // if(followmode)
    //     //     wigglemode = !wigglemode;
    //     //     System.out.println();
    //     // {

    //     //     var i = neededGameObjects.iterator();
    //     //     while(i.hasNext()){
    //     //         var o = i.next();
    //     //         System.out.println(o);
    //     //     }
    //     // }
    //     // System.out.println();
    //     // if(missingGameObjects != null){
    //     //     var i = missingGameObjects.iterator();
    //     //     if(i.hasNext()){
    //     //         var ii = i.next();
    //     //         System.out.println(ii);
    //     //         System.out.println(((Pasty)ii).sprite);
    //     //         System.out.println(((Pasty)ii).data);
    //     //         System.out.println(((Pasty)ii).lastLoaded);
    //     //         x = ii.x;
    //     //         z = ii.z;
    //     //         System.out.println(x);
    //     //         System.out.println(z);
    //     //     }
    //     // }

    // }
    if(f9){
        // if(nearest instanceof Pasty){
        //     var p = (Pasty)nearest;
        //     pasties.remove(p);
        //     var pqi = ParkerQuizItem.promotePasty(p, "no importa");
        //     pasties.add(pqi);
        // }else{
        //     if(TimeLockingKey.globalTypingSwitch != textgentypingswitch){
        //         TimeLockingKey.globalTypingSwitch = textgentypingswitch;
        //     }else{
        //         TimeLockingKey.globalTypingSwitch = null;
        //     }
        // }


        // Chat2.java

        // Take the string, use call2
        // using the linkedlists, construct X number of WordGen
        // I need WordGens working
        // They shouldn't be Pasty objects, 
        // They should be MultipleChoice Objects
        // After the multiple choice object (which is cloneable) is escaped from
        // submit to the yet-constructed quizEvaluator.java function
        // similar to f6
        // dump to inventory, convenience can come later



        // from the above,
        // the subsections I need to work 
        // quizEvaluator.java w/ function; does not need integration, is stand alone, do first
        // WordGen integration, how BTFO'd is it right now? it's an integration; put it off
        // the MultipleChoice part should be...not too bad i think
        // it's pure integration but just needs tie ins to that one part with the esc
        // Ok good all pasties are done
        // f6 is also kinda simple, a pure integration, kinda annoying but should work
        // fucking TERRIFIED of "code too large" but I know I need to work through that
        
    }

    // if(f9){
    //     // FreeMouse.mode = !FreeMouse.mode;
    //     // if(!FreeMouse.mode)glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_HIDDEN);
    //     // if( FreeMouse.mode)glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);

    //     // breathe through all of it.
    //     // CYCLING_MODE = !CYCLING_MODE;
    //     int xx = (int)(x-1_000_000);
    //     int yy = (int)(z-1_000_000);
    //     System.out.println("WTF " + xx +" " + yy );
    //     // int c = swaths[9].getColorFromCompletedMap(xx,yy);
    //     // swaths[9].specialGetMapImage(xx, yy);
    //     // for(int i = 0; i < swaths.length; i++){
    //     //     System.out.println(swaths[i]);
    //     //     System.out.println(swaths[i].seed);
    //     // }
    //     swaths[9].specialGetMapImage(xx, yy);
    //     // try{
    //     //     Thread.sleep(5000);
    //     //     swaths[22].specialGetMapImage(xx, yy);

    //     // }catch(Exception e){
    //     //     e.printStackTrace();
    //     // }
    // }
    if(f8){
        // F8_MODE = !F8_MODE;
        if(nearest instanceof TextNodule){
            var text = (TextNodule)nearest;
            if(text.state != null){
                final var s = text.state.stringify(" ").replace("\"", "\\\"");
                // just kick it off instead
                PromiseNodule pn = new PromiseNodule();
                intoInventory(pn);
                Runnable arbfunc = () ->{
                    if (Input.simulatedControlEnabled) {
                        // Allowed voices list.
                        List<String> allowedVoices = Arrays.asList("alloy", "ash", "coral", "echo", "fable", "onyx", "nova", "sage", "shimmer");
                        
                        // Randomly choose one voice for all chunks.
                        Random random = new Random();
                        String chosenVoice = allowedVoices.get(random.nextInt(allowedVoices.size()));
                        
                        // Generate a random speed between 0.9 and 1.1.
                        double speed = 0.9 + random.nextDouble() * (1.1 - 0.9);
                        speed = 1;
                        // Split string s into chunks of at most 4096 characters.
                        final int CHUNK_SIZE = 4096;
                        List<String> chunks = new ArrayList<>();
                        for (int start = 0; start < s.length(); start += CHUNK_SIZE) {
                            int end = Math.min(s.length(), start + CHUNK_SIZE);
                            chunks.add(s.substring(start, end));
                        }
                        
                        // Process each chunk separately using TTS.
                        // We'll save each chunk's output as a separate MP3 file.
                        List<String> chunkFiles = new ArrayList<>();
                        String baseTimestamp = String.valueOf(System.currentTimeMillis());
                        
                        for (int i = 0; i < chunks.size(); i++) {
                            // Create a unique filename for this chunk; adding the chunk index helps uniqueness.
                            String chunkFile = "_" + baseTimestamp + "_" + i + ".mp3";
                            // Call your TTS engine on the chunk using the chosen voice and speed.
                            TextToSpeech.call(chunks.get(i), chosenVoice, speed, chunkFile);
                            chunkFiles.add("./temp/" + chunkFile);
                        }
                        
                        // Optionally: Merge the individual MP3 files into one.
                        // To do this, create a temporary file list for ffmpeg.
                        try {
                            File fileList = new File("./temp/chunks.txt");
                            try (PrintWriter writer = new PrintWriter(fileList)) {
                                for (String filename : chunkFiles) {
                                    // ffmpeg expects lines of the form: file 'path/to/file'
                                    writer.println("file '" + new File(filename).getAbsolutePath() + "'");
                                }
                            }
                            
                            // Merge the files using ffmpeg concat demuxer.
                            String mergedFile = "merged_" + baseTimestamp + ".mp3";
                            ProcessBuilder mergePb = new ProcessBuilder(
                                "../../ffmpeg/ffmpeg.exe",
                                "-f", "concat",
                                "-safe", "0",
                                "-i", fileList.getAbsolutePath(),
                                "-c", "copy",
                                "./temp/" + mergedFile
                            );
                            mergePb.redirectErrorStream(true);
                            mergePb.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                            Process mergeProcess = mergePb.start();
                            if (!mergeProcess.waitFor(180, TimeUnit.SECONDS)) {
                                System.out.println("Merging process timeout. Terminating the process.");
                                mergeProcess.destroy();
                            }
                            
                            // Convert the merged MP3 file to WAV (if that is your final output).
                            String myname2 = "processme" + System.currentTimeMillis() + ".wav";
                            ProcessBuilder processBuilder = new ProcessBuilder(
                                "../../ffmpeg/ffmpeg.exe",
                                "-i", "./temp/" + mergedFile,
                                "-vn",
                                "-acodec", "pcm_s16le",
                                "-ar", "44100",
                                "-ac", "1",
                                "./temp/" + myname2
                            );
                            processBuilder.redirectErrorStream(true);
                            processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                            Process process = processBuilder.start();
                            
                            if (!process.waitFor(180, TimeUnit.SECONDS)) {
                                System.out.println("Audio conversion process timeout. Terminating the process.");
                                process.destroy();
                            }
                            
                            // Move the final WAV file to your destination folder.
                            Path sourcePath = Paths.get("./temp/" + myname2);
                            Path destinationPath = Paths.get("./DumpFolder/processme.wav");
                            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                            
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                
                    else if(Input.simulatedShiftEnabled){
                        String data = Chat.call(s);
                        TextNodule p = new TextNodule();
                        p.sizeMulti/=(1.5*1.5*1.5*1.5);
                        p.state = new State();
                        {
                            System.out.println("I have the data which is... " + data);
                            Scanner ss = new Scanner(data);
                            int i = 0;
                            while(ss.hasNextLine()){
                                if(i!= 0){
                                    p.state.enter(false);
                                }
                                i++;
                                var line = ss.nextLine();
                                int ii = 0;
                                while(ii < line.length()){
                                    System.out.println("about to extract..." + ii + " , " + (Math.min(ii+40, line.length())));
                                    String sub = line.substring(ii,Math.min(ii+40, line.length()));
                                    p.state.insert(sub);
                                    ii+=40;
                                    p.state.enter(false);
                                }
                                // Scanner s2 = new Scanner(line);
                                // int ii = 0;
                                // while(s2.hasNext()){
                                //     System.out.println("yo");
                                //     p.state.insert(s2.next());
                                //     ii++;
                                //     if(ii == 40){
                                //         ii = 0;
                                //         p.state.enter(false);
                                //     }
                                // }
                                // s2.close();
                            }
                            ss.close();
                        }
                        {
                            var x = pn.x;
                            var z = pn.z;
                            pasties.query2D(x-50, x+50, z-50, z+50);
                            boolean pastiesContains = false;
                            for(int i = 0; i < pasties.size(); i++){
                                GameObject pp = pasties.getAt(i);
                                if(pp == pn){
                                    pastiesContains = true;
                                    break;
                                }
                            }
                            if(pastiesContains){
                                System.out.println("()Decided to replace the pn");
                                System.out.println("pnx " + pn.x);
                                System.out.println("pnz " + pn.z);
                                System.out.println("pny " + pn.ydisp);
                                p.assumeApproximatePositionOf(pn);
                                System.out.println("px " + p.x);
                                System.out.println("pz " + p.z);
                                System.out.println("py " + p.ydisp);
                                PURGATORY.add(p);
                                System.out.println("added to purgatgory");
                            }else{
                                System.out.println("not in pasties, attempting bobbies!");
                                // System.exit(0);
                                BOBBIES_REPLACE.add(new Tuple<GameObject, GameObject>(pn,p));

                        
                            }
                        }
                        DELETION_PURGATORY.add(pn);

                        // p.state.insert(new RichChar(b));
                    }else{

                        try {
                            rateLimiter.sendRequest(() -> {
                                System.out.println("Executing request  at " + System.currentTimeMillis());
                                // Simulate request execution
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                }
                            });
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }

                        InputStream done = CustomPostRequestExample.call(s);
                        if(done != null){
                            Pasty p = new Pasty();
                            BufferedImage image = null;
                            try{

                                image = ImageIO.read(done);
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                            var bi = image;
                            // p.sprite = new Spriter(bi, p.sl(), p.mso());
                            p.width = bi.getWidth();
                            p.height = bi.getHeight();
                            // p.data = p.sprite.tempIntDataForStorage;
                            p.data = ImageExpirements.biToPix(bi);
                            // p.sprite.tempIntDataForStorage = null;
                            p.whrat = bi.getWidth() * 1.0 / bi.getHeight();

                            // if(bobbies.contains(pn)){
                            //     // int i = bobbies.indexOf(pn);
                            //     // bobbies.remove(pn);
                            //     // bobbies.set(i, p);
                            //     System.out.println("BOB REP 1");
                            //     // intoInventory(pn);
                            // }else{
                                // query2d the area, does it have what I want.
                                var x = pn.x;
                                var z = pn.z;
                                pasties.query2D(x-50, x+50, z-50, z+50);
                                boolean pastiesContains = false;
                                for(int i = 0; i < pasties.size(); i++){
                                    GameObject pp = pasties.getAt(i);
                                    if(pp == pn){
                                        pastiesContains = true;
                                        break;
                                    }
                                }
                                if(pastiesContains){
                                    System.out.println("()Decided to replace the pn");
                                    System.out.println("pnx " + pn.x);
                                    System.out.println("pnz " + pn.z);
                                    System.out.println("pny " + pn.ydisp);
                                    p.assumeApproximatePositionOf(pn);
                                    System.out.println("px " + p.x);
                                    System.out.println("pz " + p.z);
                                    System.out.println("py " + p.ydisp);
                                    PURGATORY.add(p);
                                    System.out.println("added to purgatgory");
                                }else{
                                    System.out.println("not in pasties, attempting bobbies!");
                                    // System.exit(0);
                                    BOBBIES_REPLACE.add(new Tuple<GameObject, GameObject>(pn,p));

                                }
                                // for(int i = 0; i < pasties.size(); i++){
                                //     GameObject p = pasties.getAt(i);
                            // }
                        }
                        DELETION_PURGATORY.add(pn);
                                
                    }
                };
                Thread t = new Thread(arbfunc);
                pn.watchThread = t;
                t.start();
            }
        }

        // TileClick.mode = !TileClick.mode;
        // TileClick.targetx = -1;
    }
    if(f10){
        if(Spriter.disable2d < 1){
            System.out.println("noice 1");
            Spriter.disable2d = 2;
        }else{
            System.out.println("noice 2");
            Spriter.disable2d = 0;
        }
    }
    if(f12 && !Input.simulatedControlEnabled){

        if(nearest != null){
            int rendx = (int)(x/128)*128+64;
            int rendz = (int)(z/128)*128+64;
            nearest.x = rendx;
            nearest.z = rendz;
            nearest.rot = 0;
            nearest.ydisp = 0;
            pasties.remove(nearest);
            pasties.add(nearest);

        }
        // Main.F12_OVERRIDE = !Main.F12_OVERRIDE;
    }
    if(f12 && Input.simulatedControlEnabled){
        int max = 1;
        if(Input.simulatedShiftEnabled) max = 9;
        if(Input.simulatedAltEnabled) max = 25;
        for(int i = 0; i < max; i++){
            int sqrt = (int)Math.sqrt(max);
            double xxx = x+128*(i%sqrt-sqrt/2);
            double zzz = z+128*(i/sqrt-sqrt/2);
            FUCKMELOOKUP.clear();
            demandreset = true;
            long nowkey = GameObject.getRegionKey(xxx, zzz);
            int putx = ((int)xxx)-(((int)xxx)%128);
            int puty = ((int)zzz)-(((int)zzz)%128);
            var params = new double[]{
                0,
                0,
                0,
                1,
                1,
                1,
                Math.random()*255,
                0,
                (Math.random()-Math.random())*4,
                0,
                0,
                0
            };
            var s = new Sector(
                // bowlHeightFunction,
                parkerHeightFunction,
                defaultColorFunction,
                defaultTextureFunction,
                params, // state
                new int[3]
            );
            {
                var key1 = xy(putx,puty);
                sectorFunctions.put(key1,s);
            }
            int flx7 = (int)(xxx/128);
            int flz7 = (int)(zzz/128);
            {
                // NUKE
                pasties.queryAll();
                for(int ii = 0; ii < pasties.size(); ii++){
                    GameObject p = pasties.getAt(ii);
                    // GameObject p = iterator.next();
                    if(p.getSectorKey() == xy(flx7, flz7)){
                        pasties.remove(p);
                    }
                }
            }
            {
                var fn = new FunnelNodule2();
                bobbies.addFirst(fn);
                {
                    int centerx = flx7*128+64;
                    int centery = flz7*128+64;
                    int ii = 0;
                    double g = 0;
                    while(g < Math.PI*2-Math.PI/180*50/5){
                        g = Math.PI/180*50/5*ii;
                        var go = new PhantomNodule();
                        System.out.println(go.getClass());
                        go.x = centerx+62*Math.sin(neck+g);
                        go.z = centery+62*Math.cos(neck+g);
                        go.rot = neck+Math.PI/2+g;
                        double height = justHeight((int)go.x, (int)go.z, 1);
                        go.ydisp = height+10;
                        fn.phantoms.add(go);
                        // pasties.add(go);
                        ii++;
                    }
                }
            }
        }
    }
    if(f11){
        // Main.cycling_remote_mode = !Main.cycling_remote_mode;
        if(nearest != null && nearest instanceof Pasty){
            nannypasty = (Pasty)nearest.clone();
            addStatement("nannypasty set. Evaluation begins in 3 minutes");
            nannypasty_timing = System.currentTimeMillis();
        }else{
            nannypasty = null;
            addStatement("nannypasty cleared.");
        }

    }
    // if(f11){
    //     if(Input.simulatedControlEnabled){
    //         // Do the big procedure (assuming 64 frames)
    //     }
    //     if(nearest instanceof Pasty){
    //         intoInventory(ProtoVideo.promote((Pasty)nearest));
    //     }
    // }
    // if(f6){
    //     last_full_save = 0;
    //     IS_FLAGGED_AS_BACKUP = false;
    //     // f6pressed = true;
    // }
    if(f7){
        last_full_save = 0;
        IS_FLAGGED_AS_BACKUP = false;
        // last_full_save = 0;
        // f6pressed = true;
    }
    // if(f4){
//     GameObject.DONT_UNLOAD_MODE_HARD_OVERRIDE = !GameObject.DONT_UNLOAD_MODE_HARD_OVERRIDE;
    // }
    // if(f7){
    //     if(nearest instanceof Pasty) {
    //         byte[] ba = new byte[4*100*400];
    //         String frag_string = "";
    //         if(tabselection != -1 && tabselection < bobbies.size()&&
    //             bobbies.get(tabselection) instanceof Shader){
    //                 var b = (Shader)bobbies.get(tabselection);
    //                 frag_string = b.fragShaderArtifact;
    //         }else{
    //             try{
    //                 frag_string = new String(Arrays.copyOf(ba, (new FileInputStream("frag_f7.glsl")).read(ba)), "UTF-8");
    //             }catch(Exception e){e.printStackTrace();}
    //         }
    //         var s = Shader.promote((Pasty)nearest, frag_string);
    //         pasties.remove(nearest);
    //         dellies.push(nearest);
    //         pasties.add(s);
    //     }
    // }
    // if(f4){
    //     // if(Input.simulatedControlEnabled){
    //     //     HashSet<ParkerQuizItem> allQuiz= new HashSet<ParkerQuizItem>();
    //     //     for(GameObject go: pasties){
    //     //         if(go instanceof ParkerQuizItem && ((ParkerQuizItem)go).cohortID == filteringCohortID && !passed.contains(go)){
    //     //             allQuiz.add((ParkerQuizItem)go);
    //     //         }
    //     //     }
    //     //     if(!allQuiz.isEmpty()){
    //     //         ArrayList<ParkerQuizItem> list = new ArrayList<>(allQuiz);
    //     //         Random rand = new Random();
    //     //         ParkerQuizItem randomElement = list.get(rand.nextInt(list.size()));
    //     //         {
    //     //             double leastd = Integer.MAX_VALUE;
    //     //             var iter = list.iterator();
    //     //             while(iter.hasNext()){
    //     //                 var k = iter.next();
    //     //                 double x = Main.x-k.x;
    //     //                 double z = Main.z-k.z;
    //     //                 var d = Math.sqrt(x*x+z*z);
    //     //                 if(d < leastd){
    //     //                     leastd = d;
    //     //                     randomElement = k;
    //     //                 }
    //     //             }
    //     //         }

    //     //         System.out.println("Random element: " + randomElement.answer);
    //     //         x = randomElement.x + Math.sin(randomElement.rot+Math.PI/2)*PortalNodule.range*4;
    //     //         z = randomElement.z + Math.cos(randomElement.rot+Math.PI/2)*PortalNodule.range*4;
    //     //         neck = randomElement.rot-Math.PI/2;
    //     //         chin = 0;

    //     //         for(var p: pasties){
    //     //             if(p instanceof Pasty){
    //     //                 ((Pasty)p).hidden = false;
    //     //             }
    //     //         }
    //     //         // hiddenCallBackTime = System.currentTimeMillis()+700;
    //     //         // randomElement.hidden = true;

    //     //         prestigelevel = 4;
    //     //         firstquizitem = randomElement;


    //     //         TimeLockingKey.globalTypingSwitch = quiztypingswitch;
    //     //         quizCheck = randomElement;
    //     //         priorQuizCheck = null;
    //     //         quizTransitionEnergy = quizTransitionEnergy_DEFAULT;
    //     //         System.out.println("Random element #2: " + randomElement.answer);
    //     //         newwigglemode = true;
    //     //         wiggleItem = randomElement;
    //     //         Spriter.setLateTexture(quizCheck.sprite);if(prestigelevel==4)Spriter.clearLateTexture();
    //     //         Spriter.clearLateTexture();

    //     //         VISUAL_PROMPT_WORD = quizCheck.prompt_word;

    //     //     }

    //     // }else{
    //         if(TimeLockingKey.globalTypingSwitch != wordgentypingswitch){
    //             TimeLockingKey.globalTypingSwitch = wordgentypingswitch;
    //         }else{
    //             TimeLockingKey.globalTypingSwitch = null;
    //         }
    //     // }
    // }
    boolean ff2 = TimeLockingKey.keys.get(GLFW_KEY_F2).isNewPress();
    if(ff2){
        if(nearest != null && nearest instanceof Pasty){
            Pasty p = (Pasty)nearest.clone();
            globalHandSpriterOverride = p;
            handSpriterOverride = p;
        }
        
    }
    // if(ff2){
    //     // if(!Input.simulatedControlEnabled){
    //     //     Spriter.setLateTexture(inversionMaskTest);
    //     // }else{
    //     //     Spriter.clearLateTexture();
    //     // }
    //     if(Input.simulatedControlEnabled){
    //         hidePQIOverlay = !hidePQIOverlay;
    //         Spriter.clearLateTexture();
    //         lastNearestPQI = null;
    //     }else{
    //         hidePQItoggle = !hidePQItoggle;
    //     }
    //     // lands
    //     // scapeSpriter.setLateTexture(inversionMaskTest);
    //     // swapSpriter.setLateTexture(inversionMaskTest);
    // }

    // boolean twokey = TimeLockingKey.keys.get(GLFW_KEY_2).isNewPress();

    // if(f2 && FLICKER_GRAB_CATCH_VALUE == null){
    //     // cycle the giga
    //     AUTO_CONTEXT_TIMER = Integer.MIN_VALUE;
    //     if(Input.simulatedControlEnabled){
    //         if(Input.simulatedAltEnabled && Input.simulatedShiftEnabled){
    //             // Delete current
    //             if(stacks.size() > 1){
    //                 stacks.removeFirst();
    //                 loadValuesFromFirstGigaStack();
    //             }
    //         }
    //     }
    //     else if(Input.simulatedShiftEnabled){
    //         if(stacks.size() > 1){
    //             dumpToFirstGigaStack();
    //             stacks.addFirst(stacks.removeLast());
    //             loadValuesFromFirstGigaStack();
    //         }
    //     }
    //     else if(Input.simulatedAltEnabled){
    //         System.out.println("Parker f2 case 2");
    //         // cycle
    //         if(stacks.size() > 1){
    //             dumpToFirstGigaStack();
    //             stacks.addLast(stacks.removeFirst());
    //             loadValuesFromFirstGigaStack();
    //         }
    //     }else{
    //         dumpToFirstGigaStack();
    //         stacks.addFirst(new GigaStack());
    //         System.out.println("Parker f2 case 1 " + stacks.size());
    //         loadValuesFromFirstGigaStack();
    //     }
        
    // }
    if(TimeLockingKey.keys.get(GLFW_KEY_R).isNewPress()){
        // 3d notes

        //1.3.21
        int[] ia = new int[1920*1080];
        Pasty p = new Pasty();
        p.sprite = new Spriter(ia, 1920, 1080, 1, 4);
        p.width = 1920;
        p.height = 1080;
        p.data = p.sprite.tempIntDataForStorage;
        p.whrat = p.width*1.0/p.height;
        p.rot = neck + Math.PI/2;
        p.x = x+Math.random()*0.00001;
        p.z = z;
        p.ydisp = dispy+15;
        PURGATORY.add(p);
        p.hidden = true;
        fortounload = p;
        TimeLockingKey.globalTypingSwitch = textstamp;


        // if(!bobbies.isEmpty())
        //     intoInventory(bobbies.remove(0));

    }
    if(false && TimeLockingKey.keys.get(GLFW_KEY_R).isNewPress()){
        Collections.reverse(bobbies);

        // if(!bobbies.isEmpty())
        //     intoInventory(bobbies.remove(0));

    }
    if(false && TimeLockingKey.keys.get(GLFW_KEY_R).isNewPress() && FLICKER_GRAB_CATCH_VALUE == null){
        AUTO_CONTEXT_TIMER = Integer.MIN_VALUE;
        PortalNodule pn = new PortalNodule();
        pn.chin = chin;
        pn.nangle = neck;
        pn.destx = x;
        pn.destz = z;
        pn.chary = dispy;
        if(Input.simulatedAltEnabled && depthStack.size() >0 ){
            var dead = depthStack.removeLast();
            FLICKER_AFTER_PORTAL = dead.y; // dies
            Tuple3<Spriter,PortalNodule,Integer> item = new Tuple3<Spriter,PortalNodule,Integer>(
                // new Spriter(ias[0], buggaX, buggaY, 1, 4),
                // new Spriter(ias[1], buggaX, buggaY, 1, 4),
                null, // spriter is created later, after the catch
                    pn, TEMPORARY_CONTEXT_CONTEXT);
            TEMPORARY_CONTEXT_CONTEXT = dead.z;
            System.out.println("SETTING TO " + TEMPORARY_CONTEXT_CONTEXT);
            // {
            //     System.out.println("setting temporary context context to... " + TEMPORARY_CONTEXT_CONTEXT);
            //     tabselection = -1;
            //     bobbyselection = TEMPORARY_CONTEXT_CONTEXT;
            //     bobbies = invStore[bobbyselection];
            //     if(bobbies.size() >= 1){
            //         tabselection = 0;
            //     } 
            // }
            FLICKER_GRAB_CATCH_VALUE = item; // this will be caught on the following cycle to
            Spriter.disable2d = 2.0F;
            depthStack.push(item);

            // HERE I NEED TO SELECT - SLOT X
            
            depthStack.size();
        }
        else if(Input.simulatedShiftEnabled && depthStack.size() >0 ){
            var dead =  depthStack.pop();
            FLICKER_AFTER_PORTAL =dead.y; // dies
            Tuple3<Spriter,PortalNodule,Integer> item = new Tuple3<Spriter,PortalNodule,Integer>(
                // new Spriter(ias[0], buggaX, buggaY, 1, 4),
                // new Spriter(ias[1], buggaX, buggaY, 1, 4),
                null, // spriter is created later, after the catch
                pn, TEMPORARY_CONTEXT_CONTEXT);
            System.out.println("SETTING TO " + TEMPORARY_CONTEXT_CONTEXT);
            TEMPORARY_CONTEXT_CONTEXT = dead.z;

            // {
            //     System.out.println("setting temporary context context to... " + TEMPORARY_CONTEXT_CONTEXT);
            //     tabselection = -1;
            //     bobbyselection = TEMPORARY_CONTEXT_CONTEXT;
            //     bobbies = invStore[bobbyselection];
            //     if(bobbies.size() >= 1){
            //         tabselection = 0;
            //     } 
            // }
            FLICKER_GRAB_CATCH_VALUE = item; // this will be caught on the following cycle to
            Spriter.disable2d = 2.0F;
            depthStack.addLast(item);

            // HERE I NEED TO SELECT - SLOT X

            depthStack.size();
        }else
        if(!Input.simulatedControlEnabled){
            // glReadBuffer(GL_FRONT);
            // ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
            // glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
            // var bbi = bb.asIntBuffer();
            // int[] ia = new int[buggaX*buggaY];
            // bbi.get(ia);
            // int[][] ias = convertToARGBAndFlip2(ia, buggaX, (1-1/Math.pow(cutoutSize, depthStack.size()+1))/2);
            Tuple3<Spriter,PortalNodule, Integer> item = new Tuple3<Spriter,PortalNodule,Integer>(
                // new Spriter(ias[0], buggaX, buggaY, 1, 4),
                // new Spriter(ias[1], buggaX, buggaY, 1, 4),
                null, // spriter is created later, after the catch
                pn, TEMPORARY_CONTEXT_CONTEXT);
            TEMPORARY_CONTEXT_CONTEXT = tryGetInvStoreIndex(); // for the new one
            FLICKER_GRAB_CATCH_VALUE = item; // this will be caught on the following cycle to
            Spriter.disable2d = 2.0F;
            // fill out the render
            depthStack.push(item);
            Spriter.globaluishrinker = (float)Math.pow(cutoutSize, depthStack.size());
            // if(central_dogma != null)
            //     central_dogma.activate();

            {   // One of the most priceless couple of lines of code ever.
                // Main.chin   += Math.random()*2*Math.PI;
                // Main.neck   += Math.random()*2*Math.PI;
                // Main.x      += Math.random()*2000-1000;
                // Main.z      += Math.random()*2000-1000;
                shouldJump = true;
            }
            // {
            //     System.out.println("setting temporary context context to... " + TEMPORARY_CONTEXT_CONTEXT);
            //     tabselection = -1;
            //     bobbyselection = TEMPORARY_CONTEXT_CONTEXT;
            //     bobbies = invStore[bobbyselection];
            //     if(bobbies.size() >= 1){
            //         tabselection = 0;
            //     } 
            // }
        }else{
            if(depthStack.size() > 0){
                var item = depthStack.pop();  
                Spriter.globaluishrinker = (float)Math.pow(cutoutSize, depthStack.size());
                item.y.activate();
                TEMPORARY_CONTEXT_CONTEXT = item.z;
                // {
                //     System.out.println("setting temporary context context to... " + TEMPORARY_CONTEXT_CONTEXT);
                //     tabselection = -1;
                //     bobbyselection = TEMPORARY_CONTEXT_CONTEXT;
                //     bobbies = invStore[bobbyselection];
                //     if(bobbies.size() >= 1){
                //         tabselection = 0;
                //     } 
                // }
            }
        }
    }
}
else if(TimeLockingKey.globalTypingSwitch != null){


    if(fortounload != null){

        if(TimeLockingKey.keys.get(GLFW_KEY_F1).isNewPress()){
            textstamp_size*=1.5;
        }

        if(TimeLockingKey.keys.get(GLFW_KEY_F2).isNewPress()){
            textstamp_size/=1.5;
        }
    }

    if(TimeLockingKey.globalTypingSwitch == quiztypingswitch){
        if(TimeLockingKey.keys.get(GLFW_KEY_F6).isNewPress()){
            if(Input.simulatedControlEnabled)
                prestigelevel--;
            else
                prestigelevel++;
        }
        if(false && TimeLockingKey.keys.get(GLFW_KEY_SPACE).isNewPress()){
            String str = TimeLockingKey.globalTypingSwitch.state.getLineAlone().trim();
            TimeLockingKey.globalTypingSwitch.state = new State();

            if(toPromoteToQuizItem != null){
                var pqi = ParkerQuizItem.promotePasty(toPromoteToQuizItem, str);
                TimeLockingKey.globalTypingSwitch = null;
                pasties.remove(toPromoteToQuizItem);
                toPromoteToQuizItem = null;
                pasties.add(pqi);
            }else{
                boolean correct = false;
                {
                    System.out.println("HOW "+quizCheck.answer + " " + quizCheck);
                    correct = quizCheck.answer.toLowerCase().equals(str.toLowerCase());
                    // correct = true;
                    if(prestigelevel == 1)
                        correct = true; // new idea override
                    if(prestigelevel != 4 /*override*/ && correct && !passed.contains(quizCheck)) {
                        for(var BACKTRACK_ITEM_STACK: BACKTRACK_ITEM_STACK_S)
                            BACKTRACK_ITEM_STACK.push(quizCheck);
                    }
                    if(correct) passed.add(quizCheck);


                    if(passed.size() == lastAllQuizSize){
                        if(forward){
                        }else{
    
                            prestigelevel++;
                        }
                        forward = !forward;
                        passed.clear();
                        score.clear();
                        // reset
    
                        // x = firstquizitem.x ;
                        // z = firstquizitem.z ;
                        
                    }
                    // {
                    //     HashSet<ParkerQuizItem> allQuiz= new HashSet<ParkerQuizItem>();
                    //     for(GameObject go: pasties){
                    //         if(go instanceof ParkerQuizItem && ((ParkerQuizItem)go).cohortID == filteringCohortID){
                    //             allQuiz.add((ParkerQuizItem)go);
                    //         }
                    //     }
                    //     lastAllQuizSize = allQuiz.size();
    
                    // }
                    score.addFirst(correct);
                    if(correct){
                        {
                            final Runnable runnable =
                                (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                            if (runnable != null) runnable.run();
                        }
                    }else{
                        {
                            final Runnable runnable =
                                (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand`");
                            if (runnable != null) runnable.run();
                        }
                    }
                    guess  = str;
                    answer = quizCheck.answer;
                    System.out.println("WAS IT RIGHT " + correct + " urs: " + str + " correct: " + quizCheck.answer);
                }
                {
                    HashSet<ParkerQuizItem> allQuiz= new HashSet<ParkerQuizItem>();
                    // for(GameObject go: pasties){
                    //     if(go instanceof ParkerQuizItem && ((ParkerQuizItem)go).cohortID == filteringCohortID && !passed.contains(go)){
                    //         allQuiz.add((ParkerQuizItem)go);
                    //     }
                    // }
                    if(!allQuiz.isEmpty()){
                        List<ParkerQuizItem> list = new ArrayList<>(allQuiz);
                        Random rand = new Random();
                        ParkerQuizItem randomElement = list.get(rand.nextInt(list.size()));
                        ParkerQuizItem searchElement = quizCheck;
                        JUST_IN_CASE = SAVED_RETURNAL_ELEMENT;
                        if(SAVED_RETURNAL_ELEMENT != null){
                            JUST_IN_CASE = SAVED_RETURNAL_ELEMENT;
                            searchElement = SAVED_RETURNAL_ELEMENT;
                        }
                        {
                            double leastd = Integer.MAX_VALUE;
                            var iter = list.iterator();
                            while(iter.hasNext()){
                                var k = iter.next();
                                double x = searchElement.x-k.x;
                                double z = searchElement.z-k.z;
                                var d = Math.sqrt(x*x+z*z);
                                if(d < leastd){
                                    leastd = d;
                                    randomElement = k;
                                }
                            }
                        }


                        // boolean triggerUnwind = false;
                        if(correct){
                            int i = 0;
                            boolean triggered = false;
                            if(!BACKTRACK_ENGAGED){
                                for(var stack: BACKTRACK_ITEM_STACK_S){
                                    int depthMulti = (int)Math.pow(2,i++);
                                    if(stack.size() >= bt_amt * depthMulti){
                                        BACKTRACK_ITEM_STACK = stack; // set it to be this one
                                        triggered = true;
                                        JUST_IN_CASE = SAVED_RETURNAL_ELEMENT;
                                        // if(i== 0)SAVED_RETURNAL_ELEMENT = randomElement; 

                                        System.out.println("BREAKING ON TIER " + i);
                                        break;
                                    }
                                }
                            }

                            if(prestigelevel == 1){
                                // BACKTRACK_ENGAGED = false; // NO 
                                triggered = false;
                                if(Input.simulatedControlEnabled && !fwd_bwd.isEmpty()){
                                    randomElement = fwd_bwd.pop();
                                    System.out.println("WTF WTF " + randomElement.answer);
                                    passed.remove(quizCheck);
                                    passed.remove(randomElement);
                                }else{
                                    fwd_bwd.push(quizCheck);
                                }

                            }


                            if(triggered){
                                BACKTRACK_ENGAGED = true;
                                SAVED_RETURNAL_ELEMENT = randomElement; 
                                // this was the next found one I was gonna use
                                LOST_WASTED = BACKTRACK_ITEM_STACK.pop(); // fuck it
                                unwindme.push(LOST_WASTED);
                                randomElement = BACKTRACK_ITEM_STACK.pop();
                                unwindme.push(randomElement);
                            }else if(BACKTRACK_ENGAGED && !BACKTRACK_ITEM_STACK.isEmpty()){
                                randomElement = BACKTRACK_ITEM_STACK.pop();
                                System.out.println("working with popping... " + randomElement);
                                unwindme.push(randomElement);
                                // if(BACKTRACK_ITEM_STACK.size()%2 == 1){
                                // }else{
                                    // randomElement = BACKTRACK_ITEM_STACK.removeLast();
                                // }
                            }else if(BACKTRACK_ENGAGED){
                                //disengage
                                BACKTRACK_ENGAGED = false;
                                // for(var BACKTRACK_ITEM_STACK: BACKTRACK_ITEM_STACK_S)
                                //     BACKTRACK_ITEM_STACK.push(LOST_WASTED);
                                JUST_IN_CASE = SAVED_RETURNAL_ELEMENT;
                                SAVED_RETURNAL_ELEMENT = null; // was this important?

                                unwindme.addLast(randomElement); // this will be the one to end on
                                randomElement = unwindme.pop();
                                // trigger
                                // randomElement = SAVED_RETURNAL_ELEMENT;
                            }
                        }



                        if(!correct) randomElement = quizCheck;
                        System.out.println("Random element: " + randomElement.answer);
                        x = randomElement.x + Math.sin(randomElement.rot+Math.PI/2)*PortalNodule.range*4;
                        z = randomElement.z + Math.cos(randomElement.rot+Math.PI/2)*PortalNodule.range*4;
                        neck = randomElement.rot-Math.PI/2;
                        chin = 0;
    
                        // for(var p: pasties){
                        //     if(p instanceof Pasty){
                        //         ((Pasty)p).hidden = false;
                        //     }
                        // }
                        if(prestigelevel == 0 || prestigelevel == 1 || prestigelevel == 2){
                            hiddenCallBackTime = System.currentTimeMillis()+100_000;
    
                        } //noop
                        // if(prestigelevel == 1)
                        //     hiddenCallBackTime = System.currentTimeMillis()+700;
                        // if(prestigelevel == 2)
                        //     hiddenCallBackTime = System.currentTimeMillis()+700;
                        // if(prestigelevel == 3)
                        //     hiddenCallBackTime = System.currentTimeMillis()+300;
                        // if(prestigelevel == 4)
                        //     hiddenCallBackTime = System.currentTimeMillis()+300;
                        else{
                        // if(prestigelevel >= 5){
                            // for(var p: pasties){
                            //     if(p instanceof Pasty){
                            //         ((Pasty)p).hidden = true;
                            //     }
                            // }
                        }
                        
                        TimeLockingKey.globalTypingSwitch = quiztypingswitch;
                        priorQuizCheck = quizCheck;
                        quizTransitionEnergy = quizTransitionEnergy_DEFAULT;

                        quizCheck = randomElement;
                        wiggleItem = randomElement;
                        Spriter.setLateTexture(quizCheck.sprite);if(prestigelevel==4)Spriter.clearLateTexture();
                        if(BACKTRACK_ENGAGED)Spriter.clearLateTexture();

                        VISUAL_PROMPT_WORD = randomElement.prompt_word;

                    }
                }
            }
        }
    }
    codeTooLarge1();
    if(TimeLockingKey.globalTypingSwitch == fuzzytypingswitch){
        if(TimeLockingKey.keys.get(GLFW_KEY_UP).isNewPress()){
            fuzzycursorposition--;
        }
        if(TimeLockingKey.keys.get(GLFW_KEY_DOWN).isNewPress()){
            fuzzycursorposition++;
        }
        if(TimeLockingKey.keys.get(GLFW_KEY_ENTER).isNewPress()){
            {
                
                exacts.clear();
                // queryall
                pasties.queryAll();

                for(int i = 0; i < pasties.size(); i++){
                    GameObject p = pasties.getAt(i);
                    if(p instanceof Searcher){
                        var idk = ((Searcher) p).getWord();
                        
                        if(idk != null && !idk.equals("")){
                            exacts.put(idk, (Searcher) p);
                        // }else{
                        //     System.out.println("yeah, here");
                        //     neededGameObjects.add(p);
                            // System.exit(0);
                        }
                    }
                }
            }
            String str = TimeLockingKey.globalTypingSwitch.state.getLineAlone().trim();
            System.out.println("KICKING OFF search with "  + str);
            if(firstOption != null){
                str = firstOption;
            }
            if(str != null && exacts.get(str) != null){
                var searcher = exacts.get(str);
                if(grid_lock_mode){
                    gridOperation(str);
                }else
                if(nearest != null){
                    // SEND NEAREST
                    double desiredX = searcher.x + Math.sin(searcher.rot+Math.PI/2)*PortalNodule.range*8;
                    double desiredY = searcher.z + Math.cos(searcher.rot+Math.PI/2)*PortalNodule.range*8;
                    desiredX += 0.0001*Math.random();
                    nearest.rot = searcher.rot +  Math.PI/2;;
                    // failed strat
                    // pasties.update(nearest, desiredX, desiredY);

                    pasties.remove(nearest);
                    // nearest.clean();
                    nearest.x = desiredX;
                    nearest.z = desiredY;
                    nearest.ydisp = searcher.ydisp - 2;
                    PURGATORY.add(nearest);
                }else{
                    searcher.teleportPlayer();
                }
                Main.lastUsed.addFirst(str);
            }
            TimeLockingKey.globalTypingSwitch = null;

        }
    }
    if(TimeLockingKey.globalTypingSwitch == wordgentypingswitch){
        if(TimeLockingKey.keys.get(GLFW_KEY_ENTER).isNewPress()){
            String str = TimeLockingKey.globalTypingSwitch.state.getLineAlone().trim();
            // if(str != null)ASSIGNING_PROMPT_WORD = str;
            System.out.println("KICKING OFF YT THING WITH  "  + str);
            
            TimeLockingKey.globalTypingSwitch.state = new State();
            TimeLockingKey.globalTypingSwitch = null;
            Runnable arbfunc = () ->{

                long fulltime = 0;
                String inputPath = "";
                try{
                    
                // Thread.sleep(10000);
                // System.out.println("waited 10");

                    var f = (new File("temp"));
                    if(!f.exists()){
                        f.mkdirs();
                    }
                    String myname = "temp"+System.currentTimeMillis()+".mp4";
                    String myname2 = "processme"+System.currentTimeMillis()+".wav";
                    {
                        ProcessBuilder processBuilder = new ProcessBuilder(
                            "../../yt-dlp/yt-dlp.exe","-f", "bestvideo+bestaudio[ext=m4a]/best", "--ffmpeg-location", "..\\..\\ffmpeg\\ffmpeg.exe", str,  "-o", "./temp/"+myname
                        );


                        processBuilder.redirectErrorStream(true);
                        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                        Process process = processBuilder.start();

                        // Handle the process's input/output streams if necessary

                        // yt-dlp -f "bestvideo+bestaudio[ext=m4a]/best" %Var1% -o temp.mp4
                        //ffmpeg -i output.mp4 -vn -acodec pcm_s16le -ar 44100 -ac 1 processme.wav
                        //ffmpeg -i temp.mp4 -vn -acodec pcm_s16le -ar 44100 -ac 1 processme.wav
                        try {
                            System.out.println("before");
                            // Wait for a maximum of 1 second
                            if (!process.waitFor(60*3, TimeUnit.SECONDS)) {
                                System.out.println("Process timeout. Terminating the process.");
                                process.destroy(); // Terminate the process
                                // Handle the timeout situation, e.g., throw an exception or return an error
                            } else {
                                System.out.println("after");
                                System.out.println("YT STAGE 1 COMPLETED  "  + str);
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            // Handle the interruption
                        }
                    }
                    if(!(new File("./temp/"+myname).exists())){
                        System.out.println("mp4 version doesn't exist, tyring mkv");
                        myname+=".mkv";
                    }
                    {
                        try {
                            Path sourcePath = Paths.get("./temp/"+myname );
                            Path destinationPath = Paths.get("./DumpFolder/"+myname);
                            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    inputPath = "./temp/"+myname;
                    {
                        ProcessBuilder processBuilder = new ProcessBuilder(
                            "../../ffmpeg/ffmpeg.exe",
                            "-i",
                            "./temp/"+myname,
                            "-vn",
                            "-acodec",
                            "pcm_s16le",
                            "-ar",
                            "44100",
                            "-ac", "1","./temp/"+myname2 
                        );


                        processBuilder.redirectErrorStream(true);
                        processBuilder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
                        Process process = processBuilder.start();

                        // Handle the process's input/output streams if necessary

                        // yt-dlp -f "bestvideo+bestaudio[ext=m4a]/best" %Var1% -o temp.mp4
                        //ffmpeg -i output.mp4 -vn -acodec pcm_s16le -ar 44100 -ac 1 processme.wav
                        //ffmpeg -i temp.mp4 -vn -acodec pcm_s16le -ar 44100 -ac 1 processme.wav
                        try {
                            System.out.println("before");
                            // Wait for a maximum of 1 second
                            if (!process.waitFor(60*3, TimeUnit.SECONDS)) {
                                System.out.println("Process timeout. Terminating the process.");
                                process.destroy(); // Terminate the process
                                // Handle the timeout situation, e.g., throw an exception or return an error
                            } else {
                                System.out.println("after");
                                System.out.println("YT STAGE 2 COMPLETED  "  + str);
                            }
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            // Handle the interruption
                        }
                    }
                    {
                        try {
                            Path sourcePath = Paths.get("./temp/"+myname2 );
                            long size2 = (new File("./temp/"+myname2 )).length();
                            fulltime = (long)(size2 /(44.1)/2);
                            
                            Path destinationPath = Paths.get("./DumpFolder/processme.wav");
                            Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
                try{
                    System.out.println("A");
                    StemCell.main(inputPath, fulltime, inputPath + ".png");
                    System.out.println("B");
                    BufferedImage bi = ImageExpirements.pngToBI(inputPath + ".png");
                    System.out.println("C");
                    if(fulltime == 0)
                        fulltime =  StemCell.stemcellfulltime;
                    System.out.println("D");
                    waitherefulltime = fulltime;
                    waitheresidelength = StemCell.stemcellsidelength;
                    System.out.println("intermediary set, sl is " + waitheresidelength);
                    waithere = bi;
                }catch(Exception e){
                    e.printStackTrace();
                }

            };
            new Thread(arbfunc).start();
        }
    }

    boolean ludeonMode = TimeLockingKey.globalTypingSwitch instanceof LudeonNodule;
    if(TimeLockingKey.globalTypingSwitch == null) break;
    if(TimeLockingKey.globalTypingSwitch.state == null) break;
    if(TimeLockingKey.keys.get(GLFW_KEY_ENTER).isNewPress()){
        if(!ludeonMode){
            if(Input.simulatedControlEnabled){
                TimeLockingKey.globalTypingSwitch.state.conenter();
            }else{
                TimeLockingKey.globalTypingSwitch.state.enter();
            }
        }else{
        }
    }
    {
        
    }
    boolean f2 = TimeLockingKey.keys.get(GLFW_KEY_F2).isNewPress();

    if(Input.isKeyDown(GLFW_KEY_LEFT_CONTROL) || Input.isKeyDown(GLFW_KEY_RIGHT_CONTROL)){
    }else{
        if(TimeLockingKey.keys.get(GLFW_KEY_UP).isNewPress()){
            if(ludeonMode && ((LudeonNodule)TimeLockingKey.globalTypingSwitch).other_state != null){
                ((LudeonNodule)TimeLockingKey.globalTypingSwitch).setState();
            }else{
                if(Input.simulatedAltEnabled){
                    TimeLockingKey.globalTypingSwitch.state.swap(false);
                }else{
                    TimeLockingKey.globalTypingSwitch.state.asc();
                }
            }
        }
        if(TimeLockingKey.keys.get(GLFW_KEY_DOWN).isNewPress()){
            if(Input.simulatedAltEnabled){
                TimeLockingKey.globalTypingSwitch.state.swap(true);
            }else{
                TimeLockingKey.globalTypingSwitch.state.dec();
            }
        }
        if(!ludeonMode){
            if(TimeLockingKey.keys.get(GLFW_KEY_LEFT).isNewPress()){
                TimeLockingKey.globalTypingSwitch.state.left();
            }
            if(TimeLockingKey.keys.get(GLFW_KEY_RIGHT).isNewPress()){
                TimeLockingKey.globalTypingSwitch.state.right();
            }
        }else{
            var ln = (LudeonNodule)TimeLockingKey.globalTypingSwitch ;
            if(ln.other_state != null){
                if(TimeLockingKey.keys.get(GLFW_KEY_LEFT).isNewPress()){
                    ln.left();
                }
                if(TimeLockingKey.keys.get(GLFW_KEY_RIGHT).isNewPress()){
                    ln.right();
                }
            }
        }
        
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_5).isNewPress() && Input.simulatedControlEnabled){ 
        p("killing...");
        running = false;
        if(Input.simulatedShiftEnabled){
            expired_death = true;
        }
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_X).isNewPress() && Input.simulatedControlEnabled){
        var state = TimeLockingKey.globalTypingSwitch.state;
        System.out.println("at least here");
        // String text = TimeLockingKey.globalTypingSwitch.state.cutLine();
        String text = "";
        if(!state.hasHighlight()){
             text = state.cutLine();
        }else{
            System.out.println("at least here 2");

            text = state.cutHighlight();
        }
        System.out.println("Text acquired is :");
        System.out.println(text);
        StringSelection stringSelection = new StringSelection(text);
        try{
            clipboard.setContents(stringSelection, stringSelection);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    if(!ludeonMode){
        if(TimeLockingKey.keys.get(GLFW_KEY_T).isNewPress() && Input.simulatedControlEnabled){
            TimeLockingKey.globalTypingSwitch.state.dupeRow();
        }
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_R).isNewPress() && Input.simulatedControlEnabled){ 
        // run it run it run it,
        // seek first batch and run it, this code is ancient and forgotten and lost
        // more important than clickcontexting, though maybe not
        List<File> files = getFilesInFolder("./VirtualFolder/");
        for(var file: files){
            if(file.getAbsolutePath().endsWith(".bat")){
                System.out.println("found batch " + file.getAbsolutePath());
                // ProcessBuilder processBuilder = new ProcessBuilder();
                // // Windows
                // processBuilder.command("cmd.exe", "/c", "\""+file.getAbsolutePath() + "\"");
                // // Linux/Mac
                // // processBuilder.command("sh", "-c", "path/to/your/script.sh");
                // try {
                //     Process process = processBuilder.start();
                //     int exitCode = process.waitFor();
                //     System.out.println("Exited with code: " + exitCode);
                // } catch (IOException | InterruptedException e) {
                //     e.printStackTrace();
                // }
                Process process = Runtime.getRuntime().exec("cmd.exe /c \""+file.getAbsolutePath() + "\"");

                // Linux/Mac
                // Process process = Runtime.getRuntime().exec("sh -c path/to/your/script.sh");

                printStream(process.getInputStream(), "OUTPUT");
                printStream(process.getErrorStream(), "ERROR");

                // int exitCode = process.waitFor();
                try {
                    System.out.println("before");
                    // Wait for a maximum of 1 second
                    if (!process.waitFor(10, TimeUnit.SECONDS)) {
                        System.out.println("Process timeout. Terminating the process.");
                        process.destroy(); // Terminate the process
                        // Handle the timeout situation, e.g., throw an exception or return an error
                        // statements.addFirst("terminated");
                        addStatement("failure");
                    } else {
                        System.out.println("after");
                        // System.out.println("YT STAGE 2 COMPLETED  "  + str);
                        // statements.addFirst("success");
                        addStatement("success");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    // Handle the interruption
                }
                // System.out.println("Exited with code .... : " + exitCode);
                break;
            }
        }
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_S).isNewPress() && Input.simulatedControlEnabled){ 
        if(TimeLockingKey.globalTypingSwitch instanceof FileNodule){
            var fn = (FileNodule) TimeLockingKey.globalTypingSwitch;
            // CTRL-S TO DUMP TO FILE

            // convert state into asci bytes and write to file
            // HOPING that it will get caught by the overwriter

            if(fn.state != null && fn.oldStateForSaving != null){
                var p ="./VirtualFolder/"+ buildFolderAsKey(fn.transientPath, "");
                System.out.println("path is... " + p);
                var val = fn.transientKey;
                String out = "./VirtualFolder/"+val;

                Path path = Paths.get(p);
                FileOutputStream fileOutputStream = new FileOutputStream(out);
                byte[] bytes = fn.state.stringify().getBytes("UTF-8");
                fileOutputStream.write(bytes);
                fileOutputStream.close();
                System.out.println("DONE WRITING!... " + out);
            }

        }
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_F).isNewPress() && Input.simulatedControlEnabled){ 
        // p("killing...");
        // running = false;
        // if(Input.simulatedShiftEnabled){
        //     expired_death = true;
        // }
        System.out.println("Converting it...");
        FileNodule fn = null;

        if(TimeLockingKey.globalTypingSwitch instanceof FileNodule){
            fn = (FileNodule) TimeLockingKey.globalTypingSwitch;

            // INTERPRET BYTES AS ASCII (String)
            // STRING TO STATE CONVERSION
            // CTRL-S TO DUMP TO FILE (NOT HERE)

            String str = new String(fn.bytes, StandardCharsets.UTF_8);

            if(fn.state != null && fn.oldStateForSaving == null){
                TimeLockingKey.globalTypingSwitch = fn; // 
                fn.oldStateForSaving = fn.state; // CRITICAL SWAP
            }
            TimeLockingKey.globalTypingSwitch.state = new State();
            String data = str;
            Scanner s = new Scanner(data);
            int i = 0;
            while(s.hasNextLine()){
                if(i!= 0){
                    TimeLockingKey.globalTypingSwitch.state.enter(false);
                }
                i++;
                TimeLockingKey.globalTypingSwitch.state.insert(s.nextLine());
            }
            s.close();
        }
        
    }
    if(TimeLockingKey.keys.get(GLFW_KEY_C).isNewPress() && Input.simulatedControlEnabled){
        // var p =  TimeLockingKey.globalTypingSwitch.state.getPic();
        // if(p != null){
        //     intoInventory(p.clone());
        // }else{
        //     String text = TimeLockingKey.globalTypingSwitch.state.getLine();
        //     StringSelection stringSelection = new StringSelection(text);
        //     try{
        //         clipboard.setContents(stringSelection, stringSelection);
        //     }catch(Exception e){
        //         e.printStackTrace();
        //     }
        // }
         var state = TimeLockingKey.globalTypingSwitch.state;
        System.out.println("at least here");
        // String text = TimeLockingKey.globalTypingSwitch.state.cutLine();
        String text = "";
        if(!state.hasHighlight()){
            //  text = state.cutLine();
        }else{
            System.out.println("at least here 2");

            text = state.copyHighlight();
            StringSelection stringSelection = new StringSelection(text);
            try{
                clipboard.setContents(stringSelection, stringSelection);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        }
    if(TimeLockingKey.keys.get(GLFW_KEY_V).isNewPress() && Input.simulatedControlEnabled){
        if(false && tabselection != -1 && tabselection < bobbies.size()){
            if(bobbies.get(tabselection) instanceof Pasty){
                var b = (Pasty)bobbies.get(tabselection);
                b = b.clone();
                TimeLockingKey.globalTypingSwitch.state.insert(new RichChar(b));
                if(ludeonMode){
                    TimeLockingKey.globalTypingSwitch.state.enter();
                    TimeLockingKey.globalTypingSwitch.state.asc();
                } 
            }
        }else{
            boolean doEet = false;
            try{
                if(!ludeonMode){
                    String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
                    Scanner s = new Scanner(data);
                    int i = 0;
                    while(s.hasNextLine()){
                        if(i!= 0){
                            TimeLockingKey.globalTypingSwitch.state.enter(false);
                        }
                        i++;
                        TimeLockingKey.globalTypingSwitch.state.insert(s.nextLine());
                    }
                    s.close();
                }
            }catch(Exception e){
                doEet = true;
            }
            if(doEet || ludeonMode){
                System.out.println("IT WASNT A STRING");
                try{
                    Pasty p = new Pasty();
                    Transferable t = clipboard.getContents(null);
                    Image i = (Image)t.getTransferData(DataFlavor.imageFlavor);
                    BufferedImage bi = imageToBufferedImage(i);
                    p.sprite = new Spriter(bi, p.sl(), p.mso());
                    p.width = bi.getWidth();
                    p.height = bi.getHeight();
                    p.data = p.sprite.tempIntDataForStorage;
                    // p.sprite.tempIntDataForStorage = null;
                    p.whrat = bi.getWidth()*1.0/bi.getHeight();
                    TimeLockingKey.globalTypingSwitch.state.insert(new RichChar(p));
                    if(ludeonMode){
                        TimeLockingKey.globalTypingSwitch.state.enter();
                        TimeLockingKey.globalTypingSwitch.state.asc();
                    } 
                }catch(Exception e2){
                    e2.printStackTrace();
                }
            }
            //PASTEPIC
            
        }
    }
    if(!Input.simulatedControlEnabled && !ludeonMode){
        int l = TextHelper.charToOffsetLookup.length;
        int[] lkp = TextHelper.charToOffsetLookup;
        State s = TimeLockingKey.globalTypingSwitch.state;
        for(int i = 0; i < l; i++){
            if(lkp[i] != -1){
                int kc = i;
                if(kc >= 65 && kc <= 90){ //turbo bodge to fix the fact the default is uppercase
                    kc += 32;
                }
                if(Input.isKeyDown(GLFW_KEY_LEFT_SHIFT) || Input.isKeyDown(GLFW_KEY_RIGHT_SHIFT)){
                    int n = TextHelper.irregularLookup[kc]; 
                    if(n != -1){
                        kc = n; // shift to alternate key
                    }
                }
                if(TimeLockingKey.tlkArray[i].isNewPress()){
                    char c = Character.toChars(kc)[0];
                    try{
                        s.insert(c);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    
                }
            }
        }
    }
    if(!Input.simulatedControlEnabled){
        State s = TimeLockingKey.globalTypingSwitch.state;
        if(TimeLockingKey.keys.get(GLFW_KEY_BACKSPACE).isNewPress()){
                s.backspace();
        }
        if(TimeLockingKey.keys.get(GLFW_KEY_TAB).isNewPress() ){ 
            if(!Input.simulatedControlEnabled){
                // if(!Input.simulatedShiftEnabled){
                //     tabselection = (tabselection+2)%(bobbies.size()+1) + -1;
                // }else{
                //     tabselection = tabselection-1;
                //     if(tabselection == -2) tabselection = bobbies.size()-1;
                // }
            }
        }
    }
    //GLFW_KEY_PAGE_DOWN,GLFW_KEY_PAGE_UP,GLFW_KEY_HOME,GLFW_KEY_END
    {
        if(TimeLockingKey.keys.get(GLFW_KEY_PAGE_UP).isNewPress()){
            // TimeLockingKey.globalTypingSwitch.state.top();
            for(int i = 0; i < 10; i++){
            TimeLockingKey.globalTypingSwitch.state.asc();
            }
            dispy+=0.4;
        }
        if(TimeLockingKey.keys.get(GLFW_KEY_PAGE_DOWN).isNewPress()){
            // TimeLockingKey.globalTypingSwitch.state.bot();
            for(int i = 0; i < 10; i++){
                TimeLockingKey.globalTypingSwitch.state.dec();
                }
            dispy-=0.4;

        }
        if(TimeLockingKey.keys.get(GLFW_KEY_HOME).isNewPress()){TimeLockingKey.globalTypingSwitch.state.home();}
        if(TimeLockingKey.keys.get(GLFW_KEY_END).isNewPress()){TimeLockingKey.globalTypingSwitch.state.end();}

    }
    if(TimeLockingKey.keys.get(GLFW_KEY_ESCAPE).isNewPress()){

        // just incase
        if(fortounload != null){
            if(fortounload instanceof MultipleChoice){
                System.out.println("YIPPEE GOODY!");
                var holder = fortounload;
                // System.exit(0);
                PromiseNodule pn = new PromiseNodule();
                presumed_assumed_edited_multiple_choice =(MultipleChoice) fortounload;
                Runnable arbfunc = () -> {
                    try{
                        System.out.println(holder);
                        System.out.println(holder.data);
                        System.out.println(holder.width);
                        System.out.println(holder.height);
                        var pngBytes = RawToPng.rawToPng(holder.data,holder.width,holder.height);
                        LinkedList<String> ch =
                            ChatImageInput.call2(pngBytes);
                        System.out.println(ch);
                        System.out.println("hey...");
                        // System.exit(0);
                        MULTIPLECHOICE_RESULT_PURGATORY.add(ch);
                        
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    // Chat2.call2(str);
                    // LinkedList<LinkedList<String>> ch = ChatImageInput.call2("test test test");
                };
                Thread t = new Thread(arbfunc);
                pn.watchThread = t;
                t.start();
            }

            // renormalize the x value somehow of the mouse, idk
            // zero it somehow,
            // send the mouse to the middle!!!
            {
                int[] temp1 = new int[1];
                int[] temp2 = new int[1];
                GLFW.glfwGetWindowPos(window, temp1, temp2);

                // set cursor to middle, IDGAF
                glfwSetCursorPos(window,buggaX/2+temp1[0], buggaY/2+temp2[0]);
                // System.exit(0);
            }


            fortounload.hidden = false;
            fortounload = null;
                if(fortounload instanceof BurningBush){
                    var bb = (BurningBush)fortounload;
                    bb.sprite.repaunch = false;
                }
            fortounload_last_x = null;
            textstamp.state = new State(); // FUCK THAT
            poppedPasty = null;
        }
        TimeLockingKey.globalTypingSwitch = null;
        JUST_IN_CASE = SAVED_RETURNAL_ELEMENT;
        SAVED_RETURNAL_ELEMENT = null;
        for(var BACKTRACK_ITEM_STACK: BACKTRACK_ITEM_STACK_S)
            BACKTRACK_ITEM_STACK.clear();
        unwindme.clear();
        BACKTRACK_ENGAGED = false;
        toPromoteToQuizItem = null;
        textSheet.repaunch = true;
        textSheet3.repaunch = true;
        textSheet.clearRepaunch();
        fwd_bwd.clear();
        System.out.println("So odd 1");

    }
}
}
}

{
    for(int i = 0; i < TimeLockingKey.tlkArray.length; i++){
        TimeLockingKey.tlkArray[i].update(delta);
    }
}
time("deleteme", false);
if(TimeLockingKey.globalTypingSwitch != null && TimeLockingKey.globalTypingSwitch.state != null){
    // p("BEEP");
    textSheet2.drawString("Typing: on" + TimeLockingKey.globalTypingSwitch.state.cx + ", " + TimeLockingKey.globalTypingSwitch.state.cy + ", " + TimeLockingKey.globalTypingSwitch.state.desiredCX, 0.8, 0.75, 0, 0.03, 255, 255, 0);
}
/******** DRAWING DRAWING DRAWING DRAWING DRAWING ********/
nano_timings[timingsIndex][4] = System.currentTimeMillis() - nano_timings[timingsIndex][4];
nano_timings[timingsIndex][5] = System.currentTimeMillis();

glClearColor(0f, 0f, 0f, 1.0f);
glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

{
    long nownow = System.currentTimeMillis();
    pasties.query2D(x-500, x+500, z-500, z+500);
    Set<BurningBush> bbsetthis = new HashSet<BurningBush>();

    // sounds
    for(int i = 0; i < pasties.size(); i++){
        GameObject go = pasties.getAt(i);
        if(go instanceof BurningBush){

            BurningBush bb = (BurningBush) go;
            if(/* NOT IN THE SAME WHATEVER */ bb.getSectorKey() != GameObject.getSectorKeyGiven((int)x,(int)z))
                continue;
            bbsetthis.add(bb);
            // bb.nextMillisToPlay < nownow;
            long dif = Math.max(nownow - bb.nextMillisToPlay, 0);

            if(bb.nextMillisToPlay < nownow){
                bb.playing = false;
            }
            {
                long fulltime = (long)(bb.audioBytes.length /(44.1)/2);
                if(fulltime != 0){
                    long clock = System.currentTimeMillis();
                    fulltime = (long)(fulltime*(bb.whereamioveride_end - bb.whereamioveride));
                    // fulltime = 
                    // long remainder = (clock%fulltime);
                    // double whereami = (remainder*1.0/fulltime);
                    double untilend = 1-(bb.nextMillisToPlay - nownow) * 1.0 / fulltime;
                    bb.currentwhereami =  bb.whereamioveride +  untilend*(bb.whereamioveride_end-bb.whereamioveride);
                }
            }
            // System.out.println(bb.loadedSource + " " + bb.playing);

            // if(bb.displayMode == 2 && !bb.playing){
            if(bb.displayMode == 2 && bb.loadedSource != 0 && !bb.playing){
                // play it and inset it, jesus
                // System.out.println("fulltime is "  + bb.audioBytes.length);
                long fulltime = (long)(bb.audioBytes.length /(44.1)/2);

                // if the fulltime was less,
                // than the full length, would that be valid?
                // say a percentage
                // try{
                //     Thread.sleep(300);
                // }catch(Exception e){
                    
                // }
                if(fulltime != 0){
                    bb.playing = true;

                    // System.out.println("fulltime is "  + fulltime);
                    long clock = System.currentTimeMillis();
                    long remainder = (clock%fulltime);
                    // System.out.println("remainder is  "  + remainder);
                    double whereami = (remainder*1.0/fulltime);
                    if(bb.whereamioveride != 0){
                        whereami = bb.whereamioveride;
                    }
                    // System.out.println("WHY ISN'T IT WORKING ???? " + bb.displayMode);
                    // System.out.println("whereami is  "  + whereami);
                    // System.out.println("going to try..." + (int)(whereami*bb.audioBytes.length));
                    {
                        // I want to delete it but NO!s
                        AL10.alDeleteBuffers(bb.loadedBuffer);
                        AL10.alDeleteSources(bb.loadedSource);
                        bb.loadedBuffer = AL10.alGenBuffers();
                        bb.loadedSource =  AL10.alGenSources();
                        // bb.fuckmebuffer.clear();
                        // bb.fuckmebuffer.put(bb.audioBytes);
                        // bb.fuckmebuffer.flip();
                    }
                    bb.fuckmebuffer.position((int)(whereami*bb.audioBytes.length)/2*2);
                    // bb.fuckmebuffer.position((int)(bb.fuckmebuffer.limit()*Math.random()));
                    // bb.fuckmebuffer.position(477520+2);
                    bb.reloadaaa();
                    bb.sprite.repaunch = false; // sometimes I am just SOO stupid

                    AL10.alSourcePlay(bb.loadedSource);
                    double end = 1;
                    if(bb.whereamioveride_end != 0){
                        end = bb.whereamioveride_end;
                    }
                    bb.nextMillisToPlay = nownow + (long)(fulltime*(end-whereami));
                    // System.out.println("delay is ... " + (long)(fulltime*(1-whereami)));
                }
            }
            float x = -(float)(go.x - Main.x);
            float y = -(float)(go.ydisp - Main.y);
            float z = -(float)(go.z - Main.z);
            {
                double dist = Math.sqrt(x*x+z*z);
                double angle = Math.atan2(z, x);
                angle += neck+Math.PI;
                x = (float)(dist*Math.cos(angle));
                z = (float)(dist*Math.sin(angle));
            }
            {
                double dist = Math.sqrt(y*y+z*z);
                double angle = Math.atan2(y, z);
                angle += chin;
                y = (float)(dist*Math.sin(angle));
                z = (float)(dist*Math.cos(angle));
            }
            // System.out.println("current: " + x + '\t' + y + '\t'+ z + '\t' + Math.sqrt(x*x + z*z + y*y));
            // FUCK I DON'T NEED IT IN A 2x2x2,
            // well the resizign I mean, all I need is
            // is the rotations,
            // no distance, no whatever,
            // just rotate the points

            // System.out.println("IT'S HAPPENING!!!");
            AL10.alSource3f(bb.loadedSource, AL10.AL_POSITION, x, y, z);
            AL10.alSource3f(bb.loadedSource, AL10.AL_VELOCITY, 0, 0, 0);
            AL10.alSource3f(bb.loadedSource, AL10.AL_DIRECTION, 0, 0, 0);

            float rolloffValue = (float)bb.rolloffValue; // Increase this to make the falloff steeper
            AL10.alSourcef(bb.loadedSource, AL10.AL_ROLLOFF_FACTOR, rolloffValue);
            AL10.alSourcef(bb.loadedSource, AL10.AL_GAIN, (float)(1.0f*bb.size));
        }
    }
    {
        // MUTE STRAGGLERS
        bbsetlast.removeAll(bbsetthis);
        Set<BurningBush> stragglers = bbsetlast;
        for(BurningBush straggler: stragglers){
            straggler.hush();
        }
        bbsetlast = bbsetthis;
    }
}

glfwSwapInterval(0);

glfwMakeContextCurrent(window);
// fightSpriter.drawSprite(0, 0.2, 0, 0.5, 0.4, 0.4, elapsed, Math.abs(1-elapsed%2));

double ze = Math.abs(1-elapsed%2);
var b = new ColorGroup(48,204,40,224,127,0, 127,117,48);
var clueScrollBrown = new ColorGroup(70,51,9); 
var silverGray = new ColorGroup(212,212,222);
var tnode2red = new ColorGroup(255,0,51);
var micColors = new ColorGroup(0,0,0, 128,128,128, 255,216,0);
var portColors = new ColorGroup(0,0,0, 128,128,128);
var chestColors = new ColorGroup(255,255,255, 128,128,128);
var marcColors = new ColorGroup(48,204,40, 117,144,52, 127,117,48 );


if(!Main.focused){
    Thread.sleep(250);
    // fightSpriter.drawSprite(29, 0, 0, 0, 2, 2, 0, 0.5);
    int idk = 19;
    int i = 0;
    // textSheet2.drawString("     CTRL Q : QUIT        ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*56, 2*56);
    // textSheet2.drawString("          F : Make Path   ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("          Z : Toggle Speed",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("             G : Watch Clipboard",   0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("          O : Make Portal ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("`(backtick) : Home        ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("ctrl `      : Spawn       ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("ctrl O      : Set Home    ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("(shift) Tab : Inv select  ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("     ctrl C : Copy        ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("     ctrl V : Place       ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("      Space : Pickup      ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("ctrl shft C : copy to clip",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("          L : New Text    ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("          T : New File    ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("          L : New Link    ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("      enter : edit text   ",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString(" ctrl alt C : open in fold",         0, -1+2.0/idk*(1+ i++), 0, 2.0/(idk+2), 112*2, 2*112,2* 112);
    // textSheet2.drawString("Menu", 0, 0, 0, 0.3, 56, 56, 56);
}
if(TimeLockingKey.globalTypingSwitch == textstamp){
    double[] temp1 = new double[1];
    double[] temp2 = new double[1];
    glfwGetCursorPos(window,temp1,temp2);
    double _mx = temp1[0]/buggaX*2-1;
    double _my = temp2[0]/buggaY*2-1;
    LinkedList<String> idk = textstamp.state.getLines();
    int i = 0;
    Color color = scalarToColor(mouseWheelRotation, 0, 1000);
    int red = color.getRed();
    int green = color.getGreen();
    int blue = color.getBlue();
    double ts = 0.15 * textstamp_size;
    for(var line: idk){
        textSheet2.drawString(line, _mx, -_my-i*ts+(idk.size()-1)*ts/2, 0, ts, red, green, blue);
        i++;
    }
}

if(TimeLockingKey.globalTypingSwitch == wordgentypingswitch ||
    TimeLockingKey.globalTypingSwitch == fuzzytypingswitch || TimeLockingKey.globalTypingSwitch == multiplechoicetypingswitch ){
    var a = TimeLockingKey.globalTypingSwitch == wordgentypingswitch;
    TextNodule bigdraw = a?
        wordgentypingswitch : fuzzytypingswitch;
    if(TimeLockingKey.globalTypingSwitch == multiplechoicetypingswitch){
        bigdraw = multiplechoicetypingswitch;
    }
    textSheet2.drawString(bigdraw.state.getLineAlone().trim(), 0, 0, 0, 0.11, 56, 216, 56);
    if(TimeLockingKey.globalTypingSwitch == multiplechoicetypingswitch){
        textSheet2.drawString("What topic?", 0, 0.7, 0, 0.09, 255, 180, 0);
    } 
    else if(a)
        textSheet2.drawString("PLEASE ENTER A YouTube URL", 0, 0.7, 0, 0.09, 255, 180, 0);    
    else{
        if(nearest != null){
            textSheet2.drawString("send object to", 0, 0.7, 0, 0.09, 255, 180, 0);    

        }else{

            textSheet2.drawString("goto", 0, 0.7, 0, 0.09, 255, 180, 0);    
        }

        // for v1 just have it here
        // framerate IDFC
        // List<String> words = List.of("apple", "banana", "grape", "apricot", "pineapple");
        List<String> words = new ArrayList<>(exacts.keySet());
        String query = "ap";
        query = TimeLockingKey.globalTypingSwitch.state.stringify().split("\\R", 2)[0];
        List<String> results = FuzzySearch.fuzzySearch(query, words);
        int blue = 0;
        if(query.equals("")){
            results = lastUsed;
            blue = 128;
        }
        if(!results.isEmpty() && results.size() > fuzzycursorposition &&  fuzzycursorposition >= 0){
            firstOption = results.get(fuzzycursorposition);
        }else{
            firstOption = null;
        }
        if(results.size() != 0)
            System.out.println(Arrays.toString(results.toArray()));

        var i = results.iterator();
        int ii = 1;
        int oldblue = blue;
        while(i.hasNext()){
            String el = i.next();
            if(/*selected*/ ii -1 == fuzzycursorposition){
                blue = 256;
            }else{
                blue = oldblue;
            }
            textSheet2.drawString(el, 0, 0.7 - 0.09*(ii++), 0, 0.09, 220, 186, blue);    
            if(ii == 6) break;
        }








    }

}
if(TimeLockingKey.globalTypingSwitch == quiztypingswitch){
    
    textSheet2.drawString(quiztypingswitch.state.getLineAlone().trim(), 0, 0, 0, 0.3, 120, 120, 120);
    if(answer != null && guess != null){
        boolean right = answer.equals(guess);
        textSheet2.drawString(answer, -0.8, 0.85, 0, 0.1, 220, 220, 0);
        textSheet2.drawString(guess, -0.8, 0.75, 0, 0.1, right?0:220, right?220:0, 0);
    }
    textSheet2.drawString("filteringCID: " + filteringCohortID, -0.8, 0.65, 0, 0.05, 220, 220, 0);    
    textSheet2.drawString("Mastery: " + passed.size() + "/" + lastAllQuizSize, -0.8, 0.7, 0, 0.05, 220, 220, 0);    
    textSheet2.drawString("prestige: " + prestigelevel, -0.8, 0.55, 0, 0.05, 220, 220, 0);    
    if(BACKTRACK_ENGAGED)textSheet2.drawString("BACKTRACKING ", -0.8, 0.5, 0, 0.05, 220, 0, 0);    
    textSheet2.drawString("fwdbwd: " +fwd_bwd.size(), -0.45, 0.7, 0, 0.05, 220, 220, 0);    
    // if(quizCheck != null)
    //     textSheet2.drawString(": " +quizCheck.prompt_word, 0, 0.7, 0, 0.29, 255, 180, 0);    
}
int iiiii = 0;
for(var item: score){
    int so = item ? 3 : 6;
    fightSpriter.drawSprite(so, -0.9+iiiii*0.09, 0.95, 0, 0.07, 0.07, 0, 1);
    iiiii++;
}


// reassign();

time("new-age");
{

    // if(!hidePQIOverlay){
    //     double lowest = 100;
        
    //     ParkerQuizItem nearestPQI = null;
    //     pasties.query2D(x-500, x+500, z-500, z+500);
    //     for(int i = 0; i < pasties.size(); i++){
    //         GameObject p = pasties.getAt(i);
    //         double dx = p.x-x;
    //         double dz = p.z-z;
    //         double dist = Math.sqrt(dx*dx+dz*dz);
    //         if(dist < lowest && p instanceof ParkerQuizItem){
    //             lowest = dist;
    //             nearestPQI = (ParkerQuizItem)p;
    //         }
    //     }
    //     // System.out.println(lastNearestPQI + " vs. " + nearestPQI);
    //     if(nearestPQI != null){
    //         double size2 = lowest / nearestPQI.alignment(neck, chin);
    //         if(size2 < 0.01) size2 = 0.01;
    //         Spriter.distanceUniformValue = (float)size2;
    //     }

    //     // Spriter.distanceUniformValue = 5; // override for test
    //     if(lastNearestPQI != nearestPQI){
    //         // trigger pqi 
    //         lastNearestPQI = nearestPQI;
    //         if(nearestPQI == null){
    //             Spriter.clearLateTexture(); // override for test
    //         }else{
    //             if(nearestPQI.sprite == null)
    //                 lastNearestPQI = null; // will keep it restless until it loads
    //             else{
    //                 Spriter.setLateTexture(nearestPQI.sprite, nearestPQI.mode);

    //                 {
    //                     for(int i = 0; i < 10; i++){
    //                         int k  = (swaths[42+i].getColorFromCompletedMap((int)nearestPQI.x-1_000_000,(int)nearestPQI.z-1_000_000));
    //                         Spriter.sinflavors[i*3+0]= r(k)/255.0f;
    //                         Spriter.sinflavors[i*3+1]= g(k)/255.0f;
    //                         Spriter.sinflavors[i*3+2]= b(k)/255.0f;
    //                     }
    //                 }
    //             }
    //         }
    //     }
    // }

    nearest = null;
    nearestself = null;
    {
        // ADJUST TO PERFECTION
        double lowest = 10;

        pasties.query2D(x-50, x+50, z-50, z+50);
        for(int i = 0; i < pasties.size(); i++){
            GameObject p = pasties.getAt(i);
            if(false && p instanceof FileNodule){
                var fn = (FileNodule)p;

                // if fn is loaded
                // if fn hasnotqueriedbefore
                // dump file to wherver, temp
                // kickoff job
                // model after f4
                if(fn.attemptedPostProcessing && fn.bytes != null
                    && fn.cachedIsFileInterestingCheck){
                    String[] interstingExtensions = new String[]{
                        ".mp3",
                        ".mp4",
                        ".webm",
                        ".mkv",
                        ".avi", // ? test?
                        ".gif"
                    };
                    var f = (new File("temp"));
                    if(!f.exists()){
                        f.mkdirs();
                    }

                    // does kjldfjglksdjfg


                    // will ffmpeg handle all of this automagically?
                    // what is the file's extension

                    String out = "./temp/"+"idk";
                    FileOutputStream fileOutputStream = new FileOutputStream(out);
                    fileOutputStream.write(fn.bytes);
                    fileOutputStream.close();
                }


                

            }
        }
        double projectedx = x+9*Math.sin(neck);
        double projectedz = z+9*Math.cos(neck);
        // for(GameObject p: pasties){
        Set<String> set1 = new HashSet<>();
        for(int i = 0; i < pasties.size(); i++){
            GameObject p = pasties.getAt(i);
            double dx = p.x-projectedx;
            double dz = p.z-projectedz;
            double dist = Math.sqrt(dx*dx+dz*dz);
            boolean canmove = !(p instanceof ThreeDumb) || ((ThreeDumb)p).displayMode == 2;
            canmove = true;
            if(dist < lowest && canmove){
                lowest = dist;
                nearest = p;
            }
            if(dist < 50 && p instanceof LinkNodule){
                var l = (LinkNodule)p;
                if(l.state != null){
                    set1.add(l.state.stringify(""));
                }
            }
        }
        
        if(ParkerSetter.myset != null)
            ParkerSetter.myset.updateSet(set1);

        if(nearest != null){
            GameObject p = nearest;
            // nearest sound intensity idea
            double dx = p.x-projectedx;
            double dz = p.z-projectedz;
            float dist = (float)Math.sqrt(dx*dx+dz*dz);

            // if(dist != 0)
            //     AL10.alListenerf(AL10.AL_GAIN, 400.0f/(float)`(dist,2.2));

        }
        for(GameObject p: skysigns){
            double dx = p.x-projectedx;
            double dz = p.z-projectedz;
            double dist = Math.sqrt(dx*dx+dz*dz);
            if(dist < lowest){
                lowest = dist;
                nearest = p;
            }
        }
        boolean holdingFunnel = (bobbies.size() >= 1) && bobbies.get(0) instanceof FunnelNodule; 
        if(holdingFunnel){
            var fn = (FunnelNodule)bobbies.get(0);
            for(GameObject p: fn.phantoms){
                double dx = p.x-projectedx;
                double dz = p.z-projectedz;
                double dist = Math.sqrt(dx*dx+dz*dz);
                if(dist < lowest){
                    lowest = dist;
                    nearest = p;
                }
            }
        }
    }
    {
        GameObject go = nearest;
        if(go instanceof Pasty){
            var p = ((Pasty)go);
            if(p.sprite != null)p.sprite.clearRepaunch();
        }
    }
    if(nearest != lastnearest){
        GameObject go = lastnearest;
        if(go instanceof Pasty){
            var p = ((Pasty)go);
            if(p.sprite != null)p.sprite.clearRepaunch();
        }
    }
    lastnearest = nearest;
    {
        double lowest = 15;

        pasties.query2D(x-50, x+50, z-50, z+50);
        double projectedx = x;
        double projectedz = z;
        // for(GameObject p: pasties){
        for(int i = 0; i < pasties.size(); i++){
            GameObject p = pasties.getAt(i);
            double dx = p.x-projectedx;
            double dz = p.z-projectedz;
            double dist = Math.sqrt(dx*dx+dz*dz);
            boolean canmove = !(p instanceof ThreeDumb) || ((ThreeDumb)p).displayMode == 2;
            if(dist < lowest && canmove){
                lowest = dist;
                nearestself = p;
            }
        }

        if(nearest != null){
            GameObject p = nearest;
            // nearest sound intensity idea
            double dx = p.x-projectedx;
            double dz = p.z-projectedz;
            float dist = (float)Math.sqrt(dx*dx+dz*dz);

            // if(dist != 0)
            //     AL10.alListenerf(AL10.AL_GAIN, 400.0f/(float)`(dist,2.2));

        }
        for(GameObject p: skysigns){
            double dx = p.x-projectedx;
            double dz = p.z-projectedz;
            double dist = Math.sqrt(dx*dx+dz*dz);
            if(dist < lowest){
                lowest = dist;
                nearestself = p;
            }
        }
    }

    if(nearestself instanceof PortalNodule ){
        var portal = (PortalNodule) nearestself;
        double dx = portal.x-x;
        double dz = portal.z-z;
        double dist = Math.sqrt(dx*dx+dz*dz);
        if(portal.activationtime + 1000 < System.currentTimeMillis()&& dist < PortalNodule.range){
            portal.activationtime = System.currentTimeMillis();
            portal.activate();
            // p("activating!!!");
            // p("activating!!!");
            // p("activating!!!");
            // p("activating!!!");
            // p("activating!!!");
        }
    }
    if(nearestself instanceof KeyNodule){
        ((KeyNodule)nearestself).activate();
    }
    if(nearestself instanceof TenPortals){
        var link = (TenPortals) nearestself;
        double dx = link.x-x;
        double dz = link.z-z;
        double dist = Math.sqrt(dx*dx+dz*dz);
        if(dist < PortalNodule.range){
            // link.launch();
            globalTenPortals = (TenPortals)link.clone();
            activeTenPortals = globalTenPortals;
            // link.loadme();
            // repell character
            x+= Math.sin(link.rot+Math.PI/2)*PortalNodule.range*4;
            z+= Math.cos(link.rot+Math.PI/2)*PortalNodule.range*4;
        }
    } 
    if(nearestself instanceof FollowPath){
        var link = (FollowPath) nearestself;
        double dx = link.x-x;
        double dz = link.z-z;
        double dist = Math.sqrt(dx*dx+dz*dz);
        if(dist < PortalNodule.range){

            walkthrough_replay = (LinkedList<Tuple<Long, PortalNodule>>)link.movements.clone();
            walkthrough_replay_stamp = System.currentTimeMillis();
            walkthrough_replay_on = true;
            x+= Math.sin(link.rot+Math.PI/2)*PortalNodule.range*4;
            z+= Math.cos(link.rot+Math.PI/2)*PortalNodule.range*4;
        }
    }
    subtiming("new-age");
    boolean lateLoadComplete = (missingGameObjects != null && missingGameObjects.isEmpty());
    if(nearestself instanceof FileNodule || nearestself instanceof ObeliskNodule 
        || nearestself instanceof CompressorNodule
        || lateLoadComplete || designatedObelisk != null){
        boolean nearestCompressorFlag = nearestself instanceof CompressorNodule;
        CompressorNodule ogCompressor = null;
        if(nearestCompressorFlag) ogCompressor = (CompressorNodule) nearestself;
        LinkedList<PortalNodule> compressedPortals = new LinkedList<PortalNodule>();
        var idk = (nearestself != null) ?  nearestself : originalBumpedFile;
        if(designatedObelisk != null){
            System.out.println("doing special obelisk thingy");
            idk = designatedObelisk;
        } 
        double dx = idk.x-x;
        double dz = idk.z-z;
        double dist = Math.sqrt(dx*dx+dz*dz);
        if(lateLoadComplete)System.out.println("LATE LOAD COMPLETE");
        if(dist < PortalNodule.range || lateLoadComplete || designatedObelisk != null ){


            // originalBumpedFile = null;
            if(!lateLoadComplete){
                Main.deleteFolderContents(new File("VirtualFolder")); // gets rid of anonymous.png
                System.out.println("BUMPED INTO " + idk);

                addStatement("opening file(s)");
            }else{
                addStatement("late load complete!");
            }

            HashSet<GameObject> exhausted = new HashSet<GameObject>();
            HashSet<FileNodule> fileNodules = new HashSet<FileNodule>();
            HashSet<Pasty> pastyNodules = new HashSet<Pasty>();
            HashSet<TextNodule> textNodules = new HashSet<TextNodule>();
            HashSet<GameObject> gameObjectsAlreadyLoaded = new HashSet<GameObject>();
            LinkedList<Tuple<GameObject, LinkedList<String>>> ingroup = new LinkedList<Tuple<GameObject, LinkedList<String>>>();
            if(lateLoadComplete){
                System.out.println("I DON'T THINK I EVER EVER GET HERE");
                idk = originalBumpedFile;
                missingGameObjects = null; // break it
            }else{
            }
            ingroup.push(new Tuple<GameObject, LinkedList<String>>(idk,new LinkedList<>()));
            neededFileNodules.clear();
            Main.deleteFolderContents(new File("VirtualFolder"));

            {
                {
                    for(var watchService: services.keySet()){
                        watchService.close();
                    }
                }
                services.clear();
                try {
                    Path toWatch = Paths.get("./VirtualFolder");
                    WatchService watchService = null;
                    watchService = FileSystems.getDefault().newWatchService();
                    var tempy = new LinkedList<String>();
                    tempy.add("VirtualFolder");
                    services.put(watchService, new Tuple<LinkedList<String>, Path>(tempy, toWatch));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            System.out.println("part 0");
            neededGameObjects.clear();
            while(!ingroup.isEmpty()){
                Tuple<GameObject, LinkedList<String>> both = ingroup.pop();
                var cur = both.x;
                var cur_context = both.y;
                exhausted.add(cur);
                System.out.println("ingroup prcessing for " + cur.getClass());
                if(cur instanceof FolderNodule){
                    var fn = (FolderNodule)cur;
                    // ALL DEPENDENTS SHOULD BE CURSED! CURSED I SAY!
                    // A DEPENDENT IS ANYTHING FOUND LATER...

                    //THE FOLDER HAS TWO MODES
                    //IF THE FOLDER IS EMPTY
                    //IT IS A BUCKET
                    //IF THE FOLDER HAS STUFF
                    //IT JUST ACTS LIKE A CHAIN NODE
                    //(AND ADDS NOTHING TO THE CONTEXT)

                    if(fn.state != null) gameObjectsAlreadyLoaded.add(fn);
                    cur_context = (LinkedList<String>)cur_context.clone();
                    cur_context.addLast(fn.getTitle());
                    var obnoxiousKey = buildFolderAsKey(cur_context, "");
                    System.out.println("FOLDERNODULE: ---- REGISTERING " + obnoxiousKey  );

                    try {
                        var tempy = (LinkedList<String>)cur_context.clone();
                        tempy.addFirst("VirtualFolder");
                        Path toWatch = Paths.get(buildFolderAsKey(tempy, ""));
                        WatchService watchService = null;
                        watchService = FileSystems.getDefault().newWatchService();
                        // System.out.println("towatch " +toWatch);
                        if (!Files.exists(toWatch)) {
                            System.out.println("creating...");
                            Files.createDirectories(toWatch);
                        }
                        services.put(watchService, new Tuple<LinkedList<String>, Path>(tempy, toWatch));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    {
                        for(var e: fn.bakedObjects){
                            e.x = 0;
                            exhausted.add(e); // don't want them picked any other way
                            gameObjectsAlreadyLoaded.add(cur); 
                            ingroup.addFirst(new Tuple<GameObject, LinkedList<String>>(e, (LinkedList<String>)cur_context.clone()));
                        }

                        // cup folder
                        if(!fn.bakedObjects.isEmpty()){
                            // POP THE CONTEXT
                            cur_context.removeLast();
                        }
                    }

                } else
                if(cur instanceof FileNodule){
                    var fn = (FileNodule)cur;
                    if(fn.bytes != null) gameObjectsAlreadyLoaded.add(fn);
                    System.out.println("currently fn.getTitle returns..  " + fn.getTitle());
                    var obnoxiousKey = buildFolderAsKey(cur_context, fn.getTitle());
                    fn.transientKey = obnoxiousKey; // for future rebuilding
                    fn.transientPath = cur_context;
                    if(neededFileNodules.get(obnoxiousKey) == null){
                        fileNodules.add(fn);
                        System.out.println("Is the state for this null  ..."  + fn.state);
                        System.out.println("---- REGISTERING " + obnoxiousKey+ " " + "\t" + fn );
                        neededFileNodules.put(obnoxiousKey, fn);
                    }else{
                        System.out.println("---- AVOIDING " + obnoxiousKey + " " + (fn.x-1000000)+ "\t" + fn);
                    }
                }
                else if(cur instanceof Pasty){
                    System.out.println("GOT ONE GOT ONE AAAAA" + cur);
                    var pasty = (Pasty)cur;
                    boolean pastyLoaded = pasty.sprite != null;
                    if(pastyLoaded){
                        pastyNodules.add(pasty);
                        gameObjectsAlreadyLoaded.add(cur);
                    }else{
                        // I'll come back for you... (somehow isn't working)
                    }
                }
                else if(cur instanceof TextNodule){
                    var pasty = (TextNodule)cur;
                    boolean pastyLoaded = pasty.state != null;
                    if(pastyLoaded){
                        textNodules.add(pasty);
                        gameObjectsAlreadyLoaded.add(cur); 
                    }else{
                        // I'll come back for you... (somehow isn't working)
                    }
                    
                }
                else if(cur instanceof TenPortals){
                    var tpp = (TenPortals)cur;
                    if(tpp.portalNodules != null && originalBumpedFile instanceof ObeliskNodule && !portals_flag_flipped){
                        System.out.println("Obelisk mode: setting TenPortals");
                        var link = (TenPortals) cur;
                        // tp.distill(); // to be resumed later
                        // globalTenPortals = tp;
                        activeTenPortals = (TenPortals)link.clone();

                        // link.loadme();
                        portals_flag_flipped = true;
                        gameObjectsAlreadyLoaded.add(cur); 
                    }
                }
                else{
                    gameObjectsAlreadyLoaded.add(cur); 
                }


                pasties.query2D(cur.x-100, cur.x+100, cur.z-100, cur.z+100);
                double least = 100000;
                GameObject found = null;
                do{
                    // System.out.println("AAAAAAAAAAAAAAAA");
                    found = null;
                    least = 10000;
                    for(int i = 0; i < pasties.size(); i++){
                        GameObject pasty = pasties.getAt(i);
                        boolean normalSkipCheck = !(pasty instanceof FileNodule) &&
                            !(pasty instanceof TextNodule) &&
                            !(pasty instanceof PortalNodule) &&
                            !(pasty instanceof ObeliskNodule) &&
                            !(pasty instanceof TenPortals) && 
                            !(pasty instanceof FolderNodule) && 
                            !(pasty instanceof Pasty && !(pasty instanceof ThreeDumb));
                        boolean skipCheck = normalSkipCheck;
                        if(nearestCompressorFlag){
                            skipCheck = !(pasty instanceof PortalNodule);
                        }
                        if(skipCheck) continue;
                        if(exhausted.contains(pasty)) continue;
                        var link = pasty;
                        // link is the equivalent of next
                        double dx2 = link.x-cur.x;
                        double dz2 = link.z-cur.z;
                        double dist2 = Math.sqrt(dx2*dx2+dz2*dz2);
                        if(dist2 < 21){
                            if(dist2 < least){
                                found = link;
                                least = dist2;
                            }
                        }
                    }
                    System.out.println(exhausted.contains(found));
                    if(found != null){
                        var link = found;
                        exhausted.add(link);
                        System.out.println("and then..." + exhausted.contains(found));

                        if(!(link instanceof PortalNodule))
                            ingroup.push(new Tuple<GameObject, LinkedList<String>>(link, cur_context));
                        else{
                            PortalNodule base = ((PortalNodule)link);
                            ingroup.push(new Tuple<GameObject, LinkedList<String>>(base, cur_context)); // must be a portal
                            if(nearestCompressorFlag && base.style != 2){
                                compressedPortals.addLast(base.clone());
                            }else{ // normal behaviour
                                PortalNodule fakeOne = base.clone();
                                fakeOne.x = base.destx;
                                fakeOne.z = base.destz;
                                exhausted.add(fakeOne);
                                ingroup.addLast(new Tuple<GameObject, LinkedList<String>>(fakeOne, cur_context)); // must be a portal
                            }
                        }
                    }
                }while(found != null);
            }
            
            // if virtual folder does not exist, create
            System.out.println("part 1 complete; beginning part 2");
            var f = (new File("VirtualFolder"));
            if(!f.exists()){
                f.mkdirs();
            }
            try{
                var connecteds = exhausted;
                //HashMap<String, FileNodule> 



                neededGameObjects = exhausted; // has to include duplicates lol
                missingGameObjects = new HashSet<GameObject>(neededGameObjects);
                missingGameObjects.removeAll(gameObjectsAlreadyLoaded);
                if(missingGameObjects.isEmpty()){
                    System.out.println("Setting missing objs to null");
                    missingGameObjects = null; // no need for late load
                } 
                originalBumpedFile = idk;
                for(var item: new LinkedList<>(neededGameObjects)){
                    // System.out.println("Presenting...(neededGameObjects) " +item  +" \t"+ item.getTitle());
                }
                for(var item: new LinkedList<>(neededFileNodules.values())){
                    // System.out.println("Presenting...(neededFileNodules) " +item  +" \t"+ item.getTitle());
                }
                // neededFileNodules = new LinkedList<>(fileNodules).stream()
                //             .collect(Collectors.toMap(FileNodule::getTitle, 
                //             fn -> fn
                //             ,(existing, replacement) -> existing
                //             ));

                // FUCKING DUPLICATES, I NEED THE DUPLICATES TO BE MAINTAINED
                // AS DISTINCT BECAUSE THE ENTRYSET IS USED TO LOAD THEM,
                // CHICKEN AND THE EGG PROBLEM, FUCK THE DUPLICATES OH SHIT.
                // ACTUALLY THIS IS A PROBLEM ROFLLLLLL

                {
                    // this will drop the need after player leaves area
                    fileneeded_x_catch = x;
                    fileneeded_z_catch = z;
                }
                boolean isObelisk = originalBumpedFile instanceof ObeliskNodule;
                System.out.println("isobelisk? " + isObelisk);
                System.out.println("connecteds.size() " + connecteds.size());
                for(FileNodule fn: fileNodules){
                    if(fn.state != null){
                        // System.out.println("Doing the meat");
                        // System.out.println(fn.state.stringify());
                        // System.out.println(fn.bytes.length);
                        //I believe this should work
                        // var val = buildFolderAsKey(cur_context, fn.getTitle());
                        var val = fn.transientKey;
                        // var val = fn.state.stringify().split("\\R", 2)[0];
                        if((val.equals("hands.png")))
                            System.out.println("Ok it is hands, let's see how it is handled");
                        if(originalBumpedFile instanceof ObeliskNodule){
                            if((val.equals("hands.png") && !hands_flag_flipped)){
                                System.out.println("hands.png found!!! OMG");
                                System.out.println("fn data size is." + fn.bytes.length);

                                try{
                                    ByteArrayInputStream inputStream = new ByteArrayInputStream(fn.bytes);

                                    // Read the image from the InputStream
                                    BufferedImage image = ImageIO.read(inputStream);

                                    Pasty p = new Pasty();
                                    var bi = image;
                                    p.sprite = new Spriter(bi, p.sl(), p.mso());
                                    p.width = bi.getWidth();
                                    p.height = bi.getHeight();
                                    p.data = p.sprite.tempIntDataForStorage;
                                    // p.sprite.tempIntDataForStorage = null;
                                    p.whrat = bi.getWidth() * 1.0 / bi.getHeight();
                                    handSpriterOverride = p;
                                    hands_flag_flipped = true;
                                }catch(Exception e){
                                    e.printStackTrace();
                                    System.out.println("unable to read hands.png");
                                }
                            }
                        } else{
                            if(!val.equals("")){
                                String out = "./VirtualFolder/"+val;
                                System.out.println("about to write... " + out);
                                // System.out.println("out "  + out);
                                // LinkedList<String> buildup = new LinkedList<String>
                                // for(var e: fn.transientPath){

                                // }
                                // var f = (new File("VirtualFolder"));
                                // if(!f.exists()){
                                //     f.mkdirs();
                                // }
                                var p ="./VirtualFolder/"+ buildFolderAsKey(fn.transientPath, "");
                                // System.out.println("path is... " + p);
                                Path path = Paths.get(p);
                                            
                                // Create directories if they do not already exist
                                // if (!Files.exists(path)) {
                                //     Files.createDirectories(path);
                                // }
                                FileOutputStream fileOutputStream = new FileOutputStream(out);
                                fileOutputStream.write(fn.bytes);
                                fileOutputStream.close();
                                // System.out.println("DONE WRITING!... " + out);
                            }else{
                                System.out.println("fn " + fn + " is seen as empty string\"\"");
                            }
                        }
                    }else{
                        System.out.println("FileNodule state is null");
                    }
                }
                if(!isObelisk && !nearestCompressorFlag){
                    // for(Pasty go: pastyNodules){
                    //     // write out 
                    //     go.openIntoVirtualFolderSpecial();

                    // }
                    
                    // for(TextNodule go: textNodules){
                    //     // write out 
                    //     go.openIntoVirtualFolderSpecial();

                    // }
                    {
                        try{
                            java.awt.Desktop.getDesktop().browse(f.toURI());
                        }catch(Exception e){
                            System.out.println("ERROR OPENING FILE");
                            e.printStackTrace();
                        }
                    }
                    if(designatedObelisk == null){
                        x+= Math.sin(idk.rot+Math.PI/2)*PortalNodule.range*4;
                        z+= Math.cos(idk.rot+Math.PI/2)*PortalNodule.range*4;
                    }
                }else{
                    if(designatedObelisk == null && !obelisk_dont_bounce){
                        x+= Math.sin(idk.rot+Math.PI/2)*PortalNodule.range*4;
                        z+= Math.cos(idk.rot+Math.PI/2)*PortalNodule.range*4;
                        System.out.println("LOOKS LIKE IT'S A @@@@@@@@@ OBELISK");
                    }
                    if(!hands_flag_flipped){
                        System.out.println("NO HANDS FOUND!");
                        handSpriterOverride = globalHandSpriterOverride;
                        
                    }
                    if(!portals_flag_flipped){
                        System.out.println("NO TEN PORTALS FOUND!");
                        
                        System.out.println("In theory loading the global tenportals" + globalTenPortals);
                        activeTenPortals = globalTenPortals;
                        // if(globalTenPortals == null)globalTenPortals = new TenPortals();


                        // globalTenPortals.loadme();
                    }
                }
                if(designatedObelisk != null){
                    obelisk_dont_bounce = true;
                }else{
                    obelisk_dont_bounce = false;
                }
                designatedObelisk = null;

                // System.out.println("FILE NODULE THINGY COMPLETED");

                if(nearestCompressorFlag){
                    var rn = new ControllerNodule();
                    rn.c1 = ogCompressor.c1;
                    rn.c2 = ogCompressor.c2;
                    rn.c3 = ogCompressor.c3;
                    rn.c4 = ogCompressor.c4;
                    rn.portalNodules = compressedPortals;
                    intoInventory(rn);
                }
            }catch(Exception e){
                // System.out.println("FileNodule error openinto");
                e.printStackTrace();
            }
            {
                
                for(var watchService: services.keySet()){
                    var bundle = services.get(watchService);
                    LinkedList<String> folders = bundle.x;
                    Path toWatch = bundle.y;
                    toWatch.register(watchService,
                        StandardWatchEventKinds.ENTRY_MODIFY,
                        StandardWatchEventKinds.OVERFLOW,
                        StandardWatchEventKinds.ENTRY_DELETE,
                        StandardWatchEventKinds.ENTRY_CREATE);
                }
            }
        }
    }
    else 
    if(nearestself instanceof LinkNodule ){
        var link = (LinkNodule) nearestself;
        double dx = link.x-x;
        double dz = link.z-z;
        double dist = Math.sqrt(dx*dx+dz*dz);
        if(dist < PortalNodule.range){
            System.out.println("linknodule bros");
            link.launch();
            // repell character
            x+= Math.sin(link.rot+Math.PI/2)*PortalNodule.range*4;
            z+= Math.cos(link.rot+Math.PI/2)*PortalNodule.range*4;
            p("EEEEEEEEEEEEEEEEEEEEEEEEEE");
        }
    }
    // else
    // if(nearest instanceof TextNodule){
    //     var link = (TextNodule) nearest;
    //     double dx = link.x-x;
    //     double dz = link.z-z;
    //     double dist = Math.sqrt(dx*dx+dz*dz);
    //     if(dist < PortalNodule.range){
    //         System.out.println("textnodule bros");
    //         link.openIntoVirtualFolder();
    //         // repell character
    //         x+= Math.sin(link.rot+Math.PI/2)*PortalNodule.range*4;
    //         z+= Math.cos(link.rot+Math.PI/2)*PortalNodule.range*4;
    //         p("FileNod repelling character");
    //     }
    // }
    // if(nearestself instanceof ChestNodule ){
    //     var chest = (ChestNodule) nearestself;
    //     double dx = chest.x-x;
    //     double dz = chest.z-z;
    //     double dist = Math.sqrt(dx*dx+dz*dz);
    //     if(dist < PortalNodule.range){
    //         chest.activate();
    //         x+= Math.sin(chest.rot+Math.PI/2)*PortalNodule.range*4;
    //         z+= Math.cos(chest.rot+Math.PI/2)*PortalNodule.range*4;
    //     }
    // }


    {
        Spriter.zfaruniformvalue = 200_000_000f;
        Spriter.znearuniformvalue = 10f;

        for(var go: permamountains){
            if(go instanceof ThreeDumb){
                ThreeDumb p = (ThreeDumb)go;
                boolean isPlaying = p instanceof BurningBush && ((BurningBush)p).playing;
                if(p.values != null && p.sprite != null && !p.sprite.hasRepaunch()){
                    for(int i = 0; i < p.pointsCt; i++){
                        double px = p.x-1_000_000;
                        double pz = p.z-1_000_000;
                        double py = p.ydisp+5/1;
                        
                        py+=p.values[1+i*3];
                        {
                        // NEED TO ROTATE AROUND ORIGIN ROTAORI

                            double px1 =p.values[0+i*3];
                            double pz1 =p.values[2+i*3];
                            double pointRot = Math.atan2(pz1, px1);
                            double str = Math.sqrt(pz1*pz1+px1*px1); 
                            px += str*Math.sin(pointRot + p.rot);
                            pz += str*Math.cos(pointRot + p.rot);
                        }
                        ColorGroup replacementC = ColorGroup.black;
                        // py+=Math.sin(elapsed)-8;
                        py-=8;
                        // p.sprite.drawTriPoint(0, px, py+Math.sin(elapsed)-8, pz, 1, b, replacementC, mode);
                        
                        // p(p.sos[i] + " " + p.sos.length + " " + i);
                        int spriteoffset = 0;
                        if(p.sos != null && p.sos.length>0) spriteoffset = p.sos[i];
                        int mode = 4;
                        double transparency = 1;
                        if(isPlaying) transparency = Math.sin(elapsed*20);

                        if(p.sprite != null){
                            p.sprite.drawTriPoint(spriteoffset, px, py, pz,  transparency, b, b, mode);
                        }else{
                            fightSpriter.drawTriPoint(0, px, py, pz,  1, b, b, mode);
                        }
                    }
                    double nx = x-1_000_000;
                    double nz = z-1_000_000;

                    double ny = (newwigglemode ? wiggleItem.ydisp : y) + dispy;
                    double nangle = neck;
                    if(followmode){
                        // nangle += followangle;
                        double horizdist = Math.cos(chin)*followdist; 
                        nx+= horizdist*Math.sin(nangle+Math.PI);
                        nz+= horizdist*Math.cos(nangle+Math.PI);
                        ny+=Math.sin(chin)*followdist;
                    }
                    
                    p.sprite.step(gridx, gridy, nx,ny,nz, nangle, chin);
                    // System.exit(0);
                }
            }
        }
        glClear(GL_DEPTH_BUFFER_BIT);
        Spriter.resetzplanes();

    }
    {
        boolean holdingFunnel = (bobbies.size() >= 1) && bobbies.get(0) instanceof FunnelNodule; 
        if(holdingFunnel){
            var fn = (FunnelNodule)bobbies.get(0);
            for(GameObject p: fn.phantoms){
                for(int i = 0; i < 4; i++){ //BEACON HERE
                    double px = p.x-1_000_000;
                    double pz = p.z-1_000_000;
                    double py = p.ydisp+5/1;
                    if(i == 1 || i == 2){
                        px -= 1*Math.sin(p.rot);
                        pz -= 1*Math.cos(p.rot);
                    }else{
                        px += 1*Math.sin(p.rot);
                        pz += 1*Math.cos(p.rot);
                    }
                    px += 0.5*Math.sin(p.rot-Math.PI/2);
                    pz += 0.5*Math.cos(p.rot-Math.PI/2);

                    if(i == 2 || i==3){
                        py+=6;
                    }else{
                        py+=-6;
                    }
                    RGB r1 = RGB.fromCombined(0xff_ff_ff_ff);
                    RGB r2 = RGB.fromCombined(0xff_00_00_00);
                    ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r2.r,r2.g,r2.b);
                    int mode = 4;
                    if(nearest == p) mode = 8;
                    fightSpriter.drawPoint(10, px, py, pz,  Math.sin(elapsed)/2+0.5, b, replacementC, mode);
                }
            }
        }
    }
    // for(GameObject go: skysigns){
    //     // go.step = false;
    //     // if(Math.abs(go.x-x)+Math.abs(go.z-z) > 500 && !(go instanceof IdeaBeacon)) continue; //manhattan distance
    //     // go.step = true;
    //     int mode = (nearest != go || tabselection >= 0)  ? 4 : 8;
    //     if(go instanceof Pasty){
            

    //         Pasty p = (Pasty)go;
    //         double a = Math.atan2(p.height, p.width);
    //         double ang = a-p.rot+Math.PI/2;
    //         for(int i = 0; i < 4; i++){
    //             double px = p.x-1_000_000;
    //             double pz = p.z-1_000_000;
    //             // double py = p.ydisp+5/p.whrat;
    //             double py = p.ydisp+500;
    //             if(i == 0 || i == 2){
    //                 ang-=a*2;
    //             }else{
    //                 ang-=Math.PI-a*2;
    //             }
    //             px += 250*Math.cos(ang);
    //             pz += 250*Math.sin(ang);
    //             if(p.sprite != null){
    //                 p.sprite.drawPoint(0, px, py, pz,  1, b, b, mode);
    //             }else{ 
    //                 fightSpriter.drawPoint(0, px, py, pz,  1, b, b, mode);
    //             }
    //         }
    //         for(int i = 0; i < 4; i++){ //BEACON HERE
    //             double px = p.x-1_000_000;
    //             double pz = p.z-1_000_000;
    //             double py = p.ydisp+5/1;
    //             if(i == 1 || i == 2){
    //                 px -= 1*Math.sin(p.rot);
    //                 pz -= 1*Math.cos(p.rot);
    //             }else{
    //                 px += 1*Math.sin(p.rot);
    //                 pz += 1*Math.cos(p.rot);
    //             }
    //             px += 0.5*Math.sin(p.rot-Math.PI/2);
    //             pz += 0.5*Math.cos(p.rot-Math.PI/2);

    //             if(i == 2 || i==3){
    //                 py+=600;
    //             }else{
    //                 py+=-6;
    //             }
    //             RGB r1 = RGB.fromCombined(0xff_ff_ff_ff);
    //             RGB r2 = RGB.fromCombined(0xff_00_00_00);
    //             ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r2.r,r2.g,r2.b);
    //             fightSpriter.drawPoint(10, px, py, pz,  Math.sin(elapsed)/2+0.5, b, replacementC, mode);
    //         }
    //     }
    // }
    if(!textSheet.hasRepaunch() ||fortounload != null){
        rememberToStep.clear();
    }
    // subtiming("new-age");
    nano_timings[timingsIndex][6] = System.currentTimeMillis();
    
    nano_timings[timingsIndex][10] = System.currentTimeMillis();
    pasties.query2D(x-1000, x+1000, z-1000, z+1000);

    // for(GameObject go: pasties){
    nano_timings[timingsIndex][11] = 0;
    nano_timings[timingsIndex][12] = 0;
    
    long before_loop = System.currentTimeMillis();
    int budget = 10;
    for(int gg = 0; gg < pasties.size(); gg++){
        GameObject go = pasties.getAt(gg);
        if(System.currentTimeMillis() - before_loop > budget){
            // break; // not doing this anymore
            if(!(go instanceof TextNodule))
                continue;
        }
        go.step = false;
        if(Math.abs(go.x-x)+Math.abs(go.z-z) > 1000 && !(go instanceof IdeaBeacon) && !(go instanceof KeyNodule)) continue; //manhattan distance
        go.step = true;
        int mode = (nearest != go || tabselection >= 0)  ? 4 : 8;
        if((go instanceof Pasty || go instanceof IHasAPasty) && !(go instanceof ThreeDumb)  && 
            ( !hidePQItoggle|| !(go instanceof ParkerQuizItem)) && !FORCE_HIDE_DURING_ROTATION_EXPIRMENT && !BACKTRACK_ENGAGED){
            
            // idea only show presently active
            boolean showIt = !(TimeLockingKey.globalTypingSwitch == quiztypingswitch);
            //  || (quizCheck == go);
            boolean isIt = quizCheck == go;
            // showIt = true;
            isIt = false; 
            Pasty hasAPasty = null;
            if(go instanceof IHasAPasty) hasAPasty = ((IHasAPasty)go).getPasty();
            if(!(go instanceof IHasAPasty) || hasAPasty != null){
                Pasty p = go instanceof IHasAPasty ? hasAPasty : (Pasty)go;
                if(go instanceof IHasAPasty){
                    if(!rememberToStep.contains(hasAPasty.sprite) && hasAPasty.sprite != null)rememberToStep.push(hasAPasty.sprite);
                }
                
                for(int i = 0; i < 4; i++){
                    double px = p.x-1_000_000;
                    double pz = p.z-1_000_000;
                    double pastySize = hasAPasty == null ? 5*p.size : 5*go.size;
                    double py = p.ydisp+pastySize/p.whrat;
                    if(i == 1 || i == 2){
                        px -= pastySize*Math.sin(p.rot);
                        pz -= pastySize*Math.cos(p.rot);
                    }else{
                        px += pastySize*Math.sin(p.rot);
                        pz += pastySize*Math.cos(p.rot);
                    }
                    if(i == 2 || i==3){
                        py+=-pastySize*2/p.whrat;
                    }
                    if(go instanceof IHasAPasty){
                        if(p.whrat < 0.75){
                            pastySize = 3.0*p.whrat;
                        }else{
                            pastySize = 4.0;
                        }
                         px = go.x-1_000_000;
                         pz = go.z-1_000_000;                
                         py = go.ydisp+pastySize/p.whrat - 1;
                        if(i == 1 || i == 2){
                            px -= pastySize*Math.sin(go.rot);
                            pz -= pastySize*Math.cos(go.rot);
                        }else{
                            px += pastySize*Math.sin(go.rot);
                            pz += pastySize*Math.cos(go.rot);
                        }
                        if(i == 2 || i==3){
                            py+=-pastySize*2/p.whrat;
                        }
                    }
                    
                    if(mode != 8 && neededGameObjects.contains(p)){
                        mode = 99;
                    }
                    if(Main.F12_OVERRIDE) mode = 4;
                    if(p.hidden){
                        fightSpriter.drawPoint(30, px, py, pz,  1, b, b, mode);
                    }else{
                        if(p.sprite != null){
                            // int sprite_offset_poc = (int)((System.currentTimeMillis()%1000)/15.625); // should be 0 -63 not 0 to 1000
                            int sprite_offset_poc = 0; // should be 0 -63 not 0 to 1000
                            if(p instanceof ProtoVideo){
                                var pp = ((ProtoVideo)p);
                                var sl = pp.sidelength;
                                double perc = pp.calcPerc();
                                sprite_offset_poc = (int)(sl*sl*perc);
                            }
                            p.sprite.drawPoint(sprite_offset_poc, px, py, pz,  1, b, b, go instanceof FileNodule ? 4: mode);
                        }else{
                            // comment this in event of memory error
                            fightSpriter.drawPoint(0, px, py, pz,  1, b, b, mode);
                        }
                    }
                }
            }
        }
        if(go instanceof IdeaBeacon){
            IdeaBeacon p = (IdeaBeacon)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double py = p.ydisp+5/1;
                if(i == 1 || i == 2){
                    px -= 2*Math.sin(p.rot);
                    pz -= 2*Math.cos(p.rot);
                }else{
                    px += 2*Math.sin(p.rot);
                    pz += 2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=600;
                }else{
                    py+=-6;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r2.r,r2.g,r2.b);
                
                fightSpriter.drawPoint(p.spriteid, px, py, pz,  Math.sin(elapsed)/2+0.5, b, replacementC, mode);
            }
        }
        long before3 = System.nanoTime();
        try{
            if(go instanceof ThreeDumb){
                ThreeDumb p = (ThreeDumb)go;
                boolean isPlaying = p instanceof BurningBush && ((BurningBush)p).playing;
                if(p.values != null && p.sprite != null && !p.sprite.hasRepaunch()){
                    for(int i = 0; i < p.pointsCt; i++){
                        double px = p.x-1_000_000;
                        double pz = p.z-1_000_000;
                        double py = p.ydisp+5/1;
                        
                        py+=p.values[1+i*3];
                        {
                        // NEED TO ROTATE AROUND ORIGIN ROTAORI

                            double px1 =p.values[0+i*3];
                            double pz1 =p.values[2+i*3];
                            double pointRot = Math.atan2(pz1, px1);
                            double str = Math.sqrt(pz1*pz1+px1*px1); 
                            px += str*Math.sin(pointRot + p.rot);
                            pz += str*Math.cos(pointRot + p.rot);
                        }
                        ColorGroup replacementC = ColorGroup.black;
                        // py+=Math.sin(elapsed)-8;
                        py-=8;
                        // p.sprite.drawTriPoint(0, px, py+Math.sin(elapsed)-8, pz, 1, b, replacementC, mode);
                        
                        // p(p.sos[i] + " " + p.sos.length + " " + i);
                        int spriteoffset = 0;
                        if(p.sos != null && p.sos.length>0) spriteoffset = p.sos[i];
                        mode = 4;
                        double transparency = 1;
                        if(isPlaying) transparency = Math.sin(elapsed*20);

                        if(p.sprite != null){
                            p.sprite.drawTriPoint(spriteoffset, px, py, pz,  transparency, b, b, mode);
                        }else{
                            fightSpriter.drawTriPoint(0, px, py, pz,  1, b, b, mode);
                        }
                    }
                }
                if((Input.keys[GLFW_KEY_F12] || nearest == p) && !grid_lock_mode){
                    //beacon thingy
                    for(int i = 0; i < 4; i++){ //BEACON HERE
                        double px = p.x-1_000_000;
                        double pz = p.z-1_000_000;
                        double py = p.ydisp+5/1;
                        if(i == 1 || i == 2){
                            px -= 1*Math.sin(p.rot);
                            pz -= 1*Math.cos(p.rot);
                        }else{
                            px += 1*Math.sin(p.rot);
                            pz += 1*Math.cos(p.rot);
                        }
                        px += 0.5*Math.sin(p.rot-Math.PI/2);
                        pz += 0.5*Math.cos(p.rot-Math.PI/2);

                        if(i == 2 || i==3){
                            py+=600;
                        }else{
                            py+=-6;
                        }
                        RGB r1 = RGB.fromCombined(0xff_ff_00_00);
                        RGB r2 = RGB.fromCombined(0xff_00_ff_00);
                        ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r2.r,r2.g,r2.b);
                        fightSpriter.drawPoint(10, px, py, pz,  Math.sin(elapsed)/2+0.5, b, replacementC, mode);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        nano_timings[timingsIndex][12] += System.nanoTime() - before3;

        if(go instanceof ShinyBauble ){
            var p = go;
            // ShinyBauble p = (ShinyBauble)go;
            for(int j = 0; j < 2; j++){
                for(int i = 0; i < 4; i++){
                    double px = p.x-1_000_000;
                    double pz = p.z-1_000_000;
                    double py = p.ydisp+5/1;
                    double dimx =5*Math.sin(p.rot); 
                    double dimz =5*Math.cos(p.rot); 
                    if(i == 1 || i == 2){
                        px -= dimx;
                        pz -= dimz;
                    }else{
                        px += dimx;
                        pz += dimz;
                    }
                    px += dimx*j;
                    pz += dimz*j;
                    if(i == 2 || i==3){
                        py+=-10/1;
                    }
                    py+= Math.sin(elapsed)*2;
                    textSheet.drawPointCharacter((char)('t'+j), px, py, pz, b);
                    // fightSpriter.drawPoint(60, px, py, pz,  1, b, b, mode);
                }
            }
        }
        ttsdraw(go, mode, b, fightSpriter, micColors);
        
        if(go instanceof Crux){
            // Conveyor p = (Conveyor) go;
            var p = go;
            var c = (Crux) go;
            for(int j = 0; j < 4; j++){
                if(j == 3 && c.downx == 0) continue;
                if(j == 0 && c.upz == 0) continue;
                if(j == 1 && c.upx == 0) continue;
                if(j == 2 && c.downz == 0) continue;
                
                // Define the center point (adjusted as before)
                double centerX = p.x - 1_000_000 + 10*Math.sin(j*Math.PI/2);
                double centerZ = p.z - 1_000_000 + 10*Math.cos(j*Math.PI/2);
                double py = p.ydisp+3;
                double radius = Conveyor.rad/4; // distance from center
            
                // Loop 4 times, one for each point.
                for (int i = 0; i < 4; i++){
                    // Choose the direction:
                    // For counterclockwise, use a plus:
                    double angle = -Math.PI/2 + j*Math.PI/2 + i * (Math.PI / 2) + (Math.PI / 4);
                    // For clockwise, use a minus:
                    // double angle = p.rot - i * (Math.PI / 2);
                    
                    // Compute positions based on the angle.
                    double px = centerX + radius * Math.sin(angle);
                    double pz = centerZ + radius * Math.cos(angle);
                    
                    int so = 31;
                    fightSpriter.drawPoint(so, px, py, pz, 1, b, b, 1);
                }
            }
        }
        if(go instanceof TenPortals){
            TenPortals p = (TenPortals)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double py = p.ydisp+5/1;
                if(i == 1 || i == 2){
                    px -= 2*Math.sin(p.rot);
                    pz -= 2*Math.cos(p.rot);
                }else{
                    px += 2*Math.sin(p.rot);
                    pz += 2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-6;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                int so = 26;
                    
                if(mode != 8 && neededGameObjects.contains(p)){
                    mode = 99;
                }
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                fightSpriter.drawPoint(so, px, py, pz,  1, micColors, replacementC, mode);
            }
        }
        if(go instanceof ObeliskNodule){
            ObeliskNodule p = (ObeliskNodule)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                int bigger = 5;
                double py = p.ydisp+5/1*bigger;
                if(i == 1 || i== 2){
                    px -= bigger*2*Math.sin(p.rot);
                    pz -= bigger*2*Math.cos(p.rot);
                }else{
                    px += bigger*2*Math.sin(p.rot);
                    pz += bigger*2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-6*bigger;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                int so = 40;
                    
                if(mode != 8 && neededGameObjects.contains(p)){
                    mode = 99;
                }
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                fightSpriter.drawPoint(so, px, py, pz,  1, micColors, replacementC, mode);
            }
        }
        if(go instanceof CompressorNodule){
            CompressorNodule p = (CompressorNodule)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                int bigger = 2;
                double py = p.ydisp+5/1*bigger;
                if(i == 1 || i== 2){
                    px -= bigger*2*Math.sin(p.rot);
                    pz -= bigger*2*Math.cos(p.rot);
                }else{
                    px += bigger*2*Math.sin(p.rot);
                    pz += bigger*2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-6*bigger;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                int so = 41 + (int)((System.currentTimeMillis()%1000)/334);
                    
                if(mode != 8 && neededGameObjects.contains(p)){
                    mode = 99;
                }
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                fightSpriter.drawPoint(so, px, py, pz,  1, micColors, replacementC, mode);
            }
        }
        if(go instanceof FollowPath){
            FollowPath p = (FollowPath)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                int bigger = 2;
                double py = p.ydisp+5/1*bigger;
                if(i == 1 || i== 2){
                    px -= bigger*2*Math.sin(p.rot);
                    pz -= bigger*2*Math.cos(p.rot);
                }else{
                    px += bigger*2*Math.sin(p.rot);
                    pz += bigger*2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-6*bigger;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                int so = 56 + (int)((System.currentTimeMillis()%1000)/334);
                    
                if(mode != 8 && neededGameObjects.contains(p)){
                    mode = 99;
                }
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                fightSpriter.drawPoint(so, px, py, pz,  1, micColors, replacementC, mode);
            }
        }
        if(go instanceof ControllerNodule){
            ControllerNodule p = (ControllerNodule)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double bigger = 0.66;
                double py = p.ydisp+5/1*bigger;
                if(i == 1 || i== 2){
                    px -= bigger*2*Math.sin(p.rot);
                    pz -= bigger*2*Math.cos(p.rot);
                }else{
                    px += bigger*2*Math.sin(p.rot);
                    pz += bigger*2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-6*bigger;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                int so = 44;
                    
                if(mode != 8 && neededGameObjects.contains(p)){
                    mode = 99;
                }
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                fightSpriter.drawPoint(so, px, py, pz,  1, micColors, replacementC, mode);
            }
        }
        if(go instanceof FunnelNodule){
            FunnelNodule p = (FunnelNodule)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double bigger = 1;
                double py = p.ydisp+5/1*bigger;
                if(i == 1 || i== 2){
                    px -= bigger*2*Math.sin(p.rot);
                    pz -= bigger*2*Math.cos(p.rot);
                }else{
                    px += bigger*2*Math.sin(p.rot);
                    pz += bigger*2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-6*bigger;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                int so = 46;
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                fightSpriter.drawPoint(so, px, py, pz,  1, micColors, replacementC, mode);
            }
        }
        if(go instanceof PumpJack){
            PumpJack p = (PumpJack)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double bigger = 1;
                double py = p.ydisp+5/1*bigger;
                if(i == 1 || i== 2){
                    px -= bigger*12*Math.sin(p.rot);
                    pz -= bigger*12*Math.cos(p.rot);
                }else{
                    px += bigger*12*Math.sin(p.rot);
                    pz += bigger*12*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-12*bigger;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                int so = 48;
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                fightSpriter.drawPoint(so, px, py, pz,  1, micColors, replacementC, mode);
            }
        }
        if(go instanceof PromiseNodule){
            PromiseNodule p = (PromiseNodule)go;
            for(int i = 0; i < 4; i++){ //BEACON HERE
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double py = p.ydisp+5/1;
                if(i == 1 || i == 2){
                    px -= 1*Math.sin(p.rot);
                    pz -= 1*Math.cos(p.rot);
                }else{
                    px += 1*Math.sin(p.rot);
                    pz += 1*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=6;
                }else{
                    py+=-6;
                }
                boolean alive = false;
                if(p.watchThread != null)
                    alive = p.watchThread.isAlive();
                RGB r1 = RGB.fromCombined(0xff_ff_ff_ff);
                RGB r2 = alive ? RGB.fromCombined(0xff_00_00_00) : RGB.fromCombined(0xff_ff_00_00);
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r2.r,r2.g,r2.b);
                // int mode = 4;
                if(nearest == p) mode = 8;
                fightSpriter.drawPoint(10, px, py, pz, alive? Math.sin(elapsed)/2+0.5:1, b, replacementC, mode);
            }
        }
        if(go instanceof ParkerQuizItem){
            ParkerQuizItem p = (ParkerQuizItem)go;
            double theta  = p.random_temporary_theta;
            double phi  = p.random_temporary_phi;
            // opposite of at the hand's height
            double[] r2 = ParkerQuizItem.rotatePoint(-3, 0, 0, theta, phi+Math.PI/2);

            for(int i = 0; i < 4; i++){
                double px = 0;
                double pz = 0;
                double bigger = 0.2;
                double py = 0;
                if(i == 1 || i== 2){
                    px -= bigger*2*Math.sin(0);
                    pz -= bigger*2*Math.cos(0);
                }else{
                    px += bigger*2*Math.sin(0);
                    pz += bigger*2*Math.cos(0);
                }
                // px += 0.5*Math.sin(0-Math.PI/2);
                // pz += 0.5*Math.cos(0-Math.PI/2);
                py+=4*bigger;
                if(i == 2 || i==3){
                    py+=-2*bigger;
                }
                double[] r = ParkerQuizItem.rotatePoint(px, py, pz, phi, theta);

                px = r[0] - 1_000_000+p.x;
                py = r[1] + p.ydisp+5/1*bigger;
                pz = r[2] - 1_000_000+p.z;
                px+=r2[0];
                py+=r2[1];
                pz+=r2[2];
                int so = 27;
                fightSpriter.drawPoint(so, px,py,pz,  1, ColorGroup.black, ColorGroup.black, mode);
            }
        }
        if(go instanceof ParkerQuizItem){
            ParkerQuizItem p = (ParkerQuizItem)go;
            double theta  = p.random_temporary_theta;
            double phi  = p.random_temporary_phi;
            double[] r2 = ParkerQuizItem.rotatePoint(0, 0, 0, phi, theta);

            for(int i = 0; i < 4; i++){
                double px = 0;
                double pz = 0;
                double bigger = 0.2;
                double py = 0;
                if(i == 1 || i== 2){
                    px -= bigger*2*Math.sin(0);
                    pz -= bigger*2*Math.cos(0);
                }else{
                    px += bigger*2*Math.sin(0);
                    pz += bigger*2*Math.cos(0);
                }
                // px += 0.5*Math.sin(0-Math.PI/2);
                // pz += 0.5*Math.cos(0-Math.PI/2);
                py+=4*bigger;
                if(i == 2 || i==3){
                    py+=-2*bigger;
                }
                double[] r = ParkerQuizItem.rotatePoint(px, py, pz, phi, theta);

                px = r[0] - 1_000_000+p.x;
                py = r[1] + p.ydisp+5/1*bigger;
                pz = r[2] - 1_000_000+p.z;
                px+=0;
                py+=0;
                pz+=0;
                int so = 45;
                fightSpriter.drawPoint(so, px,py,pz,  1, ColorGroup.black, ColorGroup.black, mode);
            }
        }
        if(go instanceof ChestNodule){
            ChestNodule p = (ChestNodule)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double py = p.ydisp+5/1;
                if(i == 1 || i == 2){
                    px -= 2*Math.sin(p.rot);
                    pz -= 2*Math.cos(p.rot);
                }else{
                    px += 2*Math.sin(p.rot);
                    pz += 2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);
                if(i == 2 || i==3){
                    py+=-6;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r2.r, r2.g,r2.b);
                fightSpriter.drawPoint(26, px, py, pz,  1, chestColors, replacementC, mode);
            }
        }
        if(go instanceof KeyNodule){
            KeyNodule p = (KeyNodule)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double py = p.ydisp+5/1;
                p.rot = elapsed;
                if(i == 1 || i == 2){
                    px -= 4*Math.sin(p.rot);
                    pz -= 4*Math.cos(p.rot);
                }else{
                    px += 4*Math.sin(p.rot);
                    pz += 4*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);
                if(i == 2 || i==3){
                    py+=-10;
                }
                fightSpriter.drawPoint(27, px, py, pz,  1, chestColors, chestColors, mode);
            }
        }
        if(go instanceof RecordNodule){
            RecordNodule p = (RecordNodule)go;
            for(int i = 0; i < 4; i++){
                double px = p.x-1_000_000;
                double pz = p.z-1_000_000;
                double py = p.ydisp+5/1;
                if(i == 1 || i == 2){
                    px -= 2*Math.sin(p.rot);
                    pz -= 2*Math.cos(p.rot);
                }else{
                    px += 2*Math.sin(p.rot);
                    pz += 2*Math.cos(p.rot);
                }
                px += 0.5*Math.sin(p.rot-Math.PI/2);
                pz += 0.5*Math.cos(p.rot-Math.PI/2);

                if(i == 2 || i==3){
                    py+=-6;
                }
                RGB r1 = RGB.fromCombined(p.c1);
                RGB r2 = RGB.fromCombined(p.c2);
                RGB r3 = RGB.fromCombined(p.c3);
                ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r2.r,r2.g,r2.b, r3.r,r3.g,r3.b);
                fightSpriter.drawPoint(19, px, py, pz,  1, micColors, replacementC, mode);
            }
            String playlength = df.format(p.recordTime);
            RGB r4 = RGB.fromCombined(p.c4);
            {
                for(int j = 0; j < playlength.length(); j++){
                    char c = ' ';
                    c = playlength.charAt(j);
                    var u = (10.0/40)*10;
                    for(int i = 0; i < 4; i++){
                        double px = p.x-1_000_000;
                        double pz = p.z-1_000_000;
                        double py = p.ydisp+5/1;
                        double dimx =u*Math.sin(p.rot)/2; 
                        double dimz =u*Math.cos(p.rot)/2;
                        if(i == 1 || i == 2){
                            px -= dimx;
                            pz -= dimz;
                        }else{
                            px += dimx;
                            pz += dimz;
                        }
                        px += dimx*(j-2);
                        pz += dimz*(j-2);
                        if(i == 2 || i==3){
                            py+=-u;
                        }
                        py+=-u-1.5;
                        textSheet.drawPointCharacter(c, px, py, pz, new ColorGroup(r4));
                    }
                }
            }
        }

        // time(">TEXT");
        long before2 = System.nanoTime();
        if(go instanceof TextNodule ){

            boolean hidden = go instanceof TextNodule2 && ((TextNodule2)go).mode != 0;
            {
                TextNodule p = (TextNodule)go;
                for(int i = 0; i < 4; i++){
                    double px = p.x-1_000_000;
                    double pz = p.z-1_000_000;
                    double py = p.ydisp+5/1*p.size;
                    if(i == 1 || i == 2){
                        px -= 7*Math.sin(p.rot)*p.size;
                        pz -= 7*Math.cos(p.rot)*p.size;
                    }else{
                        px += 7*Math.sin(p.rot)*p.size;
                        pz += 7*Math.cos(p.rot)*p.size;
                    }
                    px += 0.505*Math.sin(p.rot-Math.PI/2);
                    pz += 0.505*Math.cos(p.rot-Math.PI/2);

                    if(i == 2 || i==3){
                        py+=-10/1*p.size;
                    }
                    int sprite = go instanceof TextNodule2 ? (hidden? 20:18): 17;
                    sprite = go instanceof LudeonNodule ? 21 : sprite;
                    sprite = go instanceof LinkNodule ? 25 : sprite;
                    sprite = go instanceof FileNodule ? 37 : sprite;
                    sprite = go instanceof Searcher ? 29 : sprite;
                    sprite = go instanceof FolderNodule ? (((FolderNodule)go).bakedObjects.isEmpty()? 51:52) : sprite;
                    if(mode != 8 && neededGameObjects.contains(go)){
                        mode = 99;
                    }
                    ColorGroup replacementC = go instanceof TextNodule2 ? new ColorGroup(RGB.fromCombined(((TextNodule2)go).c1)) : b;
                    ColorGroup start = tnode2red;
                    if(go instanceof Searcher ){
                        var idk = (Searcher) go;
                        replacementC = new ColorGroup(RGB.fromCombined(idk.c1));
                        start = b;
                    }
                    fightSpriter.drawPoint(sprite, px, py, pz,  1, start, replacementC, mode);
                }
            }
            TextNodule p = (TextNodule)go;
            {
                try{
                    var s = p.state;
                    if(p.state != null && !textSheet.hasRepaunch() || p == TimeLockingKey.globalTypingSwitch){
                        // System.out.println("about to render...");
                        int lettercount = 0;
                        int ll = !hidden ? s.rows.size() : 1;
                        for(int k = 0; k < ll; k++){

                            if(k > 10) break; // reduce text size
                            
                            int l = s.rows.get(k).size();
                            for(int j = -1; j < l; j++){
                                char c = ' ';
                                Spriter alt = null;
                                Pasty pa = null;
                                double modif = 1;
                                if(j != -1 && s.rows.size() > 0 && s.rows.get(k).size() > 0){
                                    var a = s.rows.get(k).get(j);
                                    if(a.p != null){
                                        if(a.p.sprite == null){
                                            a.p.load();
                                            p("AAAYEEEEEEEEEEEEEEEE");
                                        }
                                        modif = 1.5;
                                        alt = a.p.sprite;
                                        pa = a.p;
                                        a.p.sprite.clearRepaunch();
                                        a.p.sprite.repaunch = textSheet.repaunch;
                                        rememberToStep.push(a.p.sprite);
                                    }else{
                                        c = a.c;
                                    }
                                }
                                var u = (10.0/40)*p.sizeMulti*p.size;
                                // Spriter.clickColors = temporary_color_expirement++;
                                int outnum = (k<<16) | (j);
                                // Spriter.clickColors = outnum;



                                boolean ishighlight = false;
                                {
                                    if(s.highlightedr1 < k || (s.highlightedr1 == k && s.highlightedc1 <= j)){
                                        if(s.highlightedr2 > k || (s.highlightedr2 == k && s.highlightedc2 > j)){
                                            ishighlight = true;
                                        }
                                    }
                                }
                                boolean ishovered = j == hover_cx && k == hover_cy;

                                for(int i = 0; i < 4; i++){
                                    double px = p.x-1_000_000;
                                    double pz = p.z-1_000_000;
                                    double py = p.ydisp+5*p.size;
                                    double dimx =u*Math.sin(p.rot)/2; 
                                    double dimz =u*Math.cos(p.rot)/2;
                                    double xmod = (pa!= null ?pa.whrat>16/9.0?16/9.0:pa.whrat : 1);
                                    double ymod = (pa!= null ?pa.whrat>16/9.0?1/pa.whrat : 1  : 1);
                                    // p(xmod, ymod, pa == null);
                                    px += dimx*(j-40/(p.sizeMulti));
                                    pz += dimz*(j-40/(p.sizeMulti));
                                    if(i == 1 || i == 2){
                                        px -= dimx/modif*xmod/2;
                                        pz -= dimz/modif*xmod/2;
                                    }else{
                                        px += dimx/modif*xmod/2;
                                        pz += dimz/modif*xmod/2;
                                    }
                                    var px2 = px;
                                    var pz2 = pz;
                                    if(i == 1 || i == 2){
                                        px -= dimx/modif*xmod/2;
                                        pz -= dimz/modif*xmod/2;
                                    }else{
                                        px += dimx/modif*xmod/2;
                                        pz += dimz/modif*xmod/2;
                                    }
                                    // double idek = Math.sqrt(Math.abs(k-20)); 
                                    if(i == 2 || i==3){
                                        // double idek = Math.pow(1.2, Math.abs(k+1-20)); 
                                        // px -= 0.5*Math.sin(p.rot-Math.PI/2)*idek;
                                        // pz -= 0.5*Math.cos(p.rot-Math.PI/2)*idek;
                                        py+=-u*ymod;
                                    }else{
                                        // double idek = Math.pow(1.2, Math.abs(k-20)); 
                                        // px -= 0.5*Math.sin(p.rot-Math.PI/2)*idek;
                                        // pz -= 0.5*Math.cos(p.rot-Math.PI/2)*idek;
                                    }
                                    py+=-u*k-1.5*p.size;
                                    // py+= Math.sin(elapsed)*2;
                                    var col = p instanceof LinkNodule ? silverGray : clueScrollBrown; 
                                    if(p instanceof Searcher){
                                        var idk = (Searcher)p;
                                        col = new ColorGroup(RGB.fromCombined(idk.c2));
                                    }
                                    if(p == TimeLockingKey.globalTypingSwitch){
                                        if(alt != null){
                                            alt.drawPoint(c, px, py, pz, 1, ColorGroup.black, ColorGroup.black, mode);
                                        }else{
                                            textSheet3.drawPointCharacter(c, px, py, pz, col);
                                            
                                            px2 += 0.05*Math.sin(p.rot-Math.PI/2)*1;
                                            pz2 += 0.05*Math.cos(p.rot-Math.PI/2)*1;

                                            // highlight,
                                            // need access to state,
                                            if(ishovered)
                                                fightSpriter.drawPoint(29, px2, py, pz2,  1, b, silverGray, 4);
                                            else if(!ishighlight)
                                                fightSpriter.drawPoint(29, px2, py, pz2,  1, b, b, 4);
                                            else
                                                fightSpriter.drawPoint(29, px2, py, pz2,  1, b, tnode2red, 4);

                                        }
                                    }else{

                                        if(alt != null){
                                            alt.drawPoint(c, px, py, pz, 1, ColorGroup.black, ColorGroup.black, mode);
                                        }else{
                                            // var col = p instanceof LinkNodule ? silverGray : clueScrollBrown; 
                                            textSheet.drawPointCharacter(c, px, py, pz, col);
                                        }
                                    }
                                    if(cursorFlicker &&
                                    s.cy == k && s.cx == j+1){
                                        // fighterSheet.drawPoint()
                                        fightSpriter.drawPoint(0, px+dimx, py, pz+dimz,  1, b, b, mode);
                                    }
                                }
                                Spriter.clickColors = 0;
                            }
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
            // else if(
            //     p instanceof LudeonNodule
            // ){
            //     var state = p.state;
            //     if(((LudeonNodule)p).other_state != null){
            //         state = ((LudeonNodule)p).other_state;
            //     }
            //     double ybasis = 0;
            //     for(int pre = 0; pre < 2; pre++){
            //         if(state != null && !textSheet.hasRepaunch()){
            //         int ll = !hidden ? state.rows.size() : 1;
            //         double yaccum = 0;
            //         for(int k = 0; k < ll; k++){
            //             int l = state.rows.get(k).size();
            //             for(int j = -1; j < l; j++){
            //                 char c = ' ';
            //                 Spriter alt = null;
            //                 Pasty pa = null;
            //                 double modif = 1;
            //                 if(j != -1){
            //                     var a = state.rows.get(k).get(j);
            //                     if(a.p != null){
            //                         if(a.p.sprite == null){
            //                             if(pre == 0)a.p.load();
            //                             p("AAAYEEEEEEEEEEEEEEEE");
            //                         }
            //                         alt = a.p.sprite;
            //                         pa = a.p;
            //                         a.p.sprite.clearRepaunch();
            //                         a.p.sprite.repaunch = textSheet.repaunch;
            //                         if(pre == 0)rememberToStep.push(a.p.sprite);
            //                     }else{
            //                         c = a.c;
            //                     }
            //                 }
            //                 var u = (10.0/40)*p.sizeMulti;
            //                 for(int i = 0; i < 4; i++){
            //                     double px = p.x-1_000_000;
            //                     double pz = p.z-1_000_000;
            //                     double py = p.ydisp - yaccum + ybasis ;
            //                     double xmod = (pa!= null ?pa.whrat>1?1:pa.whrat : 1);
            //                     double ymod = (pa!= null ?pa.whrat>1?1/pa.whrat : 1  : 1);
            //                     double dimx =u*Math.sin(p.rot)/2*xmod; 
            //                     double dimz =u*Math.cos(p.rot)/2*xmod;
                                
            //                     if(i == 1 || i == 2){
            //                         px -= dimx/modif;
            //                         pz -= dimz/modif;
            //                     }else{
            //                         px += dimx/modif;
            //                         pz += dimz/modif;
            //                     }
            //                     px += dimx*(j-40/p.sizeMulti);
            //                     pz += dimz*(j-40/p.sizeMulti);
            //                     if(i == 2 || i==3){
            //                         py+=-u*ymod;
            //                     }
            //                     if(i == 3){
            //                         if(state.cy == k){
            //                             if(pre == 0) ybasis = yaccum;
            //                         }
            //                         yaccum += u*ymod/5;
            //                     }
            //                     if(pre == 1){
            //                         if(alt != null){
            //                             alt.drawPoint(c, px, py, pz, 1, ColorGroup.black, ColorGroup.black, mode);
            //                         }else{
            //                             textSheet.drawPointCharacter(c, px, py, pz, clueScrollBrown);
            //                         }
            //                     }
            //                 }
            //             }
            //         }

            //         }
            //     }
            // }
        } 
        nano_timings[timingsIndex][11] += System.nanoTime() - before2;

        // time(">TEXT");

    }
    nano_timings[timingsIndex][11] = nano_timings[timingsIndex][11]/1_000_000;
    nano_timings[timingsIndex][12] = nano_timings[timingsIndex][12]/1_000_000;
    // subtiming("new-age");
    nano_timings[timingsIndex][10] = System.currentTimeMillis() - nano_timings[timingsIndex][10];



    // {
    //     fightSpriter.drawSprite(23, 0.8, 0.95, 0, 0.06, 0.06, 0, 1);
    //     fightSpriter.drawSprite(24, 0.8, 0.95, 0, 0.06, 0.06, neck, 1);
    // }
    if(fortounload == null){
        // System.out.println("it's null");
    }
    if(fortounload != null){
        if(fortounload.sprite == null){
            fortounload.hidden = false;
            fortounload = null;
        }else{

            // { // new code just for this
            //     for(int i = 0; i < 100; i++){
            //         int aj = (int)(fortounload.data.length*Math.random());
            //         fortounload.data[aj] = 0xff_00_00_00 | rand.nextInt(); 
            //     }
            //     // fortounload.sprite.clean();
            //     fortounload.sprite.cleanUp();
            //     fortounload.load();
            // }
            glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
            if(poppedPasty != null){
            }else
            if(fortounload_mousedown){
                // System.out.println("somehow here, how");

                // System.out.println("attempting to draw; mousedown");
                double[] temp1 = new double[1];
                double[] temp2 = new double[1];
                glfwGetCursorPos(window,temp1,temp2);
                double _mx = temp1[0]/buggaX;
                double _my = temp2[0]/buggaY;

                double ogx = fortounload_last_x != null ?  fortounload_last_x : _mx; 
                double ogy = fortounload_last_y != null ?  fortounload_last_y : _my;
                fortounload_last_x = _mx;
                fortounload_last_y = _my;
                int steps = 10; 
                if(fortounload instanceof BurningBush){
                    steps = 1;
                }
                for(int step = 0; step < steps; step++)
                {
                    double mx = ogx+(_mx-ogx)/steps*step;
                    double my = ogy+(_my-ogy)/steps*step;




                    textSheet2.drawString(""+mx, 0.8, 0.6, 0, 0.03, 255, 255, 0);
                    textSheet2.drawString(""+my, 0.8, 0.5, 0, 0.03, 255, 255, 0);

                    double magic = Spriter.globaluishrinker;
                    {
                        mx = (mx*2-1)*(magic);
                        mx = mx/2+0.5;
                    }{
                        my = (my*2-1)*(magic);
                        my = my/2+0.5;
                    }
                    mx=Math.max(0, mx);
                    mx=Math.min(1, mx);
                    my=Math.max(0, my);
                    my=Math.min(1, my);
                    System.out.println("IS IT A BURNING BUSH" + (fortounload instanceof BurningBush));
                    System.out.println("mouse is " + mx + ":"+my);
                    if(fortounload instanceof BurningBush){
                        var bb = (BurningBush) fortounload;
                        if(fortounload_mousebutton == GLFW_MOUSE_BUTTON_1){
                            bb.whereamioveride = mx;
                            bb.playing = false;
                            // bb.reloadaaa();
                            // bb.playing = true;
                            // bb.clean();
                            // bb.load();
                            {
                                AL10.alDeleteBuffers(bb.loadedBuffer);
                                AL10.alDeleteSources(bb.loadedSource);
                                bb.loadedBuffer = AL10.alGenBuffers();
                                bb.loadedSource =  AL10.alGenSources();
                            }
                            // bb.fuckmebuffer.position(0);
                            bb.reloadaaa();
                            bb.sprite.repaunch = false;
                            // AL10.alSourceStop(bb.loadedSource);
                        }else if(fortounload_mousebutton == GLFW_MOUSE_BUTTON_2){

                            bb.whereamioveride_end = mx;
                            if(bb.currentwhereami >= mx){
                                // NEEDS RESTARTING!
                                bb.playing = false;
                                // bb.reloadaaa();
                                // bb.playing = true;
                                // bb.clean();
                                // bb.load();
                                {
                                    AL10.alDeleteBuffers(bb.loadedBuffer);
                                    AL10.alDeleteSources(bb.loadedSource);
                                    bb.loadedBuffer = AL10.alGenBuffers();
                                    bb.loadedSource =  AL10.alGenSources();
                                }
                                // bb.fuckmebuffer.position(0);
                                bb.reloadaaa();
                                bb.sprite.repaunch = false;
                            }
                            // AL10.alSourceStop(bb.loadedSource);
                        }else if(fortounload_mousebutton == GLFW_MOUSE_BUTTON_3){
                            System.out.println("YEAH IT'S 3!");
                            if(bb.whereamioveride < bb.whereamioveride_end){
                                // EXTRACT
                                var len = bb.audioBytes.length;
                                int pos1 = ((int)(len * bb.whereamioveride))/2*2;
                                int pos2 = ((int)(len * bb.whereamioveride_end))/2*2;
                                var copy = Arrays.copyOfRange(bb.audioBytes, pos1, pos2);
                                if(copy.length > 0){
                                    var bb2 = bbExNihilo(copy);
                                    bb2.temporary_reapply_start_holder = bb.whereamioveride;
                                    bb2.temporary_reapply_end_holder = bb.whereamioveride_end;
                                    // System.exit(0);
                                    intoInventory(bb2);
                                    fortounload.hidden = false;
                                    bb.whereamioveride = 0;
                                    bb.whereamioveride_end = 1;
                                    TimeLockingKey.globalTypingSwitch = null;
                                    textSheet.repaunch = true;
                                    textSheet3.repaunch = true;
                                    textSheet.clearRepaunch();
                                    fortounload = null; // yes escape it
                                    fortounload_last_x = null;
                                    fortounload_mousedown = false;
                                    glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
                                }
                                
                            }
                        }
                    }
                    if(!(fortounload instanceof BurningBush) && fortounload != null){

                        int width = fortounload.width;
                        int height = fortounload.height;
                        int x = (int)(width*mx);
                        int y = (int)(((int)(height*my)));
                        int expi1 = 2 + width/300;
                        int expi2 = 2 + height/300;
                        for(int xx = -expi1; xx < expi1; xx++){
                        for(int yy = -expi2; yy < expi2; yy++){
                            int xxx = x+xx;
                            int yyy = y+yy;
                            if(xxx < 0 || xxx >= width || yyy < 0 || yyy >= height) continue;
                            int calc = xxx + yyy*width;
                            int color = 0xff_ff_00_00;
                            
                            if(fortounload_mousebutton == GLFW_MOUSE_BUTTON_2) color = 0xff_00_ff_ff; 
                            if(fortounload_mousebutton == GLFW_MOUSE_BUTTON_3) color = 0xff_00_ff_00; 
                            color = scalarToColor(mouseWheelRotation, 0, 1000).getRGB();
                            fortounload.data[calc] = color; // red
                        }
                        }
                    }
                }
                if(!(fortounload instanceof BurningBush)&& fortounload != null){
                    fortounload.sprite.cleanUp();
                    fortounload.load();
                }
                // { // new code just for this
                //     for(int i = 0; i < 100; i++){
                //         int aj = (int)(fortounload.data.length*Math.random());
                //         fortounload.data[aj] = 0xff_00_00_00 | rand.nextInt(); 
                //     }
                //     // fortounload.sprite.clean();
                //     fortounload.sprite.cleanUp();
                //     fortounload.load();
                // }
            }

            if(fortounload != null){

                // System.out.println("doing it here");
                fortounload.sprite.drawSprite(0, 
                    0, 0,
                    0, 2, 2, 0, ColorGroup.black, ColorGroup.black, 1000);
                if(fortounload instanceof BurningBush){
                    var bb = (BurningBush) fortounload;
                    fightSpriter.drawSprite(0, 
                        bb.currentwhereami*2-1, 0,
                        0, 0.2, 0.2, 0, ColorGroup.black, ColorGroup.black, 1000);
                }
                // fortounload.sprite.step();
                rememberToStep.add(fortounload.sprite);
            }
            if(poppedPasty != null){
                double[] temp1 = new double[1];
                double[] temp2 = new double[1];
                glfwGetCursorPos(window,temp1,temp2);
                double _mx = temp1[0]/buggaX;
                double _my = temp2[0]/buggaY;
                double mx = _mx;
                double my = _my;
                double magic = Spriter.globaluishrinker;
                {
                    mx = (mx*2-1)*(magic);
                    // mx = mx/2+0.5;
                }{
                    my = -(my*2-1)*(magic);
                    // my = my/2+0.5;
                }
                // double idk = Math.pow(poppedPastySize-10, 1.06); 
                double idk = Math.pow(1.2, poppedPastySize-10); 
                // System.out.println(idk);
                // System.out.println(poppedPasty.whrat);
                double whrat_adj = poppedPasty.whrat/fortounload.whrat;
                poppedPasty.sprite.drawSprite(0, mx, my, -0.1, 0.5*idk*whrat_adj, 0.5*idk, 0, ColorGroup.black, ColorGroup.black, 1000);
                rememberToStep.add(poppedPasty.sprite);
            }
        }
    }
}
// nano_timings[timingsIndex][9] = System.currentTimeMillis();

subtiming("new-age");
subtiming("all-time"); // 0

{
    var k = (Future<Void>)terTaskCry;
    
    class LandscapeTask implements Callable<Void> {
        public boolean idie = false;
        public LandscapeTask(){}
        public Void call() throws Exception {
            p("gutting , LandscapeTask call() something here is where I will call set_temple");

            long idk = 0;
            try{
                int sp = 29;
                int sp2 = sp/2;
                double mx = x;
                double mz = z;
                outer:
                for(int oo = 0; oo < sp; oo++){
                    for(int pp = 0; pp < sp; pp++){
                        
                        
                        int o = oo <= sp2 ? oo:sp2-oo;
                        int p = pp <= sp2 ? pp:sp2-pp;
                        int resolution = (1+(int)((Math.abs(o)+Math.abs(p))/2));
                        if(resolution >= 5) resolution = 5;
                        // if(resolution >= 7) resolution = 7;
                        // resolution = 1;
                        int jump = 1<<resolution;
                        int temp = 128;
                        int osx = temp*o+(int)(Math.floor(mx/temp)*temp);
                        int osy = temp*p+(int)(Math.floor(mz/temp)*temp);
                        
                        // System.out.println((osx-1_000_000)/128);
                        // System.out.println((osy-1_000_000)/128);
                        // System.exit(0);
                        if((osx-1_000_000)/128 == 15 && (osy-1_000_000)/128 == 15){
                            continue;
                        }
                        {
                            // check if set already using the get function
                            // var get = sectorFunctions.get((osx,osy));
                            // if no get, roll a dice (SMEAR), if a good roll, call arbfunc, otherwise set to default (noop)
                            int difx = osx-1_000_000;
                            int dify = osy-1_000_000;
                            var puty = osy;
                            var putx = osx;
                            
                            var get = sectorFunctions.get(xy(putx,puty));
                            boolean tooClose = (Math.abs(difx) < 100 || Math.abs(dify) < 100 );
                            if(get == null){
                                {  
        // System.out.println("Setting new sector " + putx + " " + puty);
                                    // System.out.println("PICKING LANDSCAPE");
                                    p("set_temple default " + print_key(xy(putx,puty)));
                                    var params = new double[]{
                                        0,
                                        0,
                                        0,
                                        1,
                                        1,
                                        1,
                                        Math.random()*255,
                                        0,
                                        (Math.random()-Math.random())*4,
                                        0,
                                        0,
                                        0
                                    };
                                    Sector s = null;
                                    // System.exit(0);
                                        if(false && Math.random() > 0.9){
                                            int fixationx = 1;
                                            int fixationy = 1;
                                            for(int i = 0;  i < fixationx; i++){
                                                for(int j = 0; j < fixationy; j++){
                                                    System.out.println("i " + i + " j " + j);
                                                    int signx = 1;
                                                    int signy = 1;
                                                    double r = Math.random();
                                                    double g = Math.random();
                                                    double b = Math.random();
                                                    double r1 =Math.random()*255;
                                                    double coef = 
                                                        (Math.random()-Math.random())*4;
                                                    double devr = (Math.random()-Math.random())*127+128;
                                                    double devg = (Math.random()-Math.random())*127+128;
                                                    double devb = (Math.random()-Math.random())*127+128;
                                                    params = new double[]{
                                                        r,
                                                        g,
                                                        b,
                                                        signx>0?i*1.0:fixationx-i*1.0-1,
                                                        signy>0?j*1.0:fixationy-j*1.0-1,
                                                        fixationx,
                                                        r1,
                                                        0,
                                                        coef,
                                                        devr,
                                                        devg,
                                                        devb
                                                    };
                                                     s = new Sector(
                                                        // bowlHeightFunction,
                                                        bowlHeightFunction,
                                                        defaultColorFunction,
                                                        defaultTextureFunction,
                                                        params, // state
                                                        new int[3]
                                                    );
                                                    // {
                                                    //     var devilx = ix+signx*i;
                                                    //     var devily = iy+signy*j;
                                                    //     var key = xy(1000064+128*0-128+128*devilx, 1000064+128*0-128+128*devily);
                                                    //     sectorFunctions.put(key,s);
                                                    // }
                                                }
                                            }
                                        }else{
                                    if(osx < 1_110_000){
                                        s = new Sector(
                                            // bowlHeightFunction,
                                            defaultHeightFunction,
                                            defaultColorFunction,
                                            defaultTextureFunction,
                                            params, // state
                                            new int[3]
                                        );
                                    }else{
                                            s = new Sector(
                                                // bowlHeightFunction,
                                                freeRangeHeight1,
                                                freeRangeColor1,
                                                defaultTextureFunction,
                                                params, // state
                                                new int[3]
                                            );
                                        }
                                    }
                                    {
                                        var key = xy(putx,puty);
                                        sectorFunctions.put(key,s);
                                    }
                                }
                            }else{
                                p("set_temple repeat....` " + print_key(xy(putx,puty)));
                            }
                        }

                        for(int i = 0; i < temp; i+=jump){
                            for(int j = 0; j < temp; j+=jump){
                                int texture = 0;
                                int ii = i + osx;
                                int jj = j + osy;
                                
                                for(int a = 0; a < 4; a++){
                                    idk++;
                                    if(idie) break outer;
                                    {
                                        ii = i + osx;
                                        jj = j + osy;
                                        if(a == 1 || a == 2) ii += jump;
                                        if(a == 2 || a == 3) jj += jump;

                                        // AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE
                                        // AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE
                                        // AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE
                                        // AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE
                                        // AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE
                                        // AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE AMPUTATE
                                        // GO GO GO GOG OG GO GO GOG OG OG GO GO GO GOG OG GO GOG OG 
                                        // I'll BUY YOU TWO INSOMNIA COOKIES GOOOOOOOOOOOOOOO GO GO GO GOG O
                                        
                                        // SEEK THE NON-DEFAULT SEEK THE NON-DEFAULT!!!!

                                        double hres = 0;
                                        int[] cres = null;

                                        if(FUCKMELOOKUP.get(xy(ii,jj)) != null){
                                            Object[] ooo = FUCKMELOOKUP.get(xy(ii,jj));
                                            hres = (Double)ooo[0];
                                            cres = (int[])ooo[1];
                                        }else{
                                            var putx = ii%128==0&&ii!=osx ? ii : osx;
                                            var puty = jj%128==0&&jj!=osy ? jj : osy;
                                            // var putx = ii-(ii%128);
                                            // var puty = jj-(jj%128);
                                            
                                            var get = sectorFunctions.get(xy(putx,puty));
                                            // var get = sectorFunctions.get(xy(osx,osy));
                                            // p(defaultColorFunction, defaultHeightFunction);
                                            var hfunc = get == null ? defaultHeightFunction : get.x;
                                            var cfunc = get == null ? defaultColorFunction : get.y;
                                            var params = get == null ? new double[10] : get.a;
                                            int[] inj = new int[]{
                                                ii,
                                                jj,
                                                resolution, ii%128-128, jj%128-128};
                                            hres = hfunc.apply(inj, params);
                                            cres = cfunc.apply(inj, params);
                                            FUCKMELOOKUP.put(xy(ii,jj), o(hres, cres));

                                        }


                                            var red   = cres[0];
                                            var green = cres[1];
                                            var blue  = cres[2];
                                            double height = hres;
                                            // p("osxosy", osx, osy);
                                            if(a == 0)
                                                texture = cres[3];
                                            // p("offending value: " + texture);
                                            var red2 =   cres[4];//(int)arbitrary(ii, jj, resolution, 4);
                                            var green2 = cres[5];//(int)arbitrary(ii, jj, resolution, 5);
                                            var blue2 =  cres[6];//(int)arbitrary(ii, jj, resolution, 6);
                                            // p("offending value: " + texture);
                                            var red3 =   cres[7];//(int)arbitrary(ii, jj, resolution, 4);
                                            var green3 = cres[8];//(int)arbitrary(ii, jj, resolution, 5);
                                            var blue3 =  cres[9];//(int)arbitrary(ii, jj, resolution, 6);
                                        


                                        var ov = overrides.get(xy(ii,jj));
                                        if(a == 0){
                                            // texture =  arbitrary(ii, jj, resolution, 7) > 128 ? 9 : 10;
                                            if(height == WATER) texture = 12;
                                            if(ov != null){
                                                texture = ov.texture;
                                            }
                                        }
                                        if(ov != null){
                                            height = ov.height;
                                        }
                                        var c = new ColorGroup(red,green,blue, red2,green2,blue2, red3,green3,blue3);
                                            scapeSpriter.drawPoint(texture,
                                            (double)ii-1_000_000,
                                            height,
                                            (double)jj-1_000_000,
                                            1.0, b, c, 56);
                                    }
                                }
                            }
                        }
                    }
                }
                p("finished with nat_struct");
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    // cirt 7 is....

    if(false){
        // time("scapework");
        int nlx7 = (int)(x/128);
        int nlz7 = (int)(z/128);

        if((nlx7 != ter_lx7 || nlz7 != ter_lz7 || demandreset)){ // OR DEMAND RESET
            // p("Entering region... " + (nlx7-7812) + " " + (nlz7-7812));
            last_electric_frogs_ts -= 20_000;
            demandreset = false;
            ter_lx7 = nlx7;
            ter_lz7 = nlz7;
            // what is my region

            if(k != null){
                // boolean bb = k.cancel(true);
                var e = (LandscapeTask)terTaskCry2;
                e.idie = true;
                while(!k.isDone()){ 
                    // p("waiting...");
                    try{
                        Thread.sleep(2);
                    }catch(Exception e2){e2.printStackTrace();}
                }
                // p("killed old if old" + " "+ k.isDone());
                terTaskCry = null;
                terTaskCry2 = null; //weird ass garbage collection thing? idek
                k = null;
                // if(!k.isDone()){
                // }
            }
            scapeSpriter.clearRepaunch();
            var e = new LandscapeTask();
            terTaskCry2 = e;
            p("submitting annoda" + scapeSpriter.offset3 + " " + scapeSpriter.drawct);
            terTaskCry = exe2.submit(e);
            //clear any existing tasks
        }else{
            nano_timings[timingsIndex][2] = System.currentTimeMillis();
            if(k != null && k.isDone()){
                // p("Cleanup! Cleanup! " + scapeSpriter.offset3 + " " + scapeSpriter.drawct);
                terTaskCry = null;
                {
                    swapSpriter.clearRepaunch();
                    // odd swapping maneuvour
                    {
                        var temp = swapSpriter.indexData;
                        
                        swapSpriter.indexData = scapeSpriter.indexData;
                        scapeSpriter.indexData = temp;
                    }
                    {
                        var temp = swapSpriter.attributesData;
                        swapSpriter.attributesData = scapeSpriter.attributesData;
                        scapeSpriter.attributesData = temp;
                    }
                    {
                        swapSpriter.drawct = scapeSpriter.drawct;
                        swapSpriter.offset3 = scapeSpriter.offset3;
                        // p("swap complete " + swapSpriter.drawct);
                    }
                }
            }
            nano_timings[timingsIndex][2] = System.currentTimeMillis() - nano_timings[timingsIndex][2];
        }
        // time("scapework");
    }


}
// subtiming("all-time"); // 1

// time("new-age");

//outer:
// if(!scapeSpriter.hasRepaunch() )
{
    //extra just for feedback, could probably be simplified/optimized somehow
// for(int oo = 0; oo < 1; oo++){
//     for(int pp = 0; pp < 1; pp++){
//         int o = oo <= 0 ? oo:0-oo;
//         int p = pp <= 0 ? pp:0-pp;
//         int resolution = (1+(int)((Math.abs(o)+Math.abs(p))/2));
//         if(resolution >= 5) continue;
//         int jump = 1<<resolution;
//         int temp = 128;
//         for(int i = 0; i < temp; i+=jump){
//             for(int j = 0; j < temp; j+=jump){
//                 int osx = temp*o+(int)(Math.floor(x/temp)*temp);
//                 int osy = temp*p+(int)(Math.floor(z/temp)*temp);
//                 // if((System.nanoTime()-now)/1_000_000 > 50){
//                 //     // p("BREAKER HIT");
//                 //     break outer;
//                 // }
    
//                 int tmapx = (i+osx)%4000;
//                 int tmapy = (j+osy)%4000;
//                 Tile t = tmap2[tmapx][tmapy];
//                 boolean yesnt = false;
//                 int texture = 0;
//                 int ii = i + osx;
//                 int jj = j + osy;
//                 double comparison_slope = Math.sin(-neck)/Math.cos(-neck); 
//                 double ix = ii-x;
//                 double iy = jj-z;
    
//                 // if(!followmode && ((ix*comparison_slope <= iy && Math.cos(neck)<0) ||
//                 //     (ix*comparison_slope >= iy && Math.cos(neck)>0))){
//                 //     continue;
//                 // }
//                 var ov = overrides.get(xy(ii,jj));
//                 if(ov == null) continue;
//                 for(int a = 0; a < 4; a++){
//                     if((yesnt || a == 0) && t != null && t.resolution == resolution){
//                         if(a == 0) yesnt = true;
//                         //redraw
//                         ARBITRARY_TRACKER_A++;
//                         // if(true)continue;
                        
//                         fightSpriter.drawPointAndCache(t.data[a]);
//                     }else
//                     {
//                         ARBITRARY_TRACKER_B++;
//                         if(a == 0){
//                             t = new Tile();
//                             t.resolution = resolution;
//                             t.data = new Object[4][0];
//                         }
//                         ii = i + osx;
//                         jj = j + osy;
//                         if(a == 1 || a == 2) ii += jump;
//                         if(a == 2 || a == 3) jj += jump;
                        
//                         var putx = i == 128 ? ii : osx;
//                         var puty = j == 128 ? jj : osy;
//                         // var putx = ii-(ii%128);
//                         // var puty = jj-(jj%128);
//                         var get = sectorFunctions.get(xy(putx,puty));
//                         hfunc = get == null ? defaultHeightFunction : get.x;
//                         var cfunc = get == null ? defaultColorFunction : get.y;
//                         var params = get == null ? new double[10] : get.a;
//                         int[] inj = new int[]{
//                             ii-osx,
//                             jj-osy,
//                             resolution,(ii-osx)%128-128,    (jj-osx)%128-128};
//                         //hres should be first you mong.
//                         double hres = hfunc.apply(inj, params);
//                         int[] cres = cfunc.apply(inj , params);
//                         var red   = cres[0];
//                         var green = cres[1];
//                         var blue  = cres[2];
//                         double height = hres;
//                         if(a == 0)
//                             texture = cres[3];
//                         var red2 =   cres[4];//(int)arbitrary(ii, jj, resolution, 4);
//                         var green2 = cres[5];//(int)arbitrary(ii, jj, resolution, 5);
//                         var blue2 =  cres[6];//(int)arbitrary(ii, jj, resolution, 6);
//                         if(a == 0){
//                             // texture =  arbitrary(ii, jj, resolution, 7) > 128 ? 9 : 10;
//                             if(height == WATER) texture = 12;
//                             if(ov != null){
//                                 texture = ov.texture;
//                             }
//                         }
//                         if(ov != null){
//                             height = ov.height;
//                         }
//                         var c = new ColorGroup(red,green,blue, red2,green2,blue2);
//                         t.data[a] = 
//                             fightSpriter.drawPointAndCache(texture,
//                             (double)ii-1_000_000,
//                             height+0.01,
//                             (double)jj-1_000_000,
//                             1.0, b, c, 0);
//                         tmap2[tmapx][tmapy] = t;
//                         // tmap.put(corn, t);
//                     }
//                     // if((System.nanoTime()-now)/1_000_000 > 16) break outer;
//                 }
//             }
//         }
//     }
//     }
}


subtiming("all-time"); // 2
time("draws");

{
    double avgheight = avgheight(x, z);
    y = 10+avgheight;
    // p("avg", y+"\t", x+"\t", z+"\t");
    
    {

        int rendx = (int)(x/128)*128+64; // expirement, if the common 3dumb is placed in the middle too, then 63 63 will be empty
        int rendz = (int)(z/128)*128+64; // expirement, if the common 3dumb is placed in the middle too, then 63 63 will be empty
        pasties.query2D(rendx-0.1, rendx+0.1, rendz-0.1, rendz+0.1);
        ThreeDumb find = null;
        for(int i = 0; i < pasties.size(); i++){
            GameObject go = pasties.getAt(i);
            if(go instanceof ThreeDumb){
                find = (ThreeDumb)go;
                break;
            }
        }
        debugTriangle = debugTriangle_EMPTY;

        if(find != null){
            // don't know what to do next
            if(find.values != null){
                double[] temp = new double[9];
                for(int i = 0; i < find.values.length; i+=9){
                    for(int j = 0; j < 9; j++){
                        temp[j] = find.values[i+j];
                    }
                    double px = (x%128)-64;
                    double pz = (z%128)-64;
                    boolean provar = isPointInTriangle(temp, pz,px);
                    // System.out.println(provar +" "+ i);
                    y = 10;
                    if(provar){
                        // y = 50;
                        y = 10 + getYOnTrianglePlane(temp, pz, px);
                        debugTriangle = temp;
                        double idkx = x-px;
                        double idkz = z-pz;
                        debugTriangle[0]+=idkx;
                        debugTriangle[2]+=idkz;
                        debugTriangle[3]+=idkx;
                        debugTriangle[5]+=idkz;
                        debugTriangle[6]+=idkx;
                        debugTriangle[8]+=idkz;
                        break;
                        // System.exit(0);
                    }
                }

            }
        }

    }
}
{
    time("rocks");

    double nx = x-1_000_000;
    double nz = z-1_000_000;

    double ny = (newwigglemode ? wiggleItem.ydisp : y) + dispy;
    double nangle = neck;
    if(followmode){
        // nangle += followangle;
        double horizdist = Math.cos(chin)*followdist; 
        nx+= horizdist*Math.sin(nangle+Math.PI);
        nz+= horizdist*Math.cos(nangle+Math.PI);
        ny+=Math.sin(chin)*followdist;
        { //draw player
            
            for(int i = 0; i < 4; i++){
                double px = x-1_000_000;
                double pz = z-1_000_000;
                double py = y;
                if(i == 1 || i == 2){
                    px += 5*Math.sin(neck+Math.PI/2);
                    pz += 5*Math.cos(neck+Math.PI/2);
                }else{
                    px -= 5*Math.sin(neck+Math.PI/2);
                    pz -= 5*Math.cos(neck+Math.PI/2);
                }
                if(i == 2 || i==3){
                    py+=-10;
                }
                int mode = 8;
                mode = 4;
                Spriter which = doomguymode > 1 ? doomguysheet2 : doomguysheet;
                which.drawPoint(0, px, py, pz,  1, b, b, mode);
            }
        }
    }
    {
        // hands
        // System.out.println(bobbyselection);
        { // draw player
            for(int j = 0; j < 2; j++){
                double myheight = 1;
                int iii = j;
                var bobbylist = invStore[iii];
                boolean idk = invStore[bobbyselection] == bobbylist;
                // double ang = Math.PI*3/2 + nangle - Math.PI/12 + Math.PI/6 * j; 
                double ang =  nangle - Math.PI/12 + Math.PI/6 * j ; 
                double handoffset = idk ? 0.5: 0;
                for(int i = 0; i < 4; i++){
                    double px = nx + 5*Math.sin(ang);
                    double pz = nz + 5*Math.cos(ang);
                    double py = ny+5-4 + 0 - 3 + handoffset;
                    boolean qq = (i == 1 || i == 2);
                    boolean jj = j == 0;
                    if(qq && jj || (!jj && !qq)){
                        px += 1*Math.sin(ang+2);
                        pz += 1*Math.cos(ang+2);
                    }else{
                        px -= 1*Math.sin(ang+2);
                        pz -= 1*Math.cos(ang+2);
                    }
                    if(i == 2 || i==3){
                        py+=-myheight;
                    }
                    if(i == 0 || i==1){
                        px += 2*Math.sin(ang);
                        pz += 2*Math.cos(ang);
                    }
                    int mode = 8;
                    mode = 4;
                    if(handSpriterOverride != null){
                        handSpriterOverride.sprite.drawPoint(38, px, py, pz,  1, b, b, mode);
                    }else{
                        // fightSpriter.drawPoint(38, px, py, pz,  1, b, b, mode);
                    }
                }
                if(handSpriterOverride != null){
                    handSpriterOverride.sprite.repaunch = false;
                    handSpriterOverride.sprite.clearRepaunch();
                    handSpriterOverride.sprite.step(gridx, gridy, nx,ny,nz, nangle, chin);
                }
                // for(int iii = 0; iii < 2; iii++){
                // System.out.println(iii + " " + bobbylist);
                int jjj = 1;
                int killme = 0;
                myheight = 0.3;
                for(var bobbyb: bobbylist){
                    int mode = 4;
                    // System.out.println("yo yo y o ");
                    
                    if(false && tabselection == killme && invStore[bobbyselection] == bobbylist){
                        mode = 8;
                    }
                    if(killme == 0 && invStore[bobbyselection] == bobbylist){
                        mode = 8;
                    }

                    // ((Pasty)bobbyb).sprite.drawSprite(0, 
                    //     (-1+size/2)+size*1.3*(i/wrap), (1-size/2)-size*1.3*(i%wrap),
                    //     0, size, size, 0, ColorGroup.black, ColorGroup.black, mode);
                    // System.out.println("Oh fuck me");
                    // System.exit(0);
                    for(int i = 0; i < 4; i++){
                        double px = nx + 5*Math.sin(ang);
                        double pz = nz + 5*Math.cos(ang);
                        double py = ny+5-4 + 0 - 3 + handoffset;
                        double whrat = bobbyb instanceof Pasty ? ((Pasty)bobbyb).whrat : 0.5;
                        whrat/=2;
                        if(!(i == 1 || i == 2)){
                            px += 0.5*Math.sin(ang+2)*whrat;
                            pz += 0.5*Math.cos(ang+2)*whrat;
                        }else{
                            px -= 0.5*Math.sin(ang+2)*whrat;
                            pz -= 0.5*Math.cos(ang+2)*whrat;
                        }
                        if(i == 2 || i==3){
                            py+=-myheight;
                        }
                        if(bobbyb instanceof Pasty){
                            ((Pasty)bobbyb).sprite.drawPoint(0, px, py+ myheight*jjj, pz,  1, b, b, mode);
                            ((Pasty)bobbyb).sprite.step(gridx, gridy, nx,ny,nz, nangle, chin);
                        }
                        if(bobbyb instanceof TextNodule){
                            int so = 17;
                            if(bobbyb instanceof LinkNodule) so = 25; 
                            if(bobbyb instanceof FileNodule) so = 37;
                            fightSpriter.drawPoint(so, px, py+ myheight*jjj, pz,  1, b, b, mode);
                        }
                        // if(bobbyb instanceof RecordNodule){
                        //     RecordNodule p = (RecordNodule)bobbyb;
                        //     RGB r1 = RGB.fromCombined(p.c1);
                        //     RGB r2 = RGB.fromCombined(p.c2);
                        //     RGB r3 = RGB.fromCombined(p.c3);
                        //     ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r2.r,r2.g,r2.b, r3.r,r3.g,r3.b);
                        //     fightSpriter.drawSprite(19, (-1+size/2)+size*1.3*(i/wrap),
                        //         (1-size/2)-size*1.3*(i%wrap), 0, size, size, 0,  micColors, replacementC, mode);
                        // }
                        if(bobbyb instanceof PortalNodule){
                            PortalNodule p = (PortalNodule)bobbyb;
                            RGB r1 = RGB.fromCombined(p.c1);
                            RGB r2 = RGB.fromCombined(p.c2);
                            RGB r3 = RGB.fromCombined(p.c3);
                            ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                            int so = p.style == 0 ? 22 : 39;
                            fightSpriter.drawPoint(so, px, py+ myheight*jjj, pz,  1, micColors, replacementC, mode);
                        }
                        if(bobbyb instanceof TenPortals){
                            TenPortals p = (TenPortals)bobbyb;
                            RGB r1 = RGB.fromCombined(p.c1);
                            RGB r2 = RGB.fromCombined(p.c2);
                            RGB r3 = RGB.fromCombined(p.c3);
                            ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                            fightSpriter.drawPoint(26, px, py+ myheight*jjj, pz,  1, micColors, replacementC, mode);
                
                            // fightSpriter.drawSprite(26, (-1+size/2)+size*1.3*(i/wrap),
                            // (1-size/2)-size*1.3*(i%wrap), 0, size, size, 0,  micColors, replacementC, mode);
                        }
                        if(bobbyb instanceof ObeliskNodule){
                            ObeliskNodule p = (ObeliskNodule)bobbyb;
                            RGB r1 = RGB.fromCombined(p.c1);
                            RGB r2 = RGB.fromCombined(p.c2);
                            RGB r3 = RGB.fromCombined(p.c3);
                            ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                            fightSpriter.drawPoint(40, px, py+ myheight*jjj, pz,  1, micColors, replacementC, mode);
                
                            // fightSpriter.drawSprite(26, (-1+size/2)+size*1.3*(i/wrap),
                            // (1-size/2)-size*1.3*(i%wrap), 0, size, size, 0,  micColors, replacementC, mode);
                        }
                        if(bobbyb instanceof ControllerNodule){
                            ControllerNodule p = (ControllerNodule)bobbyb;
                            RGB r1 = RGB.fromCombined(p.c1);
                            RGB r2 = RGB.fromCombined(p.c2);
                            RGB r3 = RGB.fromCombined(p.c3);
                            ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                            fightSpriter.drawPoint(44, px, py+ myheight*jjj, pz,  1, micColors, replacementC, mode);
                
                            // fightSpriter.drawSprite(26, (-1+size/2)+size*1.3*(i/wrap),
                            // (1-size/2)-size*1.3*(i%wrap), 0, size, size, 0,  micColors, replacementC, mode);
                        }
                        if(bobbyb instanceof FunnelNodule){
                            FunnelNodule p = (FunnelNodule)bobbyb;
                            RGB r1 = RGB.fromCombined(p.c1);
                            RGB r2 = RGB.fromCombined(p.c2);
                            RGB r3 = RGB.fromCombined(p.c3);
                            ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                            fightSpriter.drawPoint(46, px, py+ myheight*jjj, pz,  1, micColors, replacementC, mode);
                
                            // fightSpriter.drawSprite(26, (-1+size/2)+size*1.3*(i/wrap),
                            // (1-size/2)-size*1.3*(i%wrap), 0, size, size, 0,  micColors, replacementC, mode);
                        }
                        if(bobbyb instanceof CompressorNodule){
                            CompressorNodule p = (CompressorNodule)bobbyb;
                            RGB r1 = RGB.fromCombined(p.c1);
                            RGB r2 = RGB.fromCombined(p.c2);
                            RGB r3 = RGB.fromCombined(p.c3);
                            ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                            fightSpriter.drawPoint(41, px, py+ myheight*jjj, pz,  1, micColors, replacementC, mode);
                
                            // fightSpriter.drawSprite(26, (-1+size/2)+size*1.3*(i/wrap),
                            // (1-size/2)-size*1.3*(i%wrap), 0, size, size, 0,  micColors, replacementC, mode);
                        }
                        if(bobbyb instanceof PumpJack){
                            PumpJack p = (PumpJack)bobbyb;
                            RGB r1 = RGB.fromCombined(p.c1);
                            RGB r2 = RGB.fromCombined(p.c2);
                            RGB r3 = RGB.fromCombined(p.c3);
                            ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b, r1.r+10, r1.g+10,r1.b+10,r2.r, r2.g,r2.b);
                            fightSpriter.drawPoint(48, px, py+ myheight*jjj, pz,  1, micColors, replacementC, mode);
                
                            // fightSpriter.drawSprite(26, (-1+size/2)+size*1.3*(i/wrap),
                            // (1-size/2)-size*1.3*(i%wrap), 0, size, size, 0,  micColors, replacementC, mode);
                        }
                        if(bobbyb instanceof PromiseNodule){
                            PromiseNodule p = (PromiseNodule)bobbyb;
                            fightSpriter.drawPoint(50, px, py+ myheight*jjj, pz,  1, micColors, micColors, mode);
                        }
                        // if(bobbyb instanceof ChestNodule){
                        //     ChestNodule p = (ChestNodule)bobbyb;
                        //     RGB r1 = RGB.fromCombined(p.c1);
                        //     RGB r2 = RGB.fromCombined(p.c2);
                        //     ColorGroup replacementC = new ColorGroup(r1.r,r1.g,r1.b,r2.r, r2.g,r2.b);
                        //     fightSpriter.drawSprite(26, (-1+size/2)+size*1.3*(i/wrap),
                        //     (1-size/2)-size*1.3*(i%wrap), 0, size, size, 0,  chestColors, replacementC, mode);
                        // }als)
                    }
                    jjj++;
                    killme++;
                }
                // }
            }
        }
        parkervalue = (chin + Math.PI)/(Math.PI*2);
        parkervalue = 0;
        // System.out.println(parkervalue);
    }

    nano_timings[timingsIndex][8] = System.currentTimeMillis();

    nano_timings[timingsIndex][9] = System.currentTimeMillis();

    for(var e: rememberToStep){
        if(e != null && !e.dead)
            e.step(gridx, gridy, nx,ny,nz, nangle, chin);
    }
    // subtiming("draws"); // 0
    // time("rocks");

    {


        time("sub-disk-2");
        {
        }


        {
            // HERE DUMB IN ALL EXTRA SECTORS 
        }
        boolean scenechange = false;
        for(int i = 0; i < sside*sside; i++){
            int nlx7 = (int)(x/128);
            int nlz7 = (int)(z/128);
            var sector = xy(nlx7+i/sside-sside/2, nlz7+i%sside-sside/2);
            neededNow.add(sector);
        }
        // if(Main.cycling_remote_mode){
        //     boolean holdingRemote = (bobbies.size() >= 1) && bobbies.get(0) instanceof ControllerNodule; 
        //     if(holdingRemote){
        //         var cn = (ControllerNodule)bobbies.get(0);
        //         if(cn.currentIndex+1 <= cn.portalNodules.size()-1){
        //             var pn = cn.portalNodules.get(cn.currentIndex+1);
        //             for(int i = 0; i < 25; i++){
        //                 int nlx7 = (int)(pn.destx/128);
        //                 int nlz7 = (int)(pn.destz/128);
        //                 var sector = xy(nlx7+i/5-2, nlz7+i%5-2);
        //                 neededNow.add(sector);
        //             }
        //         }
        //     }
        // }
        // subtiming("sub-disk-2"); // 0
        if(stage1ukeyportal != null){
            // just add 3 if whatever
            for(int i = 0; i < 9; i++){
                int nlx7 = (int)(stage1ukeyportal.destx/128);
                int nlz7 = (int)(stage1ukeyportal.destz/128);
                var sector = xy(nlx7+i/3-1, nlz7+i%3-1);
                neededNow.add(sector);
            }
        }
        if(neededTenPortals != null){
            int nlx7 = (int)(neededTenPortals.x/128);
            int nlz7 = (int)(neededTenPortals.z/128);
            var sector = xy(nlx7, nlz7);
            neededNow.add(sector);
        }
        {
            var i = INVENTORY_PURGATORY.iterator();
            while(i.hasNext()){
                var o = i.next();
                i.remove();
                if(o instanceof Pasty){
                    var oo = (Pasty)o;
                    if(oo.sprite == null){
                        // System.exit(0);
                        oo.load();
                    }
                }
                intoInventory(o);
            }
        }
        {
            WordGen.spinColor();
            var i = MULTIPLECHOICE_PURGATORY.iterator();
            while(i.hasNext()){
                var e = i.next();
                System.out.println("help\n");
                System.out.println(e);
                splitLongStrings(e, 39);
                int[] ia = WordGen.takeFrame(e, false);  
                int ww = 1500;
                int hh = 850;
                MultipleChoice p = new MultipleChoice();
                p.sprite = new Spriter(ia, ww, hh, 1, 4);
                p.width = ww;
                p.height = hh;
                p.data = ia;
                p.whrat = ww*(1.0)/hh;
                intoInventory(p);
                i.remove();
            }
        }
        {
            var ii = MULTIPLECHOICE_RESULT_PURGATORY.iterator();
            while(ii.hasNext()){
                var e = ii.next();
                System.out.println("help\n");
                splitLongStrings(e, 39);
                // peek for check
                if(e.size() > 0){
                    String fline = e.get(0);
                    if(fline.startsWith("Yes")){
                        sfx_success.noise();
                        WordGen.colorGreen();
                    }else{
                        sfx_failure.noise();
                        WordGen.colorRed();
                    }
                }
                int[] ia = WordGen.takeFrame(e,false);  
                int ww = 1500;
                int hh = 850;
                Pasty pp = new Pasty();
                pp.sprite = new Spriter(ia, ww, hh, 1, 4);
                pp.width = ww;
                pp.height = hh;
                pp.data = ia;
                pp.whrat = ww*(1.0)/hh;
                if(presumed_assumed_edited_multiple_choice != null &&
                    ((Pasty)presumed_assumed_edited_multiple_choice).data != null)
                {
                    var poppedPasty1 = presumed_assumed_edited_multiple_choice;
                    presumed_assumed_edited_multiple_choice = null;
                    var poppedPasty2 = pp;
                    BufferedImage bi1 = null;
                    BufferedImage bi2 = null;
                    {
                        var p = poppedPasty1;
                        bi1 = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                        for(int i = 0; i < p.width; i++){
                            for(int j = 0; j < p.height; j++){
                                bi1.setRGB(i, j, p.data[i+j*p.width]);
                            }
                        }
                    }
                    {
                        var p = poppedPasty2;
                        bi2 = new BufferedImage(p.width, p.height, BufferedImage.TYPE_4BYTE_ABGR);
                        for(int i = 0; i < p.width; i++){
                            for(int j = 0; j < p.height; j++){
                                bi2.setRGB(i, j, p.data[i+j*p.width]);
                            }
                        }
                    }
                    var _fff0000012720 = a2._fff0000019471(bi1,bi2);
                    Pasty p = new Pasty();
                    var bi = _fff0000012720;
                    p.sprite = new Spriter(bi, p.sl(), p.mso());
                    p.width = bi.getWidth();
                    p.height = bi.getHeight();
                    p.data = p.sprite.tempIntDataForStorage;
                    p.sprite.tempIntDataForStorage = null;
                    p.whrat = bi.getWidth() * 1.0 / bi.getHeight();
                    {
                        p.x = poppedPasty1.x+Math.random()/100000;
                        p.z = poppedPasty1.z;
                        p.ydisp = poppedPasty1.ydisp;
                        p.rot = poppedPasty1.rot;
                        p.size = poppedPasty1.size;
                    }
                    DELETION_PURGATORY.add(poppedPasty1);
                    PURGATORY.add(p);
                    // intoInventory(p);
                }
                ii.remove();
            }
        }
        {
            var i = PURGATORY.iterator();
            while(i.hasNext()){
                var o = i.next();
                var obj = (GameObject)o;
                int nlx7 = (int)(obj.x/128);
                int nlz7 = (int)(obj.z/128);
                var sector = xy(nlx7, nlz7);

                {
                    // IS   THE   SECTOR   LOADED   ???
                    var inplay = new HashSet<>(InPlay);
                    if(inplay.contains(sector)){
                        // PLACE IT AND LOAD IT
                        System.out.println("here, adding the belated Pasty");
                        pasties.add(o);
                        o.load();
                        i.remove();
                        if(o instanceof Pasty){
                            System.out.println("fingers crossed");
                            rememberToStep.add(((Pasty)o).sprite);
                        }
                        // textSheet.repaunch = false;
                        textSheet.clearRepaunch();
                    }else{
                        neededNow.add(sector);
                        // System.out.println("Told you");
                        // System.exit(0);
                    }
                }
            }
        }
        {
            var i = DELETION_PURGATORY.iterator();
            while(i.hasNext()){
                var o = i.next();
                var obj = (GameObject)o;
                int nlx7 = (int)(obj.x/128);
                int nlz7 = (int)(obj.z/128);
                var sector = xy(nlx7, nlz7);
                {
                    // IS   THE   SECTOR   LOADED   ???
                    var inplay = new HashSet<>(InPlay);
                    if(inplay.contains(sector)){
                        // PLACE IT AND LOAD IT
                        // pasties.add(o);
                        // o.load();
                        pasties.remove(o);
                        o.clean();
                        i.remove();
                    }else{
                        neededNow.add(sector);
                    }
                }
            }
        }
        {
            var i = PUMP_PURGATORY.iterator();
            while(i.hasNext()){
                var o = i.next();
                var obj = (GameObject)o;
                int nlx7 = (int)(obj.x/128);
                int nlz7 = (int)(obj.z/128);
                var sector = xy(nlx7, nlz7);
                {
                    // IS   THE   SECTOR   LOADED   ???
                    var inplay = new HashSet<>(InPlay);
                    if(inplay.contains(sector)){
                        intoInventory(o.clone());
                        i.remove();
                    }else{
                        neededNow.add(sector);
                    }
                }
            }
        }
        {
            var i = neededGameObjects.iterator();
            while(i.hasNext()){
                var o = i.next();
                // System.out.println(o);
                // if(!(o instanceof FileNodule)) continue; // It's null I guess???? hmm
                var obj = (GameObject)o;
                int nlx7 = (int)(obj.x/128);
                int nlz7 = (int)(obj.z/128);
                var sector = xy(nlx7, nlz7);
                neededNow.add(sector);
                // System.out.println("adding sector... " + sector);
            }
        }
        {
            int ii = 0;
            var i = temporarilyNeededGameObjects.iterator();
            while(i.hasNext()){
                var o = i.next();
                if(ii++ > 40){
                    break;
                }

                // System.out.println(o);
                // if(!(o instanceof FileNodule)) continue; // It's null I guess???? hmm
                var obj = (GameObject)o;
                int nlx7 = (int)(obj.x/128);
                int nlz7 = (int)(obj.z/128);
                var sector = xy(nlx7, nlz7);
                neededNow.add(sector);
                // System.out.println("adding sector... " + sector);
            }
        }
        {
            int ii = 0;
            var i = temporarilyNeededTextNodules.keySet().iterator();
            while(i.hasNext()){
                var o = i.next();
                if(ii++ > 40){
                    break;
                }

                // System.out.println(o);
                // if(!(o instanceof FileNodule)) continue; // It's null I guess???? hmm
                var obj = (GameObject)o;
                int nlx7 = (int)(obj.x/128);
                int nlz7 = (int)(obj.z/128);
                var sector = xy(nlx7, nlz7);
                neededNow.add(sector);
                // System.out.println("adding sector... " + sector);
            }
        }
        // {
            
        //     MemoryUsage after = memoryMXBean.getHeapMemoryUsage();
        //     long usedDiff = after.getUsed() - before.getUsed();
        //     before = after;
        //     if(after.getUsed()/1_000_000 > 7000){

        //         System.out.println("HOPING 4 REDUCTION  @ " +after.getUsed()/1_000_000);
        //         System.gc();
        //         System.runFinalization();
        //     }
        // }
        // subtiming("sub-disk-2"); // 1
        nano_timings[timingsIndex][9] = System.currentTimeMillis() - nano_timings[timingsIndex][9];

        {
            // if(neededNow.size() > 25){
            //     System.out.println("submitting needednow of " + neededNow.size());
            // }
            var i = neededNow.iterator();
            while(i.hasNext()){
                // System.out.println("WTF");
                Long sector = i.next();
                boolean shouldLoad = !InPlay.contains(sector) &&
                    !NeededLoading.keySet().contains(sector) &&
                    !UnneededUnloading.keySet().contains(sector);
                if(shouldLoad){
                    MemoryUsage after = memoryMXBean.getHeapMemoryUsage();
                    long usedDiff = after.getUsed() - before.getUsed();
                    before = after;
                    // System.out.println("Should load @ " +after.getUsed()/1_000_000);
                    scenechange = true;
                    var t = new LoadRegion();
                    t.sector = sector;
                    var f = exe3.submit(t);
                    NeededLoading.put(sector, f);
                    taskMap.put(f, t);
                }
            }
        }
        // if(scenechange) System.out.println("\nSCENE CHANGE HAS OCCURRED ---- ");
        subtiming("sub-disk-2"); // 2
        nano_timings[timingsIndex][0] = System.currentTimeMillis();
        long timed = System.currentTimeMillis();
        { // nothing to do but process, right?
            var ii = NeededLoading.keySet().iterator();
            time("sector-load");
            while(ii.hasNext()){
                if(System.currentTimeMillis() - timed > 10){
                    // System.exit(0);
                    break;
                }
                Long sector = ii.next();
                Future f = NeededLoading.get(sector);
                
                if(f.isDone()){
                    
                    
                    // Iterating over the list
                    for (Long element : pleasenukeme) {
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
                        System.out.println("Doing the Parker special clean out");
                        System.out.println(element);
                        int flx7 = (int)(x(element)/128);
                        int flz7 = (int)(y(element)/128);
                        
                        pasties.queryAll();

                        for(int i = 0; i < pasties.size(); i++){
                            GameObject p = pasties.getAt(i);
                            // GameObject p = iterator.next();
                            if(p.getSectorKey() == xy(flx7, flz7)){
                                pasties.remove(p);
                            }
                        }
                    }
                    pleasenukeme.clear();
                    // System.out.println("Finished loading a sector " + _7812(x(sector)) +","+ _7812(y(sector)));
                    NeededLoading.remove(sector);
                    InPlay.add(sector);
                    // handle main-thread onload jobs
                    LoadRegion lr = (LoadRegion)taskMap.remove(f);
                    lr.toReturn.forEach(x ->{
                        x.load();
                        if(missingGameObjects != null){
                            boolean removed = missingGameObjects.remove(x);
                            if(removed){
                                System.out.println("Found one of the saught after files: " + missingGameObjects.size() + "  to go...");
                                missingGameObjects.stream().forEach(zzz->System.out.println(zzz));
                            }
                        }
                    });
                    textSheet.clearRepaunch();
                }
            }
            time("sector-load");
        }
        subtiming("sub-disk-2");
        {
            // System.out.println("in play ct is " + InPlay.size());
        }
        {
            // subtract neededNow and InPlay to come up with the loadOut group
            var inplay = new HashSet<>(InPlay);
            // System.out.println("Debug + " + inplay.size() + " " + neededNow.size());
            inplay.removeAll(neededNow);
            var leftover = inplay;
            // System.out.println("leftovers: I found: " + leftover.size());
            InPlay.removeAll(leftover); // they are no longer in-play
            for(var sector: leftover){
                var t = new SaveRegion();
                t.sector = sector;
                var f = exe3.submit(t);
                taskMap.put(f, t);
                UnneededUnloading.put(sector, f);
            }
        }
        subtiming("sub-disk-2");

        {
            long timed2 = System.currentTimeMillis();
            var ii = UnneededUnloading.keySet().iterator();
            while(ii.hasNext()){
                MemoryUsage after = memoryMXBean.getHeapMemoryUsage();
                long usedDiff = after.getUsed() - before.getUsed();
                before = after;
                // System.out.println("Should unload @ " +after.getUsed()/1_000_000);
                if(System.currentTimeMillis() - timed2 > 10){
                    // System.exit(0);
                    // System.out.println("here");
                    break;
                }
                Long sector = ii.next();
                Future f = UnneededUnloading.get(sector);
                if(f.isDone()){
                    // System.out.println("beep beep beep");
                    UnneededUnloading.remove(sector);
                    // handle cleaning on main thread
                    SaveRegion sr = (SaveRegion)taskMap.remove(f);
                    sr.cleanUpAfter.forEach(x -> x.clean());
                }
            }

        }
        nano_timings[timingsIndex][0] = System.currentTimeMillis()-nano_timings[timingsIndex][0];

        subtiming("sub-disk-2");

        outerLock = !NeededLoading.isEmpty() || !UnneededUnloading.isEmpty(); 
        time("sub-disk-2", false);
    }
    subtiming("draws"); // 1

    var zz = System.currentTimeMillis();
    time("impossible");
    doomguysheet.step(gridx, gridy, nx,ny,nz, nangle, chin); // must be called strictly once per loop
    doomguysheet2.step(gridx, gridy, nx,ny,nz, nangle, chin); // must be called strictly once per loop
    {
        Spriter.clickColors = 0xff_00;
        // fightSpriter.drawBillboard(1, 2000, 10, 2000, 1, 1, 0, 1, "player");
        Spriter.clickColors = 0;
    }
    

    fightSpriter.step(gridx, gridy, nx,ny,nz, nangle, chin); // must be called strictly once per loop
    time("impossible");
    nano_timings[timingsIndex][8] = System.currentTimeMillis() - nano_timings[timingsIndex][8];


    // scapeSpriter.step(gridx, gridy, nx,ny,nz, nangle, chin); // must be called strictly once per loop
    time("Scape ");
    nano_timings[timingsIndex][14] = System.currentTimeMillis();
    
    swapSpriter.flag = true;
    { // if SLOW DRIP DONE
        // swapSpriter.debug = true;
        //swapping maneuver should go here actually?

        // don't call until slow drip done
        // Don't call until slow drip done
        swapSpriter.step(gridx, gridy, nx,ny,nz, nangle, chin);
        // swapSpriter.debug = false;

    }


    nano_timings[timingsIndex][14] = System.currentTimeMillis() - nano_timings[timingsIndex][14];

    
    { //else
        // DO SLOW DRIP
        // actually... might need to wait until
    }
    swapSpriter.flag = false;
    var gg = System.currentTimeMillis();
    var dd = gg-zz;
    // if(dd > 10){
    //     System.out.println("Explicit issue with SwapSpriter " + dd);
    //     triggerPrint = true;
    // } 
    subtiming("draws"); // 2

    time("Scape ", false);
    time("EX:B");
    // time("skysigns");
    // for(GameObject go: skysigns){
    //     if(go instanceof Pasty){
    //         var p = (Pasty)go;
    //         if(p.step && p.sprite != null)
    //         p.sprite.step(gridx, gridy, nx,ny,nz, nangle, chin);
    //         if(p instanceof ThreeDumb){
    //             // System.out.println("AAAAARGGHH");
    //         }
    //     }
    // }
    // time("skysigns");
    time("pasties");
    nano_timings[timingsIndex][3] = System.currentTimeMillis();
    
    // pasties.queryAll();
    pasties.query2D(x-2048, x+2048, z-2048, z+2048);

    for(int i = 0; i < pasties.size(); i++){
        GameObject go = pasties.getAt(i);
        if(go instanceof Pasty){
            var p = (Pasty)go;
            if(p.step && p.sprite != null){
                if(!ghostSpriters.contains(p.sprite)){
                    p.sprite.step(gridx, gridy, nx,ny,nz, nangle, chin);
                }else{
                    // System.out.println("woot!" + ghostSpriters.size());
                    // System.exit(0);
                }
            }
        }
    }
    if(ghostAssignment){
        ghostAssignment = false;
        ghost_captured_x = nx;
        ghost_captured_y = ny;
        ghost_captured_z = nz;
        ghost_captured_chin = chin;
        ghost_captured_neck = nangle;
        var capture = new MonoTuple5<Double>(nx, ny, nz, nangle, chin);
        ghost_pos.addLast(capture);
    }
    
    {
        var i = ghost_subs.iterator();
        var i2 = ghost_pos.iterator();
        // if(i.size() != i.size()){
        //     System.out.println("Can't happen");
        //     System.exit(0);
        // }
        while(i.hasNext()){
            var ghostSpriters = i.next();
            var pos = i2.next();
            for(var idk: ghostSpriters){
                idk.step(gridx, gridy,
                    pos.x,
                    pos.y,
                    pos.z,
                    pos.a,
                    pos.b);
                            // System.out.println("woot!" + ghostSpriters.size());
                            // System.exit(0);
            }
        }
    }
    // System.out.println("Gsle" + ghostSpriters.size());
    // for(var idk: ghostSpriters){
    //     idk.step(gridx, gridy,
    //          ghost_captured_x,
    //          ghost_captured_y,
    //          ghost_captured_z,
    //          ghost_captured_neck,
    //          ghost_captured_chin);
    //                 // System.out.println("woot!" + ghostSpriters.size());
    //                 // System.exit(0);
    // }
    // time("pasties");
    // time("bobbies");
    long before_loop = System.currentTimeMillis();
    int budget = 10;
    for(var bobbies: invStore){
        for(GameObject go: bobbies){
            if(go instanceof Pasty){
                if(System.currentTimeMillis() - before_loop > budget){
                    break;
                }
                var p = (Pasty)go;
                if(p.step && p.sprite != null){
                    p.sprite.step(gridx, gridy, nx,ny,nz, nangle, chin);
                    
                }
            }
        }
    }
    nano_timings[timingsIndex][3] = System.currentTimeMillis() - nano_timings[timingsIndex][3];
    nano_timings[timingsIndex][7] = System.currentTimeMillis();
    nano_timings[timingsIndex][2] = System.currentTimeMillis();

    // time("bobbies");

    // subtiming("draws"); // 0

    {
        // actually pick the cubemap spriter here
        // cubemapSpriter = cubemapSpriters[Math.abs(color)%cubemapSpriters.length];
        // int color = Vor.getColor((int)x-1_000_000,(int)z-1_000_000);

        //  Actually here use
        // base seed AND x,y total location, to come up with 5,
        //

        // whats the calc for that fn?
        // map(int input)
        // {
        //     int input = x - 1_000_000;
        //     int result = input % 4096;
        //     int xx =  (result < 0) ? result + 4096 : result;
        // }
        // {
        //     int input = z - 1_000_000;
        //     int result = input % 4096;
        //     return (result < 0) ? result +
        //     int yy =  4096 : result;
        // }
        
        {
            // int result = input % 4096;
            // int input = x - 1_000_000;
            // int xx =  (result < 0) ? result + 4096 : result;
        }
        int xx = Swath.map2((int)x-1_000_000);
        // System.out.println(xx%10);
        cubemapSpriter = cubemapSpriters[xx%10];
    }

    if(false){
        {
            int[] lkp = new int[]{4, 5, 6, 7};
            double dist = 10000;
            for(int a = 0; a < 4; a++){
                for(int i = 0; i < 4; i++){
                    double an = Math.PI/2*a;
                    double px = dist*Math.cos(an)+x-1_000_000;
                    double pz = dist*Math.sin(an)+z-1_000_000;
                    double py = 0;
                    if(a == 1 || a == 3){
                        an += Math.PI;
                    }
                    if(i == 1 || i == 2){
                        px -= dist*Math.sin(an);
                        pz -= dist*Math.cos(an);
                    }else{
                        px += dist*Math.sin(an);
                        pz += dist*Math.cos(an);
                    }
                    if(i == 2 || i==3){
                        py-=dist;
                    }else{
                        py+=dist;
        
                    }
                    // int mode = 8;
                    // if(nearest != p)mode = 4;
                    cubemapSpriter.drawPoint(lkp[a], px, py, pz,  1, b, b, 55);
                }
            }
            {
                for(int i = 0; i < 4; i++){
                    double an = Math.PI/2*i+Math.PI/4+Math.PI;
                    double px = dist*Math.sqrt(2)*Math.cos(an)+x-1_000_000;
                    double pz = dist*Math.sqrt(2)*Math.sin(an)+z-1_000_000;
                    double py = 0;
                    py+=dist;
                    cubemapSpriter.drawPoint(1, px, py, pz,  1, b, b, 55);
                }
                for(int i = 0; i < 4; i++){
                    double an = Math.PI/2*(i+1)+Math.PI/4;
                    double px = dist*Math.sqrt(2)*Math.cos(an)+x-1_000_000;
                    double pz = dist*Math.sqrt(2)*Math.sin(an)+z-1_000_000;
                    double py = 0;
                    py-=dist;
                    cubemapSpriter.drawPoint(9, px, py, pz,  1, b, b, 55);
                }
            }
        }
        cubemapSpriter.step(gridx, gridy, nx,ny,nz, nangle, chin);

    }
    subtiming("draws"); // 0

    time("EX:B");

    // String playlength = "" +(int)(10*1/(lastClock/1_000_000_000.0));
    // String playlength = df.format(1/(lastClock/1_000_000_000.0));
    time("EX:A");


    {
        lastClock= System.nanoTime() -lastClock ;
        double fps = 1/(lastClock/1_000_000_000.0);
        fpsAgr.addFirst(fps);
        if(fpsAgr.size() > 100) fpsAgr.removeLast();
        Double all = 0.0;
        double avg = 0;
        for(var a: fpsAgr){
            all+=a;
        }
        
        if(fpsAgr.size() != 0)avg = all/fpsAgr.size();
        
        String playlength = df2.format(fps);
        
        MemoryUsage after = memoryMXBean.getHeapMemoryUsage();
        long usedDiff = after.getUsed() - before.getUsed();
        before = after;
        textSheet2.drawString("mem:"+after.getUsed()/1_000_000, 0.8, 0.9, 0, 0.03, 255, 255, 0);
        // textSheet2.drawString(numberFormat.format(usedDiff)+":"+numberFormat.format(after.getUsed()) + df2.format(avg), 0.8, 0.9, 0, 0.03, 255, 255, 0);
        textSheet2.drawString("fps: " + df2.format(avg), 0.8, 0.8, 0, 0.03, 255, 255, 0);
        // textSheet2.drawString("zone: " +_7812((int)(x/128)) + "," + _7812((int)(z/128)), 0.8, 0.8, 0, 0.03, 255, 255, 0);
        // textSheet2.drawString("tris:  " + df3.format(Spriter.ALL_CAUSE_TRIANGLES), 0.8, 0.6, 0, 0.03, 255, 255, 0);
        // textSheet2.drawString("steps: "+ df3.format(Spriter.ALL_CAUSE_STEP), 0.8, 0.5, 0, 0.03, 255, 255, 0);
        // if(F8_MODE)
        //     textSheet2.drawString("Cycle Everything", 0.8, 0., 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("TRACKERS: "+ df3.format(ARBITRARY_TRACKER_A)+" , "+ df3.format(ARBITRARY_TRACKER_B), 0.8, 0.4, 0, 0.03, 255, 255, 0);
        // textSheet2.drawString(getTimings(), 0.6, 0.3, 0, 0.02, 255, 255, 0);
        // textSheet.drawString("tir: "+ df2.format(Spriter.ALL_CAUSE_STEP), 0.8, 0.5, 0, 0.03, 255, 255, 0);
        Spriter.ALL_CAUSE_TRIANGLES = 0;
        Spriter.ALL_CAUSE_STEP = 0;
        ARBITRARY_TRACKER_A=0;
        ARBITRARY_TRACKER_B=0;
        lastClock = System.nanoTime();  
    }
    subtiming("draws"); // 0

    time("EX:A");
    time("textstep");
    if(pumpjackmode){
        textSheet2.drawString("pumpjack mode", 0.8, 0.95, 0, 0.03, 255, 255, 0);

    }
    if(SEARCHER_TOOLTIP != null){
        textSheet2.drawString(SEARCHER_TOOLTIP, 0, -0.5, 0, 0.18, 255, 255, 0);
    }
    // if(ClipboardMonitor.insertionMode)
    //     textSheet2.drawString("insertion mode", 0.8, 0.7, 0, 0.03, 255, 255, 0);
    // if(!FINE_MODE)
    //     textSheet2.drawString("slow mode", 0.8, 0.6, 0, 0.03, 255, 255, 0);
    // if(stage1ukeyportal != null)
    //     textSheet2.drawString("STAGE 1 U_KEY (esc to abort)", 0.8, 0.5, 0, 0.03, 255, 0, 0);
    // if(neededTenPortals != null)
    //     textSheet2.drawString("Marked Portal Box (esc to abort)", 0.8, 0.5, 0, 0.03, 255, 0, 0);
    // if(IS_FLAGGED_AS_BACKUP){
    //     textSheet2.drawString("THIS IS A BACKUP MAP", 0.6, 0.4, 0, 0.05, 255, 0, 0);
    //     textSheet2.drawString("FOR RETRIEVAL OF LOST DATA", 0.6, 0.3, 0, 0.05, 255, 0, 0);
    //     textSheet2.drawString("PRESS F6 TO PROMOTE TO FULL WORLD", 0.6, 0.2, 0, 0.05, 255, 0, 0);
    // }
    {
        int peak = 0;
        int c7volume = 0;
        int c6volume = 0;
        int c10volume = 0;
        int c11volume = 0;
        int c12volume = 0;
        int c5volume = 0;
        int c0volume = 0;
        int c3volume = 0;
        int c14volume = 0;
        int peakvalue = 0;
        int trough = 0;
        int troughvalue = Integer.MAX_VALUE;
        int sum = 0;
        for(int i = 0; i < fps_timings.length; i++){
            // fightSpriter.drawSprite(29, 0.02, 0.02*fps_timings[i], 1, 0.03, 0.5+0.02*i, 0.5,  1);
            // fightSpriter.drawSprite(29, 0.02, 0.02*fps_timings[i], 1, 0.03, 0.5+0.02*i, 0.5,  1);
            if(i != timingsIndex){

                sum += fps_timings[i];
                c7volume += nano_timings[i][7];
                c6volume += nano_timings[i][6];
                c10volume += nano_timings[i][10];
                c11volume += nano_timings[i][11];
                c12volume += nano_timings[i][12];
                c5volume += nano_timings[i][5];
                c0volume += nano_timings[i][0];
                c3volume += nano_timings[i][3];
                c14volume += nano_timings[i][14];
                
                if(fps_timings[i] > peakvalue){
                    peak = i;
                    peakvalue = fps_timings[i];
                    // peak = fps_timings[i];
                }
                if(fps_timings[i] < troughvalue){
                    // trough = fps_timings[i];
                    trough = i;
                    troughvalue = fps_timings[i];
                }
                fightSpriter.drawSprite(29, 0.5+0.002*i,0.5, 0,
                0.002, 0.002*fps_timings[i],0, 1);
            }
        }
        int lagvolume = sum - troughvalue*100; // 
        // textSheet2.drawString("peak:" + fps_timings[peak], 0.8, 0.5, 0, 0.03, 255, 255, 0);
        // textSheet2.drawString("trough:" + fps_timings[trough], 0.8, 0.53, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit0_peak:" + nano_timings[peak][0], 0.8, 0.56, 0, 0.03, 255, 0, 0);
        // // textSheet2.drawString("crit0_trough:" + nano_timings[trough][0], 0.8, 0.59, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit2_peak:" + nano_timings[peak][2], 0.8, 0.47, 0, 0.03, 255, 0, 0);
        // // textSheet2.drawString("crit2_trough:" + nano_timings[trough][2], 0.8, 0.44, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit3_peak:" + nano_timings[peak][3], 0.8, 0.41, 0, 0.03, 255, 0, 0);
        // // textSheet2.drawString("crit3_trough:" + nano_timings[trough][3], 0.8, 0.38, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit4_peak:" + nano_timings[peak][4], 0.8, 0.35, 0, 0.03, 255, 0, 0);
        // // textSheet2.drawString("crit4_trough:" + nano_timings[trough][4], 0.8, 0.32, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit5_peak:" + nano_timings[peak][5], 0.8, 0.29, 0, 0.03, 255, 0, 0);
        // // textSheet2.drawString("crit5_trough:" + nano_timings[trough][5], 0.8, 0.26, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit6_peak:" +     nano_timings[peak][6], 0.8, 0.23, 0, 0.03, 255, 255, 0);
        // // textSheet2.drawString("crit6_trough:" + nano_timings[trough][6], 0.8, 0.20, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit7_tgen:" +     nano_timings[peak][7], 0.8, 0.17, 0, 0.03, 255, 255, 0);
        // // textSheet2.drawString("crit7_trough:" + nano_timings[trough][7], 0.8, 0.14, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit8_peak:" +     nano_timings[peak][8], 0.8, 0.11, 0, 0.03, 255, 255, 0);
        // // textSheet2.drawString("crit8_trough:" + nano_timings[trough][8], 0.8, 0.08, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit9_peak:" +     nano_timings[peak][9], 0.8, 0.05, 0, 0.03, 255, 255, 0);
        // // textSheet2.drawString("crit9_trough:" + nano_timings[trough][9], 0.8, 0.02, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("alldraws10:" +     nano_timings[peak][10], 0.8, -0.01, 0, 0.03, 255, 255, 0);
        // // textSheet2.drawString("crit10_trough:" + nano_timings[trough][10], 0.8, -0.04, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("crit11_peak:" +     nano_timings[peak][11], 0.8, -0.07, 0, 0.03, 255, 255, 0);
        // // textSheet2.drawString("crit10_trough:" + nano_timings[trough][10], 0.8, -0.04, 0, 0.03, 255, 0, 0);
        // textSheet2.drawString("(3d)12_peak:" +     nano_timings[peak][12], 0.8, -0.13, 0, 0.03, 255, 255, 0);
        // textSheet2.drawString("crit13_swpB:" +     nano_timings[peak][13], 0.8, -0.19, 0, 0.03, 255, 255, 0);
        // textSheet2.drawString("swap14_peak:" +     nano_timings[peak][14], 0.8, -0.25, 0, 0.03, 255, 255, 0);


        // textSheet2.drawString("lag volume:" + lagvolume                 , 0.8, -0.28, 0, 0.03, 0, 255, 128);
        // textSheet2.drawString("c0 volume:" + c0volume                 , 0.8, -0.31, 0, 0.03, 0, 255, 128);
        // textSheet2.drawString("c6 volume:" + c6volume                 , 0.8, -0.34, 0, 0.03, 0, 255, 128);
        // textSheet2.drawString("c10 volume:" + c10volume                 , 0.8, -0.43, 0, 0.03, 0, 255, 128);
        // textSheet2.drawString("c5 volume:" + c5volume                 , 0.8, -0.40, 0, 0.03, 0, 255, 128);
        // textSheet2.drawString("c12 volume:" + c12volume                 , 0.8, -0.37, 0, 0.03, 0, 255, 128);
        // textSheet2.drawString("c3 volume:" + c3volume                 , 0.8, -0.46, 0, 0.03, 0, 255, 128);
        textSheet2.drawString("c14 volume:" + c14volume                 , 0.8, -0.49, 0, 0.03, 0, 255, 128);
        textSheet2.drawString(""+g_cx + " " + g_cy + " " + g_lkp     , 0.8, -0.52, 0, 0.03, 0, 255, 128);
        textSheet2.drawString("+"     , 0.002,0.002, 0, 0.03, 0, 255, 128);
        // textSheet2.drawString("c6 volume:" + c6volume                 , 0.8, -0.31, 0, 0.03, 0, 255, 128);

    }
    {
        int i = 0;
        long time_diff = System.currentTimeMillis() - statement_bump_stamp;
        // System.out.println(time_diff);
        double colormult = Math.min(1000, 5000 - time_diff)/1000.0;
        if(time_diff < 5000){
            for(var e: statements){
                // System.out.println(colormult); 
                textSheet2.drawString(e, -0.6,0.8-i*0.03, 0, 0.03, 255*colormult, 255, 0);
                i++;
                if(i == 20) break;
            }
        }
    }
    // {
    //     double ang =  nangle - Math.PI/12 + Math.PI/6 * 0 ; 
    //     textSheet2.drawString("ANG:" + ang, 0, 0.1, 0, 0.1, 255, 255, 0);
    //     textSheet2.drawString("chin:" + parkervalue, 0.1, 0, 0, 0.1, 255, 255, 0);
    // }
    // {
    //     int c = swaths[9].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000);
    //     int c2 = swaths[10].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000);
    //     int c3 = swaths[11].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000);
    //     textSheet2.drawString(c + " " + c2 + " " + c3, 0,0, 0, 0.05, 255, 0, 0);
    // }
    // {
    //     int c  = (int)((100*swaths[9].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff);
    //     int c2 = (int)((100*swaths[10].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff);
    //     int c3 = (int)((100*swaths[11].getColorFromCompletedMap((int)x-1_000_000,(int)z-1_000_000)-0xff_00_00_00)*1.0/0xff_ff_ff);
    //     textSheet2.drawString(c + " " + c2 + " " + c3, 0,0, 0, 0.05, 255, 0, 0);
    // }
    // {
    //     int a = Swath.readmex1;
    //     int bb = Swath.readmey1;
    //     int c = Swath.readmex2;
    //     int d = Swath.readmey2;
    //     textSheet2.drawString(a + " " + bb + " " + c + " "+ d, 0,0.2, 0, 0.05, 255, 0, 0);
    // }

    // if(GameObject.DONT_UNLOAD_MODE_HARD_OVERRIDE)
    //     textSheet2.drawString("DONT UNLOAD MODE: on", 0.8, 0.85, 0, 0.07, 255, 0, 0);
    // if(lock())
    //     textSheet2.drawString("lock", 0.8, 0.85, 0, 0.07, 255, 0, 0);
     nano_timings[timingsIndex][2] = System.currentTimeMillis();
    textSheet.step(gridx, gridy, nx,ny,nz, nangle, chin);
    textSheet3.step(gridx, gridy, nx,ny,nz, nangle, chin);

    textSheet2.step(gridx, gridy, nx,ny,nz, nangle, chin);
    nano_timings[timingsIndex][2] = System.currentTimeMillis() - nano_timings[timingsIndex][2];


    // {
    //     mountainTemp.drawSprite(0,
    //     0.0,
    //     0.0,
    //     -0.4, // move on up 
    //     2.0,
    //     2.0,0.0, 1);
    //     mountainTemp.step(gridx, gridy, nx,ny,nz, nangle, chin);
    // }
    {
        if(depthStack.size() >= 0){
            long untouched = last - Input.lastTouch;
            double unt = untouched/1_000_000_000.0;
            if(!F8_MODE) unt = 0;
            double actual = Math.max(0,unt-1.5 /*seconds */);
            // System.out.println("Drawing... " + depthStack.size());
            if(depthStack.size() == 0 && unt >= 6){
                                AUTO_CONTEXT_TIMER = Integer.MIN_VALUE;
                                presentation_cycle_counter = 0;

                    System.out.println("FUCKING KILL ME");
                    dumpToFirstGigaStack();
                                    stacks.addFirst(stacks.removeLast());
                                    loadValuesFromFirstGigaStack();
                                    
                                    Input.lastTouch = System.nanoTime();
            }


            for(int i = 0; i < depthStack.size(); i++){
                // if this doesn't work I'll just go the other way
                var val = depthStack.get(i);
                if(val.x != null){
                    val.x.uishrinker = 1.0f;
                    // double cutout = (depthStack.size()+1)*(0.5-Math.cos(3 * actual / (depthStack.size()+1))/2);
                    double unt_state = (0.5-Math.cos(3 * actual / (depthStack.size()+1))/2)*depthStack.size();
                    // System.out.println("unt "+unt_state);
                    double this_state = Math.max(0,Math.min(1,unt_state - i));
                    double cutin = 1/Math.pow(cutoutSize, (depthStack.size()-i));
                    double cutout = (1-cutin);
                    // System.out.println(this_state);
                    // cutout += this_state*cutin; // untouched blend/cutin effect
                    // System.out.println("AND NOW... " + i + " " + cutout);
                    // var val = depthStack.peek();
                    val.x.cutout = (float)(1-cutout); // is it some amount of 1?

                    int chosen = (int)((actual*3)%(depthStack.size()+1));
                    // System.out.println("Chosen " + chosen);
                    // if(!CYCLING_MODE) chosen = 0; // always 0 
                    if(chosen == 0 || chosen == i || chosen == 1 || true){
                        if( presentation_last_imprint != chosen){
                            presentation_cycle_counter++;
                            System.out.println("DOING IT DOING IT " + presentation_cycle_counter);
                            if(presentation_cycle_counter >= depthStack.size()*6){
                                presentation_cycle_counter = 0;
                                AUTO_CONTEXT_TIMER = Integer.MIN_VALUE;
                                if(stacks.size() > 1 || true){ // cupid is so dumb
                                    dumpToFirstGigaStack();
                                    stacks.addFirst(stacks.removeLast());
                                    loadValuesFromFirstGigaStack();
                                    
                                    Input.lastTouch = System.nanoTime(); // reset it
                                }
                            }
                        }
                        presentation_last_imprint = chosen;
                        // System.out.println("HELP PARKER PARKER PARKER PARKER PARKER " + chosen + " " + actual);
                        if(chosen > i ){
                            // System.out.println("HELP PARKER  ");
                            val.x.cutout = 0;
                        }
                        val.x.drawSprite(0,
                        0.0,
                        0.0,
                        -0.1-(i)*0.03, // move on up 
                        2.0,
                        2.0,0.0, 1);
                        val.x.step(gridx, gridy, nx,ny,nz, nangle, chin);
                    }
                }else{
                }
            }
        }
    }
}

    {
        // 
        double nx = x-1_000_000;
        double nz = z-1_000_000;

        double ny = (newwigglemode ? wiggleItem.ydisp : y) + dispy;
        double nangle = neck;
        glClear(GL_DEPTH_BUFFER_BIT);
        Spriter.registerDepthClear();
        // fightSpriter.drawBillboard(1, 2000, 10, 2000, 1, 1, 0, 1, "player");
        {
            Spriter.clickColors = 0xff_00;
            int j = 0;
            pasties.query2D(x-2000, x+2000, z-2000, z+2000);
            for(int i = 0; i < pasties.size(); i++){
                if(dispy <= 100){
                    // {
                    //     double tx = debugTriangle[0]-1_000_000;
                    //     double ty = debugTriangle[1];
                    //     double tz = debugTriangle[2]-1_000_000;
                    //     // draw
                    //     slimeText.drawBillboardWithOffset(Spriter.convertCharacter('A'), tx, ty, tz, 0, 0.2f, 5f, 5f, 0, 1, 
                    //                 0,0,0,
                    //                 0,0,0, 0,0,0, 0,0,0,
                    //                 //color
                    //                 // r,g,bb,
                    //                 128,255,64,
                    //                 0,0,0, 0,0,0, 0,0,0,
                    //                 null);
                    // }
                    // {
                    //     double tx = debugTriangle[3]-1_000_000;
                    //     double ty = debugTriangle[4];
                    //     double tz = debugTriangle[5]-1_000_000;
                    //     // draw
                    //     slimeText.drawBillboardWithOffset(Spriter.convertCharacter('B'), tx, ty, tz, 0, 0.2f, 5f, 5f, 0, 1, 
                    //                 0,0,0,
                    //                 0,0,0, 0,0,0, 0,0,0,
                    //                 //color
                    //                 // r,g,bb,
                    //                 128,255,64,
                    //                 0,0,0, 0,0,0, 0,0,0,
                    //                 null);
                    // }
                    // {
                    //     double tx = debugTriangle[6]-1_000_000;
                    //     double ty = debugTriangle[7];
                    //     double tz = debugTriangle[8]-1_000_000;
                    //     // draw
                    //     slimeText.drawBillboardWithOffset(Spriter.convertCharacter('C'), tx, ty, tz, 0, 0.2f, 5f, 5f, 0, 1, 
                    //                 0,0,0,
                    //                 0,0,0, 0,0,0, 0,0,0,
                    //                 //color
                    //                 // r,g,bb,
                    //                 128,255,64,
                    //                 0,0,0, 0,0,0, 0,0,0,
                    //                 null);
                    // }

                    if(!data_sizes_mode)break;
                    GameObject pp = pasties.getAt(i);
                    {
                        var p = pp;
                        double dx = p.x-x;
                        double dy = p.z-z;
                        double dist = Math.sqrt(dx*dx+dy*dy);
                        double px = p.x-1_000_000;
                        double pz = p.z-1_000_000;
                        double py = p.ydisp+5/1;
                        if(dist < 500){
                            j++;
                            if(j < 50000){

                                

                                // Spriter.clickColors = p.hashCode()&0xff_ff;
                                // int rgb = p.c1;
                                // int bb = (rgb>>>0)&255;
                                // int g = (rgb>>>8)&255;
                                // int r = (rgb>>>16)&255;
    
                                // // slimeSpriter.drawBillboard(1, px, py, pz, 1, 1, 0, 1, "player");
                                // slimeSpriter.drawBillboardWithOffset(1, px, py, pz, 10f, 0.2f, 1, 1, 0, 1, 
                                // 255,255,255,
                                // 0,0,0, 0,0,0, 0,0,0,
                                // //color
                                // r,g,bb,
                                // 0,0,0, 0,0,0, 0,0,0,
                                // null);
    
                                if(p.isWritable()){
                                    // draw the text for it
                                    // String word = p.getWord();
                                    // String word = p.getWord();

                                    ParkerSizer sizer = new ParkerSizer();
                                    GameObject.marshal(sizer, p, true);
                                    long theSize = sizer.getSize();
                                    String word = theSize+"B";
                                    int r5 = 255;
                                    int g5 = 255;
                                    int b5 = 0;
                                    if(theSize > 1_000_000){
                                        int uknowit = (int)(theSize/100_000)%10;
                                        theSize/=1_000_000;
                                        word = theSize+"."+uknowit+"MB";
                                        r5 = 0;
                                        g5 = 128;
                                        b5 = 255;
                                    }
                                    else if(theSize > 1000){
                                        int uknowit = (int)(theSize/100)%10;
                                        theSize/=1000;
                                        word = theSize+"."+uknowit+"KB";
                                        r5 = 255;
                                        g5 = 255;
                                        b5 = 255;
                                    }

                                    if(word != null){
                                        
                                        char[] cc = word.toCharArray();
                                        for(int ii = 0; ii < cc.length; ii++){
                                            char c = cc[ii];
                                            slimeText.drawBillboardWithOffset(Spriter.convertCharacter(c), px, py, pz, 4.5f*ii, 0.2f, 5f, 5f, 0, 1, 
                                                0,0,0,
                                                0,0,0, 0,0,0, 0,0,0,
                                                //color
                                                // r,g,bb,
                                                r5,g5,b5,
                                                0,0,0, 0,0,0, 0,0,0,
                                                null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }else{
                    GameObject pp = pasties.getAt(i);
                    if(pp instanceof Searcher){
                        var p = (Searcher) pp;
                        double dx = p.x-x;
                        double dy = p.z-z;
                        double dist = Math.sqrt(dx*dx+dy*dy);
                        double px = p.x-1_000_000;
                        double pz = p.z-1_000_000;
                        double py = p.ydisp+5/1;
                        if(dist > 100 && dist < 2000){
                            j++;
                            if(j < 50){
                                // Spriter.clickColors = 0xff_00;
                                Spriter.clickColors = p.hashCode()&0xff_ff;
                                int rgb = p.c1;
                                int bb = (rgb>>>0)&255;
                                int g = (rgb>>>8)&255;
                                int r = (rgb>>>16)&255;
    
                                // slimeSpriter.drawBillboard(1, px, py, pz, 1, 1, 0, 1, "player");
                                slimeSpriter.drawBillboardWithOffset(1, px, py, pz, 10f, 0.2f, 20, 20, 0, 1, 
                                255,255,255,
                                0,0,0, 0,0,0, 0,0,0,
                                //color
                                r,g,bb,
                                0,0,0, 0,0,0, 0,0,0,
                                null);
    
                                {
                                    // draw the text for it
                                    String word = p.getWord();
                                    if(word != null){
                                        char[] cc = word.toCharArray();
                                        for(int ii = 0; ii < cc.length; ii++){
                                            char c = cc[ii];
                                            slimeText.drawBillboardWithOffset(Spriter.convertCharacter(c), px, py, pz, 10f*ii, 0.2f, 20, 20, 0, 1, 
                                                255,255,255,
                                                0,0,0, 0,0,0, 0,0,0,
                                                //color
                                                r,g,bb,
                                                0,0,0, 0,0,0, 0,0,0,
                                                null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spriter.clickColors = 0;
        }
        
        slimeSpriter.step(gridx, gridy, nx,ny,nz, nangle, chin); // must be called strictly once per loop
        slimeText.step(gridx, gridy, nx,ny,nz, nangle, chin); // must be called strictly once per loop
    }

    {
        double nx = x-1_000_000;
        double nz = z-1_000_000;

        double ny = (newwigglemode ? wiggleItem.ydisp : y) + dispy;
        double nangle = neck;
        glClear(GL_DEPTH_BUFFER_BIT);
        //

        if(nearest instanceof Pasty){
            // draw pasty in the middle of the screen,

            // because of the step call, the draw call, it should
            // work fine. I can just use the step function again
            var pasty = (Pasty)nearest;
            if(pasty.sprite != null){
                var s = pasty.sprite;
                s.clearRepaunch();
                // s.repaunch = false;
                s.drawct = 0;
                s.offset3 = 0;
                s.mumu = 0;
                var temp = s.sideLength;
                s.sideLength = 1;
                var aspect = buggaX*1.0f/buggaY;
                s.drawSprite(0, 0, panvertical*zoomScrollAdjustment, 0, 1f*zoomScrollAdjustment*pasty.whrat/aspect, 1f*zoomScrollAdjustment, 0, 1);
                // s.drawBillboard(0, 0, 0, 1, 0, 0, 100f, 100f, 0, 1, false);
                s.step(gridx, gridy, nx,ny,nz, nangle, chin);
                s.sideLength = temp;
                // System.exit(0);
            }
        }

    }
    {

    subtiming("draws"); // 0
    nano_timings[timingsIndex][7] = System.currentTimeMillis() - nano_timings[timingsIndex][7];
    if(Spriter.ClickTrack.mode)
        if(Main.focused)glfwSwapBuffers(window);
    
    glClearColor(1f, 1f, 1f, 1.0f);
    glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
    Spriter.secretFinalClickCheckCall();
    if(!Spriter.ClickTrack.mode)
        if(Main.focused)glfwSwapBuffers(window);
    }
{
    
    // glReadBuffer(GL_BACK);
    
    // glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
    // // System.out.println("snapping " + Mouse.mx + " " + Mouse.my);
    // int width = buggaX;
    // int height = buggaY;
    // {
    //     // int x =Mouse.mxi; 
    //     // int y =Mouse.myi; 
    //     int x =width/2; 
    //     int y =height/2; 
    //     if(x >= 0 && y >= 0 && x < width && y < height){
    //         // p(x + ", " + y);
    //         y = (height-y-1);
    //         int i = (x + (width * y)) * 4; //offset??
    //         int r = bb.get(i) & 0xFF;
    //         int g = bb.get(i + 1) & 0xFF;
    //         int b = bb.get(i + 2) & 0xFF;
    //         int lkp = ((r<<0)|(g<<8)|(b<<16));
    //         System.out.println("ALERT ALERT ALERT");
    //         System.out.println("LOOKUP WAS lkp " + lkp);
    //         System.exit(0);
    //         // Object o = Spriter.ClickTrack.lookup(lkp);
    //         // if(o != null){
    //         //     slime = o;
    //         //     // p("color is "+ r + " " + g + " " + b);
    //         //     // p("num is "+ lkp);
    //         //     // p("xy is " + x(l) + " " + y(l));
    //         //     // Spriter.ClickTrack.targetx = (int) x(l);
    //         //     // Spriter.ClickTrack.targety = (int) y(l);
    //         // }else{
    //         //     slime = null;
    //         // }
    //     }
    // }
}
// System.out.println("first loop complete");
// System.exit(0);

time("gugga");
subtiming("all-time"); // 4
nano_timings[timingsIndex][13] = System.currentTimeMillis();

{
    var zz = System.currentTimeMillis();

    var sub1 = System.currentTimeMillis() - zz;
    // var sub2 = System.currentTimeMillis() - zz;
    glfwPollEvents();
    var sub3 = System.currentTimeMillis() - zz;


    var gg = System.currentTimeMillis();
    var dd = gg-zz;
    // if(dd > 10){
    //     System.out.println("SWAP BUFFERS EXPLICIT " + dd + " " + sub1 + " " + sub3);
    //     triggerPrint = true;
    // } 
}

    subtiming("draws"); // 0

time("gugga");
time("draws", false);
    subtiming("all-time"); // 5


subtiming("draws"); // 0

    if(FLICKER_GRAB_CATCH_VALUE != null){
        // should be completely    
        glReadBuffer(GL_FRONT);
        ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
        glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
        var bbi = bb.asIntBuffer();
        int[] ia = new int[buggaX*buggaY];
        bbi.get(ia);
        ia = convertToARGBAndFlip(ia,buggaX);
        var s = new Spriter(ia, buggaX, buggaY, 1, 4);
        FLICKER_GRAB_CATCH_VALUE.x = s;
        FLICKER_GRAB_CATCH_VALUE = null;
        if(FLICKER_AFTER_PORTAL != null){
            FLICKER_AFTER_PORTAL.activate();
            FLICKER_AFTER_PORTAL = null;
        }
        Spriter.disable2d = 0.0F;
        if(shouldJump){
            shouldJump = false;
            {   // One of the most priceless couple of lines of code ever.
                // Main.chin   += Math.random()*2*Math.PI;
                // Main.neck   += Math.random()*2*Math.PI;
                // double kk = Math.cos(Math.random()*Math.PI);
                // double rad = Math.random()*300+100;
                // Main.x      += Math.cos(kk)*(rad);
                // Main.z      += Math.sin(kk)*rad;
            }
        }
        // bb.clean();
    }
    subtiming("all-time"); // 6
    
    time("all-time",false);
    // timingsMap.clear();
    // subtimingsMap.clear();
    long final_time = System.currentTimeMillis() - allTimeTest;
    // if(final_time >= 32)
    //     System.out.println("Backup proof test " + final_time);

// System.out.println("EEEEEEEEE FU FU ");
nano_timings[timingsIndex][1] = System.currentTimeMillis() - nano_timings[timingsIndex][1];
nano_timings[timingsIndex][5] = System.currentTimeMillis() - nano_timings[timingsIndex][5];
nano_timings[timingsIndex][6] = System.currentTimeMillis() - nano_timings[timingsIndex][6];
nano_timings[timingsIndex][13] = System.currentTimeMillis() - nano_timings[timingsIndex][13];

// end while
}

// try{
//     exe.awaitTermination(1l, TimeUnit.HOURS);
// }catch(Exception e){e.printStackTrace();}
if(IS_FLAGGED_AS_BACKUP){
    System.out.println("not saving since this is a backup map");

    System.exit(0);
}
    addStatement("Exiting. Please wait...");

    System.out.println("saving NEW AGE");

    try {
        // Create a FileWriter object to write to the file "Latest.txt"
        FileWriter writer = new FileWriter("Latest.txt", false); // 'true' to append to file, 'false' or omitted to overwrite

        long playtime = pastcum  + ((System.currentTimeMillis() - startM));
        // Write a value to the file
        writer.write(System.currentTimeMillis() + "\n"); // Replace "Your value here" with the actual value you want to write
        writer.write(playtime + "\n"); // Replace "Your value here" with the actual value you want to write
        writer.write("false" + "\n"); // Replace "Your value here" with the actual value you want to write

        // Close the FileWriter
        writer.close();
    } catch (Exception e) {
        // Print the stack trace if an exception occurs
        e.printStackTrace();
    }
    if(Main.focused){
        glReadBuffer(GL_FRONT);
        ByteBuffer bb = ByteBuffer.allocateDirect(buggaX*buggaY*4);
        glReadPixels(0, 0, buggaX, buggaY, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, bb);
        var bbi = bb.asIntBuffer();
        int[] ia = new int[buggaX*buggaY];
        bbi.get(ia);
        ia = convertToARGBAndFlip(ia,buggaX);
        int width = buggaX;
        int height = buggaY;
        double fivepoint3 = 1.6/0.3;
        double ihave = width*1.0/height;
        int desireheight = (int)(width /fivepoint3);
        if(desireheight > height){
            desireheight = height;
        }
        BufferedImage image = new BufferedImage(width, desireheight, BufferedImage.TYPE_INT_ARGB);
        int inset = (height-desireheight)/2;
        int pixelIndex = width * inset; // the inset
        for (int y = 0; y < desireheight; y++) {
            for (int x = 0; x < width; x++) {
                image.setRGB(x, y, ia[pixelIndex]);
                pixelIndex++;
            }
        }
        try {
            File output = new File("cover2sliver.png");
            ImageIO.write(image, "png", output);
            System.out.println("Image saved as 'cover2sliver.png'");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // bb.clean();
    }
    // foreach long in hashset,
    // swap the filenames appropriately

    var ii = touchedSet.iterator();
    while(ii.hasNext()){
        Long key = ii.next();
        int lx7 = x(key);
        int lz7 = y(key);
        String strOld = "./"+folder+"/" + "sector-x" + lx7 + "z" +lz7 + "-TOUCHED";
        String strNew = "./"+folder+"/" + "sector-x" + lx7 + "z" +lz7 + "";
        // RENAME RENAME
        try {
            Path sourcePath = Paths.get(strOld);
            Path destinationPath = Paths.get(strNew);
                // Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            // }
            // Files.move(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
            // }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    {
        {
            int nlx7 = (int)(x/128);
            int nlz7 = (int)(z/128);
            {
                
                HashSet<Long> incoming = new HashSet<Long>();
                for(int i = 0; i < sside*sside; i++){
                    incoming.add(xy(nlx7+i/sside-sside/2, nlz7+i%sside-sside/2));
                }
                // for(int i = 0; i < 25; i++){
                //     if((i/5 == 0 && (i%5==0 ||i%5==4)) || (i/5 == 4 && (i%5==0 ||i%5==4)))continue;
                //     incoming.add(xy(nlx7+i/5-2, nlz7+i%5-2));
                // }
                HashSet<Long> toSave = (HashSet<Long>)incoming.clone();
                // toSave.removeAll(incoming);
                for(var sector: toSave){
                    int lx7 = x(sector);
                    int lz7 = y(sector);
                    String str = "sector-x" + lx7 + "z" +lz7;
                    ParkerBuffer pb = new ParkerBuffer(true, "./"+folder+"/"+str);
                    
                    pasties.queryAll();
                    {
                        int psize = pasties.gathermeindex;
                        GameObject[] gatherme  = pasties.gatherme;
                        int fromIndex = 0; // start from the second element (index 1)
                        int toIndex = psize;
                        Arrays.sort(gatherme, fromIndex, toIndex, new Comparator<GameObject>() {
                            // @Override
                            public int compare(GameObject p1, GameObject p2) {
                                if (p1.x != p2.x) {
                                    return Double.compare(p1.x, p2.x);
                                } else {
                                    return Double.compare(p1.z, p2.z);
                                }
                            }
                        });
                    }
                    // for(var e: pasties){
                    for(int i = 0; i < pasties.size(); i++){
                        GameObject e = pasties.getAt(i);

                        // old saves
                        int flx7 = (int)(e.x/128);
                        int flz7 = (int)(e.z/128);
                        //get all pasties in sector
                        //save sector
                        if(flx7 == lx7 && flz7 == lz7){
                            try{
                                e.sectorMarshal(pb);
                            }catch(Exception ee){
                                p("sector_error caught error for sector " + _7812(flx7) + ":"+_7812(flz7));
                            }
                        }
                    }
                    pb.finalizeWritingFile();
                }
            }
        }

        
        //save
        dumpToFirstGigaStack();
        marshal();

        class a1{


            private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
                if (fileToZip.isHidden()) {
                    return;
                }
                if (fileToZip.isDirectory()) {
                    if (fileName.endsWith("/")) {
                        zipOut.putNextEntry(new ZipEntry(fileName));
                        zipOut.closeEntry();
                    } else {
                        zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                        zipOut.closeEntry();
                    }
                    File[] children = fileToZip.listFiles();
                    for (File childFile : children) {
                        zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
                    }
                    return;
                }
                FileInputStream fis = new FileInputStream(fileToZip);
                ZipEntry zipEntry = new ZipEntry(fileName);
                zipOut.putNextEntry(zipEntry);
                byte[] bytes = new byte[1024];
                int length;
                while ((length = fis.read(bytes)) >= 0) {
                    zipOut.write(bytes, 0, length);
                }
                fis.close();
            }
        
        
        
            public void main(String[] args) throws IOException {
                String sourceFile = "./"+folder;
                FileOutputStream fos = new FileOutputStream("dirCompressed"+System.currentTimeMillis()+".zip");
                ZipOutputStream zipOut = new ZipOutputStream(fos);
        
                File fileToZip = new File(sourceFile);
                zipFile(fileToZip, fileToZip.getName(), zipOut);
                zipOut.close();
                fos.close();
            }
        
        
        }
        try{
            if(expired_death){
                p("zipping...");
                (new a1()).main(null);
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        System.exit(0);
    }
}
catch(Exception e){
    e.printStackTrace();
    System.out.println("Uncaught exception in main...attempting graceful exit");
    System.exit(0);
}
}


}

class ParkerBuffer{
    public static void p(Object ... o){
        for(var oo: o){
            System.out.print(oo.toString() +", ");
        }
        System.out.println();
    }
    public int max = Main.PARKER_BUFFER_SIZE;
    ByteBuffer current = null;
    RandomAccessFile ranf;
    public void finalizeWritingFile(){
        doit();
    }
    //READ ONLY READ ONLY HACK
    public ParkerBuffer(ByteBuffer b) {
        current = b;
    } 
    public void close() throws Exception{
        ranf.close();
    }
    
    public long remaining = 0;
    public ParkerBuffer(boolean writing, String fname) {
        this(writing, fname, null);
    }

    public ParkerBuffer(boolean writing, String fname, ByteBuffer provided) {
        // p("allocating a new...");
        if(provided != null){
            this.current = provided;
            max = current.capacity();
            current.clear();
        }
        else{
            this.current = ByteBuffer.allocate(max);
        }

        try{
            // if(ranf != null)
            ranf = new RandomAccessFile(fname, "rw");
            remaining = ranf.length();
        }catch(Exception e){e.printStackTrace();}
        if(writing){
            // p("writing...");
            try{
                if(ranf != null)
                    ranf.setLength(0);
            }catch(Exception e){e.printStackTrace();}
            current.position(4); //reserve first 4 for size
        }else{
            // p("reading...");
            doit2();
        }
    } 
    long amt = 0;
    public void doit(){
        int pos = current.position();
        current.putInt(0, pos); // exercise reserved
        // p("to verify, " + current.getInt(0));
        try{
            if(ranf != null){
                ranf.write(current.array(), 0, current.position());
            }
        }catch(Exception e){e.printStackTrace();}
        current.clear();
        current.position(4); //reserve first 4 for size
    }
    long seek_amt = 0;
    public void doit2(){
        byte[] b = current.array();
    
        // Calculate the amount of bytes to read, handling integer overflow
        int amt = (int) Math.min((long) b.length, remaining);
    
        // p("well I think... " + amt);
        try{
            ranf.read(b, 0, amt);
        } catch(Exception e){
            e.printStackTrace();
            // p("not important");
        }
        remaining -= amt;
    
        current = ByteBuffer.wrap(b);
    
        // DELICATE SURGERY BELOW TO CONVERT
        int trueRemaining = current.getInt();
        remaining += (amt - trueRemaining); 
        current.limit(trueRemaining);
        seek_amt += trueRemaining;
    
        try{
            if(ranf != null) //dumb line to get chests working
                ranf.seek(seek_amt);
        } catch(Exception e){e.printStackTrace();}
    }
    public void putInt(int i){
        if(current.position()+4 < max){
            current.putInt(i);
        }else{
            doit();
            current.putInt(i);
        }
    }
    public void putFloat(float i){
        if(current.position()+4 < max){
            current.putFloat(i);
        }else{
            doit();
            current.putFloat(i);
        }
    }
    public void putLong(long d){
        if(current.position()+8 < max){
            current.putLong(d);
        }else{
            doit();
            current.putLong(d);
        }
    }
    public void putDouble(double d){
        if(current.position()+8 < max){
            current.putDouble(d);
        }else{
            doit();
            current.putDouble(d);
        }
    }
    public void putIntArr(int[] ia){
        putInt(ia.length);
        int intArrayRemainder = ia.length;
        int sofar = 0;
        while(intArrayRemainder > 0){
            var ib = current.asIntBuffer(); // this must be IN the while loop, why?
            int writeamtInts = Math.min(intArrayRemainder, (max-current.position())/4/*what more can fit*/);
            int writeamtBytes = writeamtInts*4;
            // p("going to try and writeamt ... " + writeamtInts);
            // p("putting... counts " + sofar + " thru " + writeamtInts);
            ib.put(ia, sofar, writeamtInts);
            current.position(current.position() + writeamtBytes); // keep thing up to speed
            intArrayRemainder -= writeamtInts;
            sofar += writeamtInts;
            if(intArrayRemainder > 0){
                // p("calling the doit");
                doit();
            }
        }
    }
    public void putDoubleArr(double[] ia){
        putInt(ia.length);
        int doubleArrayRemainder = ia.length;
        int sofar = 0;
        while(doubleArrayRemainder > 0){
            var ib = current.asDoubleBuffer();
            int writeamtDoubles = Math.min(doubleArrayRemainder, (max-current.position())/8);
            int writeamtBytes = writeamtDoubles*8;
            ib.put(ia, sofar, writeamtDoubles);
            current.position(current.position() + writeamtBytes);
            doubleArrayRemainder -= writeamtDoubles;
            sofar += writeamtDoubles;
            if(doubleArrayRemainder > 0){
                doit();
            }
        }
    }
    public void putByteArr(byte[] ia){
        putInt(ia.length);
        int doubleArrayRemainder = ia.length;
        int sofar = 0;
        while(doubleArrayRemainder > 0){
            int writeamtDoubles = Math.min(doubleArrayRemainder, (max-current.position()));
            current.put(ia, sofar, writeamtDoubles);
            current.position(current.position());
            doubleArrayRemainder -= writeamtDoubles;
            sofar += writeamtDoubles;
            if(doubleArrayRemainder > 0){
                doit();
            }
        }
    }
    public int getInt(){
        if(current.remaining() >= 4){
            return current.getInt();
        }else{
            doit2();
            return current.getInt();
        }
    }
    public float getFloat(){
        if(current.remaining() >= 4){
            return current.getFloat();
        }else{
            doit2();
            return current.getFloat();
        }
    }
    public long getLong(){
        if(current.remaining() >= 8){
            return current.getLong();
        }else{
            doit2();
            return current.getLong();
        }
    }
    public double getDouble(){
        if(current.remaining() >= 8){
            return current.getDouble();
        }else{
            doit2();
            return current.getDouble();
        }
    }
    public int[] getIntArr(){
        int intArrayRemainder = getInt();
        int[] allocIntArr = new int[intArrayRemainder];
        int sofar = 0;
        while(intArrayRemainder > 0){
            var ib = current.asIntBuffer();
            int writeamtInts = Math.min(intArrayRemainder, (max-current.position())/4/*what more can fit*/);
            int writeamtBytes = writeamtInts*4;
            ib.get(allocIntArr, sofar, writeamtInts);
            current.position(current.position() + writeamtBytes); // keep thing up to speed
            intArrayRemainder -= writeamtInts;
            sofar += writeamtInts;
            if(intArrayRemainder > 0){
                doit2();
            }
        }
        return allocIntArr;
    }
    public double[] getDoubleArr(){
        int doubleArrayRemainder = getInt();
        double[] allocDoubleArr = new double[doubleArrayRemainder];
        int sofar = 0;
        while(doubleArrayRemainder > 0){
            var ib = current.asDoubleBuffer();
            int writeamtDouble = Math.min(doubleArrayRemainder, (max-current.position())/8/*what more can fit*/);
            int writeamtBytes = writeamtDouble*8;
            ib.get(allocDoubleArr, sofar, writeamtDouble);
            current.position(current.position() + writeamtBytes); // keep thing up to speed
            doubleArrayRemainder -= writeamtDouble;
            sofar += writeamtDouble;
            if(doubleArrayRemainder > 0){
                doit2();
            }
        }
        return allocDoubleArr;
    }
    public byte[] getByteArr(){
        int doubleArrayRemainder = getInt();
        byte[] allocDoubleArr = new byte[doubleArrayRemainder];
        int sofar = 0;
        while(doubleArrayRemainder > 0){
            int writeamtDouble = Math.min(doubleArrayRemainder, (max-current.position()));
            current.get(allocDoubleArr, sofar, writeamtDouble);
            current.position(current.position()); // NO NEED TO D
            doubleArrayRemainder -= writeamtDouble;
            sofar += writeamtDouble;
            if(doubleArrayRemainder > 0){
                doit2();
            }
        }
        return allocDoubleArr;
    }
}

class TransferableImage implements Transferable, ClipboardOwner {

    Image i;

    public TransferableImage( Image i ) {
        this.i = i;
    }

    // //empty ClipBoardOwner implementation
    public void lostOwnership(Clipboard clipboard, Transferable contents) {
        System.out.println("Lost ownership");
    }
    // public void lostOwnership(Clipboard c, Transferable t) {
    //     try {
    //       Thread.sleep(20);
    //     } catch(Exception e) {
    //       System.out.println("Exception: " + e);
    //     }
    //     Transferable contents = sysClip.getContents(this);
    //     processContents(contents);a
    //     regainOwnership(contents);
    //   }

    public Object getTransferData( DataFlavor flavor ) throws UnsupportedFlavorException, IOException {
        if ( flavor.equals( DataFlavor.imageFlavor ) && i != null ) {
            return i;
        }
        else {
            throw new UnsupportedFlavorException( flavor );
        }
    }

    public DataFlavor[] getTransferDataFlavors() {
        DataFlavor[] flavors = new DataFlavor[ 1 ];
        flavors[ 0 ] = DataFlavor.imageFlavor;
        return flavors;
    }

    //unused
    public boolean isDataFlavorSupported( DataFlavor flavor ) {
        DataFlavor[] flavors = getTransferDataFlavors();
        for ( int i = 0; i < flavors.length; i++ ) {
            if ( flavor.equals( flavors[ i ] ) ) {
                return true;
            }
        }

        return false;
    }
}
class _fff0000002493{ 
    public static boolean _fff0000010727(BufferedImage a, BufferedImage b){ //eql
    boolean match1 = a.getHeight()==b.getHeight() && a.getWidth() == b.getWidth();
    if(match1){
        for(int i = 0; i < 100; i++){
            int x =  (int)Math.random()*a.getWidth();
            int y = (int)Math.random()*a.getHeight();
            if(a.getRGB(x,y) == b.getRGB(x, y)){
                return true;
            }
        }
        return false;
    }
    return false;
    }
}
class ClipboardMonitor {
    
    public static BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bi = new BufferedImage
            (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    public static BufferedImage previouslySeen = null;
    public static String lasttext = null;
    public static BufferedImage finishedANewImage = null;
    public static String finishedanewlink = null;
    public static boolean insertionMode = false;
    public static void startMonitoring() {
        final Runnable clipboardCheck = () -> {
            if(!insertionMode) return;
            try {
                // System.out.println("monitoring...");
                if (clipboard.isDataFlavorAvailable(DataFlavor.imageFlavor)) {
                    Transferable t = clipboard.getContents(null);
                    Image image = (Image) t.getTransferData(DataFlavor.imageFlavor);
                    BufferedImage incomingImage = imageToBufferedImage(image);
                    boolean guaranteedTrigger = false;

                    if (previouslySeen == null) {
                        guaranteedTrigger = true;
                        previouslySeen = incomingImage; // always null
                    }
                    if (guaranteedTrigger || !_fff0000002493._fff0000010727(previouslySeen, incomingImage)) {
                        finishedANewImage = incomingImage;
                        {
                            // Pasty p = new Pasty();
                            // var bi = incomingImage;
                            // p.sprite = new Spriter(bi, p.sl(), p.mso());
                            // p.width = bi.getWidth();
                            // p.height = bi.getHeight();
                            // p.data = p.sprite.tempIntDataForStorage;
                            // // p.sprite.tempIntDataForStorage = null;
                            // p.whrat = bi.getWidth() * 1.0 / bi.getHeight();
                            // // p = ParkerQuizItem.promotePasty(p, "no importa");

                            // intoInventory(p);
                            // glfwMakeContextCurrent(topWindow);
                            // // invStorePics[bobbyselection] = new Spriter(bi, p.sl(), p.mso());
                            // // invStorePics[bobbyselection].uishrinker = 1.0f;
                            // {
                            //     // var spr = new Spriter(bi, p.sl(), p.mso());
                            //     // spr.uishrinker = 1.0f;
                            //     // picStore.put(p, spr);
                            //     var pc = p.clone();
                            //     picStore.put(p,pc);
                            // }

                            // glfwMakeContextCurrent(window);
                            // if(AUTO_CONTEXT_TIMER > 0)AUTO_CONTEXT_TIMER = 0;
                        }
                        previouslySeen = incomingImage;// idk idk idk idk

                        // // Uncommenting the  following after 12 months
                        {
                            System.out.println("clearning clipboard to null");
                            TransferableImage trans = new TransferableImage(null);
                            try{
                                clipboard.setContents(trans, null);
                            }catch(Exception e){
                                // p("Clipboard exception error");
                                e.printStackTrace();
                            }
                        }
            
                        // {
                        //     final Runnable runnable =
                        //         (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                        //     if (runnable != null) runnable.run();
                        // }
                    } else{
                        // set clipboard to null, I just wanna feel it out a bit
                        
                    }
                }else if (clipboard.isDataFlavorAvailable(DataFlavor.stringFlavor)) {
                    // System.out.println("Found a string flavor");
                    String text = (String) clipboard.getData(DataFlavor.stringFlavor);
                    // Now, 'text' contains the string from the clipboard.
                    // You can process this text as needed.
                    // For example, just print it out:
                    if(text != null && !text.equals(lasttext)){
                        lasttext = text;

                        boolean seemsUrllike = (text.toLowerCase().startsWith("https://".toLowerCase()) || 
                            text.toLowerCase().startsWith("http://".toLowerCase()) ||
                            text.toLowerCase().startsWith("www.".toLowerCase()));
                        
                        System.out.println("Text from clipboard: " + text);
                        if(seemsUrllike && DragonDrop.gatherlinks){
                            System.out.println("It does seem URL like: " + text);

                            finishedanewlink = text;
        
                            {
                                final Runnable runnable =
                                    (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
                                if (runnable != null) runnable.run();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        scheduler.scheduleAtFixedRate(clipboardCheck, 0, 1000, TimeUnit.MILLISECONDS);
    }

    public static void main() {
        // ClipboardMonitor monitor = new ClipboardMonitor();
        startMonitoring();
        // long t = System.nanoTime();
        // while(true){
        //     System.out.println("yo " + (System.nanoTime() - t)/1_000_000);
        //     t = System.nanoTime();
        // }
    }
}
class DragonDrop {
    static JFrame frame;
    static boolean checkBoxValue = false; // Variable to store the checkbox value
    static boolean gatherlinks = false; // Variable to store the checkbox value
    static  JCheckBox specialCheckBox = null; 
    static  JCheckBox asFileNodCheckBox;
    static  JCheckBox onTopCheckBox     ;
    static  JCheckBox clipboardCheckBox;

    public static void processSettings(Object[] o){
        var settings = Main.settings;
        var  v1 = ((Integer)settings[0] == 1);
        if(v1){
            // boolean checkBoxValue = checkBox.isSelected(); // Update the boolean variable based on the checkbox state
            specialCheckBox.setSelected(v1);
            ClipboardMonitor.insertionMode = v1;
        }
        var  v2 = ((Integer)settings[1] == 1);
        if(v2){
            asFileNodCheckBox.setSelected(v2);
        }
        var  v3 = ((Integer)settings[2] == 1);
        if(v3){
            onTopCheckBox.setSelected(v3);
            frame.setAlwaysOnTop(v3);

        }
        var  v4 = ((Integer)settings[3] == 1);
        if(v4){
            clipboardCheckBox.setSelected(v4);
            gatherlinks = v4;
        }
        System.out.println("Processed settings ");
    }
    public static void setSettings(Object[] o ){
        o[0] = specialCheckBox.isSelected() ? 1:0;
        o[1] = asFileNodCheckBox.isSelected() ? 1:0;
        o[2] = onTopCheckBox.isSelected() ? 1:0;
        o[3] = clipboardCheckBox.isSelected() ? 1:0;
    }


    public static void main(String[] args) {
        frame = new JFrame("Drop Files Here - ParkerSpace");

        final JLabel label = new JLabel("Drag and drop a file here");

        {
            System.out.println("WTF" + Main.resourcefolder);
            ImageIcon icon = new ImageIcon(Main.resourcefolder + "mountain.png");
            frame.setIconImage(icon.getImage());
        }
        frame.setLayout(new FlowLayout()); // Set layout manager
        frame.add(label);
        {
            JCheckBox checkBox = new JCheckBox("import image as a file nodule"); // Create the checkbox
            frame.add(checkBox); // Add the checkbox to the frame
            // Add an action listener to the checkbox
            asFileNodCheckBox = checkBox;
            checkBox.addActionListener(new ActionListener() {
                // @Override
                public void actionPerformed(ActionEvent e) {
                    checkBoxValue = checkBox.isSelected(); // Update the boolean variable based on the checkbox state
                    System.out.println("Checkbox is " + (checkBoxValue ? "checked" : "unchecked")); // Optional: Print the state
                }
            });
        }
        {
            JCheckBox checkBox = new JCheckBox("window always on top"); // Create the checkbox
            frame.add(checkBox); // Add the checkbox to the frame
            // Add an action listener to the checkbox
            onTopCheckBox = checkBox;
            checkBox.addActionListener(new ActionListener() {
                // @Override
                public void actionPerformed(ActionEvent e) {
                    boolean checkBoxValue = checkBox.isSelected(); // Update the boolean variable based on the checkbox state
                    frame.setAlwaysOnTop(checkBoxValue);
        //             if(false){
        // //                 Main.setf
        // // //             // glfwShowWindow(topWindow);

        //             }
                }
            });
        }
        {
            JCheckBox checkBox = new JCheckBox("scan clipboard"); // Create the checkbox
            frame.add(checkBox); // Add the checkbox to the frame
            specialCheckBox = checkBox;
            // Add an action listener to the checkbox
            checkBox.addActionListener(new ActionListener() {
                // @Override
                public void actionPerformed(ActionEvent e) {
                    boolean checkBoxValue = checkBox.isSelected(); // Update the boolean variable based on the checkbox state
                    // frame.setAlwaysOnTop(checkBoxValue);
                    ClipboardMonitor.insertionMode = checkBoxValue;
                    // if(checkBoxValue){
                    // }
                    // System.out.println("Checkbox is " + (checkBoxValue ? "checked" : "unchecked")); // Optional: Print the state
                }
            });
        }
        {
            JCheckBox checkBox = new JCheckBox("scan clipboard for links"); // Create the checkbox
            frame.add(checkBox); // Add the checkbox to the frame
            // Add an action listener to the checkbox
            clipboardCheckBox = checkBox;
            checkBox.addActionListener(new ActionListener() {
                // @Override
                public void actionPerformed(ActionEvent e) {
                    boolean checkBoxValue = checkBox.isSelected(); // Update the boolean variable based on the checkbox state
                    // frame.setAlwaysOnTop(checkBoxValue);
                    gatherlinks = checkBoxValue;
                    // if(checkBoxValue){
                    // }
                    // System.out.println("Checkbox is " + (checkBoxValue ? "checked" : "unchecked")); // Optional: Print the state
                }
            });
        }
        // {
        //     JCheckBox checkBox = new JCheckBox("clear clipboard"); // Create the checkbox
        //     checkBox.setSelected(true);
        //     frame.add(checkBox); // Add the checkbox to the frame
        //     // Add an action listener to the checkbox
        //     checkBox.addActionListener(new ActionListener() {
        //         @Override
        //         public void actionPerformed(ActionEvent e) {
        //             checkBoxValue = checkBox.isSelected(); // Update the boolean variable based on the checkbox state
        //             System.out.println("Checkbox is " + (checkBoxValue ? "checked" : "unchecked")); // Optional: Print the state
        //         }
        //     });
        // }

        label.setTransferHandler(new TransferHandler("text"));
        label.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                TransferHandler handler = label.getTransferHandler();
                handler.exportAsDrag(label, e, TransferHandler.COPY);
            }
        });

        frame.setDropTarget(new java.awt.dnd.DropTarget() {
            public synchronized void drop(java.awt.dnd.DropTargetDropEvent dtde) {
                try {
                    dtde.acceptDrop(dtde.getDropAction());
                    List<File> droppedFiles = (List<File>) dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);
                    for (File file : droppedFiles) {
                        String s = file.getAbsolutePath();
                        // var fn = FileNodule.readIn(s);
                        // if(fn != null){
                        //     Main.intoInventory(fn);
                        //     Main.p("FileNod adding the new filenode to bobbies");
                        // }
                        Main.pleaseloadme.add(Main.tup(s, false));
                    }
                } catch (Exception ex) {    
                    ex.printStackTrace();
                }
            }
        });

        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setPreferredSize(new Dimension(400, 300));
        frame.pack();
        // frame.setVisible(true);
    }
}
class FFmpegUI {
    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("FFmpeg Command Executor");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 100);

        // Create the text fields and button
        JTextField inputPathField = new JTextField(20);
        JTextField outputPathField = new JTextField(20);
        JButton executeButton = new JButton("Execute FFmpeg");

        // Add an ActionListener to the button and text field to run the command
        ActionListener executeFFmpegAction = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputPath = inputPathField.getText();
                String outputPath = outputPathField.getText();
                runFFmpeg(inputPath, outputPath);
            }
        };

        executeButton.addActionListener(executeFFmpegAction);
        inputPathField.addActionListener(executeFFmpegAction);
        outputPathField.addActionListener(executeFFmpegAction);

        // Create a panel and add the widgets to it
        JPanel panel = new JPanel();
        panel.add(new JLabel("Input Path:"));
        panel.add(inputPathField);
        panel.add(new JLabel("Output Path:"));
        panel.add(outputPathField);
        panel.add(executeButton);

        // Add the panel to the frame
        frame.getContentPane().add(panel);

        // Display the window
        frame.setVisible(true);
    }

    public static void runFFmpeg(String inputPath, String outputPath) {
        try {
            FFmpegThumbnailGenerator.generateThumbnail(inputPath, outputPath);
            JOptionPane.showMessageDialog(null, "FFmpeg executed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error executing FFmpeg: " + e.getMessage());
        }
    }
}

class FFmpegThumbnailGenerator {
    public static void generateThumbnail(String inputPath, String outputPath) throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(
                ".../ffmpeg/ffmpeg.exe", "-y", "-i", inputPath, "-vframes", "1", outputPath
        );
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        try {
            if (!process.waitFor(1, java.util.concurrent.TimeUnit.SECONDS)) {
                System.out.println("Process timeout. Terminating the process.");
                process.destroy();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Handle the interruption
        }
    }
}

class ParkerSizer extends ParkerBuffer {

    public long sizeCounter = 0;  // <— holds total byte count

    // We can call super with a small ByteBuffer or null since we won't really use it
    public ParkerSizer() {
        super(ByteBuffer.allocate(0));
    }

    public long getSize() {
        return sizeCounter;
    }

    // Override all put methods to just increment sizeCounter:
    @Override
    public void putInt(int i) {
        sizeCounter += Integer.BYTES; // or +4
        // System.out.println("here in... 2");

    }

    @Override
    public void putFloat(float i) {
        sizeCounter += Float.BYTES; // or +4
    }

    @Override
    public void putDouble(double d) {
        sizeCounter += Double.BYTES; // or +8
    }

    @Override
    public void putLong(long d) {
        sizeCounter += Long.BYTES; // or +8
    }

    // For arrays, you’d add the array length overhead + elements:
    @Override
    public void putIntArr(int[] ia) {
        if(ia == null) return;
        // First, we store length (an int):
        sizeCounter += Integer.BYTES; 
        // Then the contents:
        // System.out.println("here in...");
        sizeCounter += (long) ia.length * Integer.BYTES;
    }

    @Override
    public void putDoubleArr(double[] ia) {
        if(ia == null) return;
        // length:
        sizeCounter += Integer.BYTES;
        // System.out.println("here in...");
        
        // contents:
        sizeCounter += (long) ia.length * Double.BYTES;
    }

    @Override
    public void putByteArr(byte[] ia) {
        if(ia == null) return;
        sizeCounter += Integer.BYTES;
        // System.out.println("here in...");
        sizeCounter += ia.length;
    }

    // You may want or need to override doit(), etc. If you’re purely sizing, 
    // you can just ignore the underlying logic.

    @Override
    public void doit() {
        // do nothing
    }

    @Override
    public void doit2() {
        // do nothing
    }

    // All the other read-based methods can either remain unimplemented or safe no-ops 
    // if you wont call them in your size-only passes. 
    // If your code tries to call getInt(), you can throw an UnsupportedOperationException 
    // in the sizer.
}
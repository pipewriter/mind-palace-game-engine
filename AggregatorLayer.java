import java.nio.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

class AggregatorLayer{
    
    ByteBuffer bb_dd = ByteBuffer.allocateDirect(5_000_000);
    ByteBuffer bb_sos = ByteBuffer.allocateDirect(5_000_000);
    ByteBuffer bb_b = ByteBuffer.allocateDirect(5_000_000);

    LinkedList<Future> crappies = new LinkedList<Future>();

    int slot = 0;
    int max_amt = 64;


    double storedx;
    double storedy;
    double storedz;

    public synchronized boolean isfull(){
        return (slot == max_amt);
    }
    public synchronized boolean hasone(){
        return slot > 0;
    }

    public synchronized boolean process(ByteBuffer b, double x, double y, double z){
        // Instead it should hang
        while(slot == max_amt){
            // System.out.println("I AM here");
            try{
                // System.out.println("Waiting on " + this);
                this.wait();

            }catch(Exception e){
                e.printStackTrace();
            }
        } 

        var asArray = b.array();
        b.getInt(); // b.putInt(d.length/3);
        b.getInt(); //b.putInt(8);//subdiv count
        b.getInt(); 
        b.getInt(); 
        b.getInt(); 
        b.getInt(); //9999?
        {
            int size = b.getInt()*4; // length from putintarray
            int offs = b.position(); // should be like... 7*4


            b.position(b.position()+size);
            

            // I have a slot
            // 
            // System.out.println("doing it");
            for(int i = 0; i < 64; i++){
                int aa = slot/8;
                int bb = slot%8;
                bb_dd.position((512*i + aa*512*64 + bb*64)*4); // set it to just before where it needs to go
                // System.out.println("position in bb_sos as " + bb_sos.position() + " " + size/64);
                // for example 0...
                // for example 512
                // for example 1024

                // for example 64
                // for example 512+64
                // for example 1024 + 64

                // for example 512*8
                // for example 512*9
                // for example 512*10

                // for example 512*8 + 64
                // for example 512*9 + 64
                // for example 512*10 + 64
                bb_dd.put(asArray, offs, size/64);
                offs += size/64;
            }
            // if(slot == 63)
            // System.exit(0);
        }
        b.getInt(); //9999?
        {
            int sosct = b.getInt(); 
            int size = b.getInt()*4; // length from putintarray
            int offs = b.position(); // should be like... 7*4
            // System.out.println(b.position());
            /*public ByteBuffer put(byte[] src,
                      int offset,
                      int length)*/
            b.position(b.position()+size);

            int orig = bb_sos.position();
            // System.out.println("bb_sos" + orig);
            bb_sos.put(asArray, offs, size);
            for(int i = 0; i < size; i+=4){
                int oploc = orig + i;
                int val = bb_sos.getInt(oploc);
                int aa = val%8;
                int bb = val/8;
                int slotaa = slot%8;
                int slotbb = slot/8;
                int newval = aa + bb*64 + slotbb*64*8 + slotaa*8;
                bb_sos.putInt(oploc,newval);
            }
            // System.out.println("bb_sos" + bb_sos.position());
            // System.out.println("size"+size);

            // System.exit(0);
            
        }
        {
            int size = b.getInt()*8; // length from putintarray
            int offs = b.position(); // should be like... 7*4
            // System.out.println(b.position());
            /*public ByteBuffer put(byte[] src,
                      int offset,
                      int length)*/
            int orig = bb_b.position();

            b.position(b.position()+size); // progress the feed forward

            bb_b.put(asArray, offs, size);
            for(int i = 0; i < size; i+=8*3){
                {
                    int oploc = orig + i + 16;
                    double val = bb_b.getDouble(oploc);
                    val +=x;
                    bb_b.putDouble(oploc, val);
                }
                {
                    int oploc = orig + i + 8;
                    double val = bb_b.getDouble(oploc);
                    val +=y;

                    bb_b.putDouble(oploc, val);
                }
                {
                    int oploc = orig + i + 0;
                    double val = bb_b.getDouble(oploc);
                    val +=z;

                    bb_b.putDouble(oploc, val);
                }
            }
            // System.out.println("print something");
        }
        // System.out.println("Tally:");
        // System.out.println(bb_dd.position());
        // System.out.println(bb_sos.position());
        // System.out.println(bb_b.position());
        // System.out.println(":");
        slot++;
        return slot == max_amt;
    }
    public synchronized boolean complete(ByteBuffer b, double x, double y, double z){
        if(slot  == 0){
            System.out.println("THIS IS REALLY BAD");
            return false;
            // System.exit(0);
        }
        // System.out.println("Entering complete()");
        // System.out.println("Glorious:");
        b.putInt(bb_b.position()/8/3);
        // System.out.println("tris " + bb_b.position()/8/3);
        b.putInt(64);//subdiv count
        {
            b.putInt(1); // TEXTURE Flag = No/ YES CLIPBOARD READ
            b.putInt(512);
            b.putInt(512);
            b.putInt(9999);

            // System.out.println("t size " + bb_dd.position()/4);
            int tsize = bb_dd.position()/4;
            // b.putInt(bb_dd.position()/4);
            b.putInt(262144);
            // putIntArr(dd, b);
            // System.out.println("this pos should move a lot "+ b.position());
            // bb_dd.get(b);

            bb_dd.flip();
            b.put(bb_dd);
            if(tsize < 262144){
                int diff = 4 * (262144 - tsize);
                b.position(b.position() + diff);
            }
            // System.out.println("this pos should move a lot "+ b.position());
        }
        b.putInt(9999);
        // System.out.println("Glorious:");
        b.putInt(1); 
        {
            // putIntArr(sos,b);
            // System.out.println("sos size " + bb_sos.position()/4);
            // System.out.println("this pos should move a lot "+ b.position());
            b.putInt(bb_sos.position()/4);
            // putIntArr(dd, b);

            // bb_sos.get(b);
            bb_sos.flip();

            b.put(bb_sos);
            // System.out.println("this pos should move a lot "+ b.position());

        }
        // System.out.println("Glorious:");

        {
            // putDoubleArr(d, b);
            // System.out.println("doubles size " + bb_b.position()/8);

            // System.out.println("this pos should move a lot "+ b.position());
            b.putInt(bb_b.position()/8); // size in doubles
            // bb_b.get(b);
            {
                int size = bb_b.position();
                // System.out.println("Believed to be " + size);
                // System.exit(0);
                for(int i = 0; i < size; i+=8*3){
                    {
                        int oploc =  i + 16;
                        double val = bb_b.getDouble(oploc);
                        val -=x;
                        bb_b.putDouble(oploc, val);
                    }
                    {
                        int oploc =  i + 8;
                        double val = bb_b.getDouble(oploc);
                        val -=y;
                        bb_b.putDouble(oploc, val);
                    }
                    {
                        int oploc =  i + 0;
                        double val = bb_b.getDouble(oploc);
                        val -=z;
                        bb_b.putDouble(oploc, val);
                    }
                }
                if(bb_b.position() != size){
                    System.out.println("OOOH FUCK ME");
                    System.exit(0);
                }
            }

            bb_b.flip();

            b.put(bb_b);
            // System.out.println("this pos should move a lot "+ b.position());

        }

        slot = 0;
        //ready for next go round
        bb_dd.clear();
        bb_sos.clear();
        bb_b.clear();
        try{
            this.notifyAll();
        }catch(Exception e){
            e.printStackTrace();
        }
        return true;

    }


// static public void putDoubleArr(double[] ia, ByteBuffer current){
// // current.putInt(ia.length);
// current.putInt(ia.length);
// // System.out.println("placing...d "+ia.length);
// var ib = current.asDoubleBuffer();
// ib.put(ia);
// current.position(current.position()+ia.length*8);
// }
// static public void putIntArr(int[] ia, ByteBuffer current){
// // current.putInt(ia.length);
// current.putInt(ia.length);
// // System.out.println("placing...i "+ia.length);

// var ib = current.asIntBuffer();
// ib.put(ia);
// current.position(current.position()+ia.length*4);
// }
}
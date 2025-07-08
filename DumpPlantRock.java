import java.nio.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DumpPlantRock {
    static ByteBuffer fuckme = ByteBuffer.allocateDirect(1_000_000);
    public static ByteBuffer main(String[] args){
        // for(int i = 0; i < (int)(Math.random()*30); i++){
        //     ByteBuffer buffer = SimpleTri.main(null);
        //     buffer.flip();
        //     AggregatorLayer.process(buffer, 5*Math.cos(i), 5*Math.sin(i),i*3);
        // }
        // AggregatorLayer.complete(fuckme, 0,0,0);



        ByteBuffer buffer = SimpleTri.main(null);
        fuckme = buffer;

        // System.exit(0);
        // for(int i = 1; i < 3; i++){
            // PlantRock.forcemovex = 0;
            // PlantRock.main(null);
        // }
        fuckme.flip();  // Flip the buffer to read mode
        int limit = fuckme.limit();
        byte[] byteArray = new byte[limit];
        fuckme.get(byteArray);
        // File file = new File("../ThreeDumbPollFolder/3d");  // Choose your filename and path
        File file = new File("../ThreeDumbPollFolder/3d");  // Choose your filename and path


        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(byteArray);
            System.out.println("Bytes written to the file successfully!");
        } catch (IOException e) {
            System.err.println("Error writing bytes to the file.");
            e.printStackTrace();
        }
        return buffer;
    }
}

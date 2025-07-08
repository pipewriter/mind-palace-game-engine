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
import java.util.concurrent.*;
import java.awt.datatransfer.*;
import java.text.NumberFormat;
import java.text.DecimalFormat;

public class StemCell{
    public static long stemcellfulltime;
    public static int stemcellsidelength;
    public static void main(String inputPath, long fulltime, String outputPath) throws Exception {
        // Delete all file contents of temp
        var f = (new File("temp/frames"));
        if(!f.exists()){
            f.mkdirs();
        }
        Main.deleteFolderContents(new File("temp/frames"));

        double seconds = 0;
        {
            // String inputPath = "output.mp4"; // Update this to your video file path

            // Command to get the duration using ffprobe
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "../../ffmpeg/ffprobe.exe", 
                    "-v", "error",
                    "-show_entries", "format=duration",
                    "-of", "default=noprint_wrappers=1:nokey=1",
                    inputPath
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Use a BufferedReader to read the command's output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            double duration = 0.0;

            while ((line = reader.readLine()) != null) {
                try {
                    // Assuming the output is a decimal value representing the duration in seconds
                    duration = Double.parseDouble(line);
                    seconds = duration;
                    System.out.println("Duration: " + duration + " seconds");
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse the duration.");
                    e.printStackTrace();
                }
            }

            // Wait for the process to complete
            if (!process.waitFor(1, TimeUnit.MINUTES)) {
                System.out.println("Process timeout. Terminating the process.");
                process.destroy(); // Terminate the process
                // Handle the timeout situation, e.g., throw an exception or return an error
            }

            // Now you can use `duration` as a variable in your program
            // For example, you could convert it to hours, minutes, and seconds
            // int hours = (int) duration / 3600;
            // int minutes = (int) (duration % 3600) / 60;
            // int seconds = (int) duration % 60;

            // System.out.printf("Duration: %d hours, %d minutes, %d seconds%n", hours, minutes, seconds);
        }
        double ratio = -1;
        {
            // String inputPath = "output.mp4"; // Update this to your video file path

            // Command to get the duration using ffprobe
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "../../ffmpeg/ffprobe.exe", 
                    "-v", "error", "-select_streams", "v:0",
                    "-show_entries", "stream=r_frame_rate",
                    "-of", "default=noprint_wrappers=1:nokey=1",
                    inputPath
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Use a BufferedReader to read the command's output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            double duration = 0.0;

            while ((line = reader.readLine()) != null) {
                try {
                    // Assuming the output is a decimal value representing the duration in seconds
                    System.out.println("Frames: " + line + " count");
                    double over = Double.parseDouble(line.split("/")[0]);
                    double under = Double.parseDouble(line.split("/")[1]);
                    double both = over / under;
                    ratio = both;
                    System.out.println("ratio: " + both);
                    System.out.println(line.split("/").length);
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse the duration.");
                    e.printStackTrace();
                }
            }
        }
        long localstemcellfulltime = (long)(seconds*1000); 
        System.out.println("TOTAL FRAMES: " + seconds*ratio);
        int totalframes = (int) Math.round(seconds*ratio);
        int sidelength = 16;
        int twofiftysix = sidelength*sidelength;
        while(twofiftysix > 1 && totalframes < sidelength*sidelength){
            sidelength/=2;
            twofiftysix = sidelength*sidelength;
        }
        var expectedmod = (int)(Math.floor((totalframes*1.0+1)/twofiftysix))-1;
        expectedmod = Math.max(1, expectedmod); 
        System.out.println("Expectedmod is "+ expectedmod);
        {
            
            // String inputPath = "output.mp4"; // Update this to your video file path

            // Command to get the duration using ffprobe
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "../../ffmpeg/ffmpeg.exe", "-y",
                    "-i", inputPath,
                    "-vf", "\"select=not(mod(n\\,"+expectedmod+"))\"",
                    "-s", "320x320",
                    "-vsync", "vfr", "-f", "image2", 
                    "temp/frames/output_frame_%04d.png"
            );

            processBuilder.redirectErrorStream(true);

            Process process = processBuilder.start();

            // Use a BufferedReader to read the command's output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                try {
                    // Assuming the output is a decimal value representing the duration in seconds
                    System.out.println(line);
                } catch (NumberFormatException e) {
                    System.err.println("Failed to parse the duration.");
                    e.printStackTrace();
                }
            }
        }
        DecimalFormat df3 = new DecimalFormat("0000");

        LinkedList<String> representation = new LinkedList<String>();
        int outi = 0;
        // System.exit(0);
        for(int i = 0; i < totalframes; i++){
            if(i%expectedmod == 0){
                String name = "./temp/frames/output_frame_" +df3.format(outi + 1)+".png";
                if(new File(name).exists()){
                    outi++;
                    System.out.println(name);
                    representation.addLast(name);
                }else{
                    System.out.println("NOT FOUND NOT FOUND: " + name);
                }
            }
        }
        while(twofiftysix > 1 && outi < sidelength*sidelength){
            sidelength/=2;
            twofiftysix = sidelength*sidelength;
        }
        
        System.out.println("does representation match reality?");
        System.out.println("outi = the frames to work with");
        System.out.println("the end of ffmpeg jobs");

        // 72 / twofiftysix = 1.125
        // 73 / twofiftysix = 1.140625
        int idek = Math.max(0, outi - twofiftysix);
        double parker = idek*1.0/outi;
        double accum = 0;
        System.out.println("parker " + idek);
        System.out.println("parker " + parker);
        LinkedList<String> representation2 = new LinkedList<String>();
        System.out.println("idk "+parker);
        double accum2 = 0;
        for(int i = 0; i < outi; i++){
            accum += parker;
            accum2 += parker;
            String get = representation.get(i);
            if(accum > 1){
                accum--;
                // skip frame
                // System.out.println("SKIPPED: " + get);
            }else{
                representation2.addLast(get);
                // System.out.println("name: " + get);
            }
        }
        System.out.println("accum should be " + accum2);
        System.out.println("final size " + representation2.size());
        // System.exit(0);
        int[] myarr = new int[320*sidelength*320*sidelength];
        for(int q = 0; q < twofiftysix; q++){
            if(q == representation2.size())break;
            var touse = representation2.get(q);
            // bi_i
            // put into tapestry
            // System.out.println(touse);
            if(!(new File(touse).exists()))continue;
            var bi = ImageExpirements.pngToBI(touse);
            var width = bi.getWidth();
            var height = bi.getHeight();
            for(int i = 0; i < width; i++){
                for(int j = 0; j < height; j++){
                    int color = bi.getRGB(i, j);

                    var ii = q%sidelength;
                    var jj = q/sidelength;
                    var dest = ii*320+i + 
                    jj*320*320*sidelength + 
                    j*320*sidelength;
                    myarr[dest] = color;
                }
            }
        }
        ImageExpirements.pixToPng(myarr, 320*sidelength, outputPath);
        stemcellfulltime = localstemcellfulltime;
        stemcellsidelength = sidelength;

        System.out.println("SIDE LENGTH IS DETERMINED TO BE... " + sidelength);

    }
}
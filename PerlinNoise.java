import java.awt.Color;
import java.util.Random;

public class PerlinNoise {
    private int[] permutation;
    
    private long seed;
    private double scale;
    private int octaves;
    private double persistence;
    private double lacunarity;

    /**
     * Construct a PerlinNoise generator with given parameters.
     * 
     * @param seed        A seed for reproducible random permutation generation.
     * @param scale       Larger scale -> "zoomed out"; smaller scale -> "zoomed in".
     * @param octaves     Number of noise layers to combine.
     * @param persistence Amplitude multiplier each octave.
     * @param lacunarity  Frequency multiplier each octave.
     */
    public PerlinNoise(long seed, double scale, int octaves, double persistence, double lacunarity) {
        this.seed = seed;
        this.scale = scale != 0.0 ? scale : 1.0; // avoid division by zero
        this.octaves = octaves;
        this.persistence = persistence;
        this.lacunarity = lacunarity;
        
        // Generate the permutation table
        initPermutation();
    }
    
    /**
     * Initialize the permutation array (size 512) using the provided seed.
     */
    private void initPermutation() {
        int[] p = new int[256];
        // Fill with 0..255
        for (int i = 0; i < 256; i++) {
            p[i] = i;
        }
        
        // Shuffle using our seed
        Random rand = new Random(seed);
        for (int i = 255; i > 0; i--) {
            int swapIndex = rand.nextInt(i + 1);
            int temp = p[i];
            p[i] = p[swapIndex];
            p[swapIndex] = temp;
        }
        
        // Duplicate array to avoid overflow in indexing
        permutation = new int[512];
        for (int i = 0; i < 512; i++) {
            permutation[i] = p[i & 255];
        }
    }

    /**
     * Single-octave 2D Perlin noise at coordinates (x, y).
     * @param x input x-coordinate
     * @param y input y-coordinate
     * @return noise value in [-1, 1]
     */
    private double singleOctaveNoise(double x, double y) {
        // Scale our coordinates
        x /= scale;
        y /= scale;
        
        // Find the unit grid cell containing (x, y)
        int X = (int)Math.floor(x) & 255;
        int Y = (int)Math.floor(y) & 255;
        
        // Relative x, y in cell
        double xf = x - Math.floor(x);
        double yf = y - Math.floor(y);
        
        // Compute fade curves
        double u = fade(xf);
        double v = fade(yf);
        
        // Hash coordinates of the 4 corners
        int aa = permutation[permutation[X    ] + Y    ];
        int ab = permutation[permutation[X    ] + Y + 1];
        int ba = permutation[permutation[X + 1] + Y    ];
        int bb = permutation[permutation[X + 1] + Y + 1];
        
        // Add blended results from the corners
        double x1 = lerp(
                grad(aa, xf,     yf),
                grad(ba, xf - 1, yf),
                u
        );
        double x2 = lerp(
                grad(ab, xf,     yf - 1),
                grad(bb, xf - 1, yf - 1),
                u
        );
        return lerp(x1, x2, v);
    }
    
    /**
     * Multi-octave Perlin noise at (x, y). Sums up several frequencies & amplitudes.
     * 
     * @param x input x-coordinate
     * @param y input y-coordinate
     * @return noise value in [-1, 1]
     */
    public double noise(double x, double y) {
        // If only one octave, just return the single-octave noise
        if (octaves <= 1) {
            return singleOctaveNoise(x, y);
        }
        
        double total = 0.0;
        double maxValue = 0.0; // for normalization
        double amplitude = 1.0;
        double frequency = 1.0;
        
        for (int i = 0; i < octaves; i++) {
            // Scale by frequency
            double n = singleOctaveNoise(x * frequency, y * frequency) * amplitude;
            total += n;
            
            maxValue += amplitude;
            amplitude *= persistence;
            frequency *= lacunarity;
        }
        
        // Normalize to roughly [-1, 1]
        if (maxValue > 0) {
            total /= maxValue;
        }
        
        return total;
    }
    
    /** Perlin's fade function: 6t^5 - 15t^4 + 10t^3. */
    private static double fade(double t) {
        return t * t * t * (t * (t * 6 - 15) + 10);
    }
    
    /** Linear interpolation between a and b, by factor t. */
    private static double lerp(double a, double b, double t) {
        return a + t * (b - a);
    }
    
    /**
     * grad() calculates the dot product between a pseudo-random gradient vector
     * (selected by 'hash') and the vector from the input coordinate to the corner.
     * 
     * @param hash hash value from permutation
     * @param x    relative x
     * @param y    relative y
     * @return dot product
     */
    private static double grad(int hash, double x, double y) {
        // For 2D, we use only the bottom 2 bits of 'hash' to choose a gradient.
        int h = hash & 3;
        
        switch (h) {
            case 0: return  x + y;
            case 1: return  x - y;
            case 2: return -x + y;
            case 3: return -x - y;
        }
        // Should never happen
        return 0;
    }
    
    public long getSeed() {
        return seed;
    }
    
    public double getScale() {
        return scale;
    }
    
    public int getOctaves() {
        return octaves;
    }
    
    public double getPersistence() {
        return persistence;
    }
    
    public double getLacunarity() {
        return lacunarity;
    }
    static double[][] huh = new double[][]{
        { 65,  232,225,24},
        { 50,  172,229,212},
        { 30,  0,122,20},
        { 10,  0,255,0},
        { 0,  255,255,0},
        { -20,  7,9,245},
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
    public static void thingy(double x, double y){
        Color c = InfiniteVoronoiUtil.getColorAt(x, y);
        huh[3][1] = c.getRed();
        huh[3][2] = c.getGreen();
        huh[3][3] = c.getBlue();
        //darker
        huh[2][1] = (int)(c.getRed()*0.8);
        huh[2][2] = (int)(c.getGreen()*0.8);
        huh[2][3] = (int)(c.getBlue()*0.8);
    }

    public static RGB terrainColor2(double elevation){
        RGB rgb = null;
        // set huh
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
    public static int[] lerp(int percentage, int r1, int g1, int b1, int r2, int g2, int b2) {
        return new int[]{
            (r2*100+(r1-r2)*percentage)/100,
            (g2*100+(g1-g2)*percentage)/100,
            (b2*100+(b1-b2)*percentage)/100,
        };
    }
}

import java.util.Random;

class Vor{
    /* x(global), y(global), color */
    static int[][] d = new int[][]{
            {3213, 1236, 0},
            {7522, 4000, 0xff},
            {8229, 2230, 0xcc},
            {13900, 3500, 0xcc_00},
            {1213, 5236, 0xff_00_00},
            {6522, 6000, 0xff_00},
            {10229, 7230, 0xff_ff},
            {12900, 6500, 0xff_ff_00},
    };
    static int[][] k;
    static int boxSize = 64*4*2;
    static int mapWidth = 16384;
    static int mapHeight = 8192;

    static int boxesWide = mapWidth/boxSize;
    static int boxesHigh = mapHeight/boxSize;
    public static Random r = null;
    public static void init(){
        k = new int[boxesHigh*boxesWide][3];
        for(int i = 0; i < boxesWide; i++){
            for(int j = 0; j < boxesHigh; j++){
                k[j*boxesWide+i] = new int[]{
                    i*boxSize + r.nextInt(boxSize),
                    j*boxSize + r.nextInt(boxSize),
                    r.nextInt()
                };
            }
        }
    }
    public static int getColor(int x, int y){
        int tx = x/boxSize;
        int ty = y/boxSize;
        double closest = Integer.MAX_VALUE;
        int color = 0;
        for(int i = -1; i <= 1; i++){ // 9 checks
            for(int j = -1; j <= 1; j++){
                int[] t = getBox(tx+i, ty+j);
                int dx = t[0]-x;
                int dy = t[1]-y;
                double d = Math.sqrt(dx*dx+dy*dy);
                if(d < closest){
                    closest = d;
                    color = t[2];
                }
            }
        }
        {
            int[] t = getBox(tx+2, ty);
            int dx = t[0]-x;
            int dy = t[1]-y;
            double d = Math.sqrt(dx*dx+dy*dy);
            if(d < closest){
                closest = d;
                color = t[2];
            }
        }
        {
            int[] t = getBox(tx-2, ty);
            int dx = t[0]-x;
            int dy = t[1]-y;
            double d = Math.sqrt(dx*dx+dy*dy);
            if(d < closest){
                closest = d;
                color = t[2];
            }
        }
        {
            int[] t = getBox(tx, ty+2);
            int dx = t[0]-x;
            int dy = t[1]-y;
            double d = Math.sqrt(dx*dx+dy*dy);
            if(d < closest){
                closest = d;
                color = t[2];
            }
        }
        {
            int[] t = getBox(tx, ty-2);
            int dx = t[0]-x;
            int dy = t[1]-y;
            double d = Math.sqrt(dx*dx+dy*dy);
            if(d < closest){
                closest = d;
                color = t[2];
            }
        }
        return color;
    }
    static int[] toofar = new int[]{-1000000, -1000000, 0};
    // x = 0,1,2,3 y = 0,1
    public static int[] getBox(int x, int y){
        if(x < 0 || x >= boxesWide || y < 0 || y >= boxesHigh){
            return toofar;
        }
        return k[boxesWide*y+x];
    } 
}
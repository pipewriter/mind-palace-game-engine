
public class KeyNodule extends GameObject{
    static double range = 3;
    public KeyNodule(){
    }
    void _marshal(ParkerBuffer b, boolean shouldwritedata) {
    }
    GameObject _unmarshal(ParkerBuffer b, boolean shouldreaddata) {
        return this;
    }
    public int classid() {
        return 12;
    }
    public void clean() {
    }
    public void load() {}
    
    public GameObject clone() {
        //screw you no cloning
        var rn = new LinkNodule();
        return rn;
    }
    void sectorMarshal(ParkerBuffer b) {
    }
    void sectorUnMarshal(ParkerBuffer b) {
    }
    void activate(){
        System.out.println("KEY NODULE ACTIVATED ACTIVAATED!!");
        //move somewhere different
        Main.arbfunc.run();
        var arr = Main.sectorFunctions.keySet().toArray();
        Long key = (Long)arr[(int)(Math.random()*arr.length)];
        int x = Main.x(key);
        int y = Main.y(key);
        this.x = x+Math.random()*128;
        this.z = y+Math.random()*128;
        var get = Main.sectorFunctions.get(key);
        var hfunc = get == null ? Main.defaultHeightFunction : get.x;
        var params = get == null ? new double[10] : get.a;
        int[] inj = new int[]{
            (int)this.x-x,
            (int)this.z-y,
            1};
        this.ydisp = hfunc.apply(inj,params) + 7;
    }
}
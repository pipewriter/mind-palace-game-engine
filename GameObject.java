abstract class GameObject{
    public static boolean DONT_UNLOAD_MODE_HARD_OVERRIDE = false;
    boolean flagged = false;
    private static Class<GameObject>[] classesLookupTable = new Class[]{
        // REGISTERED CLASSES GO HERE
        Pasty.class, ShinyBauble.class, TextNodule.class, TextNodule2.class, RecordNodule.class, IdeaBeacon.class,
        ThreeDumb.class, LudeonNodule.class, Shader.class, PortalNodule.class, LinkNodule.class, ChestNodule.class,
        KeyNodule.class, FileNodule.class, BurningBush.class, ParkerQuizItem.class, TenPortals.class, ObeliskNodule.class,
        ProtoVideo.class, CompressorNodule.class, ControllerNodule.class,
        FunnelNodule.class, PhantomNodule.class, PromiseNodule.class, FolderNodule.class,
        FunnelNodule2.class, PumpJack.class, FollowPath.class, Searcher.class,
        MultipleChoice.class, Conveyor.class, TtsMachine.class, PressurePlate.class, Crux.class
    };
    abstract void        _marshal(ParkerBuffer b, boolean shouldwritedata);
    abstract GameObject  _unmarshal(ParkerBuffer b, boolean shouldreaddata);
    public abstract int classid();
    public abstract void clean();
    public abstract void load();
    public abstract GameObject clone();

    public double dist(GameObject other){
        double dx = other.x-this.x;
        double dz = other.z-this.z;
        double dist = Math.sqrt(dx*dx+dz*dz);
        return dist;
    }
    public double dist(double x, double z){
        double dx = x-this.x;
        double dz = z-this.z;
        double dist = Math.sqrt(dx*dx+dz*dz);
        return dist;
    }
    public boolean isWritable(){
        return true;
    }
    public boolean isObtainable(){
        return true;
    }
    public static long xy(int x, int y) {
        return (((long)x) << 32) | y;
    }
    public boolean designatedCenterGO(){
        int rendx = (int)(x/128)*128+64; // expirement, if the common 3dumb is placed in the middle too, then 63 63 will be empty
        int rendz = (int)(z/128)*128+64; // expirement, if the common 3dumb is placed in the middle too, then 63 63 will be empty
        return rendx + 0.1 > x && rendx - 0.1 < x &&  rendz + 0.1 > z && rendz - 0.1 < z; 
    }
    public long getSectorKey(){
        int nlx7 = (int)(x/128);
        int nlz7 = (int)(z/128);
        return xy(nlx7, nlz7);
    }
    public static long getSectorKeyGiven(int x, int z){
        int nlx7 = (int)(x/128);
        int nlz7 = (int)(z/128);
        return xy(nlx7, nlz7);
    }
    public long getRegionKey(){
        int x = (int)this.x-1_000_000;
        int y = (int)this.z-1_000_000;
        int nlx7=x/4096*4096+2048+1_000_000;
        int nlz7=y/4096*4096+2048+1_000_000;
        return xy(nlx7, nlz7);
    }
    public static long getRegionKey(double xx, double zz){
        int x = (int)xx-1_000_000;
        int y = (int)zz-1_000_000;
        int nlx7=x/4096*4096+2048+1_000_000;
        int nlz7=y/4096*4096+2048+1_000_000;
        return xy(nlx7, nlz7);
    }

    public static void marshal(ParkerBuffer b, GameObject go, boolean shouldreaddata){
        b.putInt(go.classid());
        // if(b instanceof ParkerSizer) System.out.println(((ParkerSizer)b).sizeCounter);
        go._marshal(b, shouldreaddata);
        // if(b instanceof ParkerSizer) System.out.println(((ParkerSizer)b).sizeCounter);
        b.putDouble(go.rot);
        b.putDouble(go.x);
        b.putDouble(go.ydisp);
        b.putDouble(go.z);
        b.putDouble(go.size);
        // if(b instanceof ParkerSizer) System.out.println(((ParkerSizer)b).sizeCounter);
        // if(b instanceof ParkerSizer) System.out.println(go.getClass());
        // if(b instanceof ParkerSizer )
        //     System.exit(0);
    }
    public void assumeApproximatePositionOf(GameObject other){
        rot = other.rot;
        x = other.x;
        z = other.z+0.00001; // for quadtree
        ydisp = other.ydisp;
        size = other.size;
    }
    public static GameObject unmarshal(ParkerBuffer b, boolean shouldreaddata){
        GameObject go = null;
        int marshalLookupIndex = b.getInt();
        // System.out.println("unmarshalling" + marshalLookupIndex);
        // int marshalLookupIndex = 0;
        try{
            go =classesLookupTable[marshalLookupIndex].getConstructor().newInstance();
        }catch(Exception e){
            e.printStackTrace();
            System.exit(0);
        }
        go._unmarshal(b, shouldreaddata);
        go.rot = b.getDouble();
        go.x = b.getDouble();
        go.ydisp = b.getDouble();
        go.z = b.getDouble();
        go.size = b.getDouble();
        return go;
    }
    boolean step = true;
    public double x;
    public double ydisp;
    public double z;
    double rot;
    double size = 1;
    abstract void sectorMarshal(ParkerBuffer b);
    abstract void sectorUnMarshal(ParkerBuffer b);
}

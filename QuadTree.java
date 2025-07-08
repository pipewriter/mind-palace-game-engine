
/******************************************************************************
 *  Compilation:  javac QuadTree.java
 *  Execution:    java QuadTree M N
 *
 *  Quad tree.
 * 
 ******************************************************************************/
import java.util.*;
public class QuadTree  {
    private Node root;

    // helper node data type
    private class Node {
        Double x, y;              // x- and y- coordinates
        Node NW, NE, SE, SW;   // four subtrees
        GameObject value;           // associated data

        Node(Double x, Double y, GameObject value) {
            this.x = x;
            this.y = y;
            this.value = value;
        }
    }


  /***********************************************************************
    *  Insert (x, y) into appropriate quadrant
    ***************************************************************************/
    public void insert(GameObject value) {
        root = insert(root, value.x, value.z, value);
    }
    public void add(GameObject value) { // alternate name
        if(engaged_modified_collecting){
            Main.modifiedSectors.add(value.getSectorKey());
        }
        root = insert(root, value.x, value.z, value);
    }
    public void insert(Double x, Double y, GameObject value) {
        root = insert(root, x, y, value);
    }
    
    public boolean update(GameObject value, double oldX, double oldY, double desiredX, double desiredY) {
        long s1 = GameObject.getSectorKeyGiven((int)oldX, (int)oldY);
        long s2 = GameObject.getSectorKeyGiven((int)desiredX, (int)desiredY);

        //actually, I need them no matter what
        // if(s1 != s2){
        //     System.out.println("yo");
        //     System.exit(0);
        // } 
        Main.neededNow.add(s1);
        Main.neededNow.add(s2);
        
        boolean bothinplay = Main.InPlay.contains(s1) && Main.InPlay.contains(s2);

        if(!bothinplay){
            return false;
        }
        // the boolean is if successful

        // about to intertwine with Main.neededSectors

        // Remove the object from its old position.
        // (This method uses the provided old coordinates so that it finds the correct node.)
        remove(oldX, oldY);
        value.x = desiredX;
        value.z = desiredY;

        // Now insert the object with its new coordinates.
        // (The GameObject's x and z fields are assumed to have been updated already.)
        // insert(value.x, value.z, value);
        add(value);
        return true;
    }

    private Node insert(Node h, Double x, Double y, GameObject value) {
        if (h == null) return new Node(x, y, value);
        //// if (eq(x, h.x) && eq(y, h.y)) h.value = value;  // duplicate
        else if ( less(x, h.x) &&  less(y, h.y)) h.SW = insert(h.SW, x, y, value);
        else if ( less(x, h.x) && !less(y, h.y)) h.NW = insert(h.NW, x, y, value);
        else if (!less(x, h.x) &&  less(y, h.y)) h.SE = insert(h.SE, x, y, value);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = insert(h.NE, x, y, value);
        return h;
    }
    public boolean engaged_modified_collecting = true;
    public void remove(GameObject value) {
        
        if(value == null)return;
        if(engaged_modified_collecting){
            Main.modifiedSectors.add(value.getSectorKey());
        }


        root = remove(root, value.x, value.z);

        // System.out.println("Adding back... " + descendants.size());
        for (Node descendant : descendants) {
            if (!eq(descendant.x, value.x) || !eq(descendant.y, value.z)) {
                // Avoid re-inserting the node we just removed
                insert(descendant.x, descendant.y, descendant.value);
            }
        }
        descendants = new ArrayList<>();
    }

    public void remove(Double x, Double y) {
        root = remove(root, x, y);
        // Re-insert descendants
        // System.out.println(root.x +" root " +  root.y);
        // System.out.println("Adding back... " + descendants.size());

        for (Node descendant : descendants) {
            if (!eq(descendant.x, x) || !eq(descendant.y, y)) {
                // Avoid re-inserting the node we just removed
                insert(descendant.x, descendant.y, descendant.value);
            }
        }
        descendants = new ArrayList<>();
    }
    private List<Node> descendants = new ArrayList<>();

    private Node remove(Node h, Double x, Double y) {
        if (h == null) return null;
        if (eq(x, h.x) && eq(y, h.y)) {
            // Collect and re-insert all descendants of h
            List<Node> descendants = new ArrayList<>();
            collectDescendants(h, descendants);
            h = null; // Remove the node
            this.descendants = descendants;
            return h;
        }

        // Recursive case: proceed to the correct quadrant
        if (less(x, h.x) && less(y, h.y)) h.SW = remove(h.SW, x, y);
        else if (less(x, h.x) && !less(y, h.y)) h.NW = remove(h.NW, x, y);
        else if (!less(x, h.x) && less(y, h.y)) h.SE = remove(h.SE, x, y);
        else if (!less(x, h.x) && !less(y, h.y)) h.NE = remove(h.NE, x, y);

        return h;
    }
    private void collectDescendants(Node h, List<Node> descendants) {
        if (h == null) return;

        descendants.add(h);
        collectDescendants(h.NW, descendants);
        collectDescendants(h.NE, descendants);
        collectDescendants(h.SW, descendants);
        collectDescendants(h.SE, descendants);
    }

    private boolean isLeaf(Node h) {
        return h.NW == null && h.NE == null && h.SE == null && h.SW == null;
    }
  /***********************************************************************
    *  Range search.
    ***************************************************************************/

    public int gathermeindex = 0;
    public GameObject[] gatherme = new GameObject[1_000_000];
    public void query2D(Interval2D<Double> rect) {
        gathermeindex = 0;
        query2D(root, rect);
    }
    private void query2D(Node h, Interval2D<Double> rect) {
        if (h == null) return;
        Double xmin = rect.intervalX.min();
        Double ymin = rect.intervalY.min();
        Double xmax = rect.intervalX.max();
        Double ymax = rect.intervalY.max();
        //this is the payload right here
        if (rect.contains(h.x, h.y)){
            if(gathermeindex < gatherme.length)
                gatherme[gathermeindex++] = h.value;
            // System.out.println("    (" + h.x + ", " + h.y + ") " + h.value);
        }
        if ( less(xmin, h.x) &&  less(ymin, h.y)) query2D(h.SW, rect);
        if ( less(xmin, h.x) && !less(ymax, h.y)) query2D(h.NW, rect);
        if (!less(xmax, h.x) &&  less(ymin, h.y)) query2D(h.SE, rect);
        if (!less(xmax, h.x) && !less(ymax, h.y)) query2D(h.NE, rect);
    }

    public synchronized void query2D(Interval2D<Double> rect, GameObject[] gatherme2, int[] gathermeindex) {
        gathermeindex[0] = 0;
        query2D(root, rect,gatherme2, gathermeindex);
    }
    private synchronized void query2D(Node h, Interval2D<Double> rect, GameObject[] gatherme2, int[] gathermeindex) {
        if (h == null) return;
        Double xmin = rect.intervalX.min();
        Double ymin = rect.intervalY.min();
        Double xmax = rect.intervalX.max();
        Double ymax = rect.intervalY.max();
        //this is the payload right here
        if (rect.contains(h.x, h.y)){
            if(gathermeindex[0] < gatherme2.length)
                gatherme2[gathermeindex[0]++] = h.value;
            // System.out.println("    (" + h.x + ", " + h.y + ") " + h.value);
        }
        if ( less(xmin, h.x) &&  less(ymin, h.y)) query2D(h.SW, rect,gatherme2, gathermeindex);
        if ( less(xmin, h.x) && !less(ymax, h.y)) query2D(h.NW, rect,gatherme2, gathermeindex);
        if (!less(xmax, h.x) &&  less(ymin, h.y)) query2D(h.SE, rect,gatherme2, gathermeindex);
        if (!less(xmax, h.x) && !less(ymax, h.y)) query2D(h.NE, rect,gatherme2, gathermeindex);
    }

   /***************************************************************************
    *  helper comparison functions
    ***************************************************************************/

    private boolean less(Double k1, Double k2) { return k1.compareTo(k2) <  0; }
    private boolean eq  (Double k1, Double k2) { return k1.compareTo(k2) == 0; }

    public void query2D(double xmin, double xmax, double ymin, double ymax){
        Interval<Double> intX = new Interval<Double>(xmin, xmax);
        Interval<Double> intY = new Interval<Double>(ymin, ymax);
        Interval2D<Double> rect = new Interval2D<Double>(intX, intY);
        query2D(rect);
    }

    public void queryAll(){
        double xmin = 0;
        double xmax = 2_000_000;
        double ymin = 0;
        double ymax = 2_000_000;
        Interval<Double> intX = new Interval<Double>(xmin, xmax);
        Interval<Double> intY = new Interval<Double>(ymin, ymax);
        Interval2D<Double> rect = new Interval2D<Double>(intX, intY);
        query2D(rect);
    }
    public synchronized void queryAll(GameObject[] gatherme, int[] gathermeindex){
        double xmin = 0;
        double xmax = 2_000_000;
        double ymin = 0;
        double ymax = 2_000_000;
        Interval<Double> intX = new Interval<Double>(xmin, xmax);
        Interval<Double> intY = new Interval<Double>(ymin, ymax);
        Interval2D<Double> rect = new Interval2D<Double>(intX, intY);
        query2D(rect, gatherme, gathermeindex);
    }
    public synchronized void queryAll(GameObject[] gatherme, int[] gathermeindex, double xmin, double xmax, double ymin, double ymax){
        // double xmin = 0;
        // double xmax = 2_000_000;
        // double ymin = 0;
        // double ymax = 2_000_000;
        Interval<Double> intX = new Interval<Double>(xmin, xmax);
        Interval<Double> intY = new Interval<Double>(ymin, ymax);
        Interval2D<Double> rect = new Interval2D<Double>(intX, intY);
        query2D(rect, gatherme, gathermeindex);
    }
    public int size(){
        // queryAll();
        return gathermeindex;
    }
    public GameObject getAt(int i){
        return gatherme[i];
    }
    public GameObject get(int i){
        return gatherme[i];
    }
    public boolean isEmpty(){
        // queryAll();
        return gathermeindex == 0;
    }



   /***************************************************************************
    *  test client
    ***************************************************************************/
    // public static void main(String[] args) {
    //     // int M = Integer.parseInt(args[0]);   // queries
    //     int M = 100;   // queries
    //     // int N = Integer.parseInt(args[1]);   // points
    //     int N = 100;   // points

    //     QuadTree<Integer, String> st = new QuadTree<Integer, String>();

    //     // insert N random points in the unit square
    //     for (int i = 0; i < N; i++) {
    //         Integer x = (int) (100 * Math.random());
    //         Integer y = (int) (100 * Math.random());
    //         // StdOut.println("(" + x + ", " + y + ")");
    //         st.insert(x, y, "P" + i);
    //     }
    //     System.out.println("Done preprocessing " + N + " points");

    //     // do some range searches
    //     for (int i = 0; i < M; i++) {
    //         Integer xmin = (int) (100 * Math.random());
    //         Integer ymin = (int) (100 * Math.random());
    //         Integer xmax = xmin + (int) (10 * Math.random());
    //         Integer ymax = ymin + (int) (20 * Math.random());
    //         Interval<Integer> intX = new Interval<Integer>(xmin, xmax);
    //         Interval<Integer> intY = new Interval<Integer>(ymin, ymax);
    //         Interval2D<Integer> rect = new Interval2D<Integer>(intX, intY);
    //         System.out.println(rect + " : ");
    //         st.query2D(rect);
    //     }
    //     {
            
    //         Interval<Integer> intX = new Interval<Integer>(0, 100);
    //         Interval<Integer> intY = new Interval<Integer>(0, 100);
    //         Interval2D<Integer> rect = new Interval2D<Integer>(intX, intY);
    //         System.out.println(rect + " : ");
    //         st.query2D(rect);

    //         System.out.println("now there are " + st.gathermeindex);
    //         st.query2D(rect);
    //         System.out.println("now there are " + st.gathermeindex);

    //         st.insert(1, 0, "P");
    //         st.query2D(rect);
            
    //         System.out.println("now there are " + st.gathermeindex);
    //         st.remove(1,0);
    //         st.insert(1, 0, "P");
    //         st.remove(1,0);
    //         st.query2D(rect);

    //         System.out.println("now there are " + st.gathermeindex);


    //     }
    // }

}

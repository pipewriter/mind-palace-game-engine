import java.util.*;

// import org.lwjgl.system.CallbackI.J;
// import org.lwjgl.system.CallbackI.P;

//BASICALLY CHAR OR DOODAD
class RichChar {
    char c;
    Pasty p;
    public RichChar(char c){
        this.c = c;
    }
    public RichChar(Pasty p){
        this.p = p;
    }
    public RichChar clone(){
        if(p != null) return new RichChar(p.clone());
        return new RichChar(c);
    }
}
class State {
    public Object parent;
    public int highlightedr1 = 0;
    public int highlightedc1 = 0;
    public int highlightedr2 = 0;
    public int highlightedc2 = 0;

    public State clone(){
        State s = new State();
        s.rows = new ArrayList<ArrayList<RichChar>>();
        for(var r: rows){
            var rr = new ArrayList<RichChar>();
            for(var e: r){
                rr.add(e.clone());
            }
            s.rows.add(rr);
        }
        s.cx = cx;
        s.cy = cy;
        return s;
    }
    
    public ArrayList<RichChar> cloneRow(){
        // for(var r: rows){
            var r = rows.get(cy);
            var rr = new ArrayList<RichChar>();
            for(var e: r){
                rr.add(e.clone());
            }
            return rr;
            // s.rows.add(rr);
        // }
        // s.cx = cx;
        // s.cy = cy;
        // return s;
    }
    public void dupeRow(){
        rows.add(cy, cloneRow());
    }

    public State(){
        rows.add(0, new ArrayList<RichChar>());
        // curb();

    }
    public  ArrayList<ArrayList<RichChar>> rows = new ArrayList<ArrayList<RichChar>>();

    public int longest(){
        int longest = 0;
        for(var r: rows){
            longest = Math.max(longest, r.size());
        }
        return longest;
    }
    public void marshal(ParkerBuffer pb){
        tempList.clear();
        pb.putInt(cx);
        pb.putInt(cy);
        pb.putInt(rows.size());
        // System.out.println(rows.size());
        for(var row: rows){
            pb.putInt(row.size());
            for(var cha: row){
                if(cha.p != null){
                    pb.putInt(0b1000_0000_0000_0000__0000_0000_0000_0000);
                    cha.p._marshal(pb, false);
                    cha.p.sectorMarshal(pb);
                    tempList.push(cha.p);
                }else{
                    pb.putInt(cha.c);
                }
            }
        }
    }
    LinkedList<Pasty> tempList = new LinkedList<Pasty>();
    public void specialClean(){
        rows.clear();
        for(var e: tempList){
            e.clean();
            // System.out.println("cleaned");
        }
    }
    public void specialLoad(){
        for(var e: tempList){
            e.load();
            // System.out.println("loaded");
        }
    }
    public void unmarshal(ParkerBuffer pb){
        tempList.clear();
        cx = pb.getInt();
        cy = pb.getInt();
        int rc = pb.getInt();
        if(rc > 10000) return;
        for(int i = 0; i < rc; i++){
            int rs = pb.getInt();
            rows.add(i, new ArrayList<RichChar>());
            for(int j = 0; j < rs; j++){
                if(rs > 10000) return;
                int val = pb.getInt();
                if(val == 0b1000_0000_0000_0000__0000_0000_0000_0000){
                    var p = new Pasty();
                    p._unmarshal(pb, false);
                    p.sectorUnMarshal(pb);
                    tempList.push(p);
                    RichChar rich = new RichChar(p);
                    rows.get(i).add(j, rich);
                }else{
                    RichChar rich = new RichChar((char)val);
                    rows.get(i).add(j, rich);
                }
            }
        }
        // System.out.println(rows.size());
        // System.out.println("DONE");
        // cy = 1000;
        curb();
    }
    int cx, cy;
    public ArrayList<RichChar> getRow(int cy){
        if(cy >= 0 && cy < rows.size()){
            return rows.get(cy);
        }
        return null;
    }
    public  boolean swap(boolean up){
        var currow = rows.get(cy);
        var above = getRow(cy+1);
        var below =  getRow(cy-1);
        if(up && above != null){
            rows.set(cy, above);
            rows.set(cy+1,currow);
            cy++;
        }
        if(!up && below != null){
            rows.set(cy, below);
            rows.set(cy-1,currow);
            cy--;
        }
        int tempcy = cy;
        curb();
        return tempcy == cy;
    }
    public  boolean dec(){
        cy++;
        int tempcy = cy;
        curb();
        return tempcy == cy;
    }
    public  boolean asc(){
        cy--;
        int tempcy = cy;
        curb();
        return tempcy == cy;
    }
    public  int desiredCX = 0;
    public  void curb(){
        cy = Math.min(cy, rows.size()-1);
        cy = Math.max(cy, 0);
        cx = desiredCX;
        if(rows.size() >cy )
        cx = Math.min(cx, rows.get(cy).size());
        cx = Math.max(cx, 0);
    }
    public  void insert(char c){
        RichChar nu = new RichChar(c);
        insert(nu);
    }
    public  void insert(RichChar nu){
        // if()
        //the crux of the issue has to do with
        rows.get(cy).add(cx, nu);
        cx++;
        desiredCX++;
        curb();
    }
    public boolean hasHighlight(){
        System.out.println("----"+highlightedc1);
        System.out.println("----"+highlightedc2);
        System.out.println("----"+highlightedr1);
        System.out.println("----"+highlightedr2);
        boolean match = highlightedc1 == highlightedc2 && highlightedr1 == highlightedr2;
        return !match;
    }
    public String cutLine(){
        var s = getLine();
        rows.remove(cy);
        if(rows.size() == 0)rows.add(new ArrayList<>());
        desiredCX = 0;
        cx = 0;
        curb();
        return s;
    }
    public String cutHighlight(){
        String buildup = "";
        var s = this;
        var ll = rows.size();
        boolean ishighlight = false;

        for(int k = 0; k < ll; k++){
            int l = rows.get(k).size();
            for(int j = 0; j < l; j++){
                ishighlight = false;
                if(s.highlightedr1 < k || (s.highlightedr1 == k && s.highlightedc1 <= j)){
                    if(s.highlightedr2 > k || (s.highlightedr2 == k && s.highlightedc2 > j)){
                        ishighlight = true;
                    }
                }
                if(ishighlight){
                    // remove
                    buildup += rows.get(k).remove(j).c;
                    j--;
                    l--;
                    if(s.highlightedr2 == k)
                        highlightedc2--;
                }
                // ishighlight = false;
                // if(s.highlightedr1 < k || (s.highlightedr1 == k && s.highlightedc1 <= j)){
                //     if(s.highlightedr2 > k || (s.highlightedr2 == k && s.highlightedc2 > j)){
                //         ishighlight = true;
                //     }
                // }
            }
            System.out.println("checking...");
            if(ishighlight){
                System.out.println("Is highlight...");
                if(k < ll){
                    System.out.println("ok...");
                    var idk = rows.remove(k+1);
                    if(highlightedr2 == k+1){
                        System.out.println("of course adding");
                        System.out.println(highlightedc2);
                        System.out.println(rows.get(k).size());
                        highlightedc2 += rows.get(k).size();
                    }
                    rows.get(k).addAll(idk);
                    ll--;
                    k--;
                    highlightedr2--;
                    buildup += "\n";
                }
            }
        }
        return buildup;
    }
    public String copyHighlight(){
        String buildup = "";
        var s = this;
        var ll = rows.size();
        boolean ishighlight = false;
        for(int k = 0; k < ll; k++){
            int l = rows.get(k).size();
            for(int j = 0; j < l; j++){
                System.out.println("----");
                System.out.println("----"+s.highlightedc1);
                System.out.println("----"+s.highlightedc2);
                System.out.println("----"+s.highlightedr1);
                System.out.println("----"+s.highlightedr2);
                System.out.println("----"+k);
                System.out.println("----"+j);
                ishighlight = false;
                if(s.highlightedr1 < k || (s.highlightedr1 == k && s.highlightedc1 <= j)){
                    if(s.highlightedr2 > k || (s.highlightedr2 == k && s.highlightedc2 > j)){
                        ishighlight = true;
                    }
                }
                System.out.println("-------"+ishighlight);
                if(ishighlight){
                    // remove
                    buildup += rows.get(k).get(j).c;
                    // j--;
                    // l--;
                }
                ishighlight = false;
                if(s.highlightedr1 < k || (s.highlightedr1 == k && s.highlightedc1 <= j)){
                    if(s.highlightedr2 > k || (s.highlightedr2 == k && s.highlightedc2 > j)){
                        ishighlight = true;
                    }
                }
            }
            if(ishighlight)
                buildup += "\n";
        }
        return buildup;

    }
    public Pasty getPic(){
        curb();
        // if(cy <= rows.size()) return null;
        if(cx >= rows.get(cy).size()) return null;
        return rows.get(cy).get(cx).p;
    }
    public String getLine(){
        StringBuilder sb = new StringBuilder();
        
        for(var g: rows.get(cy)){

            sb.append(g.c);
        }
        sb.append("\r\n");
        sb.append("\r\n");
        return sb.toString();
    }
    public String getLineAlone(){
        StringBuilder sb = new StringBuilder();
        // System.out.println("WTF");
        for(var g: rows.get(cy)){
            // System.out.println("EEEE");
            sb.append(g.c);
        }
        // System.out.println("HELL" +sb.toString());
        return sb.toString();
    }
    public LinkedList<String> getLines(){
        LinkedList<String> idk = new LinkedList<String>();
        for(var k: rows){
            StringBuilder sb = new StringBuilder();
            for(var g: k){
                // System.out.println("EEEE");
                sb.append(g.c);
            }
            idk.add(sb.toString());
        }
        return idk;
    }
    public String stringify(){
        return stringify("\r\n");
    }
    public String stringify(String joiner){
        StringBuilder sb = new StringBuilder();
        for(var k: rows){
            for(var g: k){
                if(g.p != null){
                    var data = g.p.data;
                    int checksum = 0;
                    for(var e: data){
                        checksum = checksum^e;
                    }
                    sb.append("z"+Math.abs(checksum));
                }else{
                    sb.append(g.c);
                }
            }
            sb.append(joiner);
        }
        sb.append(joiner);
        return sb.toString();
    }
    public  void insert(String s){
        for(char c: s.toCharArray()){
            RichChar nu = new RichChar(c);
            // if()
            rows.get(cy).add(cx, nu);
            cx++;
            desiredCX++;
            curb();
        }
    }
    public  void enter(){
        enter(true);   
    }
    public  void conenter(){
        ArrayList<RichChar> nu = new ArrayList<>();
        cy++;
        rows.add(cy, nu);
        cx = 0;
        desiredCX = cx;
        curb();
    }

    public  void enter(boolean spaceShennanigans){
        ArrayList<RichChar> cur = rows.get(cy);
        ArrayList<RichChar> nu = new ArrayList<>();
        int uh = 0;
        int c = 0;
        if(spaceShennanigans){
            while(uh < cur.size() && cur.get(uh++).c == ' '){
                nu.add(new RichChar(' '));
                c++;
            }
        }
        for(int i = 0; i < cur.size(); i++){
            if(i >= cx){
                nu.add(cur.remove(i));
                i--;
            }
        }
        cy++;
        rows.add(cy, nu);
        cx = c;
        desiredCX = cx;
        curb();

    }
    

    public  void bigBackspace(){
        if(cx == 0){
            backspace();
        }else{
            for(int i = 0; i < 7; i++){
                if(cx != 0) backspace();
            }
        }
    }
    public  void backspace(){
        if(cx > 0){
            rows.get(cy).remove(cx-1);
            cx--;
            desiredCX--;
        }
        else if(cx == 0){
            if(cy != 0){
                ArrayList<RichChar> prev = rows.get(cy-1);
                cx=prev.size(); 
                desiredCX = cx;
                prev.addAll(prev.size(), rows.remove(cy));
                cy--;
            }
        }
    }
    public  void delete(){
        if(rows.get(cy).size() > cx){
            System.out.println("delete inside");
            rows.get(cy).remove(cx);
            // cx--;
            // desiredCX--;
        }
        // else if(cx == 0){
        //     if(cy != 0){
        //         ArrayList<RichChar> prev = rows.get(cy-1);
        //         cx=prev.size(); 
        //         desiredCX = cx;
        //         prev.addAll(prev.size(), rows.remove(cy));
        //         cy--;
        //     }
        // }
    }
    public  void home(){
        cx = 0;
    }
    public  void end(){
        cx = 1_000_000;
        desiredCX = cx;
        curb();
    }

    public  void top(){
        cy = 0;
    }
    public  void bot(){
        cy = 1_000_000;
        curb();
    }
    public  void left(){
        cx--;
        desiredCX = cx;
        curb();
    }
    public  void right(){
        cx++;
        desiredCX = cx;
        curb();
    }

}
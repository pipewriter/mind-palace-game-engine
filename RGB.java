
public class RGB {
public int r;
public int g;
public int b;
public RGB(int r, int g, int b){
this.r = r;
this.g = g;
this.b = b;
}

public static RGB fromCombined(int rgb){
int b = (rgb>>>0)&255;
int g = (rgb>>>8)&255;
int r = (rgb>>>16)&255;
// System.out.println("HEHEHEH EHEH "+ r+" "+ g +" "+ b + "   " + rgb);
return new RGB(r,g,b);
}
public int toInt(){
return ((255<<24)|(r&255)<<16) |
((g&255)<<8) |
((b&255<<0));
}
}
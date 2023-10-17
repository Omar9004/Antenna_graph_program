package Uppgiftspaket_4;
/*@project Uppgiftspaket_4
 *@author Omar Alfakir
 */
public class Node_G implements Comparable<Node_G>{

    private int x;
    private int y;
    private int r;
    private int linked;

    public Node_G(int x, int y,int r){
        this.x = x;
        this.y = y;
        this.r = r;
        linked=0;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public String toString(){
        return "{ "+"X:"+getX()+", Y:"+getY()+", R:"+getR()+" }";
    }
    public void increment(){
        linked++;
    }
    public int getLinked() {
        return linked;
    }

    @Override
   public int compareTo(Node_G o) {
        int d = (int)(Math.sqrt(Math.pow((o.getX() - this.getX()),2)+Math.pow((o.getY() - this.getY()),2))); //Calculate the distance between two circles origo
        int R = (o.getR()+this.getR());
        if(d<R && d!=0)//The compared circles are overlapped.
            return -1;
        else if(d>R){//Non-overlap
            return 1;
        }
        return 0;// Circles are congruent.
    }
}

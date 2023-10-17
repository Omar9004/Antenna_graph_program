package Uppgiftspaket_4;
/*@project Uppgiftspaket_4
 *@author Omar Alfakir
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
/*
* My algorithm to solve this Graph exercise illustrates as following:
* 1.Scan the files(Karta1.txt or Karta2.txt) that contain the coordinates of each node.
* 2.Save the scanned coordinates in Hashtable that takes String as key and Node_G object (Node_G Object contains x,y,etc. coordinates of each node)
* OBS- I chose Hashtable data structure to save the coordinates because this data structure offer access time complexity as O(1) in best case and O(N) in worst case
* which works perfectly when it comes to Draw a huge number of circles.
* 3.Draw these nodes using StdDraw api DrawPath(Hashtable<String, Node_G> table, int v1, int v2)
* 4.Fill these nodes in the Graph and link each node to their neighbors.Using  create_GNet(Hashtable<String,Node_G> table) and get_neighbors(Hashtable<String,Node_G) functions.
* I got benefit of compareTo method in Node_G object to calculate the distance between circles center.And if they overlap each other or not.
* 5.Before drawing the path between two nodes. We have to check whether the both two are sharing the same network section.
* To do that I used depth first search(Class) to make sure that both nodes can be connected,and I use the depth first search to calculate the distance between searched nodes.
* 6.To draw the path I used breadth first search to draw the nearest path between two nodes.
* 7. When you run the program it will draw all circle with their respective nodes numbers. Afterwards, the program will ask you in the terminal to type in the first node to begin with.
* Then which node you want to find the path to.
* */
public class Graph {
    private int V;
    private int E;
    private LinkedList<Integer>[] adj;
    private ArrayList<Integer> keys;

   public Graph(int V)
   {
       this.V = V;
       adj = (LinkedList<Integer>[]) new LinkedList[V];
       keys = new ArrayList<>();
       for (int i = 0; i < V; i++)
           adj[i] = new LinkedList<Integer>();
   }
   
   public int V()
   {
       return V;
   }    
   
   
   public int E()
   {
       return E;
   }

   
   public void addEdge(int v, int w)
   {
       if (v < 0 || v >= V || w < 0 || w >= V)
       {
           throw new IndexOutOfBoundsException("No such node!");
       }
       
       adj[v].add(w);
       adj[w].add(v);
       E++;
   }
   
   
   public Iterable<Integer> adj(int v)
   {
       return adj[v];
   }

   private Hashtable<String, Node_G> get_neighbors(Hashtable<String, Node_G> list, Node_G node){
       Hashtable<String, Node_G>temp = new Hashtable<String, Node_G>();
       String k;
       String l;
       for(int i =0 ; i< list.size();i++){
           k = "Node:"+i;
           if(node.compareTo(list.get(k))==-1){
               l="Line"+i;
               keys.add(i);
               temp.put(l,list.get(k));
               node.increment();
           }
       }
       return temp;
   }
   /*
   *The depth-first search is used in this function to:
   * 1-Find out if two nodes are connected and share the same network
   * 2-The distance between two nodes.
   * */
   public boolean connected(int v1, int v2){
       DepthFirstPaths dfs = new DepthFirstPaths(this,v1);
       Stack<Integer> iterator = (Stack<Integer>) dfs.pathTo(v2);
       System.out.println(iterator.toString());
       System.out.println("The distance between "+v1+" and "+v2+" is "+dfs.getDistance()+" nodes");
       System.out.println("The size of network section that contain both nodes "+v1+
               " and "+v2+ " are "+ dfs.getNet_size() );
       return iterator!=null;
   }

   public void DrawCircles(Hashtable<String, Node_G> table){
       String k;
       for(int i=0; i< table.size(); i++){
           StdDraw.setPenColor(StdDraw.BLACK);
           k="Node:"+i;
           StdDraw.circle(table.get(k).getX(), table.get(k).getY(), table.get(k).getR());
           StdDraw.text(table.get(k).getX(), table.get(k).getY(),String.valueOf(i));
       }
   }
   /*
   * The breadth-first search is used in this function to:
   * Find the nearest path between two nodes that share the same network.
   * Moreover, This function draw the found path between these two nodes as blue line.
   * */
   public void DrawPath(Hashtable<String, Node_G> table, int v1, int v2){
       BreadthFirstPaths bfs = new BreadthFirstPaths(this,v1);
       Stack<Integer> iterator = (Stack<Integer>) bfs.pathTo(v2);
       if(connected(v1,v2)) {
           int j = 0;
           String k;
           for (int i = iterator.size() - 1; i > 0; i--) {
               j = iterator.pop();
               k = "Node:" + j;

               StdDraw.setPenColor(StdDraw.BLUE);
               StdDraw.line(table.get(k).getX(), table.get(k).getY(), table.get("Node:" + iterator.get(i - 1)).getX(), table.get("Node:" + iterator.get(i - 1)).getY());
           }
       }else {
           System.out.println("The Nodes are not connected.");
       }
   }

   private ArrayList<Integer>keys(){
       return keys;
   }
   /*
   * This function fils the graph with nodes and link each node with their neighbors.
   * */
   public void create_GNet(Hashtable<String,Node_G> table){
       String k;
       int k1;
       for(int i=0 ; i<table.size();i++) {
           k="Node:"+i;
           Hashtable<String, Node_G> Line_table = get_neighbors(table, table.get(k));
           for (int j = 0; j < Line_table.size(); j++) {
               k1 = keys().get(j); //K1 contains the edges to a node.
               addEdge(i, k1);
           }
           keys().removeAll(keys());
       }
   }

   public static void main(String[] cmdLn) {
       Hashtable<String,Node_G> table = new Hashtable<>();
       StdDraw.setXscale(0,1000);
       StdDraw.setYscale(0,1000);

       //Read coordinates of nodes from file "Karta1.txt" or "Karta1.txt" and insert them in hashtable as node object (Node_G))
       try (
            Scanner scanner = new Scanner(new FileInputStream("Karta1.txt"))) {
           int i =0;
           while(scanner.hasNext()){
               Node_G node = new Node_G(0,0,0);
               scanner.nextLine();
               scanner.next();
               node.setX(scanner.nextInt());
               scanner.nextLine();
               scanner.next();
               node.setY(scanner.nextInt());
               scanner.nextLine();
               scanner.next();
               node.setR(scanner.nextInt());
               table.put("Node:"+ i++, node);
               scanner.nextLine();
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }
       Graph g = new Graph(table.size());

       //Draw Circles from using these nodes from hashtable.
       g.DrawCircles(table);
       // Fill Graph with nodes from hashtable and link each node with their neighbors.
       g.create_GNet(table);
//       System.out.println(g.adj(42));
       boolean flag = true;
       int s1=0;
       int s2=0;
       while(flag) {
           Scanner in = new Scanner(System.in);
           System.out.println("Skriv den första antenns nummer!");
           s1 = in.nextInt();
           System.out.println("Den andra antennen som (den första antennen)ska kopplas till!");
           s2 = in.nextInt();

           if(g.connected(s1,s2)){
               flag=false;
           }else{
               System.out.println("De antennerna är inte i samma nätverk, försök igen!!");
           }
       }
       System.out.println("The neighbors of node "+s1+" are "+table.get("Node:"+s1).getLinked()+ " that share the same network");
       System.out.println("The neighbors of node "+s2+" are "+table.get("Node:"+s2).getLinked()+ " that share the same network");


       g.DrawPath(table,s1,s2);

   }

}

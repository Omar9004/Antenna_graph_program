package Uppgiftspaket_4;
/*@project Uppgiftspaket_4
 *@author Omar Alfakir
 * OBS- This implementation of DepthFirstPaths has been taken from Algorithms book
 */

import java.util.Stack;

public class DepthFirstPaths
{
    private boolean[] marked; // Has dfs() been called for this vertex?
    private int[] edgeTo; // last vertex on known path to this vertex
    private final int s; // source
    private int distance; //distance counter
    private int Net_size;

    public DepthFirstPaths(Graph G, int s)
    {
        distance=0;
        Net_size=1;
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        dfs(G, s);
    }
    private void dfs(Graph G, int v)
    {
        marked[v] = true;
        for (int w : G.adj(v))
            if (!marked[w])
            {
                Net_size++;
                edgeTo[w] = v;
                dfs(G, w);
            }
    }
    public boolean hasPathTo(int v)
    { return marked[v]; }

    public Iterable<Integer> pathTo(int v)
    {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x]) {
            path.push(x);
            distance++;
        }
        path.push(s);
        return path;
    }
    public int getDistance(){
        return distance;
    }

    //Return the size of Network section.
    public int getNet_size() {
        return Net_size;
    }
}

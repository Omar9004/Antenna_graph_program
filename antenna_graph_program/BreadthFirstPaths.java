package Uppgiftspaket_4;
/*@project Uppgiftspaket_4
 *@author Omar Alfakir
 * OBS- This implementation of BreadthFirstPaths has been taken from Algorithms book
 */

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BreadthFirstPaths
{
    private boolean[] marked; // Is a shortest path to this vertex known?
    private int[] edgeTo; // last vertex on known path to this vertex
    private final int s; // source
    public BreadthFirstPaths(Graph G, int s)
    {
        marked = new boolean[G.V()];
        edgeTo = new int[G.V()];
        this.s = s;
        bfs(G, s);
    }
    private void bfs(Graph G, int s)
    {
        Queue<Integer> queue = new LinkedList<>();
        marked[s] = true; // Mark the source
        queue.add(s); // and put it on the queue.
        while (!queue.isEmpty())
        {
            int v = queue.remove(); // Remove next vertex from the queue.
            for (int w : G.adj(v))
                if (!marked[w]) // For every unmarked adjacent vertex,
                {
                    edgeTo[w] = v; // save last edge on a shortest path,
                    marked[w] = true; // mark it because path is known,
                    queue.add(w); // and add it to the queue.
                }
        }
    }
    public boolean hasPathTo(int v)
    { return marked[v]; }

    public Iterable<Integer> pathTo(int v)
    {
        if (!hasPathTo(v)) return null;
        Stack<Integer> path = new Stack<Integer>();
        for (int x = v; x != s; x = edgeTo[x])
            path.push(x);
        path.push(s);
        return path;
    }
// Same as for DFS (see page 536).
}

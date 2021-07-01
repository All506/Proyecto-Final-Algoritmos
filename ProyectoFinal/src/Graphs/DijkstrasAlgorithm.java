/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Graphs;

import list.ListException;

/**
 *
 * @author LuisGa
 */
public class DijkstrasAlgorithm {
  private static final int NO_PARENT = -1;
  private static String[] result;
  private static int counter;
    // Function that implements Dijkstra's
    // single source shortest path
    // algorithm for a graph represented
    // using adjacency matrix
    // representation
    private static String[] dijkstra(int[][] adjacencyMatrix, int startVertex){
        
        int nVertices = adjacencyMatrix[0].length;
        result = new String[nVertices];
        counter=0;
        
        // shortestDistances[i] will hold the
        // shortest distance from src to i
        int[] shortestDistances = new int[nVertices];
 
        // added[i] will true if vertex i is
        // included / in shortest path tree
        // or shortest distance from src to
        // i is finalized
        boolean[] added = new boolean[nVertices];
 
        // Initialize all distances as
        // INFINITE and added[] as false
        for (int vertexIndex = 0; vertexIndex < nVertices;
                                            vertexIndex++)
        {
            shortestDistances[vertexIndex] = Integer.MAX_VALUE;
            added[vertexIndex] = false;
        }
         
        // Distance of source vertex from
        // itself is always 0
        shortestDistances[startVertex] = 0;
 
        // Parent array to store shortest
        // path tree
        int[] parents = new int[nVertices];
 
        // The starting vertex does not
        // have a parent
        parents[startVertex] = NO_PARENT;
 
        // Find shortest path for all
        // vertices
        for (int i = 1; i < nVertices; i++)
        {
 
            // Pick the minimum distance vertex
            // from the set of vertices not yet
            // processed. nearestVertex is
            // always equal to startNode in
            // first iteration.
            int nearestVertex = -1;
            int shortestDistance = Integer.MAX_VALUE;
            for (int vertexIndex = 0;
                     vertexIndex < nVertices;
                     vertexIndex++)
            {
                if (!added[vertexIndex] &&
                    shortestDistances[vertexIndex] <
                    shortestDistance)
                {
                    nearestVertex = vertexIndex;
                    shortestDistance = shortestDistances[vertexIndex];
                }
            }
 
            // Mark the picked vertex as
            // processed
            added[nearestVertex] = true;
 
            // Update dist value of the
            // adjacent vertices of the
            // picked vertex.
            for (int vertexIndex = 0;
                     vertexIndex < nVertices;
                     vertexIndex++)
            {
                int edgeDistance = adjacencyMatrix[nearestVertex][vertexIndex];
                 
                if (edgeDistance > 0
                    && ((shortestDistance + edgeDistance) <
                        shortestDistances[vertexIndex]))
                {
                    parents[vertexIndex] = nearestVertex;
                    shortestDistances[vertexIndex] = shortestDistance +
                                                       edgeDistance;
                }
            }
        }
 
        printSolution(startVertex, shortestDistances, parents);
        return result;
    }
 
    // A utility function to print
    // the constructed distances
    // array and shortest paths
    private static void printSolution(int startVertex,
                                      int[] distances,
                                      int[] parents)
    {
        int nVertices = distances.length;
        System.out.print("Vertex\t Distance\tPath");
         
        for (int vertexIndex = 0;vertexIndex < nVertices;vertexIndex++){
            if (vertexIndex != startVertex)
            {
                result[vertexIndex]=distances[vertexIndex]+"-"+startVertex+"-"+vertexIndex+"-";
                System.out.print("\n" + startVertex + " -> ");
                System.out.print(vertexIndex + " \t\t ");
                System.out.print(distances[vertexIndex] + "\t\t");
                counter=vertexIndex;
                printPath(vertexIndex, parents);
            }else{
            result[vertexIndex]="0-"+startVertex+"-"+vertexIndex+"-"+vertexIndex+"-";
            }
        }
        System.out.println("\n----------------------------------");
    }
 
    // Function to print shortest path
    // from source to currentVertex
    // using parents array
    private static void printPath(int currentVertex,
                                  int[] parents)
    {
         
        // Base case : Source node has
        // been processed
        if (currentVertex == NO_PARENT)
        {
            return;
        }
        printPath(parents[currentVertex], parents);
        System.out.print(currentVertex + " ");
        result[counter]+=currentVertex +"-";
    }
    
    private static int[][] graphAsMatrix(AdjacencyListGraph g) throws ListException, GraphException{
    
        int[][] matrix = new int[g.size()][g.size()];
        for (int i = 0; i < g.size(); i++) {
            for (int j = 0; j < g.size(); j++) {
                if (i != j) {
                    matrix[i][j] = Integer.parseInt(g.getWeight(g.getVertexByIndex(i).data, g.getVertexByIndex(j).data).toString());
                }
            }
        }
    return matrix;
    }
    
    public static String[] getPath(AdjacencyListGraph g, Object start) throws ListException, GraphException{
        for (int i = 0; i < g.size(); i++) {
            if(Util.Utility.equals(g.getVertexByIndex(i).data, start)){
                    return dijkstra(graphAsMatrix(g),i);
            }
        }
        
        return null;
        
    }
 
}

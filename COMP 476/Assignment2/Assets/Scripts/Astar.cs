using System.Collections.Generic;
using UnityEngine;

public class Astar : MonoBehaviour
{
    private Grid grid;
    private Graph graph;

    public Transform npc, target;

    [HideInInspector] public bool GridPathfinding = false;
    [HideInInspector] public bool GraphPathfinding = false;


    // WARNING cant get delegates working with the GenericNode Parent Class
    public delegate float HeuristicGridFunction(Node from, Node to);
    public delegate float HeuristicGraphFunction(GraphNode from, GraphNode to);

    [SerializeField] private HeuristicGridFunction[] gridFunction = { GridH.Heuristic.NullHeuristic, GridH.Heuristic.EuclideanDistance, GridH.Heuristic.ClusteringHeuristic };
    [SerializeField] private HeuristicGraphFunction[] graphFunction = { GraphH.Heuristic.NullHeuristic, GraphH.Heuristic.EuclideanDistance, GraphH.Heuristic.ClusteringHeuristic };
    
    public int chosenHeuristic { get; set; }

    private void Awake() 
    {
        grid = GetComponent<Grid>();
        graph = GetComponent<Graph>();
        npc = grid.player;
        target = grid.goal;
    }

    private void Update() 
    {
        if (GridPathfinding)
            FindPathGrid(npc.position, target.position, gridFunction[chosenHeuristic]);
        if (GraphPathfinding)
            FindPathGraph(npc.position, target.position, graphFunction[chosenHeuristic]);
    }

    public void FindPathGrid(Vector3 startPos, Vector3 endPos, HeuristicGridFunction hFunction)
    {
        Node startNode = grid.NodeFromWorld(startPos);
        Node goalNode = grid.NodeFromWorld(endPos);

        List<Node> openSet = new List<Node>();
        HashSet<Node> closedSet = new HashSet<Node>();

        openSet.Add(startNode);

        while(openSet.Count > 0)
        {
            // WARNING horribly unoptimal
            // finds next node
            Node currentNode = openSet[0];
            for (int i = 1; i<openSet.Count; i++)
            {
                if (openSet[i].fCost < currentNode.fCost || (openSet[i].fCost == currentNode.fCost && openSet[i].hCost < currentNode.hCost))
                    currentNode = openSet[i];
            }
            //---------------------------

            openSet.Remove(currentNode);
            closedSet.Add(currentNode);

            // path found
            if (currentNode == goalNode)
            {
                grid.openSet = openSet;
                grid.closedSet = closedSet;
                RetracePath(startNode, goalNode);
                return;
            }

            // calculates costs for neighbours
            foreach (var neighbour in grid.GetNeighbours(currentNode))
            {
                if (!neighbour.walkable || closedSet.Contains(neighbour))
                    continue;
                
                int newGCost = Mathf.RoundToInt(currentNode.gCost + GridH.Heuristic.EuclideanDistance(currentNode, neighbour));
                // if (neighbour == goalNode)
                //     print($"Cost to next is {newGCost}");

                if (newGCost < neighbour.gCost || !openSet.Contains(neighbour))
                {
                    neighbour.gCost = newGCost;
                    neighbour.hCost = Mathf.RoundToInt(hFunction(neighbour, goalNode));
                    neighbour.parent = currentNode;

                    if (!openSet.Contains(neighbour))
                        openSet.Add(neighbour);
                }
            }

        }
    }

    public void FindPathGraph(Vector3 startPos, Vector3 endPos, HeuristicGraphFunction hFunction)
    {
        GraphNode startNode = graph.NodeFromWorld(startPos);
        GraphNode goalNode = graph.NodeFromWorld(endPos);

        List<GraphNode> openSet = new List<GraphNode>();
        HashSet<GraphNode> closedSet = new HashSet<GraphNode>();

        openSet.Add(startNode);

        while(openSet.Count > 0)
        {
            // WARNING horribly unoptimal
            // finds next node
            GraphNode currentNode = openSet[0];
            for (int i = 1; i<openSet.Count; i++)
            {
                if (openSet[i].fCost < currentNode.fCost || (openSet[i].fCost == currentNode.fCost && openSet[i].hCost < currentNode.hCost))
                    currentNode = openSet[i];
            }
            //---------------------------

            openSet.Remove(currentNode);
            closedSet.Add(currentNode);

            // path found
            if (currentNode == goalNode)
            {
                graph.openSet = openSet;
                graph.closedSet = closedSet;
                RetracePath(startNode, goalNode);
                return;
            }

            // calculates costs for neighbours
            foreach (var neighbour in graph.GetNeighbours(currentNode))
            {
                if (closedSet.Contains(neighbour))
                    continue;

                float newGCost = currentNode.gCost + GraphH.Heuristic.EuclideanDistance(currentNode, neighbour);

                if (newGCost < neighbour.gCost || !openSet.Contains(neighbour))
                {
                    neighbour.gCost = newGCost;
                    neighbour.hCost = hFunction(neighbour, goalNode);
                    neighbour.parent = currentNode;

                    if (!openSet.Contains(neighbour))
                        openSet.Add(neighbour);
                }
            }

        }

    }

    // Retracing path for the tile based grid
    void RetracePath(Node start, Node end)
    {
        List<Node> path = new List<Node>();
        Node currentNode = end;
        
        while (currentNode != start)
        {
            path.Add(currentNode);
            currentNode = currentNode.parent;
        }

        path.Reverse();
        grid.path = path;
    }

    // Retracing path for the POV based graph
    void RetracePath(GraphNode start, GraphNode end)
    {
        List<GraphNode> path = new List<GraphNode>();
        GraphNode currentNode = end;

        while (currentNode != start)
        {
            path.Add(currentNode);
            currentNode = currentNode.parent;
        }
        path.Add(currentNode);

        path.Reverse();
        graph.path = path;
    }
}



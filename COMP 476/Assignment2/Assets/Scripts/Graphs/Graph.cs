using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.Linq;

public class Graph : MonoBehaviour
{

    [SerializeField] private LayerMask clusterChecker;
    [SerializeField] private Transform player;
    [SerializeField] private Transform goal;
    [SerializeField] private GameObject nodeHolder;
    [SerializeField] private float nodeSize = 0.5f;
    private GraphNode[] graph;
    private List<GraphNode>[] adjacency;
    private Transform[] nodePositions;

    public List<GraphNode> path { get; set; }

    public List<GraphNode> openSet;
    public HashSet<GraphNode> closedSet;

    private void Start() 
    {
        nodePositions = nodeHolder.GetComponentsInChildren<Transform>().Where(obj => obj.gameObject != nodeHolder).ToArray();
        path = new List<GraphNode>();
        openSet = new List<GraphNode>();
        closedSet = new HashSet<GraphNode>();
    }

    public void CreateGraph()
    {
        //enables the placeholder meshes
        nodeHolder.SetActive(true);

        // set up the graph
        graph = new GraphNode[nodePositions.Length];
        adjacency = new List<GraphNode>[nodePositions.Length];
        for(int i = 0; i < graph.Length; i++)
        {
            graph[i] = new GraphNode(nodePositions[i].position);
            adjacency[i] = new List<GraphNode>();
        }

        // set up adjacency list
        for (int i = 0; i < graph.Length; i++)
        {
            GraphNode current = graph[i];
            foreach (GraphNode comparing in graph)
            {
                if (current == comparing)
                {
                    continue;
                }
                Physics.Raycast(current.worldPos, comparing.worldPos - current.worldPos, out RaycastHit hit, Mathf.Infinity);

                if (Vector3.Distance(hit.transform.position, comparing.worldPos) < Vector3.kEpsilon)
                {
                    adjacency[i].Add(comparing);
                }
            }
        }

        // disables the mesh of the placeholder node
        foreach (Transform t in nodePositions)
        {
            t.gameObject.SetActive(false);
        }

        // setup the cluster groups
        foreach (var node in graph)
        {
            Physics.SphereCast(node.worldPos + Vector3.down * 5f, 1f, Vector3.up * 10f, out RaycastHit hit, Mathf.Infinity, clusterChecker);
            if (hit.collider != null)
            {
                //print($"{hit.collider.name} at {node.worldPos}");
                if (hit.collider.name == "ClusterGroup1")
                    node.clusterID = 1;
                else if (hit.collider.name == "ClusterGroup2")
                    node.clusterID = 2;
                else if (hit.collider.name == "ClusterGroup3")
                    node.clusterID = 3;
            }
        }
    }

    public GraphNode NodeFromWorld(Vector3 worldPos)
    {
        GraphNode closestNode = graph[0];
        for (int i = 1; i < graph.Length; i++)
        {
            if (Vector3.Distance(worldPos, graph[i].worldPos) < Vector3.Distance(worldPos, closestNode.worldPos))
                closestNode = graph[i];
        }
        return closestNode;
    }

    public GraphNode PickNewNode()
    {
        return graph[Random.Range(0,graph.Length)];
    }

    public List<GraphNode> GetNeighbours(GraphNode center)
    {
        for (int i = 0; i<graph.Length; i++)
        {
            if (center == graph[i])
                return adjacency[i];
        }
        return null;
    }


    private void OnDrawGizmos() 
    {
        if (graph != null)
        {
            // drawing the graph
            for (int i = 0; i<graph.Length; i++)
            {
                if (Vector3.Distance(NodeFromWorld(player.position).worldPos, graph[i].worldPos) < Vector3.kEpsilon)
                    Gizmos.color = Color.cyan;
                else if (Vector3.Distance(NodeFromWorld(goal.position).worldPos, graph[i].worldPos) < Vector3.kEpsilon)
                    Gizmos.color = Color.green;
                // else if (graph[i].clusterID == 1)
                //     Gizmos.color = Color.green;
                // else if (graph[i].clusterID == 2)
                //     Gizmos.color = Color.blue;
                // else if (graph[i].clusterID == 3)
                //     Gizmos.color = Color.black;
                else
                    Gizmos.color = Color.white;
                Gizmos.DrawSphere(graph[i].worldPos, nodeSize);
                foreach (GraphNode neighbour in adjacency[i])
                {
                    Gizmos.color = Color.red;
                    Gizmos.DrawLine(graph[i].worldPos, neighbour.worldPos);
                }
            }

#region not working
            // drawing open set connections
            // for (int i = 0; i < openSet.Count-1; i++)
            // {
            //     // int index = System.Array.FindIndex(graph, val => val.Equals(openSet[i]));
            //     // foreach (var item in adjacency[index])
            //     // {
            //     //     Gizmos.DrawLine(openSet[i].worldPos, openSet[i+1].worldPos);
            //     // }
            //     foreach (var item in path)
            //     {
                    
            //     }
            // }
#endregion

            foreach (var node in openSet)
            {
                Gizmos.color = Color.yellow;
                Gizmos.DrawSphere(node.worldPos, nodeSize);
            }

#region not working
            // drawing closed set connections
            // for (int i = 0; i < closedSet.Count-1; i++)
            // {
            //     // System.Array.FindIndex(graph, closedSet[i]);
            //     // Gizmos.DrawLine(closedSet[i].worldPos, closedSet[i+1].worldPos);
            // }
#endregion

            foreach (var node in closedSet)
            {
                Gizmos.color = Color.magenta;
                Gizmos.DrawSphere(node.worldPos, nodeSize);
            }

            // drawing the path
            for (int i = 0; i < path.Count-1; i++)
            {
                Gizmos.color = Color.black;
                Gizmos.DrawLine(path[i].worldPos, path[i+1].worldPos);
            }

            foreach (var node in path)
            {
                Gizmos.DrawSphere(node.worldPos, nodeSize);
            }
        }
    }
}




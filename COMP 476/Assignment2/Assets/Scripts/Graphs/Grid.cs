using System.Collections.Generic;
using UnityEngine;

public class Grid : MonoBehaviour
{
    [SerializeField] private LayerMask unwalkableMask;
    [SerializeField] private LayerMask clusterChecker;
    [SerializeField] private Vector2 gridSize;
    [SerializeField] private float nodeRadius;
    public Transform player;
    public Transform goal;

    private Node[,] grid;
    private float nodeDiameter;
    private int gridSizeX, gridSizeY;

    public List<Node> path { get; set; }
    public List<Node> openSet;
    public HashSet<Node> closedSet;
    
    private void Start() 
    {
        nodeDiameter = nodeRadius * 2;
        gridSizeX = Mathf.RoundToInt(gridSize.x / nodeDiameter);
        gridSizeY = Mathf.RoundToInt(gridSize.y / nodeDiameter);
        openSet = new List<Node>();
        closedSet = new HashSet<Node>();
    }


    public void CreateGrid()
    {
        grid = new Node[gridSizeX, gridSizeY];
        Vector3 botLeft = transform.position - Vector3.right * gridSize.x/2 - Vector3.forward * gridSize.y/2;
        for (int x = 0; x < gridSizeX; x++)
        {
            for (int y = 0; y < gridSizeY; y++)
            {
                Vector3 point = botLeft + Vector3.right * (x * nodeDiameter + nodeRadius) + Vector3.forward * (y * nodeDiameter + nodeRadius);
                bool isWalkable = !Physics.CheckSphere(point, nodeRadius, unwalkableMask);
                grid[x,y] = new Node(isWalkable, point, x, y);
            }
        }

        // setup the cluster groups
        for (int x = 0; x < gridSizeX; x++)
        {
            for (int y = 0; y < gridSizeY; y++)
            {
                Physics.SphereCast(grid[x,y].worldPos + Vector3.down * 5f, 1f, Vector3.up * 10f, out RaycastHit hit, Mathf.Infinity, clusterChecker);
                if (hit.collider != null)
                {
                    //print($"{hit.collider.name} at {grid[x,y].worldPos}");
                    if (hit.collider.name == "ClusterGroup1")
                        grid[x,y].clusterID = 1;
                    else if (hit.collider.name == "ClusterGroup2")
                        grid[x,y].clusterID = 2;
                    else if (hit.collider.name == "ClusterGroup3")
                        grid[x,y].clusterID = 3;
                }
            }
        }
    }

    public Node NodeFromWorld(Vector3 worldPos)
    {
        float percentX = Mathf.Clamp01((worldPos.x + gridSize.x/2) / gridSize.x);
        float percentY = Mathf.Clamp01((worldPos.z + gridSize.y/2) / gridSize.y);

        int x = Mathf.RoundToInt((gridSizeX-1) * percentX);
        int y = Mathf.RoundToInt((gridSizeY-1) * percentY);

        return grid[x,y];
    }

    public Node PickNewNode()
    {
        Node result;
        do
        {
            result = grid[Random.Range(0,gridSizeX), Random.Range(0,gridSizeY)];
        } 
        while (!result.walkable);
        
        return result;
    }
    

    public List<Node> GetNeighbours(Node center)
    {
        List<Node> neighbours = new List<Node>();

        for(int x = -1; x <= 1; x++)
        {
            for(int y = -1; y <= 1; y++)
            {
                if (x == 0 && y == 0)
                    continue;
                int checkX = center.x + x;
                int checkY = center.y + y;

                if (checkX >= 0 && checkX < gridSizeX && checkY >= 0 && checkY < gridSizeY)
                {
                    neighbours.Add(grid[checkX, checkY]);
                }
            }
        }

        return neighbours;
    }

    private void OnDrawGizmos() 
    {
        Gizmos.DrawWireCube(transform.position, new Vector3(gridSize.x, 1f, gridSize.y));

        if (grid != null)
        {
            Node playerNode = NodeFromWorld(player.position);
            Node goalNode = NodeFromWorld(goal.position);

            foreach(Node node in grid)
            {
                if (node == playerNode)
                    Gizmos.color = Color.cyan;
                else if (node == goalNode)
                    Gizmos.color = Color.green;
                else if (closedSet.Contains(node))
                    Gizmos.color = Color.magenta;
                else if (openSet.Contains(node))
                    Gizmos.color = Color.yellow;
                // else if (node.clusterID == 1)
                //     Gizmos.color = Color.green;
                // else if (node.clusterID == 2)
                //     Gizmos.color = Color.blue;
                // else if (node.clusterID == 3)
                    // Gizmos.color = Color.black;
                else
                    Gizmos.color = node.walkable ? Color.white : Color.red;

                if (path != null && path.Contains(node))
                    Gizmos.color = Color.black;

                Gizmos.DrawCube(node.worldPos, Vector3.one * (nodeDiameter-0.1f));
            }
        }
    }

}



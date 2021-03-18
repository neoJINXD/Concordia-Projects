using UnityEngine;

[System.Serializable]
public class Node : GenericNode
{
    public bool walkable { get; private set; }
    public int gCost { get; set; }
    public int hCost { get; set; }

    public int fCost
    {
        get
        {
            return gCost + hCost;
        }
    }

    public int x { get; private set; }
    public int y { get; private set; }

    public Node parent { get; set; }

    public Node(bool _walkable, Vector3 _worldPos, int _x, int _y)
    {
        walkable = _walkable;
        worldPos = _worldPos;
        x = _x;
        y = _y;
    }
}

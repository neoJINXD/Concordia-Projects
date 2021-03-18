using UnityEngine;

public class GraphNode : GenericNode
{
    public float gCost;
    public float hCost;

    public float fCost
    {
        get
        {
            return gCost + hCost;
        }
    }
    
    public GraphNode parent;


    public GraphNode(Vector3 _worldPos)
    {
        worldPos = _worldPos;
    }


    public override bool Equals(object obj)
    { 
        if (!(obj is GraphNode))
            return false;
 
        var other = obj as GraphNode;
 
        if (worldPos != other.worldPos)
            return false;
 
        return true;
    }
 
    public static bool operator ==(GraphNode x, GraphNode y)
    { 
        return x.Equals(y);
    }
 
    public static bool operator !=(GraphNode x, GraphNode y)
    {
        return !(x == y);
    }
}
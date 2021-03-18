using UnityEngine;

namespace GridH
{
    public class Heuristic : MonoBehaviour
    {
        public static float NullHeuristic(Node from, Node to) => 0;
        
        public static float EuclideanDistance(Node from, Node to)
        {
            int x = Mathf.Abs(from.x - to.x);
            int y = Mathf.Abs(from.y - to.y);

            if (x > y)
                return 14*y + 10*(x-y);
            else
                return 14*x + 10*(y-x);
        }
        public static float ClusteringHeuristic(Node from, Node to)
        {
            if (from.clusterID == to.clusterID)
                return EuclideanDistance(from, to);
            else
            {
                //use lookup table
                if ((from.clusterID == 1 && to.clusterID == 2) || (from.clusterID == 2 && to.clusterID == 1))
                    return 27;
                else if ((from.clusterID == 1 && to.clusterID == 3) || (from.clusterID == 3 && to.clusterID == 1))
                    return 12;
                else if ((from.clusterID == 2 && to.clusterID == 3) || (from.clusterID == 3 && to.clusterID == 2))
                    return 5;
                return 0;
            }
        }
    }
}

namespace GraphH
{
    public class Heuristic : MonoBehaviour
    {
        public static float NullHeuristic(GraphNode from, GraphNode to) => 0;
        
        public static float EuclideanDistance(GraphNode from, GraphNode to)
        {
            return Vector3.Distance(from.worldPos, to.worldPos);
        }
        public static float ClusteringHeuristic(GraphNode from, GraphNode to)
        {
            if (from.clusterID == to.clusterID)
                return EuclideanDistance(from, to);
            else
            {
                //use lookup table
                if ((from.clusterID == 1 && to.clusterID == 2) || (from.clusterID == 2 && to.clusterID == 1))
                    return 27;
                else if ((from.clusterID == 1 && to.clusterID == 3) || (from.clusterID == 3 && to.clusterID == 1))
                    return 12;
                else if ((from.clusterID == 2 && to.clusterID == 3) || (from.clusterID == 3 && to.clusterID == 2))
                    return 5;
                return 0;
            }
        }

    }

}



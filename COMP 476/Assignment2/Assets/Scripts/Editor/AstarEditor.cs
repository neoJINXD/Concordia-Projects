using UnityEngine;
using UnityEditor;

[CustomEditor(typeof(Astar))]
public class AstarEditor : Editor
{
    public override void OnInspectorGUI()
    {
        DrawDefaultInspector();

        Astar astar = (Astar)target;

        GUILayout.Label("Choose a Heuristic", EditorStyles.boldLabel);
        if (GUILayout.Button("Null Heuristic"))
            astar.chosenHeuristic = 0;
        if (GUILayout.Button("Euclidean Distance Heuristic"))
            astar.chosenHeuristic = 1;
        if (GUILayout.Button("Clustering Heuristic"))
            astar.chosenHeuristic = 2;

        GUILayout.Label("Running Time", EditorStyles.boldLabel);
        if(!astar.GridPathfinding && GUILayout.Button("Activate Grid Pathfinding"))
        {
            astar.GridPathfinding = true;
        }
        if(!astar.GraphPathfinding && GUILayout.Button("Activate Graph Pathfinding"))
        {
            astar.GraphPathfinding = true;
        }
    }
}
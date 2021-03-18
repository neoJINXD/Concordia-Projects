using UnityEngine;
using UnityEditor;

[CustomEditor(typeof(Graph))]
public class GraphEditor : Editor
{
    public override void OnInspectorGUI()
    {
        DrawDefaultInspector();

        Graph graph = (Graph)target;
        if(GUILayout.Button("Build Graph"))
        {
            graph.CreateGraph();
        }
    }
}
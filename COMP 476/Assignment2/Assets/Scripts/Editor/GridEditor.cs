using UnityEngine;
using UnityEditor;

[CustomEditor(typeof(Grid))]
public class GridEditor : Editor
{
    public override void OnInspectorGUI()
    {
        DrawDefaultInspector();

        Grid grid = (Grid)target;
        if(GUILayout.Button("Build Grid"))
        {
            grid.CreateGrid();
        }
    }
}
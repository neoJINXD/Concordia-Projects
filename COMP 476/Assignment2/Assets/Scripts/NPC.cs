using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class NPC : MonoBehaviour
{
    [SerializeField] private float acceleration = 1f;
    [SerializeField] private float maxSpeed = 10f;
    // public Transform targetPos; // TODO need to 
    public GameObject target;
    public Vector3 targetPos; // TODO need to 
    private float currentSpeed;

    public Vector3 velocity;

    [SerializeField] private Grid grid;
    [SerializeField] private Graph graph;
    [SerializeField] private Astar astar;


    private void Update() 
    {
        if ((astar.GridPathfinding || astar.GraphPathfinding) && ((grid.path != null && grid.path.Count > 0) || (graph.path != null && graph.path.Count > 0)))
        {
            targetPos = astar.GridPathfinding ? grid.path[0].worldPos : graph.path[1].worldPos;
            Arrive();
            Align(targetPos-transform.position, 5f);
            if (astar.GridPathfinding)
            {
                targetPos = grid.path[0].worldPos;
                if (grid.NodeFromWorld(target.transform.position) == grid.NodeFromWorld(transform.position))
                {
                    PickNewGridGoal();
                }
            }
            else if (astar.GraphPathfinding)
            {
                // TODO this isnt working, sometimes going through walls
                if (Vector3.Distance(transform.position, graph.path[0].worldPos) < 2.6f)
                {
                    print("yes");
                    targetPos = graph.path[1].worldPos;
                    // graph.path.RemoveAt(0);ww
                }
                
                if (graph.NodeFromWorld(target.transform.position) == graph.NodeFromWorld(transform.position))
                {
                    PickNewGraphGoal();
                }
            }
        }
    }

    private void PickNewGridGoal()
    {
        Node newGoal = grid.PickNewNode();
        target.transform.position = newGoal.worldPos;
    }
    private void PickNewGraphGoal()
    {
        GraphNode newGoal = graph.PickNewNode();
        target.transform.position = newGoal.worldPos;
    }
    
    private void FixedUpdate() 
    {
        transform.position += velocity;
    }

    private void Arrive()
    {
        velocity += transform.forward * acceleration * Time.deltaTime;
        velocity.y = 0f;
        velocity = Vector3.ClampMagnitude(velocity, maxSpeed);
    }

    private void Align(Vector3 facing, float rotationSpeed)
    {
        Quaternion lookWhereYoureGoing = Quaternion.LookRotation(facing, Vector3.up);
        transform.rotation = Quaternion.RotateTowards(transform.rotation, lookWhereYoureGoing, rotationSpeed);
        transform.rotation = Quaternion.Euler(0f, transform.rotation.eulerAngles.y, 0f); 
    }

    private void OnCollisionEnter(Collision other) 
    {
        print(other.collider.name);
    }
}

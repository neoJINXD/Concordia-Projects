using UnityEngine;

public static class Kinematic {
    public static void Arrive(Rigidbody rb, Transform transform, Vector3 targetPos, float speed)
    {
        rb.velocity = (targetPos - transform.position).normalized * speed;
    }

    public static void Flee(Rigidbody rb, Transform transform, Vector3 targetPos, float speed)
    {
        rb.velocity = (transform.position - targetPos).normalized * speed;
    }

    public static void Pursue()
    {
        //TODO
    }

    public static void Wander(Rigidbody rb, Transform transform, Vector3 targetPos, float wanderSpeed)
    {
        rb.velocity = (targetPos-transform.position).normalized * wanderSpeed;
    }
}
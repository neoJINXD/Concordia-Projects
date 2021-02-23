using UnityEngine;

public static class Steering {
    public static void Arrive(Rigidbody rb, Transform transform, Vector3 targetPos, float accel, float maxSpeed)
    {
        rb.AddForce((targetPos - transform.position).normalized * accel * Time.deltaTime);
        rb.velocity = Vector3.ClampMagnitude(rb.velocity, maxSpeed);
    }
    public static void Flee(Rigidbody rb, Transform transform, Vector3 targetPos,float accel, float maxSpeed)
    {
        rb.AddForce((transform.position - targetPos).normalized * accel * Time.deltaTime);
        rb.velocity = Vector3.ClampMagnitude(rb.velocity, maxSpeed);
    }

    public static void Pursue()
    {
        //TODO
    }


    public static void Wander(Rigidbody rb, Transform transform, Vector3 targetPos, float wanderAcceleration, float wanderSpeed)
    {
        rb.AddForce((targetPos-transform.position).normalized * wanderAcceleration * Time.deltaTime);
        rb.velocity = Vector3.ClampMagnitude(rb.velocity, wanderSpeed);
    }
}
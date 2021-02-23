using UnityEngine;

public class Follower : MonoBehaviour
{
    [SerializeField] private float closeDistance = 5f;
    [SerializeField] private float farAcceleration = 10f;
    [SerializeField] private float closeAcceleration = 5f;
    [SerializeField] private float farSpeed = 6f;
    [SerializeField] private float closeSpeed = 1f;
    [SerializeField] private float slowSpeed = 3f;
    [SerializeField] private float nearRadius = 3f;
    [SerializeField] private float stopRotationSpeed = 1f;
    [SerializeField] private float moveRotationSpeed = 0.2f;
    [SerializeField] private float slowAngle = 50f;
    [SerializeField] private float fastAngle = 20f;
    [SerializeField] private Transform targetPos;

    private Rigidbody rb;

    private void Start() 
    {
        rb = GetComponent<Rigidbody>();
    }

    private void Update() 
    {
        Vector3 position = transform.position;
        Vector3 target = targetPos.position;
        bool isSteering = GlobalSettings.isInSteering;
        if (rb.velocity.magnitude <= slowSpeed)
        {
            // print("we slow");
            // A
            if (Vector3.Distance(position, target) <= closeDistance)
            {
                // print("we close");
                //A i) behaviour
                
                //move to target, no turning
                if (!isSteering)
                {
                    Kinematic.Arrive(rb, transform, targetPos.position, closeSpeed);
                }
                else
                {
                    Steering.Arrive(rb, transform, targetPos.position, closeAcceleration, closeSpeed);
                }
            }
            else
            {
                // print("we far");
                //A ii) behaviour

                //if angle btw forward and target close to 0, 
                if (Vector3.Angle(transform.forward, target - position) <= 2f)
                {
                    // then move forward
                    if (!isSteering)
                    {
                        Kinematic.Arrive(rb, transform, targetPos.position, farSpeed);
                    }
                    else
                    {
                        Steering.Arrive(rb, transform, targetPos.position, farAcceleration, farSpeed);
                    }   
                }
                else 
                {
                    // else turn towards target
                    if (isSteering || rb.velocity.magnitude < 1f)
                    {
                        Helper.Alignment(transform, target-position, stopRotationSpeed);
                    }
                    else
                    {
                        Helper.Alignment(transform, rb.velocity, stopRotationSpeed);
                    }   

                }

            }
        }
        else
        {
            // print("we fast");
            //B
            // check arc infront of character
            float coneAngle = Helper.Map(rb.velocity.magnitude, 0f, farSpeed, fastAngle, slowAngle);
            if (Vector3.Angle(transform.forward, target - position) 
                <= coneAngle)
            {
                // print("OhISee");
                //B i)
                if (!isSteering)
                {
                    Kinematic.Arrive(rb, transform, targetPos.position, farSpeed);
                }
                else
                {
                    Steering.Arrive(rb, transform, targetPos.position, farAcceleration, farSpeed);
                } 
                Helper.Alignment(transform, rb.velocity, moveRotationSpeed);
            }
            else 
            {
                // print("OhImBlind");
                // B ii) 
                rb.velocity = Vector3.zero; //stop
                Helper.Alignment(transform, target-position, stopRotationSpeed);
            }
        }
    }
}

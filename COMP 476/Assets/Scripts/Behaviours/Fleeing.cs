using UnityEngine;

public class Fleeing : MonoBehaviour
{
    [SerializeField] private float speed = 6f;
    [SerializeField] private float acceleration = 10f;
    [SerializeField] private float distance = 5f;
    [SerializeField] private float stopRotationSpeed = 1f;
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
        
        //C
        if (Vector3.Distance(position, target) <= distance)
        {
            //C i)
            if (isSteering)
            {
                Steering.Flee(rb, transform, targetPos.position, acceleration, speed);
            }
            else
            {
                Kinematic.Flee(rb, transform, targetPos.position, speed);
            }
        }
        else
        {
            rb.velocity = Vector3.zero;
            //C ii)
            //if angle btw forward and target close to 0, 
                if (Vector3.Angle(-transform.forward, target - position) <= 2f)
                {
                    // then move forward
                    if (isSteering)
                    {
                        Steering.Flee(rb, transform, targetPos.position, acceleration, speed);
                    }
                    else
                    {
                        Kinematic.Flee(rb, transform, targetPos.position, speed);
                    }   
                }
                else 
                {
                    // else turn towards target
                    if (isSteering || rb.velocity.magnitude < 1f)
                    {
                        Helper.Alignment(transform ,position-target, stopRotationSpeed);
                    }
                    else
                    {
                        Helper.Alignment(transform ,position-target, stopRotationSpeed);
                    }  
                }
        }
    }
}

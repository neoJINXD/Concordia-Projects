using UnityEngine;

// WARNING: For testing the wrapping of the plane
public class SimpleMove : MonoBehaviour
{
    [SerializeField] float speedX = 1f;
    [SerializeField] float speedY = 1f;
    private void Update() 
    {
        transform.position += new Vector3(speedX, 0f, speedY) * Time.deltaTime;
    }
}

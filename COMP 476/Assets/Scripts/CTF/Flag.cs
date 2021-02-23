using UnityEngine;

public class Flag : MonoBehaviour
{
    [SerializeField] private int teamID;

    private Vector3 initialPos;

    private void Start() 
    {
        initialPos = transform.position;    
    }
    private void OnTriggerEnter(Collider other) 
    {
        //TODO can refactor in 1 if
        if (other.CompareTag("TeamBlue") && teamID == 2)
        {
            // // TODO attach to NPC that 'picked up' the flag
            print("Blue caught");
            transform.SetParent(other.transform);
            other.GetComponent<Capturer>().triggerCapture();
        }
        else if (other.CompareTag("TeamRed") && teamID == 1)
        {
            print("Red caught");
            transform.SetParent(other.transform);
            other.GetComponent<Capturer>().triggerCapture();
        }
    }

    public void ResetPosition()
    {
        transform.SetParent(null);
        transform.position = initialPos;
    }
}

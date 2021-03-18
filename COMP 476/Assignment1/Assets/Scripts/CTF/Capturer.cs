using System.Collections;
using UnityEngine;

public class Capturer : MonoBehaviour
{
    [SerializeField] private float wanderSpeed = 5f;
    [SerializeField] private float wanderAcceleration = 5f;
    [SerializeField] private float pursueSpeed = 7f;
    [SerializeField] private float pursueAcceleration = 20f;
    [SerializeField] private Camera topCam;
    [SerializeField] private Transform flagPos;
    [SerializeField] private Transform myFlagPos;
    [SerializeField] private GameObject captureIcon;

    

    private Rigidbody rb;
    public int teamID { get; set; }
    public bool isCapturer;// { get; set; } = false;
    public bool isTagged;// { get; set; } = false;
    public bool isReviver;// { get; set; } = false;
    public bool isReturning;// { get; set; } = false;

    public Vector3 targetPos;
    
    private bool findingNewTarget = true;

    private GameObject[] blueTeam;
    private GameObject[] redTeam;

    private void Start() 
    {
        rb = GetComponent<Rigidbody>();
        teamID = gameObject.CompareTag("TeamBlue") ? 1 : 2;
        topCam = GameObject.Find("TopCamera").GetComponent<Camera>();

        blueTeam = GameObject.FindGameObjectsWithTag("TeamBlue");
        redTeam = GameObject.FindGameObjectsWithTag("TeamRed");

    }   


    private void Update() 
    {
        captureIcon.SetActive(isCapturer);

        if (isTagged)
        {
            if (isReturning)
            {
                ResetFlag();
                isReturning = false;
            }
            isCapturer = false;
            // FREEZE
            rb.velocity = Vector3.zero;
            return;
        }

        if (!isCapturer && !isReviver && !isTagged && !isReturning)
        {
            if (findingNewTarget)
                StartCoroutine(FindNewPosition());
            Debug.DrawLine(transform.position, targetPos, Color.green);
        }

        if (isCapturer && !isReturning)
        {
            targetPos = flagPos.position;
        }

        if (isReviver)
        {
            if (teamID == 1)
            {

                foreach (var guy in blueTeam)
                {   
                    var capturer = guy.GetComponent<Capturer>();
                    if (capturer.isTagged)
                    {
                        targetPos = capturer.transform.position;
                        break;
                    }
                }
            }
            else if (teamID == 2)
            {
                foreach (var guy in redTeam)
                {   
                    var capturer = guy.GetComponent<Capturer>();
                    if (capturer.isTagged)
                    {
                        targetPos = capturer.transform.position;
                        break;
                    }
                }
            }
        }

        Kinematic.Arrive(rb, transform, targetPos, wanderSpeed);
        Helper.Alignment(transform, rb.velocity, 1f);
        transform.rotation = Quaternion.Euler(0f, transform.eulerAngles.y, 0f);
    } 

    public void ResetFlag() => GetComponentInChildren<Flag>().ResetPosition();

    public void triggerCapture()
    {
        targetPos = myFlagPos.position;
        isCapturer = false;
        isReturning = true;
    }

    IEnumerator FindNewPosition()
    {
        findingNewTarget = false;
        
        // Get new target in plane radius
        if (teamID == 1)
        {
            //team blue side
            Vector3 screenPosition = new Vector3(Random.Range(0.5f, 1f), Random.Range(0f, 1f), 0f);
            targetPos = topCam.ViewportToWorldPoint(screenPosition);
        }
        else if (teamID == 2)
        {
            //team red side
            Vector3 screenPosition = new Vector3(Random.Range(0f, 0.5f), Random.Range(0f, 1f), 0f);
            targetPos = topCam.ViewportToWorldPoint(screenPosition);
        }

        yield return new WaitForSeconds(2f);
        findingNewTarget = true;
    }

    private void OnTriggerEnter(Collider other) 
    {
        if (teamID == 1 && other.CompareTag("TeamBlue") && isReviver && other.GetComponent<Capturer>().isTagged)
        {
            other.GetComponent<Capturer>().isTagged = false;
            isReviver = false;
        }    
        else if (teamID == 2 && other.CompareTag("TeamBlue") && isReviver && other.GetComponent<Capturer>().isTagged)
        {
            other.GetComponent<Capturer>().isTagged = false;
            isReviver = false;
        }    
    }
}

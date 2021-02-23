using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Picker : MonoBehaviour
{

    private GameObject[] blueTeam;
    private GameObject[] redTeam;


    private void Start()
    {
        blueTeam = GameObject.FindGameObjectsWithTag("TeamBlue");
        redTeam = GameObject.FindGameObjectsWithTag("TeamRed");

        // pick a capturer initially
        PickCapturer(1);
        PickCapturer(2);
    }

    private void Update() 
    {
        
        // WARNING i really hate this implementation

        // if there is no NPC with isCapturer == true
        // then pick a new one
        bool shouldPickNewCapturer = true;
        foreach (var guy in blueTeam)
        {   
            if (guy.GetComponent<Capturer>().isCapturer)
            {
                shouldPickNewCapturer = false;
                break;
            }
        }
        if (shouldPickNewCapturer) PickCapturer(1);

        shouldPickNewCapturer = true;
        foreach (var guy in redTeam)
        {   
            if (guy.GetComponent<Capturer>().isCapturer)
            {
                shouldPickNewCapturer = false;
                break;
            }
        }
        if (shouldPickNewCapturer) PickCapturer(2);

        // TODO if no NPC is set to revive
        // TODO then pick one

    }

    public void PickCapturer(int id)
    {
        if (id == 1)
        {
            int range = blueTeam.Length;
            int choice = (int)Random.Range(0, range);
            Capturer capturer = blueTeam[choice].GetComponent<Capturer>();
            capturer.isCapturer = true;
            
            foreach (var guy in blueTeam)
            {
                if (guy.GetComponent<Capturer>().isTagged)
                {
                    capturer.targetPos = guy.transform.position;
                    return;
                }
            }
        }
        else
        {
            int range = redTeam.Length;
            int choice = (int)Random.Range(0, range);
            Capturer capturer = redTeam[choice].GetComponent<Capturer>();
            capturer.isCapturer = true;

            foreach (var guy in redTeam)
            {
                if (guy.GetComponent<Capturer>().isTagged)
                {
                    capturer.targetPos = guy.transform.position;
                    return;
                }
            }
        }
    }

    public void PickReviver(int id)
    {
        if (id == 1)
        {
            int range = blueTeam.Length;
            int choice = (int)Random.Range(0, range);
            blueTeam[choice].GetComponent<Capturer>().isReviver = true;
        }
        else
        {
            int range = redTeam.Length;
            int choice = (int)Random.Range(0, range);
            redTeam[choice].GetComponent<Capturer>().isReviver = true;
        }
    }
}

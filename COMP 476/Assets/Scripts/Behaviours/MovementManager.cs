using UnityEngine;

public class MovementManager : MonoBehaviour
{
    private void Update() 
    {
        if (Input.GetKeyDown(KeyCode.Space))
        {
            GlobalSettings.isInSteering = !GlobalSettings.isInSteering;
            print($"Now switching to {(GlobalSettings.isInSteering ? "Behaviour II" : "Behaviour I")}");
            // print(GlobalSettings.isInSteering);
        }    
    }
}

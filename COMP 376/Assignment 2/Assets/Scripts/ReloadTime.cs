using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ReloadTime : MonoBehaviour
{
    //References
    private Image loading;

    //Other - for circular loading bar for the reload
    private float currentValue = 5;
    private float maxValue = 5;
    private float speed = 1;
    private bool on = false;

    void Start() 
    {
        loading = GetComponent<Image>();
        loading.fillAmount = 0;
    }
    
    void Update() 
    {
        if (on) {
			// currentValue -= speed;
			currentValue -= Time.deltaTime;

		    loading.fillAmount = currentValue / maxValue;
            if (currentValue <= 0)
            {
                on = false;
                // print("done");
            }
		}
    }

    public void SetMax(float max)
    {
        maxValue = max;
    }

    public void TurnOn()
    {
        // print("brrrr");
        on = true;
        currentValue = maxValue;
        loading.fillAmount = 1;
    }

}

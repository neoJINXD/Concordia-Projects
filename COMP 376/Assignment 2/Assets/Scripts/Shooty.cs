using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Shooty : MonoBehaviour
{
    [SerializeField] float timeBtwShot = 1;
    private float timer;

    void Start() 
    {
        timer = timeBtwShot;
        GameObject.Find("ReloadIndicator").GetComponent<ReloadTime>().SetMax(timeBtwShot);    
    }

    void Update()
    {
        timer += Time.deltaTime;
        if (timer >= timeBtwShot)
        {
            if (Input.GetMouseButtonDown(0))
            {
                // print("click");
                GetComponent<AudioSource>().Play();
                
                Vector3 mousePos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
                Vector2 mouse2D = new Vector2(mousePos.x, mousePos.y);

                RaycastHit2D hit = Physics2D.Raycast(mouse2D, Vector2.zero);

                if (hit.collider != null)
                {
                    // print(hit.collider.name);
                    hit.collider.GetComponent<Enemy>().Damage();
                    //TODO add posibility of killing multiple enemies with 1 shot
                } 
                else
                {
                    GameObject.Find("GameManager").GetComponent<GameManager>().DecreasePoints(1);
                }        
                timer = 0;
                GameObject.Find("ReloadIndicator").GetComponent<ReloadTime>().TurnOn();
            }
        }
    }
}

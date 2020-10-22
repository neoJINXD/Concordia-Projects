using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Shooty : MonoBehaviour
{
    [SerializeField] float timeBtwShot = 1;
    private float timer;

    [SerializeField] int missedBad;
    [SerializeField] int missedBadHyper;

    [SerializeField] SpriteRenderer moonElement;
    [SerializeField] Sprite normalMoon;
    [SerializeField] Sprite bloodMoon;


    private AudioSource shootySound;
    private GameManager gm;
    private ReloadTime reloader;
    private bool hyperMode = false;
    private float hyperModeDuration;
    private int hyperCounter = 0;
    private float hyperCountLimit;

    void Start() 
    {
        timer = timeBtwShot;
        GameObject.Find("ReloadIndicator").GetComponent<ReloadTime>().SetMax(timeBtwShot);
        shootySound = GetComponent<AudioSource>();
        gm = GameObject.Find("GameManager").GetComponent<GameManager>();
        reloader = GameObject.Find("ReloadIndicator").GetComponent<ReloadTime>();
        
    }

    void Update()
    {
        hyperCountLimit = hyperModeDuration / timeBtwShot;
        timer += Time.deltaTime;
        if (timer >= timeBtwShot)
        {
            if (Input.GetMouseButtonDown(0) || hyperMode)
            {
                Shoot();
                timer = 0;
                hyperCounter++;
                if (hyperCounter >= hyperCountLimit)
                {
                    hyperMode = false;
                    moonElement.sprite = normalMoon;
                }
            }
        }
    }

    private void Shoot()
    {
        // print("click");
        shootySound.Play();
        
        Vector3 mousePos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
        Vector2 mouse2D = new Vector2(mousePos.x, mousePos.y);

        RaycastHit2D[] hits = Physics2D.RaycastAll(mouse2D, Vector2.zero);

        if (hits.Length != 0)
        {
            foreach (RaycastHit2D hit in hits)
            {
                hit.collider.GetComponent<Enemy>().Damage();
            }
            gm.IncreasePoints(5 * (hits.Length - 1));

        } 
        else
        {
            if (hyperMode)
                gm.DecreasePoints(missedBadHyper);
            else
                gm.DecreasePoints(missedBad);
        }        
        reloader.TurnOn();
    }

    public void activateHyperMode()
    {
        hyperMode = true;
        hyperCounter = 0;
        moonElement.sprite = bloodMoon;
    }

    public void setHyperDur(float dur)
    {
        hyperModeDuration = dur;
    }
}

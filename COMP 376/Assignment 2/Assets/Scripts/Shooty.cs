using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Shooty : MonoBehaviour
{
    //Assignables
    [SerializeField] float timeBtwShot = 1;
    [SerializeField] int missedBad; // score decrement for missing
    [SerializeField] int missedBadHyper; // score decrement for missing in hyper mode
    [SerializeField] SpriteRenderer moonElement; // changing sprite of the moon when in hyper mode
    [SerializeField] Sprite normalMoon;
    [SerializeField] Sprite bloodMoon;

    [SerializeField] int hyperModeSpawnLimit;
    [SerializeField] float hyperModeTimeBtwSpawn;

    //References
    private AudioSource shootySound;
    private GameManager gm;
    private ReloadTime reloader;
    private EnemySpawner spawner;

    //Other
    private float timer;
    private bool hyperMode = false;
    private float hyperModeDuration; // received from GameManager
    private int hyperCounter = 0;
    private float hyperCountLimit;

    void Start() 
    {
        timer = timeBtwShot;
        GameObject.Find("ReloadIndicator").GetComponent<ReloadTime>().SetMax(timeBtwShot);
        shootySound = GetComponent<AudioSource>();
        gm = GameObject.Find("GameManager").GetComponent<GameManager>();
        reloader = GameObject.Find("ReloadIndicator").GetComponent<ReloadTime>();
        spawner = GameObject.Find("EnemySpawner").GetComponent<EnemySpawner>();
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
                    // turning hyper mode off
                    hyperMode = false;
                    moonElement.sprite = normalMoon;
                    spawner.ResetHyper();
                    GameObject[] arr = GameObject.FindGameObjectsWithTag("Baddy");
                    foreach (GameObject i in arr)
                    {
                        // Destroy(i);
                        i.GetComponent<Enemy>().Die();
                    }
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
            // all raycast hits, checking for all the hit enemies
            foreach (RaycastHit2D hit in hits)
            {
                hit.collider.GetComponent<Enemy>().Damage();
            }
            // bonus points for each of them
            gm.IncreasePoints(5 * (hits.Length - 1));
        } 
        else
        {
            if (hyperMode)
                gm.DecreasePoints(missedBadHyper);
            else
                gm.DecreasePoints(missedBad);
        }        
        reloader.TurnOn(); // activates the reload bar
    }

    public void activateHyperMode()
    {
        hyperMode = true;
        hyperCounter = 0;
        moonElement.sprite = bloodMoon;
        spawner.timeBtw(hyperModeTimeBtwSpawn);
        spawner.changeLimit(hyperModeSpawnLimit);
    }

    public void setHyperDur(float dur)
    {
        hyperModeDuration = dur;
    }
}

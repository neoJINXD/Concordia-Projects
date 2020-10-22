using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class GameManager : MonoBehaviour
{
    [SerializeField] int points;
    [SerializeField] Text pointCounter; 
    [SerializeField] Text shotsLeft; 
    [SerializeField] GameObject reloader;
    private int level;
    [SerializeField] Text levelCounter;

    //TODO increase in speed per level player is at
    //TODO add SUPER ULTRA SHOOTING MODE
    private GameObject player;
    private int missedShots = 0;
    private EnemySpawner spawner;

    void Start() 
    {
        player = GameObject.FindWithTag("Player");
        level = 1;
        spawner = GameObject.Find("EnemySpawner").GetComponent<EnemySpawner>();
        spawner.setLevel(level);
    }

    void Update() 
    {
        pointCounter.text = "Score: " + points.ToString();
        shotsLeft.text = "Bullets: " + (3-missedShots).ToString();
        if (missedShots >= 3)
        {
            GameOver();
        }
        if (spawner.EnoughWitchesKilled())
        {
            level++;
            spawner.ResetWitchCount();
            spawner.setLevel(level);
        }
        levelCounter.text = "Level: " + level.ToString();
    }

    private void GameOver()
    {
        // Destroy(player);
        player.SetActive(false);
        reloader.SetActive(false);
        print("suckle the dingdong");
    }

    public void IncreasePoints(int pt)
    {
        points += pt;
    }
    public void DecreasePoints(int pt)
    {
        points = points-pt < 0 ? 0 : points-pt;
        missedShots++;
    }
}

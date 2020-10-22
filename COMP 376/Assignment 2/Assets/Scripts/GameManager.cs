using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;


public class GameManager : MonoBehaviour
{
    [SerializeField] int points;
    [SerializeField] Text pointCounter; 
    [SerializeField] Text shotsLeft; 
    [SerializeField] GameObject reloader;
    private int level;
    [SerializeField] Text levelCounter;
    [SerializeField] KeyCode ultraInstinctKey;
    [SerializeField] float ultraInstictTimeLimit;
    private Shooty shooter;
    private GameObject player;
    private int missedShots = 0;
    private EnemySpawner spawner;
    private ScoreKeeper scoreKeeper;

    void Start() 
    {
        player = GameObject.FindWithTag("Player");
        level = 1;
        spawner = GameObject.Find("EnemySpawner").GetComponent<EnemySpawner>();
        spawner.setLevel(level);
        shooter = player.GetComponent<Shooty>();
        shooter.setHyperDur(ultraInstictTimeLimit);
        scoreKeeper = GameObject.Find("ScoreKeeper").GetComponent<ScoreKeeper>();
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

        if (Input.GetKeyDown(ultraInstinctKey))
        {
            shooter.activateHyperMode();
        }
    }

    private void GameOver()
    {
        // Destroy(player);
        player.SetActive(false);
        reloader.SetActive(false);
        // print("suckle the dingdong");
        Cursor.visible = true;
        scoreKeeper.setScore(points);
        SceneManager.LoadScene("GameOver");
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

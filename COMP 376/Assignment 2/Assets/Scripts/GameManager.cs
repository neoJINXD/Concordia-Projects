using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;


public class GameManager : MonoBehaviour
{
    //Assignables
    [SerializeField] KeyCode ultraInstinctKey;
    [SerializeField] float ultraInstictTimeLimit;

    //UI
    [SerializeField] Text pointCounter; 
    [SerializeField] Text shotsLeft; 
    [SerializeField] GameObject reloader;
    [SerializeField] Text levelCounter;

    //References
    private Shooty shooter;
    private EnemySpawner spawner;
    private ScoreKeeper scoreKeeper;
    private GameObject player;

    //Other
    [SerializeField] int points; // point count
    private int level; // current level
    private int missedShots = 0;



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
        if (missedShots >= 3)
        {
            // lose after missing 3 shots
            GameOver();
        }
        if (spawner.EnoughWitchesKilled())
        {
            // level progression is done through killing 2 witches
            level++;
            spawner.ResetWitchCount();
            spawner.setLevel(level);
        }

        // updating ui
        pointCounter.text = "Score: " + points.ToString();
        shotsLeft.text = "Bullets: " + (3-missedShots).ToString();
        levelCounter.text = "Level: " + level.ToString();

        // activate hyper mode
        if (Input.GetKeyDown(ultraInstinctKey))
        {
            shooter.activateHyperMode();
        }
    }

    private void GameOver()
    {
        player.SetActive(false);
        reloader.SetActive(false);
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
        points = points-pt < 0 ? 0 : points-pt; // clamping points to be non negative
        missedShots++;
    }
}

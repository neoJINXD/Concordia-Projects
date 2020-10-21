using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class GameManager : MonoBehaviour
{
    [SerializeField] int points;
    [SerializeField] Text pointCounter; 
    [SerializeField] Text shotsLeft; 
    private GameObject player;
    private int level = 0;
    private int missedShots = 0;

    void Start() 
    {
        player = GameObject.FindWithTag("Player");
    }

    void Update() 
    {
        pointCounter.text = "Score: " + points.ToString();
        shotsLeft.text = "Bullets: " + (3-missedShots).ToString();
        if (missedShots >= 3)
        {
            GameOver();
        }
    }

    private void GameOver()
    {
        // Destroy(player);
        player.SetActive(false);
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

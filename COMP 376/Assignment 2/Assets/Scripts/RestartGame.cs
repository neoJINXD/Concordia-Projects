using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class RestartGame : MonoBehaviour
{
    [SerializeField] Text finalScore;
    private ScoreKeeper scoreKeeper;
    void Start() 
    {
        scoreKeeper = GameObject.Find("ScoreKeeper").GetComponent<ScoreKeeper>();
    }

    void Update() 
    {
        finalScore.text = "Score: " + scoreKeeper.getScore();    
    }

    public void ResetGame()
    {
        SceneManager.LoadScene("GameScene");
    }
}

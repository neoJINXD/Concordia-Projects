using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class RestartGame : MonoBehaviour
{
    //Assignable
    [SerializeField] Text finalScore;
    
    //Reference
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
        // function for the button in the GameOver scene
        SceneManager.LoadScene("GameScene");
    }
}

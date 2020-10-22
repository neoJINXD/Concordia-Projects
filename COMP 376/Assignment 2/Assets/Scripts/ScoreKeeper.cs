using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScoreKeeper : MonoBehaviour
{
    //Other - keep track of score between the scene change
    private int score;

    void Start()
    {
        DontDestroyOnLoad(gameObject);
    }

    public void setScore(int _score)
    {
        score = _score;
    }

    public int getScore()
    {
        return score;
    }
}

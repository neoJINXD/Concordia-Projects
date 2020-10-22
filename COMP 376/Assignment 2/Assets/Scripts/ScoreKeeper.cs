using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ScoreKeeper : MonoBehaviour
{
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

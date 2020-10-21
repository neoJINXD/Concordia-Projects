using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemySpawner : MonoBehaviour
{

    [SerializeField] GameObject enemyPrefab;
    [SerializeField] int spawnLimit;
    [SerializeField] float spawnTimer;
    private float timer = 0;
    private int count = 0;
    

    void Update() 
    {
        timer += Time.deltaTime;
        if (timer >= spawnTimer && count <= spawnLimit)
        {
            Instantiate(enemyPrefab);
            timer = 0;
            count++;
        }
    }

    public void Killed()
    {
        count--;
    }
}

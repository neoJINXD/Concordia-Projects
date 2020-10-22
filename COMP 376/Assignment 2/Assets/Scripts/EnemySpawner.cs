using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemySpawner : MonoBehaviour
{

    [SerializeField] GameObject enemyPrefab;

    [SerializeField] GameObject witchPrefab;
    [SerializeField, Range(0,1)] float witchChance = 0;
    private bool witchAlive;
    [SerializeField]private int witchCount = 0;

    [SerializeField] int spawnLimit;
    [SerializeField] float spawnTimer;
    private float timer = 0;
    private int count = 0;

    

    void Start() 
    {
        GameObject[] enemies = GameObject.FindGameObjectsWithTag("Baddy");
        count = enemies.Length;
        witchAlive = false;
    }

    void Update() 
    {
        timer += Time.deltaTime;
        if (timer >= spawnTimer && count < spawnLimit)
        {
            // print("reeeeeeeeeeeee");
            Instantiate(enemyPrefab);
            timer = 0;
            count++;
            if (!witchAlive && witchCount < 2)
                SpawnWitch();
        }
    }

    public void Killed()
    {
        count--;
        timer = 0;
    }

    private void SpawnWitch()
    {
        float result = Random.value;
        // print(result);
        if (result <= witchChance)
        {
            Instantiate(witchPrefab);
            witchAlive = true;
            count++;
        }
        // timer = 0;
    }

    public void WitchKilled()
    {
        witchCount++;
    }
    
    public void ResetWitchState()
    {
        witchAlive = false;
    }
}

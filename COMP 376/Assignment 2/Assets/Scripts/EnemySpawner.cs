using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemySpawner : MonoBehaviour
{
    //Assignables
    [SerializeField] GameObject enemyPrefab;
    [SerializeField] GameObject witchPrefab;
    [SerializeField, Range(0,1)] float witchChance = 0; // chance of the next spawn being a witch
    [SerializeField] int spawnLimit; // how many enimies at a time
    [SerializeField] float spawnTimer; // time between spawns
    
    //References
    private Enemy prefabEnemy;
    private bool witchAlive;

    //Others
    private int witchCount = 0; // how many witches killed
    private float timer = 0;
    private int count = 0;
    private int level = 0;


    void Start() 
    {
        GameObject[] enemies = GameObject.FindGameObjectsWithTag("Baddy");
        count = enemies.Length; // adds the already existing enemies (for debug use mainly)
        witchAlive = false;
        prefabEnemy = enemyPrefab.GetComponent<Enemy>();
    }

    void Update() 
    {
        timer += Time.deltaTime;
        if (timer >= spawnTimer && count < spawnLimit)
        {
            // print("reeeeeeeeeeeee");
            // print(levelToSpeed(0));
            prefabEnemy.setSpeed(levelToSpeed(level));
            GameObject spawnedEnemy = Instantiate(enemyPrefab);
            // spawnedEnemy.GetComponent<Enemy>().setSpeed(levelToSpeed(level));
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
        float result = Random.value; // random!!!
        if (result <= witchChance)
        {
            Instantiate(witchPrefab);
            witchAlive = true;
            count++;
        }
    }

    public void WitchKilled()
    {
        witchCount++;
    }
    
    public void ResetWitchState()
    {
        witchAlive = false;
    }

    public bool EnoughWitchesKilled()
    {
        return witchCount == 2;
    }

    public void ResetWitchCount()
    {
        witchCount = 0;
    }

    private float levelToSpeed(int level)
    {
        // formula for the speed enemies should have based on the level
        // https://www.desmos.com/calculator/gkckylmckf
        return level + 0.6f * Mathf.Cos(1.7f * level); 
    }

    public void setLevel(int _level)
    {
        level = _level;
    }
}

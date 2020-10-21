using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemySpawner : MonoBehaviour
{

    [SerializeField] GameObject enemyPrefab;
    [SerializeField] int spawnLimit;
    [SerializeField] float spawnTimer;
    private float timer = 0;
    [SerializeField]private int count = 0;
    
    //TODO add witch spawn

    void Start() 
    {
        GameObject[] enemies = GameObject.FindGameObjectsWithTag("Baddy");
        count = enemies.Length;
    }

    void Update() 
    {
        timer += Time.deltaTime;
        if (timer >= spawnTimer && count < spawnLimit)
        {
            print("reeeeeeeeeeeee");
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

using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    [SerializeField] ParticleSystem explosionFX;
    [SerializeField] float speed = 1f;
    [SerializeField] float threshold = 1f;
    [SerializeField] int pointValue = 3;
    [SerializeField] bool randomize = true;

    //TODO add life timer for enemy

    private bool stopped = true;
    private Vector3 targetLocation;

    void Start()
    {
        if (randomize)
            transform.position = GetRandomPos();
    }

    void FixedUpdate() 
    {
        if (stopped)
        {
            targetLocation = GetRandomPos();
            // print("Moving to " + targetLocation.ToString());
            stopped = false;
        } 
        else
        {
            Vector3 dir = targetLocation - transform.position;
            if (dir.magnitude < threshold)
                stopped = true; // we have arrived
            else
                transform.position += dir.normalized * speed * Time.deltaTime; // vroom vroom
        }

    }

    public void Damage()
    {
        Instantiate(explosionFX, new Vector3(transform.position.x, transform.position.y, -1), Quaternion.identity);
        Destroy(gameObject);
        GameObject.Find("GameManager").GetComponent<GameManager>().IncreasePoints(pointValue);
        GameObject.Find("EnemySpawner").GetComponent<EnemySpawner>().Killed();
    }

    private Vector3 GetRandomPos()
    {
        float spawnX = Random.Range(Camera.main.ScreenToWorldPoint(new Vector2(0, 0)).x, Camera.main.ScreenToWorldPoint(new Vector2(Screen.width, 0)).x);
        float spawnY = Random.Range(Camera.main.ScreenToWorldPoint(new Vector2(0, 0)).y, Camera.main.ScreenToWorldPoint(new Vector2(0, Screen.height)).y);
        return new Vector3(spawnX, spawnY, 0);
    }
}

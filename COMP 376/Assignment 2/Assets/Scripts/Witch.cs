using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Witch : Enemy
{
    private int health = 3;

    [SerializeField] Animation dmgAnimation;
    

    override public void Damage()
    {
        health--;
        dmgAnimation.Play("Witch_Dmg");
        Invoke("ResetState", 0.5f);
        if (health <= 0)
        {
            Die();
            spawner.ResetWitchState();
            spawner.WitchKilled();
        }
    }

    void Update() 
    {
        timer += Time.deltaTime;
        if (timer >= lifeTime)
        {
            Destroy(gameObject);
            spawner.ResetWitchState();
            spawner.Killed();
        }
        if (lifeTime-timer < 5)
            dmgAnimation.Play("Witch_Sayonara");
    }
    

    
}

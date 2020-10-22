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
        // anim.SetBool("damage", true);
        Invoke("ResetState", 0.5f);
        if (health <= 0)
        {
            Die();
            // spawner.WitchAlive = false;
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
    }
    

    
}

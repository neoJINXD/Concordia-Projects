using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Witch : Enemy
{
    private int health = 3;

    override public void Damage()
    {
        health--;
        if (health <= 0)
        {
            Die();
        }
    }
}

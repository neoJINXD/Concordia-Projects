using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class MouseFollower : MonoBehaviour
{
    //References
    [SerializeField] Transform player;
    [SerializeField] Image reloader;
    private RectTransform canvas;
    private Camera cam;
    
    void Start()
    {
        Cursor.visible = false;
        cam = GetComponent<Camera>();
        player = GameObject.FindGameObjectWithTag("Player").GetComponent<Transform>();
        reloader = GameObject.Find("ReloadIndicator").GetComponent<Image>();
        canvas = GameObject.FindObjectOfType<Canvas>().GetComponent<RectTransform>();
    }

    void FixedUpdate()
    {
        //Setting player position to mouse position
        Vector3 mousePos = cam.ScreenToWorldPoint(Input.mousePosition);
        mousePos.z = 0;
        // print(mousePos);
        player.position = mousePos;
        reloader.rectTransform.position = new Vector3(Input.mousePosition.x, Input.mousePosition.y, 0);
    }
}

using UnityEngine;

public class Wrapper : MonoBehaviour
{
    private Renderer renderMesh;
    [SerializeField] private bool wrappingX;
    [SerializeField] private bool wrappingY;
    [SerializeField] private Camera topCam;
    [SerializeField] private Vector3 viewPos;

    private void Start()
    {
        renderMesh = GetComponent<Renderer>();
    }

    private void Update()
    {
        transform.position = new Vector3(transform.position.x, 1f, transform.position.z);
        Debug.DrawRay(transform.position, transform.forward * 5f, Color.red);
        bool isVisible = IsVisibleFrom(renderMesh, topCam);

        if (isVisible)
        {
            wrappingX = false;
            wrappingY = false;
            return;
        }

        if(wrappingX && wrappingY) {
            return;
        }

        viewPos = topCam.WorldToViewportPoint(transform.position);

        
        Vector3 newPosition = transform.position;
    
        if (!wrappingX && (viewPos.x > 1 || viewPos.x < 0))
        {
            Vector3 newPos = viewPos;
            newPos.x += viewPos.x > 1 ? -1 : 1;
            newPosition = topCam.ViewportToWorldPoint(newPos);

            wrappingX = true;
        }
    
        if (!wrappingY && (viewPos.y > 1 || viewPos.y < 0))
        {
            Vector3 newPos = viewPos;
            newPos.y += viewPos.y > 1 ? -1 : 1;
            newPosition = topCam.ViewportToWorldPoint(newPos);
    
            wrappingY = true;
        }
        newPosition.y = 1f;
    
        transform.position = newPosition;
    }
    
    
    private bool IsVisibleFrom(Renderer renderMesh, Camera camera)
    {
        Plane[] planes = GeometryUtility.CalculateFrustumPlanes(camera);
        return GeometryUtility.TestPlanesAABB(planes, renderMesh.bounds);
    }


}

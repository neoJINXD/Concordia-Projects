using UnityEngine;

public static class Helper {
    public static void Alignment(Transform transform, Vector3 facing, float rotationSpeed)
    {
        Quaternion lookWhereYoureGoing = Quaternion.LookRotation(facing, Vector3.up);
        transform.rotation = Quaternion.RotateTowards(transform.rotation, lookWhereYoureGoing, rotationSpeed);
    }

    public static float Map(float input, float start, float end, float newStart, float newEnd)
    {
        float output = newStart + ((newEnd - newStart) / (end - start)) * (input - start);
        return output;
    }
}
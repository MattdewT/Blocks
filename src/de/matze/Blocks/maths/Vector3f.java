package de.matze.Blocks.maths;

/**
 * @autor matze
 * @date 07.06.2017
 */

//ToDO: Comment

public class Vector3f {

    public float x;
    public float y;
    public float z;

    public Vector3f() {
        set(0, 0, 0);
    }

    public Vector3f(float x, float y, float z) {
        set(x, y, z);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3f translate(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public float lengthSquared() {
        return x * x + y * y + z * z;
    }

    public float length() {
        return (float) Math.sqrt(lengthSquared());
    }

    public static Vector3f cross(Vector3f left, Vector3f right, Vector3f dest) {

        if (dest == null)
            dest = new Vector3f();

        dest.set(
                left.y * right.z - left.z * right.y,
                right.x * left.z - right.z * left.x,
                left.x * right.y - left.y * right.x
        );

        return dest;
    }

    public Vector3f normalize(Vector3f dest) {
        float l = length();

        if (dest == null)
            dest = new Vector3f(x / l, y / l, z / l);
        else
            dest.set(x / l, y / l, z / l);

        return dest;
    }

    public static float dot(Vector3f left, Vector3f right) {
        return left.x * right.x + left.y * right.y + left.z * right.z;
    }


}

package de.matze.Blocks.maths;

import de.matze.Blocks.utils.BufferUtils;

import java.nio.FloatBuffer;

/**
 * @version 1.2
 * @autor matze
 * @date 07.06.2017
 */

//ToDO: Comment


/*
 * elements[0 + 0 * 4] elements[0 + 1 * 4] elements[0 + 2 * 4] elements[0 + 3 * 4]
 * elements[1 + 0 * 4] elements[1 + 1 * 4] elements[1 + 2 * 4] elements[1 + 3 * 4]
 * elements[2 + 0 * 4] elements[2 + 1 * 4] elements[2 + 2 * 4] elements[2 + 3 * 4]
 * elements[3 + 0 * 4] elements[3 + 1 * 4] elements[3 + 2 * 4] elements[3 + 3 * 4]
 */
@SuppressWarnings("PointlessArithmeticExpression")
public class Matrix4f {

    public static final int SIZE = 4 * 4;
    public float[] elements;

    public Matrix4f() {
        elements = new float[SIZE];
    }

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            result.elements[i] = 0.0f;
        }
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;

        return result;
    }

    public static Matrix4f multiply(Matrix4f matrix, Matrix4f second_matrix) {
        Matrix4f result = new Matrix4f();
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                float sum = 0.0f;
                for (int e = 0; e < 4; e++) {
                    sum += second_matrix.elements[x + e * 4] * matrix.elements[e + y * 4];
                }
                result.elements[x + y * 4] = sum;
            }
        }
        return result;
    }

    public static Vector4f transform(Matrix4f left, Vector4f right, Vector4f dest) {
        if (dest == null)
            dest = new Vector4f();

        float x = left.elements[0 + 0 * 4] * right.x + left.elements[1 + 0 * 4] * right.y + left.elements[2 + 0 * 4] * right.z + left.elements[3 + 0 * 4] * right.w;
        float y = left.elements[0 + 1 * 4] * right.x + left.elements[1 + 1 * 4] * right.y + left.elements[2 + 1 * 4] * right.z + left.elements[3 + 1 * 4] * right.w;
        float z = left.elements[0 + 2 * 4] * right.x + left.elements[1 + 2 * 4] * right.y + left.elements[2 + 2 * 4] * right.z + left.elements[3 + 2 * 4] * right.w;
        float w = left.elements[0 + 3 * 4] * right.x + left.elements[1 + 3 * 4] * right.y + left.elements[2 + 3 * 4] * right.z + left.elements[3 + 3 * 4] * right.w;

        dest.x = x;
        dest.y = y;
        dest.z = z;
        dest.w = w;

        return dest;
    }

    public static Matrix4f scale(Vector3f vec, Matrix4f src, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();
        dest.elements[0 + 0 * 4] = src.elements[0 + 0 * 4] * vec.x;
        dest.elements[0 + 1 * 4] = src.elements[0 + 1 * 4] * vec.x;
        dest.elements[0 + 2 * 4] = src.elements[0 + 2 * 4] * vec.x;
        dest.elements[0 + 3 * 4] = src.elements[0 + 3 * 4] * vec.x;
        dest.elements[1 + 0 * 4] = src.elements[1 + 0 * 4] * vec.y;
        dest.elements[1 + 1 * 4] = src.elements[1 + 1 * 4] * vec.y;
        dest.elements[1 + 2 * 4] = src.elements[1 + 2 * 4] * vec.y;
        dest.elements[1 + 3 * 4] = src.elements[1 + 3 * 4] * vec.y;
        dest.elements[2 + 0 * 4] = src.elements[2 + 0 * 4] * vec.z;
        dest.elements[2 + 1 * 4] = src.elements[2 + 1 * 4] * vec.z;
        dest.elements[2 + 2 * 4] = src.elements[2 + 2 * 4] * vec.z;
        dest.elements[2 + 3 * 4] = src.elements[2 + 3 * 4] * vec.z;
        return dest;
    }

    public static Matrix4f rotate(float angle, Vector3f axis, Matrix4f src, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();
        float c = (float) Math.cos(angle);
        float s = (float) Math.sin(angle);
        float oneminusc = 1.0f - c;
        float xy = axis.x*axis.y;
        float yz = axis.y*axis.z;
        float xz = axis.x*axis.z;
        float xs = axis.x*s;
        float ys = axis.y*s;
        float zs = axis.z*s;

        float f00 = axis.x*axis.x*oneminusc+c;
        float f01 = xy*oneminusc+zs;
        float f02 = xz*oneminusc-ys;
        // n[3] not used
        float f10 = xy*oneminusc-zs;
        float f11 = axis.y*axis.y*oneminusc+c;
        float f12 = yz*oneminusc+xs;
        // n[7] not used
        float f20 = xz*oneminusc+ys;
        float f21 = yz*oneminusc-xs;
        float f22 = axis.z*axis.z*oneminusc+c;

        float t00 = src.elements[0 + 0 * 4] * f00 + src.elements[1 + 0 * 4] * f01 + src.elements[2 + 0 * 4] * f02;
        float t01 = src.elements[0 + 1 * 4] * f00 + src.elements[1 + 1 * 4] * f01 + src.elements[2 + 1 * 4] * f02;
        float t02 = src.elements[0 + 2 * 4] * f00 + src.elements[1 + 2 * 4] * f01 + src.elements[2 + 2 * 4] * f02;
        float t03 = src.elements[0 + 3 * 4] * f00 + src.elements[1 + 3 * 4] * f01 + src.elements[2 + 3 * 4] * f02;
        float t10 = src.elements[0 + 0 * 4] * f10 + src.elements[1 + 0 * 4] * f11 + src.elements[2 + 0 * 4] * f12;
        float t11 = src.elements[0 + 1 * 4] * f10 + src.elements[1 + 1 * 4] * f11 + src.elements[2 + 1 * 4] * f12;
        float t12 = src.elements[0 + 2 * 4] * f10 + src.elements[1 + 2 * 4] * f11 + src.elements[2 + 2 * 4] * f12;
        float t13 = src.elements[0 + 3 * 4] * f10 + src.elements[1 + 3 * 4] * f11 + src.elements[2 + 3 * 4] * f12;
        dest.elements[2 + 0 * 4] = src.elements[0 + 0 * 4] * f20 + src.elements[1 + 0 * 4] * f21 + src.elements[2 + 0 * 4] * f22;
        dest.elements[2 + 1 * 4] = src.elements[0 + 1 * 4] * f20 + src.elements[1 + 1 * 4] * f21 + src.elements[2 + 1 * 4] * f22;
        dest.elements[2 + 2 * 4] = src.elements[0 + 2 * 4] * f20 + src.elements[1 + 2 * 4] * f21 + src.elements[2 + 2 * 4] * f22;
        dest.elements[2 + 3 * 4] = src.elements[0 + 3 * 4] * f20 + src.elements[1 + 3 * 4] * f21 + src.elements[2 + 3 * 4] * f22;
        dest.elements[0 + 0 * 4] = t00;
        dest.elements[0 + 1 * 4] = t01;
        dest.elements[0 + 2 * 4] = t02;
        dest.elements[0 + 3 * 4] = t03;
        dest.elements[1 + 0 * 4] = t10;
        dest.elements[1 + 1 * 4] = t11;
        dest.elements[1 + 2 * 4] = t12;
        dest.elements[1 + 3 * 4] = t13;
        return dest;
    }

    public static Matrix4f rotate(float angle, float x, float y, float z) {
        Matrix4f rotation = new Matrix4f();

        float c = (float) Math.cos(Math.toRadians(angle));
        float s = (float) Math.sin(Math.toRadians(angle));
        Vector3f vec = new Vector3f(x, y, z);
        if (vec.length() != 1f) {
            vec = vec.normalize(null);
            x = vec.x;
            y = vec.y;
            z = vec.z;
        }

        rotation.elements[0 + 0 * 4] = x * x * (1f - c) + c;
        rotation.elements[1 + 0 * 4] = y * x * (1f - c) + z * s;
        rotation.elements[2 + 0 * 4] = x * z * (1f - c) - y * s;
        rotation.elements[0 + 1 * 4] = x * y * (1f - c) - z * s;
        rotation.elements[1 + 1 * 4] = y * y * (1f - c) + c;
        rotation.elements[2 + 1 * 4] = y * z * (1f - c) + x * s;
        rotation.elements[0 + 2 * 4] = x * z * (1f - c) + y * s;
        rotation.elements[1 + 2 * 4] = y * z * (1f - c) - x * s;
        rotation.elements[2 + 2 * 4] = z * z * (1f - c) + c;

        return rotation;
    }

    public static Matrix4f scale(float x, float y, float z) {
        Matrix4f scaling = new Matrix4f();

        scaling.elements[0 + 0 * 4] = x;
        scaling.elements[1 + 1 * 4] = y;
        scaling.elements[2 + 2 * 4] = z;

        return scaling;
    }

    public static Matrix4f translate(float x, float y, float z) {
        Matrix4f translation = new Matrix4f();

        translation.elements[0 + 3 * 4] = x;
        translation.elements[1 + 3 * 4] = y;
        translation.elements[2 + 3 * 4] = z;

        return translation;
    }

    public static Matrix4f translate(Vector3f b) {
        return Matrix4f.translate(b.x, b.y, b.z);
    }

    public static Matrix4f translate(Vector3f vec, Matrix4f src, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();

        dest.elements[3 + 0 * 4] += src.elements[0 + 0 * 4] * vec.x + src.elements[1 + 0 * 4] * vec.y + src.elements[2 + 0 * 4] * vec.z;
        dest.elements[3 + 1 * 4] += src.elements[0 + 1 * 4] * vec.x + src.elements[1 + 1 * 4] * vec.y + src.elements[2 + 1 * 4] * vec.z;
        dest.elements[3 + 2 * 4] += src.elements[0 + 2 * 4] * vec.x + src.elements[1 + 2 * 4] * vec.y + src.elements[2 + 2 * 4] * vec.z;
        dest.elements[3 + 3 * 4] += src.elements[0 + 3 * 4] * vec.x + src.elements[1 + 3 * 4] * vec.y + src.elements[2 + 3 * 4] * vec.z;

        return dest;
    }

    public static Matrix4f transpose(Matrix4f src, Matrix4f dest) {
        if (dest == null)
            dest = new Matrix4f();
        dest.elements[0 + 0 * 4] = src.elements[0 + 0 * 4];
        dest.elements[0 + 1 * 4] = src.elements[1 + 0 * 4];
        dest.elements[0 + 2 * 4] = src.elements[2 + 0 * 4];
        dest.elements[0 + 3 * 4] = src.elements[3 + 0 * 4];
        dest.elements[1 + 0 * 4] = src.elements[0 + 1 * 4];
        dest.elements[1 + 1 * 4] = src.elements[1 + 1 * 4];
        dest.elements[1 + 2 * 4] = src.elements[2 + 1 * 4];
        dest.elements[1 + 3 * 4] = src.elements[3 + 1 * 4];
        dest.elements[2 + 0 * 4] = src.elements[0 + 2 * 4];
        dest.elements[2 + 1 * 4] = src.elements[1 + 2 * 4];
        dest.elements[2 + 2 * 4] = src.elements[2 + 2 * 4];
        dest.elements[2 + 3 * 4] = src.elements[3 + 2 * 4];
        dest.elements[3 + 0 * 4] = src.elements[0 + 3 * 4];
        dest.elements[3 + 1 * 4] = src.elements[1 + 3 * 4];
        dest.elements[3 + 2 * 4] = src.elements[2 + 3 * 4];
        dest.elements[3 + 3 * 4] = src.elements[3 + 3 * 4];

        return dest;
    }

    public float determinant() {
        float f =
                elements[0 + 0 * 4]
                        * ((elements[1 + 1 * 4] * elements[2 + 2 * 4] * elements[3 + 3 * 4] + elements[1 + 2 * 4] * elements[2 + 3 * 4] * elements[3 + 1 * 4] + elements[1 + 3 * 4] * elements[2 + 1 * 4] * elements[3 + 2 * 4])
                        - elements[1 + 3 * 4] * elements[2 + 2 * 4] * elements[3 + 1 * 4]
                        - elements[1 + 1 * 4] * elements[2 + 3 * 4] * elements[3 + 2 * 4]
                        - elements[1 + 2 * 4] * elements[2 + 1 * 4] * elements[3 + 3 * 4]);
        f -= elements[0 + 1 * 4]
                * ((elements[1 + 0 * 4] * elements[2 + 2 * 4] * elements[3 + 3 * 4] + elements[1 + 2 * 4] * elements[2 + 3 * 4] * elements[3 + 0 * 4] + elements[1 + 3 * 4] * elements[2 + 0 * 4] * elements[3 + 2 * 4])
                - elements[1 + 3 * 4] * elements[2 + 2 * 4] * elements[3 + 0 * 4]
                - elements[1 + 0 * 4] * elements[2 + 3 * 4] * elements[3 + 2 * 4]
                - elements[1 + 2 * 4] * elements[2 + 0 * 4] * elements[3 + 3 * 4]);
        f += elements[0 + 2 * 4]
                * ((elements[1 + 0 * 4] * elements[2 + 1 * 4] * elements[3 + 3 * 4] + elements[1 + 1 * 4] * elements[2 + 3 * 4] * elements[3 + 0 * 4] + elements[1 + 3 * 4] * elements[2 + 0 * 4] * elements[3 + 1 * 4])
                - elements[1 + 3 * 4] * elements[2 + 1 * 4] * elements[3 + 0 * 4]
                - elements[1 + 0 * 4] * elements[2 + 3 * 4] * elements[3 + 1 * 4]
                - elements[1 + 1 * 4] * elements[2 + 0 * 4] * elements[3 + 3 * 4]);
        f -= elements[0 + 3 * 4]
                * ((elements[1 + 0 * 4] * elements[2 + 1 * 4] * elements[3 + 2 * 4] + elements[1 + 1 * 4] * elements[2 + 2 * 4] * elements[3 + 0 * 4] + elements[1 + 2 * 4] * elements[2 + 0 * 4] * elements[3 + 1 * 4])
                - elements[1 + 2 * 4] * elements[2 + 1 * 4] * elements[3 + 0 * 4]
                - elements[1 + 0 * 4] * elements[2 + 2 * 4] * elements[3 + 1 * 4]
                - elements[1 + 1 * 4] * elements[2 + 0 * 4] * elements[3 + 2 * 4]);
        return f;
    }

    private static float determinant3x3(float t00, float t01, float t02,
                                        float t10, float t11, float t12,
                                        float t20, float t21, float t22)
    {
        return   t00 * (t11 * t22 - t12 * t21)
                + t01 * (t12 * t20 - t10 * t22)
                + t02 * (t10 * t21 - t11 * t20);
    }

    public static Matrix4f invert(Matrix4f src, Matrix4f dest) {
        float determinant = src.determinant();

        if (determinant != 0) {
            if (dest == null)
                dest = new Matrix4f();
            float determinant_inv = 1f/determinant;

            // first row
            float t00 =  determinant3x3(src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
            float t01 = -determinant3x3(src.elements[1 + 0 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
            float t02 =  determinant3x3(src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 3 * 4]);
            float t03 = -determinant3x3(src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4]);
            // second row
            float t10 = -determinant3x3(src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
            float t11 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
            float t12 = -determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 3 * 4]);
            float t13 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4]);
            // third row
            float t20 =  determinant3x3(src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
            float t21 = -determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 0 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 2 * 4], src.elements[3 + 3 * 4]);
            float t22 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 3 * 4], src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 3 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 3 * 4]);
            float t23 = -determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[3 + 0 * 4], src.elements[3 + 1 * 4], src.elements[3 + 2 * 4]);
            // fourth row
            float t30 = -determinant3x3(src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4]);
            float t31 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 2 * 4], src.elements[0 + 3 * 4], src.elements[1 + 0 * 4], src.elements[1 + 2 * 4], src.elements[1 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 2 * 4], src.elements[2 + 3 * 4]);
            float t32 = -determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 3 * 4], src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 3 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 3 * 4]);
            float t33 =  determinant3x3(src.elements[0 + 0 * 4], src.elements[0 + 1 * 4], src.elements[0 + 2 * 4], src.elements[1 + 0 * 4], src.elements[1 + 1 * 4], src.elements[1 + 2 * 4], src.elements[2 + 0 * 4], src.elements[2 + 1 * 4], src.elements[2 + 2 * 4]);

            // transpose and divide by the determinant
            dest.elements[0 + 0 * 4] = t00*determinant_inv;
            dest.elements[1 + 1 * 4] = t11*determinant_inv;
            dest.elements[2 + 2 * 4] = t22*determinant_inv;
            dest.elements[3 + 3 * 4] = t33*determinant_inv;
            dest.elements[0 + 1 * 4] = t10*determinant_inv;
            dest.elements[1 + 0 * 4] = t01*determinant_inv;
            dest.elements[2 + 0 * 4] = t02*determinant_inv;
            dest.elements[0 + 2 * 4] = t20*determinant_inv;
            dest.elements[1 + 2 * 4] = t21*determinant_inv;
            dest.elements[2 + 1 * 4] = t12*determinant_inv;
            dest.elements[0 + 3 * 4] = t30*determinant_inv;
            dest.elements[3 + 0 * 4] = t03*determinant_inv;
            dest.elements[1 + 3 * 4] = t31*determinant_inv;
            dest.elements[3 + 1 * 4] = t13*determinant_inv;
            dest.elements[3 + 2 * 4] = t23*determinant_inv;
            dest.elements[2 + 3 * 4] = t32*determinant_inv;
            return dest;
        } else
            return null;
    }

    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f ortho = new Matrix4f();

        float tx = -(right + left) / (right - left);
        float ty = -(top + bottom) / (top - bottom);
        float tz = -(far + near) / (far - near);

        ortho.elements[0 + 0 * 4] = 2f / (right - left);
        ortho.elements[1 + 1 * 4] = 2f / (top - bottom);
        ortho.elements[2 + 2 * 4] = -2f / (far - near);
        ortho.elements[0 + 3 * 4] = tx;
        ortho.elements[1 + 3 * 4] = ty;
        ortho.elements[2 + 3 * 4] = tz;

        return ortho;
    }

    public static Matrix4f frustum(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f frustum = new Matrix4f();

        float a = (right + left) / (right - left);
        float b = (top + bottom) / (top - bottom);
        float c = -(far + near) / (far - near);
        float d = -(2f * far * near) / (far - near);

        frustum.elements[0 + 0 * 4] = (2f * near) / (right - left);
        frustum.elements[1 + 1 * 4] = (2f * near) / (top - bottom);
        frustum.elements[0 + 2 * 4] = a;
        frustum.elements[1 + 2 * 4] = b;
        frustum.elements[2 + 2 * 4] = c;
        frustum.elements[3 + 2 * 4] = -1f;
        frustum.elements[2 + 3 * 4] = d;
        frustum.elements[3 + 3 * 4] = 0f;

        return frustum;
    }

    public static Matrix4f perspective(float fovy, float aspect, float near, float far) {
        Matrix4f perspective = new Matrix4f();

        float f = (float) (1f / Math.tan(Math.toRadians(fovy) / 2f));

        perspective.elements[0 + 0 * 4] = f / aspect;
        perspective.elements[1 + 1 * 4] = f;
        perspective.elements[2 + 2 * 4] = (far + near) / (near - far);
        perspective.elements[3 + 2 * 4] = -1f;
        perspective.elements[2 + 3 * 4] = (2f * far * near) / (near - far);
        perspective.elements[3 + 3 * 4] = 0f;

        return perspective;
    }

    //ToDo: copied function
    public static Matrix4f ViewMatrix(Vector3f angles, Vector3f position) {
        float sx, sy, sz, cx, cy, cz, theta;
        // rotation angle about X-axis (pitch)
        theta = (float) Math.toRadians(angles.x);
        sx = (float) Math.sin(theta);
        cx = (float) Math.cos(theta);

        // rotation angle about Y-axis (yaw)
        theta = (float) Math.toRadians(angles.y);
        sy = (float) Math.sin(theta);
        cy = (float) Math.cos(theta);

        // rotation angle about Z-axis (roll)
        theta = (float) Math.toRadians(angles.z);
        sz = (float) Math.sin(theta);
        cz = (float) Math.cos(theta);

        Matrix4f result = identity();

        // determine left axis
        result.elements[0 + 0 * 4] = cy * cz;
        result.elements[1 + 0 * 4] = sx * sy * cz + cx * sz;
        result.elements[2 + 0 * 4] = -cx * sy * cz + sx * sz;

        // determine up axis
        result.elements[0 + 1 * 4] = -cy * sz;
        result.elements[1 + 1 * 4] = -sx * sy * sz + cx * cz;
        result.elements[2 + 1 * 4] = cx * sy * sz + sx * cz;

        // determine forward axis
        result.elements[0 + 2 * 4] = sy;
        result.elements[1 + 2 * 4] = -sx * cy;
        result.elements[2 + 2 * 4] = cx * cy;

        result = Matrix4f.multiply(Matrix4f.translate(position), result);

        return result;
    }

    public FloatBuffer toFloatBuffer() {
        return BufferUtils.createFloatBuffer(elements);
    }
}

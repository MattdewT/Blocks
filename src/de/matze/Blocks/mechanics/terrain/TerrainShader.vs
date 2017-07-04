#version 130

attribute vec3 position;
attribute vec3 normal;

flat varying vec3 v_diffuse;

uniform mat4 pr_matrix;
uniform mat4 view_matrix;
uniform mat4 ml_matrix;

void main()
{
	gl_Position = pr_matrix * view_matrix * ml_matrix * vec4(position, 1.0);

	vec3 unitNormal = normalize(normal);
    vec3 unitLightVector = normalize(vec3(0,1,0));      //get Ligthpsotions instead of Vec3(1)

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.0);
    vec3 diffuse = brightness * vec3(1);        //get LigthColor instead of Vec3(1)

    v_diffuse = diffuse * vec3(0.5f, 0.35f, 0.05f);
}


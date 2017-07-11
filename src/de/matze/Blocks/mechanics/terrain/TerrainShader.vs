#version 130

attribute vec3 position;
attribute vec3 normal;

flat varying vec3 v_normal;

uniform mat4 pr_matrix;
uniform mat4 view_matrix;
uniform mat4 ml_matrix;

void main()
{
	gl_Position = pr_matrix * view_matrix * ml_matrix * vec4(position, 1.0);

    v_normal = normal;
}


#version 130

attribute vec3 position;
attribute vec3 normal;

uniform mat4 pr_matrix;
uniform mat4 view_matrix;

void main()
{
	gl_Position = pr_matrix * view_matrix * vec4(position, 1.0);
}


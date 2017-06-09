attribute vec3 position;
attribute vec2 textures;
attribute vec3 normal;

varying vec3 OutNorms;
varying vec2 OutCoord;

uniform mat4 pr_matrix;
uniform mat4 view_matrix;
uniform mat4 ml_matrix;

void main()
{
	vec4 worldPosition = ml_matrix * vec4(position, 1.0);
	gl_Position = pr_matrix * view_matrix * worldPosition;
	OutCoord = textures;
	//OutNorms = normal;
}


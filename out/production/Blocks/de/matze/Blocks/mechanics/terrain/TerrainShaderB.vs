#version 130

attribute vec3 position;
attribute vec3 normal;

varying vec3 ec_pos;
varying float v_blend_factor;
varying mat4 v_pr_matrix;

uniform mat4 pr_matrix;
uniform mat4 view_matrix;
uniform mat4 ml_matrix;

uniform vec4 clipPlane;

uniform vec2 center;
uniform float inner;
uniform float outer;

void main()
{
	vec4 world_position = ml_matrix * vec4(position, 1.0);
	gl_Position = pr_matrix * view_matrix * world_position;

	ec_pos = gl_Position.xyz;

	gl_ClipDistance[0] = dot(world_position, clipPlane);

	v_pr_matrix = pr_matrix;

	float distance = distance(vec2(center), world_position.xz);
    v_blend_factor = clamp(((distance - inner) / (outer - inner)), 0.0, 1.0);
}


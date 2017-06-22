#version 130

varying vec3 v_norm;

void main()
{
	gl_FragColor = vec4(normalize(v_norm),1);
}

#version 130

flat varying vec3 v_diffuse;

void main()
{
	gl_FragColor = vec4(v_diffuse,1);
}

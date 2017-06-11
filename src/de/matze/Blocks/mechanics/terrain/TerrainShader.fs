varying vec3 OutNorms;
varying vec2 OutCoord;

uniform sampler2D Sampler;

void main()
{
	//gl_FragColor = texture2D(Sampler, OutCoord);
	//vec3 unit = normalize(OutNorms);
	gl_FragColor = vec4(0,1,1,1);
}

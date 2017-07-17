#version 130

//ToDo: Copied File

varying vec4 clipSpace;
varying vec2 textureCoords;
varying vec3 toCameraVector;

uniform sampler2D reflectionTexture;
uniform sampler2D refractionTexture;
uniform sampler2D dudvMap;
uniform sampler2D depthMap;
uniform float near;
uniform float far;

uniform float moveFactor;

const float waveStrength = 0.02;

void main(void) {
	vec2 ndc = (clipSpace.xy/clipSpace.w)/2.0 + 0.5;
	vec2 reflectionCoords = vec2(ndc.x, -ndc.y);
	vec2 refractionCoords = vec2(ndc.x,  ndc.y);
	
	float depth = texture2D(depthMap, refractionCoords).r;
	float floorDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));

	depth = gl_FragCoord.z;
	float waterDistance = 2.0 * near * far / (far + near - (2.0 * depth - 1.0) * (far - near));
	float waterDepth = floorDistance - waterDistance;

	vec2 disortion1 = (texture2D(dudvMap, vec2(textureCoords.x + moveFactor, textureCoords.y)).rg * 2.0 -1.0) * waveStrength  * clamp(waterDepth/20.0, 0.0, 1.0);;
	vec2 disortion2 = (texture2D(dudvMap, vec2(-textureCoords.x, textureCoords.y + moveFactor)).rg * 2.0 -1.0) * waveStrength  * clamp(waterDepth/20.0, 0.0, 1.0);;
	vec2 totalDisortion = disortion1 + disortion2;

	refractionCoords += totalDisortion;
	refractionCoords = clamp(refractionCoords, 0.001, 0.999);

	reflectionCoords += totalDisortion;
	reflectionCoords.x = clamp(reflectionCoords.x, 0.001, 0.999);
	reflectionCoords.y = clamp(reflectionCoords.y, -0.999, -0.001);
	
	vec4 ReflectionColor = texture2D(reflectionTexture, reflectionCoords);
	vec4 RefractionColor = texture2D(refractionTexture, refractionCoords);
	
	vec3 viewVector = normalize(toCameraVector);
	float refractiveFactor = dot(vec3(0,-1,0), viewVector);
	refractiveFactor = pow(refractiveFactor, 0.5);
	refractiveFactor = clamp(refractiveFactor, 0.4, 0.8);
	
	gl_FragColor = mix(ReflectionColor, RefractionColor, refractiveFactor);
	gl_FragColor.a = clamp(waterDepth/5.0, 0.0, 1.0);
	gl_FragColor = mix(gl_FragColor, vec4(0.0, 0.3, 0.5, 1.0), 0.2);
	//gl_FragColor = vec4(0.0, 0.0, 1.0, 1.0);
}
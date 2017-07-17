#version 130

attribute vec2 position;

varying  vec2 blurTextureCoords[11];

uniform float targetHeight;

void main() 
{
	gl_Position = vec4(position, 0, 1);
	vec2 TextureCenter = position * 0.5 + 0.5;
	float pixelsize = 1.0 / targetHeight;
	
	for(int i = -5; i <= 5; i++) {
		blurTextureCoords[i+5] = TextureCenter + vec2(pixelsize * i, 0.0);
 	}
 }


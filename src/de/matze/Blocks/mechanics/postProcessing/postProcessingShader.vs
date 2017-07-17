attribute vec2 position;

varying vec2 OutCoord;

void main() 
{
	gl_Position = vec4(position, 0, 1);
	OutCoord = gl_Position.xy * 0.5 + 0.5;
}


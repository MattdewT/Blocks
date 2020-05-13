//ToDo: copied class

attribute vec3 position;

varying vec3 textureCoords;

uniform mat4 pr_matrix;
uniform mat4 view_matrix;

void main(void){
	
	gl_Position = pr_matrix * view_matrix * vec4(position, 1.0); 
	textureCoords = position;
	
}

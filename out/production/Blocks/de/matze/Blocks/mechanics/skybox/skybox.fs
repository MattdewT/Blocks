//ToDo: copied class

varying vec3 textureCoords;

uniform samplerCube cubeMap;

void main(void){
    gl_FragColor = textureCube(cubeMap, textureCoords);
}



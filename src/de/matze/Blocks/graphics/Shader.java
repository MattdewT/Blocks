package de.matze.Blocks.graphics;


import de.matze.Blocks.maths.*;
import de.matze.Blocks.utils.FileUtils;

import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL20.*;

//ToDo: Comments

public abstract class Shader {

    private int programm_ID;
    private int vertex_shader_ID;
    private int fragement_shader_ID;

    public boolean debug_msg = true;

    public Shader(String vertPath, String fragPath) {
        create(vertPath, fragPath);
        bindAttributes();
        getAllUniformLocations();
    }

    //---------------------Attribute Stuff---------------------

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName) {
        glBindAttribLocation(programm_ID, attribute, variableName);
    }

    //---------------------Uniform Stuff---------------------

    protected abstract void getAllUniformLocations();

    protected int getUniformLocation(String uniform_name) {
        int result = glGetUniformLocation(programm_ID, uniform_name);
        if (result == -1)
            System.err.println("Could not find uniform variable '" + uniform_name + "'!");
        return result;
    }

    protected void setUniform1i(int location, int value) {
        glUniform1i(location, value);
    }

    protected void setUniform1f(int location, float value) {
        glUniform1f(location, value);
    }

    protected void setUniform2f(int location, float x, float y) {
        glUniform2f(location, x, y);
    }

    protected void setUniform3f(int location, Vector3f vector) {
        glUniform3f(location, vector.x, vector.y, vector.z);
    }

    protected void setUniform4f(int location, Vector4f vector) {
        glUniform4f(location, vector.x, vector.y, vector.z, vector.w);
    }

    protected void setUniformMat4f(int location, Matrix4f matrix) {
        glUniformMatrix4fv(location, false, matrix.toFloatBuffer());
    }

    //---------------------Other Stuff---------------------

    public void enable() {
        glUseProgram(programm_ID);
    }

    public void disable() {
        glUseProgram(0);
    }

    public void cleanUp() {
        disable();
        glDetachShader(programm_ID, vertex_shader_ID);
        glDetachShader(programm_ID, fragement_shader_ID);
        glDeleteShader(vertex_shader_ID);
        glDeleteShader(fragement_shader_ID);
        glDeleteProgram(programm_ID);
    }

    //---------------------Init Stuff---------------------

    private void create(String vertPath, String fragPath) {
        String vert = FileUtils.loadAsString(vertPath);
        String frag = FileUtils.loadAsString(fragPath);

        int program = glCreateProgram();
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(vertID, vert);
        glShaderSource(fragID, frag);

        if(debug_msg)
            System.out.println("Compiling : " + vertPath);
        glCompileShader(vertID);
        if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader!");
            System.err.println(glGetShaderInfoLog(vertID));
        } else {
            if(debug_msg)
                System.out.println("Done");
        }

        if(debug_msg)
            System.out.println("Compiling : " + fragPath);
        glCompileShader(fragID);
        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader!");
            System.err.println(glGetShaderInfoLog(fragID));
        } else {
            if(debug_msg)
                System.out.println("Done");
        }

        glAttachShader(program, vertID);
        glAttachShader(program, fragID);
        glLinkProgram(program);
        glValidateProgram(program);

        vertex_shader_ID = vertID;
        fragement_shader_ID = fragID;
        programm_ID = program;
    }
}

#version 130

varying vec3 v_normal;
varying float v_blend_factor;

void main()
{

	vec3 unitNormal = normalize(v_normal);
    vec3 unitLightVector = normalize(vec3(0,1,0));      //get Ligthpsotions instead of Vec3(1)

    float nDot1 = dot(unitNormal, unitLightVector);
    float brightness = max(nDot1, 0.0);
    vec3 diffuse = brightness * vec3(1);        //get LigthColor instead of Vec3(1)

    vec3 v_diffuse = diffuse * vec3(0.5f, 0.35f, 0.05f);

	gl_FragColor = vec4(mix(v_diffuse, vec3(1), v_blend_factor),1);
}

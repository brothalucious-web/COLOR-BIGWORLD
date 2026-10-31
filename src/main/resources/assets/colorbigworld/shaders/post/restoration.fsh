#version 150
uniform sampler2D InSampler;
uniform float GameTime;
in vec2 texCoord;
out vec4 fragColor;

float hash1(vec3 p) {
    return fract(sin(dot(p, vec3(127.1,311.7,74.7))) * 43758.5453123);
}
vec3 hsv2rgb(vec3 c) {
    vec3 p = abs(fract(c.xxx+vec3(0.,2./3.,1./3.))*6.-3.);
    return c.z * mix(vec3(1.),clamp(p-1.,0.,1.),c.y);
}
void main() {
    vec4 source = texture(InSampler, texCoord);
    float luma = dot(source.rgb, vec3(0.299,0.587,0.114));
    vec3 gray = vec3(luma);

    // PROTOTYPE: GameTime is the game's day clock, not an independent timer.
    // It must be replaced before claiming an accurate 30-second reset.
    float slot = GameTime * 40.0;
    float epoch = floor(slot);
    float progress = fract(slot);
    vec3 groupKey = floor(source.rgb*12.0+0.5);
    float pick = hash1(groupKey + vec3(epoch*2.3));
    vec3 recolored = hsv2rgb(vec3(
        hash1(groupKey+vec3(epoch*3.7+11.0)), 0.88, max(luma,0.25)
    ));
    // Only restored pixels receive color. The 45/55 split is per RGB key,
    // not a guarantee of the same proportion of SCREEN AREA.
    vec3 selected = pick < 0.45 ? source.rgb : recolored;
    float screenKey = hash1(vec3(floor(gl_FragCoord.xy/2.0), epoch));
    vec3 result = screenKey < progress ? selected : gray;
    fragColor = vec4(result, source.a);
}

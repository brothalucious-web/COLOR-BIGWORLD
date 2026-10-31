#version 150
uniform sampler2D InSampler;
uniform float GameTime;
in vec2 texCoord;
out vec4 fragColor;

float hash3(vec3 p) {
    p = fract(p * 0.1031);
    p += dot(p, p.yxz + 33.33);
    return fract((p.x+p.y)*p.z);
}
vec3 hsvToRgb(vec3 c) {
    vec3 p = abs(fract(c.xxx + vec3(0.0,2.0/3.0,1.0/3.0))*6.0-3.0);
    return c.z*mix(vec3(1.0),clamp(p-1.0,0.0,1.0),c.y);
}
void main() {
    vec4 source = texture(InSampler,texCoord);
    vec3 rgb = source.rgb;
    float gray = dot(rgb,vec3(0.299,0.587,0.114));

    // Known limitation: GameTime is world time, NOT a stand-alone 30s clock.
    float cycles = GameTime * 40.0;
    float cycle = floor(cycles);
    float progress = fract(cycles);

    // Source-color groups are independent of the spatial screen positions.
    // Every matching quantized RGB key gets the same original/random choice.
    vec3 group = floor(rgb*15.0+0.5);
    float originalRoll = hash3(group + vec3(cycle*7.0,cycle*3.0,cycle*5.0));
    float hue = hash3(group + vec3(cycle*11.0+51.0));
    vec3 randomized = hsvToRgb(vec3(hue,0.88,max(0.18,gray)));
    vec3 restored = originalRoll < 0.45 ? rgb : randomized;

    // Instant group-level activation. A group either has color or is gray.
    // This is NOT an exact world-area counter: some keys cover more area.
    float revealRoll = hash3(group + vec3(cycle*13.0+91.0));
    vec3 result = revealRoll < progress ? restored : vec3(gray);
    fragColor = vec4(result,source.a);
}

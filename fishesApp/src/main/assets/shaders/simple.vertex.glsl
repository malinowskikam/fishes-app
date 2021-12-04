
attribute vec4 position;
attribute vec2 uv;
uniform mat4 vpMatrix;
uniform mat4 modelMatrix;
varying vec4 frag_position;
varying vec2 frag_uv;

void main() {
    gl_Position = vpMatrix * modelMatrix * position;
    frag_position = modelMatrix * position;
    frag_uv = uv;
}

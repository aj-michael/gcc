#include <jni.h>
#include "Squarer.h"

JNIEXPORT jint JNICALL Java_Squarer_square(JNIEnv *env, jobject obj, jint i) {
  return i*i;
}

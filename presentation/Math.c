#include <math.h>
#include <jni.h>
#include "Math.h"

JNIEXPORT jdouble JNICALL Java_Math_sqrt(JNIEnv *env, jobject obj, jdouble x) {
  return sqrt(x);
}

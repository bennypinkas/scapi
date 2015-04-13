/**
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* 
* Copyright (c) 2012 - SCAPI (http://crypto.biu.ac.il/scapi)
* This file is part of the SCAPI project.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
* 
* Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"),
* to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, 
* and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
* 
* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
* 
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
* FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
* WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
* 
* We request that any publication and/or code referring to and/or based on SCAPI contain an appropriate citation to SCAPI, including a reference to
* http://crypto.biu.ac.il/SCAPI.
* 
* SCAPI uses Crypto++, Miracl, NTL and Bouncy Castle. Please see these projects for any further licensing issues.
* %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
* 
*/

/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC */

#ifndef _Included_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC
#define _Included_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC
 * Method:    createHMAC
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC_createHMAC
  (JNIEnv *, jobject, jstring);

/*
 * Class:     edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC
 * Method:    setKey
 * Signature: (JLjava/lang/String;[B)V
 */
JNIEXPORT void JNICALL Java_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC_setKey
  (JNIEnv *, jobject, jlong, jbyteArray);

/*
 * Class:     edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC
 * Method:    getNativeBlockSize
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC_getNativeBlockSize
  (JNIEnv *, jobject, jlong);

JNIEXPORT jstring JNICALL Java_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC_getName
  (JNIEnv *, jobject, jlong);
/*
 * Class:     edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC
 * Method:    updateNative
 * Signature: (J[BII)V
 */
JNIEXPORT void JNICALL Java_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC_updateNative
  (JNIEnv *, jobject, jlong, jbyteArray, jint, jint);

/*
 * Class:     edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC
 * Method:    updateFinal
 * Signature: (J[BI)V
 */
JNIEXPORT void JNICALL Java_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC_updateFinal
  (JNIEnv *, jobject, jlong, jbyteArray, jint);

/*
 * Class:     edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC
 * Method:    deleteNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_edu_biu_scapi_primitives_prf_openSSL_OpenSSLHMAC_deleteNative
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif
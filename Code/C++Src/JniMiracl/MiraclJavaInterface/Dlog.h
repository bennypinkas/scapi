/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
#include "miracl.h"
#include <vector>

using namespace std;

/* Header for class edu_biu_scapi_primitives_dlog_miracl_MiraclAdapterDlogEC */

#ifndef _Included_edu_biu_scapi_primitives_dlog_miracl_MiraclAdapterDlogEC
#define _Included_edu_biu_scapi_primitives_dlog_miracl_MiraclAdapterDlogEC
#ifdef __cplusplus
extern "C" {
#endif

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclAdapterDlogEC
 * Method:    createMip
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclAdapterDlogEC_createMip
  (JNIEnv *, jobject);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    initFpCurve
 * Signature: (J[B[B[B)V
 */
JNIEXPORT void JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_initFpCurve
  (JNIEnv *, jobject, jlong, jbyteArray, jbyteArray, jbyteArray);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    multiplyFpPoints
 * Signature: (JJJ)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_multiplyFpPoints
  (JNIEnv *, jobject, jlong, jlong, jlong);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    simultaneousMultiplyFp
 * Signature: (J[J[[B)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_simultaneousMultiplyFp
  (JNIEnv *, jobject, jlong, jlongArray, jobjectArray);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    exponentiateFpPoint
 * Signature: (JJ[B)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_exponentiateFpPoint
  (JNIEnv *, jobject, jlong, jlong, jbyteArray);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    invertFpPoint
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_invertFpPoint
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    validateFpGenerator
 * Signature: (JJ[B[B)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_validateFpGenerator
  (JNIEnv *, jobject, jlong, jlong, jbyteArray, jbyteArray);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    isFpMember
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_isFpMember
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    createInfinityFpPoint
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_createInfinityFpPoint
  (JNIEnv *, jobject, jlong);


/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m
 * Method:    initF2mCurve
 * Signature: (IIII[B[B)J
 */
JNIEXPORT void JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_initF2mCurve
  (JNIEnv *, jobject, jlong, jint, jint, jint, jint, jbyteArray, jbyteArray);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m
 * Method:    multiplyF2mPoints
 * Signature: (JJJ)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_multiplyF2mPoints
  (JNIEnv *, jobject, jlong, jlong, jlong);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m
 * Method:    exponentiateF2mPoint
 * Signature: (JJ[B)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_exponentiateF2mPoint
  (JNIEnv *, jobject, jlong, jlong, jbyteArray);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m
 * Method:    simultaneousMultiplyF2m
 * Signature: (J[J[[B)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_simultaneousMultiplyF2m
  (JNIEnv *, jobject, jlong, jlongArray, jobjectArray);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m
 * Method:    invertF2mPoint
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_invertF2mPoint
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m
 * Method:    validateF2mGenerator
 * Signature: (JJ[B[B)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_validateF2mGenerator
  (JNIEnv *, jobject, jlong, jlong, jbyteArray, jbyteArray);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m
 * Method:    isF2mMember
 * Signature: (JJ)Z
 */
JNIEXPORT jboolean JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_isF2mMember
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp
 * Method:    createInfinityFpPoint
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_createInfinityFpPoint
  (JNIEnv *, jobject, jlong);

/*
 * Class:     edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m
 * Method:    createInfinityF2mPoint
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_createInfinityF2mPoint
  (JNIEnv *, jobject, jlong);

JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_createECFpObject
  (JNIEnv *, jobject, jlong m, jbyteArray, jbyteArray, jbyteArray);

JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_createECF2mObject
  (JNIEnv *, jobject, jlong m, jint, jint, jint, jint, jbyteArray, jbyteArray);

JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECFp_exponentiateFpWithPreComputed
  (JNIEnv *, jobject, jlong, jlong, jlong, jbyteArray);

JNIEXPORT jlong JNICALL Java_edu_biu_scapi_primitives_dlog_miracl_MiraclDlogECF2m_exponentiateF2mWithPreComputed
  (JNIEnv *, jobject, jlong, jlong, jlong, jbyteArray);
  
#ifdef __cplusplus
}
#endif
#endif

epoint* computeLL(miracl* mip, epoint** elements, big* exponents, int n, int field);
int getLLW(int t);
epoint* computeLoop(miracl* mip, big* exponentiations, int w, int h, epoint*** preComp, epoint* result, int bitIndex, int n, int field);
epoint*** createLLPreCompTable(miracl* mip, epoint** points, int w, int h, int n, int field);
epoint* getIdentity(miracl* mip, int field);

class ECFp{

private:
	big p, a, b;
	miracl* mip;
	map<epoint*, ebrick*>* exponentiationsMap;

public:
	ECFp(miracl* mip, big p, big a, big b);
	~ECFp();
	long exponentiateWithPreComputedValues(epoint* base, big exponent);
};

class ECF2m{

private:
	int m, k1, k2, k3;
	big a, b;
	miracl* mip;
	map<epoint*, ebrick2*>* exponentiationsMap;

public:
	ECF2m(miracl* mip, int m, int k1, int k2, int k3, big a, big b);
	~ECF2m();
	long exponentiateWithPreComputedValues(epoint* base, big exponent);
};

/*class GroupElementsExponentiations{
	vector<epoint*> exponentiations;
	int fp;
public:
	GroupElementsExponentiations(miracl* mip, epoint* base, bool fp);
	~GroupElementsExponentiations();
	void prepareExponentiations(big size);
	epoint* getExponentiation(big size);
};*/


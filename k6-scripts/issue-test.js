import http from 'k6/http';
import { check, sleep } from 'k6';
import { Counter } from 'k6/metrics';

export let options = {
    vus: 200,
    iterations: 200,
}
const BASE_URL   = 'http://app:8080';
const USER_COUNT = 200;

export function setup(){
    const params = { headers: { 'Content-Type': 'application/json' } };
    // couponPolicy 데이터 생성
    const couponPolicyPayload = JSON.stringify({
        name: "3천원 할인",
        minOrderAmount: 0,
        discountType: "FIX",
        discountPrice: 3000
    });
    let policyRes = http.post(`${BASE_URL}/coupon-policies`,couponPolicyPayload,params);
    console.log(policyRes.status, policyRes.body);

    // couponType 데이터 생성
    const couponTypePayload = JSON.stringify({
        name: "신규 가입 쿠폰",
        targetType: "ALL",
        targetId: 0,
        period: 30,
        deadline: null,
        couponPolicyId: policyRes.json().data.id,
        maxIssueCnt: 30
    });

    let typeRes = http.post(`${BASE_URL}/coupon-types`,couponTypePayload,params);
    const couponTypeId = typeRes.json().data.id;
    console.log(typeRes.status, typeRes.body);


    for (let i = 1; i <= USER_COUNT; i++) {
        const userPayload = JSON.stringify({
            name : `user${i}`,
            email: `user${i}@test.com`,
            birth: "1997-06-29"
        });
        http.post(`${BASE_URL}/users`,userPayload,params);
    }
    return {couponTypeId};
}

export let success200 = new Counter('enrollment_success') // 상태코드 200일때!
export let success409 = new Counter('enrollment_fail') // 상태코드 409일때!

export default function(data){
    const params = { headers: { 'Content-Type': 'application/json' } };

    const payload = JSON.stringify({
        userId: __VU,
        couponTypeId: data.couponTypeId,
        });
    const res = http.post(`${BASE_URL}/coupons/issue`, payload, params);

    // 상태 코드 201 일 때 1증가
    if(res.status === 201) success200.add(1);
    // 상태 코드 409 일 때 1증가
    if(res.status === 409) success409.add(1);

    check(res, {
        'HTTP 상태 코드 : 200 or 409': (r) => r.status === 201 || r.status === 409,
    });
    sleep(1);
}
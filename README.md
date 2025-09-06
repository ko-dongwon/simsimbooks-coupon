
# 심심북스 쿠폰 도메인

## 1. 프로젝트 소개
`심심북스`: https://github.com/nhnacademy-be8-simsimhe  

`심심북스`는 Java Spring 기반의 온라인 서점 서비스입니다.  
`심심북스 쿠폰 도메인` 프로젝트는 원본 프로젝트인 `심심북스`에서 제가 담당했던 쿠폰 도메인을 별도의 프로젝트로 분리해 성능을 고도화했습니다.  
정률 • 정액 할인, 생일 쿠폰 발급, 책•카테고리•전체 쿠폰 기능 등을 제공합니다.

## 2. 기술 스택

<div align="center">

### **애플리케이션**
<img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=java&logoColor=white"> <img src="https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white"> <img src="https://img.shields.io/badge/JPA-59666C?style=for-the-badge&logo=jpa&logoColor=white">

### **데이터베이스**
<img src="https://img.shields.io/badge/mysql-4479A1.svg?style=for-the-badge&logo=mysql&logoColor=white"> <img src="https://img.shields.io/badge/Redis-DC382D?style=for-the-badge&logo=redis&logoColor=white"> 

### **클라우드 및 모니터링**
<img src="https://img.shields.io/badge/Prometheus-E6522C?style=for-the-badge&logo=prometheus&logoColor=white"> <img src="https://img.shields.io/badge/Grafana-F46800?style=for-the-badge&logo=grafana&logoColor=white"> <img src="https://img.shields.io/badge/Amazon%20EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white">

</div>

## 3. 아키텍처 
<div align="center">
<img width="861" height="539" alt="image" src="https://github.com/user-attachments/assets/318e55cf-0750-4c86-94dd-11f9282246ba" />
</div>

## 4. ERD
<div align="center">
<img width="1341" height="671" alt="image" src="https://github.com/user-attachments/assets/d9ea2715-7d72-4fb3-9ad0-5af7c93e43ef" />
</div>


<br>
<h2>5. <a href="https://vagabond-snake-4bf.notion.site/API-265d3128995c80a593f5c2fa943512e3?source=copy_link">API 명세</a></h2>

## 6. 트러블 슈팅 & 성능 개선

### 1.쿠폰 발급 동시성 처리 (Redisson)
블로그 글 : <a href="https://velog.io/@kodongwon/Redisson-%EB%B6%84%EC%82%B0-%EB%9D%BD%EC%9D%84-%ED%99%9C%EC%9A%A9%ED%95%9C-%EC%BF%A0%ED%8F%B0-%EB%B0%9C%EA%B8%89-%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0">Redisson 분산 락을 활용한 쿠폰 발급 동시성 문제 해결</a>
#### 🔍 문제 원인
- 동시 요청으로 인한 쿠폰 중복 발급 및 수량 불일치 발생
#### 💡 문제 해결
- Redisson 기반 분산 락을 적용하여 쿠폰 발급 로직의 임계 구역을 보호하고, 중복 발급 및 수량 불일치를 방지
#### 📈 문제 해결 전후 비교
테스트 시나리오 : 30장의 수량을 가진 쿠폰에 대해 200명의 유저가 동시에 발급 요청
- **동시성 제어 전** : 동시 요청으로 인한 데이터 불일치
  <img width="1398" height="187" alt="image" src="https://github.com/user-attachments/assets/8725ec6c-9dbf-4fb1-9bb9-5dca0b8b96ea" />
- **동시성 제어 후** : 정확한 쿠폰 수량 관리 가능
  <img width="1395" height="141" alt="image" src="https://github.com/user-attachments/assets/ec8bb9bf-8848-472f-8679-c533b2fb1dc9" />
<br>

### 2. 모니터링을 통한 N+1 문제 추적•해결
블로그 글 : <a href="https://velog.io/@kodongwon/API%EB%B3%84-%EC%BF%BC%EB%A6%AC-%EB%AA%A8%EB%8B%88%ED%84%B0%EB%A7%81%EC%9D%84-%ED%86%B5%ED%95%9C-N1-%EB%AC%B8%EC%A0%9C-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0">API별 쿼리 모니터링을 통한 N+1 문제 해결하기</a>
#### 🔍 문제 원인
- N+1 문제 발생 지점 파악 어려움
- 코드만으로는 런타임 시점 쿼리 현황 추적 불가
#### 💡 문제 해결
- Interceptor + ThreadLocal로 요청 단위 쿼리 집계
- StatementInspector로 실행 쿼리 수집
- MeterRegistry로 Prometheus에 지표 저장
- 문제 지점을 추적 후 fetch join으로 N+1 해결
#### 📈 문제 해결 전후 비교
- **문제 해결 전** :  `GET /users/{userId}/coupons` → 40회 쿼리 발생
  <img width="1246" height="398" alt="image" src="https://github.com/user-attachments/assets/15180223-d94d-4a07-afe8-98d4cd127960" />
- **문제 해결 후** : `GET /users/{userId}/coupons` → 3회 쿼리 발생
  <img width="1243" height="400" alt="image" src="https://github.com/user-attachments/assets/7de95705-1d69-4e69-b03b-345f0d5acc27" />
#### 🌟 요약
- **쿼리 발생** : 40회 → 3회 (92% 감소)
- **API 단위 모니터링 구축** : 런타임 쿼리 현황 추적 가능
<br>

  
### 3. 생일 쿠폰 발급 성능 향상 (벌크 연산)
블로그 글 : <a href="https://velog.io/@kodongwon/%EC%83%9D%EC%9D%BC-%EC%BF%A0%ED%8F%B0-1%EB%A7%8C-%EA%B1%B4-%EB%B0%9C%EA%B8%89-%EC%8B%9C-%EC%84%B1%EB%8A%A5-%EC%A0%80%ED%95%98-%EB%B2%8C%ED%81%AC%EC%97%B0%EC%82%B0%EC%9C%BC%EB%A1%9C-%EC%B5%9C%EC%A0%81%ED%99%94%ED%95%98%EA%B8%B0">쿠폰 대량 발급 시 성능 저하, 벌크연산으로 최적화하기</a>

#### 🔍 문제 원인
- JPA IDENTITY 전략으로 인해 엔티티 단위로 INSERT 쿼리 즉시 실행 → 과도한 네트워크 왕복 발생
- 1만 건 데이터 처리 시 1만 개 개별 INSERT 실행
#### 💡 문제 해결
- JPA 대신 JdbcTemplate batchUpdate로 쿼리 배치 처리 → 네트워크 왕복 최소화
- MySQL 드라이버 옵션 rewriteBatchedStatements=true 적용 → 다중 VALUES INSERT로 변환 후 실행
#### 📈 문제 해결 전후 비교
테스트 시나리오 : 1만 건 쿠폰 대량 발급
- **문제 해결 전** : JPA `saveAllAndFlush`  
  <img width="512" height="126" alt="image" src="https://github.com/user-attachments/assets/aedd388f-2888-4e21-9a2c-e1a484e6eac6" />
- **1차 개선** : batchUpdate 적용  
  <img width="511" height="123" alt="image" src="https://github.com/user-attachments/assets/7897fef5-6292-4c58-a7c7-0ae5af4a78f2" />
- **2차 개선** : batchUpdate + 다중 VALUES 적용  
  <img width="507" height="121" alt="image" src="https://github.com/user-attachments/assets/97db1b71-a022-4918-bca0-5fc2c5baa633" />
#### 🌟 요약
- 실행 시간 1차 개선 : 10370ms → 6859ms (약 33.9% 감소)  
- 실행 시간 2차 개선 : 6859ms → 834ms (약 88% 감소)  
- 총 실행 시간 단축 : 10370ms → 834ms (약 92% 감소)  
<img width="435" height="554" alt="image" src="https://github.com/user-attachments/assets/87617c81-d4ee-4ef4-abaa-e892d88b02ee" />

<br>

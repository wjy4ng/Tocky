### 📌 0. 개요

##### 📄 앱 이름
토팁 - TOTP를 활용한 통합 비밀번호 관리 시스템
##### 📄 개발 기간
'2025.03.16 ~ ing
##### 📄 개발 인원 및 역할 분담
기획, UI 디자인, 프로그래밍 - 양원준
##### 📄 사용 플랫폼 (IOS, Android, Web 등)
- Android Studio
	- Android 15.0 ("VanillaIceCream")
	- Android SDK Platform 35
	- Sources for Android 35
	- Google APIs ARM 64 v8a System Image
---
### 📌 1. 앱 소개
##### 📄 개발 배경
2025년 4월 22일, SKT 서버가 해킹당해 **대한민국 통신사 역사상 최악의 유심 정보 유출 사건**이 발생했다. 유출된 정보는 **IMSI, ICCID, 유심 인증키** 등으로, 이는 **유심 복제**와 같은 심각한 보안 위협에 악용될 수 있는 민감한 개인 식별 정보이다. 특히 이번 사건의 핵심 문제는 서버에 저장된 유심 관련 데이터가 **암호화되어 있지 않았던 점**이다.
이러한 보안 취약점은, 사용자 단말기나 앱이 해킹당하지 않더라도 **서버가 해킹되면 곧바로 개인정보가 탈취될 수 있다는 사실**을 보여준다. 이는 단순한 기술적 실수가 아닌, 구조적 보안 인식 부족에서 비롯된 중대한 실책이다. 오늘날과 같이 서버가 상시 연결된 환경에서는 **서버 측 정보는 반드시 암호화된 형태로 보관되어야 하며, 평문 저장은 보안상 치명적**이다.
##### 📄 앱 개발 목적
이 앱은 사용자의 **개인 정보 보안 강화를 위한 2단계 인증(Two-Factor Authentication, 2FA)**을 촉진하기 위해 개발되었다.
특히 다음과 같은 목적을 지닌다:
- **사용자 스스로 비밀키를 직접 관리**하게 하여, 서버는 인증 정보를 **암호화된 상태로만** 보관하도록 한다.
- 2단계 인증 방식을 통해 **비밀번호 외의 두 번째 보안 요소**를 제공함으로써, 계정 탈취나 서버 유출과 같은 상황에서도 보안을 유지할 수 있도록 한다.
- 유심 인증키 등 고위험 정보와 별개로, **TOTP 기반 인증 코드**를 사용해 인증 시 동적으로 변하는 코드를 요구함으로써, 고정된 정보 탈취만으로는 계정 접근이 불가능하게 만든다.
##### 📄 주요 기능
- ✅ **비밀키 입력 및 생성**: 사용자가 직접 비밀키를 입력하거나 생성할 수 있으며, 이는 기기 내 안전하게 보관된다.
- 🔐 **TOTP(Time-based One-Time Password) 생성**: 입력된 비밀키를 기반으로 30초 간격으로 갱신되는 6자리 인증 코드를 생성.
- 📡 **서버와의 안전한 인증 통신**: 서버는 인증 요청 시 오직 TOTP 코드와 사용자 ID만 받아들이며, **비밀키는 클라이언트 단에서만 보관 및 활용**된다.
- 🚫 **서버 비밀정보 저장 금지**: 서버는 인증용 비밀키를 저장하지 않으며, 필요한 경우에도 **단방향 암호화 또는 대칭키 암호화를 적용**해 정보 유출 위험을 최소화한다.    
- 🛡️ **QR 코드 등록 및 백업**: Google Authenticator 등 외부 인증 앱과의 호환성을 위해 QR 코드 등록 및 비밀키 백업 기능 제공.
---
### 📌 2. 기획 및 설계
##### 📄 전체 기능 흐름
- 네이버, 깃허브 등에서 2단계 인증 활성화 시, 해당 서비스에서 QR이나 비밀키 제공
- 그 QR을 스캔하여 인증앱에 비밀키를 저장
- 비밀키는 안드로이드의 EncryptedSharedPreferences 기능을 이용해 안전하게 저장
- 이 비밀키를 바탕으로 앱에서 주기적으로 변하는 6자리 TOTP 생성 후 반환
- 로그인 할때마다, 앱에서 생성한 6자리 숫자 코드를 사이트에 입력해 확인 후 인증

##### 📄 화면 설계 (와이어프레임, UI/UX 디자인 개요)
- 시작 화면 ➡️ 로그인/회원가입
- 앱 홈 ➡️ 등록된 사이트 목록
- 사이트 추가 ➡️ QR 코드 스캔 or 수동 입력
- 사이트 상세 보기 ➡️ TOTP 코드 표시, 복사 버튼
- 1번 탭 ➡️ 로그인 테스트 사이트
- 3번 탭 ➡️ 백업, 보안 설정 (생체인증)

##### 📄 플로우차트 또는 앱 흐름도
---
### 📌 3. 개발 과정
##### 📄 개발 언어 및 프레임워크
- Java

##### 📄 사용된 라이브러리/기술 스택
- DBHelper
- ViewPager2
- TOTPUtil
- qrScanLauncher
```kts
dependencies {  
    implementation(libs.appcompat)  
    implementation(libs.material)  
    implementation(libs.activity)  
    implementation(libs.constraintlayout)  
    implementation(libs.firebase.inappmessaging)  
    testImplementation(libs.junit)  
    androidTestImplementation(libs.ext.junit)  
    androidTestImplementation(libs.espresso.core)  
    implementation(libs.zxing.android.embedded)  
    implementation(libs.core)  
    implementation("dev.samstevens.totp:totp:1.6.1")  
    implementation("androidx.security:security-crypto:1.1.0-alpha06")  
}
```
##### 📄 주요 기능 구현 방법 (MVP 구현)
- QR 스캔해서 TOTP 키 등록하기
- 등록된 계정의 6자리 코드를 시간에 맞춰 표시
- 로컬에 안전하게 저장하기 (EncryptedSharedPreference 사용)
- 생체인증으로 앱 잠금

##### 📄 일정 관리 및 이슈 해결 방법
---
### 📌 4. 테스트 및 결과
##### 📄 테스트 방법
##### 📄 버그 및 해결 방법
문제점 1. QR 코드 스캔 후 AVD 렉이 심해짐
해결방법
1. saveSecretKey(), addSiteRow() 함수를 Handler를 이용해 0.1초 늦게 실행하여 한꺼번에 실행하는 것을 방지 -> 해결X
1. TextView.setGravity()에 View.TEXT_ALIGNMENT_CENTER 사용 -> 예외가 발생할 수 있어 Gravity 상수를 사용해야함 -> Gravity.CENTER로 대체 -> 해결X
1. TOTP 생성 버튼과 삭제 버튼을 TextView -> Button 으로 대체 -> 해결x
1. 행 추가 시 애니메이션으로 인해 렉 걸릴 수 있음 -> siteTableLayout.setLayoutTransition(null); 삽입 -> 해결x
1. TOTP 관련 UI 갱신을 별도 스레드나 비동기 처리하는 방법 -> Executors + Handler 사용 -> 해결X
1. System.gc()로 스캔 종료 후 강제 GC 호출 -> 조금 빨라졌으나 이 문제가 아닌 듯함 -> UI 많아서 오류가 있는듯
1. addSiteRow()를 호출할 때마다 new해서 row를 만들지 말고 row_item.xml을 만들고 이를 inflate하는 식으로 교체 -> 해결X
1. 행을 동적으로 추가하는 addSiteRow() 말고 UI를 새롭게 렌더링만 하는 renderSiteList() 함수로 교체 -> 렉이 조금 풀림. 그래도 있음
1. row_item.xml을 LinearLayout -> CardView로 변경 

문제점 2. 앱 재실행 시 저장된 데이터를 다시 로드해서 UI에 복원하는 처리를 하지 않음.
해결방법
1. onCreateView에서 저장된 키들을 다시 불러와서 UI에 표시 -> loadSavedSites() 함수 생성 -> 해결

문제점 3. 로그인한 계정이 어떤것이든 등록되어있는 비밀키 목록이 똑같이 뜸

문제점 4. 아이디가 이메일 형식이 아닌데 회원가입과 로그인이 됨

##### 📄 성능 개선 내용
---
### 📌 5. 문제점 및 개선사항
##### 📄 개발 중 겪은 어려움
- 계정을 보관하는 데이터베이스로 처음엔 firebase를 택했으나 구현의 어려움 때문에 DBHelper, Viewpager2로 대체함
- TOTP 생성하는 라이브러리로 Google Authentication을 사용하려 했으나 Android에 의존성 성립이 안됨
	➡️  찾아보니 현재 사라진 기능이었음
	➡️ 대체제로 samstevens의 totp code를 사용함
##### 📄 해결하지 못한 문제 또는 추후 개선 방향
1. 네이버는 TOTP용 QR 코드를 제공하지 않음
	- 구글, 깃허브, microsoft 계정, AWS,  Facebook, Instagram, dropbox, X, Notion, discord, zoom 등은 제공함.
1. 여러 사이트의 TOTP를 저장할 수 있게 구현하기
1. 사이트 추가, 삭제 버튼
1. 설정탭에 뭐 넣을지 생각하기

---
### 📌 6. 결론 및 소감
##### 📄 프로젝트를 통해 배운 점
##### 📄 팀워크 또는 협업 소감
---
### 📌 7. 부록
##### 📄 코드 일부
##### 📄 스크린샷 또는 동작 화면
##### 📄 참고자료, URL, Github 링크 등

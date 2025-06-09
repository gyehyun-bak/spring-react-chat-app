## 💁‍♂️ 소개
누구나 간단하게 Spring과 React를 통해 기본적인 채팅 서비스를 구축을 시작할 수 있도록 만든 템플릿 레포지토리 프로젝트입니다.

Spring 서버의 구현에 초점을 맞춘 프로젝트로, 클라이언트 측은 최소한으로 구현됩니다.

해당 레포지토리는 최대한 누구나 다양한 설계에 적용하고 재사용하기 편하도록 만드는 것을 목적으로 하고 있습니다.

기능 요청 및 버그는 `Issues`에, 상세 구현 및 기타 질문은 `Discussions`에 올려주세요!

## 🚩 개요
SpringBoot 백엔드 서버와 React(TypeScript) 클라이언트로 SockJS와 STOMP를 이용하여 간단한 채팅 웹 서비스를 구현했습니다.

클라이언트 측에서 스프링 서버 엔드포인트("/app/chat")로 전송한 메시지를 단일 토픽("/topic/chat")으로 브로드캐스팅하는 구조로 구현되어 있습니다.

구체적인 구조와 구현 방법은 아래 그림과 각 블로그 포스트를 참조해주세요.

## 🛠 기능
- 접속 시 채팅 메시지를 실시간으로 주고 받을 수 있습니다.
- 자신이 보낸 메시지는 오른쪽에 파란색으로 표시됩니다.
- 접속 시 닉네임을 입력받으며, 각 메시지 상단에 닉네임이 표시됩니다.
- 새로운 사용자가 참여하거나 나가면 이를 별도 UI 메시지로 표시합니다.

## 📝 개발 노트
개발 과정을 블로그로 기록 중입니다. 각 기능의 상세 구현에 대해 정보를 얻으실 수 있습니다.

|  순서  | 블로그 포스트 | 추가 설명
|--------|--------------|-----------|
| 1 | [스프링 + 리액트 + 웹소켓 + STOMP로 간단한 채팅 구현하기](https://velog.io/@gyehyunbak/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%A6%AC%EC%95%A1%ED%8A%B8-%EC%9B%B9%EC%86%8C%EC%BC%93-STOMP%EB%A1%9C-%EA%B0%84%EB%8B%A8%ED%95%9C-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0) | WebSocket과 SockJS 개념, 기본적인 프로젝트 세팅
| 2 | [Session ID로 내가 보낸 메시지 오른쪽에 표시하기](https://velog.io/@gyehyunbak/Spring-React%EB%A1%9C-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84-2https://velog.io/@gyehyunbak/Spring-React%EB%A1%9C-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84-2) | 내가 보낸 메시지 UI 구별 기능

## 🏗 개발 환경 및 의존성
### 프론트엔드
- React 18.3.1
- TypeScript 5.6.2
- Vite 6.0.1
- TailwindCSS 3.4.17
- sockjs-client 1.6.1
- stompjs 7.0.0
### 백엔드
- Java 17
- Spring Boot 3.4.0
- Spring Web
- WebSocket
- Lombok
- Spring Boot DevTools

## ⚙ 실행 방법
### 프론트엔드
`client` 디렉토리에서 아래 명령어를 통해 vite 프로젝트를 IDE에서 실행합니다.

```bash
npm i
npm run dev
```

### 백엔드
IntelliJ와 같은 적당한 스프링 IDE를 통해 `server` 프로젝트를 엽니다.

기본적으로 `http://localhost:8080/`을 베이스 서버 URL로 연결하도록 클라이언트측에 구현되어있습니다. 

## Class 다이어그램
![클래스 다이어그램](https://github.com/user-attachments/assets/648c1dfa-d7f4-4aa3-9856-36852d1a30f4)

## 프로젝트 흐름도
![흐름도](https://github.com/user-attachments/assets/24b44838-1c4d-4b5e-99f3-4205918ab213)

## 실행 화면
![화면 캡처 2025-01-16 125958](https://github.com/user-attachments/assets/0ef2c031-4410-4e33-91c3-6835663b5ffb) | ![spring chat 업데이트 2025-01-16](https://github.com/user-attachments/assets/dd4ed3f4-1f7a-4087-af7e-45650f8dcbb3)
---|---|

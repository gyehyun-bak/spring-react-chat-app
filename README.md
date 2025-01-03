# Spring Starter Pack - Chat App

## 소개
누구나 간단하게 Spring Boot와 React를 통해 기본적인 채팅 서비스를 구축을 시작할 수 있도록 만든 템플릿 레포지토리 프로젝트입니다.

해당 레포지토리는 최대한 누구나 다양한 설계에 적용하고 재사용하기 편하도록 만드는 것을 목적으로 하고 있습니다.

## 프로젝트 구조
SpringBoot 백엔드 서버와 React(TypeScript) 클라이언트로 SockJS와 STOMP를 이용하여 간단한 채팅 웹 서비스를 구현했습니다.

클라이언트 측에서 스프링 서버 엔드포인트("/app/chat")로 전송한 메시지를 단일 토픽("/topic/chat")으로 브로드캐스팅하는 구조로 구현되어 있습니다.

웹소켓 및 STOMP, SockJS 등 주요 개념과 구현에 대한 자세한 설명은 저의 [블로그 포스트](https://velog.io/@gyehyunbak/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%A6%AC%EC%95%A1%ED%8A%B8-%EC%9B%B9%EC%86%8C%EC%BC%93-STOMP%EB%A1%9C-%EA%B0%84%EB%8B%A8%ED%95%9C-%EC%B1%84%ED%8C%85-%EA%B5%AC%ED%98%84%ED%95%98%EA%B8%B0)를 참고해주시기 바랍니다.

(구체적인 구조와 구현 방법 전체에 대한 설명을 추가할 예정입니다.)

## 개발 환경 및 의존성
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

## 실행 방법
### 프론트엔드
`frontend` 디렉토리에서 아래 명령어를 통해 vite 프로젝트를 IDE에서 실행합니다.
```bash
npm i
npm run dev
```

### 백엔드
IntelliJ와 같은 적당한 스프링 IDE를 통해 `SpringWebsocketApplication.java`을 실행합니다. 기본적으로 `http://localhost:8080/`을 베이스 서버 URL로 연결하도록 클라이언트측에 구현되어있습니다. 

### 연동
브라우저 콘솔에 기록되는 로그를 통해 STOMP의 연결과 메시지를 확인할 수 있습니다.

## 프로젝트 흐름도
![image](https://github.com/user-attachments/assets/d8f2a18f-209f-4bdc-a40a-5cfc25ff8c6f)

## 실행 화면
![image](https://github.com/user-attachments/assets/9d158d30-aa79-42e1-9c2f-ea05c13580b8)

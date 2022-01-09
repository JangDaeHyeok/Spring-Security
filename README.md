# Spring-Security
1. session
 - 로그인 시 로그인 정보 session에 저장 -> db에 저장되어있는 권한에 대한 접근 허용 페이지 적용
 - 권한이 없는 경우 403 페이지로 이동
 - 인증 정보가 없는 경우 401 에러 반환
 - ajax, 페이지 요청이 들어올 경우 static data와 권한 비교 체크
 - ajax, 페이지 요청 별도 권한 체크

2. JWT
 - 사용자 정보 인증 시 JWT accessToken, refreshToken 발급 -> accessToken 쿠키 저장 -> refreshToken DB 저장
 - 요청 시 쿠키에 저장된 JWT accessToken 체크
 - accessToken이 인증 완료된 경우 비즈니스 로직 진행
 - accessToken이 인증 완료되고 refreshToken이 만료된 경우 refreshToken 재발급(예정)
 - accessToken이 만료된 경우 DB에 저장된 refreshToken을 체크 후 accessToken 재발급
 - refreshToken이 만료된 경우 result : fail 리턴

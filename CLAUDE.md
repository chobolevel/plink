# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# 전체 빌드
./gradlew build

# 전체 테스트 실행
./gradlew test

# 특정 모듈 테스트
./gradlew :api:test

# 단일 테스트 클래스 실행
./gradlew :api:test --tests "com.plink.user.UserServiceTest"

# 단일 테스트 메서드 실행
./gradlew :api:test --tests "com.plink.user.UserServiceTest.회원 생성"

# 코드 스타일 검사 (ktlint)
./gradlew ktlintCheck

# 코드 스타일 자동 수정
./gradlew ktlintFormat

# api 모듈 실행
./gradlew :api:bootRun
```

## 모듈 구조

3개의 Gradle 서브모듈로 구성된 멀티모듈 프로젝트:

- **`core`**: JPA 엔티티, QueryDSL 리포지토리, JWT, 공통 예외/DTO 등 인프라 레이어. `bootJar` 비활성화, `jar`만 생성.
- **`api`**: Spring Boot 웹 애플리케이션. `core`에 의존. 컨트롤러 → 서비스 → `core` 리포지토리 순서로 호출.
- **`batch`**: 배치 모듈 (현재 미개발).

`api`는 `core`를 `implementation(project(":core"))`로 참조하며, `core`는 JPA/QueryDSL 관련 의존성을 `api` configuration으로 노출한다.

## 아키텍처 패턴

`api` 모듈의 각 도메인(user, post, region)은 아래 레이어를 따른다:

```
Controller → Service → Validator / Assembler / Converter / Updater → Repository(core)
```

- **Converter**: Request DTO → Entity 또는 Entity → Response DTO 변환
- **Assembler**: 여러 엔티티를 조합해 관계를 설정 (예: `post.assignUser(user)`)
- **Validator**: 비즈니스 규칙 검증 (소유권, 파라미터 유효성 등)
- **Updater**: `UpdateMask` 열거형을 순회하며 엔티티 필드를 선택적으로 갱신

`core` 모듈의 리포지토리는 인터페이스(`PostRepository`) + 어댑터(`PostRepositoryAdapter`) 패턴으로 구성되며, 어댑터 내부에서 `JpaRepository`와 `QueryDslRepository`를 조합한다.

## 인증/인가

- **JWT** 기반 Stateless 인증. `OnceAuthorizeFilter`가 `Authorization: Bearer <token>` 헤더를 파싱해 `SecurityContext`에 사용자를 설정한다.
- **Refresh Token**은 Redis에 저장 (`CacheRepository`). Access Token은 10분, Refresh Token은 7일.
- 엔드포인트 접근 제어는 Spring Security `@PreAuthorize`를 사용하는 커스텀 어노테이션으로 처리:
  - `@Authenticated`: USER 또는 ADMIN
  - `@UserOnly`: USER만
  - `@AdminOnly`: ADMIN만
- Spring Security의 `authorizeHttpRequests`는 전체 `permitAll`로 열어두고, 메서드 레벨 보안(`@EnableMethodSecurity`)으로 제어한다.

## 핵심 설계 결정

- **TSID**: PK는 UUID 대신 TSID(`@TsidGenerator`)를 사용한다. 길이 13의 문자열 ID.
- **Soft Delete**: 엔티티 삭제 시 `isDeleted = true`로 표시하며, 조회 시 `isDeletedFalse` 조건을 명시해야 한다.
- **Hibernate Envers**: `@Audited`가 붙은 엔티티는 변경 이력이 `_histories` 접미사 테이블에 자동 저장된다.
- **Jasypt**: `application.yaml`의 민감 설정값은 `ENC(...)` 형식으로 암호화되어 있다. 로컬 실행 시 복호화 키가 필요하다.
- **응답 형식**: 모든 API 응답은 `ApiResponse<T>` 또는 `ApiPagingResponse`로 래핑된다. Jackson은 `SNAKE_CASE` 네이밍 전략을 사용한다.
- **예외 처리**: `GlobalExceptionHandler`가 도메인별 예외(`DataNotFoundException`, `ForbiddenException` 등)를 HTTP 상태 코드에 매핑한다. 새 예외 추가 시 `ErrorCode` 열거형과 핸들러 모두 수정이 필요하다.

## 테스트 구조

- 서비스 테스트는 Mockito(`@ExtendWith(MockitoExtension::class)`)를 사용한 순수 단위 테스트.
- 각 도메인마다 `Dummy*.kt` 팩토리 객체가 있어 테스트용 엔티티/DTO를 생성한다.
- 엔티티 테스트(`*EntityTest`)는 엔티티의 도메인 메서드(예: `delete()`, `assignUser()`)를 직접 검증한다.

## 실행 환경 요구사항

- MySQL (DDL auto: `validate` — DB 스키마가 사전에 존재해야 함)
- Redis (localhost:6379)
- Jasypt 복호화 키 (`jasypt.encryptor.password`)
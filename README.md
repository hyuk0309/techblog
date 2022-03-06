# techblog

<br/>

## Intro

Spring MVC, JPA 를 이용한 토이 프로젝트입니다.

[프로젝트 관련 문서](https://muddy-aries-717.notion.site/cee703a784d9453cb275a4bc0475bd71)

<br/>

## 다음 사항을 지키면서 프로젝트를 진행

- 자바 코드 컨벤션 지키기
    <br/>
    [자바 코드 컨벤션](https://naver.github.io/hackday-conventions-java)
  

- 커밋은 다음 규칙을 지키자
    - 7가지 규칙
        - 제목과 본문을 빈 행으로 구분한다.
        - 제목을 50글자 내로 제한
        - 제목 첫글자는 대문자로 작성
        - 제목 끝에 마침표 넣지 않기
        - 제목은 명령문으로 사용하며 과거형을 사용하지 않는다.
        - 본문의 각 행은 72글자 내로 제한
        - 어떻게 보다는 무엇과 왜를 설명
        
        <br/>
          
    - Commit message 구조
        ~~~
        type(타입) : title(제목)
      
        body(본문, 생략 가능)
        
        Resolves : #issue, ...(해결한 이슈, 생략 가능)
    
        See also : #issue, ...(참고 이슈, 생략 가능)
        ~~~
      
        <br/>
    
        ~~~
        types = {
            feat : 새로운 기능에 대한 커밋
            fix : 버그 수정에 대한 커밋
            build : 빌드 관련 파일 수정에 대한 커밋
            chore : 그 외 자잘한 수정에 대한 커밋
            ci : CI관련 설정 수정에 대한 커밋
            docs : 문서 수정에 대한 커밋
            style : 코드 스타일 혹은 포맷 등에 관한 커밋
            refactor : 코드 리팩토링에 대한 커밋
            test : 테스트 코드 수정에 대한 커밋
        }
        ~~~
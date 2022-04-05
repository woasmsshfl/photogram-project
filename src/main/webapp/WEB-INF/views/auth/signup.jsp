<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Photogram</title>
    <link rel="stylesheet" href="/css/style.css">
    <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css"
        integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
</head>

<body>
    <div class="container">
        <main class="loginMain">
           <!--회원가입섹션-->
            <section class="login">
                <article class="login__form__container">
                  
                   <!--회원가입 폼-->
                    <div class="login__form">
                        <!--로고-->
                        <h1><img src="/images/logo.jpg" alt=""></h1>
                         <!--로고end-->
                         
                         <!--회원가입 인풋-->
                        <!-- DB에 데이터를 INSERT하기 위해 POST 메소드를 사용한다.
                        회원가입 정보를 서버에 전송할때 서버 입구에서 시큐리티 CSRF 토큰 검사를 실시한다. 
                        서버가 aignup.jsp를 응담해줄때 CSRF 토큰을 담아서 클라이언트에게 전송한다.
                        이후 클라이언트가 로그인 요청을 할때, 서버가 같이 보내주었던 CSRF토큰이 있는지 확인한다. 
                        즉, 서버가 정상적인 경로로 회원가입하고 로그인을 했는지 확인하는 기능이다. -->
                        <form class="login__input" action="/auth/signup" method="post"> 
                            <input type="text" name="username" placeholder="유저네임" required="required" />
                            <input type="password" name="password" placeholder="패스워드" required="required" />
                            <input type="email" name="email" placeholder="이메일" required="required" />
                            <input type="text" name="name" placeholder="이름" required="required" />
                            <button>가입</button>
                        </form>
                        <!--회원가입 인풋end-->
                    </div>
                    <!--회원가입 폼end-->
                    
                    <!--계정이 있으신가요?-->
                    <div class="login__register">
                        <span>계정이 있으신가요?</span>
                        <a href="/auth/signin">로그인</a>
                    </div>
                    <!--계정이 있으신가요?end-->
                    
                </article>
            </section>
        </main>
    </div>
</body>

</html>
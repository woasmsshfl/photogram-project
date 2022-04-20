<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Photogram</title>
        <link rel="stylesheet" href="/css/style.css">
        <link rel="stylesheet" href="https://pro.fontawesome.com/releases/v5.10.0/css/all.css" integrity="sha384-AYmEC3Yw5cVb3ZcuHtOA93w35dYTsvhLPVnYs9eStHfGJvOvKxVfELGroGkvsg+p" crossorigin="anonymous" />
    </head>

    <body>
        <div class="container">
            <main class="loginMain">
                <!--로그인섹션-->
                <section class="login">
                    <!--로그인박스-->
                    <article class="login__form__container">
                        <!--로그인 폼-->
                        <div class="login__form">
                            <h1><img src="/images/logo.jpg" alt=""></h1>

                            <!--로그인 인풋-->
                            <!--로그인 정보는 보안이 지켜져야 하기 때문에 header가 아닌 body에 담아야한다-->
                            <!-- get요청을 하면 header에 담겨서 주소창에 정보가 담겨서 보안이 허술하다.-->
                            <!-- 따라서 method를 post로 설정해야 한다.-->
                            <form class="login__input" action="/auth/signin" method="post">
                                <input type="text" name="username" placeholder="유저네임" required="required" />
                                <input type="password" name="password" placeholder="비밀번호" required="required" />
                                <button>로그인</button>
                            </form>
                            <!--로그인 인풋end-->

                            <!-- 또는 -->
                            <div class="login__horizon">
                                <div class="br"></div>
                                <div class="or">또는</div>
                                <div class="br"></div>
                            </div>
                            <!-- 또는end -->

                            <!-- Oauth 소셜로그인 -->
                            <div class="login__facebook">
                                <button onclick="javascript:location.href='/oauth2/authorization/facebook'">
                                    <i class="fab fa-facebook-square"></i>
                                    <span>Facebook계정으로 로그인</span>
                                </button>
                            </div>

                            <div class="login__google">
                                <button onclick="javascript:location.href='/oauth2/authorization/google'">
                                    <img src="/images/googleicon.png" class="OAuth2_icon_size">
                                    <span>Google계정으로 로그인</span>
                                </button>
                            </div>

                            <!-- <div class="login__kakao">
                                <button onclick="javascript:location.href='/oauth2/authorization/kakao'">
                                    <img src="/images/kakaoicon.png" class="OAuth2_icon_size">
                                    <span>Kakao계정으로 로그인</span>
                                </button>
                            </div> -->

                            <!-- <a href="https://kauth.kakao.com/oauth/authorize?client_id=a432c90ec0018e07716a7fa15a190ae3&redirect_uri=http://localhost:8080/auth/kakao/callback&response_type=code">
                                <img src="/image/kakao_login_button.png">
                            </a> -->

                            <!-- Oauth 소셜로그인end -->
                        </div>

                        <!--계정이 없으신가요?-->
                        <div class="login__register">
                            <span>계정이 없으신가요?</span>
                            <a href="/auth/signup">가입하기</a>
                        </div>
                        <!--계정이 없으신가요?end-->
                    </article>
                </section>
            </main>

        </div>
    </body>

    </html>
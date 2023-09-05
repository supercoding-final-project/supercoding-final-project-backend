<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>kakao login test</title>
</head>
<body>
<a href="javascript:kakaologin();">
    <img
            src="https://developers.kakao.com/tool/resource/static/img/button/login/full/ko/kakao_login_medium_narrow.png"
            alt=""
    />
</a>
<script
        src="https://t1.kakaocdn.net/kakao_js_sdk/2.3.0/kakao.min.js"
        integrity="sha384-70k0rrouSYPWJt7q9rSTKpiTfX6USlMYjZUtr1Du+9o4cGvhPAWxngdtVZDdErlh"
        crossorigin="anonymous"
></script>
<script>
    Kakao.init("d4c1508770b1a0cd80b8c8fd3b1b5112");

    function kakaologin() {
        fetch("http://localhost:8080/api/v1/user/login")
            .then((res) => res.json)
            .then((data) => console.log(data.data));
        console.log("카카오 로그인 요청 보냄");
        // Kakao.Auth.authorize();
        // Kakao.Auth.login({
        //   scope: "profile_nickname, account_email, talk_message",
        //   success: function (authObj) {
        //     console.log(authObj);
        //     window.Kakao.API.request({
        //       url: "/v2/user/me",
        //       success: (res) => {
        //         const nickname = res.profile_nickname;
        //         console.log(nickname);
        //       },
        //     });
        //   },
        // });
    }
</script>
</body>
</html>

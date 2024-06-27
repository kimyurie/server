$(document).ready(function() {
    
    function login() {
        var email = $('input[name="userEmail"]').val();
        var password = $('input[name="userPassword"]').val();
        $.ajax({
            url: 'loginProcess',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ userEmail: email, userPassword: password }),
            success: function(data) {
                localStorage.setItem('jwtToken', data.token);
                setCookie('jwtToken', data.token, 1); 
                window.location.href = baseUrl + '/main';
            },
            error: function(xhr) {
                try {
                    var response = JSON.parse(xhr.responseText);
                    var message = response.message;
                    
                    if (message === "not_user") {
                        alert("가입되지 않은 유저입니다. 회원가입을 해주세요.");
                    } else if (message === "not_Match") {
                        alert("비밀번호가 틀렸습니다. 다시 시도해주세요.");
                    } else {
                        console.log("error 발생: " + message);
                    }
                } catch (e) {
                    alert('서버 응답을 처리하는 중 오류가 발생했습니다.');
                }
            }
        });
    }

    $('#loginForm').submit(function(event) {
        event.preventDefault(); // 폼의 기본 제출 동작 막기
        login(); // 로그인 함수 호출
    });

    // 쿠키 설정 함수
    function setCookie(name, value, days) {
        var expires = "";
        if (days) {
            var date = new Date();
            date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
            expires = "; expires=" + date.toUTCString();
        }
        document.cookie = name + "=" + (value || "") + expires + "; path=/";
    }

    const borderColor = '#499268'; // 지정된 색상 정의

    function validateEmail(email) {
        const re = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
        return re.test(String(email).toLowerCase());
    }

    function checkEmail($element) {
        const errorMessage = $('#emailError');
        if (validateEmail($element.val())) {
            $element.addClass('valid-email').css('borderColor', borderColor);
            errorMessage.hide();
        } else {
            $element.removeClass('valid-email').css('borderColor', '#ddd');
            errorMessage.show();
        }
    }

    function checkInput($element) {
        const errorMessage = $('#passwordError');
        if ($element.val().trim() !== '') {
            $element.css('borderColor', borderColor);
            errorMessage.hide();
        } else {
            $element.css('borderColor', '#ddd');
            errorMessage.show();
        }
    }

    $('input[name="userEmail"]').on('input', function() {
        checkEmail($(this));
    });

    $('input[name="userPassword"]').on('input', function() {
        checkInput($(this));
    });

    // 객체의 key와 value를 출력하는 함수
    function printObjectKeysAndValues(obj) {
        for (var key in obj) {
            if (obj.hasOwnProperty(key)) {
                console.log("Key: " + key + ", Value: " + obj[key]);
            }
        }
    }
});

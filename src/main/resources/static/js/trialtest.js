$(document).ready(function () {
    var questionsArray = new Array();
    const csrfToken = $('meta[name="_csrf"]').attr('content');
    const csrfHeader = $('meta[name="_csrf_header"]').attr('content');

    // ...

    $(document).on("click", "#ort-test-link", function (event) {
        event.preventDefault();
        var moduleId = $(this).data("ort-test-link"); // Получение moduleId из data-атрибута
        loadQuestions(moduleId);
    });

    var courseId = $("#courseId").val();

    function loadQuestions(moduleId) {
        $.ajax({
            url: "/trial-test/ort",
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (questions) {
                questionsArray = questions;
                sessionStorage.setItem("questionsArray", JSON.stringify(questions));
                window.location.href = "/examination";
                console.log(questions);
                console.log(questionsArray[0]);
            },
            error: function (error) {
                console.log("Произошла ошибка при загрузке пробного теста: ", error);
            }
        });
    }

    function onPromoCodeCheckSuccess(response) {
        sessionStorage.setItem("TrialUser", JSON.stringify(response));
        var moduleId = 123;
        loadQuestions(moduleId);
    }

    // Отправка запроса на проверку промокода
    $('#promo-form').submit(function (event) {
        event.preventDefault();
        const promoCode = $('#promo-input').val();

        // Создайте объект JSON с полем "token"
        const promoCodeObject = {token: promoCode};

        $.ajax({
            url: "/trial-test/check-promo",
            type: "POST",
            dataType: "json",
            data: JSON.stringify(promoCodeObject),
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                onPromoCodeCheckSuccess(response);
            },
            error: function (error) {
                var promoValidationMessage = $('#promo-validation-message');

                promoValidationMessage.css('display', 'block');
                console.log("Произошла ошибка при проверке промокода: ", error);
            }
        });
    });
});

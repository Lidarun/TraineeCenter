$(document).ready(function () {
    var questionsArray = new Array();
    const csrfToken = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const csrfHeader = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

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
});

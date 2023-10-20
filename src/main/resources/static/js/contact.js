function toggleUserMenu() {
    const userMenu = document.getElementById('user-menu');
    userMenu.classList.toggle('hidden');

    if (!userMenu.classList.contains('hidden')) {
        // Если меню видимо, добавляем слушатели для клика и клавиши Escape
        document.addEventListener('click', closeUserMenuOnClick);
        document.addEventListener('keydown', closeUserMenuOnEscape);
    } else {
        // Если меню скрыто, удаляем слушатели
        document.removeEventListener('click', closeUserMenuOnClick);
        document.removeEventListener('keydown', closeUserMenuOnEscape);
    }
}

function closeUserMenuOnClick(event) {
    const userMenu = document.getElementById('user-menu');
    const userMenuBtn = document.getElementById('user-menu-btn');

    if (!userMenu.contains(event.target) && !userMenuBtn.contains(event.target)) {
        userMenu.classList.add('hidden');
        document.removeEventListener('click', closeUserMenuOnClick);
        document.removeEventListener('keydown', closeUserMenuOnEscape);
    }
}

function closeUserMenuOnEscape(event) {
    if (event.key === 'Escape') {
        const userMenu = document.getElementById('user-menu');
        userMenu.classList.add('hidden');
        document.removeEventListener('click', closeUserMenuOnClick);
        document.removeEventListener('keydown', closeUserMenuOnEscape);
    }
}

document.addEventListener('keydown', (event) => {
    if (event.key === 'Escape') {
        userMenu.classList.add('hidden');
    }
});

$(document).ready(function() {
    $('#applicationForm').submit(function(event) {
        event.preventDefault();

        var csrfToken = $("meta[name='_csrf']").attr("content");
        var csrfHeader = $("meta[name='_csrf_header']").attr("content");

        var name = $('#name').val();
        var phone = $('#phoneNum').val();
        var course = $('#course').val();

        // Проверка, что поля не пустые
        if (!name || !phone || !course) {
            alert('Заполните все поля');
            return;
        }

        var formData = {
            name: $('#name').val(),
            phone: $('#phoneNum').val(),
            course: $('#course').val()
        };

        console.log(formData);

        $.ajax({
            url: '/application',
            method: 'POST',
            data: JSON.stringify(formData),
            contentType: 'application/json',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(data) {
                console.log('Серверный ответ:', data);

                $('#name').val('');     // Очистить поле name
                $('#phoneNum').val(''); // Очистить поле phoneNum
                $('#course').val('');   // Сбросить выбор в поле course

                $('#message').text('Ваша заявка принята, мы скоро с вами свяжемся');
            },
            error: function(xhr, status, error) {
                console.error('Произошла ошибка при отправке заявки:', error);
            }
        });
    });
});

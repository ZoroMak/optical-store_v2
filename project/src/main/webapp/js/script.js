document.addEventListener('DOMContentLoaded', function() {
    const burger = document.querySelector('.header_burger');
    const menu = document.querySelector('.header_menu');
    const body = document.querySelector('body');
  
    burger.addEventListener('click', function(event) {
      burger.classList.toggle('active');
      menu.classList.toggle('active');
      body.classList.toggle('lock');
    });
  });

const supportLink = document.getElementById('supportLink');

document.getElementById('supportLink').addEventListener('click', function(event) {
    event.preventDefault(); // Отменяет переход по ссылке
    sendData();
});

async function authorization() {
    var form = document.getElementById("loginForm");

    // Получаем значения полей ввода
    const username = form.username.value
    const password = form.password.value

    // Отправляем данные на сервер
    try {
        const response = await fetch('/login', { // Убедитесь, что путь соответствует вашему серверу
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username, password})
        });

        if (response.ok) {
            const data = await response.json();
            console.log(data)
            localStorage.setItem('username', data['user']['username']);
            localStorage.setItem('authority', data['user']['authorities'][0]['authority']);
            localStorage.setItem('supportEmail', data['user']['supportId']);
            localStorage.setItem('jwtToken', data['jwt']);
            //window.location.href = '/'; // Перенаправление на защищенную страницу
        } else {
            const errorData = await response.json();
            alert(errorData.message || 'Ошибка при входе'); // Показываем сообщение об ошибке
        }
    } catch (error) {
        console.error('Ошибка:', error);
        alert('Произошла ошибка при подключении к серверу');
    }
}

async function sendData() {
    const username = localStorage.getItem('username');
    const tocken = localStorage.getItem('jwtToken');
    const authority = localStorage.getItem('authority');
    const supportEmail = localStorage.getItem('supportEmail');

    const dataToSend = {
        username: username,
        authority: authority,
        tokenJWT: tocken,
        supportEmail: supportEmail
    };

    try {
        const response = await fetch('http://localhost:8088/data', { // Убедитесь, что путь соответствует вашему серверу
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(dataToSend)
        });

        if (response.ok) {
            window.location.href = '/websocket';
        } else {
            console.error('Ошибка:', response.status, response.statusText);
        }
    } catch (error) {
        console.error('Произошла ошибка при выполнении запроса:', error);
    }
}
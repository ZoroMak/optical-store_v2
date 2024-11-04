function toCurrency(num) {
    return new Intl.NumberFormat("ru-RU", {
        style: "currency",
        currency: "RUB",
        minimumFractionDigits: 0,
    }).format(num);
}

let basket = {};//Корзина
let basketCount = {};//Количесвто товаров в корзине

$('document').ready(function(){
    checkCart();
    showGoods();
});

let total = 0;
let totalPriceField = document.getElementById('totalPrice');
let shoppingCart = document.getElementById('shoppingCart');

//Загрузка товаров на страницу
function showGoods(){
    let out = '';
    total = 0
    let str
    for (let key in basket){
        out += '<li>';
        //out += '<a href="'+basket[key].link+'">';
        out += '<img alt="" class="Photo" src="'+basket[key]['image']+'">';
        //out += '</a>';
        out += '<div class="internal">';
        out += '<p>'+basket[key]['name']+'</p>';
        out += '<div class="remove">';
        out += '<p id='+key+' class="Cost"><b>'+toCurrency(basketCount[key]*basket[key]['cost'])+'</b></p>'
        out += '<div class="add">';
        out += '<button class="minus" dataArt='+key+'>–</button>'
        str = key + "quantity"
        out += '<p id='+str+' class="quantity">'+basketCount[key]+'</p>'
        out += '<button class="plus" dataArt='+key+'>+</button>'
        out += '</div>';
        out +=  '<button class="delete" dataArt='+key+'><img alt="" src="/img/rubbishbin.png"></button>';
        out += '</div>';
        out += '</div>';
        out += '</li>';
        total += basketCount[key]*basket[key]['cost'];
    }

    totalPriceField.value = total
    shoppingCart.value = JSON.stringify(basketCount);

    /*localStorage.setItem('basket', JSON.stringify(basket));*/
    $('.price').html(toCurrency(total))
    $('.List').html(out);
    $('.plus').on('click', plusGoods);
    $('.minus').on('click', minusGoods);
    $('.delete').on('click', function() {
        deleteGoods($(this).attr('dataArt'));
    });
}

function plusGoods(){
    let articular = $(this).attr('dataArt');

    if (basketCount[articular] < basket[articular]['productCol']) {
        basketCount[articular]++;
        localStorage.setItem('basketCount', JSON.stringify(basketCount));
        total += basket[articular]['cost'];
        showPrice(articular)
    }
}

function minusGoods(){
    let articular = $(this).attr('dataArt');
    basketCount[articular]--;
    localStorage.setItem('basketCount', JSON.stringify(basketCount));
    total -= basket[articular]['cost'];
    showPrice(articular);

    if (basketCount[articular] == 0){
        deleteGoods(articular);
    }
}

function deleteGoods(articular){ // изменяем название переменной на articular
    delete basket[articular];
    delete basketCount[articular];
    localStorage.setItem('basket', JSON.stringify(basket));
    localStorage.setItem('basketCount', JSON.stringify(basketCount));
    showGoods();
}

function showPrice(articular){
    let cost = document.getElementById(articular);
    let id = articular + "quantity"
    let count = document.getElementById(id);

    cost.textContent = toCurrency(basketCount[articular]*basket[articular]['cost'])
    count.textContent = basketCount[articular];

    $('.price').html(toCurrency(total))

    totalPriceField.value = total;
    shoppingCart.value = JSON.stringify(basketCount);
}

function checkCart(){
    if (localStorage.getItem('basket') != null) {
        basket = JSON.parse(localStorage.getItem('basket'))
        basketCount = JSON.parse(localStorage.getItem('basketCount'))
    }
}

let form = document.getElementById("basketForm");

form.addEventListener("submit", function(event) {
    event.preventDefault();
    console.log("Создание заказа")
    const token = localStorage.getItem('jwtToken');

    let formData = new FormData(form);
    let formDataJson = {};
    for (let [key, value] of formData.entries()) {
        formDataJson[key] = value;
    }

    let config = {
        method: 'post',
        url: `/createOrder`,
        headers: {
            'Authorization': 'Bearer ' + token,
        },
        data: formDataJson

    }
    console.log(formDataJson)
    axios.request(config)
        .then(r => {
            console.log("dcec");
            console.log(r.status);
            if (r.status === 200) {
                console.log("Заказ создан")
                localStorage.removeItem('basketCount');
                localStorage.removeItem('basket');
                location.reload();
            } else {
                console.log("Произошла ошибка при отправке данных на сервер при создании заказа");
                window.location.href = '/loginPage'
            }
        })
        .catch(error => {
        console.error("Ошибка при отправке данных на сервер: ", error);
        window.location.href = '/loginPage';
        });
});

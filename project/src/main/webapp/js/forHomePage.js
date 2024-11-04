let basket = {}; // Инициализация пустого объекта
let basketCount = {}; // Инициализация пустого объекта

let cart = {};
let length = 0;
$(document).ready(function(){
    checkGoods();
    getTotalLength();
    loadGoods(0);
});

function getTotalLength() {
    let url = '/getLength';
    let search_value = document.getElementById("searchInput").value

    $.ajax({
        url: url,
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        data: {search_value: search_value},
        success: function (response) {
            console.log(response)
            length = response
        }
    });
}
let page = 0;

function loadGoods(page_number){
    let countPerPage = $("#count_1").val();
    countPerPage = parseInt(countPerPage);
    if (countPerPage === null) {
        countPerPage = 3;
    }
    page = page_number
    sortCart()
}
let dropdownList = document.getElementById("typeSorting");

function sortCart() {
    let value = dropdownList.value;
    let countPerPage = $("#count_1").val();

    if (value === "increase"){
        loadSortedData(page, countPerPage, true)
    }
    else if(value === "decrease"){
        loadSortedData(page, countPerPage, false)
    }
    else{
        loadData(page, countPerPage)
    }

    generateProductList(page, countPerPage)
}

function loadSortedData(page_number, countPerPage, value){
    let url = '/getSortedData';
    let search_value = document.getElementById("searchInput").value

    $.ajax({
        url: url,
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        data: {page_number: page_number, countPerPage: countPerPage, value: value, search_value: search_value},
        success: function (response) {
            cart = response.content;
        }
    });
}

function loadData(page_number, countPerPage) {
    let url = '/getData';
    let search_value = document.getElementById("searchInput").value

    $.ajax({
        url: url,
        type: 'GET',
        async: false,
        contentType: 'application/json;charset=UTF-8',
        data: {page_number: page_number, countPerPage: countPerPage, search_value: search_value},
        success: function (response) {
            cart = response.content;
            console.log(response.content)
        }
    });
}

function generateProductList(page_number, countPerPage) {
    let out = '';

    if (length < countPerPage)
        countPerPage = length

    let pagesCount = Math.ceil(length / countPerPage);

    for (let i = 0; i < Math.min(countPerPage, Object.keys(cart).length); i++) {
        out += '<li class="pic-container" draggable="true">';
        out += '<a href="'+cart[i]['link']+'">';
        out += '<img src="'+cart[i]['image']+'" alt="Игрушки" width="200" height="200">';
        out += '</a>';
        out += '<div class="Inform">';
        out += '<p>'+cart[i]['name']+'</p>';
        out += '<div class="toy">';
        out += '<p><b>'+toCurrency(cart[i]['cost'])+'</b></p>'
        out += '<button dataArt="'+cart[i]['dataArt']+'" class="buy" onclick="changeButtonColor(this)"><img alt="ошибка" src="/webapp/img/basket.png"></button>';
        out += '</div>';
        out += '</div>';
        out += '</li>';
    }

    $('#product_list').html(out);

    $('button.buy').click();
    $('button.buy').on('click', addToCart);

    $('button.buy').each(function() {
        changeButtonColor(this);
    });

    // remove all existing paging buttons
    $("button.pgn-bnt-styled").remove();

    //add paging buttons
    for (let i = 0; i < pagesCount; i++) {
        let button_tag = "<button>" + (i + 1) + ("</button>");
        let btn = $(button_tag)
            .attr('id', "paging_button_" + i)
            .attr('onclick', "loadGoods(" + i + ")")
            .addClass('pgn-bnt-styled');
        $('#paging_buttons').append(btn);
    }

    // make current page
    if (page_number !== null) {
        let identifier = "#paging_button_" + page_number;
        $(identifier).css("color", "red").css("font-weight", "bold");
    } else {
        $("#paging_button_0").css("color", "red").css("font-weight", "bold");
    }
}

function changeButtonColor(button) {
    let articul = $(button).attr('dataArt')

    if (basket[articul] === undefined){
        button.style.background =  'linear-gradient(to right, #800080, #ffc0cb)';
        button.innerHTML = '<img alt="ошибка" src="/img/basket.png">';
    }
    else{
        button.style.background = 'grey';
        button.innerHTML = '<img alt="ошибка" src="/img/checkmark.png">';
    }
}

function toCurrency(num) {
    return new Intl.NumberFormat("ru-RU", {
        style: "currency",
        currency: "RUB",
        minimumFractionDigits: 0,
    }).format(num);
}

//Добавление в корзину с помощью localStorage
function addToCart(){
    let articular = $(this).attr('dataArt')
    if (articular == undefined)
        return;

    console.log(cart)


    if (basket[articular] == undefined) {
        for (let i = 0; i < Object.keys(cart).length; i++) {
            if (cart[i]['dataArt'] == articular) {
                basket[articular] = cart[i];
                basketCount[articular] = 1;
                found = true;
                break;
            }
        }
    }
    else{
        delete basket[articular];
        delete basketCount[articular];
    }


    localStorage.setItem('basket', JSON.stringify(basket));
    localStorage.setItem('basketCount', JSON.stringify(basketCount));

    changeButtonColor(this);
}

function checkGoods(){
    if (localStorage.getItem('basket') != null) {
        basket = JSON.parse(localStorage.getItem('basket'))
    }

    if (localStorage.getItem('basketCount') != null) {
        basketCount = JSON.parse(localStorage.getItem('basketCount'))
    }
}

/*Драгон дроп*/

/*var total = 0;
var elem;

const dragAndDrop = () => {

    $(document).ready(function(){
        const items = document.querySelectorAll('.pic-container');

        items.forEach((item) =>{
            item.addEventListener("dragstart", dragStart);
        })

        cell = $(".basket")

        cell.on("dragover", dragOver)
        cell.on("drop", dragDrop)
    });

};

function dragStart(){
    elem = this
}

function dragOver(evt){
    evt.preventDefault();
}

function dragDrop(){
    var childElement = $(elem).find(".buy");
    addToCartDragonDrop(childElement);
}

function addToCartDragonDrop(product){
    var articul = $(product).attr('dataArt')

    if (articul === undefined)
        return;

    if (basket[articul] === undefined){
        basket[articul] = 1;
    }

    localStorage.setItem('basket', JSON.stringify(basket));
    loadGoods();
}

dragAndDrop();*/
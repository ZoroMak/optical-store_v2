const express = require('express');
const path = require('path');
const createPath = (page) => path.resolve(__dirname, 'html', `${page}`);

const app = express();

const port = 8084;

app.use(express.static(path.join(__dirname, 'html')));

app.listen(port, (error) => {
    error ? console.log(error) : console.log(`Server is running on port ${port}`);
});

app.get('/', (req, res) => {
    res.sendFile(createPath('home.html'));
});

app.get('/basket', (req, res) => {
    res.sendFile(createPath('basket.html'));
});

app.get('/login', (req, res) => {
    res.sendFile(createPath('login.html'));
});

app.get('/websocket', (req, res) => {
    res.sendFile(createPath('websocket.html'));
});

app.get('/regist', (req, res) => {
    res.sendFile(createPath('regist.html'));
});

app.use((req, res) => {
    console.log('Неправильный url ' + req.url);
})
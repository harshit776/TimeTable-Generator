var express    = require("express");
var session = require('express-session');
var login = require('./routes/login_register');
var bodyParser = require('body-parser');
const opn = require('opn')

var app = express();
app.set('view-engine','ejs')
app.use(session({secret: 'ssshhhhh'}));
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());
app.use(function(req, res, next) {
    res.header("Access-Control-Allow-Origin", "*");
    res.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
    next();
});
//var router = express.Router();
// test route
//app.use(express.static('./register'));
var sess;

app.use(express.static('./forms'));
app.post('/register',login.register);
app.post('/login',login.login)
app.get('/',login.admin);
app.get('/logout',login.logout);
app.get('/display',login.display)
app.get('/cse',login.cse)
app.get('/ece',login.cse)
app.get('/ee',login.cse)
app.get('/civil',login.cse)
app.get('/metta',login.cse)



app.listen(5000, function() {
	console.log('listening to port 5000');
});
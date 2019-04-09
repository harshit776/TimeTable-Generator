var mysql      = require('mysql');
var express    = require("express");
var bodyParser = require('body-parser');
var session = require('express-session');

var app = express();
app.use(session({secret: 'ssshhhhh'}));
app.use(express.static('./forms'));
app.set('view-engine','ejs')

var connection = mysql.createConnection({
  host     : 'localhost',
  user     : 'root',
  password : '',
  database : 'GA_PROJECT'
});
connection.connect(function(err){
if(!err) {
    console.log("Database is connected ... nn");
} else {
    console.log("Error connecting database ... nn");
}
});

var sess;

exports.register = function(req,res){

  var today = new Date();
  var users={
    "first_name":req.body.first_name,
    "last_name":req.body.last_name,
    "email":req.body.email,
    "password":req.body.password,
    "created":today,
    "modified":today
  }
  connection.query('INSERT INTO LOGIN_DETAILS SET ?',users, function (error, results, fields) {
  if (error) {
    console.log("error ocurred",error);
    res.send({
      "code":400,
      "failed":"error ocurred"
    })
  }else{
    console.log('The solution is: ', results);
     res.redirect('/login.html');
  }
  });
}

exports.login = function(req,res){
  sess = req.session;
  console.log(sess);
  if(sess.email)
  {
      console.log("already logged in");
      res.render('F:/testproj/front/hello.ejs',{username:sess.user});
      return;   
  }
  //console.log(sess.user);
  var email= req.body.email;
  var password = req.body.password;
  //console.log(password);
  connection.query('SELECT * FROM LOGIN_DETAILS WHERE email = ?',[email], function (error, results, fields) {
  if (error) {
    // console.log("error ocurred",error);
    res.send({
      "code":400,
      "failed":"error ocurred"
    })
  }else{
    // console.log('The solution is: ', results);
    if(results.length >0){
      if(results[0].PASSWORD == password)
      {
        sess.email=req.body.email;
        sess.user=results[0].FIRST_NAME;
        res.render('F:/testproj/front/hello.ejs',{username:results[0].FIRST_NAME});
        console.log(sess);
      }
      else{
         res.redirect('/login.html');
      }
    }
    else{
       res.redirect('/login.html');
    }
  }
  });
}

exports.admin = function(req,res){
  //console.log("here");
  sess=req.session;
  //console.log(sess);
  if(sess.email)
  {
    console.log("again");
    res.render('F:/testproj/front/hello.ejs',{username:sess.user});
  }
  else
  {
    res.redirect('/hello.html');
  }
}

exports.logout = function(req,res){
  sess=req.session;
  if ( sess.email === undefined )
  {
      console.log("Need to login first");
      res.redirect('/login.html');
      return;
  }
  req.session.destroy(function(err) 
  {
      if(err)
      {
        console.log(err);
      }
      else
      {
          console.log("yes");
          res.redirect('/login.html');
      }
  });
}

exports.display = function(req,res){  
      
      var query = connection.query('SELECT * FROM subjects',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        subs=rows;
      });

      var query = connection.query('SELECT * FROM rooms',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        room=rows;
      });

      var query = connection.query('SELECT * FROM teacher',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        tech=rows;
        res.render('F:/testproj/front/table.ejs',{sub:subs,teach:tech,rm:room});
      });

      //res.render('F:/testproj/front/table.ejs',{data:subs,data2:tech,data3:room});
}

exports.cse = function(req,res){  
      
      var query = connection.query('SELECT * FROM subjects',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        subs=rows;
      });

      var query = connection.query('SELECT * FROM rooms',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        room=rows;
      });

      var query = connection.query('SELECT * FROM teacher',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        tech=rows;
        res.render('F:/testproj/front/tables/cse.ejs',{username:sess.user,sub:subs,teach:tech,rm:room});
      });

      //res.render('F:/testproj/front/table.ejs',{data:subs,data2:tech,data3:room});
}

exports.ece = function(req,res){  
      
      var query = connection.query('SELECT * FROM subjects',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        subs=rows;
      });

      var query = connection.query('SELECT * FROM rooms',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        room=rows;
      });

      var query = connection.query('SELECT * FROM teacher',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        tech=rows;
        res.render('F:/testproj/front/tables/ece.ejs',{username:sess.user,sub:subs,teach:tech,rm:room});
      });

      //res.render('F:/testproj/front/table.ejs',{data:subs,data2:tech,data3:room});
}

exports.ee = function(req,res){  
      
      var query = connection.query('SELECT * FROM subjects',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        subs=rows;
      });

      var query = connection.query('SELECT * FROM rooms',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        room=rows;
      });

      var query = connection.query('SELECT * FROM teacher',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        tech=rows;
        res.render('F:/testproj/front/tables/ee.ejs',{username:sess.user,sub:subs,teach:tech,rm:room});
      });

      //res.render('F:/testproj/front/table.ejs',{data:subs,data2:tech,data3:room});
}

exports.civil = function(req,res){  
      
      var query = connection.query('SELECT * FROM subjects',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        subs=rows;
      });

      var query = connection.query('SELECT * FROM rooms',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        room=rows;
      });

      var query = connection.query('SELECT * FROM teacher',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        tech=rows;
        res.render('F:/testproj/front/tables/civil.ejs',{username:sess.user,sub:subs,teach:tech,rm:room});
      });

      //res.render('F:/testproj/front/table.ejs',{data:subs,data2:tech,data3:room});
}

exports.metta = function(req,res){  
      
      var query = connection.query('SELECT * FROM subjects',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        subs=rows;
      });

      var query = connection.query('SELECT * FROM rooms',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        room=rows;
      });

      var query = connection.query('SELECT * FROM teacher',function(err,rows){
        if(err)
          console.log("Error Selecting : %s ",err );
        //console.log(rows);
        tech=rows;
        res.render('F:/testproj/front/tables/metta.ejs',{username:sess.user,sub:subs,teach:tech,rm:room});
      });

      //res.render('F:/testproj/front/table.ejs',{data:subs,data2:tech,data3:room});
}
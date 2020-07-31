import 'dart:io';

import 'package:flutter/material.dart';

void main() => runApp(LoginFormExample());

class LoginFormExample extends StatelessWidget{
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Login',
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        body: Container(
          padding: EdgeInsets.fromLTRB(20, 120, 20, 120),
          child: Column(
            children: <Widget>[
              Hero(
                tag: 'Hero',
                child: CircleAvatar(
                  child: Image.asset('asset/images/cat.jpeg'),
                  backgroundColor: Colors.transparent,
                  radius: 58.0,
                )
              ),
              SizedBox(height: 45.0),
              TextFormField(
                keyboardType: TextInputType.emailAddress,
                decoration: InputDecoration(
                  hintText: '이메일',
                  border: OutlineInputBorder()
                ),
              ),
              SizedBox(height: 15.0),
              TextFormField(
                keyboardType: TextInputType.visiblePassword,
                obscureText: true,
                decoration: InputDecoration(
                    hintText: '비밀번호',
                    border: OutlineInputBorder()
                ),
              ),
              SizedBox(height: 10.0),
              Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: <Widget>[
                  RaisedButton(
                    child: Text('로그인'),
                    onPressed: () {},
                  ),
                  SizedBox(width: 10.0),
                  RaisedButton(
                    child: Text('취소'),
                    onPressed: () { exit(0); },
                  )
                ],
              )
            ],
          ),
        ),
      ),
    );
  }
}
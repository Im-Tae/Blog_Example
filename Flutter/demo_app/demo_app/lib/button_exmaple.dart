import 'package:flutter/material.dart';

void main() => runApp(ButtonExample());

class ButtonExample extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => ButtonExampleState();
}

class ButtonExampleState extends State<ButtonExample> {

  static const String _title = "Button";
  String _buttonState = 'OFF';

  void onClick() {
    print('onClick');
    setState(() {
      if (_buttonState == 'OFF') _buttonState = "ON";
      else _buttonState = 'OFF';
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: _title,
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(title: Text(_title)),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            RaisedButton(
              child: Text('네모 버튼'),
              onPressed: onClick,
            ),
            Text('$_buttonState'),
            RaisedButton(
              child: Text('둥근 버튼'),
              onPressed: onClick,
              shape: RoundedRectangleBorder(
                borderRadius: new BorderRadius.circular(30.0)
              ),
            )
          ],
        ),
      ),
    );
  }
}
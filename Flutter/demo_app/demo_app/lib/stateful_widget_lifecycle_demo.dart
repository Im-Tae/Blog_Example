import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(
  title: 'Stateless -> Stateful',
  home: Scaffold(
    appBar: AppBar(title: Text('Stateless -> Stateful')),
    body: _MyStatefulWidget(),
  ),
));

class _MyStatefulWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _MyStatefulWidgetState();
}

class _MyStatefulWidgetState extends State<_MyStatefulWidget> {

  var _buttonState;

  @override
  void initState() {
    super.initState();
    print('initState: 기본 값 설정');
    _buttonState = 'OFF';
  }

  @override
  void didChangeDependencies() {
    print('didChangeDependencies 호출');
  }

  @override
  Widget build(BuildContext context) {
    print('build 호출');
    return Column(
      children: <Widget>[
        RaisedButton(
          child: Text('버튼을 누르세요.'),
          onPressed: _onClick,
        ),
        Row(
          children: <Widget>[
            Text('버튼 상태: '),
            Text(_buttonState)
          ],
        )
      ],
    );
  }

  void _onClick() {
    print('onClick 호출');
    setState((){
      print('setState 호출');
      if(_buttonState == 'OFF') {
        _buttonState = 'ON';
      } else {
        _buttonState = 'OFF';
      }
    });
  }

  @override
  void didUpdateWidget(_MyStatefulWidget oldWidget) {
    print('didUpdateWidget');
  }

  @override
  void deactivate() {
    print('deactivate');
  }

  @override
  void dispose() {
    print('dispose');
  }
}
import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(
  title: 'Stateless Widget',
  home: Scaffold(
    appBar: AppBar(title: Text('Stateless 위젯')),
    body: _FirstStatelessWidget(),
  ),
));

class _FirstStatelessWidget extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Text('Stateless');
  }
}
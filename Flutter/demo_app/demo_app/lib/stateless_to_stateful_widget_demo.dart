import 'package:flutter/material.dart';

void main() => runApp(MaterialApp(
  title: 'Stateless -> Stateful',
  home: Scaffold(
    appBar: AppBar(title: Text('Stateless -> Stateful')),
    body: _FirstStatefulWidget(),
  ),
));

class _FirstStatefulWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() => _FirstStatefulWidgetState();
}

class _FirstStatefulWidgetState extends State<_FirstStatefulWidget> {
  @override
  Widget build(BuildContext context) {
    return Text('Stateful');
  }
}
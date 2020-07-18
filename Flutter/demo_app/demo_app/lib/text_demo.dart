import 'package:flutter/material.dart';

void main() => runApp(TextDemo());

class TextDemo extends StatelessWidget {

  static const String _title = "제목";
  static const String _name = "이름";
  static const String _longText = """
  Lorem text Lorem text Lorem text Lorem text Lorem text Lorem text Lorem text Lorem text 
  Lorem text Lorem text Lorem text Lorem text Lorem text Lorem text Lorem text Lorem text 
  """;


  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: _title,
      home: Scaffold(
        appBar: AppBar(title: Text(_title)),
        body: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: <Widget>[
            Text('텍스트'),
            Text(
              'Styled Text $_name',
              style: TextStyle(
                color: Colors.black,
                fontSize: 20.0,
                background: Paint()
                  ..color = Color(0xFFFF2400)
                  ..style = PaintingStyle.fill,
                fontWeight: FontWeight.bold
              ),
            ),
            Text(
              _longText,
              overflow: TextOverflow.ellipsis,
            )
          ],
        ),
      ),
    );
  }
}
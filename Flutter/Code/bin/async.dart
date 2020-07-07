Future<String> string() async => 'Hello, World';

Future getString() async {
  var str = await string();
}

void main() {
  getString();
}
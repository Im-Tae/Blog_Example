class Example {
  String _name;

  void setName(String name) => _name = name;

  void getName() => print(_name);
}

void main() {

  var ex1 = Example();
  ex1.setName('리프');
  ex1.getName();

  var ex2 = Example()
  ..setName('리프')
  ..getName();

}
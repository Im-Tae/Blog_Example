import java.util.*

fun main(args:Array<String>){
    val input = Scanner(System.`in`)
    var list: MutableList<String> = mutableListOf()

    println("입력을 원하는 학생 수를 입력해주세요")
    var c = input.nextInt()


    for (i in 0 until c ){
        println("학생의 학교를 입력해주세요.")
        list.add(input.next())
        println("학생의 반을 입력해주세요.")
        list.add(input.nextInt().toString())

    }

    for (i in list) {
        print("$i ")

        if (i.toIntOrNull() != null) {
            println(" ")
        }
    }

}
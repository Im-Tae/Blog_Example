package common

class Order(private val id: String) {

    fun getId(): String = id

    override fun toString(): String {
        return "Order ID: $id"
    }
}

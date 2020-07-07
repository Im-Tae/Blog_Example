package common


class Shape {

    val HEXAGON = "HEXAGON"
    val OCTAGON = "OCTAGON"
    val RECTANGLE = "RECTANGLE"
    val TRIANGLE = "TRIANGLE"
    val DIAMOND = "DIAMOND"
    val PENTAGON = "PENTAGON"
    val BALL = "BALL"
    val STAR = "STAR"
    val FLIPPED = "(flipped)";

    fun getShape(obj: String): String {
        if (obj == "") return "NO-SHAPE"
        if (obj.endsWith("-H")) return "HEXAGON"
        if (obj.endsWith("-O")) return "OCTAGON"
        if (obj.endsWith("-R")) return "RECTANGLE"
        if (obj.endsWith("-T")) return "TRIANGLE"
        if (obj.endsWith("◇")) return "DIAMOND"
        return "BALL"
    }

    fun getSuffix(shape: String): String {
        if (HEXAGON == shape) return "-H"
        if (OCTAGON == shape) return "-O"
        if (RECTANGLE == shape) return "-R"
        if (TRIANGLE == shape) return "-T"
        if (DIAMOND == shape) return "<>"
        if (PENTAGON == shape) return "-P"
        return if (STAR == shape) "-S"
        else "" // 이것은 BALL
    }

    fun getColor(shape: String): String {
        if (shape.endsWith("<>")) //diamond
            return shape.replace("<>", "").trim(' ')

        val hyphen = shape.indexOf("-")

        return if (hyphen > 0) {
            shape.substring(0, hyphen)
        } else shape
    }

    fun flip(item: String): String {
        if (item.startsWith(FLIPPED)) return item.replace(FLIPPED, "")

        return FLIPPED + item
    }
}
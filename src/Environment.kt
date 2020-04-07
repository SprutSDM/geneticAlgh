import kotlin.math.pow

data class Environment(
    val variables: List<Variable>,
    val bounds: Pair<Float, Float>,
    val step: Float
) {
    fun getInfluence(value: Float) = variables.fold(0f) { prev, next -> prev + next.getInfluence(value) }

    data class Variable(
        val const: Float,
        val power: Float
    ) {
        fun getInfluence(value: Float) = const * value.pow(power)
    }
}

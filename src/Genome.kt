import kotlin.math.pow
import kotlin.random.Random

data class Genome (
    val gens: List<Gen>
) {
    fun getFitness(value: Float) = gens.fold(0f) { prev, next ->
        prev + when (next) {
            is Gen.Fitness -> next.getFitness(value)
            else -> 0f
        }
    }

    sealed class Gen(
        val first: Float,
        val second: Float
    ) {
        class Fitness(
            val const: Float,
            val power: Float
        ) : Gen(first = const, second = power) {
            fun getFitness(value: Float) = first * value.pow(second)
        }

        class Mutation(
            val chanceForMutation: Float,
            val intensityForMutation: Float
        ): Gen(first = chanceForMutation, second = intensityForMutation)

        companion object {
            fun mutateGen(gen: Gen, intensityForMutation: Float): Gen {
                return when (gen) {
                    is Mutation -> Mutation(
                        chanceForMutation = mutateValue(gen.chanceForMutation, intensityForMutation).coerceIn(0f, 1f),
                        intensityForMutation = mutateValue(gen.chanceForMutation, intensityForMutation).coerceIn(0f, 1f)
                    )
                    is Fitness -> Fitness(
                        const = mutateValue(gen.const, intensityForMutation),
                        power = mutateValue(gen.power, intensityForMutation)
                    )
                }
            }

            private fun mutateValue(value: Float, intensityForMutation: Float): Float {
                return value + value * (Random.nextFloat() * intensityForMutation * 2 - 1f)
            }
        }
    }

    fun getMutationGen(): Gen.Mutation? = (gens.find { it is Gen.Mutation }) as Gen.Mutation

    companion object {
        fun generateRandomGenome(gensCount: Int, genBounds: Pair<Float, Float>): Genome {
            val gens = mutableListOf<Gen>()
            gens.add(Gen.Mutation(
                chanceForMutation = Random.nextFloat(),
                intensityForMutation = Random.nextFloat()
            ))
            repeat(gensCount - 1) {
                gens.add(Gen.Fitness(
                    const = genBounds.first + Random.nextFloat() * (genBounds.second - genBounds.first),
                    power = genBounds.first + Random.nextFloat() * (genBounds.second - genBounds.first)
                ))
            }
            return Genome(gens = gens)
        }
    }
}

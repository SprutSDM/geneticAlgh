import java.lang.Math.abs
import kotlin.math.pow
import kotlin.random.Random

class World(
    val env: Environment,
    var individs: List<Individ>
) {
    private val envVariables: List<Pair<Float, Float>> = List(((env.bounds.second - env.bounds.first) / env.step).toInt() + 1) {
        val x = env.bounds.first + it * env.step
        val y = env.getInfluence(x)
        Pair(x, y)
    }

    fun nextGenerate() {
        nextGenerate(findBestIndivid(), individs.size)
    }

    fun findBestIndivid(): Individ {
        var minDiff = -1f
        var bestIndivid = individs.first()
        individs.forEach { individ ->
            val diff = calcDiff(individ)
            if (minDiff == -1f || diff < minDiff) {
                minDiff = diff
                bestIndivid = individ
            }
        }
        return bestIndivid
    }

    private fun nextGenerate(bestIndivid: Individ, count: Int) {
        val mutationGen = bestIndivid.genome.getMutationGen() ?: return
        individs = List(count) {
            Individ(
                genome = Genome(
                    gens = bestIndivid.genome.gens.map {
                        if (Random.nextFloat() <= mutationGen.chanceForMutation) {
                            Genome.Gen.mutateGen(
                                gen = it,
                                intensityForMutation = mutationGen.intensityForMutation)
                        } else {
                            it
                        }
                    }
                )
            )
        }
    }

    fun calcDiff(individ: Individ): Float {
        var sum = 0f
        for (envVar in envVariables) {
            val fitness = individ.getFitness(envVar.first)
            sum += (fitness - envVar.second).pow(2)
        }
        return (sum / (envVariables.size)).pow(0.5f)
    }
}

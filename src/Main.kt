@file:JvmName("Main")

import kotlin.random.Random

fun main() {
    val testWorld = getTestWorld()
    val satisfyDiff = 5f
    var generationNumber = 1

    println("envVariables: ${testWorld.env.variables}")
    while (true) {
        val bestIndivid = testWorld.findBestIndivid()
        val diff = testWorld.calcDiff(bestIndivid)
        if (generationNumber % 100 == 0) {
            println("Generation $generationNumber diff: ${diff}, best individ: $bestIndivid")
        }
        if (diff < satisfyDiff) {
            break
        }
//        readLine()
        testWorld.nextGenerate()
        generationNumber++
    }
    println("Population adapted at gen $generationNumber: ${testWorld.findBestIndivid()}")
}

fun getTestWorld(): World {
    return World(
        env = getTestEnvironment(),
        individs = getTestIndivids()
    )
}

fun getTestEnvironment(): Environment {
    val variables = List(5) {
        Environment.Variable(
            const = Random.nextFloat() * 10f,
            power = Random.nextFloat() * 3f)
    }
    val bounds = Pair(0f, 50f)
    val step = 1f
    return Environment(
        variables = variables,
        bounds = bounds,
        step = step
    )
}

fun getTestIndivids(): List<Individ> {
    return List(10) {
        Individ(genome = Genome.generateRandomGenome(6, Pair(0f, 10f)))
    }
}

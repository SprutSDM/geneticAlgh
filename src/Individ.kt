data class Individ(
    val genome: Genome
) {
    val chanceForMutation: Float
        get() = genome.getMutationGen()?.chanceForMutation ?: 0f
    val intensityForMutation: Float
        get() = genome.getMutationGen()?.chanceForMutation ?: 0f

    fun getFitness(value: Float) = genome.getFitness(value)

    companion object {
        fun makeRandomIndivid(genBounds: Pair<Float, Float>) = Individ(Genome.generateRandomGenome(5, genBounds))
    }
}

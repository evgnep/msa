package ru.otus.graphql

fun <T, R> n1helper(
    list: List<T>,
    getId: (T) -> Int,
    getResults: (Collection<Int>) -> Map<Int, R>,
    def: R?
): Map<T, R> {
    val objectById = list.associateBy(getId)
    val results = mutableMapOf<T, R>()

    getResults(objectById.keys)
        .forEach { (id, result) -> results[objectById[id]!!] = result }

    if (def != null)
        list.forEach { if (results[it] == null) results[it] = def }

    return results
}
import groovy.json.JsonOutput

// If filename is passed as an argument, use it; else default
def filename = args ? args[0] : "builds.csv"
def file = new File(filename)

// Parse CSV rows (skip header)
def rows = file.readLines().drop(1).collect { line ->
    def parts = line.split(',')
    [name: parts[0], time: parts[1].toInteger(), status: parts[2]]
}

// Step A: total build time
def totalTime = rows*.time.sum()

// Step B: successful builds
def successful = rows.findAll { it.status == 'SUCCESS' }*.name

// Step C: group by status
def grouped = rows.groupBy { it.status }

// Step D: longest build
def longest = rows.max { it.time }

// Step E: prepare JSON output
def result = [
    totalTime: totalTime,
    success  : successful,
    grouped  : grouped,
    longest  : [name: longest.name, time: longest.time]
]
// Challenge 1: Failure Count per Status
def failureCounts = [:].withDefault{0}
rows.each { build ->
    if (build.status != 'SUCCESS') {
        failureCounts[build.status]++
    }
}
result.failures = failureCounts

println JsonOutput.prettyPrint(JsonOutput.toJson(result))

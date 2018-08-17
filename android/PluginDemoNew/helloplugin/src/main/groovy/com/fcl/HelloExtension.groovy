
class HelloExtension {
    Closure nameMap = { name ->
        println('hello, '+name)
        return name
    }
    String destDir = "apk"
}
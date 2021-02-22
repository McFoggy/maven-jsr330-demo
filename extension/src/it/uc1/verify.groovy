// Check the extension was used
def foundLines = new File("$basedir", "build.log").readLines().findAll { it =~ /Build started at [0-9]+/ } 
assert 2 == foundLines.size

return true

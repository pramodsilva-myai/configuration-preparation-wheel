# Useful Gradle Commands with Optimizations

Below are some useful Gradle commands to optimize your build process, with comments in English and Sinhala explaining what they do:

| Command | Description in English / Sinhala |
|---------|----------------------------------|
| `./gradlew build` | Standard Gradle build command. <br>**මෙය build එකක් සිදු කිරීම සඳහා ස්වභාවික commad එකයි.** |
| `./gradlew build --scan` | Generates a detailed performance build scan. <br>**මෙය build performance විස්තර ලෙස scan එකක් නිර්මාණය කරයි.** |
| `./gradlew build --parallel` | Runs tasks in parallel. <br>**ආවස්ථා කලාප ගණන වැඩිවීමෙන් සමන්තරව කාර්යයන් ක්‍රියාත්මක කරයි.** |
| `./gradlew clean build` | Cleans the project and then builds. <br>**එලෙසම project එකේ සෑම output file එකක්ම clean කර build එකක් සිදු කරයි.** |
| `./gradlew --no-rebuild --no-recompile-scripts` | Prevents unnecessary rebuilds and recompiles. <br>**අනවශ්‍ය rebuilds නැවත compile වීම වලක්වා ක්‍රියාත්මක වේ.** |
| `./gradlew build --offline` | Runs the build in offline mode (no dependency checks). <br>**මෙම offline mode එක භාවිතයෙන් dependency network requests වලක්වයි.** |
| `./gradlew dependencies` | Shows dependency trees. <br>**මෙම command එකෙන් dependency tree එක inspect කරයි.** |
| `./gradlew build --build-cache` | Uses the build cache for faster builds. <br>**පෙර දත්ත cache එකෙන් ඉක්මනින් build කිරීම සක්‍රීය කරයි.** |
| `./gradlew test --max-parallel-forks=<num>` | Runs tests in parallel forks. <br>**Test සමන්තරව සම්මත CPU ප්‍රමාණයට කෙරේ.** |
| `./gradlew tasks --all` | Lists all tasks available. <br>**සියලුම available tasks එන පරිදි තෝරාගනී.** |
| `./gradlew assemble` | Assembles the project without running tests. <br>**Test එකක් නොකර project එක assemble කරනවා.** |
| `./gradlew wrapper --gradle-version <version>` | Upgrades to the specified Gradle version. <br>**නවතම Gradle සංස්කරණයකට upgrade කිරීම.** |
| `./gradlew --profile` | Generates a profiling report for the build. <br>**මෙම profile එක build performance එක analyze කරන්න.** |

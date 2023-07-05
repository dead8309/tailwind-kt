<div align="center">
<h1>tailwind-kt</h1>

![Maven Central](https://img.shields.io/maven-central/v/io.github.dead8309.tailwind-kt/io.github.dead8309.tailwind-kt.gradle.plugin?style=flat-square&color=5b5ef7)
[![License](https://img.shields.io/github/license/cortinico/kotlin-android-template.svg?style=flat-square&color=5b5ef7)](LICENSE)
![Language](https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?logo=kotlin&style=flat-square&color=5b5ef7)

A gradle plugin that configures tailwindcss to be used in kotlinJs projects

</div>


## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Examples](#examples)
- [Extensions & Tasks](#extensions--tasks)
    - [Extensions](#extensions)
        - [TailwindPluginExtension](#tailwindpluginextension)
    - [Tasks](#tasks)
        - [copyConfigFiles](#copyconfigfiles)
        - [generatedefaultfiles](#generatedefaultfiles)
- [Contributing](#contributing-)
- [License](#license-)

## Installation

**tailwind-kt Plugin** is available on [MavenCentral](https://central.sonatype.com/artifact/io.github.dead8309.tailwind-kt/io.github.dead8309.tailwind-kt.gradle.plugin/)

1\. Add the `mavenCentral()` to your top-level

`settings.gradle`:

```groovy
pluginManagement {
    repositories {
        mavenCentral()
    }
}
```

or `build.gradle`(legacy):

```groovy
buildscript {
    repositories {
        mavenCentral()
    }
}
```

2\. Apply the plugin to your project.

![Maven Central](https://img.shields.io/maven-central/v/io.github.dead8309.tailwind-kt/io.github.dead8309.tailwind-kt.gradle.plugin?style=flat-square&color=5b5ef7)

<details open><summary>Kotlin DSL</summary>

```Kotlin
// build.gradle.kts
plugins {
    id("io.github.dead8309.tailwind-kt").version("$latest_version")
}
```

</details>

<details close><summary>Legacy Groovy</summary>

```groovy
// build.gradle
buildscript {
    //...
    dependencies {
        //...
        classpath 'io.github.dead8309.tailwind-kt:io.github.dead8309.tailwind-kt.gradle.plugin:${latest_version}'
    }
}
apply plugin: "io.github.dead8309.tailwind-kt"
```

</details>


## Usage

To use the tailwind-kt plugin follow these steps:

> **Note**
> The tailwind-kt plugin automatically creates all the required files upon first start of your application. You don't need to create these files manually. The initial files will have default values which you can later customize according to your specific needs.

1. Apply `"io.github.dead8309.tailwind-kt"` plugin.

2. Configure `tailwind {}` block as per your needs.
   - The tailwind-kt plugin offers configuration options that can be customised through the
   `tailwind {}` block in your build.gradle. Although it is **optional and not necessary in
   most cases**, you can adjust the default settings according to your requirements
   .Refer to [TailwindPluginExtension](#tailwindpluginextension) for more details
   - ```groovy
     tailwind {
        configDir.set(rootDir.resolve("my_configs_dir"))
        moduleName.set("my_module_name")
     }
     ```

3. In your `kotlin` block use `setupTailwindProject()` function.
   ```groovy
   kotlin {
     setupTailwindProject()
     // ...
   }
   ```
   The `setupTailwindProject()` function performs the following:
    - Sets up the *Js(IR)* target with webpack settings to enable CSS support.
    - Installs necessary npm dependencies
        - `tailwindcss`: `3.3.2`
        - `postcss`: `8.4.8`
        - `autoprefixer`: `10.4.2`
        - `postcss-loader`: `4.3.0`
    - Adds the Kotlin dependency:
      - `org.jetbrains.kotlin-wrappers:kotlin-extensions`:`1.0.1-pre.256-kotlin-1.5.31`

   #### Skipping Dependencies
    - You can skip installing **npm** dependencies by passing `skipDependencies = true` when calling
    `setupTailwindProject()`.
> **Note**
> The `kotlin-extensions` dependency will always be installed as it is necessary to include your CSS file.

4. Make sure to include `kotlinext.js.require("./globals.css")` in the entry point of your application,
such as `main` function.
    ```kotlin
   fun main() {
     kotlinext.js.require("./globals.css")
     // ...
   }
   ```

> If the `globals.css` file doesn't exist in `resources/globals.css`,the plugin will provide a default one

🎉 Now you can start using tailwind classes in your application.

To create a consistent layout across your app, consider the following:

1. Define a basic layout file and include the `kotlinext.js.require("./globals.css")` line in it.
2. Reuse this layout in all other files to maintain a consistent design, which will automatically
include `globals.css` file in all your files.

For reference, you can check the layout file used in the [shadcn-kotlin](https://github.com/dead8309/shadcn-kotlin/blob/cde4b64e1616e632e5660b195145578fa0fe1dd8/site/src/jsMain/kotlin/org/example/kobwebreaxttailwind/components/layouts/PageLayout.kt#L23) project.

Lastly don't forget to consult the [Tailwind Docs](https://tailwindcss.com) for more detailed information on using Tailwind CSS.

## Examples

Check out the [examples](./examples) folder


## Extensions & Tasks

### Extensions

The tailwind-kt plugin provides the following extension for configuration:

#### TailwindPluginExtension
These values can be configured through `tailwind {}` block in your `build.gradle` file.

| Field        | Type               | Description                                                                                                                                                                                            | Default              |
|--------------|--------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| `configDir`  | `Property<File>`   | The folder containing *tailwind.config.js* and *postcss.config.js* files.                                                                                                                              | `project.projectDir` |
| `moduleName` | `Property<String>` | The name of the folder where the build files packages are located. For most projects this corresponds to the folder under `build/js/packages/[rootProject.name]` in the root directory of your project | `rootProject.name`   |


### Tasks

> **Note**
> In the context of these tasks, `$$configDir` represents the value you have set in the `tailwind {}` extension's `configDir` field, or if you haven't set it, its value will be the default value (`project.projectDir`). Similarly, `$$moduleName` represents the value of the `moduleName` field in the `tailwind {}` extension.

#### copyConfigFiles

This task copies `tailwind.config.js` and `postcss.config.js` from `$$configDir` to `buildDir/js/packages/$$moduleName`.
It runs after every `compileKotlinJs` task and depends on [generateDefaultFiles](#generatedefaultfiles) task


#### generateDefaultFiles

This task generates the following files if they don't already exist to prevent overwriting your configurations:


| File/Folder name         | Location                                       |
|--------------------------|------------------------------------------------|
| tailwind.config.js       | `$$configDir`                                  |
| postcss.config.js        | `$$configDir`                                  |
| globals.css              | `./src/jsMain/resources/`                      |
| webpack.config.d         | `./webpack.config.d/` <br/>(project directory) |
| postcss-loader.config.js | `./webpack.config.d/postcss-loader.config.js`  |

> **Note**
> The `./` notation refers to the project directory in which the plugin is applied.


## Contributing 🤝

Feel free to open an issue or submit a pull request for any bugs/improvements.

## License 📄

This template is licensed under the MIT License. See the [License](LICENSE) file for details.

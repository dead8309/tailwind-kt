<div align="center">
<h1>tailwind-kt</h1>

![Maven Central](https://img.shields.io/maven-central/v/io.github.dead8309.tailwind-kt/io.github.dead8309.tailwind-kt.gradle.plugin?style=flat-square&color=5b5ef7)
[![License](https://img.shields.io/github/license/cortinico/kotlin-android-template.svg?style=flat-square&color=5b5ef7)](LICENSE)
![Language](https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?logo=kotlin&style=flat-square&color=5b5ef7)

A simple gradle plugin that configures tailwindcss to be used in kotlinJs projects

</div>


## Table of Contents

- [Installation](#installation)
- [Configuration](#configuration)
    - [tailwind {}](#tailwind--block)
    - [setupTailwindProject()](#setuptailwindproject)
- [Usage](#usage)
- [Layout File](#layout-file)
- [Extensions & Tasks](#extensions--tasks)
    - [Extensions](#extensions)
        - [TailwindOluginExtension](#tailwindpluginextension)
    - [Tasks](#tasks)
        - [copyConfigFiles](#copyconfigfiles)
        - [generatedefaultfiles](#generatedefaultfiles)
- [Contributing](#contributing-)
- [License](#license-)

## Installation

**Tailwind-kt Plugin** is available on [MavenCentral](https://central.sonatype.com/artifact/io.github.dead8309.tailwind-kt/io.github.dead8309.tailwind-kt.gradle.plugin/)

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


## Configuration

### `tailwind {} block`
* Optional.
* you don't have to configure it manually (in most cases)

* **tailwind-kt** plugin can be customized through `tailwind {}` block in your build.gradle file.

* It comes with some default values. check [TailwindPluginExtension](#tailwindpluginextension)

> Custom configuration
> ```groovy
> tailwind {
>     configDir.set(rootProject.resolve("my_configs_dir"))
>     moduleName.set("my_module_name")
>   }
> ```



### setupTailwindProject()
```groovy
kotlin {
    setupTailwindProject()
    ...
}
```

**tailwind-kt** also provides a utility function `setupTailwindProject()` which sets up

* *Js(IR)* target with webpack settings to enabling css support.
* Additional dependencies:

npm:
- `tailwindcss`: `3.3.2`
- `postcss`: `8.4.8`
- `autoprefixer`: `10.4.2`
- `postcss-loader`: `4.3.0`

kotlin:
- `org.jetbrains.kotlin-wrappers:kotlin-extensions`:`1.0.1-pre.256-kotlin-1.5.31`

#### Skip dependencies

```kotlin
setupTailwindProject(skipDependencies = true)
```

You can skip **npm** dependencies by passing `skipDependencies = true`.

> **Note**
> `kotlin-extensions` will always be installed as its necessary to including your css file.


## Usage

Head over to your file where you want to use tailwindcss and add the below code to the start of your function
```kotlin
fun main() {
    kotlinext.js.require("./globals.css")
    ...
}
```

> globals.css will be provided by the plugin if it does not exist in `resources/globals.css`


See how [kobweb-example](./examples/kobweb/src/jsMain/kotlin/org/example/pages/Index.kt) does it.

#### 🎉 Now we can finally use tailwind classes in our app

- [Tailwind Docs](https://tailwindcss.com)


> **Note**
> You need to always require `globals.css` file wherever you are using tailwind classes

#### Layout File

- Define a basic layout file and add the `kotlinext.js.require("./globals.css")` line there.
- Use this layout in all other files

This is what I do in my
[shadcn-kotlin](https://github.com/dead8309/shadcn-kotlin/blob/cde4b64e1616e632e5660b195145578fa0fe1dd8/site/src/jsMain/kotlin/org/example/kobwebreaxttailwind/components/layouts/PageLayout.kt#L23) project


## Extensions & Tasks

### Extensions

#### TailwindPluginExtension
These values can be configured through `tailwind {}` block.

| field        | type               | description                                                                                                                                                                                                                       | default              |
|--------------|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| `configDir`  | `Property<File>`   | A `folder` containing *tailwind.config.js* & *postcss.config.js*                                                                                                                                                                  | `project.projectDir` |
| `moduleName` | `Property<String>` | Name of the module where the build files packages are located. Usually all the js files are present in the folder named after the project name.<br/> You can find your moduleName by going to root project's `build/js/packages/` | `rootProject.name`   |


### Tasks

#### copyConfigFiles
This task copies `tailwind.config.js` & `postcss.config.js` from `$$configDir` to `buildDir/js/packages/$$moduleName`. It runs after every `compileKotlinJs` task
and depends on [generateDefaultFiles](#generatedefaultfiles)


#### generateDefaultFiles
This task generates the following files only if they don't exist while skipping the ones which does to prevent overwriting your configs

| File                     | configured by | default location                       |
|--------------------------|---------------|----------------------------------------|
| tailwind.config.js       | `$$configDir` | `-`                                    |
| postcss.config.js        | `$$configDir` | `-`                                    |
| globals.css              |               | `src/jsMain/resources/`                |
| webpack.config.d         |               | `project.projectDir`                   |
| postcss-loader.config.js |               | `project.projectDir/webpack.config.d/` |

## Contributing 🤝

Feel free to open an issue or submit a pull request for any bugs/improvements.

## License 📄

This template is licensed under the MIT License. See the [License](LICENSE) file for details.

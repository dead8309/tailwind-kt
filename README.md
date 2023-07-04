# tailwind-kt

[![Pre Merge Checks](https://github.com/cortinico/kotlin-gradle-plugin-template/workflows/Pre%20Merge%20Checks/badge.svg)](https://github.com/cortinico/kotlin-gradle-plugin-template/actions?query=workflow%3A%22Pre+Merge+Checks%22)  [![License](https://img.shields.io/github/license/cortinico/kotlin-android-template.svg)](LICENSE) ![Language](https://img.shields.io/github/languages/top/cortinico/kotlin-android-template?color=blue&logo=kotlin)

A simple gradle plugin that configured tailwindcss to be used in kotlinJs target projects

## Installation
Currently, the plugin is published no where. I'm trying to publish it to maven repo

## Configuration
tailwind-kt plugin can be customized through `tailwind {}` block in your build.gradle file.

- #### The Plugin comes with default values, so you don't have to configure them manually

| name         | type               | description                                                                                                                                                                                                                       | default              |
|--------------|--------------------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------|
| `configDir`  | `Property<File>`   | A `folder` containing *tailwind.config.js* & *postcss.config.js*                                                                                                                                                                  | `project.projectDir` |
| `moduleName` | `Property<String>` | Name of the module where the build files packages are located. Usually all the js files are present in the folder named after the project name.<br/> You can find your moduleName by going to root project's `build/js/packages/` | `rootProject.name`   |

- #### Example of how to configure the plugin based on your specific needs
```groovy
tailwind {
    configDir.set(rootProject.resolve("my_configs_dir"))
    moduleName.set("my_module_name")
}
```
- ### setupTailwindProject()

We provide a utility function `setupTailwindProject()` which sets up *Js(IR)*
target with some other settings like enabling css support.

```groovy
kotlin {
    setupTailwindProject()

}
```

These dependencies are also installed by the utility function

npm:
- `tailwindcss`: `3.3.2`
- `postcss`: `8.4.8`
- `autoprefixer`: `10.4.2`
- `postcss-loader`: `4.3.0`

kotlin:
- `org.jetbrains.kotlin-wrappers:kotlin-extensions`:`1.0.1-pre.256-kotlin-1.5.31`

### Skipping dependencies
You can skip **npm** dependencies by passing `skipDependencies = true` parameter.

```kotlin
setupTailwindProject(skipDependencies = true)
```
**note**: `kotlin-extensions` will always be installed as its necessary to including your css file.

## Usage
Head over to your file where you want to use tailwindcss and add the below code to the start of your function
```kotlin
kotlinext.js.require("./globals.css")
```
See how [kobweb-example](./examples/kobweb/src/jsMain/kotlin/org/example/pages/Index.kt) does it.

##### 🎉 Now we can finally use tailwind classes in our app

- [Tailwind Docs](https://tailwindcss.com)


## Best Practices
#### problem: You need to require css file in every file where you will be using tailwind classes else it won't work*

#### solution:

- Define a basic layout file and add the `kotlinext.js.require("./globals.css")` line there.
- Use this layout in all other files

This is what I do in my
[shadcn-kotlin](https://github.com/dead8309/shadcn-kotlin/blob/cde4b64e1616e632e5660b195145578fa0fe1dd8/site/src/jsMain/kotlin/org/example/kobwebreaxttailwind/components/layouts/PageLayout.kt#L23) project

## Contributing 🤝

Feel free to open an issue or submit a pull request for any bugs/improvements.

## License 📄

This template is licensed under the MIT License. See the [License](LICENSE) file for details.

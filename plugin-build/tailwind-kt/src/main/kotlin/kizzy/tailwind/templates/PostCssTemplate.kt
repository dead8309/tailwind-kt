package kizzy.tailwind.templates

internal fun postCssConfigTemplate() = """
    module.exports = {
      plugins: {
        tailwindcss: {},
        autoprefixer: {},
      }
    }
""".trimIndent()

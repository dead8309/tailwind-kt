package kizzy.tailwind.templates

internal fun tailwindTemplate() = """
    module.exports = {
      darkMode: 'class', // use 'media' or 'class'
      content: [
      "**/*.{html,js}" // This is required for our app to work
      ],
      theme: {
          extend: {},
      },
      variants: {
          extend: {},
      },
      plugins: []
    }
""".trimIndent()


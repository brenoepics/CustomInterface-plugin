require("@rushstack/eslint-patch/modern-module-resolution")

module.exports = {
   "env": {
      "browser": true,
      "commonjs": true,
      "es6": true,
      "node": true
   },
   "parser": "vue-eslint-parser",
   "parserOptions": {
      "parser": "@typescript-eslint/parser",
      "sourceType": "module"
    },
    extends: [
      'eslint:recommended',
      'plugin:vue/essential',
      'plugin:vue/strongly-recommended',
      'plugin:vue/recommended',
      '@vue/eslint-config-typescript',
      '@vue/eslint-config-prettier'
    ],
  }
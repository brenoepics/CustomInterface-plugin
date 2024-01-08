require("@rushstack/eslint-patch/modern-module-resolution")

module.exports = {
    extends: [
      'eslint:recommended',
      'plugin:vue/essential',
      'plugin:vue/strongly-recommended',
      'plugin:vue/recommended',
      '@vue/eslint-config-typescript',
      '@vue/eslint-config-prettier'
    ],
  }
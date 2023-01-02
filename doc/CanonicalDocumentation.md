# Spring Boot H2 Database CRUD example: Building Rest API with Spring Data JPA

[![Travis build status](http://img.shields.io/travis/gajus/prettyprint/master.svg?style=flat-square)](https://travis-ci.org/gajus/prettyprint)
[![NPM version](http://img.shields.io/npm/v/canonical.svg?style=flat-square)](https://www.npmjs.com/package/canonical)
[![js-canonical-style](https://img.shields.io/badge/code%20style-canonical-blue.svg?style=flat-square)](https://github.com/gajus/canonical)




## Install
```
mvn spring-boot:run
```

## API

```js
/**
 * @typedef {Object} optionsType
 * @property {string} indentTemplate String used to indent one level of code (default: '    ').
 * @property {valueIndex|null} valueIndex A function used to index values in the object, the line of declaration in the output and the internal type of the value.
 */

/**
 * @param {Object} subject
 * @param {optionsType} options
 * @returns {string}
 */
prettyprint;
```

## Use

```js
import prettyprint from 'prettyprint';
```

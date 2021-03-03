<h3 align="center">FileSharer</h3>
<p align="center">Using Capacitor 3.0</p>


<p align="center">
Capacitor plugin for Android and iOS that enables sharing of files
</p>

<p align="center">
  <img src="https://img.shields.io/maintenance/yes/2021?style=flat-square" />
  <a href="https://github.com/mpat8121/capacitor-plugin-filesharer/actions?query=workflow%3A%22CI%22"><img src="https://img.shields.io/github/workflow/status/mpat8121/capacitor-plugin-filesharer/CI?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/capacitor-plugin-filesharer"><img src="https://img.shields.io/npm/l/capacitor-plugin-filesharer?style=flat-square" /></a>
<br>
  <a href="https://www.npmjs.com/package/capacitor-plugin-filesharer"><img src="https://img.shields.io/npm/dw/capacitor-plugin-filesharer?style=flat-square" /></a>
  <a href="https://www.npmjs.com/package/capacitor-plugin-filesharer"><img src="https://img.shields.io/npm/v/capacitor-plugin-filesharer?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:START - Do not remove or modify this section -->
<a href="#contributors-"><img src="https://img.shields.io/badge/all%20contributors-2-orange?style=flat-square" /></a>
<!-- ALL-CONTRIBUTORS-BADGE:END -->
</p>

## Maintainers

| Maintainer        | GitHub                                    | Social |
| ----------------- | ----------------------------------------- | ------------------------------------------------------- |
| Mick Patterson    | [mpat8121](https://github.com/mpat8121)   | [@Mick_Patterson_](https://twitter.com/Mick_Patterson_) |
| G. Starr          | [g-starr](https://github.com/g-starr)     |                                                         |

## Install

```bash
npm install capacitor-plugin-filesharer
npm run build or ionic build
npx cap sync
npx cap add android
npx cap add ios
```

## API

<docgen-index>

* [`share(...)`](#share)
* [`shareMultiple(...)`](#sharemultiple)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### share(...)

```typescript
share(opts: FileShareSingleOptions) => Promise<FileShareResult>
```

Open share activity card with an attached base64Data string

| Param      | Type                                                                      |
| ---------- | ------------------------------------------------------------------------- |
| **`opts`** | <code><a href="#filesharesingleoptions">FileShareSingleOptions</a></code> |

**Returns:** <code><a href="#FileShareResult">FileShareResult</a></code>

**Since:** 1.0.0

--------------------


### shareMultiple(...)

```typescript
shareMultiple(opts: FileShareMultiOptions) => Promise<FileShareResult>
```

Open share activity with multiple base64Data strings

| Param      | Type                                                                    |
| ---------- | ----------------------------------------------------------------------- |
| **`opts`** | <code><a href="#filesharemultioptions">FileShareMultiOptions</a></code> |

**Returns:** <code><a href="#FileShareResult">FileShareResult</a></code>

**Since:** 1.0.0

--------------------


### Interfaces


#### FileShareSingleOptions

| Prop         | Type                | Description                     |
| ------------ | ------------------- | ------------------------------- |
| **`header`** | <code>string</code> | Title of the share window popup |


#### FileShareResult

| Prop          | Type                 |
| ------------- | -------------------- |
| **`result`**  | <code>boolean</code> |
| **`message`** | <code>string</code>  |


#### FileShareMultiOptions

| Prop         | Type                | Description                     |
| ------------ | ------------------- | ------------------------------- |
| **`header`** | <code>string</code> | Title of the share window popup |
| **`files`**  | <code>{}</code>     | Array of files for uploading    |


#### FileShareOptions

| Prop              | Type                | Description                                                          |
| ----------------- | ------------------- | -------------------------------------------------------------------- |
| **`filename`**    | <code>string</code> | Filename                                                             |
| **`base64Data`**  | <code>string</code> | base64Data string                                                    |
| **`contentType`** | <code>string</code> | Content type using FileShareContentType of string for advanced types |

</docgen-api>


## Supported Methods

| Name                    | Android | iOS | Web |
| :---------------------- | :------ | :-- | :-- |
| share                   | ✅      | ✅ | ❌ |
| shareMultiple           | ✅      | ✅ | ❌ |

## Contributors

This project follows the [all-contributors](https://github.com/all-contributors/all-contributors) specification. Contributions of any kind welcome!
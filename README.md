# capacitor-plugin-filesharer

Allows sharing of files

## Install

```bash
npm install capacitor-plugin-filesharer
npx cap sync
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
share(opts: FileShareSingleOptions) => any
```

Open share activity card with an attached base64Data

| Param      | Type                                                                      |
| ---------- | ------------------------------------------------------------------------- |
| **`opts`** | <code><a href="#filesharesingleoptions">FileShareSingleOptions</a></code> |

**Returns:** <code>any</code>

**Since:** 0.0.1

--------------------


### shareMultiple(...)

```typescript
shareMultiple(opts: FileShareMultiOptions) => any
```

Open share activity with multiple base64Data strings

| Param      | Type                                                                    |
| ---------- | ----------------------------------------------------------------------- |
| **`opts`** | <code><a href="#filesharemultioptions">FileShareMultiOptions</a></code> |

**Returns:** <code>any</code>

**Since:** 0.0.1

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

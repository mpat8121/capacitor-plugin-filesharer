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
* [Enums](#enums)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### share(...)

```typescript
share(opts: FileShareOptions) => any
```

| Param      | Type                                                          |
| ---------- | ------------------------------------------------------------- |
| **`opts`** | <code><a href="#fileshareoptions">FileShareOptions</a></code> |

| Param      | Type                                                          |
| ---------- | ------------------------------------------------------------- |
| **`opts`** | <code><a href="#fileshareoptions">FileShareOptions</a></code> |

**Returns:** <code>any</code>

**Since:** 0.0.1

--------------------


### shareMultiple(...)

```typescript
shareMultiple(opts: FileShareMultiOptions) => any
```

| Param      | Type                                                                    |
| ---------- | ----------------------------------------------------------------------- |
| **`opts`** | <code><a href="#filesharemultioptions">FileShareMultiOptions</a></code> |

**Returns:** <code>any</code>

--------------------


### Interfaces


#### FileShareOptions

| Prop              | Type                |
| ----------------- | ------------------- |
| **`filename`**    | <code>string</code> |
| **`base64Data`**  | <code>string</code> |
| **`contentType`** | <code>string</code> |
| **`header`**      | <code>string</code> |


#### FileShareMultiOptions

| Prop         | Type                |
| ------------ | ------------------- |
| **`header`** | <code>string</code> |
| **`files`**  | <code>{}</code>     |


NOTE: The header property does not apply to all OS versions

</docgen-api>

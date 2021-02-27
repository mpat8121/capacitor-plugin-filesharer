# capacitor-plugin-filesharer

Allows sharing of files

## Install

```bash
npm install capacitor-plugin-filesharer
npx cap sync
```

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`share(...)`](#share)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => any
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>any</code>

--------------------


### share(...)

```typescript
share(opts: FileShareOptions) => any
```

| Param      | Type                                                          |
| ---------- | ------------------------------------------------------------- |
| **`opts`** | <code><a href="#fileshareoptions">FileShareOptions</a></code> |

**Returns:** <code>any</code>

--------------------


### Interfaces


#### FileShareOptions

| Prop              | Type                |
| ----------------- | ------------------- |
| **`filename`**    | <code>string</code> |
| **`base64Data`**  | <code>string</code> |
| **`contentType`** | <code>string</code> |

</docgen-api>

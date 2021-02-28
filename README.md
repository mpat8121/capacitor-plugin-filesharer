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

Open share activity card with an attached base64Data

| Param      | Type                                                          |
| ---------- | ------------------------------------------------------------- |
| **`opts`** | <code><a href="#fileshareoptions">FileShareOptions</a></code> |

**Returns:** <code>any</code>

**Since:** 0.0.1

--------------------


### shareMultiple(...)

```typescript
shareMultiple(opts: any) => any
```

| Param      | Type             |
| ---------- | ---------------- |
| **`opts`** | <code>any</code> |

**Returns:** <code>any</code>

--------------------


### Interfaces


#### FileShareOptions

| Prop              | Type                |
| ----------------- | ------------------- |
| **`filename`**    | <code>string</code> |
| **`base64Data`**  | <code>string</code> |
| **`contentType`** | <code>string</code> |


### Enums


#### FileShareContentType

| Members          | Value                          |
| ---------------- | ------------------------------ |
| **`TEXT`**       | <code>'text/*'</code>          |
| **`TEXT_PLAIN`** | <code>'text/plain'</code>      |
| **`TEXT_RTF`**   | <code>'text/rtf'</code>        |
| **`TEXT_HTML`**  | <code>'text/html'</code>       |
| **`TEXT_JSON`**  | <code>'text/json'</code>       |
| **`IMAGE`**      | <code>'image/*'</code>         |
| **`IMAGE_JPG`**  | <code>'image/jpg'</code>       |
| **`IMAGE_PNG`**  | <code>'image/png'</code>       |
| **`IMAGE_GIF`**  | <code>'image/gif'</code>       |
| **`PDF`**        | <code>'application/pdf'</code> |

</docgen-api>

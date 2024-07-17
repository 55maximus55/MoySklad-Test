# API
## Products
### Получить список товаров
```
GET: /products
```
#### Параметры запроса
Сортировка:
- `sortBy` - Поле по которому будет сортировка
- `order` - `asc` или `desc` в порядке возрастания/убывания

Фильтры:
- `name` - имя товара
- `priceMin` - Минимальная цена
- `priceMax` - Максимальная цена
#### Ответ
```json
[
  {
    "id": 0,
    "name": "apple",
    "description": "lorem ipsum",
    "price": 2.0,
    "inStock": false
  },
  {
    "id": 1,
    "name": "orange",
    "description": "lorem ipsum",
    "price": 3.0,
    "inStock": true
  },
  {
    "id": 2,
    "name": "banana",
    "description": "lorem ipsum",
    "price": 1.0,
    "inStock": true
  }
]
```



### Получить отдельный товар
```
/products/{id}
```
#### Ответ
```json
{
  "id": 2,
  "name": "banana",
  "description": "lorem ipsum",
  "price": 1.0,
  "inStock": true
}
```



### Создать товар
```
/products/create
```
#### Тело запроса
- `name` - Название товара
- `description` - Описание товара
- `price` - Цена товара
- `inStock` - Наличие товара
```json
{
  "name": "banana",
  "description": "lorem ipsum",
  "price": 1.0,
  "inStock": true
}
```
#### Ответ
В случае успешного создания товара возвращает его вместе с его `id`
```json
{
  "id": 2,
  "name": "banana",
  "description": "lorem ipsum",
  "price": 1.0,
  "inStock": true
}
```



### Изменить товар
```
/products/update
```
#### Тело запроса
- `id` - идентификатор изменяемого товара
- `name` - Название товара
- `description` - Описание товара
- `price` - Цена товара
- `inStock` - Наличие товара
```json
{
  "id": 2,
  "name": "Banana",
  "inStock": true
}
```
#### Ответ
Возвращает изменённый товар
```json
{
  "id": 2,
  "name": "Banana",
  "description": "lorem ipsum",
  "price": 2.0,
  "inStock": true
}
```



### Удалить товар
- `id` - идентификатор удаляемого товара
```
/products/delete
```
#### Тело запроса
```json
{
  "id": 1
}
```
#### Ответ
```json
{
  "status": 200
}
```
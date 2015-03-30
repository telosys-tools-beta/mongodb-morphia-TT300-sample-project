# mongodb-morphia-TT300
MongoDB with Morphia

# Todo List
Done :

Todo :
* Generate basic classes for Morphia
* Generated tests run sucessfully
* Manage embedded entities

# Sample

## Model (Telosys DSL)

```js
Employee {
	id : integer { @Id }; // the id
	firstName : string ;
	birthDate : date ;
	country 
}
```

```js
Country {
    code: string { @Id };
    label: string ;
}
```

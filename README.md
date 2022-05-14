# Immutable entities

```
Author --- (1..*) ---> Book
```

It does not seem to be possible to work with immutable objects in Hibernate. The pro of this approach would be that
Hibernate does not manage the copy of the object once this is done and the logic to update or modify an object would
reside
in a single place like a `Builder` or a `copy` factory method, for example:

```java
public static Author of(Author from){
    return new Author(from.getName(), from.getBooks());
}
```

but doing this will result in Hibernate triggering a select statement to retrieve all the `Book`s associated with the `Author` which 
is absolutely not acceptable.

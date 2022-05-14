# hibernate-playground

## One-To-Many Associations

```
Author --- (1..*) ---> Book
```

### Unidirectional mapping:

It is possible to map a `@OneToMany` association in two different ways. 

#### From the owning side to the owned side

In this case the `Author` has a reference to the `Book`. In the `Author` class the association with the `Book` is specified this way:

```java
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "ASD_ID")
private List<Book> books;
```

For unidirectional `@OneToMany` associations the join column will be created in the `Book` class and `mappedBy` is only necessary when the relationship is bidirectional.
So the resulting model looks as follows:

```
create table AUTHOR (ID bigint not null, NAME varchar(255), primary key (ID))
create table BOOK (id bigint not null, name varchar(255), ASD_ID bigint, primary key (id))
create index name_idx on BOOK (name)
```

#### Cons

This way of mapping is not the best way to go about this kind of association. When the `@OneToMany` is in the main entity, hibernate triggers an update when saving the `Book`:

```
insert into AUTHOR (NAME, ID) values (?, ?)
binding parameter [1] as [VARCHAR] - [Joe]
binding parameter [2] as [BIGINT] - [1]

insert into BOOK (name, id) values (?, ?)
binding parameter [1] as [VARCHAR] - [Nice book]
binding parameter [2] as [BIGINT] - [1]

update BOOK set ASD_ID=? where id=?
binding parameter [1] as [BIGINT] - [1]
binding parameter [2] as [BIGINT] - [1]
```

## TODO
- [ ] Fix README.md
# hibernate-playground

## One-To-Many Association

```
Author --- (1..*) ---> Book
```

### Double-side association with `mappedBy`

To map a bidirectional association between an `Author` and a `Book` we can proceed as follows. On one side of the association, in the `Author` class we can add:

```java
@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
private List<Book> books;
```

On the other side of the association, in the `Book` class we just need a `@ManyToOne`:

```java
@ManyToOne
@JoinColumn(name = "ASD_ID") // This is not necessary, but it is nice to be able to define a decent name for the foreign key
private Author author;
```

This is the resulting model:

```
create table AUTHOR (ID bigint not null, NAME varchar(255), primary key (ID))
create table Book (id bigint not null, name varchar(255), ASD_ID bigint, primary key (id))
create index book_name_idx on Book (name)
```

Inserting data into the DB produces the following statements:

```
insert into AUTHOR (NAME, ID) values (?, ?)
binding parameter [1] as [VARCHAR] - [Joe]
binding parameter [2] as [BIGINT] - [1]
insert into Book (ASD_ID, name, id) values (?, ?, ?)
binding parameter [1] as [BIGINT] - [1]
binding parameter [2] as [VARCHAR] - [Nice book]
binding parameter [3] as [BIGINT] - [1]
```
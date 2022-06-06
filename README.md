# One-To-Many Association

### Double-side association with `mappedBy`

```
Author <--- (1..*) ---> Book
```

To map a bidirectional association between an `Author` and a `Book` we can proceed as follows. On one side of the association, in the `Author` class we can add:

```java
@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
private Set<Book> books;
```

On the other side of the association, in the `Book` class we just need a `@ManyToOne`:

```java
@ManyToOne
@JoinColumn(name = "AUTHOR_ID") // This is not necessary, but it is nice to be able to define a decent name for the foreign key
private Author author;
```

This is the resulting model:

```
DEBUG: create sequence AUTHOR_SEQ start with 1 increment by 50
DEBUG: create sequence BOOK_ID_GEN start with 1 increment by 50

DEBUG: create table AUTHOR (ID bigint not null, NAME varchar(255), primary key (ID))
DEBUG: create table BOOK (id bigint not null, name varchar(255), AUTHOR_ID bigint, primary key (id))

DEBUG: create index book_name_idx on BOOK (name)

DEBUG: alter table if exists BOOK add constraint UK_kkd7aa41jutasor701u64yvs8 unique (name)
DEBUG: alter table if exists BOOK add constraint FKbj8bip3gmii2iwakx86il4939 foreign key (AUTHOR_ID) references AUTHOR
```

Inserting an `Author` with a `Book` into the DB produces the following statements:

```
DEBUG: select next value for AUTHOR_SEQ
DEBUG: select next value for BOOK_ID_GEN

DEBUG: insert into AUTHOR (NAME, ID) values (?, ?)
TRACE: binding parameter [1] as [VARCHAR] - [Joe]
TRACE: binding parameter [2] as [BIGINT] - [1]

DEBUG: insert into BOOK (AUTHOR_ID, name, id) values (?, ?, ?)
TRACE: binding parameter [1] as [BIGINT] - [1]
TRACE: binding parameter [2] as [VARCHAR] - [Nice book]
TRACE: binding parameter [3] as [BIGINT] - [1]
```

## TODO
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
022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - create sequence BOOK_ID_GEN start with 1 increment by 50

2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - create table AUTHOR (ID bigint not null, NAME varchar(255), primary key (ID))
2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - create table BOOK (id bigint not null, name varchar(255), AUTHOR_ID bigint, primary key (id))

2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - create index book_name_idx on BOOK (name)

2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - alter table if exists BOOK add constraint UK_kkd7aa41jutasor701u64yvs8 unique (name)
2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - alter table if exists BOOK add constraint FKbj8bip3gmii2iwakx86il4939 foreign key (AUTHOR_ID) references AUTHOR
```

Inserting an `Author` with a `Book` into the DB produces the following statements:

```
2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - select next value for AUTHOR_SEQ
2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - select next value for BOOK_ID_GEN

2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - insert into AUTHOR (NAME, ID) values (?, ?)
2022-May-14 14:42:53 pm [main] DEBUG org.hibernate.SQL - insert into BOOK (AUTHOR_ID, name, id) values (?, ?, ?)
```

## TODO
- [ ] Fix missing parameters in logs
# One-To-Many Associations

## Unidirectional mapping:

It is possible to map a `@OneToMany` association in two different ways. 

### From the owning side to the owned side

```
Author --- (1..*) ---> Book
```

In this case the `Author` has a reference to the `Book`. In the `Author` class the association with the `Book` is specified this way:

```java
@OneToMany(cascade = CascadeType.ALL)
@JoinColumn(name = "AUTHOR_ID")
private Set<Book> books;
```

For unidirectional `@OneToMany` associations the join column will be created in the `Book` class and `mappedBy` is only necessary when the relationship is bidirectional.
So the resulting model looks as follows:

```
2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - create table AUTHOR (ID bigint not null, NAME varchar(255), primary key (ID))
2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - create table BOOK (id bigint not null, isbn varchar(255), name varchar(255), AUTHOR_ID bigint, primary key (id))
2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - create index book_name_idx on BOOK (name)
```

#### Cons:

This way of mapping is not the best way to go about this kind of association. When the `@OneToMany` is in the main entity, hibernate triggers an update when saving the `Book`:

```
2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - select next value for AUTHOR_SEQ
2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - select next value for BOOK_ID_GEN
2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - select next value for BOOK_ID_GEN

2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - insert into AUTHOR (NAME, ID) values (?, ?)

2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - insert into BOOK (isbn, name, id) values (?, ?, ?)
2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - insert into BOOK (isbn, name, id) values (?, ?, ?)

2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - update BOOK set AUTHOR_ID=? where id=?
2022-May-14 14:11:39 pm [main] DEBUG org.hibernate.SQL - update BOOK set AUTHOR_ID=? where id=?
```

### From the owned side to the owning side

```
Customer <--- (1..*) --- Receipt
```

In this case the `Receipt` class has a `@ManyToOne` association to `Customer`.

In the `Receipt` class:

```java
@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
private Customer customer;
```

Following is the table structure:

```shell
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - create table CUSTOMER (id bigint not null, name varchar(255), naturalId varchar(255), primary key (id))
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - create table RECEIPT (id bigint not null, naturalId varchar(255), customer_id bigint, primary key (id))

2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - create index custromer_dummy_idx on CUSTOMER (naturalId)
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - alter table if exists CUSTOMER add constraint UK_nu8smlkhvd2adkus22dpcymes unique (naturalId)

2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - create index receipt_dummy_idx on RECEIPT (naturalId)
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - alter table if exists RECEIPT add constraint UK_8ep6h9jji42jrv23x6fdgtnpx unique (naturalId)
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - alter table if exists RECEIPT add constraint FKiwp9cc69vsg1tpy9kyups69d5 foreign key (customer_id) references CUSTOMER
```

When trying to persist a `Receipt` leveraging the `CascadeType.PERSIST` this is what happens:

```shell
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - select next value for RECEIPT_ID_GEN
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - select next value for CUSTOMER_ID_GEN

2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - insert into CUSTOMER (name, naturalId, id) values (?, ?, ?)
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - insert into RECEIPT (customer_id, naturalId, id) values (?, ?, ?)
2022-May-14 14:18:58 pm [main] DEBUG org.hibernate.SQL - select r1_0.id,r1_0.customer_id,r1_0.naturalId from RECEIPT r1_0
```

On the other hand, trying to persist a `Customer` will result in no `Receipt` being persisted to the database.

## TODO
- [ ] Fix missing parameters in logs
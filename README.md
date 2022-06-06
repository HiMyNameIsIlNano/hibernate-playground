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
DEBUG: create table AUTHOR (ID bigint not null, NAME varchar(255), primary key (ID))
DEBUG: create table BOOK (id bigint not null, isbn varchar(255), name varchar(255), AUTHOR_ID bigint, primary key (id))
DEBUG: create index book_name_idx on BOOK (name)
```

#### Cons:

This way of mapping is not the best way to go about this kind of association. When the `@OneToMany` is in the main entity, hibernate triggers an update when saving the `Book`:

```
DEBUG: select next value for AUTHOR_SEQ
DEBUG: select next value for BOOK_ID_GEN
DEBUG: select next value for BOOK_ID_GEN

DEBUG: insert into AUTHOR (NAME, ID) values (?, ?)
TRACE: binding parameter [1] as [VARCHAR] - [Joe]
TRACE: binding parameter [2] as [BIGINT] - [1]

DEBUG: insert into BOOK (isbn, name, id) values (?, ?, ?)
TRACE: binding parameter [1] as [VARCHAR] - [XYZ-456]
TRACE: binding parameter [2] as [VARCHAR] - [Awesome book]
TRACE: binding parameter [3] as [BIGINT] - [1]

DEBUG: insert into BOOK (isbn, name, id) values (?, ?, ?)
TRACE: binding parameter [1] as [VARCHAR] - [XYZ-123]
TRACE: binding parameter [2] as [VARCHAR] - [Nice book]
TRACE: binding parameter [3] as [BIGINT] - [2]

DEBUG: update BOOK set AUTHOR_ID=? where id=?
TRACE: binding parameter [1] as [BIGINT] - [1]
TRACE: binding parameter [2] as [BIGINT] - [1]

DEBUG: update BOOK set AUTHOR_ID=? where id=?
TRACE: binding parameter [1] as [BIGINT] - [1]
TRACE: binding parameter [2] as [BIGINT] - [2]
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
DEBUG: create table CUSTOMER (id bigint not null, name varchar(255), naturalId varchar(255), primary key (id))
DEBUG: create table RECEIPT (id bigint not null, naturalId varchar(255), customer_id bigint, primary key (id))

DEBUG: create index custromer_dummy_idx on CUSTOMER (naturalId)
DEBUG: alter table if exists CUSTOMER add constraint UK_nu8smlkhvd2adkus22dpcymes unique (naturalId)

DEBUG: create index receipt_dummy_idx on RECEIPT (naturalId)
DEBUG: alter table if exists RECEIPT add constraint UK_8ep6h9jji42jrv23x6fdgtnpx unique (naturalId)
DEBUG: alter table if exists RECEIPT add constraint FKiwp9cc69vsg1tpy9kyups69d5 foreign key (customer_id) references CUSTOMER
```

When trying to persist a `Receipt` leveraging the `CascadeType.PERSIST` this is what happens:

```shell
DEBUG: select next value for RECEIPT_ID_GEN
DEBUG: select next value for CUSTOMER_ID_GEN

DEBUG: insert into CUSTOMER (name, naturalId, id) values (?, ?, ?)
TRACE: binding parameter [1] as [VARCHAR] - [Jack]
TRACE: binding parameter [2] as [VARCHAR] - [XYZ-123]
TRACE: binding parameter [3] as [BIGINT] - [1]

DEBUG: insert into RECEIPT (customer_id, naturalId, id) values (?, ?, ?)
TRACE: binding parameter [1] as [BIGINT] - [1]
TRACE: binding parameter [2] as [VARCHAR] - [831a2f87-8580-452d-8b6a-eb62d9b28ba5]
TRACE: binding parameter [3] as [BIGINT] - [1]

DEBUG: select r1_0.id,r1_0.customer_id,r1_0.naturalId from RECEIPT r1_0
```

On the other hand, trying to persist a `Customer` will result in no `Receipt` being persisted to the database.

## TODO

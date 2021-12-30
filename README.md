# hibernate-playground

## One-To-Many Associations

```
Author --- (1..*) ---> Book
```

### Single-side mapping with @JoinColumn only:

In the Author class (owning entity):

```java
import javax.persistence.JoinColumn;

@OneToMany
@JoinColumn(name = "AUTHOR_ID")
List<Book> books;
```

The resulting model is:

```
create table BOOK (id bigint not null, name varchar(255), AUTHOR_ID bigint, primary key (id))
create table CUSTOMER (ID bigint not null, NAME varchar(255), primary key (ID))
```

#### Cons

When the `@OneToMany` is in the main entity, hibernate triggers an update when saving the `Book`:

```
insert into CUSTOMER (NAME, ID) values (?, ?)
binding parameter [1] as [VARCHAR] - [Joe]
binding parameter [2] as [BIGINT] - [1]

insert into BOOK (name, id) values (?, ?)
binding parameter [1] as [VARCHAR] - [Nice book]
binding parameter [2] as [BIGINT] - [1]

update BOOK set AUTHOR_ID=? where id=?
binding parameter [1] as [BIGINT] - [1]
binding parameter [2] as [BIGINT] - [1]
```

### Single-side association with @JoinColumn

In the `Book` class (the owning entity):

```java
@ManyToOne
@JoinColumn(name = "ASD_ID")
private Author author;
```

The resulting model is:

```
create table BOOK (id bigint not null, name varchar(255), ASD_ID bigint, primary key (id))
create table CUSTOMER (ID bigint not null, NAME varchar(255), primary key (ID))
```

The insertion does trigger the following statements:

```
insert into CUSTOMER (NAME, ID) values (?, ?)
binding parameter [1] as [VARCHAR] - [Joe]
binding parameter [2] as [BIGINT] - [1]
insert into BOOK (ASD_ID, name, id) values (?, ?, ?)
binding parameter [1] as [BIGINT] - [1]
binding parameter [2] as [VARCHAR] - [Nice book]
binding parameter [3] as [BIGINT] - [1]
```

#### Cons

In this case the `Author` does not know anything about the book, which means, that if one wants to know what `Book`(s) were written by an `Author` two queries have to be triggered.

### Double-side association with `mappedBy`

In the `Author` class (the owned entity):

```java
@OneToMany(cascade = CascadeType.ALL, mappedBy = "author")
private List<Book> books;
```

In the `Book` class (the owning entity):

```java
@ManyToOne
@JoinColumn(name = "ASD_ID")
private Author author;
```

This is the resulting model:

```
create table BOOK (id bigint not null, name varchar(255), ASD_ID bigint, primary key (id))
create table CUSTOMER (ID bigint not null, NAME varchar(255), primary key (ID))
```

```
create table BOOK (id bigint not null, name varchar(255), author_ID bigint, primary key (id))
create table CUSTOMER (ID bigint not null, NAME varchar(255), primary key (ID))
```

The insertion does not trigger any update:

```
insert into CUSTOMER (NAME, ID) values (?, ?)
binding parameter [1] as [VARCHAR] - [Joe]
binding parameter [2] as [BIGINT] - [1]
insert into BOOK (author_ID, name, id) values (?, ?, ?)
binding parameter [1] as [BIGINT] - [1]
binding parameter [2] as [VARCHAR] - [Nice book]
binding parameter [3] as [BIGINT] - [1]
```
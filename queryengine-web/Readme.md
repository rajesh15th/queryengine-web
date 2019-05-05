
# Search Query Engine for Database Tables lookup

Its hard to find the tables which contains the specific word in its rows. So This module will give a flexibility of configuring database credentials and search on the database

# Configure application.properties

```properties
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:postgresql://localhost:5432/queryengine
spring.datasource.username=personuser
spring.datasource.password=YOUR_PASSWORD
```

# Getting Started


There are two screens for in this product
* [Search](#search)
* [Database configuration](#database-configuration)

# Search
### Here we can screen choose the datasource and search the word in it.

*	select the datasource and enter the word you want to search
![alt Input page](https://raw.githubusercontent.com/rajesh15th/queryengine-web/master/queryengine-web/src/main/resources/query-engine/word-search-results.JPG)
*	Once you click on the Get Data button, we can see the results page
* 	There are several buttons.
![alt Input page](https://raw.githubusercontent.com/rajesh15th/queryengine-web/master/queryengine-web/src/main/resources/query-engine/results-page.JPG)
![alt Input page](https://raw.githubusercontent.com/rajesh15th/queryengine-web/master/queryengine-web/src/main/resources/query-engine/execution-information.JPG)

## Database configuration
Its a CRUD operation database info table.

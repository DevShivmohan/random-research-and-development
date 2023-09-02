## GraphQL example with Spring boot

- Create type inside the `resources/graphql/schema.graphqls`.
- While requesting with GraphQL then by default its base URL - `<HOST>:<PORT>/graphql` with POST method of every request.
- When want to access the H2 database then hit the url - `<HOST>:<PORT>/h2-console` in the browser and enter username and password from the properties file.
  - GraphQL request syntax/example
      - For single Object syntax
        ```
        query{
            getStudent(id:<value>){
                # Required response parameters
                <parameter_1>
                <parameter_2>
                <parameter_n>
            }
        }
        ```
      - For single Object example
        ```
          query{
              getStudent(id:25){
                  # Required response parameters
                  name
                  age
              }
          }
        ```
    - For List of Objects syntax
      ```
      query{
          getStudents{
              # Required response parameters
              <parameter_1>
              <parameter_2>
              <parameter_n>
          }
      }
      ```
    - For List of Objects example
      ```
        query{
            getStudents{
                # Required response parameters
                name
                age
            }
        }
      ```
    - For mutation(Create/Update) of Object example
      ```
      query{
          addStudent(studentDTO:{
              # Request body data
              name:"Shiv",
              branch:"CSE",
              age:23,
              isActive:true
          }){
              # Required response parameters
              <parameter_1>
              <parameter_2>
              <parameter_n>
          }
      }
      ```
Spring boot automatically follows the mvc architecture.
You have models, controllers and view. 

## models
In the models, which we'll define in the models folder, you'll create
domain classes, which are annotated with @Entity so that JPA,(Java Persistence API)
can mark it as persistent. 
This is also what you'll use to link with the database later with the jdbc provided by postgres.

There's a must for the @ID fields and the Id private member, this should correspond
with the type you have set in your database.

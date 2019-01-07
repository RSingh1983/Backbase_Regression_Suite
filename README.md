**Backbase automation framework**

This framework is written using Serenity BDD with Cucumber JVM.

**The project structure is shown below:**

+ **main**: Contains all the utility and basic functions which are generic and can be used across projects.
+ **test**: Contains all the tests and their corresponding implementations.


**Steps to run:**
+ mvn clean install -Denv=live -Dcucumber.options="--tags @regression"

where **regression** is the tag of the features which need to be run and **live** is the name of the property file specifying the properties specific to a test environment.
# SpringCloud Contract Provider Demo

In this example demonstrates the following

* Contract creation on the provider side.
* Contract verification 
* Contract stub creation

### Contract creation on the provider side
Spring Cloud contracts are creating in Groovy DSL or .yml. In this example we will be create 2 contracts saveUser.groovy and updateUser.groovy created in the resources folder of the test.

#### Key task needed for setting up build.gradle

Spring cloud contract provides a plugin to take care of generating test for the contract test 

    `apply plugin: 'spring-cloud-contract' ` 


The plugin needs the following dependencies 

    buildscript {
      repositories {
        // ...
      }
      dependencies {
        classpath "org.springframework.boot:spring-boot-gradle-plugin"
        classpath "org.springframework.cloud:spring-cloud-contract-gradle-plugin"
      }
    }

In the contracts closure we define some configuration

    contracts {
      baseClassMappings {
        baseClassMapping(".*userservice.*", "io.selflearning.UserServiceBase")
      }
    }
      
The mapping we defined above tells spring cloud contract to generate test  for all contracts its finds in `src/test/resources/contracts`  that contains "userservice"  in their path as to be subclassed from the test base class "UserServiceBase"

n order for the automatically generated tests to work, we need to include some further dependencies in the testCompile scope:

    dependencies {
      // ...
      testCompile('org.codehaus.groovy:groovy-all')
      testCompile("org.springframework.cloud:spring-cloud-starter-contract-verifier")
      testCompile("org.springframework.cloud:spring-cloud-contract-spec")
      testCompile("org.springframework.boot:spring-boot-starter-test")
    }
    
To generate the test we call 

    ./gradlew generateContractTests
  
    
### Contract verification

One the build process kicks in the test automically verfy the contracts


### Contract stub creation

Stubs are needs to test out consumers of  this contract. Inorder for consumers to test contract against stubs , the stubs needs to be generated and copied to local and remote artifactory so that the test can use the local for local testing and the remote stub n the articatory for CI.

    apply plugin: 'maven-publish'
    
We want to control the `groupId`, `version` and `artifactId` of the stub so that we can later use these coordinates to load the stub from the Maven repository. For this, we add some information to `build.gradle`    :

    group = 'io.selflearning'
    version = '0.0.1-SNAPSHOT'

The the artifactId can be set up in settings.gradle (unless you’re OK with it being the name of the project directory, which is the default):

    rootProject.name = 'user-service'
    
Then we run 

    ./gradlew publishToMavenLocal
    